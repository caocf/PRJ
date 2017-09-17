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

function initleftmenu() {
    $("#baobiaotree").etree({
        id: '-1',
        childrendatafn: initfadingmenu
    });
}

function resize() {
    // 通过屏幕高度设置相应的滚动区域高度
    var clientheight = window.innerHeight;
    $("#baobiaotree").css('height', (clientheight - 53) + 'px');
    $("#divright").css('height', (clientheight - 68) + 'px');
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
    var checked = true;
    if (hasPerm('VIEW_SHENGGYHBB') || hasPerm('VIEW_SHIGGYHBB') || hasPerm('VIEW_DPTGGYHBB')) {
        nodes.push({
            id: '1',
            clicked: checked,
            name: '1.内河骨干航道例行养护工作统计表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/fading/guganyanghu_report.jsp');
            }
        });
        checked = false;
    }
    if (hasPerm('VIEW_SHENZXYHBB') || hasPerm('VIEW_SHIZXYHBB') || hasPerm('VIEW_DPTZXYHBB')) {
        nodes.push({
            id: '2',
            clicked: checked,
            name: '2.全区其他航道例行养护工作统计表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/fading/qitayanghu_report.jsp');
            }
        });
        checked = false;
    }
    if (hasPerm('VIEW_SHENZXGCBB') || hasPerm('VIEW_SHIZXGCBB') || hasPerm('VIEW_DPTZXGCBB')) {
        nodes.push({
            id: '3',
            clicked: checked,
            name: '3.专项养护工程完成情况工作统计表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/fading/zhuanxiang_report.jsp');
            }
        });
        checked = false;
    }
    if (hasPerm('VIEW_JSYBB')) {
        nodes.push({
            id: '4',
            clicked: checked,
            name: '4.航道维护里程统计分项表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/fading/jiaoshuiyun.jsp?tableindex=61');
            }
        });
        checked = false;
    }
    if (hasPerm('VIEW_JSYBB')) {
        nodes.push({
            id: '5',
            clicked: checked,
            name: '5.主要河流(海区)航道维护尺度统计分项表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/fading/jiaoshuiyun.jsp?tableindex=62');
            }
        });
        checked = false;
    }
    if (hasPerm('VIEW_JSYBB')) {
        nodes.push({
            id: '6',
            clicked: checked,
            name: '6.航标维护统计分项表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/fading/jiaoshuiyun.jsp?tableindex=63');
            }
        });
        checked = false;
    }
    if (hasPerm('VIEW_JSYBB')) {
        nodes.push({
            id: '7',
            clicked: checked,
            name: '7.主要过船建筑物分布与技术状况统计分项表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/fading/jiaoshuiyun.jsp?tableindex=65');
            }
        });
    }
    if (hasPerm('VIEW_JSYBB')) {
        nodes.push({
            id: '8',
            clicked: checked,
            name: '8.航道管理情况统计分项表',
            createnodefn: createmenufn,
            clickfn: function (node, event) {
                $("#iframewin").attr('src', 'page/tongjibaobiao/fading/jiaoshuiyun.jsp?tableindex=671');
            }
        });
        checked = false;
    }
    fncallback(nodes);
}