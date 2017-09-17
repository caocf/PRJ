var maplet;
var currentJwd = "120.76403,30.74283";
var currentZoom = 8;

var taxiinit=0;    //出租车标志

var hasFinish = 1;
var taxincenter = "120.76403,30.74283";

$(document).ready(function(){
	addLinkToGuid();
	loadmap();
	
	initRunDiv(); //初始化菜单栏
	initTag();//初始化菜单栏点击事件
	initTaxiPage();
	
});
window.onresize=function(){
	setLinkLeft2();
};
//刷新地图
setInterval(refreshMap,30000);

//刷新出租车信息
function refreshMap(){



	if(hasFinish==0){
		return;
	}
	taxiinit=1;
	//alert(typeic_choose);
	
		initTaxiPage();
	
	
}

function loadmap(){
	//$("#traffic_map").empty();
	maplet = new XMap("traffic_map",3);
	maplet.setMapCenter(taxincenter,12);
	//initCenterZoom(currentJwd,currentZoom);
	maplet.setScaleVisible(false);//设置比例尺不可见
	maplet.setOverviewVisible(false,false);//鹰眼不可见
        
}

function addLinkToGuid(){
	setLinkLeft2();
	initGuildPic();
	
}

function initTag(){
   /**
    * 车辆种类图标初始化
    */

	//出租车监测点击事件
	$("#tra_taxilink").click(function() {
		location.reload(true);
	});
	//公交车监测点击事件
	$("#tra_buslink").click(function() {
		window.open("http://172.20.18.60/jtj.jsp");
	});
	//船舶监测点击事件
	$("#tra_shiplink").click(function() {
		window.location.href=$("#basePath").val()+"page/main/trafficShip.jsp";
	});
	//路网监测点击事件
	
	$("#tra_roadlink").click(function() {
		window.location.href=$("#basePath").val()+"page/main/trafficRoad.jsp";
	});
	 //行车诱导
	$("#tra_carrunlink").click(function() {
		window.open("http://192.168.170.17/jxydp/mainIndex.jsp");
	});
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
	//maplet.removeAllOverlay();
	//alert("shua xin taxi");
	findTaxiInfo();
	findTaxiData();
	
	
	
}

function findTaxiInfo(){  //查询出租车信息
	hasFinish =0;
	var i = new Date().getTime();
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
				//var taxilist=JSON.parse(data.result.obj);
				//var taxilist = eval('(' + data.result.obj + ')');  
				var taxilist = data.message.obj;  
				var taxipoints  = data.message.map.points;
				markTaxiPoint(taxilist,taxipoints);
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
			//	var taxidata = eval('(' + data.result.obj + ')');  
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
		
	 if (taxipoints != null && taxipoints.length>0) {
		 maplet.removeAllOverlay();        
		for (var i = 0; i < taxipoints.length; i++) {
			   var stateTaxi = "重车";
                           if(taxilist[i].CLKZZZT == 0){
                                stateTaxi = "空车";
                            }

                //maplet.drawMarker(taxilist[i].JD/10000000+","+taxilist[i].WD/10000000, "image/mapicon/ic_map_car.png",17)
				var html=" <div class='carinfodiv_title'>车辆信息</div>    " +
				"<div class='carinfodiv_left'>车辆车牌号&nbsp;："+judgeIsNull(taxilist[i].CPHM)+"</div>"+
				"<div class='carinfodiv_left'>车辆类型&nbsp;："+judgeIsNull(taxilist[i].CLLXMC)+"</div>"+
				"<div class='carinfodiv_left'>车牌类型名称&nbsp;："+judgeIsNull(taxilist[i].CPLXMC)+"</div>"+
				"<div class='carinfodiv_left'>燃料类型名称&nbsp;："+judgeIsNull(taxilist[i].RLLX)+"</div>"+
				"<div class='carinfodiv_left'>所属公司&nbsp;："+judgeIsNull(taxilist[i].YHMC)+"</div>"+
				"<div class='carinfodiv_left'>当前车速&nbsp;："+taxilist[i].SD+"Km/h</div>"+
				"<div class='carinfodiv_left'>行政区划名称&nbsp;："+judgeIsNull(taxilist[i].XZQHMC)+"</div>"+
                                "<div class='carinfodiv_left'>状态&nbsp;："+stateTaxi+"</div>";
			//	var point = getLonlat(taxilist[i].JD/10000000,taxilist[i].WD/10000000);
			//	alert(taxipoints[i].lon+","+taxipoints[i].lat);
				if(taxilist[i].CLKZZZT == 0){
					maplet.createPopupForMarker(taxipoints[i].lon+","+taxipoints[i].lat,html,"image/mapicon/ic_map_car_empty.png",12,400,300);
				}else{
				maplet.createPopupForMarker(taxipoints[i].lon+","+taxipoints[i].lat,html,"image/mapicon/ic_map_car_problem.png",12,400,300);
				}
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
/**
 * 判断是否为null
 */
function judgeIsNullSD(value){
	returnValue="无";
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


function initRunDiv(){
	$("#change_trun").bind({
		  mouseover:function(){
			  //修改图片
				 $(this).css('background-color','#1c5196');   	
              //出现菜单栏
	    	 // setRunDivPos();
	    		$("#runlist").show();
	    	  
	    	 },
	    mouseout:function(){
	    	 $(this).css('background-color','#1f5aa7');   	
	    	 $("#runlist").hide();
	    	 }
	});
	
	$("#runlist").bind({
		  mouseover:function(){
			  //修改图片			
	    		$("#runlist").show();
	    	  
	    	 },
	     mouseout:function(){
	    	 $("#runlist").hide();
	    	 }
	});
	$("#mainguid_tscience").bind({
		  mouseover:function(){
			  //修改图片			
	    		$("#carrunlist").show();
	    	  
	    	 },
	     mouseout:function(){
	    	 $("#carrunlist").hide();
	    	 }
	});
	$("#carrunlist").bind({
		  mouseover:function(){
			  //修改图片			
	    		$("#carrunlist").show();
	    	  
	    	 },
	     mouseout:function(){
	    	 $("#carrunlist").hide();
	    	 }
	});
	
	
}

/**
 * 获取菜单栏的位置
 */
function setRunDivPos(){
	
   if(document.getElementById("linkline")==undefined){
	   return;
   }else{
	var el =  document.getElementById("linkline");
	var setTop = el.offsetHeight;
    var setLeft = el.offsetWidth;
	alert(el);
    var setEl = document.getElementById("runlist");
    setEl.style.top = setTop+"px";
    setEl.style.left = setLeft+"px";
   }
//	var setTop = el.offsetTop + el.offsetHeight +15;
	
}

var transferUrl = "http://10.100.9.199:3000/mapAPI/getLatLon";
