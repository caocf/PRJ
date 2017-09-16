$(document).ready(function() {
		showInspectionList("showInspectionList",1);// 待审核列表
		
});
//待审核列表
function showInspectionList(actionName, selectedPage){
	$("#layer4_left a").css("color","#77797e");
	$("#layer4_left a:first").css("color","#0260a6");
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : 'i.reviewResult',
			'queryCondition.listKeyValuePair[0].pair' : '!=',
			'queryCondition.listKeyValuePair[0].value' : 1,
			'queryCondition.orderByFielName' : 'i.inspectionTime',
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
					printPage(data.totalPage, selectedPage, 'showInspectionList',
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
function InspectionListByCategories(type,id){
	$("#layer4_left a").css("color","#77797e");
	type.style.color="#0260a6";
	Categories=id;
	showInspectionListByCategories("showInspectionList", 1);
}
//类别列表
function showInspectionListByCategories(actionName, selectedPage){
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : 'i.insCategoriese',
			'queryCondition.listKeyValuePair[0].pair' : '=',
			'queryCondition.listKeyValuePair[0].value' : Categories,
			'queryCondition.orderByFielName' : 'i.inspectionTime',
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
					printPage(data.totalPage, selectedPage, 'showInspectionListByCategories',
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
function showAllInspectionList(actionName, selectedPage){
	$("#layer4_left a").css("color","#77797e");
	$("#layer4_left a:last").css("color","#0260a6");
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.orderByFielName' : 'i.inspectionTime',
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
					printPage(data.totalPage, selectedPage, 'showAllInspectionList',
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
			'queryCondition.listKeyValuePair[1].key' : 'i.inspectionObject',
			'queryCondition.listKeyValuePair[1].pair' : '%%',
			'queryCondition.listKeyValuePair[1].value' : searchcontent,
			'queryCondition.listKeyValuePair[1].connector' : 'or',
			'queryCondition.orderByFielName' : 'i.inspectionTime',
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
	$("#Inspectiontable").append(newTr);
	
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
		showInspectionList(actionName, pageNo);
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
		showInspectionListByCategories(actionName, pageNo);
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
		showAllInspectionList(actionName, pageNo);
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
		newTr.append($("<td>"+list[i][0].inspectionObject+"</td>"));
		newTr.append($("<td>"+list[i][1].name+"</td>"));
		newTr.append($("<td>"+GetShortTime(list[i][0].inspectionTime)+"</td>"));
		if(list[i][0].reviewWether==0)
		{
		newTr.append($("<td>待审核</td>"));
		newTr.append($("<td><a onclick='GotoSee("+list[i][0].inspectionId+")' class='operation'>查看</a>&nbsp;&nbsp;" +
				"<a onclick='GotoUpdate("+list[i][0].inspectionId+")'  class='operation'>补充</a>&nbsp;&nbsp;" +
					"<a onclick='GotoCheck("+list[i][0].inspectionId+")'  class='operation'>审核</a></td>"));
		}
		if(list[i][0].reviewResult==1){
			newTr.append($("<td>审核通过</td>"));
			newTr.append($("<td><a onclick='GotoSee("+list[i][0].inspectionId+")'  class='operation'>查看</a>&nbsp;&nbsp;" +
					"<label style='color:#ccc'>补充</label>&nbsp;&nbsp;" +
						"<label style='color:#ccc'>审核</label></td>"));
			
		}
		else if(list[i][0].reviewResult==2){
			newTr.append($("<td>审核驳回</td>"));
			newTr.append($("<td><a onclick='GotoSee("+list[i][0].inspectionId+")' class='operation'>查看</a>&nbsp;&nbsp;" +
					"<a onclick='GotoUpdate("+list[i][0].inspectionId+")'  class='operation'>补充</a>&nbsp;&nbsp;" +
						"<a onclick='GotoCheck("+list[i][0].inspectionId+")'  class='operation'>审核</a></td>"));
		}
		$("#Inspectiontable").append(newTr);
	}
	
}

function GotoSee(id){
	window.parent.location.href=$("#basePath").val()+"page/business/permit_see.jsp?inspectionId="+id;
}
function GotoUpdate(id){
	window.parent.location.href=$("#basePath").val()+"page/business/permit_update.jsp?inspectionId="+id;
}
function GotoCheck(id){
	window.parent.location.href=$("#basePath").val()+"page/business/permit_check.jsp?inspectionId="+id;
}