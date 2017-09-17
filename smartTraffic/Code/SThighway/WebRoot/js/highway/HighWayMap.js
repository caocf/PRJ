var map;
var leftmark=1;
$(document).ready(function(){
	$("#top_text").text("公路地图");
	//setWidth();
	loadmap();
	$(".highway_image").bind({
		mouseover:function(){
			var id=$(this).attr("id");
			var image_url='image/map/'+id+'pressed.png';
			$(this).attr("src",image_url);
		},
		mouseout:function(){
			var id=$(this).attr("id");
			var image_url='image/map/'+id+'normal.png';
			$(this).attr("src",image_url);
		}
	});
	/*if($("#bridgebm").val()!=""&&$("#bridgebm").val()!="null"){
		$("#highway_left_info").show();
		showBridgeInfo('qljbxxlist/qlgklist',1);
	}else if($("#routebm").val()!=""&&$("#routebm").val()!="null"){
		$("#highway_left_info").show();
		showRouteInfo('lxjbxxlist/lxgklist',1)
	}else{
		showmap();
	}*/
});
/*function showmap(){
	$("#highway_left_select").show();
}*/
function showOrhideleft(){
	var width=$(".top_u1").width();
	if(leftmark==1){
		$(".highway_image").attr("src","image/map/arrowright_normal.png");
		$(".highway_image").attr("id","arrowright_");
		$(".image_size").css({"left":"0"});
		$(".highway_left").hide();
		$(".highway_right").css({"margin":"0"});
		leftmark=2;
		$(".highway_right_map").css({"width":""+width+"px"});
		//$(".highway_right_map").css({"width":"100%"});
		loadmap();
	}else{
		$(".highway_image").attr("src","image/map/arrowleft_normal.png");
		$(".highway_image").attr("id","arrowleft_");
		$(".image_size").css({"left":"200px"});
		$(".highway_left").show();
		$(".highway_right").css({"margin":"0 0 0 201px"});
		leftmark=1;
		$(".highway_right_map").css({"width":""+(width-201)+"px"});
		loadmap();
	}
}
function loadmap(){
	map = new XMap("highwaymap",3);
	map.setMapCenter("120.76042769896,30.773992239582",8);
	map.setScaleVisible(false);//设置比例尺不可见
	map.setOverviewVisible(false,false);//鹰眼不可见
}
/**
 * 返回
 */
function gobackToSelectPage(){
	window.location.href=$("#basePath").val()+"page/highway/HighWayMap.jsp";
}
function showhighwayleftinfo(num){
	if(num==4){
		window.location.href=$("#basePath").val()+"page/highway/HighWayBridgeMap.jsp";
	}
	if(num==1){
		window.location.href=$("#basePath").val()+"page/highway/HighWayRouteMap.jsp";
	}
	if(num==5){
		window.location.href=$("#basePath").val()+"page/highway/HighWayMarkSignMap.jsp";
	}
	if(num==7){
		window.location.href=$("#basePath").val()+"page/highway/HighWayGljgMap.jsp";
	}
	if(num==6){
		window.location.href=$("#basePath").val()+"page/highway/HighWayStationMap.jsp";
	}
	if(num==3){
		window.location.href=$("#basePath").val()+"page/highway/HighWayServiceMap.jsp";
	}
	if(num==2){
		window.location.href=$("#basePath").val()+"page/highway/HighWayVideoMap.jsp";
	}
	if(num==8){
		window.location.href=$("#basePath").val()+"page/highway/HighWayTrafficMap.jsp";
	}
}
/**
 * 自适应设置宽度
 */
/*function setWidth(){
	var s=document.documentElement.clientWidth-350;
	if(s<1016){
		document.getElementById("highway_right").style.width=1016+"px";
	}else{
		document.getElementById("highway_right").style.width=s+"px";
	}
}
window.onresize=function(){
	var s=document.documentElement.clientWidth-350;
	if(s<1016){
		document.getElementById("highway_right").style.width=1016+"px";
	}else{
		document.getElementById("highway_right").style.width=s+"px";
	}
}*/
function NullInfo(){
	var newDiv=$("<div class='highway_left_middle_div'>暂无相关数据</div>");
	$(".highway_left_middle").append(newDiv);
	$(".highway_left_bottom").hide();
}
function fullOver(id){
	id.src="image/main/full_pressed.png";
}
function fullOut(id){
	id.src="image/main/full_normal.png";
}
/**
 * 判断是否为null
 */
function judgeIsNull(value){
	returnValue="";
	if(value==null||value=="null"||value==""){
		return returnValue;
	}else{	
		return value;
	}
}
function mapLDXXOver(id){
	id.src="image/map/ldxx_pressed.png";
}
function mapLDXXOut(id){
	id.src="image/map/ldxx_normal.png";
}
function mapSPJKOver(id){
	id.src="image/map/spjk_pressed.png";
}
function mapSPJKOut(id){
	id.src="image/map/spjk_normal.png";
}
function mapFWSSOver(id){
	id.src="image/map/fwss_pressed.png";
}
function mapFWSSOut(id){
	id.src="image/map/fwss_normal.png";
}
function mapQLXXOver(id){
	id.src="image/map/qlxx_pressed.png";
}
function mapQLXXOut(id){
	id.src="image/map/qlxx_normal.png";
}
function mapBZBXOver(id){
	id.src="image/map/bzbx_pressed.png";
}
function mapBZBXOut(id){
	id.src="image/map/bzbx_normal.png";
}
function mapGLZOver(id){
	id.src="image/map/glz_pressed.png";
}
function mapGLZOut(id){
	id.src="image/map/glz_normal.png";
}
function mapGLJGOver(id){
	id.src="image/map/gljg_pressed.png";
}
function mapGLJGOut(id){
	id.src="image/map/gljg_normal.png";
}
function mapJTLOver(id){
	id.src="image/map/jtl_pressed.png";
}
function mapJTLOut(id){
	id.src="image/map/jtl_normal.png";
}