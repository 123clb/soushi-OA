<div class="pageHeader">
<style type="text/css">
#tooltip {
    background: none repeat scroll 0 0 #F7F5D1;
    border: 1px solid #333333;
    color: #333333;
    display: none;
    padding: 3px;
    position: absolute;
}
</style> 
<script type="text/javascript">
$(function(){
	var x = 10;
	var y = 20;
	$("a.tooltip").live("mouseover", function(e){
		this.myTitle = this.title;		
		this.title = "";	
		var imgTitle = this.myTitle? "<br/>" + this.myTitle : "";
		var tooltip = "<div id='tooltip'><img src='"+ this.href +"'  width=200; height=200;  alt='预览图'/>"+imgTitle+"<\/div>";
		$("body").append(tooltip);	
		$("#tooltip").css({
				"top": (e.pageY+y) + "px",
				"left":  (e.pageX+x)  + "px"
			}).show("fast");	  
    }).live("mouseout", function(){
		this.title = this.myTitle;	
		$("#tooltip").remove();	
    }).live("mousemove", function(e){
		$("#tooltip")
			.css({
				"top": (e.pageY+y) + "px",
				"left":  (e.pageX+x)  + "px"
			});
	});
})
</script>
    <form id="pagerForm" method="post" action="assets/devicelist.do"  onsubmit="return navTabSearch(this);" >
        <#if pageCtx??>
        <input type="hidden" name="pageNum" value="${pageCtx.pageBean.currentPage}" />
        <input type="hidden" name="numPerPage" value="${pageCtx.pageBean.pageSize}" />
        <input type="hidden" name="orderField" value="${orderField!}" />
        <input type="hidden" name="orderDirection" value="${orderDirection!}" />
        </#if>
        <div class="searchBar" style="text-align:center">
            <table class="searchContent" >
                <tr>
                    <td>设备名称：<input type="text" name="searchDeviceName" id="searchDeviceName"   value="${searchDeviceName!}" style="width:140px"/></td>
                     <td>
                      <select name="searchassigId" id="">
                            <option value="">监管人</option>
                         <#if assigners??>
                              <#list assigners?keys as mykey>
                                  <option value="${mykey!}" <#if searchassigId?? && searchassigId?string == mykey>selected</#if>>${assigners[mykey]!}</option>
                              </#list>
                         </#if>
                  </select>  
                   </td>
                   <td>
                   	<label for="">使用人</label>
                   	<input type = "text" name="searchPersonUseName" value="${searchPersonUseName!}"/ >
                   </td>                   
                    <td>
                        <label for="">更新时间：</label>
                        <input type="text" name="deviceStartTime" id=""  format="yyyy-MM-dd HH:mm:00" class="date" value="${(deviceStartTime?string("yyyy-MM-dd HH:mm:00"))!}"/>
                        <span> - </span>
                        <input type="text" name="deviceEndTime" id=""  format="yyyy-MM-dd HH:mm:00" class="date" value="${(deviceEndTime?string("yyyy-MM-dd HH:mm:00"))!}"/>
                  </td>          
                    <td>                       
                       <select name="deviceState" class="combox" id="deviceState">
                            <#--<option value="" <#if !status??>selected</#if>>使用状态</option>-->
                         <option value="" >使用状态</option>
                            <#list statusEnum?values as s>
                                <option value="${s}" <#if status?? && status == s>selected</#if>>${s.label}</option>
                            </#list>
                        </select>     
                    </td>
                    <td><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div>	
                    </div></td>
                </tr>
            </table>
        </div>    
    </form>
</div>
<div class="pageContent" >
    <div class="panelBar">
        <ul class="toolBar">
            <li><a href="device/add.do" class="add" fresh="true" width="890" height="542" mark="true" rel="drdialog"  target="dialog"><span>添加</span></a></li>
            <li><a href="device/modify.do?deviceId={sid_device}" width="890" height="542"  class="edit" fresh="true" mark="true" target="dialog"><span>修改</span></a></li>
            <li><a href="device/delete.do?deviceId={sid_device}" class="delete" fresh="true" target="ajaxTodo" title="确定要删除吗？"><span>删除</span></a></li>
            <li><a href="device/scrap.do?deviceId={sid_device}"  width="450" height="325"  class="add" fresh="true"  mark="true"  target="dialog"><span>设置状态</span></a></li>
        </ul>
</div>
 <table class="table" width="100%" layoutH="90" id="fvbox">
        <thead>
            <tr>
                <th align="center"  width="2%">ID</th>
                <th align="center"  width="5%">设备编号</th>
                <th align="center"  width="10%">设备名称</th>
                <th align="center"  width="5%">品牌</th>
                <th align="center"  width="5%">采购价格</th>
                <th align="center"  width="7%">采购时间</th>
                <th align="center"  width="5%">使用人</th>
                <th align="center"  width="5%">监管人</th>
                <th align="center"  width="5%">状态</th>
                <th align="center"  width="7%">变更使用人</th>
                <th align="center"  width="4%">使用情况</th>
                <th align="center"  width="3%">报废图片</th>
            </tr>
      </thead>
        <tbody>
            <#if pageCtx??>
                <#list pageCtx.itemList as dv> 
                <tr target="sid_device"    rel="${dv.deviceId!}" colspan="1">
                    <td>${dv.deviceId!}</td>
                    <td>${dv.dNumber!}</td>
                    <td>${dv.deviceName!}<#if dv.devicePic??>&nbsp;&nbsp;&nbsp;&nbsp;<a href="resources/upload/${dv.devicePic!}" class="tooltip"  target="_blank" title="${dv.deviceName!}"><img src="resources/upload/${dv.devicePic!}" width="20" alt="${dv.deviceName!}"></a></#if></td>
                    <td>${dv.brand!}</td>
                    <td>${dv.devicePrice!}</td>
                    <td>${(dv.purchaseDate?string("yyyy-MM-dd"))!}</td>
                    <td>${dv.usePersonName!}</td>
                    <td>${assigners[dv.supervisePerson?string]!}</td>
                    <td>${dv.deviceState.label!}</td>
                    <td><a href="device/allocation.do?deviceId=${dv.deviceId!}"   class="add"   fresh="true" width="450" height="325" mark="true" rel="drdialog" target="dialog"><span>变更使用人</span></a></d>
               		<td><a href="device/detaillist.do?deviceId=${dv.deviceId!}"   class="add"   target="dialog"><span>查看明细 </span></a></td>
              		<td><#if dv.scrapPic??>&nbsp;&nbsp;&nbsp;&nbsp;<a href="resources/upload/${dv.scrapPic!}"  target="_blank"><img src="resources/upload/${dv.scrapPic!}"  width="20"  alt="${dv.scrapPic!}"></a></#if></td>
               </#list>
       </#if>
      </tbody>
   </table>
    <div class="panelBar">
        <#if pageCtx??>
            <@p.page pageCtx=pageCtx /><div style="float:left;margin-top:6px"><span>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">总计金额: ${totalPrice!}</font></span></div>
        </#if>
    </div>
</div> 


