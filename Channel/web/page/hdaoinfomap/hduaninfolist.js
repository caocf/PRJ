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
    var xzqh = getUrlParam('xzqh');
    var sshdid = getUrlParam('sshdid');
    var hddj = getUrlParam('hddj')
    initXzqh(xzqh, sshdid);
    /*    initHdaolist(sshdid);*/
    initHddjlist(hddj);
    $("#gotoflag").val("1");
    sethduaninfolist(xzqh, sshdid, hddj, "");

});

function resize() {
    // 通过屏幕高度设置相应的滚动区域高度
    var clientHeight = window.innerHeight;
    $("#divleft").css('height', (clientHeight - 53) + 'px');
    $("#lefthdaohduantree").css('height', ($("#divleft").height()) + 'px');
    $("#leftfswtree").css('height', ($("#divleft").height()) + 'px');
    $("#divright").css('height', ($("#divleft").height() - 20) + 'px');
}
function initXzqh(xzqh, sshdid) {
    if (xzqh == -1) {
        xzqh = 1;
    }
    if (sshdid == null || sshdid == "") {
        sshdid = -1;
    }
    $("#selxzqhdiv").addselxzqh(
        {
            id: "selxzqh",
            defaultval: xzqh,
            selectfn: initHdaolist(sshdid)
        }
    );
}
function initHdaolist(sshdid) {
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
                    if (sshdid == hdao.id) {
                        $("#selhdaolist").append('<option selected value="' + hdao.id + '">' + hdao.hdmc + '</option>');
                    } else {
                        $("#selhdaolist").append('<option value="' + hdao.id + '">' + hdao.hdmc + '</option>');
                    }
                }
            }
        }
    });
}

function initHddjlist(hddj) {
    if (hddj == null) {
        hddj = -1;
    }
    ajax('dic/querydicattr', {
        loginid: $("#userid").val(),
        name: 'hddj'
    }, function (data) {
        $("#selhddj").append(
            '<option value="-1">航段等级</option>');
        if (ifResultOK(data)) {
            var records = getResultRecords(data);
            if (records) {
                for (var i = 0; i < records.data.length; i++) {
                    var dict = records.data[i];
                    if (hddj == dict.id) {
                        $("#selhddj").append('<option selected value="' + dict.id + '">' + dict.attrdesc + '</option>');
                    } else {
                        $("#selhddj").append('<option value="' + dict.id + '">' + dict.attrdesc + '</option>');
                    }
                }
            }
        }
    });
}

function loadhduaninfolist() {
    $("#gotoflag").val("0");
    var xzqh = $("#selxzqh").attr("selitem");
    var sshdid = $("#selhdaolist").val();
    var hddj = $("#selhddj").val();
    var content = $("#hdname").val();
    sethduaninfolist(xzqh, sshdid, hddj, content);
}

function sethduaninfolist(xzqh, sshdid, hddj, content) {
    var page = $("#dt").attr('page');
    var data = {
        'xzqh': xzqh,
        'sshdid': sshdid,
        'hddj': hddj,
        'content': content
    };
    $("#dt").adddatatable(
        {
            url: 'hangduan/zhcxhduaninfo',
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
                        var obj = records.data[i];
                        dt.id = obj[0];
                        dt.hdbh = obj[1];
                        dt.hdmc = obj[2];
                        dt.hdao = obj[3];
                        dt.hddj = obj[4];
                        dt.xzqh = obj[5];
                        dt.hdlc = obj[6] <= 0 ? "" : obj[6];
                        dt.hb = obj[7] == 0 ? "" : obj[7];
                        dt.ql = obj[8] == 0 ? "" : obj[8];
                        dt.lx = obj[9] == 0 ? "" : obj[9];
                        dt.mt = obj[10] == 0 ? "" : obj[10];
                        dt.sn = obj[11] == 0 ? "" : obj[11];
                        dt.gcd = obj[12] == 0 ? "" : obj[12];
                        list.push(dt);
                    }
                }
                ret.data = list;
                ret.total = records.total;
                ret.secho = data.map.sEcho;
                return ret;
            },
            columns: [{
                mDataProp: 'hdbh'
            }, {
                mDataProp: 'hdmc'
            }, {
                mDataProp: 'hdao'
            }, {
                mDataProp: 'hddj'
            }, {
                mDataProp: 'xzqh'
            }, {
                mDataProp: 'hdlc'
            }, {
                mDataProp: 'hb'
            }, {
                mDataProp: 'ql'
            }, {
                mDataProp: 'lx'
            }, {
                mDataProp: 'mt'
            }, {
                mDataProp: 'sn'
            }, {
                mDataProp: 'gcd'
            }, {
                mDataProp: null
            }],
            columndefs: [{
                targets: 6,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="gotoappinfolist(9,'
                        + xzqh + ',' + row.id
                        + ')">' + row.hb + '</a>';
                    return ret;
                }
            }, {
                targets: 7,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="gotoappinfolist(10,'
                        + xzqh + ',' + row.id
                        + ')">' + row.ql + '</a>';
                    return ret;
                }
            }, {
                targets: 8,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="gotoappinfolist(12,'
                        + xzqh + ',' + row.id
                        + ')">' + row.lx + '</a>';
                    return ret;
                }
            }, {
                targets: 9,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="gotoappinfolist(15,'
                        + xzqh + ',' + row.id
                        + ')">' + row.mt + '</a>';
                    return ret;
                }
            }, {
                targets: 10,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="gotoappinfolist(23,'
                        + xzqh + ',' + row.id
                        + ')">' + row.sn + '</a>';
                    return ret;
                }
            }, {
                targets: 11,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="gotoappinfolist(29,'
                        + xzqh + ',' + row.id
                        + ')">' + row.gcd + '</a>';
                    return ret;
                }
            },
                {
                    targets: 12,
                    render: function (data, type, row) {
                        var ret = '<a class="btn btn-link btnoper" onclick="viewinfo(\''
                            + row.id
                            + '\')">详情</a>';
                        return ret;
                    }
                }]
        }
    );
}

function gotohduan() {
    window.location.href = $('#basePath').val() + '/page/hdaoinfomap/hduanlist.jsp';
}

function gotoappinfolist(fswlx, xzqh, sshdid) {
    window.location.href = $('#basePath').val() + '/page/hdaoinfomap/appinfolist.jsp?fswlx=' + fswlx + '&xzqh=' + xzqh + '&sshdid=' + sshdid;
}

function exporttable() {
    var xzqh = $("#selxzqh").attr("selitem");
    window.location.href = $("#basePath").val() + "hangdao/exporthduan?xzqh=" + xzqh;
}

function cleardetail() {
    $('#detailinfodiv').empty();
    resize();
}

function viewinfo(id) {
    cleardetail();
    $('#detailinfodiv').empty();
    ajax('hangduan/queryhangduaninfo', {
        'id': id
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