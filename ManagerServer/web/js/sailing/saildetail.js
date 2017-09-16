$(document).ready(function(){
	showReportDetail();
	//ShowBoatUserInfo();
});
function showReportDetail(){
	$.ajax({
		url:'ElectricReportByReportId',
		type:'post',
		dataType:'json',
		data:{
			'electricReportNew.reportID':$("#seereportId").val()
		},
		success:function(data){
			var list=data.list[0];
			appendToInfo(list);
		}
	});
}
//显示船员信息
function ShowBoatUserInfo(){
	$.ajax({
		url:'ShowBoatUserInfo',
		type:'post',
		dataType:'json',
		data:{
		},
		success:function(data){
			var list=data.list;/*alert(JSON.stringify(list));*/
			$("#captain").text(list[0][0]);
			$("#mate").text(list[1][0]);
			$("#sailor").text(list[2][0]);
		}
	});
}
function appendToInfo(list){
	if(list.abnormal==0){
		$("#report_sta").text("异常");
		var newSpan=$("<span>"+judgeIsNull(list.prompt)+"</span>");
		$("#reportstatus").append(newSpan);
	}else{
		$("#report_sta").text("正常");
		var newSpan=$("<span>"+judgeIsNull(list.prompt)+"</span>");
		$("#reportstatus").append(newSpan);
	}
	$("#boatName").text(list.shipName);
	if(list.reportKind==1){
		$("#selectsail").text("重船航行");
	}else{
		$("#selectsail").text("空船航行");
		$("#cargoStyle,#cargoNum").hide();
	}
	if(list.outOrIn==1){
		$("#selectport").text("进港");
	}else{
		$("#selectport").text("出港");
	}
	$("#startport").text(list.startingPort);
	$("#arriveport").text(list.arrivalPort);
	if($("#startport").text().indexOf("湖州")>=0||$("#arriveport").text().indexOf("湖州")>=0){
		$("#workwharf,#inOrNotPort").show();
	}
	$("#cargoName").text(list.cargoType);
	$("#cargoquantity").text(list.cargoNumber);
	$("#stopWharf").text(list.toBeDockedAtThePier);
	$("#entertime").text(HourTimeFormat(list.estimatedTimeOfArrival));
	if(list.draft==""){
		$("#oilquantity").text("0");
	}else{
		$("#oilquantity").text(list.draft);
	}
	$("#addOiltime").text(HourTimeFormat(list.draftTime));
	if(list.shipInfo!=null){
		var shipmanInfo=list.shipInfo.split(';');
		for(var i=0;i<shipmanInfo.length;i++){
			var newTr=$("<tr class='addTr'></tr>");
			newTr.append($("<td>"+shipmanInfo[i].split(",")[0]+"</td>"));
			newTr.append($("<td><label>"+shipmanInfo[i].split(",")[1]+"</label><label>&nbsp;(&nbsp;"+shipmanInfo[i].split(",")[2]+":"+shipmanInfo[i].split(",")[3]+"&nbsp;)</label></td>"));
			$("#boatmanInfoTable").append(newTr);
		}
	}
}
function judgeIsNull(value){
	var returnValue="无";
	if(value==null||value=="null"||value==""){
		return returnValue;
	}else{	
		return value;
	}
}
function gotoBack(){
	window.history.go("-1");
}
