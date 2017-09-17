var curxzqh=1;
$(document).ready(function () {
    commoninit("角色权限");

    resize();
    $(window).resize(function (event) {
        resize();
    });

    dialogpreshowinit();
    loadRole();
    checkBtnSaveRolePerms();
    loadrolexzqh();
});

function loadrolexzqh() {
    $("#divseladdxzqh").empty();
    var xzqhid = $("#xzqhid").val();
    if (xzqhid == null) {
        $("#divseladdxzqh").addselxzqh({id: "addxzqhid"});
    }
    else {
        $("#divseladdxzqh").addselbyxzqh({id: "addxzqhid", defaultval: xzqhid});
    }
}

function resize() {
    var clientheight = window.innerHeight;
    $("#divleftrole").css('height', (clientheight - 52 - 80 - 70) + 'px');
    $("#divrightrow").css('height', (clientheight - 52 - 90 + 42) + 'px');
    $("#divleftrgoup").css('height', (clientheight - 52 - 90 - 130 + 42) + 'px');
    $("#divrightperm").css('height', (clientheight - 52 - 90 - 130 + 42) + 'px');
    $("#divleftdpt").css('height', (clientheight - 52 - 90 - 130 + 42) + 'px');
    $("#divrightuser").css('height', (clientheight - 52 - 90 - 130 + 42) + 'px');
}

function dialogpreshowinit() {
    $("#addrole").on('show.bs.modal', function () {
        $("#addrolename").val("");
        $("#addrolename").validateTargetBind({notempty: {}});
    });

    $("#updaterole").on('show.bs.modal', function () {
        $("#addrolename").validateTargetBind({notempty: {}});
    });

    $('#userlist-tab[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        var role = $("#divleftrole").etreegetselectednode();
        if (role != null)
            loadroleuserlist(role.id);
    })
}

//保存角色对应的权限ID信息
var gl_currpermlist = new Array();
var gl_newpermlist = new Array();
var gl_delpermlist = new Array();

function findperm(list, permid) {
    for (var i = 0; i < list.length; i++) {
        var perm = list[i];
        if (perm == permid)
            return perm;
    }
    return null;
}

function clearperm(list) {
    list.length = 0;
}

function addperm(list, permid) {
    if (findperm(list, permid) != null) {
        delperm(list, permid);
    }
    list.push(permid);
}

function delperm(list, permid) {
    for (var i = 0; i < list.length; i++) {
        var perm = list[i];
        if (perm == permid) {
            list.splice(i, 1);
            return;
        }
    }
}

function loadRolePerms(roleid) {
    clearperm(gl_currpermlist);
    clearperm(gl_newpermlist);
    clearperm(gl_delpermlist);
    ajax('queryRolePerms', {roleid: roleid}, function (result) {
        if (ifResultOK(result)) {
            var perms = getResultObj(result);

            if (perms != null) {
                for (var i = 0; i < perms.length; i++) {
                    var perm = perms[i];
                    addperm(gl_currpermlist, perm.id);
                }
            }

            checkBtnSaveRolePerms();
        } else {
            alert(getResultDesc(result));
        }
    });
}

function loadRoleDatafn(pnode, fncallback) {
    ajax("queryRoles", {}, function (result) {
            if (ifResultOK(result)) {
                var groups = getResultObj(result);
                var dptshixzqh = getResultMap(result).dptshixzqh;
                $("#xzqhid").val(dptshixzqh);
                var nodes = new Array();

                for (var i = 0; i < groups.length; i++) {
                    var group = groups[i];
                    nodes.push({
                        id: group.id,
                        name: group.name,
                        xzqh: group.xzqh,
                        createnodefn: createRolefn,
                        clicked: i == 0 ? true : false,
                        enterfn: function (node) {
                            $('#delspan' + node.id).removeClass('hide');
                            $('#editspan' + node.id).removeClass('hide');
                        },
                        leavefn: function (node) {
                            $('#delspan' + node.id).addClass('hide');
                            $('#editspan' + node.id).addClass('hide');
                        },
                        clickfn: function (node, event) {
                            curxzqh=node.xzqh;
                            loadRolePerms(node.id);
                            loadRoleUsers(node.id);
                            loadPermGroup();
                            loadleftdpt();
                            loadroleuserlist(node.id);

                            checkBtnSaveRolePerms();
                        }
                    });
                }
                fncallback(nodes);
            }
            else {
                alert(getResultDesc(result));
                fncallback(new Array());
            }
        }
    );
}

function createRolefn(node, container) {
    container.attr('response-click', true);
    container.attr('response-select', true);
    container.append('<img response-click="true" response-select="true" src="img/ic_role.png" ' +
        'style="float:left;margin-top:12px;margin-right:10px;"></img>');
    container.append('<label response-click="true" response-select="true" class="nomargin">' + node.name + '</label>');

    if (hasPerm("MAN_ROLEPERM")) {
        container.append('<span class="icon-trash icon-large hide" id="delspan' + node.id + '" ' +
            ' style="color:red;float:right;margin-top:10px;margin-right:50px;cursor: pointer;"' +
            ' onclick="delrole(' + node.id + ')"></span>');
        container.append('<span class="icon-edit icon-large hide" id="editspan' + node.id + '" ' +
            ' style="color:red;float:right;margin-top:10px;margin-right:10px;cursor: pointer;"' +
            ' onclick="loadupdatemodal(' + node.id + ',\'' + node.name + '\',' + node.xzqh + ')" ' +
            '></span>');
    }
}

function loadupdatemodal(id, name, xzqh) {
    $('#updateroleid').val(id);
    $('#updaterolename').val(name);
    $("#divselupdatexzqh").empty();
    if (xzqh == null || xzqh < 1) {
        xzqh = 1;
    }
    $("#divselupdatexzqh").addselbyxzqh({id: "updatexzqhid", defaultval: xzqh});
    $('#updaterole').modal('show');
}

function loadRole() {
    $("#divleftgroup").empty();
    $("#divrightperm").empty();
    $("#divleftrole").empty();
    $("#divleftrole").etree({
        id: -1,
        childrendatafn: loadRoleDatafn
    });
}

function addrole() {
    var xzqhid = $("#addxzqhid").attr("selitem");
    $("#addroleform").validateForm(function () {
        $("#addroleform").autoajax("addRole", {
            xzqhid: xzqhid
        }, function (result) {
            $("#addrole").modal('hide');
            if (ifResultOK(result)) {
                loadRole();
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}

function updaterole() {
    var xzqhid = $("#updatexzqhid").attr("selitem");
    $("#updateroleform").validateForm(function () {
        $("#updateroleform").autoajax("updateRole", {
            xzqhid: xzqhid
        }, function (result) {
            $("#updaterole").modal('hide');
            if (ifResultOK(result)) {
                loadRole();
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}

function delrole(roleid) {
    ajax("delRole", {roleid: roleid}, function (result) {
        if (ifResultOK(result)) {
            loadRole();
        } else {
            alert(getResultDesc(result));
        }
    })
}

function loadPermGroupDatafn(pnode, fncallback) {
    ajax("queryPermGroups",
        {
            groupid: pnode.id
        }, function (result) {
            if (ifResultOK(result)) {
                var groups = getResultObj(result);
                var isleafs = getResultMap(result).isleaf;
                var nodes = new Array();

                for (var i = 0; i < groups.length; i++) {
                    var group = groups[i];
                    nodes.push({
                        id: group.id,
                        name: group.name,
                        isleaf: isleafs[i],
                        createnodefn: createPermGroupfn,
                        childrendatafn: loadPermGroupDatafn,

                        enterfn: function (node) {
                            $('#delspan' + node.id).removeClass('hide');
                            $('#editspan' + node.id).removeClass('hide');
                        },
                        leavefn: function (node) {
                            $('#delspan' + node.id).addClass('hide');
                            $('#editspan' + node.id).addClass('hide');
                        },
                        clickfn: function (node, event) {
                            loadperms(node.id);
                            if (node.expanded == '1') {
                                $('#caret' + node.id).removeClass('icon-caret-right');
                                $('#caret' + node.id).addClass('icon-caret-down');
                            } else {
                                $('#caret' + node.id).removeClass('icon-caret-down');
                                $('#caret' + node.id).addClass('icon-caret-right');
                            }
                        }
                    });
                }

                fncallback(nodes);
            }
            else {
                alert(getResultDesc(result));
                fncallback(new Array());
            }
        }
    )
    ;
}

function createPermGroupfn(node, container) {
    if (parseInt(node.isleaf) == 0) {
        container.attr('response-click', true);
        container.attr('response-select', true);
        container.append('<span id="caret' + node.id + '" response-click="true" response-expand=true class="icon-caret-right" ' +
            'style="float:left;margin-top:12px;margin-right:10px;"></span>');
        container.append('<label response-click="true" response-select="true" class="nomargin">' + node.name + '</label>');
    } else {
        container.attr('response-click', true);
        container.attr('response-select', true);
        container.append('<img response-click="true" response-select="true" src="img/ic_permgroup.png" ' +
            'style="float:left;margin-top:12px;margin-right:10px;"></img>');
        container.append('<label response-click="true" response-select="true" class="nomargin">' + node.name + '</label>');
    }
}

function loadPermGroup() {
    $("#divleftgroup").empty();
    $("#divrightperm").empty();
    $("#divleftgroup").etree({
        id: -1,
        childrendatafn: loadPermGroupDatafn
    }, null, null);
}

function loadperms(groupid) {
    $("#divrightperm").empty();
    ajax("queryPermGroupPerms_r", {groupid: groupid}, function (result) {
        if (ifResultOK(result)) {
            var perms = getResultObj(result);
            if (perms != null && perms.length > 0) {
                for (var i = 0; i < perms.length; i++) {
                    var perm = perms[i];
                    var permlimit = perm.permlimit;

                    var checked = false;

                    if (findperm(gl_currpermlist, perm.id) != null && findperm(gl_delpermlist, perm.id) == null)
                        checked = true;
                    else if (findperm(gl_newpermlist, perm.id) != null)
                        checked = true;

                    var $div = null;
                    if (checked) {
                        $div = '<div class="col-sm-6">' +
                            '<input class="permcheckbox" id="permcheckbox' + perm.id + '" type="checkbox" checked ' + (hasPerm('MAN_ROLEPERM') ? '' : 'disabled') + ' value="' + perm.id + '" ' +
                            'permid=' + perm.id + ' permcode=\'' + perm.name + '\' permdesc=\'' + perm.desc + '\' permlimit=\'' + permlimit + '\'>' +
                            '<label style="font-size:15px;margin-top:4px;">&nbsp;' + perm.desc + '</label></div>';
                    }
                    else {
                        $div = '<div class="col-sm-6">' +
                            '<input class="permcheckbox" id="permcheckbox' + perm.id + '" type="checkbox" ' + (hasPerm('MAN_ROLEPERM') ? '' : 'disabled') + ' value="' + perm.id + '" ' +
                            'permid=' + perm.id + ' permcode=\'' + perm.name + '\' permdesc=\'' + perm.desc + '\' permlimit=\'' + permlimit + '\'>' +
                            '<label style="font-size:15px;margin-top:4px;">&nbsp;' + perm.desc + '</label></div>';
                    }

                    $("#divrightperm").append($div);
                }
            }
            $('.permcheckbox').iCheck({
                checkboxClass: 'icheckbox_square-blue',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%' // optional
            });
            $('.permcheckbox').on('ifChecked', function (event) {
                var permid = $(event.target).attr('permid');

                //如果当前节点不在原节点中，则需要添加
                delperm(gl_delpermlist, permid);
                if (findperm(gl_currpermlist, permid) == null) {
                    addperm(gl_newpermlist, permid);
                }
                checkBtnSaveRolePerms();

                var permlimit = $(event.target).attr('permlimit');
                try {
                    if (permlimit != null && permlimit != '' && permlimit != 'null') {
                        var permlimitsp = permlimit.split(',');
                        for (var i = 0; i < permlimitsp.length; i++) {
                            var permlim = permlimitsp[i];

                            var code = permlim.split(':')[0];
                            var val = permlim.split(':')[1];

                            $("#divrightperm").find('[permcode*=' + code + ']').each(function () {
                                if (val == '1' || val == 1) {
                                    $(this).iCheck('check');
                                    $(this).iCheck('disable');
                                } else {
                                    $(this).iCheck('uncheck');
                                    $(this).iCheck('disable');
                                }
                            });
                        }
                    }
                } catch (e) {
                }
            });
            $('.permcheckbox').on('ifUnchecked', function (event) {
                var permid = $(event.target).attr('permid');

                delperm(gl_newpermlist, permid);
                if (findperm(gl_currpermlist, permid) != null) {
                    addperm(gl_delpermlist, permid);
                }
                checkBtnSaveRolePerms();

                var permlimit = $(event.target).attr('permlimit');
                if (permlimit != null && permlimit != '' && permlimit != 'null') {
                    var permlimitsp = permlimit.split(',');
                    for (var i = 0; i < permlimitsp.length; i++) {
                        var permlim = permlimitsp[i];

                        var code = permlim.split(':')[0];
                        var val = permlim.split(':')[1];

                        $("#divrightperm").find('[permcode*=' + code + ']').each(function () {
                            $(this).iCheck('enable');
                        });
                    }
                }
            });
        } else {
            alert(getResultDesc(result));
        }
    })
}

function checkBtnSaveRolePerms() {
    if (gl_delpermlist.length != 0 || gl_newpermlist.length != 0) {
        $("#btnSaveRolePerms").removeClass('disabled');
        $("#btnSaveRolePerms").removeAttr('disabled', 'disabled');
    } else {
        $("#btnSaveRolePerms").addClass('disabled');
        $("#btnSaveRolePerms").attr('disabled', 'disabled');
    }

    if (gl_delroleusers.length != 0 || gl_newroleusers.length != 0) {
        $("#btnSaveRoleUsers").removeClass('disabled');
        $("#btnSaveRoleUsers").removeAttr('disabled', 'disabled');
    } else {
        $("#btnSaveRoleUsers").addClass('disabled');
        $("#btnSaveRoleUsers").attr('disabled', 'disabled');
    }
}

function saveRolePerms() {
    var role = $("#divleftrole").etreegetselectednode();

    if (role == null) {
        alert("请选择角色");
    }
    var data = new Data();
    data.addDataObj("roleid", role.id);
    data.addDataList("permids", gl_newpermlist);
    ajax("bindRolePerms", data, function (rest) {
        if (ifResultOK(rest)) {
            var data = new Data();
            data.addDataObj("roleid", role.id);
            data.addDataList("permids", gl_delpermlist);
            ajax("delBindRolePerms", data, function (rest) {
                if (ifResultOK(rest)) {
                    alert("保存成功");
                    //保存成功以后重新获取角色权限列表
                    clearperm(gl_currpermlist);
                    clearperm(gl_newpermlist);
                    clearperm(gl_delpermlist);
                    loadRolePerms(role.id);
                } else {
                    alert(getResultDesc(rest));
                }
            });
        } else {
            alert(getResultDesc(rest));
        }
    });
}

function loadleftdpt() {
    $("#tableUserbody").empty();
    $("#divleftdpt").empty();
    $("#divleftdpt").etree({
        id: -1,
        /*rootnode:rootnode,*/
        childrendatafn: loaddptdatafn,
        containerdomid: $("#divleftdpt").attr('id')
    });
}

function createdptfn(node, container) {
    container.attr('response-click', true);
    container.attr('response-select', true);
    if (node.subcnt > 0) {
        if (node.expanded != null && node.expanded == '1' || node.expanded == true)
            container
                .append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-click="true" response-expand=true style="color:black;" response-expand=true class=\"icon-caret-down\"></span>'));
        else
            container
                .append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-click="true" response-expand=true style="color:black;" response-expand=true class=\"icon-caret-right\"></span>'));
    }
    container
        .append($('<a response-click="true" respose-select=true class="nomargin nopadding" style="color:inherit;width:80%;overflow:hidden;">'
            + '<label response-click=true response-select=true style="color:inherit;margin-left:5px;">'
            + node.name + '</label></a>'));
}

function loaddptdatafn(pnode, fncallback) {
    ajax("dpt/queryxzqhdpt", {
        'loginid': $("#userid").val(),
        'id': pnode.id,
        'xzqh':curxzqh
    }, function (data) {
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
                    var pnodeid = pnode.id;
                    nodes.push({
                        id: id,
                        pid: pnodeid,
                        name: name,
                        xzqh: xzqh,
                        subcnt: subcnt,
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
                        childrendatafn: loaddptdatafn,
                        createnodefn: createdptfn,
                        containerdomid: pnode.containerdomid,
                        inited: true,
                        expanded: true
                    });
                }
            }

            fncallback(nodes);
        }
    })
}

function userdatafn(sSource, aoData, fnCallback) {

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
            'department': getDateTableParam(aoData, 'dptid'),
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

function loaduserlist(dptid) {
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
            "iDisplayLength": 10, // 每页显示10条数据
            "sAjaxSource": "user/queryuser",// 获取数据的url
            "fnServerData": userdatafn, // 获取数据的处理函数
            "fnServerParams": function (aoData) {
                aoData.push({
                    'name': 'dptid',
                    'value': dptid
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
                "mDataProp": 'title'
            }, {
                "mDataProp": 'ustatus'
            }],
            "columnDefs": [
                {
                    "render": function (data, type, row) {
                        if (row.ustatus == USTATUS_ENABLE) {
                            return "<label class='ustatuslbgreen'>正常</label>";
                        } else if (row.ustatus == USTATUS_DISABLE) {
                            return "<label class='ustatuslbred'>已禁用</label>";
                        } else {
                            return "<label>已删除</label>";
                        }
                    },
                    "targets": 3
                }],
            "fnCreatedRow": function (nRow, aData, iDataIndex) {
                var checked = false;

                if (findperm(gl_currroleusers, aData.id) != null && findperm(gl_delroleusers, aData.id) == null)
                    checked = true;
                else if (findperm(gl_newroleusers, aData.id) != null)
                    checked = true;

                if (checked == true) {
                    $("td:eq(0)", nRow).html(
                        "<input name='usercheck' class='usercheck' type='checkbox' checked onclick='usercbclick(this);'  username='"
                        + aData.username + "' userid='"
                        + aData.id + "' id='cb"
                        + (start + iDataIndex) + "'>&nbsp;"
                        + (start + iDataIndex));
                }
                else {
                    $("td:eq(0)", nRow).html(
                        "<input name='usercheck' class='usercheck' type='checkbox' onclick='usercbclick(this);' username='"
                        + aData.username + "' userid='"
                        + aData.id + "' id='cb"
                        + (start + iDataIndex) + "'>&nbsp;"
                        + (start + iDataIndex));
                }

            }
        });
}

function usercbclick(ths) {
    var checked = $(ths).prop("checked");
    var userid = $(ths).attr('userid');

    if (checked) {
        //如果当前节点不在原节点中，则需要添加
        delperm(gl_delroleusers, userid);
        if (findperm(gl_currroleusers, userid) == null) {
            addperm(gl_newroleusers, userid);
        }
        checkBtnSaveRolePerms();
    } else {
        delperm(gl_newroleusers, userid);
        if (findperm(gl_currroleusers, userid) != null) {
            addperm(gl_delroleusers, userid);
        }
        checkBtnSaveRolePerms();
    }
}

/**
 * 存入的为用户的ID
 * @type {Array}
 */
var gl_currroleusers = new Array();
var gl_newroleusers = new Array();
var gl_delroleusers = new Array();

function loadRoleUsers(roleid) {
    clearperm(gl_currroleusers);
    clearperm(gl_newroleusers);
    clearperm(gl_delroleusers);
    ajax('queryRoleUsers', {roleid: roleid}, function (result) {
        if (ifResultOK(result)) {
            var users = getResultObj(result);

            if (users != null) {
                for (var i = 0; i < users.length; i++) {
                    var user = users[i];
                    addperm(gl_currroleusers, parseInt(user.name));
                }
            }
            checkBtnSaveRolePerms();
        } else {
            alert(getResultDesc(result));
        }
    });
}

function saveRoleUsers() {
    var role = $("#divleftrole").etreegetselectednode();

    if (role == null) {
        alert("请选择角色");
    }
    var data = new Data();
    data.addDataObj("roleid", role.id);
    data.addDataList("userids", gl_newroleusers);
    ajax("bindRoleUsers", data, function (rest) {
        if (ifResultOK(rest)) {
            var data = new Data();
            data.addDataObj("roleid", role.id);
            data.addDataList("userids", gl_delroleusers);
            ajax("delBindRoleUsers", data, function (rest) {
                if (ifResultOK(rest)) {
                    alert("保存成功");
                    loadRoleUsers(role.id);
                } else {
                    alert(getResultDesc(rest));
                }
            });
        } else {
            alert(getResultDesc(rest));
        }
    });
}

function selalluser() {
    if ($("#cbselall").prop('checked') == true) {
        for (var i = start; i < start + 30; i++) {
            $("#cb" + i).prop('checked', true);

            usercbclick("#cb" + i);
        }
    } else {
        for (var i = start; i < start + 30; i++) {
            $("#cb" + i).prop('checked', false);

            usercbclick("#cb" + i);
        }
    }
}


function userlistdatafn(sSource, aoData, fnCallback) {
    start = getDateTableParam(aoData, "iDisplayStart") + 1;
    $.ajax({
        "type": "post",
        "url": sSource,
        "dataType": "json",
        "data": {
            'roleid': getDateTableParam(aoData, 'roleid'),
            'sEcho': getDateTableParam(aoData, "sEcho"),
            'rows': getDateTableRows(aoData),
            'page': getDateTablePage(aoData)
        }, // 以json格式传递
        "success": function (resp) {
            if (ifResultOK(resp)) {
                var users = getResultRecords(resp).data;
                var list = new Array();
                for (var i = 0; i < users.length; i++) {
                    var user = users[i];
                    var id = user[0].id;
                    var username = user[0].username;
                    var name = user[0].name;
                    var title = user[2].attrdesc;
                    var ustatus = user[0].ustatus;
                    var jstatus = user[3].type + "/"
                        + user[3].typedesc;
                    list.push({
                        id: id,
                        username: username,
                        name: name,
                        title: title,
                        ustatus: ustatus,
                        jstatus: jstatus
                    });
                }
                var data = toDateTableJsonResult(list, getResultRecords(resp).total,
                    getResultRecords(resp).total, resp.map.sEcho);
                fnCallback(data); // 服务器端返回的对象的returnObject部分是要求的格式
            } else {

            }
        }
    });
}

function loadroleuserlist(roleid) {
    $('#tableUserlist')
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
            "iDisplayLength": 10, // 每页显示10条数据
            "sAjaxSource": "queryRoleUserInfos",// 获取数据的url
            "fnServerData": userlistdatafn, // 获取数据的处理函数
            "fnServerParams": function (aoData) {
                aoData.push({
                    'name': 'roleid',
                    'value': roleid
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
                "mDataProp": 'title'
            }, {
                "mDataProp": 'ustatus'
            }, {
                "mDataProp": null,
                bVisible: hasPerm("MAN_ROLEUSER") ? true : false
            }],
            "columnDefs": [
                {
                    "render": function (data, type, row) {
                        if (row.ustatus == USTATUS_ENABLE) {
                            return "<label class='ustatuslbgreen'>正常</label>";
                        } else if (row.ustatus == USTATUS_DISABLE) {
                            return "<label class='ustatuslbred'>已禁用</label>";
                        } else {
                            return "<label>已删除</label>";
                        }
                    },
                    "targets": 3
                }, {
                    "render": function (data, type, row) {
                        return '<button class="btn btn-link" onclick="removeRoleUser(' + roleid + ',' + row.id + ');">移除</button>'
                    },
                    "targets": 4
                }],
            "fnCreatedRow": function (nRow, aData, iDataIndex) {
                $("td:eq(0)", nRow).html(
                    (start + iDataIndex));

            }
        });
}


function removeRoleUser(roleid, userid) {
    var data = new Data();
    data.addDataObj("roleid", roleid);
    data.addDataObj("userid", userid);
    ajax("delBindRoleUsers", data, function (rest) {
        if (ifResultOK(rest)) {
            loadRoleUsers(roleid);
            loadroleuserlist(roleid);
        } else {
            alert(getResultDesc(rest));
        }
    });
}