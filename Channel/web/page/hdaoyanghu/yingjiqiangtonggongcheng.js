var deletefileids = new Array();
$(document).ready(function () {
    comminit();
    initui();
});

function initui() {
    var xzqh = 1;
    xzqh = $("#deptxzqh").val();
    $('#btnaddproject').click(addyjqtgc);
    $('#btneditproject').click(edityjqtgc);
    $("#cbselall").bind('click', function () {
        selallgc();
    });
    $('#modalnewproject').bind('show.bs.modal', function () {
        $("#addgldw").focus();
        $('#addproject').validateFormReset();
    });

    $('#btndelyjqtgc').click(function () {
        var delids = $("#modaldelyjqtgc").attr('delids').split(',');
        delyjqtgc(delids);
    });
    $("#selhdao").empty();
    $("#selhdao").append($('<option value="-1">全部航道</option>'));
    $("#seledithdao").empty();
    $("#seledithdao").append($('<option value="-1">全部航道</option>'));
    queryhangdao(-1, xzqh, function (hdaolist) {
        var data = {};
        for (var i = 0; i < hdaolist.length; i++) {
            var hdao = hdaolist[i];
            var id = hdao.id;
            var no = hdao.hdbh;
            var name = hdao.hdmc;
            data[id] = name;
            $("#selhdao").append($('<option value="' + id + '">' + name + '</option>'));
            $("#seledithdao").append($('<option value="' + id + '">' + name + '</option>'));
        }
        $("#divhdaoid").empty();
        $("#divhdaoid").addselect({
            data: data,
            validators: {
                notempty: {
                    msg: '请选择航道'
                }
            },
            onchange: function () {
                loadselhduan();
            },
            autoajax: {
                name: 'yjqtgc.hdaoid'
            },
            id: "addhdaoid",
            selectstyle: 'width:100%;padding-right:0px;'
        });
        $("#divselhdaoid").empty();
        $("#divselhdaoid").addselect({
            data: data,
            validators: {
                notempty: {
                    msg: '请选择航道'
                }
            },
            autoajax: {
                name: 'hdaoid'
            },
            id: "searchhdaoid",
            selectstyle: 'width:100%;padding-right:0px;'
        });
    });
    loadyjqtgc();
}

function loadselhduan() {
    $("#tempselect").hide();
    // 加载航道选择
    ajax('hangduan/queryhangduanbysshdid', {
        'sshdid': $("#addhdaoid").val(),
    }, function (data) {
        if (ifResultOK(data)) {
            var records = getResultObj(data);
            if (records) {
                var data = {};
                for (var i = 0; i < records.length; i++) {
                    var hduan = records[i].hangduan;
                    var id = hduan.id;
                    var name = hduan.hdqdmc + "---" + hduan.hdzdmc;
                    data[id] = name;
                }
                $("#divhduanid").empty();
                $("#divhduanid").addselect({
                    data: data,
                    validators: {
                        notempty: {
                            msg: '请选择航段'
                        }
                    },
                    autoajax: {
                        name: 'yjqtgc.hduanid'
                    },
                    id: "addhduanid",
                    selectstyle: 'width:100%;padding-right:0px;'
                });
            }
        }
    });
}

function loadeditselhduan() {
    $("#tempeditselect").hide();
    // 加载航道选择
    ajax('hangduan/queryhangduanbysshdid', {
        'sshdid': $("#edithdaoid").val(),
    }, function (data) {
        if (ifResultOK(data)) {
            var records = getResultObj(data);
            if (records) {
                var data = {};
                for (var i = 0; i < records.length; i++) {
                    var hduan = records[i].hangduan;
                    var id = hduan.id;
                    var name = hduan.hdqdmc + "---" + hduan.hdzdmc;
                    data[id] = name;
                }
                $("#divedithduanid").empty();
                $("#divedithduanid").addselect({
                    data: data,
                    validators: {
                        notempty: {
                            msg: '请选择航段'
                        }
                    },
                    autoajax: {
                        name: 'yjqtgc.hduanid'
                    },
                    id: "edithduanid",
                    selectstyle: 'width:100%;padding-right:0px;'
                });
            }
        }
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

function loadyjqtgc() {
    var hdaoid = $("#selhdao").val();
    var hduanid = $("#selhduan").val();
    var gldw = $('#selgldw').attr('selitem');
    var starttime = $('#seldaystart').attr('realvalue');
    var endtime = $('#seldayend').attr('realvalue');
    var content = $('#searchContent').val();

    var page = $("#tableRecords").attr('page');

    //查询时是否递归查询
    var sfdg = 0;
    if (hasPerm('VIEW_SHENYJQTGC') || hasPerm('MAN_SHIYJQTGC') || hasPerm('VIEW_SHIYJQTGC')) {
        sfdg = 1;
    }

    $('#tableRecords')
        .adddatatable(
        {
            url: "maintenance/searchyjqtgc",
            data: {
                'loginid': $("#userid").val(),
                'gldw': gldw == null ? '' : gldw,
                'sfdg': sfdg,
                'hdaoid': hdaoid,
                'hduanid': hduanid,
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
                    var dt = records.data;
                    for (var idx in dt) {
                        var obj = records.data[idx][0];
                        obj.starttime = getTimeFmtCn(obj.starttime);
                        obj.hdmc = records.data[idx][1].hdmc + "/" + records.data[idx][2].hdqdmc + "-" + records.data[idx][2].hdzdmc;
                        obj.deptname = records.data[idx][3].name;
                        list.push(obj);
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
                mDataProp: 'name'
            }, {
                mDataProp: 'hdmc'
            }, {
                mDataProp: 'starttime'
            }, {
                mDataProp: 'deptname'
            }, {
                mDataProp: 'cost'
            }, {
                mDataProp: 'updatetime'
            }, {
                mDataProp: null
            }],
            columndefs: [{
                targets: 7,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" data-toggle="tooltip" data-placement="top" title="查看工程信息" onclick="viewyjqtgc(\''
                        + row.id
                        + '\')">查看</a>';
                    if (hasPerm('MAN_DPTYJQTGC') || hasPerm('MAN_SHIYJQTGC')) {
                        ret += '<a class="btn btn-link btnoper" data-toggle="tooltip" data-placement="top" title="修改工程基本信息" onclick="editload('
                            + row.id
                            + ')">修改</a>'
                            + '<a class="btn btn-link btnoper" data-toggle="tooltip" data-placement="top" title="删除当前工程" onclick="delsingle('
                            + row.id
                            + ', \''
                            + row.name
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
                    + aData.id + ' gcmc="'
                    + aData.name + '" id="cb'
                    + (start + iDataIndex)
                    + '" value="'
                    + (start + iDataIndex) + '">&nbsp;'
                    + (start + iDataIndex))
            }
        });
}

function addyjqtgc() {
    $("#addproject").validateForm(function () {
        $("#addproject").autoajaxfileuploading('maintenance/addyjqtgc', {
            loginid: $("#userid").val()
        }, function (result) {
            if (ifResultOK(result)) {
                $("#modalnewproject").modal('hide');
                loadyjqtgc();
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
        alert('请先选择要删除的应急抢通工程！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldelyjqtgc").attr('delids', delids.join(","));
    $("#modaldelyjqtgc").modal('show');
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
        alert('请先选择要删除的应急抢通工程！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldelyjqtgc").attr('delids', delids.join(","));
    $("#modaldelyjqtgc").modal('show');
}

function delyjqtgc(ids) {
    var data = {
        loginid: $("#userid").val()
    };
    for (var index in ids) {
        var id = ids[index];
        data["ids[" + index + "]"] = id;
    }
    ajax('maintenance/delyjqtgc', data, function (result) {
        if (ifResultOK(result)) {
            $("#modaldelyjqtgc").modal('hide');
            loadyjqtgc();
        } else {
            alert(getResultDesc(result));
        }
    });
}

function viewyjqtgc(id) {
    ajax('maintenance/queryyjqtgcbyid', {
        id: id
    }, function (result) {
        if (ifResultOK(result)) {
            if (result.records != null) {
                var data = result.records.data[0];
                if (data != null) {
                    var gc = data[0];
                    $("#labname").text(gc.name);
                    $("#vieweventdesc").text(gc.eventdesc);
                    $("#labstarttime").text(getTimeFmtCn(gc.starttime));
                    $("#labmandept").text(data[3].name);
                    $("#labhdaoid").text(data[1].hdmc);
                    $("#labhduanid").text(data[2].hdqdmc + "-" + data[2].hdzdmc);
                    $("#labaddress").text(gc.address);
                    $("#viewlosecase").text(gc.losecase);
                    $("#viewmainreason").text(gc.mainreason);
                    $("#viewrealcase").text(gc.realcase);
                    $("#labparticipants").text(gc.participants);
                    $("#labcost").text(gc.cost);
                    $("#labrestoretime").text(getTimeFmtCn(gc.restoretime));
                    $("#viewremark").text(gc.remark);
                    //附件处理
                    var fjlist = getResultMap(result).fj.data;
                    $("div[name='fjrow']").empty();
                    for (var index in fjlist) {
                        var fjid = fjlist[index].id;
                        var fjname = fjlist[index].fname;
                        var fjsize = fjlist[index].fsize;
                        var fjtype = fjlist[index].ftype;
                        var rantoken = rand(1, 99999999);
                        var row = $('<div name="fjrow"></div>');
                        var col = $('<div class="col-xs-12" style="background:#e4f0ff;margin-top:2px;"></div>');
                        row.append(col);
                        col.append('<i class="icon-paper-clip" style="clolr:bule;"></i>&nbsp;'
                            + '<label style="font-weight:normal;" id="label' + rantoken + '">' + fjname
                            + "("
                            + fjsize
                            + "kb)"
                            + '</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
                            + '<a class="btn btn-link" href="maintenance/downloadyjqtgcfj?id=' + fjid + '">下载</a>'
                        );
                        if (fjtype == 1) {
                            col.append('<a class="btn btn-link" onclick="viewattachment(' + fjid + ')">预览</a>');
                        }
                        $("#showproject").append(row);
                    }
                }
            }
            $('#modalviewproject').modal('show');
        } else {
            alert(getResultDesc(result));
        }
    });
}
function editload(id) {
    ajax('maintenance/queryyjqtgcbyid', {
        loginid: $("#userid").val(),
        id: id
    }, function (result) {
        if (ifResultOK(result)) {
            if (result.records != null) {
                var data = result.records.data[0];
                if (data != null) {
                    var gc = data[0];
                    var id = gc.id;
                    var name = gc.name;
                    var eventdesc = gc.eventdesc;
                    var stime = getTimeFmt(getTimeFromStr(gc.starttime,
                        'yyyy-MM-dd HH:mm:ss'), 'yyyy-MM-dd');
                    var mandept = gc.mandept;
                    var address = gc.address;
                    var losecase = gc.losecase;
                    var mainreason = gc.mainreason;
                    var realcase = gc.realcase;
                    var participants = gc.participants;
                    var cost = gc.cost;
                    var rtime = getTimeFmt(getTimeFromStr(gc.restoretime,
                        'yyyy-MM-dd HH:mm:ss'), 'yyyy-MM-dd');
                    var remark = gc.remark;
                    $("#diveditid").empty();
                    $("#diveditid").addhidden({
                        defaultval: id,
                        autoajax: {
                            name: 'yjqtgc.id'
                        }
                    });
                    $("#diveditname").addinputtext({
                        id: 'editname',
                        defaultval: name
                    });
                    $("#divediteventdesc").addtextarea({
                        id: 'editeventdesc',
                        defaultval: eventdesc,
                        validators: {
                            notempty: {
                                msg: '请输入事件描述!'
                            }
                        }
                    });
                    $("#diveditstarttime").addday({
                        id: 'editstarttime',
                        defaultval: stime
                    });
                    $("#diveditmandept").addseldpt({
                        id: 'editmandept',
                        defaultval: mandept
                    });
                    initedithangduan(gc.hdaoid, gc.hduanid);
                    $("#seledithdao option[value='" + gc.hdaoid + "']").attr("selected", true);
                    $("#diveditaddress").addinputtext({
                        id: 'editaddress',
                        defaultval: address
                    });
                    $("#diveditlosecase").addtextarea({
                        id: 'editlosecase',
                        defaultval: losecase
                    });
                    $("#diveditmainreason").addtextarea({
                        id: 'editmainreason',
                        defaultval: mainreason
                    });
                    $("#diveditrealcase").addtextarea({
                        id: 'editrealcase',
                        defaultval: realcase,
                        validators: {
                            notempty: {
                                msg: '请输入处理情况!'
                            }
                        }
                    });
                    $("#diveditparticipants").addinputtext({
                        id: 'editparticipants',
                        defaultval: participants
                    });
                    $("#diveditcost").addinputtext({
                        id: 'editcost',
                        defaultval: cost
                    });
                    $("#diveditrestoretime").addday({
                        id: 'editrestoretime',
                        defaultval: rtime
                    });
                    $("#diveditremark").addtextarea({
                        id: 'editremark',
                        defaultval: remark
                    });
                    //附件处理
                    var fjlist = getResultMap(result).fj.data;
                    $("div[name='fjrow']").empty();
                    for (var index in fjlist) {
                        var fjid = fjlist[index].id;
                        var fjname = fjlist[index].fname;
                        var fjsize = fjlist[index].fsize;
                        var fjtype = fjlist[index].ftype;
                        var rantoken = rand(1, 99999999);
                        var row = $('<div  name="fjrow" ftype="originfile" id="trfileid' + fjid
                            + '"></div>');
                        var col = $('<div class="col-xs-12" style="background:#e4f0ff;margin-top:2px;"></div>');
                        row.append(col);
                        col.append('<i class="icon-paper-clip" style="clolr:bule;"></i>&nbsp;'
                            + '<label style="font-weight:normal;" id="label' + rantoken + '">' + fjname
                            + "("
                            + fjsize
                            + "kb)"
                            + '</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
                            + '<input type="button" class="btn btn-link" value="删除" onclick="adddelids(' + fjid + ')"/>');
                        $("#editproject").append(row);
                    }
                }
                $('#modaleditproject').modal('show');
            }
        } else {
            alert(getResultDesc(result));
        }
    });
}

function edityjqtgc() {
    var hdaoid = $("#seledithdao").val();
    var hduanid = $("#seledithduan").val();
    var url = "maintenance/updateyjqtgc";
    var param = {
        loginid: $("#userid").val(),
        'yjqtgc.hdaoid': hdaoid,
        'yjqtgc.hduanid': hduanid
    };
    for (var index in deletefileids) {
        var id = deletefileids[index];
        param["delfileids[" + index + "]"] = id;
    }
    ;
    $("#editproject").validateForm(function () {
        $("#editproject").autoajaxfileuploading(
            url,
            param,
            function (result) {
                if (ifResultOK(result)) {
                    $("#modaleditproject").modal('hide');
                    deletefileids.length = 0;
                    loadyjqtgc();
                } else {
                    alert(getResultDesc(result));
                }
            });
    });
}
/*********************处理附件信息js*****************************/
function addpicture(containerid) {
    var rantoken = rand(1, 99999999);

    var row = $('<div class="hide" id="row' + rantoken + '"></div>');
    var col = $('<div class="col-xs-12" style="background:#e4f0ff;margin-top:2px;"></div>');
    row.append(col);
    col.append('<i class="icon-paper-clip" style="clolr:bule;"></i>&nbsp;'
        + '<label style="font-weight:normal;" id="label'
        + rantoken
        + '"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
        + '<input type="button" class="btn btn-link" value="删除" onclick="$(\'#row'
        + rantoken
        + '\').remove();"><input id="file'
        + rantoken
        + '" type="file" class="hide" autoajax autoajax-name="filelist" onchange="selectfile(this,\''
        + containerid + '\',\'' + rantoken + '\')">');

    $("#" + containerid).append(row);
    $("#file" + rantoken).click();
}

function selectfile(from, containerid, rantoken) {
    var file = $('#file' + rantoken).val();
    var size = '';
    try {
        var size = (from.files[0].size / 1024).toFixed(2);
    } catch (e) {
        size = '';
    }

    if (file != null && file != "") {
        $("#row" + rantoken).removeClass('hide');

        var pos = file.lastIndexOf('\\');
        var filename = file.substring(pos + 1);
        if (size == null || size == '')
            $("#label" + rantoken).html(filename);
        else
            $("#label" + rantoken).html(filename + "(" + size + "kb)");
    }
}

function adddelids(id) {
    deletefileids.push(id);
    $("#trfileid" + id).remove();
}
// 显示图片附件
function viewattachment(id) {
    $("#yjqtgcpic").empty();
    $("#yjqtgcpic").append(
        '<img style="width:100%;height:auto;" src="maintenance/downloadyjqtgcfj?id=' + id + '">');
    $("#modalyjqtgcpic").modal('show');
}

function queryhangdao(sfgg, xzqh, callbk) {
    ajax('hangdao/queryallhangdao', {
        'loginid': $("#userid").val(),
        'xzqh': xzqh,
        'sfgg': -1
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
            $("#selhduan").empty();
            $("#selhduan").append($('<option value="-1">全部航段</option>'));
            for (var i = 0; i < hduanlist.length; i++) {
                var hduan = hduanlist[i].hangduan;
                var namestart = hduan.hdqdmc;
                var nameend = hduan.hdzdmc;
                var hduanmc = namestart + "-" + nameend;
                $("#selhduan").append($('<option value="' + hduan.id + '">' + hduanmc + '</option>'));
            }
        });
    } else {
        $("#selhduan").empty();
        $("#selhduan").append($('<option value="-1">全部航段</option>'));
    }
}

function initedithangduan(hdaoid, hduanid) {
    if (hdaoid != "-1") {
        var xzqh = 1;
        xzqh = $("#deptxzqh").val();
        queryhangduan(hdaoid, xzqh, function (hduanlist) {
            $("#seledithduan").empty();
            for (var i = 0; i < hduanlist.length; i++) {
                var hduan = hduanlist[i].hangduan;
                var namestart = hduan.hdqdmc;
                var nameend = hduan.hdzdmc;
                var hduanmc = namestart + "-" + nameend;
                if (hduanid == hduan.id) {
                    $("#seledithduan").append($('<option selected value="' + hduan.id + '">' + hduanmc + '</option>'));
                } else {
                    $("#seledithduan").append($('<option value="' + hduan.id + '">' + hduanmc + '</option>'));
                }
            }
        });
    } else {
        $("#seledithduan").empty();
    }
}