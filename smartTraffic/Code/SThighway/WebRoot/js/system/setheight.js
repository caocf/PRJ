$(function(){
	var height=document.documentElement.clientHeight-50;
	if(height<680){
		$(".left_I1").css({"height":"680px"});
		$(".common_c1").css({"height":"680px"});
	}else{
		$(".left_I1").css({"height":""+height+"px"});
		$(".common_c1").css({"height":""+height+"px"});
	}
});
window.onresize=function(){
	var height=document.documentElement.clientHeight-50;
	if(height<680){
		$(".left_I1").css({"height":"680px"});
		$(".common_c1").css({"height":"680px"});
	}else{
		$(".left_I1").css({"height":""+height+"px"});
		$(".common_c1").css({"height":""+height+"px"});
	}
}