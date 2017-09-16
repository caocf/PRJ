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
<script src="js/officoa/knowledge_update.js"></script>
  </head>
  
  <body>
  <input type="hidden" value="<%=basePath1%>" id="basePath"/>
   <input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
   <input id="kindID" name="kindID" type="hidden" value="<%=request.getParameter("kindID") %>" />
   <input id="kindID1" name="kindID1" type="hidden" value="<%=request.getParameter("kindID1") %>" />
   <input id="knowledgeID" name="knowledgeID" type="hidden" value="<%=request.getParameter("knowledgeID") %>" />
   
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
            通知名称: 
	</td>
	<td>
	<input id="knowledgeName" name="knowledgeName" type="text"/>
	<font style="color:red;">*</font>
    </td>
	
	</tr>
	
	<tr>	
	<td> 
            通知摘要: 
	</td>
	<td>
	<textarea   name="knowledgeIndex" id="knowledgeIndex" cols="50" rows="3"  ></textarea> 
    </td>
	</tr>
	
	<tr>	
	<td>
            是否是连接:
	</td>
	<td>
     <input type="radio" value="1" name="isLink"  id="isLink1" />是    <input type="radio" value="2" id="isLink2" name="isLink"  />否 
    </td>
	</tr>
	
	
	<tr>	
	<td> 
            具体内容: 
	</td>
	<td>
	<textarea   name="knowledgeContent" id="knowledgeContent" cols="50" rows="20"  ></textarea> 
	<font style="position: relative;top: -130px;color:red;">*</font>
    </td>
	</tr>
	
	<tr>	
	<td>
	&nbsp;
	</td>
	<td>
	<img  src="image/operation/sure_normal.png" onClick="knowledgeUpdate()"
	onmouseover="SureOver(this)" onMouseOut="SureOut(this)"/>
    </td>
	</tr>
	
    </table>

  </body>
</html>
