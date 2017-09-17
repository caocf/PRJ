<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>主页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/login/main.css">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/login/main.js"></script>	
	<script type="text/javascript" src="<%=basePath%>js/common/cookie.js"></script>	
  </head>
  
<body>
<div class="mainall">
<div class="mainstyle">
<input type="hidden" value="<%=basePath%>" id="basePath"/>
 <input type="hidden" value="<%=session.getAttribute("token")%>" id="roadtoken"/>
 <div class="logintop">
  <div class="p1">
  <%-- <div style="float:left">
  <img  src="<%=basePath%>image/login/logo1.png" /> </div> --%>
  <div style="float:left;margin-top:9px;">
  <img src="<%=basePath%>image/login/logo2.png"/></div>
  <div style="height:65px;inline-height:65px;margin-left:16px;float:left;" > 
  <label>嘉兴公路综合数据交换平台</label></div>
  
  <div id="userdiv" style="float:right;height:65px;cursor:pointer;">
  <div  id="userdiv_1" style="float:left; margin-top:10px;margin-right:5px;font-family:'微软雅黑';font-weight:normal;" >
  <div id="username" style="font-size:18px;"></div>
  <div id="depart" style="font-size:14px;margin-top:3px;"></div>
  </div>
  <img id="userimage" style="width:19px;height:19px;padding-top:23px;" src="image/login/main_arrow_down.png"/></div>
  </div> 
</div>

    <div class="userdiv" id="userset"  >
 
    <div class="userset"  id="setPerson">
      <div style="float:left;margin-top:12px;margin-left:18px;display: inline-block;">
       <img  src="<%=basePath%>image/login/selfset.png"></div>
       <div style="margin-top:8px;margin-left:18px;float:left;display: inline-block;">
            个人设置</div>
    </div>
    
     <div class="userset"  id="setSystem" onclick="gotosystem()">
       <div style="float:left;margin-top:12px;margin-left:18px;display: inline-block;">
       <img  src="<%=basePath%>image/login/systemset.png"></div>
       <div style="margin-top:8px;margin-left:18px;float:left;display: inline-block;">
            系统设置</div>
         </div>
            
      <div class="logout" id="logout">
            <div style="float:left;margin-top:12px;margin-left:18px;display: inline-block;">
       <img  src="<%=basePath%>image/login/logout.png"></div>
       <div style="margin-top:8px;margin-left:18px;float:left;display: inline-block;">
            退出</div>
      </div>
  </div>
		<div class="maindiv">
		 <div class="main_left">
            <!-- 路段信息 -->
			<div class="routediv" id="route" onclick="goToLDXX()">
			<img id="module_route" src="image/login/module_route.png"/>
			 </div>
			<div style="margin-top:15px;">
			  <div class="tzxxdiv" onclick="goToTZXX()">
			   <img id="module_tzxx" src="image/login/module_tzxx.png"/>
			  </div>
			  <div class="vediodiv"  onclick="goToSPJK()">
			  <img id="module_video" src="image/login/module_video.png"/>
			  </div>
			</div> 
			<div class="statisticsdiv"  onclick="goToTJFX()">
				<img id="module_statistics" src="image/login/module_statistics.png"/>
			</div>	
		</div>
		<div class="main_middle">
			<!-- <div style="cursor:pointer;">
	           <img  src="image/login/map.png"/></div>
	           <div style="cursor:pointer;"> 
	           <img  id="module_map" src="image/login/module_map.png"/></div> -->
	           <div class="mapdiv" onclick="goToGLDT()">
	           <img id="module_map"  src="image/login/module_map.png"/>
	           </div>
	           <div class="lawdiv" onclick="goToXZJF()">
	           <img id="module_law"  src="image/login/module_law.png"/>
	           </div>
		</div>
		<div class="main_right">
			<div class="managerdiv"  onclick="goToContacts()">
				<img id="module_manager" src="image/login/module_manager.png"  />
			</div>
	    	<div class="datadiv"  onclick="goToSJTB()">
				<img id="module_data" src="image/login/module_data.png"  />
		   </div>
		   <div class="transportdiv"  onclick="goToJTL()">
				<img id="module_transport" src="image/login/module_transport.png"  />
		   </div>
		   </div>
		   </div>
		  

      
		   </div>

 	   <div class="footer" id="footer">
		 <div class="versiondiv">
                  <label>浙江省嘉兴市公路管理局  版权所有 &nbsp;&nbsp;&nbsp;版本号<span id="version"></span></label> 
          </div>
          <div class="whitediv"></div>
        <div class="yellowdiv"></div>
        </div>
        </div>
  </body>
</html>
