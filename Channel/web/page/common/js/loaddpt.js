// {
// --id: 如果不指定id，默认会自动生成，并由接口返回
// --hint: 自动提示
// --disabled: true/false,禁止选择日期
// --defaultval:
// --selectfn:  //选中后的事件
// --autoajax: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// ----}是否自动提交
// --validators:{
// ----notempty:{
// ----},
// --.....
// --}
// }
$.fn.addseldpt = function (options, rootid, onelayer, compfn) {
    var hint, disabled, id, defaultval, autoajax, validators, selectfn;
    if (options != null) {
        id = options.id;
        hint = options.hint;
        disabled = options.disabled;
        defaultval = options.defaultval;
        autoajax = options.autoajax;
        validators = options.validators;
        selectfn = options.selectfn;
    }
    if (id == null || id == '') {
        id = 'input' + rand(0, 999999999);
    }
    if ($('#' + id).length <= 0) {
        var div = $('<div id="div' + id + '" class="dropdown"></div>')
        var btn = $('<button class="btn btn-default dropdown-toggle btn-block"'
            + 'type="button" id="'
            + id
            + '" data-toggle="dropdown"'
            + 'aria-haspopup="true" aria-expanded="false" selitem="">'
            + '<p id="p'
            + id
            + '" class="text-left" style="margin:0;width:80%;float:left;overflow:hidden;">'
            + (hint == null ? '请选择组织机构' : hint) + '</p>'
            + '<span class="caret pull-right" style="margin-top: 7px;"></span>'
            + '</button>');
        var ul = $('<ul id="' + id + 'ul-1" class="dropdown-menu"'
            + 'style="list-style-type: none;width:300px;max-height:500px;overflow:auto;"'
            + 'aria-labelLedby="div' + id + '"></ul>');
        div.append(btn);
        div.append(ul);
        $(this).append(div);
        // 如果设置了disabled，则
        if (disabled != null) {
            if (disabled == true) {
                btn.attr('disabled', '');
            } else if (disabled == false) {
                btn.removeAttr('disabled');
            }
        }

        // 如果设置了autoajax，则
        if (autoajax != null) {
            autoajax.attr = 'selitem';
            btn.autoajaxBind(autoajax);
        }

        // 如果设置了validators，则
        if (validators != null) {
            btn.validateTargetClear();
            // 为每一个validator添加attr:selitem字段
            for (var validator in validators) {
                validators[validator].attr = 'selitem';
            }
            btn.validateTargetBind(validators);
        }

        loadseldpt(id + 'ul-1', id, defaultval, selectfn, rootid, onelayer, compfn);
    } else {
        if (defaultval != null && defaultval != '') {
            $('#' + (id + 'ul-1')).etreeclicknode(defaultval);
        }
    }
    return id;
}

function loadseldpt(containerdomid, label, defaultval, selectfn, rootid, onelayer, compfn) {
    if (onelayer == null)
        onelayer = false;
    if (rootid == null)
        rootid = -1;
    $('#' + containerdomid).etree({
        id: '-1',
        rootid: rootid,
        isroot: 1,
        onelayer: onelayer,
        containerdomid: containerdomid,
        childrendatafn: dptsrvfn,
        createnodefn: createdptsrvfn,
        selectfn: selectfn
    }, function (node) {
        node.label = label;
        node.defaultval = defaultval;
    }, function (node) {
        if (node.id == defaultval) {
            $("#" + containerdomid).etreeclicknode(node.id);
        }
    }, compfn);
}

function dptsrvfn(pnode, fncallback) {
    var containerdomid = pnode.containerdomid;

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

    ajax('dpt/queryalldpt', data, function (data) {
        if (ifResultOK(data)) {
            var records = getResultRecords(data);
            if (records) {
                var nodes = new Array();
                for (var i = 0; i < records.data.length; i++) {
                    var nd = records.data[i];
                    var name = nd.name;
                    var subcnt = nd.subcnt;
                    var id = nd.id;

                    nodes.push({
                        id: id,
                        name: name,
                        subcnt: onelayer ? 0 : subcnt,
                        selectfn: pnode.selectfn,
                        clickfn: function (node, event) {
                            if ($(event.target).is('span') || $(event.target).hasClass('spandiv')) {
                                event.stopPropagation();
                            } else {
                                $('#p' + node.label).html(node.name);
                                $('#' + node.label).attr('selitem', node.id);
                                verifytarget(node.label);
                                if (pnode.selectfn != null)
                                    pnode.selectfn();
                            }
                            if (node.expanded == 1) {
                                $("#" + containerdomid + "_span" + node.id).removeClass('icon-caret-right');
                                $("#" + containerdomid + "_span" + node.id).addClass('icon-caret-down');
                            } else {
                                $("#" + containerdomid + "_span" + node.id).addClass('icon-caret-right');
                                $("#" + containerdomid + "_span" + node.id).removeClass('icon-caret-down');
                            }
                        },
                        childrendatafn: onelayer ? null : dptsrvfn,
                        createnodefn: createdptsrvfn,
                        containerdomid: containerdomid,
                        inited: true,
                        expanded: true

                    });
                }
                fncallback(nodes);
            }
        }
    });
}

function createdptsrvfn(node, container) {
    container.attr('response-click', true);
    container.attr('response-select', true);

    $div = $('<div class="spandiv" response-click="true" response-expand="true"  style="padding-top:13px;width:17px;float:left;height:40px;"/>');

    if (node.subcnt > 0) {
        if (node.expanded != null && node.expanded == '1' || node.expanded == true)
            $div
                .append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-expand=true response-click=true style="color:black;" class=\"icon-caret-down\"></span>'));
        else
            $div
                .append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-expand=true response-click=true style="color:black;" class=\"icon-caret-right\"></span>'));
    }

    container.append($div);

    container
        .append($('<a response-select="true" response-click=true class="nomargin nopadding" style="color:inherit;width:80%;overflow:hidden;">'
            + '<label response-select="true" response-click=true style="color:inherit;margin-left:5px;">'
            + node.name + '</label></a>'));
}

function loadselxzqh(containerdomid, label, defaultval, selectfn, rootid, onelayer, compfn, beforenodeappendfn) {
    if (onelayer == null)
        onelayer = false;
    if (rootid == null)
        rootid = -1;
    $('#' + containerdomid).etree({
        id: '-1',
        rootid: rootid,
        isroot: 1,
        onelayer: onelayer,
        containerdomid: containerdomid,
        childrendatafn: xzqhsrvfn,
        createnodefn: createxzqhsrvfn,
        selectfn: selectfn
    }, function (node) {
        node.label = label;
        node.defaultval = defaultval;
        node.inited = '1';
        node.expanded = '1';
        if (beforenodeappendfn != null)
            beforenodeappendfn();
    }, function (node) {
        if (node.id == defaultval) {
            $("#" + containerdomid).etreeclicknode(node.id);
        }
    }, compfn);
}

function xzqhsrvfn(pnode, fncallback) {
    var containerdomid = pnode.containerdomid;

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

    ajax(
        'xzqh/queryallxzqh',
        data,
        function (data) {
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
                            name: name,
                            type: type,
                            subcnt: onelayer ? 0 : subcnt,
                            selectfn: pnode.selectfn,
                            clickfn: function (node, event) {
                                if ($(event.target).is('span') ||$(event.target).hasClass('spandiv')) {
                                    event.stopPropagation();
                                } else {
                                    $('#p' + node.label).html(node.name);
                                    $('#' + node.label).attr('selitem', node.id);
                                    verifytarget(node.label);
                                    if (pnode.selectfn != null)
                                        pnode.selectfn();
                                }
                                if (node.expanded == 1) {
                                    $("#" + containerdomid + "_span" + node.id).removeClass('icon-caret-right');
                                    $("#" + containerdomid + "_span" + node.id).addClass('icon-caret-down');
                                } else {
                                    $("#" + containerdomid + "_span" + node.id).addClass('icon-caret-right');
                                    $("#" + containerdomid + "_span" + node.id).removeClass('icon-caret-down');
                                }
                            },
                            childrendatafn: onelayer ? null : xzqhsrvfn,
                            createnodefn: createxzqhsrvfn,
                            containerdomid: containerdomid,
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

    $div = $('<div class="spandiv" response-click="true" response-expand="true"  style="padding-top:13px;width:17px;float:left;height:40px;"/>');


    if (node.subcnt > 0) {
        if (node.expanded != null && node.expanded == '1' || node.expanded == true)
            $div.append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-expand=true response-click=true style="color:black;" class=\"icon-caret-down\"></span>'));
        else
            $div.append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-expand=true response-click=true style="color:black;" class=\"icon-caret-right\"></span>'));
    }

    container.append($div);
    container.append($('<a response-select="true" response-click=true class="nomargin nopadding" style="color:inherit;width:80%;overflow:hidden;">'
        + '<label response-select="true" response-click=true  style="color:inherit;margin-left:5px;">' + node.name + '</label></a>'));
}

// {
// --id: 如果不指定id，默认会自动生成，并由接口返回
// --hint: 自动提示
// --disabled: true/false,禁止选择日期
// --defaultval:
// --autoajax: {
// ----name: 提交名
// ----defaultval: 如果输入为空，由以此默认值进行提交
// ----}是否自动提交
// --validators:{
// ----notempty:{
// ----},
// --.....
// --}
// }
$.fn.addselxzqh = function (options, rootid, onelayer, compfn, beforenodeappendfn) {
    var hint, disabled, id, defaultval, autoajax, validators, selectfn;
    if (options != null) {
        id = options.id;
        hint = options.hint;
        disabled = options.disabled;
        defaultval = options.defaultval;
        autoajax = options.autoajax;
        validators = options.validators;
        selectfn = options.selectfn;
    }
    if (id == null || id == '') {
        id = 'input' + rand(0, 999999999);
    }
    if ($('#' + id).length <= 0) {
        var div = $('<div id="div' + id + '" class="dropdown"></div>')
        var btn = $('<button class="btn btn-default dropdown-toggle btn-block"'
            + 'type="button" id="'
            + id
            + '" data-toggle="dropdown"'
            + 'aria-haspopup="true" aria-expanded="false" selitem="">'
            + '<p id="p'
            + id
            + '" class="text-left" style="margin:0;width:80%;float:left;overflow:hidden;">'
            + (hint == null ? '请选择行政区划' : hint) + '</p>'
            + '<span class="caret pull-right" style="margin-top: 7px;"></span>'
            + '</button>');
        var ul = $('<ul id="' + id + 'ul-1" class="dropdown-menu"'
            + 'style="list-style-type: none;width:250px;max-height:500px;overflow:auto;"'
            + 'aria-labelLedby="div' + id + '"></ul>');
        div.append(btn);
        div.append(ul);
        $(this).append(div);
        // 如果设置了disabled，则
        if (disabled != null) {
            if (disabled == true) {
                btn.attr('disabled', '');
            } else if (disabled == false) {
                btn.removeAttr('disabled');
            }
        }

        // 如果设置了autoajax，则
        if (autoajax != null) {
            autoajax.attr = 'selitem';
            btn.autoajaxBind(autoajax);
        }

        // 如果设置了validators，则
        if (validators != null) {
            btn.validateTargetClear();
            // 为每一个validator添加attr:selitem字段
            for (var validator in validators) {
                validators[validator].attr = 'selitem';
            }
            btn.validateTargetBind(validators);
        }

        loadselxzqh(id + 'ul-1', id, defaultval, selectfn, rootid, onelayer, compfn, beforenodeappendfn);
    } else {
        if (defaultval != null && defaultval != '') {
            $('#' + (id + 'ul-1')).etreeclicknode(defaultval);
        }
    }
    return id;
}

// 添加辖区,xqstr以1:长兴县;2:德清县
$.fn.addselxqxzqh = function (options,xqstr) {
    var hint, disabled, id, defaultval, autoajax, validators, selectfn;
    if (options != null) {
        id = options.id;
        hint = options.hint;
        disabled = options.disabled;
        defaultval = options.defaultval;
        autoajax = options.autoajax;
        validators = options.validators;
        selectfn = options.selectfn;
    }
    if (id == null || id == '') {
        id = 'input' + rand(0, 999999999);
    }
    if ($('#' + id).length <= 0) {
        var div = $('<div id="div' + id + '" class="dropdown"></div>')
        var btn = $('<button class="btn btn-default dropdown-toggle btn-block"'
            + 'type="button" id="'
            + id
            + '" data-toggle="dropdown"'
            + 'aria-haspopup="true" aria-expanded="false" selitem="">'
            + '<p id="p'
            + id
            + '" class="text-left" style="margin:0;width:80%;float:left;overflow:hidden;">'
            + (hint == null ? '请选择行政区划' : hint) + '</p>'
            + '<span class="caret pull-right" style="margin-top: 7px;"></span>'
            + '</button>');
        var ul = $('<ul id="' + id + 'ul-1" class="dropdown-menu"'
            + 'style="list-style-type: none;width:250px;"'
            + 'aria-labelLedby="div' + id + '"></ul>');
        div.append(btn);
        div.append(ul);
        $(this).append(div);
        // 如果设置了disabled，则
        if (disabled != null) {
            if (disabled == true) {
                btn.attr('disabled', '');
            } else if (disabled == false) {
                btn.removeAttr('disabled');
            }
        }

        // 如果设置了autoajax，则
        if (autoajax != null) {
            autoajax.attr = 'selitem';
            btn.autoajaxBind(autoajax);
        }

        // 如果设置了validators，则
        if (validators != null) {
            btn.validateTargetClear();
            // 为每一个validator添加attr:selitem字段
            for (var validator in validators) {
                validators[validator].attr = 'selitem';
            }
            btn.validateTargetBind(validators);
        }

        loadselxqxzqh(id + 'ul-1', id, defaultval, selectfn, xqstr);
    } else {
        if (defaultval != null && defaultval != '') {
            $('#' + (id + 'ul-1')).etreeclicknode(defaultval);
        }
    }
    return id;
}


function loadselxqxzqh(containerdomid, label, defaultval, selectfn, xqstr, compfn, beforenodeappendfn) {
    $('#' + containerdomid).etree({
        id: '-1',
        xqstr :xqstr,
        containerdomid: containerdomid,
        childrendatafn: xqxzqhsrvfn,
        createnodefn: createxzqhsrvfn,
        selectfn: selectfn
        }, function (node) {
        node.label = label;
        node.defaultval = defaultval;
        node.inited = '1';
        node.expanded = '1';
        if (beforenodeappendfn != null)
            beforenodeappendfn();
    }, function (node) {
        if (node.id == defaultval) {
            $("#" + containerdomid).etreeclicknode(node.id);
        }
    }, compfn);
}


function xqxzqhsrvfn(pnode, fncallback) {
    var containerdomid = pnode.containerdomid;

    var xqstr = pnode.xqstr;
    var nodes = new Array();

    var xqs = xqstr.split(';');
    for (var i = 0;i < xqs.length;i++){
        var ss = xqs[i].split(',');
        var id = ss[0];
        var name = ss[1];
        var type = ss[2];

        nodes.push({
            id: id,
            name: name,
            type: type,
            subcnt: 0,
            selectfn: pnode.selectfn,
            clickfn: function (node, event) {
                if ($(event.target).is('span')) {
                    event.stopPropagation();
                } else {
                    $('#p' + node.label).html(node.name);
                    $('#' + node.label).attr('selitem', node.id);
                    verifytarget(node.label);
                    if (pnode.selectfn != null)
                        pnode.selectfn();
                }
                if (node.expanded == 1) {
                    $("#" + containerdomid + "_span" + node.id).removeClass('icon-caret-right');
                    $("#" + containerdomid + "_span" + node.id).addClass('icon-caret-down');
                } else {
                    $("#" + containerdomid + "_span" + node.id).addClass('icon-caret-right');
                    $("#" + containerdomid + "_span" + node.id).removeClass('icon-caret-down');
                }
            },
            createnodefn: createxzqhsrvfn,
            containerdomid: containerdomid
        });
    }


    fncallback(nodes);
}

$.fn.addselbyxzqh = function (options, rootid, onelayer, compfn, beforenodeappendfn) {
    var hint, disabled, id, defaultval, autoajax, validators, selectfn;
    if (options != null) {
        id = options.id;
        hint = options.hint;
        disabled = options.disabled;
        defaultval = options.defaultval;
        autoajax = options.autoajax;
        validators = options.validators;
        selectfn = options.selectfn;
    }
    if (id == null || id == '') {
        id = 'input' + rand(0, 999999999);
    }
    if ($('#' + id).length <= 0) {
        var div = $('<div id="div' + id + '" class="dropdown"></div>')
        var btn = $('<button class="btn btn-default dropdown-toggle btn-block"'
            + 'type="button" id="'
            + id
            + '" data-toggle="dropdown"'
            + 'aria-haspopup="true" aria-expanded="false" selitem="">'
            + '<p id="p'
            + id
            + '" class="text-left" style="margin:0;width:80%;float:left;overflow:hidden;">'
            + (hint == null ? '请选择行政区划' : hint) + '</p>'
            + '<span class="caret pull-right" style="margin-top: 7px;"></span>'
            + '</button>');
        var ul = $('<ul id="' + id + 'ul-1" class="dropdown-menu"'
            + 'style="list-style-type: none;width:250px;max-height:500px;overflow:auto;"'
            + 'aria-labelLedby="div' + id + '"></ul>');
        div.append(btn);
        div.append(ul);
        $(this).append(div);
        // 如果设置了disabled，则
        if (disabled != null) {
            if (disabled == true) {
                btn.attr('disabled', '');
            } else if (disabled == false) {
                btn.removeAttr('disabled');
            }
        }

        // 如果设置了autoajax，则
        if (autoajax != null) {
            autoajax.attr = 'selitem';
            btn.autoajaxBind(autoajax);
        }

        // 如果设置了validators，则
        if (validators != null) {
            btn.validateTargetClear();
            // 为每一个validator添加attr:selitem字段
            for (var validator in validators) {
                validators[validator].attr = 'selitem';
            }
            btn.validateTargetBind(validators);
        }

        loadselbyxzqh(id + 'ul-1', id, defaultval, selectfn, rootid, onelayer, compfn, beforenodeappendfn);
    } else {
        if (defaultval != null && defaultval != '') {
            $('#' + (id + 'ul-1')).etreeclicknode(defaultval);
        }
    }
    return id;
}

function loadselbyxzqh(containerdomid, label, defaultval, selectfn, rootid, onelayer, compfn, beforenodeappendfn) {
    if (onelayer == null)
        onelayer = false;
    if (rootid == null)
        rootid = -1;
    $('#' + containerdomid).etree({
        id: '-1',
        rootid: defaultval,
        isroot: 1,
        onelayer: onelayer,
        containerdomid: containerdomid,
        childrendatafn: xzqhsrvfn,
        createnodefn: createxzqhsrvfn,
        selectfn: selectfn
    }, function (node) {
        node.label = label;
        node.defaultval = defaultval;
        node.inited = '1';
        node.expanded = '1';
        if (beforenodeappendfn != null)
            beforenodeappendfn();
    }, function (node) {
        if (node.id == defaultval) {
            $("#" + containerdomid).etreeclicknode(node.id);
        }
    }, compfn);
}