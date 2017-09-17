var maplet;
var taxincenter = "120.76403,30.74283";
var currentJwd = "120.76403,30.74283";
var currentZoom = 8;
var taxiinit=0;
var hasFinish=1;
$(document).ready(function(){
	addLinkToGuid();
	loadmap();
    initTaxiPage();;//初始化出租车
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
		   taxiinit=1;
	      initTaxiPage();
	   }
	
}

function loadmap(){
	//$("#taxi_map").empty();
	maplet = new XMap("taxi_map",3);
	maplet.setMapCenter(taxincenter,8);
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
 * --------------------------- 出租车js--------------------------------------
 */
function initTaxiPage(){  //初始化页面
	//loadmap();  //重新加载页面
	
	findTaxiInfo();
	findTaxiData();
	
	
}

function findTaxiInfo(){  //查询出租车信息
	hasFinish=0;
	$.ajax({
		url:'taxidata/searchAllTaxis',
		type:'post',
		dataType:'json',
		data:{
		},
       beforeSend : function() {
			
			if(taxiinit==0){
			ShowLoadingDiv();// 获取数据之前显示loading图标。
			}
		},
		success:function(data){	
			if(data.message.resultcode==0){
				//var taxilist = data.result.obj;
				//var taxilist=JSON.parse(data.result.obj);
				var taxilist = data.message.obj;  
				var taxipoints  = data.message.map.points; 
				
				markTaxiPoint(taxilist,taxipoints);
				//initMapCenter();
				if(taxiinit==0){
					
					taxiinit=1;
				}
			}else{
				if(taxiinit==0){
					CloseLoadingDiv();
					alert(data.message.resultdesc);
					taxiinit=1;
				}
			}
			CloseLoadingDiv();
		}
	});	
}

function findTaxiData(){  //查询出租车统计数据
	
	$.ajax({
		url:'taxidata/searchTaxiData',
		type:'post',
		dataType:'json',
		data:{
		},
		success:function(data){	
			if(data.message.resultcode==0){
				//var taxidata=JSON.parse(data.result.obj);
				var taxidata =  data.message.obj ;
				writeTaxiData(taxidata);
			}else{
				
			}
		}
	});	
}

function writeTaxiData(taxidata){
	
//	$("#onlineTaxinum").val(taxidata.taxiActNum);
	$("#onlineTaxinum").empty();
	$("#onlineTaxinum").append(taxidata.taxiActNum);
	$("#allTaxinum").empty();
	$("#allTaxinum").append(taxidata.taxiAllNum);
	$("#avgSpeed").empty();
	$("#avgSpeed").append(taxidata.taxiAvgSpeed);
/*	$("#allTaxinum").val(taxidata.taxiAllNum);
	$("#avgSpeed").val(taxidata.taxiAvgSpeed);*/
	$("#taxi_runstate").css('display','block'); 

}

/**
 * 创建出租车图标及浮云
 * @param 
 * @param 
 */
function markTaxiPoint(taxilist,taxipoints){
	 if (taxilist != null && taxipoints.length>0) {
        maplet.removeAllOverlay();
		for (var i = 0; i < taxipoints.length; i++) {
                          var stateTaxi = "重车";
                           if(taxilist[i].CLKZZZT == 0){
                                stateTaxi = "空车";
                            }
var html=" <div class='carinfodiv_title'>车辆信息</div>    " +
				"<div class='carinfodiv_left'>车辆车牌号&nbsp;："+judgeIsNull(taxilist[i].CPHM)+"</div>"+
				"<div class='carinfodiv_left'>车辆类型&nbsp;："+judgeIsNull(taxilist[i].CLLXMC)+"</div>"+
				"<div class='carinfodiv_left'>车牌类型名称&nbsp;："+judgeIsNull(taxilist[i].CPLXMC)+"</div>"+
				"<div class='carinfodiv_left'>燃料类型名称&nbsp;："+judgeIsNull(taxilist[i].RLLX)+"</div>"+
				"<div class='carinfodiv_left'>所属公司&nbsp;："+judgeIsNull(taxilist[i].YHMC)+"</div>"+
				"<div class='carinfodiv_left'>当前车速&nbsp;："+taxilist[i].SD+"Km/h</div>"+
				"<div class='carinfodiv_left'>行政区划名称&nbsp;："+judgeIsNull(taxilist[i].XZQHMC)+"</div>"+
                                "<div class='carinfodiv_left'>状态&nbsp;："+stateTaxi+"</div>";
	
if(taxilist[i].CLKZZZT == 0){
	maplet.createPopupForMarker(taxipoints[i].lon+","+taxipoints[i].lat,html,"image/mapicon/ic_map_car_empty.png",12,400,300);
}else{
maplet.createPopupForMarker(taxipoints[i].lon+","+taxipoints[i].lat,html,"image/mapicon/ic_map_car_problem.png",12,400,300);
}
//maplet.createPopupForMarker(taxipoints[i].lon+","+taxipoints[i].lat,html,"image/mapicon/ic_map_car.png",12,400,300);
		}

                   
}else{
			if(taxiinit==0){
				alert("暂无出租车地图数据");
			
			}
			//alert("暂无数据");
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

