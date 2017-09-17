<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-交通常识</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="<%=basePath%>WebSit/css/trafficNews/trafficNews.css" type="text/css"></link>
			

		<script type="text/javascript"	src="WebSit/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>
		<style type="text/css">
		.com_div {
			margin-top: 10px;
			margin-bottom: 10px;
			border-bottom: dashed 1px blue;
		}
		
		.com_title {
			cursor: pointer;
			margin-top: 10px;
			margin-bottom: 10px;
			font-size: 16px;
			font-weight: bold;
		}
		
		.com_title:hover {
			color: #FF9900
		}
		
		.com_title img {
			vertical-align: bottom;
		}
		
		.com_time {
			float: right;
			font-size: 12px;
			line-height: 20px;
			font-weight: normal;
		}
		
		.com_content {
			font-size: 14px;
			font-weight: normal;
			letter-spacing: 2px;
			color: rgb(51, 51, 51);
			line-height: 20px;
			text-indent: 2em;
			margin-bottom: 20px;
			display: none
		}
		</style>
		<script type="text/javascript">
			function ChangeDiv(thisop, id) {
				if (thisop != null) {
					$$(".main_left_select").attr("class", "main_left_select_no");
					thisop.className = "main_left_select";
				}
				$$(".main_right_li").css("display", "none");
				$$("#" + id).css("display", "block");
			}
			function ToggleP(kind,id) {
				window.location.href=$$("#basePath").val()+"WebSit/page/knowledge/knowledge_detail.jsp?id="+id+"&kind="+kind;
			}
		</script>
		
</head>
	<body>
		<div id="page_content">
			<jsp:include page="../../../WebSit/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="9" />
			</jsp:include>

			<div id="main_content">
				<div style="width: 100%; height: 820px">
					<div class="main_left">
						<div class="main_left_one">
							<a class="main_left_select" onclick="ChangeDiv(this,'NewsList1')">交通小常识</a>
						</div>
						<div class="main_left_one">
							<a class="main_left_select_no" onclick="ChangeDiv(this,'NewsList2')">交通法律小常识</a>
						</div>
					</div>
					
					<div class="main_right">
						<div id="NewsList1" class="main_right_li">
						
						
							 <div class="com_div">
								<p onclick="ToggleP('1','1')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									骑自行车常识
									<font class="com_time" color="#999">2014-01-07</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','2')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									交通事故报警台能够为您提供的帮助
									<font class="com_time" color="#999">2013-05-07</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','3')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									交通安全小常识
									<font class="com_time" color="#999">2012-05-07</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','3')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									避让转弯车辆
									<font class="com_time" color="#999">2011-04-21</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','4')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									道路不是游戏场	
									<font class="com_time" color="#999">2011-03-29</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','5')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									乘车
									<font class="com_time" color="#999">2011-02-06</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','6')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									安全横过道路
									<font class="com_time" color="#999">2010-12-01</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','7')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									公路的主要技术指标有哪些项目？其中包含的意义是什么？
									<font class="com_time" color="#999">2010-07-12</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','8')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									步行安全常识
									<font class="com_time" color="#999">2010-04-25</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','9')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									小学生安全知识——骑车安全
									<font class="com_time" color="#999">2010-01-01</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','10')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									行走时，哪些情况最危险呢？
									<font class="com_time" color="#999">2009-07-12</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','11')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									驾车上路遇见异物要小心
									<font class="com_time" color="#999">2008-06-12</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','12')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									汽车颜色与交通安全
									<font class="com_time" color="#999">2008-06-12</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','13')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									行车中的错觉
									<font class="com_time" color="#999">2008-06-12</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','14')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									发生交通事故了，怎么办？
									<font class="com_time" color="#999">2008-06-12</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','15')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									用车小常识——巧除新车气味
									<font class="com_time" color="#999">2006-10-09</font>
								<p>
							</div>
							
							<div class="com_div">
								<p onclick="ToggleP('1','16')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									行走时，哪些情况最危险呢？
									<font class="com_time" color="#999">2009-07-12</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','17')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									六大加油小技巧
									<font class="com_time" color="#999">2006-10-09</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('1','18')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									汽车入库“独门秘笈”
									<font class="com_time" color="#999">2006-10-09</font>
								<p>
							</div>
							<!-- <div class="com_div">
								<p onclick="ToggleP('1','19')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									购车指南--九大方法帮您分辩汽车性价比高低
									<font class="com_time" color="#999">2006-10-09</font>
								<p>
							</div> -->
							<div class="com_div">
								<p onclick="ToggleP('1','20')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									如何避让转弯车辆
									<font class="com_time" color="#999">2006-09-28</font>
								<p>
							</div>
						 </div>
				         <div id="NewsList2" class="main_right_li" style="display:none">
							
							<div class="com_div">
								<p onclick="ToggleP('2','3')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									交通安全小常识
									<font class="com_time" color="#999">2011-04-22</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('2','4')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									交通事故赔偿数额计算
									<font class="com_time" color="#999">2010-07-12</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('2','5')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									交通事故责任认定书的制作期限是多少？
									<font class="com_time" color="#999">2008-11-01</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('2','1')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									浙江省高速公路运行管理办法
									<font class="com_time" color="#999">2006-09-20</font>
								<p>
							</div>
							<div class="com_div">
								<p onclick="ToggleP('2','2')" class="com_title">
									<img src="WebSit/image/common/arrow.gif">
									超限运输车辆行驶公路管理规定
									<font class="com_time" color="#999">2006-09-20</font>
								<p>
							</div>
						 </div>
					</div>
				</div>
			</div>
			<jsp:include page="../../../WebSit/page/main/foot.jsp" />

		</div>
	</body>
</html>
