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
    loadxzcf();
}

function loadxzcf() {
    var dept = null, contenttype = null, content = null;
    var data = {
        'loginid': $("#userid").val()
    };
    //dept = $("#searchdept").attr("selitem");
    contenttype = $("#selsearchtype").attr("searchtype");
    content = $("#inputcontent").val();
    //data.dept = dept == "" || dept == null ? 0 : dept;
    data.contenttype = contenttype == "" || contenttype == null ? 0 : contenttype;
    if (content != null && content.length != 0)
        data.content = content;
    $("#xzcftable").adddatatable(
        {
            url: 'channelmanage/queryadminpenalty',
            data: data,
            datafn: function (data) {
                var records = getResultRecords(data);
                var ret = {};
                var list = new Array();
                if (records != null) {
                    for (var i in records.data) {
                        var dt = {};
                        dt.id = records.data[i].id;
                        dt.slh = records.data[i].slh;
                        dt.zwcm = records.data[i].zwcm;
                        dt.ay = records.data[i].ay;
                        dt.slsj = records.data[i].slsj;
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
                mDataProp: 'slh'
            }, {
                mDataProp: 'zwcm'
            }, {
                mDataProp: 'ay'
            }, {
                mDataProp: 'slsj'
            }, {
                mDataProp: null
            }],
            columndefs: [{
                targets: 5,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="viewxzcf(\''
                            + row.id
                            + '\')">查看</a>'
                        ;
                    return ret;
                }
            }],
            fncreatedrow: function (nRow, aData, iDataIndex) {
                var start = parseInt($('#xzcftable').attr('start'));
                $("td:eq(0)", nRow).html(
                    (start + iDataIndex))
            }
        });
}

function viewxzcf(id) {
    $.ajax({
        url: 'channelmanage/queryadminpenaltybyid',
        type: 'post',
        data: {
            'id': id
        },
        success: function (data) {
            $("#modalviewbody").load('page/hangzheng/xzcf-view.jsp #container');
            $("#modalview").modal('show');
        }
    });
}