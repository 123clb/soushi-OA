<div class="pageHeader">
	<form id="pagerForm" method="post" action="attend/rule/list.do"  onsubmit="return navTabSearch(this);">
	 <input type="hidden" name="pageNum" value="${pageCtx.pageBean.currentPage}" />
	 <input type="hidden" name="numPerPage" value="${pageCtx.pageBean.pageSize}" />
	 <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                    <input type="text" name="loginName" id="" alt="登录名" value="" />
                    </td>
                    <td>
                    <input type="text" name="realName" id="" alt="真实姓名" value="" />
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
            <li><a class="add" width="460" height="550" fresh=true mask=true href="attend/rule/rulesedit.do" rel="ruledialog" target="dialog"><span>添加</span></a></li>
            <li><a class="edit" width="460" height="560" fresh=true mask=true href="attend/rule/rulesedit.do?ruleId={rid}" rel="modifyruledialog" target="dialog"><span>修改</span></a></li>
            <li><a class="delete" fresh=true href="perm/user/delete.do?userId={sid_user}" target="ajaxTodo" title="确定要删除目录及其子目录吗?" callback="treeReloadNavTabAjaxDone"><span>删除</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="105">
        <thead>
            <tr>
                <th align="center" width="5%">ID</th>
                <th align="center" width="5%">规则名称</th>
                <th align="center" width="7%">上班时间</th>
                <th align="center" width="5%">下班时间</th>               
                <th align="left" width="20%">描述</th>
            </tr>
        </thead>
        <tbody>
            <#if pageCtx??>
            <#list pageCtx.itemList as rule>
                <tr target="rid" rel="${rule.ruleId}"">
                    <td>${rule.ruleId!}</td>
                    <td>${rule.ruleName!}</td>
                    <td>${rule.morningTime!}</td>
                    <td>${rule.afterTime!}</td>
                    <td>${rule.description!}</td>
                </tr>
            </#list>
           </#if>
        </tbody>
    </table>
    <div class="panelBar">
        <@p.page pageCtx=pageCtx />
    </div>
</div>