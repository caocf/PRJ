var curselxzqh = 1;
$(document).ready(function () {
    commoninit("组织机构管理");

    resize();
    $(window).resize(function (event) {
        resize();
    });

    if (hasPerm('VIEW_SHENDPT') || hasPerm('MAN_SHENDPT')) {
        loadleftdpt(-1);
    }
    else {
        if ((hasPerm('VIEW_SHIDPT') || hasPerm('MAN_SHIDPT')) && szshiju != -1) {
            var szshiju = parseInt($("#szshiju").val());
            loadleftdpt(szshiju);
        }else{
            var szdpt = parseInt($("#szdpt").val());
            loadleftdpt(szdpt);
        }
    }

    $("#AddNew").on('show.bs.modal', function () {
        $("#divseladddpt").empty();
        $("#divseladdxzqh").empty();

        var seldpt = $("#divleftdpt").etreegetselectednode();
        if (seldpt == null) {
            alert("请先选择组织机构");
        }
        if (seldpt == null) {
            $("#divseladdxzqh").addselxzqh({id: "seladdxzqh"});
        }
        else {
            $("#divseladdxzqh").addselbyxzqh({id: "seladdxzqh", defaultval: seldpt.xzqh});
        }

        $("#inputDptName").val('');
        $("#inputDptName").validateTargetBind({notempty: {}});
        $("#txadptdesc").val('');
        $("#txadptdesc").validateTargetBind({notempty: {}, length: {max: 200}});
        $("#seladddpt").validateTargetBind({notempty: {attr: 'selitem'}});
        $("#seladdxzqh").validateTargetBind({notempty: {attr: 'selitem'}});
    });

    $("#UpdateDpt").on('show.bs.modal', function () {
        $("#divselupdatedpt").empty();
        $("#divselupdatexzqh").empty();
        $("#inputDptNameupdate").val('');
        $("#inputDptNameupdate").validateTargetBind({notempty: {}});
        $("#txadptdesc").val('');
        $("#txadptdescupdate").validateTargetBind({notempty: {}, length: {max: 200}});
        loadupdateinfo();
    });

    $("#Del").on('show.bs.modal', function () {
        var delnode = $("#divleftdpt").etreegetselectednode();
        var name = delnode.name;

        $("#lbdel").text(name);
    });

    $("#btnXzqh1").on('click', function () {
        $("#divXzqh").removeClass('hide');
        $("#divXzqh").addClass('show');
    });

    $("#btnXzqh1Update").on('click', function () {
        $("#divXzqhUpdate").removeClass('hide');
        $("#divXzqhUpdate").addClass('show');
    });

    $("#btnDpt1").on('click', function () {
        $("#divDpt").removeClass('hide');
        $("#divDpt").addClass('show');
    });

    $("#btnDpt1Update").on('click', function () {
        $("#divDptUpdate").removeClass('hide');
        $("#divDptUpdate").addClass('show');
    });
    $("#btnDel").on('click', function () {
        deldpt();
    });

    $("#btnSave").on('click', function () {
        adddpt();
    });

    $("#btnUpdate").on('click', function () {
        updatedpt();
    });

});

function resize() {
    var clientheight = window.innerHeight;
    $("#divleftdpt").css('height', (clientheight - 52 - 80) + 'px');
    $("#divrightrow").css('height', (clientheight - 53 - 80 - 50) + 'px');
}

function loadleftdpt(pid) {
    $("#divleftdpt").etree({
        id: -1,
        path: '-1',
        pid: -1,
        rootid: pid,
        isroot: 1,
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
                .append($('<span  id="' + node.containerdomid + '_span' + node.id + '" response-click="true" response-expand=true style="color:black;" response-expand=true class=\"icon-caret-right\"></span>'));
    }
    container.append($div);
    container
        .append($('<a response-click="true" respose-select=true class="nomargin nopadding" style="color:inherit;width:80%;overflow:hidden;">'
            + '<label response-click=true response-select=true style="color:inherit;margin-left:5px;">'
            + node.name + '</label></a>'));
}

function leftdptdatafn(pnode, fncallback) {
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
            var nodes = new Array();
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                if (records) {
                    for (var i = 0; i < records.data.length; i++) {
                        var nd = records.data[i];
                        var type = nd.type;
                        var name = nd.name;
                        var subcnt = nd.subcnt;
                        var id = nd.id;
                        var xzqh = nd.xzqh;
                        var defaultroles = nd.defaultroles;

                        nodes.push({
                                id: id,
                                type: type,
                                pid: pnode.id,
                                path: pnode.path + ',' + id,
                                name: name,
                                xzqh: xzqh,
                                defaultroles: defaultroles,
                                subcnt: subcnt,
                                clickfn: function (node, event) {
                                    if ($(event.target).is('span')) {
                                        event.stopPropagation();
                                    } else {
                                        currseldpt = node.id;
                                        curselxzqh = node.xzqh;
                                        loaddptdtl(currseldpt, curselxzqh);
                                    }
                                    if (node.expanded == 1) {
                                        $("#" + node.containerdomid + "_span" + node.id).removeClass('icon-caret-right');
                                        $("#" + node.containerdomid + "_span" + node.id).addClass('icon-caret-down');
                                    } else {
                                        $("#" + node.containerdomid + "_span" + node.id).addClass('icon-caret-right');
                                        $("#" + node.containerdomid + "_span" + node.id).removeClass('icon-caret-down');
                                    }
                                }
                                ,
                                childrendatafn: subcnt != 0 ? leftdptdatafn : null,
                                createnodefn: leftcreatedptfn,
                                containerdomid: pnode.containerdomid,
                                inited: type == 0 || type == 1 ? true : false,
                                expanded: type == 0 || type == 1 ? true : false
                            }
                        )
                        ;
                    }
                }
            }

            fncallback(nodes);
        }, function () {
            fncallback(null);
        }
    )
}

function deldpt() {
    var delnode = $("#divleftdpt").etreegetselectednode();
    $.ajax({
        url: 'dpt/deldpt',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val(),
            'id': delnode.id
        },
        success: function (data) {
            $("#Del").modal('hide');
            if (ifResultOK(data)) {
                var delpnode = $("#divleftdpt").etreegetnode(delnode.pid);
                $("#divleftdpt").etreereload(delpnode.pid, function () {
                    checkbuttonstatus();
                });

                //清空右侧
                $("#inputName").text("");
                $("#taDesc").text("");
                $("#inputPdpt").text("");
                $("#inputXzqh").text("");
                $("#btnEdit").addClass('hide');
                $("#btnDelete").addClass('hide');
            } else {
                alert(getResultDesc(data));
            }
        }
    });
}

function updatedpt() {
    $("#formupdatedpt").validateForm(function () {
        var updatenode = $("#divleftdpt").etreegetselectednode();
        var data = new Data();
        data.addDataObj('loginid', $("#userid").val());
        data.addDataObj('id', updatenode.id);
        data.addDataObj('name', $("#inputDptNameupdate").val());
        data.addDataObj('xzqh', $("#selupdatexzqh").attr('selitem'));
        data.addDataObj('dptdesc', $("#txadptdescupdate").val());

        if ($("#updatedeptxzqh").length > 0) {
            var seldefaultxzqhs = $("#updatedeptxzqh").attr('selitem');
            if (seldefaultxzqhs != null && seldefaultxzqhs != '') {
                data.addDataListStr('manxzqhs', seldefaultxzqhs);
            }
        }
        if ($("#selupdatedefaultroles").length > 0) {
            var seldefaultroles = $("#selupdatedefaultroles").attr('selitem');
            if (seldefaultroles != null && seldefaultroles != '') {
                data.addDataListStr('defaultroles', seldefaultroles);
            }
        }
        $.ajax({
            url: 'dpt/updatedpt',
            type: 'post',
            dataType: 'json',
            data: data,
            success: function (data) {
                if (ifResultOK(data)) {
                    $("#divleftdpt").etreereload(updatenode.pid, function () {
                        $("#divleftdpt").etreeclicknode(updatenode.id);
                        checkbuttonstatus();
                    });
                } else {
                    alert(getResultDesc(data));
                }
                $("#UpdateDpt").modal('hide');
            }
        });
    });
}

function adddpt() {
    $("#formadddpt").validateForm(function () {
        var node = $("#divseladdxzqh").etreegetselectednode();

        var data = new Data();
        data.addDataObj('loginid', $("#userid").val());
        data.addDataObj('name', $("#inputDptName").val());
        data.addDataObj('xzqh', $("#seladdxzqh").attr('selitem'));
        data.addDataObj('dptdesc', $("#txadptdesc").val());
        data.addDataObj('pid', $("#divleftdpt").etreegetselectednode().id);

        if ($("#adddeptxzqh").length > 0) {
            var seldefaultxzqhs = $("#adddeptxzqh").attr('selitem');
            if (seldefaultxzqhs != null && seldefaultxzqhs != '') {
                data.addDataListStr('manxzqhs', seldefaultxzqhs);
            }
        }
        if ($("#seldefaultroles").length > 0) {
            var seldefaultroles = $("#seldefaultroles").attr('selitem');
            if (seldefaultroles != null && seldefaultroles != '') {
                data.addDataListStr('defaultroles', seldefaultroles);
            }
        }
        $.ajax({
            url: 'dpt/adddpt',
            type: 'post',
            dataType: 'json',
            data: data,
            success: function (data) {
                if (ifResultOK(data)) {
                    // 隐藏添加弹出窗
                    $("#AddNew").modal('hide');
                    var selnode = $("#divleftdpt").etreegetselectednode();
                    $("#divleftdpt").etreereload(selnode.pid, function () {
                        checkbuttonstatus();
                    });

                } else {
                    alert(getResultDesc(data));
                }
            }
        });
    });
}

function checkbuttonstatus() {
    var seldpt = $("#divleftdpt").etreegetselectednode();
    if (seldpt == null) {
        $("#btnAdd").addClass('hide');
        $("#btnEdit").addClass('hide');
        $("#btnDelete").addClass('hide');
    }
    else {
        $("#btnAdd").removeClass('hide');
        $("#btnEdit").removeClass('hide');

        if (parseInt(seldpt.type) == 3)
            $("#btnAdd").addClass('hide');
        else
            $("#btnAdd").removeClass('hide');

        if (seldpt.pid != -1)
            $("#btnDelete").removeClass('hide');
        else
            $("#btnDelete").addClass('hide');
    }
}

function loaddptdtl(id, xzqh) {
    checkbuttonstatus();
    initcityxzqh(xzqh);
    initrolexzqh(xzqh);
    initdptinfo(id);
}
//左边点击 出现基本信息的 处理基本信息
function initdptinfo(id) {
    $.ajax({
        url: 'dpt/querydptinfo',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val(),
            'id': id
        },
        success: function (data) {
            if (ifResultOK(data)) {
                // 机构名称
                //显示模块
                var name = getResultMap(data).dpt.name;
                $("#inputName").text(name);
                // 机构描述
                var desc = getResultMap(data).dpt.dptdesc;
                $("#taDesc").text(desc);
                var dpts = getResultMap(data).dpts;
                $("#inputPdpt").text(dpts);
                var xzqhs = getResultMap(data).xzqhs;
                $("#inputXzqh").text(xzqhs);
                var defaultxzqhs = getResultMap(data).defaultxzqhs;
                var defaultxzqhstr = '';
                if (defaultxzqhs != null) {
                    defaultxzqhstr = defaultxzqhs.join(',');
                }
                $("#tadefaultxzqhs").text(defaultxzqhstr);
                var defaultroles = getResultMap(data).defaultroles;
                var defaultrolestr = '';
                if (defaultroles != null) {
                    defaultrolestr = defaultroles.join(',');
                }
                $("#tadefaultroles").text(defaultrolestr);
            } else {

            }
        }
    });
}
//处理默认行政区划
function initcityxzqh(xzqh) {
    $("#divadddeptxzqh").empty();
    $("#divupdatedeptxzqh").empty();
    var xzqhdata = {};
    ajax("querycityxzqh", {
            id: xzqh
        }, function (result) {
            if (ifResultOK(result)) {
                var objs = getResultObj(result);
                var defxzqhs = getResultMap(result).defxzqhs;
                var len = objs.length;
                for (var i = 0; i < len; i++) {
                    var obj = objs[i];
                    var id = obj.id;
                    var name = obj.name;
                    xzqhdata[id] = name;
                }
                $("#divadddeptxzqh").addmultiselect({
                    id: "adddeptxzqh",
                    validators: {
                        notempty: {attr: 'selitem'}
                    },
                    data: xzqhdata
                })
                $("#divupdatedeptxzqh").addmultiselect({
                    id: "updatedeptxzqh",
                    validators: {
                        notempty: {attr: 'selitem'}
                    },
                    data: xzqhdata
                })
            }
        }
    );
}
//处理默认角色
function initrolexzqh(xzqh) {
    $("#divseldefaultroles").empty();
    $("#divselupdatedefaultroles").empty();
    var roledata = {};
    ajax("queryRolesByXzqh", {
            xzqhid: xzqh
        }, function (result) {
            if (ifResultOK(result)) {
                var groups = getResultObj(result);
                for (var i = 0; i < groups.length; i++) {
                    var group = groups[i];
                    var id = group.id;
                    var name = group.name;
                    roledata[id] = name;
                }
                $("#divseldefaultroles").addmultiselect({
                    id: "seldefaultroles",
                    data: roledata
                })
                $("#divselupdatedefaultroles").addmultiselect({
                    id: "selupdatedefaultroles",
                    data: roledata
                })
            }
        }
    );
}

function loadupdateinfo() {
    $.ajax({
        url: 'dpt/querydptinfo',
        type: 'post',
        dataType: 'json',
        data: {
            'loginid': $("#userid").val(),
            'id': currseldpt
        },
        success: function (data) {
            if (ifResultOK(data)) {
                // 机构名称
                var name = getResultMap(data).dpt.name;
                $("#inputDptNameupdate").val(name);

                // 机构描述
                var desc = getResultMap(data).dpt.dptdesc;
                $("#txadptdescupdate").val(desc);

                var pdpt = getResultMap(data).pid;
                $("#divselupdatedpt").addseldpt({id: "selupdatedpt", defaultval: pdpt});

                var xzqh = getResultMap(data).dpt.xzqh;
                $("#divselupdatexzqh").addselbyxzqh({id: "selupdatexzqh", defaultval: xzqh});

                var defaultxzqhs = getResultMap(data).dpt.managexzqh;
                if (defaultxzqhs != null && defaultxzqhs != '' && defaultxzqhs != 'null') {
                    $("#divupdatedeptxzqh").addmultiselect({
                        id: "updatedeptxzqh",
                        defaultval: defaultxzqhs
                    })
                }
                var defaultroles = getResultMap(data).dpt.defaultroles;
                if (defaultroles != null && defaultroles != '' && defaultroles != 'null') {
                    $("#divselupdatedefaultroles").addmultiselect({
                        id: "selupdatedefaultroles",
                        defaultval: defaultroles
                    })
                }
            }
        }
    });
}

function loadxzqh(pid) {

    if (pid == -1 || $("#xzqhli" + pid).attr('inited') == '0') {
        $
            .ajax({
                url: 'xzqh/queryallxzqh',
                type: 'post',
                dataType: 'json',
                data: {
                    'loginid': $("#userid").val(),
                    'id': pid
                },
                success: function (data) {
                    if (ifResultOK(data)) {
                        var records = getResultObj(data);
                        if (records) {
                            var layer = $('#xzqhli' + pid).attr('layer');
                            if (pid != -1) {
                                layer++;
                                $('#xzqhli' + pid)
                                    .append(
                                    $("<ul class='ultree' style='list-style:none' id='xzqhul"
                                        + pid + "'>"));
                            } else {
                                $('#xzqhul' + pid).empty();
                                layer = 1;
                            }
                            for (var i = 0; i < records.length; i++) {
                                var nd = records[i];
                                var name = nd.xzqhdm.name;
                                var type = nd.xzqhdm.type;
                                var id = nd.xzqhdm.id;
                                var subcnt = nd.countchild;

                                var newli = $("<li layer='"
                                    + layer
                                    + "'inited='0' expand='0' id='xzqhli"
                                    + id + "'></li>");

                                var padding = 20 * layer;

                                var newdiv = $('<div onclick="$(\'[id*=xzqhdiv]\').removeClass(\'divtreeactive\');$(\'#xzqhdiv'
                                    + id
                                    + '\').addClass(\'divtreeactive\');selxzqh('
                                    + id
                                    + ')" id="xzqhdiv'
                                    + id
                                    + '" class="divtree" style="padding-left:'
                                    + padding + 'px"></div');

                                newli.append(newdiv);
                                if (subcnt > 0) {
                                    newdiv
                                        .append($("<span onclick='loadxzqh("
                                            + id
                                            + ");event.stopPropagation();' style='color:black;float:left;margin:12px 15px 0 0;' id='xzqhspan"
                                            + id
                                            + "' class='icon-caret-right icon-large'></span>"));
                                    newdiv
                                        .append($("<a id='xzqha"
                                            + id
                                            + "' style='float:left;width:80%;overflow:hidden;'><label id='labxzqh"
                                            + id
                                            + "' class='liLable'>"
                                            + name + "</label></a>"));
                                } else {
                                    newdiv
                                        .append($("<a id='xzqha"
                                            + id
                                            + "' style='float:left;width:80%;overflow:hidden;margin-left:26px;'><label id='labxzqh"
                                            + id
                                            + "' class='liLable'>"
                                            + name + "</label></a>"));
                                }

                                $("#xzqhul" + pid).append(newli);

                                if (subcnt > 0) {
                                    $('#xzqhspan' + id).click();
                                }
                            }
                            $("#xzqhli" + pid).attr('expand', '1');
                            $("#xzqhli" + pid).attr('inited', '1');

                            $("#xzqhspan" + pid)
                                .addClass('icon-caret-down');
                            $("#xzqhspan" + pid).removeClass(
                                'icon-caret-right');
                        }
                    } else {

                    }
                }
            });
    } else {
        if ($("#xzqhli" + pid).attr('expand') == '0') {
            $("#xzqhul" + pid).show();
            $("#xzqhli" + pid).attr('expand', '1');
            $("#xzqhspan" + pid).addClass('icon-caret-down');
            $("#xzqhspan" + pid).removeClass('icon-caret-right');
        } else {
            $("#xzqhul" + pid).hide();
            $("#xzqhli" + pid).attr('expand', '0');
            $("#xzqhspan" + pid).addClass('icon-caret-right');
            $("#xzqhspan" + pid).removeClass('icon-caret-down');
        }
    }
}

function selxzqh(id) {
    var content = $("#labxzqh" + id).text();
    $("#labxzqhname").text(content);
    $("#labxzqhid").val(id);
    $("#divXzqh1").removeClass('open');
}

function loaddpt(pid) {
    var label = 'dptnew';
    if (pid == -1 || $("#" + label + "li" + pid).attr('inited') == '0') {
        $
            .ajax({
                url: 'dpt/queryalldpt',
                type: 'post',
                dataType: 'json',
                data: {
                    'loginid': $("#userid").val(),
                    'id': pid
                },
                success: function (data) {
                    if (ifResultOK(data)) {
                        var records = getResultRecords(data);
                        if (records) {
                            var layer = $('#' + label + 'li' + pid).attr(
                                'layer');

                            if (pid != -1) {
                                layer++;
                                $('#' + label + 'li' + pid).append(
                                    $("<ul class='ultree' id='" + label
                                        + "ul" + pid + "'>"));
                            } else {
                                $('#' + label + 'ul' + pid).empty();
                                layer = 1;
                            }
                            for (var i = 0; i < records.data.length; i++) {
                                var nd = records.data[i];
                                var name = nd.name;
                                var subcnt = nd.subcnt;
                                var id = nd.id;
                                var newli = $("<li layer='" + layer
                                    + "' inited='0' expand='0' id='"
                                    + label + "li" + id + "'></li>");

                                var padding = 20 * layer;
                                var newdiv = null;
                                newdiv = $('<div onclick="$(\'[id*='
                                    + label
                                    + 'div]\').removeClass(\'divtreeactive\');$(\'#'
                                    + label
                                    + 'div'
                                    + id
                                    + '\').addClass(\'divtreeactive\');seldpt('
                                    + id
                                    + ',\''
                                    + name
                                    + '\')" id="'
                                    + label
                                    + 'div'
                                    + id
                                    + '" class="divtree" style="padding-left:'
                                    + padding + 'px"></div');

                                newli.append(newdiv);
                                if (subcnt > 0) {
                                    newdiv
                                        .append($('<span onclick="loaddpt('
                                            + id
                                            + ');event.stopPropagation();" style="color:black;float:left;margin:12px 15px 0 0;" id=\"'
                                            + label
                                            + 'span'
                                            + id
                                            + '\" class=\"icon-caret-right icon-large\"></span>'));
                                    newdiv
                                        .append($('<a class="nomargin nopadding" id="'
                                            + label
                                            + 'a'
                                            + id
                                            + '" style="float:left;overflow:hidden;width:80%;"><label>'
                                            + name + '</label></a>'));
                                } else {
                                    newdiv
                                        .append($('<a class="nomargin nopadding" id="'
                                            + label
                                            + 'a'
                                            + id
                                            + '" style="float:left;overflow:hidden;width:80%;"><label>'
                                            + name + '</label></a>'));
                                }

                                $("#" + label + "ul" + pid).append(newli);

                                if (subcnt > 0) {
                                    $('#' + label + 'span' + id).click();
                                }
                            }
                            $("#" + label + "li" + pid).attr('expand', '1');
                            $("#" + label + "li" + pid).attr('inited', '1');

                            $("#" + label + "span" + pid).addClass(
                                'icon-caret-down');
                            $("#" + label + "span" + pid).removeClass(
                                'icon-caret-right');
                        }
                    } else {

                    }
                }
            });
    } else {
        if ($("#" + label + "li" + pid).attr('expand') == '0') {
            $("#" + label + "ul" + pid).show();
            $("#" + label + "li" + pid).attr('expand', '1');
            $("#" + label + "span" + pid).addClass('icon-caret-down');
            $("#" + label + "span" + pid).removeClass('icon-caret-right');
        } else {
            $("#" + label + "ul" + pid).hide();
            $("#" + label + "li" + pid).attr('expand', '0');
            $("#" + label + "span" + pid).addClass('icon-caret-right');
            $("#" + label + "span" + pid).removeClass('icon-caret-down');
        }
    }
}

function seldpt(id, name) {
    $("#labdptname").text(name);
    $("#labdptid").val(id);
    $("#divDpt1").removeClass('open');
}

function loadxzqhupdate(pid) {
    if (pid == -1 || $("#xzqhliupdate" + pid).attr('inited') == '0') {
        $
            .ajax({
                url: 'xzqh/queryallxzqh',
                type: 'post',
                dataType: 'json',
                data: {
                    'loginid': $("#userid").val(),
                    'id': pid
                },
                success: function (data) {
                    if (ifResultOK(data)) {
                        var records = getResultObj(data);
                        if (records) {
                            var layer = $('#xzqhliupdate' + pid).attr(
                                'layer');
                            if (pid != -1) {
                                layer++;
                                $('#xzqhliupdate' + pid)
                                    .append(
                                    $("<ul class='ultree' style='list-style:none' id='xzqhulupdate"
                                        + pid + "'>"));
                            } else {
                                layer = 1;
                                $('#xzqhulupdate' + pid).empty();
                            }
                            for (var i = 0; i < records.length; i++) {
                                var nd = records[i];
                                var name = nd.xzqhdm.name;
                                var type = nd.xzqhdm.type;
                                var id = nd.xzqhdm.id;
                                var subcnt = nd.countchild;

                                var newli = $("<li layer='"
                                    + layer
                                    + "' inited='0' expand='0' id='xzqhliupdate"
                                    + id + "'></li>");
                                var padding = 20 * layer;
                                var newdiv = $('<div onclick="$(\'[id*=xzqhdivupdate]\').removeClass(\'divtreeactive\');$(\'#xzqhdivupdate'
                                    + id
                                    + '\').addClass(\'divtreeactive\');selxzqhupdate('
                                    + id
                                    + ')" id="xzqhdivupdate'
                                    + id
                                    + '" class="divtree" style="padding-left:'
                                    + padding + 'px"></div');

                                newli.append(newdiv);
                                if (subcnt > 0) {
                                    newdiv
                                        .append($("<span onclick='loadxzqhupdate("
                                            + id
                                            + ");event.stopPropagation();' style='color:black;float:left;margin:12px 15px 0 0;' id='xzqhspanupdate"
                                            + id
                                            + "' class='icon-caret-right icon-large'></span>"));

                                    newdiv
                                        .append($("<a id='xzqhaupdate"
                                            + id
                                            + "' style='float:left;width:80%;overflow:hidden;'><label id='labxzqhupdate"
                                            + id
                                            + "' class='liLable'>"
                                            + name + "</label></a>"));
                                } else {
                                    newdiv
                                        .append($("<a id='xzqhaupdate"
                                            + id
                                            + "' style='float:left;width:80%;overflow:hidden;margin-left:26px;'><label id='labxzqhupdate"
                                            + id
                                            + "' class='liLable'>"
                                            + name + "</label></a>"));
                                }

                                $("#xzqhulupdate" + pid).append(newli);

                                if (subcnt > 0) {
                                    $('#xzqhspanupdate' + id).click();
                                }
                            }
                            $("#xzqhliupdate" + pid).attr('expand', '1');
                            $("#xzqhliupdate" + pid).attr('inited', '1');

                            $("#xzqhspanupdate" + pid).addClass(
                                'icon-caret-down');
                            $("#xzqhspanupdate" + pid).removeClass(
                                'icon-caret-right');
                        }
                    } else {

                    }
                }
            });
    } else {
        if ($("#xzqhliupdate" + pid).attr('expand') == '0') {
            $("#xzqhulupdate" + pid).show();
            $("#xzqhliupdate" + pid).attr('expand', '1');
            $("#xzqhspanupdate" + pid).addClass('icon-caret-down');
            $("#xzqhspanupdate" + pid).removeClass('icon-caret-right');
        } else {
            $("#xzqhulupdate" + pid).hide();
            $("#xzqhliupdate" + pid).attr('expand', '0');
            $("#xzqhspanupdate" + pid).addClass('icon-caret-right');
            $("#xzqhspanupdate" + pid).removeClass('icon-caret-down');
        }
    }
}

function selxzqhupdate(id) {
    var content = $("#labxzqhupdate" + id).text();
    $("#labxzqhnameUpdate").text(content);
    $("#labxzqhidUpdate").val(id);
    $("#divXzqh1Update").removeClass('open');
}

function loaddptupdate(pid) {
    var label = 'dptupdate';
    if (pid == -1 || $("#" + label + "li" + pid).attr('inited') == '0') {
        $
            .ajax({
                url: 'dpt/queryalldpt',
                type: 'post',
                dataType: 'json',
                data: {
                    'loginid': $("#userid").val(),
                    'id': pid
                },
                success: function (data) {
                    if (ifResultOK(data)) {
                        var records = getResultRecords(data);
                        if (records) {
                            var layer = $('#' + label + 'li' + pid).attr(
                                'layer');

                            if (pid != -1) {
                                layer++;
                                $('#' + label + 'li' + pid).append(
                                    $("<ul class='ultree' id='" + label
                                        + "ul" + pid + "'>"));
                            } else {
                                $('#' + label + 'ul' + pid).empty();
                                layer = 1;
                            }
                            for (var i = 0; i < records.data.length; i++) {
                                var nd = records.data[i];
                                var name = nd.name;
                                var subcnt = nd.subcnt;
                                var id = nd.id;
                                var newli = $("<li layer='" + layer
                                    + "' inited='0' expand='0' id='"
                                    + label + "li" + id + "'></li>");


                                var padding = 20 * layer;
                                var newdiv = null;
                                newdiv = $('<div onclick="$(\'[id*='
                                    + label
                                    + 'div]\').removeClass(\'divtreeactive\');$(\'#'
                                    + label
                                    + 'div'
                                    + id
                                    + '\').addClass(\'divtreeactive\');seldptupdate('
                                    + id
                                    + ',\''
                                    + name
                                    + '\')" id="'
                                    + label
                                    + 'div'
                                    + id
                                    + '" class="divtree" style="padding-left:'
                                    + padding + 'px"></div');
                                newli.append(newdiv);
                                if (subcnt > 0) {
                                    newdiv
                                        .append($('<span onclick="loaddptupdate('
                                            + id
                                            + ');event.stopPropagation();" style="color:black;float:left;margin:12px 15px 0 0;" id=\"'
                                            + label
                                            + 'span'
                                            + id
                                            + '\" class=\"icon-caret-right icon-large\"></span>'));
                                    newdiv
                                        .append($('<a class="nomargin nopadding" id="'
                                            + label
                                            + 'a'
                                            + id
                                            + '" style="float:left;overflow:hidden;width:80%;"><label>'
                                            + name + '</label></a>'));
                                } else {
                                    newdiv
                                        .append($('<a class="nomargin nopadding" id="'
                                            + label
                                            + 'a'
                                            + id
                                            + '" style="float:left;overflow:hidden;width:80%;"><label>'
                                            + name + '</label></a>'));
                                }

                                $("#" + label + "ul" + pid).append(newli);

                                if (subcnt > 0) {
                                    $('#' + label + 'span' + id).click();
                                }
                            }
                            $("#" + label + "li" + pid).attr('expand', '1');
                            $("#" + label + "li" + pid).attr('inited', '1');

                            $("#" + label + "span" + pid).addClass(
                                'icon-caret-down');
                            $("#" + label + "span" + pid).removeClass(
                                'icon-caret-right');
                        }
                    } else {

                    }
                }
            });
    } else {
        if ($("#" + label + "li" + pid).attr('expand') == '0') {
            $("#" + label + "ul" + pid).show();
            $("#" + label + "li" + pid).attr('expand', '1');
            $("#" + label + "span" + pid).addClass('icon-caret-down');
            $("#" + label + "span" + pid).removeClass('icon-caret-right');
        } else {
            $("#" + label + "ul" + pid).hide();
            $("#" + label + "li" + pid).attr('expand', '0');
            $("#" + label + "span" + pid).addClass('icon-caret-right');
            $("#" + label + "span" + pid).removeClass('icon-caret-down');
        }
    }
}

function seldptupdate(id, name) {
    $("#labdptnameUpdate").text(name);
    $("#labdptidUpdate").val(id);
    $("#divDpt1Update").removeClass('open');

}