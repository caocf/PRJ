$(document).ready(function () {
    comminit();
    initui();
});

function initui() {
    loadbranch();
    $("#cbselall").bind('click', function () {
        selallgc();
    });
    $("#modaladdtz").bind('shown.bs.modal', function () {
        $("#formaddtz").validateFormReset();
    });
    $('#btndeltz').click(function () {
        var delids = $("#modaldeltz").attr('delids').split(',');
        deltz(delids);
    });
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
function loadbranch() {
    var starttime = null, endtime = null, dw = null;

    var sfdg = 0;
    if (hasPerm('VIEW_SHENZXYH') || hasPerm('VIEW_SHIZXYH') || hasPerm('MAN_SHIZXYH')) {
        sfdg = 1;
    } else {
        sfdg = 0;
    }

    var data = {
        'loginid': $("#userid").val(),
        'sfdg': sfdg
    };
    starttime = $("#selstarttime").attr('realvalue');
    endtime = $("#selendtime").attr('realvalue');
    dw = $("#seldw").attr('selitem');
    if (starttime != null && starttime.length != 0)
        data.starttime = starttime;
    if (endtime != null && endtime.length != 0)
        data.endtime = endtime;
    if (dw != null && dw.length != 0)
        data.dw = dw;
    $("#tablebranch")
        .adddatatable(
        {
            url: 'maintenance/querybranches',
            data: data,
            datafn: function (data) {
                var records = getResultRecords(data);
                var ret = {};
                var list = new Array();
                if (records != null) {
                    for (var i in records.data) {
                        var dt = {};
                        dt.id = records.data[i][0].id;
                        dt.dw = records.data[i][2].name;
                        dt.clsl = records.data[i][0].clsl;
                        dt.clje = records.data[i][0].clje;
                        dt.ahsjsl = records.data[i][0].ahsjsl;
                        dt.ahsjje = records.data[i][0].ahsjje;
                        dt.zzjzwxfsl = records.data[i][0].zzjzwxfsl;
                        dt.zzjzwxfje = records.data[i][0].zzjzwxfje;
                        dt.wxglmtsl = records.data[i][0].wxglmtsl;
                        dt.wxglmtje = records.data[i][0].wxglmtje;
                        dt.hbwhsl = (records.data[i][0].hbwhzsl == null ? "0" : records.data[i][0].hbwhzsl) + "/" + (records.data[i][0].hbwhzcsl == null ? "0" : records.data[i][0].hbwhzcsl);
                        if (dt.hbwhsl == '/')
                            dt.hbwhsl = '';
                        dt.hbwhje = records.data[i][0].hbwhje;
                        dt.dlccsl = (records.data[i][0].dlccssl == null ? "0" : records.data[i][0].dlccssl) + "/" + (records.data[i][0].dlccdwsl == null ? "0" : records.data[i][0].dlccdwsl);
                        if (dt.dlccsl == '/')
                            dt.dlccsl = '';
                        dt.dlccje = records.data[i][0].dlccje;
                        dt.qtqzsl = (records.data[i][0].qtqzcsl == null ? "0" : records.data[i][0].qtqzcsl) + "/" + (records.data[i][0].qtqzdsl == null ? "0" : records.data[i][0].qtqzdsl);
                        if (dt.qtqzsl == '/')
                            dt.qtqzsl = '';
                        dt.qtqzje = records.data[i][0].qtqzje;
                        dt.qtwxgcsl = records.data[i][0].qtwxgcsl;
                        dt.qtwxgcje = records.data[i][0].qtwxgcje;
                        dt.month = getTimeFmt(getTimeFromStr(records.data[i][0].starttime, 'yyyy-MM-dd'), 'yyyy年MM月');
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
                mDataProp: 'dw'
            }, {
                mDataProp: 'clsl'
            }, {
                mDataProp: 'clje'
            }, {
                mDataProp: 'ahsjsl'
            }, {
                mDataProp: 'ahsjje'
            }, {
                mDataProp: 'zzjzwxfsl'
            }, {
                mDataProp: 'zzjzwxfje'
            }, {
                mDataProp: 'wxglmtsl'
            }, {
                mDataProp: 'wxglmtje'
            }, {
                mDataProp: 'hbwhsl'
            }, {
                mDataProp: 'hbwhje'
            }, {
                mDataProp: 'dlccsl'
            }, {
                mDataProp: 'dlccje'
            }, {
                mDataProp: 'qtqzsl'
            }, {
                mDataProp: 'qtqzje'
            }, {
                mDataProp: 'qtwxgcsl'
            }, {
                mDataProp: 'qtwxgcje'
            }, {
                mDataProp: 'month'
            },
                {
                    mDataProp: null
                }],
            columndefs: [{
                targets: 19,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="viewtz(\''
                        + row.id
                        + '\')">查看</a>';
                    if (hasPerm('MAN_SHIZXYH') || hasPerm('MAN_DPTZXYH')) {
                        ret += '<a class="btn btn-link btnoper" onclick="updateload('
                            + row.id
                            + ')">修改</a>'
                            + '<a class="btn btn-link btnoper" onclick="delsingle('
                            + row.id
                            + ',\''
                            + row.dw
                            + '\')">删除</a>';
                    }
                    return ret;
                }
            }],
            fncreatedrow: function (nRow, aData, iDataIndex) {
                var start = parseInt($('#tablebranch')
                    .attr('start'));
                $("td:eq(0)", nRow).html(
                    '<input type="checkbox" name="branchcheckbox" tzid='
                    + aData.id + ' dw="' + aData.dw
                    + '" id="cb' + (start + iDataIndex)
                    + '" value="'
                    + (start + iDataIndex) + '">&nbsp;'
                    + (start + iDataIndex))
            }
        },
        {
            'sScrollX': "160%"
        });
}

function addtz() {
    $("#formaddtz").validateForm(function () {
        $("#modaladdtz").autoajax('maintenance/addbranch', {
            loginid: $("#userid").val()
        }, function (result, data) {
            if (ifResultOK(result)) {
                $("#modaladdtz").modal('hide');
                loadbranch();
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}

function viewtz(id) {
    ajax(
        'maintenance/querybranchbyid',
        {
            loginid: $("#userid").val(),
            id: id
        },
        function (result) {
            if (ifResultOK(result)) {
                $("#modalviewtz").modal('show');
                var records = getResultRecords(result);
                var zxjbxx = records.data[0][0];
                var dpt = records.data[0][2];
                $("#viewtzdw").text(dpt.name);
                $("#viewtzselrq").text(getTimeFmt(getTimeFromStr(zxjbxx.starttime, 'yyyy-MM-dd'), 'yyyy年MM月'));
                $("#iview11").text(zxjbxx.clsl == null ? "0" : zxjbxx.clsl);
                $("#iview12").text(zxjbxx.clje == null ? "0" : zxjbxx.clje);
                $("#iview13").text(zxjbxx.clbz == null ? "" : zxjbxx.clbz);
                $("#iview21").text(
                    zxjbxx.ahsjsl == null ? "0" : zxjbxx.ahsjsl);
                $("#iview22").text(
                    zxjbxx.ahsjje == null ? "0" : zxjbxx.ahsjje);
                $("#iview23").text(
                    zxjbxx.ahsjbz == null ? "" : zxjbxx.ahsjbz);
                $("#iview31").text(
                    zxjbxx.zzjzwxfsl == null ? "0" : zxjbxx.zzjzwxfsl);
                $("#iview32").text(
                    zxjbxx.zzjzwxfje == null ? "0" : zxjbxx.zzjzwxfje);
                $("#iview33").text(
                    zxjbxx.zzjzwxfbz == null ? "" : zxjbxx.zzjzwxfbz);
                $("#iview41").text(
                    zxjbxx.wxglmtsl == null ? "0" : zxjbxx.wxglmtsl);
                $("#iview42").text(
                    zxjbxx.wxglmtje == null ? "0" : zxjbxx.wxglmtje);
                $("#iview43").text(zxjbxx.wxglmtbz == null ? "" : zxjbxx.wxglmtbz);
                var hbwhzsl = zxjbxx.hbwhzsl == null ? "0" : zxjbxx.hbwhzsl;
                var hbwhzcsl = zxjbxx.hbwhzcsl == null ? "0" : zxjbxx.hbwhzcsl;
                $("#iview51").text(hbwhzsl + "/" + hbwhzcsl);
                $("#iview52").text(zxjbxx.hbwhje == null ? "0" : zxjbxx.hbwhje);
                $("#iview53").text(zxjbxx.hbwhbz == null ? "" : zxjbxx.hbwhbz);
                var dlccssl = zxjbxx.dlccssl == null ? "0" : zxjbxx.dlccssl;
                var dlccdwsl = zxjbxx.dlccdwsl == null ? "0" : zxjbxx.dlccdwsl;
                $("#iview61").text(dlccssl + "/" + dlccdwsl);
                $("#iview62").text(zxjbxx.dlccje == null ? "0" : zxjbxx.dlccje);
                $("#iview63").text(zxjbxx.dlccbz == null ? "" : zxjbxx.dlccbz);
                var qtqzcsl = zxjbxx.qtqzcsl == null ? "0" : zxjbxx.qtqzcsl;
                var qtqzdsl = zxjbxx.qtqzdsl == null ? "0" : zxjbxx.qtqzdsl;
                $("#iview71").text(qtqzcsl + "/" + qtqzdsl);
                $("#iview72").text(
                    zxjbxx.qtqzje == null ? "0" : zxjbxx.qtqzje);
                $("#iview73").text(
                    zxjbxx.qtqzbz == null ? "" : zxjbxx.qtqzbz);
                $("#iview81").text(
                    zxjbxx.qtwxgcsl == null ? "0" : zxjbxx.qtwxgcsl);
                $("#iview82").text(
                    zxjbxx.qtwxgcje == null ? "0" : zxjbxx.qtwxgcje);
                $("#iview83").text(
                    zxjbxx.qtwxgcbz == null ? "" : zxjbxx.qtwxgcbz);
            } else {
                alert(getResultDesc(result));
            }
        });
}
function delsingle(id, dw) {
    var delids = new Array();
    var delnames = new Array();

    delids.push(id);
    delnames.push(dw);

    if (delids.length <= 0) {
        alert('请先选择要删除的台账！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldeltz").attr('delids', delids.join(","));
    $("#modaldeltz").modal('show');
}
function deltz(ids) {
    var data = {
        loginid: $("#userid").val()
    };
    for (var index in ids) {
        var id = ids[index];
        data["zxjbxxidlist[" + index + "]"] = id;
    }
    ajax('maintenance/delbranch', data, function (result) {
        if (ifResultOK(result)) {
            $("#modaldeltz").modal('hide');
            loadbranch();
        } else {
            alert(getResultDesc(result));
        }
    });
}
function delmulti() {
    var delids = new Array();
    var delnames = new Array();
    $("[name='branchcheckbox']").each(function () {
        if ($(this).prop("checked") == true) {
            var id = $(this).attr('tzid');
            delids.push(id);
            var dw = $(this).attr('dw');
            delnames.push(dw);
        }
    });
    if (delids.length <= 0) {
        alert('请先选择要删除的台账！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldeltz").attr('delids', delids.join(","));
    $("#modaldeltz").modal('show');
}

function updateload(id) {
    ajax('maintenance/querybranchbyid', {
        loginid: $("#userid").val(),
        id: id
    }, function (result) {
        if (ifResultOK(result)) {
            $('#modalupdatetz').validateFormReset();
            if (result.records != null) {
                var zxjbxx = result.records.data[0][0];
                if (zxjbxx != null) {
                    var id = zxjbxx.id;
                    var dw = zxjbxx.dw;
                    var starttime = getTimeFmt(getTimeFromStr(zxjbxx.starttime,
                        'yyyy-MM-dd'), 'yyyy-MM');
                    $("#divupdatetzid").empty();
                    $("#divupdatetzid").addhidden({
                        defaultval: id,
                        autoajax: {
                            name: 'zxjbxx.id'
                        }
                    });
                    $("#divupdatetzdw").addseldpt({
                        id: 'updatetzdw',
                        defaultval: dw
                    });
                    $("#divupdatetzselrq").addmonth({
                        id: 'updatetzselrq',
                        defaultval: starttime
                    });

                    var clsl = zxjbxx.clsl;
                    var clje = zxjbxx.clje;
                    var clbz = zxjbxx.clbz;
                    var ahsjsl = zxjbxx.ahsjsl;
                    var ahsjje = zxjbxx.ahsjje;
                    var ahsjbz = zxjbxx.ahsjbz;
                    var zzjzwxfsl = zxjbxx.zzjzwxfsl;
                    var zzjzwxfje = zxjbxx.zzjzwxfje;
                    var zzjzwxfbz = zxjbxx.zzjzwxfbz;
                    var wxglmtsl = zxjbxx.wxglmtsl;
                    var wxglmtje = zxjbxx.wxglmtje;
                    var wxglmtbz = zxjbxx.wxglmtbz;
                    var hbwhzsl = zxjbxx.hbwhzsl;
                    var hbwhzcsl = zxjbxx.hbwhzcsl;
                    var hbwhje = zxjbxx.hbwhje;
                    var hbwhbz = zxjbxx.hbwhbz;
                    var dlccssl = zxjbxx.dlccssl;
                    var dlccdwsl = zxjbxx.dlccdwsl;
                    var dlccje = zxjbxx.dlccje;
                    var dlccbz = zxjbxx.dlccbz;
                    var qtqzcsl = zxjbxx.qtqzcsl;
                    var qtqzdsl = zxjbxx.qtqzdsl;
                    var qtqzje = zxjbxx.qtqzje;
                    var qtqzbz = zxjbxx.qtqzbz;
                    var qtwxgcsl = zxjbxx.qtwxgcsl;
                    var qtwxgcje = zxjbxx.qtwxgcje;
                    var qtwxgcbz = zxjbxx.qtwxgcbz;

                    $("#iupdate11").addinputtext({
                        id: 'inputupdate11',
                        defaultval: clsl
                    });
                    $("#iupdate12").addinputtext({
                        id: 'inputupdate12',
                        defaultval: clje
                    });

                    $("#iupdate13").addinputtext({
                        id: 'inputupdate13',
                        defaultval: clbz
                    });
                    $("#iupdate21").addinputtext({
                        id: 'inputupdate21',
                        defaultval: ahsjsl
                    });
                    $("#iupdate22").addinputtext({
                        id: 'inputupdate22',
                        defaultval: ahsjje
                    });
                    $("#iupdate23").addinputtext({
                        id: 'inputupdate23',
                        defaultval: ahsjbz
                    });
                    $("#iupdate31").addinputtext({
                        id: 'inputupdate31',
                        defaultval: zzjzwxfsl
                    });
                    $("#iupdate32").addinputtext({
                        id: 'inputupdate32',
                        defaultval: zzjzwxfje
                    });
                    $("#iupdate33").addinputtext({
                        id: 'inputupdate33',
                        defaultval: zzjzwxfbz
                    });
                    $("#iupdate41").addinputtext({
                        id: 'inputupdate41',
                        defaultval: wxglmtsl
                    });
                    $("#iupdate42").addinputtext({
                        id: 'inputupdate42',
                        defaultval: wxglmtje
                    });
                    $("#iupdate43").addinputtext({
                        id: 'inputupdate43',
                        defaultval: wxglmtbz
                    });
                    $("#iupdate51").addinputtext({
                        id: 'inputupdate51',
                        defaultval: hbwhzsl
                    });
                    $("#iupdate511").addinputtext({
                        id: 'inputupdate511',
                        defaultval: hbwhzcsl
                    });
                    $("#iupdate52").addinputtext({
                        id: 'inputupdate52',
                        defaultval: hbwhje
                    });
                    $("#iupdate53").addinputtext({
                        id: 'inputupdate53',
                        defaultval: hbwhbz
                    });
                    $("#iupdate61").addinputtext({
                        id: 'inputupdate61',
                        defaultval: dlccssl
                    });
                    $("#iupdate611").addinputtext({
                        id: 'inputupdate611',
                        defaultval: dlccdwsl
                    });
                    $("#iupdate62").addinputtext({
                        id: 'inputupdate62',
                        defaultval: dlccje
                    });
                    $("#iupdate63").addinputtext({
                        id: 'inputupdate63',
                        defaultval: dlccbz
                    });
                    $("#iupdate71").addinputtext({
                        id: 'inputupdate71',
                        defaultval: qtqzcsl
                    });
                    $("#iupdate711").addinputtext({
                        id: 'inputupdate711',
                        defaultval: qtqzdsl
                    });
                    $("#iupdate72").addinputtext({
                        id: 'inputupdate72',
                        defaultval: qtqzje
                    });
                    $("#iupdate73").addinputtext({
                        id: 'inputupdate73',
                        defaultval: qtqzbz
                    });
                    $("#iupdate81").addinputtext({
                        id: 'inputupdate81',
                        defaultval: qtwxgcsl
                    });
                    $("#iupdate82").addinputtext({
                        id: 'inputupdate82',
                        defaultval: qtwxgcje
                    });
                    $("#iupdate83").addinputtext({
                        id: 'inputupdate83',
                        defaultval: qtwxgcbz
                    });
                }
                $('#modalupdatetz').modal('show');
            }
        } else {
            alert(getResultDesc(result));
        }
    });
}

function updatetz() {
    $("#modalupdatetz").validateForm(function () {
        $("#modalupdatetz").autoajax('maintenance/updatebranch', {
            loginid: $("#userid").val()
        }, function (result) {
            if (ifResultOK(result)) {
                $("#modalupdatetz").modal('hide');
                loadbranch();
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}
