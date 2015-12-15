<form method="post" action="attend/leave/save.do" modelAttribute="leave" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" novalidate="novalidate">
  <div class="pageContent" layoutH="35">
        <div class="pageFormContent">
        <#if leave.leaveId??>
           <input type="hidden" name="leaveId" value="${leave.leaveId!}" />
        </#if>    
            <p>
              <label for="">申请人：</label> 
              <input type="type" name="applyName" readonly="true" value="<#if leave.leaveId??>${leave.applyName!}<#else>${applyName!}</#if>" />
              <input type="hidden" name="applyId" readonly="true" value="${leave.applyId!}" />
              <input type="hidden" name="rtxUid" id="rtxUid" value="${(leave.rtxUid)!}"/>
              <#--<@p.choose_user id="userview.id" name="dwz.userview.userName" idValue="${(leaveId)!}" nameValue="${(leaveName)!}"/>-->
            </p>
                <p>
            <label for="">类 别：</label>
                <select name="attendLeaveEnum" id="attendLeaveEnum" class="combox required">
                <option value="">请选择</option>
                <#if leaveEnum??>
                <#list leaveEnum?values as s>
	                     <option value="${s!}" <#if leave.attendLeaveEnum?? &&leave.attendLeaveEnum== s> selected </#if> >${s.label!}</option>         
                  </#list>
               </#if>
                </select>
            </p> 
           <p>
                <label for="">开始日期：</label>            
                 <input type="text" name="leaveStartDate" id="leaveStartDate"    class="date textInput readonly valid"  format="yyyy-MM-dd"
   value="<#if leave.leaveStartTime??>${leave.leaveStartTime?string("yyyy-MM-dd")!}</#if>" /> 
                 
                 <select name=sTime>
                 <option value="8:30">8:30</option>
                 <option value="12:30">14:00</option>
                 </select>        
           </p>      
          <p>
          <label for="">结束日期：</label>                
                <input type="text" name="leaveEndDate" id="leaveEndDate"  class="date textInput readonly valid"    format="yyyy-MM-dd"   value="<#if leave.leaveEndTime??>${leave.leaveEndTime?string("yyyy-MM-dd")!}</#if>" />            
         		&nbsp;&nbsp;<select name=eTime>
                 <option value="12:30">12:30</option>
                 <option value="18:00">18:00</option>
                 </select>  
          </p>
         <p>
          <label for="">请假天数：</label>  
          <input type="text"  name="leaveNum"  class=" required number" width="25"  id="leaveNum"  value="${leave.leaveNum!}" >
            （请输入数字）
         </p>
         <p>
         <label for="">提交主管：</label>      
         <select name="chargeId" id="chargeId" class="combox required">
                <option value="">请选择</option>
                <#if usermap??>
                <#list usermap?keys as mykey>
	                     <option value="${mykey!}"  <#if leave.chargeId?? &&leave.chargeId?string==mykey> selected </#if>>  ${usermap[mykey]!}</option>         
                  </#list>
                 </#if>
           </select>
         </p>  
       </div>
        <div class="tabs" currentIndex="0" eventType="click">
            <div class="tabsHeader">
                <div class="tabsHeaderContent">
                    <ul>
                        <li><a href="javascript:;"><span>请假事由</span></a></li>
                    </ul>
                </div>
            </div>
            <div class="tabsContent" style="height:318px">
                <div>
                    <textarea class=" "  style="width:90%;height:260px"   id="description"   name="description" placeholder="">${leave.description!}</textarea>
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