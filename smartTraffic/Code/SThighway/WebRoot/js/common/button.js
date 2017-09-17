function buttonOver(){
	$(".c3_button1_left").css({"background":"url('image/main/left_pressed.png')"});
	$(".c3_button1_center").css({"background":"url('image/main/center_pressed.png') repeat"});
	$(".c3_button1_right").css({"background":"url('image/main/right_pressed.png')"});
}
function buttonOut(){
	$(".c3_button1_left").css({"background":"url('image/main/left_normal.png')"});
	$(".c3_button1_center").css({"background":"url('image/main/center_normal.png') repeat"});
	$(".c3_button1_right").css({"background":"url('image/main/right_normal.png')"});
}
function buttonDelOver(){
	$(".c3_button2_left").css({"background":"url('image/main/left_white_pressed.png')"});
	$(".c3_button2_center").css({"background":"url('image/main/center_white_pressed.png') repeat"});
	$(".c3_button2_right").css({"background":"url('image/main/right_white_pressed.png')"});
}
function buttonDelOut(){
	$(".c3_button2_left").css({"background":"url('image/main/left_white_normal.png')"});
	$(".c3_button2_center").css({"background":"url('image/main/center_white_normal.png') repeat"});
	$(".c3_button2_right").css({"background":"url('image/main/right_white_normal.png')"});
}
function buttonOverPop(){
	$(".c3_button1_left_pop").css({"background":"url('image/main/left_pressed.png')"});
	$(".c3_button1_center_pop").css({"background":"url('image/main/center_pressed.png') repeat"});
	$(".c3_button1_right_pop").css({"background":"url('image/main/right_pressed.png')"});
}
function buttonOutPop(){
	$(".c3_button1_left_pop").css({"background":"url('image/main/left_normal.png')"});
	$(".c3_button1_center_pop").css({"background":"url('image/main/center_normal.png') repeat"});
	$(".c3_button1_right_pop").css({"background":"url('image/main/right_normal.png')"});
}
function buttonDelOverPop(){
	$(".c3_button2_left_pop").css({"background":"url('image/main/left_white_pressed.png')"});
	$(".c3_button2_center_pop").css({"background":"url('image/main/center_white_pressed.png') repeat"});
	$(".c3_button2_right_pop").css({"background":"url('image/main/right_white_pressed.png')"});
}
function buttonDelOutPop(){
	$(".c3_button2_left_pop").css({"background":"url('image/main/left_white_normal.png')"});
	$(".c3_button2_center_pop").css({"background":"url('image/main/center_white_normal.png') repeat"});
	$(".c3_button2_right_pop").css({"background":"url('image/main/right_white_normal.png')"});
}
function addbuttonover(id){
	/*id.childNodes[0].style.background-image="../../image/main/left_pressed.png";
	id.childNodes[1].style.background-image="../../image/main/center_pressed.png";
	id.childNodes[2].style.background-image="../../image/main/right_pressed.png";*/
	$("#add_button_left").css({"background":"url('image/main/left_pressed.png')"});
	$("#add_button_center").css({"background":"url('image/main/center_pressed.png') repeat"});
	$("#add_button_right").css({"background":"url('image/main/right_pressed.png')"});
}
function addbuttonout(){
	$("#add_button_left").css({"background":"url('image/main/left_normal.png')"});
	$("#add_button_center").css({"background":"url('image/main/center_normal.png') repeat"});
	$("#add_button_right").css({"background":"url('image/main/right_normal.png')"});
}
function orderbuttonover(id){
	$("#order_button_left").css({"background":"url('image/main/left_pressed.png')"});
	$("#order_button_center").css({"background":"url('image/main/center_pressed.png') repeat"});
	$("#order_button_right").css({"background":"url('image/main/right_pressed.png')"});
}
function orderbuttonout(){
	$("#order_button_left").css({"background":"url('image/main/left_normal.png')"});
	$("#order_button_center").css({"background":"url('image/main/center_normal.png') repeat"});
	$("#order_button_right").css({"background":"url('image/main/right_normal.png')"});
}
function addsfzbuttonover(){
	$("#sfz_button_left").css({"background":"url('image/main/left_pressed.png')"});
	$("#sfz_button_center").css({"background":"url('image/main/center_pressed.png') repeat"});
	$("#sfz_button_right").css({"background":"url('image/main/right_pressed.png')"});
}
function addsfzbuttonout(){
	$("#sfz_button_left").css({"background":"url('image/main/left_normal.png')"});
	$("#sfz_button_center").css({"background":"url('image/main/center_normal.png') repeat"});
	$("#sfz_button_right").css({"background":"url('image/main/right_normal.png')"});
}
function CloseOver(id){
	id.src="image/main/close_pressed.png";
}
function CloseOut(id){
	id.src="image/main/close.png";
}
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}