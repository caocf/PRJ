var startDate;//出发时间
$$(document).ready(function() {
	ShowDataBySearch('GetAgent', 1)
	
});
function ShowDataBySearch(actionName, selectedPage) {
	$$(".layout6").css("visibility","");
	ShowDataByPage(actionName, selectedPage);

}
function ShowDataByPage(actionName, selectedPage) {
	
	$$(".addTr").empty();
	$$(".addTr").remove();
	$$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'agentName':$$("#agentName").val(),
			'num':PAGESIZE,
			'page' : selectedPage
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success : function(data) {
			if (data.agents.length == 0) {
				$$("#alltotal").text("0");
				TableIsNull();
			} else {
				var list = data.agents;
				appendToTable(list,list.length);// 跳转页码的方法名称
				gotoPageMethodName = "gotoPageNo";
				var number=CountItemTrByPageNum(list.length,PAGESIZE);
				printPage(number, selectedPage, 'ShowDataByPage',
						actionName, gotoPageMethodName);
			}
		},
		complete : function() {
			CloseLoadingDiv();// 请求执行完后隐藏loading图标。
	}
	});

}
function TableIsNull(){
	newTr = $$("<tr class='addTr'></tr>");
	newTr.append($$("<td colspan='7'><label class='tishi'>"+NODATA+"</label></td>"));
	$$(".listTable2").append(newTr);
}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $$(".indexCommon").attr("value");
	var str = $$.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$$(".indexCommon").val("");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		ShowDataByPage(actionName, pageNo);
	}
}
function appendToTable(list, listLength){
	for ( var i = 0; i < listLength; i++) {
		newTr = $$("<tr class='addTr'></tr>");
		newTr.append($$("<td>&nbsp;</td>"));
		newTr.append($$("<td class='td_first'>"+DateIsNull(list[i].agentName,'--')+"</td>"));
		newTr.append($$("<td>"+DateIsNull(list[i].agentPhone,'--')+"</td>"));
		newTr.append($$("<td>"+DateIsNull(list[i].agentIntroduce,'--')+"</td>"));
		newTr.append($$("<td>"+DateIsNull(list[i].agentAddress,'--')+"</td>"));
		$$(".listTable2").append(newTr);
	}
}
