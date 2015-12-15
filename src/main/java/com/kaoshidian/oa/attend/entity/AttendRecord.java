package com.kaoshidian.oa.attend.entity;

import com.kaoshidian.oa.base.entity.BaseEntity;

public class AttendRecord extends BaseEntity {
	
	private Integer  rId;
	
	private String  rName;
	
	private Integer rUid;
	
	private String  rFlag;
	
	private  String  rTime;

	public Integer getrId() {
		return rId;
	}

	public void setrId(Integer rId) {
		this.rId = rId;
	}

	public String getrFlag() {
		return rFlag;
	}

	public void setrFlag(String rFlag) {
		this.rFlag = rFlag;
	}

	public String getrTime() {
		return rTime;
	}

	public void setrTime(String rTime) {
		this.rTime = rTime;
	}

	public String getrName() {
		return rName;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}
	
	public Integer getrUid() {
		return rUid;
	}

	public void setrUid(Integer rUid) {
		this.rUid = rUid;
	}
	
	

}
