<form method="post" action="device/save.do" modelAttribute="device" class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);">
  <style>
  .fileQueue1 {
	width: 300px;
	height: 100px;
	overflow: auto;
	border: 1px solid #E5E5E5;
	margin-bottom: 10px;
  </style>
  
  <div class="pageContent" layoutH="35">
        <div class="pageFormContent">
        <#if device.deviceId??>
           <input type="hidden" name="deviceId" value="${device.deviceId!}" />
        </#if>  
           <script>
			function uploadDone(event, queueId, fileObj, response, data)
				{	    
						  if(data!="")
						  {
						  	 var tUrl="<img src=resources/upload/"+response+" width=200; height=100 >";
						  	 $("#fileQueue").hide();
						     $("#picShow").html(tUrl);
						     $("#devicePic").val(response);
						  }
				}
			</script>	
            <p>
                <label for="">设备编号：</label>               
                <input type="text" name="sNumber" id="sNumber"    value="${device.sNumber!}" />                 
            </p>          
            <p>
              <label for="">设备名称：</label> 
               <input type="text" name="deviceName" id="deviceName"  class="required"   value="${device.deviceName!}" />
            </p>
            <p>
            <label for="">品牌：</label>
                <input type="text" name="brand"  id="brand"    value="${device.brand!}" />
             </p> 
             <p>
                <label for="">采购价格：</label>               
                <input type="text" name="devicePrice" id="devicePrice"  class="required"  value="${device.devicePrice!}" />                 
            </p> 
              <p>
                <label for="">型号：</label>               
                <input type="text" name="dNumber" id="dNumber"    value="${device.dNumber!}" />             
            </p>   
            <p>
                <label for="">类 别：</label>
                <select name="categoryId" id="" class="combox required">
                <option value="">请选择</option>
                <#if categotymap??>
                <#list categotymap?keys as mykey>
	                     <option value="${mykey!}" <#if device.categoryId?? &&device.categoryId?string== mykey> selected </#if> >${categotymap[mykey]!}</option>         
                  </#list>
                 </#if>
                </select>
              </p> 
             <p>
                <label for="">供应商：</label>            
                <input type="hidden" name="orgLookup1.id"  id="orgLookup1.id" value="${device.supplierId!}">              
                <input type="text"  class="required textInput valid"  id="dwz.orgLookup1.syName"    name="dwz.orgLookup1.syName"   value="<#if device.deviceId??> ${syMap[device.supplierID?string]!}</#if>"  lookupgroup="orgLookup" >
                <a class="btnLook"  href="device/data/supplylist.do"  lookupgroup="orgLookup1" rel="sydialog" fresh="true" mask="true">选择供应商</a>   
            </p>      
                     
            <p>
                <label for="">采购时间：</label>               
                <input type="text" name="purchaseDate" id="purchaseDate"  format="yyyy-MM-dd" class="date"  value="${device.purchaseDate!}" />                 
            </p>             
           <#--<p>
                <label for="">使用状态：</label>
                <input type="text" name="deviceState" id="deviceState" class="required"  value="${device.deviceState!}" />
           	    <select name="tapedRoomType" id="" class="combox required">
                    <option value="">请选择</option>
                    <#if tapedRoomTypeEnum??>
                        <#list tapedRoomTypeEnum?values as taped>
                            <option value="${taped}" <#if tapedRoom.tapedRoomType?? && tapedRoom.tapedRoomType == taped>selected</#if>>${taped.label}</option>
                        </#list>
                    </#if>
                </select>
           </p>-->
          <p>
            <label for="">使用人：</label>
            <input type="hidden"  name="orgLookup.id"    id="orgLookup.id"  value="${deviceDetail.usePersonId!}">                          
            <input type="text"     readonly="readonly"     id="dwz.orgLookup.dtName"    name="dwz.orgLookup.dtName"   value="<#if deviceDetail.detailId?? >${deviceDetail.usePersonName!}</#if>"   lookupgroup="orgLookup" >  
            <a class="btnLook"    href="device/user/list.do"      lookupgroup="orgLookup"      rel="dtdialog"   fresh="true"  mask="true">选择</a>              
          
          </p>
          <p>
                <label for="">监管人：</label>
                <select name="supervisePerson" id="" class="combox required">               
                <option value="">请选择</option>
                <#if usermap??>
                <#list usermap?keys as mykey>
	              <option value="${mykey!}" <#if device.supervisePerson?? && device.supervisePerson?string==mykey> selected </#if>>${usermap[mykey]!}</option>      
                  </#list>
                 </#if>
                </select>
            </p> 
           <p>
                <label for="">录入人：</label>
                 <#if device.deviceId??>
                 <input type="text" name="createName" readonly="true" id="createName" class="textInput readonly"  value="${usermap[device.createPerson?string]!}" />
                 <input type="hidden" name="createPerson" id="createPerson" class="required"  value="${device.createPerson!}" />
                 <#else>
                <input type="text" name="createName" readonly="true" id="createName" class="textInput readonly"  value="${createName!}" />
                <input type="hidden" name="createPerson" id="createPerson" class="required"  value="${userId!}" />
                </#if>
          </p>          
	<div id="isDate">
	 <label >选择图片：</label> 
		<input id="testFileInput" type="file" name="image"   
			uploader="resources/js/dwz-ria/uploadify/scripts/uploadify.swf"
			cancelImg="resources/js/dwz-ria/uploadify/cancel.png" 
			script="record/upload.do;JSESSIONID=66c9288b-9c6b-4784-9583-4efc65004497" 
			folder="/upload"								
			fileQueue="fileQueue"
			scriptData="{serverName:'ad', isRename:'true'}"
			onComplete="uploadDone"
			fileExt="*.jpg;*.gif;"
			fileDesc="*.jpg;*.gif;" /> 	
	</div>

      <label for=""></label>  
        <#if device.deviceId??>
        <div id="picShow"><img src=resources/upload/${device.devicePic!}  width=200; height=100 ></div> 
        <#else>
       	<div id="picShow"></div> 
       	<div id="fileQueue" class="fileQueue1"></div>
       </#if>
       <input type="hidden" name="devicePic"  id="devicePic" value="${device.devicePic!}">  
       </div>
        <div class="tabs" currentIndex="0" eventType="click">
            <div class="tabsHeader">
                <div class="tabsHeaderContent">
                    <ul>
                        <li><a href="javascript:;"><span>附件</span></a></li>
                    </ul>
                </div>
            </div>
            <div class="tabsContent" style="height:115px">
                <div>
                    <textarea class=" "  style="width:90%;height:80px"   id="attaChment"  name="attaChment" placeholder="">${device.attaChment!}</textarea>
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
