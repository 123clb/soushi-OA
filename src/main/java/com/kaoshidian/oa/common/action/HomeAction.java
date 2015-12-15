/**
 * 
 */
package com.kaoshidian.oa.common.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaoshidian.oa.log.mng.LogMgr;
import com.kaoshidian.oa.permission.dao.UserDAO;
import com.kaoshidian.oa.permission.entity.Menu;
import com.kaoshidian.oa.permission.entity.User;
import com.kaoshidian.oa.permission.mgr.PermissionMgr;
import com.kaoshidian.oa.util.LogEntityEnum;
import com.kaoshidian.oa.util.LogOperationEnum;
import com.kaoshidian.oa.util.LogStatusEnum;
import com.kaoshidian.oa.util.Util;


/**
 * @author <p>Innate Solitary 于 2012-5-16 下午3:58:39</p>
 *
 */
@Controller
public class HomeAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(HomeAction.class);
	
	@Autowired
	private PermissionMgr premissionMgr;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private LogMgr logMgr;
	
	@RequestMapping("/")
	public String home(ModelMap model) throws IOException {
		Subject currentUser = SecurityUtils.getSubject();
		String loginName = currentUser.getPrincipal().toString();
		User user = this.userDao.findUniq("loginName", loginName);
		model.addAttribute("user", user);
		List<Menu> menuList = premissionMgr.findMenuByParentId(null, user);
		model.addAttribute("menulist",menuList);
		model.addAttribute("isProductManager", currentUser.hasRole("ProductManager"));
		return "home";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request, ModelMap model) {
		Subject currentUser = SecurityUtils.getSubject();
		try{
			if(currentUser.isAuthenticated()){
				String loginName = currentUser.getPrincipal().toString();
				User user = this.userDao.findUniq("loginName", loginName);
				if(user == null) {
					currentUser.logout();
					return "redirect:/welcome";
				}
				logMgr.save(user, Util.getIpAddr(request), user.getUserId(), LogEntityEnum.USER, LogOperationEnum.LOGIN, LogStatusEnum.SUCCESS, "登录成功");
				return "redirect:/";
			}
		}catch(UnavailableSecurityManagerException  e){
			logger.debug(e.getMessage());
			currentUser.logout();
			return "permission/login";
		}
		currentUser.logout();
		return "permission/login";
	}
	
	@RequestMapping("/welcome")
	public String welcome() {
		return "permission/login";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		String loginName = currentUser.getPrincipal().toString();
		User user = this.userDao.findUniq("loginName", loginName);
		logMgr.save(user, Util.getIpAddr(request), user.getUserId(), LogEntityEnum.USER, LogOperationEnum.LOGOUT, LogStatusEnum.SUCCESS, "登出成功");
		currentUser.logout();
		return "redirect:/welcome";
	}
}
