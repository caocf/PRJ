var markerS="";
var infolist="";
var selectLonlat="";
var videoList="";
var g_iWndIndex=0;
var selectnum="";
var leftmark=1;
var mapMark=new Array();
$(document).ready(function(){
	$("#top_text").text("公路地图");
	loadmap();
	if($("#videobh").val()!=""&&$("#videobh").val()!="null"){
		showcontrolunitlist('videolist/controlunitlist');
	}else{
		showcontrolunitlist('videolist/controlunitlist');
	}
});
function loadmap(){
	maplet = new XMap("highwaymap",3);
	maplet.setMapCenter("120.76042769896,30.773992239582",8);
	maplet.setScaleVisible(false);//设置比例尺不可见
	maplet.setOverviewVisible(false,false);//鹰眼不可见
}


function showcontrolunitlist(actionName){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				if(list==""){
					//DivIsNull()
				}else{
					appendToDiv(list);
				}
			}else{
				alert(data.result.resultdesc);
			}
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
}
function appendToDiv(list){
	for(var i=0;i<list.length;i++){
		var newDiv=$("<div class='equipment'></div>");
		newDiv.append($("<div class='img_div'><img src='image/main/arrow_down.png' id='"+list[i].controlUnitId+"' class='department_arrow_style' /></div>"));
		newDiv.append($("<label class='equipment_name'>"+list[i].name+"</label>"));
		newDiv.append($("<ul class='second' id='second"+list[i].controlUnitId+"'></ul>"));
		$(".highway_left_video").append(newDiv);
		showregionlist('videolist/regionlist',list[i].controlUnitId);
	}
	$(".img_div").click(function(){
		var id=$(this).children("img").attr("id");
		//$(".department_arrow_style").attr("src","image/main/arrow.png");
		if($(this).parent().children(".second").css("display")=="none"){
			$(this).parent().children(".second").show();
			$("#"+id).attr("src","image/main/arrow_down.png");
		}else{
			$(this).parent().children(".second").hide();
			//$("#department"+dm).children().hide();
			$("#"+id).attr("src","image/main/arrow.png");
		}
		
	});
}

function showregionlist(actionName,controlUnitId){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'controlUnitId':controlUnitId
		},
		beforeSend : function() {
			ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				if(list==""){
					//DivIsNull()
				}else{
					appendToSecondDiv(list,controlUnitId);
				}
			}else{
				alert(data.result.resultdesc);
			}
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
}

function appendToSecondDiv(list,controlUnitId){
	for(var i=0;i<list.length;i++){
		if(list[i].parentId==0){
			var newDiv=$("<div class='second_Div'></div>");
			newDiv.append($("<div class='img_div2'><img src='image/main/arrow.png' id='region"+list[i].regionId+"' class='department_arrow_style' /></div>"));
			newDiv.append($("<label class='equipment_name'>"+list[i].name+"</label>"));
			newDiv.append($("<ul class='three' id='three"+list[i].regionId+"'></ul>"));
			$("#second"+controlUnitId).append(newDiv);
		}
	}
	for(var i=0;i<list.length;i++){
		if(list[i].parentId!=0){
			var newDiv=$("<div class='second_Div'></div>");
			newDiv.append($("<div class='img_div3'><img src='image/main/arrow.png' id='region"+list[i].regionId+"' class='department_arrow_style' /></div>"));
			newDiv.append($("<label class='equipment_name'>"+list[i].name+"</label>"));
			newDiv.append($("<ul class='third' id='third"+list[i].regionId+"'></ul>"));
			$("#three"+list[i].parentId).append(newDiv);
			showregioncameralist('videolist/regioncameralist',list[i].regionId);
		}
	}
	$(".img_div2").click(function(){
		var id=$(this).children("img").attr("id");
		if($(this).parent().children(".three").css("display")=="none"){
			$(this).parent().children(".three").show();
			$("#"+id).attr("src","image/main/arrow_down.png");
			if($(this).parent().children(".three").children(".second_Div").length==0){
				showChindregioncameralist('videolist/regioncameralist',getNum(id));
			}
		}else{
			$(this).parent().children(".three").hide();
			//$("#department"+dm).children().hide();
			$("#"+id).attr("src","image/main/arrow.png");
		}
		
	});
	$(".img_div3").click(function(){
		var id=$(this).children("img").attr("id");
		if($(this).parent().children(".third").css("display")=="none"){
			$(this).parent().children(".third").show();
			$("#"+id).attr("src","image/main/arrow_down.png");
		}else{
			$(this).parent().children(".third").hide();
			//$("#department"+dm).children().hide();
			$("#"+id).attr("src","image/main/arrow.png");
		}
		
	});
}
function getNum(value){
	return value.replace(/[^0-9]/ig,"");
}
function showChindregioncameralist(actionName,regionId){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'regionId':regionId
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				if(list==""){
					//DivIsNull()
				}else{
					appendToThreeDiv(list,regionId);
				}
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function appendToThreeDiv(list,regionId){
	$("#three"+regionId).empty();
	for(var i=0;i<list.length;i++){
		var newDiv=$("<div class='regioncamera' onclick=showVideoInMap('"+(list[i].chanNum+1)+"','"+list[i].networkAddr+"',this,'"+list[i].cameraId+"')></div>");
		newDiv.append($("<label class='regioncamera_name'>"+list[i].name+"</label>"));
		$("#three"+regionId).append(newDiv);
	}
}
function showregioncameralist(actionName,regionId){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'regionId':regionId
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.list;
				if(list==""){
					//DivIsNull()
				}else{
					appendToThirdDiv(list,regionId);
				}
			}else{
				alert(data.result.resultdesc);
			}
		}
	});
}
function appendToThirdDiv(list,regionId){
	for(var i=0;i<list.length;i++){
		var newDiv=$("<div class='regioncamera' onclick=showVideoInMap('"+(list[i].chanNum+1)+"','"+list[i].networkAddr+"',this,'"+list[i].cameraId+"')></div>");
		newDiv.append($("<label class='regioncamera_name'>"+list[i].name+"</label>"));
		$("#third"+regionId).append(newDiv);
	}
}

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
		if(selectnum!=""){
			showMarkInMap();
		}
	}else{
		$(".highway_image").attr("src","image/map/arrowleft_normal.png");
		$(".highway_image").attr("id","arrowleft_");
		$(".image_size").css({"left":"350px"});
		$(".highway_left").show();
		$(".highway_right").css({"margin":"0 0 0 351px"});
		leftmark=1;
		$(".highway_right_map").css({"width":""+(width-351)+"px"});
		loadmap();
		if(selectnum!=""){
			showMarkInMap();
		}
	}
}
function showMarkInMap(){
	maplet.createLonlat(mapMark[0]);
	markerS=maplet.drawMarker(mapMark[0],"image/map/video.png",30);
	maplet.setMapCenter(mapMark[0],13);
	createPopup(mapMark[0],mapMark[1],mapMark[2]);
}


function showVideoInMap(chanNum,networkAddr,thisop,cameraId){
	selectnum=1;
	$.ajax({
		url:'videolist/cameradto',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'cameraId':cameraId
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				/*var list=data.result.obj.chanNum+1;
				beginVedio(list,data.result.objs[0]);*/
				$(".regioncamera_select").attr("class","regioncamera");
				thisop.className="regioncamera_select";
				var lon=data.result.obj.longitude;
				var lat=data.result.obj.latitude;
				var lonlat = lon+","+lat;
				selectLonlat=maplet.createLonlat(lonlat);
				markerS=maplet.drawMarker(lonlat,"image/map/video.png",30);
				maplet.setMapCenter(lonlat,13);
				mapMark[0]=lonlat;mapMark[1]=chanNum;mapMark[2]=networkAddr;
				createPopup(lonlat,chanNum,networkAddr);
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
function createPopup(lonlat,chanNum,networkAddr){
	var html="<div class='plugin'><div id='plugin' style='width:400px;height:200px;margin-left:15px;'></div>" +
			"<button class='video_button_start' onclick=startVideo('"+chanNum+"','"+networkAddr+"')>播放</button><button class='video_button_stop' onclick='stopVideo()'>停止</button>" +
					"&nbsp;&nbsp;&nbsp;<a id='downLoad' style='display:none' onclick='downLoad()'>下载插件</a></div>";
	maplet.createPopupForMarker(lonlat,html,"image/map/video.png",30,450,280);
}
/**
 * 开始播放视频
 * @param cameraId
 */
function startVideo(chanNum,networkAddr){
	if (-1 == WebVideoCtrl.I_CheckPluginInstall()) {
		alert("您还未安装过插件，点击停止预览旁的链接下载插件！");
		$("#downLoad").show();
		return;
	}else{
		$("#downLoad").hide();
	}
	// 初始化插件参数及插入插件
	WebVideoCtrl.I_InitPlugin(400, 200, {
        iWndowType: 1,
		cbSelWnd: function (xmlDoc) {
			g_iWndIndex = $(xmlDoc).find("SelectWnd").eq(0).text();
		}
	});
	WebVideoCtrl.I_InsertOBJECTPlugin("plugin");
	// 窗口事件绑定
	$(window).bind({
		resize: function () {
			var $Restart = $("#restartDiv");
			if ($Restart.length > 0) {
				var oSize = getWindowSize();
				$Restart.css({
					width: oSize.width + "px",
					height: oSize.height + "px"
				});
			}
		}
	});
	//初始化日期时间
    var szCurTime = dateFormat(new Date(), "yyyy-MM-dd");
    $("#starttime").val(szCurTime + " 00:00:00");
    $("#endtime").val(szCurTime + " 23:59:59");
    beginVideo(chanNum,networkAddr);
	
}
/*function showEquipmentList(actionName,selectedPage){
$.ajax({
	url:actionName,
	type:'post',
	dataType:'json',
	data:{
		'token':token
	},
	success:function(data){
		if(data.result.resultcode==-1){
			BackToLoginPage();
		}else if(data.result.resultcode==1){
			var list=data.result.list;
			if(list==""){
				DivIsNull()
			}else{
				appendToDiv(list);
			}
		}else{
			alert(data.result.resultdesc);
		}
	}
});
}
function appendToDiv(list){
for(var i=0;i<list.length;i++){
	var newDiv=$("<div class='equipment'></div>");
	newDiv.append($("<div class='img_div'><img src='image/main/arrow.png' id='"+list[i].deviceId+"' class='department_arrow_style' /></div>"))
	newDiv.append($("<label class='equipment_name'>"+list[i].name+"</label>"))
	newDiv.append($("<ul class='camera' id='camera"+list[i].deviceId+"'></ul>"))
	$(".highway_left_video").append(newDiv);
}
$(".img_div").click(function(){
	var id=$(this).children("img").attr("id");
	$(".department_arrow_style").attr("src","image/main/arrow.png");
	if($(this).parent().children(".camera").css("display")=="none"){
		$(".camera").hide();
		showCameraList(id);
		$(this).parent().children(".camera").show();
		$("#"+id).attr("src","image/main/arrow_down.png");
	}else{
		$(this).parent().children(".camera").hide();
		//$("#department"+dm).children().hide();
		$("#"+id).attr("src","image/main/arrow.png");
	}
	
});
}
function showCameraList(id){
$.ajax({
	url:'videolist/cameralist',
	type:'post',
	dataType:'json',
	data:{
		'token':token,
		'deviceId':id
	},
	success:function(data){
		if(data.result.resultcode==-1){
			BackToLoginPage();
		}else if(data.result.resultcode==1){
			$(".camera_no_select_li").remove();
			var list=data.result.list;
			videoList=list;
			for(var i=0;i<list.length;i++){
				var newLi=$("<li class='camera_no_select_li' name='camera' id='"+i+"'><label class='camera_label' >"+list[i].name+"</label></li>");
				$("#camera"+id).append(newLi);
			}
			$("li[name='camera']").click(function(){
				$(".camera_select_li").attr("class","camera_no_select_li");
				$(this).attr("class","camera_select_li");
				showVideoInMap($(this).attr("id"));
			});
		}else{
			alert(data.result.resultdesc);
		}
	}
});
}*/
/*function showVideo(cameraId){
	$.ajax({
		url:'videolist/cameradto',
		type:'post',
		dataType:'json',
		data:{
			'token':token,
			'cameraId':cameraId
		},
		success:function(data){
			if(data.result.resultcode==-1){
				BackToLoginPage();
			}else if(data.result.resultcode==1){
				var list=data.result.obj.chanNum+1;
				beginVedio(list,data.result.objs[0]);
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}*/
function beginVideo(chanNum,Ip){
	var szIP=Ip;
	var iStreamType=1;
	var iChannelID=chanNum;
	var bZeroChannel=false;
	var szPort = 80;
	var szUsername = "admin";
	var szPassword = "12345";

	if ("" == szIP || "" == szPort) {
		return;
	}
	var oWndInfo = WebVideoCtrl.I_GetWindowStatus(g_iWndIndex);

	if(oWndInfo!= null){
		WebVideoCtrl.I_Stop();
	}
	var aa=WebVideoCtrl.I_Login(szIP, 1, szPort, szUsername, szPassword, {
		success: function (xmlDoc) {
			WebVideoCtrl.I_StartRealPlay(szIP, {
				iStreamType: iStreamType,
				iChannelID: iChannelID,
				bZeroChannel: bZeroChannel
			});
		},
		error: function () {
			
		}
	});
	var iRet =WebVideoCtrl.I_StartRealPlay(szIP, {
		iStreamType: iStreamType,
		iChannelID: iChannelID,
		bZeroChannel: bZeroChannel
	});
}
function stopVideo(){
	var oWndInfo = WebVideoCtrl.I_GetWindowStatus(g_iWndIndex);
	if(oWndInfo!= null){
		WebVideoCtrl.I_Stop();
		/*if (0 == iRet) {
			alert("停止预览成功");
		} else {
			alert("停止预览失败")
		}*/
	}
}
//显示操作信息
function showOPInfo(szInfo) {
	szInfo = "<div>" + dateFormat(new Date(), "yyyy-MM-dd hh:mm:ss") + " " + szInfo + "</div>";
	$("#opinfo").html(szInfo + $("#opinfo").html());
}

// 显示回调信息
function showCBInfo(szInfo) {
	szInfo = "<div>" + dateFormat(new Date(), "yyyy-MM-dd hh:mm:ss") + " " + szInfo + "</div>";
	$("#cbinfo").html(szInfo + $("#cbinfo").html());
}

// 格式化时间
function dateFormat(oDate, fmt) {
	var o = {
		"M+": oDate.getMonth() + 1, //月份
		"d+": oDate.getDate(), //日
		"h+": oDate.getHours(), //小时
		"m+": oDate.getMinutes(), //分
		"s+": oDate.getSeconds(), //秒
		"q+": Math.floor((oDate.getMonth() + 3) / 3), //季度
		"S": oDate.getMilliseconds()//毫秒
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (oDate.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for (var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}

// 获取窗口尺寸
function getWindowSize() {
	var nWidth = $(this).width() + $(this).scrollLeft(),
		nHeight = $(this).height() + $(this).scrollTop();

	return {width: nWidth, height: nHeight};
}
function downLoad(){
	window.location.href=$("#basePath").val()+"videolist/exedownload";
}
/**
 * 返回
 */
function gobackToSelectPage(){
	window.location.href=$("#basePath").val()+"page/highway/HighWayMap.jsp";
}

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
