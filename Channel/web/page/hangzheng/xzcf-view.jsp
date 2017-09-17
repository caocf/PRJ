<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.channel.model.user.CXtUser" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    CXtUser user = ((CXtUser) session.getAttribute("user"));
    String username = null;
    int userid = -1;
    if (user != null) {
        username = user.getUsername();
        userid = user.getId();
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>Page</title>

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

</head>

<body>
<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<div class="container-fluid" id="container">
    <table class="table table-bordered tableview">
        <tr>
            <td  width="20%" class="text-right" style="background-color: whitesmoke">受理号</td>
            <td  width="30%" class="text-left">${xzcf.slh}</td>
            <td  width="20%" class="text-right" style="background-color: whitesmoke">中文船名</td>
            <td  width="30%" class="text-left">${xzcf.zwcm}</td>
        </tr>
        <tr>
            <td class="text-right" style="background-color: whitesmoke">案由</td>
            <td class="text-left" colspan="3">${xzcf.ay}</td>
        </tr>
        <tr>
            <td  width="20%" class="text-right" style="background-color: whitesmoke">受理时间</td>
            <td  width="30%" class="text-left">${xzcf.slsj}</td>
            <td  width="20%" class="text-right" style="background-color: whitesmoke">发案时间</td>
            <td  width="30%" class="text-left">${xzcf.fasj}</td>
        </tr>
        <tr>
            <td  width="20%" class="text-right" style="background-color: whitesmoke">发案地点</td>
            <td  width="30%" class="text-left">${xzcf.fadd}</td>
            <td  width="20%" class="text-right" style="background-color: whitesmoke">执法手册编号</td>
            <td  width="30%" class="text-left">${xzcf.zfscbh}</td>
        </tr>
        <tr>
            <td class="text-right" style="background-color: whitesmoke">主要事实</td>
            <td class="text-left" colspan="3">${xzcf.zyss}</td>
        </tr>
        <tr>
            <td class="text-right" style="background-color: whitesmoke">违法内容</td>
            <td class="text-left" colspan="3">${xzcf.wfnr}</td>
        </tr>
        <tr>
            <td class="text-right" style="background-color: whitesmoke">违法条款</td>
            <td class="text-left" colspan="3">${xzcf.wftk}</td>
        </tr>
        <tr>
            <td class="text-right" style="background-color: whitesmoke">处罚条款</td>
            <td class="text-left" colspan="3">${xzcf.cftk}</td>
        </tr>
        <tr>
            <td class="text-right" style="background-color: whitesmoke">处罚意见</td>
            <td class="text-left" colspan="3">${xzcf.cfyj}</td>
        </tr>
        <tr>
            <td  width="20%" class="text-right" style="background-color: whitesmoke">处罚类别</td>
            <td  width="30%" class="text-left">${xzcf.cflb}</td>
            <td  width="20%" class="text-right" style="background-color: whitesmoke">是否处罚</td>
            <td  width="30%" class="text-left">${xzcf.sfcf==1?"是":"否"}</td>
        </tr>
        <tr>
            <td  width="20%" class="text-right" style="background-color: whitesmoke">是否结案</td>
            <td  width="30%" class="text-left">${xzcf.sfja==1?"是":"否"}</td>
            <td  width="20%" class="text-right" style="background-color: whitesmoke">结案日期</td>
            <td  width="30%" class="text-left">${xzcf.jarq}</td>
        </tr>
    </table>
</div>
</body>
</html>
