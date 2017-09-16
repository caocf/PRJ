$(document).ready(function() {
	CreateReport();
});

function CreateReport() {

	$.ajax( {
		url : 'queryAppCount',
		type : "post",
		dataType : "json",
		data : {
			'appid' : 0
		},
		success : function(data) {

			var newTr = $("#dataTable");

			var temp = 0;
			var name = "";

			//判断是否有内部版或外部版下载
			var inner=false;
			var outer=false;
			
			
			for ( var i = 0; i < data.countInfo.length; i++) {
				
				if (data.countInfo[i].appId == 1 && temp != 1) {
					name = "湖州港航内部版";
					temp = 1;
					inner=true;
				} else if (data.countInfo[i].appId == 2 && temp != 2) {
					name = "湖州港航公众版";
					temp = 2;
					outer=true;
				}
				else
				{
					name="";
				}
				
				if(i==data.countInfo.length-1)
					newTr.append($("<tr class='addTr'><td class='td3'>" + name
						+ "</td><td class='td3'>" + data.countInfo[i].versionId
						+ "</td><td class='td4'>" + data.countInfo[i].count
						+ "</td></tr>"));
				else
					newTr.append($("<tr class='addTr'><td class='td1'>" + name
							+ "</td><td class='td1'>" + data.countInfo[i].versionId
							+ "</td><td class='td2'>" + data.countInfo[i].count
							+ "</td></tr>"));
			}
			
			if(!inner)
			{
				newTr.append($("<tr class='addTr'><td class='td3'>" + "湖州港航内部版"
						+ "</td><td class='td3'>" + "无"
						+ "</td><td class='td4'>" + 0
						+ "</td></tr>"));
			}
			
			if(!outer)
			{
				newTr.append($("<tr class='addTr'><td class='td3'>" + "湖州港航外部版"
						+ "</td><td class='td3'>" + "无"
						+ "</td><td class='td4'>" + 0
						+ "</td></tr>"));
			}
		}
	});
}
