<form method="post" action="attend/rule/save.do"  modelAttribute="user"  class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);">
<div class="pageContent" >
    <div class="pageFormContent">
        <#if (rule.ruleId)??>
            <input type="hidden" name="ruleId" value="${rule.ruleId!}"/>
        </#if>
        <p>
            <label for="">规则名称：</label>
            <input type="text" name="ruleName" id="" class="required" value="${rule.ruleName!}" />
        </p>
        <p>
            <label for="">上班时间：</label>
            <input type="text" name="morningTime" id="" class="required" value="${rule.morningTime!}" />
        </p>         
        <p>
            <label for="">下班时间：</label>
            <input type="text" name="afterTime"  id="" class="required" value="${rule.afterTime!}" />
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
                <textarea class=" "  style="width:90%;height:105px;"  name="description">${rule.description!}</textarea>
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
