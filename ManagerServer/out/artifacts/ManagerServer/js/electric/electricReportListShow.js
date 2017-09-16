$(document).ready(function() {
	ElectricReportByReportId();
});


function ElectricReportByReportId() {
	$.ajax( {
		url : 'ElectricReportByReportId',
		type : "post",
		dataType : "json",
		data : {
			'reportID' : $("#reportID").val()
		},
	success : function(data) {
	$("#reportID").attr("value",data.list[0].reportID);
	$("#shipName").text( data.list[0].shipName);
	$("#shipUserName").text( data.list[0].shipUserName);
	$("#draft").text( data.list[0].draft+"升");
	$("#cargoType").text( data.list[0].cargoType);
	$("#estimatedTimeOfArrival").text( dateT(data.list[0].estimatedTimeOfArrival));
	$("#toBeDockedAtThePier").text( data.list[0].toBeDockedAtThePier);
	$("#reportTime").text( dateT(data.list[0].reportTime));
	$("#uniti").text( data.list[0].uniti);
	$("#cargoNumber").text( (data.list[0].cargoNumber)+data.list[0].uniti);
	
	if(data.list[0].reportKind==1){
		$("#reportKind").text("重船航行");
	}else{
		$("#reportKind").text("空船航行");
		document.getElementById( "hiddentr1").style.display= "none"; 
		document.getElementById( "hiddentr2").style.display= "none"; 
	}
	$("#startingPort").text(data.list[0].startingPort);
	$("#arrivalPort").text(data.list[0].arrivalPort);
	var shipInfo=data.list[0].shipInfo.split(";");
	if(shipInfo.length>0){
		var newTr=$(".listTable1");
		var html_boat="";
		for(var i=0;i<shipInfo.length;i++){
			html_boat+=shipInfo[i].split(",")[0]+"：&nbsp;"+shipInfo[i].split(",")[1]+"("+shipInfo[i].split(",")[2]+":"+shipInfo[i].split(",")[3]+")";
			if(i!=shipInfo.length-1) html_boat+=",&nbsp;&nbsp;";
		}
		newTr.append("<tr><td><font color='red' style='font-weight: bold'>船员信息：</font></td><td>"+html_boat+"</td></tr>");
	}
	if(data.list[0].outOrIn==1){
		$("#outOrIn").text("进港");
	}else{
		$("#outOrIn").text("出港");
	}
	$("#drafttime").text(dateT(data.list[0].draftTime));
}
	});
}
function dateT(value){
	returnValue="";
	if(value==null|value==""){
		return returnValue;
	}else{
		returnValue=value.substring(0,16);
		return returnValue;
	}
	return returnValue;
}
function returnhistory(){
	$("#adduser").css("display","none");
	$("#usersTableDiv").css("display","block");
}