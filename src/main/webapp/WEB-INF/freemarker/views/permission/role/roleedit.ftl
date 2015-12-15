<form method="post" action="perm/role/save.do" modelAttribute="role" class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);">
<div class="pageContent" >
    <div class="pageFormContent" layoutH="55">
        <#if role.roleId??>
            <input type="hidden" name="roleId" value="${role.roleId!}"/>
            <input type="hidden" name="createDate" value="${(role.createDate?string("yyyy-MM-dd hh:mm:ss"))!}"/>
        </#if>
        <p>
            <label for="">角色名称：</label>
            <input type="text" name="roleName" id="" class="required" value="${role.roleName!}" />
        </p>
        <p>
            <label for="">角色中文名称：</label>
            <input type="text" name="label" id="" class="required" value="${role.label!}" />
        </p>
        <p>
            <label for="">描述：</label>
            <textarea name="description" id="" style="width:60%;height:164px">${role.description!}</textarea>
        </p>
    </div>
</div>
<div class="formBar">
    <ul>
        <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
        <li>
            <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
        </li>
    </ul>
</div>
</form>
