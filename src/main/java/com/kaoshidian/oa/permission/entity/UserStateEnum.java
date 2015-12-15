/**
 * 
 */
package com.kaoshidian.oa.permission.entity;

/**
 * @author <p>Innate Solitary 于 2012-5-31 上午11:48:02</p>
 *
 */
public enum UserStateEnum {
	ACTIVITY("活动"), LOCK("锁定");
	
	private String label;
	
	private UserStateEnum(String label) {
		this.label = label;
	}
	
	public String getLabel() {
	    return label;
    }
}
