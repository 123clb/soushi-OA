/**
 * 
 */
package com.kaoshidian.oa.util;

/**
 * @author <p>Innate Solitary 于 2012-9-26 下午7:07:11</p>
 *
 */
public enum HandoutsTypeEnum {
	PPT("PPT演示稿"), WORD("Word文档"), PRINTOUT("打印稿");
	
	private String label;

	private HandoutsTypeEnum(String label) {
		this.label = label;
	}
	
	public String getLabel() 
	{
		return this.label;
	}

}
