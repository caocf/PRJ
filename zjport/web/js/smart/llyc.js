$(document).ready(function () {
    $("#smart").addClass("active");
    $("#warn").addClass("active");
    $("#llyc_li").addClass("active");
    getallarea();
})
//获取地区下拉框
function getallarea() {
    $.ajax({
        url: '../supervise/sites',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            $("#areaselect").empty();
            $(data.records.data).each(function (index, item) {
                $("#areaselect").append(
                    "<option value='" + item.id + "'>" + item.name + "</option>"
                );
            })
            gettb();
        }
    })
}
//获取地区下拉框
function gettb() {
    $.ajax({
        url: '../supervise/llyc',
        type: 'post',
        dataType: 'json',
        data: {
            'site': $("#areaselect").val()
        },
        success: function (data) {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('chartsdiv'));
            var xlist = [];
            var uplist = [];
            var downlist = [];
            var alllist = [];
            $(data.obj).each(function (index, item) {
                xlist.push(item.sj);
                uplist.push(item.up);
                downlist.push(item.down);
                alllist.push(item.total);
            })
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: $("#areaselect>option:selected").text()+'流量预测'
                },
                tooltip: {},
                legend: {
                    data: ['上行', '下行', '总量'],
                },
                xAxis: {
                    data: xlist
                },
                yAxis: {},
                series: [
                    {
                        name: '上行',
                        type: 'line',
                        data: uplist
                    },
                    {
                        name: '下行',
                        type: 'line',
                        data: downlist
                    },
                    {
                        name: '总量',
                        type: 'line',
                        data: alllist
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    })
}

//href传参获取
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
}