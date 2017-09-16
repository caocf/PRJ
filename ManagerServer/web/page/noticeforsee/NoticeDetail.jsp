<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path1 + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <base href="<%=basePath1%>">

<link rel="stylesheet" type="text/css" href="css/common/style.css" />
	<link rel="stylesheet" type="text/css" href="css/Notice_CSS/noticedetail.css"/>
<script src="js/common/jquery-1.5.2.min.js"></script>
<script src="js/noticeforsee/knowledgeShow.js"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>

<script>
function stop() {
    if(window.event.keyCode == 8) 
    window.event.keyCode = 0;
}
</script>

</head>
<body>
<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
<input type="hidden" value="<%=basePath1%>" id="basePath"/>
<input id="knowledgeID" name="knowledgeID" type="hidden" value="<%=request.getParameter("id") %>" />
  <div id="head">
	  <img id="back" src="<%=basePath1%>image/btn_return_normal.png" style="margin-left: 10px;"
			onclick="javascript:window.history.go(-1);" onmousedown="this.src='<%=basePath1%>image/btn_return_2.png'" onmouseup="this.src='<%=basePath1%>image/btn_return_normal.png'"/>
	  <img id="edit" src="<%=basePath1%>image/btn_edit_normal.png"
		    onclick="Edit(document.getElementById('knowledgeID').value)" onmousedown="this.src='<%=basePath1%>image/btn_edit_2.png'" onmouseup="this.src='<%=basePath1%>image/btn_edit_normal.png'"/>
	  <img id="delete" src="<%=basePath1%>image/btn_delete_normal.png"
		   onclick="Delete(document.getElementById('knowledgeID').value)" onmousedown="this.src='<%=basePath1%>image/btn_delete_2.png'" onmouseup="this.src='<%=basePath1%>image/btn_delete_normal.png'"/>

	  <p style="float: right; margin-right: 10px;font-size: 18px;font-weight: 500;">通知公告>>详情</p>

  </div>

		<p id="title" style="width: 100%;text-align: center;padding-top: 20px;font-size: 20px;font-family: 仿宋"></p>
		<p id="time" style="margin-left:15%;text-align: center;padding-top: 20px;font-size: 18px;font-family: 仿宋"></p>
		<div id="content" style="width: 100%"></div>

	</body>
</html>