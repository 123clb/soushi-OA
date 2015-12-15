<form method="post" action="allocation/save.do"   modelAttribute="deviceDetail" class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);">
<div class="pageContent" >
    <div class="pageFormContent" layoutH="55">
      <input type="hidden"    name="deviceId" value="${devid!}"/>
       <#if deviceDetail.detailId??>
            <input type="hidden" name="detailId" value="${deviceDetail.detailId!}"/>
       </#if>    
        <p>
            <label for="">使用人：</label>
            <input type="hidden"  name="orgLookup.id"    id="orgLookup.id"  value="${deviceDetail.usePersonId!}">                          
            <input type="text"    class="required textInput valid" readonly="readonly"    id="dwz.orgLookup.dtName"    name="dwz.orgLookup.dtName"   value="<#if deviceDetail.detailId?? >${deviceDetail.usePersonName!}</#if>"   lookupgroup="orgLookup" >  
            <a class="btnLook"    href="device/user/list.do"      lookupgroup="orgLookup"      rel="dtdialog"   fresh="true"  mask="true">选择</a>   
        </p>
        <p>
           <label for="">分配者：</label>
           <select   name="assignPersonId"  id="assignPersonId"  class="combox required">
           <#if assigners?? >
           <#list assigners?keys as mykey>
             <option  value="${mykey!}" <#if deviceDetail.detailId?? &&deviceDetail.detailId?string==mykey> selected</#if>>${assigners[mykey]!}</option>
           </#list>
        </#if>
            </select>
        </p>
        <p>
            <label for="">描述：</label>
            <textarea name="description" id="" style="width:50%;height:64px">${deviceDetail.description!}</textarea>
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
