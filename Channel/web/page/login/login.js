$(document).ready(function () {
    // 如果有记住密码，尝试加载
    var checkRemPwd = getCookie("rempwd", "/");
    if (checkRemPwd) {
        var user = DecodeCookie(getCookie("user", "/"));
        var pwd = DecodeCookie(getCookie("upwd", "/"));

        $("#inputUser").val(user);
        $("#inputPwd").val(pwd);
        $("#checkRemPwd").attr('checked', 'checked');

        $("#btnLogin").focus();
    } else {
        $("#inputUser").val('');
        $("#inputPwd").val('');
        $("#inputUser").focus();
    }
    $("#btnLogin").on('click', function () {
        login();
    });
    $("#verifyCode_span").on("click",function () {
       re();
    });
    //initperm();
});
var sum = 0;
var isFirst = true;
var time = new Date();
function initperm() {
    $.ajax({
        url: 'user/login',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (ifResultOK(data)) { // 如果登录成功，则记住密码
                window.location.href = "page/home/home.jsp";
            }
        }
    });
}
//change by 庄佳彬 at 2017-03-28
function re() {
    $("#verifyCode").attr( "src", "user/verifyCode?t=" + Math.random());
}

function clearLoginInfo(){
    sum = 0;
    isFirst = true;
    localStorage.removeItem($("#inputUser").val());
    localStorage.removeItem($("#inputUser").val()+"sum");
}

function login() {
    var now = new Date();
    sum = localStorage.getItem($("#inputUser").val()+"sum");
    var tt = localStorage.getItem($("#inputUser").val());
    if(tt != null && parseInt(tt) > now.getTime()){
        time.setTime(parseInt(tt));
    }

    if(sum != null && sum >= 5){
        var str;
        if(parseInt((time.getTime() - now.getTime())/60/1000) > 0){
            str = parseInt((time.getTime() - now.getTime())/60/1000)+"分钟后再试"
        }else {
            str = (parseInt((time.getTime() - now.getTime())/1000) > 0 ? parseInt((time.getTime() - now.getTime())/1000):0)+"秒后再试"
        }
        showerr("用户名或密码错误连续5次请"+str);
        if(isFirst){
            alert(0);
            localStorage.setItem($("#inputUser").val(),time.getTime());
            isFirst = false;
            setTimeout(function () {
                sum = 0;
                isFirst = true;
                localStorage.removeItem($("#inputUser").val());
                localStorage.removeItem($("#inputUser").val()+"sum");
            },time.getTime() - now.getTime());
        }
        return;
    }
    console.log("login");
    var user = $("#inputUser").val();
    var pwd = $("#inputPwd").val();
    var checkRemPwd = $("#checkRemPwd").is(":checked");
    //change by 庄佳彬 at 2017-03-28
    var verifyCode = $("#input_identifying_code").val();

    if (user == null || user == "") {
        $("#inputUser").focus();
        $("#divUser").addClass("has-error");
        showerr("用户名不能为空");
        return;
    }
    if (pwd == null || pwd == "") {
        $("#inputPwd").focus();
        $("#divPwd").addClass("has-error");
        showerr("密码不能为空");
        return;
    }

    deleteCookie("rempwd", "/");
    deleteCookie("user", "/");
    deleteCookie("upwd", "/");
    $.ajax({
        url: 'user/login',
        type: 'post',
        dataType: 'json',
        data: {
            'username': user,
            'password': pwd,
            verifyCode:verifyCode//change by 庄佳彬 at 2017-03-28
        },
        beforeSend:function(){
            showloading("登录中,请耐心等待...");
        },
        success: function (data) {

            hideerr();//隐藏错误信息
            re();//刷新验证码
            if (ifResultOK(data)) { // 如果登录成功，则记住密码
                sum = 0;
                isFirst = true;
                localStorage.removeItem($("#inputUser").val());
                localStorage.removeItem($("#inputUser").val()+"sum");
                if (checkRemPwd) {
                    setCookie("rempwd", checkRemPwd, 24, "/");
                    setCookie("user", CodeCookie(user), 24, "/");
                    setCookie("upwd", CodeCookie(pwd), 24, "/");
                } else {
                    deleteCookie("rempwd", "/");
                    deleteCookie("user", "/");
                    deleteCookie("upwd", "/");
                }
                window.location.href = $("#basePath").val() + "page/home/home.jsp";
            } else {
                // if (getResultCode(data) == RESULT_USER_NOTEXIST) {
                //     $("#inputUser").focus();
                //     $("#divUser").addClass("has-error");
                //     showerr("用户不存在");
                // }
                // if (getResultCode(data) == RESULT_USER_DISABLE) {
                //     $("#inputUser").focus();
                //     $("#divUser").addClass("has-error");
                //     showerr("用户已禁用，请联系管理员");
                // }
                // if (getResultCode(data) == RESULT_USER_DELETE) {
                //     $("#inputUser").focus();
                //     $("#divUser").addClass("has-error");
                //     showerr("用户不存在");
                // }
                // if (getResultCode(data) == RESULT_USER_PASSERROR) {
                //     $("#inputPwd").focus();
                //     $("#divPwd").addClass("has-error");
                //     showerr("密码错误");
                // }
                if(getResultCode(data) == VERIFY_CODE_ERROR){
                    $("#input_identifying_code").focus();
                    $("#identifying_code").addClass("has-error");
                    showerr(getResultDesc(data));
                }else{
                    sum++;
                    localStorage.setItem($("#inputUser").val()+"sum",sum);
                    if(sum == 5){
                        time = new Date();
                        time.setTime(time.getTime() + 5*60*1000);
                    }
                    $("#inputPwd").focus();
                    $("#divPwd").addClass("has-error");
                    $("#inputUser").focus();
                    $("#divUser").addClass("has-error");
                    showerr("用户名或密码错误还有" + (5-sum)+"机会");
                }
                hideloading();//隐藏遮罩
            }
        },
        error: function (data) {
            showerr("登录失败，请重新登录！");
            deleteCookie("rempwd", "/");
            deleteCookie("user", "/");
            deleteCookie("upwd", "/");
        }
    });
}

function showerr(msg) {
    $("#errmsg").html(msg);
    $("#errmsg").removeClass('hide');
    $("#errmsg").addClass('show');
}

function hideerr() {
    $("#divPwd").removeClass('has-error');
    $("#divUser").removeClass('has-error');
    $("#errmsg").removeClass('show');
    $("#errmsg").addClass('hide');
    $("#identifying_code").removeClass('has-error');
}

function keylogin() {
    if (event.keyCode == 13) // 回车键的键值为13
        $("#btnLogin").click(); // 调用登录按钮的登录事件
}