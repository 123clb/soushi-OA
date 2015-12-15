/**
 * 
 */
package com.kaoshidian.oa.permission.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaoshidian.oa.log.mng.LogMgr;
import com.kaoshidian.oa.permission.dao.MenuDAO;
import com.kaoshidian.oa.permission.dao.UserDAO;
import com.kaoshidian.oa.permission.entity.Menu;
import com.kaoshidian.oa.permission.entity.User;
import com.kaoshidian.oa.permission.mgr.PermissionMgr;
import com.kaoshidian.oa.util.ActionExtend;
import com.kaoshidian.oa.util.JSONUtils;
import com.kaoshidian.oa.util.LogEntityEnum;
import com.kaoshidian.oa.util.LogOperationEnum;
import com.kaoshidian.oa.util.LogStatusEnum;
import com.kaoshidian.oa.util.Util;
/**
 * @author <p>Innate Solitary 于 2012-5-16 下午6:23:03</p>
 *
 */
@Controller
public class MenuAction extends ActionExtend {
	
	@Autowired
	private PermissionMgr permissionMgr;
	
	@Autowired
	private MenuDAO menuDao;
	
	@Autowired
	private LogMgr logMgr;
	
	@Autowired
	private UserDAO userDao;
	
	@ModelAttribute("menu")
	public Menu createMenu() {
		return new Menu();
	}
	
	@RequestMapping("menu/submenu.do")
	public String subMenu(ModelMap model, Integer menuId) {
		Subject currentUser = SecurityUtils.getSubject();
		User user = this.getCurrentUser();
		if(user == null) {
			return "redirect:/welcome";
		}
		Menu curMenu = permissionMgr.getMenuById(menuId);
		List<Menu> menuList = permissionMgr.getMenuTree(menuId, user);
		model.addAttribute("menuList", menuList);
		if(StringUtils.isNotEmpty(curMenu.getMenuPath()))
		{
			return curMenu.getMenuPath();
		}
		return "/sys/sysleft";
	}
	
	@RequestMapping("perm/menu.do")
	public String menuList(ModelMap model) {
		List<Menu> menuList = permissionMgr.findMenuByParentId(null);
		model.addAttribute("menuList", menuList);
		return "/permission/menu/menu";
	}
	
	@RequestMapping("perm/menu/list.do")
	public String menuList(ModelMap model, Integer menuId) {
		if(menuId != null && menuId.intValue() == 0) {
			menuId = null;
		}
		Menu parentMenu = menuDao.findById(menuId);
		List<Menu> menuList = permissionMgr.findMenuByParentId(menuId);
		model.addAttribute("menuList", menuList);
		model.addAttribute("parentMenu", parentMenu);
		return "/permission/menu/menulist";
	}
	
	@RequestMapping("perm/menu/before/add.do")
	public String beforeAdd(ModelMap model, Integer parentId) {
		Menu parentMenu = permissionMgr.getMenuById(parentId);
		model.addAttribute("parentMenu", parentMenu);
		return "/permission/menu/menuedit";
	}
	
	@RequestMapping("perm/menu/before/modify.do")
	public String beforeModify(ModelMap model, Integer menuId) {
		Menu menu = permissionMgr.getMenuById(menuId);
		Menu parentMenu = permissionMgr.getMenuById(menu.getParentMenuId());
		model.addAttribute("menu", menu);
		model.addAttribute("parentMenu", parentMenu);
		return "/permission/menu/menuedit";
	}
	
	
	@RequestMapping("perm/menu/save.do")
	@ResponseBody
	public JSONObject save(HttpServletRequest request, Menu menu) {
		LogOperationEnum op = null;
		if(menu.getMenuId() == null) {
			op = LogOperationEnum.SAVE;
		} else {
			op = LogOperationEnum.MODIFY;
		}
		menuDao.saveOrUpdate(menu);
		logMgr.save(getCurrentUser(), Util.getIpAddr(request), menu.getMenuId(), LogEntityEnum.MENU, op, LogStatusEnum.SUCCESS, op.getLabel() + "菜单:" + menu.getMenuId());
		return JSONUtils.getJsonResult("browser", menu.getParentMenuId() == null ? "0" : menu.getParentMenuId().toString(), "200", "保存成功", "closeCurrent", null);
	}
	
	@RequestMapping("perm/menu/delete.do")
	@ResponseBody
	public JSONObject delete(HttpServletRequest request, Integer menuId) {
		Menu menu = menuDao.findById(menuId);
		menuDao.delete(menu);
		logMgr.save(getCurrentUser(), Util.getIpAddr(request), menu.getMenuId(), LogEntityEnum.MENU, LogOperationEnum.DELETE, LogStatusEnum.SUCCESS, LogOperationEnum.DELETE.getLabel() + "菜单:" + menu.getMenuId());
		return JSONUtils.getJsonResult("browser", menu.getParentMenuId() == null ? "0" : menu.getParentMenuId().toString(), "200", "删除成功", null, null);
	}
	
	@RequestMapping("perm/menu/tree.do")
	@ResponseBody
	public JSONArray menuTree(ModelMap model, Integer nodeid) {
		JSONArray treejson = permissionMgr.createMenuTreeJSON(nodeid);
		return treejson;
	}
}
