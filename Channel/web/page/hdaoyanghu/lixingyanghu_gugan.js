$(document).ready(function () {
    comminit();
    initui();
});

function initui() {
    loadhdaosel();
    loadgugan();
    $('#btneditproject').click(edittz);
    $("#cbselall").bind('click', function () {
        selallgc();
    });
    $("#modaladdtz").bind('shown.bs.modal', function () {
        $("#addtzdw").focus();
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
function loadhdaosel() {

    var xzqh = 1;

    if (hasPerm('MAN_SHIGGYH')) {
        xzqh = $("#szshixzqh").val();
    }

    // 加载航道选择
    ajax('hangdao/queryallhangdao', {
        'loginid': $("#userid").val(),
        'sfgg': '1',
        'xzqh': xzqh
    }, function (data) {
        if (ifResultOK(data)) {
            var records = getResultRecords(data);
            if (records) {
                var data = {};
                for (var i = 0; i < records.data.length; i++) {
                    var hdao = records.data[i];

                    var id = hdao.id;
                    var no = hdao.hdbh;
                    var name = hdao.hdmc;
                    data[id] = name;
                }
                $("#divselhdao").empty();
                $("#divselhdao").addmultiselect({
                    data: data,
                    onchange: function () {
                        loadgugan();
                    },
                    id: "multiselhdao"
                });
                $("#divedittzselhdao").addselect({
                    data: data,
                    id: "edittzselhdao",
                    autoajax: {
                        name: 'jbxx.hdid'
                    },
                    validators: {
                        notempty: {
                            msg: '请选择航道！'
                        }
                    }
                });
                $("#divaddtzselhdao").addselect({
                    data: data,
                    id: "addtzselhdao",
                    autoajax: {
                        name: 'jbxx.hdid'
                    },
                    validators: {
                        notempty: {
                            msg: '请选择航道！'
                        }
                    }
                });
            }
        }
    });
}

function loadgugan() {
    var starttime = null, endtime = null, hdids = null, content = null;

    var sfdg = 0;
    if (hasPerm('VIEW_SHENGGYH') || hasPerm('VIEW_SHIGGYH') || hasPerm('MAN_SHIGGYH')) {
        sfdg = 1;
    } else {
        sfdg = 0;
    }
    if (hasPerm('VIEW_SHENGGYH')) {
        dw = 1;//省
    }
    else if (hasPerm('VIEW_SHIGGYH') || hasPerm('MAN_SHIGGYH')) {
        dw = $("#szshiju").val();
    } else {
        dw = $("#szju").val();
    }

    var data = {
        'loginid': $("#userid").val(),
        'sfdg': sfdg,
        'dw': dw
    };

    hdids = $("#multiselhdao").attr('selitem');
    starttime = $("#selstarttime").attr('realvalue');
    endtime = $("#selendtime").attr('realvalue');
    content = $("#inputContent").val();

    if (starttime != null && starttime.length != 0)
        data.starttime = starttime;
    if (endtime != null && endtime.length != 0)
        data.endtime = endtime;
    if (content != null && content.length != 0)
        data.content = content;
    if (hdids != null) {
        hdids = hdids.split(',');
        var index = 0;
        for (var i = 0; i < hdids.length; i++) {
            if (hdids[i] != null && hdids[i] != '') {
                data['hdids[' + index + ']'] = hdids[i];
                index++;
            }
        }
    }
    $("#tablegugan").adddatatable(
        {
            url: 'maintenance/querymaintenances',
            data: data,
            datafn: function (data) {
                var records = getResultRecords(data);
                var ret = {};
                var list = new Array();
                if (records != null) {
                    for (var i in records.data) {
                        var dt = {};
                        dt.id = records.data[i][0].id;
                        dt.hdmc = records.data[i][1];
                        dt.dw = records.data[i][2];
                        //dt.dw = records.data[i][0].dw;
                        dt.clsl = records.data[i][0].clsl;
                        dt.cljg = records.data[i][0].cljg;
                        dt.sjsl = records.data[i][0].sjsl;
                        dt.sjjg = records.data[i][0].sjjg;
                        dt.zzjzwxfsl = records.data[i][0].zzjzwxfsl;
                        dt.zzjzwxfjg = records.data[i][0].zzjzwxfjg;
                        dt.glmtxfsl = records.data[i][0].glmtxfsl;
                        dt.glmtxfjg = records.data[i][0].glmtxfjg;
                        dt.xlzxfsl = records.data[i][0].xlzxfsl;
                        dt.xlzxfjg = records.data[i][0].xlzxfjg;
                        dt.hbwhsl = (records.data[i][0].hbwhzsl == null ? "" : records.data[i][0].hbwhzsl) + "/" + (records.data[i][0].hbwhzcsl == null ? "" : records.data[i][0].hbwhzcsl);
                        if (dt.hbwhsl == '/')
                            dt.hbwhsl = '';
                        dt.hbwhjg = records.data[i][0].hbwhjg;
                        dt.wzahwqcsdwsl = (records.data[i][0].wzahwqcssl == null ? "" : records.data[i][0].wzahwqcssl) + "/" + (records.data[i][0].wzahwqcdwsl == null ? "" : records.data[i][0].wzahwqcdwsl);
                        if (dt.wzahwqcsdwsl == '/')
                            dt.wzahwqcsdwsl = '';
                        dt.wzahwqccdsl = (records.data[i][0].wzahwqccsl == null ? "" : records.data[i][0].wzahwqccsl) + "/" + (records.data[i][0].wzahwqcdsl == null ? "" : records.data[i][0].wzahwqcdsl);
                        if (dt.wzahwqccdsl == '/')
                            dt.wzahwqccdsl = '';
                        dt.wzahwqcjg = records.data[i][0].wzahwqcjg;
                        dt.lhyhsl = records.data[i][0].lhyhsl;
                        dt.lhyhjg = records.data[i][0].lhyhjg;
                        // dt.djrmc = records.data[i][2];
                        // dt.createtime =
                        // records.data[i][0].createtime;
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
                mDataProp: 'hdmc'
            }, {
                mDataProp: 'dw'
            }, {
                mDataProp: 'clsl'
            }, {
                mDataProp: 'cljg'
            }, {
                mDataProp: 'sjsl'
            }, {
                mDataProp: 'sjjg'
            }, {
                mDataProp: 'zzjzwxfsl'
            }, {
                mDataProp: 'zzjzwxfjg'
            }, {
                mDataProp: 'glmtxfsl'
            }, {
                mDataProp: 'glmtxfjg'
            }, {
                mDataProp: 'xlzxfsl'
            }, {
                mDataProp: 'xlzxfjg'
            }, {
                mDataProp: 'hbwhsl'
            }, {
                mDataProp: 'hbwhjg'
            }, {
                mDataProp: 'wzahwqcsdwsl'
            }, {
                mDataProp: 'wzahwqccdsl'
            }, {
                mDataProp: 'wzahwqcjg'
            }, {
                mDataProp: 'lhyhsl'
            }, {
                mDataProp: 'lhyhjg'
            }, {
                mDataProp: 'month'
            }, {
                mDataProp: null
            }],
            columndefs: [{
                targets: 21,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="viewtz(\''
                        + row.id
                        + '\')">查看</a>';
                    if (hasPerm('MAN_DPTGGYH') || hasPerm('MAN_SHIGGYH')) {
                        ret += '<a class="btn btn-link btnoper" onclick="editload('
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
                var start = parseInt($('#tablegugan').attr('start'));
                $("td:eq(0)", nRow).html(
                    '<input type="checkbox" name="guganhucheckbox" tzid='
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
        $("#modaladdtz").autoajax('maintenance/addmaintenance', {
            loginid: $("#userid").val()
        }, function (result, data) {
            if (ifResultOK(result)) {
                $("#modaladdtz").modal('hide');
                loadgugan();
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}
function viewtz(id) {
    ajax('maintenance/querymainbyid', {
        loginid: $("#userid").val(),
        jbxxid: id
    }, function (result) {
        if (ifResultOK(result)) {
            $("#modalviewtz").modal('show');
            var records = getResultRecords(result);
            $("#viewtzdw").text(records.data[0][2].name);
            $("#viewtzselhdao").text(records.data[0][1].hdmc);
            $("#viewtzselrq").text(getTimeFmt(getTimeFromStr(records.data[0][0].starttime,
                'yyyy-MM-dd'), 'yyyy年MM月'));
            $("#iview11").text(
                records.data[0][0].clsl == null ? "0"
                    : records.data[0][0].clsl);
            $("#iview12").text(
                records.data[0][0].cljg == null ? "0"
                    : records.data[0][0].cljg);
            $("#iview13").text(
                records.data[0][0].clbz == null ? ""
                    : records.data[0][0].clbz);
            $("#iview21").text(
                records.data[0][0].sjsl == null ? "0"
                    : records.data[0][0].sjsl);
            $("#iview22").text(
                records.data[0][0].sjjg == null ? "0"
                    : records.data[0][0].sjjg);
            $("#iview23").text(
                records.data[0][0].sjbz == null ? ""
                    : records.data[0][0].sjbz);
            $("#iview31").text(
                records.data[0][0].zzjzwxfsl == null ? "0"
                    : records.data[0][0].zzjzwxfsl);
            $("#iview32").text(
                records.data[0][0].zzjzwxfjg == null ? "0"
                    : records.data[0][0].zzjzwxfjg);
            $("#iview33").text(
                records.data[0][0].zzjzwxfbz == null ? ""
                    : records.data[0][0].zzjzwxfbz);
            $("#iview41").text(
                records.data[0][0].glmtxfsl == null ? "0"
                    : records.data[0][0].glmtxfsl);
            $("#iview42").text(
                records.data[0][0].glmtxfjg == null ? "0"
                    : records.data[0][0].glmtxfjg);
            $("#iview43").text(
                records.data[0][0].glmtxfbz == null ? ""
                    : records.data[0][0].glmtxfbz);
            $("#iview51").text(
                records.data[0][0].xlzxfsl == null ? "0"
                    : records.data[0][0].xlzxfsl);
            $("#iview52").text(
                records.data[0][0].xlzxfjg == null ? "0"
                    : records.data[0][0].xlzxfjg);
            $("#iview53").text(
                records.data[0][0].xlzxfbz == null ? ""
                    : records.data[0][0].xlzxfbz);
            var zsl = records.data[0][0].hbwhzsl == null ? "0" : records.data[0][0].hbwhzsl;
            var zcsl = records.data[0][0].hbwhzcsl == null ? "0" : records.data[0][0].hbwhzcsl;
            $("#iview61").text(zsl + "/" + zcsl);
            $("#iview62").text(
                records.data[0][0].hbwhjg == null ? "0"
                    : records.data[0][0].hbwhjg);
            $("#iview63").text(
                records.data[0][0].hbwhbz == null ? ""
                    : records.data[0][0].hbwhbz);
            var ssl = records.data[0][0].wzahwqcssl == null ? "0"
                : records.data[0][0].wzahwqcssl;
            var dwsl = records.data[0][0].wzahwqcdwsl == null ? "0"
                : records.data[0][0].wzahwqcdwsl;
            var csl = records.data[0][0].wzahwqccsl == null ? "0"
                : records.data[0][0].wzahwqccsl;
            var dsl = records.data[0][0].wzahwqcdsl == null ? "0"
                : records.data[0][0].wzahwqcdsl;
            $("#iview71").text(ssl + "/" + dwsl)
            $("#iview72").text(
                records.data[0][0].wzahwqcjg == null ? "0"
                    : records.data[0][0].wzahwqcjg);
            $("#iview73").text(
                records.data[0][0].wzahwqcbz == null ? ""
                    : records.data[0][0].wzahwqcbz);
            $("#iview81").text(csl + "/" + dsl);
            $("#iview91").text(
                records.data[0][0].lhyhsl == null ? "0"
                    : records.data[0][0].lhyhsl);
            $("#iview92").text(
                records.data[0][0].lhyhjg == null ? "0"
                    : records.data[0][0].lhyhjg);
            $("#iview93").text(
                records.data[0][0].lhyhbz == null ? ""
                    : records.data[0][0].lhyhbz);
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
        data["jbxxidlist[" + index + "]"] = id;
    }
    ajax('maintenance/delmaintenance', data, function (result) {
        if (ifResultOK(result)) {
            $("#modaldeltz").modal('hide');
            loadgugan();
        } else {
            alert(getResultDesc(result));
        }
    });
}
function delmulti() {
    var delids = new Array();
    var delnames = new Array();
    $("[name='guganhucheckbox']").each(function () {
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

function editload(id) {
    ajax('maintenance/querymainbyid', {
        loginid: $("#userid").val(),
        jbxxid: id
    }, function (result) {
        if (ifResultOK(result)) {
            $('#modaledittz').validateFormReset();
            if (result.records != null) {
                var jbxx = result.records.data[0][0];
                var hduan = result.records.data[0][1];
                if (jbxx != null) {
                    var id = jbxx.id;
                    var hdid = jbxx.hdid;
                    var dw = jbxx.dw;
                    var hdmc = hduan.hdmc;
                    var starttime = getTimeFmt(getTimeFromStr(jbxx.starttime, 'yyyy-MM-dd'), 'yyyy-MM');
                    var clsl = jbxx.clsl;
                    var cljg = jbxx.cljg;
                    var clbz = jbxx.clbz;
                    var sjsl = jbxx.sjsl;
                    var sjjg = jbxx.sjjg;
                    var sjbz = jbxx.sjbz;
                    var zzjzwxfsl = jbxx.zzjzwxfsl;
                    var zzjzwxfjg = jbxx.zzjzwxfjg;
                    var zzjzwxfbz = jbxx.zzjzwxfbz;
                    var glmtxfsl = jbxx.glmtxfsl;
                    var glmtxfjg = jbxx.glmtxfjg;
                    var glmtxfbz = jbxx.glmtxfbz;
                    var xlzxfsl = jbxx.xlzxfsl;
                    var xlzxfjg = jbxx.xlzxfjg;
                    var xlzxfbz = jbxx.xlzxfbz;
                    var hbwhzsl = jbxx.hbwhzsl;
                    var hbwhzcsl = jbxx.hbwhzcsl;
                    var hbwhjg = jbxx.hbwhjg;
                    var hbwhbz = jbxx.hbwhbz;
                    var wzahwqcssl = jbxx.wzahwqcssl;
                    var wzahwqcdwsl = jbxx.wzahwqcdwsl;
                    var wzahwqccsl = jbxx.wzahwqccsl;
                    var wzahwqcdsl = jbxx.wzahwqcdsl;
                    var wzahwqcjg = jbxx.wzahwqcjg;
                    var wzahwqcbz = jbxx.wzahwqcbz;
                    var lhyhsl = jbxx.lhyhsl;
                    var lhyhjg = jbxx.lhyhjg;
                    var lhyhbz = jbxx.lhyhbz;

                    $("#divedittzid").empty();
                    $("#divedittzid").addhidden({
                        defaultval: id,
                        autoajax: {
                            name: 'jbxx.id'
                        }
                    });
                    $("#divedittzdw").addseldpt({
                        id: 'edittzdw',
                        defaultval: dw
                    });
                    $("#divedittzselhdao").addselect({
                        id: 'edittzselhdao',
                        defaultval: hdid
                    });
                    $("#divedittzselrq").addmonth({
                        id: 'edittzselrq',
                        defaultval: starttime
                    });
                    $("#e11").addinputtext({
                        id: 'einput11',
                        defaultval: clsl
                    });
                    $("#e12").addinputtext({
                        id: 'einput12',
                        defaultval: cljg
                    });
                    $("#e13").addinputtext({
                        id: 'einput13',
                        defaultval: clbz
                    });
                    $("#e21").addinputtext({
                        id: 'einput21',
                        defaultval: sjsl
                    });
                    $("#e22").addinputtext({
                        id: 'einput22',
                        defaultval: sjjg
                    });
                    $("#e23").addinputtext({
                        id: 'einput23',
                        defaultval: sjbz
                    });
                    $("#e31").addinputtext({
                        id: 'einput31',
                        defaultval: zzjzwxfsl
                    });
                    $("#e32").addinputtext({
                        id: 'einput32',
                        defaultval: zzjzwxfjg
                    });
                    $("#e33").addinputtext({
                        id: 'einput33',
                        defaultval: zzjzwxfbz
                    });
                    $("#e41").addinputtext({
                        id: 'einput41',
                        defaultval: glmtxfsl
                    });
                    $("#e42").addinputtext({
                        id: 'einput42',
                        defaultval: glmtxfjg
                    });
                    $("#e43").addinputtext({
                        id: 'einput43',
                        defaultval: glmtxfbz
                    });
                    $("#e51").addinputtext({
                        id: 'einput51',
                        defaultval: xlzxfsl
                    });
                    $("#e52").addinputtext({
                        id: 'einput52',
                        defaultval: xlzxfjg
                    });
                    $("#e53").addinputtext({
                        id: 'einput53',
                        defaultval: xlzxfbz
                    });
                    $("#e61").addinputtext({
                        id: 'einput61',
                        defaultval: hbwhzsl
                    });
                    $("#e611").addinputtext({
                        id: 'einput611',
                        defaultval: hbwhzcsl
                    });
                    $("#e62").addinputtext({
                        id: 'einput62',
                        defaultval: hbwhjg
                    });
                    $("#e63").addinputtext({
                        id: 'einput63',
                        defaultval: hbwhbz
                    });
                    $("#e71").addinputtext({
                        id: 'einput71',
                        defaultval: wzahwqcssl
                    });
                    $("#e711").addinputtext({
                        id: 'einput711',
                        defaultval: wzahwqcdwsl
                    });
                    $("#e72").addinputtext({
                        id: 'einput72',
                        defaultval: wzahwqcjg
                    });
                    $("#e73").addinputtext({
                        id: 'einput73',
                        defaultval: wzahwqcbz
                    });
                    $("#e81").addinputtext({
                        id: 'einput81',
                        defaultval: wzahwqccsl
                    });
                    $("#e811").addinputtext({
                        id: 'einput811',
                        defaultval: wzahwqcdsl
                    });
                    $("#e91").addinputtext({
                        id: 'einput91',
                        defaultval: lhyhsl
                    });
                    $("#e92").addinputtext({
                        id: 'einput92',
                        defaultval: lhyhjg
                    });
                    $("#e93").addinputtext({
                        id: 'einput93',
                        defaultval: lhyhbz
                    });
                }
                $('#modaledittz').modal('show');
            }
        } else {
            alert(getResultDesc(result));
        }
    });
}

function edittz() {
    $("#modaledittz").validateForm(function () {
        $("#modaledittz").autoajax('maintenance/updatemaintenance', {
            loginid: $("#userid").val()
        }, function (result) {
            if (ifResultOK(result)) {
                $("#modaledittz").modal('hide');
                loadgugan();
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}
