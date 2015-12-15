<div class="pageHeader">
    <form id="pagerForm" method="post" action="attend/leave/list.do"   onsubmit="return navTabSearch(this);" >
        <#if pageCtx??>
	        <input type="hidden" name="pageNum" value="${pageCtx.pageBean.currentPage}" />
	        <input type="hidden" name="numPerPage" value="${pageCtx.pageBean.pageSize}" />
	        <input type="hidden" name="orderField" value="${orderField!}" />
	        <input type="hidden" name="orderDirection" value="${orderDirection!}" />
        </#if>
        <div class="searchBar" style="text-align:center">
            <table class="searchContent" >
                <tr>
                    <td>请假人：      
                     <input type="text"  name="searchLeaveName" id="" value="${searchLeaveName!}">
                    </td>                                                                           
                    <td>
                        <label for="">请假时间：</label>
                        <input type="text" name="searchStartTime"  id=""   format="yyyy-MM-dd HH:mm:00" class="date" value="${(searchStartTime?string("yyyy-MM-dd HH:mm:00"))!}"/>
                        <span> - </span>
                        <input type="text" name="searchEndTime"    id=""   format="yyyy-MM-dd HH:mm:00" class="date" value="${(searchEndTime?string("yyyy-MM-dd HH:mm:00"))!}"/>
                   </td>            
                    <td>                       
                       <select name="attendLeaveEnum" class="combox" id="attendLeaveEnum">                           
                         <option value="" >请假类型</option>
                            <#list leaveEnum?values as s>
                                <option value="${s}" <#if status?? && status == s>selected</#if>>${s.label}</option>
                            </#list>
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
            <li><a href="attend/leave/delete.do?leaveId={lid_leaveId}" class="delete" fresh="true" target="ajaxTodo" title="确定要删除吗？"><span>删除</span></a></li>
       </#if>
        </ul>
    </div>
 <table class="table" width="100%" layoutH="90" id="fvbox">
        <thead>
            <tr>
                <th align="center"  width="2%">ID</th>
                <th align="center"  width="5%"  <@p.order field="applyName"/>>请假人</th>
                <th align="center"  width="5%"  <@p.order field="attendLeaveEnum"/>>类型</th>
                <th align="center"  width="15%">事由</th>
                <th align="center"  width="14%" <@p.order field="leaveStartTime"/>>请假时间</th>              
                <th align="center"  width="5%"  <@p.order field="leaveNum"/>>请假天数</th>
                <th align="center"  width="4%">所属主管</th>
                <th align="center"  width="4%">签 字</th>
                <th align="center"  width="4%">行政审核</th>
                <th align="center"  width="4%">总经理</th>
                <th align="center"  width="10%">销假</th>
                <th align="center"  width="7%" <@p.order field="createDate"/>>创建时间</th>
            </tr>
      </thead>
        <tbody>
            <#if pageCtx??>
                <#list pageCtx.itemList as lv> 
                <tr target="lid_leaveId"    rel="${lv.leaveId!}" colspan="1">
                    <td>${lv.leaveId!}</td>
                    <td>${lv.applyName!}</td>
                    <td>${lv.attendLeaveEnum.label!}</td>
                    <td>${lv.description!}</td>
                    <td>${lv.leaveStartTime?string("yyyy-MM-dd HH:mm")!}--${lv.leaveEndTime?string("MM-dd HH:mm")}</td>                    
                    <td>${lv.leaveNum!}</td>
                    <td>${chargeMap[lv.chargeId?string]!}</td>
                    <td>
                    <#if !lv.isAgree??>                                   
                 	 <a href="attend/leave/beforesign.do?leaveId=${lv.leaveId}&f=a" class="add"  fresh="true"  width="830" height="327" mark="true" rel="drdialog"  target="dialog"><img src="resources/img/unsign.png"  style="margin-top:3px" height=15px;  alt="未 签" /></a>
                    <#elseif lv.isAgree>
                      <img src="resources/img/sign.png" title="已 签"  style="margin-top:3px" height=15px; />
                      <#else>
                     <img src="resources/img/disagree.png" title="不同意"  style="margin-top:3px" height=15px; />
                   </#if>          
                 </td><td>
                    <#if !lv.isVerify??>                                        
                    <a href="attend/leave/beforesign.do?leaveId=${lv.leaveId}&f=v" class="add"  fresh="true"  width="830" height="327" mark="true" rel="drdialog"  target="dialog">  <img src="resources/img/nocheck.png" title=" ${chargeMap[lv.chargeId?string]!}未审"   style="margin-top:3px" height=15px; /></a>
                    <#elseif lv.isVerify>
                     <img src="resources/img/check.png" title="已通过"  style="margin-top:3px" height=15px; />
                     <#else>
                      <img src="resources/img/uncheck.png" title=" 未通过"  style="margin-top:3px" height=15px; />
                    </#if>
                    </td>
                    <td>
                    <#if lv.leaveNum gte 2>
                    <#if !lv.isApproval??>                                        
                    <a href="attend/leave/beforesign.do?leaveId=${lv.leaveId}&f=p" class="add"  fresh="true"  width="830" height="327" mark="true" rel="drdialog"  target="dialog">未审</a>
                    <#elseif lv.isApproval>
                      <img src="resources/img/check.png" title="已通过"  style="margin-top:3px" height=15px; />
                     <#else>
                     	(${chargeMap[lv.chargeId?string]!})未通过
                    </#if>
                    </#if>
                    </td>
                    <td> 
       		   <#if !lv.closeTime??>  
       		   <a href="attend/leave/beforesign.do?leaveId=${lv.leaveId}&f=c" class="add"  fresh="true"  width="830" height="227" mark="true" rel="drdialog"  target="dialog">未销假</a>
       				<#else>
       				  已销(${lv.closeTime?string("yyyy-MM-dd HH:mm")!})
       				</#if>                                     
                    </td>
                    <td>${lv.createDate!}</td>
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
