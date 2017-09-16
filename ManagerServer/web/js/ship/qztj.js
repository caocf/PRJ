$(document).ready(function () {
    $("#search_li").addClass("active");
    $("#qztj_li").addClass("active");
    init();
});

function init() {
    var typeen = $("#seltypeen").val();
    var starttime = $("#beginTime").val();
    var endtime = $("#endTime").val();
    $(".temptr").remove();
    $.ajax({
        url: 'qztj',
        dataType: 'json',
        type: 'post',
        data: {
            'typeen': typeen,
            'starttime': starttime,
            'endtime': endtime
        },
        success: function (data) {
            var map = data.map;
            if (map != null && map != '') {
                if (map.appok > 0 || map.appnook > 0) {
                    pieqz(map.appok, map.appnook);
                    var piearr=map.piearr;
                    var trok = $("<tr class='temptr'><td>通过</td><td>"+piearr[0]+"</td><td>"+piearr[1]+"</td><td>"+piearr[2]+"</td><td>"+map.appok+"</td></tr>");
                    $("#t1").append($(trok));
                    var trnok = $("<tr class='temptr'><td>驳回</td><td>"+piearr[3]+"</td><td>"+piearr[4]+"</td><td>"+piearr[5]+"</td><td>"+map.appnook+"</td></tr>");
                    $("#t1").append($(trnok));
                }
                var arr = map.colqz;
                if (arr.length > 0) {
                    colqz(arr);
                    sett2data(arr);
                }
            }
        }
    });
};

function pieqz(appok, appnook) {
    var data = new Array();
    var objok = {
        name: '审核通过',
        y: appok
    };
    var objnook = {
        name: '审核驳回',
        y: appnook
    };
    data.push(objok);
    data.push(objnook);
    $('#pieqz').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: '取证统计饼图'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            name: '比例',
            colorByPoint: true,
            data: data
        }]
    });
}

function colqz(arr) {
    var xlist = new Array();
    var totallist = new Array();
    var oklist = new Array();
    var nolist = new Array();
    for (var i in arr) {
        var obj = arr[i];
        xlist.push(obj.name);
        totallist.push(obj.sum);
        oklist.push(obj.n1);
        nolist.push(obj.n2);
    }
    $('#colqz').highcharts({
        chart: {
            backgroundColor: null,//背景色设置
            type: 'column'
        },
        credits: {
            text: null,
        },
        exporting: {
            enabled: true
        },//隐藏插件
        title: {
            text: '取证统计直方图'
        },
        subtitle: {
            text: null
        },
        colors: ['#3d8bff', '#5fc219', '#fe4a4e'],//数据颜色
        legend: {
            itemStyle: {color: '#666'},
            align: 'right', //水平方向位置
            verticalAlign: 'top', //垂直方向位置
            y: 30,
            borderRadius: 4
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
                text: '审核次数',
                style: {
                    color: '#333'
                }
            },
            gridLineWidth: 1,
            gridLineColor: '#f6fafb'
        },
        tooltip: {
            valueSuffix: '个'
        },
        plotOptions: {
            column: {
                borderWidth: 0
            }
        },
        series: [
            {
                name: '合计',
                data: totallist,
                dataLabels: {
                    enabled: true,
                    color: '#333',
                    style: {
                        fontSize: 10
                    }
                }
            },
            {
                name: '通过',
                data: oklist,
                dataLabels: {
                    enabled: true,
                    color: '#333',
                    style: {
                        fontSize: 10
                    }
                }
            },
            {
                name: '驳回',
                data: nolist,
                dataLabels: {
                    enabled: true,
                    color: '#333',
                }
            }
        ]
    });

}

function sett2data(arr) {
    for (var i in arr) {
        var obj = arr[i];
        var trdata = $("<tr class='temptr'><td>" + obj.name + "</td><td>" + obj.sum + "</td><td>" + obj.n1 + "</td><td>" + obj.n2 + "</td><td>" + obj.t1 + "</td><td>" + obj.t2 + "</td><td>" + obj.t3 + "</td></tr>");
        $("#t2").append($(trdata));
    }
}