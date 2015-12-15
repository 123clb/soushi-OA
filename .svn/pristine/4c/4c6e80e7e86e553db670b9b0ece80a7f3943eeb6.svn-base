<div class="pageContent">
 <table class="table" width="100%" layoutH="90" id="fvbox">
    <thead>
        <tr> 
            <th align="center"  width="5%">日期</th>
            <th align="center"  width="7%">打卡时间</th>           
         </tr>
    </thead>
 <tbody>
      <#list infoMap?keys as mykey>
          <tr>
              <td>${mykey!}</td>
              <td>${infoMap[mykey]?string("HH:mm")!}</td>                  
          </tr>
       </#list>   
     </tbody>
</table>
</div>