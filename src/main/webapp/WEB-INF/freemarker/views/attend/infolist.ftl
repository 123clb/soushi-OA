<div class="pageHeader">
<#if rManager??>
<#if rManager==1>
    <form id="pagerForm" method="post" action="attend/info/list.do"   onsubmit="return navTabSearch(this);" >
        <#if pageCtx??>
        <input type="hidden" name="pageNum" value="${pageCtx.pageBean.currentPage}" />
        <input type="hidden" name="numPerPage" value="${pageCtx.pageBean.pageSize}" />
        <input type="hidden" name="orderField" value="${orderField!}" />
        <input type="hidden" name="orderDirection" value="${orderDirection!}" />
        </#if>
        <div class="searchBar" style="text-align:center">
            <table class="searchContent" >
                <tr>
                    <td>姓 名：      
                     <input type="text"  name="searchName" id="" value="${searchName!}">
                    </td>                                                                           
                    <td>
                        <label for="">年 月:</label>
                        <input type="text" name="searchTime"  id=""   format="yyyy-MM" class="date" value="${searchTime!}"/>
                   </td>            
                    <td><div class="buttonActive"><div class="buttonContent"><button type="submit">查 询</button></div>                    	
                    </div></td> 
                </tr>
            </table>
        </div>    
    </form>
   </#if>
  </#if>
</div>
<div class="pageContent" >
    <div class="panelBar">
       
    </div>
 <table class="table" width="100%" layoutH="90" id="fvbox">
        <thead>
            <tr>
                <th align="center"  width="2%">ID</th>
                <th align="center"  width="6%" <@p.order field="infoTime"/>>年-月</th> 
                <th align="center"  width="5%">姓名</th>
                <th align="center"  width="6%" <@p.order field="laterNum"/>>迟到次数</th> 
                <th align="center"  width="6%" <@p.order field="leaveNum"/>>请假天数</th>             
                <th align="center"  width="5%" >早勤记录</th>  
                <th align="center"  width="5%" >晚勤记录</th>
            </tr>
      </thead>
        <tbody>
            <#if pageCtx??>
                <#list pageCtx.itemList as info> 
                <tr target="lid_againId"    rel="${info.infoId!}" colspan="1">
                    <td>${info.infoId!}</td>
                    <td>${info.infoTime!}</td>
                    <td>${info.personName!}</td>
                    <td>${info.laterNum!}</td>
                    <td>${info.leaveNum!}</td> 
                    <td><a href="attend/info/amDetail.do?r=${info.rtxNo!}&t=${info.infoTime}&f=m" class="add"  fresh="true"  width="310" height="327" mark="true" rel="drdialog"  target="dialog">早记录 </a> (<a href="attend/amcharts.do?r=${info.rtxNo!}&t=${info.infoTime}&f=m" class="add"  fresh="true"  width="610" height="427" mark="true" rel="drdialog"   target="dialog"> <img src="resources/img/datepic.png" title="打卡时间图"  style="margin-top:3px" height=15px; /></a>)</td>                                    
                    <td><a href="attend/info/amDetail.do?r=${info.rtxNo!}&t=${info.infoTime}&f=n" class="add"  fresh="true"  width="310" height="327" mark="true" rel="drdialog"  target="dialog">晚记录</a></td>                                    
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

