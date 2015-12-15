<#macro page pageCtx rel="">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20"  <#if pageCtx.pageBean.pageSize==20>selected</#if>>20</option>
				<option value="50"  <#if pageCtx.pageBean.pageSize==50>selected</#if>>50</option>
				<option value="100" <#if pageCtx.pageBean.pageSize==100>selected</#if>>100</option>
				<option value="200" <#if pageCtx.pageBean.pageSize==200>selected</#if>>200</option>
			</select>
			<span>条，共${pageCtx.pageBean.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageCtx.pageBean.totalCount}" numPerPage="${pageCtx.pageBean.pageSize}" pageNumShown="10" currentPage="${pageCtx.pageBean.currentPage}" <#if rel!="">rel="${rel}"</#if>></div>
</#macro> 
<#macro pageDialog pageCtx rel="">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="dialogPageBreak({numPerPage:this.value})">
				<option value="20" <#if pageCtx.pageBean.pageSize==20>selected</#if>>20</option>
				<option value="50" <#if pageCtx.pageBean.pageSize==50>selected</#if>>50</option>
				<option value="100" <#if pageCtx.pageBean.pageSize==100>selected</#if>>100</option>
				<option value="200" <#if pageCtx.pageBean.pageSize==200>selected</#if>>200</option>
			</select>
			<span>条，共${pageCtx.pageBean.totalCount}条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${pageCtx.pageBean.totalCount}" numPerPage="${pageCtx.pageBean.pageSize}" pageNumShown="10" currentPage="${pageCtx.pageBean.currentPage}" <#if rel!="">rel="${rel}"</#if>></div>
</#macro> 
<#macro order field>
	orderField="${field}" <#if orderField??&orderField==field>class="${orderDirection}"</#if>
</#macro>
<#macro menu menu>
	<#if menu??>
		<#if menu.child??&menu.child?size gt 0>
		    <li>
		    	<#if menu.url??&menu.url!="/">
		    		<a href="${menu.url}" target="navTab" rel="main">${menu.menuName}</a>
		    	<#else>
		    		<a>${menu.menuName}</a>
		    	</#if>
				<ul>
					<#list menu.child as m>
						<@p.menu menu=m />
					</#list>
				</ul>
			</li>
		<#else>
			<li><a href="${menu.url}" target="navTab" rel="main">${menu.menuName}</a></li>
		</#if>
	</#if>
</#macro>

<#--循环-->
<#macro repeat count> 
  <#list 0..count as x> 
    <#nested x, x+1, x<count> 
  </#list> 
</#macro> 

<#--递归菜单下拉框-->
<#macro menu_dropdown menuTreeList selected=-1 deepth=0 >
	<#assign sel = selected> 
	<#if menuTreeList??>
		<#list menuTreeList as m>
			<option value="${m.menuId}" <#if m.menuId=selected>selected</#if>>		
				<@repeat count=deepth>
  					&nbsp;&nbsp;
				</@repeat>
				|--${m.menuName}
			</option>
			<#if m.child?size gt 0>
				<#assign d = deepth+1> 
				<@menu_dropdown menuTreeList=m.child selected=sel deepth=d/>
			</#if>
		</#list>
	</#if>
</#macro>
<#macro year_dropdown selected=2012 current=2012>
	<option value="${current + 1}" <#if selected?int=(current?int + 1)>selected</#if>>${current + 1}</option>
	<option value="${current + 2}" <#if selected?int=(current?int + 2)>selected</#if>>${current + 2}</option>
	<option value="${current + 3}" <#if selected?int=(current?int + 3)>selected</#if>>${current + 3}</option>
</#macro>

<#macro mrms_catalog_tree catalogViewTree>
    <#if catalogViewTree??>
        <#list catalogViewTree as cv>
            <li><a href="course/course/getcatalog.do?nodeId=${cv.catalogId}" rel="cataloglistbox" target="ajax">${cv.catalogName}</a>
                <#if cv.children?? && cv.children?size gt 0>
                    <ul>
                        <@p.mrms_catalog_tree catalogViewTree = cv.children/>
                    </ul>
                </#if>
            </li>
        </#list>
    </#if>
</#macro>
<#macro upload id name folder="" scriptData="" attributes="" fileQueue="" onComplete="" onAllComplete="" fileExt="" fileDesc="">
		<input id="${id}" type="file" name="${name}" ${attributes}
			uploader="<@s.url '/resources/js/dwz-ria/uploadify/scripts/uploadify.swf'/>"
			cancelImg="<@s.url '/resources/js/dwz-ria/uploadify/cancel.png'/>" 
			script="upload.do" 
			folder="${folder}"								
			fileQueue="${fileQueue}"
			scriptData="${scriptData}"
			onComplete="${onComplete}"
			onAllComplete="${onAllComplete}"
			fileExt="${fileExt}"
			fileDesc="${fileDesc}" />
</#macro>

<#macro uploadImg id name folder="" scriptData="" attributes="" fileExt="*.jpg;*.jpeg;*.gif;*.png" fileDesc="图像文件(*.jpg;*.jpeg;*.gif;*.png)" fileQueue="" onComplete="" onAllComplete="">
		<input id="${id}" type="file" name="${name}" ${attributes}
			uploader="<@s.url '/resources/js/dwz-ria/uploadify/scripts/uploadify.swf'/>"
			cancelImg="<@s.url '/resources/js/dwz-ria/uploadify/cancel.png'/>" 
			buttonImg="<@s.url '/resources/images/aqua1.jpg' />" 
			script="upload.do" 
			wmode="transparent"
			folder="${folder}"								
			fileQueue="${fileQueue}"
			scriptData="${scriptData}"
			onComplete="${onComplete}"
			onAllComplete="${onAllComplete}"
			fileExt="${fileExt}"
			fileDesc="${fileDesc}" />
</#macro>

<#macro category_tree moduleId="">
    <a class="btnLook" href="category/ctreeList.do?moduleId=${moduleId!}" lookupGroup="categoryview" >查找带回</a>
</#macro>
<#--转换图片路径-->
<#macro imgUrl relativeUrl="files/nopic.gif" defaultImgUrl="files/nopic.gif">
	<#compress>
	<#if relativeUrl??&relativeUrl?trim?length gt 0>
		<#assign relUrl =relativeUrl>
	<#else>
		<#assign relUrl = defaultImgUrl>
	</#if>${filesroot}/${relUrl}
	</#compress>
</#macro>
<#--
content 所显示的内容
contentLength 需要截取的字数
-->
<#macro contentSubstr content contentLength>
<#if content??>
<#if (content?length<=contentLength)>${content!}<#else>${content[0..contentLength-1]!}...</#if>
</#if>
</#macro>

<#macro file_size size>
    <#local kb = 1024/>
    <#local mb = (1024 * 1024)/>
    <#local gb = (1024 * 1024 * 1024)/>
    <#local tb = (1024 * 1024 * 1024 * 1024)/>
    <#if size lt kb>
        <#local formatstr = size?string("0.##") + " Bytes">
    <#elseif size gt kb && size lt mb>
        <#local formatstr = (size / kb)?string("0.##") + " KB">
    <#elseif size gt mb && size lt gb>
        <#local formatstr = (size / mb)?string("0.##") + " MB">
    <#elseif size gt gb && size lt tb>
        <#local formatstr = (size / gb)?string("0.##") + " GB">
    <#else>
        <#local formatstr = (size / tb)?string("0.##") + " TB">
    </#if>
    ${formatstr?trim}
</#macro>
<#macro hms seconds>
    <#--
	<#local hours=(seconds / 3600)?int/>
    <#local seconds=seconds - (3600 * hours) />
    -->
	<#local minutes=(seconds / 60)?int />
	<#local seconds=seconds - 60 * minutes />
	<#--${hours?string("00")}:-->${minutes?string("00")}'${seconds?string("00")}"
</#macro>
<#macro choose_catalog id="orgview.id" name="orgview.orgName" lookupGroup="orgview" nameValue="" idValue="" class="">
<input type="hidden" id="${id!}" name="${id!}" value="${idValue!}" class="${class!}"/>
<input type="text" name="dwz.${name!}" id="dwz.${name!}" class="textInput ${class!}" readonly value="${nameValue!}" style="width:200px"/>
<a id="parentidedit" href="catalog/dialog/open.do" width="874" height="545" rel="choicecatalogdialog" fresh=true mask=true  class="btnLook" lookupGroup="${lookupGroup}" title="选择父目录"></a>
</#macro>

<#macro choose_cataid id="orgview.id" name="orgview.orgName" lookupGroup="orgview" nameValue="" idValue="" class="">
<input type="hidden" id="${id!}" name="${id!}" value="${idValue!}"  class="catalogId" class="${class!}"/>
<input type="text" name="dwz.${name!}" id="dwz.${name!}" class="catalog"  value="${nameValue!}" style="width:150px"  />
<a id="parentidedit" href="catalog/dialog/open.do" width="874" height="545" rel="choicecatalogdialog" fresh=true mask=true  class="btnLook" lookupGroup="${lookupGroup}" title="选择父目录"></a>
</#macro>

<#macro choose_user id="orgview.id" name="orgview.orgName" nameValue="" idValue="" class="" lookupGroup="userview">
<input type="hidden" name="${id!}" id="${id!}" value="${(idValue)!}" class="${class!}"/>
<input type="text" name="${name!}" id="${name!}" value="${nameValue!}" readonly class="${class!}"/>
<a id="useridedit" href="perm/user/dialog/open.do" width="874" height="545" rel="userdialog" fresh=true mask=true  class="btnLook" lookupGroup="${lookupGroup!}" title="选择责任人"></a>
</#macro>

<#macro choose_tapedroom id="tapedroomview.id" name="tapedroomview.roomName" nameValue="" idValue="" class="" lookupGroup="tapedroomview" sid="">
<input type="hidden" name="${id!}" id="${id!}" value="${(idValue)!}" class="${class!}"/>
<input type="text" name="dwz.${name!}" id="dwz.${name!}" value="${nameValue!}" readonly class="${class!}"/>
<a id="tapedroomidedit" href="record/choice/list.do<#if sid != "">?${sid!}</#if>" width="874" height="545" rel="tapedroomdialog" fresh=true mask=true  class="btnLook" lookupGroup="${lookupGroup!}" title="选择责任人"></a>
</#macro>


<#macro player src mrmsroot width="720" height="440" id="player" mode="transparent" allowScriptAccess="always" allowFullScreen="true" quality="high">
<div class="lt flash_lightoff" id="flashContent" style="width: ${width};height: ${height}">
    <OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
    codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10.0.2"
    WIDTH="${width}" HEIGHT="${height}" id="${id}">
        <PARAM NAME=movie VALUE="${src}">
        <PARAM NAME=quality VALUE="${quality}">
        <PARAM NAME=allowScriptAccess VALUE="${allowScriptAccess}">
        <PARAM NAME=allowFullScreen VALUE="${allowFullScreen}">
        <PARAM NAME=mode VALUE="${mode}">
        <PARAM name=flashvars VALUE="baseUrl=${mrmsroot}">
        <EMBED src="${src}" quality="${quality}" WIDTH="${width}" HEIGHT="${height}" flashvars="baseUrl=${mrmsroot}" 
        allowScriptAccess="${allowScriptAccess}" allowFullScreen="${allowFullScreen}" mode="${mode}"
        NAME="${id}" ALIGN="middle" TYPE="application/x-shockwave-flash"
        PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer">
        </EMBED>
    </OBJECT> 
</div>
</#macro>

