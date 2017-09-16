/**
 * Created by Will on 2016/9/30.
 */
var shipOrbitmap;//轨迹地图
var shiplayergreen;//船舶绿色图标图层
var videolayer;//视频图层
$(document).ready(function () {
    $("#smart").addClass("active");
    $("#electronicCruise_id").addClass("active");
    $("#rjjh_li").addClass("active");
    $("#capture").on("click", captureImage);
    $("#saveCruisePhoto").on("click", saveCruisePhoto);
    $("#submit_b").on("click", endCruise);
    $("#cancel_b").on("click", function () {
        $("#content_div").hide();
    });
    //$("#upload_file").on("change",readFile);
    $("#cancel_img").on("click", function () {
        $("#upload_img").hide();
        $("#upload_file").show();
    });
    $("#upload_img").on("click", function () {
        $("#upload_file").click();
    });
    $("#upload_file").change(readFile);
    $("#videodiv").css('height',(document.body.clientHeight-150)+'px');
});
function shipOrbitmapmake() {
    shipOrbitmap = new WebtAPI.WMap($$("shipOrbitmap_div"));
    // var lonlat = new WebtAPI.LonLat(120.125122, 30.916621);
    var lonlat = new WebtAPI.LonLat(120.12142, 30.753567);
    // var lonlat = new WebtAPI.LonLat(120.270266,30.920194);
    shipOrbitmap.setCenterByLonLat(lonlat, 10);
    shiplayergreen = new WebtAPI.MarkerLayer("");
    videolayer = new WebtAPI.MarkerLayer("");
    shipOrbitmap.addLayer(shiplayergreen);
    shipOrbitmap.addLayer(videolayer);
    shipOrbitmap.events.register("zoomend", shipOrbitmap, zoomchange);
    getship(330000, true);
    addvideomarkers();
}
var isshowzb;
var shipmap = {};
var videomap = {};
function zoomchange(e) {
    isshowzb = shipOrbitmap.getZoom() >= 9;
    shiplayergreen.setVisibility(isshowzb);
}
//添加当前航段视频图标
function addvideomarkers(){
    var pointsstr = '';
    for (var i = 0; i < latlng.length; i++) {
        if (i == (latlng.length - 1)) {
            pointsstr += latlng[i].lng.toFixed(8) + ' ' + latlng[i].lat.toFixed(8);
        } else {
            pointsstr += latlng[i].lng.toFixed(8) + ' ' + latlng[i].lat.toFixed(8) + ',';
        }
    }
    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': pointsstr
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = "";
            jwd = data.response.result.point;
            var jwdsp = jwd.split(',');
            if (jwdsp != null && jwdsp.length > 0) {
                for (var i = 0; i < jwdsp.length; i++) {
                    var p = para[i];
                    var start = p.indexOf("<CameraName>");
                    var end = p.indexOf("</CameraName>");
                    var name = p.substr(start + 12, end - start + 11);
                    var lonlat = new WebtAPI.LonLat(jwdsp[i].split(' ')[0], jwdsp[i].split(' ')[1]);//生成点
                    var icon = new WebtAPI.WIcon("../image/smart/map_ic_movie_normal.png", new WebtAPI.Size(40, 40));//生成视频图片
                    var marker = new WebtAPI.WMarker(lonlat, icon);//生成标注
                    marker.name = name;
                    marker.i = i;
                    marker.register(
                        'mouseover',
                        function () {
                            var html = "<div >"
                                + this.name
                                + "</div>"
                            var popup = new WebtAPI.WPopup('neirongpop', this.getLonLat(), 200,
                                html, false);
                            popup.setToolbarDisplay(false);
                            popup.setOffset(60, 60);
                            shipOrbitmap.addPopup(popup);
                        });
                    marker.register('mouseout',
                        function () {
                            hidepupop();
                        });
                    marker.register('click',
                        function () {
                            changeVideo(this.i);
                        });
                    videomap[name] = marker;
                    videolayer.addMarker(marker);//添加标注到船标图层
                }
                carousel();
            } else {
                alert("坐标转化失败");
            }
        }
    })
}
/**
 * 获取地区船舶
 * @param areaid：地区编码
 * @param isfirst：是否第一次调用：判断是否加载推送
 * @param isfirst：是否第一次调用：判断是否加载推送
 */
function getship(areaid, isfirst) {
    areano = areaid;
    $.ajax({
        url: "../supervise/areaCbs",
        type: 'post',
        data: {
            'area': areano
        },
        dataType: "json",
        success: function (data) {
            shipmap = {};
            var list = data.records.data;
            if (list == null || list.length == 0) {
                return
            }
            var length200 = parseInt(list.length / 200);
            for (var i = 0; i <= length200; i++) {
                var ifts = false;
                var pointsstr = '';
                var endno = (1 + i) * 200;
                var list200 = [];
                var islastpasslonlat = false;
                if (i == length200) {
                    if (isfirst) {
                        ifts = true;
                    }
                    islastpasslonlat = true;
                    endno = list.length;
                }
                for (var j = i * 200; j < endno; j++) {
                    if (0 < parseFloat(list[j].longitude) && parseFloat(list[j].longitude) < 180 && -90 < parseFloat(list[j].latitude) && parseFloat(list[j].latitude) < 90) {
                        if (j == (list.length - 1)) {
                            pointsstr += list[j].longitude.toFixed(8) + ' ' + list[j].latitude.toFixed(8);
                        } else {
                            pointsstr += list[j].longitude.toFixed(8) + ' ' + list[j].latitude.toFixed(8) + ',';
                        }
                        list200.push(list[j]);
                    }
                }
                zbchange(pointsstr, list200, ifts, islastpasslonlat);
            }
        }
    });
}
/**
 * 坐标转换
 * @param pointsstr:坐标字符串
 * @param list：数据集合
 * @param ifts：是否推送
 * @param islastpasslonlat：是否当前区域最后一次推送点
 */
function zbchange(pointsstr, list, ifts, islastpasslonlat) {
    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': pointsstr
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = "";
            jwd = data.response.result.point;
            var jwdsp = jwd.split(',');
            if (jwdsp != null && jwdsp.length > 0) {
                for (var i = 0; i < jwdsp.length; i++) {
                    var shipiconjd = parseInt(list[i].cruisedirection) - (parseInt(list[i].cruisedirection)) % 10;//获取船向
                    if (shipiconjd == 0) {
                        shipiconjd = 360;
                    }
                    var lonlat = new WebtAPI.LonLat(jwdsp[i].split(' ')[0], jwdsp[i].split(' ')[1]);//生成点
                    var shipname = list[i].shipname;
                    var shipcolor = "green";
                    var shiplayer = shiplayergreen;
                    if (list[i].shiptype == 2) {
                        shipcolor = "red";
                    }
                    var icon = new WebtAPI.WIcon("../image/smart/ship/" + shipcolor + "/small/" + shipiconjd + ".png", new WebtAPI.Size(40, 40));//生成船图片
                    var marker = new WebtAPI.WMarker(lonlat, icon);//生成标注
                    marker.name = shipname;
                    marker.register(
                        'mouseover',
                        function () {
                            var html = "<div >"
                                + this.name
                                + "</div>"
                            var popup = new WebtAPI.WPopup('neirongpop', this.getLonLat(), 200,
                                html, false);
                            popup.setToolbarDisplay(false);
                            popup.setOffset(60, 60);
                            shipOrbitmap.addPopup(popup);
                        });
                    marker.register('mouseout',
                        function () {
                            hidepupop();
                        });
                    shipmap[list[i].shipname] = marker;
                    shiplayer.addMarker(marker);//添加标注到船标图层
                }
                if (islastpasslonlat) {
                    zoomchange();
                }
                if (ifts) {
                    sschange();
                }
            } else {
                alert("坐标转化失败");
            }
        }
    });
}
var webSocket = null;
/**
 * 实时船舶更新
 * @returns {boolean}
 */
function sschange() {
    if (!window.WebSocket) {
        alert("您的浏览器不支持websocket！");
        return false;
    }
    webSocket = new WebSocket("ws://10.100.70.129:8081/ZjGz/ws");
    // 收到服务端消息
    webSocket.onmessage = function (msg) {
        if (isshowzb) {
            var mess = msg.data;
            var ssmarkers = mess.split(';');
            var areamarkers = [];
            if (areano == 330000) {
                for (var i = 0; i < ssmarkers.length; i++) {
                    if (ssmarkers[i].split(',')[1] > 180 || ssmarkers[i].split(',')[2] > 90) {
                    } else {
                        areamarkers.push(ssmarkers[i]);
                    }
                }
            } else {
                for (var i = 0; i < ssmarkers.length; i++) {
                    if (ssmarkers[i].split(',')[4] == areano) {
                        if (ssmarkers[i].split(',')[1] > 180 || ssmarkers[i].split(',')[2] > 90) {
                        } else {
                            areamarkers.push(ssmarkers[i]);
                        }
                    }
                }
            }
            ssdatachuli(areamarkers);
        }
    }
}
/**
 * 实时数据处理
 * @param areamarkers：该地区数据集合
 */
function ssdatachuli(areamarkers) {
    var pointsstr = '';
    for (var i = 0; i < areamarkers.length; i++) {
        if (i == (areamarkers.length - 1)) {
            pointsstr += parseFloat(areamarkers[i].split(',')[1]).toFixed(8) + ' ' + parseFloat(areamarkers[i].split(',')[2]).toFixed(8);
        } else {
            pointsstr += parseFloat(areamarkers[i].split(',')[1]).toFixed(8) + ' ' + parseFloat(areamarkers[i].split(',')[2]).toFixed(8) + ',';
        }
    }
    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': pointsstr
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = data.response.result.point;
            var jwdsp = jwd.split(',');
            var lefttop = shipOrbitmap.getLeftTopLonLat();
            var rhghtbottom = shipOrbitmap.getRightBottomLonLat();
            for (var i = 0; i < jwdsp.length; i++) {
                if (jwdsp[i].split(' ')[0] >= lefttop.lon && jwdsp[i].split(' ')[0] <= rhghtbottom.lon && jwdsp[i].split(' ')[1] <= lefttop.lat && jwdsp[i].split(' ')[1] >= rhghtbottom.lat) {
                    var lonlat = new WebtAPI.LonLat(jwdsp[i].split(' ')[0], jwdsp[i].split(' ')[1]);//生成点
                    var shipiconjd = parseInt(areamarkers[i].split(',')[3]) - (parseInt(areamarkers[i].split(',')[3])) % 10;//获取船向
                    if (shipiconjd == 0) {
                        shipiconjd = 360;
                    }
                    var shipcolor = "green";
                    var shiplayer = shiplayergreen;
                    if (areamarkers[i].split(',')[5] == 2) {
                        shipcolor = "red";
                    }
                    var icon = new WebtAPI.WIcon("../image/smart/ship/" + shipcolor + "/small/" + shipiconjd + ".png", new WebtAPI.Size(40, 40));//生成船图片
                    var marker = new WebtAPI.WMarker(lonlat, icon);//生成标注
                    if (shipmap.hasOwnProperty(areamarkers[i].split(',')[0])) {
                        shiplayer.removeMarker(shipmap[areamarkers[i].split(',')[0]]);
                        //shipmap[areamarkers[i].split(',')[0]].moveToLonlat(lonlat);
                    }
                    marker.name = areamarkers[i].split(',')[0];
                    marker.register(
                        'mouseover',
                        function () {
                            var html = "<div >"
                                + this.name
                                + "</div>"
                            var popup = new WebtAPI.WPopup('neirongpop', this.getLonLat(), 200,
                                html, false);
                            popup.setToolbarDisplay(false);
                            popup.setOffset(60, 60);
                            shipOrbitmap.addPopup(popup);
                        });
                    marker.register('mouseout',
                        function () {
                            hidepupop();
                        });
                    shipmap[areamarkers[i].split(',')[0]] = marker
                    shiplayer.addMarker(marker);//添加标注到船标图层
                }
            }
        }
    })
}
function hidepupop() {
    shipOrbitmap.clearPopups();
}
/**
 * 结束巡航
 */
var endCruise = function () {
    var cruiseId = $("#cruiseId").val();
    var content = $("#cruiseContent").val();
    $.ajax({
        async: false,
        url: "../smart/end",
        data: {
            stCuriseMiddleId: cruiseId,
            stCuriseContent: content
        },
        dataType: "json",
        type: "post",
        success: function (data) {
            console.error(JSON.stringify(data));
            $('#endmodal').modal('hide');
            alert("提交成功");
            window.location.href="./electronicCruise.jsp";
        },
        error: function (xOption, status) {
            console.error(status);
            console.error(JSON.stringify(xOption));
            //console.error(error);
            $("#content_div").hide();
        }
    });

}
/**
 * 暂停巡航
 */
var suspensionCruise = function () {
}

/**
 * 截图
 */
var captureImage = function () {
    var scale = 0.25;
    var video = $("#video").get(0);
    var output = $("#output");
    output.children().remove();
    var canvas = document.createElement("canvas");
    canvas.id = "canvasId";
    canvas.width = video.videoWidth * scale;
    canvas.height = video.videoHeight * scale;
    var ctx = canvas.getContext('2d');
    ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
    //console.error(img.src);

    var img = document.createElement("img");
    img.src = canvas.toDataURL("image/png", 1.0);
    output.prepend(img);
}
/**
 * 提交截图
 */
var saveCruisePhoto = function () {
    var base64 = $("#upload_img")[0].src;
    var curiseId = $("#cruiseMiddleId").val();
    //console.error(base64);
    var context = $("#photo_context").val();
    $.ajax({
        async: false,
        url: "../smart/saveCruisePhoto",
        data: {
            cruiseId: curiseId,
            picBs64: base64,
            describe: context,
            address: ""
        },
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#photo_context").val("");
            $("#chart_list").show();
                $("#sjdiv").append(
                "<div style='padding:10px;height: 300px;width:400px;float:left;background: white;margin-left: 10px;box-shadow: 0 0 3px #ccc'>"+
                "<div style='width: 100%;height: 170px;margin-bottom: 10px;'>"+
                "<img style='width: 100%;height: 170px' src='../"+data.obj.stSource+"' alt='图片'>"+
                "</div>"+
                "<span class='float-left' style='color:#999;'><i class='fa fa-clock-o'></i>&nbsp;"+data.obj.dtCurise+"</span>"+
                //"<span class='float-right' style='color:blue;'><i class='fa fa-clock-o'></i>&nbsp;编辑</span>"+
                "<br/>"+
                "<br/>"+
                //"<p style='color:#999;width: 100%;'><i class='fa fa-video-camera'></i>&nbsp;"++"</p>"+
                "<span class='float-left'>"+isnull(data.obj.stPhotoDescribe,'--',true)+"</span>"+
                "</div>"
                );
            var width=parseInt($('#sjdiv').css('width'))+400;
            $('#sjdiv').css('width',width+'px');
            $("#upload_img").attr('src','');
            $("#upload_file").empty();
            $('#videomodal').modal('hide');
            //TODO 数据加载到chart_list最上方
        },
        error: function (xOption, status) {
            console.error(status);
            console.error(JSON.stringify(xOption));
            //console.error(error);
        }
    });
}

var readFile = function () {
    var file = $("#upload_file")[0].files[0];
    if (!/image\/\w+/.test(file.type)) {
        alert("选择图片");
    } else {
        var read = new FileReader();
        read.readAsDataURL(file);
        read.onload = function (e) {
            $("#upload_img").attr("src", this.result);
        }
    }

}

//空值替换 str验证字符串 ，isnullstr空值返回字符串，islong是否验证长度
function isnull(str, isnullstr, islong) {
    var isnull = isnullstr;
    if (str == null || str == '' || str == 'null' || str == undefined) {
        return isnull;
    } else {
        if (islong) {
            if (str.length >= 20) {
                return "<abbr title='" + str + "'>" + str.substr(0, 20) + "</abbr>";
            }
        }
        return str;
    }
}