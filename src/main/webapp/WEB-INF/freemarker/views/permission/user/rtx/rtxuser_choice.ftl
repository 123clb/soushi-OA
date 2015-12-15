<div class="pageContent">
    <table class="table" width="100%" layoutH="75">
        <thead>
            <tr>
                <th align="center" width="5%">选择</th>
                <th align="center" width="5%">用户ID</th>
                <th align="center" width="5%">登录名</th>
                <th align="center" width="5%">真实姓名</th>
                <th align="center" width="10%">部门</th>
                <th align="center" width="5%">性别</th>
            </tr>
        </thead>
        <tbody>
            <#if rtxUserList??>
            <#assign department=""/>
            <#list rtxUserList as rtxUser>
                <#if rtxUser.deptName != department>
                <tr style="background:#339933;">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </#if>
                <tr target="sid_user" rel="${rtxUser.uid}">
                    <td>
                        <a class="btnSelect" href="javascript:$.bringBack({uid:'${(rtxUser.uid)!}', rtxno:'${(rtxUser.rtxno)!}', rtxName:'${(rtxUser.name)!}', deptId:'${(rtxUser.deptId)!}', department:'${(rtxUser.deptName)!}'})" title="请选择责任人">选择</a>
                    </td>
                    <td>${rtxUser.rtxno!}</td>
                    <td>${rtxUser.uid!}</td>
                    <td>${rtxUser.name!}</td>
                    <td>${(rtxUser.deptName)!}</td>
                    <td><#if rtxUser.gendar?? && rtxUser.gendar==0>男<#elseif rtxUser.gendar?? && rtxUser.gendar==1>女<#else>未知</#if></td>
                </tr>
                <#assign department = rtxUser.deptName/>
            </#list>
            </#if>
        </tbody>
    </table>
</div>