
<div class="pageContent" >
    <div class="panelBar">
        <ul class="toolBar">
            <li><a href="perm/role/before/add.do" class="add" fresh="true" mark="true" target="dialog"><span>添加</span></a></li>
            <li><a href="perm/role/before/modify.do?roleId={sid_role}" class="edit" fresh="true" mark="true" target="dialog"><span>修改</span></a></li>
            <li><a href="perm/role/delete.do?roleId={sid_role}" class="delete" fresh="true" target="ajaxTodo" title="确定要删除吗？"><span>删除</span></a></li>
            <li><a href="perm/role/before/grant.do?roleId={sid_role}" class="icon" fresh="true" target="dialog"><span>授权</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="90" id="fvbox">
        <thead>
            <tr>
                <th align="center" width="5%">角色ID</th>
                <th align="center" width="7%">角色名称</th>
                <th align="center" width="7%">角色中文名称</th>
                <th align="center" width="20%">描述</th>
            </tr>
        </thead>
        <tbody>
            <#if roleList??>
                <#list roleList as role> 
                <tr target="sid_role" rel="${role.roleId!}" colspan="1">
                    <td>${role.roleId!}</td>
                    <td>${role.roleName!}</td>
                    <td>${role.label!}</td>
                    <td>${role.description!}</td>
                </#list>
            </#if>
        </tbody>
    </table>
</div>
