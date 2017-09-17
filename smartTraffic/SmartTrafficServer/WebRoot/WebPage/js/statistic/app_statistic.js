$(document).ready(function() {
	CreateReport();
});

function queryStatistic()
{

	if ($("#beginTime").val() == "") {
		alert("请选择起始时间");
		return;
	}
	if ($("#endTime").val() == "") {
		alert("请选择结束时间");
		return;
	}
	
	$("#report_imgdiv").empty();
	$(".addTr").empty();
	$(".addTr").remove();
	
	var beginTime=$("#beginTime").val();
	var	endTime=$("#endTime").val();
	
	$.ajax( {
		url : 'queryAppCount',
		type : "post",
		dataType : "json",
		data : {
			'startTime' : beginTime,
			'endTime':endTime
		},
		success : function(data) {

			var newTr = $("#dataTable");

			var temp = 0;
			var name = "";
			
			newTr.append($("<tr class='addTr'><td class='td1'>APP名称</td><td class='td1'>APP版本号</td><td class='td4'>下载量（次）</td></tr>"));
			
			for ( var i = 0; i < data.countInfo.length; i++) {
				
				if(i==0)
					name="禾行通Android版";
				else 
					name="";
				
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
			
		}
	});
}

function CreateReport() {

	$.ajax( {
		url : 'queryAppCount',
		type : "post",
		dataType : "json",
		data : {
		//	'appid' : 0
		},
		success : function(data) {

			var newTr = $("#dataTable");

			var temp = 0;
			var name = "";
			
			
			for ( var i = 0; i < data.countInfo.length; i++) {
				
				if(i==0)
					name="禾行通Android版";
				else 
					name="";
				
				
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
			
		}
	});
}
