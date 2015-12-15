<form  method="post" action="device/data/supplylist.do" onsubmit="return dwzSearch(this, 'dialog');">
	<input type="hidden" name="pageNum"    value="" />
	<input type="hidden" name="numPerPage" value="" />
	<input type="hidden" name="orderField" value="" />
	<input type="hidden" name="orderDirection" value="" />
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>部门名称:</label>
				<input class="textInput" name="orgName" value="" type="text">
			</li>	  
			<li>
				<label>部门编号:</label>
				<input class="textInput" name="orgNum" value="" type="text">
			</li>
			<li>
				<label>部门经理:</label>
				<input class="textInput" name="fullName" value="" type="text">
			</li>
				<li>
				<label>上级部门:</label>
				<input class="textInput" name="parentOrg.orgName" value="" type="text">
			</li> 
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent">
	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
			    <th orderfield="supplierID">ID</th>
				<th orderfield="supplierName">供应商名称</th>
				<th orderfield="supplierPhone">联系电话</th>
				<th orderfield="supplierAddress">联系地址</th>
				<th width="80">查找</th>
			</tr>
		</thead>
		<tbody>
			<tr>
			
			<#if pageCtx??>
                <#list pageCtx.itemList as dv> 
                <tr target="sid_device"  rel="${dv.supplierId!}" colspan="1">
                   <td>${dv.supplierId!}</td>
                   <td>${dv.supplierName!}</td>
                   <td>${dv.supplierPhone!}</td>
                   <td>${dv.supplierAddress!}</td>
                   <td><a class="btnSelect" href="javascript:$.bringBack({id:'${(dv.supplierId)!}', syName:'${(dv.supplierName)!}(${dv.supplierId})'})" title="请选择录">选择</a>
                        </td>
                </#list>
            </#if>
		</tbody>
	</table>

	<div class="panelBar">
        <@p.page pageCtx=pageCtx />
    </div>
</div>