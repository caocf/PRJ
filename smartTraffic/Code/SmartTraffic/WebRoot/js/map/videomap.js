var leftmark=1;
var selectCameraId=-1;
var longtitue=-1;
var latitude=-1;
var myListener;
var isOpen =1;
var centerJwd = "120.76403,30.74283";
var level=8;
var markerS="";
$(document).ready(function(){
	loadmap();
	findDimensions();
	loadLeftMenu();
	findAllCamera();
	   /**
	    * 比例尺图标初始化
	    */	
	$("#zoomLarge").click(function() {
		maplet.zoomIn();
		//initCenterZoom(currentJwd,currentZoom+1);
	});	
	$("#zoomSmall").click(function() {
		maplet.zoomOut();
		//initCenterZoom(currentJwd,currentZoom-1);
	});
});



function showOrhideleft(){
	//findDimensions();
	var width=$(".top_u1").width();
	if(leftmark==1){
		leftmark=2;
		$(".video_image").attr("src","image/map/arrowright_normal.png");
		$(".video_image").attr("id","arrowright_");
		$(".image_size").css({"left":"0"});
		$(".highway_left").hide();
		$(".highway_right").css({"margin":"0"});
		
		$(".highway_right_map").css({"width":""+width+"px"});
		$(".highway_right_map").css({"width":"100%"});
		
		maplet.removeAllOverlay();
		$("#video_map").empty();
		findDimensions();
		loadmap();
		findAllCamera();
		 initCenterZoom(centerJwd ,level);
		/*if(selectnum!=""){
			showMarkInMap();
		}*/
	}else{
		leftmark=1;
		$(".video_image").attr("src","image/map/arrowleft_normal.png");
		$(".video_image").attr("id","arrowleft_");
		$(".image_size").css({"left":"349px"});
		$(".highway_left").show();
		$(".highway_right").css({"margin":"0 0 0 355px"});
		$(".highway_right_map").css({"width":""+(width-351)+"px"});
		maplet.removeAllOverlay();
		$("#video_map").empty();
		findDimensions();
		loadmap();
		findAllCamera();
		initCenterZoom(centerJwd ,level);
		/*if(selectnum!=""){
			showMarkInMap();
		}*/
	}
	
}

function loadmap(){
	maplet = new XMap("video_map",3);
	maplet.setMapCenter("120.76403,30.74283",8);
	maplet.setScaleVisible(false);//设置比例尺不可见
	maplet.setOverviewVisible(false,false);//鹰眼不可见
}


//从数据库加载
function loadLeftMenu(){
	//视频返回按钮事件，返回上一页
	$("#videoguid_back").click(function(){
		window.location.href=$("#basePath").val()+"page/main/traffic.jsp";
	});
	//findDimensions();
	loadControlUnit();//加载控制中心

}

function loadVideo(){
	//连接
	/* var o = new ActiveXObject("DemoCtrl");
	 alert(o);*/
	/*var ocx=document.getElementById("DemoCtrl");
	  //var r=-1;
	  var r=ocx.InitConnect();
	  alert(r);
	  if(r==-1){
		  alert("视频连接失败，无法播放！");
		  loadLeftMenu();
	  }else{
		  loadLeftMenu();
	  }*/
}

function playVideo(id){
	alert(id);
}

/**
 * 加载控制中心列表---------------------------------------------------------------------
 */
//获取数据
function loadControlUnit(){
	$.ajax({
		url:'video/selectControlUnit',
		type:'post',
		dataType:'json',
		data:{
		},
       beforeSend : function() {					
			   ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==0){
				var unitlist = data.result.obj;
				appendToFirstDiv(unitlist);	
			
			}else{		
				alert(data.result.resultdesc);					
			}
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
}
//界面显示
function appendToFirstDiv(list){
	for(var i=0;i<list.length;i++){
		if(list[i].UPCONTROLUNITID==1){//第一层
			 if(list[i+1]==undefined||list[i+1].UPCONTROLUNITID!=list[i].CONTROLUNITID){//只有1层
			 var newDiv=$("<div class='firstUnit_Div' id=unit_"+list[i].CONTROLUNITID+"></div>");
					newDiv.append($("<div class='img_div3'><img src='image/main/arrow.png' id='uniticon_"+list[i].CONTROLUNITID+"' class='department_arrow_style' /></div>"));
					newDiv.append($("<label class='equipment_name'>"+list[i].CONTROLUNITNAME+"</label>"));
					newDiv.append($("<div class='firstRegion_Div' name='1' style='display:none;margin-left:5px\9;paddint-left:5px\9;' id='unitFather_"+list[i].CONTROLUNITID+"'></div>"));
					$("#video_left").append(newDiv);
					
			 }else{
				 var newDiv=$("<div class='firstUnit_Div' id=unit_"+list[i].CONTROLUNITID+"></div>");
					newDiv.append($("<div class='img_div2'><img src='image/main/arrow.png' id='uniticon_"+list[i].CONTROLUNITID+"' class='department_arrow_style' /></div>"));
					newDiv.append($("<label class='equipment_name'>"+list[i].CONTROLUNITNAME+"</label>"));
					newDiv.append($("<div class='firstUnit_Div' name='1' style='display:none' id='unitFather_"+list[i].CONTROLUNITID+"'></div>"));
					$("#video_left").append(newDiv);
			 }
			
		}else{
		
		   var fatherDivId = "unitFather_"+list[i].UPCONTROLUNITID;
		   var level = parseInt($("div[id$='"+fatherDivId+"']").attr('name'));
		  // alert(level);
		   var mylevel = level;
		   var childleft  = parseInt($("div[id$='"+fatherDivId+"']").css("marginLeft"))+15;
		   if(isIEListener()){  
			   //childleft = document.getElementById(fatherDivId).offsetLeft+15;
			   childleft = level*15;
			   //alert(childleft+"||"+level);
			 //  alert(childleft);
		   }
		   if(list[i+1]==undefined||list[i+1].UPCONTROLUNITID!=list[i].CONTROLUNITID){//只有1层
		       var newDiv=$("<div class='firstUnit_Div' style='margin-left:"+childleft+"px;' id=unit_"+list[i].CONTROLUNITID+"></div>");
		  
			newDiv.append($("<div class='img_div3'><img src='image/main/arrow.png' id='uniticon_"+list[i].CONTROLUNITID+"' class='department_arrow_style' /></div>"));
			newDiv.append($("<label class='equipment_name'>"+list[i].CONTROLUNITNAME+"</label>"));
			newDiv.append($("<div class='firstRegion_Div' style='display:none' name='"+mylevel+"' id='unitFather_"+list[i].CONTROLUNITID+"'></div>"));
			$("#"+fatherDivId).append(newDiv);
		   }else{
			   var newDiv=$("<div class='firstUnit_Div' style='margin-left:"+childleft+"px;' id=unit_"+list[i].CONTROLUNITID+"></div>");			  
				newDiv.append($("<div class='img_div2'><img src='image/main/arrow.png' id='uniticon_"+list[i].CONTROLUNITID+"' class='department_arrow_style' /></div>"));
				newDiv.append($("<label class='equipment_name'>"+list[i].CONTROLUNITNAME+"</label>"));
			   newDiv.append($("<div class='firstUnit_Div' style='display:none'  name='"+mylevel+"' id='unitFather_"+list[i].CONTROLUNITID+"'></div>"));
				$("#"+fatherDivId).append(newDiv);
			
		   }
		
		}
		
	}
	
	
	//icon点击下级列表显示或者隐藏
	$(".img_div2").click(function(){
		var iconid=$(this).children("img").attr("id");
		var fid = iconid.split("_")[1];
		var parDiv = $("div[id$='unitFather_"+fid+"']");
		if(parDiv.css("display")=="none"){
			parDiv.show();
		//	$(this).parent().children(".three").css("display","inline-block");
			$("#"+iconid).attr("src","image/main/arrow_down.png");
			
		}else{
			parDiv.hide();
			//$("#department"+dm).children().hide();
			$("#"+iconid).attr("src","image/main/arrow.png");
		}
		
	});
	$(".img_div3").click(function(){//级联区域数据
		var iconid=$(this).children("img").attr("id");
		var unitId = iconid.split("_")[1];
		var parDiv = $("div[id$='unitFather_"+unitId+"']");
		if(parDiv.css("display")=="none"){
			 parDiv.show();
			 $("#"+iconid).attr("src","image/main/arrow_down.png");
			 loadRegion(unitId);
		}else{
			//消除
			$("#"+iconid).attr("src","image/main/arrow.png");
			parDiv.hide();
			parDiv.empty();
		}
	});
		
}




/**
 * 加载区域列表---------------------------------------------------------------------
 */

function loadRegion(unitId){
	$.ajax({
		url:'video/selectRegion',
		type:'post',
		dataType:'json',
		data:{
			"unitId":unitId
		},
       beforeSend : function() {					
			   ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==0){
				var unitlist = data.result.obj;
				appendToSecondDiv(unitlist,unitId);	
			
			}else{		
						
			}
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
}

function appendToSecondDiv(list,unitId){
	for(var i=0;i<list.length;i++){
		if(list[i].REGIONHIGH==0){//第一层
			var regionDiv =  $("div[id$='unitFather_"+unitId+"']");
			 var level = parseInt(regionDiv.attr('name'));
		     
		     var mylevel = level+1;
			var fleft =  parseInt(regionDiv.css("marginLeft"))+15;
			 if(isIEListener()){
				/* if(document.getElementById("unitFather_"+unitId).offsetLeft>25){
				   fleft = document.getElementById("unitFather_"+unitId).offsetLeft-25;
				 }else{*/
					// fleft = document.getElementById("unitFather_"+unitId).offsetLeft+level15;
				/* }*/
				// alert(fleft+"||"+document.getElementById("unitFather_"+unitId).offsetLeft);
				   fleft = level*15;
				//   alert(fleft+"||"+level);
			   }
			 if(list[i+1]==undefined||list[i+1].REGIONHIGH!=list[i].REGIONID){//只有1层
				 var newDiv=$("<div class='firstUnit_Div' style='margin-left:"+fleft+"px;' id=region_"+list[i].REGIONID+"></div>");				  
					newDiv.append($("<div class='img_region3'" +
							"  onclick='imgregion3Click("+list[i].REGIONID+",this);' ><img src='image/main/arrow.png' id='regionicon_"+list[i].REGIONID+"' class='department_arrow_style' /></div>"));
					newDiv.append($("<label class='equipment_name'>"+list[i].REGIONNAME+"</label>"));
					newDiv.append($("<div class='firstCamera_Div'  name='"+mylevel+"'  style='display:none' id='regionFather_"+list[i].REGIONID+"'></div>"));
					regionDiv.append(newDiv);
			 }else{
				 var newDiv=$("<div class='firstUnit_Div' style='margin-left:"+fleft+"px;' id=region_"+list[i].REGIONID+"></div>");				  
					newDiv.append($("<div class='img_region2'" +
							" onclick='imgregion2Click("+list[i].REGIONID+",this);'><img src='image/main/arrow.png' id='regionicon_"+list[i].REGIONID+"' class='department_arrow_style' /></div>"));
					newDiv.append($("<label class='equipment_name'>"+list[i].REGIONNAME+"</label>"));
					newDiv.append($("<div class='firstRegion_Div' style='display:none' name='"+mylevel+"'  id='regionFather_"+list[i].REGIONID+"'></div>"));
					regionDiv.append(newDiv);
			 }
			
		}else{
			var regionDiv =  $("div[id$='regionFather_"+list[i].REGIONHIGH+"']");
			 var level = parseInt(regionDiv.attr('name'));
			
			     var mylevel = level;
			var fleft =  parseInt(regionDiv.css("marginLeft"))+15;
			 if(isIEListener()){
				// fleft = document.getElementById("regionFather_"+list[i].regionHigh).offsetLeft+15;
				 fleft = level*15;
				 //  alert(childleft);
			   }
			 if(list[i+1]==undefined||list[i+1].REGIONHIGH!=list[i].REGIONID){//只有1层
				 var newDiv=$("<div class='firstUnit_Div' style='margin-left:"+fleft+"px;' id=region_"+list[i].REGIONID+"></div>");				  
					newDiv.append($("<div class='img_region3'" +
							" onclick='imgregion3Click("+list[i].REGIONID+",this);' ><img src='image/main/arrow.png' id='regionicon_"+list[i].REGIONID+"' class='department_arrow_style' /></div>"));
					newDiv.append($("<label class='equipment_name'>"+list[i].REGIONNAME+"</label>"));
					newDiv.append($("<div class='firstCamera_Div' style='display:none' name='"+mylevel+"'  id='regionFather_"+list[i].REGIONID+"'></div>"));
					regionDiv.append(newDiv);
			 }else{
				 var newDiv=$("<div class='firstUnit_Div' style='margin-left:"+fleft+"px;' id=region_"+list[i].REGIONID+"></div>");				  
					newDiv.append($("<div class='img_region2'" +
							" onclick='imgregion2Click("+list[i].REGIONID+",this);' ><img src='image/main/arrow.png' id='regionicon_"+list[i].REGIONID+"' class='department_arrow_style' /></div>"));
					newDiv.append($("<label class='equipment_name'>"+list[i].REGIONNAME+"</label>"));
					newDiv.append($("<div class='firstRegion_Div' style='display:none'  name='"+mylevel+"'  id='regionFather_"+list[i].REGIONID+"'></div>"));
					regionDiv.append(newDiv);
			 }
			
			
		}
	}
	

	//icon点击下级列表显示或者隐藏
/*	$(".img_region2").click(function(){
		var iconid=$(this).children("img").attr("id");
		var fid = iconid.split("_")[1];
		var parDiv = $("div[id$='regionFather_"+fid+"']");
		alert(iconid+"|"+fid+parDiv.css("display"));
		if(parDiv.css("display")=="none"){
			parDiv.show();
		//	$(this).parent().children(".three").css("display","inline-block");
			$("#"+iconid).attr("src","image/main/arrow_down.png");
			
		}else{
			parDiv.hide();
			//$("#department"+dm).children().hide();
			$("#"+iconid).attr("src","image/main/arrow.png");
		}
		
	});
	$(".img_region3").click(function(){//级联区域数据
		var iconid=$(this).children("img").attr("id");
		var regionId = iconid.split("_")[1];
		var parDiv = $("div[id$='regionFather_"+regionId+"']");
		alert(regionId+parDiv.css("display"));
		if(parDiv.css("display")=="none"){
			 parDiv.show();
			 $("#"+iconid).attr("src","image/main/arrow_down.png");
	     	 loadCamera(regionId);
	     	 
		}else{
			//消除
			$("#"+iconid).attr("src","image/main/arrow.png");
			parDiv.hide();
			parDiv.empty();
		}
	});*/
		
}



function imgregion2Click(regionId,obj){
	
	var parDiv = $("div[id$='regionFather_"+regionId+"']");
	var icondiv= $("img[id$='regionicon_"+regionId+"']");
	
	if(parDiv.css("display")=="none"){
		icondiv.attr("src","image/main/arrow_down.png");
		parDiv.show();
		
		
	}else{
		icondiv.attr("src","image/main/arrow.png");
		parDiv.hide();
		
	}
}


function imgregion3Click(regionId,obj){
	var parDiv = $("div[id$='regionFather_"+regionId+"']");
	var icondiv= $("img[id$='regionicon_"+regionId+"']");
	if(parDiv.css("display")=="none"){
		 parDiv.show();
		 icondiv.attr("src","image/main/arrow_down.png");
     	 loadCamera(regionId);
		
	}else{
		icondiv.attr("src","image/main/arrow.png");
		parDiv.hide();
		parDiv.empty();
	}
}

/**
 * 加载摄像头列表---------------------------------------------------------------------
 */
function loadCamera(regionId){
	$.ajax({
		url:'video/selectCamere',
		type:'post',
		dataType:'json',
		data:{
			"regionId":regionId
		},
       beforeSend : function() {					
			   ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==0){
				var cameralist = data.result.obj;
				appendToThirdDiv(cameralist,regionId);	
			
			}else{		
						
			}
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
}


function appendToThirdDiv(list,regionId){
	
	var cameraDiv =  $("div[id$='regionFather_"+regionId+"']");
	  
	  var level = parseInt(cameraDiv.attr('name'));
	 
	 cameraDiv.empty();
	for(var i=0;i<list.length;i++){	
		var fleft =  parseInt(cameraDiv.css("marginLeft"))+20;
		 if(isIEListener()){
			// fleft = document.getElementById("regionFather_"+regionId).offsetLeft-35;
			   //alert(fleft+"||"+document.getElementById("regionFather_"+regionId).offsetLeft);
			  fleft =level*15;
			//  alert(fleft+"||"+level);
		   }
		 var newDiv=$("<div class='cameraItem' style='margin-left:"+fleft+"px;' id=camera_"+list[i].CAMERAID+"" +
		 		" ></div>");				  
		 newDiv.append($("<label class='cameraLabel_name' onclick='playVideo("+list[i].CAMERAID+",this)' >"+list[i].DEVICENAME+"</label>" +
					"<a class='Operate' href='page/videoplay.jsp?cameraId="+list[i].CAMERAID+"&cameraName="+list[i].DEVICENAME+"'  target='_blank'>播放</a><a class='Operate' style='margin-left:10px;' onclick='openClickListener("+list[i].CAMERAID+")'>标记</a>"));
			/*newDiv.append($("<div class='firstCamera_Div' style='display:none' id='camera_"+list[i].cameraId+"'></div>"));*/
			cameraDiv.append(newDiv);
	}
}

function playVideo(cameraId,thisop){
	$(".cameraLabel_selected").attr("class","cameraLabel_name");
	thisop.className="cameraLabel_selected";	
	showCameraItemInfo(cameraId);
	
	
}

/**
 * 视频标记经纬度功能---------------------------------------------
 * 
 */
function openClickListener(cameraId){//开启鼠标监听事件	
	if(isOpen==2){
             return;
        }

	selectCameraId=cameraId;	
        $(".Operate").css("background-color","grey");
	selectName=$("div[id$='camera_"+cameraId+"']").children("label");
        $(".cameraLabel_selected").attr("class","cameraLabel_name");
        selectName.attr("class","cameraLabel_selected");
	//alert(selectName);
	isOpen = 2;
	myListener = MEvent.addListener(maplet,"bookmark",showLonlat);
	maplet.setMode("bookmark");
}




function updateLonlat(){
	alert("保存");
}

function showLonlat(data){
	
	if(isOpen==2){		
	var a=data.point;
	longitude=data.point.lon;
	latitude=data.point.lat;
	 creatMarker(longitude+","+latitude);
	if(confirm("确定保存该坐标点?")){
		saveLonlat();
  	  //  MEvent.removeListener(myListener);
	    isOpen=1; 
            $(".Operate").css("background-color","#1C58A5");  
	    
	    
		
	}else{
		maplet.removeLay(markerS);
		loadmap();
  	    MEvent.removeListener(myListener);
        $(".Operate").css("background-color","#1C58A5");
	    isOpen=1;
	}
	
	}
}

function isIEListener(){
//判断IE7\8 兼容性检测
var isIE=!!window.ActiveXObject;
var isIE6=isIE&&!window.XMLHttpRequest;
var isIE8=isIE&&!!document.documentMode;
var isIE7=isIE&&!isIE6&&!isIE8;
 
if(isIE8 || isIE7){
   return true;
    
}else{
	return false;
}


}


function saveLonlat(){
	$.ajax({
		url:'video/updateLonlat',
		type:'post',
		dataType:'json',
		data:{
			"cameraId":selectCameraId,
			"longitude":longitude,
			"latitude":latitude
	
		},
       beforeSend : function() {					
			   ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.resultcode==0){
				alert("保存成功");
				loadmap();
		  	    MEvent.removeListener(myListener);
		  	  maplet.removeAllOverlay();
		      loadmap();
		      findAllCamera();
				//createPopup(longitude+","+latitude);
                                showCameraItemInfo(selectCameraId);
			}else{		
				alert(data.result.resultdesc);
			}
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
}


function showCameraItemInfo(selectCameraId){
   
	$.ajax({
		url:'video/selectLonlat',
		type:'post',
		dataType:'json',
		data:{
			"cameraId":selectCameraId
		},
       beforeSend : function() {					
			   ShowLoadingDiv();// 获取数据之前显示loading图标。
		},
		success:function(data){
			if(data.result.obj!=undefined&&data.result.obj!=null&&data.result.obj!=""&&
			 data.result.obj.LONGITUDE!=undefined&&data.result.obj.LONGITUDE!=""&& data.result.obj.longitude!=""){
				var lonlat = data.result.obj.LONGITUDE+","+data.result.obj.LATITUDE;
				//alert(lonlat)
				//creatMarker(lonlat);
				centerJwd = lonlat;
				level = 14;
                initCenterZoom(lonlat,14);
                                 
			}else{
				alert("该视频暂无坐标信息");
			}
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});
}

function searchPlayInfo(selectCameraId,lonlat){
	$.ajax({
		url:'video/selectPlayInfo',
		type:'post',
		dataType:'json',
		data:{
			"cameraId":selectCameraId
		},
		success:function(data){
			if(data.result.resultcode==0){
				createPopup(lonlat,data.result.obj);
			}else{
				alert("查询播放参数失败");
			}
		}
	});
}

function createPopup(lonlat,obj){
	var html = "";
	maplet.createPopupForMarker(lonlat,html,"image/mapicon/ic_map_video.png",30,500,500);
	//devread.StartTask_Preview(creatMyXml(obj));
	//devread.HideToolBar();
}

function creatMarker(lonlat){
	markerS=maplet.drawMarker(lonlat,"image/mapicon/ic_map_video.png",30);
        
}
function initCenterZoom(centerpoint,zoomvalue){
	maplet.setMapCenter(centerpoint,zoomvalue);
}


function findDimensions() //函数：获取尺寸 
{ 
  winHeight=0;
//获取窗口高度 
  if (window.innerHeight) {
     winHeight = window.innerHeight; 
   } else if ((document.body) && (document.body.clientHeight)) {
     winHeight = document.body.clientHeight; 
   }
//通过深入Document内部对body进行检测，获取窗口大小 
     if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth) 
     { 
          winHeight = document.documentElement.clientHeight; 
          winWidth = document.documentElement.clientWidth; 
     } 
   //  alert(winHeight-64);
    $("#video_out").css("height",winHeight-64);
    $("#video_out").css("width",winWidth);
    $("#video_map").css("height",winHeight-64);
	if(leftmark==1){
    $("#video_map").css("width",winWidth-349);
	}else{
		$("#video_map").css("width",winWidth);
	}
}

 window.onresize=function(){
	 var browserVersion= window.navigator.userAgent.toUpperCase();
 	  
	//获取窗口高度 
	  if (window.innerHeight) {
	     winHeight = window.innerHeight; 
	   } else if ((document.body) && (document.body.clientHeight)) {
	     winHeight = document.body.clientHeight; 
	   }
	//通过深入Document内部对body进行检测，获取窗口大小 
	     if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth) 
	     { 
	          winHeight = document.documentElement.clientHeight; 
	          winWidth = document.documentElement.clientWidth; 
	     } 
	  
	     $("#video_out").css("height",winHeight-64);
	     $("#video_out").css("width",winWidth);
	     $("#video_map").css("height",winHeight-64);
	    if(leftmark==1){
           $("#video_map").css("width",winWidth-349);
	     }else{
		  $("#video_map").css("width",winWidth);
	     }
 };
 
 
 function findAllCamera(){
//	 hasFinish=0;
	 $.ajax({
			url:'video/selectAllCamera',
			type:'post',
			dataType:'json',
			data:{
			},
			success:function(data){	
				
				var list= data.result.obj;
				creatAllCamera(list);
				}
			
		});	
	}

 function creatAllCamera(list){
	     maplet.removeAllOverlay();
		 var len = list.length;
		for (var i = 0; i < len; i++) {
				//maplet.drawMarker(shiplist[i].longitude+","+shiplist[i].latitude, "image/mapicon/ic_map_ship.png",17);
				var html=" <div class='carinfodiv_title'>"+list[i].NAME+"</div>    " +
				"<div class='carinfodiv_left'><a href='page/videoplay.jsp?cameraId="+list[i].CAMERA_ID+"'  target='_blank'>播放视频</a></div>";
        //    var point = getLonlat(shiplist[i].longitude,shiplist[i].latitude);
				var video = list[i];
				if(video!=undefined&&video!=null&&video!=""&&
						video.LONGITUDE!=undefined&&video.LONGITUDE!=""&& video.LATITUDE!=""){
							var lonlat = video.LONGITUDE+","+video.LATITUDE;
							//console.log()
			       maplet.createPopupForMarker(lonlat,html,changeIcon(video.CONTROL_UNIT_ID),21,200,100);
		}
		}
 } 

 
 
 /**
  * 判断是否为null
  */
 function judgeIsNull(value){
 	returnValue="";
 	if(value==null||value=="null"||value==""||value==undefined){
 		return returnValue;
 	}else{	
 		return value;
 	}
 }
 

function changeIcon(uniteId){
	if(uniteId==""||uniteId==null||uniteId=="null"||uniteId==undefined){
		return "image/mapicon/ic_map_gl.png";
	}else if(uniteId==172){
		return "image/mapicon/ic_map_gl.png";
	}else if(uniteId==144){
		return "image/mapicon/ic_map_gh.png";
	}else if(uniteId==142||uniteId==173){
		return "image/mapicon/ic_map_gw.png";
	}else{
		return "image/mapicon/ic_map_yg.png";
	}
	
}

