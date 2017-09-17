/**
 * Created by Dj on 2015/7/14.
 */
$(document).ready(function () {
    var username = $("#username").val();
    if (username == null || username == "" || username == "null") {
        window.location.href = $("#basePath").val() + "page/login/login.jsp";
    }

    initui();
    loadversion();
});

function initui() {
    // 用户信息
    $("#divPersonalInfo").bind(
        'mouseover',
        function () {
            $("#divUserInfoImg").css('background-image',
                "url('img/home_yonghuzhongxin_pressed.png')");
            $("#ulPersonalInfo").removeClass('hide');
            $("#ulPersonalInfo").addClass('show');
        });
    $("#divPersonalInfo").bind(
        'mouseout',
        function () {
            $("#divUserInfoImg").css('background-image',
                "url('img/home_yonghuzhongxin.png')");
            $("#ulPersonalInfo").removeClass('show');
            $("#ulPersonalInfo").addClass('hide');
        });

    $("#lbUsername").html($("#uname").val()+"("+$("#deptname").val()+")");
    $("#alogout").bind('click', function () {
        logout();
        return false;
    });
}

function loadversion() {
    $.ajax({
        url: 'queryversion',
        type: 'post',
        dataType: 'json',
        data: {},
        success: function (data) {
            if (ifResultOK(data)) { // 如果登录成功，则记住密码
                $("#lbVersion").html(data.map.versionname);
            } else {
                $("#lbVersion").html("");
            }
        },
        error: function (data) {
            $("#lbVersion").html("");
        }
    });
}