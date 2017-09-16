$(document).ready(function() {
	showJsonChannel("showChannelInfo");//进入界面调用此方法
	});
// 查询全部记录
function showJsonChannel(actionName) {
	getJsonChannel(actionName, 1);
}
/**
 * 无条件查询用户信息
 * 
 * @param actionName:执行获取的action名称
 * @param selectedPage:当前选中页码
 */
function getJsonChannel(actionName, selectedPage) {
	/*deleteTr();*/
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.orderByFielName' : "c.channelId",
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
					printPage(data.totalPage, selectedPage, 'getJsonChannel',
							actionName, gotoPageMethodName, data.allTotal);
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
		getJsonChannel(actionName, pageNo);
	}
}
function appendToTable(list, listLength) {

	for ( var i = 0; i < listLength; i++) {
		newTr = $("<tr class='addTr'></tr>");
		//newTr.append($("<td>" + DateIsNull(Channellist[i].reportID) + "&nbsp;</td>"));
		newTr.append($("<td >" + DateIsNull(list[i].channelName)
				+ "&nbsp;</td>"));
		newTr.append($("<td >" + DateIsNull(list[i].channelCoding)
				+ "&nbsp;</td>"));
		newTr
				.append($("<td >" + DateIsNull(list[i].riverName)
						+ "&nbsp;</td>"));
		newTr
				.append($("<td >" + DateIsNull(list[i].riversCode)
						+ "&nbsp;</td>"));
		newTr.append($("<td >" + DateIsNull(list[i].startingName)
				+ "&nbsp;</td>"));
		newTr
				.append($("<td >" + DateIsNull(list[i].endingName)
						+ "&nbsp;</td>"));
		newTr.append($("<td><a onclick=findChannelById('" + list[i].channelId
				+ "') class='operation'>查看</a></td>"));
		$("#channelTable").append(newTr);
	}
	$(".litigantTable tr:even").css("background-color", "#f6f6f6");
	$(".litigantTable tr:odd").css("background-color", "#fff");
}
function findChannelById(channelId) {
	window.location.href = $("#basePath").val()
			+ "page/electric/channelListShow.jsp?" + "&channelId=" + channelId;
}
function DateIsNull(value) {
	returnValue = "暂未命名";
	if (value == null || value == "null" || value == "") {

		return returnValue;
	} else {
		return value;
	}
}

function dateT(value){
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
function getConditionChannelInfo(actionName, selectedPage) {
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
			'queryCondition.listKeyValuePair[0].key' : 'c.riverName',
			'queryCondition.listKeyValuePair[0].pair' : '%%',
			'queryCondition.listKeyValuePair[0].value' : $("#conditionId")
					.val(),
			'queryCondition.listKeyValuePair[0].connector' : 'or',
			'queryCondition.listKeyValuePair[1].key' : 'c.channelName',
			'queryCondition.listKeyValuePair[1].pair' : '%%',
			'queryCondition.listKeyValuePair[1].value' : $("#conditionId")
					.val(),
			'queryCondition.listKeyValuePair[1].connector' : 'or',
			'queryCondition.listKeyValuePair[2].key' : 'c.startingName',
			'queryCondition.listKeyValuePair[2].pair' : '%%',
			'queryCondition.listKeyValuePair[2].value' : $("#conditionId")
					.val(),
			'queryCondition.listKeyValuePair[2].connector' : 'or',
			'queryCondition.listKeyValuePair[3].key' : 'c.endingName',
			'queryCondition.listKeyValuePair[3].pair' : '%%',
			'queryCondition.listKeyValuePair[3].value' : $("#conditionId")
					.val(),
			'queryCondition.listKeyValuePair[3].connector' : 'or',
			'queryCondition.orderByFielName' : 'c.riverName',
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
			printPage(0, selectedPage, 'getConditionChannelInfo', actionName,
						gotoPageMethodName, 0);
			DataIsNullTitile();
		} else {
			var listLength = data.list.length;
			var list = data.list;
			appendToTable(list, listLength);// 跳转页码的方法名称		
		printPage(data.totalPage, selectedPage, 'getConditionChannelInfo',
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

function joinFileNull(){
	if($("#fileExleText").val()==null||$("#fileExleText").val()==""){
		alert("请添加导入文件");
		return false;
	}else{
		return true;
	}
}