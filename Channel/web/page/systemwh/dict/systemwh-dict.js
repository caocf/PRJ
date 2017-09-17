$(document).ready(function () {
    commoninit("数据字典");

    resize();
    $(window).resize(function (event) {
        resize();
    });
    loaddictlist();
});

function resize() {
    var clientheight = window.innerHeight;
    $("#divleftrow").css('height', (clientheight - 52 - 80) + 'px');
    $("#divrightrow").css('height', (clientheight - 53 - 80 - 50) + 'px');
    $("#divrightxzqhrow").css('height', (clientheight - 53 - 80 - 50) + 'px');
    $("#divxzqhlist").css('height', (clientheight - 53 - 80) + 'px');
}

function dictclickfn(node, event) {
    if (node.nametype == 'xzqh') {
        $("#divright").hide();
        $("#divrightzzzt").hide();
        $("#divrightxzqh").show();
        loadselxzqh("divxzqhlist", "xzqh");
        refreshxzqhoperbtn();
    } else if (node.nametype == 'zzzt') {
        $("#divrightxzqh").hide();
        $("#divrightzzzt").show();
        $("#divright").hide();

        ajax(
            'dic/querydicattr',
            {
                'loginid': $("#userid").val(),
                'name': node.nametype
            },
            function (result) {
                if (ifResultOK(result)) {
                    try {
                        $("#tablerighttzzztbody").empty();
                        for (var i = 0; i < getResultRecords(result).data.length; i++) {
                            var o = getResultRecords(result).data[i];

                            var id = o.id;
                            var type = o.type;
                            var typedesc = o.typedesc;
                            var createtime = o.createtime;
                            var updatetime = o.updatetime;

                            createtime = createtime.replace('T', ' ');
                            updatetime = updatetime.replace('T', ' ');


                            createtime = ((createtime.indexOf('1970-') != -1) ? '' : createtime);
                            updatetime = ((updatetime.indexOf('1970-') != -1) ? '' : updatetime);

                            var tr = $('<tr></tr>');
                            tr
                                .append($('<td class="text-center" style="padding-bottom:0px;">'
                                    + (i + 1) + '</td>'));
                            tr
                                .append($('<td class="text-center" style="padding-bottom:0px;">'
                                    + type + '</td>'));
                            tr
                                .append($('<td class="text-center" style="padding-bottom:0px;">'
                                    + typedesc + '</td>'));
                            tr
                                .append($('<td class="text-center" style="padding-bottom:0px;">'
                                    + createtime + '</td>'));
                            tr
                                .append($('<td class="text-center" style="padding-bottom:0px;">'
                                    + updatetime + '</td>'));
                            if (hasPerm("MAN_DICT")) {
                                tr
                                    .append($('<td class="text-center" style="padding-bottom:0px;">'
                                        + '<button class="btn btn-link" onclick="updatezzztdictload('
                                        + id
                                        + ',\''
                                        + type
                                        + '\',\''
                                        + typedesc
                                        + '\')">修改</button>'
                                        + '<button class="btn btn-link" onclick="delzzztdictload('
                                        + id
                                        + ',\''
                                        + type
                                        + '\',\''
                                        + typedesc
                                        + '\')">删除</button></td>'));
                            }
                            $("#tablerighttzzztbody").append(tr);
                        }
                    } catch (e) {
                    }
                }
            });

    } else {
        $("#divrightxzqh").hide();
        $("#divrightzzzt").hide();
        $("#divright").show();
        ajax(
            'dic/querydicattr',
            {
                'loginid': $("#userid").val(),
                'name': node.nametype
            },
            function (result) {
                if (ifResultOK(result)) {
                    try {
                        $("#tablerighttbody").empty();
                        for (var i = 0; i < getResultRecords(result).data.length; i++) {
                            var o = getResultRecords(result).data[i];

                            var id = o.id;
                            var attrdesc = o.attrdesc;
                            var createtime = o.createtime;
                            var updatetime = o.updatetime;

                            createtime = createtime.replace('T', ' ');
                            updatetime = updatetime.replace('T', ' ');

                            createtime = ((createtime.indexOf('1970-') != -1) ? '' : createtime);
                            updatetime = ((updatetime.indexOf('1970-') != -1) ? '' : updatetime);

                            var tr = $('<tr></tr>');
                            tr
                                .append($('<td class="text-center" style="padding-bottom:0px;">'
                                    + (i + 1) + '</td>'));
                            tr
                                .append($('<td class="text-center" style="padding-bottom:0px;">'
                                    + attrdesc + '</td>'));
                            tr
                                .append($('<td class="text-center" style="padding-bottom:0px;">'
                                    + createtime + '</td>'));
                            tr
                                .append($('<td class="text-center" style="padding-bottom:0px;">'
                                    + updatetime + '</td>'));
                            if (hasPerm("MAN_DICT")) {
                                tr
                                    .append($('<td class="text-center" style="padding-bottom:0px;">'
                                        + '<button class="btn btn-link" onclick="updatedictload('
                                        + id
                                        + ',\''
                                        + attrdesc
                                        + '\')">修改</button>'
                                        + '<button class="btn btn-link" onclick="deldictload('
                                        + id
                                        + ',\''
                                        + attrdesc
                                        + '\')">删除</button></td>'));
                            }
                            $("#tablerighttbody").append(tr);
                        }
                    } catch (e) {
                    }
                }
            });
    }
}

function loaddictlistfn(pnode, fncallback) {
    var nodes = new Array();
    ajax('dic/querynamedesc', {
        'loginid': $("#userid").val()
    }, function (result) {
        if (ifResultOK(result)) {
            var obj = getResultObj(result);
            if (obj != null) {
                for (var i = 0; i < obj.length; i++) {
                    var node = {
                        id: i,
                        name: obj[i][0],
                        nametype: obj[i][1],
                        clicked: (i == 0 ? true : false),
                        clickfn: dictclickfn
                    };
                    nodes.push(node);
                }

            }
        }

        fncallback(nodes);
    });
}

function loaddictlist() {
    $("#divdictlist").etree({
        id: -1,
        childrendatafn: loaddictlistfn
    });
}

function newdictload() {
    // 获得当前选中的节点
    var selnode = $("#divdictlist").etreegetselectednode();
    if (selnode != null) {
        var name = selnode.nametype;
        var namedesc = selnode.name;
        $("#newdicttype").text(namedesc);

        $("#newdictname").text('');
        $("#newdictname").validateTargetBind({
            notempty: {}
        });

        $("#btnSave").unbind('click');
        $("#btnSave").bind('click', newdict);

        $("#dialogaddnew").modal('show');
    }
}

function newdict() {
    var selnode = $("#divdictlist").etreegetselectednode();
    if (selnode != null) {
        $("#divaddform").validateForm(function () {
            var name = selnode.nametype;
            var namedesc = selnode.name;
            var attrdesc = $("#newdictname").val();

            ajax('dic/adddic', {
                'loginid': $("#userid").val(),
                'name': name,
                'namedesc': namedesc,
                'attrdesc': attrdesc
            }, function (result) {
                if (ifResultOK(result)) {
                    $("#divdictlist").etreeclicknode(selnode.id);
                } else {
                    alert(getResultDesc(result));
                }
                // 隐藏弹窗
                $("#dialogaddnew").modal('hide');
            });
        });

    }
}

function deldictload(id, attrdesc) {
    $("#lbdel").text(attrdesc);
    $("#dialogdel").modal('show');
    $("#btnDel").unbind('click');
    $("#btnDel").bind('click', function () {
        deldict(id);
    });
}

function deldict(id) {
    var selnode = $("#divdictlist").etreegetselectednode();
    if (selnode != null) {

        ajax('dic/deldic', {
            'loginid': $("#userid").val(),
            'id': id
        }, function (result) {
            if (ifResultOK(result)) {
                $("#divdictlist").etreeclicknode(selnode.id);
            } else {
                alert(getResultDesc(result));
            }
            // 隐藏弹窗
            $("#dialogdel").modal('hide');
        });
    }
}

function updatedictload(id, attrdesc) {
    var selnode = $("#divdictlist").etreegetselectednode();
    if (selnode != null) {
        var name = selnode.nametype;
        var namedesc = selnode.name;

        $("#updatedicttype").text(namedesc);
        $("#updatedictname").val(attrdesc);
        $("#updatedictname").validateTargetBind({
            notempty: {}
        });
        $("#btnUpdate").unbind('click');
        $("#btnUpdate").bind('click', function () {
            updatedict(id);
        });
        $("#dialogupdate").modal('show');
    }
}

function updatedict(id) {
    var selnode = $("#divdictlist").etreegetselectednode();
    if (selnode != null) {
        $("#divupdateform").validateForm(function () {
            var name = selnode.nametype;
            var namedesc = selnode.name;
            var attrdesc = $("#updatedictname").val();

            ajax('dic/updatedic', {
                'loginid': $("#userid").val(),
                'id': id,
                'name': name,
                'namedesc': namedesc,
                'attrdesc': attrdesc
            }, function (result) {
                if (ifResultOK(result)) {
                    $("#divdictlist").etreeclicknode(selnode.id);
                } else {
                    alert(getResultDesc(result));
                }
                // 隐藏弹窗
                $("#dialogupdate").modal('hide');
            });
        });
    }
}

/** **********行政区划 ****** */
function loadselxzqh(containerdomid, label) {
    $('#' + containerdomid).empty();
    $('#' + containerdomid).etree({
        id: '-1',
        childrendatafn: xzqhsrvfn,
        createnodefn: createxzqhsrvfn,
        containerdomid: containerdomid
    }, function (node) {
        node.label = label;
    });
}

function xzqhsrvfn(pnode, fncallback) {
    ajax('xzqh/queryallxzqh', {
        'loginid': $("#userid").val(),
        'id': pnode.id
    }, function (data) {
        if (ifResultOK(data)) {
            var records = getResultObj(data);
            if (records) {
                var nodes = new Array();
                for (var i = 0; i < records.length; i++) {
                    var nd = records[i];
                    var name = nd.xzqhdm.name;
                    var type = nd.xzqhdm.type;
                    var id = nd.xzqhdm.id;
                    var subcnt = nd.countchild;

                    nodes.push({
                        id: id,
                        pid: pnode.id,
                        name: name,
                        type: type,
                        subcnt: subcnt,
                        containerdomid: pnode.containerdomid,
                        clickfn: function (node, event) {
                            if ($(event.target).is('span')) {
                                event.stopPropagation();
                            } else {
                                var containerdomid = node.containerdomid;
                                var currnode = $("#" + containerdomid)
                                    .etreegetnode(node.id);
                                var selnode = $("#" + containerdomid)
                                    .etreegetselectednode();
                                if (selnode != null)
                                    $("#" + containerdomid).etreesetselected(
                                        selnode.id, false, 'white');
                                var selectbg = currnode.selectbg;
                                if (selectbg == null || selectbg == '') {
                                    selectbg = '#337ab7';
                                }
                                $("#" + containerdomid).etreesetselected(
                                    currnode.id, true, selectbg);

                                refreshxzqhoperbtn();
                                // 展示详情
                                ajax('xzqh/queryxzqhinfo', {
                                    'loginid': $("#userid").val(),
                                    'id': node.id
                                }, function (result) {
                                    if (ifResultOK(result)) {
                                        try {
                                            var o = result.map.xzqh;
                                            var id = o.id;
                                            var code = o.code;
                                            var name = o.name;
                                            var createtime = o.createtime;
                                            var updatetime = o.updatetime;

                                            createtime = createtime.replace('T',
                                                ' ');
                                            updatetime = updatetime.replace('T',
                                                ' ');

                                            createtime = ((createtime.indexOf('1970-') != -1) ? '' : createtime);
                                            updatetime = ((updatetime.indexOf('1970-') != -1) ? '' : updatetime);

                                            $("#xzqhname").text(name);
                                            $("#xzqhcode").text(code);
                                            $("#createtime").text(createtime);
                                            $("#updatetime").text(updatetime);
                                        } catch (e) {
                                        }
                                    }
                                });
                            }
                            if (node.expanded == 1) {
                                $("#" + node.containerdomid + "_span" + node.id).removeClass('icon-caret-right');
                                $("#" + node.containerdomid + "_span" + node.id).addClass('icon-caret-down');
                            } else {
                                $("#" + node.containerdomid + "_span" + node.id).addClass('icon-caret-right');
                                $("#" + node.containerdomid + "_span" + node.id).removeClass('icon-caret-down');
                            }
                        },
                        childrendatafn: xzqhsrvfn,
                        createnodefn: createxzqhsrvfn,
                        inited: true,
                        expanded: true
                    });
                }
                fncallback(nodes);
            }
        }
    });
}

function createxzqhsrvfn(node, container) {
    container.attr('response-click', true);
    container.attr('response-select', true);

    $div = $('<div response-click="true" response-expand="true"  style="padding-top:13px;width:17px;float:left;height:40px;"/>');

    if (node.subcnt > 0) {
        if (node.expanded != null && node.expanded == '1' || node.expanded == true)
            $div
                .append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-click="true" response-expand=true style="color:black;" class=\"icon-caret-down\"></span>'));
        else
            $div
                .append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-click="true" response-expand=true style="color:black;" class=\"icon-caret-right\"></span>'));
    }

    container.append($div);
    container
        .append($('<a class="nomargin nopadding" style="color:inherit;width:80%;overflow:hidden;">'
            + '<label response-click="true" response-select=true style="color:inherit;margin-left:5px;">'
            + node.name + '</label></a>'));
}

function newxzqhload() {
    // 获得当前选中的节点
    var selnode = $("#divxzqhlist").etreegetselectednode();
    $("#newxzqhname").val('');
    $("#newxzqhcode").val('');
    if (selnode != null) {
        $("#ssxzqh").show();
        var name = selnode.name;

        $("#newssxzqh").text(name);

        $("#newxzqhname").validateTargetBind({
            notempty: {}
        });
        $("#newxzqhcode").validateTargetBind({
            notempty: {}
        });
    } else {
        $("#ssxzqh").hide();
    }

    $("#btnSavexzqh").unbind('click');
    $("#btnSavexzqh").bind('click', newxzqh);

    $("#dialogaddnewxzqh").modal('show');
}

function newxzqh() {
    var selnode = $("#divxzqhlist").etreegetselectednode();
    var pid = 0;
    if (selnode != null) {
        pid = selnode.id;
    }
    $("#divaddxzqhform").validateForm(function () {
        var newxzqhname = $("#newxzqhname").val();
        var newxzqhcode = $("#newxzqhcode").val();

        ajax('xzqh/addxzqh', {
            'loginid': $("#userid").val(),
            'name': newxzqhname,
            'code': newxzqhcode,
            'pid': pid
        }, function (result) {
            // 隐藏弹窗
            $("#dialogaddnewxzqh").modal('hide');

            if (ifResultOK(result)) {
                if (selnode != null) {
                    $("#divxzqhlist").etreereload(selnode.pid, function (node) {
                        if (node == selnode.id) {
                            $("#divxzqhlist").etreeclicknode(selnode.id);

                            refreshxzqhoperbtn();
                        }
                    });
                } else
                    $("#divxzqhlist").etreereload(-1);

            } else {
                alert(getResultDesc(result));
            }
        });
    });
}

function updatexzqhload() {
    // 获得当前选中的节点
    var selnode = $("#divxzqhlist").etreegetselectednode();
    if (selnode != null) {
        // 展示详情
        ajax('xzqh/queryxzqhinfo', {
            'loginid': $("#userid").val(),
            'id': selnode.id
        }, function (result) {
            if (ifResultOK(result)) {
                try {
                    var o = result.map.xzqh;
                    var id = o.id;
                    var code = o.code;
                    var name = o.name;
                    var createtime = o.createtime;
                    var updatetime = o.updatetime;

                    createtime = createtime.replace('T', ' ');
                    updatetime = updatetime.replace('T', ' ');

                    $("#updatexzqhname").val(name);
                    $("#updatexzqhcode").val(code);

                    $("#updatexzqhname").validateTargetBind({
                        notempty: {}
                    });
                    $("#updatexzqhcode").validateTargetBind({
                        notempty: {}
                    });
                    $("#btnUpdatexzqh").unbind('click');
                    $("#btnUpdatexzqh").bind('click', updatexzqh);

                    $("#dialogupdatexzqh").modal('show');
                } catch (e) {
                }
            }
        });
    } else {
        alert('请选择要修改的行政区划！');
    }
}

function updatexzqh() {
    var selnode = $("#divxzqhlist").etreegetselectednode();
    if (selnode != null) {
        $("#divupdatexzqhform").validateForm(
            function () {
                var updatexzqhname = $("#updatexzqhname").val();
                var updatexzqhcode = $("#updatexzqhcode").val();

                ajax('xzqh/updatexzqh', {
                    'loginid': $("#userid").val(),
                    'name': updatexzqhname,
                    'code': updatexzqhcode,
                    'pid': selnode.pid,
                    'id': selnode.id
                }, function (result) {
                    // 隐藏弹窗
                    $("#dialogupdatexzqh").modal('hide');

                    if (ifResultOK(result)) {
                        alert("修改成功");
                        if (selnode != null) {
                            $("#divxzqhlist").etreereload(
                                selnode.pid,
                                function (node) {
                                    if (node.id == selnode.id)
                                        $("#divxzqhlist")
                                            .etreeclicknode(
                                            selnode.id);
                                });
                        } else
                            $("#divxzqhlist").etreereload(-1);

                    } else {
                        alert(getResultDesc(result));
                    }
                    refreshxzqhoperbtn();
                });
            });
    }

}

function delxzqhload() {
    var selnode = $("#divxzqhlist").etreegetselectednode();
    if (selnode != null) {
        var id = selnode.id;
        var name = selnode.name;
        $("#lbxzqhdel").text(name);
        $("#dialogxzqhdel").modal('show');
        $("#btnxzqhDel").unbind('click');
        $("#btnxzqhDel").bind('click', function () {
            delxzqh(id);
        });
    } else {
        alert("请选择要删除的行政区划！");
    }
}

function delxzqh(id) {
    var selnode = $("#divxzqhlist").etreegetselectednode();
    if (selnode != null) {
        ajax('xzqh/delxzqh', {
                'loginid': $("#userid").val(),
                'id': id
            },
            function (result) {
                // 隐藏弹窗
                $("#dialogxzqhdel").modal('hide');
                if (ifResultOK(result)) {
                    if (selnode.pid != -1 && selnode.pid != '-1') {
                        try {
                            var ppid = $("#divxzqhlist").etreegetnode(
                                selnode.pid).pid;
                            $("#divxzqhlist").etreereload(ppid);
                            $("#divxzqhlist").etreeclicknode(selnode.pid);
                        } catch (e) {
                        }
                    } else {
                        $("#divxzqhlist").etreereload(-1);
                    }
                } else {
                    alert(getResultDesc(result));
                }
                refreshxzqhoperbtn();
            });
    }
}

/**
 * 根据当前是否选中了行政区划，隐藏或显示行政区划操作按钮
 */
function refreshxzqhoperbtn() {
    var selnode = $("#divxzqhlist").etreegetselectednode();
    if (selnode == null) {
        $("#newxzqhbtn").hide();
        $("#delxzqhbtn").hide();
        $("#updatexzqhbtn").hide();
    } else {
        if (selnode.type == 1) {
            $("#delxzqhbtn").hide();
        } else {
            $("#delxzqhbtn").show();
        }
        if (selnode.type == 3) {
            $("#newxzqhbtn").hide();
        } else {
            $("#newxzqhbtn").show();
        }

        $("#updatexzqhbtn").show();
    }
}

function newzzztdictload() {
    // 获得当前选中的节点
    var selnode = $("#divdictlist").etreegetselectednode();
    if (selnode != null) {
        var name = selnode.nametype;
        var namedesc = selnode.name;
        $("#newzzztdicttype").text(namedesc);

        $("#newzzztdictname").val('');
        $("#newzzztdictname").validateTargetBind({
            notempty: {}
        });

        $("#btnzzztSave").unbind('click');
        $("#btnzzztSave").bind('click', newzzztdict);

        $("#dialogaddnewzzzt").modal('show');
    }
}

function newzzztdict() {
    var selnode = $("#divdictlist").etreegetselectednode();
    if (selnode != null) {
        $("#divaddzzztform").validateForm(function () {
            var name = selnode.nametype;
            var type = $("#zzzttype").val();
            var typedesc = $("#newzzztdictname").val();

            ajax('dic/adddic', {
                'loginid': $("#userid").val(),
                'name': name,
                'namedesc': type,
                'attrdesc': typedesc
            }, function (result) {
                if (ifResultOK(result)) {
                    $("#divdictlist").etreeclicknode(selnode.id);
                } else {
                    alert(getResultDesc(result));
                }
                // 隐藏弹窗
                $("#dialogaddnewzzzt").modal('hide');
            });
        });

    }
}

function delzzztdictload(id, type, typedesc) {
    $("#lbzzztdel").text(type + "/" + typedesc);
    $("#dialogzzztdel").modal('show');
    $("#btnzzztDel").unbind('click');
    $("#btnzzztDel").bind('click', function () {
        delzzztdict(id);
    });
}

function delzzztdict(id) {
    var selnode = $("#divdictlist").etreegetselectednode();
    if (selnode != null) {

        ajax('dic/deldic', {
            'loginid': $("#userid").val(),
            'id': id,
            'name': 'zzzt'
        }, function (result) {
            if (ifResultOK(result)) {
                $("#divdictlist").etreeclicknode(selnode.id);
            } else {
                alert(getResultDesc(result));
            }
            // 隐藏弹窗
            $("#dialogzzztdel").modal('hide');
        });
    }
}

function updatezzztdictload(id, type, typedesc) {
    var selnode = $("#divdictlist").etreegetselectednode();
    if (selnode != null) {
        $("#updatezzztdicttype").text(selnode.name);

        $("#updatezzzttype").val(type);

        $("#updatezzztdictname").val(typedesc);
        $("#updatezzztdictname").validateTargetBind({
            notempty: {}
        });
        $("#btnzzztUpdate").unbind('click');
        $("#btnzzztUpdate").bind('click', function () {
            updatezzztdict(id);
        });
        $("#dialogzzztupdate").modal('show');
    }
}

function updatezzztdict(id) {
    var selnode = $("#divdictlist").etreegetselectednode();
    if (selnode != null) {
        $("#divupdatezzztform").validateForm(function () {
            var name = selnode.nametype;
            var type = $("#updatezzzttype").val();
            var typedesc = $("#updatezzztdictname").val();

            ajax('dic/updatedic', {
                'loginid': $("#userid").val(),
                'id': id,
                'name': name,
                'namedesc': type,
                'attrdesc': typedesc
            }, function (result) {
                if (ifResultOK(result)) {
                    $("#divdictlist").etreeclicknode(selnode.id);
                } else {
                    alert(getResultDesc(result));
                }
                // 隐藏弹窗
                $("#dialogzzztupdate").modal('hide');
            });
        });
    }
}
