$(document).ready(function () {
    resize();
    $(window).resize(function (event) {
        resize();
    });

    loadPermGroup();
    checkbuttonstatus();
    dialogpreshowinit();
});

function resize() {
    var clientheight = window.innerHeight;
    $("#divleftgroup").css('height', (clientheight - 52 - 80 - 70) + 'px');
    $("#divrightrow").css('height', (clientheight - 52 - 80 - 70 ) + 'px');
}

function checkbuttonstatus() {
    var selnode = $("#divleftgroup").etreegetselectednode();
    if (selnode == null) {
        $("#btnnewperm").addClass('disabled');
        $("#btnnewperm").attr('disabled', 'disabled');

        $("#btnupdateperm").addClass('disabled');
        $("#btnupdateperm").attr('disabled', 'disabled');

        $("#btndelperm").addClass('disabled');
        $("#btndelperm").attr('disabled', 'disabled');
    } else {
        $("#btnnewperm").removeClass('disabled');
        $("#btnnewperm").removeAttr('disabled', 'disabled');


        var checkcnt = 0;
        $("#divrightrow").find('.permcheckbox').each(function () {
            if ($(this).prop('checked') == true) {
                checkcnt++;
            }
        });
        if (checkcnt == 1) {
            $("#btnupdateperm").removeClass('disabled');
            $("#btnupdateperm").removeAttr('disabled', 'disabled');
        } else {
            $("#btnupdateperm").addClass('disabled');
            $("#btnupdateperm").attr('disabled', 'disabled');
        }
        if (checkcnt > 0) {
            $("#btndelperm").removeClass('disabled');
            $("#btndelperm").removeAttr('disabled', 'disabled');
        } else {
            $("#btndelperm").addClass('disabled');
            $("#btndelperm").attr('disabled', 'disabled');
        }
    }
}

function dialogpreshowinit() {
    $("#addgroup").on('show.bs.modal', function () {
        var selnode = $("#divleftgroup").etreegetselectednode();
        if (selnode != null) {
            $("#addgroupssfz").val("1");
        }

        $("#newgroupname").val("");
        $("#newgroupname").validateTargetBind({notempty: {}});
    });
    $("#updategroup").on('show.bs.modal', function () {
        $("#updategroupname").validateTargetBind({notempty: {}});
    });

    $("#addperm").on('show.bs.modal', function () {

        var selnode = $("#divleftgroup").etreegetselectednode();
        if (selnode == null) {
            alert("请先选择要添加在哪个权限组下");
            return false;
        }
        $("#addpermgroupid").val(selnode.id);
        $("#addpermcode").val("");
        $("#addpermdesc").val("");
        $("#addpermcode").validateTargetBind({notempty: {}});
        $("#addpermdesc").validateTargetBind({notempty: {}});
    });

    $("#updateperm").on('show.bs.modal', function () {
        $("#divrightrow").find('.permcheckbox').each(function () {
            if ($(this).prop('checked') == true) {
                var permid = $(this).attr('permid');
                var permcode = $(this).attr('permcode');
                var permdesc = $(this).attr('permdesc');

                $("#updatepermid").val(permid);
                $("#updatepermcode").val(permcode);
                $("#updatepermdesc").val(permdesc);
                $("#updatepermcode").validateTargetBind({notempty: {}});
                $("#updatepermdesc").validateTargetBind({notempty: {}});
                return false;
            }
        });
    });

}

function loadPermGroupDatafn(pnode, fncallback) {
    ajax("queryPermGroups",
        {
            groupid: pnode.id
        },
        function (result) {
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
                        inited: true,
                        expanded: true,
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
                        clickfn: function (node,event) {
                            checkbuttonstatus();
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
        container.append('<span id="caret' + node.id + '" response-click="true" response-select="true" response-expand=true class="icon-caret-down" ' +
            'style="float:left;margin-top:12px;margin-right:10px;"></span>');
        container.append('<label response-click="true"  response-select="true" class="nomargin">' + node.name + '</label>');
        container.append('<span class="icon-edit icon-large hide" id="editspan' + node.id + '" ' +
            ' style="color:red;float:right;margin-top:10px;margin-right:50px;cursor: pointer;"' +
            ' onclick="$(\'#updategroupid\').val(' + node.id + ');$(\'#updategroupname\').val(\'' + node.name + '\');$(\'#updategroup\').modal(\'show\')" ' +
            '></span>');
    } else {
        container.attr('response-click', true);
        container.attr('response-select', true);
        container.append('<img response-click="true" response-select="true" src="img/ic_permgroup.png" ' +
            'style="float:left;margin-top:12px;margin-right:10px;"></img>');
        container.append('<label response-click="true" response-select="true" class="nomargin">' + node.name + '</label>');
        container.append('<span class="icon-trash icon-large hide" id="delspan' + node.id + '" ' +
            ' style="color:red;float:right;margin-top:10px;margin-right:50px;cursor: pointer;"' +
            ' onclick="delgroup(' + node.id + ')"></span>');
        container.append('<span class="icon-edit icon-large hide" id="editspan' + node.id + '" ' +
            ' style="color:red;float:right;margin-top:10px;margin-right:5px;cursor: pointer;"' +
            ' onclick="$(\'#updategroupid\').val(' + node.id + ');$(\'#updategroupname\').val(\'' + node.name + '\');$(\'#updategroup\').modal(\'show\')" ' +
            '></span>');
    }
}

function loadPermGroup() {
    $("#divleftgroup").empty();
    $("#divleftgroup").etree({
        id: -1,
        childrendatafn: loadPermGroupDatafn
    }, null, null, function () {
        checkbuttonstatus();
    });
}

function addgroup() {
    var groupid = -1;

    if ($("#addgroupssfz").val() == "1") {
        var selgroup = $("#divleftgroup").etreegetselectednode();
        if (selgroup == null) {
            alert("请先选择要添加在哪个组下");
            return
        }
        groupid = selgroup.id;
    }
    $("#addgroupform").validateForm(function () {
        $("#addgroupform").autoajax("addPermGroup",
            {
                groupid: groupid
            }, function (result) {
                if (ifResultOK(result)) {
                    $("#addgroup").modal('hide');
                    loadPermGroup();
                } else {
                    alert(getResultDesc(result));
                }
            });
    });
}

function delgroup(groupid) {
    ajax("delPermGroup", {groupid: groupid}, function (result) {
        if (ifResultOK(result)) {
            loadPermGroup();
        } else {
            alert(getResultDesc(result));
        }
    })
}

function updategroup() {
    $("#updategroupform").validateForm(function () {
        $("#updategroupform").autoajax("updatePermGroup", {}, function (result) {
            if (ifResultOK(result)) {
                $("#updategroup").modal('hide');
                loadPermGroup();
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}

function delperm() {
    var delids = new Array();
    $("#divrightrow").find('.permcheckbox').each(function () {
        if ($(this).prop('checked') == true) {
            delids.push($(this).attr('permid'));
        }
    });

    var data = new Data();
    data.addDataList("permids", delids);
    ajax("delPerms", data, function (result) {
        if (ifResultOK(result)) {
            $("#delperm").modal('hide');
            var selnode = $("#divleftgroup").etreegetselectednode();
            if (selnode != null)
                loadperms(selnode.id);
        } else {
            alert(getResultDesc(result));
        }
    })
}

function loadperms(groupid) {
    $("#divrightrow").empty();
    ajax("queryPermGroupPerms", {groupid: groupid}, function (result) {
        if (ifResultOK(result)) {
            var perms = getResultObj(result);
            if (perms != null && perms.length > 0) {
                for (var i = 0; i < perms.length; i++) {
                    var perm = perms[i];
                    $("#divrightrow").append('<div class="col-xs-12">' +
                        '<input class="permcheckbox" id="permcheckbox' + perm.id + '" type="checkbox" value="' + perm.id + '" ' +
                        'permid=' + perm.id + ' permcode=\'' + perm.name + '\' permdesc=\'' + perm.desc + '\'>' +
                        '<label style="font-size:15px;margin-top:4px;">&nbsp;' + perm.desc + '(' + perm.name + ')' + '</label></div>');
                }
            }
            $('.permcheckbox').iCheck({
                checkboxClass: 'icheckbox_square-blue',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%' // optional
            });
            $('.permcheckbox').on('ifChecked', function (event) {
                checkbuttonstatus();
            });
            $('.permcheckbox').on('ifUnchecked', function (event) {
                checkbuttonstatus();
            });
            checkbuttonstatus();
        } else {
            alert(getResultDesc(result));
        }
    })
}

function addperm() {
    $("#addpermform").validateForm(function () {
        $("#addpermform").autoajax("addPerm", {}, function (result, data) {
            if (ifResultOK(result)) {
                $("#addperm").modal('hide');
                loadperms(data.groupid);
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}

function updateperm() {
    $("#updatepermform").validateForm(function () {
        $("#updatepermform").autoajax("updatePerm", {}, function (result, data) {
            if (ifResultOK(result)) {
                $("#updateperm").modal('hide');
                $("#updateperm").modal('hide');
                var selnode = $("#divleftgroup").etreegetselectednode();
                if (selnode != null)
                    loadperms(selnode.id);
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}

function delperm() {
    var delids = new Array();
    $("#divrightrow").find('.permcheckbox').each(function () {
        if ($(this).prop('checked') == true) {
            delids.push($(this).attr('permid'));
        }
    });

    var data = new Data();
    data.addDataList("permids", delids);
    ajax("delPerms", data, function (result) {
        if (ifResultOK(result)) {
            $("#delperm").modal('hide');
            var selnode = $("#divleftgroup").etreegetselectednode();
            if (selnode != null)
                loadperms(selnode.id);
        } else {
            alert(getResultDesc(result));
        }
    })
}