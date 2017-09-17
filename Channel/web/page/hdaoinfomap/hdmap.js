var map;
/*var curmarker;
var curpopup;*/
var fsweditmode = 0;
$(function () {
   /* loadfswtree();
    // 附属物新增按钮点击事件
    $("#btnmodalfswnew").bind('click', function () {
        addfsw();
    });
    // 删除附属物按钮事件
    $("#btnmodalfswdel").bind('click', function () {
        delfsw();
    });
    // 附属物编辑按钮点击事件
    $("#btnmodalfswedit").bind('click', function () {
        updatefsw();
    });*/
    map = new WebtAPI.WMap(WebtAPI.Util.getElement("divmap"));
    var huzhou = new WebtAPI.LonLat(120.497122, 30.758621);
    map.addControl(new WebtAPI.WControl.WPanZoom());
    map.addControl(new WebtAPI.WControl.WOverviewMap());
    map.addControl(new WebtAPI.WControl.ScaleLine());
    map.setDisplayNauticalLayer(true); //开启海图
    map.setCenterByLonLat(huzhou, 11);
});

function transferjwd(jd, wd, callbk, data) {
    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': jd + " " + wd
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = "";
            jwd = data.response.result.point;
            callbk(jwd, data);
        }
    });
}

/**
 *
 * @param jdwds [{'jd':,'wd':},{},{},]
 * @param callbk
 * @param data
 */
function transferjwds(jdwds, callbk, data) {
    var data = "";

    for (var i = 0; i < jdwds.length; i++) {
        var jwd = jdwds[i];
        data += jwd.jd + " " + jwd.wd;
        if (i != jdwds.length - 1)
            data += ',';
    }

    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': data
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = "";
            jwd = data.response.result.point;
            var jwdsp = jwd.split(',');
            if (jwdsp != null && jwdsp.length > 0) {
                var jwd = new Array();

                for (var i = 0; i < jwdsp.length; i++) {
                    jwd.push({
                        'jd': jwdsp[i].split(' ')[0],
                        'wd': jwdsp[i].split(' ')[1]
                    });
                }
                callbk(jwd, data);
            } else {
                alert("坐标转化失败");
            }
        }
    });
}

function showmap(fswlx, id, jd, wd) {
    map.clearMarkers();
    map.clearPopups();
    var jwd = "";
    if (jd > 0 && wd > 0) {
        jwd = transferjwd(jd, wd, function (jwd) {
            var arr = new Array();
            arr = jwd.split(" ");
            var lonlat = new WebtAPI.LonLat(arr[0], arr[1]);
            var size = new WebtAPI.Size('28px', '28px');
            $.ajax({
                url: 'appurtenance/queryapppopup',
                type: 'post',
                dataType: 'json',
                data: {
                    'loginid': $("#userid").val(),
                    'id': id,
                    'fswlx': fswlx
                },
                success: function (data) {
                    if (ifResultOK(data)) {
                        var records = getResultRecords(data).data[0];
                        var iconpath = 'img/mapmr.png';
                        var html = '';
                        var fsw = records[0];
                        var bh = fsw.bh;
                        var mc = fsw.mc;
                        switch (parseInt(fswlx)) {
                            case APP_NAVIGATIONMARK:// 航标
                                iconpath = 'img/maphb.png';
                                html = '<div>编号:<label>HB' + bh + '</label><br>名称:<label>' + mc + '</label><br>标志类型:<label>' + records[1].attrdesc + '</label><br>支撑方式:<label>' + records[5].attrdesc + '</label><br>标志结构:<label>' + records[6].attrdesc + '</label><br>光学性质:<label>' + records[2].attrdesc + '</label><br>灯质信号:<label>' + records[3].attrdesc + '</label><br>灯标颜色:<label>' + records[4].attrdesc + '</label></div>';
                                break;
                            case APP_BRIDGE:// 桥梁
                                iconpath = 'img/mapql.png';
                                html = '<div>编号:<label>QL' + bh + '</label><br>名称:<label>' + mc + '</label><br>结构型式:<label>' + records[1].attrdesc + '</label><br>用途分类:<label>' + records[2].attrdesc + '</label></div>';
                                break;
                            case APP_AQUEDUCT:// 渡槽
                                iconpath = 'img/mapdc.png';
                                html = '<div>编号:<label>DC' + bh + '</label><br>名称:<label>' + mc + '</label><br>用途分类:<label>' + records[0].ytfl + '</label></div>';
                                break;
                            case APP_CABLE:// 缆线
                                iconpath = 'img/maplx.png';
                                html = '<div>编号:<label>LX' + bh + '</label><br>名称:<label>' + mc + '</label><br>种类:<label>' + records[1].attrdesc + '</label></div>';
                                break;
                            case APP_PIPELINE:// 管道
                                iconpath = 'img/mapgd.png';
                                html = '<div>编号:<label>GD' + bh + '</label><br>名称:<label>' + mc + '</label><br>穿越型式:<label>' + records[0].cyxs + '</label></div>';
                                break;
                            case APP_TUNNEL:// 隧道
                                iconpath = 'img/mapsd.png';
                                html = '<div>编号:<label>SD' + bh + '</label><br>名称:<label>' + mc + '</label><br>用途分类:<label>' + records[0].ytfl + '</label></div>';
                                break;
                            case APP_KYDOCK:// 码头
                                iconpath = 'img/mapmt.png';
                                html = '<div>编号:<label>KYMT' + bh + '</label><br>名称:<label>' + mc + '</label><br>结构类型:<label>' + records[1].attrdesc + '</label></div>';
                                break;
                            case APP_HYDOCK:// 码头
                                iconpath = 'img/mapmt.png';
                                html = '<div>编号:<label>HYMT' + bh + '</label><br>名称:<label>' + mc + '</label><br>结构类型:<label>' + records[1].attrdesc + '</label></div>';
                                break;
                            case APP_GWDOCK:// 码头
                                iconpath = 'img/mapmt.png';
                                html = '<div>编号:<label>GWMT' + bh + '</label><br>名称:<label>' + mc + '</label><br>结构类型:<label>' + records[1].attrdesc + '</label></div>';
                                break;
                            case APP_SHIPYARD:// 船厂
                                iconpath = 'img/mapcc.png';
                                html = '<div>编号:<label>CC' + bh + '</label><br>名称:<label>' + mc + '</label><br>修造船吨级:<label>' + records[0].xzcwj + '</label></div>';
                                break;
                            case APP_TAKEOUTFALL:// 取排水口
                                iconpath = 'img/mapqps.png';
                                html = '<div>编号:<label>QPS' + bh + '</label><br>名称:<label>' + mc + '</label><br>类型:<label>' + records[1].attrdesc + '</label></div>';
                                break;
                            case APP_HYDROLOGICALSTATION:// 水文站
                                iconpath = 'img/mapswz.png';
                                html = '<div>编号:<label>SWZ' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                                break;
                            case APP_MANAGEMENTSTATION:// 管理站
                                iconpath = 'img/mapglz.png';
                                html = '<div>编号:<label>GLZ' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                                break;
                            case APP_SERVICEAREA:// 服务区
                                iconpath = 'img/mapfwq.png';
                                html = '<div>编号:<label>FWQ' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                                break;
                            case APP_MOORINGAREA:// 锚泊区
                                iconpath = 'img/mapmbq.png';
                                html = '<div>编号:<label>MBQ' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                                break;
                            case APP_HUB:// 枢纽
                                iconpath = 'img/mapsn.png';
                                html = '<div>编号:<label>SN' + bh + '</label><br>名称:<label>' + mc + '</label><br>通航类型:<label>' + records[2].attrdesc + '</label><br>型式:<label>' + records[1].attrdesc + '</label><br>过船设施位置:<label>' + records[3].attrdesc + '</label></div>';
                                break;
                            case APP_DAM:// 坝
                                iconpath = 'img/mapb.png';
                                html = '<div>编号:<label>B' + bh + '</label><br>名称:<label>' + mc + '</label><br>类型:<label>' + records[1].attrdesc + '</label></div>';
                                break;
                            case APP_REGULATIONREVEMENT:// 整治护岸
                                iconpath = 'img/mapzzha.png';
                                html = '<div>编号:<label>ZZHA' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                                break;
                            case APP_LASEROBSERVATION:// 激光流量观测点
                                iconpath = 'img/mapgcd.png';
                                html = '<div>编号:<label>JGLLGCD' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                                break;
                            case APP_VIDEOOBSERVATION:// 视频观测点
                                iconpath = 'img/mapspgcd.png';
                                html = '<div>编号:<label>SPGCD' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                                break;
                            case APP_MANUALOBSERVATION:
                                iconpath = 'img/mapgcd.png';
                                html = '<div>编号:<label>RGGCD' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                                break;
                            case APP_BOLLARD:// 系缆桩
                                iconpath = "img/mapxlz.png";
                                html = '<div>编号:<label>XLZ' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                                break;
                        }
                        var apppopup = new WebtAPI.WPopup(id, lonlat, 200, html, true);
                        apppopup.setToolbarDisplay(false);
                        apppopup.setOffset(60, 62);
                        map.addPopup(apppopup);
                        var icon = new WebtAPI.WIcon(iconpath, size);
                        var marker = new WebtAPI.WMarker(lonlat, icon);
                        map.markersLayer.addMarker(marker);
                        map.setCenterByLonLat(lonlat, 11);
                     /*   curmarker = marker;
                        curpopup = apppopup;*/
                    }
                },
                error: function (data) {
                    alert("查询失败");
                }
            });

        });
    } else {
        alert("查找不到相应地理信息");
        var hz = new WebtAPI.LonLat(120.125122, 30.916621);
        map.setCenterByLonLat(hz, 7);
    }
}

function showapppopup(fswlx, id, jd, wd, pmarker) {
    map.clearPopups();
    if (jd > 0 && wd > 0) {
        var html = '';
        $.ajax({
            url: 'appurtenance/queryapppopup',
            type: 'post',
            dataType: 'json',
            data: {
                'loginid': $("#userid").val(),
                'id': id,
                'fswlx': fswlx
            },
            success: function (data) {
                if (ifResultOK(data)) {
                    var records = getResultRecords(data).data[0];
                    var fsw = records[0];
                    var bh = fsw.bh;
                    var mc = fsw.mc;
                    switch (parseInt(fswlx)) {
                        case APP_NAVIGATIONMARK:// 航标
                            html = '<div>编号:<label>HB' + bh + '</label><br>名称:<label>' + mc + '</label><br>标志类型:<label>' + records[1].attrdesc + '</label><br>支撑方式:<label>' + records[5].attrdesc + '</label><br>标志结构:<label>' + records[6].attrdesc + '</label><br>光学性质:<label>' + records[2].attrdesc + '</label><br>灯质信号:<label>' + records[3].attrdesc + '</label><br>灯标颜色:<label>' + records[4].attrdesc + '</label></div>';
                            break;
                        case APP_BRIDGE:// 桥梁
                            html = '<div>编号:<label>QL' + bh + '</label><br>名称:<label>' + mc + '</label><br>结构型式:<label>' + records[1].attrdesc + '</label><br>用途分类:<label>' + records[2].attrdesc + '</label></div>';
                            break;
                        case APP_AQUEDUCT:// 渡槽
                            html = '<div>编号:<label>DC' + bh + '</label><br>名称:<label>' + mc + '</label><br>用途分类:<label>' + records[0].ytfl + '</label></div>';
                            break;
                        case APP_CABLE:// 缆线
                            html = '<div>编号:<label>LX' + bh + '</label><br>名称:<label>' + mc + '</label><br>种类:<label>' + records[1].attrdesc + '</label></div>';
                            break;
                        case APP_PIPELINE:// 管道
                            html = '<div>编号:<label>GD' + bh + '</label><br>名称:<label>' + mc + '</label><br>穿越型式:<label>' + records[0].cyxs + '</label></div>';
                            break;
                        case APP_TUNNEL:// 隧道
                            html = '<div>编号:<label>SD' + bh + '</label><br>名称:<label>' + mc + '</label><br>用途分类:<label>' + records[0].ytfl + '</label></div>';
                            break;
                        case APP_KYDOCK:// 码头
                            html = '<div>编号:<label>KYMT' + bh + '</label><br>名称:<label>' + mc + '</label><br>结构类型:<label>' + records[1].attrdesc + '</label></div>';
                            break;
                        case APP_HYDOCK:// 码头
                            html = '<div>编号:<label>HYMT' + bh + '</label><br>名称:<label>' + mc + '</label><br>结构类型:<label>' + records[1].attrdesc + '</label></div>';
                            break;
                        case APP_GWDOCK:// 码头
                            html = '<div>编号:<label>GWMT' + bh + '</label><br>名称:<label>' + mc + '</label><br>结构类型:<label>' + records[1].attrdesc + '</label></div>';
                            break;
                        case APP_SHIPYARD:// 船厂
                            html = '<div>编号:<label>CC' + bh + '</label><br>名称:<label>' + mc + '</label><br>修造船吨级:<label>' + records[0].xzcwj + '</label></div>';
                            break;
                        case APP_TAKEOUTFALL:// 取排水口
                            html = '<div>编号:<label>QPS' + bh + '</label><br>名称:<label>' + mc + '</label><br>类型:<label>' + records[1].attrdesc + '</label></div>';
                            break;
                        case APP_HYDROLOGICALSTATION:// 水文站
                            html = '<div>编号:<label>SWZ' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                            break;
                        case APP_MANAGEMENTSTATION:// 管理站
                            html = '<div>编号:<label>GLZ' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                            break;
                        case APP_SERVICEAREA:// 服务区
                            html = '<div>编号:<label>FWQ' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                            break;
                        case APP_MOORINGAREA:// 锚泊区
                            html = '<div>编号:<label>MBQ' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                            break;
                        case APP_HUB:// 枢纽
                            html = '<div>编号:<label>SN' + bh + '</label><br>名称:<label>' + mc + '</label><br>通航类型:<label>' + records[2].attrdesc + '</label><br>型式:<label>' + records[1].attrdesc + '</label><br>过船设施位置:<label>' + records[3].attrdesc + '</label></div>';
                            break;
                        case APP_DAM:// 坝
                            html = '<div>编号:<label>B' + bh + '</label><br>名称:<label>' + mc + '</label><br>类型:<label>' + records[1].attrdesc + '</label></div>';
                            break;
                        case APP_REGULATIONREVEMENT:// 整治护岸
                            html = '<div>编号:<label>ZZHA' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                            break;
                        case APP_LASEROBSERVATION:// 激光流量观测点
                            html = '<div>编号:<label>JGLLGCD' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                            break;
                        case APP_VIDEOOBSERVATION:// 视频观测点
                            html = '<div>编号:<label>SPGCD' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                            break;
                        case APP_MANUALOBSERVATION:
                            html = '<div>编号:<label>RGGCD' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                            break;
                        case APP_BOLLARD:// 系缆桩
                            html = '<div>编号:<label>XLZ' + bh + '</label><br>名称:<label>' + mc + '</label></div>';
                            break;
                    }
                    var lonlat = new WebtAPI.LonLat(jd, wd);
                    var popup = new WebtAPI.WPopup(id, lonlat, 200, html, true);
                    popup.setToolbarDisplay(false);
                    popup.setOffset(60, 62);
                    map.addPopup(popup);
                    map.panToLonLat(lonlat);
            /*        curmarker = pmarker;
                    curpopup = popup;*/
                }
            },
            error: function (data) {
                alert("查询失败");
            }
        });

    } else {
        alert("查找不到相应地理信息");
    }
}
/*
// 加载添加附属物时弹窗内容
function loadaddfsw(fswlx, fswmc) {
    var hdaoid = $("#maphdaoid").val();
    var hduanid = $("#maphduanid").val();
    if (hduanid == '' || hduanid == 0) {
        alert("请先在左边选择你要添加的附属物所在航道和航段!");
        return false;
    }
    var hduanxzqhid = 1;
    fsweditmode = 0;
    $("#myModalLabelfswnew").html("新增" + fswmc);
    var modelname = "";
    $("#btnmodalfswnew").attr('secondclass', fswlx);
    switch (parseInt(fswlx)) {
        case APP_NAVIGATIONMARK:// 航标
            modelname = "CHdHb";
            break;
        case APP_BRIDGE:// 桥梁
            modelname = "CHdQl";
            break;
        case APP_AQUEDUCT:// 渡槽
            modelname = "CHdDc";
            break;
        case APP_CABLE:// 缆线
            modelname = "CHdLx";
            break;
        case APP_PIPELINE:// 管道
            modelname = "CHdGd";
            break;
        case APP_TUNNEL:// 隧道
            modelname = "CHdSd";
            break;
        case APP_KYDOCK:// 客运码头
            modelname = "CHdKymt";
            break;
        case APP_HYDOCK:// 货运码头
            modelname = "CHdHymt";
            break;
        case APP_GWDOCK:// 公务码头
            modelname = "CHdGwmt";
            break;
        case APP_SHIPYARD:// 船厂
            modelname = "CHdCc";
            break;
        case APP_TAKEOUTFALL:// 取排水口
            modelname = "CHdQpsk";
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            modelname = "CHdSwz";
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            modelname = "CHdGlz";
            break;
        case APP_SERVICEAREA:// 服务区
            modelname = "CHdFwq";
            break;
        case APP_MOORINGAREA:// 锚泊区
            modelname = "CHdMbq";
            break;
        case APP_HUB:// 枢纽
            modelname = "CHdSn";
            break;
        case APP_DAM:// 坝
            modelname = "CHdB";
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            modelname = "CHdZzha";
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            modelname = "CHdJgllgcd";
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            modelname = "CHdSpgcd";
            break;
        case APP_MANUALOBSERVATION:// 人工流量观测点
            modelname = "CHdRggcd";
            break;
        case APP_BOLLARD:// 系缆桩
            modelname = "CHdXlz";
            break;
    }

    loadaddinfo(function () {
        $("#modalfswnew").modal('show');
    }, "addfswform", modelname, '{"hdaoid":"' + hdaoid + '","hduanid":"'
        + hduanid + '","szxzqh":"' + hduanxzqhid + '"}', true);
}

// 调用后台接口添加附属物
function addfsw() {
    var url = "";
    var secondclass = $("#btnmodalfswnew").attr('secondclass');
    var hduanbh = $("#btnmodalfswnew").attr('hduanbh');
    var fswclassid = $("#btnmodalfswnew").attr('fswclassid');
    var fswclass = $("#btnmodalfswnew").getobj('fswclass');
    switch (parseInt(secondclass)) {
        case APP_NAVIGATIONMARK:// 航标
            url = 'appurtenance/addnavigationmark';
            break;
        case APP_BRIDGE:// 桥梁
            url = 'appurtenance/addbridge';
            break;
        case APP_AQUEDUCT:// 渡槽
            url = 'appurtenance/addaqueduct';
            break;
        case APP_CABLE:// 缆线
            url = 'appurtenance/addcable';
            break;
        case APP_PIPELINE:// 管道
            url = 'appurtenance/addpipeline';
            break;
        case APP_TUNNEL:// 隧道
            url = 'appurtenance/addtunnel';
            break;
        case APP_KYDOCK:// 客运码头
            url = 'appurtenance/addkydock';
            break;
        case APP_HYDOCK:// 货运码头
            url = 'appurtenance/addhydock';
            break;
        case APP_GWDOCK:// 公务码头
            url = 'appurtenance/addgwdock';
            break;
        case APP_SHIPYARD:// 船厂
            url = 'appurtenance/addshipyard';
            break;
        case APP_TAKEOUTFALL:// 取排水
            url = 'appurtenance/addtakeoutfall';
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            url = 'appurtenance/addhydrologicalstation';
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            url = 'appurtenance/addmanagementstation';
            break;
        case APP_SERVICEAREA:// 服务区
            url = 'appurtenance/addservicearea';
            break;
        case APP_MOORINGAREA:// 锚泊区
            url = 'appurtenance/addmooringarea';
            break;
        case APP_HUB:// 枢纽
            url = 'appurtenance/addhub';
            break;
        case APP_DAM:// 坝
            url = 'appurtenance/adddam';
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            url = 'appurtenance/addregulationrevement';
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            url = 'appurtenance/addlaserobservation';
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            url = 'appurtenance/addvideoobservation';
            break;
        case APP_MANUALOBSERVATION:// 视频观测点
            url = 'appurtenance/addmanualobservation';
            break;
        case APP_BOLLARD:// 系缆桩
            url = 'appurtenance/addbollard';
            break;
    }

    $("#addfswform").validateForm(
        function () {
            $("#addfswform").autoajaxfileuploading(
                url,
                {
                    loginid: $("#userid").val(),
                },
                function (data) {
                    if (ifResultOK(data)) {
                        alert("添加成功!");
                        $("#modalfswnew").modal('hide');
                        window.location.reload();
                    } else {
                        alert(getResultDesc(data));
                    }
                });
        });
}

// 加载编辑附属物时弹窗内容
function loaduptfsw(fswid, fswlx, fswmc) {
    $('#myModalLabelfswedit').html("修改" + fswmc);
    $("#editfswform").attr('fswid', fswid);
    $("#editfswform").attr('fswclassid', fswlx);
    fsweditmode = 1;
    var modelname = '';
    $("#btnmodalfswnew").attr('secondclass', fswlx);
    switch (parseInt(fswlx)) {
        case APP_NAVIGATIONMARK:// 航标
            modelname = "CHdHb";
            break;
        case APP_BRIDGE:// 桥梁
            modelname = "CHdQl";
            break;
        case APP_AQUEDUCT:// 渡槽
            modelname = "CHdDc";
            break;
        case APP_CABLE:// 缆线
            modelname = "CHdLx";
            break;
        case APP_PIPELINE:// 管道
            modelname = "CHdGd";
            break;
        case APP_TUNNEL:// 隧道
            modelname = "CHdSd";
            break;
        case APP_KYDOCK:// 客运码头
            modelname = "CHdKymt";
            break;
        case APP_HYDOCK:// 货运码头
            modelname = "CHdHymt";
            break;
        case APP_GWDOCK:// 公务码头
            modelname = "CHdGwmt";
            break;
        case APP_SHIPYARD:// 船厂
            modelname = "CHdCc";
            break;
        case APP_TAKEOUTFALL:// 取排水口
            modelname = "CHdQpsk";
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            modelname = "CHdSwz";
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            modelname = "CHdGlz";
            break;
        case APP_SERVICEAREA:// 服务区
            modelname = "CHdFwq";
            break;
        case APP_MOORINGAREA:// 锚泊区
            modelname = "CHdMbq";
            break;
        case APP_HUB:// 枢纽
            modelname = "CHdSn";
            break;
        case APP_DAM:// 坝
            modelname = "CHdB";
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            modelname = "CHdZzha";
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            modelname = "CHdJgllgcd";
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            modelname = "CHdSpgcd";
            break;
        case APP_MANUALOBSERVATION:// 人工流量观测点
            modelname = "CHdRggcd";
            break;
        case APP_BOLLARD:// 系缆桩
            modelname = "CHdXlz";
            break;
    }

    ajax('appurtenance/queryappurtenanceinfo', {
        'id': fswid,
        'fswlx': fswlx
    }, function (data) {
        if (ifResultOK(data)) {
            if (ifResultOK(data)) {
                var modalobj = data.modelobj;
                var json_data = JSON.stringify(modalobj);

                // 获取原初始下载信息
                var obj = getResultObj(data);
                var fjxx = null;
                if (obj != null) {
                    for (var i = 0; i < obj.length; i++) {
                        var dt = obj[i];
                        if (dt[0].indexOf('附件') >= 0)
                            fjxx = dt;
                    }
                }

                loadeditinfo(function () {
                    $("#modalfswedit").modal('show');
                }, 'editfswform', modelname, json_data, fjxx, true);
            }
        }
    });
}

// 调用后台接口编辑附属物
function updatefsw() {
    var url = "";
    var fswid = $("#editfswform").attr('fswid');
    var fswsecondclassid = $("#editfswform").attr('fswclassid');
    var fsw = $("#editfswform").getobj('fsw');
    switch (parseInt(fswsecondclassid)) {
        case APP_NAVIGATIONMARK:// 航标
            url = 'appurtenance/updatenavigationmark';
            break;
        case APP_BRIDGE:// 桥梁
            url = 'appurtenance/updatebridge';
            break;
        case APP_AQUEDUCT:// 渡槽
            url = 'appurtenance/updateaqueduct';
            break;
        case APP_CABLE:// 缆线
            url = 'appurtenance/updatecable';
            break;
        case APP_PIPELINE:// 管道
            url = 'appurtenance/updatepipeline';
            break;
        case APP_TUNNEL:// 隧道
            url = 'appurtenance/updatetunnel';
            break;
        case APP_KYDOCK:// 客运码头
            url = 'appurtenance/updatekydock';
            break;
        case APP_HYDOCK:// 货运码头
            url = 'appurtenance/updatehydock';
            break;
        case APP_GWDOCK:// 公务码头
            url = 'appurtenance/updategwdock';
            break;
        case APP_SHIPYARD:// 船厂
            url = 'appurtenance/updateshipyard';
            break;
        case APP_TAKEOUTFALL:// 取排水口
            url = 'appurtenance/updatetakeoutfall';
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            url = 'appurtenance/updatehydrologicalstation';
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            url = 'appurtenance/updatemanagementstation';
            break;
        case APP_SERVICEAREA:// 服务区
            url = 'appurtenance/updateservicearea';
            break;
        case APP_MOORINGAREA:// 锚泊区
            url = 'appurtenance/updatemooringarea';
            break;
        case APP_HUB:// 枢纽
            url = 'appurtenance/updatehub';
            break;
        case APP_DAM:// 坝
            url = 'appurtenance/updatedam';
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            url = 'appurtenance/updateregulationrevement';
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            url = 'appurtenance/updatelaserobservation';
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            url = 'appurtenance/updatevideoobservation';
            break;
        case APP_MANUALOBSERVATION:// 视频观测点
            url = 'appurtenance/updatemanualobservation';
            break;
        case APP_BOLLARD:// 系缆桩
            url = 'appurtenance/updatebollard';
            break;
    }

    var param = {
        loginid: $("#userid").val()
    };
    var delindex = 0;
    // 添加已有的
    $("#editfswform").find("[id*=originalfileids]").each(function () {
        if ($(this).attr('removed') == "1") {
            param['delfileids[' + delindex + ']'] = $(this).attr('fileid');
            delindex++;
        }
    });

    $("#editfswform").validateForm(
        function () {
            $("#editfswform").autoajaxfileuploading(
                url,
                param,
                function (result, data) {
                    if (ifResultOK(result)) {
                        alert("更新成功!");
                        $("#modalfswedit").modal('hide');
                        hideloading();
                        window.location.reload();
                    } else {
                        alert(getResultDesc(result));
                    }
                });
        });
}

function loaddelfsw(fswid, fswlx, fswmc) {
    $("#lbmodalfswdel").html(fswmc);
    $("#lbmodalfswdel").attr('delid', fswid);
    $("#lbmodalfswdel").attr('delfswclass', fswlx);
    $("#modalfswdel").modal('show');
}

// 删除附属物后台调用接口
function delfsw() {
    var delfswid = $("#lbmodalfswdel").attr('delid');
    var delfswclass = $("#lbmodalfswdel").attr('delfswclass');
    ajax('appurtenance/delappurtenance', {
            'loginid': $("#userid").val(),
            'id': delfswid,
            'fswlx': delfswclass
        },
        function (data) {
            if (ifResultOK(data)) {
                $("#modalfswdel").modal('hide');
               /!* map.markersLayer.removeMarker(curmarker);
                map.removePopup(curpopup);*!/
                alert("删除成功!");
                window.location.reload();
            } else {
                alert(getResultDesc(data));
            }
        });
}

/!**
 * 自动生成编辑信息，inputdata为键值对各个属性的信息 需调用方自动生成。
 *!/
function loadeditinfo(callfn, containerid, modelname, inputdata, fjxxarray,
                      needpic) {
    loadaddinfo(callfn, containerid, modelname, inputdata, needpic, true,
        fjxxarray);
}

/!**
 * 自动生成新增信息，inputdata为键值对各个属性的信息 需调用方自动生成。
 *!/
function loadaddinfo(callfn, containerid, modelname, inputdata, needpic,
                     editmode, fjxxarray/!* 附件信息 *!/) {
    var container = $("#" + containerid);
    if (inputdata == null)
        inputdata = '';
    container.empty();
    if (editmode != true) {
        editmode = false;
    }
    // 此行代码解决如果对同一信息再次自动生成，会产生页面元素id冲突的问题
    $(".auto-generate").empty();
    ajaxloading(
        'modelutil/querymodelinfo',
        {
            modelname: modelname,
            inputdata: inputdata,
            editmode: editmode
        },
        function (data) {
            if (ifResultOK(data)) {
                var modelinfo = getResultObj(data);
                var modelname = modelinfo.modelName;
                var rowelementsize = 0;
                var row = null;
                container.validateFormClear();
                for (var i = 0; i < modelinfo.propertyInfos.length; i++) {
                    var propinfo = modelinfo.propertyInfos[i];

                    var name = propinfo.name;
                    var type = propinfo.type;
                    var desc = propinfo.desc;
                    var group = propinfo.group;
                    var order = propinfo.order;
                    var groupname = propinfo.groupname;
                    var inputtype = propinfo.inputtype;
                    var readonly = propinfo.readonly;
                    var defaultval = propinfo.defaultval;
                    var defaultvalpre = propinfo.defaultvalpre;
                    var oneline = propinfo.oneline;
                    var selectdictname = propinfo.selectdictname;
                    var must = propinfo.must;
                    var validatorjsons = propinfo.validatorjsons;
                    var autoajax = propinfo.autoajax;
                    var autoajax_name = propinfo.autoajax_name;
                    var autoajax_attr = propinfo.autoajax_attr;
                    var autoajax_defaultval = propinfo.autoajax_defaultval;
                    var edithidden = propinfo.edithidden;
                    var editable = propinfo.editable;

                    if (groupname != null) {
                        container.append($('<div class="row">'
                            + '<div class="col-xs-12 addinfoline">'
                            + groupname + '</div>' + '</div>'));
                        row = null;
                        rowelementsize = 0;
                    }
                    if (edithidden) {
                        row = $('<input type="hidden" class="form-control" autoajax autoajax-name=\''
                            + (modelname.toLowerCase() + "." + name)
                            + '\' value="' + defaultval + '">');
                        container.append(row);
                        continue;
                    } else if (oneline) {
                        // 添加行
                        row = $('<div class="row"></div>');
                        container.append(row);
                        var col = $('<div class="col-xs-3 form-group"></div>');
                        row.append(col);
                        var label = $('<label class="col-xs-12 text-right addinfodetailkey control-label">'
                            + desc
                            + (must ? '<sup style="color:red;">*</sup>'
                                : '') + '</label>');
                        col.append(label);
                        var inputdiv = $('<div class="col-xs-9"></div>');
                        row.append(inputdiv);
                    } else {
                        // 添加行
                        if (rowelementsize == 0) {
                            row = $('<div class="row"></div>');
                            container.append(row);
                        }
                        var col = $('<div class="col-xs-6 form-group"></div>');
                        row.append(col);
                        var label = $('<label class="col-xs-6 text-right addinfodetailkey control-label">'
                            + desc
                            + (must ? '<sup style="color:red;">*</sup>'
                                : '') + '</label>');
                        col.append(label);
                        var inputdiv = $('<div class="col-xs-6 addinfodetailval"></div>');
                        col.append(inputdiv);
                    }
                    // /////////////////////////////添加元素/////////////////////////////

                    var autoajaxstr = '';
                    var autoajaxnamestr = '';
                    var autoajaxattrstr = '';
                    var autoajaxdefaultvalstr = '';
                    if (autoajax) {
                        autoajaxstr = ' autoajax ';
                        if (autoajax_name == null)
                            autoajaxnamestr = ' autoajax-name="'
                                + (modelname.toLowerCase() + "." + name)
                                + '" ';
                        else
                            autoajaxnamestr = ' autoajax-name="'
                                + autoajax_name + '" ';

                        if (autoajax_attr != null)
                            autoajaxattrstr = ' autoajax-attr="'
                                + autoajax_attr + '" ';
                        if (autoajax_defaultval != null)
                            autoajaxdefaultvalstr = ' autoajax-defaultval="'
                                + autoajax_defaultval + '"';
                    }
                    if (editmode && editable == false) {
                        readonly = true;
                    }
                    // 输入框
                    if (inputtype == "input") {
                        var readonlystr = '';
                        if (readonly) {
                            readonlystr = ' readonly="readonly" disabled';
                        }
                        if (defaultvalpre != null && defaultvalpre != '' && defaultvalpre != 'null' && defaultvalpre != 'NULL') {
                            inputdiv.append($(
                                '<div class="input-group" id="inputgroup' + modelname
                                + "_"
                                + name + '">' +
                                '<span class="input-group-addon" id="basic-addon1' + modelname
                                + "_"
                                + name + '">' + defaultvalpre + '</span>' +
                                '<input type="text" class="form-control" ' + readonlystr
                                + autoajaxstr
                                + autoajaxnamestr
                                + autoajaxattrstr
                                + autoajaxdefaultvalstr
                                + ' id="'
                                + modelname
                                + "_"
                                + name
                                + '" value="' + defaultval + '" defaultval="' + defaultval + '" aria-describedby="basic-addon1' + modelname
                                + "_"
                                + name + '">' +
                                '</div>'
                            ))
                            ;
                        } else {
                            inputdiv
                                .append($('<input type="text" class="form-control"'
                                    + readonlystr
                                    + autoajaxstr
                                    + autoajaxnamestr
                                    + autoajaxattrstr
                                    + autoajaxdefaultvalstr
                                    + ' id="'
                                    + modelname
                                    + "_"
                                    + name
                                    + '" value="' + defaultval + '" defaultval="' + defaultval + '">'));
                        }
                    } else if (inputtype == "selectyesno") {
                        var readonlystr = '';
                        if (readonly) {
                            readonlystr = ' disabled="disabled" ';
                        }
                        var select = $('<select class="form-control"'
                            + readonlystr + autoajaxstr
                            + autoajaxnamestr + autoajaxattrstr
                            + autoajaxdefaultvalstr + ' id="'
                            + modelname + "_" + name + '"></select>');
                        var selected1 = '';
                        var selected0 = '';
                        if (defaultval == '1'
                            || defaultval.toLowerCase() == 'yes') {
                            selected1 = 'selected="selected"';
                        } else if (defaultval == '0'
                            || defaultval.toLowerCase() == 'no') {
                            selected0 = 'selected="selected"';
                        }
                        select.append('<option value="1" ' + selected1
                            + '>是</option>');
                        select.append('<option value="0" ' + selected0
                            + '>否</option>');
                        inputdiv.append(select);
                    } else if (inputtype == "selectdict") {
                        var readonlystr = '';
                        if (readonly) {
                            readonlystr = ' disabled="disabled" ';
                        }
                        inputdiv.append($('<select class="form-control"'
                            + readonlystr + autoajaxstr
                            + autoajaxnamestr + autoajaxattrstr
                            + autoajaxdefaultvalstr + ' id="'
                            + modelname + "_" + name + '">'
                            + '</select>'));

                        loadseldict(modelname + "_" + name, selectdictname,
                            defaultval);
                    } else if (inputtype == "textarea") {
                        var readonlystr = '';
                        if (readonly) {
                            readonlystr = ' readonly="readonly" ';
                        }
                        inputdiv
                            .append($('<textarea class="form-control" rows="8"'
                                + readonlystr
                                + autoajaxstr
                                + autoajaxnamestr
                                + autoajaxattrstr
                                + autoajaxdefaultvalstr
                                + ' id="'
                                + modelname
                                + "_"
                                + name
                                + '">'
                                + defaultval + '</textarea>'));
                    } else if (inputtype == "selectdpt") {
                        var dftvals = defaultval.split(',');
                        var dftid = '';
                        var dftval = '--请选择管理机构--';

                        if (dftvals != null && dftvals.length == 2) {
                            if (dftvals[0] != '' && dftvals[1] != '') {
                                dftid = dftvals[0];
                                dftval = dftvals[1];
                            }
                        }

                        var div = $('<div id="div' + modelname + "_" + name
                            + '" class="dropdown"></div>')
                        var btn = $('<button class="btn btn-default dropdown-toggle btn-block"'
                            + 'type="button" id="'
                            + modelname
                            + "_"
                            + name
                            + '" data-toggle="dropdown"'
                            + 'aria-haspopup="true" aria-expanded="false" '
                            + autoajaxstr
                            + autoajaxnamestr
                            + autoajaxdefaultvalstr
                            + ' autoajax-attr="selitem" selitem="'
                            + dftid
                            + '">'
                            + '<p id="p'
                            + modelname
                            + "_"
                            + name
                            + '" class="text-left" style="margin:0;width:80%;float:left;overflow:hidden;">'
                            + dftval
                            + '</p>'
                            + '<span class="caret pull-right" style="margin-top: 7px;"></span>'
                            + '</button>');
                        var ul = $('<ul id="'
                            + modelname
                            + "_"
                            + name
                            + 'ul-1" class="dropdown-menu"'
                            + 'style="list-style-type: none;width:250px;"'
                            + 'aria-labelLedby="div' + modelname + "_"
                            + name + '"></ul>');
                        div.append(btn);
                        div.append(ul);

                        inputdiv.append(div);
                        inputdiv.removeClass('addinfodetailval');
                        inputdiv.css('padding-top', '5px');
                        loadseldpt(modelname
                            + "_"
                            + name
                            + 'ul-1', modelname
                            + "_"
                            + name);
                    } else if (inputtype == "selectxzqh") {
                        var dftvals = defaultval.split(',');
                        var dftid = '';
                        var dftval = '--请选择行政区划--';

                        if (dftvals != null && dftvals.length == 2) {
                            if (dftvals[0] != '' && dftvals[1] != '') {
                                dftid = dftvals[0];
                                dftval = dftvals[1];
                            }
                        }

                        var div = $('<div id="div' + modelname + "_" + name
                            + '" class="dropdown"></div>')
                        var btn = $('<button class="btn btn-default dropdown-toggle btn-block"'
                            + 'type="button" id="'
                            + modelname
                            + "_"
                            + name
                            + '" data-toggle="dropdown"'
                            + 'aria-haspopup="true" aria-expanded="false" '
                            + autoajaxstr
                            + autoajaxnamestr
                            + autoajaxdefaultvalstr
                            + ' autoajax-attr="selitem" selitem="'
                            + dftid
                            + '">'
                            + '<p id="p'
                            + modelname
                            + "_"
                            + name
                            + '" class="text-left" style="margin:0;width:80%;float:left;overflow:hidden;">'
                            + dftval
                            + '</p>'
                            + '<span class="caret pull-right" style="margin-top: 7px;"></span>'
                            + '</button>');
                        var ul = $('<ul id="'
                            + modelname
                            + "_"
                            + name
                            + 'ul-1" class="dropdown-menu"'
                            + 'style="list-style-type: none;width:250px;"'
                            + 'aria-labelLedby="div' + modelname + "_"
                            + name + '"></ul>');
                        div.append(btn);
                        div.append(ul);

                        inputdiv.append(div);
                        inputdiv.removeClass('addinfodetailval');
                        inputdiv.css('padding-top', '5px');
                        loadselxzqh(modelname
                            + "_"
                            + name
                            + 'ul-1', modelname + "_" + name);
                    } else if (inputtype == "selectdate") {
                        var div = $('<div class="input-group"></div>')
                        div
                            .append($('<input '
                                + autoajaxstr
                                + autoajaxnamestr
                                + autoajaxattrstr
                                + autoajaxdefaultvalstr
                                + ' id="'
                                + modelname
                                + "_"
                                + name
                                + '" class="form-control"'
                                + ' type="text" value="'
                                + defaultval
                                + '" readonly><span'
                                + ' class="input-group-addon"  onclick="WdatePicker({el:\''
                                + modelname
                                + "_"
                                + name
                                + '\'});" style="padding-left:5px;padding-right:5px;"><i class="icon-time"></i></span>'));
                        inputdiv.append(div);
                    } else if (inputtype == "selectyear") {
                        var div = $('<div class="input-group"></div>')
                        div
                            .append($('<input '
                                + autoajaxstr
                                + autoajaxnamestr
                                + autoajaxattrstr
                                + autoajaxdefaultvalstr
                                + ' id="'
                                + modelname
                                + "_"
                                + name
                                + '" class="form-control"'
                                + ' type="text" value="'
                                + defaultval
                                + '" readonly><span'
                                + ' class="input-group-addon"  onclick="WdatePicker({el:\''
                                + modelname
                                + "_"
                                + name
                                + '\',dateFmt:\'yyyy\'});" style="padding-left:5px;padding-right:5px;"><i class="icon-time"></i></span>'));
                        inputdiv.append(div);
                    } else if (inputtype == "selectmonth") {
                        var div = $('<div class="input-group"></div>')
                        div
                            .append($('<input '
                                + autoajaxstr
                                + autoajaxnamestr
                                + autoajaxattrstr
                                + autoajaxdefaultvalstr
                                + ' id="'
                                + modelname
                                + "_"
                                + name
                                + '" class="form-control"'
                                + ' type="text" value="'
                                + defaultval
                                + '" readonly><span'
                                + ' class="input-group-addon"  onclick="WdatePicker({el:\''
                                + modelname
                                + "_"
                                + name
                                + '\',dateFmt:\'yyyy-MM\'});" style="padding-left:5px;padding-right:5px;"><i class="icon-time"></i></span>'));
                        inputdiv.append(div);
                    }

                    if (oneline) {
                        row = null;
                        rowelementsize = 0;
                    } else {
                        rowelementsize++;
                        if (rowelementsize == 2) {
                            row = null;
                            rowelementsize = 0;
                        }
                    }

                    if (validatorjsons != null && validatorjsons != "") {
                        var inputid = modelname + "_" + name;
                        var validatorobj = {};
                        var validatorfilters = {};
                        for (var v = 0; v < validatorjsons.length; v++) {
                            var validatorjson = validatorjsons[v];
                            var jsonobj = JSON.parse(validatorjson);

                            if (inputtype == 'input' && defaultvalpre != null && defaultvalpre != '' && defaultvalpre != 'null' && defaultvalpre != 'NULL') {
                                jsonobj['settings'] = {};
                                jsonobj['settings']['posafter'] = "inputgroup" + modelname
                                    + "_"
                                    + name;
                            }

                            for (va in jsonobj) {
                                validatorfilters[va] = jsonobj[va];
                            }
                        }
                        validatorobj[inputid] = validatorfilters;


                        container.validateFormBind(validatorobj);
                    }
                }// for end
                if (needpic != null && needpic == true) {
                    container
                        .append($('<div class="row">'
                            + '<div class="col-xs-12 addinfoline">附件信息 &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;'
                            + '<i class="icon-paper-clip" style="color:blue;"></i>'
                            + '<input type="button" class="btn btn-link" value="添加附件" onclick="addpicture(\''
                            + containerid + '\');"></div></div>'));

                    container.append(row);

                }
                if (editmode == true && fjxxarray != null
                    && fjxxarray[0].indexOf('附件') >= 0) {
                    for (var k = 1; k < fjxxarray.length; k += 3) {
                        var filename = fjxxarray[k];
                        var filesize = fjxxarray[k + 1];
                        var fileid = fjxxarray[k + 2];

                        var rantoken = rand(1, 99999999);
                        var row = $('<div class="row" id="row' + rantoken
                            + '"></div>');
                        var col = $('<div class="col-xs-12" style="background:#e4f0ff;margin-top:2px;"></div>');
                        row.append(col);
                        col
                            .append('<i class="icon-paper-clip" style="clolr:bule;"></i>&nbsp;'
                            + '<label style="font-weight:normal;" id="label'
                            + rantoken
                            + '">'
                            + filename
                            + "("
                            + filesize
                            + "kb)"
                            + '</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
                            + '<input type="button" class="btn btn-link" value="删除" onclick="$(\'#row'
                            + rantoken
                            + '\').addClass(\'hide\');$(this).attr(\'removed\',\'1\')" id="originalfileids" removed=\"0\" fileid=\"'
                            + fileid + '\">');

                        $("#" + containerid).append(row);
                    }
                }
                if (callfn != null)
                    callfn();

            }
        });
}

// 加载数据字典，并以select展示，可供用户选择
function loadseldict(selectid, name, defaultval) {
    ajax('dic/querydicattr', {
        loginid: $("#userid").val(),
        name: name
    }, function (data) {
        if (ifResultOK(data)) {
            var records = getResultRecords(data);
            $("#" + selectid).empty();
            $("#" + selectid).append('<option value="">--请选择--</option>');
            if (records) {
                for (var i = 0; i < records.data.length; i++) {
                    var dict = records.data[i];

                    if (defaultval != null && dict.id == defaultval) {
                        $("#" + selectid).append(
                            '<option value="' + dict.id
                            + '" selected=\'selected\'>'
                            + dict.attrdesc + '</option>');
                    } else {
                        $("#" + selectid).append(
                            '<option value="' + dict.id + '">'
                            + dict.attrdesc + '</option>');
                    }

                }
            }
        } else {
        }
    });
}

function loadseldpt(containerdomid, label) {
    $('#' + containerdomid).etree({
        id: '-1',
        containerdomid: containerdomid,
        childrendatafn: dptsrvfn,
        createnodefn: createdptsrvfn
    }, function (node) {
        node.label = label;
        node.inited = '1';
        node.expanded = '1';
    });
}

function appuniquecheck(text, blockid, targetid) {
    var fswlx = $("#btnmodalfswnew").attr('secondclass');
    if (text == $("#" + targetid).attr('defaultval'))
        return true;

    return {
        url: "appurtenance/queryappbhexisted",
        data: {
            fswlx: fswlx,
            bh: text
        }
    };
}

/!**
 * 附属物编号后台验证接口,如何设计为输入时异步提交，而最终提交验证表单时，同步提交
 *!/
function verifyfswbh(val, verifyblock, verifytarget) {
    alert("ddd");
}

function addpicture(containerid) {
    var rantoken = rand(1, 99999999);

    var row = $('<div class="row hide" id="row' + rantoken + '"></div>');
    var col = $('<div class="col-xs-12" style="background:#e4f0ff;margin-top:2px;"></div>');
    row.append(col);
    col
        .append('<i class="icon-paper-clip" style="clolr:bule;"></i>&nbsp;'
        + '<label style="font-weight:normal;" id="label'
        + rantoken
        + '"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
        + '<input type="button" class="btn btn-link" value="删除" onclick="$(\'#row'
        + rantoken
        + '\').remove();"><input id="file'
        + rantoken
        + '" type="file" class="hide" autoajax autoajax-name="filelist" accept="image/!*" onchange="selectfile(this,\''
        + containerid + '\',\'' + rantoken + '\')">');

    $("#" + containerid).append(row);
    $("#file" + rantoken).click();
}

function selectfile(from, containerid, rantoken) {
    var file = $('#file' + rantoken).val();
    var size = '';
    try {
        var size = (from.files[0].size / 1024).toFixed(2);
    } catch (e) {
        size = '';
    }

    if (file != null && file != "") {
        $("#row" + rantoken).removeClass('hide');

        var pos = file.lastIndexOf('\\');
        var filename = file.substring(pos + 1);
        if (size == null || size == '')
            $("#label" + rantoken).html(filename);
        else
            $("#label" + rantoken).html(filename + "(" + size + "kb)");
    }
}

function loadfswtree() {
    $('#fswtree').empty();
    $("#fswtree").etree({
        id: -1,
        childrendatafn: fswclasssrvfn,
    }, null, null, null);
}*/

function fswclasssrvfn(hduan, fncallback) {
    // 加载附属物分类列表
    ajax('appurtenancetype/queryallhduanparent', {
            'loginid': $("#userid").val(),
            'hduanid': -1
        },
        function (data) {
            var nodes = new Array();
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                var fswcntlist = getResultMap(data).fswcnt;
                if (records) {

                    for (var i = 0; i < records.data.length; i++) {
                        var fswclass = records.data[i];
                        // 添加附属物分类文件夹
                        var fswclassid = fswclass.id;
                        var fswclassmc = fswclass.name;
                        var fswcnt = fswcntlist[i];
                        nodes.push({
                            id: hduan.hduanid + "_" + fswclassid,
                            name: name,
                            fswclassid: fswclassid,
                            fswclassmc: fswclassmc,
                            fswcnt: fswcnt,
                            createnodefn: createfswclassfn,
                            childrendatafn: fswsecondclassfn,
                            clickfn: function (fswclass, event) {
                                if (fswclass.expanded == '1') {
                                    $("#fswclassspanfolder"
                                        + fswclass.hduanid
                                        + "_" + fswclass.fswclassid).removeClass('icon-folder-close-alt');
                                    $("#fswclassspanfolder"
                                        + fswclass.hduanid
                                        + "_" + fswclass.fswclassid).addClass('icon-folder-open-alt');
                                } else {
                                    $("#fswclassspanfolder"
                                        + fswclass.hduanid
                                        + "_" + fswclass.fswclassid).removeClass('icon-folder-open-alt');
                                    $("#fswclassspanfolder"
                                        + fswclass.hduanid
                                        + "_" + fswclass.fswclassid).addClass('icon-folder-close-alt');
                                }
                            },
                            enterfn: function (fswclass) {
                                $('#fswclassspan'
                                    + fswclass.hduanid
                                    + "_" + fswclass.fswclassid).removeClass('hide');
                                $('#fswclassspan'
                                    + fswclass.hduanid
                                    + "_" + fswclass.fswclassid).css('cursor', 'pointer');
                            },
                            leavefn: function (fswclass) {
                                $('#fswclassspan'
                                    + fswclass.hduanid
                                    + "_" + fswclass.fswclassid).addClass('hide');
                            },
                            width: '100%',
                            height: '40px'
                        });
                    }
                }
            }
            fncallback(nodes);
        }, function () {
            fncallback(null);
        });
}

function createfswclassfn(fswclass, container) {
    container.attr('response-click', true);
    container.attr('response-expand', true);
    var name = fswclass.fswclassmc;
    var newhduanspan = $('<span response-click=true response-expand=true id="fswclassspanfolder'
        + fswclass.hduanid
        + "_"
        + fswclass.fswclassid
        + '" class="icon-folder-close-alt" style="width:10%;color:#337ab7;"></span>');
    var newa = $("<a  response-click=true response-expand=true  style='width:80%;padding:0 0 0 0'><label response-click=true response-expand=true  class='fswclasslabel'>&nbsp;"
        + name + "</label></a>");
    container.append(newhduanspan);
    container.append(newa);
}

function fswsecondclassfn(fswclass, fncallback) {
    ajax('appurtenancetype/querysubclass',
        {
            'loginid': $("#userid").val(),
            'parentid': fswclass.fswclassid,
            'hduanid': fswclass.hduanid
        },
        function (data) {
            var nodes = new Array();
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                if (records) {
                    for (var i = 0; i < records.data.length; i++) {
                        var secondclass = records.data[i];
                        var secondclassid = secondclass.id;
                        var fswsecondclassmc = secondclass.name;

                        nodes.push({
                            id: fswclass.hduanid
                            + "_" + fswclass.fswclassid + "_" + secondclassid,
                            name: fswsecondclassmc,
                            type: 'fswsecondclass',
                            fswlx: secondclassid,
                            selectedfn: function (event, fswsecondclass) {
                                var $img = $('#fswimg'
                                    + "_"
                                    + fswsecondclass.fswsecondclassid);

                                if ($img.is('img')) {
                                    var src = $img.attr('src');
                                    src = src.replace('.png', '_white.png');
                                    $img.attr('src', src);
                                }
                            },
                            unselectedfn: function (event, fswsecondclass) {
                                var $img = $('#fswimg'
                                    + "_"
                                    + fswsecondclass.fswsecondclassid);

                                if ($img.is('img')) {
                                    var src = $img.attr('src');
                                    src = src.replace('_white.png', '.png');
                                    $img.attr('src', src);
                                }
                            },
                            clickfn: function (node) {
                                loadaddfsw(node.fswlx, node.name);
                            },
                            enterfn: function (node) {
                                $('#fswsecondclassspan'

                                    + "_" + node.fswsecondclassid).removeClass('hide');
                                $('#fswsecondclassspan'

                                    + "_" + node.fswsecondclassid).css('cursor', 'pointer');
                            },
                            leavefn: function (node) {
                                $('#fswsecondclassspan'

                                    + "_" + node.fswsecondclassid).addClass('hide');
                            },
                        })
                    }
                }
            }
            fncallback(nodes);
        }, function () {
            fncallback(null);
        });
}