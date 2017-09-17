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
	
	<link rel="stylesheet" type="text/css" href="WebSit/css/publicTraffic/PassengerSuvey.css"/>
	
	<script type="text/javascript" src="WebSit/js/XMap/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>
	<script type="text/javascript" src="WebSit/js/Passenger/ShuttleBusTime.js"></script>

  </head>
  
  <body><input type="hidden" value="4" id="allnum"/>
 	 <div align="center" class="list">
  		<font color="#000000" size="4" face="黑体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
           	 北站时刻表&nbsp;&nbsp;&nbsp;&nbsp; <span class="hight"><font color="#ff0000" size="2">临时变动请见车站时刻表或车站公告</font>
	    </span>
	    </font> 
	 </div>
          
			       <TABLE width=100% height="0" border=0 cellPadding=0 cellSpacing=0>
			             <TR>
			               <TD width="15%" align="right">北站线路分类： </TD>
			               <TD width="35%" align="left">
			              	 全部线路
			               </TD>
						<td width="50%" align="right" >
							<a onclick="TableChange(1,'first')" class="thisfirst" style="display:none;">首页</a>&nbsp;
							<a onclick="TableChange(1,'before')" class="thisfirst" style="display:none;">上一页</a>&nbsp;
							<a onclick="TableChange(1,'after')" class="thislast" >下一页</a>
							<a onclick="TableChange(4,'last')" class="thislast" >尾页</a><font>&nbsp;页次：</font><font
								color=red class="thispage">1</font><font>/4页</font>
							<font>&nbsp;共78个 20个/页</font>
						</td>
			               </TR>
				</TABLE>
				<table width="98%" border="0"  cellpadding="0" cellspacing="1" bgcolor="#9ec4e5" id="tabel1">
                    <tr align="center" bgcolor="#dff3ff">
                      <td height="20" bgcolor="#dff3ff">终点站</td>
                      <td bgcolor="#dff3ff">分类</td>
                      <td bgcolor="#dff3ff">主要途径站点</td>
                      <td  bgcolor="#dff3ff">终点站票价(全/半)</td>
                      <td width="282" bgcolor="#dff3ff">发车时间</td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">千岛湖 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">客运中心/新登/后浦/分水/百江/潭头/千岛湖</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">95.0/48.0 </td>
                      <td align="center" bgcolor="#FFFFFF">7:10 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">宜宾 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">四川方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">/万州/重庆/内江/自贡/宜宾</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">531.0/266.0 </td>
                      <td align="center" bgcolor="#EEEEEE">7:45 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">象山 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">客运中心站/宁海/象山</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">200.0/100.0 </td>
                      <td align="center" bgcolor="#FFFFFF">9:10 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">周口 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">河南方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">王江泾站/利辛/太和/界首/沈丘/项城/周口</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">200.00/100.00 </td>
                      <td align="center" bgcolor="#EEEEEE">7:00 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">太仓 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">江苏方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">胜浦/甪直/新镇/太仓</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">44.00/22.00 </td>
                      <td align="center" bgcolor="#FFFFFF">7:35 10:05 13:50 16:10 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">屯溪 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">客运中心/于潜/昌化/歙县/屯溪</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">111.0/55.0 </td>
                      <td align="center" bgcolor="#EEEEEE">7:15 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">张家港 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">江苏方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/渭塘/凤凰镇/塘桥/张家港</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">57.0/29.0 </td>
                      <td align="center" bgcolor="#FFFFFF">8:25 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">平望 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">江苏方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">王江泾/平望</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">11.0/6.0 </td>
                      <td align="center" bgcolor="#EEEEEE">07:00 07:25 08:00 08:30 09:00 09:35 10:00 10:30 11:00 11:35 12:00 12:30 13:00 13:30 14:00 14:30 15:00 15:35 16:05 16:30 17:00 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">平江 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">湖南方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">客运中心/通城/平江</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">284.0/142.0 </td>
                      <td align="center" bgcolor="#FFFFFF">9:35 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">邳州 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">江苏方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">新沂/邳州</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">178.0/89.0 </td>
                      <td align="center" bgcolor="#EEEEEE">08:30 16:35 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">磐安 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">客运中心/磐安</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">83.0/41.0 </td>
                      <td align="center" bgcolor="#FFFFFF">08:20 12:45 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">牛头山 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">三天门/李家巷/长兴/小浦/煤山/新槐/牛头山</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">53.0/27.0 </td>
                      <td align="center" bgcolor="#EEEEEE">10:15 11:30 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">宁国 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">泗安/广德/宁国</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">86.0/43.0 </td>
                      <td align="center" bgcolor="#FFFFFF">9:10 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">宁波 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">北站快客</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">客运中心/宁波</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">76.0/38.0 </td>
                      <td align="center" bgcolor="#EEEEEE">07:10 08:10 09:15 10:00 11:00 12:00 13:00 13:55 14:55 16:00 17:00 18:00 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">南阳 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">河南方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/南阳</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">234.0/117.0 </td>
                      <td align="center" bgcolor="#FFFFFF">11:35 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">南浔 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">梅堰/震泽/南浔</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">21.0/11.0 </td>
                      <td align="center" bgcolor="#EEEEEE">10:25 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">南通 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">北站快客</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">南通</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">66.0/33.0 </td>
                      <td align="center" bgcolor="#FFFFFF">07:45 10:45 13:55 16:55 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">南京(快) </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">北站快客</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">南京(快)</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">101.0/51.0 </td>
                      <td align="center" bgcolor="#EEEEEE">07:55 13:50 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">马鞍山 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/溧水/博望/当涂/马鞍山</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">110.0/55.0 </td>
                      <td align="center" bgcolor="#FFFFFF">8:10 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">洛阳 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">河南方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">王江泾站/漯河/舞阳/平顶山/宝丰/汝州/洛阳</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">282.0/141.0 </td>
                      <td align="center" bgcolor="#EEEEEE">11:40 </td>
                      </tr>
                    
                  </table>
                  
                  <!-- 2 -->
                   <table width="100%" border="0"  cellpadding="0" cellspacing="1" bgcolor="#9ec4e5" id="tabel2" style="display:none">
                    <tr align="center" bgcolor="#dff3ff">
                      <td height="20" bgcolor="#dff3ff">终点站</td>
                      <td bgcolor="#dff3ff">分类</td>
                      <td bgcolor="#dff3ff">主要途径站点</td>
                      <td width="150" bgcolor="#dff3ff">终点站票价(全/半)</td>
                      <td width="153" bgcolor="#dff3ff">发车时间</td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">罗山 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">河南方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/罗山</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">190.0/95.0 </td>
                      <td align="center" bgcolor="#FFFFFF">11:55 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">芦墟 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">江苏方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">黎里/黎星/芦墟</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">21.0/11.0 </td>
                      <td align="center" bgcolor="#EEEEEE">08:40 09:30 12:25 13:15 15:55 16:45 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">庐江 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/庐江</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">159.0/80.0 </td>
                      <td align="center" bgcolor="#FFFFFF">7:55 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">龙游 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">客运中心/桐庐/新安江/寿昌/龙游</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">100.0/50.0 </td>
                      <td align="center" bgcolor="#EEEEEE">7:25 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">六安 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/合肥/六安</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">144.0/72.0 </td>
                      <td align="center" bgcolor="#FFFFFF">7:35 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">菱湖 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">炉头/民兴/练市/双林/和浮/菱湖</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">28.0/14.0 </td>
                      <td align="center" bgcolor="#EEEEEE">09:05 14:55 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">灵璧 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/泗县/灵璧</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">183.0/91.0 </td>
                      <td align="center" bgcolor="#FFFFFF">9:25 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">临沂 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">山东方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">王江泾站/沭阳/新沂/郯城 /李庄/临沂</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">200.0/100.0 </td>
                      <td align="center" bgcolor="#EEEEEE">09:15 11:20 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">临海 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">客运中心/天台/临海</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">112.0/56.0 </td>
                      <td align="center" bgcolor="#FFFFFF">08:30 13:05 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">临安 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">客运中心/临安</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">53.0/27.0 </td>
                      <td align="center" bgcolor="#EEEEEE">8:00 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">溧阳 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">江苏方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/丁山/宜兴/徐舍/溧阳</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">74.0/37.0 </td>
                      <td align="center" bgcolor="#FFFFFF">8:35 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">丽水 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">客运中心/永康/缙云/丽水</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">123.0/62.0 </td>
                      <td align="center" bgcolor="#EEEEEE">8:25 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">乐清 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">客运中心/乐清</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">156.0/78.0 </td>
                      <td align="center" bgcolor="#FFFFFF">07:45 16:30 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">昆山 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">北站快客</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">昆山</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">39.0/20.0 </td>
                      <td align="center" bgcolor="#EEEEEE">08:00 09:45 11:50 13:30 15:35 17:20 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">开封 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">河南方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/睢县/杞县/兰考/开封</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">220.0/110.0 </td>
                      <td align="center" bgcolor="#FFFFFF">12:35 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">句容 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">江苏方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">丹阳/白兔/句容</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">87.0/44.0 </td>
                      <td align="center" bgcolor="#EEEEEE">06:55 12:15 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">金寨 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">金寨</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">195.0/97.0 </td>
                      <td align="center" bgcolor="#FFFFFF">8:45 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">金华 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">客运中心/浦江口/金华</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">94.0/47.0 </td>
                      <td align="center" bgcolor="#EEEEEE">07:50 13:15 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">焦作 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">河南方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/开封/郑州/焦作</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">222.0/111.0 </td>
                      <td align="center" bgcolor="#FFFFFF">15:15 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">椒江 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">客运中心/黄岩/路桥/椒江</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">128.0/64.0 </td>
                      <td align="center" bgcolor="#EEEEEE">8:00 </td>
                      </tr>
                    
                  </table>
                  <!-- 3 -->
                   <table width="100%" border="0"  cellpadding="0" cellspacing="1" bgcolor="#9ec4e5" id="tabel3" style="display:none">
                    <tr align="center" bgcolor="#dff3ff">
                      <td height="20" bgcolor="#dff3ff">终点站</td>
                      <td bgcolor="#dff3ff">分类</td>
                      <td bgcolor="#dff3ff">主要途径站点</td>
                      <td width="150" bgcolor="#dff3ff">终点站票价(全/半)</td>
                      <td width="153" bgcolor="#dff3ff">发车时间</td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">江阴 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">江苏方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/青阳/江阴</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">62.0/31.0 </td>
                      <td align="center" bgcolor="#FFFFFF">06:40 13:10 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">江山 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">客运中心/衢州/江山</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">94.0/47.0 </td>
                      <td align="center" bgcolor="#EEEEEE">6:45 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">建湖 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">江苏方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/靖江/如皋/海安/东台/盐城/龙岗/马庄/建湖</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">145.0/72.0 </td>
                      <td align="center" bgcolor="#FFFFFF">07:40 14:10 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">嘉定 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">上海方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">安亭/嘉定</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">42.0/21.0 </td>
                      <td align="center" bgcolor="#EEEEEE">10:05 16:10 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">绩溪 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">0.0/0.0 </td>
                      <td align="center" bgcolor="#FFFFFF">6:40 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">黄石 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">湖北方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">王江泾站/黄石</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">162.0/81.0 </td>
                      <td align="center" bgcolor="#EEEEEE">9:20 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">淮滨 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">河南方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/叶集/固始/淮滨</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">183.0/92.0 </td>
                      <td align="center" bgcolor="#FFFFFF">13:20 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">淮北 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">王江泾站/泗洪/泗县/宿州/符离集/濉溪/淮北</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">236.0/118.0 </td>
                      <td align="center" bgcolor="#EEEEEE">8:15 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">淮安 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">江苏方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">楚州/淮安</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">147.0/73.0 </td>
                      <td align="center" bgcolor="#FFFFFF">6:20 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">沪南汇 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">上海方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">奉贤/沪南汇</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">40.0/20.0 </td>
                      <td align="center" bgcolor="#EEEEEE">06:30 12:50 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">湖州（快） </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">北站快客</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">湖州（快）</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">32.0/16.0 </td>
                      <td align="center" bgcolor="#FFFFFF">07:05 07:45 08:25 08:55 09:40 10:10 10:55 11:25 12:15 12:50 13:35 14:10 14:45 15:35 16:10 16:55 17:30 18:05 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">湖州 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">练市/南浔/东迁/三济桥/晟舍/八里店</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">28.0/14.0 </td>
                      <td align="center" bgcolor="#EEEEEE">06:50 07:55 09:20 10:30 12:05 13:20 14:30 16:35 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">合肥 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/肥东/合肥</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">122.0/61.0 </td>
                      <td align="center" bgcolor="#FFFFFF">8:05 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">杭州北 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">北站快客</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">客运中心/杭州北</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">37.0/19.0 </td>
                      <td align="center" bgcolor="#EEEEEE">06:50 07:55 09:00 10:00 11:10 12:25 13:10 14:15 15:20 16:20 17:30 18:50 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">杭州（快） </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">北站快客</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">客运中心/杭州（快）</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">36.0/18.0 </td>
                      <td align="center" bgcolor="#FFFFFF">06:30 06:45 07:05 07:15 07:35 08:10 08:40 08:50 09:30 10:20 10:40 11:00 11:25 11:35 11:50 12:10 12:50 13:00 13:30 13:50 14:05 14:30 14:55 15:10 15:35 15:45 16:00 16:30 17:00 17:50 18:40 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">海门 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">江苏方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">海门</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">79.0/40.0 </td>
                      <td align="center" bgcolor="#EEEEEE">07:30 12:40 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">贵池 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">南陵/青阳/九华山/贵池</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">132.0/66.0 </td>
                      <td align="center" bgcolor="#FFFFFF">8:55 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">广州 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">广州方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">客运中心/广州</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">392.0/196.0 </td>
                      <td align="center" bgcolor="#EEEEEE">15:05 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">固镇 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/五河/小圩/固镇</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">178.0/89.0 </td>
                      <td align="center" bgcolor="#FFFFFF">9:45 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">固始 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">河南方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">王江泾站/叶集/长兴集/黎集/固始</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">154.0/77.0 </td>
                      <td align="center" bgcolor="#EEEEEE">05:50 14:00 </td>
                      </tr>
                    
                  </table>
                  <!-- 4 -->
                   <table width="100%" border="0"  cellpadding="0" cellspacing="1" bgcolor="#9ec4e5" id="tabel4" style="display:none">
                    <tr align="center" bgcolor="#dff3ff">
                      <td height="20" bgcolor="#dff3ff">终点站</td>
                      <td bgcolor="#dff3ff">分类</td>
                      <td bgcolor="#dff3ff">主要途径站点</td>
                      <td width="150" bgcolor="#dff3ff">终点站票价(全/半)</td>
                      <td width="153" bgcolor="#dff3ff">发车时间</td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">赣榆 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">江苏方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/灌云/连云港/赣榆</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">194.0/97.0 </td>
                      <td align="center" bgcolor="#FFFFFF">9:00 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">富阳 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">客运中心/富阳</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">47.0/23.0 </td>
                      <td align="center" bgcolor="#EEEEEE">08:15 13:20 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">奉贤 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">上海方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">奉贤</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">32.0/16.0 </td>
                      <td align="center" bgcolor="#FFFFFF">07:15 08:25 09:40 11:40 13:55 16:00 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">奉化 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">客运中心/奉化</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">82.0/41.0 </td>
                      <td align="center" bgcolor="#EEEEEE">8:35 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">东阳 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">客运中心/大唐庵/郑家坞/义乌/东阳</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">80.0/40.0 </td>
                      <td align="center" bgcolor="#FFFFFF">07:40 14:00 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">大悟 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">湖北方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">王江泾站/宣化/河口/新城/大悟</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">234.0/117.0 </td>
                      <td align="center" bgcolor="#EEEEEE">9:10 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">大丰 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">江苏方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/如皋/海安/东台/刘庄/大丰</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">122.0/61.0 </td>
                      <td align="center" bgcolor="#FFFFFF">7:25 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">枞阳 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">王江泾站/广德/宣城/铜陵/横埠/枞阳</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">155.0/78.0 </td>
                      <td align="center" bgcolor="#EEEEEE">8:30 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">慈溪 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">客运中心/庵东/周巷/慈溪</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">60.0/30.0 </td>
                      <td align="center" bgcolor="#FFFFFF">07:20 08:40 09:15 10:40 11:10 12:50 14:05 14:35 15:20 16:40 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">崇阳 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">湖北方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">王江泾站/大冶/崇阳</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">277.0/139.0 </td>
                      <td align="center" bgcolor="#EEEEEE">9:05 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">常州 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">北站快客</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">王江泾站/横山桥/常州</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">64.0/32.0 </td>
                      <td align="center" bgcolor="#FFFFFF">08:55 13:25 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">常熟 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">北站快客</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">常熟</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">42.0/21.0 </td>
                      <td align="center" bgcolor="#EEEEEE">07:00 08:20 09:40 10:50 12:20 13:45 15:05 16:20 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">常德 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">湖南方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">客运中心/常德</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">290.0/145.0 </td>
                      <td align="center" bgcolor="#FFFFFF">12:30 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">长兴 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">梅堰/震泽/三天门/李家巷/长兴</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">41.0/21.0 </td>
                      <td align="center" bgcolor="#EEEEEE">08:00 09:00 13:30 15:00 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">昌化 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">客运中心/临安/于潜/昌化</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">56.0/28.0 </td>
                      <td align="center" bgcolor="#FFFFFF">13:15 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">苍南 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">客运中心/乐清/瑞安/平阳/龙港/苍南</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">187.0/93.0 </td>
                      <td align="center" bgcolor="#EEEEEE">10:05 </td>
                      </tr>
                    
                    <tr>
                      <td width="59" height="20" bgcolor="#FFFFFF"><div align="center">北林场 </div></td>
                      <td width="96" bgcolor="#FFFFFF"><div align="center">浙江方向</div></td>
                      <td width="176" align="center" bgcolor="#FFFFFF">震泽/东迁/升山/八里店/和平/晓墅/梅溪/南林场/北林场</td>
                      <td width="79" align="center" bgcolor="#FFFFFF">47.0/24.0 </td>
                      <td align="center" bgcolor="#FFFFFF">12:00 </td>
                      </tr>
                    
                    <tr >
                      <td width="59" height="20" bgcolor="#EEEEEE"><div align="center">安庆 </div></td>
                      <td width="96" bgcolor="#EEEEEE"><div align="center">安徽方向</div></td>
                      <td width="176" align="center" bgcolor="#EEEEEE">南陵/铜陵/官埠/安庆</td>
                      <td width="79" align="center" bgcolor="#EEEEEE">164.0/82.0 </td>
                      <td align="center" bgcolor="#EEEEEE">6:10 </td>
                      </tr>
                    
                  </table>
                  
          <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td align="right"><br>
							<a onclick="TableChange(1,'first')" class="thisfirst" style="display:none;">首页</a>&nbsp;
							<a onclick="TableChange(1,'before')" class="thisfirst" style="display:none;">上一页</a>&nbsp;
							<a onclick="TableChange(1,'after')" class="thislast">下一页</a>
							<a onclick="TableChange(4,'last')" class="thislast">尾页</a><font>&nbsp;页次：</font><font
								color=red class="thispage">1</font><font>/4页</font>
							<font>&nbsp;共78个 20个/页</font>
					</td>
               
              </tr>
            </table>
          
          
          
          

  </body>
</html>
