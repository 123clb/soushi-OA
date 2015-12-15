<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a href="device/detail/modify.do?detailId={sid_detail}" class="edit" fresh="true" mark="true" target="dialog"><span>修改</span></a></li>              
            <li><a href="device/detail/delete.do?detailId={sid_detail}" class="delete" fresh="true" target="ajaxTodo" title="确定要删除吗？"><span>删除</span></a></li>
        </ul>
    </div>
   <table class="table" width="100%" layoutH="90" id="fvbox">
        <thead>
            <tr> 
                <th align="center"  width="5%">ID</th>
                <th align="center"  width="7%">使用者</th>
                <th align="center"  width="7%">变更人</th>
                <th align="center"  width="15%">操作时间</th>
                <th align="center"  width="20%">描 述</th>               
            </tr>
       </thead>
     <tbody>
          <#if detailsList??>
               <#list detailsList as dv>
                 <tr target="sid_detail"  rel="${dv.detailId!}"   colspan="1">
                   <td>${dv.detailId!}</td>
                   <td>${dv.usePersonName!}</td>
                   <td>${assigners[dv.assignPersonId?string]!}</td> 
                   <td>${dv.createDate!}</td>
                   <td>${dv.description!}</td> 
               </#list>
         </#if>         
     </tbody>
</table>
</div>