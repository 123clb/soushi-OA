<div class="pageContent">
    <table class="table" width="100%" layoutH="80">
        <thead>
            <tr>
                <th align="center" width="5%">选择</th>
                <th align="center" width="5%">用户ID</th>
                <th align="center" width="7%">登录名</th>
                <th align="center" width="5%">真实姓名</th>
                <th align="center" width="5%">状态</th>
                <th align="center" width="7%">类型</th>
                <th align="left" width="20%">描述</th>
            </tr>
        </thead>
        <tbody>
            <#if userList??>
            <#list userList as user>
                <tr target="sid_user" rel="${user.userId}">
                    <td>
                        <#if user.state == "ACTIVITY">
                            <a class="btnSelect" href="javascript:$.bringBack({id:'${(user.userId)!}', userName:'${(user.realName)!}'})" title="请选择责任人">选择</a>
                        </#if>
                    </td>
                    <td>${user.userId!}</td>
                    <td>${user.loginName!}</td>
                    <td>${user.realName!}</td>
                    <td>${(user.state.label)!}</td>
                    <td>${(user.userType.label)!}</td>
                    <td>${user.description!}</td>
                </tr>
            </#list>
            </#if>
        </tbody>
    </table>
</div>