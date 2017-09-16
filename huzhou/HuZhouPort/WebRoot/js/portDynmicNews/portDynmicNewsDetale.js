$(document).ready(function() {
	findPortDynicNews();//进入界面调用此方法
	});

function findPortDynicNews() {
	$.ajax( {
		url : 'findDetailInfo',
		type : "post",
		dataType : "json",
		data : {
			'url' : $("#url").val()
		},
		success : function(data) {
			showInfo(data.modelPortDynmicNews.contenct);
			
		}
	});
}

function showInfo(contenct){
	$("#portDynmicNewsInfo").html("<div>"+contenct);
	
}
