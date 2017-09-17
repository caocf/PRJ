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
    $("#selhdaolist").change(function () {
        initHduanlist();
    });
    var xzqh = getUrlParam('xzqh');
    var fswlx = getUrlParam('fswlx')
    var sshdid = getUrlParam('sshdid')
    initXzqh(xzqh);
    initFswlist();
    $("#gotoflag").val("1");
    setappinfolist(fswlx, xzqh, -1, sshdid, "");
});

function resize() {
    // 通过屏幕高度设置相应的滚动区域高度
    var clientHeight = window.innerHeight;
    $("#divleft").css('height', (clientHeight - 53) + 'px');
    $("#lefthdaohduantree").css('height', ($("#divleft").height()) + 'px');
    $("#leftfswtree").css('height', ($("#divleft").height()) + 'px');
    $("#divright").css('height', ($("#divleft").height() - 20) + 'px');
}

function initXzqh(xzqh) {
    if (xzqh == -1) {
        xzqh = 1;
    }
    $("#selxzqhdiv").addselxzqh(
        {
            id: "selxzqh",
            defaultval: xzqh,
            selectfn: initHdaolist
        }
    );
}

function initHdaolist() {
    $("#selhdaolist").children().remove();
    var xzqh = null;
    var tempxzqh = $("#selxzqh").attr('selitem');
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
                    $("#selhdaolist").append('<option  value="' + hdao.id + '">' + hdao.hdmc + '</option>');
                }
            }
        }
    });
}

function initHduanlist() {
    $("#selhduanlist").children().remove();
    var xzqh = null;
    var tempxzqh = $("#selxzqh").attr('selitem');
    if (tempxzqh != -1) {
        xzqh = tempxzqh;
    }
    var hdaoid = null;
    hdaoid = $("#selhdaolist").val();
    ajax('hangduan/queryhangduanbysshdid', {
        'loginid': $("#userid").val(),
        'sshdid': hdaoid,
        'xzqh': xzqh
    }, function (data) {
        if (ifResultOK(data)) {
            $("#selhduanlist").append(
                '<option value="-1">全部航段</option>');
            var records = getResultObj(data);
            if (records) {
                for (var i = 0; i < records.length; i++) {
                    var hduan = records[i].hangduan;
                    $("#selhduanlist").append('<option  value="' + hduan.id + '">' + hduan.hdqdmc + '-' + hduan.hdzdmc + '</option>');
                }
            }
        }
    });
}

function initFswlist() {
    $("#selfswlist").children().remove();
    ajax('appurtenancetype/queryappbytype', {'type': 2}, function (data) {
        if (ifResultOK(data)) {
            $("#selfswlist").append('<option value="-1">请选择</option>');
            var res = getResultRecords(data).data;
            if (res != null) {
                for (var i = 0; i < res.length; i++) {
                    var fsw = res[i];
                    $("#selfswlist").append('<option  value="' + fsw.id + '">' + fsw.name + '</option>');
                }
            }
        }
    });
}


function loadappinfolist() {
    $("#gotoflag").val("0");
    var fswlx = $("#selfswlist").val();
    var xzqh = $("#selxzqh").attr("selitem");
    var hdaoid = $("#selhdaolist").val();
    var hduanid = $("#selhduanlist").val();
    var content = $("#appname").val();
    setappinfolist(fswlx, xzqh, hdaoid, hduanid, content);
}

function setappinfolist(fswlx, xzqh, hdaoid, hduanid, content) {
    if (fswlx != null && fswlx != "" && fswlx != "-1") {
        fswlx = parseInt(fswlx);
        var columns = [
            {
                mDataProp: null,
                sTitle: '序号'
            }, {
                mDataProp: 'fswbh',
                sTitle: '编号'
            }, {
                mDataProp: 'fswmc',
                sTitle: '名称'
            }];
        switch (fswlx) {
            case APP_NAVIGATIONMARK:// 航标
                columns.push({mDataProp: 'zd3', sTitle: '标志类型'});
                columns.push({mDataProp: 'zd7', sTitle: '支撑方式'});
                columns.push({mDataProp: 'zd8', sTitle: '标志结构'});
                columns.push({mDataProp: 'zd4', sTitle: '光学性质'});
                columns.push({mDataProp: 'zd5', sTitle: '灯质信号'});
                columns.push({mDataProp: 'zd6', sTitle: '灯标颜色'});
                break;
            case APP_BRIDGE:// 桥梁
                columns.push({mDataProp: 'zd3', sTitle: '结构形式'});
                columns.push({mDataProp: 'zd4', sTitle: '用途分类'});
                break;
            case APP_AQUEDUCT:// 渡槽
                columns.push({mDataProp: 'ytfl', sTitle: '用途分类'});
                break;
            case APP_CABLE:// 缆线
                columns.push({mDataProp: 'zd3', sTitle: '缆线种类'});
                break;
            case APP_PIPELINE:// 管道
                columns.push({mDataProp: 'cyxs', sTitle: '穿越型式'});
                break;
            case APP_TUNNEL:// 隧道
                columns.push({mDataProp: 'ytfl', sTitle: '用途分类'});
                break;
            case APP_KYDOCK:// 客运码头
                columns.push({mDataProp: 'zd3', sTitle: '结构类型'});
                break;
            case APP_HYDOCK:// 货运码头
                columns.push({mDataProp: 'zd3', sTitle: '结构类型'});
                break;
            case APP_GWDOCK:// 公务码头
                columns.push({mDataProp: 'zd3', sTitle: '结构类型'});
                break;
            case APP_SHIPYARD:// 船厂
                columns.push({mDataProp: 'xzcwj', sTitle: '修造船吨级'});
                break;
            case APP_TAKEOUTFALL:// 取排水口
                columns.push({mDataProp: 'zd3', sTitle: '类型'});
                break;
            case APP_HYDROLOGICALSTATION:// 水文站
                break;
            case APP_MANAGEMENTSTATION:// 管理站
                break;
            case APP_SERVICEAREA:// 服务区
                break;
            case APP_MOORINGAREA:// 锚泊区
                break;
            case APP_HUB:// 枢纽
                columns.push({mDataProp: 'zd4', sTitle: '通航类型'});
                columns.push({mDataProp: 'zd3', sTitle: '型式'});
                columns.push({mDataProp: 'zd5', sTitle: '过船设施位置'});
                break;
            case APP_DAM:// 坝
                columns.push({mDataProp: 'zd3', sTitle: '类型'});
                break;
            case APP_REGULATIONREVEMENT:// 整治护岸
                break;
            case APP_LASEROBSERVATION:// 激光流量观测点
                break;
            case APP_VIDEOOBSERVATION:// 视频观测点
                break;
            case APP_MANUALOBSERVATION:// 人工流量观测点
                break;
            case APP_BOLLARD:// 系缆桩
                break;
        }
        columns.push({mDataProp: null, sTitle: '操作'});
        var len = columns.length;
        var columndefs = [
            {
                targets: 0,
                sWidth: "50px"
            }
        ];
        $("#divtable").empty();
        $("#divtable").append($('<table id="dt" class="col-xs-12 nomargin nopadding"/>'));
        $("#dt").attr('inited', '0');
        $("#dt").adddatatable({
            url: 'appurtenance/zhcxapps',
            data: {
                'xzqh': xzqh,
                'sshdid': hdaoid,
                'sshduanid': hduanid,
                'fswlx': fswlx,
                'content': content
            },
            rows: 10,
            datafn: function (data) {
                var records = getResultRecords(data);
                var bhpre = getResultMap(data).bhpre;
                var ret = {};
                var list = new Array();
                if (records != null) {
                    var resultdata = records.data;
                    var recordslen = resultdata.length;
                    for (var i in resultdata) {
                        var dt = {};
                        var temppojo = resultdata[i];
                        var pojo = temppojo[0];
                        var hdao = temppojo[1];
                        var hduan = temppojo[2];
                        var pojolen = temppojo.length;
                        //是否要显示字典信息
                        if (pojolen > 3) {
                            //字典赋值
                            for (var j = 3; j < pojolen; j++) {
                                var zd = temppojo[j];
                                dt["zd" + j] = zd["attrdesc"];
                            }
                        }
                        // 添加附属物
                        var fswid = pojo.id;
                        var fswbh = pojo.bh;
                        var fswmc = pojo.mc;
                        dt.fswid = fswid;
                        dt.fswbh = bhpre + fswbh;
                        dt.fswmc = fswmc;
                        dt.bhpre = bhpre;
                        dt.hdaoid = hdao.id;
                        dt.hdaomc = hdao.hdmc;
                        dt.hduanid = hduan.id;
                        dt.hduanmc = hduan.hdqdmc + '-' + hduan.hdzdmc;
                        for (var key in pojo) {
                            dt[key] = pojo[key];
                        }
                        list.push(dt);
                    }
                }
                ret.data = list;
                ret.total = records.total;
                ret.secho = data.map.sEcho;
                return ret;
            },
            columns: columns,
            columndefs: columndefs,
            fncreatedrow: function (nRow, aData, iDataIndex) {
                $(nRow).attr('id', 'tablerow' + aData.fswid);
                var start = parseInt($('#dt').attr('start'));
                $("td:eq(0)", nRow).html((start + iDataIndex));
                $("td:eq(" + (len - 1) + ")", nRow).html(("<a onclick='viewinfo(" + fswlx + "," + aData.fswid + ")'>详情</a>"));
            }
        }, {
            "scrollY": scrollY
        });
    } else {
        alert("请选择附属物!");
    }
}

function gotohduan() {
    window.location.href = $('#basePath').val() + '/page/hdaoinfomap/hduanlist.jsp';
}

function exporttable() {
    var xzqh = $("#selxzqh").attr("selitem");
    window.location.href = $("#basePath").val() + "hangdao/exporthduan?xzqh=" + xzqh;
}

function cleardetail() {
    $('#detailinfodiv').empty();
    resize();
}

function viewinfo(fswlx, id) {
    cleardetail();
    $('#detailinfodiv').empty();
    ajax('appurtenance/queryappurtenanceinfo', {
        'id': id,
        'fswlx': fswlx
    }, function (data) {
        if (ifResultOK(data)) {
            var datalist = getResultObj(data);
            if (datalist != null && datalist.length > 0) {
                showdetail(datalist);
            }
        } else {
            var datalist = [];
            showdetail(datalist);
        }
    });
    $("#modalview").modal('show');
}

function showdetail(datalist) {
    for (var i = 0; i < datalist.length; i++) {
        var data = datalist[i];

        var newcol = null;

        // 如果是附件
        if (data.length >= 4 && data[0].indexOf('附件') >= 0) {
            if ((data.length - 1) % 3 == 0) {
                // 添加一行
                newrow = $('<div class="row infodetailrow borderbottom"></div>');
                // 添加左边列
                newcolleft = $('<div class="col-xs-3 text-right infodetailkey">'
                    + data[0] + '</div>');
                newrow.append(newcolleft);
                // 添加右边列
                newcolright = $('<div class="col-xs-9 text-left infodetailval borderleft"></div>');
                newrow.append(newcolright);
                var ids = [];
                for (var j = 1; j < data.length; j += 3) {
                    var fileid = data[j + 2];
                    ids[ids.length] = fileid;
                }
                // 右边列添加下载打开图片
                for (var j = 1; j < data.length;) {
                    var filename = data[j];
                    var dindex = filename.indexOf(".");
                    var ext = filename.substr(dindex + 1);
                    var imgsrc = "img/ic_picture.png";
                    var filesize = data[j + 1];
                    var fileid = data[j + 2];
                    j += 3;
                    switch (ext) {
                        case "jpg":
                            imgsrc = "img/ic_jpg.png";
                            break;
                        case "doc":
                            imgsrc = "img/ic_doc.png";
                            break;
                        case "gif":
                            imgsrc = "img/ic_gif.png";
                            break;
                        case "pdf":
                            imgsrc = "img/ic_pdf.png";
                            break;
                        case "ppt":
                            imgsrc = "img/ic_ppt.png";
                            break;
                        case "rar":
                            imgsrc = "img/ic_rar.png";
                            break;
                    }
                    newcolright
                        .append('<div class="row" style="margin-top:15px;">'
                        + '<div class="col-xs-1 text-right"><img style="margin-top:5px;" src="' + imgsrc + '"></div>'
                        + '<div class="col-xs-10 text-left" style="padding-left:0px;">'
                        + '<div style="font-size:14px;color:#333333;height:25px;line-height:25px;"> '
                        + filename
                        + '('
                        + filesize
                        + 'kb)'
                        + '</div>'
                        + '<div>'
                        + '	<a class="btn btn-link nomargin nopadding" href="appurtenance/downloadchdfj?loginid='
                        + $("#userid").val()
                        + '&id='
                        + fileid
                        + '" style="font-size:12px;color:#337ab7;">下载</a><a class="btn btn-link nomargin nopadding" style="margin-left:5px;font-size:12px;color:#337ab7;" onclick="viewattachment('
                        + fileid
                        + ',\''
                        + ids.join(',')
                        + '\')">打开</a>' + '</div></div></div>');
                }

                $('#detailinfodiv').append(newrow);
                continue;
            }
        }

        if (data.length == 4) {
            var name = data[0];
            var val = data[1];
            var name2 = data[2];
            var val2 = data[3];
            newrow = $('<div class="row borderbottom"></div>');

            newcol = $('<div class="col-xs-3 text-right infodetailkey">' + name
                + '</div>');
            newrow.append(newcol);
            newcol = $('<div class="col-xs-3 text-left infodetailval borderleft" data-toggle="tooltip" data-placement="top" title="'
                + val + '">' + val + '</div>');
            newrow.append(newcol);

            newcol = $('<div class="col-xs-3 text-right infodetailkey borderleft">'
                + name2 + '</div>');
            newrow.append(newcol);
            newcol = $('<div class="col-xs-3 text-left infodetailval borderleft" data-toggle="tooltip" data-placement="top" title="'
                + val2 + '">' + val2 + '</div>');
            newrow.append(newcol);

            $('#detailinfodiv').append(newrow);
        }
        if (data.length == 2) {
            var name = data[0];
            var val = data[1];
            newrow = $('<div class="row infodetailrow borderbottom"></div>');

            newcol = $('<div class="col-xs-3 text-right infodetailkey">' + name
                + '</div>');
            newrow.append(newcol);
            newcol = $('<div class="col-xs-9 text-left infodetailval borderleft" data-toggle="tooltip" data-placement="top" title="'
                + val + '">' + val + '</div>');
            newrow.append(newcol);

            $('#detailinfodiv').append(newrow);
        } else if (data.length == 1) {
            newrow = $('<div class="row borderbottom"></div>');
            newcol = $('<div class="col-xs-12 infoline">' + data[0] + '</div>');
            newrow.append(newcol);

            $('#detailinfodiv').append(newrow);
        }
    }
}
