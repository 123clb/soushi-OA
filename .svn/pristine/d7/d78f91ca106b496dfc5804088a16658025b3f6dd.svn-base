/**
 * 
 */
package com.kaoshidian.oa.permission.action;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.dom4j.DocumentException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kaoshidian.oa.attend.dao.AttendRulesDao;
import com.kaoshidian.oa.base.EntityView;
import com.kaoshidian.oa.base.page.PageContext;
import com.kaoshidian.oa.base.util.BeansWrapperUtil;
import com.kaoshidian.oa.log.mng.LogMgr;
import com.kaoshidian.oa.permission.dao.RoleDAO;
import com.kaoshidian.oa.permission.dao.UserDAO;
import com.kaoshidian.oa.permission.entity.Role;
import com.kaoshidian.oa.permission.entity.User;
import com.kaoshidian.oa.permission.entity.UserStateEnum;
import com.kaoshidian.oa.permission.entity.UserTypeEnum;
import com.kaoshidian.oa.permission.mgr.PermissionMgr;
import com.kaoshidian.oa.util.ActionExtend;
import com.kaoshidian.oa.util.JSONUtils;
import com.kaoshidian.oa.util.LogEntityEnum;
import com.kaoshidian.oa.util.LogOperationEnum;
import com.kaoshidian.oa.util.LogStatusEnum;
import com.kaoshidian.oa.util.Util;
import com.kaoshidian.tool.rtx.IRTXServer;

/**
 * @author <p>Innate Solitary 于 2012-5-31 上午10:34:43</p>
 *
 */
@Controller
public class UserAction extends ActionExtend {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private RoleDAO roleDao;

	
	@Autowired
	private PermissionMgr permissionMgr;
	
	@Autowired
	private LogMgr logMgr;
	
	@Autowired
	private IRTXServer rtx;
	
	@ModelAttribute("user")
	public User getUser(Integer userId) {
		if (userId == null) {
	        return new User();
        }
		return userDao.findById(userId);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("perm/user/list.do")
	public String userList(ModelMap model,String loginName,String realName,Integer pageNum,Integer numPerPage) throws Exception {
		EntityView userev = new EntityView();
		userev.addOrder(Order.asc("state"));
		userev.addOrder(Order.asc("userId"));
		
		if(StringUtils.isNotEmpty(loginName)) {
			userev.add(Restrictions.ilike("loginName", loginName, MatchMode.ANYWHERE));
			model.addAttribute("loginName", loginName);
		}
		
		if(StringUtils.isNotEmpty(realName)) {
			userev.add(Restrictions.ilike("realName", realName, MatchMode.ANYWHERE));
			model.addAttribute("realName", realName);
		}
		
		PageContext<User> pc=userDao.queryUsePage(userev, pageNum, numPerPage);
		
		for (User user : pc.getItemList()) {
			user.setRoles(permissionMgr.getRolesByGrantUser(user.getUserId()));
        }
		
		model.addAttribute("pageCtx", pc);
		return "/permission/user/userlist";
	}
	
	@RequestMapping("perm/user/before/add.do")
	public String beforeAdd(ModelMap model) {
		List<Role> roleList = roleDao.findAll();
		model.addAttribute("roleList", roleList);
		model.addAttribute("userTypeEnum", BeansWrapperUtil.wrapEnum(UserTypeEnum.class));	
		model.addAttribute("ruleMap", permissionMgr.getRuleMap());
		return "/permission/user/useredit";
	}
	
	@RequestMapping("perm/user/before/modify.do")
	public String beforeModify(ModelMap model, Integer userId) {
		List<Role> roleList = roleDao.findAll();
		model.addAttribute("roleList", roleList);
		List<Integer> grantRoleIdList = permissionMgr.getRoleIdsByGrantUser(userId);
		model.addAttribute("grantRoleIdList", grantRoleIdList);
		User user = userDao.findById(userId);
		model.addAttribute("user", user);
		model.addAttribute("userTypeEnum", BeansWrapperUtil.wrapEnum(UserTypeEnum.class));
		model.addAttribute("ruleMap", permissionMgr.getRuleMap());
		return "/permission/user/useredit";
	}
	
	@RequestMapping("perm/user/save.do")
	@ResponseBody
	public JSONObject save(HttpServletRequest request, Integer userId, User user, Integer[] roleIds) {
		LogOperationEnum op = null;
		
		if(user.getUserId() == null) {
			op = LogOperationEnum.SAVE;
		} else {
			op = LogOperationEnum.MODIFY;
		}
		
		 String isSend=request.getParameter("isSend");
		 
		 if(StringUtils.isEmpty(isSend))
		 {
			 user.setIsSend(false);
		 }
		String save = "";
		if(user.getUserId() == null) {
			save = "创建";
		} else {
			save = "修改";
		}
		boolean result = permissionMgr.saveOrUpdateUser(user, roleIds);
		if(!result) {
			return JSONUtils.getJsonResult(null, null, "300", "登录名存在，请重新输入", null, null);
		}
		logMgr.save(this.getCurrentUser(), Util.getIpAddr(request), user.getUserId(), LogEntityEnum.USER, op, LogStatusEnum.SUCCESS, op.getLabel() + "用户：" + user.getUserId());
		
		return JSONUtils.SAVE_SUCCESS;
	}
	
	@RequestMapping("perm/user/delete.do")
	@ResponseBody
	public JSONObject delete(HttpServletRequest request, Integer userId) {
		User user = userDao.findById(userId);
		if("admin".equals(user.getLoginName())) {
			logMgr.save(this.getCurrentUser(), Util.getIpAddr(request), user.getUserId(), LogEntityEnum.USER, LogOperationEnum.DELETE, LogStatusEnum.SUCCESS, LogOperationEnum.DELETE.getLabel() + "用户 [" + user.getUserId() + "] 失败");
			return JSONUtils.getJsonResult(null, null, "200", "管理员不能删除", null, null);
		}
		permissionMgr.deleteUser(userId);
		logMgr.save(this.getCurrentUser(), Util.getIpAddr(request), user.getUserId(), LogEntityEnum.USER, LogOperationEnum.DELETE, LogStatusEnum.SUCCESS, LogOperationEnum.DELETE.getLabel() + "用户：" + user.getUserId());
		return JSONUtils.DELETE_SUCCESS;
	}
	
	@RequestMapping("perm/user/before/grant.do")
	public String beforeGrant(ModelMap model, Integer userId) {
		User user = userDao.findById(userId);
		model.addAttribute("user", user);
		return "/permission/user/grant_dataperm";
	}
	
	@RequestMapping("perm/user/rtx/userchoice.do")
	public String rtxUserOpen(ModelMap model) throws DocumentException {
		model.addAttribute("rtxUserList", rtx.getRtxUserInfos().values());
		return "/permission/user/rtx/rtxuser_choice";
	}
	
	
	@RequestMapping("perm/user/dialog/open.do")
	public String userListDialog(ModelMap model) throws Exception {
		model.addAttribute("userList", userDao.findAll());
		return "/permission/user/choiceuser";
	}
	
	@RequestMapping("perm/user/pwd/before/modify.do")
	public String beforeModifyPwd(ModelMap model, Integer userId) {
		model.addAttribute("userId", userId);
		return "/permission/user/modifypwd";
	}
	
	
	@RequestMapping("perm/user/enable.do")
	@ResponseBody
	public JSONObject enableUser(HttpServletRequest request, Integer userId, UserStateEnum state) {
		User user = userDao.findById(userId);
		
		if (state != null) {
	        user.setState(state);
        }
		userDao.update(user);
		logMgr.save(getCurrentUser(), Util.getIpAddr(request), userId, LogEntityEnum.USER, LogOperationEnum.MODIFY, LogStatusEnum.SUCCESS, 
				String.format("设置帐户[%s#%d]的状态为[%s]", user.getRealName(), user.getUserId(), state.getLabel()));
		
		
		return JSONUtils.getJsonResult(null, null, "200",  state.getLabel() + "帐户[" + user.getRealName() + "]", null, "main");
	}
	
	@RequestMapping("perm/user/beforetransfer.do")
	public String beforePermTransfer(ModelMap model, Integer fromUserId) {
		User fromUser = userDao.findById(fromUserId);
		model.addAttribute("fromUser", fromUser);
		model.addAttribute("isProductManager", SecurityUtils.getSubject().hasRole("ProductManager"));
		return "/permission/user/permtransfer";
	}

}
