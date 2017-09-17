 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-意见建议</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/suggest/style.css" />
	 <link rel="stylesheet" type="text/css" href="WebPage/css/common/CRselectBox.css" />
	 
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"/></script>
    <script type="text/javascript" src="WebPage/js/common/Operation.js"/></script>
	<script type="text/javascript" src="WebPage/js/suggest/Complain.js"></script>
    <script type="text/javascript" src="WebPage/js/common/inputValidator.js"></script>
	

  </head>
  <body>
   <jsp:include page="../../../WebPage/page/main/top.jsp" flush="true" >
		<jsp:param name="menu_number" value="9,1"/> 
		</jsp:include>
  
  
   <div id="content2" >
      <div class="left">
          <a class="left1">投诉受理</a>
          <a href="WebPage/page/suggest/advice.jsp" class="left2">意见建议受理</a>
      </div>
      <div class="right">
          <div class="right_top">投诉受理</div>
				<div class="right_xtop">
				<div style="width: 250px; float: left;"  >
						标题：
						<input type="text" id="titletext" class="abc" style="line-height: 28px;outline: none; width: 190px;height: 28px;border: 1px solid #999;padding: 0 5px;" />
				</div>
				
					<div style="width: 200px; float: left;margin-left: 20px"  >
						类型：
						<div class="CRselectBox" id="regionCode" style="margin-top: 3px;" >
							<input type="hidden" id="inputtext" class="abc" />
							<input type="hidden"  class="abc_CRtext" />
							<a class="CRselectValue"></a>
							<ul class="CRselectBoxOptions"></ul>
				</div>
				</div>&nbsp;<span id="titletexterr"></span>
				</div>
				<div class="right_cont">请详细描述您遇到的问题（500个字以内），目前已输入<span id="textareatexterr" style="COLOR:red;FONT-WEIGHT:bold">0</span>个字&nbsp;<span id="textareacont"></span>
              <textarea id="textareatext"  onkeydown="checktextarea('textareatext','textareatexterr',500);" onKeyUp="checktextarea('textareatext','textareatexterr',500);" 
                 class="right_cont_kuang" style="float:left"></textarea>
          </div>
          <div class="right_foot">
           <!--  <a href=""><img style="float:left;border:none;" src="WebPage/image/suggest/add_pic.png"/></a><p style=" color:#a3a3a3;float:left;margin:15px auto auto 5px;">如果需要图片来辅助说明，可以上传图片</p> --> 
             <div class="clear"></div>
             <div class="right_foot_x" style="width: 700px"><p style="float:left;color: black;margin-top: 2px">您的手机：</p>
     
                  <input type="text" style="float:left;border:1px solid #999;width: 220px;height:31px; margin-top:1px;outline:medium;line-height: 31px;padding: 0 5px;" id="phonetext" onChange="return checkphone()"/>
                  <p style="float:left;margin-top: inherit">&nbsp;&nbsp;请填写您的手机号码，方便我们及时告知您处理结果</p>&nbsp;<span id="phonetexterr"></span></div>                  
                  <div class="clear"></div>
             <div><a class="left1"  style="width: 80px;height:30px; margin:5px auto auto 60px;line-height: 30px;text-align:center;padding-left: 0px;" onClick="SaveComplain()"/>提交</a></div>
          </div>
      </div>  
   </div>
   
   <jsp:include page="../../../WebPage/page/main/foot.jsp" />
   
  </body>
</html>
