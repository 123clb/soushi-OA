<div class="pageContent" >
    <div class="panelBar">
        <ul class="toolBar">
            <li><a href="device/category/add.do" class="add" fresh="true" width="450" height="210" mark="true"  target="dialog"><span>添加</span></a></li>
            <li><a href="device/category/modify.do?categoryId={sid_category}" class="edit" fresh="true" mark="true" target="dialog"><span>修改</span></a></li>
            <li><a href="device/category/delete.do?categoryId={sid_category}" class="delete" fresh="true" target="ajaxTodo" title="确定要删除吗？"><span>删除</span></a></li>
        </ul>
    </div>
   <table class="table" width="100%" layoutH="90" id="fvbox">
        <thead>
            <tr>
                <th align="center" width="5%">类别ID</th>
                <th align="center" width="7%">类别名称</th>
                <th align="center" width="7%">添加时间</th>
                <th align="center" width="20%">描述</th>
            </tr>
        </thead>
        <tbody>
            <#if categoryList??>
                <#list categoryList as dcagy> 
                <tr target="sid_category"  rel="${dcagy.categoryId!}" colspan="1">
                    <td>${dcagy.categoryId!}</td>
                    <td>${dcagy.categoryName!}</td>
                    <td>${dcagy.createDate!}</td>
                    <td>${dcagy.description!}</td>
                </#list>
            </#if>
        </tbody>
    </table>
</div>
