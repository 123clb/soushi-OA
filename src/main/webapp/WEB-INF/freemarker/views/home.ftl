<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>搜视办公管理系统</title>
<link href="<@s.url '/resources/js/dwz-ria/themes/default/style.css'/>" rel="stylesheet" type="text/css" />
<link href="<@s.url '/resources/js/dwz-ria/themes/css/core.css'/>" rel="stylesheet" type="text/css" />
<link href="<@s.url '/resources/js/dwz-ria/uploadify/css/uploadify.css'/>" rel="stylesheet" type="text/css" />
<link href="<@s.url '/resources/js/treeview/jquery.treeview.css'/>" type="text/css" rel="stylesheet" />
<!--[if IE]>
<link href="<@s.url '/resources/js/dwz-ria/themes/css/ieHack.css'/>" rel="stylesheet" type="text/css" />
<![endif]-->
<style type="text/css">
	#header{height:85px}
	#leftside, #container, #splitBar, #splitBarProxy{top:90px}
</style>

<script src="<@s.url '/resources/js/static_value.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/speedup.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/jquery-1.4.4.js'/>" type="text/javascript"></script>

<script src="<@s.url '/resources/js/dwz-ria/js/jquery.cookie.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/jquery.validate.js'/>" type="text/javascript"></script>

<script src="<@s.url '/resources/js/dwz-ria/js/jquery.validate.custom.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/jquery.bgiframe.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/xheditor/xheditor-1.1.9-zh-cn.min.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/uploadify/scripts/swfobject.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/uploadify/scripts/jquery.uploadify.v2.1.0.js'/>" type="text/javascript"></script>

<script src="<@s.url '/resources/js/dwz-ria/js/dwz.core.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.util.date.js'/>" type="text/javascript"></script>

<script src="<@s.url '/resources/js/dwz-ria/js/dwz.validate.method.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.regional.zh.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.barDrag.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.drag.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.tree.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.accordion.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.ui.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.theme.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.switchEnv.js'/>" type="text/javascript"></script>

<script src="<@s.url '/resources/js/dwz-ria/js/dwz.alertMsg.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.contextmenu.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.navTab.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.tab.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.resize.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.dialog.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.dialogDrag.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.cssTable.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.stable.js'/>" type="text/javascript"></script>

<script src="<@s.url '/resources/js/dwz-ria/js/dwz.taskBar.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.ajax.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.pagination.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.database.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.datepicker.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.effects.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.panel.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.checkbox.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.history.js'/>" type="text/javascript"></script>

<script src="<@s.url '/resources/js/dwz-ria/js/dwz.combox.js'/>" type="text/javascript"></script>

<script type="text/javascript" src="<@s.url '/resources/js/player/jwplayer.js'/>"></script>
<script type="text/javascript" src="<@s.url '/resources/js/player/yl/flashcontent.js'/>"></script>
<script type="text/javascript" src="<@s.url '/resources/js/player/yl/player.js'/>"></script>
<script type="text/javascript" src="<@s.url '/resources/js/player/yl/swfobject.js'/>"></script>

<!--
<script src="<@s.url '/resources/js/dwz-ria/bin/dwz.min.js'/>" type="text/javascript"></script>
-->
<script src="<@s.url '/resources/js/dwz-ria/js/dwz.regional.zh.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/treeview/jquery.treeview.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/treeview/jquery.treeview.async.js'/>" type="text/javascript"></script>
<script src="<@s.url '/resources/js/jquery.url.js'/>" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	DWZ.init("<@s.url '/resources/js/dwz-ria/dwz.frag.xml'/>", {
		loginUrl:"<@s.url '/resources/js/dwz-ria/login_dialog.html'/>", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"<@s.url '/resources/js/dwz-ria/themes'/>"}); // themeBase 相对于index页面的主题base路径
			$("#0").click();
		}
	});
});
</script>
</head>
<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo">标志</a>
				<ul class="nav">
				    <#if isProductManager?? && isProductManager>
    				    <li><a href="perm/user/beforetransfer.do?fromUserId=${user.userId!}" target="dialog" rel="permtransferdialog" fresh="true" mask="true" title="责任人转移" width="392" height="546"><span>责任人转移</span></a></li>
				    </#if>
				    <li><a href="perm/user/pwd/before/modify.do" target="dialog" fresh="true" mask="true" title="修改密码">${user.loginName!}</a></li>
					<li><a href="javascript:void(0)" onclick="location.href='logout.do';return false;">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<!--<li theme="red"><div>红色</div></li>-->
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>

					<li theme="azure"><div>天蓝</div></li>
				</ul>
			</div>
			<!-- navMenu -->
			<div id="navMenu">
				<ul>
				  <#--
				  <#list menulist as m>
				  	<#if currMenuId?? && currMenuId?number=m.menuId>
				  		<li class="selected"><a href="sys/menu/findSubmenus.do?menuId=${m.menuId}"><span>${m.menuName}</span></a></li>
				  	<#else>
				  		<li><a href="sys/menu/findSubmenus.do?menuId=${m.menuId}"><span>${m.menuName}</span></a></li>
				  	</#if>
				  </#list>
				  -->
				  <#if menulist??>
				    <#list menulist as menu>
        				  <li><a id="${menu_index}" href="menu/submenu.do?menuId=${menu.menuId}"><span>${menu.menuName!}</span></a></li>
				    </#list>
				  </#if>
				</ul>
			</div>				
		</div>
		<div id="leftside">
			<div id="sidebar_s">

				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>

				<div class="accordion" fillSpace="sidebar">

					<div class="accordionHeader">
						<h2><span>Folder</span>界面组件</h2>						
					</div>

				</div>

			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">

					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main selected" url=""><a href="javascript:;"><span><span class="home_icon">kao</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->

					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox" layoutH="0">
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">Copyright &copy; 2012 <a href="javascript:void(0)">西安搜视网络科技有限公司</a></div>
</body>
</html>