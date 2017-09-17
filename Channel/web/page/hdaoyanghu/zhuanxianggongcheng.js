$(document).ready(function () {
    comminit();
    initui();
});

function initui() {
    //$('#btnSearch').click(loadzxgclist);
    $('#btnaddproject').click(addzxgc);
    $('#btneditproject').click(editzxgc);
    $("#cbselall").bind('click', function () {
        selallgc();
    });
    $('#modalnewproject').bind('show.bs.modal', function () {
        $("#addgldw").focus();
        $('#addproject').validateFormReset();
    });

    $('#btndelzxgc').click(function () {
        var delids = $("#modaldelzxgc").attr('delids').split(',');
        delzxgc(delids);
    });
    loadzxgclist();

}

function selallgc() {
    if ($("#cbselall").prop('checked') == true) {
        for (var i = start; i < start + 30; i++) {
            $("#cb" + i).prop('checked', true);
        }
    } else {
        for (var i = start; i < start + 30; i++) {
            $("#cb" + i).prop('checked', false);
        }
    }
}

function loadzxgclist() {
    var gldw = $('#selgldw').attr('selitem');
    var starttime = $('#seldaystart').attr('realvalue');
    var endtime = $('#seldayend').attr('realvalue');
    var content = $('#searchContent').val();

    var page = $("#tableRecords").attr('page');

    //查询时是否递归查询
    var sfdg = 0;
    if (hasPerm('VIEW_SHENZXGC') || hasPerm('MAN_SHIZXGC') || hasPerm('VIEW_SHIZXGC')) {
        sfdg = 1;
    }

    $('#tableRecords')
        .adddatatable(
        {
            url: "maintenance/queryzxgcs",
            data: {
                'loginid': $("#userid").val(),
                'gldw': gldw == null ? '' : gldw,
                'sfdg': sfdg,
                'starttime': starttime,
                'endtime': endtime,
                'content': content
            },
            rows: 10,
            page: page,
            datafn: function (data) {
                var records = getResultRecords(data);
                var ret = {};
                var list = new Array();
                if (records != null) {
                    for (var dt in records.data) {
                        var item = records.data[dt][0];
                        item.statusname = records.data[dt][1];
                        item.gldwname = records.data[dt][2];
                        list.push(item);
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
                mDataProp: 'gcmc'
            }, {
                mDataProp: 'gldwname'
            }, {
                mDataProp: 'statusname'
            }, {
                mDataProp: 'tz'
            }, {
                mDataProp: 'updatetime'
            }, {
                mDataProp: null
            }],
            columndefs: [{
                targets: 6,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" data-toggle="tooltip" data-placement="top" title="查看工程信息" onclick="viewzxgc(\''
                        + row.zxgcid
                        + '\')">查看</a>';
                    if (hasPerm('MAN_DPTZXGC') || hasPerm('MAN_SHIZXGC')) {
                        ret += '<a class="btn btn-link btnoper" data-toggle="tooltip" data-placement="top" title="修改工程基本信息" onclick="editload('
                            + row.zxgcid
                            + ')">修改</a>'
                            + '<a class="btn btn-link btnoper" data-toggle="tooltip" data-placement="top" title="删除当前工程" onclick="delsingle('
                            + row.zxgcid
                            + ', \''
                            + row.gcmc
                            + '\')">删除</a>';
                    }
                    return ret;
                }
            }],
            fncreatedrow: function (nRow, aData, iDataIndex) {
                var start = parseInt($('#tableRecords').attr(
                    'start'));
                $("td:eq(0)", nRow).html(
                    '<input type="checkbox" name="usercheck" gcid='
                    + aData.zxgcid + ' gcmc="'
                    + aData.gcmc + '" id="cb'
                    + (start + iDataIndex)
                    + '" value="'
                    + (start + iDataIndex) + '">&nbsp;'
                    + (start + iDataIndex))
            }
        });
}

function viewzxgc(id) {
    window.location.href = $("#basePath").val()
        + "page/hdaoyanghu/zhuanxianggongcheng_detail.jsp?zxgcid=" + id
        + "&defaultpage=" + $("#tableRecords").attr('page');
}

function addzxgc() {
    $("#addproject").validateForm(function () {
        $("#addproject").autoajax('maintenance/addzxgc', {
            loginid: $("#userid").val()
        }, function (result) {
            if (ifResultOK(result)) {
                $("#modalnewproject").modal('hide');
                loadzxgclist();
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}

function delsingle(id, gcmc) {
    var delids = new Array();
    var delnames = new Array();

    delids.push(id);
    delnames.push(gcmc);

    if (delids.length <= 0) {
        alert('请先选择要删除的专项工程！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldelzxgc").attr('delids', delids.join(","));
    $("#modaldelzxgc").modal('show');
}

function delmulti() {
    var delids = new Array();
    var delnames = new Array();
    $("[name='usercheck']").each(function () {
        if ($(this).prop("checked") == true) {
            var id = $(this).attr('gcid');
            delids.push(id);

            var gcmc = $(this).attr('gcmc');
            delnames.push(gcmc);
        }
    });
    if (delids.length <= 0) {
        alert('请先选择要删除的专项工程！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldelzxgc").attr('delids', delids.join(","));
    $("#modaldelzxgc").modal('show');
}

function delzxgc(ids) {
    var data = {
        loginid: $("#userid").val()
    };
    for (var index in ids) {
        var id = ids[index];
        data["zxgcidlist[" + index + "]"] = id;
    }
    ajax('maintenance/delzxgc', data, function (result) {
        if (ifResultOK(result)) {
            $("#modaldelzxgc").modal('hide');
            loadzxgclist();
        } else {
            alert(getResultDesc(result));
        }
    });
}

function editload(id) {
    ajax('maintenance/queryzxgcbyid', {
        loginid: $("#userid").val(),
        zxgcid: id
    }, function (result) {
        if (ifResultOK(result)) {
            $('#editproject').validateFormReset();
            if (result.records != null) {
                var gc = result.records.data[0][0];
                if (gc != null) {
                    var gcid = gc.zxgcid;
                    var gcmc = gc.gcmc;
                    var starttime = getTimeFmt(getTimeFromStr(gc.starttime,
                        'yyyy-MM-dd HH:mm:ss'), 'yyyy-MM-dd');
                    var endtime = getTimeFmt(getTimeFromStr(gc.endtime,
                        'yyyy-MM-dd HH:mm:ss'), 'yyyy-MM-dd');
                    var status = gc.status;
                    var tz = gc.tz;
                    var jsdw = gc.jsdw;
                    var gldw = gc.gldw;
                    var jldw = gc.jldw;
                    var sjdw = gc.sjdw;
                    var sgdw = gc.sgdw;
                    var bz = gc.bz;
                    $("#diveditgcid").empty();
                    $("#diveditgcid").addhidden({
                        defaultval: gcid,
                        autoajax: {
                            name: 'zxgc.zxgcid'
                        }
                    });
                    $("#diveditxmgk").addtextarea({
                        id: 'editbz',
                        defaultval: bz
                    });
                    $("#diveditsgdw").addinputtext({
                        id: 'editsgdw',
                        defaultval: sgdw
                    });
                    $("#diveditsjdw").addinputtext({
                        id: 'editsjdw',
                        defaultval: sjdw
                    });
                    $("#diveditjldw").addinputtext({
                        id: 'editjldw',
                        defaultval: jldw
                    });
                    $("#diveditgldw").addseldpt({
                        id: 'editgldw',
                        defaultval: gldw
                    });
                    $("#diveditjsdw").addinputtext({
                        id: 'editjsdw',
                        defaultval: jsdw
                    });
                    $("#divedittzze").addinputtext({
                        id: 'edittz',
                        defaultval: tz
                    });
                    $("#diveditgcmc").addinputtext({
                        id: 'editgcmc',
                        defaultval: gcmc
                    });
                    $("#diveditstartday").addday({
                        id: 'editstarttime',
                        defaultval: starttime
                    });
                    $("#diveditendday").addday({
                        id: 'editendtime',
                        defaultval: endtime
                    });

                    $("#diveditselprostatus").addselect({
                        id: 'editstatus',
                        defaultval: status
                    });
                }

                $('#modaleditproject').modal('show');
            }
        } else {
            alert(getResultDesc(result));
        }
    });
}

function editzxgc() {
    $("#editproject").validateForm(function () {
        $("#editproject").autoajax('maintenance/updatezxgc', {
            loginid: $("#userid").val()
        }, function (result) {
            if (ifResultOK(result)) {
                $("#modaleditproject").modal('hide');
                loadzxgclist();
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}