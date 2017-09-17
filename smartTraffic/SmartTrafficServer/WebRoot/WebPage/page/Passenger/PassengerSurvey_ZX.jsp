<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-长途客运</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/publicTraffic/PassengerSuvey.css"/>
	
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
	<script type="text/javascript" src="WebPage/js/Passenger/ShuttleBusTime.js"></script>

  </head>
  
  <body><input type="hidden" value="6" id="allnum"/>
		<div align="center" class="list">
			<font color="#000000" size="4" face="黑体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				客运中心时刻表&nbsp;&nbsp;&nbsp;&nbsp; <span class="hight"><font
					color="#ff0000" size="2">临时变动请见车站时刻表或车站公告</font>
			</span>
			</font>
		</div>

		<TABLE width=100% height="0" border=0 align="center" cellPadding=0
			cellSpacing=0>

			<TR>
				<TD width="15%" align="right">
					客过中心线路分类：
				</TD>
				<TD width="35%" align="left">
					全部线路
				</TD>
			
				<td width="50%" align="right" >
					<a onclick="TableChange(1,'first')" class="thisfirst" style="display:none;">首页</a>&nbsp;
					<a onclick="TableChange(1,'before')" class="thisfirst" style="display:none;">上一页</a>&nbsp;
					<a onclick="TableChange(1,'after')" class="thislast" >下一页</a>
					<a onclick="TableChange(6,'last')" class="thislast" >尾页</a><font>&nbsp;页次：</font><font
						color=red class="thispage">1</font><font>/6页</font>
					<font>&nbsp;共102个 20个/页</font>
				</td>
			</TR>
		</TABLE>
					<!-- 1 -->
           			 <table width="98%" border="0"  cellpadding="0" cellspacing="1" bgcolor="#9ec4e5" id="tabel1">
                      <tr align="center" bgcolor="#dff3ff">
                        <td height="20" bgcolor="#dff3ff">终点站</td>
                        <td bgcolor="#dff3ff">分类</td>
                        <td bgcolor="#dff3ff">主要途径站点</td>
                        <td bgcolor="#dff3ff">终点站票价(全/半)</td>
                        <td width="282" bgcolor="#dff3ff">发车时间</td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">苍南 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">乐清/瑞安/平阳/龙港/苍南</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">187.0/93.0 </td>
                        <td align="center" bgcolor="#FFFFFF">10:35 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">昌化 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">临安/于潜/昌化</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">56.0/28.0 </td>
                        <td align="center" bgcolor="#EEEEEE">13:45 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">常德 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">常德</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">290.0/145.0 </td>
                        <td align="center" bgcolor="#FFFFFF">13:00 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">常山 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">衢州/常山</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">92.0/46.0 </td>
                        <td align="center" bgcolor="#EEEEEE">16:00 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">常州 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/横山桥/常州</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">66.0/33.0 </td>
                        <td align="center" bgcolor="#FFFFFF">08:30 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">慈溪 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">庵东/周巷/慈溪</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">58.0/29.0 </td>
                        <td align="center" bgcolor="#EEEEEE">07:50 09:05 09:40 11:05 11:40 13:20 14:30 15:05 15:45 17:10 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">枞阳 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/广德/宣城/铜陵/横埠/枞阳</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">155.0/78.0 </td>
                        <td align="center" bgcolor="#FFFFFF">7:55 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">大丰 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/如皋/海安/东台/刘庄/大丰</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">122.0/61.0 </td>
                        <td align="center" bgcolor="#EEEEEE">6:55 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">东阳 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">大唐庵/郑家坞/义乌/东阳</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">80.0/40.0 </td>
                        <td align="center" bgcolor="#FFFFFF">08:10 14:25 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">奉化 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">宁波/奉化</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">82.0/41.0 </td>
                        <td align="center" bgcolor="#EEEEEE">09:05 12:55 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">富阳 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">富阳</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">47.0/23.0 </td>
                        <td align="center" bgcolor="#FFFFFF">08:45 13:50 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">赣榆 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/灌云/连云港/赣榆</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">194.0/97.0 </td>
                        <td align="center" bgcolor="#EEEEEE">8:30 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">固始 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/叶集/长兴集/黎集/固始</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">154.0/77.0 </td>
                        <td align="center" bgcolor="#FFFFFF">13:25 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">广州 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">广州</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">392.0/196.0 </td>
                        <td align="center" bgcolor="#EEEEEE">15:35 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">贵池 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/南陵/青阳/九华山/贵池</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">132.0/66.0 </td>
                        <td align="center" bgcolor="#FFFFFF">8:25 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">海门 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/海门</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">79.0/40.0 </td>
                        <td align="center" bgcolor="#EEEEEE">07:00 12:10 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">杭州（快） </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心快客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">杭州（快）</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">34.0/17.0 </td>
                        <td align="center" bgcolor="#FFFFFF">07:00 07:15 07:35 07:45 08:05 08:40 09:10 09:20 10:00 10:50 11:10 11:30 11:50 12:05 12:20 12:40 13:20 13:30 14:00 14:20 14:35 15:00 15:20 15:35 16:00 16:15 16:30 17:00 17:30 18:20 19:10 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">杭州北 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心快客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">杭州北</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">35.0/18.0 </td>
                        <td align="center" bgcolor="#EEEEEE">07:20 08:25 09:30 10:30 11:40 12:55 13:40 14:45 15:45 16:50 18:00 19:20 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">合肥 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/肥东/合肥</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">122.0/61.0 </td>
                        <td align="center" bgcolor="#FFFFFF">7:35 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">淮北 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/泗洪/泗县/宿州/符离集/濉溪/淮北</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">236.0/118.0 </td>
                        <td align="center" bgcolor="#EEEEEE">7:50 </td>
                      </tr>
                      
                    </table>
                    <!-- 2 -->
                    <table width="98%" border="0"  cellpadding="0" cellspacing="1" bgcolor="#9ec4e5" id="tabel2" style="display:none">
                      <tr align="center" bgcolor="#dff3ff">
                        <td height="20" bgcolor="#dff3ff">终点站</td>
                        <td bgcolor="#dff3ff">分类</td>
                        <td bgcolor="#dff3ff">主要途径站点</td>
                        <td bgcolor="#dff3ff">终点站票价(全/半)</td>
                        <td width="282" bgcolor="#dff3ff">发车时间</td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">淮滨 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/叶集/固始/淮滨</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">183.0/92.0 </td>
                        <td align="center" bgcolor="#FFFFFF">12:50 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">黄石 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/黄石</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">162.0/81.0 </td>
                        <td align="center" bgcolor="#EEEEEE">8:50 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">建湖 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/靖江/如皋/海安/东台/盐城/龙岗/马庄/建湖</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">145.0/72.0 </td>
                        <td align="center" bgcolor="#FFFFFF">07:10 13:40 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">江山 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">衢州/江山</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">94.0/47.0 </td>
                        <td align="center" bgcolor="#EEEEEE">7:15 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">椒江 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">黄岩/路桥/椒江</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">128.0/64.0 </td>
                        <td align="center" bgcolor="#FFFFFF">8:30 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">焦作 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/开封/郑州/焦作</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">222.0/111.0 </td>
                        <td align="center" bgcolor="#EEEEEE">14:45 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">金华 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">浦江口/金华</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">94.0/47.0 </td>
                        <td align="center" bgcolor="#FFFFFF">08:20 13:40 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">开封 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/睢县/杞县/兰考/开封</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">220.0/110.0 </td>
                        <td align="center" bgcolor="#EEEEEE">12:05 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">乐清 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">乐清</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">156.0/78.0 </td>
                        <td align="center" bgcolor="#FFFFFF">08:15 16:55 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">丽水 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">永康/缙云/丽水</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">123.0/62.0 </td>
                        <td align="center" bgcolor="#EEEEEE">8:50 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">临安 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">临安</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">53.0/27.0 </td>
                        <td align="center" bgcolor="#FFFFFF">8:30 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">临海 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">天台/临海</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">110.0/55.0 </td>
                        <td align="center" bgcolor="#EEEEEE">09:05 13:35 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">临平 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">灵安/新农村/小桥村/崇福/上市/科同/孙桥/临平</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">19.0/9.0 </td>
                        <td align="center" bgcolor="#FFFFFF">07:00 07:35 08:00 08:35 09:00 09:30 10:00 10:30 11:00 11:30 12:00 12:45 13:25 14:00 14:30 15:00 15:30 15:50 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">临沂 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/沭阳/新沂/郯城 /李庄/临沂</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">200.0/100.0 </td>
                        <td align="center" bgcolor="#EEEEEE">08:45 10:50 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">灵璧 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/泗县/灵璧</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">183.0/91.0 </td>
                        <td align="center" bgcolor="#FFFFFF">8:55 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">六安 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/合肥/六安</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">144.0/72.0 </td>
                        <td align="center" bgcolor="#EEEEEE">7:05 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">龙游 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">桐庐/新安江/寿昌/龙游</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">100.0/50.0 </td>
                        <td align="center" bgcolor="#FFFFFF">7:55 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">路桥 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">黄岩/路桥</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">106.0/53.0 </td>
                        <td align="center" bgcolor="#EEEEEE">10:40 15:25 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">罗山 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/罗山</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">190.0/95.0 </td>
                        <td align="center" bgcolor="#FFFFFF">11:25 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">洛阳 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/漯河/舞阳/平顶山/宝丰/汝州/洛阳</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">282.0/141.0 </td>
                        <td align="center" bgcolor="#EEEEEE">11:15 </td>
                      </tr>
                      
                    </table>
           			<!-- 3 -->
           			 <table width="98%" border="0"  cellpadding="0" cellspacing="1" bgcolor="#9ec4e5" id="tabel3" style="display:none">
                      <tr align="center" bgcolor="#dff3ff">
                        <td height="20" bgcolor="#dff3ff">终点站</td>
                        <td bgcolor="#dff3ff">分类</td>
                        <td bgcolor="#dff3ff">主要途径站点</td>
                        <td bgcolor="#dff3ff">终点站票价(全/半)</td>
                        <td width="282" bgcolor="#dff3ff">发车时间</td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">马鞍山 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/溧水/博望/当涂/马鞍山</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">110.0/55.0 </td>
                        <td align="center" bgcolor="#FFFFFF">7:45 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">南京(快) </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心快客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/南京(快)</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">103.0/52.0 </td>
                        <td align="center" bgcolor="#EEEEEE">07:25 13:20 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">南阳 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/南阳</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">234.0/117.0 </td>
                        <td align="center" bgcolor="#FFFFFF">11:05 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">宁波 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心快客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">宁波</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">74.0/37.0 </td>
                        <td align="center" bgcolor="#EEEEEE">07:40 08:40 09:45 10:30 11:25 12:30 13:30 14:25 15:25 16:30 17:30 18:30 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">磐安 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">磐安</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">83.0/41.0 </td>
                        <td align="center" bgcolor="#FFFFFF">08:55 13:10 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">平江 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">通城/平江</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">284.0/142.0 </td>
                        <td align="center" bgcolor="#EEEEEE">10:10 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">莆田 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">福鼎/宁德/罗源/福州/莆田</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">303.0/151.0 </td>
                        <td align="center" bgcolor="#FFFFFF">15:20 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">浦东机场 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">浦东机场</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">70.0/35.0 </td>
                        <td align="center" bgcolor="#EEEEEE">07:15 08:20 09:30 11:00 12:50 14:40 17:00 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">启东 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">启东</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">89.0/44.0 </td>
                        <td align="center" bgcolor="#FFFFFF">9:40 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">千岛湖 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">新登/后浦/分水/百江/潭头/千岛湖</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">95.0/48.0 </td>
                        <td align="center" bgcolor="#EEEEEE">7:40 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">青岛 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/日照/胶南/青岛</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">243.0/122.0 </td>
                        <td align="center" bgcolor="#FFFFFF">15:15 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">青田 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">义乌/永康/缙云/丽水/青田</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">150.0/75.0 </td>
                        <td align="center" bgcolor="#EEEEEE">9:50 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">瑞安 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">黄岩/乐清/瑞安</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">187.0/94.0 </td>
                        <td align="center" bgcolor="#FFFFFF">12:20 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">三门 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">宁海/三门</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">105.0/53.0 </td>
                        <td align="center" bgcolor="#EEEEEE">09:15 13:35 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">商丘 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/宿州/永城/夏邑/商丘</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">196.0/98.0 </td>
                        <td align="center" bgcolor="#FFFFFF">8:25 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">上海北 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心快客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">上海北</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">36.0/18.0 </td>
                        <td align="center" bgcolor="#EEEEEE">10:40 12:00 16:20 17:35 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">上海南 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心快客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">上海南</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">35.0/18.0 </td>
                        <td align="center" bgcolor="#FFFFFF">07:55 09:25 10:15 11:30 12:40 13:50 14:55 15:55 17:00 18:40 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">上虞 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">上虞</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">65.0/32.0 </td>
                        <td align="center" bgcolor="#EEEEEE">08:40 14:10 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">绍兴 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心快客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">柯桥/绍兴</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">57.0/29.0 </td>
                        <td align="center" bgcolor="#FFFFFF">07:55 10:25 13:15 15:45 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">嵊州 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">上虞/嵊州</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">70.0/35.0 </td>
                        <td align="center" bgcolor="#EEEEEE">08:05 13:30 15:10 </td>
                      </tr>
                      
                    </table>
           			<!-- 4 -->
           			<table width="98%" border="0"  cellpadding="0" cellspacing="1" bgcolor="#9ec4e5" id="tabel4" style="display:none">
                      <tr align="center" bgcolor="#dff3ff">
                        <td height="20" bgcolor="#dff3ff">终点站</td>
                        <td bgcolor="#dff3ff">分类</td>
                        <td bgcolor="#dff3ff">主要途径站点</td>
                        <td bgcolor="#dff3ff">终点站票价(全/半)</td>
                        <td width="282" bgcolor="#dff3ff">发车时间</td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">沭阳 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/宝应/淮安/沭阳</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">142.0/71.0 </td>
                        <td align="center" bgcolor="#FFFFFF">7:55 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">双林 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">炉头/民兴/练市/双林</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">16.0/8.0 </td>
                        <td align="center" bgcolor="#EEEEEE">12:20 16:10 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">苏州火车站 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心快客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">苏州火车站</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">30.0/15.0 </td>
                        <td align="center" bgcolor="#FFFFFF">06:50 07:50 08:50 09:50 10:50 11:50 12:50 13:50 14:55 15:50 16:50 17:50 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">宿迁 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/淮安/泗阳/宿迁</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">164.0/82.0 </td>
                        <td align="center" bgcolor="#EEEEEE">08:20 11:35 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">宿州 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/泗洪/泗县/灵璧/宿州</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">160.0/80.0 </td>
                        <td align="center" bgcolor="#FFFFFF">17:00 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">泰州 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/江阴/黄桥/姜堰/泰州</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">81.0/40.0 </td>
                        <td align="center" bgcolor="#EEEEEE">7:40 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">桐庐 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">桐庐</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">64.0/32.0 </td>
                        <td align="center" bgcolor="#FFFFFF">13:55 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">桐乡（直达 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心快客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">桐乡（直达</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">8.5/4.0 </td>
                        <td align="center" bgcolor="#EEEEEE">07:35 08:00 08:25 08:45 09:15 09:45 10:05 10:30 10:55 11:20 11:45 12:10 12:35 13:00 13:30 13:55 14:15 14:40 15:05 15:30 15:55 16:20 16:40 17:10 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">铜陵 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/广德/十字铺/宣城/铜陵</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">122.0/61.0 </td>
                        <td align="center" bgcolor="#FFFFFF">7:15 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">屯溪 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">于潜/昌化/歙县/屯溪</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">111.0/55.0 </td>
                        <td align="center" bgcolor="#EEEEEE">7:45 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">温岭 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">温岭</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">135.0/67.0 </td>
                        <td align="center" bgcolor="#FFFFFF">08:05 14:30 17:20 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">温州(快) </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心快客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">温州(快)</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">180.0/90.0 </td>
                        <td align="center" bgcolor="#EEEEEE">08:35 15:30 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">乌镇 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">西双桥/炉头/和尚浜/乌镇</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">9.0/4.5 </td>
                        <td align="center" bgcolor="#FFFFFF">07:00 07:20 08:00 08:30 09:20 10:05 10:45 11:20 12:25 13:05 13:40 14:20 15:05 15:40 16:20 17:00 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">无为 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">汤沟/无为</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">143.0/72.0 </td>
                        <td align="center" bgcolor="#EEEEEE">15:05 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">无锡 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心快客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/无锡</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">49.0/24.0 </td>
                        <td align="center" bgcolor="#FFFFFF">07:00 07:40 08:10 08:55 09:40 11:00 12:40 13:15 14:00 14:50 15:25 16:45 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">芜湖 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/广德/十字铺/宣城/湾址/芜湖</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">106.0/53.0 </td>
                        <td align="center" bgcolor="#EEEEEE">6:40 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">武汉 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/潜山/太湖/黄梅/武穴/黄石/武汉</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">208.0/104.0 </td>
                        <td align="center" bgcolor="#FFFFFF">9:30 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">武康 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">德清乾元/武康</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">31.0/16.0 </td>
                        <td align="center" bgcolor="#EEEEEE">15:35 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">西安 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/郑州/洛阳/三门峡/渭南/西安</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">368.0/184.0 </td>
                        <td align="center" bgcolor="#FFFFFF">12:15 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">西平 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/新蔡/平玉/汝南/驻马店/上蔡/西平</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">243.0/122.0 </td>
                        <td align="center" bgcolor="#EEEEEE">9:35 </td>
                      </tr>
                      
                    </table>
           			<!-- 5 -->
           			 <table width="98%" border="0"  cellpadding="0" cellspacing="1" bgcolor="#9ec4e5" id="tabel5" style="display:none">
                      <tr align="center" bgcolor="#dff3ff">
                        <td height="20" bgcolor="#dff3ff">终点站</td>
                        <td bgcolor="#dff3ff">分类</td>
                        <td bgcolor="#dff3ff">主要途径站点</td>
                        <td bgcolor="#dff3ff">终点站票价(全/半)</td>
                        <td width="282" bgcolor="#dff3ff">发车时间</td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">象山 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">横溪/横码/象山</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">100.0/50.0 </td>
                        <td align="center" bgcolor="#FFFFFF">09:40 14:15 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">萧山(快) </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心快客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">萧山(快)</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">42.0/21.0 </td>
                        <td align="center" bgcolor="#EEEEEE">08:20 09:35 11:05 12:15 13:55 15:05 16:20 17:40 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">萧山机场 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心快客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">萧山机场</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">49.0/24.0 </td>
                        <td align="center" bgcolor="#FFFFFF">08:00 09:15 12:10 14:40 17:05 18:55 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">萧县 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/睢宁/双沟/徐州/萧县</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">153.0/77.0 </td>
                        <td align="center" bgcolor="#EEEEEE">7:20 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">新安江 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">桐庐/大畈/新安江</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">84.0/42.0 </td>
                        <td align="center" bgcolor="#FFFFFF">12:55 14:45 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">新昌 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">新昌</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">80.0/40.0 </td>
                        <td align="center" bgcolor="#EEEEEE">13:45 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">新乡 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/徐州/商丘/郑州/新乡</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">262.0/131.0 </td>
                        <td align="center" bgcolor="#FFFFFF">16:25 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">信阳 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/叶集/潢川/罗山/信阳</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">201.0/101.0 </td>
                        <td align="center" bgcolor="#EEEEEE">12:10 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">兴化 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/姜堰/戴南/兴化</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">117.0/59.0 </td>
                        <td align="center" bgcolor="#FFFFFF">11:50 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">邢台 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">济南/聊城/邯郸/邢台</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">261.0/131.0 </td>
                        <td align="center" bgcolor="#EEEEEE">11:40 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">徐州 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/泗阳/宿迁/睢宁/徐州</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">218.0/109.0 </td>
                        <td align="center" bgcolor="#FFFFFF">6:45 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">盐城 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/大冈/盐城</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">132.0/66.0 </td>
                        <td align="center" bgcolor="#EEEEEE">06:20 12:30 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">扬州 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/靖江/泰兴/刁铺/江都/扬州</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">101.0/51.0 </td>
                        <td align="center" bgcolor="#FFFFFF">7:30 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">宜宾 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">重庆/内江/自贡/宜宾</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">531.0/266.0 </td>
                        <td align="center" bgcolor="#EEEEEE">8:15 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">义乌 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">义乌</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">70.0/35.0 </td>
                        <td align="center" bgcolor="#FFFFFF">07:45 13:05 15:40 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">永嘉 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">永嘉</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">150.0/75.0 </td>
                        <td align="center" bgcolor="#EEEEEE">9:20 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">余姚 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心快客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">余姚</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">62.0/31.0 </td>
                        <td align="center" bgcolor="#FFFFFF">08:10 10:30 13:10 15:50 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">张家港 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/渭塘/凤凰镇/塘桥/张家港</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">57.0/29.0 </td>
                        <td align="center" bgcolor="#EEEEEE">7:50 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">柘城 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/王江泾站/涡阳/亳州/鹿邑/柘城</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">180.0/90.0 </td>
                        <td align="center" bgcolor="#FFFFFF">10:00 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">周口 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">嘉兴北站/王江泾站/利辛/太和/界首/沈丘/项城/周口</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">200.0/100.0 </td>
                        <td align="center" bgcolor="#EEEEEE">6:30 </td>
                      </tr>
                      
                    </table>
           			<!-- 6 -->
           			 <table width="98%" border="0"  cellpadding="0" cellspacing="1" bgcolor="#9ec4e5" id="tabel6" style="display:none">
                      <tr align="center" bgcolor="#dff3ff">
                        <td height="20" bgcolor="#dff3ff">终点站</td>
                        <td bgcolor="#dff3ff">分类</td>
                        <td bgcolor="#dff3ff">主要途径站点</td>
                        <td bgcolor="#dff3ff">终点站票价(全/半)</td>
                        <td width="282" bgcolor="#dff3ff">发车时间</td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">诸暨 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心普客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">临浦/大唐庵/诸暨</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">60.0/30.0 </td>
                        <td align="center" bgcolor="#FFFFFF">13:35 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">北京 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心普客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">济南/德州/沧州/天津/北京</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">302.0/151.0 </td>
                        <td align="center" bgcolor="#EEEEEE">18:10 </td>
                      </tr>
                      
                    </table>
            <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td align="right"><br>
					<a onclick="TableChange(1,'first')" class="thisfirst" style="display:none;">首页</a>&nbsp;
					<a onclick="TableChange(1,'before')" class="thisfirst" style="display:none;">上一页</a>&nbsp;
					<a onclick="TableChange(1,'after')" class="thislast">下一页</a>
					<a onclick="TableChange(6,'last')" class="thislast">尾页</a><font>&nbsp;页次：</font><font
						color=red class="thispage">1</font><font>/6页</font>
					<font>&nbsp;共102个 20个/页</font>
                </td>
              </tr>
          </table>
  </body>
</html>
