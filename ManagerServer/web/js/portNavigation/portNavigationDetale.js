$(document).ready(function() {
	findPortNavigation();//进入界面调用此方法
	});

function findPortNavigation() {
	$.ajax( {
		url : 'findDetailInfoPortNavigation',
		type : "post",
		dataType : "json",
		data : {
			'url' : $("#url").val()
		},
		success : function(data) {
			showInfo(data.modelPortNavigation.contenct);
			
		}
	});
}

function showInfo(contenct){
	$("#portNavigationInfo").html("<div>"+contenct);
	
}
