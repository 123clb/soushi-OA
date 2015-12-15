package com.kaoshidian.oa.assets.entity;

import com.kaoshidian.oa.base.entity.BaseEntity;

public class DeviceCategory extends BaseEntity {
	private Integer  categoryId;
	private String categoryName;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

}
