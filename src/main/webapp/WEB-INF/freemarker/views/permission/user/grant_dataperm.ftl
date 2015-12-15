<form method="post" action="perm/user/grant.do" class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);">
    <input type="hidden" name="userId" id="userId" value="${(user.userId)!}"/>
    <div class="pageContent">
        <div class="unitBox" layoutH="35">
            <ul id="catalog_tree" class="filetree"></ul>
            <script type="text/javascript">
                $("#catalog_tree").treeview({
                    url: "perm/user/data/perm.do?userid=${(user.userId)!}"
                });
                
                function grant(event) {
                    $checkbox = $(event.target);
                    catalogId = $checkbox.val();
                    isGrant = $checkbox[0].checked;
                    userId = $('input#userId').val();
                    $.post("perm/user/grant.do", {catalogId:catalogId, isGrant:isGrant, userId:userId}, function(result){
                        
                    });
                }
            </script>
        </div>
    </div>
    <div class="formBar">
        <ul>
            <li>
                <div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
            </li>
        </ul>
    </div>
</form>