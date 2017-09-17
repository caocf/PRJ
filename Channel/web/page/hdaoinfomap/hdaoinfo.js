/**
 * Created by 25019 on 2015/10/21.
 */
$(document).ready(function () {
    $("#tabmap").click(
        function () {
            window.location.href = $("#basePath").val()
                + "page/hdaoinfomap/hdaoinfo.jsp";
        });
    resize();
    $(window).resize(function (event) {
        resize();
    });
    // 初始化左侧航道菜单折叠按钮事件
    $("#leftmenu").bind('click', function () {
        if ($("#leftmenu").attr('expand') == "1") {
            $("#leftmenu").removeClass('menuleftshouqi');
            $("#leftmenu").addClass('menuleftzhankai');
            $("#leftmenu").attr('expand', '0');

            $("#divleft").addClass('hide');
            $("#divright").css("margin-left", "15px");
        } else {
            $("#leftmenu").removeClass('menuleftzhankai');
            $("#leftmenu").addClass('menuleftshouqi');
            $("#leftmenu").attr('expand', '1');

            $("#divleft").removeClass('hide');
            $("#divright").css("margin-left", "370px");
        }
    });
    showSearchDiv(1);
});

function resize() {
    // 通过屏幕高度设置相应的滚动区域高度
    var clientHeight = window.innerHeight;
    $("#divleft").css('height', (clientHeight - 53) + 'px');
    $("#lefthdaohduantree").css('height', ($("#divleft").height()) + 'px');
    $("#leftfswtree").css('height', ($("#divleft").height()) + 'px');
    $("#divright").css('height', ($("#divleft").height() - 20) + 'px');
}

function datafnhduan(pnode, fncallback) {
    var hddj = $("#selhduandj").val();
    var content = $("#hduanipt").val();
    ajax('hangduan/searchhangduanhddj', {
        'xzqh': pnode.xzqh,
        'hddj': hddj,
        'content': content,
        'sshdid': pnode.hdaoid
    }, function (data) {
        if (ifResultOK(data)) {
            var newul = $('<ul id="hduanul" class="ultree" style="list-style-type: none;"></ul>');
            $("#hduanfolderli").append(newul);
            var records = getResultObj(data);
            if (records) {
                var nodes = new Array();
                for (var i = 0; i < records.length; i++) {
                    var hduan = records[i].hangduan;

                    // 添加航段文件夹
                    var hduanid = hduan.id;
                    var hduanbh = hduan.hdbh;// 航段编号
                    var namestart = hduan.hdqdmc;
                    var nameend = hduan.hdzdmc;
                    var hduanxzqhid = hduan.hdszxzqh;
                    var hduanxzqhname = records[i].xzqh;
                    var hduanmc = namestart + "-" + nameend;

                    nodes.push({
                        id: 'hduan' + pnode.hdaoid + "_" + hduanid,
                        name: hduanmc,
                        hdaoid: pnode.hdaoid,
                        hdaomc: pnode.hdaomc,
                        hdaobh: pnode.hdaobh,
                        hduancnt: pnode.hduancnt,
                        hduansjbh: pnode.hduansjbh,
                        hduanid: hduanid,
                        hduanbh: hduanbh,
                        hduanxzqhid: hduanxzqhid,
                        hduanxzqhname: hduanxzqhname,
                        hduanmc: hduanmc,
                        createnodefn: createhduannodefn,
                        clickfn: function (hduan, event) {
                            //$("#maphdaoid").val(pnode.hdaoid);
                            showleftfsw(hduan);
                            navonehduan(hduan);
                        }
                    });
                }
                fncallback(nodes);
            }
        }
    });
}

function createhduannodefn(node, container) {
    container.attr('response-click', true);
    container.attr('response-select', true);
    container.attr('response-expand', true);
    var name = node.name;
    var hduanxzqhname = node.hduanxzqhname;

    var newa = $("<a response-click=true response-expand=true>"
        + "<label response-click=true response-expand=true style='color:#666666;'>&nbsp;" + name + "(" + hduanxzqhname + ")" + "</label></a>");
    container.append(newa);
}

function datafnhdao(pnode, fncallback) {
    //加载骨干航道
    var data = {};
    var url = '';
    var sfgg = -1;
    if (pnode.id == "ggfl")
        sfgg = 1;
    //加载支线航道
    if (pnode.id == 'zxfl')
        sfgg = 0;
    var lx = $("#lxflag").val();
    var xzqh = null;
    if (lx == 1) {
        url = 'hangdao/searchallhangdao';
        xzqh = $("#selhdaosjbh").val();
        if (xzqh == null || xzqh < 1) {
            xzqh = $("#xzqhid").val();
        }
        var content = $("#hdaoipt").val();
        data = {
            'sfgg': sfgg,
            'xzqh': xzqh,
            'content': content
        };
    }
    if (lx == 2) {
        url = 'hangdao/searchhangdaohddj';
        xzqh = $("#selhduanxzqh").attr("selitem");
        var hdid = $("#selhdaolist").val();
        var hddj = $("#selhduandj").val();
        var content = $("#hduanipt").val();
        data = {
            'sfgg': sfgg,
            'xzqh': xzqh,
            'hdid': hdid,
            'hddj': hddj,
            'content': content
        };

    }
    ajax(url, data, function (data) {
        if (ifResultOK(data)) {
            var records = getResultRecords(data);
            if (records) {
                var nodes = new Array();
                for (var i = 0; i < records.data.length; i++) {
                    var hdao = records.data[i];
                    var id = hdao.id;
                    var no = hdao.hdbh;
                    var name = hdao.hdmc;
                    var hduancnt = hdao.hduancnt;
                    nodes.push({
                        id: 'hdao' + id,
                        name: name,
                        hdaoid: id,
                        hdaomc: name,
                        hdaobh: no,
                        hduancnt: hduancnt,
                        xzqh: xzqh,
                        createnodefn: createhdaonodefn,
                        childrendatafn: datafnhduan,
                        clickfn: function (node, event) {
                            if (node.expanded == 1) {
                                $("#span" + node.id).removeClass('icon-caret-right');
                                $("#span" + node.id).addClass('icon-caret-down');
                            } else {
                                $("#span" + node.id).addClass('icon-caret-right');
                                $("#span" + node.id).removeClass('icon-caret-down');
                            }
                            navonehdao(node);
                        }
                    });
                }
                fncallback(nodes);
            }
        }
    });

}

function createhdaonodefn(node, container) {
    container.attr('response-click', true);
    container.attr('response-select', true);
    container.attr('response-expand', true);
    var name = node.name;
    if (node.hduancnt != null) {
        name += " (" + node.hduancnt + "条航段)";
    }

    var newhduanspan = null;
    if (node.expanded == true)
        newhduanspan = $('<span id="span' + node.id + '" response-click=true response-expand=true class="icon-caret-down"></span>');
    else
        newhduanspan = $('<span id="span' + node.id + '" response-click=true response-expand=true class="icon-caret-right"></span>');
    var newa = $("<a response-select=true response-click=true response-expand=true style='color:inherit;'>"
        + "<label response-select=true response-click=true response-expand=true style='color:inherit;'>&nbsp;" + name + "</label></a>");

    container.append(newhduanspan);
    container.append(newa);
}

function datafnguganzhixian(pnode, fncallback) {
    var node = new Array();
    node.push({
        id: 'ggfl',
        name: '骨干航道',
        childrendatafn: datafnhdao,
        createnodefn: createhdaonodefn,
        clickfn: function (node, event) {
            if (node.expanded == 1) {
                $("#span" + node.id).removeClass('icon-caret-right');
                $("#span" + node.id).addClass('icon-caret-down');
            } else {
                $("#span" + node.id).addClass('icon-caret-right');
                $("#span" + node.id).removeClass('icon-caret-down');
            }
            navstack.length = 0;
            navallhdao();
        },
        inited: true,
        expanded: true
    });
    node.push({
        id: 'zxfl',
        name: '支线航道',
        childrendatafn: datafnhdao,
        createnodefn: createhdaonodefn,
        clickfn: function (node, event) {
            if (node.expanded == 1) {
                $("#span" + node.id).removeClass('icon-caret-right');
                $("#span" + node.id).addClass('icon-caret-down');
            } else {
                $("#span" + node.id).addClass('icon-caret-right');
                $("#span" + node.id).removeClass('icon-caret-down');
            }
            navstack.length = 0;
            navallhdao();
        },
        inited: true,
        expanded: true
    });

    fncallback(node);
}

function createfswclassnodefn(node, container) {
    container.attr('response-click', true);
    container.attr('response-select', true);
    container.attr('response-expand', true);
    var name = node.name + " (" + node.fswcnt + ")";
    var newhduanspan = $('<span id="fswspan' + node.id + '" response-click=true response-expand=true class="icon-caret-right"></span>');
    var newa = $("<a response-select=true response-click=true response-expand=true style='color:inherit;'>"
        + "<label response-select=true response-click=true response-expand=true style='color:inherit;'>&nbsp;" + name + "</label></a>");

    container.append(newhduanspan);
    container.append(newa);
}

function createfswnodefn(node, container) {
    container.attr('response-click', true);
    container.attr('response-select', true);
    container.attr('response-expand', true);

    // 附属物小图标选择
    var fswicon = 'icon-building';
    var imgicon = '';
    switch (parseInt(node.fswsecondclassid)) {
        case APP_NAVIGATIONMARK:// 航标
            imgicon = 'ic_navigationmark.png';
            break;
        case APP_BRIDGE:// 桥梁
            imgicon = 'ic_bridge.png';
            break;
        case APP_AQUEDUCT:// 渡槽
            imgicon = 'ic_flume.png';
            break;
        case APP_CABLE:// 缆线
            imgicon = 'ic_cabled-twist.png';
            break;
        case APP_PIPELINE:// 管道
            imgicon = 'ic_pipeline.png';
            break;
        case APP_TUNNEL:// 隧道
            imgicon = 'ic_tunnel.png';
            break;
        case APP_KYDOCK:// 码头
            imgicon = 'ic_wharf.png';
            break;
        case APP_HYDOCK:// 码头
            imgicon = 'ic_wharf.png';
            break;
        case APP_GWDOCK:// 码头
            imgicon = 'ic_wharf.png';
            break;
        case APP_SHIPYARD:// 船厂
            break;
        case APP_TAKEOUTFALL:// 取排水口
            imgicon = 'ic_outfall.png';
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            imgicon = 'ic_hydrologic-station.png';
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            imgicon = 'ic_guanlizhan.png';
            break;
        case APP_SERVICEAREA:// 服务区
            imgicon = 'ic_service.png';
            break;
        case APP_MOORINGAREA:// 锚泊区
            imgicon = 'ic_roadstead.png';
            break;
        case APP_HUB:// 枢纽
            imgicon = 'ic_key-position.png';
            break;
        case APP_DAM:// 坝
            imgicon = 'ic_dam.png';
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            imgicon = 'ic_revetment.png';
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            imgicon = 'ic_observation-point.png';
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            imgicon = 'ic_movie.png';
            break;
        case APP_MANUALOBSERVATION:
            imgicon = 'ic_observation-point.png';
            break;
        case APP_BOLLARD:// 系缆桩
            imgicon = "ic_bitt.png";
            break;
    }
    var fswspan = null;
    if (imgicon != '') {
        fswspan = $('<img id="fswimg' + node.hduanid
            + "_"
            + node.fswsecondclassid + "_" + node.fswid
            + '" src="img/'
            + imgicon
            + '" style="float:left;margin-top:12px;">');
    } else {
        fswspan = $('<span id="fswimg' + node.hduanid
            + "_"
            + node.fswsecondclassid + "_" + node.fswid
            + '" class="'
            + fswicon
            + '" style="float:left;margin-top:12px;"></span>');
    }
    var name = node.name;
    var newa = $("<a response-click=true response-select=true response-expand=true>"
        + "<label response-click=true  response-select=true response-expand=true style='color:black;'>&nbsp;" + name + "</label></a>");

    container.append(fswspan);
    container.append(newa);
}

function createfswsecondclassfn(fswsecondclass, container) {
    container.attr('response-select', true);
    container.attr('response-click', true);
    container.attr('response-expand', true);

    var newspan = $('<span response-click=true response-expand=true id="fswsecondclassspan'
        + fswsecondclass.fswsecondclassid
        + '" class="icon-caret-right"></span>');


    var newa = $("<a response-expand=true response-select=true response-click=true  style='color:inherit;'>"
        + "<label response-expand=true response-select=true response-click=true style='color:inherit;'>&nbsp;"
        + fswsecondclass.name + "</label></a>");

    container.append(newspan);
    container.append(newa);
}

function datafnfswsecondclass(fswclass, fncallback) {
    ajax('appurtenancetype/querysubclass',
        {
            'loginid': $("#userid").val(),
            'parentid': fswclass.fswclassid
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
                            hdaoid: fswclass.hdaoid,
                            hdaobh: fswclass.hdaobh,
                            hdaomc: fswclass.hdaomc,
                            hduanid: fswclass.hduanid,
                            hduanbh: fswclass.hduanbh,
                            hduanmc: fswclass.hduanmc,
                            hduanxzqhid: fswclass.hduanxzqhid,
                            hduanxzqhmc: fswclass.hduanxzqhmc,
                            fswclassid: fswclass.fswclassid,
                            fswclassmc: fswclass.fswclassmc,
                            fswsecondclassid: secondclassid,
                            fswsecondclassmc: fswsecondclassmc,
                            createnodefn: createfswsecondclassfn,
                            childrendatafn: datafnfsw,
                            clickfn: function (node, event) {
                                if (node.expanded == 1) {
                                    $('#fswsecondclassspan'
                                        + node.fswsecondclassid).removeClass('icon-caret-right');
                                    $('#fswsecondclassspan'
                                        + node.fswsecondclassid).addClass('icon-caret-down');
                                } else {
                                    $('#fswsecondclassspan'
                                        + node.fswsecondclassid).addClass('icon-caret-right');
                                    $('#fswsecondclassspan'
                                        + node.fswsecondclassid).removeClass('icon-caret-down');
                                }
                            }
                        })
                    }
                }
            }
            fncallback(nodes);
        }, function () {
            fncallback(null);
        });
}

function searchfnfswsecondclass(fswclass, fncallback) {
    ajax('appurtenancetype/querysubclass',
        {
            'loginid': $("#userid").val(),
            'parentid': fswclass.fswclassid
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
                            xzqh: fswclass.xzqh,
                            hdaoid: fswclass.hdaoid,
                            hduanid: fswclass.hduanid,
                            fswlx: fswclass.fswlx,
                            content: fswclass.content,
                            fswclassid: fswclass.fswclassid,
                            fswclassmc: fswclass.fswclassmc,
                            fswsecondclassid: secondclassid,
                            fswsecondclassmc: fswsecondclassmc,
                            createnodefn: createfswsecondclassfn,
                            childrendatafn: searchfnfsw,
                            clickfn: function (node, event) {
                                if (node.expanded == 1) {
                                    $('#fswsecondclassspan'
                                        + node.fswsecondclassid).removeClass('icon-caret-right');
                                    $('#fswsecondclassspan'
                                        + node.fswsecondclassid).addClass('icon-caret-down');
                                } else {
                                    $('#fswsecondclassspan'
                                        + node.fswsecondclassid).addClass('icon-caret-right');
                                    $('#fswsecondclassspan'
                                        + node.fswsecondclassid).removeClass('icon-caret-down');
                                }
                            }
                        })
                    }
                }
            }
            fncallback(nodes);
        }, function () {
            fncallback(null);
        });
}

function datafnfsw(pnode, fncallback) {
    ajax('appurtenance/queryappurtenances',
        {
            'loginid': $("#userid").val(),
            'pid': pnode.fswsecondclassid,
            'sshduanid': pnode.hduanid
        },
        function (data) {
            if (ifResultOK(data)) {
                var records = getResultRecords(data).data;
                var jdwds = new Array();
                if (records) {
                    var nodes = new Array();
                    try {
                        map.clearMarkers();
                        map.clearPopups();
                    } catch (e) {
                    }
                    for (var i = 0; i < records.length; i++) {
                        var pojo = records[i];
                        var pojolen = pojo.length;
                        //是否要显示字典信息
                        if (pojolen == undefined) {
                            var sj = pojo;
                        } else {
                            var sj = pojo[0];
                        }

                        // 添加附属物
                        var fswid = sj.id;
                        var fswbh = sj.bh;
                        var fswmc = sj.mc;
                        var fswsecondclassid = pnode.fswsecondclassid;
                        var jd = sj.jd;
                        var wd = sj.wd;

                        jdwds.push({
                            'jd': jd,
                            'wd': wd
                        });

                        nodes.push({
                            id: 'fsw' + pnode.hdaoid
                            + "_" + pnode.hduanid
                            + "_" + fswsecondclassid + "_" + fswid,
                            name: fswmc,
                            hdaoid: pnode.hdaoid,
                            hdaomc: pnode.hdaomc,
                            hdaobh: pnode.hdaobh,
                            hduanid: pnode.hduanid,
                            hduanbh: pnode.hduanbh,
                            hduanxzqhid: pnode.hduanxzqhid,
                            hduanxzqhname: pnode.hduanxzqhname,
                            hduanmc: pnode.hduanmc,
                            fswclassid: pnode.fswclassid,
                            fswclassname: pnode.fswclassname,
                            fswid: fswid,
                            fswbh: fswbh,
                            fswmc: fswmc,
                            fswsecondclassid: fswsecondclassid,
                            jd: jd,
                            wd: wd,
                            createnodefn: createfswnodefn,
                            clickfn: function (fsw, event) {
                                try {
                                    showmap(fsw.fswsecondclassid, fsw.fswid, fsw.jd, fsw.wd);
                                } catch (e) {
                                }
                            },
                            selectedfn: function (event, fswsecondclass) {
                                var $img = $('#fswimg' + fswsecondclass.hduanid
                                    + "_"
                                    + fswsecondclass.fswsecondclassid + "_" + fswsecondclass.fswid);

                                if ($img.is('img')) {
                                    var src = $img.attr('src');
                                    src = src.replace('.png', '_white.png');
                                    $img.attr('src', src);
                                }
                            },
                            unselectedfn: function (event, fswsecondclass) {
                                var $img = $('#fswimg' + fswsecondclass.hduanid
                                    + "_"
                                    + fswsecondclass.fswsecondclassid + "_" + fswsecondclass.fswid);

                                if ($img.is('img')) {
                                    var src = $img.attr('src');
                                    src = src.replace('_white.png', '.png');
                                    $img.attr('src', src);
                                }
                            }
                        });

                        //坐标没转
                        //地图标点

                        try {
                            if (i == records.length - 1) {
                                map.setCenterByLonLat(new WebtAPI.LonLat(jd, wd), 10);
                            }
                        } catch (e) {
                        }
                    }
                    try {
                        transferjwds(jdwds, function (jdwds) {
                            for (var i = 0; i < nodes.length; i++) {
                                var fsw = nodes[i];
                                addMapMarker(fsw.fswsecondclassid, fsw.fswid, jdwds[i].jd, jdwds[i].wd);
                            }
                        })
                    } catch (e) {
                    }
                    fncallback(nodes);
                }
            }
        });
}

function searchfnfsw(pnode, fncallback) {
    var url = "appurtenance/searchappbyxzqh";
    var data = {
        'xzqh': pnode.xzqh,
        'pid': pnode.fswsecondclassid,
        'sshdid': pnode.hdid,
        'sshduanid': pnode.hduanid,
        'content': pnode.content
    };
    ajax(url, data, function (data) {
        if (ifResultOK(data)) {
            var records = getResultRecords(data).data;
            var jdwds = new Array();
            if (records) {
                var nodes = new Array();
                try {
                    map.clearMarkers();
                    map.clearPopups();
                } catch (e) {
                }
                for (var i = 0; i < records.length; i++) {
                    var pojo = records[i];
                    var pojolen = pojo.length;
                    //是否要显示字典信息
                    if (pojolen == undefined) {
                        var sj = pojo;
                    } else {
                        var sj = pojo[0];
                    }

                    // 添加附属物
                    var fswid = sj.id;
                    var fswbh = sj.bh;
                    var fswmc = sj.mc;
                    var fswsecondclassid = pnode.fswsecondclassid;
                    var jd = sj.jd;
                    var wd = sj.wd;

                    jdwds.push({
                        'jd': jd,
                        'wd': wd
                    });

                    nodes.push({
                        id: 'fsw' + pnode.hdaoid
                        + "_" + pnode.hduanid
                        + "_" + fswsecondclassid + "_" + fswid,
                        name: fswmc,
                        hdaoid: pnode.hdaoid,
                        hduanid: pnode.hduanid,
                        fswclassid: pnode.fswclassid,
                        fswclassname: pnode.fswclassname,
                        fswid: fswid,
                        fswbh: fswbh,
                        fswmc: fswmc,
                        fswsecondclassid: fswsecondclassid,
                        jd: jd,
                        wd: wd,
                        createnodefn: createfswnodefn,
                        clickfn: function (fsw, event) {
                            try {
                                showmap(fsw.fswsecondclassid, fsw.fswid, fsw.jd, fsw.wd);
                            } catch (e) {
                            }
                        },
                        selectedfn: function (event, fswsecondclass) {
                            var $img = $('#fswimg' + fswsecondclass.hduanid
                                + "_"
                                + fswsecondclass.fswsecondclassid + "_" + fswsecondclass.fswid);

                            if ($img.is('img')) {
                                var src = $img.attr('src');
                                src = src.replace('.png', '_white.png');
                                $img.attr('src', src);
                            }
                        },
                        unselectedfn: function (event, fswsecondclass) {
                            var $img = $('#fswimg' + fswsecondclass.hduanid
                                + "_"
                                + fswsecondclass.fswsecondclassid + "_" + fswsecondclass.fswid);

                            if ($img.is('img')) {
                                var src = $img.attr('src');
                                src = src.replace('_white.png', '.png');
                                $img.attr('src', src);
                            }
                        }
                    });

                    //坐标没转
                    //地图标点

                    try {
                        if (i == records.length - 1) {
                            map.setCenterByLonLat(new WebtAPI.LonLat(jd, wd), 10);
                        }
                    } catch (e) {
                    }
                }
                try {
                    transferjwds(jdwds, function (jdwds) {
                        for (var i = 0; i < nodes.length; i++) {
                            var fsw = nodes[i];
                            addMapMarker(fsw.fswsecondclassid, fsw.fswid, jdwds[i].jd, jdwds[i].wd);
                        }
                    })
                } catch (e) {
                }
                fncallback(nodes);
            }
        }
    });
}

function addMapMarker(fswlx, fswid, jd, wd) {
    var iconpath = 'img/mapmr.png';
    switch (parseInt(fswlx)) {
        case APP_NAVIGATIONMARK:// 航标
            iconpath = 'img/maphb.png';
            break;
        case APP_BRIDGE:// 桥梁
            iconpath = 'img/mapql.png';
            break;
        case APP_AQUEDUCT:// 渡槽
            iconpath = 'img/mapdc.png';
            break;
        case APP_CABLE:// 缆线
            iconpath = 'img/maplx.png';
            break;
        case APP_PIPELINE:// 管道
            iconpath = 'img/mapgd.png';
            break;
        case APP_TUNNEL:// 隧道
            iconpath = 'img/mapsd.png';
            break;
        case APP_KYDOCK:// 码头
            iconpath = 'img/mapmt.png';
            break;
        case APP_HYDOCK:// 码头
            iconpath = 'img/mapmt.png';
            break;
        case APP_GWDOCK:// 码头
            iconpath = 'img/mapmt.png';
            break;
        case APP_SHIPYARD:// 船厂
            iconpath = 'img/mapcc.png';
            break;
        case APP_TAKEOUTFALL:// 取排水口
            iconpath = 'img/mapqps.png';
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            iconpath = 'img/mapswz.png';
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            iconpath = 'img/mapglz.png';
            break;
        case APP_SERVICEAREA:// 服务区
            iconpath = 'img/mapfwq.png';
            break;
        case APP_MOORINGAREA:// 锚泊区
            iconpath = 'img/mapmbq.png';
            break;
        case APP_HUB:// 枢纽
            iconpath = 'img/mapsn.png';
            break;
        case APP_DAM:// 坝
            iconpath = 'img/mapb.png';
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            iconpath = 'img/mapzzha.png';
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            iconpath = 'img/mapgcd.png';
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            iconpath = 'img/mapspgcd.png';
            break;
        case APP_MANUALOBSERVATION:
            iconpath = 'img/mapgcd.png';
            break;
        case APP_BOLLARD:// 系缆桩
            iconpath = "img/mapxlz.png";
            break;
    }
    var lonlat = new WebtAPI.LonLat(jd, wd);
    var icon = new WebtAPI.WIcon(iconpath,
        new WebtAPI.Size(28, 28),
        null, null);
    var marker = new WebtAPI.WMarker(lonlat, icon);
    map.markersLayer.addMarker(marker);
    marker.register("click", function () {
        showapppopup(fswlx, fswid, jd, wd, this);
    });
}

function datafnfswclass(pnode, fncallback) {
    // 加载附属物分类列表
    ajax('appurtenancetype/queryallhduanparent',
        {
            'loginid': $("#userid").val(),
            'hduanid': pnode.hduanid
        },
        function (data) {
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                var fswcntlist = getResultMap(data).fswcnt;
                if (records) {
                    var nodes = new Array();
                    for (var i = 0; i < records.data.length; i++) {
                        var fswclass = records.data[i];
                        // 添加附属物分类文件夹
                        var fswclassid = fswclass.id;
                        var fswclassname = fswclass.name;
                        var fswcnt = fswcntlist[i];
                        nodes.push({
                            id: 'fswclass' + pnode.hdaoid + "_" + pnode.hduanid + "_" + fswclassid,
                            name: fswclassname,
                            hdaoid: pnode.hdaoid,
                            hdaomc: pnode.hdaomc,
                            hdaobh: pnode.hdaobh,
                            hduancnt: pnode.hduancnt,
                            hduanid: pnode.hduanid,
                            hduanbh: pnode.hduanbh,
                            hduanxzqhid: pnode.hduanxzqhid,
                            hduanxzqhname: pnode.hduanxzqhname,
                            hduanmc: pnode.hduanmc,
                            fswcnt: fswcnt,
                            fswclassid: fswclassid,
                            fswclassname: fswclassname,
                            clickfn: function (node, event) {
                                if (node.expanded == 1) {
                                    $("#fswspan" + node.id).removeClass('icon-caret-right');
                                    $("#fswspan" + node.id).addClass('icon-caret-down');
                                } else {
                                    $("#fswspan" + node.id).addClass('icon-caret-right');
                                    $("#fswspan" + node.id).removeClass('icon-caret-down');
                                }
                            },
                            createnodefn: createfswclassnodefn,
                            childrendatafn: datafnfswsecondclass,
                        });
                    }
                    fncallback(nodes);
                }
            }
        });
}

function searchfnfswclass(pnode, fncallback) {
    // 加载附属物分类列表
    ajax('appurtenancetype/searchallhduanxzqh',
        {
            'xzqh': pnode.xzqh,
            'hduanid': pnode.hduanid,
            'fswlx': pnode.fswlx,
            'content': pnode.content
        },
        function (data) {
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                var fswcntlist = getResultMap(data).fswcnt;
                if (records) {
                    var nodes = new Array();
                    for (var i = 0; i < records.data.length; i++) {
                        var fswclass = records.data[i];
                        // 添加附属物分类文件夹
                        var fswclassid = fswclass.id;
                        var fswclassname = fswclass.name;
                        var fswcnt = fswcntlist[i];
                        nodes.push({
                            id: 'fswclass' + pnode.hdaoid + "_" + pnode.hduanid + "_" + fswclassid,
                            name: fswclassname,
                            xzqh: pnode.xzqh,
                            hdaoid: pnode.hdaoid,
                            hduanid: pnode.hduanid,
                            fswlx: pnode.fswlx,
                            content: pnode.content,
                            hduancnt: pnode.hduancnt,
                            fswcnt: fswcnt,
                            fswclassid: fswclassid,
                            fswclassname: fswclassname,
                            clickfn: function (node, event) {
                                if (node.expanded == 1) {
                                    $("#fswspan" + node.id).removeClass('icon-caret-right');
                                    $("#fswspan" + node.id).addClass('icon-caret-down');
                                } else {
                                    $("#fswspan" + node.id).addClass('icon-caret-right');
                                    $("#fswspan" + node.id).removeClass('icon-caret-down');
                                }
                            },
                            createnodefn: createfswclassnodefn,
                            childrendatafn: searchfnfswsecondclass,
                        });
                    }
                    fncallback(nodes);
                }
            }
        });
}

function showlefthdaohduan() {
    $("#lefthdaohduantree").show();
    $("#leftfswtree").hide();
    $("#leftsearchtree").hide();
    $("#lefthdaohduantree").empty();
    $("#lefthdaohduantree").etree({
        id: -1,
        childrendatafn: datafnguganzhixian,
    });
}

function showleftfsw(hduan) {
    //$("#maphduanid").val(hduan.hduanid);
    $("#lefthdaohduantree").hide();
    $("#leftfswtree").show();
    $("#leftsearchtree").hide();
    $("#leftfswtree").empty();
    $("#leftfswtree").etree({
        id: -1,
        hdaoid: hduan.hdaoid,
        hdaomc: hduan.hdaomc,
        hdaobh: hduan.hdaobh,
        hduancnt: hduan.hduancnt,
        hduanid: hduan.hduanid,
        hduanbh: hduan.hduanbh,
        hduanxzqhid: hduan.hduanxzqhid,
        hduanxzqhname: hduan.hduanxzqhname,
        hduanmc: hduan.hduanmc,
        childrendatafn: datafnfswclass,
    });
}

function showleftsearchresult() {
    $("#lefthdaohduantree").hide();
    $("#leftfswtree").hide();
    $("#leftsearchtree").show();
    $("#leftsearchtree").empty();
}

function searchleftfsw(hduan) {
    var xzqh = $("#selfswxzqh").attr("selitem");
    var arr = $("#selfswhduan").val().split(",");
    var fswlx = $("#selfswlx").val();
    var content = $("#fswipt").val();
    $("#lefthdaohduantree").hide();
    $("#leftfswtree").show();
    $("#leftsearchtree").hide();
    $("#leftfswtree").empty();
    $("#leftfswtree").etree({
        id: -1,
        xzqh: xzqh,
        hdaoid: arr[0],
        hduanid: arr[1],
        fswlx: fswlx,
        content: content,
        childrendatafn: searchfnfswclass
    });
}

var navstack = new Array();
var NAVHDAO = 1;
var NAVHDUAN = 2;
var NAVSEARCH = 3;

function gennavpath() {
    $("#leftnavpath").empty();
    if (navstack.length == 0) {
        $("#leftnavpath").append($('<li class="active">所有航道</li>'));
    } else {
        for (var i = navstack.length - 1; i >= 0; i--) {
            var obj = navstack[i];
            if (obj.type == NAVSEARCH) {
                $("#leftnavpath").append($('<li><a onclick="navback();">返回上一页</a></li>'));
                $("#leftnavpath").append($('<li class="active">搜索结果</li>'));
                return;
            } else {
                if (obj.type == NAVHDUAN) {
                    if (i == navstack.length - 1) {
                        $("#leftnavpath").prepend($('<li class="active">' + obj.hduanmc + '</li>'));
                    } else {
                        $("#leftnavpath").prepend($('<li><a onclick="navback();">' + obj.hduanmc + '</a></li>'));
                    }
                }
                if (obj.type == NAVHDAO) {
                    if (i == navstack.length - 1) {
                        $("#leftnavpath").prepend($('<li class="active">' + obj.hdaomc + '</li>'));
                    } else {
                        $("#leftnavpath").prepend($('<li><a onclick="navback();">' + obj.hdaomc + '</a></li>'));
                    }
                }

            }
        }
    }
}

function searchhdao() {
    $("#lefthdaohduantree").hide();
    $("#leftfswtree").hide();
    $("#leftsearchtree").show();
    navsearchresult();
}

function searchhduan() {
    $("#lefthdaohduantree").hide();
    $("#leftfswtree").hide();
    $("#leftsearchtree").show();
    navsearchresult();
}

function searchfsw() {
    $("#lefthdaohduantree").hide();
    $("#leftfswtree").hide();
    $("#leftsearchtree").show();
    navsearchresult();
}

// 导航前进，需要自行绑定数据
// 导航后退，无需绑定数据，返回原div的内容

function navallhdao() {
    gennavpath();
}

function navonehdao(hdao) {
    //如果首元素已为NAVHDAO,则进行替换
    if (navstack.length > 0) {
        var obj = navstack[navstack.length - 1];
        if (obj.type == NAVHDAO) {
            navstack.pop();
        }
    }
    hdao.type = NAVHDAO;
    navstack.push(hdao);
    gennavpath();
}

function navonehduan(hduan) {
    //导航到该界面时，只需要清空stack，重新添加航道与航段
    navstack.length = 0;
    var hdao = {
        hdaoid: hduan.hdaoid,
        hdaomc: hduan.hdaomc,
        hdaobh: hduan.hdaobh,
        hduancnt: hduan.hduancnt,
    }
    hdao.type = NAVHDAO;
    navstack.push(hdao);

    hduan.type = NAVHDUAN;
    navstack.push(hduan);
    gennavpath();
}

function navsearchresult() {
    var ret = {};
    //如果当前已为搜索结果，则不需要两次进stack
    if (navstack.length == 0) {
        ret.type = NAVSEARCH;
        navstack.push(ret);
        gennavpath();
    } else {
        var obj = navstack[navstack.length - 1];
        if (obj.type != NAVSEARCH) {
            ret.type = NAVSEARCH;
            navstack.push(ret);
            gennavpath();
        }
    }
}

function navback() {
    if (navstack.length == 0) {
        showlefthdaohduan();
        navallhdao();
    } else {
        var obj = navstack[navstack.length - 1];
        //如果顶上第一个元素为搜索,则返回到上一个界面
        if (obj.type == NAVSEARCH) {
            navstack.pop();//弹出一个元素
            if (navstack.length == 0) {
                gennavpath();
                $("#lefthdaohduantree").show();
                $("#leftfswtree").hide();
                $("#leftsearchtree").hide();
            } else {
                //根据下一个元素类型选择要显示的窗口
                var nxtobj = navstack[navstack.length - 1];
                gennavpath();
                if (nxtobj.type == NAVHDAO) {
                    $("#lefthdaohduantree").show();
                    $("#leftfswtree").hide();
                    $("#leftsearchtree").hide();
                }
                else if (nxtobj.type == NAVHDUAN) {
                    $("#lefthdaohduantree").hide();
                    $("#leftfswtree").show();
                    $("#leftsearchtree").hide();
                }
            }
        } else if (obj.type == NAVHDAO) {
            alert('something error!!');
        }
        else if (obj.type == NAVHDUAN) {
            navstack.pop();//弹出一个元素
            //根据下一个元素类型选择要显示的窗口
            var nxtobj = navstack[navstack.length - 1];

            gennavpath();
            if (nxtobj.type == NAVHDAO) {
                $("#lefthdaohduantree").show();
                $("#leftfswtree").hide();
                $("#leftsearchtree").hide();
            }
            else {
                alert('something error!!');
            }
        }
    }
}

function scrollto() {
    //$("#lefthdaohduantree").etreelocationTonode({id: 'hduan2_9'});
    $("#lefthdaohduantree").etreelocationTopath(['-1', 'ggfl', 'hdao1', 'hduan1_6']);
}

function bindHdaolist() {
    $("#selhdaolist").children().remove();
    var xzqh = null;
    var tempxzqh = $("#selhduanxzqh").attr('selitem');
    if (tempxzqh != -1) {
        xzqh = tempxzqh;
    }
    ajax('hangdao/queryallhangdao', {'xzqh': xzqh, 'sfgg': -1}, function (data) {
        $("#selhdaolist").append(
            '<option value="-1">全部航道</option>');
        if (ifResultOK(data)) {
            var res = getResultRecords(data).data;
            if (res != null) {
                for (var i = 0; i < res.length; i++) {
                    var hdao = res[i];
                    $("#selhdaolist").append(
                        '<option value="' + hdao.id + '">'
                        + hdao.hdmc + '</option>');
                }
            }
        }
    });
}

function bindFswHduan() {
    $("#selfswhduan").children().remove();
    var xzqh = null;
    var tempxzqh = $("#selfswxzqh").attr('selitem');
    if (tempxzqh != -1) {
        xzqh = tempxzqh;
    }
    ajax('hangduan/queryhangduanbyxzqh', {'xzqh': xzqh}, function (data) {
        $("#selfswhduan").append(
            '<option value="-1,-1">全部航段</option>');
        if (ifResultOK(data)) {
            var objs = getResultObj(data);
            if (objs) {
                for (var i = 0; i < objs.length; i++) {
                    var hduan = objs[i][0];
                    var hdao = objs[i][1];
                    $("#selfswhduan").append(
                        '<option value="' + hdao.id + ',' + hduan.id + '">'
                        + hdao.hdmc + ':' + hduan.hdqdmc + '-' + hduan.hdzdmc + '</option>');
                }
            }
        }
    });
}

function showSearchDiv(lx) {
    var headtd = null;
    $("#headtd").children().remove();
    switch (lx) {
        case 1:
            $("#hdaodiv").show();
            $("#hduandiv").hide();
            $("#fswdiv").hide();
            $("#ahdao").addClass("searchclk");
            $("#ahduan").removeClass("searchclk");
            $("#afsw").removeClass("searchclk");
            headtd = $('<select id="mfswlx" class="form-control middleinputgroup" onchange="loadtr()"><option value="1">航道</option></select>');
            hdaosearch();
            break;
        case 2:
            $("#hdaodiv").hide();
            $("#hduandiv").show();
            $("#fswdiv").hide();
            $("#ahduan").addClass("searchclk");
            $("#ahdao").removeClass("searchclk");
            $("#afsw").removeClass("searchclk");
            headtd = $('<select id="mfswlx" class="form-control middleinputgroup" onchange="loadtr()"><option value="2">航段</option></select>');
            hduansearch()
            break;
        case 3:
            $("#hdaodiv").hide();
            $("#hduandiv").hide();
            $("#fswdiv").show();
            $("#afsw").addClass("searchclk");
            $("#ahdao").removeClass("searchclk");
            $("#ahduan").removeClass("searchclk");
            headtd = $('<select id="mfswlx" class="form-control middleinputgroup" onchange="loadtr()"><option value="9">航标</option><option value="10">桥梁</option><option value="11">渡槽</option><option value="12">缆线</option><option value="13">管道</option><option value="14">隧道</option><option value="15">客运码头</option><option value="16">船厂</option><option value="17">取排水</option><option value="18">水文站</option><option value="19">货运码头</option><option value="20">管理站</option><option value="21">服务区</option><option value="22">锚泊区</option><option value="23">枢纽</option><option value="24">坝</option><option value="25">激光观测点</option><option value="26">视频观测点</option><option value="27">系缆桩</option><option value="28">整治护岸</option><option value="29">人工观测点</option><option value="30">公务码头</option></select>');
            fswsearch();
            break;
    }
    $("#headtd").append(headtd);
}

function loadsearchmodal() {
    loadtr();
    $("#navmapdiv").hide();
    $("#dtdiv").hide();
    $("#modalsearch").modal('show');


}

function loadtr() {
    $(".temptr").remove();
    var fswlx = $("#mfswlx").val();
    var data = {
        'fswlx': fswlx
    };
    $.ajax({
        url: 'searchpro/loadfield',
        type: 'post',
        dataType: 'json',
        data: data,
        success: function (data) {
            var p = data.obj;
            var newtd = "<td><select class='form-control middleinputgroup' onchange='addtd(this)'>";
            for (var i in p) {
                var obj = p[i];
                if (i != 0 && !obj.vname == '') {
                    if (obj.pname == 'sshdid' || obj.pname == 'sshdaoid') {
                        newtd = newtd + "<option value='hd," + obj.pname + ",所属航道名称," + obj.flag + "," + obj.dics + "'>所属航道名称</option>";
                    } else if (obj.pname == 'sshduanid') {
                        newtd = newtd + "<option value='hd,hdqdmc,所属航段起点名称," + obj.flag + "," + obj.dics + "'>所属航段起点名称</option>";
                        newtd = newtd + "<option value='hd,hdzdmc,所属航段终点名称," + obj.flag + "," + obj.dics + "'>所属航段终点名称</option>";
                    } else {
                        newtd = newtd + "<option value='" + obj.tname + "," + obj.pname + "," + obj.vname + "," + obj.flag + "," + obj.dics + "'>" + obj.vname + "</option>";
                    }
                }
            }
            newtd = newtd + ("</select></td>");
            $("#hidetd").val(newtd);
        }
    });
}

function addtr() {
    var newtd = $("#hidetd").val();
    var $newtr = $("<tr class='temptr'></tr>");
    $newtr.append($(newtd));
    $("#searchtable").append($newtr);
    $newtr.children().children().trigger("change");
}

function deltr(obj) {
    var trobj = $(obj.parentElement.parentElement);
    trobj.remove();
}

function addtd(obj) {
    var v = obj.value;
    var arr = v.split(",");
    var len = arr.length;
    var newtd = "";
    var newtr = $(obj.parentElement.parentElement);
    newtr.find('.temptd').remove();
    switch (arr[0]) {
        case 'String'://字符串
            newtd = $("<td class='temptd'><select class='form-control middleinputgroup' style='float:left;width: 80px;margin-left: 10px'><option value='='>匹配</option><option value='like'>包含</option></select><input name='iptname' placeholder='请输入关键字'  class='form-control' style='float:left;width: 240px;margin-left: 10px;'/></td><td class='temptd'><a onclick='addtr()'  class='icon-plus' style='margin-left:10px;color: green'/><a onclick='deltr(this)'  class='icon-remove' style='margin-left:10px;color: red '/></td>");
            break;
        case 'Integer'://字典
            if (arr[3] == 'true') {
                var temptd = "<td class='temptd'><select  class='form-control middleinputgroup' style='float:left;width:auto;margin-left: 10px'>";
                for (var i = 4; i < len; i = i + 2) {
                    temptd = temptd + "<option value='" + arr[i] + "'>" + arr[i + 1] + "</option>";
                }
                temptd = temptd + "</select></td><td class='temptd'><a onclick='addtr()'  class='icon-plus' style='margin-left:10px;color: green'/><a onclick='deltr(this)'  class='icon-remove' style='margin-left:10px;color: red '/></td>";
                newtd = $(temptd);
            } else {
                if (arr[2].indexOf("是否") == -1) {//类型是flag的 是否//int
                    newtd = $("<td class='temptd'><select class='form-control middleinputgroup' style='float:left;width: 80px;margin-left: 10px;margin-right: 10px'><option value='='>&nbsp;&nbsp;=</option><option value='>'>&nbsp;&nbsp;></option><option value='<'>&nbsp;&nbsp;<</option></select><input class='form-control' style='width: 240px;margin-left: 100px' name='iptname' placeholder='请输入数字'  style='margin-left:10px '/></td><td class='temptd'><a onclick='addtr()'  class='icon-plus' style='margin-left:10px;color: green'/><a onclick='deltr(this)'  class='icon-remove' style='margin-left:10px;color: red '/></td>");
                } else {//boolean
                    newtd = $("<td class='temptd'><select class='form-control middleinputgroup' style='float:left;width: 80px;margin-left: 10px'><option value='1'>是</option><option value='0'>否</option></select></td><td class='temptd'><a onclick='addtr()'  class='icon-plus' style='margin-left:10px;color: green'/><a onclick='deltr(this)'  class='icon-remove' style='margin-left:10px;color: red '/></td>");
                }
            }
            break;
        case 'Double'://实数
            newtd = $("<td class='temptd'><select class='form-control middleinputgroup' style='float:left;width: 80px;margin-left: 10px'><option value='='>&nbsp;&nbsp;=</option><option value='>'>&nbsp;&nbsp;></option><option value='<'>&nbsp;&nbsp;<</option></select><input name='iptname' class='form-control' style='width: 240px;float:left;margin-left: 10px' placeholder='请输入数字'/></td><td class='temptd'><a onclick='addtr()'  class='icon-plus' style='margin-left:10px;color: green'/><a onclick='deltr(this)'  class='icon-remove' style='margin-left:10px;color: red '/></td>");
            break;
        case 'Date'://时间
            newtd = $("<td class='temptd'><input  name='starttime' type='text' onClick='WdatePicker()' placeholder='开始时间'  class='form-control'  style='margin-left:10px;width: 140px;float: left'/><label style='float: left;padding-top: 5px;margin-left: 16px;margin-right: 16px'>至</label><input name='endtime' type='text' onClick='WdatePicker()' placeholder='结束时间' class='form-control' style='width: 140px;float: left'/></td><td class='temptd'><a onclick='addtr()'  class='icon-plus' style='margin-left:10px;color: green'/><a onclick='deltr(this)'  class='icon-remove' style='margin-left:10px;color: red '/></td>");
            break;
        case 'hd'://航道/段相关
            newtd = $("<td class='temptd'><select class='form-control middleinputgroup' style='float:left;width: 80px;margin-left: 10px'><option value='='>匹配</option><option value='like'>包含</option></select><input name='iptname' placeholder='请输入关键字'  class='form-control' style='float:left;width: 240px;margin-left: 10px;'/></td><td class='temptd'><a onclick='addtr()'  class='icon-plus' style='margin-left:10px;color: green'/><a onclick='deltr(this)'  class='icon-remove' style='margin-left:10px;color: red '/></td>");
            break;
    }
    newtr.append(newtd);
}

function searchpro() {
    var trs = $(".temptr");
    var len = trs.length;
    var conditions = new Array();
    var ssel = "";
    var fipt = "";
    for (var i = 0; i < len; i++) {
        var cdt = "";
        var $td = $(trs[i]).children('td');
        var fsel = $td.eq(0).find("select").val();
        var arr = fsel.split(",");
        ssel = $td.eq(1).find("select").val();
        fipt = $td.eq(1).find("input").val();
        if (fipt != "") {
            switch (arr[0]) {
                case 'String':
                    if (ssel == 'like') {
                        cdt = "a." + arr[1] + " " + ssel + " '%" + fipt + "%'";
                    } else {
                        cdt = "a." + arr[1] + ssel + " '" + fipt + "'";
                    }
                    break;
                case 'Integer':
                    if (arr[3] == 'false' && arr[2].indexOf("是否") == -1) {
                        cdt = "a." + arr[1] + ssel + fipt;
                    } else {
                        cdt = "a." + arr[1] + "=" + ssel;
                    }
                    break;
                case 'Double':
                    cdt = "a." + arr[1] + ssel + fipt;
                    break;
                case 'Date':
                    var endtimeipt = $td.eq(1).find("input").eq(1).val();
                    if (fipt == null || fipt == undefined || fipt == '') {
                        if (endtimeipt == null || endtimeipt == undefined || endtimeipt == '') {

                        } else {
                            cdt = "a." + arr[1] + "<='" + endtimeipt + " 23:59:59'";
                        }
                    } else {
                        if (endtimeipt == null || endtimeipt == undefined || endtimeipt == '') {
                            cdt = "a." + arr[1] + ">='" + fipt + " 00:00:00' ";
                        } else {
                            cdt = "a." + arr[1] + ">='" + fipt + " 00:00:00' and a." + arr[1] + "<='" + endtimeipt + " 23:59:59'";
                        }
                    }
                    break;
                case 'hd':
                    //确认航道表还是航段表 sql语句
                    if (arr[1] == 'hdqdmc' || arr[1] == 'hdzdmc') {
                        if (ssel == 'like') {
                            cdt = "c." + arr[1] + " " + ssel + " '%" + fipt + "%'";
                        } else {
                            cdt = "c." + arr[1] + " " + ssel + " '" + fipt + "'";
                        }
                    } else {
                        if (ssel == 'like') {
                            cdt = "b.hdmc " + ssel + " '%" + fipt + "%'";
                        } else {
                            cdt = "b.hdmc " + ssel + " '" + fipt + "'";
                        }
                    }

                    break;
            }
        } else {
            if (arr[0] == 'Date') {
                var endtimeipt = $td.eq(1).find("input").eq(1).val();
                if (fipt == null || fipt == undefined || fipt == '') {
                    if (endtimeipt == null || endtimeipt == undefined || endtimeipt == '') {

                    } else {
                        cdt = "a." + arr[1] + "<='" + endtimeipt + " 23:59:59'";
                    }
                } else {
                    if (endtimeipt == null || endtimeipt == undefined || endtimeipt == '') {
                        cdt = "a." + arr[1] + ">='" + fipt + " 00:00:00' ";
                    } else {
                        cdt = "a." + arr[1] + ">='" + fipt + " 00:00:00' and a." + arr[1] + "<='" + endtimeipt + " 23:59:59'";
                    }
                }
            }
        }
        if (cdt != '') {
            conditions.push(cdt);
        }
    }
    var fswlx = $("#mfswlx").val();
    var data = {
        'fswlx': fswlx
    };
    var page = $("#dt").attr('page');
    var labcount = 0;
    for (var i in conditions) {
        data["contents[" + i + "]"] = conditions[i];
    }
    $("#dt").adddatatable({
        url: "searchpro/searchpro",
        data: data,
        rows: 10,
        page: page,
        datafn: function (data) {
            var records = getResultRecords(data);
            var ret = {};
            var list = new Array();
            if (records != null) {
                for (var i in records.data) {
                    var dt = {};
                    dt.id = records.data[i].id;
                    if (fswlx == 1) {
                        dt.bh = records.data[i].hdbh;
                        dt.mc = records.data[i].hdmc;
                        dt.jd = 0;
                        dt.wd = 0;
                    } else if (fswlx == 2) {
                        dt.bh = records.data[i].hdbh;
                        dt.mc = records.data[i].hdqdmc + "-" + records.data[i].hdzdmc;
                        dt.jd = 0;
                        dt.wd = 0;
                    } else {
                        dt.bh = records.data[i].bh;
                        dt.mc = records.data[i].mc;
                        dt.jd = records.data[i].jd;
                        dt.wd = records.data[i].wd;
                    }
                    list.push(dt);
                }
            }
            ret.data = list;
            ret.total = records.total;
            labcount = records.total;
            $("#labcount").text("搜索结果(共" + labcount + "个)");
            ret.secho = data.map.sEcho;
            return ret;
        },
        columns: [
            {mDataProp: null, title: '序号'},
            {mDataProp: 'bh', title: '编号'},
            {mDataProp: 'mc', title: '名称'},
            {mDataProp: null, title: '操作'}
        ],
        columndefs: [{
            targets: 3,
            render: function (data, type, row) {
                var ret = '<a class="btn btn-link btnoper" data-toggle="tooltip" data-placement="top" title="地图查看" onclick="showSimpleMap(\''
                    + fswlx + ',' + row.id + ',' + row.jd + ',' + row.wd
                    + '\')">地图查看</a>';
                return ret;
            }
        }],
        fncreatedrow: function (nRow, aData, iDataIndex) {
            var start = parseInt($('#dt').attr(
                'start'));
            $("td:eq(0)", nRow).html(
                (start + iDataIndex))
        }
    });
    $("#navmapdiv").show();
    $("#dtdiv").show();
}

function hdaosearch() {
    $("#leftnavpath").empty();
    $("#leftnavpath").append($('<li class="active">所有航道</li>'));
    $("#lxflag").val(1);
    showlefthdaohduan();
}

function hduansearch() {
    $("#leftnavpath").empty();
    $("#leftnavpath").append($('<li class="active">所有航段</li>'));
    $("#lxflag").val(2);
    showlefthdaohduan();
}

function fswsearch() {
    $("#leftnavpath").empty();
    $("#leftnavpath").append($('<li class="active">所有附属物</li>'));
    $("#lxflag").val(3);
    searchleftfsw();
}

function showMultiMap() {
    $("#modalsearch").modal('hide');
}

function showSimpleMap(str) {
    $("#modalsearch").modal('hide');
    var arr = str.split(',');
    var fswlx = arr[0];
    if (fswlx > 2) {
        var fswid = arr[1];
        var jd = arr[2];
        var wd = arr[3];
        showmap(fswlx, fswid, jd, wd);
    }
}

function gotohduan() {
    window.location.href = $('#basePath').val() + '/page/hdaoinfomap/hduanlist.jsp';
}
