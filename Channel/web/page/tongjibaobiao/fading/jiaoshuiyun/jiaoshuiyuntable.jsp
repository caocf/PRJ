<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.channel.model.user.CXtUser" %>
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

    <title>交水运表</title>

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
    <script type="text/javascript"
            src="common/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/unauth.js"></script>

    <style>
        table td {
            word-break: keep-all;
            white-space: nowrap;
        }
    </style>
    <script>
        $(document).ready(function () {
            var width = document.getElementById("contenttable").scrollWidth + 50;
            $(document.body).css('width', width);
            document.getElementById("titletable").width = width;

            $(parent.document.getElementById("baobiaoiframe")).css('width', width);
            $(parent.document.getElementsByClassName("container-fluid")).css('width', width);

            var title = $("#pretitle").text().trim();
            try {
                title = title.split('\n')[0];
            } catch (E) {
                title = "交水运表";
            }
            $(parent.document.getElementById("divtitle")).text(title);
        });
    </script>
</head>

<body style="background: white;overflow:hidden;width:1700px;font-family: 宋体;">
<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<div class="container-fluid">
    <c:choose>
        <c:when test="${data == null}">
            <div class="col-xs-12">
                <center>
                    <h3>暂无报表数据</h3>
                </center>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row" style="">
                <div class="col-xs-12">
                    <table class="table nomargin" id="titletable">
                        <c:set var="flag" value="0"/>
                        <c:forEach items="${data[0]}" var="cell">
                            <c:if test="${cell.cellvalue != ''}">
                                <c:set var="flag" value="${flag+1}"/>
                            </c:if>
                            <c:choose>
                                <c:when test="${flag==1}">
                                    <td class="text-left bbcontent ${cell.cssclass==null? '':cell.cssclass}"
                                        style="border:0;vertical-align: bottom;${cell.cssstyle==null? '':cell.cssstyle}"
                                        rowspan="${cell.rspan}"
                                        colspan="${cell.cspan}">
                                        <pre style="background: transparent;border:0;margin:0px;padding:0px;font-size: inherit;font-weight: inherit; font-family: inherit;">${cell.cellvalue}</pre>
                                    </td>
                                </c:when>
                                <c:when test="${flag==2}">
                                    <td class="text-center bbtitle ${cell.cssclass==null? '':cell.cssclass}"
                                        style="border:0;vertical-align: middle;${cell.cssstyle==null? '':cell.cssstyle}"
                                        rowspan="${cell.rspan}"
                                        colspan="${cell.cspan}">
                                        <pre id="pretitle"
                                             style="background: transparent;border:0;margin:0px;padding:0px;font-size: inherit;font-weight: inherit; font-family: inherit;">${cell.cellvalue}</pre>
                                    </td>

                                </c:when>
                                <c:when test="${flag==3}">
                                    <td class="text-center bbcontent ${cell.cssclass==null? '':cell.cssclass}"
                                        style="border:0;vertical-align: bottom;${cell.cssstyle==null? '':cell.cssstyle}"
                                        rowspan="${cell.rspan}"
                                        colspan="${cell.cspan}">
                                        <pre style="background: transparent;border:0;margin:0px;padding:0px;font-size: inherit;font-weight: inherit; font-family: inherit;">${cell.cellvalue}</pre>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td class="text-center bbcontent ${cell.cssclass==null? '':cell.cssclass}"
                                        style="border:0;vertical-align: bottom;${cell.cssstyle==null? '':cell.cssstyle}"
                                        rowspan="${cell.rspan}"
                                        colspan="${cell.cspan}">
                                        <pre style="background: transparent;border:0;margin:0px;padding:0px;font-size: inherit;font-weight: inherit; font-family: inherit;">${cell.cellvalue}</pre>
                                    </td>
                                </c:otherwise>
                            </c:choose>

                        </c:forEach>
                        </tr>
                    </table>
                    <table class="table table-bordered" id="contenttable">
                            <%--遍历行--%>
                        <c:forEach items="${data}" begin="1" var="row" varStatus="ss">
                            <c:choose>
                                <c:when test="${ss.last}">
                                    <tr>
                                            <%--写最后一行的每一列--%>
                                        <c:forEach items="${row}" var="cell">
                                            <td class="text-left bbcontent ${cell.cssclass==null? '':cell.cssclass}"
                                                style="vertical-align: middle;font-size: 12px;${cell.cssstyle==null? '':cell.cssstyle}"
                                                rowspan="${cell.rspan}"
                                                colspan="${cell.cspan}">
                                                <pre style="background: transparent;border:0;margin:0px;padding:0px;font-size: inherit;font-weight: inherit; font-family: inherit;">${cell.cellvalue}</pre>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                            <%--写每一列--%>
                                        <c:forEach items="${row}" var="cell">
                                            <td class="text-center bbcontent ${cell.cssclass==null? '':cell.cssclass}"
                                                style="vertical-align: middle;font-size: 12px;${cell.cssstyle==null? '':cell.cssstyle}"
                                                rowspan="${cell.rspan}"
                                                colspan="${cell.cspan}">
                                                <pre style="background: transparent;border:0;margin:0px;padding:0px;font-size: inherit;font-weight: inherit; font-family: inherit;">${cell.cellvalue}</pre>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
