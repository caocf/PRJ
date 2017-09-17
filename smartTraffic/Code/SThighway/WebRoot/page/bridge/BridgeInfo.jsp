<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>桥梁信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/system/RoleManage.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/newdialog.css">
	<link rel="stylesheet" type="text/css" href="css/system/system_left.css" >
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bridge/BridgeInfo.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>
	<script type="text/javascript" src="js/common/search.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="js/common/roadbasicinfo.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/bridge/BridgeInfo.js"></script>

  </head>
  
  <body>
    <div class="common_c0">
    	<jsp:include page="../system/mtop.jsp" flush="true" />
    			<div class="left_I1" id="left_I1">
					<div class="left_no_select" id="left_no_select1" onclick="showThePartBridge(0,'嘉兴市',this)" style="cursor: pointer;">
						<label class="left_name" id="gljg_bossname">嘉兴市</label>
					</div>
					<ul id="left_select_child1" class="left_select_child"></ul>
				</div>
				<input type="hidden" value="<%=request.getParameter("num") %>" id="num"/>
	    	<div class="common_c1" id="common_c1">
		    	<div class="common_c2" id="sss">
		    			<div class="right_title">
							<span class="wordstyle" id="area_name">桥梁信息</span>
							<!-- <div class="c3_button1" onclick="goToStatistic(1)" style="float:right;margin-right:15px;" onmouseover="buttonOver()" onmouseout="buttonOut()">
								<div class="c3_button1_left"></div>
								<div class="c3_button1_center">统计分析</div>
								<div class="c3_button1_right"></div>
							</div> -->
						</div>
						<div class="common_c3" style="border-bottom:solid 1px #dcdcdc">
							<div class='search_div'>
							<div class="image_div" onmousemove="imageOver()" onmouseout="imageOut()" onclick="searchBridgeInfo()">
								<img src="image/main/search_normal.png" style="margin-top:9px;" id="search_image">
								</div><input value='桥梁名称' id='bridge_info' class="search_input" 
							onfocus="TextFocus(this)" onblur="TextBlur(this)" /></div>
							<!-- <button class="superSearch" onclick="showSearchDiv(4)">高级搜索</button> -->
						</div>
						<table class="listTable" id="roleTable" cellpadding="0" cellspacing="0">
						<col width="15%"/><col width="15%"/><col width="15%"/><col width="10%"/>
						<col width="15%"/><col width="10%"/><col width="10%"/><col width="10%"/>
							<tr>
								<!-- <th>桥梁代码</th> -->
								<th>桥梁名称</th>
								<th>路线简称</th>
								<th>桥梁中心桩号</th>
								<th>桥梁全长（米）</th>
								<th>管养单位名称</th>
								<th>监管单位名称</th>
								<th>修建年度</th>
								<th>操作</th>
							</tr>
							
						</table> 
						<div class="User_S4" id="pageDiv" style="background:#f6f6f6;display:none;">
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
	    <jsp:include page="../main/bottom.jsp" flush="true" />
    </div>
    	<!-- 查看桥梁详情 -->
   				 <div class="dialog" id="bridgeDetailDiv" style="display:none;width:900px;margin:-300px 0 0 -450px;">
		    			<div class="User_Top1">
		    				<label class="Top_left">桥梁信息详情</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closebridgeDetailDiv()">
		    			</div>
		    			<div class="pop_up_middle" style="height:550px;overflow-y:auto;overflow-x:hidden; ">
				    			<div style="margin:20px;">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="184px"/><col width="221px"/><col width="198px"/><col width="235px"/>
				    					<tr>
											<td class="td_left1">桥梁名称&nbsp;&nbsp;&nbsp;</td>
											<td  class="td_right">&nbsp;&nbsp;&nbsp;<label id="qlmc"></label></td>
											<td class="td_left2">桥梁全长&nbsp;&nbsp;&nbsp;</td>
											<td  class="td_right">&nbsp;&nbsp;&nbsp;<label id="qlqc"></label></td>
										</tr>
										<tr>
											<td class="td_left1">所在路段&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="lxjc"></label></td>
											<td class="td_left2">路线代码&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="lxdm"></label></td>
										</tr>
										<tr>
											<td class="td_left1">桥梁中心桩号&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qlzxzh"></label></td>
											<td class="td_left2">路径总长&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ljzc"></label></td>
										</tr>
										<tr>
											<td class="td_left1">桥面净宽&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qmjk"></label></td>
											<td class="td_left2">设计荷载等级&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sjhzdj"></label></td>
										</tr>
										<tr>
											<td class="td_left1">跨越地物名称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="kydwmc"></label></td>
											<td class="td_left2">通航等级&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="thdj"></label></td>
										</tr>
										<tr>
											<td class="td_left1">墩台防撞设施类型&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="dtfzsslx"></label></td>
											<td class="td_left2">是否互通立交&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sfhtlj"></label></td>
										</tr>
										<tr>
											<td class="td_left1">建设单位名称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="jsdwmc"></label></td>
											<td class="td_left2">设计单位名称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sjdwmc"></label></td>
										</tr>
										<tr>
											<td class="td_left1">施工单位名称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sgdwmc"></label></td>
											<td class="td_left2">监理单位名称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="jldwmc"></label></td>
										</tr>
										<tr>
											<td class="td_left1">修建年度&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xjnd"></label></td>
											<td class="td_left2">监管单位名称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="jgdwmc"></label></td>
										</tr>
										<tr>
											<td class="td_left1">管养单位名称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gydwmc"></label></td>
											<td class="td_left2">管养单位所属行业类别&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gydwsshylb"></label></td>
										</tr>
										<tr>
											<td class="td_left1">评定等级&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="pddj"></label></td>
											<td class="td_left2">评定日期&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="pdrq"></label></td>
										</tr>
										<tr>
											<td class="td_left1">管理单位名称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right" colspan="3">&nbsp;&nbsp;&nbsp;<label id="gldwmc"></label></td>
										</tr>
										<tr class="aTr">
											<td style="border: 0;">&nbsp;&nbsp;&nbsp;</td>
											<td style="border: 0;">&nbsp;</td>
											<td style="border: 0;">&nbsp;</td>
											<td style="border: 0;text-align: right;" ><a class="Operate" onclick="seeAllInfo()" style="border-right: 0;font-weight: bold;">查看更多</a></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">工程性质&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gcxz"></label></td>
											<td class="td_left2">改造年度&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gznd"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">行政区划代码：&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xzqhdm"></label></td>
											<td class="td_left2">行政区划：&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xzqh"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥梁经度：&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qljd"></label></td>
											<td class="td_left2">桥梁纬度&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qlwd"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥梁性质&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qlxz"></label></td>
											<td class="td_left2">收费性质&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sfxz"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥梁所在地名&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qlszdm"></label></td>
											<td class="td_left2">跨越地物类型&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="kydwlx"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥梁用途分类&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qlytfl"></label></td>
											<td class="td_left2">按使用年限分&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="synx"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥梁跨径分类&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qlkjfl"></label></td>
											<td class="td_left2">技术状况评定&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="jszkpd"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">最近定期检查日期&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zjdqjcrq"></label></td>
											<td class="td_left2">已采取交通管制措施&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ycqjtgzcs"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">立交桥类别&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ljqlb"></label></td>
											<td class="td_left2">是否公铁立交&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sfgtlj"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">立交桥跨越各路线&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ljqkyglx"></label></td>
											<td class="td_left2">立交桥连通各路线&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ljqltglx"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">主要病害位置&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zybhwz"></label></td>
											<td class="td_left2">主要病害描述&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zybhms"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">多跨径总长（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="dkjzc"></label></td>
											<td class="td_left2">单孔最大跨径（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="dkzdkj"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥梁跨径组合（孔*米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qlkjzh"></label></td>
											<td class="td_left2">桥梁全宽（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qlqk"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥面净宽（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qmjk"></label></td>
											<td class="td_left2">桥梁孔数（孔）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qlks"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">主桥孔数（孔）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zqks"></label></td>
											<td class="td_left2">主桥主跨（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zqzk"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">主桥边跨（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zqbk"></label></td>
											<td class="td_left2">前引桥长（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qyqc"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">后引桥长（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="hyqc"></label></td>
											<td class="td_left2">行车道宽（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xcdk"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">人行道宽（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="rxdk"></label></td>
											<td class="td_left2">桥梁高度（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qlgd"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥面标高（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qmbg"></label></td>
											<td class="td_left2">桥下净空（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qxjk"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥下净高（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qxjg"></label></td>
											<td class="td_left2">匝道（平米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zdpm"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">匝道（公里）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zdgl"></label></td>
											<td class="td_left2">上部结构类型&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sbjgxs"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">上部结构材料&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sbjgcl"></label></td>
											<td class="td_left2">下部结构形式&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xbjgxs"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">下部材料类型&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xbcllx"></label></td>
											<td class="td_left2">桥墩类型&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qdlx"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥台类型&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qtlx"></label></td>
											<td class="td_left2">基础结构型式&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="jcjgxs"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">引桥结构型式&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="yqjgxs"></label></td>
											<td class="td_left2">支座类型&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zzlx"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">伸缩缝类型&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ssflx"></label></td>
											<td class="td_left2">拱桥矢跨比&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gqskb"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥面纵坡&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qmzp"></label></td>
											<td class="td_left2">桥面线型&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qmxx"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥面全宽&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qmqk"></label></td>
											<td class="td_left2">弯坡斜特征&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="wpxtz"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥位地形&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qwdx"></label></td>
											<td class="td_left2">桥面铺装类型&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qmpzlx"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">主桥截面型式&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zqjmxs"></label></td>
											<td class="td_left2">引桥截面型式&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="yqjmxs"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">桥梁验算荷载&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qlyshz"></label></td>
											<td class="td_left2">抗震等级&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="kzdj"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">河床地质及纵坡&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="hcdzjzp"></label></td>
											<td class="td_left2">河床最低标高（米）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="hczdbg"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">设计洪水频率（年）&nbsp;&nbsp;&nbsp;</td>
											<td  class="td_right">&nbsp;&nbsp;&nbsp;<label id="sjhspl"></label></td>
											<td class="td_left2">总造价（万元）&nbsp;&nbsp;&nbsp;</td>
											<td  class="td_right">&nbsp;&nbsp;&nbsp;<label id="zzj"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">设计洪水位&nbsp;&nbsp;&nbsp;</td>
											<td class="td_right">&nbsp;&nbsp;&nbsp;<label id="sjhsw" ></label></td>
											<td class="td_left2">常年水位&nbsp;&nbsp;&nbsp;</td>
											<td  class="td_right">&nbsp;&nbsp;&nbsp;<label id="cnsw"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">历史最高洪水位&nbsp;&nbsp;&nbsp;</td>
											<td  class="td_right">&nbsp;&nbsp;&nbsp;<label id="lszghsw"></label></td>
											<td class="td_left2">通车日期&nbsp;&nbsp;&nbsp;</td>
											<td  class="td_right">&nbsp;&nbsp;&nbsp;<label id="tcrq"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">开工日期&nbsp;&nbsp;&nbsp;</td>
											<td class="td_right">&nbsp;&nbsp;&nbsp;<label id="kgrq" ></label></td>
											<td class="td_left2">竣工日期&nbsp;&nbsp;&nbsp;</td>
											<td class="td_right">&nbsp;&nbsp;&nbsp;<label id="jgrq" ></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">改建变更日期&nbsp;&nbsp;&nbsp;</td>
											<td class="td_right">&nbsp;&nbsp;&nbsp;<label id="gjbgrq" ></label></td>
											<td class="td_left2">改造完工日期&nbsp;&nbsp;&nbsp;</td>
											<td class="td_right">&nbsp;&nbsp;&nbsp;<label  id="gzwgrq"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">改建变更原因&nbsp;&nbsp;&nbsp;</td>
											<td class="td_right">&nbsp;&nbsp;&nbsp;<label id="gjbgyy" ></label></td>
											<td class="td_left2">变更原因说明&nbsp;&nbsp;&nbsp;</td>
											<td class="td_right">&nbsp;&nbsp;&nbsp;<label id="bgyysm" ></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">备注&nbsp;&nbsp;&nbsp;</td>
											<td colspan="3" class="td_right">&nbsp;&nbsp;&nbsp;<label id="bz"></label></td>
										</tr>
				    				</table>
				    			</div>	    			
		    			</div> 
		    		</div>
  </body>
</html>
