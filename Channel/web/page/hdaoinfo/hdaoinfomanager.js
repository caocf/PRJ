var chartfswlx = 0;
var oldjd = 0;
var oldwd = 0;
var newjd = 0;
var newwd = 0;
$(document).ready(function () {
    initui();
    $("#searchdropdowndialog").click(function (event) {
        event.stopPropagation();
    });
    $(window).click(function (event) {
        hideleftpopmenu();
        if (event.target.id != "searchcontent")
            hidesearchdropdown();
    });
    $(window).resize(function (event) {
        resize();
    });
});

function resize() {
    // 通过屏幕高度设置相应的滚动区域高度
    var clientheight = window.innerHeight;
    $("#divleft").css('height', (clientheight - 53 - $("#searchbardiv").height()) + 'px');
    $("#hdaotree").css('height', ($("#divleft").height() - $("#leftbottomdiv").height()) + 'px');
    $("#divright").css('height', ($("#divleft").height() - 20) + 'px');
    $("#divrightleft").css('height', ($("#divright").height()) + 'px');
    $("#divrightright").css('height', ($("#divright").height()) + 'px');
    $("#hduantree").css('height', ($("#divrightleft").height() - $("#divrightlefttop").height() - ($("#divrightleftbottom").height() + 16)) + 'px');

    if ($("#fswtablediv").is(":visible"))
        $("#detailinfodiv").css('height', ($("#divrightright").height() - $("#divrightrighttop").height() - $("#fswtablediv").height()) + 'px');
    else
        $("#detailinfodiv").css('height', ($("#divrightright").height() - $("#divrightrighttop").height()) + 'px');
    $("#fswtablediv").css('width', $("#divrightright").width() + 'px');

    $(".dataTables_scrollHeadInner").css('width', $("#fswtablediv").width() + 'px');
    $(".dataTable").css('width', $("#fswtablediv").width() + 'px');
    $(".dataTables_scrollBody").css('height', getTableScrollY() + 'px');
}

function getTableScrollY() {
    return $("#fswtablediv").height() - $("#fswtableoperbar").height() - 42;
}

/**
 * 公共的初始化，会在页面加载时调用
 */
function initui() {
    if ($("#sfgg").val() == '1') {
        $("#liguganhdao").addClass('active');
    } else {
        $("#lizhixianhdao").addClass('active');
    }

    resize();

    // 未登录返回登录页面
    var username = $("#username").val();
    if (username == null || username == "" || username == "null") {
        window.location.href = $("#basePath").val() + "page/login/login.jsp";
    }

    /*
     * // 返回按钮 $('#back').click(function() { window.location.href =
     * $("#basePath").val() + "page/home/home.jsp"; });
     */

    // 点击时标签页进行切换
    $('#menu a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    // 标签页切换，新标签显示后回调函数，此函数中加载相关数据。
    $('#tabhangdao').bind(
        'click',
        function () {
            window.location.href = $("#basePath").val()
                + "page/hdaoinfo/hdaoinfomanager.jsp";
        });
    // 标签页切换，新标签显示后回调函数，此函数中加载相关数据。
    $('#tabmapinfo').bind(
        'click',
        function () {
            window.location.href = $("#basePath").val()
                + "page/hdaoinfo/mapinfomanager.jsp";
        });
    // 标签页切换，新标签显示后回调函数，此函数中加载相关数据。
    $('#tabimport').bind(
        'click',
        function () {
            window.location.href = $("#basePath").val()
                + "page/hdaoinfo/hdaoimport.jsp";
        });
    /*
     * $('#tabmap').bind( 'click', function() { window.location.href =
     * $("#basePath").val() + "page/hdaoinfo/hdaomap.jsp"; });
     */

    // 查看图片信息弹出窗时
    $("#modalpicview").on('shown.bs.modal', function () {
        var width = $("#imgviewbody").width();
        $('#imgviewbanner').css('width', width);
    });

    // 航道新增时，动态加载航道所需新增信息

    $("#btnnewgghdao").click(function () {
        loadhdaoaddinfo(1);
    });
    $("#btnnewzxhdao").click(function () {
        loadhdaoaddinfo(0);
    });

    $("#btnnewhduan").click(function () {
        var hdao = $("#hdaotree").etreegetselectednode();
        if (hdao == null) {
            alert("请选择要新增在哪条航道上");
        }
        loadhduanaddinfo(hdao);
    });

    // 航道新增按钮点击事件
    $("#btnmodalhdaonew").bind('click', function () {
        addhdao();
    });

    // 航段新增按钮点击事件
    $("#btnmodalhduannew").bind('click', function () {
        addhduan();
    });

    // 航道编辑按钮点击事件
    $("#btnmodalhdaoedit").bind('click', function () {
        edithdao();
    });

    // 航段编辑按钮点击事件
    $("#btnmodalhduanedit").bind('click', function () {
        edithduan();
    });


    // 载入附属物新增按钮点击事件
    $("#btnmodalfswnew").bind('click', function () {
        loadaddchart(chartfswlx);
    });

    // 附属物新增按钮点击事件
    $("#btnmodaladdchart").bind('click', function () {
        addfsw();
        $("#modaladdchart").modal('hide');
    });

    // 载入附属物编辑按钮点击事件
    $("#btnmodalfswedit").bind('click', function () {
        loadupdatechart(chartfswlx);
    });

    // 附属物编辑按钮点击事件
    $("#btnmodalupdatechart").bind('click', function () {
        editfsw();
        $("#modalupdatechart").modal('hide');
    });

    // 初始化左侧航道菜单折叠按钮事件
    $("#leftmenu").bind('click', function () {
        if ($("#leftmenu").attr('expand') == "1") {
            $("#leftmenu").removeClass('menuleftshouqi');
            $("#leftmenu").addClass('menuleftzhankai');
            $("#leftmenu").attr('expand', '0');

            $("#divleft").addClass('hide');
            $("#divright").css("margin-left", "15px");
            $("#fswtablediv").css("left", "331px");

            $("#fswtablediv").css('width', $("#divrightright").width() + 'px');

        } else {
            $("#leftmenu").removeClass('menuleftzhankai');
            $("#leftmenu").addClass('menuleftshouqi');
            $("#leftmenu").attr('expand', '1');

            $("#divleft").removeClass('hide');
            $("#divright").css("margin-left", "250px");
            $("#fswtablediv").css("left", "566px");
            $("#fswtablediv").css('width', $("#divrightright").width() + 'px');
        }
    });

    // 删除航道按钮事件
    $("#btnmodalhdaodel").bind('click', function () {
        delhdao();
    });

    // 删除航段按钮事件
    $("#btnmodalhduandel").bind('click', function () {
        delhduan();
    });

    // 删除附属物按钮事件
    $("#btnmodalfswdel").bind('click', function () {
        delfsw();
    });
}

function loadhdaotree() {
    $("#hdaotree").empty();
    $("#hdaotree").etree({id: '-1', childrendatafn: hangdaofolderdatafn});
}

function createhangdaofolderfn(node, container) {
    container.attr('response-click', true);
    container.attr('response-expand', true);
    container.css('margin-left', '-20px');
    var newhduanspan = null;
    if (node.expanded == true || node.expanded == "1") {
        newhduanspan = $('<span response-click=true response-expand=true id="hangdaofolder'
            + node.id
            + '" class="icon-folder-open" style="color:#337ab7"></span>');
    } else {
        newhduanspan = $('<span response-click=true response-expand=true id="hangdaofolder'
            + node.id
            + '" class="icon-folder-close" style="color:#337ab7"></span>');
    }
    var newa = $("<label response-click=true response-expand=true style=\"font-weight:normal;\">&nbsp;&nbsp;"
        + node.name + "</label>");
    container.append(newhduanspan);
    container.append(newa);
}

function hangdaofolderdatafn(pnode, fncallback) {
    var folderarray = new Array();

    folderarray.push({
        id: -11,
        sfgg: 1,
        expanded: true,
        inited: true,
        clickfn: function (node) {
            if (node.expanded == '1') {
                $('#hangdaofolder' + node.id).removeClass('icon-folder-close');
                $('#hangdaofolder' + node.id).addClass('icon-folder-open');
            } else {
                $('#hangdaofolder' + node.id).removeClass('icon-folder-open');
                $('#hangdaofolder' + node.id).addClass('icon-folder-close');
            }
        },
        createnodefn: createhangdaofolderfn,
        childrendatafn: hangdaodatafn,
        name: "骨干航道",
    });

    folderarray.push({
        id: -10,
        sfgg: 0,
        expanded: true,
        inited: true,
        clickfn: function (node) {
            if (node.expanded == '1') {
                $('#hangdaofolder' + node.id).removeClass('icon-folder-close');
                $('#hangdaofolder' + node.id).addClass('icon-folder-open');
            } else {
                $('#hangdaofolder' + node.id).removeClass('icon-folder-open');
                $('#hangdaofolder' + node.id).addClass('icon-folder-close');
            }
        },
        createnodefn: createhangdaofolderfn,
        childrendatafn: hangdaodatafn,
        name: "支线航道",
    });

    fncallback(folderarray);
}

function initsearchhangdao() {
    $("#selecthduansearchhdao").empty();
    $("#selecthduansearchhdao").append($('<option value="-1">全部航道</option>'));
    $("#selectfswsearchhdao").empty();
    $("#selectfswsearchhdao").append($('<option value="-1">全部航道</option>'));

    queryhangdao(-1, function (hdaolist) {
        for (var i = 0; i < hdaolist.length; i++) {
            var hdao = hdaolist[i];
            $("#selecthduansearchhdao").append($('<option value="' + hdao.id + '">' + hdao.hdmc + '</option>'));
            $("#selectfswsearchhdao").append($('<option value="' + hdao.id + '">' + hdao.hdmc + '</option>'));
        }
    });
}

function queryhangdao(sfgg, callbk) {
    var selxzqh = $("#selsearchxzqh").attr('selitem');
    if (selxzqh == "" || selxzqh == null)
        selxzqh = -1;
    ajax('hangdao/queryallhangdao', {
        'xzqh': selxzqh,
        'sfgg': sfgg
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

function hangdaodatafn(pnode, fncallback) {
    queryhangdao(pnode.sfgg, function (hdaolist) {
        var nodes = new Array();
        for (var i = 0; i < hdaolist.length; i++) {
            var hdao = hdaolist[i];
            nodes.push({
                id: hdao.id,
                name: hdao.hdmc,
                hdaoid: hdao.id,
                hdaobh: hdao.hdbh,
                hdaomc: hdao.hdmc,
                clicked: pnode.sfgg == 1 && i == 0 ? true : false,
                createnodefn: createhdaofn,
                clickfn: function (hdao, event) {
                    loadhdaodetail(hdao);
                    loadhduantree(hdao);
                },
                enterfn: function (hdao) {
                    $('#hdaospandel' + hdao.hdaoid).css('cursor', 'pointer');
                    $('#hdaospandel' + hdao.hdaoid).removeClass('hide');
                },
                leavefn: function (hdao) {
                    $('#hdaospandel' + hdao.hdaoid).addClass('hide');
                }
            });
        }

        fncallback(nodes);
    });
}

function createhdaofn(hdao, container) {
    container.attr('response-click', true);
    container.attr('response-select', true);
    container.css('margin-left', '-30px');
    var hdaoid = hdao.hdaoid;
    var hdaomc = hdao.hdaomc;

    var newa = $('<a class="hdaoa" response-click=true response-select=true style="color:inherit"> ' +
        '<label class="hdaolabel" response-click=true response-select=true style="color:inherit">' + hdaomc + '</label></a>');

    container.append(newa);

    if (hasPerm('MAN_SHIHDAO') || hasPerm('MAN_XQHDAO')) {
        var newdelspan = $('<span id="hdaospandel' + hdaoid + '" class="icon-trash icon-large hdaodelspan hide"'
            + 'onclick="event.stopPropagation();loaddelhdaoinfo(\''
            + hdaoid
            + '\',\''
            + hdaomc
            + '\');"></span>');
    }
    container.append(newdelspan);
}

function initsearchhangduan(hdaoid) {
    if (hdaoid != "-1") {
        queryhangduan(hdaoid, function (hduanlist) {
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

function queryhangduan(hdaoid, callbk, failedfn) {
    var selxzqh = $("#selsearchxzqh").attr('selitem');
    if (selxzqh == "" || selxzqh == null)
        selxzqh = -1;
    ajax('hangduan/queryhangduanbysshdid', {
        'loginid': $("#userid").val(),
        'sshdid': hdaoid,
        'xzqh': selxzqh
    }, function (data) {
        if (ifResultOK(data)) {
            var records = getResultObj(data);
            if (records) {
                callbk(records);
            }
        }
    }, failedfn);
}

function loadhduantree(hdao, compfn) {
    $('#hduantree').empty();
    $("#hduantree").etree({
        id: -1,
        hdaoid: hdao.hdaoid,
        hdaomc: hdao.hdaomc,
        hdaobh: hdao.hdaobh,
        childrendatafn: hduansrvfn
    }, null, null, compfn);
}

function hduansrvfn(hdao, fncallback) {
    queryhangduan(hdao.hdaoid, function (hduanlist) {
        var nodes = new Array();
        for (var i = 0; i < hduanlist.length; i++) {
            var hduan = hduanlist[i].hangduan;

            // 添加航段文件夹
            var hduanid = hduan.id;
            var hduanbh = hduan.hdbh;// 航段编号
            var namestart = hduan.hdqdmc;
            var nameend = hduan.hdzdmc;
            var hduanxzqhid = hduan.hdszxzqh;
            var hduanxzqhmc = hduan.xzqh;
            var hduanmc = namestart + "-" + nameend;

            nodes.push({
                id: hduanid,
                hdaoid: hdao.hdaoid,
                hdaomc: hdao.hdaomc,
                hdaobh: hdao.hdaobh,
                hduanid: hduanid,
                hduanbh: hduanbh,
                hduanmc: hduanmc,
                hduanxzqhid: hduanxzqhid,
                hduanxzqhmc: hduanxzqhmc,
                childrendatafn: fswclasssrvfn,
                createnodefn: createhduanfn,
                selectedfn: function (event, hduan) {
                    $('#hduanspanmenufolder' + hduan.hduanid).css('color', 'white');
                },
                unselectedfn: function (event, hduan) {
                    $('#hduanspanmenufolder' + hduan.hduanid).css('color', '#337ab7');
                },
                clickfn: function (hduan, event) {
                    if (hduan.expanded == '1') {
                        $('#hduanspanmenufolder' + hduan.hduanid).removeClass('icon-folder-close');
                        $('#hduanspanmenufolder' + hduan.hduanid).addClass('icon-folder-open');
                    } else {
                        $('#hduanspanmenufolder' + hduan.hduanid).removeClass('icon-folder-open');
                        $('#hduanspanmenufolder' + hduan.hduanid).addClass('icon-folder-close');
                    }
                    loadhduandetail(hduan);
                },
                enterfn: function (hduan) {
                    $('#hduanspandel' + hduan.hduanid).removeClass('hide');
                    $('#hduanspandel' + hduan.hduanid).css('cursor', 'pointer');
                },
                leavefn: function (hduan) {
                    $('#hduanspandel' + hduan.hduanid).addClass('hide');
                },
            });
        }
        fncallback(nodes);
    }, function () {
        fncallback(null);
    });

}

function createhduanfn(hduan, container) {
    container.attr('response-click', true);
    container.attr('response-expand', true);
    container.attr('response-select', true);
    var newhduanspan = $('<span response-click=true response-expand=true id="hduanspanmenufolder'
        + hduan.hduanid
        + '" class="icon-folder-close hduanspan hduanspanfolder"></span>');
    var newa = $("<a class='hduana' response-select=true response-click=true response-expand=true style='color:inherit;'>" +
        "<label class='hduanspanmenulabel hduanlabel' response-select=true  response-click=true response-expand=true style='color:inherit;'>&nbsp;"
        + hduan.hduanmc + "</label></a>");


    container.append(newhduanspan);
    container.append(newa);
    if (hasPerm('MAN_SHIHDAO') || hasPerm('MAN_XQHDAO')) {
        var newdelspan = $('<span id="hduanspandel' + hduan.hduanid + '" class="icon-trash icon-large hdaodelspan hide" '
            + 'onclick="event.stopPropagation();loaddelhduaninfo('
            + hduan.hdaoid
            + ','
            + hduan.hduanid
            + ',\''
            + hduan.hduanmc
            + '\',\''
            + hduan.hduanbh
            + '\');"></span>');

        container.append(newdelspan);
    }
}

function fswclasssrvfn(hduan, fncallback) {
    // 加载附属物分类列表
    ajax('appurtenancetype/queryallhduanparent', {
            'loginid': $("#userid").val(),
            'hduanid': hduan.hduanid
        },
        function (data) {
            var nodes = new Array();
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                var fswcntlist = getResultMap(data).fswcnt;
                if (records) {

                    for (var i = 0; i < records.data.length; i++) {
                        var fswclass = records.data[i];
                        // 添加附属物分类文件夹
                        var fswclassid = fswclass.id;
                        var fswclassmc = fswclass.name;
                        var fswcnt = fswcntlist[i];
                        nodes.push({
                            id: hduan.hduanid + "_" + fswclassid,
                            name: name,
                            hdaoid: hduan.hdaoid,
                            hdaobh: hduan.hdaobh,
                            hdaomc: hduan.hdaomc,
                            hduanid: hduan.hduanid,
                            hduanbh: hduan.hduanbh,
                            hduanmc: hduan.hduanmc,
                            hduanxzqhid: hduan.hduanxzqhid,
                            hduanxzqhmc: hduan.hduanxzqhmc,
                            fswclassid: fswclassid,
                            fswclassmc: fswclassmc,
                            fswcnt: fswcnt,
                            createnodefn: createfswclassfn,
                            childrendatafn: fswsecondclassfn,
                            clickfn: function (fswclass, event) {
                                if (fswclass.expanded == '1') {
                                    $("#fswclassspanfolder"
                                        + fswclass.hduanid
                                        + "_" + fswclass.fswclassid).removeClass('icon-folder-close-alt');
                                    $("#fswclassspanfolder"
                                        + fswclass.hduanid
                                        + "_" + fswclass.fswclassid).addClass('icon-folder-open-alt');
                                } else {
                                    $("#fswclassspanfolder"
                                        + fswclass.hduanid
                                        + "_" + fswclass.fswclassid).removeClass('icon-folder-open-alt');
                                    $("#fswclassspanfolder"
                                        + fswclass.hduanid
                                        + "_" + fswclass.fswclassid).addClass('icon-folder-close-alt');
                                }
                            },
                            enterfn: function (fswclass) {
                                $('#fswclassspan'
                                    + fswclass.hduanid
                                    + "_" + fswclass.fswclassid).removeClass('hide');
                                $('#fswclassspan'
                                    + fswclass.hduanid
                                    + "_" + fswclass.fswclassid).css('cursor', 'pointer');
                            },
                            leavefn: function (fswclass) {
                                $('#fswclassspan'
                                    + fswclass.hduanid
                                    + "_" + fswclass.fswclassid).addClass('hide');
                            },
                            width: '100%',
                            height: '40px'
                        });
                    }
                }
            }
            fncallback(nodes);
        }, function () {
            fncallback(null);
        });
}

function createfswclassfn(fswclass, container) {
    container.attr('response-click', true);
    container.attr('response-expand', true);
    var name = fswclass.fswclassmc + " (" + fswclass.fswcnt + ")";
    var newhduanspan = $('<span response-click=true response-expand=true id="fswclassspanfolder'
        + fswclass.hduanid
        + "_"
        + fswclass.fswclassid
        + '" class="icon-folder-close-alt" style="width:10%;color:#337ab7;"></span>');
    var newa = $("<a  response-click=true response-expand=true  style='width:80%;'><label response-click=true response-expand=true  class='fswclasslabel'>&nbsp;"
        + name + "</label></a>");

    container.append(newhduanspan);
    container.append(newa);
    if (hasPerm('MAN_SHIHDAO') || hasPerm('MAN_XQHDAO')) {
        var newmenuspan = $('<span onclick="$(\'#fswclassspan'
            + fswclass.hduanid
            + "_"
            + fswclass.fswclassid
            + '\').dropdown();" data-stopPropagation="true" id="fswclassspan'
            + fswclass.hduanid
            + "_"
            + fswclass.fswclassid
            + '" class="dropdown-toggle icon-reorder menumoreicon hide" data-toggle="dropdown" aria-haspopup="false" aria-expanded="false"></span>');

        var popmenu = $('<ul id="newfswsecondul'
            + fswclass.hduanid
            + '_'
            + fswclass.fswclassid
            + '" class="dropdown-menu dropdown-menu-right popmenuul"></ul>');
        loadfswnewmenu("newfswsecondul" + fswclass.hduanid + '_' + fswclass.fswclassid, fswclass);

        var div = $('<div class=\'dropdown\' style="float:right;"></div>');
        div.append(newmenuspan);
        div.append(popmenu);
        container.append(div);
    }
}

//加载附属物新增弹出菜单
function loadfswnewmenu(ulid, fswclass) {
    ajax('appurtenancetype/querysubclass',
        {
            'loginid': $("#userid").val(),
            'parentid': fswclass.fswclassid
        },
        function (data) {
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                if (records) {
                    for (var i = 0; i < records.data.length; i++) {
                        var secondclass = records.data[i];
                        var id = secondclass.id;
                        var name = secondclass.name;

                        var newli = $('<li data-stopPropagation="true" onclick="loadfswaddinfo($(\'#hduantree\').etreegetnode(\''
                            + fswclass.id + '\'),' + id + ',\'' + name + '\');"><a data-stopPropagation="true" class="btn nopadding nomargin btn-block">'
                            + '<span class="icon-plus"></span>&nbsp;'
                            + name + '</a></li>');
                        $("#" + ulid).append(newli);
                    }
                }
            }
        });
}

function createfswsecondclassfn(fswsecondclass, container) {
    container.attr('response-select', true);
    container.attr('response-click', true);
    // 附属物小图标选择
    var fswicon = 'icon-building';
    var imgicon = '';
    switch (fswsecondclass.fswsecondclassid) {
        case APP_NAVIGATIONMARK:// 航标
            imgicon = 'ic_navigationmark.png';
            break;
        case APP_BRIDGE:// 桥梁
            imgicon = 'ic_bridge.png';
            break;
        case APP_AQUEDUCT:// 渡槽
            imgicon = 'ic_flume.png';
            break;
        case APP_CABLE:// 缆线
            imgicon = 'ic_cabled-twist.png';
            break;
        case APP_PIPELINE:// 管道
            imgicon = 'ic_pipeline.png';
            break;
        case APP_TUNNEL:// 隧道
            imgicon = 'ic_tunnel.png';
            break;
        case APP_KYDOCK:// 客运码头
            imgicon = 'ic_wharf.png';
            break;
        case APP_HYDOCK:// 货运码头
            imgicon = 'ic_wharf.png';
            break;
        case APP_GWDOCK:// 公务码头
            imgicon = 'ic_wharf.png';
            break;
        case APP_SHIPYARD:// 船厂
            break;
        case APP_TAKEOUTFALL:// 取排水
            imgicon = 'ic_outfall.png';
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            imgicon = 'ic_hydrologic-station.png';
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            imgicon = 'ic_guanlizhan.png';
            break;
        case APP_SERVICEAREA:// 服务区
            imgicon = 'ic_service.png';
            break;
        case APP_MOORINGAREA:// 锚泊区
            imgicon = 'ic_roadstead.png';
            break;
        case APP_HUB:// 枢纽
            imgicon = 'ic_key-position.png';
            break;
        case APP_DAM:// 坝
            imgicon = 'ic_dam.png';
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            imgicon = 'ic_revetment.png';
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            imgicon = 'ic_observation-point.png';
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            imgicon = 'ic_movie.png';
            break;
        case APP_MANUALOBSERVATION:
            imgicon = 'ic_observation-point.png';
            break;
        case APP_BOLLARD:// 系缆桩
            imgicon = "ic_bitt.png";
            break;
    }

    var fswspan = null;
    if (imgicon != '') {
        fswspan = $('<img id="fswimg' + fswsecondclass.hduanid
            + "_"
            + fswsecondclass.fswsecondclassid
            + '" src="img/'
            + imgicon
            + '" style="float:left;margin-top:12px;">');
    } else {
        fswspan = $('<span id="fswimg' + fswsecondclass.hduanid
            + "_"
            + fswsecondclass.fswsecondclassid
            + '"  class="'
            + fswicon
            + '" style="float:left;margin-top:12px;"></span>');
    }
    var newa = $("<a response-select=true response-click=true class='fswa' style='color:inherit;float:left;width:70%;'>"
        + "<label response-select=true response-click=true class='fswlabel' style='color:inherit;'>&nbsp;"
        + fswsecondclass.name + " (" + fswsecondclass.fswcnt + ")" + "</label></a>");

    container.append(fswspan);
    container.append(newa);

    if (hasPerm('MAN_SHIHDAO') || hasPerm('MAN_XQHDAO')) {
        var newnewspan = $('<span id="fswsecondclassspan' + fswsecondclass.hduanid
                + "_"
                + fswsecondclass.fswsecondclassid
                + '" class="icon-plus icon-large hdaodelspan hide" style="color:black;"'
                + ' onclick="loadfswaddinfo($(\'#hduantree\').etreegetnode(\'' + fswsecondclass.hduanid
                + "_" + fswsecondclass.fswclassid + "_" + fswsecondclass.fswsecondclassid + '\'),' + fswsecondclass.fswsecondclassid + ',\'' + fswsecondclass.name +
                '\')"></span>'
            )
            ;
        container.append(newnewspan);
    }

}

function fswsecondclassfn(fswclass, fncallback) {
    ajax('appurtenancetype/querysubclass',
        {
            'loginid': $("#userid").val(),
            'parentid': fswclass.fswclassid,
            'hduanid': fswclass.hduanid
        },
        function (data) {
            var nodes = new Array();
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                if (records) {
                    for (var i = 0; i < records.data.length; i++) {
                        var secondclass = records.data[i];
                        var secondclassid = secondclass.id;
                        var fswsecondclassmc = secondclass.name;
                        var fswcnt = secondclass.fswcnt;

                        nodes.push({
                            id: fswclass.hduanid
                            + "_" + fswclass.fswclassid + "_" + secondclassid,
                            name: fswsecondclassmc,
                            type: 'fswsecondclass',
                            hdaoid: fswclass.hdaoid,
                            hdaobh: fswclass.hdaobh,
                            hdaomc: fswclass.hdaomc,
                            hduanid: fswclass.hduanid,
                            hduanbh: fswclass.hduanbh,
                            hduanmc: fswclass.hduanmc,
                            hduanxzqhid: fswclass.hduanxzqhid,
                            hduanxzqhmc: fswclass.hduanxzqhmc,
                            fswclassid: fswclass.fswclassid,
                            fswclassmc: fswclass.fswclassmc,
                            fswsecondclassid: secondclassid,
                            fswsecondclassmc: fswsecondclassmc,
                            fswcnt: fswcnt,
                            selectedfn: function (event, fswsecondclass) {
                                var $img = $('#fswimg' + fswsecondclass.hduanid
                                    + "_"
                                    + fswsecondclass.fswsecondclassid);

                                if ($img.is('img')) {
                                    var src = $img.attr('src');
                                    src = src.replace('.png', '_white.png');
                                    $img.attr('src', src);
                                }
                            },
                            unselectedfn: function (event, fswsecondclass) {
                                var $img = $('#fswimg' + fswsecondclass.hduanid
                                    + "_"
                                    + fswsecondclass.fswsecondclassid);

                                if ($img.is('img')) {
                                    var src = $img.attr('src');
                                    src = src.replace('_white.png', '.png');
                                    $img.attr('src', src);
                                }
                            },
                            createnodefn: createfswsecondclassfn,
                            clickfn: function (node) {
                                loadfsws(node);
                            },
                            enterfn: function (node) {
                                $('#fswsecondclassspan'
                                    + node.hduanid
                                    + "_" + node.fswsecondclassid).removeClass('hide');
                                $('#fswsecondclassspan'
                                    + node.hduanid
                                    + "_" + node.fswsecondclassid).css('cursor', 'pointer');
                            },
                            leavefn: function (node) {
                                $('#fswsecondclassspan'
                                    + node.hduanid
                                    + "_" + node.fswsecondclassid).addClass('hide');
                            },
                        })
                    }
                }
            }
            fncallback(nodes);
        }, function () {
            fncallback(null);
        });
}

function loadfsws(fswsecondclass, defaultfswid, compfn) {
    cleardetail();
    $('#fswtablediv').show();
    resize();
    var scrollY = getTableScrollY();
    scrollY = scrollY + "px";

    $('#btnfswnew').setobj('fsw', fswsecondclass);

    $('#pRightmc').html(fswsecondclass.fswsecondclassmc + "基本信息");


    var columns = [
        {
            mDataProp: null,
            sTitle: '序号'
        }, {
            mDataProp: 'fswbh',
            sTitle: '编号'
        }, {
            mDataProp: 'fswmc',
            sTitle: '名称'
        }];

    switch (parseInt(fswsecondclass.fswsecondclassid)) {
        case APP_NAVIGATIONMARK:// 航标
            columns.push({mDataProp: 'zd1', sTitle: '标志类型'});
            columns.push({mDataProp: 'zd5', sTitle: '支撑方式'});
            columns.push({mDataProp: 'zd6', sTitle: '标志结构'});
            columns.push({mDataProp: 'zd2', sTitle: '光学性质'});
            columns.push({mDataProp: 'zd3', sTitle: '灯质信号'});
            columns.push({mDataProp: 'zd4', sTitle: '灯标颜色'});
            break;
        case APP_BRIDGE:// 桥梁
            columns.push({mDataProp: 'zd1', sTitle: '结构形式'});
            columns.push({mDataProp: 'zd2', sTitle: '用途分类'});
            break;
        case APP_AQUEDUCT:// 渡槽
            columns.push({mDataProp: 'ytfl', sTitle: '用途分类'});
            break;
        case APP_CABLE:// 缆线
            columns.push({mDataProp: 'zd1', sTitle: '缆线种类'});
            break;
        case APP_PIPELINE:// 管道
            columns.push({mDataProp: 'cyxs', sTitle: '穿越型式'});
            break;
        case APP_TUNNEL:// 隧道
            columns.push({mDataProp: 'ytfl', sTitle: '用途分类'});
            break;
        case APP_KYDOCK:// 客运码头
            columns.push({mDataProp: 'zd1', sTitle: '结构类型'});
            break;
        case APP_HYDOCK:// 货运码头
            columns.push({mDataProp: 'zd1', sTitle: '结构类型'});
            break;
        case APP_GWDOCK:// 公务码头
            columns.push({mDataProp: 'zd1', sTitle: '结构类型'});
            break;
        case APP_SHIPYARD:// 船厂
            columns.push({mDataProp: 'xzcwj', sTitle: '修造船吨级'});
            break;
        case APP_TAKEOUTFALL:// 取排水口
            columns.push({mDataProp: 'zd1', sTitle: '类型'});
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            break;
        case APP_SERVICEAREA:// 服务区
            break;
        case APP_MOORINGAREA:// 锚泊区
            break;
        case APP_HUB:// 枢纽
            columns.push({mDataProp: 'zd2', sTitle: '通航类型'});
            columns.push({mDataProp: 'zd1', sTitle: '型式'});
            columns.push({mDataProp: 'zd3', sTitle: '过船设施位置'});
            break;
        case APP_DAM:// 坝
            columns.push({mDataProp: 'zd1', sTitle: '类型'});
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            break;
        case APP_MANUALOBSERVATION:// 人工流量观测点
            break;
        case APP_BOLLARD:// 系缆桩
            break;
    }
    //columns需要根据数据结果动态去更改

    var columndefs = [
        {
            targets: 0,
            sWidth: "50px"
        }
    ];
    $("#fswtablerow").empty();
    $("#fswtablerow").append($('<table id="tableFsw" class="" cellspacing="0" width="100%" />'));
    $("#tableFsw").attr('inited', '0');
    $("#tableFsw").adddatatable({
        url: 'appurtenance/queryappurtenances',
        data: {
            'loginid': $("#userid").val(),
            'pid': fswsecondclass.fswsecondclassid,
            'sshduanid': fswsecondclass.hduanid
        },
        rows: 0,
        datafn: function (data) {
            var records = getResultRecords(data);
            var bhpre = getResultMap(data).bhpre;
            var ret = {};
            var list = new Array();
            if (records != null) {
                var recordslen = records.data.length;
                for (var i in records.data) {
                    var dt = {};
                    var pojo = records.data[i];
                    var pojolen = pojo.length;
                    //是否要显示字典信息
                    if (pojolen == undefined) {
                        var sj = pojo;
                    } else {
                        var sj = pojo[0];
                        //字典赋值
                        for (var j = 1; j < pojolen; j++) {
                            var zd = pojo[j];
                            dt["zd" + j] = zd["attrdesc"];
                        }
                    }

                    // 添加附属物
                    var fswid = sj.id;
                    var fswbh = sj.bh;
                    var fswmc = sj.mc;

                    dt.bhpre = bhpre;
                    dt.hdaoid = fswsecondclass.hdaoid;
                    dt.hdaobh = fswsecondclass.hdaobh;
                    dt.hdaomc = fswsecondclass.hdaomc;
                    dt.hduanid = fswsecondclass.hduanid;
                    dt.hduanbh = fswsecondclass.hduanbh;
                    dt.hduanmc = fswsecondclass.hduanmc;
                    dt.hduanxzqhid = fswsecondclass.hduanxzqhid;
                    dt.hduanxzqhmc = fswsecondclass.hduanxzqhmc;
                    dt.fswclassid = fswsecondclass.fswclassid;
                    dt.fswclassmc = fswsecondclass.fswclassmc;
                    dt.fswsecondclassid = fswsecondclass.fswsecondclassid;
                    dt.fswsecondclassmc = fswsecondclass.fswsecondclassmc;
                    dt.fswid = fswid;
                    dt.fswbh = bhpre + fswbh;
                    dt.fswmc = fswmc;
                    for (var key in sj) {
                        //处理显示的布尔字段 01显示是否
                        /*                        switch (parseInt(fswsecondclass.fswsecondclassid)) {
                         case APP_KYDOCK:// 客运码头

                         break;
                         case APP_HYDOCK:// 货运码头
                         break;
                         case APP_GWDOCK:// 公务码头
                         break;
                         }*/
                        dt[key] = sj[key];
                    }

                    list.push(dt);
                }
            }
            ret.data = list;
            ret.total = records.total;
            ret.secho = data.map.sEcho;
            return ret;
        },
        columns: columns,
        columndefs: columndefs,
        fncreatedrow: function (nRow, aData, iDataIndex) {
            $(nRow).attr('id', 'tablerow' + aData.fswid);

            var start = parseInt($('#tableFsw')
                .attr('start'));
            $("td:eq(0)", nRow).html((start + iDataIndex));
            $(nRow).addClass('tableFswTR');
            $(nRow).attr('seled', '0');
            $(nRow).click(function () {
                $('.tableFswTR').css('background', 'white');
                $('.tableFswTR').attr('seled', '0');
                $('.tableFswTR td').css('color', 'black');
                $('.tableFswTR td a').css('color', 'black');
                $(nRow).css('background', '#337ab7');
                $(nRow).attr('seled', '1');
                $("td", nRow).css('color', 'white');
                $("td a", nRow).css('color', 'white');

                loadfswdetail(aData);

                $('#btnfswmodify').setobj('fsw', aData);
                $('#btnfswdel').setobj('fsw', aData);
                $('#btnfswexport').setobj('fsw', aData);
            });
            $(nRow).mouseenter(function () {
                if ($(nRow).attr('seled') != '1')
                    $(nRow).css('background', '#c1d7e9');
            });
            $(nRow).mouseleave(function () {
                if ($(nRow).attr('seled') != '1')
                    $(nRow).css('background', 'white');
            });

            //如果指定了默认的，则自动定位并显示这一行
            if (defaultfswid != null) {
                if (defaultfswid == aData.fswid) {
                    $(nRow).click();
                }
            } else {
                if ($("#tableFsw").attr('inited') == '0' && (start + iDataIndex) == 1) {
                    $(nRow).click();
                }
                $("#tableFsw").attr('inited', '1');
            }
        }
    }, {
        "scrollY": scrollY,
        "fnInitComplete": function () {
            if (compfn != null)
                compfn();
        }
    });
}

// 加载添加航道时弹窗内容
function loadhdaoaddinfo(sfgg) {
    if (sfgg == 1) {
        $("#myModalLabelhdaonew").text("新增骨干航道");
    } else {
        $("#myModalLabelhdaonew").text("新增支线航道");
    }
    loadaddinfo(function () {
        $("#modalhdaonew").modal('show');
    }, "addhdaoform", "CHdHdaojcxx", '{"sfgg":' + sfgg + '}');

}

// 加载添加航段时弹窗内容
function loadhduanaddinfo(hdao) {
    var hdaoid = hdao.hdaoid;
    var hdaobh = hdao.hdaobh;
    var hdaomc = hdao.hdaomc;

    $("#addhduanform").attr('hdaoid', hdaoid);
    $("#addhduanform").attr('hdaobh', hdaobh);
    $("#addhduanform").attr('hdaomc', hdaomc);
    loadaddinfo(function () {
        $("#modalhduannew").modal('show');
    }, "addhduanform", "CHdHduanjcxx", '{\"sshdbh\":\"' + hdaobh + '\",\'sshdid\':' + hdaoid + '}');
}

var fsweditmode = 0;
// 加载添加附属物时弹窗内容
function loadfswaddinfo(fswclass, secondclassid, secondclassname) {
    var fswclassid = fswclass.fswclassid;
    var fswsecondclassid = secondclassid;
    var fswsecondclassname = secondclassname;
    var hdaobh = fswclass.hdaobh;
    var hduanbh = fswclass.hduanbh;
    var hdaoid = fswclass.hdaoid;
    var hduanid = fswclass.hduanid;
    var hduanxzqhid = fswclass.hduanxzqhid;
    var hduanxzqhmc = fswclass.hduanxzqhmc;
    fsweditmode = 0;

    $("#myModalLabelfswnew").html("新增" + fswsecondclassname);
    var modelname = "";
    $("#btnmodalfswnew").attr('secondclass', fswsecondclassid);
    $("#btnmodalfswnew").attr('hduanbh', hduanbh);
    $("#btnmodalfswnew").attr('fswclassid', fswclassid);
    $("#btnmodalfswnew").setobj('fswclass', fswclass);
    switch (parseInt(fswsecondclassid)) {
        case APP_NAVIGATIONMARK:// 航标
            modelname = "CHdHb";
            break;
        case APP_BRIDGE:// 桥梁
            modelname = "CHdQl";
            break;
        case APP_AQUEDUCT:// 渡槽
            modelname = "CHdDc";
            break;
        case APP_CABLE:// 缆线
            modelname = "CHdLx";
            break;
        case APP_PIPELINE:// 管道
            modelname = "CHdGd";
            break;
        case APP_TUNNEL:// 隧道
            modelname = "CHdSd";
            break;
        case APP_KYDOCK:// 客运码头
            modelname = "CHdKymt";
            break;
        case APP_HYDOCK:// 货运码头
            modelname = "CHdHymt";
            break;
        case APP_GWDOCK:// 公务码头
            modelname = "CHdGwmt";
            break;
        case APP_SHIPYARD:// 船厂
            modelname = "CHdCc";
            break;
        case APP_TAKEOUTFALL:// 取排水口
            modelname = "CHdQpsk";
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            modelname = "CHdSwz";
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            modelname = "CHdGlz";
            break;
        case APP_SERVICEAREA:// 服务区
            modelname = "CHdFwq";
            break;
        case APP_MOORINGAREA:// 锚泊区
            modelname = "CHdMbq";
            break;
        case APP_HUB:// 枢纽
            modelname = "CHdSn";
            break;
        case APP_DAM:// 坝
            modelname = "CHdB";
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            modelname = "CHdZzha";
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            modelname = "CHdJgllgcd";
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            modelname = "CHdSpgcd";
            break;
        case APP_MANUALOBSERVATION:// 人工流量观测点
            modelname = "CHdRggcd";
            break;
        case APP_BOLLARD:// 系缆桩
            modelname = "CHdXlz";
            break;
    }
    loadaddinfo(function () {
        $("#modalfswnew").modal('show');
    }, "addfswform", modelname, '{"hdaoid":"' + hdaoid + '","hduanid":"'
        + hduanid + '","szxzqh":"' + hduanxzqhid + '"}', true);
}

// 删除航道时弹窗初始化
function loaddelhdaoinfo(id, name) {
    $("#lbmodalhdaodel").html(name);
    $("#lbmodalhdaodel").attr('delid', id);
    $('#modalhdaodel').modal('show');
}

// 删除航段时弹窗初始化
function loaddelhduaninfo(hdaoid, hduanid, name, hduanbh) {
    $("#lbmodalhduandel").html(name);
    $("#lbmodalhduandel").attr('hdaoid', hdaoid);
    $("#lbmodalhduandel").attr('delid', hduanid);
    $("#lbmodalhduandel").attr('hduanbh', hduanbh);
    $('#modalhduandel').modal('show');
}

// 删除附属物弹窗附属化
function loaddelfswinfo(fsw) {
    var fswlx = fsw.fswsecondclassid;
    $("#lbmodalfswdel").html(fsw.fswmc);
    $("#lbmodalfswdel").attr('hdaoid', fsw.hdaoid);
    $("#lbmodalfswdel").attr('hduanid', fsw.hduanid);
    $("#lbmodalfswdel").attr('hduanbh', fsw.hduanbh);
    $("#lbmodalfswdel").attr('delid', fsw.fswid);
    $("#lbmodalfswdel").attr('delfswclass', fsw.fswclassid);
    $("#lbmodalfswdel").attr('delsecondclass', fswlx);
    $("#modalfswdel").modal('show');
    $("#lbmodalfswdel").setobj('fsw', fsw);
    oldjd = fsw.jd;
    oldwd = fsw.wd;
    showDelChartMap(fswlx);
}

// 加载编辑航道时弹窗内容
function loadhdaoeditinfo(event) {
    var hdaoid = event.data.hdao.hdaoid;
    $("#edithdaoform").setobj('hdao', event.data.hdao);
    // 查询航道详情
    ajax('hangdao/queryhangdaoinfo', {
        'loginid': $("#userid").val(),
        'id': hdaoid
    }, function (data) {
        if (ifResultOK(data)) {
            var modalobj = data.modelobj;
            var json_data = JSON.stringify(modalobj);
            loadeditinfo(function () {
                $("#modalhdaoedit").modal('show');
            }, 'edithdaoform', "CHdHdaojcxx", json_data, null, false);

        } else {

        }
    });
}

function exporthdaoinfo(event) {
    var hdaoid = event.data.hdao.hdaoid;
    var selxzqh = $("#selsearchxzqh").attr('selitem');
    if (selxzqh == "" || selxzqh == null)
        selxzqh = -1;
    window.location.href = $("#basePath").val() + "hangdao/exporthdaoinfo?id=" + hdaoid + "&xzqh=" + selxzqh;
}

// 加载编辑航段时弹窗内容
function loadhduaneditinfo(event) {
    var hduanid = event.data.hduan.hduanid;
    $("#edithduanform").setobj('hduan', event.data.hduan);
    // 查询航道详情
    ajax('hangduan/queryhangduaninfo', {
        'loginid': $("#userid").val(),
        'id': hduanid
    }, function (data) {
        if (ifResultOK(data)) {
            var modalobj = data.modelobj;
            var json_data = JSON.stringify(modalobj);
            loadeditinfo(function () {
                $("#modalhduanedit").modal('show');
            }, 'edithduanform', "CHdHduanjcxx", json_data, null, false);
        } else {

        }
    });
}

function exporthduaninfo(event) {
    var id = event.data.hduan.hduanid;
    window.location.href = $("#basePath").val() + "hangduan/exporthduaninfo?id=" + id ;
}

// 加载编辑附属物时弹窗内容
function loadfsweditinfo(fsw) {
    var fswid = fsw.fswid;
    var fswsecondclassid = fsw.fswsecondclassid;
    var fswclassid = fsw.fswclassid;
    var hduanbh = fsw.hduanbh;
    $("#editfswform").attr('fswsecondclassid', fswsecondclassid);
    $("#editfswform").attr('fswid', fswid);
    $("#editfswform").attr('fswclassid', fswclassid);
    $("#editfswform").attr('hduanbh', hduanbh);
    $("#editfswform").setobj('fsw', fsw);
    oldjd = fsw.jd;
    oldwd = fsw.wd;
    fsweditmode = 1;
    var modelname = '';
    switch (parseInt(fswsecondclassid)) {
        case APP_NAVIGATIONMARK:// 航标
            modelname = "CHdHb";
            break;
        case APP_BRIDGE:// 桥梁
            modelname = "CHdQl";
            break;
        case APP_AQUEDUCT:// 渡槽
            modelname = "CHdDc";
            break;
        case APP_CABLE:// 缆线
            modelname = "CHdLx";
            break;
        case APP_PIPELINE:// 管道
            modelname = "CHdGd";
            break;
        case APP_TUNNEL:// 隧道
            modelname = "CHdSd";
            break;
        case APP_KYDOCK:// 客运码头
            modelname = "CHdKymt";
            break;
        case APP_HYDOCK:// 货运码头
            modelname = "CHdHymt";
            break;
        case APP_GWDOCK:// 公务码头
            modelname = "CHdGwmt";
            break;
        case APP_SHIPYARD:// 船厂
            modelname = "CHdCc";
            break;
        case APP_TAKEOUTFALL:// 取排水口
            modelname = "CHdQpsk";
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            modelname = "CHdSwz";
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            modelname = "CHdGlz";
            break;
        case APP_SERVICEAREA:// 服务区
            modelname = "CHdFwq";
            break;
        case APP_MOORINGAREA:// 锚泊区
            modelname = "CHdMbq";
            break;
        case APP_HUB:// 枢纽
            modelname = "CHdSn";
            break;
        case APP_DAM:// 坝
            modelname = "CHdB";
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            modelname = "CHdZzha";
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            modelname = "CHdJgllgcd";
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            modelname = "CHdSpgcd";
            break;
        case APP_MANUALOBSERVATION:// 人工流量观测点
            modelname = "CHdRggcd";
            break;
        case APP_BOLLARD:// 系缆桩
            modelname = "CHdXlz";
            break;
    }

    ajax('appurtenance/queryappurtenanceinfo', {
        'loginid': $("#userid").val(),
        'id': fswid,
        'fswlx': fswsecondclassid
    }, function (data) {
        if (ifResultOK(data)) {
            if (ifResultOK(data)) {
                var modalobj = data.modelobj;
                var json_data = JSON.stringify(modalobj);

                // 获取原初始下载信息
                var obj = getResultObj(data);
                var fjxx = null;
                if (obj != null) {
                    for (var i = 0; i < obj.length; i++) {
                        var dt = obj[i];
                        if (dt[0].indexOf('附件') >= 0)
                            fjxx = dt;
                    }
                }

                loadeditinfo(function () {
                    $("#modalfswedit").modal('show');
                }, 'editfswform', modelname, json_data, fjxx, true);
            }
        }
    });
}

// 调用后台接口添加航道
function addhdao() {
    $("#addhdaoform").validateForm(function () {
        $("#addhdaoform").autoajax('hangdao/addhangdao', {
            loginid: $("#userid").val()
        }, function (ret, data) {
            if (ifResultOK(ret)) {
                $("#modalhdaonew").modal('hide');
                $("#hdaotree").etreereload('-1');
            } else {
                alert(getResultDesc(ret));
            }
        });
    });
}

// 调用后台接口添加航段
function addhduan() {
    $("#addhduanform")
        .validateForm(
        function () {
            $("#addhduanform")
                .autoajax(
                'hangduan/addhangduan',
                {
                    loginid: $("#userid").val(),
                    sshdid: $("#addhduanform").attr(
                        'hdaoid')
                },
                function (result) {
                    if (ifResultOK(result)) {
                        $("#modalhduannew").modal(
                            'hide');

                        $('#hduantree').etreereload("-1");
                    } else {
                        alert(getResultDesc(result));
                    }
                });
        });
}

// 调用后台接口添加附属物
function addfsw() {
    var url = "";
    var secondclass = $("#btnmodalfswnew").attr('secondclass');
    var hduanbh = $("#btnmodalfswnew").attr('hduanbh');
    var fswclassid = $("#btnmodalfswnew").attr('fswclassid');
    var fswclass = $("#btnmodalfswnew").getobj('fswclass');
    switch (parseInt(secondclass)) {
        case APP_NAVIGATIONMARK:// 航标
            url = 'appurtenance/addnavigationmark';
            break;
        case APP_BRIDGE:// 桥梁
            url = 'appurtenance/addbridge';
            break;
        case APP_AQUEDUCT:// 渡槽
            url = 'appurtenance/addaqueduct';
            break;
        case APP_CABLE:// 缆线
            url = 'appurtenance/addcable';
            break;
        case APP_PIPELINE:// 管道
            url = 'appurtenance/addpipeline';
            break;
        case APP_TUNNEL:// 隧道
            url = 'appurtenance/addtunnel';
            break;
        case APP_KYDOCK:// 客运码头
            url = 'appurtenance/addkydock';
            break;
        case APP_HYDOCK:// 货运码头
            url = 'appurtenance/addhydock';
            break;
        case APP_GWDOCK:// 公务码头
            url = 'appurtenance/addgwdock';
            break;
        case APP_SHIPYARD:// 船厂
            url = 'appurtenance/addshipyard';
            break;
        case APP_TAKEOUTFALL:// 取排水
            url = 'appurtenance/addtakeoutfall';
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            url = 'appurtenance/addhydrologicalstation';
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            url = 'appurtenance/addmanagementstation';
            break;
        case APP_SERVICEAREA:// 服务区
            url = 'appurtenance/addservicearea';
            break;
        case APP_MOORINGAREA:// 锚泊区
            url = 'appurtenance/addmooringarea';
            break;
        case APP_HUB:// 枢纽
            url = 'appurtenance/addhub';
            break;
        case APP_DAM:// 坝
            url = 'appurtenance/adddam';
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            url = 'appurtenance/addregulationrevement';
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            url = 'appurtenance/addlaserobservation';
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            url = 'appurtenance/addvideoobservation';
            break;
        case APP_MANUALOBSERVATION:// 视频观测点
            url = 'appurtenance/addmanualobservation';
            break;
        case APP_BOLLARD:// 系缆桩
            url = 'appurtenance/addbollard';
            break;
    }

    $("#addfswform").validateForm(
        function () {
            $("#addfswform").autoajaxfileuploading(
                url,
                {
                    loginid: $("#userid").val(),
                },
                function (data) {
                    if (ifResultOK(data)) {
                        $("#modalfswnew").modal('hide');
                        initjwdfswlx();
                        $('#hduantree').etreereload("-1", function () {
                            loadfsws($('#hduantree').etreegetselectednode());
                        });
                    } else {
                        alert(getResultDesc(data));
                    }
                });
        }
    );
}

// 删除航道后台调用接口
function delhdao() {
    var delhdaoid = $("#lbmodalhdaodel").attr('delid');
    var selxzqh = $("#selsearchxzqh").attr('selitem');
    if (selxzqh == "" || selxzqh == null)
        selxzqh = -1;

    ajax('hangdao/delhangdao', {
        'loginid': $("#userid").val(),
        'id': delhdaoid,
        'xzqh': selxzqh
    }, function (data) {
        if (ifResultOK(data)) {
            $("#modalhdaodel").modal('hide');
            $("#hdaotree").etreeremovenode(delhdaoid);
        } else {
            alert(getResultDesc(data));
        }
    });
}

// 删除航段后台调用接口
function delhduan() {
    var delhduanid = $("#lbmodalhduandel").attr('delid');
    var hdaoid = $("#lbmodalhduandel").attr('hdaoid');
    var delhduanbh = $("#lbmodalhduandel").attr('hduanbh');
    ajax('hangduan/delhangduan', {
        'loginid': $("#userid").val(),
        'id': delhduanid
    }, function (data) {
        if (ifResultOK(data)) {
            $("#modalhduandel").modal('hide');

            $('#hduantree').etreeremovenode(delhduanid);
            cleardetail();
        } else {
            alert(getResultDesc(data));
        }
    });
}

// 删除附属物后台调用接口
function delfsw() {
    var hdaoid = $("#lbmodalfswdel").attr('hdaoid');
    var hduanid = $("#lbmodalfswdel").attr('hduanid');
    var delfswid = $("#lbmodalfswdel").attr('delid');
    var delfswclass = $("#lbmodalfswdel").attr('delfswclass');
    var delsecondclass = $("#lbmodalfswdel").attr('delsecondclass');
    var hduanbh = $("#lbmodalfswdel").attr('hduanbh');
    var fsw = $("#lbmodalfswdel").getobj('fsw');
    ajax('appurtenance/delappurtenance', {
            'loginid': $("#userid").val(),
            'id': delfswid,
            'fswlx': delsecondclass
        },
        function (data) {
            if (ifResultOK(data)) {
                $("#modalfswdel").modal('hide');
                initjwdfswlx();
                var selnode = $('#hduantree').etreegetselectednode();
                $('#hduantree').etreereload("-1", function () {
                    loadfsws(fsw);
                });
            } else {
                alert(getResultDesc(data));
            }
        });
}

// 调用后台接口编辑航道
function edithdao() {
    var hdao = $("#edithdaoform").getobj('hdao');
    $("#edithdaoform").validateForm(function () {
        $("#edithdaoform").autoajax('hangdao/updatehangdao', {
            loginid: $("#userid").val()
        }, function (result, data) {
            if (ifResultOK(result)) {
                $("#modalhdaoedit").modal('hide');
                $("#hdaotree").etreereload('-1', function () {
                    $("#hdaotree").etreeclicknode(hdao.id);
                });
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}

// 调用后台接口编辑航段
function edithduan() {
    var hduan = $("#edithduanform").getobj('hduan');
    $("#edithduanform").validateForm(function () {
        $("#edithduanform").autoajax('hangduan/updatehangduan', {
            loginid: $("#userid").val()
        }, function (result, data) {
            if (ifResultOK(result)) {
                $("#modalhduanedit").modal('hide');

                cleardetail();
                // 编辑完成后，同步更新UI
                $('#hduantree').etreereload("-1", function () {
                    loadhduandetail($('#hduantree').etreegetnode(hduan.id));
                });
            } else {
                alert(getResultDesc(result));
            }
        });
    });
}

// 调用后台接口编辑附属物
function editfsw() {
    var url = "";
    var fswclassid = $("#editfswform").attr('fswclassid');
    var fswsecondclassid = $("#editfswform").attr('fswsecondclassid');
    var hduanbh = $("#editfswform").attr('hduanbh');
    var fswid = $("#editfswform").attr('fswid');
    var fsw = $("#editfswform").getobj('fsw');
    switch (parseInt(fswsecondclassid)) {
        case APP_NAVIGATIONMARK:// 航标
            url = 'appurtenance/updatenavigationmark';
            break;
        case APP_BRIDGE:// 桥梁
            url = 'appurtenance/updatebridge';
            break;
        case APP_AQUEDUCT:// 渡槽
            url = 'appurtenance/updateaqueduct';
            break;
        case APP_CABLE:// 缆线
            url = 'appurtenance/updatecable';
            break;
        case APP_PIPELINE:// 管道
            url = 'appurtenance/updatepipeline';
            break;
        case APP_TUNNEL:// 隧道
            url = 'appurtenance/updatetunnel';
            break;
        case APP_KYDOCK:// 客运码头
            url = 'appurtenance/updatekydock';
            break;
        case APP_HYDOCK:// 货运码头
            url = 'appurtenance/updatehydock';
            break;
        case APP_GWDOCK:// 公务码头
            url = 'appurtenance/updategwdock';
            break;
        case APP_SHIPYARD:// 船厂
            url = 'appurtenance/updateshipyard';
            break;
        case APP_TAKEOUTFALL:// 取排水口
            url = 'appurtenance/updatetakeoutfall';
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            url = 'appurtenance/updatehydrologicalstation';
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            url = 'appurtenance/updatemanagementstation';
            break;
        case APP_SERVICEAREA:// 服务区
            url = 'appurtenance/updateservicearea';
            break;
        case APP_MOORINGAREA:// 锚泊区
            url = 'appurtenance/updatemooringarea';
            break;
        case APP_HUB:// 枢纽
            url = 'appurtenance/updatehub';
            break;
        case APP_DAM:// 坝
            url = 'appurtenance/updatedam';
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            url = 'appurtenance/updateregulationrevement';
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            url = 'appurtenance/updatelaserobservation';
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            url = 'appurtenance/updatevideoobservation';
            break;
        case APP_MANUALOBSERVATION:// 视频观测点
            url = 'appurtenance/updatemanualobservation';
            break;
        case APP_BOLLARD:// 系缆桩
            url = 'appurtenance/updatebollard';
            break;
    }

    var param = {
        loginid: $("#userid").val()
    };
    var delindex = 0;
    // 添加已有的
    $("#editfswform").find("[id*=originalfileids]").each(function () {
        if ($(this).attr('removed') == "1") {
            param['delfileids[' + delindex + ']'] = $(this).attr('fileid');
            delindex++;
        }
    });
    $("#editfswform").validateForm(
        function () {
            $("#editfswform").autoajaxfileuploading(
                url,
                param,
                function (result, data) {
                    if (ifResultOK(result)) {
                        $("#modalfswedit").modal('hide');
                        initjwdfswlx();
                        hideloading();
                        var middleselnode = $('#hduantree').etreegetselectednode();
                        cleardetail();
                        loadfsws(middleselnode);
                    } else {
                        alert(getResultDesc(result));
                    }
                });
        }
    );
}

// 显示航段详情
function loadhduandetail(hduan) {
    cleardetail();
    var hduanmc = hduan.hduanmc;
    var hduanid = hduan.hduanid;
    $('#detailinfodiv').empty();
    $('#pRightmc').html(hduanmc + "基本信息");
    $('#btnEdit').hide();
    $('#btnExport').hide();
    ajax('hangduan/queryhangduaninfo', {
        'loginid': $("#userid").val(),
        'id': hduanid
    }, function (data) {
        if (ifResultOK(data)) {
            var datalist = getResultObj(data);
            if (datalist != null && datalist.length > 0) {
                showdetail(datalist);
                $('#btnEdit').show();
                $('#btnEdit').unbind('click');
                $('#btnEdit').bind('click', {
                    hduan: hduan
                }, loadhduaneditinfo);
                $('#btnExport').show();
                $('#btnExport').unbind('click');
                $('#btnExport').bind('click', {
                    hduan: hduan
                }, exporthduaninfo);
            }
        } else {
            var datalist = [];
            showdetail(datalist);
        }
    });
}

// 显示航道详情
function loadhdaodetail(hdao) {
    cleardetail();
    var hdaomc = hdao.hdaomc;
    var hdaoid = hdao.hdaoid;
    $('#detailinfodiv').empty();
    $('#btnEdit').hide();
    $('#btnExport').hide();
    $('#pRightmc').html(hdaomc + "基本信息");
    var selxzqh = $("#selsearchxzqh").attr('selitem');
    if (selxzqh == "" || selxzqh == null)
        selxzqh = -1;
    ajax('hangdao/queryhangdaoinfo', {
        'loginid': $("#userid").val(),
        'xzqh': selxzqh,
        'id': hdaoid
    }, function (data) {
        if (ifResultOK(data)) {
            var datalist = getResultObj(data);
            if (datalist != null && datalist.length > 0) {
                showdetail(datalist);
                $('#btnEdit').show();
                $('#btnEdit').unbind('click');
                $('#btnEdit').bind('click', {
                    hdao: hdao
                }, loadhdaoeditinfo);
                $('#btnExport').show();
                $('#btnExport').unbind('click');
                $('#btnExport').bind('click', {
                    hdao: hdao
                }, exporthdaoinfo);
            }
        } else {
            var datalist = [];
            showdetail(datalist);
        }
    });
}

function cleardetail() {
    $('#detailinfodiv').empty();
    $('#pRightmc').html('');
    $('#btnEdit').hide();
    $('#btnExport').hide();
    $('#fswtablediv').hide();
    resize();
}

// 显示附属物详情
function loadfswdetail(fsw) {
    var fswid = fsw.fswid;
    var fswmc = fsw.fswmc;
    var fswsecondclassid = fsw.fswsecondclassid;
    $('#detailinfodiv').empty();
    $('#pRightmc').html(fswmc);
    $('#btnEdit').hide();
    $('#btnExport').hide();
    ajax('appurtenance/queryappurtenanceinfo', {
        'loginid': $("#userid").val(),
        'id': fswid,
        'fswlx': fswsecondclassid
    }, function (data) {
        if (ifResultOK(data)) {
            oldjd = fsw.jd;
            oldwd = fsw.wd;
            chartfswlx = fswsecondclassid;
            var datalist = getResultObj(data);
            if (datalist != null && datalist.length > 0) {
                showdetail(datalist);
                $('#myModalLabelfswedit').html("修改" + fswmc);
            }
        } else {
            var datalist = [];
            showdetail(datalist);
        }
    });
}

/**
 * datalist的格式应该为[ [],[],[] ]
 *
 * 元素为数组，每个元素代表一行， 如果该数组大小为1，该占12列 如果该数组大小为2，数组第一个元素占3列，第二个元素占9列
 * 如果该数组大小为4，数组元素各占4列 其它情况不作处理
 *
 * 其中图片格式为[附件信息,file1name,file1size,file1id,file2name,file2size,file2id...]
 *
 * @param datalist
 */
function showdetail(datalist) {
    for (var i = 0; i < datalist.length; i++) {
        var data = datalist[i];

        var newcol = null;

        // 如果是附件
        if (data.length >= 4 && data[0].indexOf('附件') >= 0) {
            if ((data.length - 1) % 3 == 0) {
                // 添加一行
                newrow = $('<div class="row infodetailrow borderbottom"></div>');
                // 添加左边列
                newcolleft = $('<div class="col-xs-3 text-right infodetailkey">'
                    + data[0] + '</div>');
                newrow.append(newcolleft);
                // 添加右边列
                newcolright = $('<div class="col-xs-9 text-left infodetailval borderleft"></div>');
                newrow.append(newcolright);
                var ids = [];
                for (var j = 1; j < data.length; j += 3) {
                    var fileid = data[j + 2];
                    ids[ids.length] = fileid;
                }
                // 右边列添加下载打开图片
                for (var j = 1; j < data.length;) {
                    var filename = data[j];
                    var dindex = filename.indexOf(".");
                    var ext = filename.substr(dindex + 1);
                    var imgsrc = "img/ic_picture.png";
                    var filesize = data[j + 1];
                    var fileid = data[j + 2];
                    j += 3;
                    switch (ext) {
                        case "jpg":
                            imgsrc = "img/ic_jpg.png";
                            break;
                        case "doc":
                            imgsrc = "img/ic_doc.png";
                            break;
                        case "gif":
                            imgsrc = "img/ic_gif.png";
                            break;
                        case "pdf":
                            imgsrc = "img/ic_pdf.png";
                            break;
                        case "ppt":
                            imgsrc = "img/ic_ppt.png";
                            break;
                        case "rar":
                            imgsrc = "img/ic_rar.png";
                            break;
                    }
                    newcolright
                        .append('<div class="row" style="margin-top:15px;">'
                        + '<div class="col-xs-1 text-right"><img style="margin-top:5px;" src="' + imgsrc + '"></div>'
                        + '<div class="col-xs-10 text-left" style="padding-left:0px;">'
                        + '<div style="font-size:14px;color:#333333;height:25px;line-height:25px;"> '
                        + filename
                        + '('
                        + filesize
                        + 'kb)'
                        + '</div>'
                        + '<div>'
                        + '	<a class="btn btn-link nomargin nopadding" href="appurtenance/downloadchdfj?loginid='
                        + $("#userid").val()
                        + '&id='
                        + fileid
                        + '" style="font-size:12px;color:#337ab7;">下载</a><a class="btn btn-link nomargin nopadding" style="margin-left:5px;font-size:12px;color:#337ab7;" onclick="viewattachment('
                        + fileid
                        + ',\''
                        + ids.join(',')
                        + '\')">打开</a>' + '</div></div></div>');
                }

                $('#detailinfodiv').append(newrow);
                continue;
            }
        }

        if (data.length == 4) {
            var name = data[0];
            var val = data[1];
            var name2 = data[2];
            var val2 = data[3];
            newrow = $('<div class="row borderbottom"></div>');

            newcol = $('<div class="col-xs-3 text-right infodetailkey">' + name
                + '</div>');
            newrow.append(newcol);
            newcol = $('<div class="col-xs-3 text-left infodetailval borderleft" data-toggle="tooltip" data-placement="top" title="'
                + val + '">' + val + '</div>');
            newrow.append(newcol);

            newcol = $('<div class="col-xs-3 text-right infodetailkey borderleft">'
                + name2 + '</div>');
            newrow.append(newcol);
            newcol = $('<div class="col-xs-3 text-left infodetailval borderleft" data-toggle="tooltip" data-placement="top" title="'
                + val2 + '">' + val2 + '</div>');
            newrow.append(newcol);

            $('#detailinfodiv').append(newrow);
        }
        if (data.length == 2) {
            var name = data[0];
            var val = data[1];
            newrow = $('<div class="row infodetailrow borderbottom"></div>');

            newcol = $('<div class="col-xs-3 text-right infodetailkey">' + name
                + '</div>');
            newrow.append(newcol);
            newcol = $('<div class="col-xs-9 text-left infodetailval borderleft" data-toggle="tooltip" data-placement="top" title="'
                + val + '">' + val + '</div>');
            newrow.append(newcol);

            $('#detailinfodiv').append(newrow);
        } else if (data.length == 1) {
            newrow = $('<div class="row borderbottom"></div>');
            newcol = $('<div class="col-xs-12 infoline">' + data[0] + '</div>');
            newrow.append(newcol);

            $('#detailinfodiv').append(newrow);
        }
    }
}

// 显示图片附件
function viewattachment(id, idarray) {
    $("#imgviewbody").empty();
    $("#imgviewbody").append(
        '<img style="width:100%;height:auto;" src="appurtenance/downloadchdfj?loginid='
        + $("#userid").val() + '&id=' + id + '">');
    $("#modalpicview").modal('show');
}

// 下载图片附件
function downloadattachment(id) {

}

// 加载数据字典，并以select展示，可供用户选择
function loadseldict(selectid, name, defaultval) {
    ajax('dic/querydicattr', {
        loginid: $("#userid").val(),
        name: name
    }, function (data) {
        if (ifResultOK(data)) {
            var records = getResultRecords(data);
            $("#" + selectid).empty();
            $("#" + selectid).append('<option value="">--请选择--</option>');
            if (records) {
                for (var i = 0; i < records.data.length; i++) {
                    var dict = records.data[i];
                    if (defaultval != null && dict.id == defaultval) {
                        $("#" + selectid).append(
                            '<option value="' + dict.id
                            + '" selected=\'selected\'>'
                            + dict.attrdesc + '</option>');
                    } else {
                        $("#" + selectid).append(
                            '<option value="' + dict.id + '">'
                            + dict.attrdesc + '</option>');
                    }
                }
                if (selectid == 'CHdHdaojcxx_sssjbh') {
                    if (!hasPerm("VIEW_SHENHDAO")) {
                        $("#CHdHdaojcxx_sssjbh option").each(function () {
                            var op = $(this).text();
                            var szname = $("#xzqhname").val();
                            if (op == szname) {
                                $("#CHdHdaojcxx_sssjbh").val($(this).val());
                            }
                        })
                        $("#CHdHdaojcxx_sssjbh").attr("disabled", "disabled");
                    }
                }
            }
        } else {
        }
    });
}

function loaddptmodel(containerdomid, label) {
    $('#' + containerdomid).etree({
        id: '-1',
        containerdomid: containerdomid,
        childrendatafn: dptdatafn,
        createnodefn: createdptdatafn
    }, function (node) {
        node.label = label;
        node.inited = '1';
        node.expanded = '1';
    });
}

function dptdatafn(pnode, fncallback) {
    var containerdomid = pnode.containerdomid;
    var pnodeid = pnode.id;
    var pnodeisroot = 0;
    if (pnodeid == -1) {
        if (hasPerm("VIEW_SHENHDAO")) {
            pnodeid = pnode.id;
        } else if (hasPerm("VIEW_SHIHDAO") || hasPerm("MAN_SHIHDAO")) {
            pnodeid = $("#szshiju").val();
        } else {
            pnodeid = $("#szju").val();
        }
        pnodeisroot = 1;
    }
    ajax(
        'dpt/querytreedpt',
        {
            'loginid': $("#userid").val(),
            'id': pnodeid,
            'isroot': pnodeisroot
        },
        function (data) {
            if (ifResultOK(data)) {
                var records = getResultRecords(data);
                if (records) {
                    var nodes = new Array();
                    for (var i = 0; i < records.data.length; i++) {
                        var nd = records.data[i];
                        var name = nd.name;
                        var type = nd.type;
                        var id = nd.id;
                        var subcnt = nd.subcnt;
                        nodes.push({
                            id: id,
                            name: name,
                            type: type,
                            clickfn: function (node, event) {
                                if ($(event.target).is('span')) {
                                    event.stopPropagation();
                                } else {
                                    $('#p' + node.label).html(node.name);
                                    $('#' + node.label).attr('selitem', node.id);
                                    verifytarget(node.label);
                                }
                                if (node.expanded == 1) {
                                    $("#" + containerdomid + "_span" + node.id).removeClass('icon-caret-right');
                                    $("#" + containerdomid + "_span" + node.id).addClass('icon-caret-down');
                                } else {
                                    $("#" + containerdomid + "_span" + node.id).addClass('icon-caret-right');
                                    $("#" + containerdomid + "_span" + node.id).removeClass('icon-caret-down');
                                }
                            },
                            subcnt: subcnt,
                            containerdomid: pnode.containerdomid,
                            childrendatafn: dptdatafn,
                            createnodefn: createdptdatafn
                        });
                    }
                    fncallback(nodes);
                }
            }
        });
}

function createdptdatafn(node, container) {
    container.attr('response-click', true);
    container.attr('response-select', true);
    if (node.subcnt > 0) {
        if (node.expanded != null && node.expanded == '1' || node.expanded == true)
            container.append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-expand=true response-click=true style="color:black;" class=\"icon-caret-down\"></span>'));
        else
            container.append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-expand=true response-click=true style="color:black;" class=\"icon-caret-right\"></span>'));
    }
    container.append($('<a id="' + node.containerdomid + '_span' + node.id + '" response-select="true" response-click=true data-stopPropagation="true" class="nomargin nopadding" style="width:80%;overflow:hidden;">'
        + '<label response-select="true" response-click=true data-stopPropagation="true" style="color:black;margin-left:5px;">' + node.name + '</label></a>'));
}

function loadxzqhmodel(containerdomid, label) {
    $('#' + containerdomid).etree({
        id: '-1',
        containerdomid: containerdomid,
        childrendatafn: xzqhdatafn,
        createnodefn: createxzqhdatafn
    }, function (node) {
        node.label = label;
        node.inited = '1';
        node.expanded = '1';
    });
}

function xzqhdatafn(pnode, fncallback) {
    var containerdomid = pnode.containerdomid;
    var pnodeid = pnode.id;
    var pnodeisroot = 0;
    if (pnodeid == -1) {
        if (hasPerm("VIEW_SHENHDAO")) {
            pnodeid = pnode.id;
        } else if (hasPerm("VIEW_SHIHDAO") || hasPerm("MAN_SHIHDAO")) {
            pnodeid = $("#szshixzqh").val();
        } else {
            pnodeid = $("#szxzqh").val();
        }
        pnodeisroot = 1;
    }
    ajax(
        'xzqh/querytreexzqh',
        {
            'loginid': $("#userid").val(),
            'xzqh': pnodeid,
            'isroot': pnodeisroot
        },
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
                            subcnt: subcnt,
                            clickfn: function (node, event) {
                                if ($(event.target).is('span')) {
                                    event.stopPropagation();
                                } else {
                                    $('#p' + node.label).html(node.name);
                                    $('#' + node.label).attr('selitem', node.id);
                                    verifytarget(node.label);
                                }
                                if (node.expanded == 1) {
                                    $("#" + containerdomid + "_span" + node.id).removeClass('icon-caret-right');
                                    $("#" + containerdomid + "_span" + node.id).addClass('icon-caret-down');
                                } else {
                                    $("#" + containerdomid + "_span" + node.id).addClass('icon-caret-right');
                                    $("#" + containerdomid + "_span" + node.id).removeClass('icon-caret-down');
                                }
                            },
                            containerdomid: containerdomid,
                            childrendatafn: xzqhdatafn,
                            createnodefn: createxzqhdatafn
                        });
                    }
                    fncallback(nodes);
                }
            }
        });
}

function createxzqhdatafn(node, container) {
    container.attr('response-click', true);
    container.attr('response-select', true);
    if (node.subcnt > 0) {
        if (node.expanded != null && node.expanded == '1' || node.expanded == true)
            container.append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-expand=true response-click=true style="color:black;" class=\"icon-caret-down\"></span>'));
        else
            container.append($('<span id="' + node.containerdomid + '_span' + node.id + '" response-expand=true response-click=true style="color:black;" class=\"icon-caret-right\"></span>'));
    }
    container.append($('<a response-select="true" response-click=true class="nomargin nopadding" style="width:80%;overflow:hidden;">'
        + '<label response-select="true" response-click=true  style="color:black;margin-left:5px;">' + node.name + '</label></a>'));
}

/**
 * 自动生成编辑信息，inputdata为键值对各个属性的信息 需调用方自动生成。
 */
function loadeditinfo(callfn, containerid, modelname, inputdata, fjxxarray,
                      needpic) {
    loadaddinfo(callfn, containerid, modelname, inputdata, needpic, true,
        fjxxarray);
}

/**
 * 自动生成新增信息，inputdata为键值对各个属性的信息 需调用方自动生成。
 */
function loadaddinfo(callfn, containerid, modelname, inputdata, needpic,
                     editmode, fjxxarray/* 附件信息 */) {
    var container = $("#" + containerid);
    if (inputdata == null)
        inputdata = '';
    container.empty();
    if (editmode != true) {
        editmode = false;
    }
    // 此行代码解决如果对同一信息再次自动生成，会产生页面元素id冲突的问题
    $(".auto-generate").empty();
    ajaxloading(
        'modelutil/querymodelinfo',
        {
            modelname: modelname,
            inputdata: inputdata,
            editmode: editmode
        },
        function (data) {
            if (ifResultOK(data)) {
                var modelinfo = getResultObj(data);
                var modelname = modelinfo.modelName;
                var rowelementsize = 0;
                var row = null;
                container.validateFormClear();
                for (var i = 0; i < modelinfo.propertyInfos.length; i++) {
                    var propinfo = modelinfo.propertyInfos[i];

                    var name = propinfo.name;
                    var type = propinfo.type;
                    var desc = propinfo.desc;
                    var group = propinfo.group;
                    var order = propinfo.order;
                    var groupname = propinfo.groupname;
                    var inputtype = propinfo.inputtype;
                    var readonly = propinfo.readonly;
                    var defaultval = propinfo.defaultval;
                    var defaultvalpre = propinfo.defaultvalpre;
                    var oneline = propinfo.oneline;
                    var selectdictname = propinfo.selectdictname;
                    var must = propinfo.must;
                    var validatorjsons = propinfo.validatorjsons;
                    var autoajax = propinfo.autoajax;
                    var autoajax_name = propinfo.autoajax_name;
                    var autoajax_attr = propinfo.autoajax_attr;
                    var autoajax_defaultval = propinfo.autoajax_defaultval;
                    var edithidden = propinfo.edithidden;
                    var editable = propinfo.editable;

                    if (groupname != null) {
                        container.append($('<div class="row">'
                            + '<div class="col-xs-12 addinfoline">'
                            + groupname + '</div>' + '</div>'));
                        row = null;
                        rowelementsize = 0;
                    }
                    if (edithidden) {
                        row = $('<input type="hidden" class="form-control" autoajax autoajax-name=\''
                            + (modelname.toLowerCase() + "." + name)
                            + '\' value="' + defaultval + '">');
                        container.append(row);
                        continue;
                    } else if (oneline) {
                        // 添加行
                        row = $('<div class="row"></div>');
                        container.append(row);
                        var col = $('<div class="col-xs-3 form-group"></div>');
                        row.append(col);
                        var label = $('<label class="col-xs-12 text-right addinfodetailkey control-label">'
                            + desc
                            + (must ? '<sup style="color:red;">*</sup>'
                                : '') + '</label>');
                        col.append(label);
                        var inputdiv = $('<div class="col-xs-9"></div>');
                        row.append(inputdiv);
                    } else {
                        // 添加行
                        if (rowelementsize == 0) {
                            row = $('<div class="row"></div>');
                            container.append(row);
                        }
                        var col = $('<div class="col-xs-6 form-group"></div>');
                        row.append(col);
                        var label = $('<label class="col-xs-6 text-right addinfodetailkey control-label">'
                            + desc
                            + (must ? '<sup style="color:red;">*</sup>'
                                : '') + '</label>');
                        col.append(label);
                        var inputdiv = $('<div class="col-xs-6 addinfodetailval"></div>');
                        col.append(inputdiv);
                    }
                    // /////////////////////////////添加元素/////////////////////////////

                    var autoajaxstr = '';
                    var autoajaxnamestr = '';
                    var autoajaxattrstr = '';
                    var autoajaxdefaultvalstr = '';
                    if (autoajax) {
                        autoajaxstr = ' autoajax ';
                        if (autoajax_name == null)
                            autoajaxnamestr = ' autoajax-name="'
                                + (modelname.toLowerCase() + "." + name)
                                + '" ';
                        else
                            autoajaxnamestr = ' autoajax-name="'
                                + autoajax_name + '" ';

                        if (autoajax_attr != null)
                            autoajaxattrstr = ' autoajax-attr="'
                                + autoajax_attr + '" ';
                        if (autoajax_defaultval != null)
                            autoajaxdefaultvalstr = ' autoajax-defaultval="'
                                + autoajax_defaultval + '"';
                    }
                    if (editmode && editable == false) {
                        readonly = true;
                    }
                    // 输入框
                    if (inputtype == "input") {
                        var readonlystr = '';
                        if (readonly) {
                            readonlystr = ' readonly="readonly" disabled';
                        }
                        if (defaultvalpre != null && defaultvalpre != '' && defaultvalpre != 'null' && defaultvalpre != 'NULL') {
                            inputdiv.append($(
                                '<div class="input-group" id="inputgroup' + modelname
                                + "_"
                                + name + '">' +
                                '<span class="input-group-addon" id="basic-addon1' + modelname
                                + "_"
                                + name + '">' + defaultvalpre + '</span>' +
                                '<input type="text" class="form-control" ' + readonlystr
                                + autoajaxstr
                                + autoajaxnamestr
                                + autoajaxattrstr
                                + autoajaxdefaultvalstr
                                + ' id="'
                                + modelname
                                + "_"
                                + name
                                + '" value="' + defaultval + '" defaultval="' + defaultval + '" aria-describedby="basic-addon1' + modelname
                                + "_"
                                + name + '">' +
                                '</div>'
                            ))
                            ;
                        } else {
                            inputdiv
                                .append($('<input type="text" class="form-control"'
                                    + readonlystr
                                    + autoajaxstr
                                    + autoajaxnamestr
                                    + autoajaxattrstr
                                    + autoajaxdefaultvalstr
                                    + ' id="'
                                    + modelname
                                    + "_"
                                    + name
                                    + '" value="' + defaultval + '" defaultval="' + defaultval + '">'));
                        }
                    } else if (inputtype == "selectyesno") {
                        var readonlystr = '';
                        if (readonly) {
                            readonlystr = ' disabled="disabled" ';
                        }
                        var select = $('<select class="form-control"'
                            + readonlystr + autoajaxstr
                            + autoajaxnamestr + autoajaxattrstr
                            + autoajaxdefaultvalstr + ' id="'
                            + modelname + "_" + name + '"></select>');
                        var selected1 = '';
                        var selected0 = '';
                        if (defaultval == '1'
                            || defaultval.toLowerCase() == 'yes') {
                            selected1 = 'selected="selected"';
                        } else if (defaultval == '0'
                            || defaultval.toLowerCase() == 'no') {
                            selected0 = 'selected="selected"';
                        }
                        select.append('<option value="1" ' + selected1
                            + '>是</option>');
                        select.append('<option value="0" ' + selected0
                            + '>否</option>');
                        inputdiv.append(select);
                    } else if (inputtype == "selectdict") {
                        var readonlystr = '';
                        if (readonly) {
                            readonlystr = ' disabled="disabled" ';
                        }
                        inputdiv.append($('<select class="form-control"'
                            + readonlystr + autoajaxstr
                            + autoajaxnamestr + autoajaxattrstr
                            + autoajaxdefaultvalstr + ' id="'
                            + modelname + "_" + name + '">'
                            + '</select>'));

                        loadseldict(modelname + "_" + name, selectdictname,
                            defaultval);
                    } else if (inputtype == "textarea") {
                        var readonlystr = '';
                        if (readonly) {
                            readonlystr = ' readonly="readonly" ';
                        }
                        inputdiv
                            .append($('<textarea class="form-control" rows="8"'
                                + readonlystr
                                + autoajaxstr
                                + autoajaxnamestr
                                + autoajaxattrstr
                                + autoajaxdefaultvalstr
                                + ' id="'
                                + modelname
                                + "_"
                                + name
                                + '">'
                                + defaultval + '</textarea>'));
                    } else if (inputtype == "selectdpt") {
                        var dftvals = defaultval.split(',');
                        var dftid = '';
                        var dftval = '--请选择管理机构--';

                        if (dftvals != null && dftvals.length == 2) {
                            if (dftvals[0] != '' && dftvals[1] != '') {
                                dftid = dftvals[0];
                                dftval = dftvals[1];
                            }
                        }

                        var div = $('<div id="div' + modelname + "_" + name
                            + '" class="dropdown"></div>')
                        var btn = $('<button class="btn btn-default dropdown-toggle btn-block"'
                            + 'type="button" id="'
                            + modelname
                            + "_"
                            + name
                            + '" data-toggle="dropdown"'
                            + 'aria-haspopup="true" aria-expanded="false" '
                            + autoajaxstr
                            + autoajaxnamestr
                            + autoajaxdefaultvalstr
                            + ' autoajax-attr="selitem" selitem="'
                            + dftid
                            + '">'
                            + '<p id="p'
                            + modelname
                            + "_"
                            + name
                            + '" class="text-left" style="margin:0;width:80%;float:left;overflow:hidden;">'
                            + dftval
                            + '</p>'
                            + '<span class="caret pull-right" style="margin-top: 7px;"></span>'
                            + '</button>');
                        var ul = $('<ul id="'
                            + modelname
                            + "_"
                            + name
                            + 'ul-1" class="dropdown-menu"'
                            + 'style="list-style-type: none;width:250px;"'
                            + 'aria-labelLedby="div' + modelname + "_"
                            + name + '"></ul>');
                        div.append(btn);
                        div.append(ul);

                        inputdiv.append(div);
                        inputdiv.removeClass('addinfodetailval');
                        inputdiv.css('padding-top', '5px');
                        loaddptmodel(modelname
                            + "_"
                            + name
                            + 'ul-1', modelname
                            + "_"
                            + name);
                    } else if (inputtype == "selectxzqh") {
                        var dftvals = defaultval.split(',');
                        var dftid = '';
                        var dftval = '--请选择行政区划--';

                        if (dftvals != null && dftvals.length == 2) {
                            if (dftvals[0] != '' && dftvals[1] != '') {
                                dftid = dftvals[0];
                                dftval = dftvals[1];
                            }
                        }

                        var div = $('<div id="div' + modelname + "_" + name
                            + '" class="dropdown"></div>')
                        var btn = $('<button class="btn btn-default dropdown-toggle btn-block"'
                            + 'type="button" id="'
                            + modelname
                            + "_"
                            + name
                            + '" data-toggle="dropdown"'
                            + 'aria-haspopup="true" aria-expanded="false" '
                            + autoajaxstr
                            + autoajaxnamestr
                            + autoajaxdefaultvalstr
                            + ' autoajax-attr="selitem" selitem="'
                            + dftid
                            + '">'
                            + '<p id="p'
                            + modelname
                            + "_"
                            + name
                            + '" class="text-left" style="margin:0;width:80%;float:left;overflow:hidden;">'
                            + dftval
                            + '</p>'
                            + '<span class="caret pull-right" style="margin-top: 7px;"></span>'
                            + '</button>');
                        var ul = $('<ul id="'
                            + modelname
                            + "_"
                            + name
                            + 'ul-1" class="dropdown-menu"'
                            + 'style="list-style-type: none;width:250px;"'
                            + 'aria-labelLedby="div' + modelname + "_"
                            + name + '"></ul>');
                        div.append(btn);
                        div.append(ul);

                        inputdiv.append(div);
                        inputdiv.removeClass('addinfodetailval');
                        inputdiv.css('padding-top', '5px');

                        loadxzqhmodel(modelname + "_" + name + 'ul-1', modelname + "_" + name);

                    } else if (inputtype == "selectdate") {
                        var div = $('<div class="input-group"></div>')
                        div
                            .append($('<input '
                                + autoajaxstr
                                + autoajaxnamestr
                                + autoajaxattrstr
                                + autoajaxdefaultvalstr
                                + ' id="'
                                + modelname
                                + "_"
                                + name
                                + '" class="form-control"'
                                + ' type="text" value="'
                                + defaultval
                                + '" readonly><span'
                                + ' class="input-group-addon"  onclick="WdatePicker({el:\''
                                + modelname
                                + "_"
                                + name
                                + '\'});" style="padding-left:5px;padding-right:5px;"><i class="icon-time"></i></span>'));
                        inputdiv.append(div);
                    } else if (inputtype == "selectyear") {
                        var div = $('<div class="input-group"></div>')
                        div
                            .append($('<input '
                                + autoajaxstr
                                + autoajaxnamestr
                                + autoajaxattrstr
                                + autoajaxdefaultvalstr
                                + ' id="'
                                + modelname
                                + "_"
                                + name
                                + '" class="form-control"'
                                + ' type="text" value="'
                                + defaultval
                                + '" readonly><span'
                                + ' class="input-group-addon"  onclick="WdatePicker({el:\''
                                + modelname
                                + "_"
                                + name
                                + '\',dateFmt:\'yyyy\'});" style="padding-left:5px;padding-right:5px;"><i class="icon-time"></i></span>'));
                        inputdiv.append(div);
                    } else if (inputtype == "selectmonth") {
                        var div = $('<div class="input-group"></div>')
                        div
                            .append($('<input '
                                + autoajaxstr
                                + autoajaxnamestr
                                + autoajaxattrstr
                                + autoajaxdefaultvalstr
                                + ' id="'
                                + modelname
                                + "_"
                                + name
                                + '" class="form-control"'
                                + ' type="text" value="'
                                + defaultval
                                + '" readonly><span'
                                + ' class="input-group-addon"  onclick="WdatePicker({el:\''
                                + modelname
                                + "_"
                                + name
                                + '\',dateFmt:\'yyyy-MM\'});" style="padding-left:5px;padding-right:5px;"><i class="icon-time"></i></span>'));
                        inputdiv.append(div);
                    }

                    if (oneline) {
                        row = null;
                        rowelementsize = 0;
                    } else {
                        rowelementsize++;
                        if (rowelementsize == 2) {
                            row = null;
                            rowelementsize = 0;
                        }
                    }

                    if (validatorjsons != null && validatorjsons != "") {
                        var inputid = modelname + "_" + name;
                        var validatorobj = {};
                        var validatorfilters = {};
                        for (var v = 0; v < validatorjsons.length; v++) {
                            var validatorjson = validatorjsons[v];
                            var jsonobj = JSON.parse(validatorjson);

                            if (inputtype == 'input' && defaultvalpre != null && defaultvalpre != '' && defaultvalpre != 'null' && defaultvalpre != 'NULL') {
                                jsonobj['settings'] = {};
                                jsonobj['settings']['posafter'] = "inputgroup" + modelname
                                    + "_"
                                    + name;
                            }

                            for (va in jsonobj) {
                                validatorfilters[va] = jsonobj[va];
                            }
                        }
                        validatorobj[inputid] = validatorfilters;


                        container.validateFormBind(validatorobj);
                    }
                }// for end
                if (needpic != null && needpic == true) {
                    container
                        .append($('<div class="row">'
                            + '<div class="col-xs-12 addinfoline">附件信息 &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;'
                            + '<i class="icon-paper-clip" style="color:blue;"></i>'
                            + '<input type="button" class="btn btn-link" value="添加附件" onclick="addpicture(\''
                            + containerid + '\');"></div></div>'));

                    container.append(row);

                }
                if (editmode == true && fjxxarray != null
                    && fjxxarray[0].indexOf('附件') >= 0) {
                    for (var k = 1; k < fjxxarray.length; k += 3) {
                        var filename = fjxxarray[k];
                        var filesize = fjxxarray[k + 1];
                        var fileid = fjxxarray[k + 2];

                        var rantoken = rand(1, 99999999);
                        var row = $('<div class="row" id="row' + rantoken
                            + '"></div>');
                        var col = $('<div class="col-xs-12" style="background:#e4f0ff;margin-top:2px;"></div>');
                        row.append(col);
                        col
                            .append('<i class="icon-paper-clip" style="clolr:bule;"></i>&nbsp;'
                            + '<label style="font-weight:normal;" id="label'
                            + rantoken
                            + '">'
                            + filename
                            + "("
                            + filesize
                            + "kb)"
                            + '</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
                            + '<input type="button" class="btn btn-link" value="删除" onclick="$(\'#row'
                            + rantoken
                            + '\').addClass(\'hide\');$(this).attr(\'removed\',\'1\')" id="originalfileids" removed=\"0\" fileid=\"'
                            + fileid + '\">');

                        $("#" + containerid).append(row);
                    }
                }
                if (callfn != null)
                    callfn();

            }
        });
}

function addpicture(containerid) {
    var rantoken = rand(1, 99999999);

    var row = $('<div class="row hide" id="row' + rantoken + '"></div>');
    var col = $('<div class="col-xs-12" style="background:#e4f0ff;margin-top:2px;"></div>');
    row.append(col);
    col
        .append('<i class="icon-paper-clip" style="clolr:bule;"></i>&nbsp;'
        + '<label style="font-weight:normal;" id="label'
        + rantoken
        + '"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
        + '<input type="button" class="btn btn-link" value="删除" onclick="$(\'#row'
        + rantoken
        + '\').remove();"><input id="file'
        + rantoken
        + '" type="file" class="hide" autoajax autoajax-name="filelist" accept="image/*" onchange="selectfile(this,\''
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

/**
 * 附属物编号后台验证接口,如何设计为输入时异步提交，而最终提交验证表单时，同步提交
 */
function verifyfswbh(val, verifyblock, verifytarget) {

}

//获取元素的纵坐标（相对于窗口）
function getTop(e) {
    var offset = e.offsetTop;
    if (e.offsetParent != null) offset += getTop(e.offsetParent);
    return offset;
}

//获取元素的横坐标（相对于窗口）
function getLeft(e) {
    var offset = e.offsetLeft;
    if (e.offsetParent != null) offset += getLeft(e.offsetParent);
    return offset;
}

function searchdropdowndialog() {
    $("#searchresulttree").empty();
    $("#searchresulttree").etree({id: "search-1", childrendatafn: datafnsearchfsw});
    var left = getLeft(document.getElementById("searchcontent"));

    $("#searchdropdowndialog").css('left', left);
}

function hidesearchdropdown() {
    $("#searchdropdowndialog").hide();
}

function createsearchresultfn(node, container) {
    container.css('padding-left', '5px');
    if (node.type == 3) {
        container.attr('response-select', true);
        container.attr('response-click', true);
        // 附属物小图标选择
        var fswicon = 'icon-building';
        var imgicon = '';
        switch (node.fswsecondclass) {
            case APP_NAVIGATIONMARK:// 航标
                imgicon = 'ic_navigationmark.png';
                break;
            case APP_BRIDGE:// 桥梁
                imgicon = 'ic_bridge.png';
                break;
            case APP_AQUEDUCT:// 渡槽
                imgicon = 'ic_flume.png';
                break;
            case APP_CABLE:// 缆线
                imgicon = 'ic_cabled-twist.png';
                break;
            case APP_PIPELINE:// 管道
                imgicon = 'ic_pipeline.png';
                break;
            case APP_TUNNEL:// 隧道
                imgicon = 'ic_tunnel.png';
                break;
            case APP_KYDOCK:// 客运码头
                imgicon = 'ic_wharf.png';
                break;
            case APP_HYDOCK:// 货运码头
                imgicon = 'ic_wharf.png';
                break;
            case APP_GWDOCK:// 公务码头
                imgicon = 'ic_wharf.png';
                break;
            case APP_SHIPYARD:// 船厂
                break;
            case APP_TAKEOUTFALL:// 取排水口
                imgicon = 'ic_outfall.png';
                break;
            case APP_HYDROLOGICALSTATION:// 水文站
                imgicon = 'ic_hydrologic-station.png';
                break;
            case APP_MANAGEMENTSTATION:// 管理站
                imgicon = 'ic_guanlizhan.png';
                break;
            case APP_SERVICEAREA:// 服务区
                imgicon = 'ic_service.png';
                break;
            case APP_MOORINGAREA:// 锚泊区
                imgicon = 'ic_roadstead.png';
                break;
            case APP_HUB:// 枢纽
                imgicon = 'ic_key-position.png';
                break;
            case APP_DAM:// 坝
                imgicon = 'ic_dam.png';
                break;
            case APP_REGULATIONREVEMENT:// 整治护岸
                imgicon = 'ic_revetment.png';
                break;
            case APP_LASEROBSERVATION:// 激光流量观测点
                imgicon = 'ic_observation-point.png';
                break;
            case APP_VIDEOOBSERVATION:// 视频观测点
                imgicon = 'ic_movie.png';
                break;
            case APP_MANUALOBSERVATION:
                imgicon = 'ic_observation-point.png';
                break;
            case APP_BOLLARD:// 系缆桩
                imgicon = "ic_bitt.png";
                break;
        }

        var fswspan = null;
        if (imgicon != '') {
            fswspan = $('<img src="img/'
                + imgicon
                + '" style="float:left;margin-top:12px;">');
        } else {
            fswspan = $('<span class="'
                + fswicon
                + '" style="float:left;margin-top:12px;"></span>');
        }
        var newa = $("<a response-select=true response-click=true style='float:left;width:90%;white-space: nowrap;'>"
            + "<label response-select=true response-click=true class='nomargin' style='color: black;'>&nbsp;"
            + node.name + "</label></a>");


        container.append(fswspan);
        container.append(newa);
    } else {
        container.attr('response-select', true);
        container.attr('response-click', true);
        container.attr('response-expand', true);
        container.css('white-space', 'nowrap');
        container.text(node.name);
    }
}

function datafnsearchfsw(pnode, fncallback) {
    var selxzqh = $("#selsearchxzqh").attr('selitem');
    if (selxzqh == "" || selxzqh == null)
        selxzqh = -1;
    var url = "";
    var data = {};
    var type = parseInt($("#selsearchtype").val());
    data.type = type;
    switch (type) {
        case 0:
            url = "hangdao/searchallhangdao";
            data['sfgg'] = $("#hdaosearchselecthdao").val();
            data['content'] = $("#searchcontent").val();
            data['xzqh'] = selxzqh;
            break;
        case 1:
            url = "hangduan/searchallhangduan";
            data['sshdid'] = $("#selecthduansearchhdao").val();
            data['hddj'] = $("#selecthduandj").val();
            data['content'] = $("#searchcontent").val();
            data['xzqh'] = selxzqh;
            break;
        case 2:
            url = "appurtenance/searchappurtenances";
            data['sshdid'] = $("#selectfswsearchhdao").val();
            data['sshduanid'] = $("#selectfswsearchhduan").val();
            data['fswsecondclassid'] = $("#selectfswsearchfswsecondclass").val();
            data['content'] = $("#searchcontent").val();
            data['xzqh'] = selxzqh;
            break;
    }
    var content = $("#searchcontent").val();
    if (content != null && content != '') {
        ajax(url, data, function (result, data) {
            if (ifResultOK(result)) {
                try {
                    var nodes = new Array();
                    var list = getResultRecords(result).data;
                    if (data.type == 0) {
                        for (var i = 0; i < list.length; i++) {
                            var sj = list[i];

                            var hdaoid = sj.id;
                            var hdaobh = sj.hdbh;
                            var hdaomc = sj.hdmc;

                            nodes.push({
                                id: "search" + hdaoid,
                                name: hdaomc,
                                hdaoid: hdaoid,
                                hdaobh: hdaobh,
                                hdaomc: hdaomc,
                                type: data.type,
                                createnodefn: createsearchresultfn,
                                clickfn: locationto
                            });
                        }
                    } else if (data.type == 1) {
                        for (var i = 0; i < list.length; i++) {
                            var sj = list[i];

                            var hdaoid = sj[0];
                            var hdaobh = sj[1];
                            var hdaomc = sj[2];
                            var hduanid = sj[3];
                            var hduanbh = sj[4];
                            var hduanqdmc = sj[5];
                            var hduanzdmc = sj[6];

                            nodes.push({
                                id: "search" + hdaoid + "_" + hduanid,
                                name: hduanqdmc + "-" + hduanzdmc + " (" + hdaomc + ")",
                                hdaoid: hdaoid,
                                hdaobh: hdaobh,
                                hdaomc: hdaomc,
                                hduanid: hduanid,
                                hduanbh: hduanbh,
                                hduanqdmc: hduanqdmc,
                                hduanzdmc: hduanzdmc,
                                type: data.type,
                                createnodefn: createsearchresultfn,
                                clickfn: locationto
                            });
                        }
                    } else if (data.type == 2) {
                        for (var i = 0; i < list.length; i++) {
                            var sj = list[i];

                            var fswid = sj[0];
                            var fswbh = sj[1];
                            var fswmc = sj[2];
                            var hdaoid = sj[3];
                            var hdaobh = sj[4];
                            var hdaomc = sj[5];
                            var hduanid = sj[6];
                            var fswclass = sj[7];
                            var fswsecondclass = sj[8];
                            var hduanbh = sj[9];
                            var hduanqdmc = sj[10];
                            var hduanzdmc = sj[11];

                            nodes.push({
                                id: "search" + hdaoid + "_" + hduanid + "_" + fswid,
                                name: fswmc + " (" + hdaomc + "/" + hduanqdmc + "-" + hduanzdmc + ")",
                                fswid: fswid,
                                fswbh: fswbh,
                                fswmc: fswmc,
                                fswclassid: fswclass,
                                fswsecondclassid: fswsecondclass,
                                hdaoid: hdaoid,
                                hdaobh: hdaobh,
                                hdaomc: hdaomc,
                                hduanid: hduanid,
                                hduanbh: hduanbh,
                                hduanqdmc: hduanqdmc,
                                hduanzdmc: hduanzdmc,
                                type: data.type,
                                createnodefn: createsearchresultfn,
                                clickfn: locationto
                            });
                        }
                    }
                    if (nodes.length <= 0) {
                        nodes.push({name: '没有找到相关结果', type: -1});
                    }
                    $("#searchdropdowndialog").show();
                    fncallback(nodes);
                } catch (e) {
                }
            }
        });
    }
}

function locationto(node, event) {
    if (node.type == 0)
        locationtohdao(node);
    else if (node.type == 1)
        locationtohduan(node);
    else if (node.type == 2)
        locationtofsw(node);
}

function locationtofsw(fsw) {
    $('#hdaotree').etreelocationTopath(['-1', fsw.hdaoid], function () {
        var selnode = $('#hdaotree').etreegetselectednode();
        if (selnode != null)
            $('#hdaotree').etreesetselected(selnode.id, false, 'white');
        $('#hdaotree').etreesetselected(fsw.hdaoid, true);

        loadhduantree(fsw, function () {
            $("#hduantree").etreelocationTopath(
                [
                    '-1',
                    fsw.hduanid,
                    fsw.hduanid + "_" + fsw.fswclassid,
                    fsw.hduanid + "_" + fsw.fswclassid + "_" + fsw.fswsecondclassid
                ], function () {
                    $("#hduantree").etreesetselected(fsw.hduanid + "_" + fsw.fswclassid + "_" + fsw.fswsecondclassid, true);
                    loadfsws(fsw, fsw.fswid, function () {

                        var scrollTo = $("#tablerow" + fsw.fswid);
                        var speed = 800;
                        var scrollval = scrollTo.offset().top - $(".dataTables_scrollBody").offset().top + $(".dataTables_scrollBody").scrollTop();
                        $(".dataTables_scrollBody").animate({
                            scrollTop: scrollval
                        }, speed);
                    });
                });
        });
        cleardetail();

    });
    hidesearchdropdown();
}

function locationtohdao(hdao) {
    $('#hdaotree').etreelocationTopath(['-1', hdao.hdaoid], function () {
        $('#hdaotree').etreeclicknode(hdao.hdaoid);
    });
    hidesearchdropdown();
}

function locationtohduan(hduan) {
    $('#hdaotree').etreelocationTopath(['-1', hduan.hdaoid], function () {
        var selnode = $('#hdaotree').etreegetselectednode();

        if (selnode != null)
            $('#hdaotree').etreesetselected(selnode.id, false, 'white');
        $('#hdaotree').etreesetselected(hduan.hdaoid, true);

        {
            loadhduantree(hduan, function () {
                $('#hduantree').etreeclicknode(hduan.hduanid);
            });
            cleardetail();
        }
    });
    hidesearchdropdown();
}

function hduanbhuniquecheck(text, blockid, targetid) {
    var sshdid = $("#addhduanform").attr('hdaoid');
    if (text == $("#" + targetid).attr('defaultval'))
        return true;

    return {
        url: "hangduan/queryhdbhexisted",
        data: {
            sshdid: sshdid,
            hdbh: text
        }
    };
}

function appuniquecheck(text, blockid, targetid) {
    var fswlx = $("#btnmodalfswnew").attr('secondclass');
    if (text == $("#" + targetid).attr('defaultval'))
        return true;

    return {
        url: "appurtenance/queryappbhexisted",
        data: {
            fswlx: fswlx,
            bh: text
        }
    };
}

function transferjwd(jd, wd, callbk, data) {
    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': jd + " " + wd
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = "";
            jwd = data.response.result.point;
            callbk(jwd, data);
        }
    });
}

function transferjwds(jdwds, callbk, data) {
    var data = "";

    for (var i = 0; i < jdwds.length; i++) {
        var jwd = jdwds[i];
        data += jwd.jd + " " + jwd.wd;
        if (i != jdwds.length - 1)
            data += ',';
    }

    $.ajax({
        url: "http://172.20.24.105/webtservice/gis/coordinate/encryption.json",
        type: 'get',
        data: {
            'point': data
        },
        dataType: "jsonp",
        success: function (data) {
            var jwd = "";
            jwd = data.response.result.point;
            var jwdsp = jwd.split(',');
            if (jwdsp != null && jwdsp.length > 0) {
                var jwd = new Array();

                for (var i = 0; i < jwdsp.length; i++) {
                    jwd.push({
                        'jd': jwdsp[i].split(' ')[0],
                        'wd': jwdsp[i].split(' ')[1]
                    });
                }
                callbk(jwd, data);
            } else {
                alert("坐标转化失败");
            }
        }
    });
}

function addDelMapMarker(fswlx, jd, wd) {
    $("#delmapbefore").empty();
    $("#delmapafter").empty();
    var oldmap = new WebtAPI.WMap(WebtAPI.Util.getElement("delmapbefore"));
    var newmap = new WebtAPI.WMap(WebtAPI.Util.getElement("delmapafter"));
    oldmap.clearMarkers();
    newmap.clearMarkers();
    oldmap.setDisplayNauticalLayer(true); //开启海图
    newmap.setDisplayNauticalLayer(true); //开启海图
    var iconpath = 'img/mapmr.png';
    var size = new WebtAPI.Size('28px', '28px');
    switch (parseInt(fswlx)) {
        case APP_NAVIGATIONMARK:// 航标
            iconpath = 'img/maphb.png';
            break;
        case APP_BRIDGE:// 桥梁
            iconpath = 'img/mapql.png';
            break;
        case APP_AQUEDUCT:// 渡槽
            iconpath = 'img/mapdc.png';
            break;
        case APP_CABLE:// 缆线
            iconpath = 'img/maplx.png';
            break;
        case APP_PIPELINE:// 管道
            iconpath = 'img/mapgd.png';
            break;
        case APP_TUNNEL:// 隧道
            iconpath = 'img/mapsd.png';
            break;
        case APP_KYDOCK:// 码头
            iconpath = 'img/mapmt.png';
            break;
        case APP_HYDOCK:// 码头
            iconpath = 'img/mapmt.png';
            break;
        case APP_GWDOCK:// 码头
            iconpath = 'img/mapmt.png';
            break;
        case APP_SHIPYARD:// 船厂
            iconpath = 'img/mapcc.png';
            break;
        case APP_TAKEOUTFALL:// 取排水口
            iconpath = 'img/mapqps.png';
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            iconpath = 'img/mapswz.png';
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            iconpath = 'img/mapglz.png';
            break;
        case APP_SERVICEAREA:// 服务区
            iconpath = 'img/mapfwq.png';
            break;
        case APP_MOORINGAREA:// 锚泊区
            iconpath = 'img/mapmbq.png';
            break;
        case APP_HUB:// 枢纽
            iconpath = 'img/mapsn.png';
            break;
        case APP_DAM:// 坝
            iconpath = 'img/mapb.png';
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            iconpath = 'img/mapzzha.png';
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            iconpath = 'img/mapgcd.png';
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            iconpath = 'img/mapspgcd.png';
            break;
        case APP_MANUALOBSERVATION:
            iconpath = 'img/mapgcd.png';
            break;
        case APP_BOLLARD:// 系缆桩
            iconpath = "img/mapxlz.png";
            break;
    }
    if (jd > 0 && wd > 0) {
        var lonlat = new WebtAPI.LonLat(jd, wd);
        var icon = new WebtAPI.WIcon(iconpath, size);
        var marker = new WebtAPI.WMarker(lonlat, icon);
        oldmap.markersLayer.addMarker(marker);
        oldmap.setCenterByLonLat(lonlat, 11);
        newmap.setCenterByLonLat(lonlat, 11);
    } else {
        var huzhou = new WebtAPI.LonLat(120.497122, 30.758621);
        oldmap.setCenterByLonLat(huzhou, 11);
        newmap.setCenterByLonLat(huzhou, 11);
    }
}

function addAddMapMarker(fswlx, jd, wd) {
    $("#addmapbefore").empty();
    $("#addmapafter").empty();
    var oldmap = new WebtAPI.WMap(WebtAPI.Util.getElement("addmapbefore"));
    var newmap = new WebtAPI.WMap(WebtAPI.Util.getElement("addmapafter"));
    oldmap.clearMarkers();
    newmap.clearMarkers();
    oldmap.setDisplayNauticalLayer(true); //开启海图
    newmap.setDisplayNauticalLayer(true); //开启海图
    var iconpath = 'img/mapmr.png';
    var size = new WebtAPI.Size('28px', '28px');
    switch (parseInt(fswlx)) {
        case APP_NAVIGATIONMARK:// 航标
            iconpath = 'img/maphb.png';
            break;
        case APP_BRIDGE:// 桥梁
            iconpath = 'img/mapql.png';
            break;
        case APP_AQUEDUCT:// 渡槽
            iconpath = 'img/mapdc.png';
            break;
        case APP_CABLE:// 缆线
            iconpath = 'img/maplx.png';
            break;
        case APP_PIPELINE:// 管道
            iconpath = 'img/mapgd.png';
            break;
        case APP_TUNNEL:// 隧道
            iconpath = 'img/mapsd.png';
            break;
        case APP_KYDOCK:// 码头
            iconpath = 'img/mapmt.png';
            break;
        case APP_HYDOCK:// 码头
            iconpath = 'img/mapmt.png';
            break;
        case APP_GWDOCK:// 码头
            iconpath = 'img/mapmt.png';
            break;
        case APP_SHIPYARD:// 船厂
            iconpath = 'img/mapcc.png';
            break;
        case APP_TAKEOUTFALL:// 取排水口
            iconpath = 'img/mapqps.png';
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            iconpath = 'img/mapswz.png';
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            iconpath = 'img/mapglz.png';
            break;
        case APP_SERVICEAREA:// 服务区
            iconpath = 'img/mapfwq.png';
            break;
        case APP_MOORINGAREA:// 锚泊区
            iconpath = 'img/mapmbq.png';
            break;
        case APP_HUB:// 枢纽
            iconpath = 'img/mapsn.png';
            break;
        case APP_DAM:// 坝
            iconpath = 'img/mapb.png';
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            iconpath = 'img/mapzzha.png';
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            iconpath = 'img/mapgcd.png';
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            iconpath = 'img/mapspgcd.png';
            break;
        case APP_MANUALOBSERVATION:
            iconpath = 'img/mapgcd.png';
            break;
        case APP_BOLLARD:// 系缆桩
            iconpath = "img/mapxlz.png";
            break;
    }
    if (jd > 0 && wd > 0) {
        var lonlat = new WebtAPI.LonLat(jd, wd);
        var icon = new WebtAPI.WIcon(iconpath, size);
        var marker = new WebtAPI.WMarker(lonlat, icon);
        newmap.markersLayer.addMarker(marker);
        oldmap.setCenterByLonLat(lonlat, 11);
        newmap.setCenterByLonLat(lonlat, 11);
    } else {
        var huzhou = new WebtAPI.LonLat(120.497122, 30.758621);
        oldmap.setCenterByLonLat(huzhou, 11);
        newmap.setCenterByLonLat(huzhou, 11);
    }
}

function addUpdateMapMarker(fswlx, jd, wd, i) {
    var iconpath = 'img/mapmr.png';
    var size = new WebtAPI.Size('28px', '28px');
    switch (parseInt(fswlx)) {
        case APP_NAVIGATIONMARK:// 航标
            iconpath = 'img/maphb.png';
            break;
        case APP_BRIDGE:// 桥梁
            iconpath = 'img/mapql.png';
            break;
        case APP_AQUEDUCT:// 渡槽
            iconpath = 'img/mapdc.png';
            break;
        case APP_CABLE:// 缆线
            iconpath = 'img/maplx.png';
            break;
        case APP_PIPELINE:// 管道
            iconpath = 'img/mapgd.png';
            break;
        case APP_TUNNEL:// 隧道
            iconpath = 'img/mapsd.png';
            break;
        case APP_KYDOCK:// 码头
            iconpath = 'img/mapmt.png';
            break;
        case APP_HYDOCK:// 码头
            iconpath = 'img/mapmt.png';
            break;
        case APP_GWDOCK:// 码头
            iconpath = 'img/mapmt.png';
            break;
        case APP_SHIPYARD:// 船厂
            iconpath = 'img/mapcc.png';
            break;
        case APP_TAKEOUTFALL:// 取排水口
            iconpath = 'img/mapqps.png';
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            iconpath = 'img/mapswz.png';
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            iconpath = 'img/mapglz.png';
            break;
        case APP_SERVICEAREA:// 服务区
            iconpath = 'img/mapfwq.png';
            break;
        case APP_MOORINGAREA:// 锚泊区
            iconpath = 'img/mapmbq.png';
            break;
        case APP_HUB:// 枢纽
            iconpath = 'img/mapsn.png';
            break;
        case APP_DAM:// 坝
            iconpath = 'img/mapb.png';
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            iconpath = 'img/mapzzha.png';
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            iconpath = 'img/mapgcd.png';
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            iconpath = 'img/mapspgcd.png';
            break;
        case APP_MANUALOBSERVATION:
            iconpath = 'img/mapgcd.png';
            break;
        case APP_BOLLARD:// 系缆桩
            iconpath = "img/mapxlz.png";
            break;
    }
    if (i == 0) {
        $("#updatemapbefore").empty();
        var oldmap = new WebtAPI.WMap(WebtAPI.Util.getElement("updatemapbefore"));
        oldmap.clearMarkers();
        oldmap.setDisplayNauticalLayer(true); //开启海图
        if (jd > 0 && wd > 0) {
            var lonlat = new WebtAPI.LonLat(jd, wd);
            var icon = new WebtAPI.WIcon(iconpath, size);
            var marker = new WebtAPI.WMarker(lonlat, icon);
            oldmap.markersLayer.addMarker(marker);
            oldmap.setCenterByLonLat(lonlat, 11);
        } else {
            var huzhou = new WebtAPI.LonLat(120.497122, 30.758621);
            oldmap.setCenterByLonLat(huzhou, 11);
        }
    }
    if (i == 1) {
        $("#updatemapafter").empty();
        var newmap = new WebtAPI.WMap(WebtAPI.Util.getElement("updatemapafter"));
        newmap.clearMarkers();
        newmap.setDisplayNauticalLayer(true); //开启海图
        if (jd > 0 && wd > 0) {
            var lonlat = new WebtAPI.LonLat(jd, wd);
            var icon = new WebtAPI.WIcon(iconpath, size);
            var marker = new WebtAPI.WMarker(lonlat, icon);
            newmap.markersLayer.addMarker(marker);
            newmap.setCenterByLonLat(lonlat, 11);
        } else {
            var huzhou = new WebtAPI.LonLat(120.497122, 30.758621);
            newmap.setCenterByLonLat(huzhou, 11);
        }
    }


}

function showDelChartMap(fswlx) {
    try {
        jwd = transferjwd(oldjd, oldwd, function (jwd) {
            var arr = new Array();
            arr = jwd.split(" ");
            addDelMapMarker(fswlx, arr[0], arr[1]);
        });
    } catch (e) {
    }
}

function showAddChartMap(fswlx) {
    var modelname = "";
    switch (parseInt(fswlx)) {
        case APP_NAVIGATIONMARK:// 航标
            modelname = "CHdHb";
            break;
        case APP_BRIDGE:// 桥梁
            modelname = "CHdQl";
            break;
        case APP_AQUEDUCT:// 渡槽
            modelname = "CHdDc";
            break;
        case APP_CABLE:// 缆线
            modelname = "CHdLx";
            break;
        case APP_PIPELINE:// 管道
            modelname = "CHdGd";
            break;
        case APP_TUNNEL:// 隧道
            modelname = "CHdSd";
            break;
        case APP_KYDOCK:// 客运码头
            modelname = "CHdKymt";
            break;
        case APP_HYDOCK:// 货运码头
            modelname = "CHdHymt";
            break;
        case APP_GWDOCK:// 公务码头
            modelname = "CHdGwmt";
            break;
        case APP_SHIPYARD:// 船厂
            modelname = "CHdCc";
            break;
        case APP_TAKEOUTFALL:// 取排水口
            modelname = "CHdQpsk";
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            modelname = "CHdSwz";
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            modelname = "CHdGlz";
            break;
        case APP_SERVICEAREA:// 服务区
            modelname = "CHdFwq";
            break;
        case APP_MOORINGAREA:// 锚泊区
            modelname = "CHdMbq";
            break;
        case APP_HUB:// 枢纽
            modelname = "CHdSn";
            break;
        case APP_DAM:// 坝
            modelname = "CHdB";
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            modelname = "CHdZzha";
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            modelname = "CHdJgllgcd";
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            modelname = "CHdSpgcd";
            break;
        case APP_MANUALOBSERVATION:// 人工流量观测点
            modelname = "CHdRggcd";
            break;
        case APP_BOLLARD:// 系缆桩
            modelname = "CHdXlz";
            break;
    }
    newjd = $("#" + modelname + "_jd").val();
    newwd = $("#" + modelname + "_wd").val();
    try {
        if (newjd > 0 && newwd > 0) {
            jwd = transferjwd(newjd, newwd, function (jwd) {
                var arr = new Array();
                arr = jwd.split(" ");
                addAddMapMarker(fswlx, arr[0], arr[1]);
            });
        } else {
            addAddMapMarker(fswlx, 0, 0);
        }

    } catch (e) {
    }
}

function showUpdateChartMap(fswlx) {
    var jdwds = new Array();
    if (oldjd > 0 && oldwd > 0) {
        jdwds.push({
            'jd': oldjd,
            'wd': oldwd
        });
    } else {
        jdwds.push({
            'jd': 0,
            'wd': 0
        });
    }
    var modelname = "";
    switch (parseInt(fswlx)) {
        case APP_NAVIGATIONMARK:// 航标
            modelname = "CHdHb";
            break;
        case APP_BRIDGE:// 桥梁
            modelname = "CHdQl";
            break;
        case APP_AQUEDUCT:// 渡槽
            modelname = "CHdDc";
            break;
        case APP_CABLE:// 缆线
            modelname = "CHdLx";
            break;
        case APP_PIPELINE:// 管道
            modelname = "CHdGd";
            break;
        case APP_TUNNEL:// 隧道
            modelname = "CHdSd";
            break;
        case APP_KYDOCK:// 客运码头
            modelname = "CHdKymt";
            break;
        case APP_HYDOCK:// 货运码头
            modelname = "CHdHymt";
            break;
        case APP_GWDOCK:// 公务码头
            modelname = "CHdGwmt";
            break;
        case APP_SHIPYARD:// 船厂
            modelname = "CHdCc";
            break;
        case APP_TAKEOUTFALL:// 取排水口
            modelname = "CHdQpsk";
            break;
        case APP_HYDROLOGICALSTATION:// 水文站
            modelname = "CHdSwz";
            break;
        case APP_MANAGEMENTSTATION:// 管理站
            modelname = "CHdGlz";
            break;
        case APP_SERVICEAREA:// 服务区
            modelname = "CHdFwq";
            break;
        case APP_MOORINGAREA:// 锚泊区
            modelname = "CHdMbq";
            break;
        case APP_HUB:// 枢纽
            modelname = "CHdSn";
            break;
        case APP_DAM:// 坝
            modelname = "CHdB";
            break;
        case APP_REGULATIONREVEMENT:// 整治护岸
            modelname = "CHdZzha";
            break;
        case APP_LASEROBSERVATION:// 激光流量观测点
            modelname = "CHdJgllgcd";
            break;
        case APP_VIDEOOBSERVATION:// 视频观测点
            modelname = "CHdSpgcd";
            break;
        case APP_MANUALOBSERVATION:// 人工流量观测点
            modelname = "CHdRggcd";
            break;
        case APP_BOLLARD:// 系缆桩
            modelname = "CHdXlz";
            break;
    }
    newjd = $("#" + modelname + "_jd").val();
    newwd = $("#" + modelname + "_wd").val();
    if (newjd > 0 && newwd > 0) {
        jdwds.push({
            'jd': newjd,
            'wd': newwd
        });
    } else {
        jdwds.push({
            'jd': 0,
            'wd': 0
        });
    }
    try {
        transferjwds(jdwds, function (jdwds) {
            for (var i = 0; i < jdwds.length; i++) {
                addUpdateMapMarker(fswlx, jdwds[i].jd, jdwds[i].wd, i);
            }
        })
    } catch (e) {
    }
}

function loadaddchart(fswlx) {
    $("#addfswform").validateForm(
        function () {
            $("#modaladdchart").modal('show');
            showAddChartMap(fswlx);
        }
    );

};

function loadupdatechart(fswlx) {
    $("#editfswform").validateForm(
        function () {
            $("#modalupdatechart").modal('show');
            showUpdateChartMap(fswlx);
        }
    );
};

function initjwdfswlx() {
    oldjd = 0;
    oldwd = 0;
    newjd = 0;
    newwd = 0;
    chartfswlx = 0;
}

// 加载编辑附属物时弹窗内容
function exportfswinfo(fsw) {
    var fswid = fsw.fswid;
    var fswsecondclassid = fsw.fswsecondclassid;
    window.location.href = $("#basePath").val() + "appurtenance/exportfswinfo?id=" + fswid + "&fswlx=" + fswsecondclassid;
}
