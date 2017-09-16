$(document).ready(function() {
	showJsonFixedTermReport("findFixedTermReportInfo");//进入界面调用此方法
	});
// 查询全部记录
function showJsonFixedTermReport(actionName) {
	getJsonFixedTermReport(actionName, 1);
}
/**
 * 无条件查询用户信息
 * 
 * @param actionName:执行获取的action名称
 * @param selectedPage:当前选中页码
 */
function getJsonFixedTermReport(actionName, selectedPage) {
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.orderByFielName' : "f.startTime",
			'queryCondition.sequence' : 'desc',
			'cpage' : selectedPage
		},

		beforeSend : function() {
			$(".loadingData").show();// 获取数据之前显示loading图标。
	},
		success : function(data) {
			deleteTr();
			//			cleanNullTitile();
		if (data.list == null) {

		} else {
			var listLength = data.list.length;
			var list = data.list;
			if (listLength == 0) {
				alert("暂无相关数据");
			} else {
				appendToTableInfo(list, listLength);// 跳转页码的方法名称	
				gotoPageMethodName = "gotoPageNo";
				printPage(data.totalPage, selectedPage,
						'getJsonFixedTermReport', actionName,
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
		getJsonFixedTermReport(actionName, pageNo);
	}
}
function appendToTableInfo(fixedTermReportlist, listLength) {
	for ( var i = 0; i < listLength; i++) {
		var newDiv = $("<div class='addDiv' id='fixedTermData_div_" + i
				+ "'></div>");
		var newTable = $("<table id='fixedTermData_table_"
				+ i
				+ "' class='fixedTermData_table' onclick=findFixedTermDataById('"
				+ fixedTermReportlist[i].fixedTermID + "','fixedTermData_div_"
				+ i + "','state_"+i+"')></table>")
		var newTr = $("<tr class='addTr' id='fixedTermData_tr_" + i + "'></tr>");
		newTr
				.append($("<td width='18px'><input type='checkbox' id='fixedTermCheckBox_"
						+ i
						+ "' onclick='jogefixedTermCheckBox(this.id)' class='fixedTermCheckBox' value='"
						+ fixedTermReportlist[i].fixedTermID + "'></td>"));
		newTr.append($("<td width='180px'>船名号："
				+ DateIsNull(fixedTermReportlist[i].shipName) + "&nbsp;</td>"));
		newTr.append($("<td width='180px'>航线："
				+ DateIsNull(fixedTermReportlist[i].startingPort) + "--"
				+ DateIsNull(fixedTermReportlist[i].arrivalPort)
				+ "&nbsp;</td>"));
		newTr.append($("<td  width='250px' >时间："
				+ dateT(fixedTermReportlist[i].startTime) + "--"
				+ dateT(fixedTermReportlist[i].endTime) + "&nbsp;</td>"));
		if (diffDate(fixedTermReportlist[i].endTime) == "进行中") {
			newTr.append($("<td  width='80px' id='state_" + i
					+ "' class='ingState'>"
					+ diffDate(fixedTermReportlist[i].endTime)
					+ "&nbsp;</td>"));
		} else {
			newTr.append($("<td  width='80px' id='state_" + i
					+ "' class='overState'>"
					+ diffDate(fixedTermReportlist[i].endTime)
					+ "&nbsp;</td>"));
		}

		newTable.append(newTr);
		newDiv.append(newTable);
		$("#showFixedTermReport").append(newDiv);

	}
	$(".litigantTable tr:even").css("background-color", "#f6f6f6");
	$(".litigantTable tr:odd").css("background-color", "#fff");
}
function jogefixedTermCheckBox(id) {
	var a = $("#" + id).attr("checked");
	$(".fixedTermCheckBox").attr("checked", false);
	$("#" + id).attr("checked", a);
}
function diffDate(end) {
	
	if (end == null) {
		return "已结束";
	}
	
	var dB = new Date(end.replace(/-/g, "/"));
	if (new Date() > Date.parse(dB)) {
		return "已结束";
	}

	return "进行中";

}
/**
 * 删除
 * 
 * @param
 */
function deleteInfo() {
	var fixedTemId="";
		$(".fixedTermCheckBox:checked").each(function(){
			fixedTemId=this.value;
			});
		if(fixedTemId==""){
			alert("请选择要删除的数据");
			return;
		}
	if (confirm("*确定删除此记录？")) {
		$
				.ajax({
					url : 'deleteFixedTermReportInfo',
					type : "post",
					dataType : "json",
					data : {
						'fixedTermID' : fixedTemId
					},
					success : function(data) {
						if (data.withoutPermission != undefined) {
							alert(data.withoutPermission);
						} else {
							deleteSuccessTitle();
							showJsonFixedTermReport("findFixedTermReportInfo",1);
						}
					},
					error : function(XMLHttpRequest) {
						alert($("#errorThrowInfo", XMLHttpRequest.responseText)
								.text());
					}
				});
	}
}

function updateFixedTermReport(url){
	var fixedTemId="";
		$(".fixedTermCheckBox:checked").each(function(){
			fixedTemId=this.value;
			});
		if(fixedTemId==""){
			alert("请选择要修改的数据");
			return;
		}
		location.href=url+"?fixedTermID="+fixedTemId;
}
//删除成功后提示信息
function deleteSuccessTitle(){
	$("#divInfobox").text("删除成功！");
	$("#divInfobox").show();
	setTimeout("codefans()",500);//2秒
}
function findFixedTermDataById(fixedTermID, id,state) {
	$(".overState").css("background","url('image/electric/to_left.png') no-repeat 75 center");
	$(".ingState").css("background","url('image/electric/to_left.png') no-repeat 75 center");
	
	if($("#fixedTermDataItem_table_" + id).attr("class")){
		$("#fixedTermDataItem_table_" + id).remove();
	$("#fixedTermDataItem_table_" + id).empty();
		return;
	}
	$("#"+state).css("background","url('image/electric/to_bottom.png') no-repeat 75 center");
	$.ajax( {
		url : 'findRegularVisaById',
		type : "post",
		dataType : "json",
		data : {
			'fixedTermID' : fixedTermID
		},

		beforeSend : function() {
			$(".loadingData").show();// 获取数据之前显示loading图标。
	},
	success : function(data) {
		appendFixedTermDataById(data.list, id)
	},
	complete : function() {
		$(".loadingData").hide();// 请求执行完后隐藏loading图标。
	}
	});
}
function appendFixedTermDataById(list, id) {
	$(".fixedTermDataItem_table").remove();
	$(".fixedTermDataItem_table").empty();
	var newTable = $("<table id='fixedTermDataItem_table_" + id
			+ "' class='fixedTermDataItem_table'></table>");
	var newTr = $("<tr class='addTrItem'></tr>");
	newTr.append($("<th width='130px'>载货种类</th>"));
	newTr.append($("<th width='80px'>货物数量</th>"));
	newTr.append($("<th width='80px'>进出港标记</th>"));
	newTr.append($("<th width='130px'>进出港时间</th>"));
	newTr.append($("<th width='130px'>报告时间</th>"));
	newTr.append($("<th width='130px'>上报人</th>"));
	newTable.append(newTr);
	for ( var i = 0; i < list.length; i++) {
		newTr = $("<tr class='addTrItem'></tr>");
		newTr
				.append($("<td>" + DateIsNull(list[i].cargoTypes)
						+ "&nbsp;</td>"));
		newTr.append($("<td>" + DateIsNull(list[i].carrying)+DateIsNull(list[i].uniti) + "&nbsp;</td>"));
		newTr.append($("<td >" + DateIsNull(list[i].mark) + "&nbsp;</td>"));
		newTr.append($("<td>" + DateIsNull(list[i].ingTime) + "&nbsp;</td>"));
		newTr
				.append($("<td>" + dateTT(list[i].reportTime)
						+ "&nbsp;</td>"));
		newTr.append($("<td>" + DateIsNull(list[i].regularvisaUserName)
						+ "&nbsp;</td>"));
		newTable.append(newTr);

	}
	$("#" + id).append(newTable);
}
function dateTT(value){
	if(value==null){
		return "";
	}
	return value.replace("T"," ");
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
}
function dateT(value) {
	var returnValue = "";
	if (value == null) {
		return returnValue;
	} else {
		returnValue = value.substring(0, 10);
		return returnValue;
	}
}
/***
 * 按条件查询
 * @param {Object} actionName
 * @param {Object} selectedPage
 */
function getConditionFixedTermReportInfo(actionName, selectedPage) {
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
			'queryCondition.listKeyValuePair[0].key' : 'f.shipName',
			'queryCondition.listKeyValuePair[0].pair' : '%%',
			'queryCondition.listKeyValuePair[0].value' : $("#conditionId")
					.val(),
			'queryCondition.listKeyValuePair[0].connector' : 'or',
			'queryCondition.orderByFielName' : 'f.shipName',
			'queryCondition.sequence' : 'asc',
			'cpage' : selectedPage
		},
		beforeSend : function() {
			$(".loadingData").show();// 获取数据之前显示loading图标。
	},
	success : function(data) {
		//cleanNullTitile();
		deleteTr();
		var gotoPageMethodName = "gotoConditionPageNo";
		if (data.list == null) {
			printPage(0, selectedPage, 'getJsonFixedTermReport', actionName,
						gotoPageMethodName, 0);
			DataIsNullTitile();
		} else {
			var listLength = data.list.length;
			var list = data.list;
			appendToTableInfo(list, listLength);// 跳转页码的方法名称		
		//printPage(data.totalPage, selectedPage, 'getJsonFixedTermReport',
		//		actionName, gotoPageMethodName,data.allTotal);
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
	$(".addDiv").remove();
	$(".addDiv").empty();
	$(".fixedTermDataItem_table").remove();
	$(".fixedTermDataItem_table").empty();
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

