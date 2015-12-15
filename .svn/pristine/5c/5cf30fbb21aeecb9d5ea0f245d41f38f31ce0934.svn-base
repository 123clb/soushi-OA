<form method="post" action="device/setState.do"   modelAttribute="device" class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);">
<div class="pageContent" >
    <div class="pageFormContent" layoutH="55">
      <input type="hidden"    name="deviceId" value="${deviceId!}"/>
        <p>
            <label for="">状 态：</label>   
            <select   name="deviceState"  id="deviceState"   >
               <option  value="0">请选择</option>
               <option  value="IDLE">闲置</option>
               <option  value="SCRAP">报废</option>
           </select>                  
        </p>
        <p>
           <label for="">设置人：</label>
            <input type="text"    name="deviceId" value="${sName!}"/>
             <input type="hidden"    name="assignPersonId" value="${aPid!}"/>
        </p>
        <div id="isScrap">
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
			<br>
			<div id="fileQueue" class="fileQueue1"></div>
		    <div id="picShow"></div> 
		    <input type="hidden"  id="scrapPic"   name="scrapPic" value=""/>
	</div>  
    <p>
            <label for="">描述：</label>
            <textarea name="description" id="" style="width:50%;height:64px"></textarea>
     </p>
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
<script>
   $(function(){      
     $("#isScrap").hide(); 
     $("#deviceState").live("change",function(){
      var state=$(this).children('option:selected').val(); 
      
      if (state=="SCRAP")
      {  
            $("#isScrap").show();  
      }
      else
       {
          
       	   $("#isScrap").hide();  
       }     
     })
   })

  function uploadDone(event, queueId, fileObj, response, data)
				{	    
						  if(data!="")
						  {
						  	 var tUrl="<img src=resources/upload/"+response+" width=200; height=100 >";
						  	 $("#fileQueue").hide();
						     $("#picShow").html(tUrl);
						     $("#scrapPic").val(response);
						  }
				}
			</script>	
