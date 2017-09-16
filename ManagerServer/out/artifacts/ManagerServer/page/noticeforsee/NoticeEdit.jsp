<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path1 + "/";
%>
<%
	request.setCharacterEncoding("UTF-8");
	String htmlData = request.getParameter("content1") != null ? request.getParameter("content1") : "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<base href="<%=basePath1%>">

		<link rel="stylesheet" href="kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="kindeditor/plugins/code/prettify.js"></script>

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/Notice_CSS/noticedetail.css"/>
		<link rel="stylesheet" type="text/css" href="css/leave/leave.css" />
		<script src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/common/Operation.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>
		<script src="js/noticeforsee/knowledgelist.js"></script>
		<script type="text/javascript" src="js/common/paging.js"></script>



	</head>
	<body>
		<input type="hidden" value="<%=basePath1%>" id="basePath" />
		<a id="newsid" style="display: none" >${news.obj.id}</a>

		<div id="head">
			<img id="back" src="<%=basePath1%>image/btn_return_normal.png" style="margin-left: 10px;"
				 onclick="javascript:window.history.go(-1);"
				 onmousedown="this.src='<%=basePath1%>image/btn_return_2.png'"
				 onmouseup="this.src='<%=basePath1%>image/btn_return_normal.png'"/>

			<p style="float: right; margin-right: 10px;font-size: 18px;font-weight: 500;">通知公告>>编辑</p>

		</div>

		<div style="height:80px;float: left;width: 95%;margin-top: 20px;margin-left: 15px;">
			<span style="float: left;line-height: 34px;font-size:14px ;color:#333;">标题 ：</span>
            <textarea id='newstitle' style="width:80%;
                          height:60px;
                          float: left;
                          border-radius:4px;
                          border: solid 1px #ccc;
                          line-height: 55px;resize: none">${news.obj.title}</textarea>
			</div>

		<div style="float: left;width: 95%;margin-top: 20px;margin-left:15px;">
			<span style="float: left;line-height: 34px;font-size:14px ;color:#333;">内容 ：</span>
			<form name="example" method="post" action="" style="float: left; width: 80%;" >

				<textarea name="content1" cols="100" rows="8" style="width:100%;height:200px;visibility:visible;" id='editor_id'>${news.obj.content}</textarea>
			</form>

		</div>
		<div style="float: left;width: 100%;height: 34px;margin-top: 10px;">
			<img id="save" src="<%=basePath1%>image/btn_save_normal.png" style="margin-left: 10px;"
				 onclick="updateN()"
				 onmousedown="this.src='<%=basePath1%>image/btn_save_2.png'"
				 onmouseup="this.src='<%=basePath1%>image/btn_save_normal.png'"/>
			<img id="cancel" src="<%=basePath1%>image/btn_cancel__normal.png" style="margin-left: 10px;"
				 onclick="javascript:window.history.go(-1);"
				 onmousedown="this.src='<%=basePath1%>image/btn_cancel_2.png'"
				 onmouseup="this.src='<%=basePath1%>image/btn_save_normal.png'"/>
		</div>
	</body>
</html>
<%!
	private String htmlspecialchars(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}
%>