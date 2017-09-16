<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'datapost.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css"
			href="css/integratedquery/query_zs.css" />
			<link rel="stylesheet" type="text/css"
			href="css/integratedquery/dialog.css" />
			<link href="js/common/jquery-ui-1.8.16.custom.css"
			rel="stylesheet" type="text/css" />
			

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/common/Operation.js"></script>
		<script type="text/javascript" src="js/common/paging.js"></script>
		<script type="text/javascript" src="js/common/formatConversion.js"></script>
		<script type="text/javascript" src="js/integratedquery/cbjfxx.js"></script>
		<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>
		<script type="text/javascript" src="js/common/jquery-ui-1.8.16.custom.min.js"></script>
		<script type="text/javascript" src="js/integratedquery/SearchShipName.js"></script>

	</head>

	<body>
		<input type="hidden" value="<%=session.getAttribute("username")%>"
			id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div id="content">
			<div id="search_div">
				<input type="text" value="" id="cbname" class="input_box"  onkeyup="ChangeTextValue(this)"/>
				<img src="image/operation/query_normal.png"
					onclick="showBaseInfo('GetAndPostDate',1)"
					onmouseover="QueryOver(this)" onmouseout="QueryOut(this)" />
			</div>
			<table id="WarnInfor" cellpadding="0" cellspacing="0">
				<col width="10%" />
				<col width="8%" />
				<col width="20%" />
				<col width="11%" />
				<col width="11%" />
				<col width="15%" />
				<col width="15%" />
				<col width="10%" />
			</table>
			<table id="Infor" cellpadding="0" cellspacing="0">
				<col width="10%" />
				<col width="8%" />
				<col width="20%" />
				<col width="11%" />
				<col width="11%" />
				<col width="15%" />
				<col width="15%" />
				<col width="10%" />
			</table>
			<div id="pageDiv">
				<p>
					共
					<span id="allTotal"></span>条
					<span class="firstBtnSpan"></span>
					<span class="prevBtnSpan"></span>
					<span class="pageNoSpan"></span>页
					<span class="nextBtnSpan"></span>
					<span class="lastBtnSpan"></span>
					<span class="gotoPageSpan"></span>
				</p>
			</div>

			<div id="dialog_div" style="display: none;">
				<div class="main">
					<a onclick="CloseDialog()" class="closeDialog">关闭</a>
					<table cellpadding="0" cellspacing="0" id="dialog_table">
					<col width="90px"/><col width="160px"/>
					<col width="90px"/><col width="*"/>
						<tr>
							<td>
								序列号：
							</td>
							<td class="XLH" ></td>
							<td>
								开票日期：
							</td>
							<td class="KPRQ"></td>
						</tr>
						<tr>
							<td>
								缴费项目代码：
							</td>
							<td class="JFXMDM"></td>
							<td>
								开票单位名称：
							</td>
							<td class="KPDWMC"></td>
						</tr>
						<tr>
							<td>
								缴费项目名称：
							</td>
							<td class="JFXMMC"></td>
							<td>
								收费方式代码：
							</td>
							<td class="SFFSDM"></td>

						</tr>
						<tr>
							<td>
								收费方式名称：
							</td>
							<td class="SFFSMC"></td>
							<td>
								开票序列号：
							</td>
							<td class="KPXLH"></td>

						</tr>
						<tr>
							<td>
								有效期起：
							</td>
							<td class="YXQQ"></td>
							<td>
								有效期止：
							</td>
							<td class="YXQZ"></td>
						</tr>
						<tr>
							<td>
								应缴金额：
							</td>
							<td class="YJZE"></td>
							<td>
								实缴金额：
							</td>
							<td class="SJZE"></td>


						</tr>
						<tr>
							<td>
								退费标志：
							</td>
							<td class="TFBZ"></td>
							<td>
								作废标志：
							</td>
							<td class="ZFBZ"></td>
						</tr>
						<tr>
							<td>
								消号标志：
							</td>
							<td class="XHBZ"></td>
							<td>
								开票人姓名：
							</td>
							<td class="KPRXM"></td>
						</tr>
						<tr>
							<td>
								开票地市代码：
							</td>
							<td class="KPDSDM"></td>
							<td>
								开票地市名称：
							</td>
							<td class="KPDSMC"></td>
						</tr>
					</table>
				</div>
			</div>

		</div>
		<div id="autocomplete_div" style="z-index: 1; top: 290px; left: 30px;position: absolute;width: 330px; "></div>
	</body>
</html>
