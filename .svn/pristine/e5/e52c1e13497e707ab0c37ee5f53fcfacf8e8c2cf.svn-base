<form method="post" action="perm/menu/save.do" modelAttribute="menu" class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);">
<div class="pageContent" layoutH="35">
    <div class="pageFormContent">
        <#if menu.menuId??>
            <input type="hidden" name="menuId" value="${menu.menuId!}"/>
            <input type="hidden" name="createDate" value="${(menu.createDate?string("yyyy-MM-dd hh:mm:ss"))!}"/>
        </#if>
        <p>
            <label for="">菜单名称：</label>
            <input type="text" name="menuName" id="" class="required" value="${menu.menuName!}" />
        </p>
        <p>
            <label for="">菜单路径：</label>
            <input type="text" name="menuPath" id="" class="" value="${menu.menuPath!}"  />
        </p>
        <p>
            <label for="">菜单序号：</label>
            <input type="text" name="menuSeq" id="" class="required digits" value="${menu.menuSeq!}" />
        </p>
        <p>
            <label for="">父菜单：</label>
            <input type="hidden" name="parentMenuId" value="${(parentMenu.menuId)!}" />
            <input type="text" name="" readonly="true" id="" value="${(parentMenu.menuName)!}"/>
        </p>
    </div>
    <div class="tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a><span>描述</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent" style="height:111px;">
            <div>
                <textarea class=" "  style="width:90%;height:105px;"  name="description" rows="10" cols="100"  >${menu.description!}</textarea>
            </div>
        </div>
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
