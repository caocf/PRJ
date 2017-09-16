<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>首页</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/huzhoumain.css">
		<script src="js/common/jquery-1.5.2.min.js">
</script>
		<script type="text/javascript" src="js/homePage.js">
</script>

<script type="text/javascript" src="js/common/CheckLogin.js"></script>
<script src="js/officoa/knowledgelist.js"></script>
	</head>

	<body>
	<input type="hidden" value="<%=basePath%>" id="basePath" />
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<div id="style_one" class="style_top">
			<img  src="image/top/muniu.png" align="left">首页
		</div>
		<div id="content_one" class="content">
			<div>
				<span id="content_one_span_1"><%=(String) session.getAttribute("username")%><span id="wenhouDate">早上好</span>，欢迎使用湖州港航移动综合管理平台</span>
				<span id="content_one_span_2"><a href="<%=basePath%>page/system/ChangPassword.jsp">修改密码</a> </span>
			</div>
		</div>
		<div id="style_two" class="style_top">
			待办事务
		</div>
		<div id="content_two" class="content">
		<div>
				<span><a href="<%=basePath%>page/electric/electricReportList.jsp">[航行电子报告]</a> </span>
				<span>今天有<span id="report" class="Span_num"></span>条航行电子报告</span>
			</div>
			<div>
				<span><a href="<%=basePath%>page/LeaveAndOvertime/LeaveAndOvertime.jsp">[考勤管理]</a> </span>
				<span>您有<span id="leaveorot" class="Span_num"></span>条请假加班申请未审批</span>
			</div>
			<div>
				<span><a href="<%=basePath%>page/business/law.jsp">[违章信息]</a> </span>
				<span>您有<span id="illega" class="Span_num"></span>条违章信息待审核</span>
			</div>
			
			<div>
				<span><a href="<%=basePath%>page/business/dangerousGoodsPortln.jsp">[船舶进港]</a> </span>
				<span>您有<span id="dangerousarrivaldeclare" class="Span_num"></span>条危险品船舶进港申请未审批</span>
			</div>
			<div>
				<span><a href="<%=basePath%>page/business/dangerousGoodsJob.jsp">[码头作业]</a> </span>
				<span>您有<span id="dangerousworkdeclare" class="Span_num"></span>条码头作业报告申请未审批</span>
			</div>
		</div>
		
		<div id="style_three" class="style_top">
			通知信息
		</div>
		<div id="content_three" class="content">
			<div>
				<table id="content_three_table">
				</table>
			</div>			
		</div>
		<div  style="margin:20px;cursor: pointer;background-color: #337ab7;width:100px;height:40px;text-align: center;font-size: 14px;vertical-align: middle;line-height: 40px;" 
			onclick="addkonwledge(1)">通知发布
			<!-- <img alt="通知发布" src="image/operation/add_know_normal.png" class="u3_img" id="addButton"
						onclick="addkonwledge(1)" onMouseOver="addkonwOver(this)" onMouseOut="addkonwOut(this)"/>-->
		</div>
		<div id="style_three" class="style_top">
			九位码
		</div>
		<div  style="margin:20px;cursor: pointer;background-color: #337ab7;width:100px;height:40px;text-align: center;font-size: 14px;vertical-align: middle;line-height: 40px;" 
			onclick="toBusiness()">九位码核查
			<!-- <img alt="通知发布" src="image/operation/add_know_normal.png" class="u3_img" id="addButton"
						onclick="addkonwledge(1)" onMouseOver="addkonwOver(this)" onMouseOut="addkonwOut(this)"/>-->
		</div>
	</body>
</html>
