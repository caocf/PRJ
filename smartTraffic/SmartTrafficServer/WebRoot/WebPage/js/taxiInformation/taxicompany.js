$$(document).ready(function() {
	
	queryTaxiCompanies("queryTaxiCompanies",1);
	
});
function queryTaxiCompanies(actionName, selectedPage) {
	$$(".layout").empty();
	var tableHtml="<table cellpadding='0' cellspacing='0' border='0' class='listTable3'><col width='20%' />" +
			"<col width='60%' /><col width='12%' /><col width='8%' /><tr><th>名称</th><th>公司简介</th><th>地址</th><th>联系电话</th></tr>";
	$$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data : {
			'page':selectedPage,
			'num':PAGESIZE
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success : function(data) {
			if (data.result.length == 0) {
				tableHtml+="<tr><td colspan='4' align='center'>暂无数据</td></tr>";
			} else {
				for(var i=0;i<data.result.length;i++){
					tableHtml+="<tr><td>"+data.result[i].name+"</td><td>"+data.result[i].introduce+"</td><td>"+data.result[i].address+"</td><td>"+data.result[i].phone+"</td></tr>";
				}
			}
			tableHtml+="</table>";
			$$(".layout").append(tableHtml);
			gotoPageMethodName = "gotoPageNo";
			var number=CountItemTrByPageNum(data.message.total,PAGESIZE);
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
										