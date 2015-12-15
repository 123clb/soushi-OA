
package com.kaoshidian.oa.util;

public enum LogStatusEnum {
	SUCCESS("成功"), FAILURE("失败");
	
	private String label;
	
	private LogStatusEnum(String label) {
		this.label = label;
	}
	
	
	public String getLabel() {
		return label;
	}
}
