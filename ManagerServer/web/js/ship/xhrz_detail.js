$(document).ready(function () {
    $("#search_li").addClass("active");
    $("#xhrz_li").addClass("active");
    init();
})

function init() {
    var id = $("#kqid").val();
    $.ajax({
        url: 'showxhrz',
        type: "post",
        dataType: "json",
        data: {
            "id": id
        },
        success: function (data) {
            var obj = data.map.xhobj;
            var evt = data.map.xhevent;
            if (isnull(obj) != '') {
                $("#smember").text(obj.member);
                $("#stools").text(obj.tools);
                $("#sarea").text(obj.area);
                $("#smiles").text(obj.miles);
                $("#seventnum").text(obj.eventnum);
                $("#sstarttime").text(obj.starttime);
                var map = new WebtAPI.WMap(WebtAPI.Util.getElement("mapdiv"));
                var jiaxing = new WebtAPI.LonLat(120.748238, 30.769126);
                map.setCenterByLonLat(jiaxing, 12);
                if (evt != null && evt.length > 0) {
                    for (var i in evt) {
                        var temptr = $("<tr></tr>");
                        var eobj = evt[i];
                        var temptd = $("<td>" + eobj.title + "</td><td>" + eobj.content + "</td><td>" + eobj.location + "</td><td>" + eobj.recordtime + "</td><td><a onclick='viewfj(" + eobj.id + ")'>附件查看</a></td>");
                        temptr.append($(temptd));
                        $("#eventtable").append($(temptr));
                    }
                }
            }
        }
    });
}

function viewfj(id) {
    $("#picdiv").empty();
    $.ajax({
        url: 'showxhfj',
        type: 'post',
        dataType: 'json',
        data: {
            'id': id
        },
        success: function (data) {
            $("#picmodal").modal("show");
            var objs = data.obj;
            $("#picdiv").append($('<div id="slides"></div>'));
            for (var i in objs) {
                var obj = objs[i];
                $("#slides").append($('<img src="../' + obj.dir + '" style="height: 100%;width: 100%" onclick="downloadpic(' + obj.id + ')">'));
            }
        },
        complete: function () {
            $('#slides').slidesjs({
                play: {
                    active: true,
                    auto: true,
                    interval: 4000,
                    swap: true
                }
            });
        }
    });
}

function downloadpic(id) {
    window.location.href = $("#basePath").val() + "shipdownload?id=" + id;
}

// 船舶轨迹地图生成
function shipOrbitmapmake() {
    var lonlat = new WebtAPI.LonLat(bd09togcj02(120.12142, 30.753567)[0],
        bd09togcj02(120.12142, 30.753567)[1]);
    shipOrbitmap.setCenterByLonLat(lonlat, 6);
}
// 定义一些常量
var x_PI = 3.14159265358979324 * 3000.0 / 180.0;
var PI = 3.1415926535897932384626;
var a = 6378245.0;
var ee = 0.00669342162296594323;
/**
 * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换 即 百度 转 谷歌、高德
 *
 * @param bd_lon
 * @param bd_lat
 * @returns {*[]}
 */
function bd09togcj02(bd_lon, bd_lat) {
    var x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    var x = bd_lon - 0.0065;
    var y = bd_lat - 0.006;
    var z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
    var theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
    var gg_lng = z * Math.cos(theta);
    var gg_lat = z * Math.sin(theta);
    return [gg_lng, gg_lat]
}
/**
 * WGS84转GCj02
 *
 * @param lng
 * @param lat
 * @returns {*[]}
 */
function wgs84togcj02(lng, lat) {
    if (out_of_china(lng, lat)) {
        return [lng, lat]
    } else {
        var dlat = transformlat(lng - 105.0, lat - 35.0);
        var dlng = transformlng(lng - 105.0, lat - 35.0);
        var radlat = lat / 180.0 * PI;
        var magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        var sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        var mglat = lat + dlat;
        var mglng = lng + dlng;
        return [mglng, mglat]
    }
}
function transformlat(lng, lat) {
    var ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng
        * lat + 0.2 * Math.sqrt(Math.abs(lng));
    ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
    ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
    ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
    return ret
}
function transformlng(lng, lat) {
    var ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1
        * Math.sqrt(Math.abs(lng));
    ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
    ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
    ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0
            * PI)) * 2.0 / 3.0;
    return ret
}
/**
 * 判断是否在国内，不在国内则不做偏移
 *
 * @param lng
 * @param lat
 * @returns {boolean}
 */
function out_of_china(lng, lat) {
    return (lng < 72.004 || lng > 137.8347)
        || ((lat < 0.8293 || lat > 55.8271) || false);
}