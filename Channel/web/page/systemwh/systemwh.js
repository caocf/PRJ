function centerModals() {
    $('.modal').each(
        function (i) {
            var $clone = $(this).clone().css('display', 'block').appendTo(
                'body');
            var top = Math.round(($clone.height() - $clone.find(
                    '.modal-content').height()) / 2);
            top = top > 0 ? top : 0;
            $clone.remove();
            $(this).find('.modal-content').css("margin-top", top);
        });
}

function commoninit(tabname) {
    // 未登录返回登录页面
    var username = $("#username").val();
    if (username == null || username == "" || username == "null") {
        window.location.href = $("#basePath").val() + "page/login/login.jsp";
    }

    /*// 返回按钮
     $('#back').click(function() {
     window.location.href = $("#basePath").val() + "page/home/home.jsp";
     });*/

    // 点击时标签页进行切换
    $('#menu a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    // 标签页切换，新标签显示后回调函数，此函数中加载相关数据。
    $('#tabuser').bind('click', function () {
        window.location.href = $("#basePath").val() + "page/systemwh/user/systemwh-user.jsp";
    });
    $('#tabdpt').bind('click', function () {
        window.location.href = $("#basePath").val() + "page/systemwh/dpt/systemwh-dpt.jsp";
    });
    $('#tabperm').bind('click', function () {
        window.location.href = $("#basePath").val() + "page/systemwh/perm/perm.jsp";
    });

    $('#tabdict').bind('click', function () {
        window.location.href = $("#basePath").val() + "page/systemwh/dict/systemwh-dict.jsp";
    });
    $('#tablog').bind('click', function () {
        window.location.href = $("#basePath").val() + "page/systemwh/log/systemwh-log.jsp";
    });
    $('#tabbackup').bind('click', function () {
        window.location.href = $("#basePath").val() + "page/systemwh/back/systemwh-back.jsp";
    });

    // bootstrap中下拉菜单中每一行点击都会触发 下拉菜单事件，关闭， 可通过添加
    // data-stopPropagation=true对该行进行事件传递停止。
    $("ul.dropdown-menu").on("click", "[data-stopPropagation]", function (e) {
        e.stopPropagation();
    });

    if (tabname != null) {
        if (tabname == '用户管理') {
            $("#tabuser").addClass("active");
        }

        if (tabname == '组织机构管理') {
            $("#tabdpt").addClass("active");
        }

        if (tabname == '角色权限') {
            $("#btnperm").addClass("active");
            $("#liperm").addClass("active");
        }

        if (tabname == '数据字典') {
            $("#tabdict").addClass("active");
        }

        if (tabname == '日志管理') {
            $("#tablog").addClass("active");
        }

        if (tabname == '备份还原') {
            $("#tabbackup").addClass("active");
        }
    }
}

	