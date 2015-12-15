<style type="text/css">
    ul.rightTools {float:right; display:block;}
    ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<script type="text/javascript">
$(function(){
    $("#browser").treeview({
        url: "perm/menu/tree.do"
    });
});
</script>
<div class="pageContent" style="padding:5px">
    <div class="tabs">
        <div class="tabsContent">
            <div>
                <div layoutH="15" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
                    <ul id="browser" class="filetree"></ul>
                </div>
                <div id="menulistbox" class="unitBox" style="margin-left:246px;">
                    <#include "menulist.ftl" />
                </div>
            </div>
        </div>
    </div>
</div>
