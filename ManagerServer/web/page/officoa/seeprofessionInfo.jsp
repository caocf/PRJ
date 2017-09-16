<%@page import="java.util.*"  pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath1%>">
    
    <title>知识库</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
<link rel="stylesheet" type="text/css" href="css/common/style.css" />
<link rel="stylesheet" type="text/css" href="css/leave/see.css"/>

<script src="js/common/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="js/common/Operation.js"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
<script type="text/javascript" src="js/common/formatConversion.js"></script>
<script src="js/officoa/seeprofessionInfo.js"></script>
  </head>
  
  <body>
  <input type="hidden" value="<%=basePath1%>" id="basePath"/>
   <input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
   <input id="InfoId" type="hidden" value="<%=request.getParameter("InfoId") %>" /> 
   <div id="layer1">
				<img src="<%=basePath1%>image/operation/back_small_normal.png"
						onclick="javascript:window.history.go(-1);"
						onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
						title="返回" />
			</div> 
    
    <table width="100%" border="0" cellspacing="0" class="seenew"
	   id="knowledgeupdate">
	<col width="100px" /><col />
	<tr>	
	<td> 
            资讯名称: 
	</td>
	<td>
	<input id="professionInfoName" readonly="readonly" style="width:200px;height:21px;line-height: 21px;vertical-align: middle;padding-left:5px;" type="text"/>
    </td>
	
	</tr>
	<tr>	
	<td> 
           创建时间: 
	</td>
	<td>
	<label id="createtime"></label>
    </td>
	
	</tr>
	<tr>	
	<td>
            通知对象:
	</td>
	<td id="faceToObject">
   				
    </td>
	</tr>
	
	<tr>	
	<td> 
            资讯内容: 
	</td>
	<td>
	<textarea   name="professionInfoContent" id="professionInfoContent" cols="60" rows="20" readonly="readonly" style="resize:none;text-indent: 2em;"></textarea> 
    </td>
	</tr>
	
	
	
    </table>

  </body>
</html>
