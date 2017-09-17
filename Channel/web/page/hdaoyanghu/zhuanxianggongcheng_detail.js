$(document).ready(function () {
    comminit();
    initui();
});

function initui() {
    $("#btndelzxgcload").click(function () {
        delsingle($("#zxgcid").val(), $("#zxgcmc").val());
    });
    $("#cbselfjall").bind('click', function () {
        selfjall();
    });
    $("#cbselybball").bind('click', function () {
        selybball();
    });
    $("#btneditzxgcload").click(function () {
        editload($("#zxgcid").val());
    });
    $('#btneditproject').click(editzxgc);

    $('#btndelzxgc').click(function () {
        var delids = $("#modaldelzxgc").attr('delids').split(',');
        delzxgc(delids);
    });
    loadzxgc($("#zxgcid").val());
}

function selfjall() {
    if ($("#cbselfjall").prop('checked') == true) {
        $(".fjcheckbox").prop('checked', true);
    } else {
        $(".fjcheckbox").prop('checked', false);
    }
}

function selybball() {
    if ($("#cbselybball").prop('checked') == true) {
        $(".ybbcheckbox").prop('checked', true);
    } else {
        $(".ybbcheckbox").prop('checked', false);
    }
}

function loadzxgc(id) {
    ajax(
        'maintenance/queryzxgcbyid',
        {
            loginid: $("#userid").val(),
            zxgcid: id
        },
        function (result) {
            if (ifResultOK(result)) {
                if (result.records != null) {
                    var gc = result.records.data[0][0];
                    var fjs = result.map.fjs.data;
                    var ybbs = result.map.ybbs.data;
                    var gldw = result.map.dpt;
                    var status = result.records.data[0][1];
                    if (gc != null) {
                        var gcid = gc.zxgcid;
                        var gcmc = gc.gcmc;
                        var starttime = getTimeFmt(getTimeFromStr(
                                gc.starttime, 'yyyy-MM-dd HH:mm:ss'),
                            'yyyy年MM月dd日');
                        var endtime = getTimeFmt(getTimeFromStr(gc.endtime,
                            'yyyy-MM-dd HH:mm:ss'), 'yyyy年MM月dd日');
                        var tz = gc.tz;
                        var jsdw = gc.jsdw;
                        var jldw = gc.jldw;
                        var sjdw = gc.sjdw;
                        var sgdw = gc.sgdw;
                        var bz = gc.bz;
                        var statuscode = gc.status;

                        $("#zxgcmc").val(gcmc);
                        $("#title").text(gcmc + "专项养护工程");
                        $("#title2").text(gcmc + "专项养护工程");
                        $("#tdstarttime").text(starttime);
                        $("#tdendtime").text(endtime);
                        $("#tdstatus").text(status);
                        $("#tdtz").text(tz);
                        $("#tdjsdw").text(jsdw);
                        $("#tdgldw").text(gldw);
                        $("#tdjldw").text(jldw);
                        $("#tdsjdw").text(sjdw);
                        $("#tdsgdw").text(sgdw);
                        $("#tdbz").text(bz);
                        $("#xmztcode").empty();
                        $("#xmztcode").text(statuscode);
                    }

                    if (fjs != null) {
                        $("#cbselfjall").prop('checked', false);
                        $("#tablefj").find('.fjclass').each(function () {
                            $(this).remove();
                        });

                        for (var i = 0; i < fjs.length; i++) {
                            var fj = fjs[i];
                            var id = fj.fjid;
                            var name = fj.resname;
                            var size = fj.ressize;
                            var time = fj.updatetime;

                            var tr = $('<tr class="fjclass"></tr>');
                            tr
                                .append($('<td><input class="fjcheckbox" type="checkbox" fjid='
                                    + id
                                    + ' fjname="'
                                    + name
                                    + '">' + (i + 1) + '</td>'));
                            tr.append($('<td>' + name + '</td>'));
                            tr.append($('<td>' + size + 'KB</td>'));
                            tr.append($('<td>' + time + '</td>'));
                            if (hasPerm('MAN_SHIZXGC') || hasPerm('MAN_DPTZXGC')) {
                                tr
                                    .append($('<td><a class="btn btn-link" onclick="downloadfj('
                                        + id
                                        + ')">下载</a><a class="btn btn-link" onclick="delfjsingle('
                                        + id
                                        + ',\''
                                        + name
                                        + '\')">删除</a></td>'));
                            }
                            $("#tablefj").append(tr);
                        }

                    }
                    if (ybbs != null) {
                        $("#cbselybball").prop('checked', false);
                        $("#tableybb").find('.ybbclass').each(function () {
                            $(this).remove();
                        });

                        for (var i = 0; i < ybbs.length; i++) {
                            var ybb = ybbs[i];

                            var id = ybb.ydjdqkid;
                            var dwmc = ybb.dwmc;
                            var month = ybb.ny;
                            var tr = $('<tr class="ybbclass"></tr>');
                            tr
                                .append($('<td><input class="ybbcheckbox" type="checkbox" ybbid='
                                    + id
                                    + ' ybbname="'
                                    + dwmc
                                    + '">' + (i + 1) + '</td>'));
                            tr.append($('<td>' + dwmc + '</td>'));
                            tr.append($('<td>' + month + '</td>'));

                            var oper = '<a class="btn btn-link" onclick="viewybb('
                                + id
                                + ')">查看</a>';
                            if (hasPerm('MAN_SHIZXGC') || hasPerm('MAN_DPTZXGC')) {
                                oper += '<a class="btn btn-link" onclick="editybbload('
                                    + id
                                    + ')">修改</a><a class="btn btn-link" onclick="delybbsingle('
                                    + id
                                    + ',\''
                                    + dwmc
                                    + '\')">删除</a>';
                            }
                            tr.append($('<td>' + oper + '</td>'));
                            $("#tableybb").append(tr);
                        }

                    }
                }
            } else {
                alert(getResultDesc(result));
            }
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

function delzxgc(ids) {
    var data = {
        loginid: $("#userid").val()
    };
    for (var index in ids) {
        var id = ids[index];
        data["zxgcidlist[" + index + "]"] = id;
    }
    ajax(
        'maintenance/delzxgc',
        data,
        function (result) {
            if (ifResultOK(result)) {
                $("#modaldelzxgc").modal('hide');

                window.location.href = 'page/hdaoyanghu/zhuanxianggongcheng.jsp?defaultpage='
                    + $("#defaultpage").val();

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
                //var gldw=result.map.dpt;
                if (gc != null) {
                    var gcid = gc.zxgcid;
                    var gcmc = gc.gcmc;
                    var starttime = getTimeFmt(getTimeFromStr(gc.starttime,
                        'yyyy-MM-dd HH:mm:ss'), 'yyyy-MM-dd');
                    var endtime = getTimeFmt(getTimeFromStr(gc.endtime,
                        'yyyy-MM-dd HH:mm:ss'), 'yyyy-MM-dd');
                    var status = gc.status;
                    var tz = gc.tz;
                    var gldw = gc.gldw;
                    var jsdw = gc.jsdw;
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
                loadzxgc($("#zxgcid").val());
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}

function uploadfj() {
    $("#fileupload").remove();
    $('body').append($('<input id="fileupload" class="hide" type="file">'));
    $("#fileupload").click();
    $("#fileupload").bind('change', function () {
        if ($('#fileupload').val() != null && $('#fileupload').val() != '') {
            var fileids = new Array();
            fileids.push('fileupload');
            ajaxfileuploading('maintenance/addcyhfj', 'filelist', fileids, {
                loginid: $("#userid").val(),
                zxgcid: $("#zxgcid").val()
            }, function (result) {
                if (ifResultOK(result)) {
                    loadzxgc($("#zxgcid").val());
                } else {
                    alert('附件上传失败!');
                }
            });
        }
    });
}

function downloadfj(fjid) {
    window.location.href = $("#basePath").val()
        + "maintenance/downloadcyhfj?loginid=" + $("#userid").val()
        + "&id=" + fjid;
}

function delfjmulti() {
    var fjids = new Array();
    var fjnames = new Array();

    $("#tablefj").find('.fjcheckbox').each(function () {
        if ($(this).prop("checked") == true) {
            var id = $(this).attr('fjid');
            var name = $(this).attr('fjname');
            fjids.push(id);
            fjnames.push(name);
        }
    });
    if (fjids.length <= 0) {
        alert('请先选择要删除的附件！');
        return;
    }

    if (fjnames.length <= 3) {
        fjnames = fjnames.join(',');
    } else {
        fjnames = fjnames[0] + ',' + fjnames[1] + ',' + fjnames[2] + '等'
            + fjids.length + '个文件';
    }

    $("#modaldelfj").attr('delids', fjids.join(','));
    $("#lbmodaldelfjnames").text(fjnames);

    $("#btndelfj").unbind('click');
    $("#btndelfj").bind('click', function () {
        var delids = $("#modaldelfj").attr('delids').split(',');
        delfj(delids);
    });
    $("#modaldelfj").modal('show');
}

function delfjsingle(fjid, fjname) {
    var fjids = new Array();
    fjids.push(fjid);

    $("#modaldelfj").attr('delids', fjids.join(','));
    $("#lbmodaldelfjnames").text(fjname);

    $("#btndelfj").unbind('click');
    $("#btndelfj").bind('click', function () {
        var delids = $("#modaldelfj").attr('delids').split(',');
        delfj(delids);
    });
    $("#modaldelfj").modal('show');
}

function delfj(fjids) {
    var data = {
        loginid: $("#userid").val(),
        zxgcid: $("#zxgcid").val()
    };
    var index = 0;
    for (var i = 0; i < fjids.length; i++) {
        if (fjids[i] != null && fjids[i] != '') {
            data['delfileids[' + index + ']'] = fjids[i];
            index++;
        }
    }
    ajax('maintenance/delcyhfj', data, function (result) {
        if (ifResultOK(result)) {
            $("#modaldelfj").modal('hide');
            loadzxgc($("#zxgcid").val());
        } else {
            alert('删除失败');
        }
    });
}

function addybb() {
    $("#formaddybb").validateForm(function () {
        $("#formaddybb").autoajax('maintenance/addcyhybb', {
            loginid: $("#userid").val(),
            zxgcid: $("#zxgcid").val()
        }, function (result, data) {
            if (ifResultOK(result)) {
                $("#modaladdybb").modal('hide');
                loadzxgc($("#zxgcid").val());
            } else {
                alert('新增月报表失败');
            }
        });
    });

}

function viewybb(id) {
    ajax(
        'maintenance/querycyhybb',
        {
            loginid: $("#userid").val(),
            id: id
        },
        function (result) {
            if (ifResultOK(result)) {
                var dwmc = "", yf = "", bywcje = "", zncljwcje = "", jdqk = "", bz = "";
                var obj = getResultObj(result);
                var xmzt = getResultMap(result).xmzt;
                if (obj != null) {
                    dwmc = obj.dwmc;
                    yf = obj.ny;
                    bywcje = obj.bywcje;
                    zncljwcje = obj.zncljwcje;
                    jdqk = obj.xmjdqk;
                    bz = obj.bz;
                }
                $("#tdviewdwmc").text(dwmc);
                $("#tdviewyf").text(yf);
                $("#tdviewbywcje").text(bywcje);
                $("#tdviewzncljwcje").text(zncljwcje);
                $("#tdviewjdqk").text(jdqk);
                $("#tdviewbz").text(bz);
                $("#tdviewxmzt").text(xmzt);
            }
            $('#modalviewybb').modal('show');
        });
}

function delybbmulti() {
    var ybbids = new Array();
    var ybbnames = new Array();

    $("#tableybb").find('.ybbcheckbox').each(function () {
        if ($(this).prop("checked") == true) {
            var id = $(this).attr('ybbid');
            var name = $(this).attr('ybbname');
            ybbids.push(id);
            ybbnames.push(name);
        }
    });
    if (ybbids.length <= 0) {
        alert('请先选择要删除的月报表！');
        return;
    }

    if (ybbnames.length <= 3) {
        ybbnames = ybbnames.join(',');
    } else {
        ybbnames = ybbnames[0] + ',' + ybbnames[1] + ',' + ybbnames[2] + '等'
            + ybbids.length + '个文件';
    }

    $("#modaldelybb").attr('delids', ybbids.join(','));
    $("#lbmodaldelybbnames").text(ybbnames);

    $("#btndelybb").unbind('click');
    $("#btndelybb").bind('click', function () {
        var delids = $("#modaldelybb").attr('delids').split(',');
        delybb(delids);
    });
    $("#modaldelybb").modal('show');
}

function delybbsingle(ybbid, ybbname) {
    var ybbids = new Array();
    ybbids.push(ybbid);

    $("#modaldelybb").attr('delids', ybbids.join(','));
    $("#lbmodaldelybbnames").text(ybbname);

    $("#btndelybb").unbind('click');
    $("#btndelybb").bind('click', function () {
        var delids = $("#modaldelybb").attr('delids').split(',');
        delybb(delids);
    });
    $("#modaldelybb").modal('show');
}

function delybb(ybbids) {
    var data = {
        loginid: $("#userid").val(),
        zxgcid: $("#zxgcid").val()
    };
    var index = 0;
    for (var i = 0; i < ybbids.length; i++) {
        if (ybbids[i] != null && ybbids[i] != '') {
            data['delybbids[' + index + ']'] = ybbids[i];
            index++;
        }
    }
    ajax('maintenance/delcyhybb', data, function (result) {
        if (ifResultOK(result)) {
            $("#modaldelybb").modal('hide');
            loadzxgc($("#zxgcid").val());
        } else {
            alert('删除失败');
        }
    });
}

function editybbload(id) {
    ajax('maintenance/querycyhybb',
        {
            loginid: $("#userid").val(),
            id: id
        },
        function (result) {
            if (ifResultOK(result)) {
                var id = "", dwmc = "", yf = "", bywcje = "", jdqk = "", bz = "", xmztcode = "";

                var obj = getResultObj(result);
                if (obj != null) {
                    dwmc = obj.dwmc;
                    id = obj.ydjdqkid;
                    yf = obj.ny;
                    bywcje = obj.bywcje;
                    jdqk = obj.xmjdqk;
                    bz = obj.bz;
                    xmzt = obj.xmzt;
                    $("#diveditybbid").empty();
                    $("#diveditybbid").addhidden({
                        defaultval: id,
                        autoajax: {
                            name: 'id'
                        }
                    });
                    $("#diveditdwmc").addinputtext({
                        id: 'editybbdwmc',
                        disabled: true,
                        defaultval: dwmc
                    });
                    $("#divedityf").addmonth({
                        id: 'editybbyf',
                        defaultval: yf
                    });
                    $("#diveditbywcje").addinputtext({
                        id: 'editybbbywcje',
                        defaultval: bywcje
                    });
                    $("#diveditjdqk").addtextarea({
                        id: 'editybbjdqk',
                        defaultval: jdqk
                    });
                    $("#diveditbz").addinputtext({
                        id: 'editybbbz',
                        defaultval: bz
                    });
                    $("#diveditxmzt").addselect({
                        id: 'editybbxmzt',
                        defaultval: xmzt
                    });
                }
            }
            $('#modaleditybb').modal('show');
        });
}

function editybb() {
    $("#formeditybb").validateForm(function () {
        $("#formeditybb").autoajax('maintenance/updatecyhybb', {
            loginid: $("#userid").val()
        }, function (result, data) {
            if (ifResultOK(result)) {
                $("#modaleditybb").modal('hide');
                loadzxgc($("#zxgcid").val());
            } else {
                alert('修改月报表失败');
            }
        });
    });
}
function addydjdqk() {
    $('#formaddybb').validateFormReset();
    $('#modaladdybb').modal('show');
    var xmztcode = $("#xmztcode").text();
    $("#ybbdwmc").val($("#zxgcmc").val());
    ajax('dic/querydicattr', {
        loginid: $("#userid").val(),
        name: 'zxgczt'
    }, function (data) {
        if (ifResultOK(data)) {
            var records = getResultRecords(data);
            if (records) {
                var data = {};
                for (var i = 0; i < records.data.length; i++) {
                    var dict = records.data[i];
                    var key = dict.id;
                    var value = dict.attrdesc;
                    data[key] = value;
                }
                $("#divxmzt").addselect({
                    data: data,
                    validators: {
                        notempty: {
                            msg: '请选择工程状态!'
                        }
                    },
                    id: 'ybbxmzt',
                    autoajax: {
                        name: 'cyhybb.xmzt'
                    },
                    defaultval: xmztcode
                });
            }
        }
    });
}