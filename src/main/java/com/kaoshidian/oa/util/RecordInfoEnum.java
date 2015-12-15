/**
 * 
 */
package com.kaoshidian.oa.util;

/**
 * @author <p>Innate Solitary 于 2012-9-26 下午6:32:44</p>
 *
 */
public enum RecordInfoEnum {
	COURSE("课程"), OPENCLASS("公开课"), CHAIR("讲座");
	
	private String label;

	private RecordInfoEnum(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
}
