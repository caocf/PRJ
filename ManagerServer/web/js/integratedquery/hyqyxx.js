// 查询航运企业信息
function showBaseInfo() {
	var cbname = $("#cbname").val();
	if (cbname == "") {
		alert("企业名称不能为空！");
		return;
	}
	if (/[-~#^$@%&!*\s*]/.test(cbname)) {
		alert("企业名称不能包含特殊字符！");
		return;
	}
	$.ajax( {
		url : 'GetShipingCompanies',
		type : "post",
		dataType : "json",
		data : {
			'cbname' : cbname
		},
		success : function(data) {
			CBInfor(data);
	},
	error : function(XMLHttpRequest) {
		ShowWarn($(".error", XMLHttpRequest.responseText).text());
	}
	});
}
function ShowWarn(warnString){
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	newTable.append("<tr><th colspan='4'>航运企业</th></tr>");
	if(warnString=="")
		newTable.append("<tr><td colspan='4' class='warn'>航运企业不正确或没有违章信息</td></tr>");
	else
		newTable.append("<tr><td colspan='4' class='warn'>"+warnString+"</td></tr>");
}
//显示航运企业信息
function CBInfor(data) {
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	newTable.append("<tr><th colspan='4'>航运企业基本档案</th></tr>");
	if(data.list[0].QYMC==undefined){
		newTable.append("<tr><td colspan='4' class='warn'>企业名称不正确或暂无信息</td></tr>");
	}
	else{
	newTable.append("<tr><td>企业名称：</td><td>" + data.list[0].QYMC + "</td>"
			+ "<td>企业地址：</td><td>" + DateIsNull(data.list[0].QYDZ) +"</td></tr>");
	newTable.append("<tr><td>法人代表：</td><td>" + DateIsNull(data.list[0].FRDB) + "</td>"
			+ "<td>电话号码：</td><td>" + DateIsNull(data.list[0].DHHM) + "</td></tr>");
	newTable.append("<tr><td>行业分类：</td><td>" + DateIsNull(data.list[0].HYFL) + "</td>"
			+ "<td>主营客运航区：</td><td>" + DateIsNull(data.list[0].ZYKYHQ) + "</td></tr>");
	newTable.append("<tr><td>主营货运航区：</td><td>" + DateIsNull(data.list[0].ZYHYHQ) + "</td>" +
					"<td>主营客运范围：</td><td>" + DateIsNull(data.list[0].ZYKYFW) + "</td></tr>");
	newTable.append("<tr><td>主营货运范围：</td><td>" + DateIsNull(data.list[0].ZYHYFW) + "</td>" +
			"<td>兼营范围：</td><td>" + DateIsNull(data.list[0].JYFW) + "</td></tr>");
	newTable.append("<tr><td>批准日期：</td><td  colspan='3'>" + ShortTimeByformateDay(data.list[0].PZRQ) + "</td></tr>");
	
	}
}
