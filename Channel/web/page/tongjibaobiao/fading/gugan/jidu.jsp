<%@page import="com.channel.model.user.CXtUser" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

    <title>报表</title>

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
    <script type="text/javascript" language="javascript"
            src="page/common/js/unauth.js"></script>

</head>

<body style="background: white;overflow: hidden;font-family: 宋体;">
<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12">
            <center>
                <h2 class="bbtitle">内河骨干航道例行养护工作统计表</h2>
            </center>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-4 pull-left bbcontent">单位：${mapinfo.dpt }</div>
        <div class="col-xs-4 text-right pull-right bbcontent">${mapinfo.selyear }年${mapinfo.selseason }季度</div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <table class="table table-bordered" style="table-layout: fixed">
                <tr>
                    <td colspan="2" class="text-center" style="width:200px;padding:0 0 0 0;">
                        <img class="img-responsive" src="img/line_italic.png" style="width:100%;height:80%;">
                        <label class="bbcontentbold" style="position: absolute;left:50px;top:30px;">工作量</label>
                        <label class="bbcontentbold" style="position: absolute;left:140px;top:5px;">航道名称</label></td>
                    <c:forEach items="${mapinfo.hdmc }" var="i">
                        <td class="text-center bbcontentbold" style="height:30px;line-height:30px;"><c:out
                                value="${i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontentbold" style="height:30px;line-height:30px;">小计</td>
                    <td class="text-center bbcontentbold" style="height:30px;line-height:30px;">备注</td>
                </tr>
                <tr>
                    <td rowspan="2" class="text-center bbcontent" style="vertical-align: middle">测量</td>
                    <td class="text-center bbcontent">平方公里</td>
                    <c:forEach items="${mapinfo.clsl }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0.0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumclsl ==0?'':mapinfo.sljg.sumclsl}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">元</td>
                    <c:forEach items="${mapinfo.cljg }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumcljg==0?'':mapinfo.sljg.sumcljg }</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent" rowspan="2" style="vertical-align: middle">疏浚</td>
                    <td class="text-center bbcontent">立方米</td>
                    <c:forEach items="${mapinfo.sjsl }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumsjsl ==0?'':mapinfo.sljg.sumsjsl}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">元</td>
                    <c:forEach items="${mapinfo.sjjg }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumsjjg ==0?'':mapinfo.sljg.sumsjjg}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent" rowspan="2" style="vertical-align: middle">整治建筑物修复</td>
                    <td class="text-center bbcontent">平方米</td>
                    <c:forEach items="${mapinfo.zzjzwxfsl }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumzzjzwxfsl ==0?'':mapinfo.sljg.sumzzjzwxfsl}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">元</td>
                    <c:forEach items="${mapinfo.zzjzwxfjg }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumzzjzwxfjg==0?'':mapinfo.sljg.sumzzjzwxfjg }</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent" rowspan="2" style="vertical-align: middle">管理码头修复</td>
                    <td class="text-center bbcontent">座</td>
                    <c:forEach items="${mapinfo.glmtxfsl }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumglmtxfsl ==0?'':mapinfo.sljg.sumglmtxfsl}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">元</td>
                    <c:forEach items="${mapinfo.glmtxfjg }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumglmtxfjg ==0?'':mapinfo.sljg.sumglmtxfjg}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent" rowspan="2" style="vertical-align: middle">系缆桩修复</td>
                    <td class="text-center bbcontent">个</td>
                    <c:forEach items="${mapinfo.xlzxfsl }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumxlzxfsl ==0?'':mapinfo.sljg.sumxlzxfsl}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">元</td>
                    <c:forEach items="${mapinfo.xlzxfjg }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumxlzxfjg ==0?'':mapinfo.sljg.sumxlzxfjg}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent" rowspan="2" style="vertical-align: middle">航标维护</td>
                    <td class="text-center bbcontent">座/座次</td>
                    <c:forEach items="${mapinfo.hbwhsl }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i=='0/0'?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumhbwhsl=='0/0'?'':mapinfo.sljg.sumhbwhsl }</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">元</td>
                    <c:forEach items="${mapinfo.hbwhjg }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumhbwhjg==0?'':mapinfo.sljg.sumhbwhjg }</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent" rowspan="3" style="vertical-align: middle">无主碍航物清除</td>
                    <td class="text-center bbcontent">艘/吨位</td>
                    <c:forEach items="${mapinfo.wzahwqcssl }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i=='0/0'?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumwzahwqcssl=='0/0'?'':mapinfo.sljg.sumwzahwqcssl }</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">处/吨</td>
                    <c:forEach items="${mapinfo.wzahwqccsl }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i=='0/0'?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumwzahwqccsl =='0/0'?'':mapinfo.sljg.sumwzahwqccsl}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">元</td>
                    <c:forEach items="${mapinfo.wzahwqcjg }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumwzahwqcjg ==0?'':mapinfo.sljg.sumwzahwqcjg}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent" rowspan="2" style="vertical-align: middle">绿化养护</td>
                    <td class="text-center bbcontent">平方米</td>
                    <c:forEach items="${mapinfo.lhyhsl }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumlhyhsl ==0?'':mapinfo.sljg.sumlhyhsl}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">元</td>
                    <c:forEach items="${mapinfo.lhyhjg }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td class="text-center bbcontent">${mapinfo.sljg.sumlhyhjg ==0?'':mapinfo.sljg.sumlhyhjg}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent" colspan="2">合计金额(元)</td>
                    <c:forEach items="${mapinfo.totalamount }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i==0?'':i}"></c:out></td>
                    </c:forEach>
                    <td></td>
                </tr>
            </table>
        </div>
    </div>
    <div class="row" style="margin-bottom: 20px;">
        <div class="col-xs-12 pull-left bbcontent">注:1.本辖区内河骨干航道：xx公里.航道巡查里程数：xx公里，参加巡查人员：xx人次，发送联系单数：x份。</div>
        <div class="col-xs-12 pull-left bbcontent">&nbsp;&nbsp;&nbsp;&nbsp;2.本表是指已经完成的养护工作量。</div>
    </div>
</div>
</body>
</html>
