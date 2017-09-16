var ChangePage=0;
var ChangeIcon=0;
var beginTime="";
var endTime="";
var Gloval_D_Id=0;
var Gloval_D_Name="";
//------考勤管理部门-start--------
$(document).ready(function() {	
	GetRoleByLeaveStatisic();
});
//权限
function GetRoleByLeaveStatisic(){
	
	$.ajax( {
		url : 'GetRoleInforBySession',
		type : "post",
		dataType : "json",
		data : {
		'permission.permissionId' : 33
		},
		success : function(data) {
			switch (Number(data.list[0].roleStatus)) {
			case 0:
				$(".selectkindtitle").hide();
				$("#puselect").hide();
				break;
			case 1:
				$(".selectkindtitle").hide();
				$("#puselect").hide();
				break;
			case 2:
				Gloval_D_Id=data.list[0].departmentId;
				Gloval_D_Name=data.list[0].departmentName;
				document.getElementById("puselect").options
				.add(new Option(data.list[0].departmentName,
						data.list[0].departmentId));
				break;
			case 3:
				Gloval_D_Id=0;
				Gloval_D_Name="港航局";
				document.getElementById("puselect").options
				.add(new Option("港航局统计","0"));
				creatTree();
				break;
			}
		},
		error : function(XMLHttpRequest) {
			alert("发生错误请刷新！");
		}
	});
}
function SelectKind(){
	
	if($("#puselect").val()=="0"){
		$(".bt_department").css("display","");
		$("#printBt").css("display","none");
	}else if($("#puselect").val()=="-1"){
		$(".bt_department").css("display","none");
		$("#printBt").css("display","");
	}else{
		$(".bt_department").css("display","none");
		$("#printBt").css("display","none");
	}
}
function CreateReportByDepartment(){
	$("#report_imgdiv").empty();
	$(".addTr").empty();
	$(".addTr").remove();
	var realendTime= $("#endTime").val()+" 23:59:59";
	$("#report_imgdiv").append($("<img src='"+$("#basePath").val()+"ShowLeaveImageByDepartment?departmentReport.startTime="+$("#beginTime").val()+"&" +
			"departmentReport.toTime="+realendTime+"&departmentReport.departmentId="+Gloval_D_Id+"&departmentReport.deparmentName="+Gloval_D_Name+"' />"));
	$.ajax( {
		url : 'DateOfLeaveReportByDepartment',
		type : "post",
		dataType : "json",
		data : {
		'departmentReport.startTime' : $("#beginTime").val(),
		'departmentReport.toTime' : realendTime,
		'departmentReport.departmentId' :Gloval_D_Id,
		'departmentReport.deparmentName':Gloval_D_Name
		},
		success : function(data) {
			var list_person=data.departmentReport.list_person;
			var list_department=data.departmentReport.list_department;
			var newTr=$("#dataTable");
			newTr.append($("<tr class='addTr'><td colspan='5' style='text-align: center;'><b>"+Gloval_D_Name+"考勤表</b></td></tr>"));
			newTr.append($("<tr class='addTr'><td>统计时间段：</td><td colspan='4'>"+$("#beginTime").val()+"至"+$("#endTime").val()+"</td></tr>"));
			if(list_person.length>0){
				newTr.append($("<tr class='addTr'><td class='td1'>姓名</td><td class='td1'>请假时间(天)</td><td class='td1'>加班时间(天)</td><td class='td1'>出差时间(天)</td><td class='td2'>合计(天)</td></tr>"));
				
				for(var i=0;i<list_person.length;i++){
					newTr.append($("<tr class='addTr'><td class='td1'>"+list_person[i].name+"</td><td class='td1'>"+list_person[i].leave+"</td><td class='td1'>"+list_person[i].work+"</td><td class='td1'>"+list_person[i].business+"</td><td class='td2'>"+list_person[i].total+"</td></tr>"));
				}
				newTr.append($("<tr class='addTr'><td  class='td3'>合计</td><td  class='td3'>"+list_department[0].leave+"</td><td  class='td3'>"+list_department[0].work+"</td><td class='td3'>"+list_department[0].business+"</td><td  class='td4'>"+list_department[0].total+"</td></tr>"));
			}
			},
		error : function(XMLHttpRequest) {
			alert($(".error", XMLHttpRequest.responseText).text());
		}
	});
	ChangePage=1;

}

//------考勤管理部门-end--------	
function getMaxDate() {
	var t = new Date();
	var maxDate = [ t.getFullYear(), t.getMonth() + 1, t.getDate() ].join('-');
	maxDate += ' ' + t.toLocaleTimeString();
	return maxDate;
}
// getMinDate生成客户端本地时间
function getMinDate() {
	var t = new Date();
	t.setMonth(t.getMonth());// 最小时间少2个月
	var maxDate = [ t.getFullYear() - 1, t.getMonth() + 1, t.getDate() ]
			.join('-');
	maxDate += ' ' + t.toLocaleTimeString();
	return maxDate;
}
function CreateReport() {
	if($("#beginTime").val() == ""){
		alert("请选择起始时间");
		return;
	}
	if ($("#endTime").val() == "") {
		alert("请选择结束时间");
		return;
	}
	if($("#puselect").val()=="-1"){
		CreateReportByPerson();
	}else{
		CreateReportByDepartment();
	}
}
function CreateReportByPerson() {
	$("#report_imgdiv").empty();
	$(".addTr").empty();
	$(".addTr").remove();
	var realendTime= $("#endTime").val()+" 23:59:59";
	$("#report_imgdiv").append($("<img src='"+$("#basePath").val()+"ShowLeaveImage?reportModel.beginTime="+$("#beginTime").val() +
			"&reportModel.endTime="+realendTime+"' />"));
	
	$.ajax( {
		url : 'DateOfLeaveReport',
		type : "post",
		dataType : "json",
		data : {
		'reportModel.beginTime' : $("#beginTime").val(),
		'reportModel.endTime' : realendTime
		},
		success : function(data) {
			var newTr=$("#dataTable");
			newTr.append($("<tr class='addTr'><td colspan='5' style='text-align: center;'><b>"+data.list[0].name+"个人考勤表</b></td></tr>"));
			newTr.append($("<tr class='addTr'><td>统计时间段：</td><td colspan='4'>"+$("#beginTime").val()+"至"+$("#endTime").val()+"</td></tr>"));
			if(data.list.length>0){
				newTr.append($("<tr class='addTr'><td class='td1'>姓名</td><td class='td1'>请假时间(天)</td><td class='td1'>加班时间(天)</td><td class='td1'>出差时间(天)</td><td class='td2'>合计(天)</td></tr>"));

				newTr.append($("<tr class='addTr'><td class='td3'>"+data.list[0].name+"</td><td class='td3'>"+data.list[0].leave+
						"</td><td class='td3'>"+data.list[0].work+"</td><td class='td3'>"+data.list[0].business+"</td><td class='td4'>"+data.list[0].total+"</td></tr>"));
			}
		},
		error : function(XMLHttpRequest) {
			alert($(".error", XMLHttpRequest.responseText).text());
		}
	});
	ChangePage=1;
}
function GetExcel(){
	if(beginTime==""&&$("#beginTime").val() == ""){
		alert("请选择起始时间");
		return;
	}
	if ($("#endTime").val() == ""&&beginTime == "") {
		alert("请选择结束时间");
		return;
	}
	var realendTime= $("#endTime").val()+" 23:59:59";
	if($("#puselect").val()=="-1"){
		window.location.href=$("#basePath").val()+"downloadLeaveExcel?reportModel.beginTime="+$("#beginTime").val()+"&" +
		"reportModel.endTime="+realendTime+"&reportModel.amount="+$("#LoginUserid").val();
	}else{
		window.location.href=$("#basePath").val()+"downloadLeaveByDepartment?departmentReport.startTime="+$("#beginTime").val()+"&" +
		"departmentReport.toTime="+realendTime+"&departmentReport.departmentId="+Gloval_D_Id+"&departmentReport.deparmentName="+Gloval_D_Name;
	}

}

function xlPrint() {
	if($("#beginTime").val() == ""){
		alert("请选择起始时间");
		return;
	}
	if ($("#endTime").val() == "") {
		alert("请选择结束时间");
		return;
	}
	window.open($("#basePath").val()+"page/statistic/wordprint.jsp?beginTime="+$("#beginTime").val()+"&endTime="+$("#endTime").val(),"_blank");
}
function Cleanup() {
	window.clearInterval(idTmr);
	CollectGarbage();
}
function ChangeDiv(id){
	if(ChangePage==0){
		alert("还未统计！");
		return;
	}
	if(ChangeIcon==0){
		$("#report_datediv").css("display","block");
		$("#report_imgdiv").css("display","none");
		id.src="image/statistic/no_report_choose.png";
		ChangeIcon=1;
	}
	else if(ChangeIcon==1){
		$("#report_datediv").css("display","none");
		$("#report_imgdiv").css("display","block");
		id.src="image/statistic/yes_report_choose.png";
		ChangeIcon=0;
	}
}