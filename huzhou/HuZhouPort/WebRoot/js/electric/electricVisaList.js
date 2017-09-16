var IsVisaStatus = '待签证';
$(document).ready(function() {
	showJsonElectricVisa("showElectricVisaInfo");//进入界面调用此方法
		changVisaStatus('待签证');
	});
function changVisaStatus(value) {
	$("#layer4_left a").css("color", "#77797e");
	$("#visaInfo_" + value).css("color", "#0260a6");
	IsVisaStatus = value;
	showJsonElectricVisa("showElectricVisaInfo");//进入界面调用此方法	
}
// 查询全部记录
function showJsonElectricVisa(actionName) {
	getConditionElectriVisaInfo(actionName, 1);
}

/**
 * 无条件查询用户信息
 * 
 * @param actionName:执行获取的action名称
 * @param selectedPage:当前选中页码
 */
function getJsonElectricVisa(actionName, selectedPage) {
	/*deleteTr();*/
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.orderByFielName' : "ev.visaID",
			'queryCondition.sequence' : 'asc',
			'cpage' : selectedPage
		},

		beforeSend : function() {
			$(".loadingData").show();// 获取数据之前显示loading图标。
		},
		success : function(data) {
			deleteTr();
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
							'getJsonElectricVisa', actionName,
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
		getJsonElectricVisa(actionName, pageNo);
	}
}
function appendToTable(electricvisalist, listLength) {

	for ( var i = 0; i < listLength; i++) {
		newTr = $("<tr class='addTr'></tr>");

		newTr.append($("<td >" + DateIsNull(electricvisalist[i].shipName)
				+ "&nbsp;</td>"));
		newTr.append($("<td >" + DateIsNull(electricvisalist[i].startingPort)
				+ "&nbsp;</td>"));
		newTr.append($("<td >" + DateIsNull(electricvisalist[i].arrivalPort)
				+ "&nbsp;</td>"));
		newTr.append($("<td>" + DateIsNull(electricvisalist[i].cargoTypes)
				+ "&nbsp;</td>"));
		newTr.append($("<td >" + DateIsNull(electricvisalist[i].mark)
				+ "&nbsp;</td>"));
		newTr.append($("<td>" + DateIsNull(electricvisalist[i].visaStatus)
				+ "&nbsp;</td>"));
		newTr.append($("<td )>" + DateIsNull(electricvisalist[i].visaUserName)
				+ "&nbsp;</td>"));
		newTr.append($("<td><a onclick=findVisaById('"
				+ electricvisalist[i].visaID + "','"
				+ electricvisalist[i].visaStatus
				+ "') class='operation'>查看</a></td>"));
		$("#electricVisaTable").append(newTr);
	}
	$(".litigantTable tr:even").css("background-color", "#f6f6f6");
	$(".litigantTable tr:odd").css("background-color", "#fff");
}
function findVisaById(visaID, visaStatus) {
	if (visaStatus == "待签证") {
		window.location.href = $("#basePath").val()
				+ "page/electric/electricVisaListShow.jsp?" + "&visaID="
				+ visaID;
	} else {
		window.location.href = $("#basePath").val()
				+ "page/electric/electricVisaListShowUser.jsp?" + "&visaID="
				+ visaID;
	}
}
function DateIsNull(value) {
	returnValue = "&nbsp;";
	if (value == null || value == "null") {

		return returnValue;
	} else {
		return value;
	}
}
function dateT(value) {
	if (value == null) {
		return "";
	}
	return value.replace("T", " ");
}
/***
 * 按条件查询
 * @param {Object} actionName
 * @param {Object} selectedPage
 */
function getConditionElectriVisaInfo(actionName, selectedPage) {
	var search = $("#conditionId").val();
	/*if (search == "") {
		alert("请先输入要查询的内容");
		return;
	}*/
	if (/[~#^$@%&!*\s*]/.test(search)) {
		alert("输入的查询内容不能包含特殊字符！");
		return;
	}
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
		    'queryCondition.listKeyValuePair[0].key' : 'ev.visaStatus',
			'queryCondition.listKeyValuePair[0].pair' : '=',
			'queryCondition.listKeyValuePair[0].value' : IsVisaStatus,			
			'condition' : $("#conditionId").val(),			
			'queryCondition.orderByFielName' : 'ev.time',
			'queryCondition.sequence' : 'desc',
			'cpage' : selectedPage
		},
		beforeSend : function() {
			$(".loadingData").show();// 获取数据之前显示loading图标。
	},
	success : function(data) {
		deleteTr();
		var gotoPageMethodName = "gotoConditionPageNo";
		cleanNullTitile();
		if (data.list == null) {
			printPage(0, selectedPage, 'getConditionUsersInfo', actionName,
						gotoPageMethodName, 0);
			DataIsNullTitile();
		} else {
			var listLength = data.list.length;
			var list = data.list;
			appendToTable(list, listLength);// 跳转页码的方法名称		
		printPage(data.totalPage, selectedPage, 'getConditionLitigantInfo',
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
 * 清除表格中的所有数据
 */
function deleteTr() {
	$(".addTr").remove();
	$(".addTr").empty();
}


