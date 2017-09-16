var IsSinIn = 0;//签到是0；签退是1
$(document).ready(function() {
	changSinState(0)// 进入页面调用此方法		
	});

function changSinState(value) {
	$("#layer4_left a").css("color","#77797e");
	$("#signInfo_"+value).css("color","#0260a6");
	IsSinIn = value;
	showJsonSigns("showSignInfo");// 进入页面调用此方法		
}

// 查询全部记录
function showJsonSigns(actionName) {
	getConditionSignsInfo(actionName, 1);
}

/**
 * 清除表格中的所有数据
 */
function deleteTr() {
	$(".addTr").remove();
	$(".addTr").empty();

}
/**
 * 把系统用户数据添加到表格中
 * 
 * @param {Object}
 *            userLength
 * @param {Object}
 *            commonDataList
 */

function appendToTable(signlist, listLength) {
	deleteTr();
	for ( var i = 0; i < listLength; i++) {
		newTr = $("<tr class='addTr'></tr>");

		newTr.append($("<td >" + DateIsNull(signlist[i][1].userName) + "</td>"));
		newTr.append($("<td >" + dateT(signlist[i][0].signTime) + "</td>"));
		newTr.append($("<td >" + DateIsNull(signlist[i][2].locationName) + "</td>"));
		newTr.append($("<td >" + isSignIn(signlist[i][0].signStatus) + "</td>"));
//		if(signlist[i][0].IsSinIn==0){
			newTr.append($("<td><a onclick=findSignById('"+signlist[i][0].signLocation + "','" + signlist[i][0].signID+"','"+signlist[i][0].signStatus+"') class='operation'>查看</a></td>"));
			
//		}else {
//			newTr.append($("<td><a onclick='findSignById("+signlist[i][0].signLocation + "','" + signlist[i][0].signID+",'"+signlist[i][0].IsSinIn+"') class='operation'>查看</a></td>"));
//		}
		$("#signTable").append(newTr);
	}
//	$(".listTable tr:even").css("background-color", "#f6f6f6");
//	$(".listTable tr:odd").css("background-color", "#fff");
}

function findSignById(signLocation,signID,signStatus) {
	if(signStatus==0){
	window.location.href = $("#basePath").val()
			+ "page/attendace/signSee.jsp?signLocation=" + signLocation
			+ "&signID=" + signID;
	}else{
		window.location.href = $("#basePath").val()
			+ "page/attendace/signSeeBack.jsp?signLocation=" + signLocation
			+ "&signID=" + signID;
	}
}
function dateT(value) {
	if (value == null) {
		return "";
	}
	return value.replace("T", " ");
}

function isSignIn(value){
	if(value==1){
		return '签退';
	}
	return '签到';
}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").attr("value");
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		getJsonSigns(actionName, pageNo);
	}
}

/***
 * 按条件查询
 * @param {Object} actionName
 * @param {Object} selectedPage
 */
function getConditionSignsInfo(actionName, selectedPage) {
	if(IsSinIn==1){
		$("#pmark").text("签退人");
		$("#tmark").text("签退时间");
		$("#lmark").text("签退地点");
	}else{
		$("#pmark").text("签到人");
		$("#tmark").text("签到时间");
		$("#lmark").text("签到地点");
	}
	var search = $("#conditionId").val();
	if (/[~#^$@%&!*\s*]/.test(search)) {
		alert("输入的查询内容不能包含特殊字符！");
		return;
	}
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
		    'signStatus':IsSinIn,
			'queryCondition.listKeyValuePair[0].key' : 'u.userName',
			'queryCondition.listKeyValuePair[0].pair' : '%%',
			'queryCondition.listKeyValuePair[0].value' : $("#conditionId")
					.val(),
					'queryCondition.listKeyValuePair[0].connector' : 'or',
			'queryCondition.listKeyValuePair[1].key' : 'date_s.signTime',
			'queryCondition.listKeyValuePair[1].pair' : '=',
			'queryCondition.listKeyValuePair[1].value' : $("#conditionId").val(),
			'queryCondition.listKeyValuePair[1].connector' : 'or',
			'queryCondition.listKeyValuePair[2].key' : 'l.locationName',
			'queryCondition.listKeyValuePair[2].pair' : '%%',
			'queryCondition.listKeyValuePair[2].value' : $("#conditionId")
					.val(),
					'queryCondition.listKeyValuePair[2].connector' : 'or',
			'queryCondition.listKeyValuePair[3].key' : 'l.locationName',
			'queryCondition.listKeyValuePair[3].pair' : '%%',
			'queryCondition.listKeyValuePair[3].value' : $("#conditionId")
					.val(),
			'queryCondition.listKeyValuePair[3].connector' : 'or',
			'queryCondition.orderByFielName' : 's.signTime',
			'queryCondition.sequence' : 'desc',
			'cpage' : selectedPage
		},
		beforeSend : function() {
			$(".loadingData").show();// 获取数据之前显示loading图标。
		},
		success : function(data) {
			var gotoPageMethodName = "gotoConditionPageNo";
			if (data.list == undefined||data.list==null||data.list=="") {
				$("#pageDiv").hide();
				deleteTr();
				DataIsNullTitile();
			} else {
				var listLength = data.list.length;
				var list = data.list;
				appendToTable(list, listLength);// 跳转页码的方法名称		

				printPage(data.totalPage, selectedPage,
						'getConditionSignsInfo', actionName,
						gotoPageMethodName, data.allTotal);
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
		getConditionSignsInfo(actionName, pageNo);
	}
}

function DateIsNull(value) {
	returnValue = "获取中...";
	if (value == null || value == "null") {

		return returnValue;
	} else {
		return value;
	}
}



/*  
 * 说明：设置传入的选项值到指定的下拉列表中   
 * @param {String || Object]} selectObj 目标下拉选框的名称或对象，必须  
 * @param {Array} optionList 选项值设置 格式：[{txt:'北京', val:'010'}, {txt:'上海', val:'020'}] ，必须  
 * @param {String} firstOption 第一个选项值，如：“请选择”，可选，值为空  
 * * @param {String} selected 默认选中值，可选  
 */
function setSelectOption(selectObj, optionList, optionList2, firstOption) {

	if (typeof selectObj != 'object') {
		selectObj = document.getElementById(selectObj);
	}
	// 清空选项     
	removeOptions(selectObj);
	// 选项计数     
	var start = 0;
	// 如果需要添加第一个选项     
	if (firstOption) {
		selectObj.options[0] = new Option(firstOption, '');
		// 选项计数从 1 开始        
		start++;
	}
	var len = optionList.length;

	for ( var i = 0; i < len; i++) {

		// 设置 option        
		selectObj.options[start] = new Option(optionList[i], optionList2[i]);
		// 选中项         

		// 计数加 1         
		start++;
	}
}
/*  
 * 说明：将指定下拉列表的选项值清空 
 * 作者：CodeBit.cn ( http://www.CodeBit.cn )  
 *  
 * @param {String || Object]} selectObj 目标下拉选框的名称或对象，必须  
 */
function removeOptions(selectObj) {

	if (typeof selectObj != 'object') {
		selectObj = document.getElementByName(selectObj);
	}
	// 原有选项计数     
	var len = selectObj.options.length;
	for ( var i = 0; i < len; i++) {

		// 移除当前选项         
		selectObj.options[0] = null;
	}
}
