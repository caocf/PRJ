var SearchName;
var dateList;
// 显示船舶信息
function showBaseInfo(actionName,selectedPage) {
	var cbname = $("#cbname").val();
	if (cbname == "") {
		alert("船名不能为空！");
		return;
	}
	if (/[-~#^$@%&!*\s*]/.test(cbname)) {
		alert("船名不能包含特殊字符！");
		return;
	}
	SearchName=cbname;
	showCBJYXXInfo(actionName,selectedPage);
}
//显示船舶信息
function showCBJYXXInfo(actionName,selectedPage) {
	
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'cbname' : SearchName,
			'method' : 4,
			'scape':selectedPage
		},
		success : function(data) {
			CBJYInfor(data);
			gotoPageMethodName = "gotoPageNo";
			printPage(data.totalPage, selectedPage, 'showCBJYXXInfo',
					actionName, gotoPageMethodName, data.allTotal);
	},
	error : function(XMLHttpRequest) {
		ShowWarn($(".error", XMLHttpRequest.responseText).text());
	}
	});

}
function gotoPageNo(actionName, totalPage) {
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
		showCBJYXXInfo(actionName, pageNo);
	}
}
//弹出框初始化
function showInfor(num) {
	$(".JYBH").text(dateList[num].JYBH);
	$(".ZWCM").text(dateList[num].ZWCM);
	$(".JYDD").text(dateList[num].JYDD);
	$(".JYDWMC").text(dateList[num].JYDWMC);
	$(".JYBM").text(dateList[num].JYBM);
	$(".SQR").text(dateList[num].SQR);
	$(".JYKSRQ").text(ShortTimeByformateDay(dateList[num].JYKSRQ));
	$(".JYKSRQ").text(ShortTimeByformateDay(dateList[num].JYKSRQ));
	$(".JYWCRQ").text(ShortTimeByformateDay(dateList[num].JYWCRQ));
	$(".QTJY").text(dateList[num].QTJY);
	$(".SFWC").text(dateList[num].SFWC);
	$(".XCJYRQ").text(ShortTimeByformateDay(dateList[num].XCJYRQ));
	$(".BZ").text(dateList[num].BZ);
	$(".CJDJH").text(dateList[num].CJDJH);
	
	$("#dialog_div").show();
	$("#dialog_div").dialog( {
		title : null,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false
	});
}
function CloseDialog(){
	$("#dialog_div").dialog("close");
}
function ShowWarn(warnString){
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	newTable.append("<tr><th colspan='8'>船舶检验信息</th></tr>");
	if(warnString=="")
		newTable.append("<tr><td colspan='8' class='warn'>船名不正确或没有检验信息</td></tr>");
	else
		newTable.append("<tr><td colspan='8' class='warn'>"+warnString+"</td></tr>");
}
// 显示船基本档案
function CBJYInfor(data) {
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	dateList=data.list;
	newTable.append("<tr><th colspan='8'>船舶检验信息</th></tr>");
	if (data.list[0].ZWCM == "" ) {
		newTable
				.append("<tr><td colspan='8' class='warn'>船舶名称错误或无检验信息</td></tr>");
	} else {
		newTable
		.append("<tr><td>检验编号</td><td>船检登记号</td><td>中文船名</td><td>检验地点</td><td>申请人</td><td>检验开始日期</td><td>是否完成</td><td>操作</td></tr>");
		for ( var i = 0; i < data.list.length; i++) {
			newTable.append("<tr><td>" + DateIsNull(data.list[i].JYBH)+ "</td><td>" + DateIsNull(data.list[i].CJDJH)
							+ "</td><td>" + DateIsNull(data.list[i].ZWCM)+ "</td><td>" + DateIsNull(data.list[i].JYDD)+ "</td>" +
							"<td>" + DateIsNull(data.list[i].SQR)+ "</td><td>" +  ShortTimeByformateDay(data.list[i].JYKSRQ)+ "</td>" +
							"<td>" + DateIsNull(data.list[i].SFWC)+ "</td><td><a id='JYBH'"+data.list[i].JYBH+" class='operation' " +
								"onclick=showInfor("+i+")>查看</a></td></tr>");
		}

	}
}
