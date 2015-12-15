<div class="pageHeader">
    <form id="pagerForm" method="post" action="attend/again/list.do"   onsubmit="return navTabSearch(this);" >
        <#if pageCtx??>
        <input type="hidden" name="pageNum" value="${pageCtx.pageBean.currentPage}" />
        <input type="hidden" name="numPerPage" value="${pageCtx.pageBean.pageSize}" />
        <input type="hidden" name="orderField" value="${orderField!}" />
        <input type="hidden" name="orderDirection" value="${orderDirection!}" />
        </#if>
        <div class="searchBar" style="text-align:center">
            <table class="searchContent" >
                <tr>
                    <td>补签人：      
                     <input type="text"  name="searchLeaveName" id="" value="${searchLeaveName!}">
                    </td>                                                                           
                    <td>
                        <label for="">补签时间：</label>
                        <input type="text" name="searchStartTime"  id=""   format="yyyy-MM-dd HH:mm:00" class="date" value="${(searchStartTime?string("yyyy-MM-dd HH:mm:00"))!}"/>
                        <span> - </span>
                        <input type="text" name="searchEndTime"    id=""   format="yyyy-MM-dd HH:mm:00" class="date" value="${(searchEndTime?string("yyyy-MM-dd HH:mm:00"))!}"/>
                   </td>            
                    <td>                       
                     <select name="chargeName" id="chargeName" class="combox required">
		                <option value="">请选择</option>
		                <#if usermap??>
		                <#list usermap?keys as mykey>
			                     <option value="${mykey!}"  <#if searchSendName?? &&searchSendName?string==mykey> selected </#if>>  ${usermap[mykey]!}</option>         
		                  </#list>
		                 </#if>
                    </select>   
                    </td>
                    <td><div class="buttonActive"><div class="buttonContent"><button type="submit">查 询</button></div>                    	
                    </div></td>
                </tr>
               
            </table>
        </div>    
    </form>
</div>
<div class="pageContent" >
    <div class="panelBar">
        <ul class="toolBar">
        <#if !isPersonnel??>
            <li><a href="attend/leave/add.do" class="add" fresh="true" width="880" height="430" mark="true" rel="drdialog"  target="dialog"><span>添加</span></a></li>
            <#--<li><a href="attend/leave/modify.do?leaveId={lid_leaveId}" width="880" height="430"  class="edit" fresh="true" mark="true" target="dialog"><span>修改</span></a></li>-->
            <li><a href="attend/leave/delete.do?againId={lid_againId}" class="delete" fresh="true" target="ajaxTodo" title="确定要删除吗？"><span>删除</span></a></li>
       </#if>
        </ul>
    </div>
 <table class="table" width="100%" layoutH="90" id="fvbox">
        <thead>
            <tr>
                <th align="center"  width="2%">ID</th>
                <th align="center"  width="5%"  <@p.order field="againName"/>>补签人</th>
                <th align="center"  width="6%" <@p.order field="againTime"/>>未打卡时间</th>    
                <th align="center"  width="6%" <@p.order field="createDate"/>>补签时间</th>              
                <th align="center"  width="5%" >主管签字</th>  
                <th align="center"  width="5%" >备注</th>
            </tr>
      </thead>
        <tbody>
            <#if pageCtx??>
                <#list pageCtx.itemList as lg> 
                <tr target="lid_againId"    rel="${lg.againId!}" colspan="1">
                    <td>${lg.againId!}</td>
                    <td>${lg.againName!}</td>
                    <td>${lg.againTime!}</td>
                    <td>${lg.createDate!}</td>                 
                    <td>
                   <#if !lg.isCheck??>                                   
                 	 <a href="attend/again/beforesign.do?againId=${lg.againId!}" class="add"  fresh="true"  width="830" height="327" mark="true" rel="drdialog"  target="dialog">未 签</a>
                    <#elseif lg.isCheck>
                                                          已 签
                      <#else>
                                                         不同意
                     </#if>
                   </td>
                <td>${lg.description!}</td>
          </#list>
       </#if>
      </tbody>
   </table>
    <div class="panelBar">
        <#if pageCtx??>
            <@p.page pageCtx=pageCtx />
        </#if>
    </div>
</div>
