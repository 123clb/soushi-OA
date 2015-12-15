/*!
 * jQuery 表单自定义验证函数库 v1.0.0
 * http://www.kaoshidian.com
 *
 * Copyright (c) 2011, SouShi All Right Reserved.
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * Date: 2011-11-11
 * Rev: 1.0.0
 * Author: zhangyf
 * LastModified：Kouis 2011-11-14 15:32:49
 */

/*
 *  Quick Look up the pattern in the List
 * --------------------------------------------------
 *    hanzi       : 中文汉字（含zh-CN,zh-TW,名字中的圆点）
 *    alnum       : 英文字母、数字和下划线
 *    alpha       : 仅限英文字母
 *    upper       : 大写英文字母
 *    lower       : 小写英文字母
 *    money       : 货币金额
 *    mobile      : 手机号码
 *    tel         : 固定电话（兼容国家区号、地方区号、分机号）
 *    phone       : 手机 或 固话
 *    fax         : 传真号码
 *    zipcode     : 邮政编码
 *    idcard      : 身份证号（15/18）
 *    bankcard    : 银行卡号
 *    qqno        : QQ号
 *    ipv4        : ip地址
 *    ipv6        : ipv6网络地址
 *    time        : 时间（00:00~23:59）
 *
 *
 *   The Default Rule from jQuery validation Plug-in
 * ----------------------------------------------------
 *     email      : 电子邮箱
 *     url        : 网址
 *     date       : 日期（英美）
 *     dateISO    : 日期（国际标准化组织的日期表示法 eg.2011-03-8,2011/7/06）
 *     number     : 所有的正数(包括整数,0,小数)
 *     digits     : 非负整数，自然数
 *     creditcard : 信用卡
 *     equalTo    : 与指定元素是否相等
 *     maxlength  : 最大长度
 *     minlength  : 最小长度
 *     rangelength: 指定长度范围[min,max]
 *     max        : 最大数值
 *     min        : 最小数值
 *     range      : 指定数值范围
 *     accept     : 可接受的后缀名(支持正则表达式) 
 *                  规则accept:true时等同于accept:png|jpe?g|gif，即图片文件。如非图片，可以用accept:'docx?|txt|pdf'
 *
 *   Usage Example
 * ----------------------------------------------------
 *  1.使用class名称  
 *     eg. <input class="email" />, <inpuut class="required email" />
 *     
 *  2.使用属性       
 *     eg. <input name="card" maxlength="5" />, 
 *         <input name="pwd2" required="true" equalTo="#pwd1" maxlength="16" />
 *     
 *  3.使用class名称和属性
 *     eg. <input name="username" class="required" maxlength="20" />
 *
 *  4.使用json书写格式（ 注：必须引入“jquery.metadata.js”文件）
 *    eg.  <input name="addr" class="{validate:{required:true,minlength:3}}" />
 *         <input name="addr" class="greenbg {validate:{required:true}}" />  --大括号外可以写其他样式
 *
 *  5.自定义提示信息（规则不匹配时，已有的提示信息不合适）
 *    eg. <input name="rmb" class="money {messages:{money:'输入的金额不合法'}}" />
 *        <input name="email" class="{validate:{required:true, email:true, messages:{required:'没有填写邮箱地址', email:'邮箱地址错误'}}}"/>
 *   也可写在js脚本中
 *   eg. $("#inputForm").validate({
 *		 	messages: {
 *				email: {
 *					required: 'Enter this!'
 *				}
 *			}		
 *	  	 });  
 *
 *  6.将验证规则和提示消息写在js脚本内（表单和校验规则完全分离，无侵入性）
 *   eg.$("#signupForm").validate({
 *			rules: {
 *				username: "required",
 *				password2: {required: true,minlength: 5, equalTo: "#password"},
 *			},
 *			messages: {
 *				username: "Please enter your username",
 *				password: {
 *					required: "Please provide a password",
 *					minlength: "Your password must be at least 5 characters long"
 *				}
 *			}
 *		});
 *
 */
(function() {

	String.prototype.trim = function() {
		var m = this.match(/^\s*(\S+(\s+\S+)*)\s*/);   
		return (m == null) ? "" : m[1];
	}
	
	function stripHtml(value) {
		// remove html tags and space chars
		return value.replace(/<.[^<>]*?>/g, ' ').replace(/&nbsp;|&#160;/gi, ' ')
		// remove numbers and punctuation
		.replace(/[0-9.(),;:!?%#$'"_+=\/-]*/g,'');
	}
	
})();

jQuery.validator.addMethod("email2", function(value, element) {
	return this.optional(element) || /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/i.test(value);
}, "电子邮箱格式不正确");

jQuery.validator.addMethod("hanzi", function(value, element) {
	return this.optional(element) || /^[\u4e00-\u9fa5\·]+$/i.test(value);
}, "请输入中文汉字");

jQuery.validator.addMethod("unblank", function(value, element) {
	return this.optional(element) || /^\S+$/i.test(value);
}, "不能包含空格");

jQuery.validator.addMethod("alnum", function(value, element) {
	return this.optional(element) || /^\w+$/i.test(value);
}, "仅限字母、数字和下划线");

jQuery.validator.addMethod("alpha", function(value, element) {
	return this.optional(element) || /^[a-zA-Z]+$/i.test(value);
}, "仅限英文字母");

jQuery.validator.addMethod("upper", function(value, element) {
	return this.optional(element) || /^[A-Z]+$/i.test(value);
}, "只能输入大写英文字母");

jQuery.validator.addMethod("lower", function(value, element) {
	return this.optional(element) || /^[a-z]+$/i.test(value);
}, "只能输入小写英文字母"); 

jQuery.validator.addMethod("money", function(value, element) {
	return this.optional(element) || /^(([1-9]\d*)|0)(\.\d{1,2})?$/i.test(value);
}, "请输入合法的金额");

var mobile = /^1[3|4|5|8]\d{9}$/i,
    tel = /^(([0\+]\d{2,3}-)?(0\d{2,3}-?))?(\d{7,8})(-(\d{2,5}))?$/i;

jQuery.validator.addMethod("mobile", function(value, element) {
	return this.optional(element) || mobile.test(value);
}, "请输入正确的手机号码");

jQuery.validator.addMethod("tel", function(value, element) {
	return this.optional(element) || tel.test(value);
}, "请输入正确的电话号码");

jQuery.validator.addMethod("phone", function(value, element) {
	return this.optional(element) || mobile.test(value) || tel.test(value);
}, "请输入正确的联系电话");

jQuery.validator.addMethod("fax", function(value, element) {
	return this.optional(element) || tel.test(value);
}, "请输入正确的传真号码");

jQuery.validator.addMethod("zipcode", function(value, element) {
	return this.optional(element) || /^\d{6}$/i.test(value);
}, "请输入正确的邮编");

jQuery.validator.addMethod("idcard", function(value, element) {
	return this.optional(element) || /(^\d{15}$)|(^\d{17}[0-9\x\X]{1}$)/i.test(value);
}, "请输入正确的身份证号");

jQuery.validator.addMethod("bankcard", function(value, element) {
	return this.optional(element) || /^\d{15,19}$/i.test(value);
}, "请输入正确的银行卡号");

jQuery.validator.addMethod("qqno", function(value, element) {
	return this.optional(element) || /^[1-9]\d{4,}$/i.test(value);
}, "请输入正确的QQ号");

jQuery.validator.addMethod("username", function(value, element) {
	return this.optional(element) || /^([a-zA-Z])\w{2,9}$/i.test(value);
}, "请输入正确的用户名");

jQuery.validator.addMethod("ipv4", function(value, element) {
  return this.optional(element) || /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/i.test(value);
}, "请输入合法的IP地址");

jQuery.validator.addMethod("ipv6", function(value, element, param) { 
    return this.optional(element) || /^((([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:))$/i.test(value);
}, "请输入正确ipv6地址.");

jQuery.validator.addMethod("time", function(value, element) {
		return this.optional(element) || /^([01][0-9])|(2[0123]):([0-5])([0-9])$/.test(value);
	}, "请输入合法的时间(00:00~23:59)"
);
jQuery.validator.addMethod("required_group1", function(value, element){
	//var $module = $(element).parent().parent().parent();
	return $(".required_group:blank").length>1;
}, "电话号码、手机号至少填一项");

jQuery.validator.addMethod("menupath", function(value, element){
  return this.optional(element) || (value.slice(0, 1) == "/" || value.slice(0, 1) == "\\");
}, "菜单地址不能以/或\\开始");

jQuery.validator.addMethod("binary", function(value, element){
  return this.optional(element) || /^[10]*$/.test(value);
}, "必须是二进制字符串(101011)");

jQuery.validator.addMethod("port", function(value, element){
  var flag = /^[1-90]*$/.test(value);
  value = parseInt(value);
  if(flag) {
    flag = value >= 1 && value <= 65535;
  }
  return this.optional(element) || flag;
}, "必须是1-65535之间的一个值");