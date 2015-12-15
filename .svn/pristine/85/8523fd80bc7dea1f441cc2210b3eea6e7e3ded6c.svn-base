<style type="text/css"> 
.graph{ 
	width:200px; 
	border:1px solid grey; 
	height:8px;
	display:block;
}
#bar{ 
	display:block; 
	background:blue; 
	float:left; 
	height:100%; 
	text-align:center; 
} 
</style>
<script type="text/javascript"> 
	function go(stationId,job){
		percent='50%'
		$("#bar").css('width',percent)
	}
	function checkStatus(stationDivs){
		$(stationDivs).each(function(){
			stationId=$(this).attr('id')
			$.getJSON('monitor/jobprogress.do',{stationId:stationId},function(data){
				if(data.currentJob){
					//alert(data.currentJob)
				}
				if(data.wait){
					$(data.wait).each(function(job){
						//alert('wait')
					})
				}
			});
		})
	}
	function checBusyStation(){
		checkStatus($(".BUSY"))
	}
	function checkIdleStation(){
		checkStatus($(".IDLE"))
	}
	function checkOfflineStation(){
		checkStatus($(".OFFLINE"))
	}
	
	$(document).ready(function(){
		t=window.setInterval("checBusyStation()",10*1000); 
		t=window.setInterval("checkIdleStation()",20*1000); 
		t=window.setInterval("checkOfflineStation()",30*1000); 
	})
</script> 
<div class="pageContent" layoutH="0">	
   <#if stationList??>
    <#list stationList as station>
		<div id="${station.stationId}" class="panel station ${station.status!}" minH="100" defH="150" style="width:300px;float:left;margin:5px;clear:none">
			<h1>
			    <span style="float:right;margin-top:1px"><img src="<@s.url '/resources/img/${station.status!}.png'/>" /></span>
			    ${station.stationId}&nbsp;&nbsp;${station.host!}:${station.port!}
			</h1>
			<div>
				<p>当前任务:</p>
				<p>进度:<span class="graph"> 
						<strong id="bar" style="width:1%;"></strong> 
						</span>
				</p>
				<p>待执行任务:</p>
			</div>
		</div>
	</#list>
	</#if>
</div>