/**
 * Created by 25019 on 2015/10/28.
 */
var deletefileids = new Array();
$(document).ready(function () {
    var username = $("#username").val();
    if (username == null || username == "" || username == "null") {
        window.location.href = $("#basePath").val() + "page/login/login.jsp";
    }
    loadhdaosel();
    loadthlz();
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
        $("#addthlzform").validateFormReset();
    });
    $('#btndelthlz').click(function () {
        var delids = $("#modaldel").attr('delids').split(',');
        delthlz(delids);
    });
    $("#cbselall").bind('click', function () {
        selallgc();
    });

});

function loadhdaosel() {
    var xzqh = 1;
    xzqh = $("#deptxzqh").val();
    // 加载航道选择
    ajax('hangdao/queryallhangdao', {
        'loginid': $("#userid").val(),
        'sfgg': '-1',
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
                $("#addhdaodiv").addselect({
                    data: data,
                    id: "addhdaosel",
                    autoajax: {
                        name: 'argument.hdao'
                    },
                    validators: {
                        notempty: {
                            msg: '请选择航道！'
                        }
                    }
                });
                $("#updatehdaodiv").addselect({
                    data: data,
                    id: "updatehdaosel",
                    autoajax: {
                        name: 'argument.hdao'
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

function loadthlz() {
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

    $("#thlztable").adddatatable(
        {
            url: 'channelmanage/queryargument',
            data: data,
            datafn: function (data) {
                var records = getResultRecords(data);
                var ret = {};
                var list = new Array();
                if (records != null) {
                    for (var i in records.data) {
                        var dt = {};
                        dt.id = records.data[i].id;
                        dt.name = records.data[i].name;
                        dt.atime = records.data[i].atime;
                        dt.pname = records.data[i].pname;
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
                mDataProp: 'name'
            }, {
                mDataProp: 'atime'
            }, {
                mDataProp: 'pname'
            }, {
                mDataProp: null
            }],
            columndefs: [{
                targets: 4,
                render: function (data, type, row) {
                    var ret = '<a class="btn btn-link btnoper" onclick="viewthlz(\''
                        + row.id
                        + '\')">查看</a>';
                    if (hasPerm('MAN_THLZ')) {
                        ret += '<a class="btn btn-link btnoper" onclick="updatethlzload(\''
                            + row.id
                            + '\')">修改</a>'
                            + '<a class="btn btn-link btnoper" onclick="delthlzload(\''
                            + row.id + '\',\'' + row.name
                            + '\')">删除</a>';
                    }
                    return ret;
                }
            }],
            fncreatedrow: function (nRow, aData, iDataIndex) {
                var start = parseInt($('#thlztable').attr('start'));
                $("td:eq(0)", nRow).html(
                    '<input type="checkbox" name="branchcheckbox" cbid=' + aData.id + ' cbname="' + aData.name
                    + '" id="cb' + (start + iDataIndex)
                    + '" value="'
                    + (start + iDataIndex) + '">&nbsp;'
                    + (start + iDataIndex))
            }
        });
}

function newthlzload() {
    $('#modalnew').modal('show');
}

function addargument() {
    var content = $("#newtextarea").val();
    if (content.length > 512) {
        alert("输入审查意见过长！");
        return false;
    }
    $("#addthlzform").validateForm(function () {
        $("#addthlzform").autoajaxfileuploading('channelmanage/addargument', {
                loginid: $("#userid").val()
            }, function (result, data) {
                if (ifResultOK(result)) {
                    $("#modalnew").modal('hide');
                    loadthlz();
                } else {
                    alert(getResultDesc(result));
                }
            }
        );
    });
}

function viewthlz(id) {
    $.ajax({
        url: 'channelmanage/queryargumentbyid',
        type: 'post',
        data: {
            'loginid': $("#userid").val(),
            'id': id
        },
        success: function (data) {
            $("#modalviewbody").load('page/hangzheng/thlz-view.jsp #container');
            $("#modalview").modal('show');
        }
    });
}

function updatethlzload(id) {
    //去除多余附件
    $("#updatethlzform").find("[ftype*=originfile]").each(function () {
        $(this).empty();
    });
    $("#updatethlzform").find("[id*=row]").each(function () {
        $(this).empty();
    });
    ajax('channelmanage/queryargumentbyidjson', {
        loginid: $("#userid").val(),
        id: id
    }, function (result) {
        if (ifResultOK(result)) {
            $('#modalupdate').validateFormReset();
            var thlz = result.obj;
            if (thlz != null) {
                var id = thlz.id;
                var name = thlz.name;
                var pname = thlz.pname;
                var title = thlz.title;
                var hdao = thlz.hdao;
                var atime = getTimeFmt(getTimeFromStr(thlz.atime, 'yyyy-MM-dd HH:mm:ss'));
                $("#divupdateid").empty();
                $("#divupdateid").addhidden({
                    defaultval: id,
                    autoajax: {
                        name: 'argument.id'
                    }
                });
                $("#divupdatename").addinputtext({
                    id: 'updatename',
                    defaultval: name
                });
                $("#divupdatepname").addinputtext({
                    id: 'updatepname',
                    defaultval: pname
                });
                $("#divupdateatime").addtime({
                    id: 'updateatime',
                    defaultval: atime
                });
                $("#divupdatetitle").addtextarea({
                    id: 'updatetitle',
                    defaultval: title
                });
                $("#updatehdaodiv").addselect({
                    id: 'updatehdaosel',
                    defaultval: hdao
                });
            }
            var fjlist = getResultMap(result).fjlist;
            for (var index in fjlist) {
                var fjid = fjlist[index].id;
                var fjname = fjlist[index].resname;
                var fjsize = fjlist[index].ressize;
                var rantoken = rand(1, 99999999);
                var row = $('<div  ftype="originfile" id="trfileid' + fjid
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
                $("#updatethlzform").append(row);
            }
            $('#modalupdate').modal('show');
        } else {
            alert(getResultDesc(result));
        }
    });
}

function updatethlz() {
    var content = $("#updatetitle").val();
    if (content.length > 512) {
        alert("输入审查意见过长！");
        return false;
    }
    var url = "channelmanage/updateargument";
    var param = {
        loginid: $("#userid").val()
    };
    for (var index in deletefileids) {
        var id = deletefileids[index];
        param["delfileids[" + index + "]"] = id;
    }
    ;
    $("#modalupdate").validateForm(function () {
        $("#updatethlzform").autoajaxfileuploading(
            url,
            param,
            function (result) {
                if (ifResultOK(result)) {
                    $("#modalupdate").modal('hide');
                    deletefileids.length = 0;
                    loadthlz();
                } else {
                    alert(getResultDesc(result));
                }
            });
    });
}

function delthlzload(id, name) {
    var delids = new Array();
    var delnames = new Array();

    delids.push(id);
    delnames.push(name);

    if (delids.length <= 0) {
        alert('请先选择要删除的通航论证！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldel").attr('delids', delids.join(","));
    $("#modaldel").modal('show');
}

function delthlzsload() {
    var delids = new Array();
    var delnames = new Array();
    $("[name='branchcheckbox']").each(function () {
        if ($(this).prop("checked") == true) {
            var id = $(this).attr('cbid');
            delids.push(id);
            var name = $(this).attr('cbname');
            delnames.push(name);
        }
    });
    if (delids.length <= 0) {
        alert('请先选择要删除的通航论证！');
        return false;
    }
    $("#lbmodaldelnames").text(delnames.join(","));
    $("#modaldel").attr('delids', delids.join(","));
    $("#modaldel").modal('show');
}

function delthlz(ids) {
    var data = {
        loginid: $("#userid").val()
    };
    for (var index in ids) {
        var id = ids[index];
        data["ids[" + index + "]"] = id;
    }
    ajax('channelmanage/delargument', data, function (result) {
        if (ifResultOK(result)) {
            $("#modaldel").modal('hide');
            loadthlz();
        } else {
            alert(getResultDesc(result));
        }
    });
}

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
    $("#imgviewbody").empty();
    $("#imgviewbody").append(
        '<img style="width:100%;height:auto;" src="channelmanage/downloadchzfj?loginid='
        + $("#userid").val() + '&id=' + id + '">');
    $("#modalpicview").modal('show');
}