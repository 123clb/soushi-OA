/**
 * 
 */
package com.kaoshidian.oa.util;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

/**
 * 生成一些自定义的JSON和一些JSON常量，用户给DWZ结果返回
 * @author <p>Innate Solitary 于 2012-5-19 下午12:18:24</p>
 *
 */
public final class JSONUtils {
	
	public static final JSONObject SAVE_SUCCESS = new JSONObject();
	public static final JSONObject DELETE_SUCCESS = new JSONObject();
	
	static {
		SAVE_SUCCESS.element("statusCode", "200");
		SAVE_SUCCESS.element("message", "保存成功");
		SAVE_SUCCESS.element("navTabId", "main");
		SAVE_SUCCESS.element("callbackType", "closeCurrent");
		SAVE_SUCCESS.element("forwardUrl", "");
		
		DELETE_SUCCESS.element("statusCode", "200");
		DELETE_SUCCESS.element("message", "删除成功");
		DELETE_SUCCESS.element("navTabId", "main");
	}
	
	/**
	 * 添加，修改，删除后需要给页面返回一个json，异步树通过此json，进行操作
	 * @return
	 */
	public static JSONObject getJsonResult(String treeId, String treeNodeId, String statusCode, String message, String callbackType, String navTabId) {
		JSONObject json = new JSONObject();
		json.element("treeId", treeId);
		json.element("treeNodeId", treeNodeId);
		json.element("statusCode", StringUtils.isEmpty(statusCode) ? "200" : statusCode);
		json.element("message", message);
		if(StringUtils.isNotEmpty(callbackType)) {
			json.element("callbackType", callbackType);
		}
		if(StringUtils.isNotEmpty(navTabId)) {
			json.element("navTabId", navTabId);
		}
		return json;
	}
	
	
	
}
