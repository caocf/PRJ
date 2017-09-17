var maplet;
var typeic_choose = "typeic_bus";
var currentJwd = "120.76042769896,30.773992239582";
var currentZoom = 8;
var shipinit=0;
var hasFinish=1;
var shipcenter = "121.05302,30.49433";
$(document).ready(function(){
	addLinkToGuid();
	loadmap();
	initShipPage();;//初始化出租车
});


window.onresize=function(){
	setLinkLeft();
};
//刷新地图
setInterval(refreshMap,30000);

//刷新出租车信息
function refreshMap(){
	  
	    if(hasFinish==0){
	    	return;
	    }else{
	      shipinit=1;
		  initShipPage();
	    }
	
}

function loadmap(){
//	$("#ship_map").empty();
	maplet = new XMap("ship_map",3);
	maplet.setMapCenter("120.76403,30.74283",8);
	//initCenterZoom(currentJwd,currentZoom);
	maplet.setScaleVisible(false);//设置比例尺不可见
	maplet.setOverviewVisible(false,false);//鹰眼不可见
}

function addLinkToGuid(){
	setLinkLeft();
	initGuildPic();
	initTag();
	
}

function initTag(){
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
}


/**
 * ---------------------船户js-----
 */
function initShipPage(){  //初始化页面
	
	 findShipInfo();
	
}

function findShipInfo(){  //查询出租车信息
	hasFinish=0;
	$.ajax({
		url:'multiple/selectShipInfo',
		type:'post',
		dataType:'json',
		data:{
		},
		beforeSend : function() {
			if(shipinit==0){
			ShowLoadingDiv();// 获取数据之前显示loading图标。
			}
		},
		success:function(data){	
		
			if(data.result.resultcode==0){
				//var shiplist=JSON.parse(data.result.obj);
				//var shiplist = eval('(' + data.result.obj + ')');  
				var shiplist = data.result.obj ; 
				var shippoints  = data.result.map.pointlist; 
				markShipPoint(shiplist,shippoints);
				if(shipinit==0){
					
					shipinit=1;
				}
			}else{
				if(shipinit==0){
					CloseLoadingDiv();
					alert("数据请求失败,暂无船舶数据");
					shipinit=1;
				}
				//alert(data.result.resultdesc);
			}
			CloseLoadingDiv();
		}
	});	
}


/**
* 创建chuan图标及浮云
* @param 
* @param 
*/
function markShipPoint(shiplist,shippoints){
	 if (shippoints != null && shippoints.length>0) {
		  maplet.removeAllOverlay();
		 var len = shippoints.length;
		for (var i = 0; i < len; i++) {
				//maplet.drawMarker(shiplist[i].longitude+","+shiplist[i].latitude, "image/mapicon/ic_map_ship.png",17);
				var html=" <div class='carinfodiv_title'>船户信息</div>    " +
				"<div class='carinfodiv_left'>船名&nbsp;："+judgeIsNull(shiplist[i].shipname)+"</div>"+
				"<div class='carinfodiv_left'>方向&nbsp;："+judgeIsNull(shiplist[i].shipdirection)+"</div>"+
				"<div class='carinfodiv_left'>巡航方向&nbsp;："+judgeIsNull(shiplist[i].cruisedirection)+"</div>"+
				"<div class='carinfodiv_left'>速度&nbsp;："+judgeIsNull(shiplist[i].speed)+"</div>"+
				"<div class='carinfodiv_left'>时间&nbsp;："+TimeChange(shiplist[i].adddate)+"</div>";
           //    var point = getLonlat(shiplist[i].longitude,shiplist[i].latitude);
				
				//maplet.createPopupForMarker(point.lon+","+point.lat,html,"image/mapicon/ic_map_ship.png",13,400,300);
			maplet.createPopupForMarker(shippoints[i].lon+","+shippoints[i].lat,html,"image/mapicon/ic_map_ship.png",17,400,300);
		}
		//initCenterZoom(shiplist[10].longitude+","+shiplist[10].latitude,12);
		if(shipinit==0){
			 // initCenterZoom("121.05302,30.49433",10);

			}
	}else{

		if(shipinit==0){
		   alert("暂无船舶地图数据");
		}
	}
	 hasFinish=1;
	 
	
	
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
function TimeChange(value){
	var value1 = judgeIsNull(value);
	return value1.replace("T","");
}

function initCenterZoom(centerpoint,zoomvalue){
	currentJwd = centerpoint;
	currentZoom = zoomvalue;
	maplet.setMapCenter(currentJwd,currentZoom);
}



