$$(document).ready(function() {
	$$(".listTable").css("display","none");
	SelectOption("layout1_select01",105);
	SelectOption("layout1_select02",190);
	SelectOption("layout1_select03",85);
});

function CheckSearchContent(){
	var ret = true;
	ret = emptyChk("#CarNumber", "#loginerr", "营运车号不能为空.");
	if (ret == true) {
		ret = valueLength("#CarNumber", "#loginerr", "3", "50", "营运车号是3-50位");
	}
	if (ret == true) {
		ret = emptyChk("#EngineNumber", "#loginerr", "营运证号不能为空.");
	}
	if (ret == true) {
		ret = valueLength("#EngineNumber", "#loginerr", "3", "50", "营运证号是3-50位");
	}
	if (ret == true) {
		ret = emptyChk("#FrameNumber", "#loginerr", "营运公司不能为空.");
	}
	if (ret == true) {
		ret = valueLength("#FrameNumber", "#loginerr", "3", "50", "营运公司是3-50位");
	}
	return ret;
}

function ShowIllegalByPage(actionName, selectedPage) {
	$$(".addTr").empty();
	$$(".addTr").remove();
	if(!CheckSearchContent()){
		alert($$("#loginerr").text());
		return;
	}
	$$(".listTable").css("display","");
	$$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'page' : selectedPage,
			'carID':1,
			'carID':$$("#layout1_select03 .abc_CRtext").val()+$$("#CarNumber").val(),
			'engineID':$$("#EngineNumber").val(),
			'frameID':$$("#FrameNumber").val()
		},
		beforeSend : function() {
			//ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success : function(data) {
			if (data.records.length == 0) {
				TableIsNull();
			} else {
				var list = data.records;
				appendToTable(list,list.length);// 跳转页码的方法名称
				gotoPageMethodName = "gotoPageNo";
				printPage(list.length/10+1, selectedPage, 'ShowIllegalByPage',
						actionName, gotoPageMethodName);
			}
		},
		complete : function() {
			//CloseLoadingDiv();// 请求执行完后隐藏loading图标。
	}
	});

}
function TableIsNull(){
	newTr = $$("<tr class='addTr'></tr>");
	newTr.append($$("<td colspan='7'><label class='tishi'>"+NODATA+"</label></td>"));
	$$(".listTable").append(newTr);
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
		ShowIllegalByPage(actionName, pageNo);
	}
}
function appendToTable(list, listLength){
	$$(".addTr").empty();
	$$(".addTr").remove();
	for ( var i = 0; i < listLength; i++) {
		newTr = $$("<tr class='addTr'></tr>");
		newTr.append($$("<td>"+list[i].carID+"</td>"));
		newTr.append($$("<td>"+list[i].carType+"</td>"));
		newTr.append($$("<td>"+HourTimeFormat(list[i].date)+"</td>"));
		newTr.append($$("<td>"+list[i].addr+"</td>"));
		newTr.append($$("<td>"+list[i].content+"</td>"));
		newTr.append($$("<td>"+list[i].cutPoint+"</td>"));
		newTr.append($$("<td>"+list[i].cutMoney+"</td>"));
		$$(".listTable").append(newTr);
	}
}

function IdCard(id){
	if($$("#"+id).css("display")=="none"){
		$$("#"+id).css("display","");
	}else{
		$$("#"+id).css("display","none");
	}
	
}