var balloonPie = "[[title]]: [[percents]]% ([[value]] 小时)\n[[description]]";
//var balloonColumn = "[[title]]: (总时长:[[value]] (小时/个))\n[[description]]";
var curPieChart;
var curPieUrl = "chart/catalog/duration/json.do";
var curPieTitle;
function a_click(tabid, url) {
  navTab.openTab(tabid, url,{title:'饼状图', fresh:true, external:false});
  return false;
}
function show_pie(url, title) {
  curPieUrl = url;
  curPieTitle = title;
  start = $("#startDate").val();
  end = $("#endDate").val();
  $.post(url, {startDate: start, endDate: end}, function(data){
      if($(data).length > 0){
        durationTotal = 0;
        fileTotalCount = 0;
        $(data).each(function(){
          durationTotal += $(this).attr('duration');
          fileTotalCount += $(this).attr('count');
        });
        $("span#duration").text(durationTotal);
        $("span#count").text(fileTotalCount);
        drawPie("duration_chart", data);
        text = $("div#catalog").text();
        if(title) {
          catalogId = title.slice(title.lastIndexOf('(')+1, -1);
          $("div#catalog").append("<span> >> </spam>");
          url = "chart/pie.do?catalogId=" + catalogId;
          $("div#catalog").append("<a href='javascript:;' onclick='a_click(\"main\", \"" + url + "\")'>" + title + "</a>");
        }
      }
  });
  return false;
}

function show_line(url, event) {
  $.post(url, function(data){
    if($(data).length > 0){
        drawLine("duration_chart", data);
    }
});
}

function refresh(event){
  var url = event.dataContext.providerUrl;
  if(!url) {
      return false;
  }
  show_pie(url, event.title);
}

function drawAreaStack(pad,chartDataJson, userDataJson, topCountUserIdJson, topDurationUserIdJson, field) {
  var unit = "";
  var topJson;
  if(field == "duration") {
    unit = "小时";
    topJson = topDurationUserIdJson;
  } else {
    unit = "个";
    topJson = topCountUserIdJson;
  }
  
  chart = new AmCharts.AmSerialChart();
  
  chart.pathToImages = mrmsroot + "/resources/js/amcharts/images/";
  chart.zoomOutButton = {
      backgroundColor: '#000000',
      backgroundAlpha: 0.15
  };
  chart.dataProvider = chartDataJson;
  chart.marginTop = 30;
  chart.autoMarginOffset = 3;
  chart.marginRight = 10;
  chart.categoryField = "date";
  
  var categoryAxis = chart.categoryAxis;
  categoryAxis.labelRotation = 90;
  categoryAxis.minPeriod = "MM"; // our data is daily, so we set minPeriod to DD
  categoryAxis.dashLength = 2;
  categoryAxis.gridAlpha = 0.15;
  categoryAxis.axisColor = "#DADADA";
  categoryAxis.startOnAxis = true;
  categoryAxis.showLastLabel = false;
  
  var valueAxis = new AmCharts.ValueAxis();
  valueAxis.axisThickness = 2;
  valueAxis.gridAlpha = 0;
  valueAxis.unit = unit;
  chart.addValueAxis(valueAxis);
  var limit = 1;
  for(var userData = userDataJson.pop(); userData; userData = userDataJson.pop()) {
    var userName = userData.realName;
    var userId = userData.userId;
    var color = userData.color;
    console.log(color);
    var isHidden = false;
    if(topJson.indexOf(userId) < 0) {
      isHidden = true;
    }
    var graph = new AmCharts.AmGraph();
    graph.valueAxis = valueAxis;
    graph.hidden = isHidden;
    graph.title = userName;
    graph.bullet = "round";
    graph.balloonText = "[[category]]: [[title]] [[value]] " + unit;
    graph.lineThickness = 1;
    graph.valueField = field + "_" + userId;
    graph.hideBulletsCount = 30;
    graph.lineColor = color;
    chart.addGraph(graph);
    limit++;
  }
  
  var chartCursor = new AmCharts.ChartCursor();
  chartCursor.cursorPosition = "mouse";
  chartCursor.cursorAlpha = 1;
  chart.addChartCursor(chartCursor);

  
  var chartScrollbar = new AmCharts.ChartScrollbar();
  chartScrollbar.scrollbarHeight = 15;
  chartScrollbar.color = "#FFFFFF";
  chartScrollbar.autoGridCount = false;
  chart.addChartScrollbar(chartScrollbar);

  
  var legend = new AmCharts.AmLegend();
  legend.marginLeft = 110;
  chart.addLegend(legend);
  
  chart.write(pad);
}


function drawPie(pad, data) {
  $("#" + pad).children().remove();
  chart = new AmCharts.AmPieChart();
  chart.dataProvider = data;
  chart.titleField = "name";
  chart.valueField = "duration";
  chart.balloonText = balloonPie;
  chart.descriptionField = "description";
  chart.urlField = "providerUrl";
  chart.outlineColor = "#FFFFFF";
  chart.outlineAlpha = 0.8;
  chart.outlineThickness = 2;
  chart.depth3D = 15;
  chart.angle = 30;
  chart.startEffect = ">";
  chart.clickSlice = refresh;
  chart.write(pad);
  pieChart = chart;
}


function zoomChart() {
  chart.zoomToCategoryValues(0, chart.dataProvider.length-1);
}

function drawLine(pad, data, valueField) {
  $("#" + pad).children().remove();
  chart = new AmCharts.AmSerialChart();
  chart.dataProvider = data;
  chart.pathToImages = mrmsroot + "/resources/js/amcharts/images/";
  chart.marginLeft = 10;
  chart.categoryField = "date";
  chart.zoomOutButton = {
      backgroundColor: '#000000',
      backgroundAlpha: 0.15
  };
  chart.balloon.bulletSize = 5;
  
  chart.addListener("dataUpdated", zoomChart);

  var categoryAxis = chart.categoryAxis;
  categoryAxis.labelRotation = 90;
  categoryAxis.minPeriod = "MM"; // our data is daily, so we set minPeriod to DD
  categoryAxis.dashLength = 2;
  categoryAxis.gridAlpha = 0.15;
  categoryAxis.axisColor = "#DADADA";
  
  var countAxis = new AmCharts.ValueAxis();
  countAxis.position = "right"; // this line makes the axis to appear on the right
  countAxis.axisColor = "#33539c";
  countAxis.axisThickness = 2;
  countAxis.gridAlpha = 0;
  
  countAxis.unit = "个";
  chart.addValueAxis(countAxis);
  
  graphCount = new AmCharts.AmGraph();
  graphCount.valueAxis = countAxis;
  graphCount.title="视频上传个数统计折线";
  graphCount.bullet = "round";
  graphCount.balloonText = "[[category]]: [[value]] 个";
  graphCount.lineThickness = 1;
  graphCount.lineColor = "#33539C";
  graphCount.valueField = "count";
  graphCount.hideBulletsCount = 30;
  chart.addGraph(graphCount);
  
  var durationAxis = new AmCharts.ValueAxis();
  durationAxis.axisColor = "#FF6600";
  durationAxis.axisThickness = 2;
  durationAxis.gridAlpha = 0;
  durationAxis.unit = "时";
  chart.addValueAxis(durationAxis);
  
  var graphDuration = new AmCharts.AmGraph();
  graphDuration.valueAxis = durationAxis;
  graphDuration.title="视频上传时长统计折线";
  graphDuration.bullet = "triangleUp";
  graphDuration.balloonText = "[[category]]: [[value]] 时";
  graphDuration.lineThickness = 1;
  graphDuration.lineColor = "#FF6600";
  graphDuration.valueField = "duration";
  graphDuration.hideBulletsCount = 30;
  chart.addGraph(graphDuration);
  
  var chartCursor = new AmCharts.ChartCursor();
  chartCursor.cursorPosition = "mouse";
  chartCursor.cursorAlpha = 1;
  chart.addChartCursor(chartCursor);

  // SCROLLBAR
  var chartScrollbar = new AmCharts.ChartScrollbar();
  chartScrollbar.scrollbarHeight = 15;
  chartScrollbar.color = "#FFFFFF";
  chartScrollbar.autoGridCount = false;
  chart.addChartScrollbar(chartScrollbar);

  
  var legend = new AmCharts.AmLegend();
  legend.marginLeft = 110;
  chart.addLegend(legend);
  
  chart.write(pad);
}

/*function drawColumn(pad, data, valueField) {
  var unit = "小时";
  var label = "时长";
  if(valueField == "count") {
      unit = "个";
      label = "个数";
  }
  $("#" + pad).children().remove();
  var chart = new AmCharts.AmSerialChart();
  chart.dataProvider = data;
  chart.categoryField = "date";
  chart.startDuration = 1;
  chart.angle = 30;
  chart.depth3D = 15;
  
  var categoryAxis = chart.categoryAxis;
  categoryAxis.labelRotation = 0;
  categoryAxis.gridPosition = "start";
  
  var graph = new AmCharts.AmGraph();
  graph.valueField = valueField;
  graph.type = "column";
  graph.lineAplha = 0;
  graph.fillAlphas = 0.8;
  graph.balloonText = "[[title]]: (总上传" + label + ":[[value]] (" + unit + "))\n[[description]]";
  graph.descriptionField = "description";
  graph.title = "按月统计上传视频时长";
  chart.addGraph(graph);
  
  chart.write(pad);
}*/