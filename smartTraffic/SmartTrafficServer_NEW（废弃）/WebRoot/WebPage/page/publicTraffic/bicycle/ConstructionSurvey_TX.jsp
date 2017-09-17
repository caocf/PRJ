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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <h4 style="color: rgb(92,92,92);">基本情况</h4>
   <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
   
     桐乡公共自行车服务有限公司组建与注册于2013年4月19日，负责公共自行车系统的建设、运营和管理。公司设立“两室两科”：经理室、办公室、营运管理科、信息技术科。  
   </p>
   <br>
   <h4 style="color: rgb(92,92,92);">办卡地点</h4>
   <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
    桐乡市公共自行车服务有限公司景雅路766号
   
   </p>
   <br>
    <h4 style="color: rgb(92,92,92);">办卡时间</h4>
    <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
   ：8:00 - 18:00，节假日照常；<br/>
    
     </p>
     <br>
    <h4 style="color: rgb(92,92,92);">收费情况</h4>
    <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
      单次使用1小时内免费（借车次数不限），超过1小时不满2小时收费1元，超过2小时不满3小时收费2元，超过3小时的按每小时3元计费（不满1小时按1小时计算）。租车服务费用实行分段合计，还车刷卡时，从租车卡中结算扣取。
     原则上随借随还，最长租借期为48小时。

    
    </p>
    <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(244,108,8);">温馨提示：请大家记住租还车时间，以避免自行车在家过夜产生大量费用</p>
    </body>
</html>
