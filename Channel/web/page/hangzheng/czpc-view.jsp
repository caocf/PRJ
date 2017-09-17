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
    <div class="form-horizontal" style="margin-top:-15px;" id="addthlzform">
        <div class="row borderbottom">
            <div class="form-group">
                <label class="col-xs-3 col-xs-offset-1 control-label" style="margin-top:15px;">占用（损坏）单位</label>

                <div class="col-xs-4" style="margin-top:15px;">
                    <label class="control-label">${czpc.dw}</label>
                </div>
            </div>
        </div>
        <div class="row borderbottom">
            <div class="form-group">
                <label class="col-xs-3 col-xs-offset-1 control-label" style="padding-left:0px;margin-top:15px;">占用（损坏）项目名称</label>

                <div class="col-xs-4" style="margin-top:15px;">
                    <label class="control-label">${czpc.name}</label>
                </div>
            </div>
        </div>

        <div class="row borderbottom">
            <div class="form-group">
                <label class="col-xs-3 col-xs-offset-1 control-label"
                       style="margin-top:15px;">占用（损坏）位置</label>

                <div class="col-xs-7" style="margin-top:15px;">
                    <label class="control-label">${mapinfo.hdao}</label>
                    <label class="control-label">${mapinfo.hduan}</label>
                </div>
            </div>
        </div>
        <div class="row borderbottom">
            <div class="form-group">
                <label class="col-xs-3 col-xs-offset-1 control-label"
                       style="margin-top:15px;">占用（损坏）类型</label>

                <div class="col-xs-7" style="margin-top:15px;">
                    <c:forEach items="${mapinfo.lx}" var="i">
                        <c:if test="${i.dfl==1}">
                            <label class="control-label">码头&nbsp;${i.sl}&nbsp;米</label><br>
                        </c:if>
                        <c:if test="${i.dfl==2}">
                            <label class="control-label">护岸&nbsp;${i.sl}&nbsp;米</label><br>
                        </c:if>
                        <c:if test="${i.dfl==3}">
                            <c:if test="${i.xfl==1}">
                                <label class="control-label">绿化&nbsp;香樟&nbsp;<c:if test="${i.sx==1}">&nbsp;胸径￠10cm以下&nbsp;<fmt:parseNumber integerOnly="true" value="${i.sl}" />&nbsp;棵</c:if><c:if
                                        test="${i.sx==2}">&nbsp;胸径￠10~20cm&nbsp;<fmt:parseNumber integerOnly="true" value="${i.sl}" />&nbsp;棵</c:if><c:if
                                        test="${i.sx==3}">&nbsp;胸径￠20cm以上&nbsp;<fmt:parseNumber integerOnly="true" value="${i.sl}" />&nbsp;棵</c:if></label><br>
                            </c:if>
                            <c:if test="${i.xfl==2}">
                                <label class="control-label">绿化&nbsp;柳树&nbsp;<c:if test="${i.sx==1}">&nbsp;胸径￠10cm以下&nbsp;<fmt:parseNumber integerOnly="true" value="${i.sl}" />&nbsp;棵</c:if><c:if
                                        test="${i.sx==2}">&nbsp;胸径￠10~20cm&nbsp;<fmt:parseNumber integerOnly="true" value="${i.sl}" />&nbsp;棵</c:if><c:if
                                        test="${i.sx==3}">&nbsp;胸径￠20cm以上&nbsp;<fmt:parseNumber integerOnly="true" value="${i.sl}" />&nbsp;棵</c:if></label><br>
                            </c:if>
                            <c:if test="${i.xfl==3}">
                                <label class="control-label">绿化&nbsp;速生杨&nbsp;<c:if test="${i.sx==1}">&nbsp;胸径￠10~20cm&nbsp;<fmt:parseNumber integerOnly="true" value="${i.sl}" />&nbsp;棵</c:if><c:if
                                        test="${i.sx==2}">&nbsp;胸径￠20cm以上&nbsp;<fmt:parseNumber integerOnly="true" value="${i.sl}" />&nbsp;棵</c:if></label><br>
                            </c:if>
                            <c:if test="${i.xfl==4}">
                                <label class="control-label">绿化&nbsp;海桐球&nbsp;<c:if test="${i.sx==1}">&nbsp;胸径￠50cm以下&nbsp;<fmt:parseNumber integerOnly="true" value="${i.sl}" />&nbsp;棵</c:if><c:if
                                        test="${i.sx==2}">&nbsp;胸径￠50cm以上&nbsp;<fmt:parseNumber integerOnly="true" value="${i.sl}" />&nbsp;棵</c:if></label><br>
                            </c:if>
                            <c:if test="${i.xfl==5}">
                                <label class="control-label">绿化&nbsp;观赏乔木&nbsp;<fmt:parseNumber integerOnly="true" value="${i.sl}" />&nbsp;棵</label><br>
                            </c:if>
                            <c:if test="${i.xfl==6}">
                                <label class="control-label">绿化&nbsp;观赏灌木&nbsp;${i.sl}&nbsp;平方</label><br>
                            </c:if>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="row borderbottom">
            <div class="form-group">
                <label class="col-xs-3 col-xs-offset-1 control-label" style="margin-top:15px;">占用（损坏）性质</label>

                <div class="col-xs-4" style="margin-top:15px;">
                    <label class="control-label">${mapinfo.xz}</label>
                </div>
            </div>
        </div>
        <div class="row borderbottom">
            <div class="form-group">
                <label class="col-xs-3 col-xs-offset-1 control-label" style="margin-top:15px;">赔补偿金额</label>

                <div class="col-xs-4" style="margin-top:15px;">
                    <label class="control-label">${czpc.pbcje}</label>
                </div>
            </div>
        </div>
        <div class="row borderbottom">
            <div class="form-group">
                <label class="col-xs-3 col-xs-offset-1 control-label" style="margin-top:15px;">受理单位</label>

                <div class="col-xs-4" style="margin-top:15px;">
                    <label class="control-label">${czpc.sldw}</label>
                </div>
            </div>
        </div>
        <div class="row borderbottom">
            <div class="form-group">
                <label class="col-xs-3 col-xs-offset-1 control-label" style="margin-top:15px;">资产评估单位</label>

                <div class="col-xs-4" style="margin-top:15px;">
                    <label class="control-label">${czpc.zcpgdw}</label>
                </div>
            </div>
        </div>
        <div class="row borderbottom">
            <div class="form-group">
                <label class="col-xs-3 col-xs-offset-1 control-label" style="margin-top:15px;">受理日期</label>

                <div class="col-xs-4" style="margin-top:15px;">
                    <label class="control-label">
                        <fmt:formatDate value="${czpc.slrq}"
                                        pattern="yyyy年MM月dd日 HH:mm:ss"/>
                    </label>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
