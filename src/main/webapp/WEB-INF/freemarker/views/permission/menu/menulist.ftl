
<div class="pageContent" >
    <div class="panelBar">
        <ul class="toolBar">
            <li><a href="perm/menu/before/add.do<#if parentMenu??>?parentId=${parentMenu.menuId!}</#if>" height="315" width="809" class="add" fresh="true" mark="true" target="dialog"><span>添加</span></a></li>
            <li><a href="perm/menu/before/modify.do?menuId={sid_menu}" height="315" width="809" class="edit" fresh="true" mark="true" target="dialog"><span>修改</span></a></li>
            <li><a href="perm/menu/delete.do?menuId={sid_menu}" class="delete" fresh="true" target="ajaxTodo" title="确定要删除吗？" callback="treeReloadNavTabAjaxDone"><span>删除</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="90" id="fvbox">
        <thead>
            <tr>
                <th align="center" width="5%">菜单ID</th>
                <th align="center" width="5%">菜单名称</th>
                <th align="center" width="20%">菜单路径</th>
                <th align="center" width="5%">菜单序号</th>
                <th align="center" width="5%">父菜单</th>
                <th align="center" width="5%">创建日期</th>
                <th align="center" width="15%">描述</th>
            </tr>
        </thead>
        <tbody>
            <#if menuList??>
                <#list menuList as menu> 
                <tr target="sid_menu" rel="${menu.menuId!}" colspan="1">
                    <td>${menu.menuId!}</td>
                    <td>${menu.menuName!}</td>
                    <td>${menu.menuPath!}</td>
                    <td>${menu.menuSeq!}</td>
                    <td>${(parentMenu.menuName)!}</td>
                    <td>${menu.createDate?default(.now)?string("yyyy-MM-dd")}</td>
                    <td>${menu.description!}</td>
                </tr>
                </#list>
            </#if>
        </tbody>
    </table>
</div>
