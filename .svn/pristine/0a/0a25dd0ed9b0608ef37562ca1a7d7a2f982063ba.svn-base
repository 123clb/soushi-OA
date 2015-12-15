/**
 * 播放器接口代码 rejoicelee 2011-12-20
 */

//加载之后自动把播放面板顶端平齐
$(function(){
	$(document).scrollTop(300);
});

function test(){
	var str = '{"play" : {"title" : "2014考研英语强化班", "p2pfile" : "high=4f69b27c0007de29022100436e6939ee&standard=4f69beac00066ff302509b657adc4c3b", "vodfile" : "null", "ads" : "http://rejoicelee:8080/ksdfront/resources/js/play/demo/ads.xml"}, "type" : "standard", "time" : "480", "isNew" : "false"} ';
	playVideo(eval("(" + str + ")"));
}

var play_record_timer = null;
function saveInfo(){
	var pi = playinfo();
	$.post(wwwroot + "/coursecenter/courseplay/saveplayerrecord", {playInfo: pi, chapterId: chapterId}, function(result){
		
	});
}


function hiddenPanel(){
	$("#important").hide();
	$("#question").hide();
	$("#note").hide();
	$("#record").hide();
	$("#share").hide();
	$("#history").hide();
	$("#speed").hide();
}

/**
 * 播放记录中直接掉到时间锚点
 */
function toplay(time){
	$("#player_mask").hide();
	playpause(false);
	shieldControl(true);
	var str = '{"play" : {"title" : "' + decodeURIComponent(player.flashvars.title) + '", "p2pfile" : "' + decodeURIComponent(player.flashvars.p2pfile) + '", "vodfile" : "' + decodeURIComponent(player.flashvars.vodfile) + '", "ads" : "' + decodeURIComponent(player.flashvars.ads) + '"}, "type" : "' + decodeURIComponent(player.flashvars.type) + '", "time" : "' + time + '", "isNew" : "false"}';
	$("#test").text(str);
	playVideo(eval("(" + str + ")"));
}

/**
 * 侧栏加速插件下载
 */
function avc_speed(){
//	playpause(true);
	var mask = create_mask();
	$(mask).show();
	hiddenPanel();
	$("#speed").css("margin-top",
			($(mask).height() - $("#speed").height() - 36) / 2 + "px").css("margin-left",
					"145px").show();
	$("#speed").find("input:button.vidie_pop_btn").click(function(){
		avc_ppshell_install();
	});
	$("#speed").find("img, input:button.colose_a").click(function(){
		$(mask).hide();
//		playpause(false);
	});
}

/**
 * 侧栏共享
 * @param vedioaddress
 * @param type
 * @param videoname
 * @param time
 */
function avc_share(vedioaddress, type, videoname, time){
//	playpause(true);
	var mask = create_mask();
	$(mask).show();
	hiddenPanel();
	$("#share").css("margin-top",
			($(mask).height() - $("#share").height() - 36) / 2 + "px").css("margin-left",
					"145px").show();
	//增加分享链接
	$("#jiathis").append('<script type="text/javascript" src="http://v2.jiathis.com/code/jia.js" charset="utf-8"></script>');
	$("#share").find("span.title").text(videoname);
	$("#share").find("img, input:button.colose_a").click(function(){
		$(mask).hide();
//		playpause(false);
	});
}


/**
 * 侧栏播放历史记录，只显示每一门课的最后观看记录
 * @param vedioaddress
 * @param type
 * @param vedioname
 * @param time
 */
function avc_history (vedioaddress, type, vedioname, time){
//	playpause(true);
	var mask = create_mask();
	$(mask).show();
	hiddenPanel();
	$("#history").css("margin-top",
			($(mask).height() - $("#history").height() - 36) / 2 + "px").css("margin-left",
					"75px").show();
	$.post(wwwroot + "/coursecenter/courseplay/allhistory", null, function(result){
		var arrs = eval("(" + result + ")");
		$("#histories").html('');
		for(var i = 0;i < arrs.length;i ++){
			var obj = arrs[i];
			$("#histories").append('<li>' +
                    '<span class="history_pop_w_a"><a class=" c_white" href="' + wwwroot + '/coursecenter/coursedetail/' + obj.courseId + '">' + obj.courseName + '</a></span>' +
                    '<span class="history_pop_w_a c_white">' + obj.subCourseName + '</span>' +
                    '<span class="history_pop_w_b c_white">' + obj.seq + '</span>' +
                    '<span class="history_pop_w_c c_g">您上次学习到</span>' +
                    '<span class="history_pop_w_d c_white">' + formatTimeStr(obj.time) + '</span>' +
                    '<span class="history_pop_w_e"><a class="c_y" href="' + wwwroot + '/coursecenter/courseplayfromhistory/' + obj.courseId + '">继续学习</a></span>' +
                 	'</li>');
		}
	});
	//增加分享链接
	$("#history").find("img, input:button.colose_a").click(function(){
		$(mask).hide();
//		playpause(false);
	});
}

/**
 * 学习记录接口
 * @param id
 * @param title
 * @param sectionName
 * @param seq
 * @param time
 */
function avc_record(id, title, sectionName, seq, time){
	playpause(true);
	shieldControl(false);
	var mask = create_mask();
	$(mask).show();
	hiddenPanel();
	$("#record").css("margin-top",
			($(mask).height() - $("#record").height() - 36) / 2 + "px").show();
	$("#title").text(title);
	$("#sectionName").text(sectionName);
	$("#seq").text(seq);
	$("#time").text("您上次学习到" + formatTimeStr(time));
	$("#continueStudy").click(function(){toplay(time);});
	$("#closeRecord").click(function(){
		//该视频从头播放
		toplay(0);
	});
}

/**
 * 视频开始播放的时候接口
 * @param vedioaddress
 * @param type
 * @param vedioname
 * @param time
 */
function avc_play_start(vedioaddress, type, videoname, time){
	shieldControl(true);
	//调用play方法
	if(jsonStr != null && jsonStr != 'undefined' && jsonStr != ''){
		var json = eval("(" + jsonStr + ")");
		playVideo(json);	
	}
	//每2分钟保存数据到服务器中
	if(is_login == "true"){
		play_record_timer = setInterval(saveInfo, 120 * 1000);
	}
}

/**
 * 播放器中弹出窗信息
 */
function avc_alert(str){
	//jTip(str);
	//$("#popup_container").height($("#popup_content").height() + 15);
}

/**
 * playinfo格式：videoaddress=830c05426b554364&type=standard&name=2013考研政治强化班&time=278
 * @returns 返回上面格式字符串的数组
 */
function playinfo(){
	var XAPlayer=document.getElementById("XAPlayer");
	return XAPlayer.playinfo();
}
/**
 * 连播
 * 播放完成之后的js接口 1. 连播 2. 相关视频
 */
function avc_playend(vedioaddress, type, vedioname, time) {
	//播放完之后关闭记录播放信息到服务器
	if(play_record_timer != null){
		clearInterval(play_record_timer);
		play_record_time = null;
	}
	
	// 如果是连播
	if ($("#auto_play").attr("class").indexOf("checkbox-defaut") != -1) {
		// 获取当前播放的章节的播放序号
		var curObj = $(".curChapter");
		var currChapter = curObj.attr("id");
		var nextIndex = parseInt(currChapter.substr(currChapter.lastIndexOf("-")+1))+1;
		var nextChapter = "play-"+nextIndex;
		var flag = false; // 是否到最后
		var obj = $("#"+nextChapter);
		if (obj != null) {
			
			if(curObj != null){
				curObj.removeClass("curChapter");
				curObj.removeClass("subcourse-cur");
				curObj.parent().parent().removeClass("coursePlay-item-cur");
				curObj.parent().parent().removeClass("curSection");
				curObj.parent().hide();
			}
		
			$(obj).addClass("curChapter");
			$(obj).addClass("subcourse-cur");
			$(obj).parent().parent().addClass("coursePlay-item-cur");
			$(obj).parent().parent().addClass("curSection");
			$(obj).parent().show();
			var chapterId = $(obj).attr("name");
			playfree(chapterId);
			
		} else {
			flag = true;
		}
		if (!flag) {
			var subjectId = url.substr(url.lastIndexOf("/")+1);
			playfree(subjectId);
		}
	} else {
		// 否则是相关视频

	}
}

/**
 * 切换暂停和播放效果
 * @param flag
 */
function playpause(flag){
	var XAPlayer=document.getElementById("XAPlayer");
	XAPlayer.playpause(flag);
}

/**
 * 可用不可用控制栏
 * @param flag
 */
function shieldControl(flag){
	var XAPlayer=document.getElementById("XAPlayer");
	XAPlayer.shieldControl(flag);
}

/**
 * 记笔记接口，在js中定义与播放器中同原型的方法
 * 
 * @param vedioaddress
 *            表示p2p的频道ID或者vod的url
 * @param type
 *            表示视频的清晰度high|standard|audio
 * @param vedioname
 *            表示视频的名称
 * @param time
 *            表示当前暂停的时间锚点
 */
function avc_note(vedioaddress, type, vedioname, time) {
	// 先暂停视频，显示记笔记界面
	playpause(true);
	var mask = create_mask();
	$(mask).show();
	hiddenPanel();
	$("#note").css("margin-top",
			($(mask).height() - $("#note").height() - 36) / 2 + "px").show();
	$("#note").find("input:button.input_btn").eq(0).click(function() {
		var note_text = $("#note_text").val().replace(/\n/g, "<br/>");
		if(note_text.length == 0){
			$("#msg").show();
		} else {
			$("#saving_note").prev("p").hide();
			$("#saving_note").show();
			$.ajax({
				url : wwwroot + "/coursecenter/courseplay/save_note",
				type : "POST",
				data : {
					cid : $("#cid").val(),
					content : note_text
				},
				dataType : "json",
				success : function(result){
				},
				error : function(result){
				},
				complete : function(result){
					$("#saving_note").prev("p").show();
					$("#saving_note").hide();
					$("#note_text").val("");
					$("#note").hide();
					$(mask).hide();
					//操作完成之后继续播放
					playpause(false);
				}
			});
		}
		event.stopPropagation();
	});
	$("#note").find("input:button.input_btn").eq(1).click(function() {
		$("#note_text").val("");
	});
	$("#note").find("input:button.input_btn").eq(2).click(function() {
		$("#note").hide();
		$(mask).hide();
		//操作完成之后继续播放
		playpause(false);
	});
}

/**
 * 划重点接口，在js中定义与播放器中同原型的方法
 * 
 * @param vedioaddress
 *            表示p2p的频道ID或者vod的url
 * @param type
 *            表示视频的清晰度high|standard|audio
 * @param vedioname
 *            表示视频的名称
 * @param time
 *            表示当前暂停的时间锚点
 */
function avc_important(vedioaddress, type, vedioname, time) {
	//先暂停视频，显示记笔记界面
	playpause(true);
	var mask = create_mask();
	$(mask).show();
	//计算时间
	if(time == null){
		time = 0;
	}
	$("#important_time").text(formatTimeStr(time));
	hiddenPanel();
	$("#important").css("margin-top",
			($(mask).height() - $("#important").height() - 36) / 2 + "px").show();
	$("#important").find("input:button.input_btn").eq(0).get(0).onclick = function() {
		var important_text = $("#important_text").val().replace(/\n/g, "<br/>");
		$("#saving_important").prev("p").hide();
		$("#saving_important").show();
		$.ajax({
			url : wwwroot + "/coursecenter/courseplay/save_important",
			type : "POST",
			data : {
				cid : $("#cid").val(),
				time : time,
				content : important_text
			},
			dataType : "json",
			success : function(result){
			},
			error : function(result){
			},
			complete : function(){
				$("#saving_important").prev("p").show();
				$("#saving_important").hide();
				$("#important_text").val("");
				$("#important").hide();
				$(mask).hide();
				//操作完成之后继续播放
				playpause(false);
			}
		});
	}
	$("#important").find("input:button.input_btn").eq(1).click(function() {
		$("#important").hide();
		$(mask).hide();
		//操作完成之后继续播放
		playpause(false);
	});
}

/**
 * 随堂提问接口，在js中与播放器接口一样
 * @param vedioaddress
 * @param type
 * @param vedioname
 * @param time
 */
function avc_question(vedioaddress, type, vedioname, time){
	//暂停播放
	playpause(true);
	var mask = create_mask();
	$(mask).show();
	hiddenPanel();
	clearQuestionForm();
	//var url = wwwroot + "/coursecenter/courseplay/ask_question/"+$('#cid').val();
	//window.open(url,'newwindow','width=500,height=500,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=0,status=no');
	//window.open(url);
	//window.showModalDialog(url, "dialogWidth=500px;scroll:none");
	
	$("#question").css("margin-top",
			($(mask).height() - $("#question").height() - 36) / 2 + "px").show();
	$("#question").find("input:button.input_btn").eq(0).click(function() {
		var result = checkQuestionForm();
		if(result == true){
			var remindWay = jqchk();
			/*var remindWay = '';
			$("#remindWayDiv input:radio").each(function(index, obj){
				if(obj.checked){
					remindWay = $(obj).val();
				}
			});*/
			$("#saving_question").prev("p").hide();
			$("#saving_question").show();
			var content = CKEDITOR.instances.description.getData();
			$.ajax({
				url : wwwroot + "/coursecenter/courseplay/save_question",
				type : "POST",
				data : {
					cid : $("#cid").val(),
					title : $("#q_title").val(),
					//description : $("#description").val().replace(/\n/g, "<br/>"),
					description : content,
					//imgUrl : $("#viewfile").val(),
					tags: $("#tags_input").val(),
					remindWay: remindWay.toString()
					//mobile: $("#mobile").val()
				},
				dataType : "json",
				success : function(result){
				},
				error : function(result){
				},
				complete : function(){
					$("#saving_question").prev("p").show();
					$("#saving_question").hide();
					$("#description").val("");
					$("#question").hide();
					$(mask).hide();
					//操作完成之后继续播放
					playpause(false);
				}
			});
		}
		event.stopPropagation();
	});
	$("#question").find("input:button.input_btn").eq(1).click(function() {
		$("#description").val("");
		$("#title").val("");
	});
	$("#question").find("input:button.input_btn").eq(2).click(function() {
		$("#question").hide();
		$(mask).hide();
		//操作完成之后继续播放
		playpause(false);
	});

}

/**
 * 随堂提问表单验证
 * @returns {Boolean}
 */
function checkQuestionForm(){
	var result = true;
	var q_title = $("#q_title").val().replace(/\n/g, "<br/>");
	if(q_title.length == 0){
		$("#msg_title").show();
		result = false;
	}

	return result;
}
/**
 * 随堂提问清空表单
 */
function clearQuestionForm(){
	$("#q_title").val("");
	$("#tags_input").val("");
	//$("input[name='remindWay']").attr('checked','');
	$(":checkbox").attr("checked","");
	CKEDITOR.instances.description.setData("");
	
}

/**
 * 创建播放器上的遮罩层对象
 */
function create_mask() {
	var flashContent = $("#XAPlayer");
	var mask = document.getElementById("player_mask");
	mask.style.top = flashContent.offset().top + "px";
	mask.style.left = flashContent.offset().left + "px";
	mask.style.width = flashContent.width() + "px";
	mask.style.height = flashContent.height() + "px";
	document.body.appendChild(mask);
	return mask;
}

/**
 * jquery获取复选框值
 */
function jqchk(){
	var chk_value =[];
	$("input[name='remindWay']:checked").each(function(){
		chk_value.push($(this).val());
	});
	//alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value);
	return chk_value;
}

/**
 * 限制记笔记的字数
 */
function limitTextArea(field){
    	maxlimit=600;
    	if (field.value.length > maxlimit){
     		field.value = field.value.substring(0, maxlimit);
     	}
      
}