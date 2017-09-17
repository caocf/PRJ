/**
 * Created by 25019 on 2015/10/28.
 */
$(document).ready(function () {
    var username = $("#username").val();
    if (username == null || username == "" || username == "null") {
        window.location.href = $("#basePath").val() + "page/login/login.jsp";
    }
    initui();
    // 标签页切换，新标签显示后回调函数，此函数中加载相关数据。
    $('#tabxzxk').bind('click', function () {
        window.location.href = $("#basePath").val() + "page/hangzheng/xzxk.jsp";
    });
    $('#tabxzcf').bind('click', function () {
        window.location.href = $("#basePath").val() + "page/hangzheng/xzcf.jsp";
    });
    $('#tabthlz').bind('click', function () {
        window.location.href = $("#basePath").val() + "page/hangzheng/thlz.jsp";
    });
    $('#tabczpc').bind('click', function () {
        window.location.href = $("#basePath").val() + "page/hangzheng/czpc.jsp";
    });
});

function initui() {
    loadxzxk();
}


function loadxzxk() {
    var xmlx = null, jzwxz = null, starttime = null, endtime = null, contenttype = null, content = null;
    var data = {
        'loginid': $("#userid").val()
    };
    xmlx = $("#selxmlx").val();
    jzwxz = $("#seljzwxz").val();
    contenttype = $("#selsearchtype").attr("searchtype");
    starttime = $("#seldaystart").attr('realvalue');
    endtime = $("#seldayend").attr('realvalue');
    content = $("#inputcontent").val();
    data.xmlx = xmlx == "" || xmlx == null ? 0 : xmlx;
    data.jzwxz = jzwxz == "" || jzwxz == null ? 0 : jzwxz;
    data.contenttype = contenttype == "" || contenttype == null ? 0 : contenttype;
    if (starttime != null && starttime.length != 0)
        data.starttime = starttime;
    if (endtime != null && endtime.length != 0)
        data.endtime = endtime;
    if (content != null && content.length != 0)
        data.content = content;
    $("#xzxktable").adddatatable(
        {
            url: 'channelmanage/querychannelmanage',
            data: data,
            datafn: function (data) {
                var records = getResultRecords(data);
                var ret = {};
                var list = new Array();
                if (records != null) {
                    for (var i in records.data) {
                        var dt = {};
                        dt.id = records.data[i].id;
                        dt.xkbh = records.data[i].xkbh;
                        dt.xmmc = records.data[i].xmmc;
                        dt.sqdw = records.data[i].sqdw;
                        var strxmlx = records.data[i].xmlx;
                        if (strxmlx == 1) {
                            strxmlx = "桥梁";
                        }
                        if (strxmlx == 2) {
                            strxmlx = "架空缆线";
                        }
                        if (strxmlx == 3) {
                            strxmlx = "水下管线";
                        }
                        if (strxmlx == 4) {
                            strxmlx = "隧道";
                        }
                        if (strxmlx == 5) {
                            strxmlx = "取排水";
                        }
                        if (strxmlx == 6) {
                            strxmlx = "闸坝";
                        }
                        var strjzwxz = records.data[i].jzwxz;
                        if (strjzwxz == 1) {
                            strjzwxz = "新建";
                        }
                        if (strjzwxz == 2) {
                            strjzwxz = "改扩建";
                        }
                        dt.xmlx = strxmlx;
                        dt.jzwxz = strjzwxz;
                        dt.xkrq = records.data[i].xkrq;
                        list.push(dt);
                    }
                }
                ret.data = list;
                ret.total = records.total;
                ret.secho = data.map.sEcho;
                return ret;
            },
            columns: [{
                mDataProp: null
            }, {
                mDataProp: 'xkbh'
            }, {
                mDataProp: 'xmmc'
            }, {
                mDataProp: 'sqdw'
            }, {
                mDataProp: 'xmlx'
            }, {
                mDataProp: 'jzwxz'
            }, {
                mDataProp: 'xkrq'
            }, {
                mDataProp: null
            }],
            columndefs: [{
                targets: 7,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="viewxzxk(\''
                            + row.id
                            + '\')">查看</a>'
                        ;
                    return ret;
                }
            }],
            fncreatedrow: function (nRow, aData, iDataIndex) {
                var start = parseInt($('#xzxktable').attr('start'));
                $("td:eq(0)", nRow).html(
                    (start + iDataIndex))
            }
        });
}

function viewxzxk(id) {
    $.ajax({
        url: 'channelmanage/querychannelmanagebyid',
        type: 'post',
        data: {
            'loginid': $("#userid").val(),
            'id': id
        },
        success: function (data) {
            $("#modalviewbody").load('page/hangzheng/xzxk-view.jsp #container');
            $("#modalview").modal('show');
        }
    });
}