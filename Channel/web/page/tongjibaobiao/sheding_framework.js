$(document).ready(
    function () {
        $("#tabfading").click(
            function () {
                window.location.href = $("#basePath").val()
                    + "page/tongjibaobiao/fading_framework.jsp";
            });
        $("#tabsheding").click(
            function () {
                window.location.href = $("#basePath").val()
                    + "page/tongjibaobiao/sheding_framework.jsp";
            });
        resize();
        $(window).resize(function (event) {
            resize();
        });
        // 初始化左侧航道菜单折叠按钮事件
        $("#leftmenu").bind('click', function () {
            if ($("#leftmenu").attr('expand') == "1") {
                $("#leftmenu").removeClass('menuleftshouqi');
                $("#leftmenu").addClass('menuleftzhankai');
                $("#leftmenu").attr('expand', '0');

                $("#divleft").addClass('hide');
                $("#divright").css("margin-left", "15px");
            } else {
                $("#leftmenu").removeClass('menuleftzhankai');
                $("#leftmenu").addClass('menuleftshouqi');
                $("#leftmenu").attr('expand', '1');

                $("#divleft").removeClass('hide');
                $("#divright").css("margin-left", "335px");
            }
        });
        initleftmenu();
    });


function resize() {
    // 通过屏幕高度设置相应的滚动区域高度
    var clientheight = window.innerHeight;
    $("#baobiaotree").css('height', (clientheight - 53) + 'px');
    $("#divright").css('height', (clientheight - 68) + 'px');

}
function initleftmenu() {
    $("#baobiaotree").etree({
        id: '-1',
        childrendatafn: initfadingmenu
    });
}

function createmenufn(node, container) {
    container.attr('response-click', true);
    container.attr('response-select', true);
    container.css('padding-left', '20px');
    container.css('white-space', 'nowrap');
    container.html(node.name);
}

function initfadingmenu(pnode, fncallback) {
    var nodes = new Array();
    if (hasPerm('VIEW_SHENHDBB') || hasPerm('VIEW_SHIHDBB')) {
        nodes.push({
            id: '1',
            clicked: true,
            name: '1.航道现状等级汇总表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/sheding/hdxz.jsp');
            }
        });
        nodes.push({
            id: '4',
            name: '2.航道里程分类统计表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/sheding/hdlcfl.jsp');
            }
        });
        nodes.push({
            id: '2',
            name: '3.航道主要设施汇总表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/sheding/hdzyss.jsp');
            }
        });
        nodes.push({
            id: '3',
            name: '4.航道沿线主要设施分类汇总表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/sheding/hdyxssfl.jsp');
            }
        });
        nodes.push({
            id: '5',
            name: '5.各航道主要设施分类汇总表(行政区划)',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/sheding/ghdzyss.jsp');
            }
        });
        nodes.push({
            id: '6',
            name: '6.各航道主要设施分类汇总表(航道名称)',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/sheding/ghdzysshdmc.jsp');
            }
        });
        nodes.push({
            id: '7',
            name: '7.核查系统船舶流量汇总表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/sheding/cbll.jsp');
            }
        });
        nodes.push({
            id: '8',
            name: '8.激光流量汇总表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/sheding/jgll.jsp');
            }
        });
        nodes.push({
            id: '9',
            name: '9.航道信息同步',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/sheding/hdsyn.jsp');
            }
        });
    }
    fncallback(nodes);
}