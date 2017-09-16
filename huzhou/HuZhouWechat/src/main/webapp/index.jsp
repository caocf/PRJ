<%--
  Created by IntelliJ IDEA.
  User: Will
  Date: 2016/8/15
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mvc" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>$Title$</title>
    <script type="text/javascript" src="js/jquery-1.11.1.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>
<a href="${pageContext.request.contextPath }/port_announcement/main.html"><spring:message code="port_announcement"/></a><br>
<a href="${pageContext.request.contextPath }/dynamics/main.html"><spring:message code="dynamics"/></a><br>
<a href="${pageContext.request.contextPath }/laws/main.html"><spring:message code="laws"/></a><br>
<a href="${pageContext.request.contextPath }/licensing/main.html"><spring:message code="licensing"/></a><br>
<a href="${pageContext.request.contextPath }/punish/main.html"><spring:message code="punish"/></a><br>
<a href="${pageContext.request.contextPath }/download.html"><spring:message code="download"/></a><br>
<a href="${pageContext.request.contextPath }/violation.html"><spring:message code="violation"/></a><br>
</body>
</html>
