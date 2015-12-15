/*
 * Async Treeview 0.1 - Lazy-loading extension for Treeview
 * 
 * http://bassistance.de/jquery-plugins/jquery-plugin-treeview/
 *
 * Copyright (c) 2007 Jörn Zaefferer
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * Revision: $Id$
 *
 */

;(function($) {

function load(settings, _this, child, container) {
	function createNode(parent) {
		var $li = $("<li/>");
		if(this.param) {
		  for(var k in this.param) {
        $li.attr(k, this.param[k]);
      }
		}
		var current = $li.attr("id", this.id || "").html("<span>" + this.text + "</span>").appendTo(parent);
		if (this.classes) {
			current.children("span").addClass(this.classes);
		}
		if (this.expanded) {
			current.addClass("open");
		}
		
		if (this.hasChildren || this.children && this.children.length) {
			var branch = $("<ul/>").appendTo(current);
			if (this.hasChildren) {
				current.addClass("hasChildren");
				createNode.call({
					classes: "placeholder",
					text: "&nbsp;",
					children:[]
				}, branch);
			}
			if (this.children && this.children.length) {
				$.each(this.children, createNode, [branch]);
			}
		}
		
	}
	
  //从当前节点中提取出属性，并加入到请求url上
  var urlstr = settings.url;
  if(_this[0] && _this[0].attributes) {
    var url = $.url(urlstr);
    var attrs = _this[0].attributes;
    var param = url.param();
    for(var i = 0; i < attrs.length; ++i) {
        param[attrs[i].name] = attrs[i].value;
    }
    urlstr = url.attr("host") + url.attr("directory") + url.attr("file") + "?";
    urlstr = urlstr + $.param(param);
  }
	$.ajax($.extend(true, {
		url: urlstr,
		dataType: "json",
		success: function(response) {
			child.empty();
			if(response==null){
				return;
			}
			$.each(response, createNode, [child]);
	    $(container).treeview({add: child});
	    $(child).initUI();
	    if(settings.reload) {
	      // 在这里模拟一次点击事件，刷新指定节点对应的右边列表.
  			$(_this).find("a:first").click();
  			// reload结束之后将reload重置
  			settings.reload = false;
	    }
	  }
	}, settings.ajax));
	/*
	$.getJSON(settings.url, {root: root}, function(response) {
		function createNode(parent) {
			var current = $("<li/>").attr("id", this.id || "").html("<span>" + this.text + "</span>").appendTo(parent);
			if (this.classes) {
				current.children("span").addClass(this.classes);
			}
			if (this.expanded) {
				current.addClass("open");
			}
			if (this.hasChildren || this.children && this.children.length) {
				var branch = $("<ul/>").appendTo(current);
				if (this.hasChildren) {
					current.addClass("hasChildren");
					createNode.call({
						classes: "placeholder",
						text: "&nbsp;",
						children:[]
					}, branch);
				}
				if (this.children && this.children.length) {
					$.each(this.children, createNode, [branch])
				}
			}
		}
		child.empty();
		$.each(response, createNode, [child]);
        $(container).treeview({add: child});
    });
    */
}

var proxied = $.fn.treeview;
$.fn.treeview = function(settings) {
	if (!settings.url) {
		return proxied.apply(this, arguments);
	}
	var container = this;
	if (!container.children().size())
		load(settings, "source", this, container);
	var userToggle = settings.toggle;
	var treeObj = proxied.call(this, $.extend({}, settings, {
		collapsed: true,
		toggle: function() {
			var $this = $(this);
			if ($this.hasClass("hasChildren")) {
				var childList = $this.removeClass("hasChildren").find("ul");
				load(settings, $this, childList, container);
			}
			if (userToggle) {
				userToggle.apply(this, arguments);
			}
		}
	}));
	
	treeObj.bind('reload', function(event, id){
		var treeNode = $("#"+id);
		var childNode = treeNode.find('ul');
		childNode.empty();
		// 告诉异步树，此次加载是一次reload
		settings.reload = true;
		load(settings, treeNode, childNode, container);
	});
	
	return treeObj;
};

})(jQuery);