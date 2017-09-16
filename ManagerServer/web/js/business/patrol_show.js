$(document).ready(function() {
	showAllPatrolList("showPatrolList",1);// 全部列表
		
});
var Categories;
function PatrolListByCategories(type,id){
	$("#layer4_left a").css("color","#77797e");
	type.style.color="#0260a6";
	Categories=id;
	showPatrolListByCategories("showPatrolList", 1);
}
//类别列表
function showPatrolListByCategories(actionName, selectedPage){
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : 'p.patCategoriese',
			'queryCondition.listKeyValuePair[0].pair' : '=',
			'queryCondition.listKeyValuePair[0].value' : Categories,
			'queryCondition.orderByFielName' : 'p.patrolTime',
			'queryCondition.sequence' : 'desc',
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
					appendToTable(list, listLength);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo2";
					printPage(data.totalPage, selectedPage, 'showPatrolListByCategories',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		},
		complete : function() {
			//$(".loadingData").hide();// 请求执行完后隐藏loading信息。
	}
	});	
}
//全部列表
function showAllPatrolList(actionName, selectedPage){
	$("#layer4_left a").css("color","#77797e");
	$("#layer4_left a:first").css("color","#0260a6");
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.orderByFielName' : 'p.patrolTime',
			'queryCondition.sequence' : 'desc',
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
					appendToTable(list, listLength);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo3";
					printPage(data.totalPage, selectedPage, 'showAllPatrolList',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		},
		complete : function() {
			//$(".loadingData").hide();// 请求执行完后隐藏loading信息。
	}
	});	
}
//搜索
function search(actionName, selectedPage){
	var searchcontent= $("#searchContent").val();
	if(searchcontent==""){
		alert("输入的查询内容不能为空！");
		return;
	}
	if (/[~#^$@%&!*\s*]/.test(searchcontent)) {
		alert("输入的查询内容不能包含特殊字符！");
		return;
	}
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : 'u.name',
			'queryCondition.listKeyValuePair[0].pair' : '%%',
			'queryCondition.listKeyValuePair[0].value' : searchcontent,
			'queryCondition.listKeyValuePair[0].connector' : 'or',
			'queryCondition.listKeyValuePair[1].key' : 'p.patrolObject',
			'queryCondition.listKeyValuePair[1].pair' : '%%',
			'queryCondition.listKeyValuePair[1].value' : searchcontent,
			'queryCondition.listKeyValuePair[1].connector' : 'or',
			'queryCondition.orderByFielName' : 'p.patrolTime',
			'queryCondition.sequence' : 'desc',
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
					appendToTable(list, listLength);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo4";
					printPage(data.totalPage, selectedPage, 'search',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		},
		complete : function() {
			//$(".loadingData").hide();// 请求执行完后隐藏loading信息。
	}
	});	

}
//数据为空
function TableIsNull(){
	$(".addTr").empty();
	$(".addTr").remove();
	newTr = $("<tr class='addTr'><td colspan='5' align='center'>暂无相关数据</td></tr>");
	$("#Patroltable").append(newTr);
	
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
		showPatrolListByCategories(actionName, pageNo);
	}
}
function gotoPageNo3(actionName, totalPage) {
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
		showAllPatrolList(actionName, pageNo);
	}
}
function gotoPageNo4(actionName, totalPage) {
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
		search(actionName, pageNo);
	}
}
function appendToTable(list, listLength){
	$(".addTr").empty();
	$(".addTr").remove();

	for ( var i = 0; i < listLength; i++) {
		newTr = $("<tr class='addTr'></tr>");
		newTr.append($("<td>"+(i+1)+"</td>"));
		newTr.append($("<td>"+list[i][0].patrolObject+"</td>"));
		newTr.append($("<td>"+list[i][1].name+"</td>"));
		newTr.append($("<td>"+GetShortTime(list[i][0].patrolTime)+"</td>"));
		newTr.append($("<td><a onclick='GotoSee("+list[i][0].patrolId+")' class='operation'>查看</a>&nbsp;&nbsp;" +
				"<a onclick='GotoUpdate("+list[i][0].patrolId+")' class='operation'>补充</a></td>"));
		$("#Patroltable").append(newTr);
	}
	
}

function GotoSee(id){
	window.parent.location.href=$("#basePath").val()+"page/business/patrol_see.jsp?patrolId="+id;
}
function GotoUpdate(id){
	window.parent.location.href=$("#basePath").val()+"page/business/patrol_update.jsp?patrolId="+id;
}
