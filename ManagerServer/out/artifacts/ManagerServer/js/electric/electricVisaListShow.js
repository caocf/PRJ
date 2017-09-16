$(document).ready(function() {
	searchshowElectricVisaIdInfo();
});

function searchshowElectricVisaIdInfo() {
	$.ajax( {
		url : 'showElectricVisaInfo',
		type : "post",
		dataType : "json",
		data : {
			'queryCondition.listKeyValuePair[0].key' : 'ev.visaID',
			'queryCondition.listKeyValuePair[0].pair' : '=',
			'queryCondition.listKeyValuePair[0].value' : $("#visaID").val(),
			'queryCondition.orderByFielName' : 'ev.visaID',
			'queryCondition.sequence' : 'asc',
			'cpage' : 1
		},
		success : function(data) {
			$("#visaID").attr("value", data.list[0].visaID);
			$("#shipName").attr("value", data.list[0].shipName);
			$("#startingPort").attr("value", data.list[0].startingPort);
			$("#arrivalPort").attr("value", data.list[0].arrivalPort);
			$("#cargoTypes").attr("value", data.list[0].cargoTypes);
			$("#mark").attr("value", dateT(data.list[0].mark));
			$("#visaStatus").attr("value", dateT(data.list[0].visaStatus));
			$("#visaStatus1").attr("value", dateT(data.list[0].visaStatus));
			$("#visaContent").attr("value", data.list[0].visaContent);
			$("#visaUserName").attr("value", dateT(data.list[0].visaUserName));

		}
	});
}
function dateT(value) {
	if (value == null) {
		return "";
	}
	return value.replace("T", " ");
}function returnhistory(){
	$("#adduser").css("display","none");
	$("#usersTableDiv").css("display","block");
}

function applyVisaInfo(){
	var visaStatus="已签证";
	if($("#visaStatus2").attr("checked")==true){
		visaStatus="拒绝签证"
	}
	var visaContent=$("#visaContent").val();
	$.ajax( {
		url : 'updateElectricVisaInfo',
		type : "post",
		dataType : "json",
		data : {
			'visaID' : $("#visaID").val(),
			'visaStatus' : visaStatus,
			'visaContent':visaContent
		},
	success : function(data) {
	alert("操作成功");
	window.location.href="/page/electric/electricVisaList.jsp";
}
	});
}