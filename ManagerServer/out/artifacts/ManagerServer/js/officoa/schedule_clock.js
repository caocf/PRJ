$(document).ready(function() {
 showEventInfo();
});


function AddColck(){
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
function showEventInfo(){

	$.ajax( {
		url : 'EventListByEventId',
		type : "post",
		dataType : "json",
		data : {
			'scheduleEvent.eventId' : $("#eventId").val()
		},
		success : function(data) {
			for ( var i = 0; i < data.list.length; i++) {
				if(data.list[i][2].userId==$("#userId").val()){
					$("#userAgree").attr("value", data.list[i][1].userAgree);
					$("#agreeReason").attr("value", data.list[i][1].agreeReason);
					$("#eventRemind").attr("value", data.list[i][1].eventRemind);
					$("#eventRemindType").attr("value", data.list[i][1].eventRemindType);
				}
				
			}
		}
	});


}