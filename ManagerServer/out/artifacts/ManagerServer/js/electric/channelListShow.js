$(document).ready(function() {
		searchshowChannelIdInfo();
});


function searchshowChannelIdInfo() {
	$.ajax( {
		url : 'showChannelInfo',
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : 'c.channelId',
			'queryCondition.listKeyValuePair[0].pair' : '=',
			'queryCondition.listKeyValuePair[0].value' : $("#channelId").val(),
			'queryCondition.orderByFielName' : 'c.channelId',
			'queryCondition.sequence' : 'asc',
			'cpage' : 1
		},
	success : function(data) {
	$("#channelId").attr("value",data.list[0].channelId);
	$("#channelName").attr("value", data.list[0].channelName);
	$("#channelCoding").attr("value", data.list[0].channelCoding);
	$("#riverName").attr("value", DateIsNull(data.list[0].riverName));
	$("#riversCode").attr("value", data.list[0].riversCode);
	$("#startingName").attr("value", data.list[0].startingName);
	$("#endingName").attr("value", data.list[0].endingName);
	$("#segmentmileage").attr("value", data.list[0].segmentmileage);
	$("#channelDepth").attr("value", data.list[0].channelDepth);
	$("#channelWidth").attr("value", data.list[0].channelWidth);
	$("#segmentTechnology").attr("value", data.list[0].segmentTechnology);
	
}
	});
}
function DateIsNull(value) {
	returnValue="暂未命名";
	if(value==null||value=="null"||value==""){
		
		return returnValue;
	}else{	
		return value;
	}
}
function dateT(value){
	if(value==null){
		return "";
	}
	return value.replace("T"," ");
}
function returnhistory(){
	$("#adduser").css("display","none");
	$("#usersTableDiv").css("display","block");
}