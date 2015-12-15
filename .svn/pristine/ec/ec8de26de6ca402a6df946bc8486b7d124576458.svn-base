<div class="pageContent">
    <form action="perm/user/transfer.do" class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);" method="post">
        <div class="pageFormContent">
            <div class="unit">
                <#if isProductManager?? && isProductManager>
                    <input type="hidden" name="fromuserview.id" id="fromuserview.id" value="${(fromUser.userId)!}"/>
                    <input type="text" name="" id="" value="${(fromUser.realName)!}" readonly/>
                <#else>
                    <@p.choose_user id="fromuserview.id" class="required" name="dwz.fromuserview.userName" idValue="${(fromUser.userId)!}" nameValue="${(fromUser.realName)!}" lookupGroup="fromuserview"/>
                </#if>
                <label for="" style="width:35px;"> >>>> </label>
                <@p.choose_user id="touserview.id" name="dwz.touserview.userName" idValue="${(toUser.userId)!}" nameValue="${(toUser.realName)!}" lookupGroup="touserview" class="required"/>  
            </div>
        </div>
        <div class="pageFormContent">
            <div class="unitBox" layoutH="105">
                <ul id="catalog_tree" class="filetree"></ul>
                <script type="text/javascript">
                    $("#catalog_tree").treeview({
                        url: "perm/user/data/haveperm.do?userid=${(fromUser.userId)!}"
                    });
                </script>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="button"><div class="buttonContent"><button type="submit">转移</button></div></div>
                </li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>