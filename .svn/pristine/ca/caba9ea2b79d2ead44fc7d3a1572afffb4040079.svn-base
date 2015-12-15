<form method="post" action="perm/role/grant.do" class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);">
<input type="hidden" name="roleId" value="${(role.roleId)}"/>
<div class="pageContent" >
    <table class="list" width="100%" layoutH="55" id="fvbox">
        <tbody>
            <#if menuList??>
                <#assign subMenuList = menuList/>
                <#assign index = 0/>
                <#assign deep = 0/>
                <#include "grantmenu.ftl"/>
            </#if>
        </tbody>
    </table>
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
