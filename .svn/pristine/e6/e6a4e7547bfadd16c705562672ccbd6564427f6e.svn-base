<form method="post" action="perm/user/save.do" modelAttribute="user" class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);">
<div class="pageContent" >
    <div class="pageFormContent">
        <#if user.userId??>
            <input type="hidden" name="userId" value="${user.userId!}"/>
            <input type="hidden" name="state" value="${user.state!}"/>
            <input type="hidden" name="createDate" value="${(user.createDate?string("yyyy-MM-dd hh:mm:ss"))!}"/>
        </#if>
        <p>
            <label for="">RTX用户：</label>
            <input type="hidden" name="dwz.rtxuserview.uid" id="dwz.rtxuserview.uid" value="${(user.rtxUid)!}" class=""/>
            <input type="hidden" name="dwz.rtxuserview.rtxno" id="dwz.rtxuserview.rtxno" value="${(user.rtxno)!}" class=""/>
            <input type="text" name="dwz.rtxuserview.rtxName" id="dwz.rtxuserview.rtxName" value="${user.rtxName!}" readonly class=""/>
            <a href="perm/user/rtx/userchoice.do" width="874" height="545" rel="rtxuserdialog"  class="btnLook" lookupGroup="rtxuserview" title="选择RTX用户"></a>
        </p>
        <p>
            <label for="">RTX部门：</label>
            <input type="hidden" name="dwz.rtxuserview.deptId" id="dwz.rtxuserview.departId" value="${(user.deptId)!}"/>
            <input type="text" name="dwz.rtxuserview.department" id="dwz.rtxuserview.department" readonly="" value="${(user.deptName)!}"/>
        </p>         
        <p>
            <label for="">登录名：</label>
            <input type="text" name="loginName" id="" class="required" value="${user.loginName!}" />
        </p>
        <p>
            <label for="">真实姓名：</label>
            <input type="text" name="realName" id="" class="" value="${user.realName!}" />
        </p>
        <p>
            <label for="">密码：</label>
            <input type="password" name="password" id="" class="" value="" />
        </p>
        <p>
            <label for="">发送弹窗信息：</label>
            <input type="checkbox" name="isSend"   <#if user.isSend>checked</#if> />
        </p>
        <p>
           <label for="">用户类型：</label>
            <select name="userType" id="" class="required combox">
                <option value="0">请选择用户类型</option>
                <#if userTypeEnum??>
                    <#list userTypeEnum?keys as key>
                        <option value="${userTypeEnum[key]}" <#if user.userType?? && userTypeEnum[key] == user.userType>selected</#if>>${userTypeEnum[key].label}</option>
                    </#list>
                </#if>
            </select>
        </p>        
         <p>
            <label for="">考勤时间：</label>
            <select name="ruleId" id="" >
                <option value="0">请选择考勤时间</option>
                <#if ruleMap??>
                    <#list ruleMap?keys as key>
                        <option value="${key!}" <#if user.ruleId?? && key==user.ruleId?string>selected</#if>>${ruleMap[key]!}</option>
                    </#list>
                </#if>
            </select>
        </p>
    </div>
    <div class="pageFormContent">
        <label for="">角色：</label>
        <#if roleList??>
            <#list roleList as role>
                <label for="${(role.roleId)!}"><input type="checkbox" name="roleIds" id="${(role.roleId)!}" <#if grantRoleIdList?? && grantRoleIdList?seq_contains(role.roleId)>checked</#if> value="${role.roleId}"/>${role.roleName}</label>
            </#list>
        </#if>
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
                <textarea class=" "  style="width:90%;height:105px;"  name="description"  >${user.description!}</textarea>
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
