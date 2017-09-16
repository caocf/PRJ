var shipgzmap;//船舶跟踪地图
var shipgzmarker;//船舶跟踪船标图层
$(document).ready(function () {
    shipOrbitmapmake();
})
function shipOrbitmapmake() {
    shipgzmap = new WebtAPI.WMap($$("shipgzmap_div"));
    // var lonlat = new WebtAPI.LonLat(120.125122, 30.916621);
    var lonlat = new WebtAPI.LonLat(120.12142, 30.753567);
    // var lonlat = new WebtAPI.LonLat(120.270266,30.920194);
    shipgzmap.setCenterByLonLat(lonlat, 11);
    shipgzmarker = new WebtAPI.MarkerLayer("");
    shipgzmap.addLayer(shipgzmarker);
    getshipgzdata();
    window.setInterval("getshipgzdata()",15000);
}

//获取船舶跟踪信息
function getshipgzdata() {
    $.ajax({
        url: 'supervise/cbgz',
        type: 'post',
        dataType: 'json',
        data: {
            'cbmc': $("#shipname").val()
        },
        success: function (data) {
            $("#cbgztitle").text($("#shipname").val() + "，速度：" + data.map.ssxx.speed + "公里/小时");
            if (data.map.ql == '') {
                $("#cbgzredword").hide();
            } else {
                $("#cbgzredword").show();
                $("#cbgzredword").text("预计30分钟内抵达" + data.map.ql);
            }
            $("#sendbtn").attr('onclick',"sendmessage('"+data.map.ssxx.speed+"','"+data.map.ql+"')");
            cbgzzbchange(data.map.ssxx);
        }
    });
}
//船舶跟踪坐标转换
function cbgzzbchange(list) {
    var pointsstr = parseFloat(list.longitude).toFixed(8) + ' ' + parseFloat(list.latitude).toFixed(8);
    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': pointsstr
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = data.response.result.point.split(' ');
            var lonlat = new WebtAPI.LonLat(jwd[0], jwd[1]);//生成点
            var ssjd = list.shipdirection;
            shipgzmarker.clearMarkers();
            if (ssjd > 360) {
                ssjd -= 360;
            }
            var shipiconjd = parseInt(ssjd) - (parseInt(ssjd)) % 10;//获取船向
            if (shipiconjd == 0) {
                shipiconjd = 360;
            }
            var shipcolor = "green";
            if (list.shiptype == 2) {
                shipcolor = "red";
            }
            var icon = new WebtAPI.WIcon("image/smart/ship/" + shipcolor + "/small/" + shipiconjd + ".png", new WebtAPI.Size(50, 50));//生成船图片
            var marker = new WebtAPI.WMarker(lonlat, icon);//生成标注
            shipgzmarker.addMarker(marker);
            shipgzmap.panToLonLat(lonlat);
            shipgzmap.zoomTo(12);
        }
    })
}
//发送短信
function sendmessage(hs,qlmc){
    $.ajax({
        url: 'supervise/cbyj',
        type: 'post',
        dataType: 'json',
        data: {
            'cbmc': $("#shipname").val(),
            'speed':hs,
            'qlmc':qlmc,
            'phone':15040544658
        },
        success: function (data) {
            alert('发送成功');
        }
    })
}