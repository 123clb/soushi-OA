/**
 * 
 */
package com.kaoshidian.oa.permission.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaoshidian.oa.log.mng.LogMgr;
import com.kaoshidian.oa.permission.dao.RoleDAO;
import com.kaoshidian.oa.permission.entity.Menu;
import com.kaoshidian.oa.permission.entity.Role;
import com.kaoshidian.oa.permission.mgr.PermissionMgr;
import com.kaoshidian.oa.util.ActionExtend;
import com.kaoshidian.oa.util.JSONUtils;
import com.kaoshidian.oa.util.LogEntityEnum;
import com.kaoshidian.oa.util.LogOperationEnum;
import com.kaoshidian.oa.util.LogStatusEnum;
import com.kaoshidian.oa.util.Util;

/**
 * @author <p>Innate Solitary 于 2012-5-30 下午3:01:50</p>
 *
 */
@Controller
public class RoleAction extends ActionExtend {
	
	@Autowired
	private RoleDAO roleDao;
	
	@Autowired
	private PermissionMgr permissionMgr;
	
	@Autowired
	private LogMgr logMgr;
	
	@ModelAttribute("role")
	public Role createRole() {
		return new Role();
	}
	
	@RequestMapping("/perm/role/list.do")
	public String roleList(ModelMap model) {
		List<Role> roleList = roleDao.findAll();
		model.addAttribute("roleList", roleList);
		return "permission/role/rolelist";
	}
	
	
	@RequestMapping("/perm/role/before/add.do")
	public String beforeAdd(ModelMap model) {
		return "permission/role/roleedit";
	}
	
	@RequestMapping("/perm/role/before/modify.do")
	public String beforeModify(ModelMap model, Integer roleId) {
		Role role = roleDao.findById(roleId);
		model.addAttribute("role", role);
		return "permission/role/roleedit";
	}
	
	
	@RequestMapping("/perm/role/save.do")
	@ResponseBody
	public JSONObject save(HttpServletRequest request, ModelMap model, Role role) {
		LogOperationEnum op = null;
		if(role.getRoleId() == null) {
			op = LogOperationEnum.SAVE;
		} else {
			op = LogOperationEnum.MODIFY;
		}
		
		Role oldRole = roleDao.findUniq("roleName", role.getRoleName());
		if(role.getRoleId() != null && oldRole != null && !oldRole.getRoleId().equals(role.getRoleId())) {
			return JSONUtils.getJsonResult(null, null, "300", "角色名称重复", null, null);
		}
		
		roleDao.saveOrUpdate(role);
		logMgr.save(getCurrentUser(), Util.getIpAddr(request), role.getRoleId(), LogEntityEnum.ROLE, op, LogStatusEnum.SUCCESS, op.getLabel() + "角色:" + role.getRoleId());
		return JSONUtils.SAVE_SUCCESS;
	}
	
	@RequestMapping("/perm/role/delete.do")
	@ResponseBody
	public JSONObject delete(HttpServletRequest request, ModelMap model,Integer roleId) {
		roleDao.delete(new Integer[] {roleId});
		logMgr.save(getCurrentUser(), Util.getIpAddr(request), roleId, LogEntityEnum.ROLE, LogOperationEnum.DELETE, LogStatusEnum.SUCCESS, LogOperationEnum.DELETE.getLabel() + "角色:" + roleId);
		return JSONUtils.DELETE_SUCCESS;
	}
	
	@RequestMapping("/perm/role/before/grant.do")
	public String beforeGrant(ModelMap model, Integer roleId) {
		Role role = roleDao.findById(roleId);
		List<Integer> menuIdList = permissionMgr.getMenuIdsByRoleId(roleId);
		List<Menu> menuList = permissionMgr.getMenuTree(null, null);
		model.addAttribute("role", role);
		model.addAttribute("menuIdList", menuIdList);
		model.addAttribute("menuList", menuList);
		return "/permission/role/grant";
	}
	
	@RequestMapping("/perm/role/grant.do")
	@ResponseBody
	public JSONObject grant(HttpServletRequest request, Integer roleId, Integer[] menuIds) {
		permissionMgr.roleGrant(roleId, menuIds);
		logMgr.save(getCurrentUser(), Util.getIpAddr(request), roleId, LogEntityEnum.ROLE, LogOperationEnum.MODIFY, LogStatusEnum.SUCCESS, LogOperationEnum.MODIFY.getLabel() + "角色 [" + roleId + "] 权限");
		return JSONUtils.getJsonResult(null, null, "200", "授权成功", "closeCurrent", null);
	}
}
