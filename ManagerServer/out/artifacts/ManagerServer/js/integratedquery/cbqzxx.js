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
	showCBQZXXInfo(actionName,selectedPage);

}
//显示船舶信息
function showCBQZXXInfo(actionName,selectedPage) {
	$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'cbname' : SearchName,
			'method' : 6,
			'scape':selectedPage
		},
		success : function(data) {
			CBZSInfor(data);
			gotoPageMethodName = "gotoPageNo";
			printPage(data.totalPage, selectedPage, 'showCBQZXXInfo',
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
		showCBQZXXInfo(actionName, pageNo);
	}
}
function ShowWarn(warnString){
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	newTable.append("<tr><th colspan='4'>船舶签证信息</th></tr>");
	if(warnString=="")
		newTable.append("<tr><td colspan='4' class='warn'>船名不正确或没有签证信息</td></tr>");
	else
		newTable.append("<tr><td colspan='4' class='warn'>"+warnString+"</td></tr>");
}
// 显示证书信息
function CBZSInfor(data) {
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	if(data.list.length==0){
		newTable.append("<tr><th colspan='4'>船舶签证信息</th></tr>");
		newTable.append("<tr><td colspan='4' class='warn'>船名不正确或没有签证信息</td></tr>");
	}
	for ( var i = 0; i < data.list.length; i++)
	{
		newTable.append("<tr><th colspan='4'>"+ data.list[i].QZH + "</th></tr>");
		newTable.append("<tr><td>序号：</td><td>"+ data.list[i].ID + "</td>" +
				"<td>中文船名：</td><td>"+ data.list[i].ZWCM + "</td></tr>");
		newTable.append("<tr><td>签证时间：</td><td>"+ ShortTimeByformate(data.list[i].QZSJ) + "</td>" +
				"<td>签证地点：</td><td>"+ data.list[i].QZDD + "</td></tr>");
		newTable.append("<tr><td>进出港时间：</td><td>"+ ShortTimeByformate(data.list[i].JCGSJ) + "</td>" +
				"<td>始发港名称：</td><td>"+ data.list[i].SFGMC + "</td></tr>");
		newTable.append("<tr><td>目的港名称：</td><td>"+ data.list[i].MDGMC + "</td>" +
				"<td>签证类型名称：</td><td>"+ data.list[i].QZLXMC+ "</td></tr>");
		newTable.append("<tr><td>签证号：</td><td>"+ data.list[i].QZH + "</td>" +
				"<td>载客人数：</td><td>"+ data.list[i].ZKRS + "</td></tr>");
		newTable.append("<tr><td>载货量：</td><td>"+ data.list[i].ZHL + "</td>" +
				"<td>船员人数：</td><td>"+ data.list[i].CYRS + "</td></tr>");
		newTable.append("<tr><td>签证人员：</td><td>"+ data.list[i].QZRY + "</td>" +
				"<td>记录时间：</td><td>"+ ShortTimeByformate(data.list[i].JLSJ) + "</td></tr>");
		if( data.list[i].JCGLX=="0"){
			newTable.append("<tr><td>进出港类型：</td><td colspan='3'>出港</td></tr>");
		}else{
			newTable.append("<tr><td>进出港类型：</td><td colspan='3'>进港</td></tr>");	
		}
	
		
	}
}
