$$(document).ready(function() {
	
	queryTicketHotLines("queryTicketHotLines",1);
	
});
function queryTicketHotLines(actionName, selectedPage) {
	var tableHtml="<table cellpadding='0' cellspacing='0' border='0' class='listTable3'><col width='25%' />" +
			"<col width='25%' /><col width='25%' /><col width='25%' /><tr><th>航空名称</th><th>订票服务热线</th><th>航空名称</th><th>订票服务热线</th></tr>";
	$$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'page':selectedPage,
			'num':KEY_PAGESIZE
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success : function(data) {
			if (data.result.length == 0) {
				tableHtml+="<tr><td colspan='4' align='center'>暂无数据</td></tr>";
			} else {
				for(var i=0;i<data.result.length;){
					if(i<data.result.length-1)
						tableHtml+="<tr><td>"+data.result[i].name+"</td><td>"+data.result[i].phone+"</td><td>"+data.result[i+1].name+"</td><td>"+data.result[i+1].phone+"</td></tr>";
					else if(i==data.result.length-1)
						tableHtml+="<tr><td>"+data.result[i].name+"</td><td>"+data.result[i].phone+"</td><td></td><td></td></tr>";
					i+=2;
				}
			}
			tableHtml+="</table>";
			$$(".layout6").append(tableHtml);
			gotoPageMethodName = "gotoPageNo";
			var number=CountItemTrByPageNum(data.message.total,KEY_PAGESIZE);
			printPage(number, selectedPage, 'queryTaxiCompanies',
					actionName, gotoPageMethodName);
			
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
		queryTaxiCompanies(actionName, pageNo);
	}
}
										