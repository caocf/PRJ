/*
 * Author: Abdullah A Almsaeed
 * Date: 4 Jan 2014
 * Description:
 *      This is a demo file used only for the main dashboard (index.html)
 **/
"use strict";

$(function () {

  //Make the dashboard widgets sortable Using jquery UI
  $(".connectedSortable").sortable({
    placeholder: "sort-highlight",
    connectWith: ".connectedSortable",
    handle: ".box-header, .nav-tabs",
    forcePlaceholderSize: true,
    zIndex: 999999,
    start: function (e,ui) {
      $(ui.helper).addClass('dragging');
    },
    stop: function (e,ui) {
      $(ui.item).css({width:''}).removeClass('dragging');
    }
  });
  $(".connectedSortable .box-header, .connectedSortable .nav-tabs-custom").css("cursor", "move");
  
  //jQuery UI sortable for the todo list
  $(".todo-list").sortable({
    placeholder: "sort-highlight",
    handle: ".handle",
    forcePlaceholderSize: true,
    zIndex: 999999
  });

  //bootstrap WYSIHTML5 - text editor
  /*$(".textarea").wysihtml5();*/

  $('.daterange').daterangepicker(
          {
            ranges: {
              'Today': [moment(), moment()],
              'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
              'Last 7 Days': [moment().subtract(6, 'days'), moment()],
              'Last 30 Days': [moment().subtract(29, 'days'), moment()],
              'This Month': [moment().startOf('month'), moment().endOf('month')],
              'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
            startDate: moment().subtract(29, 'days'),
            endDate: moment()
          },
  function (start, end) {
    alert("You chose: " + start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
  });

  /* jQueryKnob */
  /*$(".knob").knob();*/

  //jvectormap data
  var visitorsData = {
    "US": 398, //USA
    "SA": 400, //Saudi Arabia
    "CA": 1000, //Canada
    "DE": 500, //Germany
    "FR": 760, //France
    "CN": 300, //China
    "AU": 700, //Australia
    "BR": 600, //Brazil
    "IN": 800, //India
    "GB": 320, //Great Britain
    "RU": 3000 //Russia
  };
  //World map by jvectormap
  /*$('#world-map').vectorMap({
    map: 'world_mill_en',
    backgroundColor: "transparent",
    regionStyle: {
      initial: {
        fill: '#e4e4e4',
        "fill-opacity": 1,
        stroke: 'none',
        "stroke-width": 0,
        "stroke-opacity": 1
      }
    },
    series: {
      regions: [{
          values: visitorsData,
          scale: ["#92c1dc", "#ebf4f9"],
          normalizeFunction: 'polynomial'
        }]
    },
    onRegionLabelShow: function (e, el, code) {
      if (typeof visitorsData[code] != "undefined")
        el.html(el.html() + ': ' + visitorsData[code] + ' new visitors');
    }
  });*/

  //Sparkline charts
  /*var myvalues = [1000, 1200, 920, 927, 931, 1027, 819, 930, 1021];
  $('#sparkline-1').sparkline(myvalues, {
    type: 'line',
    lineColor: '#92c1dc',
    fillColor: "#ebf4f9",
    height: '50',
    width: '80'
  });
  myvalues = [515, 519, 520, 522, 652, 810, 370, 627, 319, 630, 921];
  $('#sparkline-2').sparkline(myvalues, {
    type: 'line',
    lineColor: '#92c1dc',
    fillColor: "#ebf4f9",
    height: '50',
    width: '80'
  });
  myvalues = [15, 19, 20, 22, 33, 27, 31, 27, 19, 30, 21];
  $('#sparkline-3').sparkline(myvalues, {
    type: 'line',
    lineColor: '#92c1dc',
    fillColor: "#ebf4f9",
    height: '50',
    width: '80'
  });*/

  //The Calender
  $("#calendar").datepicker();

  //SLIMSCROLL FOR CHAT WIDGET
  $('#chat-box').slimScroll({
    height: '250px'
  });

  /* Morris.js Charts */
  // Sales chart
  /*var area = new Morris.Area({
    element: 'revenueChart',
    resize: true,
    data: [
      {time: '2015-12-28T08:00:00', value: 26},
      {time: '2015-12-28T09:00:00', value: 24},
      {time: '2015-12-28T10:00:00', value: 9},
      {time: '2015-12-28T11:00:00', value: 35},
      {time: '2015-12-28T12:00:00', value: 4},
      {time: '2015-12-28T13:00:00', value: 93},
      {time: '2015-12-28T14:00:00', value: 35},
      {time: '2015-12-28T15:00:00', value: 27},
      {time: '2015-12-28T16:00:00', value: 10},
      {time: '2015-12-28T17:00:00', value: 3}
    ],
    xkey: 'time',
    ykeys: ['value'],
    labels: ['Value'],
    lineColors: ['#a0d0e0', '#3c8dbc'],
    hideHover: 'auto'
  });*/
  /*var line = new Morris.Bar({
    element: 'lineChart',
    resize: true,
    data: [
           { label: '海事系统', value: 85 },
           { label: '港政系统', value: 78 },
           { label: '运政系统', value: 65 },
           { label: 'OA', value: 58 },
           { label: '稽征系统', value: 46 },
           { label: '航道运行系统', value: 33 }
         ],
	xkey: 'label',
	// A list of names of data record attributes that contain y-values.
	ykeys: ['value'],
	// Labels for the ykeys -- will be displayed when you hover over the
	// chart.
	labels: ['Value'],
	barColors: ['#efefef'],
    hideHover: 'auto',
    barRatio: 0.4,
    xLabelAngle: 35,
    gridTextColor: "#fff"
    //gridTextFamily: "Open Sans",
    //gridTextSize: 10
  });*/

  //Donut Chart
  /*var donut = new Morris.Donut({
    element: 'sales-chart',
    resize: true,
    colors: ["#3c8dbc", "#f56954", "#00a65a", "#f39c12", "#00c0ef", "#d2d6de"],
    data: [
      {label: "教师学生中心", value: 24},
      {label: "管理通", value: 55},
      {label: "地图系统", value: 15},
      {label: "学籍系统", value: 21},
      {label: "行政平台", value: 85},
      {label: "人事系统", value: 38}
    ],
    hideHover: 'auto'
  });*/
  
  
  //-------------
  //- PIE CHART -
  //-------------
  // Get context with jQuery - using jQuery's .get() method.
  /*
  var pieChartCanvas = $("#pieChart").get(0).getContext("2d");
  var pieChart = new Chart(pieChartCanvas);
  var PieData = [
    {
      value: 85,
      color: "#f56954",
      highlight: "#f56954",
      label: "管理通"
    },
    {
      value: 38,
      color: "#00a65a",
      highlight: "#00a65a",
      label: "人事系统"
    },
    {
      value: 24,
      color: "#f39c12",
      highlight: "#f39c12",
      label: "教师学生中心"
    },
    {
      value: 85,
      color: "#00c0ef",
      highlight: "#00c0ef",
      label: "行政平台"
    },
    {
      value: 15,
      color: "#3c8dbc",
      highlight: "#3c8dbc",
      label: "地图系统"
    },
    {
      value: 21,
      color: "#d2d6de",
      highlight: "#d2d6de",
      label: "学籍系统"
    }
  ];
  var pieOptions = {
    //Boolean - Whether we should show a stroke on each segment
    segmentShowStroke: true,
    //String - The colour of each segment stroke
    segmentStrokeColor: "#fff",
    //Number - The width of each segment stroke
    segmentStrokeWidth: 1,
    //Number - The percentage of the chart that we cut out of the middle
    percentageInnerCutout: 50, // This is 0 for Pie charts
    //Number - Amount of animation steps
    animationSteps: 100,
    //String - Animation easing effect
    animationEasing: "easeOutBounce",
    //Boolean - Whether we animate the rotation of the Doughnut
    animateRotate: true,
    //Boolean - Whether we animate scaling the Doughnut from the centre
    animateScale: false,
    //Boolean - whether to make the chart responsive to window resizing
    responsive: true,
    // Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
    maintainAspectRatio: false,
    //String - A legend template
    legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>",
    //String - A tooltip template
    tooltipTemplate: "<%=value %> <%=label%>"
  };
  //Create pie or douhnut chart
  // You can switch between pie and douhnut using the method below.  
  pieChart.Doughnut(PieData, pieOptions);
  */
  //-----------------
  //- END PIE CHART -
  //-----------------


  //-----------------------
  //- MONTHLY SALES CHART -
  //-----------------------
/*
  // Get context with jQuery - using jQuery's .get() method.
  var salesChartCanvas = $("#salesChart").get(0).getContext("2d");
  // This will get the first returned node in the jQuery collection.
  var salesChart = new Chart(salesChartCanvas);

  var salesChartData = {
    labels: ["一月", "二月", "三月", "四月", "五月", "六月","七月"],
    datasets: [
      {
        label: "Electronics",
        fillColor: "rgb(210, 214, 222)",
        strokeColor: "rgb(210, 214, 222)",
        pointColor: "rgb(210, 214, 222)",
        pointStrokeColor: "#c1c7d1",
        pointHighlightFill: "#fff",
        pointHighlightStroke: "rgb(220,220,220)",
        data: [67, 91, 36, 151, 28, 123, 38]
      },
      {
        label: "Digital Goods",
        fillColor: "rgba(60,141,188,0.9)",
        strokeColor: "rgba(60,141,188,0.8)",
        pointColor: "#3b8bba",
        pointStrokeColor: "rgba(60,141,188,1)",
        pointHighlightFill: "#fff",
        pointHighlightStroke: "rgba(60,141,188,1)",
        data: [59, 49, 45, 94, 76, 22, 31]
      }
    ]
  };

  var salesChartOptions = {
    //Boolean - If we should show the scale at all
    showScale: true,
    //Boolean - Whether grid lines are shown across the chart
    scaleShowGridLines: false,
    //String - Colour of the grid lines
    scaleGridLineColor: "rgba(0,0,0,.05)",
    //Number - Width of the grid lines
    scaleGridLineWidth: 1,
    //Boolean - Whether to show horizontal lines (except X axis)
    scaleShowHorizontalLines: true,
    //Boolean - Whether to show vertical lines (except Y axis)
    scaleShowVerticalLines: true,
    //Boolean - Whether the line is curved between points
    bezierCurve: true,
    //Number - Tension of the bezier curve between points
    bezierCurveTension: 0.3,
    //Boolean - Whether to show a dot for each point
    pointDot: false,
    //Number - Radius of each point dot in pixels
    pointDotRadius: 4,
    //Number - Pixel width of point dot stroke
    pointDotStrokeWidth: 1,
    //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
    pointHitDetectionRadius: 20,
    //Boolean - Whether to show a stroke for datasets
    datasetStroke: true,
    //Number - Pixel width of dataset stroke
    datasetStrokeWidth: 2,
    //Boolean - Whether to fill the dataset with a color
    datasetFill: true,
    //String - A legend template
    legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%=datasets[i].label%></li><%}%></ul>",
    //Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
    maintainAspectRatio: false,
    //Boolean - whether to make the chart responsive to window resizing
    responsive: true
  };

  //Create the line chart
  salesChart.Line(salesChartData, salesChartOptions);
*/
  //---------------------------
  //- END MONTHLY SALES CHART -
  //---------------------------


  //Fix for charts under tabs
  $('.box ul.nav a').on('shown.bs.tab', function (e) {
    area.redraw();
    donut.redraw();
  });


  /* BOX REFRESH PLUGIN EXAMPLE (usage with morris charts) */
  $("#loading-example").boxRefresh({
    source: "ajax/dashboard-boxrefresh-demo.php",
    onLoadDone: function (box) {
      bar = new Morris.Bar({
        element: 'bar-chart',
        resize: true,
        data: [
          {y: '2006', a: 100, b: 90},
          {y: '2007', a: 75, b: 65},
          {y: '2008', a: 50, b: 40},
          {y: '2009', a: 75, b: 65},
          {y: '2010', a: 50, b: 40},
          {y: '2011', a: 75, b: 65},
          {y: '2012', a: 100, b: 90}
        ],
        barColors: ['#00a65a', '#f56954'],
        xkey: 'y',
        ykeys: ['a', 'b'],
        labels: ['CPU', 'DISK'],
        hideHover: 'auto'
      });
    }
  });

  /* The todo list plugin */
  $(".todo-list").todolist({
    onCheck: function (ele) {
      console.log("The element has been checked")
    },
    onUncheck: function (ele) {
      console.log("The element has been unchecked")
    }
  });

});