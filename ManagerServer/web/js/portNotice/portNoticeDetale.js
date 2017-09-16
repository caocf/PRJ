$(document).ready(function() {
	findPortNotice();//进入界面调用此方法
	});

function findPortNotice() {
	$.ajax( {
		url : 'findDetailInfoPortNotice',
		type : "post",
		dataType : "json",
		data : {
			'url' : $("#url").val()
		},
		success : function(data) {
			showInfo(data.modelPortNotice.contenct);
			
		}
	});
}

function showInfo(contenct){
	$("#portNoticeInfo").html("<div>"+contenct);
	
}
