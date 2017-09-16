$(document).ready(function () {
    $("#search_li").addClass("active");
    $("#xctj_li").addClass("active");
    init();
});

function init() {
    var starttime = $("#beginTime").val();
    var endtime = $("#endTime").val();
    $(".temptr").remove();
    $.ajax({
        url: 'xctj',
        dataType: 'json',
        type: 'post',
        data: {
            'starttime': starttime,
            'endtime': endtime
        },
        success: function (data) {
            var map = data.map;
            if (map != null && map != '') {
                var arr = map.coldata;
                if (arr.length > 0) {
                    colmile(arr);
                    colevent(arr);
                    settabledata(map.retmap);
                }
            }
        }
    });
};

function colmile(arr) {
    var xlist = new Array();
    var totallist = new Array();
    for (var i in arr) {
        var obj = arr[i];
        xlist.push(obj.name);
        totallist.push(obj.d1);
    }
    $('#colmile').highcharts({
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
            text: '巡航里程统计直方图'
        },
        subtitle: {
            text: null
        },
        colors: ['#3d8bff'],//数据颜色
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
                text: '里程',
                style: {
                    color: '#333'
                }
            },
            gridLineWidth: 1,
            gridLineColor: '#f6fafb'
        },
        tooltip: {
            valueSuffix: '公里'
        },
        plotOptions: {
            column: {
                borderWidth: 0
            }
        },
        series: [
            {
                name: '巡航里程',
                data: totallist,
                dataLabels: {
                    enabled: true,
                    color: '#333',
                }
            }
        ]
    });

}

function colevent(arr) {
    var xlist = new Array();
    var totallist = new Array();
    for (var i in arr) {
        var obj = arr[i];
        xlist.push(obj.name);
        totallist.push(obj.sum);
    }
    $('#colevent').highcharts({
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
            text: '巡航事件统计直方图'
        },
        subtitle: {
            text: null
        },
        colors: ['#3d8bff'],//数据颜色
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
                text: '事件',
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
                name: '事件个数',
                data: totallist,
                dataLabels: {
                    enabled: true,
                    color: '#333',
                }
            }
        ]
    });

}

/*
 function settabledata(arr) {
 for (var i in arr) {
 var obj = arr[i];
 var tr1data = $("<tr class='temptr'><td>" + obj.name + "</td><td>" + obj.d1 + "</td></tr>");
 $("#t1").append($(tr1data));
 var tr2data = $("<tr class='temptr'><td>" + obj.name + "</td><td>" + obj.sum + "</td></tr>");
 $("#t2").append($(tr2data));
 }
 }*/

function settabledata(map) {
    $.each(map, function (sk, sv) {
        var vlen = 0;
        $.each(sv, function (k, v) {
            vlen++;
        });
        var tdf = 0;
        $.each(sv, function (k, v) {
            var summiles = 0;
            var eventnum = 0;
            for (var i in v) {
                var obj = v[i];
                summiles = summiles + obj.miles;
                eventnum = eventnum + obj.eventnum;
            }
            if (tdf == 0) {
                var tr1data = $("<tr class='temptr'><td  rowspan='" + vlen + "'>" + sk + "</td><td>" + k + "</td><td>" + summiles + "</td><td>" + eventnum + "</td></tr>");
                $("#t1").append($(tr1data));
            } else {
                var tr1data = $("<tr class='temptr' ><td>" + k + "</td><td>" + summiles + "</td><td>" + eventnum + "</td></tr>");
                $("#t1").append($(tr1data));
            }
            tdf++;
        });
    });
}
