<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
  </head>
  
  <body>
   <h4 style="color: rgb(92,92,92);">基本情况</h4>
   <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
       嘉兴市公共自行车服务有限公司成立于2011年9月，是嘉兴市交通投资集团有限责任公司所属国有全资子公司，主要承担嘉兴市本级公共自行车系统投资建设、营运管理任务。
   </p>
   <br>
   <h4 style="color: rgb(92,92,92);">办卡地点</h4>
   <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
    1、嘉兴市体育中心2号通道（戴梦得超市旁）<br/>
    2、中国移动少年路营业厅内(2楼)，位于少年路128号<br/>
    3、中国移动城北营业厅内，位于禾兴北路405号  <br/>   
    4、城西售卡点，新洲路60号（亚厦风和苑大门南侧，神獒安防门店内）<br/>
    5、嘉兴市禾兴南路334号，市民卡服务中心11号服务窗口<br/>
   
   </p>
   <br>
    <h4 style="color: rgb(92,92,92);">办卡时间</h4>
    <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
    1、体育中心办卡点：8:00 - 18:00，节假日照常；<br/>
    2、少年路：9:00-11:30，13:00-17:00，节假日照常；<br/>
    3、城北营业厅：8:30-11:30，13:30-17:00，周二休息；<br/>
    4、城西售卡点：8:30-11:30，13:30-17:00，周二休息；<br/>
    5、市民卡服务中心售卡点：8:30-12:00，14:30-17:30，每周日休息。<br/>
    </p>
     <br>
    <h4 style="color: rgb(92,92,92);">收费情况</h4>
    <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
    1小时内免费，超过1小时后，每小时收费1元，不足1小时的按1小时计算。如：您租车时间为1小时12分，收费1元。
    
    </p>
    <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(244,108,8);">温馨提示：请大家记住租还车时间，以避免自行车在家过夜产生大量费用</p>
    </body>
</html>
