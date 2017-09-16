<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/7/19
  Time: 9:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>浙江港航综合管理与服务平台</title>
    <!-- Bootstrap 3.3.4 -->
    <link href="css/common/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- FontAwesome 4.3.0 -->
    <%--<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />--%>
    <link href="css/common/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Ionicons 2.0.0 -->
    <%--<link href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet" type="text/css" />--%>
    <link href="css/common/ionicons.min.css" rel="stylesheet" type="text/css" />
    <!-- jQuery 2.1.4 -->
    <script src="js/common/jQuery-2.1.4.min.js"></script>
    <!-- Bootstrap 3.3.2 JS -->
    <script src="js/common/bootstrap.min.js" type="text/javascript"></script>
</head>
<body style="background-image: url(image/login/start_page_bg.jpg)" onload="">
<%--<a href="login/personal">登录</a>--%>
    <div id="logo" align="center" style="margin-top: 8%">
        <img src="image/login/start_page_logo.png" alt="User Image" />
    </div>
    <div id="frame" align="center">
        <div id="background" style="margin-top: 2%;background-image: url(image/login/start_page_borders.png);width: 1040px;height: 188px">
            <div id="info" style="margin-right: 30%;" >
                <div  style="float: right;width:400px;margin-top:40px;margin-right:50px;color: rgb(70,221,230);">
                <%--<div id="myCarousel" class="carousel slide" style="float: right;width:400px;margin-top:40px;margin-right:50px;color: rgb(70,221,230);">--%>
                <%--<!-- 轮播（Carousel）项目 -->--%>
                <%--<div class="carousel-inner">--%>
                    <%--<div class="item active">--%>
                        <%--阿大使12312312312321312312321--%>
                    <%--</div>--%>
                    <%--<div class="item">--%>
                        <%--adsad--%>
                    <%--</div>--%>
                    <%--<div class="item">--%>
                        <%--as发酸发--%>
                    <%--</div>--%>
                <%--</div>--%>
                    <marquee direction="left" behavior="scroll" scrollamount="5" scrolldelay="0" loop="-1" align="left">
                        　　省局荣获2015年度全省绿色交通创建考核第一名
                    </marquee>
                </div>
                <img src="image/login/start_page_ic_notice.png" style="margin-top:40px ;margin-right:10px;float: right;"/>
<%--<script>--%>
    <%--$(document).ready(function(){--%>
    <%--$("#myCarousel").carousel('cycle');--%>
    <%--})--%>
<%--</script>--%>
            <a href="login/personal"><img src="image/login/start_page_ic_noticebtn_login_normal.png" style="margin-top: 30px"/></a>
        </div>
    </div>
    <div id="footer" align="center" style="margin-top: 10%">
        <strong style="color: #3695ff">浙江省港航管理版权所有</strong>
    </div>

</body>
</html>
