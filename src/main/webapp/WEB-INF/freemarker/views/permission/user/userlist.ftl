<div class="pageHeader">
	<form id="pagerForm" method="post" action="perm/user/list.do"  onsubmit="return navTabSearch(this);">
	 <input type="hidden" name="pageNum" value="${pageCtx.pageBean.currentPage}" />
	 <input type="hidden" name="numPerPage" value="${pageCtx.pageBean.pageSize}" />
	 <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                    <input type="text" name="loginName" id="" alt="登录名" value="${loginName!}" />
                    </td>
                    <td>
                    <input type="text" name="realName" id="" alt="真实姓名" value="${realName!}" />
                    </td>
                    <td><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></td>
                </tr>
            </table>
        </div>
	</form>
</div>

<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" width="460" height="600" fresh=true mask=true href="perm/user/before/add.do" rel="adduserdialog" target="dialog"><span>添加</span></a></li>
            <li><a class="edit" width="460" height="600" fresh=true mask=true href="perm/user/before/modify.do?userId={sid_user}" rel="modifyuserdialog" target="dialog"><span>修改</span></a></li>
            <#--
            <li><a class="delete" fresh=true href="perm/user/delete.do?userId={sid_user}" target="ajaxTodo" title="确定要删除目录及其子目录吗?" callback="treeReloadNavTabAjaxDone"><span>删除</span></a></li>
            -->
            <li><a href="perm/user/before/grant.do?userId={sid_user}" height="552" fresh=true mask=true target="dialog" class="add"><span>授权</span></a></li>
            <li><a href="perm/user/pwd/reset.do?userId={sid_user}" fresh=true mask=true target="ajaxTodo" class="icon" title="你确定要重置此帐户的密码吗？"><span>重置密码</span></a></li>
            <li id="state_lock_btn"><a class="icon" href="perm/user/enable.do?userId={sid_user}&state=LOCK" target="ajaxTodo" title="你确定要锁定此帐户"><span>锁定</span></a></li>
            <li id="state_activity_btn" style="display:none;"><a class="edit" href="perm/user/enable.do?userId={sid_user}&state=ACTIVITY" target="ajaxTodo" title="你确定要解锁此帐户"><span>解锁</span></a></li>
            <li><a href="perm/user/beforetransfer.do?fromUserId={sid_user}" class="edit" target="dialog" fresh="true" mask="true" rel="permtransfer" width="392" height="546"><span>目录权限转移</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="105">
        <thead>
            <tr>
                <th align="center" width="5%">用户ID</th>
                <th align="center" width="5%">真实姓名</th>
                <th align="center" width="7%">登录名</th>
                <th align="center" width="5%">状态</th>
                <th align="left" width="20%">角色</th>
                <th align="center" width="7%">类型</th>
                <th align="left" width="20%">描述</th>
            </tr>
        </thead>
        <tbody>
            <#if pageCtx??>
            <#list pageCtx.itemList as user>
                <tr target="sid_user" rel="${user.userId}" state="${user.state!}" onClick="state(this)" style="<#if user.state?string == 'LOCK'>background-color:#FF0033</#if>">
                    <td>${user.userId!}</td>
                    <td>${user.realName!}</td>
                    <td>${user.loginName!}</td>
                    <td>${(user.state.label)!}</td>
                    <td>
                        <#if user.roles?? && user.roles?size gt 0>
                            <#list user.roles as role>
                                ${role.label!}<#if role_has_next> , </#if>
                            </#list>
                        <#else>未分配角色</#if>
                    </td>
                    <td>${(user.userType.label)!}</td>
                    <td>${user.description!}</td>
                </tr>
            </#list>
           </#if>
        </tbody>
    </table>
    <div class="panelBar">
        <@p.page pageCtx=pageCtx />
    </div>
</div>
<script type="text/javascript">
    function state(tr) {
        var $tr = $(tr);
        var state_val = $tr.attr("state");
        if(state_val == "LOCK") {
            $("#state_activity_btn").show();
            $("#state_lock_btn").hide();
        } else {
            $("#state_activity_btn").hide();
            $("#state_lock_btn").show();
        }
        
    }
</script>