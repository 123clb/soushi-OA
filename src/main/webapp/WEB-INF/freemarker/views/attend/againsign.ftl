<form method="post" action="attend/again/save.do" modelAttribute="attendAgain" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);" novalidate="novalidate">
  <div class="pageContent" layoutH="35">
        <div class="pageFormContent">
      <p>
              <label for="">补签人：</label> 
              <input type="type" name="againName" readonly="true" value="${againName!}"  />
              <input type="hidden" name="againLoginName" id="againLoginName" value="${againRtxNum!}"/>
      </p>
   <p>
          <label for="">补签日期：</label>            
          <input type="text" name="againTime" id="againTime"    class="date textInput readonly valid required"  format="yyyy-MM-dd HH:mm:ss"
          value=""   /> <a class="inputDateButton" href="javascript:;">选择</a>  
   </p>      
    <p>
        <label for="">提交主管：</label>      
         <select name="chargeLoginName" id="chargeLoginName" class="combox required">
            <option value="">请选择</option>
                <#if usermap??>
                <#list usermap?keys as mykey>
	                     <option value="${mykey!}">${usermap[mykey]!}</option>         
                 </#list>
                 </#if>
          </select>
      </p>  
       </div>
        <div class="tabs" currentIndex="0" eventType="click">
            <div class="tabsHeader">
                <div class="tabsHeaderContent">
                    <ul>
                        <li><a href="javascript:;"><span>备注</span></a></li>
                    </ul>
                </div>
            </div>
            <div class="tabsContent" style="height:78px">
                <div>
                    <textarea class=" "  style="width:90%;height:70px"   id="description"   name="description" placeholder="">${leave.description!}</textarea>
                </div>
            </div>
        </div>
</div>
<div class="formBar">
    <ul>
        <li><div class="buttonActive"><div class="buttonContent"><button type="submit">提 交</button></div></div></li>
        <li>
            <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
        </li>
   </ul>
</div>
</form>