package com.kaoshidian.oa.permission.entity;

import com.kaoshidian.oa.base.entity.BaseEntity;

/**
 * RoleMenu entity. @author MyEclipse Persistence Tools
 */

public class RoleMenu extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer roleMenuId;
	private Integer roleId;
	private Integer menuId;
	private String description;

	// Constructors

	/** default constructor */
	public RoleMenu() {
	}

	/** minimal constructor */
	public RoleMenu(Integer roleMenuId, Integer roleId, Integer menuId) {
		this.roleMenuId = roleMenuId;
		this.roleId = roleId;
		this.menuId = menuId;
	}

	/** full constructor */
	public RoleMenu(Integer roleMenuId, Integer roleId, Integer menuId,
			String description) {
		this.roleMenuId = roleMenuId;
		this.roleId = roleId;
		this.menuId = menuId;
		this.description = description;
	}

	// Property accessors

	public Integer getRoleMenuId() {
		return this.roleMenuId;
	}

	public void setRoleMenuId(Integer roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}