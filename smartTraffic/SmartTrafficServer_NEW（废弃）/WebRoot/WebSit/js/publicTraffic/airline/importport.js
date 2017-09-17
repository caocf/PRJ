$$(document).ready(function() {
	ShowDataByPage("GetRealTimeAir",1);
	SelectOption("layout1_select01",105);
	SelectOption("layout1_select02",140);
});
function ShowDataByPage(actionName, selectedPage) {
	$$(".addTr").empty();
	$$(".addTr").remove();
	$$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'status':$$("#layout1_select01 .abc").val(),
			'page' : selectedPage,
			'airportName':$$("#airportName").val()
			
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success : function(data) {
			if (data.airRealTimeSchedules == null) {
				TableIsNull();
			} else {
				var list = data.airRealTimeSchedules;
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
		newTr.append($$("<td class='td_first'>"+DateIsNull(list[i].airNumber,'--')+"</td>"));
		newTr.append($$("<td>"+DateIsNull(list[i].company,'--')+"</td>"));
		newTr.append($$("<td>"+DateIsNull(list[i].startCity,'--')+"</td>"));
		newTr.append($$("<td>"+DateIsNull(list[i].endCity,'--')+"</td>"));
		newTr.append($$("<td>"+HourTimeFormat(list[i].reachTime)+"</td>"));
		newTr.append($$("<td>"+HourTimeFormat(list[i].leaveTime)+"</td>"));
		newTr.append($$("<td>"+HourTimeFormat(list[i].realLeaveTime)+"</td>"));
		newTr.append($$("<td>"+HourTimeFormat(list[i].realReachTime)+"</td>"));
		if(list[i].status==0){
			newTr.append($$("<td><label class='label_grey'>待发</label></td>"));
		}else if(list[i].status==1){
			newTr.append($$("<td><label class='td_achieve'>起飞</label></td>"));
		}else if(list[i].status==2){
			newTr.append($$("<td><label class='td_delay'>到达</label></td>"));
		}else if(list[i].status==3){
			newTr.append($$("<td><label class='td_takeoff'>延误</label></td>"));
		}else{
			newTr.append($$("<td>--</td>"));
		}
		
		$$(".listTable2").append(newTr);
	}
}