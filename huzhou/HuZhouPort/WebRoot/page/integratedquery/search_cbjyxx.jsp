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
		<link rel="stylesheet" type="text/css" href="css/integratedquery/query.css" />
		<link rel="stylesheet" type="text/css" href="css/integratedquery/dialog.css" />
		<link href="js/common/jquery-ui-1.8.16.custom.css"
			rel="stylesheet" type="text/css" />
			
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		 <script type="text/javascript" src="js/common/Operation.js"></script>
		  <script type="text/javascript" src="js/common/paging.js"></script>
		 <script type="text/javascript" src="js/common/formatConversion.js"></script>
		<script type="text/javascript" src="js/integratedquery/cbjyxx.js"></script>
		<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>
		<script type="text/javascript" src="js/common/jquery-ui-1.8.16.custom.min.js"></script>
		<script type="text/javascript" src="js/integratedquery/SearchShipName.js"></script>

	</head>

	<body><input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<input type="hidden" value="<%=basePath%>" id="basePath" />
	<div id="content">
		<div id="search_div">
		<input type="text" value="" id="cbname" class="input_box"  onkeyup="ChangeTextValue(this)"/>
		<img src="image/operation/query_normal.png" onclick="showBaseInfo('GetAndPostDate',1)" onmouseover="QueryOver(this)" onmouseout="QueryOut(this)"/>
		</div>
		<table id="Infor" cellpadding="0" cellspacing="0">
		<col width="10%"/><col width="10%"/><col width="15%"/><col width="15%"/>
		<col width="20%"/><col width="13%"/><col width="10%"/><col width="7%"/>
		</table>
		<div id="dialog_div" style="display:none;">
            <div class="main">
                <a onclick="CloseDialog()" class="closeDialog">关闭</a>
					<table cellpadding="0" cellspacing="0" id="dialog_table">
					<col width="90px"/><col width="140px"/>
					<col width="90px"/><col width="*"/>
						<tr>
							<td>
								检验编号：
							</td>
							<td class="JYBH"></td>

							<td>
								中文船名：
							</td>
							<td class="ZWCM"></td>
						</tr>
						<tr>
							<td>
								船检登记号：
							</td>
							<td class="CJDJH"></td>
							<td>
								申请人：
							</td>
							<td class="SQR"></td>						
						</tr>
						<tr>
							<td>
								检验地点：
							</td>
							<td class="JYDD"></td>
							<td>
								检验单位名称：
							</td>
							<td class="JYDWMC"></td>						
						</tr>
						<tr>
							<td>
								检验种类：
							</td>
							<td class="JYZL"></td>

							<td>
								检验开始日期：
							</td>
							<td class="JYKSRQ"></td>
						</tr>
						<tr>
							
							<td>
								检验部门：
							</td>
							<td class="JYBM"></td>

							<td>
								检验完成日期：
							</td>
							<td class="JYWCRQ"></td>
						</tr>
						<tr>
							<td>
								其他检验：
							</td>
							<td class="QTJY"></td>

							<td>
								是否完成：
							</td>
							<td class="SFWC"></td>
						</tr>
						<tr>
							<td>
								下次检验日期：
							</td>
							<td class="XCJYRQ"></td>
							<td>
								备注：
							</td>
							<td class="BZ"></td>
						</tr>
					</table>
				</div>
        </div>
		</div>
		<div id="autocomplete_div" style="z-index: 1; top: 290px; left: 30px;position: absolute;width: 330px; "></div>
	</body>
</html>
