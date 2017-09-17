<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-欢迎进入行业基本信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/publicTraffic/PassengerSuvey.css"/>

  </head>
  
  <body>
		<div align="center" class="list">
			<font color="#000000" size="4" face="黑体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				客运中心时刻表&nbsp;&nbsp;&nbsp;&nbsp; <span class="hight"><font
					color="#ff0000" size="2">临时变动请见车站时刻表或车站公告</font>
			</span>
			</font>
		</div>

		<TABLE width=100% height="0" border=0 align="center" cellPadding=0 cellSpacing=0>

			<TR>
				<TD width="15%" align="right">
					客过中心线路分类：
				</TD>
				<TD width="35%" align="left">
					<select name="select1" id=select1 class="editbox22"
						onChange="form.submit();">
						<option value="0">
							全部线路
						</option>
						<option value=1 selected="selected">
							客运中心快客
						</option>
						<option value=2>
							客运中心普客
						</option>
						<option value=3>
							客运中心城乡公交
						</option>
					</select>
				</TD>
			
				<td width="50%" align="right" >
					<font color='#000080'>&nbsp;页次：</font><font
						color=red>1</font><font color='#000080'>/1页</font>
					<font color='#000080'>&nbsp;共14个 20个/页</font>
				</TABLE>
					<!-- 1 -->
           			 <table width="98%" border="0"  cellpadding="0" cellspacing="1" bgcolor="#9ec4e5">
                      <tr align="center" bgcolor="#dff3ff">
                        <td height="20" bgcolor="#dff3ff">终点站</td>
                        <td bgcolor="#dff3ff">分类</td>
                        <td bgcolor="#dff3ff">主要途径站点</td>
                        <td bgcolor="#dff3ff">终点站票价(全/半)</td>
                        <td width="282" bgcolor="#dff3ff">发车时间</td>
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
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">南京(快) </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心快客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/南京(快)</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">103.0/52.0 </td>
                        <td align="center" bgcolor="#FFFFFF">07:25 13:20 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">宁波 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心快客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">宁波</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">74.0/37.0 </td>
                        <td align="center" bgcolor="#EEEEEE">07:40 08:40 09:45 10:30 11:25 12:30 13:30 14:25 15:25 16:30 17:30 18:30 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">上海北 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心快客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">上海北</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">36.0/18.0 </td>
                        <td align="center" bgcolor="#FFFFFF">10:40 12:00 16:20 17:35 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">上海南 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心快客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">上海南</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">35.0/18.0 </td>
                        <td align="center" bgcolor="#EEEEEE">07:55 09:25 10:15 11:30 12:40 13:50 14:55 15:55 17:00 18:40 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">绍兴 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心快客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">柯桥/绍兴</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">57.0/29.0 </td>
                        <td align="center" bgcolor="#FFFFFF">07:55 10:25 13:15 15:45 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">苏州火车站 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心快客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">苏州火车站</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">30.0/15.0 </td>
                        <td align="center" bgcolor="#EEEEEE">06:50 07:50 08:50 09:50 10:50 11:50 12:50 13:50 14:55 15:50 16:50 17:50 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">桐乡（直达 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心快客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">桐乡（直达</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">8.5/4.0 </td>
                        <td align="center" bgcolor="#FFFFFF">07:35 08:00 08:25 08:45 09:15 09:45 10:05 10:30 10:55 11:20 11:45 12:10 12:35 13:00 13:30 13:55 14:15 14:40 15:05 15:30 15:55 16:20 16:40 17:10 </td>
                      </tr>
                      
                      <tr >
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">温州(快) </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心快客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">温州(快)</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">180.0/90.0 </td>
                        <td align="center" bgcolor="#EEEEEE">08:35 15:30 </td>
                      </tr>
                      
                      <tr>
                        <td width="76" height="20" bgcolor="#FFFFFF"><div align="center">无锡 </div></td>
                        <td width="65" align="center" bgcolor="#FFFFFF">客运中心快客</td>
                        <td width="174" align="center" bgcolor="#FFFFFF">嘉兴北站/无锡</td>
                        <td width="74" align="center" bgcolor="#FFFFFF">49.0/24.0 </td>
                        <td align="center" bgcolor="#FFFFFF">07:00 07:40 08:10 08:55 09:40 11:00 12:40 13:15 14:00 14:50 15:25 16:45 </td>
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
                        <td width="76" height="20" bgcolor="#EEEEEE"><div align="center">余姚 </div></td>
                        <td width="65" align="center" bgcolor="#EEEEEE">客运中心快客 </td>
                        <td width="174" align="center" bgcolor="#EEEEEE">余姚</td>
                        <td width="74" align="center" bgcolor="#EEEEEE">62.0/31.0 </td>
                        <td align="center" bgcolor="#EEEEEE">08:10 10:30 13:10 15:50 </td>
                      </tr>
                      
                    </table>
                  
                    
            <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td align="right"><br>
                  <font color='#000080'>&nbsp;页次：</font><font color=red>1</font><font color='#000080'>/1页</font> <font color='#000080'>&nbsp;共14个 20个/页</font> 
                </td>
              </tr>
          </table>
  </body>
</html>
