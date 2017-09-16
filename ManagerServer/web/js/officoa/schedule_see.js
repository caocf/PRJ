$(document).ready(function() {
	showEventList();
	//showUserList();//显示相关人员
	UpdatePushmsgstatusByInfor();
});

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
//标志已读
function UpdatePushmsgstatusByInfor() {
	$.ajax( {
		url : 'UpdatePushmsgstatusByInfor',
		type : "post",
		dataType : "json",
		data : {
			'pushMsg.messageid' : $("#eventId").val(),
			'pushMsg.userid' : $("#userId").val()
		},
		success : function(data) {
			
		}
	});

}
function showEventTable(list){
	$("#sName").text(list[0][0].eventName);
	$("#sKind").text(list[0][3].scheduleKindName);
	$("#sTime").text(GetShortTime(list[0][0].eventTime));
	$("#sLocation").text(list[0][0].eventLocation);
	$("#sContent").text(list[0][0].eventContent);	

	var tdText="";
	var newTable=$("#ReplyTable");
	if(list.length==1)
	$("#Reply_title").css("display","none");
	for(var i=0;i<list.length;i++)
	{
		if(list[i][1].userAgree==0)
		{ //发起者
			$("#sFirstUser").text(list[i][2].name);
			showRemind(list[i][1].eventRemind,list[i][1].eventRemindType);
			break;
		}
	}
		$.ajax( {
			url : 'FindFeedbackByEventId',
			type : "post",
			dataType : "json",
			data : {
				'scheduleEvent.eventId':$("#eventId").val()
			},
			success : function(data) {
				var list2=data.list;
				
				for(var i=0;i<list2.length;i++)
				{
					var userAgreeStruts=list2[i][1];
					var msgstatus=list2[i][3];
					var msgstatusString="未读"; 
					if(msgstatus==2||msgstatus==4){
						msgstatusString=",并已读";
					}else{
						userAgreeStruts=7;
					}
					if(userAgreeStruts==1)
					{
						newTable.append($("<tr><td>"+list2[i][0]+"</td><td>参加"+DateIsNull3(list2[i][2])+"</td></tr>"));
						tdText=tdText+list2[i][0]+" ";
					}
					else if(userAgreeStruts==2)
					{
						newTable.append($("<tr><td>"+list2[i][0]+"</td><td>不参加"+DateIsNull3(list2[i][2])+"</td></tr>"));
						tdText=tdText+list2[i][0]+" ";
					}
					else if(userAgreeStruts==3)
					{
						newTable.append($("<tr><td>"+list2[i][0]+"</td><td>无回馈"+msgstatusString+"</td></tr>"));
						tdText=tdText+list2[i][0]+" ";
					}	else if(userAgreeStruts==4)
					{
						newTable.append($("<tr><td>"+list2[i][0]+"</td><td>参与，并转发"+DateIsNull3(list2[i][2])+"</td></tr>"));
						tdText=tdText+list2[i][0]+" ";
					}else if(userAgreeStruts==5)
					{
						newTable.append($("<tr><td>"+list2[i][0]+"</td><td>不参与，并转发"+DateIsNull3(list2[i][2])+"</td></tr>"));
						tdText=tdText+list2[i][0]+" ";
					}else if(userAgreeStruts==6)
					{
						newTable.append($("<tr><td>"+list2[i][0]+"</td><td>转发</td></tr>"));
						tdText=tdText+list2[i][0]+" ";
					}else if(userAgreeStruts==7)
					{
						newTable.append($("<tr><td>"+list2[i][0]+"</td><td>未读</td></tr>"));
						tdText=tdText+list2[i][0]+" ";
					}
				}
				$("#sUser").text(tdText);//相关人员
				AttachmentByEventId($("#eventId").val());//附件
			}
		});
}
function showRemind(eventRemind,eventRemindType){
	var remind;

	switch(eventRemind){
	case "0":remind="无提醒";
	break;
	case "5":remind="提前5分钟提醒";
	break;
	case "10":remind="提前10分钟提醒";
	break;
	case "15":remind="提前15分钟提醒";
	break;
	case "30":remind="提前30分钟提醒";
	break;
	case "60":remind="提前1个小时提醒";
	break;
	case "120":remind="提前2个小时提醒";
	break;
	case "240":remind="提前4个小时提醒";
	break;
	case "720":remind="提前12个小时提醒";
	break;
	case "1440":remind="提前24个小时提醒";
	break;
	}
	$("#sRemind").text(remind);
	if(eventRemindType==1)
	$("#sRemindType").text("铃声");
	if(eventRemindType==2)
		$("#sRemindType").text("震动");
	if(eventRemindType==3)
		$("#sRemindType").text("铃声和震动");
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
			var divhtml="";
			if(data.list.length==0)
			{
				$("#evidenceDiv_title").css("display","none");
				$("#evidenceDiv").css("display","none");
			}
			for(var i=0;i<data.list.length;i++){		
				var name=data.list[i].attachmentName;
				var type=name.substring(name.lastIndexOf('.')+ 1);
				if(type=="mp4")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].attachmentPath+"'>" +
							"<img src ='image/fileImg/word.png'></a>" +
									"<br /><a class='txt'>"+data.list[i].attachmentName+"</a></div>";
				else if(type=="pdf")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].attachmentPath+"'>" +
							"<img src ='image/fileImg/pdf.png'></a>" +
									"<br /><a class='txt'>"+data.list[i].attachmentName+"</a></div>";
				else if(type=="xls")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].attachmentPath+"'>" +
							"<img src ='image/fileImg/excel.png'></a>" +
									"<br /><a class='txt'>"+data.list[i].attachmentName+"</a></div>";
				else if(type=="ppt")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].attachmentPath+"'>" +
							"<img src ='image/fileImg/ppt.png'></a>" +
									"<br /><a class='txt'>"+data.list[i].attachmentName+"</a></div>";
				else if(type=="png")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].attachmentPath+"'>" +
							"<img src ='File/"+data.list[i].attachmentPath+"'></a>" +
									"<br /><a class='txt'>"+data.list[i].attachmentName+"</a></div>";
				else if(type=="jpg")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].attachmentPath+"'>" +
							"<img src ='File/"+data.list[i].attachmentPath+"'></a>" +
									"<br /><a class='txt'>"+data.list[i].attachmentName+"</a></div>";
				else if(type=="gif")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].attachmentPath+"'>" +
							"<img src ='File/"+data.list[i].attachmentPath+"'></a>" +
									"<br /><a class='txt'>"+data.list[i].attachmentName+"</a></div>";
				else if(type=="doc")
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].attachmentPath+"'>" +
							"<img src ='image/fileImg/word.png'></a>" +
									"<br /><a class='txt'>"+data.list[i].attachmentName+"</a></div>";
				else
					
					divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+data.list[i].attachmentPath+"'>" +
							"<img src ='image/fileImg/otherFile.png'></a>" +
									"<br /><a class='txt'>"+data.list[i].attachmentName+"</a></div>";
			}
			$("#evidenceDiv").html(divhtml);
		}
	});
}
