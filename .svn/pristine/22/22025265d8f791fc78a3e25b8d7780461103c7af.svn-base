/**
 * 
 */
package com.kaoshidian.oa.permission;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.kaoshidian.oa.log.mng.LogMgr;
import com.kaoshidian.oa.permission.dao.RoleDAO;
import com.kaoshidian.oa.permission.dao.UserDAO;
import com.kaoshidian.oa.permission.entity.User;
import com.kaoshidian.oa.util.LogEntityEnum;
import com.kaoshidian.oa.util.LogOperationEnum;
import com.kaoshidian.oa.util.LogStatusEnum;

/**
 * @author <p>Turbidsoul Chen 于 2013年7月1日 下午5:08:34</p>
 * @format:off
 *
 */
public class ShiroLdapRealm extends JndiLdapRealm {
	private String baseDn;
	@Autowired
	private LogMgr logMgr;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private RoleDAO roleDao;
	
	
	@Override
	protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token, LdapContextFactory ldapContextFactory) throws NamingException {
		UsernamePasswordToken uptoken = (UsernamePasswordToken) token;
		Object username = token.getPrincipal();
		
		if(username == null) {
			logMgr.save(null, null, uptoken.getHost(), null, LogEntityEnum.USER, LogOperationEnum.LOGIN, 
					LogStatusEnum.FAILURE, "登录失败：未提交用户名" );
            throw new AccountException("Null usernames are not allowed by this realm.");
		}
		
		Object password = token.getCredentials();
		
		if(password == null) {
            throw new AccountException("Null password are not allowed by this realm.");
		}
		
		User curuser = userDao.findUniq("loginName", username);
		
		if(curuser == null) {
			logMgr.save(null, uptoken.getHost(), null, LogEntityEnum.USER, LogOperationEnum.LOGIN, 
					LogStatusEnum.FAILURE, "登录失败：未找到用户[" + username + "]信息");
			throw new AccountException("Can't not find user[" + username.toString() + "] in system." );
		}
		
		if(StringUtils.isBlank(curuser.getDn())) {
			logMgr.save(curuser, uptoken.getHost(), curuser.getUserId(), LogEntityEnum.USER, 
					LogOperationEnum.LOGIN, LogStatusEnum.FAILURE, "登录失败：此用户没有DN");
			throw new AccountException("This user[" + username.toString() + "] has not ldap server." );
		}
		
		LdapContext ctx = null;
        try {
	        ctx = ldapContextFactory.getLdapContext(curuser.getDn(), password);
        } catch(Exception e) {
        	logMgr.save(null, uptoken.getHost(), null, LogEntityEnum.USER, LogOperationEnum.LOGIN, 
        			LogStatusEnum.FAILURE, "登录失败：Ldap服务器验证失败！");
        	throw new AccountException("Ldap auth failure!");
        } finally {
        	LdapUtils.closeContext(ctx);
        }
		
	    return createAuthenticationInfo(token, username, password, ctx);
	}
	
	
    @Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		try {
	        return this.queryForAuthenticationInfo(token, getContextFactory());
        } catch (NamingException e) {
	        throw new AccountException(e);
        }
	}
	
	
	@SuppressWarnings("rawtypes")
    @Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
		
		User user = userDao.findUniq("loginName", getAvailablePrincipal(principals));
		
		String rolesql = "select r.* from oa_perm_role r, oa_perm_userrole ur where ur.roleId=r.roleId and ur.userId=?";
		
		List<Map> rolemlist = roleDao.findBySql(rolesql, new Object[] {user.getUserId()});
		Set<String> roleNames = new LinkedHashSet<String>();
		for (Map rm : rolemlist) {
	        roleNames.add(rm.get("roleName").toString());
        }
		
        return new SimpleAuthorizationInfo(roleNames);
	}
	
	 
	

	public void setBaseDn(String baseDn) {
	    this.baseDn = baseDn;
    }
}
