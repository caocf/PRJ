var pageusercnt = 10;
$(document).ready(function () {
    commoninit("用户管理");

    resize();
    $(window).resize(function (event) {
        resize();
    });

    var szshiju = parseInt($("#szshiju").val());
    var szchuju = parseInt($("#szchuju").val());
    var szju = parseInt($("#szju").val());
    if (hasPerm('VIEW_SHENUSER') || hasPerm('MAN_SHENUSER')) {
        loadleftdpt(-1);
    }
    else if ((hasPerm('VIEW_SHIUSER') || hasPerm('MAN_SHIUSER')) && szshiju != -1) {
        loadleftdpt(szshiju);
    }
    else if ((hasPerm('VIEW_CHUUSER') || hasPerm('MAN_CHUUSER')) && szchuju != -1) {
        loadleftdpt(szchuju);
    } else if (hasPerm('VIEW_SELFDPT') || hasPerm('MAN_SELFUSER')) {
        loadleftdpt(szju, true);
    }

    loaduserlist(-1);

    $("#btnSearch").bind('click', function () {
        var content = $("#inputContent").val();
        var dpt;
        var dptflag = 0;
        if (hasPerm('VIEW_SHENUSER')) {
            dpt = 1;
            dptflag = 1;
        }
        if (hasPerm('VIEW_SHIUSER')) {
            dpt = $("#szshiju").val();
            dptflag = 1;
        }
        if (hasPerm('VIEW_CHUUSER')) {
            dpt = $("#szchuju").val();
            dptflag = 1;
        }
        if (hasPerm('VIEW_SELFDPT')) {
            dpt = $("#szju").val();
        }
        if (content == null || content == "") {
            loaduserlist(currseldpt, null);
            return;
        }
        loaduserlist(dpt, content, dptflag);
    });

    $("#inputContent").bind('blur', function () {
        $("#divSearch").removeClass('has-error');
    });

    $("#btnAddUser").bind('click', function () {
        addUser();
    });

    $("#btnEditUser").bind('click', function () {
        editUser();
    });

    $("#AddUser").on('show.bs.modal', function () {
        hideAllErr();
        $("#inputUsername").focus();
        $("#pTitle").html("请选择职务");
        $("#pSeldpt").html("请选择组织机构");
        $("#btnTitle").attr('seltitleid', '-1');
        $("#btnSeldpt").attr('seldptid', '-1');
        $("#inputUsername").val("");
        $("#inputPassword").val("");
        $("#inputName").val("");
        $("#inputPhone").val("");
        $("#inputEmail").val("");
        $("#inputLawid").val("");
        loadTitle();
        loadjstatus1();
        var seldpt = $('#divleftdpt').etreegetselectednode();

        if (seldpt == null) {
            alert("请选择要添加到哪个部门？");
            return false;
        }

        addseldptdefaultroles(seldpt.id);
    });

    $("#EditUser").on('show.bs.modal', function () {
        hideAllEditErr();
        $("#inputUsernameEdit").val("");
        $("#inputPasswordEdit").val("");
        $("#inputNameEdit").val("");
        $("#inputPhoneEdit").val("");
        $("#inputEmailEdit").val("");
        $("#inputLawidEdit").val("");
        $("#inputUsernameEdit").focus();
        loadTitleEdit();
        loadjstatus1Edit();
        $("#divSeldptEdit").addseldpt({id: 'seldptupdate'});

        var seldpt = $('#divleftdpt').etreegetselectednode();
        if (seldpt == null) {
            alert("请选择要修改的用户属于哪个部门？");
            return false;
        }
        addseldptdefaultroles(seldpt.id);
    });

    $("#cbselall").bind('click', function () {
        selalluser();
    });
    $("#btnDelusers").bind('click', function () {
        delMultiUsers();
    });

    $("#btnDelUser").bind('click', function () {
        delUsers();
    });
});

function addseldptdefaultroles(dptid) {
    var data = {};
    ajax("dpt/queryDptDefaultRoles", {id: dptid}, function (result) {
            if (ifResultOK(result)) {
                var groups = getResultObj(result);
                for (var i = 0; i < groups.length; i++) {
                    var group = groups[i];
                    var id = group.id;
                    var name = group.name;

                    data[id] = name;
                }

                $("#divUserRole").empty();
                $("#divUserRole").addmultiselect({
                    id: "seluserrole",
                    data: data
                });
                $("#divUpdateUserRole").empty();
                $("#divUpdateUserRole").addmultiselect({
                    id: "selupdateuserrole",
                    data: data
                });
            }
        }
    );
}

function resize() {
    var clientheight = window.innerHeight;
    $("#divleftdpt").css('height', (clientheight - 52 - 80) + 'px');
    $("#divrightrow").css('height', (clientheight - 53 - 80 - 50) + 'px');
    var clientheight = window.innerHeight;
    pageusercnt = parseInt((clientheight - 53 - 80 - 100 - 50) / 50);
}

function loadleftdpt(pid, onelayer) {
    if (onelayer == null)
        onelayer = false;
    $("#divleftdpt").etree({
        id: -1,
        rootid: pid,
        isroot: 1,
        onelayer: onelayer,
        childrendatafn: leftdptdatafn,
        containerdomid: $("#divleftdpt").attr('id')
    });
}

function leftcreatedptfn(node, container) {
    container.attr('response-click', true);
    container.attr('response-select', true);

    $div = $('<div response-click="true" response-expand="true"  style="padding-top:13px;width:17px;float:left;height:40px;"/>');

    if (node.subcnt > 0) {
        if (node.expanded != null && node.expanded == '1' || node.expanded == true)
            $div
                .append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-click="true" response-expand=true style="color:black;" response-expand=true class=\"icon-caret-down\"></span>'));
        else
            $div
                .append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-click="true" response-expand=true style="color:black;" response-expand=true class=\"icon-caret-right\"></span>'));
    }
    container.append($div);
    container
        .append($('<a response-click="true" respose-select=true class="nomargin nopadding" style="color:inherit;width:80%;overflow:hidden;">'
            + '<label response-click=true response-select=true style="color:inherit;margin-left:5px;">'
            + node.name + '</label></a>'));
}

function leftdptdatafn(pnode, fncallback) {

    var onelayer = false;
    if (pnode.onelayer != null && pnode.onelayer == true)
        onelayer = true;

    var data = {
        'loginid': $("#userid").val()
    };
    if (pnode.isroot) {
        data['id'] = pnode.rootid;
        data['isroot'] = 1;
    } else {
        data['id'] = pnode.id;
    }
    ajax("dpt/queryalldpt", data, function (data) {
        if (ifResultOK(data)) {
            var nodes = new Array();
            var records = getResultRecords(data);
            if (records) {
                for (var i = 0; i < records.data.length; i++) {
                    var nd = records.data[i];
                    var name = nd.name;
                    var subcnt = nd.subcnt;
                    var id = nd.id;
                    var xzqh = nd.xzqh;
                    var type = nd.type;
                    var defaultroles = nd.defaultroles;

                    nodes.push({
                        id: id,
                        type: type,
                        pid: pnode.id,
                        defaultroles: defaultroles,
                        name: name,
                        xzqh: xzqh,
                        subcnt: onelayer ? 0 : subcnt,
                        clickfn: function (node, event) {
                            if ($(event.target).is('span')) {
                                event.stopPropagation();
                            } else {
                                loaduserlist(node.id);
                            }
                            if (node.expanded == 1) {
                                $("#" + node.containerdomid + "_span" + node.id).removeClass('icon-caret-right');
                                $("#" + node.containerdomid + "_span" + node.id).addClass('icon-caret-down');
                            } else {
                                $("#" + node.containerdomid + "_span" + node.id).addClass('icon-caret-right');
                                $("#" + node.containerdomid + "_span" + node.id).removeClass('icon-caret-down');
                            }
                        },
                        childrendatafn: onelayer ? null : leftdptdatafn,
                        createnodefn: leftcreatedptfn,
                        containerdomid: pnode.containerdomid,
                        inited: type == 0 || type == 1 ? true : false,
                        expanded: type == 0 || type == 1 ? true : false
                    });
                }
            }

            fncallback(nodes);
        }
    })
}

var start = 0;
function retrieveData(sSource, aoData, fnCallback) {

    /**
     * 清除全选状态
     */
    $("#cbselall").prop('checked', false);

    start = getDateTableParam(aoData, "iDisplayStart") + 1;
    $.ajax({
        "type": "post",
        "url": sSource,
        "dataType": "json",
        "data": {
            'loginid': $("#userid").val(),
            'dptflag': getDateTableParam(aoData, 'dptflag'),
            'department': getDateTableParam(aoData, 'dptid'),
            'content': getDateTableParam(aoData, 'content'),
            'sEcho': getDateTableParam(aoData, "sEcho"),
            'rows': getDateTableRows(aoData),
            'page': getDateTablePage(aoData)
        }, // 以json格式传递
        "success": function (resp) {
            if (ifResultOK(resp)) {
                var users = getResultObj(resp);
                var list = new Array();
                for (var i = 0; i < users.length; i++) {
                    var user = users[i];
                    var id = user.arruser[0].id;
                    var username = user.arruser[0].username;
                    var name = user.arruser[0].name;
                    var title = user.arruser[2].attrdesc;
                    var dpt = user.strdpt;
                    var ustatus = user.arruser[0].ustatus;
                    var jstatus = user.arruser[3].type + "/"
                        + user.arruser[3].typedesc;
                    list.push({
                        id: id,
                        username: username,
                        name: name,
                        title: title,
                        dpt: dpt,
                        ustatus: ustatus,
                        jstatus: jstatus
                    });
                }
                var data = toDateTableJsonResult(list, resp.map.total,
                    resp.map.total, resp.map.sEcho);
                fnCallback(data); // 服务器端返回的对象的returnObject部分是要求的格式
            } else {

            }
        }
    });
}
var currseldpt = -1;
function loaduserlist(dptid, content, dptflag) {
    if (dptflag == null)
        dptflag = 0;
    currseldpt = dptid;
    if (content == null || content == "")
        content = "";
    $('#tableUser')
        .dataTable(
        {
            "retrieve": false,
            "destroy": true,
            "bAutoWidth": false,
            "bSort": false,
            "bDeferRender": true,
            "bProcessing": true, // 加载数据时显示正在加载信息
            "bServerSide": true, // 指定从服务器端获取数据
            "bFilter": false, // 不使用过滤功能
            "bSort": false,
            "bLengthChange": false, // 用户是否可改变每页显示数量
            "iDisplayLength": pageusercnt, // 每页显示10条数据
            "sAjaxSource": "user/queryuser",// 获取数据的url
            "fnServerData": retrieveData, // 获取数据的处理函数
            "fnServerParams": function (aoData) {
                aoData.push({
                    'name': 'dptid',
                    'value': dptid
                });
                aoData.push({
                    'name': 'content',
                    'value': content
                });
                aoData.push({
                    'name': 'dptflag',
                    'value': dptflag
                });
            },
            "sPaginationType": "full_numbers", // 翻页界面类型
            "oLanguage": { // 汉化
                "sProcessing": "",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "暂无用户数据",
                "sLoadingRecords": "",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            "aoColumns": [{
                "mDataProp": null
            }, {
                "mDataProp": 'username'
            }, {
                "mDataProp": 'dpt'
            }, {
                "mDataProp": 'title'
            }, {
                "mDataProp": 'ustatus'
            }, {
                "mDataProp": null
            }],
            "columnDefs": [
                {
                    "render": function (data, type, row) {
                        var loginid = parseInt($("#userid").val());
                        if (row.id == loginid) {
                            var ret = '<a class="btn btn-link btnoper" data-toggle="modal" data-target="#ViewUser" style="font-size:14px;" onclick="viewUser('
                                + row.id
                                + ')">查看</a>';
                            if (hasPerm('MAN_SHENUSER') || hasPerm('MAN_SHIUSER') || hasPerm('MAN_CHUUSER') || hasPerm('MAN_SELFUSER')) {
                                ret += '<a class="btn btn-link btnoper disabled" data-toggle="modal" data-target="#EditUser"  style="font-size:14px;" onclick="vieweditUser('
                                    + row.id
                                    + ')">修改</a>'
                                    + '<a class="btn btn-link btnoper disabled"  style="font-size:14px;" onclick="delSingleUser('
                                    + row.id
                                    + ',\''
                                    + row.username
                                    + '\')">删除</a>';
                            }
                        } else {
                            var ret = '<a class="btn btn-link btnoper" data-toggle="modal" data-target="#ViewUser" style="font-size:14px;" onclick="viewUser('
                                + row.id
                                + ')">查看</a>';
                            if (hasPerm('MAN_SHENUSER') || hasPerm('MAN_SHIUSER') || hasPerm('MAN_CHUUSER') || hasPerm('MAN_SELFUSER')) {
                                ret += '<a class="btn btn-link btnoper" data-toggle="modal" data-target="#EditUser" style="font-size:14px;" onclick="vieweditUser('
                                    + row.id
                                    + ')">修改</a>'
                                    + '<a class="btn btn-link btnoper" style="font-size:14px;" onclick="delSingleUser('
                                    + row.id
                                    + ',\''
                                    + row.username
                                    + '\')">删除</a>';
                            }
                        }
                        if (hasPerm('MAN_SHENUSER') || hasPerm('MAN_SHIUSER') || hasPerm('MAN_CHUUSER') || hasPerm('MAN_SELFUSER')) {
                            if (row.id == loginid) {
                                if (row.ustatus == USTATUS_ENABLE) {
                                    ret += "<a class='btn btn-link btnoper disabled' style='font-size:14px;' onclick='disableSingleUser("
                                        + row.id + ")'>禁用</a>";
                                } else if (row.ustatus == USTATUS_DISABLE) {
                                    ret += "<a class='btn btn-link btnoper disabled' style='font-size:14px;' onclick='enableSingleUser("
                                        + row.id + ")'>激活</a>";
                                }
                            } else {
                                if (row.ustatus == USTATUS_ENABLE) {
                                    ret += "<a class='btn btn-link btnoper' style='font-size:14px;' onclick='disableSingleUser("
                                        + row.id + ")'>禁用</a>";
                                } else if (row.ustatus == USTATUS_DISABLE) {
                                    ret += "<a class='btn btn-link btnoper' style='font-size:14px;' onclick='enableSingleUser("
                                        + row.id + ")'>激活</a>";
                                }
                            }
                        }
                        return ret;
                    },
                    "targets": 5
                },
                {
                    "render": function (data, type, row) {
                        if (row.ustatus == USTATUS_ENABLE) {
                            return "<label class='ustatuslbgreen' style='font-size:14px;'>正常</label>";
                        } else if (row.ustatus == USTATUS_DISABLE) {
                            return "<label class='ustatuslbred' style='font-size:14px;'>已禁用</label>";
                        } else {
                            return "<label style='font-size:14px;'>已删除</label>";
                        }
                    },
                    "targets": 4
                }],
            "fnCreatedRow": function (nRow, aData, iDataIndex) {
                $("td:eq(0)", nRow).html(
                    "<input name='usercheck' type='checkbox' username='"
                    + aData.username + "' userid='"
                    + aData.id + "' id='cb"
                    + (start + iDataIndex) + "'>&nbsp;"
                    + (start + iDataIndex));
            }
        });

}

function loadjstatus1() {
    $("#jstatusul1").empty();
    $
        .ajax({
            url: 'queryalljstatus',
            type: 'post',
            dataType: 'json',
            data: {
                'loginid': $("#userid").val()
            },
            success: function (data) {
                if (ifResultOK(data)) {
                    var records = getResultRecords(data);
                    if (records) {
                        var js1 = new Array();
                        var inited = false;
                        for (var i = 0; i < records.data.length; i++) {
                            var jstatus = records.data[i];
                            var jstatus1 = jstatus.type;
                            var found = false;
                            for (var j = 0; j < js1.length; j++) {
                                if (js1[j] == jstatus1) {
                                    found = true;
                                    break;
                                }
                            }
                            if (found == false) {
                                if (inited == false) {
                                    $("#pJstatus1").html(jstatus1);
                                    loadjstatus2(jstatus1);
                                    inited = true;
                                }
                                js1.push(jstatus1);
                                var newli = $("<li></li>");
                                newli
                                    .append($('<a onclick=\"loadjstatus2(\''
                                        + jstatus1
                                        + '\')\">'
                                        + jstatus1 + '</a>'));
                                $("#jstatusul1").append(newli);
                            }
                        }
                    }
                } else {
                }
            },
            error: function (data) {

            }
        });
}

function loadjstatus2(jstatus1) {
    $("#pJstatus1").html(jstatus1);
    $("#jstatusul2").empty();
    $.ajax({
        url: 'queryalljstatus',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val()
        },
        success: function (data) {
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                if (records) {
                    var inited = false;
                    for (var i = 0; i < records.data.length; i++) {
                        var jstatus = records.data[i];
                        var jstatusid = jstatus.id;
                        var jstatus1 = jstatus.type;
                        var jstatus2 = jstatus.typedesc;
                        if (jstatus1 == $("#pJstatus1").html()) {
                            if (inited == false) {
                                $("#pJstatus2").html(jstatus2);
                                inited = true;
                                $("#btnJstatus2").attr('seljstatusid',
                                    jstatusid);
                            }
                            var newli = $("<li></li>");
                            newli.append($('<a onclick=\"seljstatus('
                                + jstatusid + ',\'' + jstatus2 + '\')\">'
                                + jstatus2 + '</a>'));
                            $("#jstatusul2").append(newli);
                        }
                    }
                }
            } else {
            }
        },
        error: function (data) {

        }
    });
}

function loadjstatus1Edit() {
    $("#jstatusulEdit1").empty();
    $
        .ajax({
            url: 'queryalljstatus',
            type: 'post',
            dataType: 'json',
            data: {
                'loginid': $("#userid").val()
            },
            success: function (data) {
                if (ifResultOK(data)) {
                    var records = getResultRecords(data);
                    if (records) {
                        var js1 = new Array();
                        var inited = false;
                        for (var i = 0; i < records.data.length; i++) {
                            var jstatus = records.data[i];
                            var jstatus1 = jstatus.type;
                            var found = false;
                            for (var j = 0; j < js1.length; j++) {
                                if (js1[j] == jstatus1) {
                                    found = true;
                                    break;
                                }
                            }
                            if (found == false) {
                                if (inited == false) {
                                    $("#pJstatusEdit1").html(jstatus1);
                                    loadjstatus2Edit(jstatus1);
                                    inited = true;
                                }
                                js1.push(jstatus1);
                                var newli = $("<li></li>");
                                newli
                                    .append($('<a onclick=\"loadjstatus2Edit(\''
                                        + jstatus1
                                        + '\')\">'
                                        + jstatus1 + '</a>'));
                                $("#jstatusulEdit1").append(newli);
                            }
                        }
                    }
                } else {
                }
            },
            error: function (data) {

            }
        });
}

function loadjstatus2Edit(jstatus1) {
    $("#pJstatusEdit1").html(jstatus1);
    $("#jstatusulEdit2").empty();
    $.ajax({
        url: 'queryalljstatus',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val()
        },
        success: function (data) {
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                if (records) {
                    var inited = false;
                    for (var i = 0; i < records.data.length; i++) {
                        var jstatus = records.data[i];
                        var jstatusid = jstatus.id;
                        var jstatus1 = jstatus.type;
                        var jstatus2 = jstatus.typedesc;
                        if (jstatus1 == $("#pJstatusEdit1").html()) {
                            if (inited == false) {
                                $("#pJstatusEdit2").html(jstatus2);
                                inited = true;
                                $("#btnJstatusEdit2").attr('seljstatusid',
                                    jstatusid);
                            }
                            var newli = $("<li></li>");
                            newli.append($('<a onclick=\"seljstatusEdit('
                                + jstatusid + ',\'' + jstatus2 + '\')\">'
                                + jstatus2 + '</a>'));
                            $("#jstatusulEdit2").append(newli);
                        }
                    }
                }
            }
        },
        error: function (data) {

        }
    });
}

function seljstatus(jstatusid, jstatus2) {
    $("#pJstatus2").html(jstatus2);
    $("#btnJstatus2").attr('seljstatusid', jstatusid);
}

function seljstatusEdit(jstatusid, jstatus2) {
    $("#pJstatusEdit2").html(jstatus2);
    $("#btnJstatusEdit2").attr('seljstatusid', jstatusid);
}

function loadTitle() {
    $("#ulTitle").empty();
    $.ajax({
        url: 'queryalltitle',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val()
        },
        success: function (data) {
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                if (records) {
                    for (var i = 0; i < records.data.length; i++) {
                        var title = records.data[i];
                        var titlename = title.attrdesc;
                        var titleid = title.id;
                        var newli = $("<li></li>");
                        newli.append($('<a onclick=\"$(\'#pTitle\').html(\''
                            + titlename
                            + '\');$(\'#btnTitle\').attr(\'seltitleid\','
                            + titleid + ');\">' + titlename + '</a>'));
                        $("#ulTitle").append(newli);
                    }
                }
            }
        }
    });
}

function loadTitleEdit() {
    $("#ulTitleEdit").empty();
    $
        .ajax({
            url: 'queryalltitle',
            type: 'post',
            dataType: 'json',
            data: {
                'loginid': $("#userid").val()
            },
            success: function (data) {
                if (ifResultOK(data)) {
                    var records = getResultRecords(data);
                    if (records) {
                        for (var i = 0; i < records.data.length; i++) {
                            var title = records.data[i];
                            var titlename = title.attrdesc;
                            var titleid = title.id;
                            var newli = $("<li></li>");
                            newli
                                .append($('<a onclick=\"$(\'#pTitleEdit\').html(\''
                                    + titlename
                                    + '\');$(\'#btnTitleEdit\').attr(\'seltitleid\','
                                    + titleid
                                    + ');\">'
                                    + titlename
                                    + '</a>'));
                            $("#ulTitleEdit").append(newli);
                        }
                    }
                }
            }
        });
}

function addUser() {
    hideAllErr();
    var seldpt = $('#divleftdpt').etreegetselectednode();

    if (seldpt == null) {
        alert("请选择要添加到哪个部门？");
        return;
    }

    var username = $("#inputUsername").val();
    var password = $("#inputPassword").val();
    var name = $("#inputName").val();
    var seltitleid = $("#btnTitle").attr("seltitleid");
    var seldptid = seldpt.id;
    var seljstatusid = $("#btnJstatus2").attr('seljstatusid');
    var phone = $("#inputPhone").val();
    var email = $("#inputEmail").val();
    var lawid = $("#inputLawid").val();

    if (username == null || username == "") {
        $("#inputUsername").focus();
        $("#divUsername").addClass('has-error');
        $("#pUsernameErr").removeClass('hide');
        $("#pUsernameErr").addClass('show');
        $("#pUsernameErr").html("请输入用户名");
        return;
    }

    if (username.length > 32) {
        $("#inputUsername").focus();
        $("#divUsername").addClass('has-error');
        $("#pUsernameErr").removeClass('hide');
        $("#pUsernameErr").addClass('show');
        $("#pUsernameErr").html("用户名输入过长");
        return;
    }

    if (password == null || password == "") {
        $("#inputPassword").focus();
        $("#divPassword").addClass('has-error');
        $("#pPasswordErr").removeClass('hide');
        $("#pPasswordErr").addClass('show');
        $("#pPasswordErr").html("请输入密码");
        return;
    }

    if (name == null || name == "") {
        $("#inputName").focus();
        $("#divName").addClass('has-error');
        $("#pNameErr").removeClass('hide');
        $("#pNameErr").addClass('show');
        $("#pNameErr").html("请输入姓名");
        return;
    }

    if (seltitleid == null || seltitleid == '-1' || seltitleid == -1) {
        $("#pTitleErr").removeClass('hide');
        $("#pTitleErr").addClass('show');
        $("#pTitleErr").html("请选择职务");
        return;
    }

    if (seldptid == null || seldptid == '-1' || seldptid == -1) {
        $("#pSeldptErr").removeClass('hide');
        $("#pSeldptErr").addClass('show');
        $("#pSeldptErr").html("请选择所属组织机构");
        return;
    }

    if (seljstatusid == null || seljstatusid == '-1' || seljstatusid == -1) {
        $("#pSeljstatusErr").removeClass('hide');
        $("#pSeljstatusErr").addClass('show');
        $("#pSeljstatusErr").html("请选择在职状态");
        return;
    }
    var roles = null;

    roles = $("#seluserrole").attr('selitem');

    $.ajax({
        url: 'userinfo/adduserinfo',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val(),
            'username': username,
            'password': password,
            'name': name,
            'tel': phone,
            'email': email,
            'title': seltitleid,
            'jstatus': seljstatusid,
            'lawid': lawid,
            'roles': roles == null ? '' : roles,
            'ustatus': USTATUS_ENABLE,
            'department': seldptid
        },
        success: function (data) {
            if (ifResultOK(data)) {
                $("#AddUser").modal('hide');

                loaduserlist(currseldpt);
            } else {
                if (getResultCode(data) == RESULT_USERINFO_USEREXISTED) {
                    $("#inputUsername").focus();
                    $("#divUsername").addClass('has-error');
                    $("#pUsernameErr").removeClass('hide');
                    $("#pUsernameErr").addClass('show');
                    $("#pUsernameErr").html("用户名已存在");
                } else {
                    alert(getResultDesc(data));
                }
            }
        },
        error: function (data) {
            alert(getResultDesc(data));
        }
    });
}

function hideAllErr() {
    $("#divUsername").removeClass('has-error');
    $("#divPassword").removeClass('has-error');
    $("#divName").removeClass('has-error');

    $("#pUsernameErr").removeClass('show');
    $("#pUsernameErr").addClass('hide');

    $("#pPasswordErr").removeClass('show');
    $("#pPasswordErr").addClass('hide');

    $("#pNameErr").removeClass('show');
    $("#pNameErr").addClass('hide');

    $("#pTitleErr").removeClass('show');
    $("#pTitleErr").addClass('hide');

    $("#pSeldptErr").removeClass('show');
    $("#pSeldptErr").addClass('hide');

    $("#pSeljstatusErr").removeClass('show');
    $("#pSeljstatusErr").addClass('hide');
}

function hideAllEditErr() {
    $("#divUsernameEdit").removeClass('has-error');
    $("#divPasswordEdit").removeClass('has-error');
    $("#divNameEdit").removeClass('has-error');

    $("#pUsernameEditErr").removeClass('show');
    $("#pUsernameEditErr").addClass('hide');

    $("#pPasswordEditErr").removeClass('show');
    $("#pPasswordEditErr").addClass('hide');

    $("#pNameEditErr").removeClass('show');
    $("#pNameEditErr").addClass('hide');

    $("#pTitleEditErr").removeClass('show');
    $("#pTitleEditErr").addClass('hide');

    $("#pSeldptEditErr").removeClass('show');
    $("#pSeldptEditErr").addClass('hide');

    $("#pSeljstatusEditErr").removeClass('show');
    $("#pSeljstatusEditErr").addClass('hide');
}

function disableSingleUser(userid) {
    $.ajax({
        url: 'user/disableuser',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val(),
            'id': userid
        },
        success: function (data) {
            if (ifResultOK(data)) {
                loaduserlist(currseldpt, "");
            } else {
                alert(getResultDesc(data));
            }
        },
        error: function (data) {
            alert(getResultDesc(data));
        }
    });
}

function enableSingleUser(userid) {
    $.ajax({
        url: 'user/enableuser',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val(),
            'id': userid
        },
        success: function (data) {
            if (ifResultOK(data)) {
                loaduserlist(currseldpt, "");
            } else {
                alert(getResultDesc(data));
            }
        },
        error: function (data) {
            alert(getResultDesc(data));
        }
    });
}

function delSingleUser(userid, username) {
    var userids = new Array();
    userids.push(userid);
    if (userids.length <= 0) {
        alert('请先选择要删除的用户！');
        return;
    }
    $('#lbdelusers').html(username);
    $('#modaldelusers').modal('show');
    $('#modaldelusers').attr('deluserids', userids.join(','));
}

function delMultiUsers() {
    var userids = new Array();
    var usernames = new Array();
    $("[name='usercheck']").each(function () {
        if ($(this).prop("checked") == true) {
            var id = $(this).attr('userid');
            userids.push(id);

            var username = $(this).attr('username');
            usernames.push(username);
        }
    });
    if (userids.length <= 0) {
        alert('请先选择要删除的用户！');
        return;
    }
    $('#lbdelusers').html(usernames.join(','));
    $('#modaldelusers').modal('show');
    $('#modaldelusers').attr('deluserids', userids.join(','));
}

function delUsers() {
    var strids = $('#modaldelusers').attr('deluserids');
    $.ajax({
        url: 'user/deluser',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val(),
            'strids': strids
        },
        success: function (data) {
            $('#modaldelusers').modal('hide');
            if (ifResultOK(data)) {
                loaduserlist(currseldpt, "");
            } else {
                alert(getResultDesc(data));
            }
        },
        error: function (data) {
            alert(getResultDesc(data));
        }
    });
}

function viewUser(userid) {
    $.ajax({
        url: 'userinfo/queryuserinfo',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val(),
            'id': userid
        },
        success: function (data) {
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                var roles = getResultMap(data).roles;
                if (records) {
                    var username = records.data[0][0].username;
                    var name = records.data[0][0].name;
                    var title = records.data[0][1].attrdesc;
                    var dpt = getResultMap(data).dpts;
                    var jstatus = records.data[0][2].type + "/"
                        + records.data[0][2].typedesc;
                    var phone = records.data[0][0].tel;
                    var email = records.data[0][0].email;
                    var lawid = records.data[0][0].lawid;

                    $("#pUsername").html(username);
                    $("#pName").html(name);
                    $("#pTitleView").html(title);
                    $("#pDpt").html(dpt);
                    $("#pJstatus").html(jstatus);
                    $("#pPhone").html(phone);
                    $("#pEmail").html(email);
                    $("#pLawid").html(lawid);

                    try {
                        var rolesstr = '';
                        for (var i = 0; i < roles.length; i++) {
                            var name = roles[i].name;
                            rolesstr += name;
                            if (i != roles.length - 1)
                                rolesstr += ",";
                        }
                        $("#divViewUserRole").text(rolesstr);
                    } catch (e) {
                    }
                }
            } else {
                alert(getResultDesc(data));
            }
        },
        error: function (data) {
            alert(getResultDesc(data));
        }
    });
}

function vieweditUser(userid) {
    $("#edituserid").val(userid);
    $.ajax({
        url: 'userinfo/queryuserinfo',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val(),
            'id': userid
        },
        success: function (data) {
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                var roles = getResultMap(data).roles;
                if (records) {
                    var username = records.data[0][0].username;
                    var password = records.data[0][0].password; // 密码是加密过的，所以这里不显示密码
                    var name = records.data[0][0].name;
                    var titlename = records.data[0][1].attrdesc;
                    var titleid = records.data[0][1].id;
                    var dptid = records.data[0][3].id;
                    ;
                    var dptname = records.data[0][3].name;
                    ;
                    var jstatus = records.data[0][2].type + "/"
                        + records.data[0][2].typedesc;
                    var phone = records.data[0][0].tel;
                    var email = records.data[0][0].email;
                    var lawid = records.data[0][0].lawid;

                    $("#inputUsernameEdit").val(username);
                    $("#inputNameEdit").val(name);
                    $("#inputPhoneEdit").val(phone);
                    $("#inputEmailEdit").val(email);
                    $("#inputLawidEdit").val(lawid);
                    $("#inputPasswordEdit").val("######");
                    $('#pTitleEdit').html(titlename);

                    $('#inputPasswordEdit').attr('ifchanged', '0');
                    $('#inputPasswordEdit').bind('change', function () {
                        $('#inputPasswordEdit').attr('ifchanged', '1');
                    });

                    $('#btnTitleEdit').attr('seltitleid', titleid);

                    $("#divSeldptEdit").addseldpt({id: 'seldptupdate', defaultval: dptid});

                    try {
                        var defaultroles = '';
                        for (var i = 0; i < roles.length; i++) {
                            defaultroles += roles[i].id;
                            if (i != roles.length - 1)
                                defaultroles += ',';
                        }

                        $("#divUpdateUserRole").addmultiselect({
                            id: "selupdateuserrole",
                            defaultval: defaultroles
                        })
                    } catch (e) {
                    }
                }
            } else {
                alert(getResultDesc(data));
            }
        },
        error: function (data) {
            alert(getResultDesc(data));
        }
    });
}

function editUser() {
    hideAllEditErr();

    var username = $("#inputUsernameEdit").val();
    var password = $("#inputPasswordEdit").val();
    var name = $("#inputNameEdit").val();
    var seltitleid = $("#btnTitleEdit").attr("seltitleid");
    var seljstatusid = $("#btnJstatusEdit2").attr('seljstatusid');
    var phone = $("#inputPhoneEdit").val();
    var email = $("#inputEmailEdit").val();
    var lawid = $("#inputLawidEdit").val();

    if (username == null || username == "") {
        $("#inputUsernameEdit").focus();
        $("#divUsernameEdit").addClass('has-error');
        $("#pUsernameEditErr").removeClass('hide');
        $("#pUsernameEditErr").addClass('show');
        $("#pUsernameEditErr").html("请输入用户名");
        return;
    }

    if (username.length > 32) {
        $("#inputUsernameEdit").focus();
        $("#divUsernameEdit").addClass('has-error');
        $("#pUsernameEditErr").removeClass('hide');
        $("#pUsernameEditErr").addClass('show');
        $("#pUsernameEditErr").html("用户名输入过长");
        return;
    }

    if ($('#inputPasswordEdit').attr('ifchanged') == '1') {
        if (password == null || password == "") {
            $("#inputPasswordEdit").focus();
            $("#divPasswordEdit").addClass('has-error');
            $("#pPasswordEditErr").removeClass('hide');
            $("#pPasswordEditErr").addClass('show');
            $("#pPasswordEditErr").html("请输入密码");
            return;
        }
    } else {
        password = '';
    }

    if (name == null || name == "") {
        $("#inputNameEdit").focus();
        $("#divNameEdit").addClass('has-error');
        $("#pNameEditErr").removeClass('hide');
        $("#pNameEditErr").addClass('show');
        $("#pNameEditErr").html("请输入姓名");
        return;
    }

    if (seltitleid == null || seltitleid == '-1' || seltitleid == -1) {
        $("#pTitleEditErr").removeClass('hide');
        $("#pTitleEditErr").addClass('show');
        $("#pTitleEditErr").html("请选择职务");
        return;
    }

    if (seljstatusid == null || seljstatusid == '-1' || seljstatusid == -1) {
        $("#pSeljstatusEditErr").removeClass('hide');
        $("#pSeljstatusEditErr").addClass('show');
        $("#pSeljstatusEditErr").html("请选择在职状态");
        return;
    }

    var roles = null;
    roles = $("#selupdateuserrole").attr('selitem');


    $.ajax({
        url: 'user/updateuser',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val(),
            'id': $("#edituserid").val(),
            'username': username,
            'password': password,
            'name': name,
            'tel': phone,
            'email': email,
            'title': seltitleid,
            'roles': roles == null ? '' : roles,
            'jstatus': seljstatusid,
            'lawid': lawid,
            'ustatus': USTATUS_ENABLE
        },
        success: function (data) {
            if (ifResultOK(data)) {
                $("#EditUser").modal('hide');
                loaduserlist(currseldpt);
            } else {
                if (getResultCode(data) == RESULT_USERINFO_USEREXISTED) {
                    $("#inputUsernameEdit").focus();
                    $("#divUsernameEdit").addClass('has-error');
                    $("#pUsernameEditErr").removeClass('hide');
                    $("#pUsernameEditErr").addClass('show');
                    $("#pUsernameEditErr").html("用户名已存在");
                } else {
                    alert(getResultDesc(data));
                }
            }
        },
        error: function (data) {
            alert(getResultDesc(data));
        }
    });
}

function selalluser() {
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
