$(function(){
	/*var height=document.documentElement.clientHeight-50;
	if(height<750){
		$(".highway_left").css({"height":"750px"});
		$(".highway_right").css({"height":"750px"});
	}else{
		$(".highway_left").css({"height":""+height+"px"});
		$(".highway_right").css({"height":""+height+"px"});
	}*/
	$("#highwaymap").width($(".highway_right").width());
	$("#highwaymap").height($(".highway_right").height());
});
window.onresize=function(){
	/*var height=document.documentElement.clientHeight-50;
	if(height<750){
		$(".highway_left").css({"height":"750px"});
		$(".highway_right").css({"height":"750px"});
	}else{
		$(".highway_left").css({"height":""+height+"px"});
		$(".highway_right").css({"height":""+height+"px"});
	}*/
	$("#highwaymap").width($(".highway_right").width());
	$("#highwaymap").height($(".highway_right").height());
}