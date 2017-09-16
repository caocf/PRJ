var statusType;
var nowPage;
$(document).ready(function() {
		statusType=1;//在职
		showUsersList("UserList",1);// 全部用户列表		
		showAllStatusInfo();//状态
		
	});
//状态
function showAllStatusInfo() {
	$.ajax( {
		url : 'showStatusInfo',
		type : "post",
		dataType : "json",
		success : function(data) {
		var Statue1="",Statue2="",Statue3="";
			for(var i=0;i<data.list.length;i++)
			{
				Statue3=Statue3+"<input type='radio' value='"+data.list[i].statusId+"' name='statusId' " +
						" onclick='onchecked("+data.list[i].statusId+")' id='radio"+data.list[i].statusId+"'/>"+data.list[i].statusName;
				if(data.list[i].statusType==1)
					Statue1=Statue1+"<option value='"+data.list[i].statusId+"' >"
					+data.list[i].statusName+"</option>";
				else 
					Statue2=Statue2+"<option value='"+data.list[i].statusId+"' >"
					+data.list[i].statusName+"</option>";
					
			}
			$("#changeUserStatus .UserStatusDIV").html(Statue3);
			$("#optgroup1").html(Statue1);
			$("#optgroup2").html(Statue2);
		}
	});
}
//查询记录
function showUsersList(actionName,selectedPage) {
	if($("#departmentID").val()==""||$("#departmentID").val()=="null" )
	getUsersList(actionName, selectedPage);
	else getUsersListByDepartment(actionName, selectedPage);
}
function changeList(actionName,selectedPage) {
	nowPage=selectedPage;
	var statusId = null;
	var statusType = null, value;
	value = $("#List_select").val();
	if (value == "inPost") {
		statusType = 1;
		getUsersListBySelected(actionName,'s.statusType', statusType, selectedPage);
	}
	if (value == "outPost") {
		statusType = 0;
		getUsersListBySelected(actionName,'s.statusType', statusType, selectedPage);
	}
	if (!isNaN(value)) {
		statusId = value;
		getUsersListBySelected(actionName,'s.statusId', statusId, selectedPage);
	}
	if (value == "all")  {showAllUsersList("UserList","1");}

}
//查询全部记录
function showAllUsersList(actionName,selectedPage) {
	if($("#departmentID").val()==""||$("#departmentID").val()=="null" )
	getAllUsersList(actionName, selectedPage);
	else getAllUsersListByDepartment(actionName, selectedPage);
}

function getUsersListBySelected(actionName,statusKey,statusValue,selectedPage){

	if($("#departmentID").val()==""||$("#departmentID").val()=="null") 
		{
		getUsersListBySelected1(actionName,statusKey,statusValue,selectedPage);
		}
	else 
		{
		getUsersListBySelected2(actionName,statusKey,statusValue,selectedPage);
		}
}
function getUsersListBySelected1(actionName,statusKey,statusValue,selectedPage){
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : statusKey,
			'queryCondition.listKeyValuePair[0].pair' : '=',
			'queryCondition.listKeyValuePair[0].value' : statusValue,
			'queryCondition.orderByFielName' : "u.order",
			'queryCondition.sequence' : 'asc',
			'cpage' : selectedPage
		},
		beforeSend : function() {
			//$(".loadingData").show();// 获取数据之前显示loading信息。
		},
		success : function(data) 
		{
			if (data.list == null) {
				TableIsNull();
			} else {
				var listLength = data.list.length;
				var list = data.list;
				if (listLength == 0) {
					alert("暂无相关数据");
				} else {
					appendToTable(list, listLength,selectedPage,data);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.totalPage, selectedPage, 'changeList',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		},
		complete : function() {
			//$(".loadingData").hide();// 请求执行完后隐藏loading信息。
	}
	});
}
function getUsersListBySelected2(actionName,statusKey,statusValue,selectedPage){
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'user.partOfDepartment' : $("#departmentID").val(),
			'queryCondition.listKeyValuePair[0].key' : statusKey,
			'queryCondition.listKeyValuePair[0].pair' : '=',
			'queryCondition.listKeyValuePair[0].value' : statusValue,
			'queryCondition.orderByFielName' : "u.order",
			'queryCondition.sequence' : 'asc',
			'cpage' : selectedPage
		},
		beforeSend : function() {
			//$(".loadingData").show();
		},
		success : function(data) {
			if (data.list == null) {
				TableIsNull();
			} else {
				var listLength = data.list.length;
				var list = data.list;
				if (listLength == 0) {
					alert("暂无相关数据");
				} else {
					appendToTable(list, listLength,selectedPage,data);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.totalPage, selectedPage, 'changeList',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		},
		complete : function() {
			//$(".loadingData").hide();// 请求执行完后隐藏loading信息。
	}
	});
}
function getAllUsersList(actionName, selectedPage) {
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.orderByFielName' : "u.order",
			'queryCondition.sequence' : 'asc',
			'cpage' : selectedPage
		},
		beforeSend : function() {
			//$(".loadingData").show();// 获取数据之前显示loading信息。
		},
		success : function(data) {
			if (data.list == null) {
				TableIsNull();
			} else {
				var listLength = data.list.length;
				var list = data.list;
				if (listLength == 0) {
					alert("暂无相关数据");
				} else {
					appendToTable(list, listLength,selectedPage,data);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.totalPage, selectedPage, 'showAllUsersList',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		},
		complete : function() {
			//$(".loadingData").hide();// 请求执行完后隐藏loading信息。
	}
	});
}
function getUsersList(actionName, selectedPage) {
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : 's.statusType',
			'queryCondition.listKeyValuePair[0].pair' : '=',
			'queryCondition.listKeyValuePair[0].value' : statusType,
			'queryCondition.orderByFielName' : "u.order",
			'queryCondition.sequence' : 'asc',
			'cpage' : selectedPage
		},
		beforeSend : function() {
			//$(".loadingData").show();// 获取数据之前显示loading信息。
		},
		success : function(data) {
			if (data.list == null) {
				TableIsNull();
			} else {
				var listLength = data.list.length;
				var list = data.list;
				if (listLength == 0) {
					alert("暂无相关数据");
				} else {
					appendToTable(list, listLength,selectedPage,data);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.totalPage, selectedPage, 'showUsersList',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		},
		complete : function() {
		//	$(".loadingData").hide();// 请求执行完后隐藏loading信息。
	}
	});
}



function appendToTable(userlist, listLength,nowpage,data)
{
	$(".addTr").empty();
	$(".addTr").remove();
	

	for ( var i = 0; i < listLength; i++) 
	{
		newTr = $("<tr class='addTr'></tr>");

		newTr.append($("<td><input type='checkbox' value='"
				+ userlist[i][0].userId
				+ "' name='userIdList' class='u"+userlist[i][0].userId+"' /></td>"));
		newTr.append($("<td>"+ (i + 1) + "</td>"));
		newTr.append($("<td>" + DateIsNull(userlist[i][0].name) + "</td>"));
		newTr.append($("<td>" + DateIsNull(userlist[i][2].departmentName) + "</td>"));
		newTr.append($("<td>" + DateIsNull(userlist[i][1].postName) + "</td>"));

		
		var ss= userlist[i][0].tel.split(',');
		
		newTr.append($("<td>" + DateIsNull(ss[0]) + "</td>"));
		newTr.append($("<td>" + DateIsNull(userlist[i][0].email) + "</td>"));
		newTr.append($("<td>" + DateIsNull(userlist[i][4].statusName)+ "</td>"));
		
		if(i==0)
		{
			if(data.pu==null)
			{
				newTr.append($("<td>" 
						
				+"<a onclick=updateUser('"+ userlist[i][0].userId+ "') class='operation'>" +"编辑</a>&nbsp;"
				+"<a class='operation' onclick='ChangeUserStatusById("+ userlist[i][0].userId + ","+userlist[i][4].statusId+")'>" +"状态</a><br>"
				+"<span style='color:#ccc;'>上移</span>&nbsp;"
				+"<a class='operation' onclick='SetUserOrder("+ userlist[i][0].userId+","+userlist[i+1][0].userId+","+nowpage+")'>" +"下移</a>"
				+ "</td>"));
			}
			else
			{
				var pulist = data.pu;
				var puLength = data.pu.length;
				
				newTr.append($("<td style='white-space: normal;line-height:20px;'>" 
						
				+"<a onclick=updateUser('"+ userlist[i][0].userId+ "') class='operation'>" +"编辑</a>&nbsp;"
				+"<a class='operation' onclick='ChangeUserStatusById("+ userlist[i][0].userId + ","+userlist[i][4].statusId+")'>" +"状态</a><br>"
				+"<a class='operation' onclick='SetUserOrder("+ pulist[puLength-1][0].userId+","+userlist[i][0].userId+","+nowpage +")'>" +"上移</a>&nbsp;"
				+"<a class='operation' onclick='SetUserOrder("+ userlist[i][0].userId +","+userlist[i+1][0].userId+","+nowpage+")'>" +"下移</a>"
				+ "</td>"));
				
			}
		}
		else if(i==listLength-1)
		{
			if(data.nu==null)
			{
				newTr.append($("<td style='white-space: normal;line-height:20px;'>" 
						
				+"<a onclick=updateUser('"+ userlist[i][0].userId+ "') class='operation'>" +"编辑</a>&nbsp;"
				+"<a class='operation' onclick='ChangeUserStatusById("+ userlist[i][0].userId + ","+userlist[i][4].statusId+")'>" +"状态</a><br>"
				+"<a class='operation' onclick='SetUserOrder("+ userlist[i-1][0].userId+","+userlist[i][0].userId+","+nowpage +")'>" +"上移</a>&nbsp;"
				+"<span style='color:#ccc;'>下移</span>&nbsp;"
				+ "</td>"));
			}
			else
			{
				var nulist = data.nu;
				
				newTr.append($("<td style='white-space: normal;line-height:20px;'>" 
						
				+"<a onclick=updateUser('"+ userlist[i][0].userId+ "') class='operation'>" +"编辑</a>&nbsp;"
				+"<a class='operation' onclick='ChangeUserStatusById("+ userlist[i][0].userId + ","+userlist[i][4].statusId+")'>" +"状态</a><br>"
				+"<a class='operation' onclick='SetUserOrder("+ userlist[i-1][0].userId+","+userlist[i][0].userId+","+nowpage +")'>" +"上移</a>&nbsp;"
				+"<a class='operation' onclick='SetUserOrder("+ userlist[i][0].userId +","+nulist[0][0].userId+","+nowpage+")'>" +"下移</a>"
				+ "</td>"));
			}
		}
		else
		{
			newTr.append($("<td style='white-space: normal;line-height:20px;'>" 
					
			+"<a onclick=updateUser('"+ userlist[i][0].userId+ "') class='operation'>" +"编辑</a>&nbsp;"
			+"<a class='operation' onclick='ChangeUserStatusById("+ userlist[i][0].userId + ","+userlist[i][4].statusId+")'>" +"状态</a><br>"
			+"<a class='operation' onclick='SetUserOrder("+ userlist[i-1][0].userId+","+userlist[i][0].userId+","+nowpage +")'>" +"上移</a>&nbsp;"
			+"<a class='operation' onclick='SetUserOrder("+ userlist[i][0].userId +","+userlist[i+1][0].userId+","+nowpage+")'>" +"下移</a>"
			+ "</td>"));
		}		

		$("#usertable").append(newTr);
	}
}

function SetUserOrder(id,idc,pagenum)
{
	$.ajax( {
		url : 'SetUserOrder',
		type : "post",
		dataType : "json",
		data:{
			't' : id,
			'tc': idc,
		},
		success : function(data) {
			alert('修改成功');
			//ShowPublicUserListByShip("PublicUserListByShip",1);
			getUsersListByDepartment("UserList",pagenum);
		}
	})
}

//数据为空
function TableIsNull(){
	$(".addTr").empty();
	$(".addTr").remove();
	newTr = $("<tr class='addTr'><td colspan='9' align='center'>暂无相关数据</td></tr>");
	$("#usertable").append(newTr);
	
}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").attr("value","");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		showUsersList(actionName, pageNo);
	}
}

function gotoPageNoBySearch(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").attr("value","");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		searchUser(actionName, pageNo);
	}
}
//全选
flag = 0;
function chooseAllInfo() {
	if (flag == 0) {
		$(":checkbox").attr("checked", "checked");
		flag = 1;
	} else {
		$(":checkbox").attr("checked", "");
		flag = 0;
	}

}
function getAllUsersListByDepartment(actionName, selectedPage) {
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'user.partOfDepartment' : $("#departmentID").val(),
			'queryCondition.orderByFielName' : "u.order",
			'queryCondition.sequence' : 'asc',
			'cpage' : selectedPage
		},
		beforeSend : function() {
			//$(".loadingData").show();// 获取数据之前显示loading信息。
		},
		success : function(data) {
			if (data.list == null) {
				TableIsNull();
			} else {
				var listLength = data.list.length;
				var list = data.list;
				if (listLength == 0) {
					alert("暂无相关数据");
				} else {
					appendToTable(list, listLength,selectedPage,data);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.totalPage, selectedPage, 'showAllUsersList',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		},
		complete : function() {
			//$(".loadingData").hide();// 请求执行完后隐藏loading信息。
	}
	});
}
function getUsersListByDepartment(actionName, selectedPage) {
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'user.partOfDepartment' : $("#departmentID").val(),
			'queryCondition.listKeyValuePair[0].key' : 's.statusType',
			'queryCondition.listKeyValuePair[0].pair' : '=',
			'queryCondition.listKeyValuePair[0].value' : statusType,
			'queryCondition.orderByFielName' : "u.order",
			'queryCondition.sequence' : 'asc',
			'cpage' : selectedPage
		},
		beforeSend : function() {
			//$(".loadingData").show();// 获取数据之前显示loading信息。
		},
		success : function(data) {
			if (data.list == null) {
				TableIsNull();
			} else {
				var listLength = data.list.length;
				var list = data.list;
				if (listLength == 0) {
					alert("暂无相关数据");
				} else {
					appendToTable(list, listLength,selectedPage,data);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.totalPage, selectedPage, 'showUsersList',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		},
		complete : function() {
			//$(".loadingData").hide();// 请求执行完后隐藏loading信息。
	}
	});
}

function updateUser(userId){
	window.location.href=$('#basePath').val()+"page/system/user_update.jsp?UserId="	+userId;
}
function ChangeUserStatusById(userId,statusId)
{
	$("#changeUserStatus :checked").attr("checked","");
	$("#usertable .u"+userId).attr("checked","checked");//列表
	$(".UserStatusDIV #radio"+statusId).attr("checked","checked");//列表
	$("#changeUserStatus").show();
	$("#changeUserStatus").dialog( {
		title : '选择用户状态',
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false
	});
	$("#hideUserId").attr("value",userId);
}
function ChangeUserStatusByList(){
	$("#changeUserStatus :checked").attr("checked","");
	 var opts=document.getElementsByName("userIdList");
	 var linkStr="";
	for(i=0;i<opts.length;i++){
            if(opts[i].checked==true){
            	if(linkStr==""){
            		linkStr=opts[i].value;
            	}else{
            		linkStr+=","+opts[i].value;
            	}
                
            }
        }
	if(linkStr==""){
		alert("请先选中要调整的用户！");
		return;
	}
	$("#changeUserStatus").show();
	$("#changeUserStatus").dialog( {
		title : '选择用户状态',
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false
	});
}
function onchecked(statusId){
	$("#hideStatusId").attr("value",statusId);
}
//确定选择状态
function changeUserStatus() {
	if ($("#hideStatusId").val() == "") {
		alert("请先选择状态！");
	} else {
		$("#changeUserStatus").dialog("close");
		changeUserByListId();

	}
}
//批量删除
function changeUserByListId(){
	 var opts=document.getElementsByName("userIdList");
	 var linkStr="";
	for(i=0;i<opts.length;i++){
            if(opts[i].checked==true){
            	if(linkStr==""){
            		linkStr=opts[i].value;
            	}else{
            		linkStr+=","+opts[i].value;
            	}
                
            }
        }
	if(linkStr==""){
		alert("请先选中要调整的用户！");
		return;
	}
	if (confirm('确定要批量调整用户状态吗？')) {
		$.ajax( {
			url : 'deleteUserByListId',
			type : "post",
			dataType : "json",
			data : {
			'userIdList':linkStr,
			'userStatus':$("#hideStatusId").val()},
			success : function(data) {
			alert("调整成功!");
			changeList('UserList',1);
			$("#chooseAll").attr("checked","");
	}});
	}
}
function hiden(){
	$("#changeUserStatus").dialog("close");
}
//搜索
function searchUser(actionName,selectedPage){

	var search = $("#search_content").val();

	if (/[~#^$@%&!*\s*]/.test(search)) {
		alert("输入的查询内容不能包含特殊字符！");
		return;
	}
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : 'u.userName',
			'queryCondition.listKeyValuePair[0].pair' : '%%',
			'queryCondition.listKeyValuePair[0].value' : search,
			'queryCondition.listKeyValuePair[0].connector' : 'or',
			'queryCondition.listKeyValuePair[1].key' : 'u.name',
			'queryCondition.listKeyValuePair[1].pair' : '%%',
			'queryCondition.listKeyValuePair[1].value' :search,
			'queryCondition.listKeyValuePair[1].connector' : 'or',
			'queryCondition.listKeyValuePair[1].key' : 'de.departmentName',
			'queryCondition.listKeyValuePair[1].pair' : '%%',
			'queryCondition.listKeyValuePair[1].value' : search,
			'queryCondition.listKeyValuePair[1].connector' : 'or',
			'queryCondition.listKeyValuePair[2].key' : 'd.postName',
			'queryCondition.listKeyValuePair[2].pair' : '%%',
			'queryCondition.listKeyValuePair[2].value' : search,
			'queryCondition.listKeyValuePair[2].connector' : 'or',
			'queryCondition.listKeyValuePair[3].key' : 'u.tel',
			'queryCondition.listKeyValuePair[3].pair' : '%%',
			'queryCondition.listKeyValuePair[3].value' : search,
			'queryCondition.listKeyValuePair[3].connector' : 'or',
			'queryCondition.listKeyValuePair[4].key' : 'u.email',
			'queryCondition.listKeyValuePair[4].pair' : '%%',
			'queryCondition.listKeyValuePair[4].value' :search,
			'queryCondition.listKeyValuePair[4].connector' : 'or',
			'queryCondition.listKeyValuePair[5].key' : 's.statusName',
			'queryCondition.listKeyValuePair[5].pair' : '%%',
			'queryCondition.listKeyValuePair[5].value' : search,
			'queryCondition.listKeyValuePair[5].connector' : 'or',
			'queryCondition.orderByFielName' : 'u.userName',
			'queryCondition.sequence' : 'asc',
			'cpage' : selectedPage
		},
		beforeSend : function() {
			//$(".loadingData").show();// 获取数据之前显示loading图标。
		},
		success : function(data) {
			$("#List_select").attr("value","all");
			var gotoPageMethodName = "gotoPageNoBySearch";
			if (data.list == undefined) {
				$("#pageDiv").hide();
				TableIsNull();
			} else {
				var listLength = data.list.length;
				var list = data.list;		
				appendToTable(list, listLength,selectedPage,data);
				printPage(data.totalPage, selectedPage,
						'searchUser', actionName,
						gotoPageMethodName, data.allTotal);				
			}
		},
		complete : function() {
			//$(".loadingData").hide();// 请求执行完后隐藏loading图标。
	}
	});

}
function addUser(){
	window.location.href=$("#basePath").val()+"page/system/user_add.jsp";
}
