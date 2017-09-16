var userList=new Array();
var userIdList=new Array();
$(document).ready(function() {	
	if($("#sc").val()=="1")
		{
		alert("保存成功！");
		window.location.href=$("#basePath").val()+"page/officoa/schedule_show.jsp";
		}
		if($("#sc").val()=="2")
		alert("保存失败！请重新保存。");
	
	ScheduleKind();//日程类型
	showEventList();
	creatTree();//显示人员
	
	
});
//getMaxDate生成客户端本地时间
function getMaxDate(){
	var t = new Date();
	var maxDate = [t.getFullYear(), t.getMonth()+4, t.getDate()].join('-');
	maxDate += ' ' + t.toLocaleTimeString();
	return maxDate; 
}
//getMinDate生成客户端本地时间
function getMinDate(){
	var t = new Date();
	t.setMonth(t.getMonth()+1);//最小时间少2个月
	var maxDate = [t.getFullYear(), t.getMonth(), t.getDate()-1].join('-');
	maxDate += ' ' + t.toLocaleTimeString();
	return maxDate; 
}
function showEventList(){
	$.ajax( {
		url : 'EventListByEventId',
		type : "post",
		dataType : "json",
		data : {
			'scheduleEvent.eventId':$("#eventId").val()
		},
		success : function(data) {
			showEventTable(data.list);
		}
	});

}


function showEventTable(list){
	$("#sName").attr("value",list[0][0].eventName);
	$("#sKind").attr("value",list[0][3].scheduleKindId);
	$("#sTime").attr("value",GetShortTime(list[0][0].eventTime));
	$("#sLocation").attr("value",list[0][0].eventLocation);
	$("#eventRemind").attr("value",list[0][1].eventRemind);
	$("#eventRemindType").attr("value",list[0][1].eventRemindType);
	$("#sContent").text(list[0][0].eventContent);
		for(var i=0;i<list.length;i++)
		{

		if ($("#listOfUserName").text().length == 0) {
			$("#listOfUserName").text(list[i][2].name);
			$("#listOfUser").attr("value", list[i][2].userId);

		} else {
			$("#listOfUserName").text(
					$("#listOfUserName").text() + "," + list[i][2].name);
			$("#listOfUser").attr("value",
					$("#listOfUser").val() + "," + list[i][2].userId);
		}
		userList[userList.length] = list[i][2].name;
		userIdList[userIdList.length] = list[i][2].userId;
		}
		AttachmentByEventId(list[0][0].eventId);//附件
		SelectedUser();
}
//附件
function AttachmentByEventId(eventId){
	$.ajax( {
		url : 'FindAttachmentByEventId',
		type : "post",
		dataType : "json",
		data : {
			'scheduleEvent.eventId':eventId
		},
		success : function(data) {
		
			for(var i=0;i<data.list.length;i++)
			{
				$("#EvidenceUpload").append($("<tr id='oldFile"+data.list[i].attachmentId+"'><td>" +
						"<label>"+data.list[i].attachmentPath+"</label></td><td><a onclick='deleteAttachment("+data.list[i].attachmentId+")' " +
								"class='operation2'>删除</a></td></tr>"));
				
			}
			
		}
	});
}
function deleteAttachment(attachmentId){
	$.ajax( {
		url : 'deleteAttachmentIdByAttachmentId',
		type : "post",
		dataType : "json",
		data:{
		'scheduleEvent.eventId':$("#eventId").val(),
		'scheduleAttachment.attachmentId':attachmentId
	},
		success : function(data) {
		$("#oldFile"+attachmentId).empty();
		$("#oldFile"+attachmentId).remove();
	}
	});
}
//日程类型
function ScheduleKind(){
	$.ajax( {
		url : 'EventKindList',
		type : "post",
		dataType : "json",
		success : function(data) {
			for(var i=0;i<data.list.length;i++)
				document.getElementById("sKind").options.add(new Option(data.list[i].scheduleKindName, data.list[i].scheduleKindId));
		}
	});
}


function update_submit(){
	if(!validSubmit()){
		return false;
	}
	if($("#listOfUser").val()==""){
		alert("请选择参加人！");
		return false;
	}
	else
		return true;
}