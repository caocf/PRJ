$(document).ready(function() {
	showJsonElectricReport("showElectricReportInfo");//进入界面调用此方法
	});
// 查询全部记录
function showJsonElectricReport(actionName) {
	getJsonElectricReport(actionName, 1);
}
/**
 * 无条件查询用户信息
 * 
 * @param actionName:执行获取的action名称
 * @param selectedPage:当前选中页码
 */
function getJsonElectricReport(actionName, selectedPage) {
	/*deleteTr();*/
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.orderByFielName' : "e.reportTime",
			'queryCondition.sequence' : 'desc',
			'cpage' : selectedPage
		},

		beforeSend : function() {
			$(".loadingData").show();// 获取数据之前显示loading图标。
		},
		success : function(data) {
			deleteTr();
			cleanNullTitile();
			if (data.list == null) {

			} else {
				var listLength = data.list.length;
				var list = data.list;
				if (listLength == 0) {
					alert("暂无相关数据");
				} else {
					appendToTable(list, listLength);// 跳转页码的方法名称	
					gotoPageMethodName = "gotoPageNo";
					printPage(data.totalPage, selectedPage,
							'getJsonElectricReport', actionName,
							gotoPageMethodName, data.allTotal);
				}
			}
		},
		complete : function() {
			$(".loadingData").hide();// 请求执行完后隐藏loading图标。
	}
	});
}

function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").attr("value", "");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		getJsonElectricReport(actionName, pageNo);
	}
}
function appendToTable(electricreportlist, listLength) {

	for ( var i = 0; i < listLength; i++) {
		newTr = $("<tr class='addTr'></tr>");
		//newTr.append($("<td>" + DateIsNull(electricreportlist[i].reportID) + "&nbsp;</td>"));
		newTr.append($("<td >"+ DateIsNull(electricreportlist[i].shipName) + "&nbsp;</td>"));
		newTr.append($("<td >"+ DateIsNull(electricreportlist[i].cargoType) + "&nbsp;</td>"));
		newTr.append($("<td >"+ DateIsNull(electricreportlist[i].toBeDockedAtThePier)+ "&nbsp;</td>"));
		newTr.append($("<td >"+ DateIsNull(electricreportlist[i].estimatedTimeOfArrival)+ "&nbsp;</td>"));
		newTr.append($("<td >"+ dateT(electricreportlist[i].reportTime) + "&nbsp;</td>"));
		
		newTr.append($("<td><a onclick=findReportById('"+ electricreportlist[i].reportID + "') class='operation'>查看</a></td>"));
		$("#electricReportTable").append(newTr);
	}
	$(".litigantTable tr:even").css("background-color", "#f6f6f6");
	$(".litigantTable tr:odd").css("background-color", "#fff");
}
function findReportById(reportID) {
	window.location.href = $("#basePath").val()
			+ "page/electric/electricReportListShow.jsp?" + "&reportID="
			+ reportID;
}
function DateIsNull(value) {
	returnValue = "";
	if (value == null || value == "null") {

		return returnValue;
	} else {
		return value;
	}
}function dateT(value){
	if(value==null){
		return "";
	}
	return value.replace("T"," ");
}
/***
 * 按条件查询
 * @param {Object} actionName
 * @param {Object} selectedPage
 */
function getConditionElectriReortcInfo(actionName, selectedPage) {
	var search = $("#conditionId").val();
	if (search == "") {
		alert("请先输入要查询的内容");
		return;
	}
	if (/[~#^$@%&!*\s*]/.test(search)) {
		alert("输入的查询内容不能包含特殊字符！");
		return;
	}

	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : 'e.shipName',
			'queryCondition.listKeyValuePair[0].pair' : '%%',
			'queryCondition.listKeyValuePair[0].value' : $("#conditionId")
					.val(),
			'queryCondition.listKeyValuePair[0].connector' : 'or',
			'queryCondition.listKeyValuePair[1].key' : 'e.cargoType',
			'queryCondition.listKeyValuePair[1].pair' : '%%',
			'queryCondition.listKeyValuePair[1].value' : $("#conditionId")
					.val(),
			'queryCondition.listKeyValuePair[1].connector' : 'or',
			'queryCondition.listKeyValuePair[2].key' : 'e.estimatedTimeOfArrival',
			'queryCondition.listKeyValuePair[2].pair' : '%%',
			'queryCondition.listKeyValuePair[2].value' : $("#conditionId")
					.val(),
			'queryCondition.listKeyValuePair[2].connector' : 'or',
			'queryCondition.listKeyValuePair[3].key' : 'e.toBeDockedAtThePier',
			'queryCondition.listKeyValuePair[3].pair' : '%%',
			'queryCondition.listKeyValuePair[3].value' : $("#conditionId")
					.val(),
			'queryCondition.listKeyValuePair[3].connector' : 'or',
			'queryCondition.listKeyValuePair[4].key' : 'e.reportTime',
			'queryCondition.listKeyValuePair[4].pair' : '%%',
			'queryCondition.listKeyValuePair[4].value' : $("#conditionId")
					.val(),
			'queryCondition.listKeyValuePair[4].connector' : 'or',
			'queryCondition.orderByFielName' : 'e.shipName',
			'queryCondition.sequence' : 'asc',
			'cpage' : selectedPage
		},
		beforeSend : function() {
			$(".loadingData").show();// 获取数据之前显示loading图标。
	},
	success : function(data) {
		cleanNullTitile();
		deleteTr();
		var gotoPageMethodName = "gotoConditionPageNo";
		if (data.list == null) {
			printPage(0, selectedPage, 'getConditionElectriReortcInfo', actionName,
						gotoPageMethodName, 0);
			DataIsNullTitile();
		} else {
			var listLength = data.list.length;
			var list = data.list;
			appendToTable(list, listLength);// 跳转页码的方法名称		
		printPage(data.totalPage, selectedPage, 'getConditionElectriReortcInfo',
				actionName, gotoPageMethodName,data.allTotal);
	}
},
complete : function() {
	$(".loadingData").hide();// 请求执行完后隐藏loading图标。
	}
	});
}

function gotoConditionPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		getConditionLitigantInfo(actionName, pageNo);
	}
}

/**
 * 查看
 */
function searchLitigantIdInfo(litigantId){$.ajax({
	url:'USeePermission',
	type : "post",
	dataType : "json",
	success : function(data) {
	searchLitigantIdInfo2(litigantId);
},
error : function(XMLHttpRequest) {
	alert($(".error", XMLHttpRequest.responseText).text());
}
});
}
function searchLitigantIdInfo2(litigantId) {
	$.ajax( {
		url : 'showLitigantInfo',
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : 'l.litigantId',
			'queryCondition.listKeyValuePair[0].pair' : '=',
			'queryCondition.listKeyValuePair[0].value' : litigantId,
			'queryCondition.orderByFielName' : 'l.litigantId',
			'queryCondition.sequence' : 'asc',
			'cpage' : 1
		},
		success : function(data) {
			$("#litigantId1").attr("value", DateIsNull2(data.list[0].litigantId));
			$("#litigantName1").attr("value", DateIsNull2(data.list[0].litigantName));
			if (data.list[0].gender == '女') {
				$("#gender21").attr("checked", true);
			} else {
				$("#gender11").attr("checked", true);
			}
			
			$("#age1").attr("value", DateIsNull2(data.list[0].age));
			$("#idcardName1").attr("value", DateIsNull2(data.list[0].idcardName));			
			$("#idcardNo1").attr("value", DateIsNull2(data.list[0].idcardNo));
			$("#nation1").attr("value", DateIsNull2(data.list[0].nation));
			$("#address1").attr("value", DateIsNull2(data.list[0].address));
			$("#tel1").attr("value", DateIsNull2(data.list[0].tel));
			$("#email1").attr("value", DateIsNull2(data.list[0].email));
			$("#divisionName1").attr("value", DateIsNull2(data.list[0].divisionName));
			$("#legal1").attr("value", DateIsNull2(data.list[0].legal));
			$("#divisionAddress1").attr("value", DateIsNull2(data.list[0].divisionAddress));
			$("#divisionTel1").attr("value", DateIsNull2(data.list[0].divisionTel));
			
		}
	});
	showSearch();
}

/**
 * 清除表格中的所有数据
 */
function deleteTr() {
	$(".addTr").remove();
	$(".addTr").empty();
}

/**
 * 显示列表
 */
function showList() {
	$("#listDiv").show();
	$("#addLitigant").hide();
	$("#searchLitigant").hide();

}
/**
 * 显示编辑信息
 */
function showEdit() {
	$(".litigantInput").val("");
	$("#listDiv").hide();
	$("#addLitigant").show();

}
/**
 * 显示查看信息
 */
function showSearch() {
	$("#listDiv").hide();
	$("#searchLitigant").show();

}
function hidenLitigantInfo() {
	$("#addLitigant").dialog("close");
}

