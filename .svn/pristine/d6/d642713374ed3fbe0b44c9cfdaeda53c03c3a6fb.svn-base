<form method="post" action="attend/leave/close.do?leaveId=${leave.leaveId}&f=${flag!}" modelAttribute="leave" class="pageForm required-validate" onsubmit="return validateCallback(this, treeReloadDialogAjaxDone);">

  <div class="pageContent" layoutH="35">
        <div class="pageFormContent">
        <#if leave.leaveId??>
           <input type="hidden" name="leaveId" value="${leave.leaveId!}" />
        </#if>    
            <p>
              <label for="">申请人：</label> 
              <#if leave.leaveId??>${leave.applyName!}<#else>${applyName!}</#if>
              <input type="hidden" name="applyId" readonly="true" value="${leave.applyId!}" />          
            </p>
           <p>
          <label for="">类 别：</label>
               ${leave.attendLeaveEnum.label!}                  
          </p> 
          <p>
             <label for="">请假时间：</label>            
              ${leave.leaveStartTime?string("yyyy-MM-dd HH:mm")!}--${leave.leaveEndTime?string("MM-dd HH:mm")!}      
          </p>
         <p>
           <label for="">请假天数：</label>  
          	${leave.leaveNum!} 天
         </p>
         <p>
          <label for="">请假事由：</label>  
          	${leave.description!} 
          </p>
          <p>
          <label for="">签字主管：</label>  
          ${charegeMap[leave.chargeId?string]!} 
          </p>
          <#if flag=="c">
          <p>
          <label for="">销假时间：</label>  
          ${currentTime!} 
          <input type="hidden"  id="closeTime"  name="closeTime"  value="${currentTime!} ">
          </p>
         </#if>
       </div>
        <#if flag!="c">
        <div class="tabs" currentIndex="0" eventType="click">
            <div class="tabsHeader">
                <div class="tabsHeaderContent">
                    <ul>
                        <li><a href="javascript:;"><span>备注</span></a></li>
                    </ul>
                </div>
            </div>
            <div class="tabsContent" style="height:105px"> 
                <div>
                    <textarea class=""  style="width:80%;height:90px"   id="remark"   name="remark" placeholder="如果请假信息中有特殊情况，请在此备注说明。"></textarea>
                </div>
            </div>
        </div>
       </#if>
</div>
<div class="formBar">
    <ul>
    <#if flag=="c">
   	<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确认</button></div></div></li>
   	<#else>	
   		<li><div class="buttonActive"><div class="buttonContent"><a href="attend/leave/sign.do?leaveId=${leave.leaveId}&f=${flag!}&g=y" target="ajaxTodo" style="line-height:24px">同意</a></div></div></li>
        <li><div class="buttonActive"><div class="buttonContent"><a href="attend/leave/sign.do?leaveId=${leave.leaveId}&f=${flag!}&g=n" target="ajaxTodo" style="line-height:24px">不同意</a></div></div></li> 		
 	</#if>
  </ul>
</div>
<script>
	$("a[target=ajaxTodo]").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
</script>
</form>