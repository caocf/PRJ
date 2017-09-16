var EnterTime="";
var PortName="";
var shipName="";
$(document).ready(function(){
	shipName=$("#shipName").val();
	showSailReportList('ElectricReportListByShip',1);
	showBindBoatInfo();
});
function showSailReportList(actionName,selectedPage){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'cpage':selectedPage,
			'electricReportNew.shipInfo':$("#searchinput").val()
		},
		success:function(data){
			$(".addTr").empty();
			$(".addTr").remove();
			if(data.list==null){
				TableIsNull();
			}else{
				var list=data.list;
				appendToTable(list);
				gotoPageMethodName = "gotoPageNo";
				printPage(data.totalPage,selectedPage,'showSailReportList',
						actionName,gotoPageMethodName,data.allTotal);
			}
		}
	});
	
}
function TableIsNull(){
	$(".addTr").empty();
	$(".addTr").remove();
	newTr = $("<tr class='addTr'><td colspan='8' align='center'>暂无相关数据</td></tr>");
	$("#reportTable").append(newTr);
	$("#pageDiv").hide();
}
function gotoPageNo(actionName, totalPage) {
	var pageNo = $(".indexCommon").val();
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").prop("value", "1");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)||parseInt(pageNo)==0) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		showSailReportList(actionName, pageNo);
	}
}
function gotoPageNo1(actionName, totalPage) {
	var pageNo = $(".indexCommon").val();
	var str = $.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$(".indexCommon").prop("value", "1");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)||parseInt(pageNo)==0) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		searchReport(actionName, pageNo);
	}
}
function appendToTable(list){
	EnterTime=HourTimeFormat(list[0].estimatedTimeOfArrival);
	PortName=list[0].arrivalPort;
	for(var i=0;i<list.length;i++){	
		var newTr=$("<tr class='addTr'></tr>");
		//newTr.append($("<td>"+list[i].shipName+"</td>"));
		if (list[i].reportKind == 1) {
			newTr.append("<td>重船航行</td>");
		} else {
			newTr.append("<td>空船航行</td>");
		}
		//始发港
		newTr.append($("<td>"+list[i].startingPort+"</td>"));
		//目的港
		newTr.append($("<td>"+list[i].arrivalPort+"</td>"));
		newTr.append($("<td>"+judgeIsNull(list[i].toBeDockedAtThePier)+"</td>"));
		newTr.append($("<td>"+HourTimeFormat(list[i].estimatedTimeOfArrival)+"</td>"));
		newTr.append($("<td>"+HourTimeFormat(list[i].reportTime)+"</td>"));
		if(list[i].abnormal==0){
			newTr.append($("<td>"+"异常"+"</td>"));
		}else{
			newTr.append($("<td>"+"正常"+"</td>"));
		}
		
		if(i==0){
			if(overTime(HourTimeFormat(list[i].reportTime))){
				newTr.append($("<td><a class='Operate' onclick='lookreport("+list[i].reportID+")'>查看</a>" +
						"<a class='Operate' onclick='GotoModify("+list[i].reportID+")'>修改</a>" +
								"<a class='Operate' onclick='adjustLine("+list[i].reportID+")'>调整航线</a>" +
										"<a class='Operate' onclick='copyAddReport("+list[i].reportID+")'>复制并新增</a></td>"));
			}else{
				newTr.append($("<td><a class='Operate' onclick='lookreport("+list[i].reportID+")'>查看</a>" +
								"<a class='Operate' onclick='adjustLine("+list[i].reportID+")'>调整航线</a>" +
										"<a class='Operate' onclick='copyAddReport("+list[i].reportID+")'>复制并新增</a></td>"));
			}
			
		}else{
			newTr.append($("<td><a class='Operate' onclick='lookreport("+list[i].reportID+")'>查看</a>" +
					"<a class='Operate' onclick='copyAddReport("+list[i].reportID+")'>复制并新增</a></td>"));
		}
		$("#reportTable").append(newTr);
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

function addReport(){
	window.location.href=$("#basePath").val()+"page/sailing/addsail.jsp";
}

function GotoModify(num){
	window.location.href=$("#basePath").val()+"page/sailing/addsail.jsp?reportId="+num;
}
function lookreport(num){
	window.location.href=$("#basePath").val()+"page/sailing/saildetail.jsp?reportId="+num;
}
function copyAddReport(num){
	window.location.href=$("#basePath").val()+"page/sailing/copyaddsail.jsp?reportId="+num;
}
//管理船员信息
function goToSailor(){
	/*var bh = $("body").height(); 
	var bw = $("body").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		}); 
	showBoatmanInfo();
	$("#boatmanInfoDiv").show();*/
	window.location.href=$("#basePath").val()+"page/sailing/sailboatmanInfo.jsp?shipName="+shipName;
}
var reportID="";
function adjustLine(num){
	var bh = $("body").height(); 
	var bw = $("body").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		}); 
	$("#entertime").val(EnterTime);
	$("#PortName").val(PortName);
	reportID=num;
	showPortList(num);
	$("#sailLineDiv").show();
	//showReportInfo(num);
}

function closeLineDialog(){
	$("#fullbg,#sailLineDiv").hide();
}
function closeBoatmanDialog(){
	$("#fullbg,#boatmanInfoDiv").hide();
}
//显示港口
function showPortList(num){
	$.ajax({
		url:'showPortList',
		type:'post',
		dataType:'json',
		data:{
		},
		success:function(data){
			$("#portname .addLi").empty();
			$("#portname .addLi").remove();
			var list=data.list;
			for(i=0;i<list.length;i++){
				newLi=$("<li class='addLi' onclick=selectPort('"+list[i].portName+"')>"+list[i].portName+"</li>");
				$("#portname").append(newLi);
			}
		}
	});
}
//显示船员信息
function showBoatmanInfo(){
	$.ajax({
		url:'ShowBoatUserInfo',
		type:'post',
		dataType:'json',
		data:{
			'boatman.boatmanShip':shipName
		},
		success:function(data){
			var list=data.list;
			$("#captain").val(list[0][0]);
			$("#captainID").val(list[0][2]);
			$("#mate").val(list[1][0]);
			$("#mateID").val(list[1][2]);
			$("#sailor").val(list[2][0]);
			$("#sailorID").val(list[2][2]);	
		}
	});
}
//管理船员信息
function manageBoatInfo(){
	var captain=$("#captain").val();
	var mate=$("#mate").val();
	var sailor=$("#sailor").val();
	var captainID=$("#captainID").val();
	var mateID=$("#mateID").val();
	var sailorID=$("#sailorID").val();
	if(captain==""||mate==""||sailor==""||captainID==""||mateID==""||sailorID==""){
		alert("不能有空的船员信息！");
		return;
	}
	if(captain.length>20||mate.length>20||sailor.length>20||captainID.length>20||mateID.length>20||sailorID.length>20){
		alert("船员信息不能大于20位！");
		return;
	}
	$.ajax({
		url:'SetBoatUserInfo',
		type:'post',
		dataType:'json',
		data:{
			'boatman.boatmanName':$("#captain").val()+","+$("#mate").val()+","+$("#sailor").val(),
			'boatman.boatmanShip':shipName,
			'boatman.boatCardNum':$("#captainID").val()+","+$("#mateID").val()+","+$("#sailorID").val(),
			'boatman.kindList':"1,2,3"
		},
		success:function(data){
			alert("设置船员信息成功！");
			window.location.reload();
		},
		error:function(XMLHttpRequest){
			alert("设置错误！");
		}
	});
}
function selectPort(name){
	$("#PortName").val(name);
}
function adjustPortAndTime(){
	if($("#entertime").val()==""){
		alert("预计进出港时间不能为空！")
		return;
	}
	$.ajax({
		url:'updateReportArrivalPort',
		type:'post',
		dataType:'json',
		data:{
			'electricReportNew.reportID':reportID,
			'electricReportNew.arrivalPort':$("#PortName").val(),
			'electricReportNew.estimatedTimeOfArrival':$("#entertime").val(),
			'electricReportNew.startingPort':PortName,
			'electricReportNew.reportTime':EnterTime
		},
		success:function(data){
			alert("调整成功！");
			window.location.reload();
		},
		error:function(XMLHttpRequest){
			alert("调整错误！");
		}
	});
}

//显示绑定船舶信息
function showBindBoatInfo(){
	$.ajax({
		url:'FindPublicUserByShip',
		type:'post',
		dataType:'json',
		data:{
		},
		success:function(data){
			$(".addOp").remove();
			$(".addOp").empty();
			var list=data.list;	
			if(list.length==1){
				$("#selectBindBoatspan").text(list[0].shipNum);
				$("#selectBindBoatInfo").hide();
			}else{
				document.getElementById("selectBindBoatInfo").options.add(new Option(shipName,shipName));
				for(var i=0;i<list.length;i++){
					if(list[i].shipNum==shipName)continue;
					document.getElementById("selectBindBoatInfo").options.add(new Option(list[i].shipNum,list[i].shipNum));
					/*var newOp=$("<option class='addOp'>"+list[i].shipNum+"</option>");
					$("#selectBindBoat").append(newOp);*/
				}
				
			}			
		}
	});
	
}
function selectBindBoat(){
	$.ajax({
		url:'UpdateBindNameBySession',
		type:'post',
		dataType:'json',
		data:{
			'publicUser.bindName':$("#selectBindBoatInfo").val()
		},
		success:function(data){
			$("#shipName").val($("#selectBindBoatInfo").val());
			showSailReportList('ElectricReportListByShip',1);	
		},
		error:function(XMLHttpRequest){
			alert("选择错误!");
		}
	});
}
//大于24小时
function overTime(time){
	var LSTR_Date = time.replace(" ", "").replace("-", "").replace("-", "").replace(":", "");//假设数据库中的下载时间
	var d = new Date();
	var vYear = d.getFullYear();
	var vMon = d.getMonth() + 1;
	var vDay = d.getDate();
	var vHour = d.getHours();
	var vMin = d.getMinutes();
	var LSTR_Date1 = vYear.toString()+"";
	if(vMon<10) LSTR_Date1+="0"+ vMon.toString();
	else LSTR_Date1+=vMon.toString();
	if(vDay<10) LSTR_Date1+="0"+ vDay.toString();
	else LSTR_Date1+=vDay.toString();
	if(vHour<10) LSTR_Date1+="0"+ vHour.toString();
	else LSTR_Date1+=vHour.toString();
	if(vMin<10) LSTR_Date1+="0"+ vMin.toString();
	else LSTR_Date1+=vMin.toString();

	if((parseInt(LSTR_Date1)-parseInt(LSTR_Date)) > 10000)
	{
	return false;
	}
	return true;	
}