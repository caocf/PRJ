<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path1 + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <base href="<%=basePath1%>">
<link rel="stylesheet" type="text/css" href="css/common/style.css" />
<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
<script src="js/common/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="js/systemTM/style.js"></script>
<script src="js/common/dru.js"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
<script>
function page1(){
var a=document.getElementById('page').value;
if(!isNaN(a)){
  if(a>${student.totalPage}||a<1){
  alert("请输入0到不大于"+$("#LogtotalPage").val()+"的数字")
  }else{
  
     if(parseInt(a)!=a)
   {
  alert("请输入整数");
   }else{
  
  var c="<%=basePath1%>"; 
str=c+"${student.action}.action?currentPage="+a+"&styleName=${styleName}&logUser=${logUser}";
 //alert(str);
  window.location.href=str;
  }}
}else{
alert("请输入数字")
}


}


function selectbutton(){
//alert("sdaj");
var a=document.getElementById('select1').value;
var b=document.getElementById('select2').value;
var c="<%=basePath1%>";       
if (a==""){
  if(b==""){
  alert ("请输入搜索关键字");
  }else{
str=c+"pageMedel2.action?logUser="+b+"&logContent="+b;
  //alert(str);
  window.location.href=str;
  }
  
}else{
   if(b==""){
 //str=c+"findByStyleName.action?pro.styleName="+a+"&pro.Stylename=12345&pro.StyleName=2345&pro.stylename=345";
  str=c+"pageMedel1.action?styleName="+a;
  
   //alert(str);
   window.location.href=str;
   
   }else{
  str=c+"pageMedel3.action?styleName="+a+"&logUser="+b;
    //alert(str);
   window.location.href=str;
   }}}
   
   function selectbutton1(){
//alert("123");
var c="<%=basePath1%>";
//alert("123");

var b=document.getElementById('select2').value;
//alert("b="+b);
str=c+"pageMedel1.action?styleName="+b;
//alert("123");
 // alert(str);
   window.location.href=str;
}
function chAll(){

                         
var AllSelect=document.getElementById("checkbox1");

	var aUsers=document.getElementsByName("checkbox");
	
	var i;
	for(i=0;i<aUsers.length;i++)
		aUsers[i].checked=AllSelect.checked;


}

function delete1(){

$.ajax({
		url:'delLogPermission',
		type : "post",
		dataType : "json",
		success : function(data) {

var sUserText="";


		var UserItems = document.getElementsByName("checkbox");
		if(UserItems!=null)
		{
			for(i=0;i<UserItems.length;i++)
			{
				if(UserItems[i].checked==true)
				{
					if(sUserText!="")
						sUserText += ",";
					sUserText += UserItems[i].value;
				}
			}
			
			if(sUserText==""){
			alert("请先选中要删除的日志");
			}else{
			if(confirm('是否确定删除日志')){
			var c="<%=basePath1%>";
			
			str=c+"delete1.action?id="+sUserText
			//alert(str);
			window.location.href=str;}
			}
			
			//alert(sUserText);
			
		}
},
	error : function(XMLHttpRequest) {
		alert($(".error", XMLHttpRequest.responseText).text());
	}
		});	

}

function delete213(){

var sUserText="";


		var UserItems = document.getElementsByName("checkbox");
		if(UserItems!=null)
		{
			for(i=0;i<UserItems.length;i++)
			{
				if(UserItems[i].checked==true)
				{
					if(sUserText!="")
						sUserText += ",";
					sUserText += UserItems[i].value;
				}
			}
			
			if(sUserText==""){
			alert("请先选中要删除的日志");
			}else{
			if(confirm('是否确定删除日志')){
			var c="<%=basePath1%>";
			
			str=c+"delete1.action?id="+sUserText
			//alert(str);
			window.location.href=str;}
			}
			
			//alert(sUserText);
			
		}


}

</script>

</head>
<body>
<input type="hidden" value="<%=basePath1%>" id="basePath"/>
<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />

<div id="r1">
<div id="r2">
<p onclick="delete213()" onmousedown="downstyle(this);" onmouseover="overstyle(this);" onmousemove="movestyle(this);" onmouseout="outstyle(this);" onmouseup="upstyle(this);">
		<img src="image/role/delete.png" class="u3_img" style="width:12px"/>&nbsp;删除日志
		</p></div>
		
	<form action="selectLog"  id="" method="post">
	操作者：<input id="logUser" name="logbean.LogUser" >
	日志内容：<input id="logUser" name="logbean.LogContent" >
	日志类型：<input id="logUser" name="logbean.StyleName" >
	日志时间：<input id="logUser" name="logbean.LogTime" >
	<input type="submit" value="查询" />
	</form>	
		
		
<!--  
		
	<div id="r3">
		<input id="select2" type="text" value="" class="text1" style="margin:0 5px 0 0;padding:1px 5px;height:22px;width:190px;border:solid 1px #ccc;vertical-align: middel;line-height:22px"/>
		<input type="button" value="搜索" onclick="selectbutton1()" class="btn_searvh" />
	</div>
	
-->
</div>




<div id="usersTableDiv">
	<form action="" id="actionNameList">
		<table width="100%" border="0" cellspacing="0" class="listTable"  
			id="usersTable">
			<tr>
				<th><input type="checkbox" name="checkbox1" id="checkbox1" value="checkbox" onclick="chAll()">
					日志编号
				</th>
				<th>
					操作者
				</th>
				<th>
					日志内容
				</th>
				<th>
					日志类型
				</th>
				<th>
					日志时间
				</th>
			</tr>
			<c:choose>
				<c:when test="${empty student.recordsDate}">
					<tr>
						<td colspan="5" height="30" valign="middle" align="center">
							没有符合的数据
						</td>
					</tr>
		</table>

	
	<!-- 用于显示页码的div -->
	<div id="pageDiv">
		<p>
			总记录:${student.total }&nbsp; 当前页:
			<font color="red">${current }</font>/${student.totalPage}
			&nbsp;
			<a
				href="<%=basePath1%>${student.action}.action?currentPage=1&loguser=${loguser }&logtime=${logtime}&logcontent=${logcontent}&stylename=${stylename}">首页</a>
			&nbsp;
			<a
				href="<%=path1%>/${student.action}.action?currentPage=${current>1?current-1:"
				1"}&loguser=${loguser }&logtime=${logtime}&logcontent=${logcontent}&stylename=${stylename}">上一页</a> &nbsp;&nbsp;
			<a
				href="<%=path1%>/${student.action}.action?currentPage=${current<student.totalPage?current+1:1}&loguser=${loguser }&logtime=${logtime}&logcontent=${logcontent}&stylename=${stylename}">下一页</a>
			&nbsp;
			<a
				href="<%=path1%>/${student.action}.action?currentPage=1&loguser=${loguser }&logtime=${logtime}&logcontent=${logcontent}&stylename=${stylename}">尾页</a>
			&nbsp;
		</p>

	</div>
	</c:when>
	<c:otherwise>
		<c:forEach var="s" items="${student.recordsDate}">
			<tr>
				<td><input type="checkbox" name="checkbox" value="${s.logId}">
					${s.logId}
				</td>
				<td>
					${s.logUser}
				</td>
				<td>
					${s.logContent}
				</td>
				<td>
					${s.styleName}
				</td>
				<td>
					${s.logTime}
				</td>
			</tr>
		</c:forEach>
		</table>
	<!-- 用于显示页码的div -->
	<div id="pageDiv">
		<p>
			总记录:${student.total }&nbsp; 当前页:
			<font color="red">${current }</font>/${student.totalPage}<input type="hidden" value="${student.totalPage}" id="LogtotalPage"/>
			&nbsp;
			<a
				href="<%=basePath1%>${student.action}.action?currentPage=1&loguser=${loguser }&logtime=${logtime}&logcontent=${logcontent}&stylename=${stylename}">首页</a>
			&nbsp;
			<a
				href="<%=path1%>/${student.action}.action?currentPage=${current>1?current-1:"
				1"}&loguser=${loguser }&logtime=${logtime}&logcontent=${logcontent}&stylename=${stylename} ">上一页</a>
			&nbsp;
			<a
				href="<%=path1%>/${student.action}.action?currentPage=${current<student.totalPage?current+1:student.totalPage}&loguser=${loguser }&logtime=${logtime}&logcontent=${logcontent}&stylename=${stylename}">下一页</a>
			&nbsp;
			<a
				href="<%=path1%>/${student.action}.action?currentPage=${student.totalPage}&loguser=${loguser }&logtime=${logtime}&logcontent=${logcontent}&stylename=${stylename}">尾页</a>
			&nbsp; 到第
			<input type="text" id="page" value="1" style="width:26px"/>
			页
			<input type="button" value="跳转" onclick="page1()" class="freeGo" />
		</p>
	</div>
	</c:otherwise>
	</c:choose>
	</form>
</div>
</body></html>