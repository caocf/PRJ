<%@page import="com.channel.model.user.CXtUser" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

<div class="container-fluid nopadding" id="container">
    <div class="row form-horizontal" style="margin-top:-15px;" id="addthlzform">
        <div class="col-xs-12" style="background:#f0f0f0;height:40px;line-height: 40px;">
            通航论证概况
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" style="margin-top:15px;">项目名称</label>

            <div class="col-sm-6" style="margin-top:15px;">
                <label class="control-label">${thlz.name}</label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">论证时间</label>

            <div class="col-sm-4" id="divatime">
                <label class="control-label"><fmt:formatDate value="${thlz.atime}" pattern="yyyy-MM-dd"/></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">航道</label>

            <div class="col-sm-6">
                <label class="control-label">${mapinfo.hdao}</label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">参与人员</label>

            <div class="col-sm-6">
                <label class="control-label">${thlz.pname}</label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">审查意见</label>

            <div class="col-sm-9" id="divtextareanew">
                <label class="control-label" style="">${thlz.title}</label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">录入人</label>

            <div class="col-sm-9">
                <label class="control-label">${mapinfo.username}</label>
            </div>

        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">录入时间</label>

            <div class="col-sm-9">
                <label class="control-label">
                    <fmt:formatDate value="${thlz.createtime}"
                                    pattern="yyyy-MM-dd HH:mm:ss"/>
                </label>
            </div>
        </div>
        <div class="col-xs-12" style="background:#f0f0f0;height:40px;line-height: 40px;">
            附件信息 &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
        </div>
        <c:forEach items="${mapinfo.fjlist }" var="i"><br>

            <div>
                <div class="col-xs-12" style="background:#e4f0ff;margin-top:2px;">
                    <i class="icon-paper-clip" style="clolr:bule;">${i.fname}</i>&nbsp;
                    <label style="font-weight:normal;">${i.fsize}KB</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a class="btn btn-link" href="channelmanage/downloadchzfj?loginid=<%=userid%>&id=${i.fid}">下载</a>
                    <c:if test="${i.ftype==1}">
                        <a class="btn btn-link" onclick="viewattachment(${i.fid})">预览</a>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
