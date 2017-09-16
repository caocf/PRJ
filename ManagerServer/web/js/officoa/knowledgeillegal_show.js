$(document).ready(function() {
		showAllIllegalList("showIllegalList",1);// 待审核列表
		
});
//待审核列表
function showIllegalList(actionName, selectedPage){
	$("#layer4_left a").css("color","#77797e");
	$("#layer4_left a:first").css("color","#0260a6");
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : 'il.reviewResult',
			'queryCondition.listKeyValuePair[0].pair' : '=',
			'queryCondition.listKeyValuePair[0].value' : 1,
			
			'queryCondition.orderByFielName' : 'il.illegalTime',
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
					gotoPageMethodName = "gotoPageNo1";
					printPage(data.totalPage, selectedPage, 'showIllegalList',
							actionName, gotoPageMethodName, data.allTotal);
				}
			}
		},
		complete : function() {
			//$(".loadingData").hide();// 请求执行完后隐藏loading信息。
	}
	});	
}
var Categories;
function IllegalListByCategories(type,id){
	$("#layer4_left a").css("color","#77797e");
	type.style.color="#0260a6";
	Categories=id;
	showIllegalListByCategories("showIllegalList", 1);
}
//类别列表
function showIllegalListByCategories(actionName, selectedPage){
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : 'il.illegalCategories',
			'queryCondition.listKeyValuePair[0].pair' : '=',
			'queryCondition.listKeyValuePair[0].value' : Categories,
			'queryCondition.orderByFielName' : 'il.illegalTime',
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
					printPage(data.totalPage, selectedPage, 'showIllegalListByCategories',
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
function showAllIllegalList(actionName, selectedPage){
	$("#layer4_left a").css("color","#77797e");
	$("#layer4_left a:last").css("color","#0260a6");
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.orderByFielName' : 'il.illegalTime',
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
					printPage(data.totalPage, selectedPage, 'showAllIllegalList',
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
			'queryCondition.listKeyValuePair[0].key' : 'u1.name',
			'queryCondition.listKeyValuePair[0].pair' : '%%',
			'queryCondition.listKeyValuePair[0].value' : searchcontent,
			'queryCondition.listKeyValuePair[0].connector' : 'or',
			'queryCondition.listKeyValuePair[1].key' : 'u2.name',
			'queryCondition.listKeyValuePair[1].pair' : '%%',
			'queryCondition.listKeyValuePair[1].value' :searchcontent,
			'queryCondition.listKeyValuePair[1].connector' : 'or',
			'queryCondition.listKeyValuePair[2].key' : 'il.illegalObject',
			'queryCondition.listKeyValuePair[2].pair' : '%%',
			'queryCondition.listKeyValuePair[2].value' : searchcontent,
			'queryCondition.listKeyValuePair[2].connector' : 'or',
			'queryCondition.listKeyValuePair[3].key' : 'ir.reasonName',
			'queryCondition.listKeyValuePair[3].pair' : '%%',
			'queryCondition.listKeyValuePair[3].value' :searchcontent,
			'queryCondition.orderByFielName' : 'il.illegalTime',
			'queryCondition.sequence' : 'desc',
			'cpage' : selectedPage
		},
		beforeSend : function() {
			//$(".loadingData").show();// 获取数据之前显示loading信息。
		},
		success : function(data) {
			$("#layer4_left a").css("color","#77797e");
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
	newTr = $("<tr class='addTr'><td colspan='8' align='center'>暂无相关数据</td></tr>");
	$("#illegaltable").append(newTr);
	
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
		showIllegalList(actionName, pageNo);
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
		showIllegalListByCategories(actionName, pageNo);
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
		showAllIllegalList(actionName, pageNo);
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
//		newTr.append($("<td>"+(i+1)+"</td>"));
		newTr.append($("<td>"+list[i][0].illegalObject+"</td>"));
		newTr.append($("<td>"+list[i][1].name+","+list[i][2].name+"</td>"));
		newTr.append($("<td>"+list[i][3].reasonName+"</td>"));
		if(list[i][0].reviewWether==0)
		{
			
			newTr.append($("<td><a onclick='GotoSee("+list[i][0].illegalId+")' class='operation'>查看</a></td>"));
		}
		if(list[i][0].reviewResult==1){
		
			newTr.append($("<td><a onclick='GotoSee("+list[i][0].illegalId+")' class='operation'>查看</a></td>"));
			
		}
		else if(list[i][0].reviewResult==2){
			
			newTr.append($("<td><a onclick='GotoSee("+list[i][0].illegalId+")' class='operation'>查看</a></td>"));
		}
		$("#illegaltable").append(newTr);
	}
	
}

function GotoSee(id){
	window.parent.location.href=$("#basePath").val()+"page/business/illegal_see.jsp?illegalId="+id;
}
function GotoUpdate(id){
	window.parent.location.href=$("#basePath").val()+"page/business/illegal_update.jsp?illegalId="+id;
}
function GotoCheck(id){
	window.parent.location.href=$("#basePath").val()+"page/business/illegal_check.jsp?illegalId="+id;
}
function AddNewIllegal(){
	window.parent.location.href=$("#basePath").val()+"page/business/illegal_add.jsp";
}