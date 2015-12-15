<div class="pageContent" >
    <div class="panelBar">
        <ul class="toolBar">
            <li><a href="device/supplier/add.do" class="add" fresh="true" width="410" height="335" mark="true"  target="dialog"><span>添加</span></a></li>
            <li><a href="device/supplier/modify.do?supplierId={sid_supplier}" class="edit" fresh="true" mark="true" target="dialog"><span>修改</span></a></li>
            <li><a href="device/supplier/delete.do?supplierId={sid_supplier}" class="delete" fresh="true" target="ajaxTodo" title="确定要删除吗？"><span>删除</span></a></li>
        </ul>
    </div>
   <table class="table" width="100%" layoutH="90" id="fvbox">
        <thead>
            <tr>
                <th align="center" width="5%">ID</th>
                <th align="center" width="7%">供应商名称</th>
                <th align="center" width="7%">联系方式</th>
                <th align="center" width="7%">联系地址</th>
                <th align="center" width="7%">创建时间</th>
                <th align="center" width="20%">描述</th>
            </tr>
        </thead>
        <tbody>
            <#if suppliersList??>
                <#list suppliersList as sp> 
                <tr target="sid_supplier"  rel="${sp.supplierId!}" colspan="1">
                    <td>${sp.supplierId!}</td>
                    <td>${sp.supplierName!}</td>
                    <td>${sp.supplierPhone!}</td>
                    <td>${sp.supplierAddress!}</td>
                    <td>${sp.createDate!}</td>
                    <td>${sp.description!}</td>
                </#list>
            </#if>
        </tbody>
    </table>
</div>
