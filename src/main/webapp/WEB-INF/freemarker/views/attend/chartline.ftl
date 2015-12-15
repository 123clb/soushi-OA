<script type="text/javascript" src="<@s.url '/resources/js/amcharts/amcharts.js'/>"></script>
<script>
var chart;
var chartData =${chartData};
var chartCursor;
// generate some random data, quite different range
function generateChartData() {
    var firstDate = new Date();
    firstDate.setDate(firstDate.getDate() - 500);
    alert(111);
    for (var i = 0; i < 500; i++) {
        var newDate = new Date(firstDate);
        newDate.setDate(newDate.getDate() + i);

        var visits = Math.round(Math.random() * 40) - 20;
       
        chartData.push({
            date: newDate,
            visits: visits
        });
    }
}
// creat chart

function  beginChart(){
    // generate some data
    // generateChartData();
    // SERIAL CHART 
    chart = new AmCharts.AmSerialChart();
    chart.autoMarginOffset = 5;
    chart.marginBottom = 0;
    chart.pathToImages = "";
    chart.zoomOutButton = {
        backgroundColor: '#000000',
        backgroundAlpha: 0.15
    };
    chart.dataProvider = chartData;
    chart.categoryField = "month";
    chart.balloon.bulletSize = 5;
     
    // listen for "dataUpdated" event (fired when chart is rendered) and call zoomChart method when it happens
    chart.addListener("dataUpdated", zoomChart);

    // AXES
    // category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.labelRotation = 90;
    //categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
   // categoryAxis.minPeriod = "DD"; // our data is daily, so we set minPeriod to DD
    categoryAxis.dashLength = 1;
    categoryAxis.gridAlpha = 0.15;
    //categoryAxis.position = "top";
    categoryAxis.axisColor = "#DADADA";

    // value                
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.axisAlpha = 0;
    valueAxis.dashLength = 1;
    chart.addValueAxis(valueAxis);

    // GRAPH
    var graph = new AmCharts.AmGraph();
    graph.title = "red line";
    graph.valueField = "workTime";
    graph.bullet = "round";
    graph.bulletBorderColor = "#FFFFFF";
    graph.balloonText = "[[value]]";
    graph.bulletBorderThickness = 2;
    graph.lineThickness = 2;
    graph.lineColor = "#5fb503";
    graph.negativeLineColor = "#efcc26";
    graph.hideBulletsCount = 50; // this makes the chart to hide bullets when there are more than 50 series in selection
    chart.addGraph(graph);

    // CURSOR
    //chartCursor = new AmCharts.ChartCursor();
    //chartCursor.cursorPosition = "mouse";
    //chartCursor.pan = true; // set it to fals if you want the cursor to work in "select" mode
    //chart.addChartCursor(chartCursor);
    // SCROLLBAR
    //var chartScrollbar = new AmCharts.ChartScrollbar();
    //chart.addChartScrollbar(chartScrollbar);

    // WRITE
    console.log(chart)
    chart.write("chartdiv");
}
beginChart();
// this method is called when chart is inited as we listen for "dataUpdated" event
function zoomChart() {
    // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
    chart.zoomToIndexes(chartData.length - 40, chartData.length - 1);
}
</script>

<div id="chartdiv" style="width: 100%; height: 362px;"></div>