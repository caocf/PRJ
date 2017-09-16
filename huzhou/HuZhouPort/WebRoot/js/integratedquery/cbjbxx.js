// 显示船舶信息
function showBaseInfo() {
	var cbname = $("#cbname").val();
	if (cbname == "") {
		alert("船名不能为空！");
		return;
	}
	if (/[-~#^$@%&!*\s*]/.test(cbname)) {
		alert("船名不能包含特殊字符！");
		return;
	}
	$.ajax( {
		url : 'GetAndPostDate',
		type : "post",
		dataType : "json",
		data : {
			'cbname' : cbname,
			'method' : 1
		},
		success : function(data) {
			if(data.list!=null){
				CBInfor(data);// 显示船基本档案
			}else{
				ShowWarn("");
			}
			
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
	newTable.append("<tr><th colspan='4'>船舶基本信息</th></tr>");
	if(warnString=="")
		newTable.append("<tr><td colspan='4' class='warn'>船名不正确或没有基本信息</td></tr>");
	else
		newTable.append("<tr><td colspan='4' class='warn'>"+warnString+"</td></tr>");
}
// 显示船基本档案
function CBInfor(data) {
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	newTable.append("<tr><th colspan='4'>船舶基本档案</th></tr>");
	if(data.list[0].CBLX==undefined){
		newTable.append("<tr><td colspan='4' class='warn'>船名不正确或暂无信息</td></tr>");
	}
	else{
	newTable.append("<tr><td>船舶名称：</td><td>" + data.list[0].CBLX + "</td>"
			+ "<td>船籍港（代码）：</td><td>" + data.list[0].CJG + "（"+data.list[0].CJGDM+"）</td></tr>");
	newTable.append("<tr><td>船检登记号：</td><td>" + data.list[0].CJDJH + "</td>"
			+ "<td>船舶登记号：</td><td>" + data.list[0].CBDJH + "</td></tr>");
	newTable.append("<tr><td>船舶类型：</td><td>" + data.list[0].CBLX + "</td>"
			+ "<td>船舶类型代码：</td><td>" + data.list[0].CBLXDM + "</td></tr>");
	newTable.append("<tr><td>主机总功率：</td><td>" + data.list[0].ZJZGL + "</td>" +
					"<td>参考载货量</td><td>" + data.list[0].CKZHL + "</td></tr>");
	newTable.append("<tr><td>总吨位：</td><td>" + data.list[0].ZDW + "</td>"
			+ "<td>净吨位：</td><td>" + data.list[0].JDW + "</td></tr>");
	newTable.append("<tr><td>吃水空载（m）：</td><td>" + data.list[0].CSKZ + "</td>"
			+ "<td>吃水满载：</td><td>" + data.list[0].CSMZ + "</td></tr>");
	newTable.append("<tr><td>总长（m）：</td><td>" + data.list[0].ZC + "</td>"
			+ "<td>船长（m）：</td><td>" + data.list[0].CC + "</td></tr>");	
	newTable.append("<tr><td>型宽（m）：</td><td>" + data.list[0].XK + "</td>"
			+ "<td>型深（m）：</td><td>" + data.list[0].XS + "</td></tr>");
	newTable.append("<tr><td>量吨甲板长（m）：</td><td>" + data.list[0].LDJBC+ "</td>"
			+ "</td><td>船舶经营人：</td><td>" + data.list[0].JYR+ "</td></tr>");
	if(data.list[0].SYRDH==undefined){
		newTable.append("<tr><td>船舶所有人：</td><td>" + data.list[0].SYR + "</td>"
				+ "<td>所有人电话：</td><td>无</td></tr>");
	}
	
	else{
		newTable.append("<tr><td>船舶所有人：</td><td>" + data.list[0].SYR + "</td>"
				+ "<td>所有人电话：</td><td>" + data.list[0].SYRDH+"</td></tr>");
	}
	}
}
