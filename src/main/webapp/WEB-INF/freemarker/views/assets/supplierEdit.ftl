<form method="post" action="device/supplier/save.do"   modelAttribute="supplier" class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);">
<div class="pageContent" >
    <div class="pageFormContent" layoutH="55">
       <#if supplier.supplierId??>
            <input type="hidden" name="supplierId" value="${supplier.supplierId!}"/>
       </#if>    
        <p>
            <label for="">供应商名称：</label>
            <input type="text" name="supplierName" id="" class="required" value="${supplier.supplierName!}" />
        </p>
       <p>
            <label for="">联系电话：</label>
            <input type="text" name="supplierPhone" id="" class="required" value="${supplier.supplierPhone!}" />
        </p>
         <p>
            <label for="">联系地址：</label>
            <input type="text" name="supplierAddress" id=""  value="${supplier.supplierAddress!}" />
        </p>
        <p>
            <label for="">描述：</label>
            <textarea name="description" id="" style="width:50%;height:64px">${supplier.description!}</textarea>
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
