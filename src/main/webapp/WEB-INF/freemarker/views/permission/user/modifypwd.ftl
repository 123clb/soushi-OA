<form method="post" action="perm/user/pwd/modify.do" modelAttribute="user" class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);">
<div class="pageContent" >
    <div class="pageFormContent" layoutH="50">
        <input type="hidden" name="userId" value="${userId!}"/>
        <p>
            <label for="">旧密码：</label>
            <input type="password" name="oldpwd" id="" value="" class="required"/>
        </p>
        <p>
            <label for="">新密码：</label>
            <input type="password" name="newpwd" id="newpwd" value="" class="required" minlength="5" />
        </p>
        <p>
            <label for="">请再次输入密码：</label>
            <input type="password" name="newpwd2" id="newpwd2" value="" equalTo="#newpwd" class="required" minlength="5" />
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
