var $$ = $.noConflict();

/*常规变量*/
var PARKING_PAGESIZE=100;//停车场分页

var PAGESIZE=15;//正常分页，每页条数

var PAGESIZE2=10;//正常分页，每页条数

var TIMELIMIT=15;//时间限制

var NODATA="暂无数据";//无数据时的显示

var NavType=1;//地图或卫星图

var LogAndExitRefresh=false;//该页面在登录后是否刷新，false不刷新，true刷新

var REAL_BUSTOSTATION_COUNT=1;//实时到站信息的车数

var KEY_PAGESIZE=50;//关键字

var BICYCLE_PAGESIZE=100;//自行车\停车

var BUS_STATUS=["未知","畅通","正常","拥堵"];

var DRAWLINE_SIZE=6;//地图划线宽度

var DRAWLINE_COLOR="#2746a2";//地图划线颜色
//天地图初始化显示比例
function InitializeTianMap(){
	/*var bound=map.getExtent();
	alert(bound.top+" "+bound.bottom+" "+bound.right+" "+bound.left);*/
	/*var top="30.763879489898";
	var bottom="30.73169298172";
	var right="120.78357763291";
	var left="120.71886129379";
	var bound=map.createBound(left,bottom,right,top);
	map.setMapExtent(bound,false);*/
	//alert(bound.top+" "+bound.bottom+" "+bound.right+" "+bound.left);
	screenResize();
	if(myWidth>1500){
		map.setMapCenter(getCenter(),14);
	}else if(myWidth>900){
		map.setMapCenter(getCenter(),13);
	}
}
//初始化字体颜色
function InitializeFontColor(value){
	var check = $$.trim($$("#"+value).val());
	if(check.substr(0, 1)!="请"){
		$$("#"+value).css("color","#000");
	}
}
/*鼠标移动的css效果*/
 function QueryOver(id){
	 id.src="WebPage/image/query_hover.png";
 }
 function QueryOut(id){
	 id.src="WebPage/image/query_normal.png";
 }
 function SearchOver(id){
	 id.src="WebPage/image/search_hover.png";
 }
 function SearchOut(id){
	 id.src="WebPage/image/search_normal.png";
 }
function FuYunCloseOver(id){
	 id.src="WebPage/image/graphical/close_hover.png";
}
function FuYunCloseOut(id){
	 id.src="WebPage/image/graphical/close_normal.png";
}
function ChangeOver(id){
	 id.src="WebPage/image/publicTraffic/chang_pressed.png";
}
function ChangeOut(id){
	 id.src="WebPage/image/publicTraffic/chang_normal.png";
}
//竖条的搜索img
function BarChangeOver(id){
	 id.src="WebPage/image/graphical/btn_search_hover.png";
}
function BarChangeOut(id){
	 id.src="WebPage/image/graphical/btn_search_normal.png";
}
//输入框内容调换img
function ChangeTextOver(id){
	 id.src="WebPage/image/graphical/btn_change_hover.png";
}
function ChangeTextOut(id){
	 id.src="WebPage/image/graphical/btn_change_normal.png";
}
function ArrowLeftOver(id){
	 id.src="WebPage/image/common/arrow_left_pressed.png";
}
function ArrowLeftOut(id){
	 id.src="WebPage/image/common/arrow_left_normal.png";
}
function ArrowRightOver(id){
	 id.src="WebPage/image/common/arrow_right_pressed.png";
}
function ArrowRightOut(id){
	 id.src="WebPage/image/common/arrow_right_normal.png";
}
//ios版图片效果
function IOSOver(id){
	 id.src="WebPage/image/firstpage/download_IOS_pressed.png";
}
function IOSOut(id){
	 id.src="WebPage/image/firstpage/download_IOS_normal.png";
}
//android版图片效果
function AndroidOver(id){
	 id.src="WebPage/image/firstpage/download_Android_pressed.png";
}
function AndroidOut(id){
	 id.src="WebPage/image/firstpage/download_Android_normal.png";
}
//下载手机版本
function DownloadAPK(type) {
	if (type == 1) {//android版
		window.open($$("#basePath").val()+"downLastApp",'_blank'); 
	} else {//ios版
		alert("抱歉！正在开发中，敬请期待！");
	}
}


//下拉框
function SelectOption(div_id,width){
	$$("#"+div_id).css("width",width+"px");
	$$("#"+div_id+" .CRselectValue").css("width",(width-30)+"px");
	$$("#"+div_id+" .CRselectBoxOptions").css("width",(width-42)+"px");
	$$("#"+div_id+" .CRselectValue").click(function(){
		var cbshow=$$("#"+div_id+" .CRselectBoxOptions").css("display");
		$$(".CRselectBoxOptions").hide();
		$$(this).blur();
		if(cbshow=="none")
			$$("#"+div_id+" .CRselectBoxOptions").show();
		else
			$$("#"+div_id+" .CRselectBoxOptions").hide();
		return false;
	});
	$$("#"+div_id+" .CRselectBoxItem a").click(function(){
		$$(this).blur();
		var value = $$(this).attr("rel");
		var txt = $$(this).text();
		$$("#"+div_id+" .abc").val(value);
		$$("#"+div_id+" .abc_CRtext").val(txt);
		$$("#"+div_id+" .CRselectValue").text(txt);
		$$("#"+div_id+" .CRselectBoxItem a").removeClass("selected");
		$$(this).addClass("selected");
		$$("#"+div_id+" .CRselectBoxOptions").hide();
		return false;
	});
	/*点击任何地方关闭层*/
	$$(document).click(function(event){
		if( $$(event.target).attr("class") != "CRselectBox" ){
			$$(".CRselectBoxOptions").hide();
		}
	});

}
//小下拉框
function SelectOption_small(div_id,width){
	$$("#"+div_id).css("width",width+"px");
	$$("#"+div_id+" .CRselectValue_small").css("width",(width-30)+"px");
	$$("#"+div_id+" .CRselectBoxOptions_small").css("width",(width-42)+"px");
	$$("#"+div_id+" .CRselectValue_small").click(function(){
		var cbshow=$$("#"+div_id+" .CRselectBoxOptions_small").css("display");
		$$(".CRselectBoxOptions_small").hide();
		$$(this).blur();
		if(cbshow=="none")
			$$("#"+div_id+" .CRselectBoxOptions_small").show();
		else
			$$("#"+div_id+" .CRselectBoxOptions_small").hide();
		return false;
	});
	$$("#"+div_id+" .CRselectBoxItem_small a").click(function(){
		$$(this).blur();
		var value = $$(this).attr("rel");
		var txt = $$(this).text();
		$$("#"+div_id+" .abc_small").val(value);
		$$("#"+div_id+" .abc_CRtext_small").val(txt);
		$$("#"+div_id+" .CRselectValue_small").text(txt);
		$$("#"+div_id+" .CRselectBoxItem_small a").removeClass("selected");
		$$(this).addClass("selected_small");
		$$("#"+div_id+" .CRselectBoxOptions_small").hide();
		return false;
	});
	/*点击任何地方关闭层*/
	$$(document).click(function(event){
		if($$(event.target).attr("class") != "CRselectBox_small" ){
			$$(".CRselectBoxOptions_small").hide();
		}
	});

}
//输入框提示
function TextFocus(id) {
	if (id.value == id.defaultValue) {
		id.value = '';
		id.style.color = '#000';
	}
}
//输入框提示
function TextBlur(id) {
	if (!id.value) {
		id.value = id.defaultValue;
		id.style.color = '#999';
	}
}

//输入框提示 带提示
function TextFocus1(id,tishitext) {
	if (id.value == tishitext) {
		id.value = '';
		id.style.color = '#000';
	}
}
//输入框提示 带提示
function TextBlur1(id,tishitext) {
	if (!id.value) {
		id.value = tishitext;
		id.style.color = '#999';
	}
}
function GetTimeList(){
	var myDate = new Date();
	$$("#TimeItem").append($$("<div class='arrow_left'  onclick='TimeListMove_left()'><img onclick='TimeListMove()' " +
			"src='WebPage/image/common/arrow_left_pressed.png' id='arrow_left_img'/></div>"));
	$$("#TimeItem").append($$("<div id='TimeItem_Outside'><ul id='TimeItem_item'></ul></div>"));
	for(var i=0;i<TIMELIMIT;i++){
		var myDate2 = new Date(myDate.getFullYear(),myDate.getMonth(),myDate.getDate()+i);
		var myDate2_time1=formatDate(myDate2,"yyyy-MM-dd");
		var  myDate2_time2=formatDate(myDate2,"MM-dd");
		if(i==0){
			$$("#TimeItem_item").append($$("<li class='TimeItem_item_yes' id='TimeItem_item_"+myDate2_time1+"' onclick=SelectOneTime(this,'"+myDate2_time1+"')>"+myDate2_time2+"<br/>"+formDatetoweek(myDate2)+"</li>"));
		}
		else{
			$$("#TimeItem_item").append($$("<li class='TimeItem_item_no' id='TimeItem_item_"+myDate2_time1+"' onclick=SelectOneTime(this,'"+myDate2_time1+"')>"+myDate2_time2+"<br/>"+formDatetoweek(myDate2)+"</li>"));	
		}
	}
	$$("#TimeItem").append($$("<div class='arrow_right'  onclick='TimeListMove_Right()'><img src='WebPage/image/common/arrow_right_normal.png' id='arrow_right_img'/></div>"));
}
function TimeListMove_left(){
	
	 var d=document.getElementById("TimeItem_Outside");
		if(d.scrollLeft>=0){
			var a=eval(95); d.scrollLeft-=a; 
		}
		if(d.scrollLeft==0){
			$$("#arrow_right_img").attr("src","WebPage/image/common/arrow_right_normal.png");
			$$("#arrow_left_img").attr("src","WebPage/image/common/arrow_left_pressed.png");
		}
		else if(d.scrollLeft==506){
			$$("#arrow_right_img").attr("src","WebPage/image/common/arrow_right_pressed.png");
			$$("#arrow_left_img").attr("src","WebPage/image/common/arrow_left_normal.png");
		}else{
			$$("#arrow_right_img").attr("src","WebPage/image/common/arrow_right_normal.png");
			$$("#arrow_left_img").attr("src","WebPage/image/common/arrow_left_normal.png");
		}
}
function TimeListMove_Right(){
	 var d=document.getElementById("TimeItem_Outside"); 
		if(d.scrollLeft<505){
			var a=eval(95); d.scrollLeft+=a; 
		}
		if(d.scrollLeft==0){
			$$("#arrow_right_img").attr("src","WebPage/image/common/arrow_right_normal.png");
			$$("#arrow_left_img").attr("src","WebPage/image/common/arrow_left_pressed.png");
		}
		else if(d.scrollLeft==506){
			$$("#arrow_right_img").attr("src","WebPage/image/common/arrow_right_pressed.png");
			$$("#arrow_left_img").attr("src","WebPage/image/common/arrow_left_normal.png");
		}else{
			$$("#arrow_right_img").attr("src","WebPage/image/common/arrow_right_normal.png");
			$$("#arrow_left_img").attr("src","WebPage/image/common/arrow_left_normal.png");
		}
}
//条形时间选择的样式，输入this
function SelectOneTimeByCSS(thisop){	
	 $$("#TimeItem_item li").attr("class","TimeItem_item_no");
	 thisop.className="TimeItem_item_yes";
}
//条形时间选择的样式，输入time(yyyy-MM-dd).
function SelectOneTimeByCSS2(time){
	$$("#TimeItem_item li").attr("class","TimeItem_item_no");
	$$("#TimeItem_item_"+time).attr("class","TimeItem_item_yes");
}
//添加地图类型切换控件2
function addNavType_self() {
	if (NavType == 1) {
		switchGroup(imageGroup);
		$$(".simpleMap_top_satellite").css("background-color","#2647a1");
		$$(".simpleMap_top_satellite").css("color","#fff");
		$$(".simpleMap_top_map").css("background-color","#fff");
		$$(".simpleMap_top_map").css("color","#4c4c4c");
		NavType=2;
	} else {
		switchGroup(baseLayerGroup);
		$$(".simpleMap_top_map").css("background-color","#2647a1");
		$$(".simpleMap_top_map").css("color","#fff");
		$$(".simpleMap_top_satellite").css("background-color","#fff");
		$$(".simpleMap_top_satellite").css("color","#4c4c4c");
		NavType=1;
	}

}
//验证输入的内容 
function SearchContentCheck(value){
	var check = $$.trim($$("#"+value).val());
	var text_defaultValue=document.getElementById(value).defaultValue;
	if(text_defaultValue.substr(0, 1)!="请"){
		text_defaultValue="请";
	}
	if(check==""||check==text_defaultValue){
		alert("搜索内容不能为空");
		return false;
	}
	if (/[~#^$@%&!*\s*]/.test(check)) {
		alert("输入的查询内容不能包含特殊字符！");
		return false;
	}
	if(check.length>=20){
		alert("请不要超过20字！");
		return false;
	}
	return true;
}
//验证输入的内容 可以为空
function SearchContentCheck2(value){
	var check = $$.trim($$("#"+value).val());
	var text_defaultValue=document.getElementById(value).defaultValue;
	if(text_defaultValue.substr(0, 1)!="请"){
		text_defaultValue="请";
	}
	if(check==""||check==text_defaultValue){
		//alert("搜索内容不能为空");
		return "";
	}
	if (/[~#^$@%&!*\s*]/.test(check)) {
		alert("输入的查询内容不能包含特殊字符！");
		return "error";
	}
	if(check.length>=20){
		alert("请不要超过20字！");
		return "error";
	}
	return check;
}

//正在加载中的显示
function ShowLoadingDiv(){
		var bh = $$("body").height(); 
		var bw = $$("body").width(); 
		$$("#fullbg").css({ 
			height:bh, 
			width:bw, 
			display:"block" 
			}); 
	$$("#ShowLoadingDiv").show(); 	
}
//正在加载中的隐藏
function CloseLoadingDiv() { 
		$$("#fullbg,#ShowLoadingDiv").hide(); 
	} 
	
	
//去左右空格\回车符;   
function trim(s){  
	if(s==undefined) return "";
 return s.replace(/[&\|\\\*^%$#@\-]/g,"");
}  