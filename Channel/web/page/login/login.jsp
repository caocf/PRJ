<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>登录</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>

    <link rel="stylesheet" type="text/css"
          href="common/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="page/common/css/common.css">
    <link rel="stylesheet" type="text/css" href="page/login/login.css">

    <script type="text/javascript" language="javascript"
            src="common/common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/common.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/login/login.js?t=123"></script>
</head>
<%--<body>--%>
<%--<div id="loading">--%>

<%--</div>--%>
<%--</body>--%>
<body onkeydown="keylogin()" style="min-width:inherit">

<input type="hidden" id="basePath" value="<%=basePath%>">

<div style="
width:770px;height:380px;
left:50%;top:40%;position: fixed;
margin:-190px 0 0 -385px;">
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-10 col-xs-offset-1 text-center">
                <img src="img/logo.png">&nbsp;&nbsp;
                <label class="lbTitle">浙江省航道运行管理系统</label>
            </div>
        </div>
        <div class="row" style="margin-top: 15px;">
            <div class="col-xs-8 col-xs-offset-2">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="row shadow center-block" style="background:white;">
                            <div class="row">
                                <div class="col-xs-10 col-xs-offset-1" style="margin-top: 20px;">
                                    <h4>
                                        用户登录&nbsp;
                                        <small>SIGNIN</small>
                                    </h4>
                                </div>
                            </div>
                            <div class="form-horizontal">
                                <div class="text-center" style="height:20px;">
                                    <label id="errmsg" class="lbErrmsg hide">请输入用户名</label>
                                </div>
                                <div class="form-group">
                                    <label for="inputUser" class="col-sm-1 control-label sr-only">Email</label>

                                    <div class="col-sm-10">
                                        <div id="divUser" class="input-group">
<span class="input-group-addon" style="height:44px;"><i
        class="icon-user"></i></span>
                                            <input id="inputUser" type="text" class="form-control btn-lg"
                                                   placeholder="请输入用户名"
                                                   style="height:44px;">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="divPwd" class="col-sm-1 control-label sr-only">Password</label>

                                    <div class="col-sm-10">
                                        <div id="divPwd" class="input-group">
                                            <span class="input-group-addon" style="height:44px;"><i class="icon-key"></i></span>
                                            <input id="inputPwd" type="password" class="form-control btn-lg" placeholder="请输入密码" style="height:44px;">
                                        </div>
                                    </div>
                                </div>
                                <!--change by 庄佳彬 at 2017-03-28-->
                                <div class="form-group">
                                    <label for="identifying_code" class="col-sm-1 control-label sr-only">验证码</label>

                                    <div class="col-sm-10">
                                        <div id="identifying_code" class="input-group">
                                            <span id="verifyCode_span" class="input-group-addon" style="height:44px;padding-top: 0px;padding-bottom: 0px;"><img id="verifyCode" src="${pageContext.request.contextPath }/user/verifyCode" /></span>
                                            <input id="input_identifying_code" type="text" class="form-control btn-lg" placeholder="请输入验证码" style="height:44px;">
                                        </div>
                                    </div>
                                </div>
                                <!---->
                                <div class="form-group">
                                    <div class="col-sm-offset-1 col-sm-10">
                                        <div class="checkbox" style="">
                                            <label> <input type="checkbox" id="checkRemPwd">记住密码
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group" style="margin-bottom:35px;">
                                    <div class="col-sm-offset-1 col-sm-10">
                                        <input class="btn btn-block btn-primary btn-lg" type="button"
                                               id="btnLogin" value="登录">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
