var maplet;
var currentJwd = "120.76403,30.74283";
var currentZoom = 8;
var shipinit=0;    //船舶标志
var hasFinish = 1;

var shipcenter = "121.05302,30.49433";
$(document).ready(function(){
	addLinkToGuid();
	loadmap();
	initRunDiv(); //初始化菜单栏
	initTag();//初始化菜单栏点击事件
	initShipPage();
	
});
window.onresize=function(){
	setLinkLeft2();
};
//刷新地图
setInterval(refreshMap,30000);


function refreshMap(){
	if(hasFinish==0){
		return;
	}	
	shipinit=1;
	initShipPage();
	
}

function loadmap(){
	//$("#traffic_map").empty();
	maplet = new XMap("traffic_map",3);
	maplet.setMapCenter(shipcenter,8);
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
		window.location.href=$("#basePath").val()+"page/main/trafficTaxi.jsp";
	});
	//公交车监测点击事件
	$("#tra_buslink").click(function() {
		window.open("http://172.20.18.60/jtj.jsp");
	});
	//船舶监测点击事件
	$("#tra_shiplink").click(function() {
		//window.location.href=$("#basePath").val()+"page/main/trafficShip.jsp";
		location.reload(true);
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
 * ---------------------船户js-----
 */
function initShipPage(){  //初始化页面
	//alert("shua xin ship");
	
	// $("#taxi_runstate").css('display','none'); 
	 if( shipinit==0){
		// loadmap();
		 
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
				var shippoints  = data.result.map.pointlist;
				markShipPoint(shiplist,shippoints);
				// findGcdInfo();
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
function markShipPoint(shiplist,shippoints){
	 
	 if (shippoints != null && shippoints.length>0) {		
		 maplet.removeAllOverlay();
		 var len = shippoints.length;
		for (var i = 0; i < len; i++) {
				var html=" <div class='carinfodiv_title'>船户信息</div>    " +
				"<div class='carinfodiv_left'>船名&nbsp;："+judgeIsNull(shiplist[i].shipname)+"</div>"+
				"<div class='carinfodiv_left'>方向&nbsp;："+judgeIsNull(shiplist[i].shipdirection)+"</div>"+
				"<div class='carinfodiv_left'>巡航方向&nbsp;："+judgeIsNull(shiplist[i].cruisedirection)+"</div>"+
				"<div class='carinfodiv_left'>速度&nbsp;："+shiplist[i].speed+"</div>"+
				"<div class='carinfodiv_left'>时间&nbsp;："+TimeChange(shiplist[i].adddate)+"</div>";
				
				var shipname = judgeIsNull(shiplist[i].shipname);
				if(shipname.indexOf('海巡')>=0)
				{
					maplet.createPopupForMarker(shippoints[i].lon+","+shippoints[i].lat,html,"image/mapicon/hxt.png",18,400,300);
				}
				else
				{
					var shiptype = shiplist[i].shiptype;
					if(shiptype == 1)
					{
						maplet.createPopupForMarker(shippoints[i].lon+","+shippoints[i].lat,html,"image/mapicon/ds.png",18,400,300);
					}
					else
					{
						maplet.createPopupForMarker(shippoints[i].lon+","+shippoints[i].lat,html,"image/mapicon/ic_map_ship.png",18,400,300);
					}
				}
		}

		if(shipinit==0){
		 // initCenterZoom("121.05302,30.49433",8);
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


function findGcdInfo(){
	$.ajax({
		url:'multiple/showGcdInfo',
		type:'post',
		dataType:'json',
		data:{
		},
		success:function(data){	
			if(data.result.resultcode==0){
				var gcdlist = data.result.obj ; 
				creatGcdList(gcdlist);
				//markGcdPoint(gcdlist);
			}
		}
	});	
}


function creatGcdList(list){
	$("#gcdTb").empty();
	var html ="";
	for(var i=0;i<list.length;i++){
		var lon = list[i].dtxzb;
		var lat = list[i].dtyzb;
		html = html+"<tr class='gcz_line'>"+
		       "<td><a style='cursor:pointer' onclick='showGcd("+lon+","+lat+")'>"+list[i].gcdmc+"</a></td></tr>";
		searchGcdPoint(list[i]);
	}
	$("#gcdTb").append(html);
}

function showGcd(lon,lats){
	var lonlats = lon+","+lats;
	initCenterZoom(lonlats,8);
}

function searchGcdPoint(gcdxx){
	$.ajax({
		url:'multiple/showTrafficByGcd',
		type:'post',
		dataType:'json',
		data:{
			'gcdid':gcdxx.gcdid
		},
		success:function(data){	
			if(data.result.resultcode==0){
				var gcdtj = data.result.obj ; 
				markGcdPoint(gcdxx,gcdtj);
			//	markGcdPoint(gcdlist);
			}
		}
	});	
	
}
function markGcdPoint(gcdxx,gcdtj){
	if (gcdtj != ""||gcdxx != " " ) {
		var lon = gcdxx.dtxzb;
		var lat = gcdxx.dtyzb;
				var html=" <div class='gcd_title'>观测点信息</div>    " +
				"<div class='gcd_line'>观测点名称&nbsp;："+judgeIsNull(gcdxx.gcdmc)+"</div>"+
				"<div class='gcd_line'>观测点位置&nbsp;："+judgeIsNull(gcdxx.wz)+"</div>"+
				"<div><table class='gcd_table'>" +
				"<tr>"+
				"<th>统计时间</th><th>人工船舶数量(艘)</th><th>人工船舶吨位(万吨)</th>"+
				"</tr>"+
				"<tr>"+
				"<td>日统计</td><td>"+gcdtj.dayrecords.cbsl+"</td>" +
				"<td>"+gcdtj.dayrecords.cbdw+"</td>"+
				"<tr>"+
				"<tr>"+
				"<td>月统计</td><td>"+gcdtj.monthrecords.cbsl+"</td>" +
				"<td>"+gcdtj.monthrecords.cbdw+"</td>"+
				"<tr>"+
				"<tr>"+
				"<td>年统计</td><td>"+gcdtj.yearrecords.cbsl+"</td>" +
				"<td>"+gcdtj.yearrecords.cbdw+"</td>"+
				"<tr>"+
				
				"</table><div>";
				maplet.createPopupForMarker(lon+","+lat,html,"image/mapicon/traffic.png",20,370,250);
	
	}
	
}
