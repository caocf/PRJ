$(document).ready(function(){
	showVersion();
	$(".top_u2").click(function(){
		window.location.href=$("#basePath").val()+"page/login/main.jsp";
	});
});
/**
 * 显示背景
 */
function showfullbg(){
	var bh = $(".common_c0").height(); 
	var bw = $(".top_u1").width();
	$("#fullbg").css({
		height:bh, 
		width:bw, 
		display:"block" 
	});
}
function showVersion(){
	$.ajax({
		url:'login/versionCode',
		type:'post',
		dataType:'json',
		success:function(data){
			var version=data.result.obj;
			$("#version").text(version);
		}
		
	});
}
function CloseLoadingDiv(){
	$("#fullbg,.loadingDiv").hide();
}
function hoverOver(id){
	id.src="image/main/back_pressed.png";
}
function hoverOut(id){
	id.src="image/main/back_normal.png";
}
