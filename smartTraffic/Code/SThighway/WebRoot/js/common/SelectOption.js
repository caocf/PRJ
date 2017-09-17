var token="";
$(document).ready(function() {
	//SelectOption("section",160);
	//SelectOption("post",160);
	//setwidth();
	token= $("#roadtoken").val();
});
function SelectOption(div_id,width){
	$("#"+div_id).css("width",width+"px");
	$("#"+div_id+" .CRselectValue").css("width",(width-30)+"px");
	$("#"+div_id+" .CRselectBoxOptions").css("width",(width-42)+"px");
	$("#"+div_id+" .CRselectValue").click(function(){
		$(".CRselectBoxOptions").hide();
		$(this).blur();
		$("#"+div_id+" .CRselectBoxOptions").show();
		return false;
	});
	$("#"+div_id+" .CRselectBoxItem a").click(function(){
		$(this).blur();
		var value = $(this).attr("rel");
		var txt = $(this).text();
		$("#"+div_id+" .abc").val(value);
		$("#"+div_id+" .abc_CRtext").val(txt);
		$("#"+div_id+" .CRselectValue").text(txt);
		$("#"+div_id+" .CRselectBoxItem a").removeClass("selected");
		$(this).addClass("selected");
		$("#"+div_id+" .CRselectBoxOptions").hide();
		return false;
	});
	/*点击任何地方关闭层*/
	$(document).click(function(event){
		if( $(event.target).attr("class") != "CRselectBox" ){
			$(".CRselectBoxOptions").hide();
		}
	});

}
//小下拉框
function SelectOption_small(div_id,width){
	$("#"+div_id).css("width",width+"px");
	$("#"+div_id+" .CRselectValue_small").css("width",(width-30)+"px");
	$("#"+div_id+" .CRselectBoxOptions_small").css("width",(width-42)+"px");
	$("#"+div_id+" .CRselectValue_small").click(function(){
		$(".CRselectBoxOptions_small").hide();
		$(this).blur();
		$("#"+div_id+" .CRselectBoxOptions_small").show();
		return false;
	});
	$("#"+div_id+" .CRselectBoxItem_small a").click(function(){
		$(this).blur();
		var value = $(this).attr("rel");
		var txt = $(this).text();
		$("#"+div_id+" .abc_small").val(value);
		$("#"+div_id+" .abc_CRtext_small").val(txt);
		$("#"+div_id+" .CRselectValue_small").text(txt);
		$("#"+div_id+" .CRselectBoxItem_small a").removeClass("selected");
		$(this).addClass("selected_small");
		$("."+div_classname+" .CRselectBoxOptions_small").hide();
		return false;
	});
	/*点击任何地方关闭层*/
	$(document).click(function(event){
		if($(event.target).attr("class") != "CRselectBox_small" ){
			$(".CRselectBoxOptions_small").hide();
		}
	});

}
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
//输入框提示
function TextFocus(id) {
	if (id.value == id.defaultValue) {
		id.value = '';
		id.style.color = '#333333';
	}
}
//输入框提示
function TextBlur(id) {
	if (!id.value) {
		id.value = id.defaultValue;
		id.style.color = '#a3a3a3';
	}
}

function CloseOver(id){
	id.src="image/common/btn_close_pressed.png";
}

function CloseOut(id){
	id.src="image/common/btn_close_normal.png";
}
function bigCloseOver(id){
	id.src="image/main/big_close_pressed.png";
}

function bigCloseOut(id){
	id.src="image/main/big_close_normal.png";
}
function closeGoBack(){
	window.history.go("-1");
}

//关闭详情弹出框
function closeDetailDiv(){
	$("#fullbg,#DetailDiv").hide();
	$(".hideTr").hide();
	$(".aTr").show();
}

//关闭
function closeDialog1(){
	$("#AddpartDiv").hide();
	$("#fullbg").hide();
}

function cancel(){
	$("#AddpartDiv").hide();
	$("#fullbg").hide();
}

function closeDeleteDialog(){
	$("#DeleteDiv,#fullbg").hide();
}

function closeDialog3(){
	$("#ResetDiv").hide();
	$("#fullbg").hide();
}
function closeDialog4(){
	$("#EditUserDiv").hide();
	$("#fullbg").hide();
}
function CloseLoadDialog(){
	$("#LoadDiv,#fullbg").hide();
}

//设置user_s2宽度
function setwidth(){
	var s=0;
	s=document.documentElement.clientWidth-200;
	
	if(s<1166){
		document.getElementById("sss").style.width=1166+"px";
	}else{
		document.getElementById("sss").style.width=s+"px";
	}
}
/*window.onresize=function(){
	var s=0;
	s=document.documentElement.clientWidth-200;
	
	var ss=document.documentElement.clientHeight;
	if(s<1166){
		document.getElementById("sss").style.width=1166+"px";
	}else{
		document.getElementById("sss").style.width=s+"px";
	}
	if(document.getElementById("bridge_map_div")!=null){
		document.getElementById("bridge_map").style.width=$("#bridge_map_div").width();
	}
}*/
//全选
function selectAll(type){
	
	var t=document.getElementsByName("box");
	if(type.checked==true){
		for(var i=0;i<t.length;i++){
			var e=t[i];
			 e.checked=true;
		}
	}else{
		for(var i=0;i<t.length;i++){
			var e=t[i];
			 e.checked=false;
		}
	}
}
//设置初始时间
function putFirstTime(){
	var t = new Date();
	var month=t.getMonth();
	var year=t.getFullYear();
	if(month==0){
		month=12;
		year=year-1;
	}
	var timeString = [year, month, t.getDate()].join('-');
	$("#beginTime").attr("value",formatDate(timeString,"yyyy-MM-dd")); 
	timeString=[t.getFullYear(), t.getMonth()+1, t.getDate()].join('-');
	$("#endTime").attr("value",formatDate(timeString,"yyyy-MM-dd")); 
}
//与搜索有关的js
function OnInput (event) {
    $(".showtip").text($(".search").val());
 }
function Cancel(){
	$(".expert_search").hide();
}
function TextFocus1(id) {
	if (id.value == id.defaultValue) {
		id.value = '';
		id.style.color = '#333333';
	}
	$(".search_tip").show();
	$(document).click(function(event){
		if($(event.target).attr("class") != "search" ){
			$(".search_tip").hide();
		}
	});
}
/**
 * 判断是否为null
 */
function judgeIsNull(value){
	returnValue=" ";
	if((value==null||value=="null"||value=="")&&value!=0){
		return returnValue;
	}else{	
		return value;
	}
}
/**
 * 转化桩号类型
 */
function conversionType(type){
	var returnValue="";
	var opts="";
	if(type!=0){
		opts+=type;
		if(opts.split(".")[1]==undefined){
			returnValue="K"+opts.split(".")[0]+"+"+0;
		}else{
			returnValue="K"+opts.split(".")[0]+"+"+opts.split(".")[1];
		}
		return returnValue;
	}else{
		returnValue="K"+0+"+"+0;
		return returnValue;
	}
}
function BackToLoginPage(){
	window.location.href=$("#basePath").val();
}
//判断是否是小数
function isNum(s) {
	 var r= /^[+-]?[1-9]?[0-9]*\.[0-9]*$/;
	 return r.test(s);
}
//进入统计页面
function goToStatistic(num){
	window.location.href=$("#basePath").val()+"page/statistic/Statistic.jsp?num="+num;
}

function ShowLoadingDiv(){
	var bh = $(".common_c0").height();
	var bw = $(".common_c0").width(); 
	$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		}); 
	$(".loadingDiv").show();
}