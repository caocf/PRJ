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

		<title>意见箱列表</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/profession/profession_information.css" />
		<link rel="stylesheet" type="text/css" href="css/user_department_show.css" />
		<link rel="stylesheet" type="text/css" href="css/sailing/newdialog.css" />
		<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
		
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		 <script type="text/javascript" src="js/common/CheckLogin.js"></script>
		 <script type="text/javascript" src="js/common/Operation.js"></script>
		 <script type="text/javascript" src="js/AddressList/yijianxiang_list.js"></script>
		 <script type="text/javascript" src="<%=basePath%>js/common/paging.js"></script>
		<style type="text/css">
			.checkedyijian font{
				color: #cccccc;
			}
		</style>
	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div id="content">
			<div id="u2">
				<div class="profession_top">
					<button class="btn-pt"  style="margin-left:10px;width: 50px;height:25px;border-right:0;border-radius: 4px 0 0 4px;">已读</button>
					<button class="btn-pt"  style="margin-left:0px;width: 50px;height:25px;border-radius: 0 4px 4px 0 ;">未读</button>
					<button class="btn-pt"  style="width: 50px;height:25px;">删除</button>
					<button class="btn-pt"  style="width: 100px;height:25px;">全部设为已读</button>
					<img alt="搜索" src="image/operation/search_normal.png" class="profession_searchbutton"
					onclick="SearchBoatmanKind('ShowBoatmanKindListByPage',1)" onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)"/>
					<input type="text" id="content_search"  class="profession_search_input"/>
				</div>
				<table class=listTable cellpadding="0" cellspacing="0">
					<col width="5%" /><col width="5%" /><col width="10%" /><col width="20%" />
					<col width="30%" /><col width="15%" /><col width="15%" />
					<tr>
						<th><input type="checkbox" onclick="checkedOrNo(this)"/></th>
						<th>序号</th>
						<th>城市</th>
						<th>联系电话</th>
						<th>内容</th>
						<th>时间</th>
						<th>操作</th>
					</tr>
					<tr>
						<td><input type="checkbox"  class="systemcheck"/></td>
						<td><span>序号</span></td>
						<td><span>序号</span></td>
						<td><span>序号</span></td>
						<td><span>序号</span></td>
						<td><span>序号</span></td>
						<td>
							<span class="clickword" onclick="window.location.href='<%=basePath%>page/AddressList/yijianxiang_xiangqing.jsp'">查看</span>&nbsp;
							<span class="clickword" >删除</span>
						</td>
					</tr>
					<tr class="checkedyijian">
						<td><input type="checkbox"  class="systemcheck"/></td>
						<td><font>序号</font></td>
						<td><font>序号</font></td>
						<td><font>序号</font></td>
						<td><font>序号</font></td>
						<td><font>序号</font></td>
						<td>
							<span class="clickword" onclick="window.location.href">查看</span>&nbsp;
							<span class="clickword" >删除</span>
						</td>
					</tr>
				</table>
				<div class="User_S4" id="pageDiv">
							<p>
								共<span id="allTotal"></span>条
								<span class="firstBtnSpan"></span>
								<span class="prevBtnSpan"></span>
								<span class="pageNoSpan"></span>页
								<span class="nextBtnSpan"></span>
								<span class="lastBtnSpan"></span>
								<span class="gotoPageSpan"></span>
							</p>
				</div>
			</div>
		</div>
	</body>
</html>
