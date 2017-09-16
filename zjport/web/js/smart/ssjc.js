var shipOrbitmap;//轨迹地图
var shipgzmap;//船舶跟踪地图
var shipgzmarker;//船舶跟踪船标图层
var shiplayergreen;//船舶绿色图标图层
var smallshipimglayer;//小船图标图层
var areano;//地区码
var gjlayer;//轨迹图层
var gjmarkerlayer;//轨迹图层
var qygjlayer;//区域轨迹图层
var flowlayer;//流量图层
var swlayer;//水位图层
var areazb = {};
var listtype;//列表类型 1：船舶
var cbgzxh;//船舶跟踪循环方法
var isxdqu=true;//是否选定区域
//    [
//    '120.275098,30.615228',
//    '120.163997,30.245753',
//    '120.887190,30.660319',
//    '119.889546,30.759837',
//    '120.622261,29.753963'
//]//地区坐标中心点
$(document).ready(function () {
    $("#smart").addClass("active");
    $("#ssjc_li").addClass("active");
    $("#clearbtn").bind("click", function () {
        $("#clearbtn").hide();
        $("#cmhinput").val('');
    })
    $("#cmhinput").bind({
        'input propertychange': function () {
            if ($('#cmhinput').val() != '' && $('#cmhinput').val() != null) {
                $("#clearbtn").show();
                if (listtype == 1) {
                    getcbmclist();
                }
            }
        },
    });
    $('#gzcbbtn').on('click', function () {
        $.ajax({
            url: '../supervise/cbmc',
            type: 'post',
            dataType: 'json',
            data : {
                'cbmc' : shipcbmc
            },
            success: function (data) {
                window.open ("../cbgz.jsp", "船舶跟踪", "height=360, width=500, top=30, left=30, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
            }
        })
    })
    //$('#cbgzModal').on('shown.bs.modal', function () {
    //    getshipgzdata();
    //    cbgzxh=window.setInterval("getshipgzdata()",15000);
    //})
    //$("#cbgjname").bind({
    //    'input propertychange': function() {
    //        if($('#cbgjname').val()!=''&&$('#cbgjname').val()!=null){
    //            getcbgjlist();
    //        }
    //    },
    //    'blur':function(){
    //        if(cmbdivfocus==0){
    //            $("#cbgjnamelist").hide();
    //        }
    //    }
    //});
    $(".togglebtn").click(function () {
        $(".togglebtn").toggle();
        $("#cbgknrdiv").slideToggle();
    })
    $("#shipinfodiv_closebtn").click(function () {
        $("#shipinfodiv").hide();
    })
    $("#cbgjclosebtn,#cbgkclosebtn,#swllinfodiv_closebtn,#hdmd_closebtn,#qugjclosebtn,#qygjlistclosebtn,#shipinfodiv_closebtn").click(function () {
        $(this).parent().parent().hide();
        shipOrbitmap.unregisterEvents("click",hdmdclick);
        shipOrbitmap.unregisterEvents("click",qygjclick);
        if ($(this).attr('id') == 'cbgjclosebtn' || $(this).attr('id') == 'hdmd_closebtn'||$(this).attr('id') == 'qugjclosebtn'||$(this).attr('id') == 'shipinfodiv_closebtn') {
            gjlayer.removeAllFeatures();
            gjlayer.setVisibility(false);
            gjmarkerlayer.setVisibility(false);
            gjmarkerlayer.clearMarkers();
        }
    });
    $(".tabdiv").click(function () {
        typedatacheck(this);
    });
    $("#showhdmddivbtn").click(function () {
        onlyshow();
        $("#hdmddiv").show();
        $("#mdworddiv").hide();
        $("#jlinput").val(1);
        gjlayer.setVisibility(true);
        document.getElementById('xdcbox').checked = false;
    });
    $("#xdcbox").click(function () {
        if (isxdqu) {
            isxdqu=false;
            $(this).removeClass('btn-primary');
            $(this).addClass('btn-success');
            //shipOrbitmap.events.register(
            //    "click", this,hdmdclick
            //)
            shipOrbitmap.registerEvents("click", hdmdclick);
        } else {
            isxdqu=true;
            $(this).removeClass('btn-success');
            $(this).addClass('btn-primary');
            shipOrbitmap.unregisterEvents("click",hdmdclick);
        }
    })
    //$(".layerbtn").bind({
    //    'click': function () {
    //        var imgsrc = $(this).find('img').attr('src');
    //        if ($(this).hasClass('layerbtnblue')) {
    //            imgsrc = imgsrc.replace('blue', 'black');
    //            $(this).find('img').attr('src', imgsrc);
    //            $(this).removeClass('layerbtnblue');
    //            switch ($.trim($(this).text())) {
    //                case '水位':
    //                    swlayer.setVisibility(false);
    //                    break;
    //                case '流量':
    //                    flowlayer.setVisibility(false);
    //                    break;
    //                case '视频':
    //                    videolayer.setVisibility(false);
    //                    break;
    //                default :
    //                    break;
    //            }
    //        } else {
    //            imgsrc = imgsrc.replace('black', 'blue');
    //            $(this).find('img').attr('src', imgsrc);
    //            $(this).addClass('layerbtnblue');
    //            switch ($.trim($(this).text())) {
    //                case '水位':
    //                    swlayer.setVisibility(true);
    //                    break;
    //                case '流量':
    //                    flowlayer.setVisibility(true);
    //                    break;
    //                case '视频':
    //                    videolayer.setVisibility(true);
    //                    break;
    //                default :
    //                    break;
    //            }
    //        }
    //    }
    //})
    areainfo();
})
/**
 * 获取区域及中心坐标
 */
function areainfo() {
    $.ajax({
        url: '../supervise/areas',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            $("#areaul").empty();
            $(data.records.data).each(function (index, item) {
                areazb[item.stAreaCode] = {
                    'userareaname': item.stAreaName,
                    'lonlat': data.obj[index][0] + ',' + data.obj[index][1]
                };
                $("#areaul").append(
                    "<li><a onclick=\"pantolonlat(" + item.stAreaCode + ")\">" + item.stAreaName + "</a></li>"
                );
            });

            shipOrbitmapmake();
        }
    })
}

/**
 * 定位搜索结果
 * @param pointstr:坐标字符串
 * @param type：类型 1船舶 2水位 3流量 4视频 5当前区域船舶
 */
function dwmarker(pointstr, type, markname) {
    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': pointstr
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = data.response.result.point;
            var lonlat = new WebtAPI.LonLat(jwd.split(' ')[0], jwd.split(' ')[1]);//生成点
            switch (parseInt(type)) {
                case 1:
                    getship(userareaNo, userareaname, lonlatNo);
                    break;
                case 2:
                    if (!$(".layerbtn:eq(1)").hasClass('layerbtnblue')) {
                        $(".layerbtn:eq(1)").click();
                    }
                    break;
                case 3:
                    if (!$(".layerbtn:eq(2)").hasClass('layerbtnblue')) {
                        $(".layerbtn:eq(2)").click();
                    }
                    break;
                case 4:
                    if (!$(".layerbtn:eq(3)").hasClass('layerbtnblue')) {
                        $(".layerbtn:eq(3)").click();
                    }
                    break;
                default :
                    break;
            }
            shipOrbitmap.zoomTo(12);
            shipOrbitmap.panToLonLat(lonlat);
            var html = "<div >"
                + markname
                + "</div>"
            var popup = new WebtAPI.WPopup('neirongpop', lonlat, 200,
                html, false);
            popup.setToolbarDisplay(false);
            popup.setOffset(60, 60);
            shipOrbitmap.addPopup(popup);
        }
    })
}
/**
 * 0-1数据处理
 * @param sf：0-1数据 0：否 1是
 */
function sf01(sf) {
    if (sf == 0)
        return "<span class='ssjgshipspan-green'>否</span>"
    else
        return "<span class='ssjgshipspan'>是</span>"
}
var userareaNo = 330000;//初始用户地区编码
// 船舶轨迹地图生成
function shipOrbitmapmake() {
    shipOrbitmap = new WebtAPI.WMap($$("shipOrbitmap_div"));
    shipgzmap = new WebtAPI.WMap($$("shipgzmap_div"));
    // var lonlat = new WebtAPI.LonLat(120.125122, 30.916621);
    var lonlat = new WebtAPI.LonLat(120.12142, 30.753567);
    // var lonlat = new WebtAPI.LonLat(120.270266,30.920194);
    shipOrbitmap.setCenterByLonLat(lonlat, 5);
    shipgzmap.setCenterByLonLat(lonlat, 11);
    shiplayergreen = new WebtAPI.MarkerLayer("");
    swlayer = new WebtAPI.MarkerLayer("");
    flowlayer = new WebtAPI.MarkerLayer("");
    shipgzmarker = new WebtAPI.MarkerLayer("");
    smallshipimglayer = new WebtAPI.MarkerLayer("");
    gjmarkerlayer = new WebtAPI.MarkerLayer("");
    gjlayer = new WebtAPI.Layer.Vector("");
    qygjlayer = new WebtAPI.Layer.Vector("");
    shiplayergreen.setVisibility(false);
    swlayer.setVisibility(false);
    flowlayer.setVisibility(false);
    gjlayer.setVisibility(false);
    shipgzmap.addLayer(shipgzmarker);
    //shipOrbitmap.mouseDrag(false);
    shipOrbitmap.addLayer(flowlayer);
    shipOrbitmap.addLayer(swlayer);
    //shipOrbitmap.addLayer(qygjlayer);
    shipOrbitmap.addLayer(smallshipimglayer);
    shipOrbitmap.addLayer(gjlayer);
    shipOrbitmap.addLayer(shiplayergreen);
    shipOrbitmap.addLayer(gjmarkerlayer);
    shipOrbitmap.events.register("zoomend", shipOrbitmap, zoomchange);
    //var sf = new WebtAPI.Control.SelectFeature(qygjlayer);
    //shipOrbitmap.addControl(sf );
    //sf.activate();
    //var sf = new OpenLayers.Control.SelectFeature(qygjlayer);
    //sf.events.register("boxselectionend", this, function(){
    //    alert(1);
    //});
    //shipOrbitmap.addControl(sf );
    //sf.activate();
    if ($("#userareaNo").val() == null || $("#userareaNo").val() == '' || $("#userareaNo").val() == 330000) {

    } else {
        $("#areabtn").text(areazb[$("#userareaNo").val()].userareaname);
        $("#areaallbtn").remove();
        $("#areabtn").show();
        userareaNo = $("#userareaNo").val();
    }
    console.log(userareaNo);
    getship(userareaNo, true);
    //getship(330600, '绍兴市', 4, true);
}
var isshowzb;
function zoomchange(e) {
    //if (gjlayer.getVisibility()) {
    //    return
    //}
    isshowzb = shipOrbitmap.getZoom() >= 9;
    if (isshowzb) {
        shiplayergreen.setVisibility(isshowzb);
        smallshipimglayer.setVisibility(false);
    } else {
        shiplayergreen.setVisibility(isshowzb);
        smallshipimglayer.setVisibility(true);
    }
}


var shipmap = {};//船标MAP数组
/**
 * 获取地区船舶
 * @param areaid：地区编码
 * @param isfirst：是否第一次调用：判断是否加载推送
 * @param isfirst：是否第一次调用：判断是否加载推送
 */
function getship(areaid, isfirst) {
    areano = areaid;
    $("#quyuname").text(areazb[areaid].userareaname);
    var areanolonlat = new WebtAPI.LonLat(areazb[areaid].lonlat.split(',')[0], areazb[areaid].lonlat.split(',')[1]);
    shipOrbitmap.panToLonLat(areanolonlat);
    $.ajax({
        url: "../supervise/areaCbs",
        type: 'post',
        data: {
            'area': areano
        },
        dataType: "json",
        success: function (data) {
            shipmap = {};
            shiplayergreen.clearMarkers();//清空船标
            smallshipimglayer.clearMarkers();//清空船标
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
 * 获取该地区船舶概况数据
 */
function cbgkdataget() {
    $.ajax({
        url: "../supervise/queryAreas",
        type: 'post',
        data: {
            'area': areano
        },
        dataType: "json",
        success: function (data) {
            onlyshow();
            $("#cbgktable").empty();
            $("#cbgkdiv").show();
            if (areano == 330000) {
                $("#cbgktable").append(
                    "<tr>" +
                    "<td class='col-sm-6 '>" +
                    "<div class='col-sm-3' style='color: rgb(0,103,172);text-align: left;padding: 0 10px;'>" +
                    "<h4 >" + data.obj[(data.obj.length - 1)].areaname + "</h4>" +
                    "<h4 >辖区</h4>" +
                    "</div>" +
                    "<div class='col-sm-3' style='text-align: center;padding: 0 10px;'>" +
                    "<h4>" + data.obj[(data.obj.length - 1)].count + "</h4>" +
                    "<h5>船舶总量（艘）</h5>" +
                    "</div>" +
                    "<div class='col-sm-3' style='text-align: center;padding: 0 10px;'>" +
                    "<h4>" + data.obj[(data.obj.length - 1)].wx + "</h4>" +
                    "<h5>危货船舶（艘）</h5>" +
                    "</div>" +
                    "<div class='col-sm-3' style='text-align: center;padding: 0 10px;'>" +
                    "<h4>" + data.obj[(data.obj.length - 1)].jzx + "</h4>" +
                    "<h5>集装箱船舶（艘）</h5>" +
                    "</div>" +
                    "</td>" +
                    "<td class='col-sm-6'></td>" +
                    "</tr>"
                );
                var cbgktr = $("<tr></tr>");
                for (var i = (data.obj.length - 2); i >= 0; i--) {
                    var inttype;
                    if ((data.obj.length - 2) % 2 == 0)
                        inttype = 0;
                    else
                        inttype = 1;
                    if (i % 2 == inttype) {
                        cbgktr = $("<tr></tr>");
                        cbgktr.append($(
                            "<td class='col-sm-6 '>" +
                            "<div class='col-sm-3' style='color: rgb(0,103,172);text-align: left;padding: 0 10px;'>" +
                            "<h4 >" + data.obj[i].areaname + "</h4>" +
                            "<h4 >辖区</h4>" +
                            "</div>" +
                            "<div class='col-sm-3' style='text-align: center;padding: 0 10px;'>" +
                            "<h4>" + data.obj[i].count + "</h4>" +
                            "<h5>船舶总量（艘）</h5>" +
                            "</div>" +
                            "<div class='col-sm-3' style='text-align: center;padding: 0 10px;'>" +
                            "<h4>" + data.obj[i].wx + "</h4>" +
                            "<h5>危货船舶（艘）</h5>" +
                            "</div>" +
                            "<div class='col-sm-3 cbgklefttd' style='text-align: center;padding: 0 10px;'>" +
                            "<h4>" + data.obj[i].jzx + "</h4>" +
                            "<h5>集装箱船舶（艘）</h5>" +
                            "</div>" +
                            "</td>"
                        ));
                        if (inttype == 0) {
                            if (i == 0) {
                                $("#cbgktable").append(cbgktr);
                            }
                        }
                    } else {
                        cbgktr.append($(
                            "<td class='col-sm-6 '>" +
                            "<div class='col-sm-3' style='color: rgb(0,103,172);text-align: left;padding: 0 10px;'>" +
                            "<h4 >" + data.obj[i].areaname + "</h4>" +
                            "<h4 >辖区</h4>" +
                            "</div>" +
                            "<div class='col-sm-3' style='text-align: center;padding: 0 10px;'>" +
                            "<h4>" + data.obj[i].count + "</h4>" +
                            "<h5>船舶总量（艘）</h5>" +
                            "</div>" +
                            "<div class='col-sm-3' style='text-align: center;padding: 0 10px;'>" +
                            "<h4>" + data.obj[i].wx + "</h4>" +
                            "<h5>危货船舶（艘）</h5>" +
                            "</div>" +
                            "<div class='col-sm-3' style='text-align: center;padding: 0 10px;'>" +
                            "<h4>" + data.obj[i].jzx + "</h4>" +
                            "<h5>集装箱船舶（艘）</h5>" +
                            "</div>" +
                            "</td>"
                        ));
                        $("#cbgktable").append(cbgktr);
                    }
                }
            } else {
                $("#cbgktable").append(
                    "<tr>" +
                    "<td class='col-sm-6 '>" +
                    "<div class='col-sm-3' style='color: rgb(0,103,172);text-align: left;padding: 0 10px;'>" +
                    "<h4 >" + data.obj.areaname + "</h4>" +
                    "<h4 >辖区</h4>" +
                    "</div>" +
                    "<div class='col-sm-3' style='text-align: center;padding: 0 10px;'>" +
                    "<h4>" + data.obj.count + "</h4>" +
                    "<h5>船舶总量（艘）</h5>" +
                    "</div>" +
                    "<div class='col-sm-3' style='text-align: center;padding: 0 10px;'>" +
                    "<h4>" + data.obj.wx + "</h4>" +
                    "<h5>危货船舶（艘）</h5>" +
                    "</div>" +
                    "<div class='col-sm-3' style='text-align: center;padding: 0 10px;'>" +
                    "<h4>" + data.obj.jzx + "</h4>" +
                    "<h5>集装箱船舶（艘）</h5>" +
                    "</div>" +
                    "</td>" +
                    "<td class='col-sm-6'></td>" +
                    "</tr>"
                );
            }
        }
    })
}

/**
 * 定位船舶
 * @param shipname：船舶名称
 * @param type：类型 1全区域船舶 5当前区域船舶
 */
function dwship(shipname, type) {
    $.ajax({
        url: "../supervise/cbwz",
        type: 'post',
        data: {
            'cbmc': shipname
        },
        dataType: "json",
        success: function (data) {
            var pointstr = parseFloat(data.obj.longitude).toFixed(6) + ' ' + parseFloat(data.obj.latitude).toFixed(6);
            dwmarker(pointstr, type, shipname);
        }
    })
}
function gjwarn(shipname) {
    $.ajax({
        url: "../supervise/cbgj",
        type: 'post',
        data: {
            'cbmc': shipname
        },
        dataType: "json",
        success: function (data) {
            window.location.href = "shipOrbit.jsp?hasship=1";
        }
    })
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
                    var smallicon = new WebtAPI.WIcon("../image/smart/point_normal.png", new WebtAPI.Size(8, 8));//生成小船标图片
                    var marker = new WebtAPI.WMarker(lonlat, icon);//生成标注
                    var smallmarker = new WebtAPI.WMarker(lonlat, smallicon);//生成标注
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
                    marker.register('click',
                        function () {
                            getshipByshipname(this.name, 2);
                        });
                    shipmap[list[i].shipname] = marker;
                    shiplayer.addMarker(marker);//添加标注到船标图层
                    smallshipimglayer.addMarker(smallmarker);//添加标注到船标图层
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
                    marker.register('click',
                        function () {
                            getshipByshipname(this.name, 2);
                        });
                    shipmap[areamarkers[i].split(',')[0]] = marker
                    shiplayer.addMarker(marker);//添加标注到船标图层
                }
            }
        }
    })
}

//webSocket.onclose=function(e){
//  alert('close');
//};


function hidepupop() {
    shipOrbitmap.clearPopups();
    shipgzmap.clearPopups();
}
//地区平移
function pantolonlat(areaCode) {
    $("#quyuname").text(areazb[areaCode].userareaname);
    var areanolonlat = new WebtAPI.LonLat(areazb[areaCode].lonlat.split(',')[0], areazb[areaCode].lonlat.split(',')[1]);
    shipOrbitmap.panToLonLat(areanolonlat);
}

//船舶列表刷新
function refreshshiplist() {
    if ($('#cmhinput').val() == '' || $('#cmhinput').val() == null) {
        getcblist();
    } else {
        getcbmclist();
    }
}
//船舶列表搜索框隐藏
function hidecblistdiv() {
    $('#cmhinput').val('');
    $('.selectshiplabel').remove();
    $('#cblistdiv').hide();
    $('#clearbtn').hide();
}
//获取船舶列表
function getcblist() {
    var lefttop = shipOrbitmap.getLeftTopLonLat();
    var rhghtbottom = shipOrbitmap.getRightBottomLonLat();
    listtype = 1;
    $("#datalisttitlename").text('船舶列表');
    $.ajax({
        url: "../supervise/cblist",
        type: 'post',
        data: {
            'area': areano,
            'minLon': lefttop.lon,
            'maxLon': rhghtbottom.lon,
            'minLat': rhghtbottom.lat,
            'maxLat': lefttop.lat
        },
        dataType: "json",
        success: function (data) {
            onlyshow();
            $('#cblistdiv,#listrefreshbtn,#cxkdiv').show();
            $('#cmhdiv').empty();
            $(data.records.data).each(function (index, item) {
                var cbtype = item.shiptype;
                var reddiv = '';
                if (cbtype == 2) {
                    reddiv = "<div style='background: red'>危</div>";
                }
                $("#cmhdiv").append(
                    "<label class='selectshiplabel' onclick=\"getshipByshipname('" + item.shipname + "',1)\">" +
                    "<span><i class='fa fa-ship'></i>&nbsp;" + item.shipname + "</span>" + reddiv +
                    "</label>"
                )
            })
        }
    })
}

/**
 * 船舶名实时监听
 */
function getcbmclist() {
    $.ajax({
        url: '../supervise/queryCbs',
        type: 'post',
        dataType: 'json',
        data: {
            'cbmc': $('#cmhinput').val(),
            'area': areano
        },
        success: function (data) {
            $('.selectshiplabel').remove();
            $(data.records.data).each(function (index, item) {
                var cbtype = item.shiptype;
                var reddiv = '';
                if (cbtype == 2) {
                    reddiv = "<div style='background: red'>危</div>";
                }
                $("#cmhdiv").append(
                    "<label class='selectshiplabel' onclick=\"getshipByshipname('" + item.shipname + "',1)\">" +
                    "<span><i class='fa fa-ship'></i>&nbsp;" + item.shipname + "</span>" + reddiv +
                    "</label>"
                )
            })
        }
    })
}


var zTreeObj;
// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
var nbsetting = {
    view: {
        nameIsHTML: true,
        showLine: false,
        showIcon: false,
        selectedMulti: false,
        dblClickExpand: false,
        addDiyDom: addDiyDom
    },
    data: {
        simpleData: {
            enable: true
        }
    }

};
function addDiyDom(treeId, treeNode) {
    var spaceWidth = 7;
    var switchObj = $("#" + treeNode.tId + "_switch"),
        icoObj = $("#" + treeNode.tId + "_ico");
    switchObj.remove();
    icoObj.before(switchObj);

    if (treeNode.level > 1) {
        var spaceStr = "<span style='display: inline-block;'></span>";
        switchObj.before(spaceStr);
    }
    if (treeNode.level > 0) {
        var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
        switchObj.before(spaceStr);
    }
    $(".ztree").find('a').removeAttr('title');
}
//水位,流量列表数据获取
function swdatalistget(type) {
    $.ajax({
        url: '../supervise/wftree',
        type: 'post',
        dataType: 'json',
        data: {
            'type': type,
            'area': areano
        },
        success: function (data) {
            onlyshow();
            listtype = 2;
            $('#cmhdiv').empty();
            $('#cblistdiv').show();
            $('#listrefreshbtn,#cxkdiv').hide();
            var treeul = $("<ul id='swllTree' class='ztree bluetree'></ul>");
            $('#cmhdiv').append(treeul);
            var nodes = [];
            if (type == 1) {
                $("#datalisttitlename").text('水位列表');
            } else {
                $("#datalisttitlename").text('流量列表');
            }
            $(data.obj).each(function (index, item) {
                var iconstr = '';
                var onclick = '';
                var cnt = '';
                if (item.type == 2) {
                    iconstr = "<i class='fa fa-tint'></i>";
                }
                if (item.type == 3) {
                    iconstr = "<i class='fa fa-bullseye'></i>";
                }
                if (item.type != 1) {
                    onclick = "onclick='dwswllmarker(" + item.id + "," + type + ")'";
                } else {
                    cnt = "（" + item.cnt + "）";
                }
                var node = {
                    "id": item.id,
                    "name": "<div " + onclick + "><span>" + iconstr + "&nbsp;" + item.name + cnt + "</span></div>",
                    "pId": item.pid,
                    "titleword": item.name,
                    "type": item.type
                };
                nodes.push(node);
            })
            zTreeObj = $.fn.zTree.init($("#swllTree"), nbsetting, nodes);
            getswllmarkerdata(type);
        }
    })
}
//获取水位，流量地图图标信息 1 水位 2 流量
function getswllmarkerdata(type) {
    $.ajax({
        url: '../supervise/wflist',
        type: 'post',
        dataType: 'json',
        data: {
            'type': type,
            'area': areano
        },
        success: function (data) {
            var layer;
            switch (parseInt(type)) {
                case 1:
                    layer = swlayer;
                    break;
                case 2:
                    layer = flowlayer;
                    break;
                default :
                    break;
            }
            swlayer.setVisibility(false);
            flowlayer.setVisibility(false);
            layer.setVisibility(true);
            layer.clearMarkers();
            $(data.records.data).each(function (index, item) {
                markerdatachange(item, type);
            })
        }
    })
}
/**
 * marker数据处理
 * @param item：数据
 * @param type：类型：1水位 2流量
 */
function markerdatachange(item, type) {
    var pointstr = item.jd + ' ' + item.wd;
    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': pointstr
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = data.response.result.point;
            var lonlat = new WebtAPI.LonLat(jwd.split(' ')[0], jwd.split(' ')[1]);//生成点
            var imgurl;
            var layer;
            switch (parseInt(type)) {
                case 1:
                    layer = swlayer;
                    imgurl = "../image/smart/map_ic_waterlevel_blue_normal.png";
                    break;
                case 2:
                    layer = flowlayer;
                    imgurl = "../image/smart/map_ic_observation_point_normal.png";
                    break;
                default :
                    break;
            }
            var icon = new WebtAPI.WIcon(imgurl, new WebtAPI.Size(32, 32));//生成船图片
            var marker = new WebtAPI.WMarker(lonlat, icon);//生成标注
            marker.name = item.name;
            marker.id = item.id;
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
                    dwswllmarker(this.id, type);
                });
            layer.addMarker(marker);//添加标注到船标图层
        }
    })
}
//定位水位，流量图标
function dwswllmarker(id, type) {
    $.ajax({
        url: '../supervise/wfdetail',
        type: 'post',
        dataType: 'json',
        data: {
            'type': type,
            'id': id
        },
        success: function (data) {
            $("#swllinfodiv").show();
            $("#swllinfodiv_table").empty();
            $("#swllinfodiv_name").text(isnull(data.obj.name, '--', true));
            if (data.obj.jd != 0 && data.obj.wd != 0) {
                hidepupop();
                var pointsstr = parseFloat(data.obj.jd).toFixed(8) + ' ' + parseFloat(data.obj.wd).toFixed(8);
                swlllistzbchange(pointsstr, isnull(data.obj.name, '--', true));
            }
            if (type == 1) {
                $("#swllinfodiv_table").append(
                    "<tr>" +
                    "<td width='90px;'>名称：</td>" +
                    "<td>" + isnull(data.obj.name, '--', true) + "</td>" +
                    "<td width='90px;'>类型：</td>" +
                    "<td>水位观测点</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td >当前水位：</td>" +
                    "<td>" + isnull(data.obj.dqsw, '--', true) + "米</td>" +
                    "<td >警戒水位：</td>" +
                    "<td style='color: #ff9900'>" + isnull(data.obj.jjsw, '--', true) + "米</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td >保证水位：</td>" +
                    "<td>" + isnull(data.obj.bzsw, '--', true) + "米</td>" +
                    "<td >更新日期：</td>" +
                    "<td>" + isnull(data.obj.gxsj, '--', true) + "</td>" +
                    "</tr>"
                );
            } else {
                $("#swllinfodiv_table").append(
                    "<tr>" +
                    "<td width='90px;'>名称：</td>" +
                    "<td>" + isnull(data.obj.name, '--', true) + "</td>" +
                    "<td width='90px;'>类型：</td>" +
                    "<td>流量观测点</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td >上行：</td>" +
                    "<td>" + isnull(data.obj.up, '--', true) + "艘</td>" +
                    "<td >下行：</td>" +
                    "<td>" + isnull(data.obj.down, '--', true) + "艘</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td >总流量：</td>" +
                    "<td>" + isnull(data.obj.zll, '--', true) + "艘</td>" +
                    "<td >更新日期：</td>" +
                    "<td>" + isnull(data.obj.gxsj, '--', true) + "</td>" +
                    "</tr>"
                );
            }
        }
    })
}
//水位流量坐标转换
function swlllistzbchange(pointsstr, name) {
    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': pointsstr
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = data.response.result.point.split(' ');
            var lonlat = new WebtAPI.LonLat(jwd[0], jwd[1]);
            shipOrbitmap.panToLonLat(lonlat);
            hidepupop();
            var html = "<div >"
                + name
                + "</div>"
            var popup = new WebtAPI.WPopup('neirongpop', lonlat, 200,
                html, false);
            popup.setToolbarDisplay(false);
            popup.setOffset(60, 60);
            shipOrbitmap.addPopup(popup);
        }
    })
}
//船舶列表坐标转换
function cblistzbchange(pointsstr, shipname) {
    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': pointsstr
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = data.response.result.point.split(' ');
            var lonlat = new WebtAPI.LonLat(jwd[0], jwd[1]);
            shipOrbitmap.panToLonLat(lonlat);
            shipOrbitmap.zoomTo(9);
            hidepupop();
            var html = "<div >"
                + shipname
                + "</div>"
            var popup = new WebtAPI.WPopup('neirongpop', lonlat, 200,
                html, false);
            popup.setToolbarDisplay(false);
            popup.setOffset(60, 60);
            shipOrbitmap.addPopup(popup);
        }
    })
}
/**
 * 获取船舶资料
 * @type: 调用源 1：列表调用 2：船标调用
 */
function getshipByshipname(shipname, type) {
    $.ajax({
        url: '../supervise/cbXx',
        type: 'post',
        dataType: 'json',
        data: {
            'cbmc': shipname
        },
        success: function (data) {
            if (data.resultcode == -1) {
                alert('暂无相关船舶信息');
                $("#shipinfodiv").hide();
                return
            }
            else {
                shipcbmc = shipname;
                if (type == 1) {
                    var pointsstr = parseFloat(data.map.ssxx.longitude).toFixed(8) + ' ' + parseFloat(data.map.ssxx.latitude).toFixed(8);
                    cblistzbchange(pointsstr, shipname);
                }
                $("#shipinfodiv").show();
                $("#shipinfodiv_table").empty();
                $(".ztcolordiv").remove();
                $("#shipinfodiv_shipname").text(shipname);
                if(data.map.jbxx==null||data.map.jbxx==''){
                    $("#shipinfodiv_table").append(
                        "<tr>" +
                        "<td width='90px;'>船名：</td>" +
                        "<td>--</td>" +
                        "<td width='90px;'>船籍港：</td>" +
                        "<td>--</td>" +
                        "</tr>" +
                        "<tr>" +
                        "<td >船长：</td>" +
                        "<td>--</td>" +
                        "<td >船宽：</td>" +
                        "<td>--</td>" +
                        "</tr>" +
                        "<tr>" +
                        "<td >型深：</td>" +
                        "<td>--</td>" +
                        "<td >总吨：</td>" +
                        "<td>--</td>" +
                        "</tr>" +
                        "<tr>" +
                        "<td >船舶类型：</td>" +
                        "<td>--</td>" +
                        "<td >所有人：</td>" +
                        "<td>--</td>" +
                        "</tr>" +
                        "<tr>" +
                        "<td >行驶速度：</td>" +
                        "<td>" + isnull(data.map.ssxx.speed, '--', true) + "公里/时</td>" +
                        "</tr>" +
                        "<tr>" +
                        "<td >更新日期：</td>" +
                        "<td>" + isnull(data.map.ssxx.shipdate, '--', true) + "</td>" +
                        "<td >联系电话：</td>" +
                        "<td>--</td>" +
                        "</tr>"
                    );
                }else{
                $("#shipinfodiv_table").append(
                    "<tr>" +
                    "<td width='90px;'>船名：</td>" +
                    "<td>" + isnull(data.map.jbxx.zwcm, '--', true) + "</td>" +
                    "<td width='90px;'>船籍港：</td>" +
                    "<td>" + isnull(data.map.jbxx.cjg, '--', true) + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td >船长：</td>" +
                    "<td>" + isnull(data.map.jbxx.cc, '--', true) + "米</td>" +
                    "<td >船宽：</td>" +
                    "<td>" + isnull(data.map.jbxx.ck, '--', true) + "米</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td >型深：</td>" +
                    "<td>" + isnull(data.map.jbxx.xs, '--', true) + "米</td>" +
                    "<td >总吨：</td>" +
                    "<td>" + isnull(data.map.jbxx.zdw, '--', true) + "吨</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td >船舶类型：</td>" +
                    "<td>" + isnull(data.map.jbxx.cblx, '--', true) + "</td>" +
                    "<td >所有人：</td>" +
                    "<td>" + isnull(data.map.jbxx.syr, '--', true) + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td >行驶速度：</td>" +
                    "<td>" + isnull(data.map.ssxx.speed, '--', true) + "公里/时</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td >更新日期：</td>" +
                    "<td>" + isnull(data.map.ssxx.shipdate, '--', true) + "</td>" +
                    "<td >联系电话：</td>" +
                    "<td>" + isnull(data.map.jbxx.jyrdh, '--', true) + "</td>" +
                    "</tr>"
                );
                }
                if (data.map.ssxx.shiptype == 2) {
                    $("#shipinfodiv_table").after(
                        "<div class='ztcolordiv' style='background: red'>危</div>"
                    )
                }
                if (data.map.ssxx.peccancy == 1) {
                    $("#shipinfodiv_table").after(
                        "<div class='ztcolordiv' style='background: #ae0f0f'>违章</div>"
                    )
                }
                if (data.map.ssxx.overdue == 1) {
                    $("#shipinfodiv_table").after(
                        "<div class='ztcolordiv' style='background: #ef3636'>证书过期</div>"
                    )
                }
                if (data.map.ssxx.arrearage == 1) {
                    $("#shipinfodiv_table").after(
                        "<div class='ztcolordiv' style='background: #f77d4d'>规费未缴清</div>"
                    )
                }
            }
        }
    })
}
var shipdata;//船舶详情
var shipcbmc;//船舶名称
//获取船舶信息
function getallshipdata() {
    $.ajax({
        url: '../supervise/cbXq',
        type: 'post',
        dataType: 'json',
        data: {
            'cbmc': shipcbmc
        },
        success: function (data) {
            if(data.resultcode==0){
            $("#cbxqModal").modal("show");
                shipdata = data.map;
                $("#titleshipname").text(shipcbmc);
                $(".tabdiv:eq(0)").click();
            }else{
                alert('暂无该船舶详情');
                return
            }
        }
    });
}
//切换船舶相关信息
function typedatacheck(event) {
    $("#shipdatatable").empty();
    $("#nulltablediv").hide();
    switch ($(event).text()) {
        case '基本信息':
            $("#shipdatatable").addClass('table-bordered');
            if (shipdata.jbxx == '' || shipdata.jbxx == null) {
                TableIsNull();
                break;
            }
            $("#shipdatatable").append(
                "<tr>" +
                "<td class='colortd col-lg-2'>船舶名称：</td>" +
                "<td class='col-lg-4'>" + isnull(shipdata.jbxx.zwcm, '--', 0) + "</td>" +
                "<td class='colortd col-lg-2'>船籍港（代码）：</td>" +
                "<td class='col-lg-4'>" + isnull(shipdata.jbxx.cjgdm, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>船检登记号：</td>" +
                "<td>" + isnull(shipdata.jbxx.cjdjh, '--', 0) + "</td>" +
                "<td class='colortd'>船舶登记号：</td>" +
                "<td>" + isnull(shipdata.jbxx.cbdjh, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>船舶类型：</td>" +
                "<td>" + isnull(shipdata.jbxx.cblx, '--', 0) + "</td>" +
                "<td class='colortd'>船舶类型代码：</td>" +
                "<td>" + isnull(shipdata.jbxx.cblxdm, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>主机总功率：</td>" +
                "<td>" + isnull(shipdata.jbxx.zjzgl, '--', 0) + "</td>" +
                "<td class='colortd'>参考载货量：</td>" +
                "<td>" + isnull(shipdata.jbxx.ckzhl, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>总吨位：</td>" +
                "<td>" + isnull(shipdata.jbxx.zdw, '--', 0) + "</td>" +
                "<td class='colortd'>净吨位：</td>" +
                "<td>" + isnull(shipdata.jbxx.jdw, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>吃水空载（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.cskz, '--', 0) + "</td>" +
                "<td class='colortd'>吃水满载（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.csmz, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>总长（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.zc, '--', 0) + "</td>" +
                "<td class='colortd'>船长（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.cc, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>型宽（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.xk, '--', 0) + "</td>" +
                "<td class='colortd'>型深（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.xs, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>量吨甲板长（m）：</td>" +
                "<td>" + isnull(shipdata.jbxx.ldjbc, '--', 0) + "</td>" +
                "<td class='colortd'>船舶经营人：</td>" +
                "<td>" + isnull(shipdata.jbxx.jyr, '--', 0) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class='colortd'>船舶所有人：</td>" +
                "<td>" + isnull(shipdata.jbxx.syr, '--', 0) + "</td>" +
                "<td class='colortd'>所有人电话：</td>" +
                "<td>" + isnull(shipdata.jbxx.syrdh, '--', 0) + "</td>" +
                "</tr>"
            );
            break;
        case '证书信息':
            $("#shipdatatable").removeClass('table-bordered');
            if (shipdata.czxx == '' || shipdata.czxx == null) {
                TableIsNull();
                break;
            }
            $("#shipdatatable").append(
                "<tr>" +
                "<th style='width: 50px;'>序号</th>" +
                "<th>证书名称</th>" +
                "<th>证书编号</th>" +
                "<th>发证日期</th>" +
                "<th>有效日期</th>" +
                    //"<th>操作</th>" +
                "</tr>"
            );
            $(shipdata.czxx).each(function (index, item) {
                var fzrq = '--';
                if (isnull(item.fzrq, '--', 0) != '--') {
                    fzrq = datecl(item.fzrq);
                }
                var yxrq = '--';
                if (isnull(item.yxrq, '--', 0) != '--') {
                    yxrq = datecl(item.yxrq);
                }
                $("#shipdatatable").append(
                    "<tr>" +
                    "<td>" + (index + 1) + "</td>" +
                    "<td>" + isnull(item.zsmc, '--', 0) + "</td>" +
                    "<td>" + isnull(item.zsbh, '--', 0) + "</td>" +
                    "<td>" + fzrq + "</td>" +
                    "<td>" + yxrq + "</td>" +
                        //"<td><span class='clickword'>详情</span></td>"+
                    "</tr>"
                );
            })
            break;
        case '违章信息':
            $("#shipdatatable").removeClass('table-bordered');
            if (shipdata.wzxx == '' || shipdata.wzxx == null) {
                TableIsNull();
                break;
            }
            $("#shipdatatable").append(
                "<tr>" +
                "<th style='width: 50px;'>序号</th>" +
                "<th>案发时间</th>" +
                "<th>案发地点</th>" +
                "<th>案件类别</th>" +
                "<th>案由</th>" +
                "<th>处罚意见</th>" +
                    //"<th>操作</th>" +
                "</tr>"
            );
            $(shipdata.wzxx).each(function (index, item) {
                var afsj = '--';
                if (isnull(item.fasj, '--', 0) != '--') {
                    var date = new Date(parseInt(item.fasj));
                    afsj = date.getFullYear() + '-' + date.getMonth() + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
                }
                $("#shipdatatable").append(
                    "<tr>" +
                    "<td>" + (index + 1) + "</td>" +
                    "<td>" + afsj + "</td>" +
                    "<td>" + isnull(item.fadd, '--', 0) + "</td>" +
                    "<td>" + isnull(item.ajlb, '--', 0) + "</td>" +
                    "<td>" + isnull(item.ay, '--', 0) + "</td>" +
                    "<td>" + isnull(item.cfyj, '--', 0) + "</td>" +
                        //"<td><span class='clickword'>详情</span></td>" +
                    "</tr>"
                );
            })
            break;
        case '缴费信息':
            $("#shipdatatable").removeClass('table-bordered');
            if (shipdata.jfxx == '' || shipdata.jfxx == null) {
                TableIsNull();
                break;
            }
            $("#shipdatatable").append(
                "<tr>" +
                "<th style='width: 50px;'>序号</th>" +
                "<th>开票日期</th>" +
                "<th>开票单位名称</th>" +
                "<th>缴费项目名称</th>" +
                "<th>收费项目名称</th>" +
                "<th>应缴金额</th>" +
                "<th>实缴金额</th>" +
                "<th>有效期起</th>" +
                "<th>有效期止</th>" +
                    //"<th>操作</th>" +
                "</tr>"
            );
            $(shipdata.jfxx).each(function (index, item) {
                var redclass = '';
                if (parseFloat(item.sjze) < parseFloat(item.yjze)) {
                    redclass = "class='redtr'";
                }
                var kprq = '--';
                if (isnull(item.kprq, '--', 0) != '--') {
                    kprq = datecl(item.kprq);
                }
                var yxqq = '--';
                if (isnull(item.yxqq, '--', 0) != '--') {
                    yxqq = datecl(item.yxqq);
                }
                var yxqz = '--';
                if (isnull(item.yxqz, '--', 0) != '--') {
                    yxqz = datecl(item.yxqz);
                }
                $("#shipdatatable").append(
                    "<tr " + redclass + ">" +
                    "<td>" + (index + 1) + "</td>" +
                    "<td>" + kprq + "</td>" +
                    "<td>" + isnull(item.kpdwmc, '--', 0) + "</td>" +
                    "<td>" + isnull(item.jfxmmc, '--', 0) + "</td>" +
                    "<td>" + isnull(item.sffsmc, '--', 0) + "</td>" +
                    "<td>" + isnull(item.yjze, '--', 0) + "</td>" +
                    "<td>" + isnull(item.sjze, '--', 0) + "</td>" +
                    "<td>" + yxqq + "</td>" +
                    "<td>" + yxqz + "</td>" +
                        //"<td><span class='clickword'>详情</span></td>"+
                    "</tr>"
                );
            })
            break;
        case '船检信息':
            $("#shipdatatable").removeClass('table-bordered');
            if (shipdata.jyxx == '' || shipdata.jyxx == null) {
                TableIsNull();
                break;
            }
            $("#shipdatatable").append(
                "<tr>" +
                "<th style='width: 50px;'>序号</th>" +
                "<th>船检登记号</th>" +
                "<th>检验地点</th>" +
                "<th>检验单位名称</th>" +
                "<th>申请人</th>" +
                "<th>检验种类</th>" +
                "<th>检验开始日期</th>" +
                "<th>检验完成日期</th>" +
                    //"<th>操作</th>" +
                "</tr>"
            );
            $(shipdata.jyxx).each(function (index, item) {
                var redclass = '';
                if (parseFloat(item.sjze) < parseFloat(item.yjze)) {
                    redclass = "class='redtr'";
                }
                var jyksrq = '--';
                if (isnull(item.jyksrq, '--', 0) != '--') {
                    jyksrq = datecl(item.jyksrq);
                }
                var jywcrq = '--';
                if (isnull(item.jywcrq, '--', 0) != '--') {
                    jywcrq = datecl(item.jywcrq);
                }
                $("#shipdatatable").append(
                    "<tr " + redclass + ">" +
                    "<td>" + (index + 1) + "</td>" +
                    "<td>" + isnull(item.cjdjh, '--', 0) + "</td>" +
                    "<td>" + isnull(item.jydd, '--', 0) + "</td>" +
                    "<td>" + isnull(item.jydwmc, '--', 0) + "</td>" +
                    "<td>" + isnull(item.sqr, '--', 0) + "</td>" +
                    "<td>" + isnull(item.jyzl, '--', 0) + "</td>" +
                    "<td>" + jyksrq + "</td>" +
                    "<td>" + jywcrq + "</td>" +
                        //"<td><span class='clickword'>详情</span></td>"+
                    "</tr>"
                );
            })
            break;
        default :
            break;
    }
}
function TableIsNull() {
    $("#nulltablediv").show();
}

//显示轨迹查询div
function showgjdiv() {
    onlyshow();
    $("#cbgjname").val('');
    var endtime=new Date();
    var starttime=new Date();
    starttime.setHours(starttime.getHours()-2);
    var starttimestr=starttime.getFullYear()+'-'+(starttime.getMonth()+1)+'-'+starttime.getDate()+' '+starttime.getHours()+':'+starttime.getMinutes()+':'+starttime.getSeconds();
    var endtimestr=endtime.getFullYear()+'-'+(endtime.getMonth()+1)+'-'+endtime.getDate()+' '+endtime.getHours()+':'+endtime.getMinutes()+':'+endtime.getSeconds();
    $("#beginTime").val(starttimestr);
    $("#endTime").val(endtimestr);
    $("#cbgjdiv").show();
}
//显示区域轨迹查询div
function showqygjdiv() {
    onlyshow();
    var endtime=new Date();
    var starttime=new Date();
    starttime.setHours(starttime.getHours()-2);
    var starttimestr=starttime.getFullYear()+'-'+(starttime.getMonth()+1)+'-'+starttime.getDate()+' '+starttime.getHours()+':'+starttime.getMinutes()+':'+starttime.getSeconds();
    var endtimestr=endtime.getFullYear()+'-'+(endtime.getMonth()+1)+'-'+endtime.getDate()+' '+endtime.getHours()+':'+endtime.getMinutes()+':'+endtime.getSeconds();
    $("#qybeginTime").val(starttimestr);
    $("#qyendTime").val(endtimestr);
    $("#qygjdiv").show();
}
/**
 * 历史轨迹生成
 */
function historygj() {
    onlyshow();
    $("#cbgjname").val(shipcbmc);
    var endtime=new Date();
    var starttime=new Date();
    starttime.setHours(starttime.getHours()-2);
    var starttimestr=starttime.getFullYear()+'-'+(starttime.getMonth()+1)+'-'+starttime.getDate()+' '+starttime.getHours()+':'+starttime.getMinutes()+':'+starttime.getSeconds();
    var endtimestr=endtime.getFullYear()+'-'+(endtime.getMonth()+1)+'-'+endtime.getDate()+' '+endtime.getHours()+':'+endtime.getMinutes()+':'+endtime.getSeconds();
    $("#beginTime").val(starttimestr);
    $("#endTime").val(endtimestr);
    $("#cbgjdiv").show();
}
/**
 * 区域历史轨迹生成
 */
function qyhistorygj(begintime,endtime) {
    $("#cbgjname").val(shipcbmc);
    $.ajax({
        url: '../supervise/trajectory',
        type: 'post',
        dataType: 'json',
        data: {
            'cbmc': shipcbmc,
            'startTime': begintime,
            'endTime':endtime
        },
        success: function (data) {
            if (data.resultcode != 0) {
                alert('该时间段该船无轨迹');
                return
            }
            gjlayer.removeAllFeatures();
            //gjlayer.clearMarkers();
            var list = data.records.data;
            gjlayer.setVisibility(true);
            gjmarkerlayer.setVisibility(true);
            //smallshipimglayer.setVisibility(false);
            //shiplayergreen.setVisibility(false);
            shipOrbitmap.zoomTo(12);
            gjzbchange(list);
        }
    })
}
/**
 * 船舶轨迹生成
 */
function guijimake() {
    if($('#cbgjname').val()==''||$('#cbgjname').val()==null){
        alert('船名不能为空');
    }
    $.ajax({
        url: '../supervise/trajectory',
        type: 'post',
        dataType: 'json',
        data: {
            'cbmc': $('#cbgjname').val(),
            'startTime': $('#beginTime').val(),
            'endTime': $('#endTime').val()
        },
        success: function (data) {
            if(data.resultcode==0){
                gjlayer.removeAllFeatures();
                var list = data.records.data;
                gjlayer.setVisibility(true);
                gjmarkerlayer.setVisibility(true);
                //smallshipimglayer.setVisibility(false);
                //shiplayergreen.setVisibility(false);
                shipOrbitmap.zoomTo(12);
                gjzbchange(list);

            }
            //gjlayer.clearMarkers();
            else{
                alert('该时间段该船无轨迹');
                return
            }
        }
    })
}
/**
 * 坐标转换
 * @param list：数据
 */
function gjzbchange(list) {
    gjmarkerlayer.clearMarkers();
    var length200 = parseInt(list.length / 200);
    for (var i = 0; i <= length200; i++) {
        var ifbegin = false;
        var ifend = false;
        var pointsstr = '';
        var endno = (1 + i) * 200;
        var list200 = [];
        if(i==0){
            ifbegin=true;
        }
        if (i == length200) {
                ifend = true;
            endno = list.length;
        }
        for (var j = i * 200; j < endno; j++) {
                if (j == (list.length - 1)) {
                    pointsstr += list[j].longitude.toFixed(8) + ' ' + list[j].latitude.toFixed(8);
                } else {
                    pointsstr += list[j].longitude.toFixed(8) + ' ' + list[j].latitude.toFixed(8) + ',';
                }
                list200.push(list[j]);
        }
        gjlonlatchange(pointsstr,list200,ifbegin,ifend);
    }
}

//轨迹坐标转换
function gjlonlatchange(pointsstr,list200,ifbegin,ifend){
    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': pointsstr
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = "";
            var points = [];
            jwd = data.response.result.point;
            var jwdsp = jwd.split(',');
            if (jwdsp != null && jwdsp.length > 0) {
                for (var i = 0; i < jwdsp.length; i++) {
                    var lonlat = new WebtAPI.LonLat(jwdsp[i].split(' ')[0], jwdsp[i].split(' ')[1]);
                    if(ifbegin){
                        if(i==0){
                            var imgurl = "../image/smart/map_ic_markers_start.png";
                            var icon = new WebtAPI.WIcon(imgurl, new WebtAPI.Size(25, 40));//生成船图片
                            var marker = new WebtAPI.WMarker(lonlat, icon);//生成标注
                            gjmarkerlayer.addMarker(marker);//添加标注到船标图层
                            shipOrbitmap.panToLonLat(lonlat);
                        }
                    }
                    if(ifend){
                        if(i==(jwdsp.length - 1)){
                            var imgurl = "../image/smart/map_ic_markers_end.png";
                            var icon = new WebtAPI.WIcon(imgurl, new WebtAPI.Size(25, 40));//生成船图片
                            var marker = new WebtAPI.WMarker(lonlat, icon);//生成标注
                            gjmarkerlayer.addMarker(marker);//添加标注到船标图层
                        }
                    }
                    var six=Math.floor(jwdsp.length/4);
                    if(i==(six*1)||i==(six*2)||i==(six*3)){
                        var icon = new WebtAPI.WIcon("../image/smart/map_middle_point.png", new WebtAPI.Size(12, 12));//生成船图片
                        var marker = new WebtAPI.WMarker(lonlat, icon);//生成标注
                        console.log(list200[i]+','+i +','+list200.length);
                        marker.sj=list200[i].shipdate;
                        marker.register(
                            'mouseover',
                            function () {
                                var html = "<div >"
                                    + this.sj
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
                        gjmarkerlayer.addMarker(marker);//添加标注到船标图层
                    }
                    points[i] = lonlat;
                }
                var style = {
                    //fillColor: "#E8F2FE",
                    //fillOpacity: 0.4,
                    //hoverFillColor: "white",
                    //hoverFillOpacity: 0.8,
                    strokeColor: "#0086ed",//轨迹颜色
                    //strokeOpacity: 0.7,
                    strokeWidth: 6,//轨迹宽度
                    //strokeLinecap: "round",
                    strokeDashstyle: "solid",
                    //hoverStrokeColor: "green",
                    //hoverStrokeOpacity: 5,
                    //hoverStrokeWidth: 0.2,
                    //pointRadius: 6,
                    //hoverPointRadius: 1,
                    //hoverPointUnit: "%",
                    //pointerEvents: "visiblePainted",
                    //cursor: "inherit"
                };
                var feature = new WebtAPI.LineFeature(points, style);
                gjlayer.addFeatures(feature);
            } else {
                alert("坐标转化失败");
            }
        }
    });
}
/**
 * 实时监听点击方法
 * @param text
 */
function cmhlabelclick(text) {
    $('#cmhinput').val(text);
    $("#cmhdiv").hide();
}
/**
 * 船舶轨迹实时监听
 */
function getcbgjlist() {
    $.ajax({
        url: '../supervise/queryCms',
        type: 'post',
        dataType: 'json',
        data: {
            'cbmc': $('#cmhinput').val()
        },
        success: function (data) {
            $("#cbgjnamelist").empty();
            var list = data.records.data;
            if (list.length == 0) {
                $("#cbgjnamelist").hide();
                return
            }
            for (var i = 0; i < list.length; i++) {
                $("#cbgjnamelist").append(
                    "<label onclick=cmhlabelclick('" + list[i] + "')>" + list[i] + "</label>"
                );
            }
            $("#cbgjnamelist").show();
        }
    })
}
var hdmdlonlat;//航段密度中心点
//获取航道密度
function gethdmd() {
    if (hdmdlonlat == null) {
        alert('请选择中心点');
        return
    }
    var distance=$("#jlinput").val();
    $.ajax({
        url: '../supervise/cbmd',
        type: 'post',
        dataType: 'json',
        data: {
            'lon': hdmdlonlat.lon,
            'lat': hdmdlonlat.lat,
            'distance': distance
        },
        success: function (data) {
            shipOrbitmap.zoomTo(9);
            shipOrbitmap.panToLonLat(hdmdlonlat);
            if(data.obj>=0&&data.obj<=5){
                $("#mdword").css('color','green');
                $("#mdword").text('小');
            }
            if(data.obj>5&&data.obj<=10){
                $("#mdword").css('color','#ff9900');
                $("#mdword").text('中');
            }
            if(data.obj>10){
                $("#mdword").css('color','red');
                $("#mdword").text('大');
            }
            $("#mdworddiv").show();
        }
    })
}

// 航道密度点击方法
function hdmdclick(e) {
    if ($("#jlinput").val() <= 0 || $("#jlinput").val() == '' || $("#jlinput").val() == null) {
        alert('请输入正确边长');
        return
    }
    gjlayer.removeAllFeatures();
    gjmarkerlayer.clearMarkers();
    var lonlat = shipOrbitmap.getLonLatFromPixel(e.xy).transform(new WebtAPI.Projection("EPSG:900913"), new WebtAPI.Projection("EPSG:4326"));
    var position = new WebtAPI.LonLat(lonlat.lon, lonlat.lat);
    hdmdlonlat = position;
    var style = {
        fillColor: "rgba(0,0,0,0)",
        //fillOpacity: 0.4,
        //hoverFillColor: "white",
        //hoverFillOpacity: 0.8,
        strokeColor: "red",//轨迹颜色
        //strokeOpacity: 0.7,
        strokeWidth: 3,//轨迹宽度
        //strokeLinecap: "round",
        strokeDashstyle: "solid",
        //hoverStrokeColor: "green",
        //hoverStrokeOpacity: 5,
        //hoverStrokeWidth: 0.2,
        //pointRadius: 6,
        //hoverPointRadius: 1,
        //hoverPointUnit: "%",
        //pointerEvents: "visiblePainted",
        //cursor: "inherit"
    };
    var bc=$('#jlinput').val()==''?1000:$('#jlinput').val()*1000;
    var feature = new WebtAPI.RegularPolygonFeature(position, bc, 4, 45, style);
    gjlayer.addFeatures(feature);
}
var qylonlat;//区域中心点
// 区域点击方法
function qygjclick(e) {
    if ($("#qybcinput").val() <= 0 || $("#qybcinput").val() == '' || $("#qybcinput").val() == null) {
        alert('请输入正确边长');
        return
    }
    gjlayer.removeAllFeatures();
    var lonlat = shipOrbitmap.getLonLatFromPixel(e.xy).transform(new WebtAPI.Projection("EPSG:900913"), new WebtAPI.Projection("EPSG:4326"));
    var position = new WebtAPI.LonLat(lonlat.lon, lonlat.lat);
    qylonlat = position;
    var style = {
        fillColor: "rgba(0,0,0,0)",
        //fillOpacity: 0.4,
        //hoverFillColor: "white",
        //hoverFillOpacity: 0.8,
        strokeColor: "red",//轨迹颜色
        //strokeOpacity: 0.7,
        strokeWidth: 3,//轨迹宽度
        //strokeLinecap: "round",
        strokeDashstyle: "solid",
        //hoverStrokeColor: "green",
        //hoverStrokeOpacity: 5,
        //hoverStrokeWidth: 0.2,
        //pointRadius: 6,
        //hoverPointRadius: 1,
        //hoverPointUnit: "%",
        //pointerEvents: "visiblePainted",
        //cursor: "inherit"
    };
    var bc=$('#qybcinput').val()*1000;
    var feature = new WebtAPI.RegularPolygonFeature(position, bc, 4, 45, style);
    gjlayer.addFeatures(feature);
}
//默认区域
function mrqy(){
    gjlayer.removeAllFeatures();
    var lonlat = shipOrbitmap.getCenterLonLat();
    hdmdlonlat=lonlat;
    $("#jlinput").val(1);
    document.getElementById('xdcbox').checked = false;
    var style = {
        fillColor: "rgba(0,0,0,0)",
        //fillOpacity: 0.4,
        //hoverFillColor: "white",
        //hoverFillOpacity: 0.8,
        strokeColor: "red",//轨迹颜色
        //strokeOpacity: 0.7,
        strokeWidth: 3,//轨迹宽度
        //strokeLinecap: "round",
        strokeDashstyle: "solid",
        //hoverStrokeColor: "green",
        //hoverStrokeOpacity: 5,
        //hoverStrokeWidth: 0.2,
        //pointRadius: 6,
        //hoverPointRadius: 1,
        //hoverPointUnit: "%",
        //pointerEvents: "visiblePainted",
        //cursor: "inherit"
    };
    var feature = new WebtAPI.RegularPolygonFeature(lonlat, 1000, 4, 45, style);
    gjlayer.addFeatures(feature);
}
//重置1航道密度,2区域轨迹
function hdmdreset(type){
    if(type==1){
        hdmdlonlat=''
        $("#jlinput").val(1);
        gjlayer.removeAllFeatures();
        isxdqu=true;
        $("#xdcbox").removeClass('btn-success');
        $("#xdcbox").addClass('btn-primary');
        $("#mdworddiv").hide();
    }else if(type==2){
        gjlayer.removeAllFeatures();
        var endtime=new Date();
        var starttime=new Date();
        starttime.setHours(starttime.getHours()-2);
        var starttimestr=starttime.getFullYear()+'-'+(starttime.getMonth()+1)+'-'+starttime.getDate()+' '+starttime.getHours()+':'+starttime.getMinutes()+':'+starttime.getSeconds();
        var endtimestr=endtime.getFullYear()+'-'+(endtime.getMonth()+1)+'-'+endtime.getDate()+' '+endtime.getHours()+':'+endtime.getMinutes()+':'+endtime.getSeconds();
        $("#qybeginTime").val(starttimestr);
        $("#qyendTime").val(endtimestr);
        $("#qybcinput").val('');
        qylonlat='';
    }

}
//区域船舶绘制
function qycbhz(){
    gjlayer.setVisibility(true);
    shipOrbitmap.registerEvents("click", qygjclick);
}

//区域轨迹查询
function selectqygj(){
    if (qylonlat == null) {
        alert('请选择中心点');
        return
    }
    if ($('#qybeginTime').val() == ''||$('#qybeginTime').val()==null) {
        alert('开始时间不能为空');
        return
    }
    if ($('#qyendTime').val() == ''||$('#qyendTime').val()==null) {
        alert('结束时间不能为空');
        return
    }
    $.ajax({
        url: '../supervise/lscb',
        type: 'post',
        dataType: 'json',
        data: {
            'lon': qylonlat.lon,
            'lat': qylonlat.lat,
            'distance': $('#qybcinput').val(),
            'startTime': $('#qybeginTime').val(),
            'endTime': $('#qyendTime').val()
        },
        success: function (data) {
            shipOrbitmap.zoomTo(9);
            shipOrbitmap.panToLonLat(qylonlat);
            if(data.resultcode!=0){
                alert('该时间段无船舶经过');
                return
            }
            $('#qygjlistdiv').show();
            $('#qygjtable').empty();
            $('#qygjlisttitle').text(
                "在所选择的区域内，"+$('#qybeginTime').val()+"至"+$('#qyendTime').val()+"这段时间内共经过"+data.records.total+"艘船舶"
            );
            $('#qygjtable').append("<tr><th>船舶名称</th><th>MMSI</th><th>操作</th></tr>");
            $(data.records.data).each(function(index,item){
                $('#qygjtable').append(
                    "<tr>" +
                    "<td>"+item.shipname+"</td>" +
                    "<td>"+item.ais+"</td>" +
                    "<td>" +
                    "<span class='clickword' onclick=\"qygjshipmake('"+item.shipname+"','"+$('#qybeginTime').val()+"','"+$('#qyendTime').val()+"')\">轨迹</span>&nbsp;" +
                    "<span class='clickword' onclick=\"qygjshipxq('"+item.shipname+"')\">详情</span>&nbsp;" +
                    "</td>" +
                    "</tr>"
                )
            });
        }
    })
}
//区域轨迹船舶详情
function qygjshipxq(shipname){
    shipcbmc=shipname;
    $('#cbxqModal').modal('show')
    getallshipdata();
}
//区域轨迹船舶详情
function qygjshipmake(shipname,begintime,endtime){
    shipcbmc=shipname;
    qyhistorygj(begintime,endtime);
}

function datecl(dateno){
    var date=new Date(parseInt(dateno));
    return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
}

//唯一显示
function onlyshow(){
    $("#qygjdiv,#cbgjdiv,#qygjlistdiv,#cbgkdiv,#hdmddiv,#swllinfodiv,#shipinfodiv,#cblistdiv").hide();
    shipOrbitmap.unregisterEvents("click",hdmdclick);
    shipOrbitmap.unregisterEvents("click",qygjclick);
    gjmarkerlayer.clearMarkers();
    gjlayer.removeAllFeatures();
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