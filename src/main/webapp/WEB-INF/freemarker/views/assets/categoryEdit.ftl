<form method="post" action="device/category/save.do"   modelAttribute="category"  class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);">
<div class="pageContent" >
    <div class="pageFormContent"  layoutH="55">
    <#if category??>y<#else>n</#if>
       <#if category.categoryId??>
            <input type="hidden" name="categoryId" value="${category.categoryId!}"/>
       </#if>    
        <p>
            <label for="">类别名称：</label>
            <input type="text" name="categoryName" id="" class="required" value="${category.categoryName!}" />
        </p>
        <p>
            <label for="">描述：</label>
            <textarea name="description" id="" style="width:50%;height:64px">${category.description!}</textarea>
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
