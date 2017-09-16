var nowPage;
$(document).ready(function() {
	ShowPublicUserListByShip("PublicUserListByShip",1);// 全部用户列表		
	
});
function ShowPublicUserListByShip(actionName,selectedPage){
	nowPage=selectedPage;
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data:{
			/*'queryCondition.orderByFielName' : 'pu.userId',
			'queryCondition.sequence' : 'asc',*/
			'cpage' : selectedPage
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
					appendToTable(list, listLength);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo1";
					printPage(data.totalPage, selectedPage, 'ShowPublicUserListByShip',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		}
	});
}

//数据为空
function TableIsNull(){
	$(".addTr").empty();
	$(".addTr").remove();
	newTr = $("<tr class='addTr'><td colspan='5' align='center'>暂无相关数据</td></tr>");
	$("#usertable").append(newTr);
	
}

function SearchPublicUser(actionName,selectedPage){
	var search = $("#search_content").val();

	if (/[~#^$@%&!*\s*]/.test(search)) {
		alert("输入的查询内容不能包含特殊字符！");
		return;
	}
	nowPage=selectedPage;
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data:{
		'queryCondition.listKeyValuePair[0].key' : 's.shipNum',
		'queryCondition.listKeyValuePair[0].pair' : '%%',
		'queryCondition.listKeyValuePair[0].value' : search,
		'queryCondition.listKeyValuePair[0].connector' : 'or',
		'queryCondition.listKeyValuePair[1].key' : 'p.userName',
		'queryCondition.listKeyValuePair[1].pair' : '%%',
		'queryCondition.listKeyValuePair[1].value' : search,
		/*'queryCondition.orderByFielName' : 'p.userId',
		'queryCondition.sequence' : 'asc',*/
		'cpage' : selectedPage
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
					appendToTable(list, listLength);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo2";
					printPage(data.totalPage, selectedPage, 'SearchPublicUser',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		}
	});
}
function gotoPageNo1(actionName, totalPage) {
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
		ShowPublicUserListByShip(actionName, pageNo);
	}
}
function gotoPageNo2(actionName, totalPage) {
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
		SearchPublicUser(actionName, pageNo);
	}
}
function appendToTable(userlist, listLength) {
	$(".addTr").empty();
	$(".addTr").remove();

	for ( var i = 0; i < listLength; i++) {
		newTr = $("<tr class='addTr'></tr>");
		var check;
		if(userlist[i][2]==0){
			check="<a class='operation' onclick='CheckPublicUser("+ userlist[i][4] + ",1)'>通过</a>&nbsp;<a class='operation' onclick='CheckPublicUser("+ userlist[i][4] + ",2)'>不通过</a>";
		}else if(userlist[i][2]==1){
			check="<span style='color:#ccc;'>审核通过</span>";
		}else{
			check="<span style='color:red;'>未通过</span>";
		}
		newTr.append($("<td><input type='checkbox' value='"
				+ userlist[i][0]
				+ "' name='userIdList'  /></td>"));
		newTr.append($("<td>"+ (i + 1) + "</td>"));
		newTr.append($("<td>" + userlist[i][1] + "</td>"));
		newTr.append($("<td>" + userlist[i][3] + "</td>"));
		newTr.append($("<td><a onclick=findPublicUserById('"
						+ userlist[i][0]+ "') class='operation'>查看</a>&nbsp;"
						+ "<a onclick=UpdatePublicUser('"
						+ userlist[i][0]+ "') class='operation'>" +
						"编辑</a>&nbsp;"
						+"<a class='operation' onclick='DeleteSimplePublicUser("+ userlist[i][0] + ")'>" +
						"删除</a>&nbsp;"
						+check
						+ "</td>"));

		$("#usertable").append(newTr);
	}
}


//审核客户
function CheckPublicUser(id,state){
	$.ajax( {
		url : 'Audit',
		type : "post",
		dataType : "json",
		data:{
			't' : id,
			'state' : state
		},
		success : function(data) {
			alert('审核成功');
			ShowPublicUserListByShip("PublicUserListByShip",1);
		}
	})
}
//删除客户
function DeletePublicUser() {
	var opts = document.getElementsByName("userIdList");
	var linkStr = "";
	for (i = 0; i < opts.length; i++) {
		if (opts[i].checked == true) {
			if(linkStr==""){
				linkStr = opts[i].value;
			}
			else{
				linkStr +=","+ opts[i].value;	
			}
		}
	}
	if (linkStr== "") {
		alert("请先选中要删除的客户！");
		return;
	}
	if (confirm('确定要删除客户吗？')) {
		$.ajax( {
			url : 'DeletePublicUser',
			type : "post",
			dataType : "json",
			data : {
				'publicUser.userList' : linkStr
			},
			success : function(data) {
				alert("删除成功!");
				ShowPublicUserListByShip("PublicUserListByShip", nowPage);
				$("#chooseAll").attr("checked", "");
			},
			error : function(XMLHttpRequest) {
				alert("删除失败，请刷新，重新尝试！");
			}
		});
	}

}
//新增客户
function AddPublicUser(){
	window.location.href=$("#basePath").val()+"page/publicuser/shipuser_add.jsp";
}
// 船舶资料管理
function GoShipManage(){
	window.location.href=$("#basePath").val()+"page/publicuser/manage_ship.jsp";
}

//查看
function findPublicUserById(userId){
	
	window.location.href = $("#basePath").val()+ "page/publicuser/shipuser_see.jsp?publicId="+userId;
}

function UpdatePublicUser(userId){
	window.location.href=$("#basePath").val()+"page/publicuser/shipuser_update.jsp?publicId="+userId;
}
function DeleteSimplePublicUser(userId){
	var linkStr =userId;
	if (confirm('确定要删除客户吗？')) {
		$.ajax( {
			url : 'DeletePublicUser',
			type : "post",
			dataType : "json",
			data : {
				'publicUser.userList' : linkStr
			},
			success : function(data) {
				alert("删除成功!");
				ShowPublicUserListByShip("PublicUserListByShip", nowPage);
			}
		});
	}

}
var SelectAllIcon=0;
function SelectAll(){
	if(SelectAllIcon==1){
	$(":checkbox").attr("checked","");
	SelectAllIcon=0;
	}else{
	$(":checkbox").attr("checked","checked");
	SelectAllIcon=1;
	}
}
//船舶资料管理
function  ShipManageOver(id){
	id.src="image/system/shipmanage_hover.png";
}
function  ShipManageOut(id){
	id.src="image/system/shipmanage_normal.png";
}
//新增客户
function  AddCustomOver(id){
	id.src="image/operation/add_custom_hover.png";
}
function  AddCustomOut(id){
	id.src="image/operation/add_custom_normal.png";
}
//删除客户
function  DeleteCustomOver(id){
	id.src="image/operation/delete_custom_hover.png";
}
function  DeleteCustomOut(id){
	id.src="image/operation/delete_custom_normal.png";
}
