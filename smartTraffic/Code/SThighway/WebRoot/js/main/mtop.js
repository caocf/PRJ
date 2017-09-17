$(document).ready(function(){
	setHeight();
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
	var bw = $(".common_c0").width();
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
function setHeight(){
	var height=document.documentElement.clientHeight-82;
	if(height<680){
		//$(".common_c0").css({"height":"762px"});
		$(".left_I1").css({"height":"680px"});
		$(".common_c1").css({"height":"680px"});
		if($(".department_I1").css("height")!=undefined){
			$(".department_I1").css({"height":"680px"});
			$("#manage_c2").css({"height":"680px"});
		}
		
	}else{
		//$(".common_c0").css({"height":""+(height+82)+"px"});//alert($(".common_c0").height())
		$(".left_I1").css({"height":""+height+"px"});
		$(".common_c1").css({"height":""+height+"px"});
		if($(".department_I1").css("height")!=undefined){
			$(".department_I1").css({"height":""+height+"px"});
			$("#manage_c2").css({"height":""+height+"px"});
		}
	}
}
window.onresize=function(){
	var height=document.documentElement.clientHeight-82;
	if(height<680){
		$(".left_I1").css({"height":"680px"});
		$(".common_c1").css({"height":"680px"});
		if($(".department_I1").css("height")!=undefined){
			$(".department_I1").css({"height":"680px"});
			$("#manage_c2").css({"height":"680px"});
		}
	}else{
		$(".left_I1").css({"height":""+height+"px"});
		$(".common_c1").css({"height":""+height+"px"});
		if($(".department_I1").css("height")!=undefined){
			$(".department_I1").css({"height":""+height+"px"});
			$("#manage_c2").css({"height":""+height+"px"});
		}
	}
}

/*function ShowLoadingDiv(){
	var bh = $(".common_c0").height();
	var bw = $(".common_c0").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		}); 
	$(".loadingDiv").show();
}*/