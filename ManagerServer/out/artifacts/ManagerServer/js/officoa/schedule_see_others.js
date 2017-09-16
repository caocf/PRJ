$(document).ready(function() {
	showEventList();
	//showUserList();// 显示相关人员
	UpdatePushmsgstatusByInfor();
	});

function showEventList() {
	$.ajax( {
		url : 'EventListByEventId',
		type : "post",
		dataType : "json",
		data : {
			'scheduleEvent.eventId' : $("#eventId").val()
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
var showUserAgree = 0
function showEventTable(list) {
	$("#sName").text(list[0][0].eventName);
	$("#sKind").text(list[0][3].scheduleKindName);
	$("#sTime").text(GetShortTime(list[0][0].eventTime));
	$("#sLocation").text(list[0][0].eventLocation);
	$("#sContent").text(list[0][0].eventContent);

	var tdText = "";
	var newTable = $("#ReplyTable");
	for ( var i = 0; i < list.length; i++) {
		if (list[i][2].userId == $("#userId").val()) {

			$("#eventRemind").attr("value", list[i][1].eventRemind);
			$("#eventRemindType").attr("value", list[i][1].eventRemindType);
		}
		if (list[i][1].userAgree == 0) {
			$("#sFirstUser").text(list[i][2].name);// 发起者
		} else {
			tdText = tdText + list[i][2].name + " ";
		}

	}
	$("#sUser").text(tdText);// 相关人员
	AttachmentByEventId(list[0][0].eventId);// 附件

}
// 附件
function AttachmentByEventId(eventId) {
	$.ajax( {
		url : 'FindAttachmentByEventId',
		type : "post",
		dataType : "json",
		data : {
			'scheduleEvent.eventId' : eventId
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

function AddAgreeReason() {
	$.ajax( {
		url : 'AddAgreeReason',
		type : "post",
		dataType : "json",
		data : $('#actionName :input').serialize(),
		success : function(data) {
			alert("保存成功！");
			window.history.go(-1);
		}
	});
}
function checkLength() {
	if ($("#agreereason").val().length >= 200)
		$("#agreereasonErr").text("理由不能超过200个字！");
}
function textareaBegan(t){
	if(t.value=='请输入不参加的理由及其他'){t.value='' ; t.style.color="#323944";}	
}
function textareEend(t){
	if (t.value ==''){t.value='请输入不参加的理由及其他'; t.style.color="#ccc"}
}