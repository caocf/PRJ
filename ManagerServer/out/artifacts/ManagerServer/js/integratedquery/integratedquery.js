$(document).ready(function() {
	getMaxDate();
	getMinDate();
	$("#from").attr("value", getMinDate());
	$("#to").attr("value", getMaxDate());
});
// getMaxDate生成客户端本地时间
function getMaxDate() {
	var t = new Date();
	var maxDate = [ t.getFullYear(), t.getMonth() + 1, t.getDate() ].join('-');
	maxDate += ' ' + t.toLocaleTimeString();
	return maxDate;
}
// getMinDate生成客户端本地时间
function getMinDate() {
	var t = new Date();
	t.setMonth(t.getMonth() - 2);// 最小时间少2个月
	var maxDate = [ t.getFullYear(), t.getMonth() + 1, t.getDate() ].join('-');
	maxDate += ' ' + t.toLocaleTimeString();
	return maxDate;
}
// 显示船舶信息
function showBaseInfo() {
	if ($("#parameters_name").val() == "") {
		alert("船名不能为空！");
		return;
	}
	if (/[-~#^$@%&!*\s*]/.test($("#parameters_name").val())) {
		alert("船名不能包含特殊字符！");
		return;
	} else {
		if ($("#mentd").val() == "1") {
			ActionName = 'ShowCBinfo';
		}
		if ($("#mentd").val() == "2") {
			ActionName = 'ShowCBZH';
		}

		$.ajax( {
			url : ActionName,
			type : "post",
			dataType : "json",
			data : {
				"parameters.name" : $("#parameters_name").val()
			},
			success : function(data) {
				if (ActionName == 'ShowCBinfo')
					CBInfor(data);// 显示船基本档案
				if (ActionName == 'ShowCBZH')
					CBZSInfor(data);// 显示证书信息

	},
	error : function(XMLHttpRequest) {
		alert("11");
		alert($(".error", XMLHttpRequest.responseText).text());
	}
		});
	}

}
// 显示船基本档案
function CBInfor(data) {
	// alert("AA ");
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	newTable.append("<tr><td colspan='2'>船舶基本档案:</td><td></td></tr>");
	newTable.append("<tr><td>船舶名称:</td><td>" + data.list[0].zwcm + "</td>"
			+ "<td>船籍港:</td><td>" + data.list[0].cjg + "</td></tr>");
	newTable.append("<tr><td>船检登记号：</td><td>" + data.list[0].cjdjh + "</td>"
			+ "<td>船舶登记号：</td><td>" + data.list[0].cbdjh + "</td></tr>");
	newTable.append("<tr><td>英文船名:</td><td>" + data.list[0].ywcm + "</td>"
			+ "<td>原中文船名:</td><td>" + data.list[0].ywcm + "</td></tr>");
	newTable.append("<tr><td>船舶呼号：</td><td>" + data.list[0].cbhh + "</td>"
			+ "<td>国际海事组织编号：</td><td>" + data.list[0].imobh + "</td></tr>");
	newTable.append("<tr><td>船舶类型：</td><td>" + data.list[0].cblx + "</td>"
			+ "<td>主机总功率：</td><td>" + data.list[0].zjzgl + "</td></tr>");
	newTable.append("<tr><td>总吨位：</td><td>" + data.list[0].zdw + "</td>"
			+ "<td>净吨位：</td><td>" + data.list[0].jdw + "</td></tr>");
	newTable.append("<tr><td>总长（m）：</td><td>" + data.list[0].zc + "</td>"
			+ "<td>船长（m）：</td><td>" + data.list[0].cc + "</td></tr>");
	newTable.append("<tr><td>船宽（m）：</td><td>" + data.list[0].ck + "</td>"
			+ "<td>型深（m）：</td><td>" + data.list[0].xs + "</td></tr>");
	newTable.append("<tr><td>参考载货量</td><td>" + data.list[0].ckzhl + "</td>"
			+ "<td>适航航区：</td><td>" + data.list[0].shhq + "</td><tr>");
	newTable.append("<tr><td>营运航区：</td><td>" + data.list[0].yyhq + "</td>"
			+ "<td>登记航区：</td><td>" + data.list[0].hq + "</td></tr>");
	newTable.append("<tr><td>船舶制造厂：</td><td>" + data.list[0].cbzzc + "</td>"
			+ "<td>建造完工日期：</td><td>" + data.list[0].jzwgrq + "</td></tr>");
	newTable.append("<tr><td>船舶所有人：</td><td>" + data.list[0].cbsyr + "</td>"
			+ "<td>船舶经营人：</td><td>" + data.list[0].cbjyr + "</td></tr>");
	newTable.append("<tr><td>船舶所有人地址：</td><td>" + data.list[0].cbsyrdz
			+ "</td>" + "<td>船舶经营人地址：</td><td>" + data.list[0].cbjyrdz
			+ "</td></tr>");
	newTable.append("<tr><td>所有人联系电话:</td><td>" + data.list[0].syrlxdh
			+ "</td>" + "<td>经营人联系电话:</td><td>" + data.list[0].jyrlxdh
			+ "</td></tr>");

}

// 显示证书信息
function CBZSInfor(data) {
	// alert("AA ");
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	newTable.append("<tr><td colspan='4'>船舶证书信息:</td><td></td></tr>");
	newTable.append("<tr><td>证书编号:</td><td>证书名称:</td><td>发证日期：</td><td>有效期</td></tr>");
alert(data.list.length);
	for ( var i = 0; i < data.list.length; i++)

		newTable.append("<tr><td>" + data.list[i].id + "</td><td>"
				+ data.list[i].mc + "</td>" + "<td>" + data.list[i].fzrq
				+ "</td><td>" + data.list[i].yxrq + "</td></tr>");

}
//显示缴费信息
function CBJFInfor(data) {
	// alert("AA ");
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	newTable.append("<tr><td colspan='2'>船舶缴费信息:</td><td></td></tr>");
	newTable.append("<tr><td>开票序列号:</td><td>缴费项目：</td><td>应缴金额（元）</td><td>实缴金额（元）</td>" +
			"<td>有效期起</td><td>有效期止</td><td>开票日期</td><td>开票单位</td><td>开票地市</td><td>退费标志</td>" +
			"<td>作废标志</td><td>销号标志</td></tr>");

	for ( var i = 0; i < data.list.length; i++)

		newTable.append("<tr><td>" + data.list[0].kpxlh + "</td>" +
						"<td>" + data.list[0].hyf+ "</td>" +
				        "<td>" + data.list[0].sjze + "</td>" +
				       	"<td>" + data.list[0].sjze+ "</td>" +				       	
				       	"<td>" + data.list[0].yxqq + "</td>" +
						"<td>" + data.list[0].yxqz + "</td>" +
						"<td>" + data.list[0].kprq + "</td>"+
						"<td>" + data.list[0].kpdw + "</td>"+
						"<td>" + data.list[0].kpds + "</td>"+
						"<td>" + data.list[0].tfbz + "</td>"+
						"<td>" + data.list[0].zfbz + "</td>"+
						"<td>" + data.list[0].xhbz + "</td></tr>");

}
//显示违章处罚记录
function CBWZInfor(data) {
	// alert("AA ");
	$("#Infor tr").remove();
	$("#Infor tr").empty();
	newTable = $("#Infor");
	newTable.append("<tr><td>证书名称:</td><td>发证日期：</td><td>有效期</td></tr>");

	for ( var i = 0; i < data.list.length; i++)

		newTable.append("<tr><td>" + data.list[0].id + "</td><td>"
				+ data.list[0].mc + "</td>" + "<td>" + data.list[0].fzrq
				+ "</td><td>" + data.list[0].yxrq + "</td></tr>");

}