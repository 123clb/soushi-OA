/**
 * 
 */
package com.kaoshidian.oa.permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaoshidian.oa.base.EntityView;
import com.kaoshidian.oa.permission.dao.OrgDao;
import com.kaoshidian.oa.permission.dao.UserDAO;
import com.kaoshidian.oa.permission.dao.UserRoleDao;
import com.kaoshidian.oa.permission.entity.Org;
import com.kaoshidian.oa.permission.entity.User;
import com.kaoshidian.oa.permission.entity.UserRole;
import com.kaoshidian.oa.permission.entity.UserStateEnum;
import com.kaoshidian.oa.permission.entity.UserTypeEnum;
import com.kaoshidian.tool.ldap.LdapAccountControl;
import com.kaoshidian.tool.ldap.LdapOrg;
import com.kaoshidian.tool.ldap.LdapService;
import com.kaoshidian.tool.ldap.LdapUser;

/**
 * @author <p>Turbidsoul Chen 于 2013年6月29日 上午11:55:34</p>
 *
 */
@Service
//@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class SyncFromLdapTask {
	
	@Autowired
	private LdapService ldap;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private OrgDao orgDao;	
	@Autowired
	private UserRoleDao userRoleDao;
	
	public LdapService getLdap() {
		return ldap;
	}
	
	public void syncLdapToOa() throws Exception {
		System.out.println("开始同步组织");
		this.syncOrg();
		System.out.println("组织同步结束");
		System.out.println("开始同步用户");
		this.syncUser();
		System.out.println("组织同步用户");
	}
	
	public void syncOrg() throws Exception {
		System.out.println("开始同步组织");
		List<LdapOrg> olist = ldap.searchAllOrg();
		
		Collections.sort(olist, new Comparator<LdapOrg>() {
			@Override
            public int compare(LdapOrg o1, LdapOrg o2) {
				
				List<String> dn1s = Arrays.asList(o1.getDn().split(","));
				List<String> dn2s = Arrays.asList(o2.getDn().split(","));
				
				Collections.reverse(dn1s);
				String dn1 = StringUtils.join(dn1s, ',');
				
				Collections.reverse(dn2s);
				String dn2 = StringUtils.join(dn2s, ',');
				
	            return dn1.compareToIgnoreCase(dn2);
            }
		});
		
		for (LdapOrg lo : olist) {
	        Org org = orgDao.findUniq("dn", lo.getDn());
	        if(org == null) {
	        	org = new Org();
	        } else {
	        	// 这个组织没有ldap上查到，则删除
	        	List<LdapOrg> curlgList = ldap.searchOrgByDn(org.getDn(), SearchScope.OBJECT);
	        	if(curlgList.size() == 0) {
	        		orgDao.delete(org);
	        		continue;
	        	}
	        }
	        org.setCreateDate(lo.getCreateTime());
	        org.setDn(lo.getDn());
	        org.setOrgName(lo.getName());
	        Org parentOrg = orgDao.findUniq("dn", lo.getParentDn());
	        if(parentOrg == null) {
	        	org.setParentId(0);
	        } else {
	        	org.setParentId(parentOrg.getOrgId());
	        }
	        
	        orgDao.saveOrUpdate(org);
        }
		
	}
	
	public void syncUser() throws Exception {
		List<LdapUser> ulist = ldap.searchAllUser();
		List<Integer> exists = new ArrayList<Integer>();
		for (LdapUser lu : ulist) {
	        User user = userDao.findUniq("loginName", lu.getAccount());
	        
	        if(user == null) {
	        	user = new User();
	        }
	        
	        Org curOrg = orgDao.findUniq("dn", lu.getParentDn());
	        
	        if(curOrg == null) {
	        	// 如果此人找到部门这把起放在根组织下
	        	curOrg = orgDao.findUniq("parentId", 0);
	        }
	        user.setCreateDate(lu.getCreateTime());
	        user.setDn(lu.getDn());
	        user.setLoginName(lu.getAccount());
	        user.setOrgId(curOrg.getOrgId());
	        user.setRealName(lu.getName());
	        user.setUserType(UserTypeEnum.WORKER);
	        user.setJobno(lu.getJobNum());
	        if(lu.getControl() == LdapAccountControl.ACCOUNT_NORMAL_DISABLED) {
	        	user.setState(UserStateEnum.LOCK);
	        } else {
	        	user.setState(UserStateEnum.ACTIVITY);
	        }
	         EntityView ev=new EntityView();
	         ev.add(Restrictions.eq("userId", user.getUserId()));
	         ev.add(Restrictions.eq("roleId", 13));
	        userDao.saveOrUpdate(user);
	        if(userRoleDao.count(ev)==0)
	        {
	        	UserRole userRole=new UserRole();	        	 
	        	userRole.setUserId(user.getUserId());
	        	userRole.setRoleId(13);
	        	userRoleDao.addNew(userRole);	                	 
	        }
	        exists.add(user.getUserId());
        }
		
		StringBuffer sb = new StringBuffer();
		for (Integer userid : exists) {
	        sb.append(userid).append(",");
        }
		
		// 同步结束之后把没有在ldap上出现的user锁定
		userDao.update(String.format("update oa_perm_user set state='%s' where userId not in (%s)", new Object[] {UserStateEnum.LOCK.name(), sb.substring(0, sb.length() - 1)}));
	}
}
