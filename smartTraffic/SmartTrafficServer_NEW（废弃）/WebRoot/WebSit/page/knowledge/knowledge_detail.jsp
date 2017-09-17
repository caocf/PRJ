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
    
    <title>智慧出行-交通法律小常识详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

	<link rel="stylesheet" href="<%=basePath%>WebSit/css/common/main.css" type="text/css"/>
	<link rel="stylesheet" href="<%=basePath%>WebSit/css/trafficNews/trafficNews.css" type="text/css"/>
	
	<script type="text/javascript" src="WebSit/js/XMap/jquery-1.10.2.min.js"/></script>
    <script type="text/javascript" src="WebSit/js/common/Operation.js"/></script>
	<script type="text/javascript" src="WebSit/js/common/formatConversion.js"></script>
	<script type="text/javascript" >
	$$(document).ready(function() {
	if($$("#id").val()!="null")
	{
		SearchDetailKnowledge();
	}
	
});
function SearchDetailKnowledge() {
	$$.ajax( {
		url : 'SearchDetailKnowledge',
		type : "post",
		dataType : "json",
		data : {
			"knowledgeDetail.id" : $$("#id").val(),
			"knowledgeDetail.type" : $$("#kind").val()
		},
		success : function(data) {
			$$("#title").text(data.knowledgeDetail.title);
			$$("#time").text("时间："+DateTimeFormat(data.knowledgeDetail.date)+
					" 来源："+DateIsNull(data.knowledgeDetail.source,"未知")+" 点击数："+ DateIsNull(data.knowledgeDetail.clickNum,"未知")
					+" 作者："+ DateIsNull(data.knowledgeDetail.author,"未知"));
			
			if(data.knowledgeDetail.imageUrl!=null){
				$$("#imageSrc").html("<img src='"+data.knowledgeDetail.imageUrl+"'/>");
			}
			$$("#news_content").html(data.knowledgeDetail.content);
		}
	});

}
	</script>
</head>

	<body>
		<input type="hidden" value="<%=request.getParameter("id")%>" id="id" />
		<input type="hidden" value="<%=request.getParameter("kind")%>" id="kind" />
		<div id="page_content">
			<jsp:include page="../../../WebSit/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="9" />
			</jsp:include>

			<!--界面内容-->
			<div id="main_content">
				<div class="top">
					<a href="WebSit/page/knowledge/knowledge.jsp"><span
						style="font-size: 14px; color: #999; text-decoration: none;margin-bottom: 10px;display: block;width: 70px;">返回&raquo;</span>
					</a>
				</div>
				<div class="bottom">
					<h1 align="center" style="margin-top: 30px;margin-bottom: 10px" id="title"></h1>
					<p align="center" id="time">
					</p>
					<p align="center">
						<img src="WebSit/image/trafficNews/trafficNews_icon_top.png" />
					</p>
					<div
						style="font-size: 14px; font-weight: normal; letter-spacing: 2px; color: rgb(51, 51, 51);line-height: 18px;text-indent: 2em;margin-left: 20px;margin-right: 20px;"
						id="news_content">
					</div>
					<p align="center" id="imageSrc"></p>

				</div>
			</div>
			
			<jsp:include page="../../../WebSit/page/main/foot.jsp" />
		</div>

	</body>
</html>
