<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
            <td class="text-right">项目名称</td>
            <td class="text-left" colspan="3">${xzxk.xmmc}</td>
        </tr>
        <tr>
            <td class="text-right">项目位置</td>
            <td class="text-left" colspan="3">${xzxk.xmwz}</td>
        </tr>
        <!--------------项目类型-------------->
        <c:if test="${xzxk.xmlx==1}">
            <tbody>
            <tr>
                <td class="text-right" rowspan="4">项目类型</td>
                <td class="text-left" rowspan="4">桥梁</td>
                <td class="text-right">结构形式</td>
                <td class="text-left">${xzxk.qljgxs}</td>
            </tr>
            <tr>
                <td class="text-right">通航净宽(m)</td>
                <td class="text-left">${xzxk.qlthjk}</td>
            </tr>
            <tr>
                <td class="text-right">通航净高(m)</td>
                <td class="text-left">${xzxk.qlthjg}</td>
            </tr>
            <tr>
                <td class="text-right">与航道夹角(°)</td>
                <td class="text-left">${xzxk.qlyhdjj}</td>
            </tr>
            </tbody>
        </c:if> <c:if test="${xzxk.xmlx==2}">
        <tbody>
        <tr>
            <td class="text-right" rowspan="4">项目类型</td>
            <td class="text-left" rowspan="4">架空缆线</td>
            <td class="text-right">类别</td>
            <td class="text-left">${xzxk.lxlb}</td>
        </tr>
        <tr>
            <td class="text-right">跨径(m)</td>
            <td class="text-left">${xzxk.lxkj}</td>
        </tr>
        <tr>
            <td class="text-right">通航净高(m)</td>
            <td class="text-left">${xzxk.lxthjg}</td>
        </tr>
        <tr>
            <td class="text-right">塔基离岸距离(m)</td>
            <td class="text-left">${xzxk.lxtjlajl}</td>
        </tr>
        </tbody>
    </c:if> <c:if test="${xzxk.xmlx==3}">
        <tbody>
        <tr>
            <td class="text-right">项目类型</td>
            <td class="text-left">水下管线</td>
            <td class="text-right">类别</td>
            <td class="text-left">${xzxk.gxlb}</td>
        </tr>
        </tbody>
    </c:if> <c:if test="${xzxk.xmlx==4}">
        <tbody>
        <tr>
            <td class="text-right" rowspan="3">项目类型</td>
            <td class="text-left" rowspan="3">隧道</td>
            <td class="text-right">管线顶标高(m)</td>
            <td class="text-left">${xzxk.sdgxdbg}</td>
        </tr>
        <tr>
            <td class="text-right">埋设深度(m)</td>
            <td class="text-left">${xzxk.sdmssd}</td>
        </tr>
        <tr>
            <td class="text-right">入土口离岸距离(m)</td>
            <td class="text-left">${xzxk.sdrtklajl}</td>
        </tr>
        </tbody>
    </c:if> <c:if test="${xzxk.xmlx==5}">
        <tbody>
        <tr>
            <td class="text-right" rowspan="4">项目类型</td>
            <td class="text-left" rowspan="4">取排水</td>
            <td class="text-right">伸出岸线距离(m)</td>
            <td class="text-left">${xzxk.qpskscaxjl}</td>
        </tr>
        <tr>
            <td class="text-right">最高点标高(m)</td>
            <td class="text-left">${xzxk.qpskzgdbg}</td>
        </tr>
        <tr>
            <td class="text-right">横向流速(m/s)</td>
            <td class="text-left">${xzxk.qpskhxls}</td>
        </tr>
        <tr>
            <td class="text-right">回流流速(m/s)</td>
            <td class="text-left">${xzxk.qpskhlls}</td>
        </tr>
        </tbody>
    </c:if>
        <c:if test="${xzxk.xmlx==6}">
        <tbody>
        <tr>
            <td class="text-right" rowspan="3">项目类型</td>
            <td class="text-left" rowspan="3">闸坝</td>
            <td class="text-right">通航吨级(t)</td>
            <td class="text-left">${xzxk.zbthdj}</td>
        </tr>
        <tr>
            <td class="text-right">闸室尺度(m)</td>
            <td class="text-left">${xzxk.zbzscd}</td>
        </tr>
        <tr>
            <td class="text-right">闸门提升净高(m)</td>
            <td class="text-left">${xzxk.zbzmtsjg}</td>
        </tr>
        </tbody</c:if>
                <!---------------高程说明---------------->
        <tr>
            <td class="text-right" rowspan="2">高程系统说明</td>
            <td class="text-left" rowspan="2">${xzxk.gcxt}</td>
            <td class="text-right">设计最高通航水位(m)</td>
            <td class="text-left">${xzxk.sjzgthsw}</td>
        </tr>
        <tr>
            <td class="text-right">设计最低通航水位(m)</td>
            <td class="text-left">${xzxk.sjzdthsw}</td>
        </tr>
        <tr>
            <td class="text-right">建筑物性质</td>
            <td class="text-left" colspan="3">
                <c:if test="${xzxk.jzwxz==1}">新建</c:if>
                <c:if test="${xzxk.jzwxz==2}">改扩建</c:if>
            </td>
        </tr>
        <tr>
            <td class="text-right">申请单位</td>
            <td class="text-left" colspan="3">${xzxk.sqdw}</td>
        </tr>
        <tr>
            <td class="text-right">地址</td>
            <td class="text-left" colspan="3">${xzxk.dz}</td>
        </tr>
        <tr>
            <td class="text-right">申请单位经办人</td>
            <td class="text-left">${xzxk.sqdwjbr}</td>
            <td class="text-right">联系电话</td>
            <td class="text-left">${xzxk.lxdh}</td>
        </tr>
        <tr>
            <td class="text-right">项目设计单位</td>
            <td class="text-left" colspan="3">${xzxk.xmsjdw}</td>
        </tr>
        <tr>
            <td class="text-right">许可受理单位</td>
            <td class="text-left" colspan="3">${xzxk.xksldw}</td>
        </tr>
        <tr>
            <td class="text-right">许可编号</td>
            <td class="text-left">${xzxk.xkbh}</td>
            <td class="text-right">许可日期</td>
            <td class="text-left"><fmt:formatDate value="${xzxk.xkrq}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
    </table>
</div>
</body>
</html>
