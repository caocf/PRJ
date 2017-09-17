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
    var sfgg = getUrlParam('sfgg');
    $("#gotoflag").val("1");
    if (xzqh == -1) {
        xzqh = 1;
    }
    $("#selxzqhdiv").addselxzqh(
        {
            id: "selxzqh",
            selectfn: function () {
                loadhdaoinfolist();
            },
            defaultval: xzqh
        }
    );
    if (sfgg != -1) {
        $("#selsfgg").val(sfgg);
    }
    sethdaoinfolist(xzqh, sfgg, "");
});

function resize() {
    // 通过屏幕高度设置相应的滚动区域高度
    var clientHeight = window.innerHeight;
    $("#divleft").css('height', (clientHeight - 53) + 'px');
    $("#lefthdaohduantree").css('height', ($("#divleft").height()) + 'px');
    $("#leftfswtree").css('height', ($("#divleft").height()) + 'px');
    $("#divright").css('height', ($("#divleft").height() - 20) + 'px');
}

function loadhdaoinfolist() {
    var flag = $("#gotoflag").val();
    if (flag != 1) {
        var xzqh = $("#selxzqh").attr("selitem");
        var sfgg = $("#selsfgg").val();
        var content = $("#hdname").val();
        sethdaoinfolist(xzqh, sfgg, content);
    }
    $("#gotoflag").val("0");
}

function sethdaoinfolist(xzqh, sfgg, content) {
    var page = $("#dt").attr('page');
    var data = {
        'xzqh': xzqh,
        'sfgg': sfgg,
        'content': content
    };
    $("#dt").adddatatable(
        {
            url: 'hangdao/zhcxhdaoinfo',
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
                        dt.id = records.data[i][0];
                        dt.xzqh = xzqh;
                        dt.hdbh = records.data[i][1];
                        dt.hdmc = records.data[i][2];
                        dt.hdlc = setDeciNull(records.data[i][3]);
                        dt.cflc = setDeciNull(records.data[i][4]);
                        dt.hdsl = setDeciNull(records.data[i][5]);
                        dt.onenum = setDeciNull(records.data[i][6]);
                        dt.onelc = setDeciNull(records.data[i][7]);
                        dt.twonum = setDeciNull(records.data[i][8]);
                        dt.twolc = setDeciNull(records.data[i][9]);
                        dt.threenum = setDeciNull(records.data[i][10]);
                        dt.threelc = setDeciNull(records.data[i][11]);
                        dt.fournum = setDeciNull(records.data[i][12]);
                        dt.fourlc = setDeciNull(records.data[i][13]);
                        dt.fivenum = setDeciNull(records.data[i][14]);
                        dt.fivelc = setDeciNull(records.data[i][15]);
                        dt.sixnum = setDeciNull(records.data[i][16]);
                        dt.sixlc = setDeciNull(records.data[i][17]);
                        dt.sevennum = setDeciNull(records.data[i][18]);
                        dt.sevemlc = setDeciNull(records.data[i][19]);
                        dt.plusnum = setDeciNull(records.data[i][20]);
                        dt.pluslc = setDeciNull(records.data[i][21]);
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
                mDataProp: 'hdlc'
            }, {
                mDataProp: 'cflc'
            }, {
                mDataProp: 'hdsl'
            }, {
                mDataProp: 'onenum'
            }, {
                mDataProp: 'onelc'
            }, {
                mDataProp: 'twonum'
            }, {
                mDataProp: 'twolc'
            }, {
                mDataProp: 'threenum'
            }, {
                mDataProp: 'threelc'
            }, {
                mDataProp: 'fournum'
            }, {
                mDataProp: 'fourlc'
            }, {
                mDataProp: 'fivenum'
            }, {
                mDataProp: 'fivelc'
            }, {
                mDataProp: 'sixnum'
            }, {
                mDataProp: 'sixlc'
            }, {
                mDataProp: 'sevennum'
            }, {
                mDataProp: 'sevemlc'
            }, {
                mDataProp: 'plusnum'
            }, {
                mDataProp: 'pluslc'
            }, {
                mDataProp: null
            }],
            columndefs: [{
                targets: 4,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="gotohduaninfolist('
                        + row.xzqh + ',' + row.id + ',-1'
                        + ')">' + row.hdsl + '</a>';
                    return ret;
                }
            }, {
                targets: 5,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="gotohduaninfolist('
                        + row.xzqh + ',' + row.id + ',92'
                        + ')">' + row.onenum + '</a>';
                    return ret;
                }
            }, {
                targets: 7,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="gotohduaninfolist('
                        + row.xzqh + ',' + row.id + ',93'
                        + ')">' + row.twonum + '</a>';
                    return ret;
                }
            }, {
                targets: 9,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="gotohduaninfolist('
                        + row.xzqh + ',' + row.id + ',94'
                        + ')">' + row.threenum + '</a>';
                    return ret;
                }
            },
                {
                    targets: 11,
                    render: function (data, type, row) {
                        var ret = '<a class="btn btn-link btnoper" onclick="gotohduaninfolist('
                            + row.xzqh + ',' + row.id + ',95'
                            + ')">' + row.fournum + '</a>';
                        return ret;
                    }
                }, {
                    targets: 13,
                    render: function (data, type, row) {
                        var ret = '<a class="btn btn-link btnoper" onclick="gotohduaninfolist('
                            + row.xzqh + ',' + row.id + ',96'
                            + ')">' + row.fivenum + '</a>';
                        return ret;
                    }
                }, {
                    targets: 15,
                    render: function (data, type, row) {
                        var ret = '<a class="btn btn-link btnoper" onclick="gotohduaninfolist('
                            + row.xzqh + ',' + row.id + ',97'
                            + ')">' + row.sixnum + '</a>';
                        return ret;
                    }
                }, {
                    targets: 17,
                    render: function (data, type, row) {
                        var ret = '<a class="btn btn-link btnoper" onclick="gotohduaninfolist('
                            + row.xzqh + ',' + row.id + ',98'
                            + ')">' + row.sevennum + '</a>';
                        return ret;
                    }
                }, {
                    targets: 19,
                    render: function (data, type, row) {
                        var ret = '<a class="btn btn-link btnoper" onclick="gotohduaninfolist('
                            + row.xzqh + ',' + row.id + ',99'
                            + ')">' + row.plusnum + '</a>';
                        return ret;
                    }
                }, {
                    targets: 21,
                    render: function (data, type, row) {
                        var ret = '<a class="btn btn-link btnoper" onclick="viewinfo('
                            + row.id + "," + xzqh
                            + ')">详情</a>';
                        return ret;
                    }
                }]
        }
    );
}

function gotohduaninfolist(xzqh, sshdid, hddj) {
    window.location.href = $('#basePath').val() + '/page/hdaoinfomap/hduaninfolist.jsp?sshdid=' + sshdid + '&xzqh=' + xzqh + '&hddj=' + hddj;
}

function gotohduan() {
    window.location.href = $('#basePath').val() + '/page/hdaoinfomap/hduanlist.jsp';
}

function exporttable() {
    var xzqh = $("#selxzqh").attr("selitem");
    window.location.href = $("#basePath").val() + "hangdao/exporthdao?xzqh=" + xzqh;
}

function cleardetail() {
    $('#detailinfodiv').empty();
    resize();
}

function viewinfo(id, xzqh) {
    cleardetail();
    $('#detailinfodiv').empty();
    ajax('hangdao/queryhangdaoinfo', {
        'xzqh': xzqh,
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