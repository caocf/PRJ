var SearchName;
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
	showCBCZXXInfo(actionName,selectedPage);

}
//显示船舶信息
function showCBCZXXInfo(actionName,selectedPage) {
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'cbname' : SearchName,
			'method' : 2,
			'scape':selectedPage
		},
		success : function(data) {
			CBZSInfor(data);
			gotoPageMethodName = "gotoPageNo";
			printPage(data.totalPage, selectedPage, 'showCBCZXXInfo',
					actionName, gotoPageMethodName, data.allTotal);
	},
	error : function(XMLHttpRequest) {
		ShowWarn($(".error", XMLHttpRequest.responseText).text());
		//alert($(".error", XMLHttpRequest.responseText).text());
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
		showCBCZXXInfo(actionName, pageNo);
	}
}
function ShowWarn(warnString){
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	newTable.append("<tr><th colspan='5'>船舶证书信息</th></tr>");
	if(warnString=="")
		newTable.append("<tr><td colspan='5' class='warn'>船名不正确或没有证书信息</td></tr>");
	else
		newTable.append("<tr><td colspan='5' class='warn'>"+warnString+"</td></tr>");
}
// 显示证书信息
function CBZSInfor(data) {
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	newTable.append("<tr><th colspan='5'>船舶证书信息</th></tr>");
	if(data.list.length==0){
		newTable.append("<tr><td colspan='5' class='warn'>船名不正确或没有证书信息</td></tr>");
	}
	else{

	newTable
			.append("<tr><td>证书编号</td><td>证书名称</td><td>发证日期</td><td>有效期</td><td>状态</td></tr>");
	for ( var i = 0; i < data.list.length; i++)
			if(data.list[i].outtime==2){
				newTable.append("<tr><td>" + DateIsNull(data.list[i].ZSBH) + "</td><td>"
						+ DateIsNull(data.list[i].ZSMC) + "</td><td>" + ShortTimeByformate(data.list[i].FZRQ)
						+ "</td><td>" + ShortTimeByformateDay(data.list[i].YXRQ) + "</td><td>过期</td></tr>");
			}else if(data.list[i].outtime==1){
				newTable.append("<tr><td>" + DateIsNull(data.list[i].ZSBH) + "</td><td>"
						+ DateIsNull(data.list[i].ZSMC) + "</td><td>" + ShortTimeByformate(data.list[i].FZRQ)
						+ "</td><td>" + ShortTimeByformateDay(data.list[i].YXRQ) + "</td><td>有效</td></tr>");
			}
		
	}
}
