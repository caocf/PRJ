var maplet;
var typeic_choose = "typeic_bus";
var currentJwd = "120.76403,30.74283";
var currentZoom = 5;
var trafficinit=0; //交调标志
var list_data = "" ;
var hasFinish = 1;
var today = new Date();
var trafficcenter = "120.76403,30.74283";

$(document).ready(function(){
	loadmap();
	addLinkToGuid();
	initRunDiv(); //初始化菜单栏
	initTag();//初始化菜单栏点击事件
	init();
	
});

function init(){
	showMarkInMap();
	findJtl();
	writeLine();
}

window.onresize=function(){
	setLinkLeft2();
};
//刷新地图
setInterval(refreshMap,120000);

//刷新
function refreshMap(){
	   maplet.clearTypeLays("roadline");
	   writeLine();	
	
}
function writeLine(){
	maplet.drawRoadLineByStake ("roadline",true,null, null,null,cbk);
}
function cbk(){
	
	
}
function findJtl(){
	$.ajax({
		url:'highway/selectJdlx',
		type:'post',
		dataType:'json',
		data:{
		},
		success:function(data){
			if(data.result.resultcode==0){
				var roadlist = data.result.obj;
				$("#jtllxmc option").remove();
		if(roadlist!=null&&roadlist.length>0){
				for(var i=0;i<roadlist.length;i++){
					$("#jtllxmc").append("<option value="+roadlist[i].lxdm+">"+roadlist[i].lxjc+"</option>");
				}
				jtlxselectfuncNew(roadlist[0].lxdm);
				
		}
			}else{				
			}
		}
	});
	$('#jtllxmc').change(function(){ 
		jtlxselectfuncNew($(this).children('option:selected').val());
	});
}

function loadmap(){
	//$("#tjsj").hide();
	//$("#traffic_map").empty();
	maplet = new XMap("traffic_map",3);
	maplet.setMapCenter("120.76403,30.74283",7);
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
		location.reload(true);
	});
	 //行车诱导
	$("#tra_carrunlink").click(function() {
		window.open("http://192.168.170.17/jxydp/mainIndex.jsp");
	});
	   /**
	    * 比例尺图标初始化
	    *//*	
	$("#zoomLarge").click(function() {
		maplet.zoomIn();
		//initCenterZoom(currentJwd,currentZoom+1);
	});	
	$("#zoomSmall").click(function() {
		maplet.zoomOut();
		//initCenterZoom(currentJwd,currentZoom-1);
	});*/
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
	$("#link_Left_other").click(function(){
		$("#tjsj").toggle();
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
    var setEl = document.getElementById("runlist");
    setEl.style.top = setTop+"px";
    setEl.style.left = setLeft+"px";
   }
//	var setTop = el.offsetTop + el.offsetHeight +15;
	
}

function jtlxselectfunc(value){
	$.ajax({
		url:'highway/selectJdInfo',
		type:'post',
		dataType:'json',
		data:{
			lxdm:value
		},
		success:function(data){
			if(data.result.resultcode==0){
				var jtl = data.result.obj.dayData;
				var jtl_m = data.result.obj.monthData;
				var jtl_y = data.result.obj.yearData;
				
				$("#jdcdl").empty();
				$("#jdcdl").append(jtl.jdcdlshj);
				$("#qcdl").empty();
				$("#qcdl").append(jtl.qcdlshj);
				$("#dxhc").empty();
				$("#dxhc").append(jtl.dxhc);
				$("#xsl").empty();
				$("#xsl").append(jtl.xsl);
				$("#syjtl").empty();
				$("#syjtl").append(jtl.syjtl);
				$("#tjsj").empty();
				$("#tjsj").append(jtl.tjsj);
				
			}else{	
				$("#jdcdl").empty();
				$("#qcdl").empty();
				$("#tjsj").empty();
				$("#dxhc").empty();
				
				$("#xsl").empty();
			
				$("#syjtl").empty();
			
			}
		}
	});
}


function showMarkInMap(num){
	$.ajax({
		url:'highway/selectGczInfo',
		type:'post',
		dataType:'json',
		data:{
		},
		success:function(data){
			if(data.result.resultcode==0){
				list_data=data.result.obj;
				   markDcz(list_data);
				}
					
		}

	});
}

function markDcz(list){
	if(list!=null&&list!=""&&list.length>0){
	for(var i=0;i<list.length;i++){
		var obj = list[i];
		var lon=obj.jd;
		var lat=obj.wd;
		var lonlat = lon+","+lat;
	if(obj!=""&&obj.jd!="null"&&obj.jd!=""&&obj.wd!="null"&&obj.wd!=""&&obj.jd!=null&&obj.wd!=null){
		 var html=" <div class='carinfodiv_title'>交调站信息</div>    " +
			"<div class='carinfodiv_left'>调查站名称&nbsp;："+judgeIsNull(obj.dczmc)+"</div>"+
			"<div class='carinfodiv_right' style='margin-left:20px;'>路线名称&nbsp;："+judgeIsNull(obj.lxmc)+"</div>"+
			"<div><table  cellpadding='4px' cellspacing='0' class='roadlistPop'>" +
			"<tr><th width='30%'>统计名称</th><th>年统计("+obj.yearData.tjsj+")</th><th>月统计("+obj.monthData.tjsj+")</th>" +
					"<th>日统计("+obj.dayData.tjsj+")</th></tr>" +
			"<tr class='tip_road_line'>" +
			"<td class='tip_road_left'>机动车当量数合计</td>" +
			"<td class='tip_road_right'>"+isNull(obj.yearData.jdcdlshj)+"</td>" +
			"<td class='tip_road_right'>"+isNull(obj.monthData.jdcdlshj)+"</td>" +
			"<td class='tip_road_right'>"+isNull(obj.dayData.jdcdlshj)+"</td>" +
			"</tr>" +
			"<tr class='tip_road_line'>" +
			"<td class='tip_road_left'>汽车当量数合计</td>" +
			"<td class='tip_road_right'>"+isNull(obj.yearData.qcdlshj)+"</td>" +
			"<td class='tip_road_right'>"+isNull(obj.monthData.qcdlshj)+"</td>" +
			"<td class='tip_road_right'>"+isNull(obj.dayData.qcdlshj)+"</td>" +
			"</tr>" +
			"<tr class='tip_road_line'>" +
			"<td class='tip_road_left'>大型货车</td>" +
			"<td class='tip_road_right'>"+isNull(obj.yearData.dxhc)+"</td>" +
			"<td class='tip_road_right'>"+isNull(obj.monthData.dxhc)+"</td>" +
			"<td class='tip_road_right'>"+isNull(obj.dayData.dxhc)+"</td>" +
			"</tr>" +
			"<tr class='tip_road_line'>" +
			"<td class='tip_road_left'>行驶量(万车公里)</td>" +
			"<td class='tip_road_right'>"+isNull(obj.yearData.xsl)+"</td>" +
			"<td class='tip_road_right'>"+isNull(obj.monthData.xsl)+"</td>" +
			"<td class='tip_road_right'>"+isNull(obj.dayData.xsl)+"</td>" +
			"</tr>" +
			"<tr class='tip_road_line'>" +
			"<td class='tip_road_left'>适应交通量(辆/日)</td>" +
			"<td class='tip_road_right' colspan='3' style='text-align:center;'>"+isNull(obj.dayData.syjtl)+"</td>" +
			"</tr>" +
			"<tr class='tip_road_line'>" +
			"<td class='tip_road_left'>拥挤度</td>" +
			"<td class='tip_road_right'>"+isNull(obj.yearData.jtyjd)+"</td>" +
			"<td class='tip_road_right'>"+isNull(obj.monthData.jtyjd)+"</td>" +
			"<td class='tip_road_right'>"+isNull(obj.dayData.jtyjd)+"</td>" +
			"</tr>" +
			"</table>" +
			"<a style='float:right;margin-right:10px;margin-top:10px;' href='"+$("#basePath").val()+"/page/statics/Statistic.jsp?num=0' target='_blank'>交调量分析</a></div>";
		 if(obj.lx.substr(0,1)=='G' ||obj.lx.substr(0,1)=='g'){
			 maplet.createPopupForMarker(lonlat,html,"image/mapicon/ic_map_traffic_gd.png",17,620,410);
		 }else{
		    maplet.createPopupForMarker(lonlat,html,"image/mapicon/ic_map_traffic_sd.png",17,620,410);
		 }

		 
			
			}
		}
	//$("#gsd_stateguid").show();
	}
					
}
//**************************************************************************
function jtlxselectfuncNew(lxdm){
	clearTb();
	findYMData(lxdm,'highway/selectYearData',1);
	findYMData(lxdm,'highway/selectMonData',2);
	findDDate(lxdm);
}
/** 最新的查询流量数据--年,月:**/
function findJdInfo(lxdm,lxjc){
	//var obj = "";
	$.ajax({
		url:'highway/selectJdInfo',
		type:'post',
		dataType:'json',
		data:{
			'lxdm':lxdm,
			'lxjc':lxjc
		},
		success:function(data){
			return data.result.obj;

		}
	});
	
}
function findYMData(lxdm,actionName,type){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'lxdm':lxdm
		},
		success:function(data){
			var obj = "";
			if(data.result.resultcode==0){
				obj  = data.result.obj;
			}
			if(type==1){
			showTbYear(obj);
			}else{
			showTbMon(obj);
			
		}
		}
	});
}

/** 最新的查询流量数据--年,月:**/
function clearTb(){
	$(".mtjsj").text("月统计时间"   );
	$(".ytjsj").text("年统计时间"   );
	$(".dtjsj").text("日统计时间"   );
	$("#jdcdl_m").text("统计中");
	$("#qcdl_m").text("统计中");
	$("#dxhc_m").text("统计中");
	$("#xsl_m").text("统计中");
	/*$("#syjtl_m").text("统计中");*/
	$("#yjd_m").text("统计中");
	$("#jdcdl_d").text("统计中");
	$("#qcdl_d").text("统计中");
	$("#dxhc_d").text("统计中");
	$("#xsl_d").text("统计中");
	$("#syjtl_d").text("统计中");
	$("#yjd_d").text("统计中");
	$("#jdcdl_y").text("统计中");
	$("#qcdl_y").text("统计中");
	$("#dxhc_y").text("统计中");
	$("#xsl_y").text("统计中");
/*	$("#syjtl_y").text("统计中");*/
	$("#yjd_y").text("统计中");
}
function findDDate(lxdm){
	$.ajax({
		url:'highway/selectDayData',
		type:'post',
		dataType:'json',
		data:{
			'lxdm':lxdm
		},
		success:function(data){
			if(data.result.resultcode==0){
				var obj =  data.result.obj;
				if(obj!="null"&&obj!=""&&obj!=null){
					$(".dtjsj").text("日统计时间("+obj.tjsj+")");
					$("#jdcdl_d").text(isNull(obj.jdcdlshj));
					$("#qcdl_d").text(isNull(obj.qcdlshj));
					$("#dxhc_d").text(isNull(obj.dxhc));
					$("#xsl_d").text(isNull(obj.xsl));
					$("#syjtl_d").text(isNull(obj.syjtl));
					$("#yjd_d").text(isNull(obj.jtyjd));
					
				}else{
					var mon =(new Date()).getMonth()+1;
					mon = ""+mon;
					var ye = (new Date()).getFullYear();
					if( mon.length==1){
						mon = "0"+mon;
					}
					var da = ""+(new Date()).getDate();
					if(da.length==1){
						da = "0"+da;
					}
					$(".dtjsj").text("日统计时间("+ye+mon+da+")");
					$("#jdcdl_d").text("暂无数据");
					$("#qcdl_d").text("暂无数据");
					$("#dxhc_d").text("暂无数据");
					$("#xsl_d").text("暂无数据");
					$("#syjtl_d").text("暂无数据");
					$("#yjd_d").text("暂无数据");
				}
				
			}
		}
	});
}

function showTbMon(obj){
	
	if(obj!="null"&&obj!=""&&obj!=null){
		$(".mtjsj").text("月统计时间("+obj.tjsj+")");
		$("#jdcdl_m").text(isNull(obj.jdcdlshj));
		$("#qcdl_m").text(isNull(obj.qcdlshj));
		$("#dxhc_m").text(isNull(obj.dxhc));
		$("#xsl_m").text(isNull(obj.xsl));
		/*$("#syjtl_m").text(obj.syjtl);*/
		$("#yjd_m").text(isNull(obj.jtyjd));
		
	}else{
		var mon =(new Date()).getMonth()+1;
		mon = ""+mon;
		var ye = (new Date()).getFullYear();
		if( mon.length==1){
			mon = "0"+mon;
		}
		
		$(".mtjsj").text("月统计时间("+ye+mon+")");
		$("#jdcdl_m").text("暂无数据");
		$("#qcdl_m").text("暂无数据");
		$("#dxhc_m").text("暂无数据");
		$("#xsl_m").text("暂无数据");
		/*$("#syjtl_m").text("暂无数据");*/
		$("#yjd_m").text("暂无数据");
	}
}

function showTbYear(obj){
	if(obj!="null"&&obj!=""&&obj!=null){
		$(".ytjsj").text("年统计时间("+obj.tjsj+")");
		$("#jdcdl_y").text(isNull(obj.jdcdlshj));
		$("#qcdl_y").text(isNull(obj.qcdlshj));
		$("#dxhc_y").text(isNull(obj.dxhc));
		$("#xsl_y").text(isNull(obj.xsl));
		/*$("#syjtl_y").text(obj.syjtl);*/
		$("#yjd_y").text(isNull(obj.jtyjd));
	}else{
		var ye = (new Date()).getFullYear();
		$(".ytjsj").text("年统计时间("+ye+")");
		$("#jdcdl_y").text("暂无数据");
		$("#qcdl_y").text("暂无数据");
		$("#dxhc_y").text("暂无数据");
		$("#xsl_y").text("暂无数据");
		/*$("#syjtl_y").text("暂无数据");*/
		$("#yjd_y").text("暂无数据");
	}
}

function isNull(obj){
	if(obj!="null"&&obj!=""&&obj!=null){
		return obj;
	}else{
		return "暂无数据";
	}
}





function findDDateNew(lxdm){
	$.ajax({
		url:'highway/selectDayData',
		type:'post',
		dataType:'json',
		data:{
			'lxdm':lxdm
		},
		success:function(data){
			if(data.result.resultcode==0){
				var obj =  data.result.obj;
				if(obj!="null"&&obj!=""&&obj!=null){
					$(".dtjsj").text("日统计时间("+obj.tjsj+")");
					$("#jdcdl_d").text(isNull(obj.jdcdlshj));
					$("#qcdl_d").text(isNull(obj.qcdlshj));
					$("#dxhc_d").text(isNull(obj.dxhc));
					$("#xsl_d").text(isNull(obj.xsl));
					$("#syjtl_d").text(isNull(obj.syjtl));
					$("#yjd_d").text(isNull(obj.jtyjd));
					
				}else{
					var mon =(new Date()).getMonth()+1;
					mon = ""+mon;
					var ye = (new Date()).getFullYear();
					if( mon.length==1){
						mon = "0"+mon;
					}
					var da = ""+(new Date()).getDate();
					if(da.length==1){
						da = "0"+da;
					}
					$(".dtjsj").text("日统计时间("+ye+mon+da+")");
					$("#jdcdl_d").text("暂无数据");
					$("#qcdl_d").text("暂无数据");
					$("#dxhc_d").text("暂无数据");
					$("#xsl_d").text("暂无数据");
					$("#syjtl_d").text("暂无数据");
					$("#yjd_d").text("暂无数据");
				}
				
			}
		}
	});
}