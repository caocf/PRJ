$$(document).ready(function() {
	
		queryTicketsAgents(0,-1,-1);
	
});
function queryTicketsAgents(no,page,num) {
	$$(".layout6").append("<b>"+JIAXING_REGION[no]+"</b>");
	var table=$$("<table cellpadding='0' cellspacing='0' border='0' class='listTable3'><col width='20%' />" +
			"<col width='30%' /><col width='25%' /><col width='15%' /><tr><th>火车票代售点</th><th>地址</th><th>服务时间</th><th>联系电话</th></tr></table>");
	$$.ajax( {
		url : 'queryTicketsAgents',
		type : "post",
		dataType : "json",
		data : {
			'zone':JIAXING_REGIONCODE[no],
			'page':page,
			'num':num
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success : function(data) {
			if (data.result.length == 0) {
				table.append("<tr><td colspan='4' align='center'>暂无数据</td></tr>");
			} else {
				for(var i=0;i<data.result.length;i++){
					table.append("<tr><td>"+data.result[i].name+"</td><td>"+data.result[i].address+"</td><td>"+data.result[i].time+"</td><td>"+data.result[i].phone+"</td></tr>");
				}
			}
			$$(".layout6").append(table);
			if(no<JIAXING_REGION.length-1){
				queryTicketsAgents(no+1,page,num)
			}
		},
		complete : function() {
			CloseLoadingDiv();// 请求执行完后隐藏loading图标。
	}
	});
}
										