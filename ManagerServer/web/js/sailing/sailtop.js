$(document).ready(function() {
	
	$("#ul1 li a:first").addClass("topHoverClick");
	$("#ul1 li a font:first").css("color","#fff");
	if($("#top_user").text()=="null"){
		window.location.href=$("#basePath").val()+"boat.jsp";
	}
});
function a_top_onclick(id) {
	$("#ul1 li a").removeClass("topHoverClick");
	$("#ul1 li a font").css("color", "#16347a");	
	$("#"+id).addClass("topHoverClick");
	$("#"+id+" font").css("color","#fff");
	
}

function exit() {
	var r = confirm("退出登陆?");
	if (r == true) {
		var basePath = $("#basePath").val();
		window.location.href = basePath + "boat.jsp";
	}
}
function downloadApp(){
	window.location.href=$("#basePath").val()+"downLastApp?appid=2";
}

