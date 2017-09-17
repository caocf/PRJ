<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>管理机构</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/manageins/Agency.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/newdialog.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/CRselectBox.css" />
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>	
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/manageins/Agency.js"></script>

  </head>
  
  <body>
    <div class="common_c0" id="allheight">
    	<jsp:include page="../system/mtop.jsp" flush="true" />
		<div class="manage_c1" >
				<div class="left_I1">
					<div class="left_select" id="left_no_select1" onmouseover="showgljgedit()" onmouseout="hidegljgedit()">
						<!-- <label class="left_name" id="gljg_bossname"></label>
						<div class="gljg_edit">
							<div class="gljg_edit_link" onclick="showGljgEditDiv()">编辑</div>
							<div class="gljg_edit_link" onclick="showGljgAddDiv()">新增</div>
							<div class="gljg_edit_link" onclick="showGljgDeleteDiv()">删除</div>
						</div> -->
					</div>
					<ul id="left_select_child1" class="left_select_child"></ul>
				</div>
				<div class="department_I1">
					<div class="department_left_no_select" id="department_left_no_select1">
						<label class="left_name">部门</label>
					</div>
					<div class="dpt_left_select" id="dpt_left_no_select2" onmouseover="showgljgeditDepartment()" onmouseout="hidegljgeditDepartment()">
						
					</div>
					<ul id="department_left_select_child2" class="department_left_select_child">
					</ul>
				</div>
				<div class="manage_c2" id="manage_c2">
					<div class="manage_info">
						<div class="manage_info_top"><label style="margin-left:20px;float:left;">基本信息</label>
						
						<div class="manage_info_middle">
							<div class="manage_info_middle_name"><label style="margin-left:20px;" id="gljg_name"></label></div>
							<div class="manage_info_middle_address"><div id="gljg_address" style="word-wrap:break-word;
								overflow:hidden;margin:0 10px 0 20px"></div></div>
							<div class="manage_info_middle_phone"><label style="margin-left:20px;" id="gljg_phone"></label></div>
							<ul class="manage_info_middle_intro">
								<li style="margin-left:20px;float:left;height:100%;width:42px;" id="gljg_intro"></li>
								<li style="float:left;width:80%;max-height: 200px;overflow-y:auto;overflow-x:hidden;" class="justify" id="">
									<textarea readonly="readonly" id="gljg_intro_text" style="line-height:20px;width:100%;border:0;overflow-y:visible;outline: none;"></textarea>
								</li>
							</ul>
						</div>
						<div class="manage_info_middle" style="height:auto;">
							<div class="manage_info_middle_name" id="manage_info_middle_name"></div>
							<div class="manage_info_middle_address">
							<ul class="manage_info_middle_intro" style="width:100%;float:left;">
								<li style="margin-left:20px;float:left;height:100%;width:42px;" id="dpt_intro"></li>
								<li style="float:left;width:80%;max-height: 160px;overflow-y:auto;overflow-x:hidden;" class="justify">
									<textarea readonly="readonly" id="dpt_desc" style="line-height:20px;width:100%;overflow-y:visible;border:0;outline: none;"></textarea>
								</li>
							</ul>	
							</div>
							<div class="manage_info_middle_phone"><a style="margin-left:20px; color:#006993"  onclick="seeInMap()">点击查看地图所在位置</a></div>
						</div>
					</div>
				</div>
						<!-- <div class="manage_info">
							<div class="manage_info_top"><label style="margin-left:20px;float:left;">基本信息</label>
								<label style="margin-right:20px;float:right;color:#006993;cursor: pointer;" onclick="showEditBasicInfo()">编辑</label></div>
							<div class="manage_info_middle">
								<div class="manage_info_middle_name"><label style="margin-left:20px;" id="gljg_name"></label></div>
								<div class="manage_info_middle_address"><div id="gljg_address" style="word-wrap:break-word;
									overflow:hidden;margin:0 10px 0 20px"></div></div>
								<div class="manage_info_middle_phone"><label style="margin-left:20px;" id="gljg_phone"></label></div>
							</div>
							<div class="manage_info_middle" style="height:auto;">
								<div class="manage_info_middle_name"><label style="margin-left:20px;">下属部门</label></div>
								<div class="manage_info_middle_address"><div id="gljg_department" style="word-wrap:break-word;
									overflow:hidden;margin:0 10px 0 20px"></div></div>
								<div class="manage_info_middle_phone"><a style="margin-left:20px; color:#006993"  onclick="seeInMap()">点击查看地图所在位置</a></div>
							</div>
						</div> -->
	    	</div>
	    <jsp:include page="../main/bottom.jsp" flush="true" />
	   
    </div>
    	
		    
		
  </body>
</html>
