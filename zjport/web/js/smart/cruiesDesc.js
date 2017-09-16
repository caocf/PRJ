var shipOrbitmap;//轨迹地图
/**
 * Created by Will on 2016/10/20.
 */
$(document).ready(function () {
    loadPage(localStorage.cruiseId);
});

var loadPage = function (id) {
    console.log(id);
    $.ajax({
        async: false,
        url: "../smart/queryCruiseDesc",
        data: {
            cruiseId: id
        },
        dataType: "json",
        type: "post",
        success: function (data) {
            setValue(data.map);
        },
        error: function (xOption, status) {
            console.error(status);
            console.error(JSON.stringify(xOption));
            //console.error(error);
        }
    });
}
var setValue = function (data) {
    var user = data.user;
    var photos = data.photos;
    var cruise = data.cruise;
    console.log(photos);
    clearAll();
    $("#photo_list").empty();
    for (var i=0;i<photos.length;i++) {
        //TODO 照片放置
        $("#photo_list").append(
            "<div style='float: left;width: 100%;padding:5px;background: white;box-shadow: 0 0 3px #ccc;'>" +
            "<img src='.."+photos[i].stSource+"' alt='视频截图' width='100%' height='400px;'>" +
            "<div style='float: left;line-height: 30px;width: 100%;padding-left:5px;'>" +
            "<span style='color:#999;float: left;'><i class='fa fa-clock-o'></i>&nbsp;"+photos[i].dtCurise+"</span>" +
            "<span style='color:#999;float: left;margin-left:15px;'><i class='fa fa-video-camera'></i>&nbsp;"+photos[i].stCruiseAddress+"</span>" +
            "</div>" +
            "<p style='padding-left:5px;line-height: 30px;'>"+photos[i].stPhotoDescribe+"</p>" +
            "</div>"
        );
    }

    $("#shipName").html("<div class='datadiv'>无</div>");
    $("#point").html("<div class='datadiv'>" + cruise.stCruiseFrom + " 至 " + cruise.stCruiseTo + "</div>");
    $("#curiesTime").html("<div class='datadiv'>" + cruise.dtCuriseBegin + " 至 " + (cruise.dtCuriseEnd != null ? cruise.dtCuriseEnd : "") + "</div>");
    $("#useTime").html("<div class='datadiv'>" + (cruise.dtCuriseEnd != null ? moment(cruise.dtCuriseEnd).diff(moment(cruise.dtCuriseBegin), "hour") : "") + "小时</div>");
    $("#userNname").html("");
    $("#datagkdiv").text(cruise.stCuriseContent != '' ? cruise.stCuriseContent : "--");
    $("#userNname").html("<div class='datadiv'>" + user.stUserName + "</div>");
    shipOrbitmapmake();
}
var clearAll = function () {
    //$("#photo_list").html("");
    $("#shipName").html("");
    $("#point").html("");
    $("#curiesTime").html("");
    $("#useTime").html("");
    $("#userNname").html("");
}
// 船舶轨迹地图生成
function shipOrbitmapmake() {
    shipOrbitmap = new WebtAPI.WMap($$("mapdiv"));
    // var lonlat = new WebtAPI.LonLat(120.125122, 30.916621);
    var lonlat = new WebtAPI.LonLat(120.12142, 30.753567);
    // var lonlat = new WebtAPI.LonLat(120.270266,30.920194);
    shipOrbitmap.setCenterByLonLat(lonlat, 6);
}