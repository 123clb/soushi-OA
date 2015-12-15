<div class="pageHeader">
    <form id="pagerForm" method="post" action="attend/record/list.do"   onsubmit="return navTabSearch(this);" >
        <#if pageCtx??>
        <input type="hidden" name="pageNum" value="${pageCtx.pageBean.currentPage}" />
        <input type="hidden" name="numPerPage" value="${pageCtx.pageBean.pageSize}" />
        <input type="hidden" name="orderField" value="${orderField!}" />
        <input type="hidden" name="orderDirection" value="${orderDirection!}" />
        </#if>
        <div class="searchBar" style="text-align:center">
            <table class="searchContent" >
                <tr>
                    <td>姓名：     
                     <input type="text"  name="searchName" id="" value="${searchName!}">
                    </td>                                                                           
                   <td>
                        <label for="">时间：</label>
                        <input type="text" name="searchTime"  id="searchTime"   format="yyyy-MM-dd"  class="date" value="${searchTime!}"/>
                   </td>               
                    <td><div class="buttonActive"><div class="buttonContent"><button type="submit">查 询</button></div>                    	
                    </div></td>
                </tr>
            </table>
        </div>    
    </form>
</div>
<div class="pageContent" >
 <table class="table" width="60%" layoutH="90" id="fvbox">
      <thead>
            <tr>
                <th align="center"  width="1%">UID</th>
                <th align="center"  width="1%" <@p.order field="rTime"/>>记录时间</th> 
                <th align="center"  width="1%" >上下午</th>    
                <th align="center"  width="1%" <@p.order field="rName"/>>姓 名</th>         
            </tr>
      </thead>
        <tbody>
            <#if pageCtx??>
               <#list pageCtx.itemList as ar> 
                <tr  colspan="1">
                    <td>${ar.rUid!}</td>
                    <td>${ar.rTime!}</td> 
                    <td><#if ar.rFlag=="m"><img src="resources/img/1.gif"  alt="上午" /><#else><img src="resources/img/2.gif" alt="下午"  height="18px"/></#if> </td>
                    <td>${ar.rName!}</td>
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
