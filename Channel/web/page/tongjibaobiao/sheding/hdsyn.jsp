<%@ page import="com.channel.model.user.CXtUser" %>
<%@ page import="com.channel.model.user.CXtDpt" %>
<%@ page import="com.channel.model.user.CZdXzqhdm" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    CXtDpt shiju = (CXtDpt) session.getAttribute("shiju");
    int szshiju = -2;
    if (shiju != null)
        szshiju = shiju.getId();

    CXtDpt chuju = (CXtDpt) session.getAttribute("chuju");
    int szchuju = -2;
    if (chuju != null)
        szchuju = chuju.getId();

    CXtDpt szjudpt = (CXtDpt) session.getAttribute("dpt");
    int szju = -2;
    if (szjudpt != null)
        szju = szjudpt.getId();

    CZdXzqhdm dptshixzqh = (CZdXzqhdm) session.getAttribute("dptshixzqh");
    int szshixzqh = -2;
    if (dptshixzqh != null)
        szshixzqh = dptshixzqh.getId();
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
    <link rel="stylesheet"
          href="common/bootstrap-multiselect/css/bootstrap-multiselect.css">

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
    <script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" src="common/common/js/tree.js"></script>
    <script type="text/javascript" src="page/common/js/loaddpt.js"></script>

    <script>
        function printbaobiao() {
            var sel = $('#selfsw').val();
            var xzqh = $("#selxzqh").attr('selitem');
            switch (parseInt(sel)) {
                case 1:
                    window.location.href = $("#basePath").val()
                            + "/statistics/exporthdsyn?flag="
                            + sel + "&xzqh=" + xzqh;
                    break;
            }
        }
    </script>

</head>

<body style="background:#f0f0f0;padding-bottom: 20px;">

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szchuju" value="<%=szchuju%>">
<input type="hidden" id="szju" value="<%=szju%>">
<input type="hidden" id="szshixzqh" value="<%=szshixzqh%>">

<div class="container-fluid  shadow border" style="background:white;">
    <div class="row navcontenttitle borderbottom">
        <div class="col-xs-12">航道信息同步</div>
    </div>

    <div class="row borderbottom" style="padding:10px 0 10px 0;">
        <div class="col-xs-1 text-right">
            <label style="margin-top:8px;">行政区划</label>
        </div>
        <div class="col-xs-2" id="selxzqhdiv">
            <script>
                <c:if test="${ui.hasPerm('VIEW_SHENHDBB')}">
                $("#selxzqhdiv").addselxzqh(
                        {
                            id: "selxzqh",
                            defaultval: 1
                        }
                );
                </c:if>
                <c:if test="${ui.hasPerm('VIEW_SHIHDBB')}">
                $("#selxzqhdiv").addselxzqh(
                        {
                            id: "selxzqh",
                            defaultval: <%=szshixzqh%>
                        }, <%=szshixzqh%>
                );
                </c:if>
            </script>
        </div>
        <div class="col-xs-3">
            <label style="float:left;margin-top:8px;width:20%;">分类</label>

            <div id="selfswdiv" style="float:left;width:60%;">
                <script>
                    $('#selfswdiv').addselect({
                        id: 'selfsw',
                        nohint: true,
                        defaultval: '1',
                        data: {
                            1: '航道',
                            2: '航段',
                            3: '桥梁',
                            4: '航标',
                            5: '码头',
                            6: '管线'
                        }
                    });
                </script>
            </div>
        </div>
        <div class="col-xs-2">
            <button class="btn btn-default" onclick="printbaobiao();">导出</button>
        </div>
    </div>
</div>
</body>
</html>
