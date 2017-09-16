$(document).ready(function(){
    $("#th-large_li").addClass("active");
    $("#loadApp_li").addClass("active");
    $("#timetype").children('div').click(function(){
        $("#timetype").children('div').css('background-color','rgb(250,250,250)');
        $(this).css('background-color','rgb(231,231,231)');
    });
    bwchartmake();
})
var xlist=['7月19','7月19','7月19','7月19','7月19','7月19','7月19','7月19'];
var gglist=[22,33,44,55,66,55,44,33];
var nblist=[44,55,66,55,44,33,22,33];
function bwchartmake(){
    $('#tab-internet').highcharts({
        chart: {
            backgroundColor:null,//背景色设置
            type: 'column'
        },
        credits: {
            text: null,
        },
        exporting: {
            enabled:false
        },//隐藏插件
        title: {
            text:null,
        },
        subtitle: {
            text: null
        },
        colors: ['#f8b451','#89abd9'],//数据颜色
        legend: {
            itemStyle:{color:'#666'},
            align: 'right', //水平方向位置
            verticalAlign: 'top', //垂直方向位置
            y: 30,
            borderRadius:4
        },

        xAxis: {
            labels: {
                style: {
                    color: '#333'//x轴字体颜色
                }
            },
            lineColor: null,//x轴颜色
            tickLength: 0,//x轴刻度线高度
            categories: xlist
        },
        yAxis: {
            labels: {
                style: {
                    color: '#333'
                }
            },
            min: 0,
            title: {
                text: '下载次数',
                style: {
                    color: '#333'
                }
            },
            gridLineWidth: 1,
            gridLineColor: '#f6fafb'
        },
        tooltip: {
            valueSuffix: '次'
//	            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
//	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
//	                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
//	            footerFormat: '</table>',
//	            shared: true,
//	            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [
            {	 name:'公众版',
                data: gglist,
                dataLabels: {
                    enabled: true,
                    color:'#333',
                    style:{
                        fontSize:10
                    }
                }
            },
            {	 name:'内部版',
                data: nblist,
                dataLabels: {
                    enabled: true,
                    color:'#333',
                }
            }
        ]
    });
}
/**
 * 设置时间段
 * @param type：时间段类型1：本周 ，2：上周，3：本月 ，4：上月
 */
function settimetype(type){
    switch (parseInt(type)){
        case 1:
            $("#beginTime").val(getWeekStartDate);
            $("#endTime").val(getWeekEndDate);
            break;
        case 2:
            $("#beginTime").val(getUpWeekStartDate);
            $("#endTime").val(getUpWeekEndDate);
            break;
        case 3:
            $("#beginTime").val(getMonthStartDate);
            $("#endTime").val(getMonthEndDate);
            break;
        case 4:
            $("#beginTime").val(getLastMonthStartDate);
            $("#endTime").val(getLastMonthEndDate);
            break;
        default :
            break;
    }
}


//日期计算方法
var now = new Date();                    //当前日期
var nowDayOfWeek = now.getDay();         //今天本周的第几天
var Day = now.getDate()- now.getDay();
if(now.getDay()==0)           //星期天表示 0 故当星期天的时候，获取上周开始的时候
{
    Day -= 6;
}           //当前日
var nowMonth = now.getMonth();           //当前月
var nowYear = now.getYear();             //当前年
nowYear += (nowYear < 2000) ? 1900 : 0;  //

var lastMonthDate = new Date();  //上月日期
lastMonthDate.setDate(1);
lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
var lastYear = lastMonthDate.getYear();
lastYear += (lastYear < 2000) ? 1900 : 0;  //
var lastMonth = lastMonthDate.getMonth();

//格式化日期：yyyy-MM-dd
function formatDate(date) {
    var myyear = date.getFullYear();
    var mymonth = date.getMonth()+1;
    var myweekday = date.getDate();

    if(mymonth < 10){
        mymonth = "0" + mymonth;
    }
    if(myweekday < 10){
        myweekday = "0" + myweekday;
    }
    return (myyear+"-"+mymonth + "-" + myweekday);
}

//获得某月的天数
function getMonthDays(myMonth){
    var monthStartDate = new Date(nowYear, myMonth, 1);
    var monthEndDate = new Date(nowYear, myMonth + 1, 1);
    var   days   =   (monthEndDate   -   monthStartDate)/(1000   *   60   *   60   *   24);
    return   days;
}

//获得本季度的开始月份
function getQuarterStartMonth(){
    var quarterStartMonth = 0;
    if(nowMonth<3){
        quarterStartMonth = 0;
    }
    if(2<6){
        quarterStartMonth = 3;
    }
    if(5<9){
        quarterStartMonth = 6;
    }
    if(nowMonth>8){
        quarterStartMonth = 9;
    }
    return quarterStartMonth;
}
//获得本周的开始日期
var getWeekStartDate = new Date(nowYear, nowMonth, Day);
getWeekStartDate =  formatDate(getWeekStartDate);
//获得本周的结束日期
var getWeekEndDate = new Date(nowYear, nowMonth, Day + 6);
getWeekEndDate =  formatDate(getWeekEndDate);


//获得上周的开始日期
var getUpWeekStartDate = new Date(nowYear, nowMonth, Day -7);
getUpWeekStartDate =  formatDate(getUpWeekStartDate);

//获得上周的结束日期
var getUpWeekEndDate = new Date(nowYear, nowMonth, Day -1);
getUpWeekEndDate =  formatDate(getUpWeekEndDate);


//获得本月的开始日期
var getMonthStartDate = new Date(nowYear, nowMonth, 1);
getMonthStartDate =  formatDate(getMonthStartDate);

//获得本月的结束日期
var getMonthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
getMonthEndDate =  formatDate(getMonthEndDate);

//获得上月开始时间
var getLastMonthStartDate = new Date(lastYear, lastMonth, 1);
getLastMonthStartDate = formatDate(getLastMonthStartDate);

//获得上月结束时间
var getLastMonthEndDate = new Date(lastYear, lastMonth, getMonthDays(lastMonth));
getLastMonthEndDate = formatDate(getLastMonthEndDate);