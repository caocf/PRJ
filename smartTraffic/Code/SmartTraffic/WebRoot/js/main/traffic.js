var maplet;
var typeic_choose = "typeic_bus";
var currentJwd = "120.76403,30.74283";
var currentZoom = 8;
var shipinit=0;    //船舶标志
var taxiinit=0;    //出租车标志
var trafficinit=0; //交调标志
var businit = 0;  //公交车标志
var hasFinish = 1;
var taxincenter = "120.76403,30.74283";
var trafficcenter = "120.76403,30.74283";
var shipcenter = "120.76403,30.74283";
$(document).ready(function(){
	addLinkToGuid();
	loadmap();
	
	initRunDiv(); //初始化菜单栏
	initTag();//初始化菜单栏点击事件
	
});
window.onresize=function(){
	setLinkLeft2();
};
//刷新地图
setInterval(refreshMap,20000);

//刷新出租车信息
function refreshMap(){
	shipinit=1;
	taxiinit=1;
	trafficinit=1;
	if(hasFinish==0){
		return;
	}
	//alert(typeic_choose);
	if(typeic_choose == "typeic_taxi"){
		initTaxiPage();
	}
	if(typeic_choose == "typeic_ship"){
		initShipPage();
	}
	if(typeic_choose == "typeic_road"){
		initRoadPage();
	}
	
}

function loadmap(){
	//$("#traffic_map").empty();
	maplet = new XMap("traffic_map",3);
	maplet.setMapCenter("120.76403,30.74283",12);
	//initCenterZoom(currentJwd,currentZoom);
	maplet.setScaleVisible(false);//设置比例尺不可见
	maplet.setOverviewVisible(false,false);//鹰眼不可见
        
}

function addLinkToGuid(){
	setLinkLeft2();
	initGuildPic();
	
}

function initTag(){

	//出租车监测点击事件
	$("#tra_taxilink").click(function() {
		window.location.href=$("#basePath").val()+"page/main/trafficTaxi.jsp";
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
	 if(taxiinit==0){
		 loadmap();
	 }
	findTaxiInfo();
	findTaxiData();
	
	
	
}

function findTaxiInfo(){  //查询出租车信息
	hasFinish =0;
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
				markTaxiPoint(taxilist);
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
function markTaxiPoint(taxilist){
		 maplet.removeAllOverlay();

	 if (taxilist != null && taxilist.length>0) {
		
		
                 //alert(taxilist.length);
		for (var i = 0; i < taxilist.length; i++) {
                                   
				//maplet.drawMarker(taxilist[i].JD/10000000+","+taxilist[i].WD/10000000, "image/mapicon/ic_map_car.png",17)
				var html=" <div class='carinfodiv_title'>车辆信息</div>    " +
				"<div class='carinfodiv_left'>车辆车牌号&nbsp;："+judgeIsNull(taxilist[i].CPHM)+"</div>"+
				"<div class='carinfodiv_left'>车辆类型&nbsp;："+judgeIsNull(taxilist[i].CLLXMC)+"</div>"+
				"<div class='carinfodiv_left'>车牌类型名称&nbsp;："+judgeIsNull(taxilist[i].CPLXMC)+"</div>"+
				"<div class='carinfodiv_left'>燃料类型名称&nbsp;："+judgeIsNull(taxilist[i].RLLX)+"</div>"+
				"<div class='carinfodiv_left'>所属公司&nbsp;："+judgeIsNull(taxilist[i].YHMC)+"</div>"+
				"<div class='carinfodiv_left'>当前车速&nbsp;："+taxilist[i].SD+"Km/h</div>"+
				"<div class='carinfodiv_left'>行政区划名称&nbsp;："+judgeIsNull(taxilist[i].XZQHMC)+"</div>";
			//	var point = getLonlat(taxilist[i].JD/10000000,taxilist[i].WD/10000000);
				maplet.createPopupForMarker(taxilist[i].point.lon+","+taxilist[i].point.lan,html,"image/mapicon/ic_map_car.png",12,400,300);
		}
 
                      if(taxiinit==0){
                    	  
                           //   alert(taxilist[0].JD/10000000+","+taxilist[0].WD/10000000);
                       //     initCenterZoom(taxilist[0].JD/10000000+","+taxilist[0].WD/10000000,8);
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
 * ---------------------船户js-----
 */
function initShipPage(){  //初始化页面
	//alert("shua xin ship");
	
	 $("#taxi_runstate").css('display','none'); 
	 if( shipinit==0){
		 loadmap();
	 }
	 findShipInfo();
	
}

function findShipInfo(){  //查询出租车信息
	hasFinish = 0;
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
//				var shiplist = eval('(' + data.result.obj + ')'); 
				var shiplist = data.result.obj ; 
				markShipPoint(shiplist);
				if(shipinit==0){
					
					shipinit=1;
				}
			}else{
				if(shipinit==0){
					CloseLoadingDiv();
					alert("数据请求失败,暂无船舶数据");
					shipinit=1;
				}
				
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
function markShipPoint(shiplist){
	 maplet.removeAllOverlay();
	 if (shiplist != null && shiplist.length>0) {		
		 var len = shiplist.length;
		for (var i = 0; i < len; i++) {
			//	maplet.drawMarker(shiplist[i].longitude+","+shiplist[i].latitude, "image/mapicon/ic_map_ship.png",17);
				var html=" <div class='carinfodiv_title'>船户信息</div>    " +
				"<div class='carinfodiv_left'>船名&nbsp;："+judgeIsNull(shiplist[i].shipname)+"</div>"+
				"<div class='carinfodiv_left'>方向&nbsp;："+judgeIsNull(shiplist[i].shipdirection)+"</div>"+
				"<div class='carinfodiv_left'>巡航方向&nbsp;："+judgeIsNull(shiplist[i].cruisedirection)+"</div>"+
				"<div class='carinfodiv_left'>速度&nbsp;："+shiplist[i].speed+"</div>"+
				"<div class='carinfodiv_left'>时间&nbsp;："+TimeChange(shiplist[i].adddate)+"</div>";
		//		var point = getLonlat(shiplist[i].longitude,shiplist[i].latitude);
				
				maplet.createPopupForMarker(shiplist[i].longitude+","+shiplist[i].latitude,html,"image/mapicon/ic_map_ship.png",17,400,300);
		}

		if(shipinit==0){
		  initCenterZoom("121.05302,30.49433",8);
		}
	}else{
		if(shipinit==0){
			alert("暂无船舶地图数据");
		}
	}
	 hasFinish=1;
}



/**
 * ---------------------路网js-----
 */
function initRoadPage(){  //初始化页面
	 $("#taxi_runstate").css('display','none'); 
	 findRoadList();
	
}
function findRoadList(){
	hasFinish=0;
	$.ajax({
		url:'highway/selectTransDataTimeCS',
		type:'post',
		dataType:'json',
		data:{
		},
       beforeSend : function() {		
			if(trafficinit==0){
			   ShowLoadingDiv();// 获取数据之前显示loading图标。
			}
		},
		success:function(data){
			if(data.result.resultcode==0){
				var roadlist = data.result.obj;
				findRoadLine(roadlist);
				if(trafficinit==0){
                     initCenterZoom(trafficcenter,10);
					CloseLoadingDiv();
					trafficinit=1;
				}
				//initMapCenter();
			}else{
				if(trafficinit==0){
					CloseLoadingDiv();
					alert(data.result.resultdesc);
					trafficinit=1;
				}
				
			}
		}
	});
}


function findRoadLine(roadlist){
	if(trafficinit==0){
		
	}else{
	maplet.removeAllOverlay();
	}
   if(roadlist!=null && roadlist.length>0){
	   
	for(var i=0;i<roadlist.length;i++){
		var road = roadlist[i];		
		if(road.sxLineCode==300000){
			markLine(road.sxList,colorTransfer(road.YJD_S));
                         }
	         if(road.xxLineCode==300000){
			markLine(road.xxList,colorTransfer(road.YJD_X));	//xiaxing
		}     
	}
   }
   hasFinish=1;
            
	   
		
}

function markLine(stakelist,color){
	var trafficCenter = "120.76042769896,30.773992239582";
	if(stakelist!=null||stakelist.length>0){
	for(var i=0;i<stakelist.length-1;i++){
		var stakeinfo = stakelist[i];
		var next = stakelist[i+1];
		var lineString = stakeinfo.longitude+","+stakeinfo.latitude+";"+
		next.longitude+","+next.latitude;
		 maplet.drawLine(lineString,color,0,10); 
		
	}
         if(stakelist.length>0&&trafficinit==0){
         trafficcenter = stakelist[0].longitude+","+stakelist[0].latitude;
}
	
	}
}

//1、2、3、4、5分别对应非常畅通（绿色）、畅通（蓝色）、一般（黄色）、拥挤（橘黄色）、堵塞（红色）
function colorTransfer(yjd){
	if(yjd==1){
		return "#008000";
	}else if(yjd==2){
		return "#99CC00";
	}else if(yjd==3){
		return "#FFE400";
	}else if(yjd==4){
		return "#FFA500";
	}else{
		return "#FF0000";
	}	
}



//-------------------------路网监测js结束----------------------------------------

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
	var e2 =  document.getElementById("mainguid_tscience");
	var setTop2 = e2.offsetHeight;
	var setLeft2 = e2.offsetWidth;
	var setTop = el.offsetHeight;
    var setLeft = el.offsetWidth;

    var setEl = document.getElementById("runlist");
    var setE2 = document.getElementById("carrunlist");
    
    setEl.style.top = setTop+"px";
    setEl.style.left = setLeft+"px";
    
   
    setE2.style.top = setTop2+"px";
    setE2.style.left = setLeft2+"px";
    
   }
//	var setTop = el.offsetTop + el.offsetHeight +15;
	
}
