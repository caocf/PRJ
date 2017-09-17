/**
 * Created by 25019 on 2015/10/28.
 */
$(document).ready(function () {
    var username = $("#username").val();
    if (username == null || username == "" || username == "null") {
        window.location.href = $("#basePath").val() + "page/login/login.jsp";
    }
    var xzqh = 1;
    xzqh = $("#deptxzqh").val();
    $("#selectfswsearchhdao").empty();
    $("#updatehdao").empty();
    $("#selectfswsearchhdao").append($('<option value="-1">全部航道</option>'));
    queryhangdao(-1, xzqh, function (hdaolist) {
        for (var i = 0; i < hdaolist.length; i++) {
            var hdao = hdaolist[i];
            $("#selectfswsearchhdao").append($('<option value="' + hdao.id + '">' + hdao.hdmc + '</option>'));
            $("#updatehdao").append($('<option value="' + hdao.id + '">' + hdao.hdmc + '</option>'));
        }
    });
    loadczpc();
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
    $("#modalnew").bind('shown.bs.modal', function () {
        $("#inputname").focus();
        $("#addczpcform").validateFormReset();
    });
    $('#btndelczpc').click(function () {
        var delids = $("#modaldel").attr('delids').split(',');
        delczpc(delids);
    });
    $("#cbselall").bind('click', function () {
        selallgc();
    });
});

function queryhangdao(sfgg, xzqh, callbk) {
    ajax('hangdao/queryallhangdao', {
        'loginid': $("#userid").val(),
        'sfgg': -1,
        'xzqh': xzqh
    }, function (data) {
        if (ifResultOK(data)) {
            var records = getResultRecords(data);
            if (records) {
                var hdaolist = records.data;
                callbk(hdaolist);
            }
        }
    });
}

function queryhangduan(hdaoid, xzqh, callbk, failedfn) {
    ajax('hangduan/queryhangduanbysshdid', {
        'loginid': $("#userid").val(),
        'sshdid': hdaoid,
        'xzqh': xzqh
    }, function (data) {
        if (ifResultOK(data)) {
            var records = getResultObj(data);
            if (records) {
                callbk(records);
            }
        }
    }, failedfn);
}

function initsearchhangduan(hdaoid) {
    if (hdaoid != "-1") {
        var xzqh = 1;
        xzqh = $("#deptxzqh").val();
        queryhangduan(hdaoid, xzqh, function (hduanlist) {
            $("#selectfswsearchhduan").empty();
            $("#selectfswsearchhduan").append($('<option value="-1">全部航段</option>'));
            for (var i = 0; i < hduanlist.length; i++) {
                var hduan = hduanlist[i].hangduan;
                var namestart = hduan.hdqdmc;
                var nameend = hduan.hdzdmc;
                var hduanmc = namestart + "-" + nameend;
                $("#selectfswsearchhduan").append($('<option value="' + hduan.id + '">' + hduanmc + '</option>'));
            }
        });
    } else {
        $("#selectfswsearchhduan").empty();
        $("#selectfswsearchhduan").append($('<option value="-1">全部航段</option>'));
    }
}

function initupdatehangduan(hdaoid, hduanid) {
    if (hdaoid != "-1") {
        var xzqh = 1;
        xzqh = $("#deptxzqh").val();
        queryhangduan(hdaoid, xzqh, function (hduanlist) {
            $("#updatehduan").empty();
            for (var i = 0; i < hduanlist.length; i++) {
                var hduan = hduanlist[i].hangduan;
                var namestart = hduan.hdqdmc;
                var nameend = hduan.hdzdmc;
                var hduanmc = namestart + "-" + nameend;
                if (hduanid == hduan.id) {
                    $("#updatehduan").append($('<option selected value="' + hduan.id + '">' + hduanmc + '</option>'));
                } else {
                    $("#updatehduan").append($('<option value="' + hduan.id + '">' + hduanmc + '</option>'));
                }
            }
        });
    } else {
        $("#updatehduan").empty();
    }
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

function loadczpc() {
    var starttime = null, endtime = null, content = null;
    var data = {
        'loginid': $("#userid").val()
    };
    starttime = $("#seldaystart").attr('realvalue');
    endtime = $("#seldayend").attr('realvalue');
    content = $("#inputcontent").val();

    if (starttime != null && starttime.length != 0)
        data.starttime = starttime;
    if (endtime != null && endtime.length != 0)
        data.endtime = endtime;
    if (content != null && content.length != 0)
        data.content = content;

    $("#czpctable").adddatatable(
        {
            url: 'channelmanage/queryreparation',
            data: data,
            datafn: function (data) {
                var records = getResultRecords(data);
                var ret = {};
                var list = new Array();
                if (records != null) {
                    for (var i in records.data) {
                        var czpc = records.data[i];
                        var dt = {};
                        dt.id = czpc.id;
                        dt.dw = czpc.dw;
                        dt.name = czpc.name;
                        dt.xz = czpc.xz == 1 ? "占用" : "损坏";
                        dt.pbcje = czpc.pbcje;
                        dt.sldw = czpc.sldw;
                        dt.slrq = czpc.slrq;
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
                mDataProp: 'name'
            }, {
                mDataProp: 'xz'
            }, {
                mDataProp: 'pbcje'
            }, {
                mDataProp: 'sldw'
            }, {
                mDataProp: 'slrq'
            }, {
                mDataProp: null
            }],
            columndefs: [{
                targets: 7,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="viewczpc(\''
                        + row.id
                        + '\')">查看</a>';
                    if (hasPerm('MAN_CZPC')) {
                        ret += '<a class="btn btn-link btnoper" onclick="updateczpcload(\''
                            + row.id
                            + '\')">修改</a>'
                            + '<a class="btn btn-link btnoper" onclick="delczpcload(\''
                            + row.id + '\',\'' + row.name
                            + '\')">删除</a>';
                    }
                    return ret;
                }
            }],
            fncreatedrow: function (nRow, aData, iDataIndex) {
                var start = parseInt($('#czpctable').attr('start'));
                $("td:eq(0)", nRow).html(
                    '<input type="checkbox" name="czpccheckbox" cbid=' + aData.id + ' cbname="' + aData.name
                    + '" id="cb' + (start + iDataIndex)
                    + '" value="'
                    + (start + iDataIndex) + '">&nbsp;'
                    + (start + iDataIndex))
            }
        });
}

function newczpcload() {
    var nowday = getTimeFmt(new Date(),
        'yyyy-MM-dd HH:mm:ss');
    $("#divslrq").addtime({
        id: "addslrq",
        defaultval: nowday,
        autoajax: {
            name: "czpc.slrq"
        }
    });
    $("#adddw").validateTargetBind({notempty: {msg: "请输入单位"}});
    $("#addname").validateTargetBind({notempty: {msg: "请输入项目名称"}});
    $("#addpbcje").validateTargetBind({double: {}, notempty: {msg: "请输入赔补偿金额"}});
    $("#addsldw").validateTargetBind({notempty: {msg: "请输入受理单位"}});
    $("#addzcpgdw").validateTargetBind({notempty: {msg: "请输入资产评估单位"}});
    $('#modalnew').modal('show');
}

function addczpc() {
    var jsondata = new Array();
    $(".zylxcontainer").each(function () {
        $container = $(this);
        var lx1 = 0, lx2 = 0, lx3 = 0;
        lx1 = parseInt($("#lx1sel", $container).val());
        if (lx1 == 3) {
            lx2 = parseInt($("#lx2sel", $container).val());
            if (lx2 == 1 || lx2 == 2 || lx2 == 3 || lx2 == 4) {
                lx3 = parseInt($("#lx3sel" + lx2, $container).val());
            }
        }
        var val = $("#ipt" + lx1 + lx2 + lx3, $container).val();
        if (val != null && val != 0) {
            var param = {};
            param.dfl = lx1;
            param.xfl = lx2;
            param.sx = lx3;
            param.sl = Math.round(parseFloat(val) * 100) / 100;
            jsondata.push(param);
        }
    });
    var hdaoid = $("#selectfswsearchhdao").val();
    var hduanid = $("#selectfswsearchhduan").val();
    if (hdaoid == -1) {
        alert("请选择航道");
        return false;
    }
    if (hduanid == -1) {
        alert("请选择航段");
        return false;
    }
    var data = {
        'jsondata': JSON.stringify(jsondata),
        'czpc.xz': $("input[name='addxz']:checked").val(),
        'czpc.hdaoid': hdaoid,
        'czpc.hduanid': hduanid
    };
    $("#addczpcform").validateForm(function () {
        $("#addczpcform").autoajax('channelmanage/addreparation', data, function (result, data) {
                if (ifResultOK(result)) {
                    $("#modalnew").modal('hide');
                    loadczpc();
                } else {
                    alert(getResultDesc(result));
                }
            }
        );
    });
}

function viewczpc(id) {
    $.ajax({
        url: 'channelmanage/queryreparationbyid',
        type: 'post',
        data: {
            'id': id
        },
        success: function (data) {
            $("#modalviewbody").load('page/hangzheng/czpc-view.jsp #container');
            $("#modalview").modal('show');
        }
    });
}

function updateczpcload(id) {
    ajax('channelmanage/queryreparationbyid', {
        id: id
    }, function (result) {
        if (ifResultOK(result)) {
            $('#updateczpcform').validateFormReset();
            var czpc = result.obj;
            var lx = getResultMap(result).lx;
            if (czpc != null) {
                var slrq = getTimeFmt(getTimeFromStr(czpc.slrq, 'yyyy-MM-dd HH:mm:ss'));
                $("#divupdateid").empty();
                if (lx != null) {
                    $(".addedcontainer", $("#modalupdate")).remove();
                    var len = lx.length - 1;
                    var islast = 0;
                    for (var i in lx) {
                        if (i == len) {
                            islast = 1;
                        }
                        addmorezylx($("#addlxupdate")[0], lx[i].dfl, lx[i].xfl, lx[i].sx, lx[i].sl, islast);
                    }
                }
                initupdatehangduan(czpc.hdaoid, czpc.hduanid);
                $("#updatehdao option[value='" + czpc.hdaoid + "']").attr("selected", true);
                $("input[name=updatexz][value=" + czpc.xz + "]").attr("checked", true);
                $("#divupdateid").addhidden({
                    defaultval: czpc.id,
                    autoajax: {
                        name: 'id'
                    }
                });
                $("#divupdatedw").addinputtext({
                    id: "updatedw",
                    validators: {
                        notempty: {
                            msg: '请输入单位'
                        }
                    },
                    defaultval: czpc.dw
                });
                $("#divupdatename").addinputtext({
                    id: 'updatename', validators: {
                        notempty: {
                            msg: '请输入项目名称'
                        }
                    },
                    defaultval: czpc.name
                });
                $("#divupdatepbcje").addinputtext({
                    id: 'updatepbcje',
                    validators: {
                        notempty: {
                            msg: '请输入赔补偿金额'
                        }
                    },
                    defaultval: czpc.pbcje
                });
                $("#divupdatesldw").addinputtext({
                    id: 'updatesldw', validators: {
                        notempty: {
                            msg: '请输入单位'
                        }
                    },
                    defaultval: czpc.sldw
                });
                $("#divupdatezcpgdw").addinputtext({
                    id: 'updatezcpgdw', validators: {
                        notempty: {
                            msg: '请输入资产评估单位'
                        }
                    },
                    defaultval: czpc.zcpgdw
                });
                $("#divupdateslrq").addtime({
                    id: 'updateslrq',
                    defaultval: slrq,
                    autoajax: {
                        name: 'czpc.slrq'
                    }
                });

            }
            $('#modalupdate').modal('show');
        } else {
            alert(getResultDesc(result));
        }
    });
}

function updateczpc() {
    var jsondata = new Array();
    $(".zylxcontainer").each(function () {
        $container = $(this);
        var lx1 = 0, lx2 = 0, lx3 = 0;
        lx1 = parseInt($("#lx1sel", $container).val());
        if (lx1 == 3) {
            lx2 = parseInt($("#lx2sel", $container).val());
            if (lx2 == 1 || lx2 == 2 || lx2 == 3 || lx2 == 4) {
                lx3 = parseInt($("#lx3sel" + lx2, $container).val());
            }
        }
        var val = $("#ipt" + lx1 + lx2 + lx3, $container).val();
        if (val != null && val != 0) {
            var param = {};
            param.dfl = lx1;
            param.xfl = lx2;
            param.sx = lx3;
            param.sl = Math.round(parseFloat(val) * 100) / 100;
            jsondata.push(param);
        }
    });
    var data = {
        'jsondata': JSON.stringify(jsondata),
        'czpc.xz': $("input[name='updatexz']:checked").val(),
        'czpc.hdaoid': $("#updatehdao").val(),
        'czpc.hduanid': $("#updatehduan").val(),
    };
    $("#updateczpcform").validateForm(function () {
        $("#updateczpcform").autoajax('channelmanage/updatereparation', data, function (result, data) {
                if (ifResultOK(result)) {
                    $("#modalupdate").modal('hide');
                    location.reload(true);
                } else {
                    alert(getResultDesc(result));
                }
            }
        );
    });
}

function delczpcload(id, name) {
    var delids = new Array();
    var delnames = new Array();

    delids.push(id);
    delnames.push(name);

    if (delids.length <= 0) {
        alert('请先选择要删除的重置赔偿！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldel").attr('delids', delids.join(","));
    $("#modaldel").modal('show');
}

function delczpcsload() {
    var delids = new Array();
    var delnames = new Array();
    $("[name='czpccheckbox']").each(function () {
        if ($(this).prop("checked") == true) {
            var id = $(this).attr('cbid');
            delids.push(id);
            var name = $(this).attr('cbname');
            delnames.push(name);
        }
    });
    if (delids.length <= 0) {
        alert('请先选择要删除的重置赔偿！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldel").attr('delids', delids.join(","));
    $("#modaldel").modal('show');
}

function delczpc(ids) {
    var data = {
        loginid: $("#userid").val()
    };
    for (var index in ids) {
        var id = ids[index];
        data["ids[" + index + "]"] = id;
    }
    ajax('channelmanage/delreparation', data, function (result) {
        if (ifResultOK(result)) {
            $("#modaldel").modal('hide');
            loadczpc();
        } else {
            alert(getResultDesc(result));
        }
    });
}


