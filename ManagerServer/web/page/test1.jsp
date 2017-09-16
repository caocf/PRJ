
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

    <title>Home</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>

    <link rel="stylesheet" type="text/css"
          href="common/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/plugins/fontawesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/css/common.css">

    <script type="text/javascript" language="javascript"
            src="../js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/plugins/bootstrap/js/bootstrap.min.js"></script>

</head>

<body style="text-align:center;">

<h1 align="center">调试 </h1>
    <hr/>
    <a onclick="wharfdanger()">点击开始安装</a>
    <script  >
        function wharfdanger()
        {
                    /*var form=$("<form>");//定义一个form表单
                    form.attr("style","display:none");
                    form.attr("target","");
                    form.attr("method","post");
                    form.attr("action","AISCeretByShipName");
                    var input1=$("<input>");
                    input1.attr("type","hidden");
                    input1.attr("name","shipname");
                    input1.attr("value","浙长兴货3955");
                    $("body").append(form);//将表单放置在web中
                    form.append(input1);

                    form.submit();//表单提交*/
        }
    </script>

<div id="showdata"/>
</body>
</html>
