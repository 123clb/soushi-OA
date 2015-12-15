<div class="pageHeader">
    <form id="pagerForm" method="post" action="log/list.do" onsubmit="return navTabSearch(this);" >
        <input type="hidden" name="pageNum" value="${pageCtx.pageBean.currentPage}" />
        <input type="hidden" name="numPerPage" value="${pageCtx.pageBean.pageSize}" />
        <input type="hidden" name="orderField" value="${orderField!}" />
        <input type="hidden" name="orderDirection" value="${orderDirection!}" />
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        <input type="text" name="userName" id="" value="${userName!}" alt="登录名称" size="7"/>
                    </td>
                    <td>
                        <input type="text" name="remoteIp" id="" value="${remoteIp!}" alt="远程IP" size="15"/>
                    </td>
                    <td>
                        <input type="text" name="entityId" id="" value="${entityId!}" alt="实例ID" size="5" title="具体一条记录的ID，就是列表最前面的数字"/>
                    </td>
                    <td>
                        <label for="" style="width:50px">实体对象:</label>
                        <select name="entity" class="combox" id="">
                            <option value="" <#if !entity??>selected</#if>>全部</option>
                            <#list entityEnum?values as v>
                                <option value="${v}" <#if entity?? && entity == v>selected</#if>>${v.label}</option>
                            </#list>
                        </select>
                    </td>
                    <td>
                        <label for="" size="5" style="width:25px">操作:</label>
                        <select name="operation" class="combox" id="">
                            <option value="" <#if !operation??>selected</#if>>全部</option>
                            <#list operationEnum?values as o>
                                <option value="${o}" <#if operation?? && operation == o>selected</#if>>${o.label}</option>
                            </#list>
                        </select>
                    </td>
                    <td>
                        <label for="" style="width:25px">状态:</label>
                        <select name="status" class="combox" id="">
                            <option value="" <#if !status??>selected</#if>>全部</option>
                            <#list statusEnum?values as s>
                                <option value="${s}" <#if status?? && status == s>selected</#if>>${s.label}</option>
                            </#list>
                        </select>
                    </td>
                    <td>
                        <label for="" style="width:50px">日期范围:</label>
                        <input type="text" name="startDate" readonly id="" class="date" value="${startDate!}"/> - <input type="text" readonly name="endDate" id="" class="date" value="${endDate!}"/>
                    </td>
                    <td><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></td>
                </tr>
            </table>
        </div>
    </form>
</div>
<div class="pageContent" >
    <table class="table" width="100%" layoutH="85" id="fvbox">
        <thead>
            <tr>
                <th width="5%">编号</th>
                <th width="5%" <@p.order field="userName"/>>用户</th>
                <th width="7%" <@p.order field="remoteIp"/>>远程IP</th>
                <th width="5%" <@p.order field="entity"/>>实体</th>
                <th width="5%" <@p.order field="entityId"/>>实体Id</th>
                <th width="5%" <@p.order field="operation"/>>操作</th>
                <th width="5%" <@p.order field="status"/>>状态</th>
                <th widht="7%" <@p.order field="createDate"/>>日期</th>
                <th width="">描述</th>
            </tr>
        </thead>
        <tbody>
            <#if pageCtx??>
                <#list pageCtx.itemList as log>
                    <tr>
                        <td>${(log.logId)!}</td>
                        <td>${(log.userName)!}</td>
                        <td>${(log.remoteIp)!}</td>
                        <td>${(log.entity.label)!}</td>
                        <td>${(log.entityId)!}</td>
                        <td>${(log.operation.label)!}</td>
                        <td>${(log.status.label)!}</td>
                        <td>${(log.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
                        <td>${(log.description)!}</td>
                    </tr>
                </#list>
            </#if>
        </tbody>
    </table>
    <div class="panelBar">
        <@p.page pageCtx=pageCtx />
    </div>
</div>