<form method="post" action="attend/again/save.do" modelAttribute="attendAgain" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" novalidate="novalidate">
  <div class="pageContent" layoutH="35">
        <div class="pageFormContent">
      <p>
         <label for="">补签人：</label> 
              ${attendAgain.againName!}             
      </p>
   	<p>
        <label for="">补签日期：</label>            
            ${attendAgain.againTime!}
   	</p>      
    <p>
      <label for="">提交主管：</label>         
          ${usermap[attendAgain.chargeLoginName]!}            
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
            <div class="tabsContent" style="height:135px">
                <div>
                    <textarea class=" "  style="width:90%;height:105px"   id="description"   name="description" placeholder="">${attendAgain.description!}</textarea>
                </div>
            </div> 
        </div>
</div>
<div class="formBar">
    <ul>      
        <li><div class="buttonActive"><div class="buttonContent"><a href="attend/again/sign.do?againId=${attendAgain.againId}&f=y" target="ajaxTodo" style="line-height:24px">同意</a></div></div></li>
        <li><div class="buttonActive"><div class="buttonContent"><a href="attend/again/sign.do?againId=${attendAgain.againId}&f=n" target="ajaxTodo" style="line-height:24px">不同意</a></div></div></li> 
   </ul>
</div>
</form>