var jsroot = mrmsroot + "/resources/js";
var jsonStr = null;
var chapterId = null;
var player = null;
var is_free = null;
var is_login = null;
/**
 * 播放接口，传递给播放器一个json对象，调用播放器的play方法 传入要播放的视频的json格式对象
 */
function playVideo(obj) {
	//声明FLASH模块
    var XAPlayer=document.getElementById("XAPlayer");
	XAPlayer.playVideo(obj);
	jsonStr = null;
}

function CoursePlayer(video_files, title, type, color, bgimage, loading, ads, buttonvisible, isFree) {
	return new Player(video_files, title, type, color, bgimage, loading, ads, buttonvisible, isFree);
}

function FreeVideoPlayer(video_files, title) {
	return new Player(video_files, title, null, null, null, null, null, false, true);
}

/**
 * 创建播放器并初始化 
 * video_files		json对象，p2pfile和vodfile属性
 * title 			视频标题 
 * type 			清晰度，默认为standard 
 * color 			播放器皮肤颜色 
 * bgimage 			播放器背景图片 
 * loading 			播放器加载图片或者swf 
 * ads 				播放器广告策略xml 
 * buttonvisible 	播放器控制栏业务按钮显示状况
 */
function Player(video_files, title, type, color, bgimage, loading,
		ads, buttonvisible, isFree) {
	this.swfVersionStr = "10.2.0";
	this.xiSwfUrlStr = jsroot + "/player/yl/playerProductInstall.swf";
	//判断是否安装P2P插件
	var isP2p = avc_ppshell_installed();
	var videoFiles = eval("(" + video_files + ")");
	var cid = videoFiles.chapterId;
	if(cid == null || cid == "" || cid == "undefined"){
		cid = "freevideo";
	}
	this.flashvars = {
		title : encodeURIComponent(title),
		p2pfile : encodeURIComponent(isP2p ? videoFiles.p2pfile : null),
		vodfile : encodeURIComponent(!isP2p ? videoFiles.vodfile : null),
		type : encodeURIComponent(type == null ? "standard" : type),
		color : encodeURIComponent(color == null ? "gray" : color),
//		bgimage : encodeURIComponent(bgimage == null ? jsroot + "/player/bg.jpg"
//				: bgimage),
//		loading : encodeURIComponent(loading == null ? jsroot + "/player/logo.png"
//				: loading),
		canCancelAds : true,
//		ads : encodeURIComponent(ads == null ? staticroot + "/play/ad/script/1.xml"
//				: ads),
		buttonvisible : !buttonvisible ? encodeURIComponent("note=hidden&important=hidden&question=hidden") : null,
		sidler : encodeURIComponent("type=visible;speed=visible;share=visible;history=visible"),
		qualify : encodeURIComponent(mrmsroot + "/file/preview/play/" + isP2p + "/" + cid + "/")
	};
	this.params = {
		quality : "standard",
		bgcolor : "#ffffff",
		allowscriptaccess : "all",
		allowfullscreen : "true",
		wmode : "Opaque"

	};
	this.attributes = {
		id : "XAPlayer",
		name : "XAPlayer",
		align : "middle"
	};
	return this;
}
/**
 * 播放器对象的创建播放器方法
 * 
 * @param obj_div
 *            将要加载播放器的html容器的id属性
 * @param width
 *            播放器宽度
 * @param height
 *            播放器高度
 */
Player.prototype.create_player = function(obj_div, width, height, css, jsonStrPar, cid) {
	swfobject.embedSWF(jsroot  + "/player/yl/XAPlayer.swf",
			obj_div, width, height, this.swfVersionStr, this.xiSwfUrlStr,
			this.flashvars, this.params, this.attributes, createSwfComplete);
	jsonStr = jsonStrPar;
	chapterId = cid;
	if(css == null){
		//css = "margin: 0 22px;";
	}
	swfobject.createCSS("#" + this.attributes.id, css);

};

function createSwfComplete(obj){
//	if(obj.success){
//		//调用play方法
//		if(jsonStr != null && jsonStr != 'undefined' && jsonStr != ''){
//			var json = eval("(" + jsonStr + ")");
//			play(json);	
//		}
//	}
}
//切换频道
function avc_play_p2p (command){
	var obj = null;
    if(navigator.appName=="Microsoft Internet Explorer"){
		obj = document.getElementById("flvplayer");	
	} else if(navigator.appName=="Netscape"){
		obj = document.getElementById("flv_pps");	
	}
	obj.SimpleSelectByURL(command);
	
}


/**
 * 检测P2P插件是否安装
 * @returns
 */
function avc_ppshell_installed(){
    var if_installed = false;
	if(navigator.appName=="Microsoft Internet Explorer"){
		if (window.ActiveXObject) {
			try {
				//x = CreateObject("divx_pps_ctrl");
				var xmlhttp = new ActiveXObject("forcetv.ForceTvCtrl");
				//excelApp.Visible = true;
				if_installed = true;
//				alert("已安装插件");
			} catch(e){
				// alert (e.message);
//				alert("请安装插件！");
//				avc_ppshell_install();
			}
		} else {
			//alert ("Your browser does not support this example.");
		}
	} else if(navigator.appName=="Netscape"){
	
	    x = navigator.mimeTypes['application/mozilla-ppshell-plugin'];
			if(x){
				if_installed = true;
//				alert("ff插件已安装");
			}else{
//				alert("ff插件没安装");
//				avc_ppshell_install();
			}
			return if_installed;
	}
	
	return if_installed;
}		

/**
 * 安装P2P插件方法
 */
function avc_ppshell_install(){
    if(navigator.appName=="Microsoft Internet Explorer"){
		window.open(staticroot + "/play/resources/ie-p2p-plugin.exe");
	} else if(navigator.appName=="Netscape"){
		window.open(staticroot + "/play/resources/ff-p2p-plugin.exe");		
	}
}	