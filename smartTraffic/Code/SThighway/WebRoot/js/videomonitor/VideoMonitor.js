var eqIp="";
var streamType="";
var chanNum=0;
var g_iWndIndex=0;
$(document).ready(function(){
	$("#top_text").text("视频监控");//top上的显示
	//showEquipmentList('videolist/devicelist',1);
	showcontrolunitlist("videolist/controlunitlist");
	if (-1 == WebVideoCtrl.I_CheckPluginInstall()) {
		alert("您还未安装过插件，点击停止预览旁的链接下载插件！");
		$("#downLoad").show();
		return;
	}else{
		$("#downLoad").hide();
	}
	var height=$(".plugin").height()-51;
	var width=$(".plugin").width();
	// 初始化插件参数及插入插件
	WebVideoCtrl.I_InitPlugin(width, height, {
        iWndowType: 1,
		cbSelWnd: function (xmlDoc) {
			g_iWndIndex = $(xmlDoc).find("SelectWnd").eq(0).text();
		}
	});
	WebVideoCtrl.I_InsertOBJECTPlugin("divPlugin");
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
});

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
		newDiv.append($("<div class='second' id='second"+list[i].controlUnitId+"'></div>"));
		$(".department_left_select_child").append(newDiv);
		showregionlist('videolist/regionlist',list[i].controlUnitId);
	}
	$(".img_div").click(function(){
		var id=$(this).children("img").attr("id");
		//$(".department_arrow_style").attr("src","image/main/arrow.png");
		if($(this).parent().children(".second").css("display")=="none"){
			$(this).parent().children(".second").css("display","inline-block");
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
			newDiv.append($("<div class='three' id='three"+list[i].regionId+"'></div>"));
			$("#second"+controlUnitId).append(newDiv);
		}
	}
	for(var i=0;i<list.length;i++){
		if(list[i].parentId!=0){
			var newDiv=$("<div class='second_Div'></div>");
			newDiv.append($("<div class='img_div3'><img src='image/main/arrow.png' id='region"+list[i].regionId+"' class='department_arrow_style' /></div>"));
			newDiv.append($("<label class='equipment_name'>"+list[i].name+"</label>"));
			newDiv.append($("<div class='third' id='third"+list[i].regionId+"'></div>"));
			$("#three"+list[i].parentId).append(newDiv);
			showregioncameralist('videolist/regioncameralist',list[i].regionId);
		}
	}
	$(".img_div2").click(function(){
		var id=$(this).children("img").attr("id");
		if($(this).parent().children(".three").css("display")=="none"){
			$(this).parent().children(".three").css("display","inline-block");
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
			$(this).parent().children(".third").css("display","inline-block");;
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
		var newDiv=$("<div class='regioncamera' onclick=beginVedio('"+(list[i].chanNum+1)+"','"+list[i].networkAddr+"',this)></div>");
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
		var newDiv=$("<div class='regioncamera' onclick=beginVedio('"+(list[i].chanNum+1)+"','"+list[i].networkAddr+"',this)></div>");
		newDiv.append($("<label class='regioncamera_name'>"+list[i].name+"</label>"));
		$("#third"+regionId).append(newDiv);
	}
}

function beginVedio(chanNum,Ip,thisop){
	$(".regioncamera_select").attr("class","regioncamera");
	thisop.className="regioncamera_select";
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
		success: function (xmlDoc) {//alert("11111");alert(aa);
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
function downLoad(){
	window.location.href=$("#basePath").val()+"videolist/exedownload";
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
function imageOver(){
	$("#search_image").attr("src","image/main/search_pressed.png");
}
function imageOut(){
	$("#search_image").attr("src","image/main/search_normal.png");
}
/*function showEquipmentList(actionName){
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
				DivIsNull()
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
	newDiv.append($("<div class='img_div'><img src='image/main/arrow.png' id='"+list[i].deviceId+"' class='department_arrow_style' /></div>"))
	newDiv.append($("<label class='equipment_name'>"+list[i].name+"</label>"))
	newDiv.append($("<ul class='camera' id='camera"+list[i].deviceId+"'></ul>"))
	$(".department_left_select_child").append(newDiv);
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
			for(var i=0;i<list.length;i++){
				var newLi=$("<li class='camera_no_select_li' name='camera' id='"+list[i].cameraId+"'><label class='camera_label' >"+list[i].name+"</label></li>");
				$("#camera"+id).append(newLi);
			}
			$("li[name='camera']").click(function(){
				$(".camera_select_li").attr("class","camera_no_select_li");
				$(this).attr("class","camera_select_li");
				showVideo($(this).attr("id"));
			});
		}else{
			alert(data.result.resultdesc);
		}
	}
});
}
function showVideo(cameraId){
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
				eqIp=data.result.objs[0];
				beginVedio(list,eqIp);
			}else{
				alert(data.result.resultdesc);
			}
			
		}
	});
}
*/