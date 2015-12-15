package com.kaoshidian.oa.permission.entity;

/**
 * @author <p>Innate Solitary 于 2012-6-5 下午3:11:24</p>
 *
 */
public enum UserTypeEnum {
	WORKER("工作人员账户"), API_VERIFY("对外API账户");
	
	private String label;

	private UserTypeEnum(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
