<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.channel.model.user.CXtUser" %>
<%@page import="com.channel.model.user.CXtDpt" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    CXtUser user = ((CXtUser) session.getAttribute("user"));
    CXtDpt dept = ((CXtDpt) session.getAttribute("dpt"));
    String username = null;
    String uname = null;
    int userid = -1;
    if (user != null) {
        username = user.getUsername();
        uname=user.getName();
        userid = user.getId();
    }
    String deptname="";
    if (dept!=null){
        deptname=dept.getName();
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>主页</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>

    <link rel="stylesheet" type="text/css"
          href="common/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="common/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css"
          href="page/common/css/common.css">
    <link rel="stylesheet" type="text/css"
          href="page/home/home.css">
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
            src="page/home/home.js"></script>
</head>
<body>

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="uname" value="<%=uname%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="deptname" value="<%=deptname%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<div class="container-fluid" style="width:85%;margin-top:33px;">
    <div class="row">
        <div class="col-xs-6 col-xs-offset-1">
            <img class="imgTitle" src="img/home_logo.png"> <label
                class="lbTitle">浙江省航道运行管理系统</label>
        </div>
        <div class="col-xs-4">
            <div class="dropdown" id="divPersonalInfo" style="float: right">
                <button class="btn" type="button" id="btnPersonalInfo"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    <label id="lbUsername" class="lbUsername"></label>
                    <div id="divUserInfoImg" style=""></div>
                </button>
                <ul id="ulPersonalInfo" class="dropdown-menu"
                    aria-labelledby="dropdownMenu1" style="margin-left: 50px">
                    <li><a onclick="window.open('page/personalinfo/personalinfo.jsp');"><i
                            class="icon-user" style="color: blue;"></i>&nbsp;&nbsp;个人设置</a></li>
                    <li class="divider">
                    <li><a href="#" id="alogout"><i class="icon-off"
                                                    style="color: red;"></i>&nbsp;&nbsp;退出</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top:33px;">
        <div class="col-xs-4 col-xs-offset-1 nopadding">
            <div class="row nomargin">
                <div class="col-xs-12 nopadding" id="div_hangdaoxinxi">
                    <c:choose>
                        <c:when test="${ui.groupHasPerms('综合查询')}">
                            <img id="imgHangdaoxinxi" class="" style="width:100%;"
                                 src="img/home_zonghechaxun.png"
                                 onmouseover="$(this).attr('src', 'img/home_zonghechaxun_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_zonghechaxun.png');$(this).css('cursor', 'default')"
                                 onclick="tohdaoxinxi();">
                        </c:when>
                        <c:otherwise>
                            <img id="imgHangdaoxinxi" class="" style="width:100%;"
                                 src="img/home_zonghechaxun.png"
                                 onmouseover="$(this).attr('src', 'img/home_zonghechaxun_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_zonghechaxun.png');$(this).css('cursor', 'default')"
                                 onclick="$('#modalnoperm').modal('show');">
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
            <div class="row nomargin">
                <div class="col-xs-12" style="line-height: 0px;">
                    <img style="width:100%" src="img/400_8.png">
                </div>
            </div>
            <div class="row nomargin" style="">
                <div class="col-xs-12 nopadding" id="div_liuliangguance">
                    <c:choose>
                        <c:when test="${ui.groupHasPerms('流量观测')}">
                            <img id="imgLiuliangguance" class="" style="width:100%;"
                                 src="img/home_liuliangguance.png"
                                 onmouseover="$(this).attr('src', 'img/home_liuliangguance_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_liuliangguance.png');$(this).css('cursor', 'default')"
                                 onclick="toliuliang();">
                        </c:when>
                        <c:otherwise>
                            <img id="imgLiuliangguance" class="" style="width:100%;"
                                 src="img/home_liuliangguance.png"
                                 onmouseover="$(this).attr('src', 'img/home_liuliangguance_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_liuliangguance.png');$(this).css('cursor', 'default')"
                                 onclick="$('#modalnoperm').modal('show');">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="col-xs-6 nopadding" style="margin-left:8px;">
            <div class="row nomargin">
                <div class="col-xs-8 nopadding" id="div_hangdaoxuncha">
                    <c:choose>
                        <c:when test="${ui.groupHasPerms('航道巡查')}">
                            <img id="imgHangdaoxuncha" class="" style="width:100%;"
                                 src="img/home_hangdaoxuncha.png"
                                 onmouseover="$(this).attr('src', 'img/home_hangdaoxuncha_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_hangdaoxuncha.png');$(this).css('cursor', 'default')"
                                 onclick="tohdaoxuncha();">
                        </c:when>
                        <c:otherwise>
                            <img id="imgHangdaoxuncha" class="" style="width:100%;"
                                 src="img/home_hangdaoxuncha.png"
                                 onmouseover="$(this).attr('src', 'img/home_hangdaoxuncha_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_hangdaoxuncha.png');$(this).css('cursor', 'default')"
                                 onclick="$('#modalnoperm').modal('show');">
                        </c:otherwise>
                    </c:choose>

                </div>
                <div class="col-xs-4 nopadding" id="div_hangzhengguanli"
                     style="margin-left:0px;">
                    <c:choose>
                        <c:when test="${ui.groupHasPerms('航政管理')}">
                            <img id="imgHangzhengguanli" class=""
                                 style="margin-left:8px;width:100%;"
                                 src="img/home_hangzhengguanli.png"
                                 onmouseover="$(this).attr('src', 'img/home_hangzhengguanli_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_hangzhengguanli.png');$(this).css('cursor', 'default')"
                                 onclick="tohangzheng();">
                        </c:when>
                        <c:otherwise>

                            <img id="imgHangzhengguanli" class=""
                                 style="margin-left:8px;width:100%;"
                                 src="img/home_hangzhengguanli.png"
                                 onmouseover="$(this).attr('src', 'img/home_hangzhengguanli_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_hangzhengguanli.png');$(this).css('cursor', 'default')"
                                 onclick="$('#modalnoperm').modal('show');">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="row nomargin" style="line-height: 0px;">
                <div class="col-xs-12">
                    <img style="width:100%" src="img/608_8.png">
                </div>
            </div>
            <div class="row nomargin">
                <div class="col-xs-4 nopadding" id="div_hangdaotubianji">
                    <c:choose>
                        <c:when test="${ui.groupHasPerms('航道信息维护')}">
                            <img id="imgHangdaotubianji" class="" style="width:100%;"
                                 src="img/home_hangdaoxinxiweihu.png"
                                 onmouseover="$(this).attr('src', 'img/home_hangdaoxinxiweihu_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_hangdaoxinxiweihu.png');$(this).css('cursor', 'default')"
                                 onclick="tohangdaotubj();">
                        </c:when>
                        <c:otherwise>
                            <img id="imgHangdaotubianji" class="" style="width:100%;"
                                 src="img/home_hangdaoxinxiweihu.png"
                                 onmouseover="$(this).attr('src', 'img/home_hangdaoxinxiweihu_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_hangdaoxinxiweihu.png');$(this).css('cursor', 'default')"
                                 onclick="$('#modalnoperm').modal('show');">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-xs-8 nopadding" id="div_hangdaoyanghu">
                    <c:choose>
                        <c:when test="${ui.groupHasPerms('航道养护')}">
                            <img id="imgHangdaoyanghu" class=""
                                 style="margin-left:8px;width:100%;"
                                 src="img/home_hangdaoyanghu.png"
                                 onmouseover="$(this).attr('src', 'img/home_hangdaoyanghu_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_hangdaoyanghu.png');$(this).css('cursor', 'default')"
                                 onclick="tohdaoyh();">
                        </c:when>
                        <c:otherwise>
                            <img id="imgHangdaoyanghu" class=""
                                 style="margin-left:8px;width:100%;"
                                 src="img/home_hangdaoyanghu.png"
                                 onmouseover="$(this).attr('src', 'img/home_hangdaoyanghu_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_hangdaoyanghu.png');$(this).css('cursor', 'default')"
                                 onclick="$('#modalnoperm').modal('show');">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="row nomargin" style="line-height: 0px;">
                <div class="col-xs-12">
                    <img style="width:100%" src="img/608_8.png">
                </div>
            </div>
            <div class="row nomargin">
                <div class="col-xs-8 nopadding" id="div_tongjibaobiao">
                    <c:choose>
                        <c:when test="${ui.groupHasPerms('统计报表')}">
                            <img id="imgTongjibaobiao" class="" style="width:100%;"
                                 src="img/home_tongjibaobiao.png"
                                 onmouseover="$(this).attr('src', 'img/home_tongjibaobiao_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_tongjibaobiao.png');$(this).css('cursor', 'default')"
                                 onclick="totongji();">
                        </c:when>
                        <c:otherwise>
                            <img id="imgTongjibaobiao" class="" style="width:100%;"
                                 src="img/home_tongjibaobiao.png"
                                 onmouseover="$(this).attr('src', 'img/home_tongjibaobiao_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_tongjibaobiao.png');$(this).css('cursor', 'default')"
                                 onclick="$('#modalnoperm').modal('show');">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-xs-4 nopadding" id="div_xitongweihu">
                    <c:choose>
                        <c:when test="${ui.groupHasPerms('系统维护')}">
                            <img id="imgXitongweihu" class=""
                                 style="margin-left:8px;width:100%;"
                                 src="img/home_xitongweihu.png"
                                 onmouseover="$(this).attr('src', 'img/home_xitongweihu_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_xitongweihu.png');$(this).css('cursor', 'default')"
                                 onclick="toxitongwh();">
                        </c:when>
                        <c:otherwise>
                            <img id="imgXitongweihu" class=""
                                 style="margin-left:8px;width:100%;"
                                 src="img/home_xitongweihu.png"
                                 onmouseover="$(this).attr('src', 'img/home_xitongweihu_pressed.png');$(this).css('cursor', 'pointer')"
                                 onmouseout="$(this).attr('src', 'img/home_xitongweihu.png');$(this).css('cursor', 'default')"
                                 onclick="$('#modalnoperm').modal('show');">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <div class="row text-center">
        <label class="footer">浙江省港航管理局 所有</label> <label class="footer"
                                                               id="lbVersion"></label>
    </div>
</div>

<div class="modal fade" id="modalnoperm" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">权限不足</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <p>
                        您无权限进入该功能！
                    </p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
