<div style="float:left; margin:10px">
	<div id="fileQueue" class="fileQueue"></div>
			<p>
			<br><br><br>			
			<div id="combox_endDate" class="select">请先选择要生成
				<select size="1" id="yearDate" name="yearDate"  class="required">
			          <option selected="" value="0">年</option>
			            <option  value="2013">2013</option>
			            <option value="2014">2014</option>
                        <option value="2015">2015</option>
                        <option value="2016">2016</option>
                        <option value="2017">2017</option>
                        <option value="2018">2018</option>
                        <option value="2019">2019</option>
                        <option value="2020">2020</option>
                        <option value="2021">2021</option>
			  </select>			 			
			<select size="1" id="monthDate" name="monthDate" class="required"> 
	            <option selected="" value="0">月  份</option>
	            <#list 1..12 as m>
	            <option value="${m}">${m}</option>
	            </#list>
             </select>    
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="idown"> </span>  
             </div>
			</p>
		 <br><br><br>
<p>
	<div id="isDate">	
		<#if IsCreate??>
		<#if IsCreate>
		<label>点击开始同步考勤记录</label>
		<input type="button" size="50" value="同步考勤记录"  onclick="uploadDone(1)"></div>
		<#else>
		<label>点击开始生成统计报表</label>
		<input type="button" size="50" value="生成统计报表" id="but1"  onclick="uploadDone(0)">	</div>
		</#if>
		</#if>
</p>
<br>
<#if !IsCreate>
 <p>
 <div>
    <label>打开关闭RTX自动提醒功能</label>
 	<input type="button"  id="msgbtn"   size="50"  value="<#if IsSend>关      闭<#else> 打      开</#if>"  onclick="setMsg()">
 	<input type="hidden" id="mvalue"  value="<#if IsSend>1<#else>0</#if>">
 	<br><br> 	 
 	 	<label> <a class="button" href="attend/info/sName.do" target="ajaxTodo" mark=true fresh=true><span>更新考勤姓名 </span></a></label></div>
</#if>
</p>
</div>

<script>
var md=0;
var yd=0;
  $(function(){
    $("#isDate").hide();   
     $("#monthDate").change(function(){
      if($("#monthDate").val()!=0)
       {
       	 $("#isDate").show();
       	 md=$("#monthDate").val();
       }
       else
       {
          $("#isDate").hide();
       }        
     })
     
    $("#yearDate").change(function(){
         yd=$("#yearDate").val();
     })
  })
function uploadDone(flag)
	{	   
		  if($("#yearDate").val()==0)
	       {
		      alert ("请先选择年份！");
		      return false;  
		   }
		   
		   if($("#monthDate").val()==0)
	       {
		      alert ("请先选择你要生成的月份！");
		      return false;  
		   }
		   if (flag==0)
		   {
		   	  $.get("record/createExcel.do",{md:md,yd:yd},function(result)
		   	  {
				  if(result!="")
				  {
				  	 alert("生成成功！");
				  	 var tUrl="<a class=icon  href='"+result+"'  target='_blank'><strong>导出EXCEL</strong></a>";
				     $("#idown").html(tUrl);
				  }		
			 });
		   }
		   
		  if (flag==1) 
		  {		  
		  	 $.get("record/realcheck.do",{md:md,yd:yd},function(result){
				  if(result!="")
				  {
				  	 alert("数据同步成功！");
				  }		
			 });
		  
		  }
		 
	}
	function confirm(fileUrl){
		if(fileUrl != ""){
			$("#imgUrl", window.parent.document).val(fileUrl);
			var text = "<img src='"+fileUrl+"' width='120px' height='80px' border='0'/>";
			$("#imgSpan", window.parent.document).html(text);
		}
		
	}
	
	function setMsg()
	{
	    var n=$("#mvalue").val();
		$.get("attend/setIsMsg.do",{cgn:n},function(result){
				
				  if(result!=null)
				  {
				     if (result==0)
				  	 {
				  	    $("#msgbtn").val("打       开");
				  	    $("#mvalue").val(0);
				  	 }
				  	 if (result=="1")
				  	 {
				  	    $("#msgbtn").val("关       闭");
				  	    $("#mvalue").val(1);
				  	 }
				  	 alert("设置成功。");
				  }		
			 });
	}
	
</script>