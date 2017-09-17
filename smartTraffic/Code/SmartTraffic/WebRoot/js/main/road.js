var maplet;  //地图
var centerpoint = "120.76403,30.74283";//中心坐标
var zoom = 8;
var roadinit=0;
$(document).ready(function(){
	init();	
});
window.onresize=function(){
	setLinkLeft();
};
//刷新地图
setInterval(refreshMap,20000);
function refreshMap(){
	roadinit=1;
	findRoadList();             //标记路线
}


function init(){
	setLinkLeft();
	addLinkToGuid();	
	centerpoint="120.76403,30.74283";  //地图中心点初始化
	zoom = 8;
	loadmap(centerpoint,zoom);  //加载地图		        //控件加超链接
	findRoadList();             //标记路线
	
	//jtl
	findJtl();
}

function loadmap(centerpoint,zoom){
//	$("#road_map").empty();
	maplet = new XMap("road_map",3);
	maplet.setMapCenter(centerpoint,zoom);
	maplet.setScaleVisible(false);//设置比例尺不可见
	maplet.setOverviewVisible(false,false);//鹰眼不可见
}

function initMapCenter(){
	 maplet.setMapCenter(centerpoint,zoom);
}



function addLinkToGuid(){
	initGuildPic();
	initTag();
	
}
function findRoadList(){
	$.ajax({
		url:'highway/selectTransDataTimeCS',
		type:'post',
		dataType:'json',
		data:{
		},
       beforeSend : function() {
			
			if(roadinit==0){
			   ShowLoadingDiv();// 获取数据之前显示loading图标。
			}
		},
		success:function(data){
			if(data.result.resultcode==0){
				var roadlist = data.result.obj;
				findRoadLine(roadlist);
				if(roadinit==0){
					
					roadinit=1;
				}
				//initMapCenter();
			}else{
				if(roadinit==0){
					CloseLoadingDiv();
					alert(data.result.resultdesc);
					roadinit=1;
				}
				
			}
			CloseLoadingDiv();
		}
	});
}

function findRoadLine(roadlist){
   if(roadlist!=null && roadlist.length>0){
	   maplet.removeAllOverlay();
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
}


function markLine(stakelist,color){
	if(stakelist!=null||stakelist.length>0){
	for(var i=0;i<stakelist.length-1;i++){
		var stakeinfo = stakelist[i];
		var next = stakelist[i+1];
		var lineString = stakeinfo.longitude+","+stakeinfo.latitude+";"+
		next.longitude+","+next.latitude;
		 maplet.drawLine(lineString,color,0,10);
	}
	
if(stakelist.length>0&&roadinit==0){
         trafficcenter = stakelist[0].longitude+","+stakelist[0].latitude;
}

	}
	
}

function findJtl(){
	$.ajax({
		url:'multiple/selectJdInfo',
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
					$("#jtllxmc").append("<option value="+roadlist[i].tddm+">"+roadlist[i].lxmc+"</option>")
				}
				jtlxselectfunc(roadlist[0].tddm);
		}
			}else{				
			}
		}
	});
	$('#jtllxmc').change(function(){ 
		jtlxselectfunc($(this).children('option:selected').val());
	});
}

function jtlxselectfunc(value){
	$.ajax({
		url:'multiple/selectNumById',
		type:'post',
		dataType:'json',
		data:{
			jtlid:value
		},
		success:function(data){
			if(data.result.resultcode==0){
				var jtl = data.result.obj;
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

//1、2、3、4、5分别对应非常畅通（绿色）、畅通（蓝色）、一般（黄色）、拥挤（橘黄色）、堵塞（红色）
function textTransfer(yjd){
	if(yjd==1){
		return "非常畅通";
	}else if(yjd==2){
		return "畅通";
	}else if(yjd==3){
		return "一般";
	}else if(yjd==4){
		return "拥挤";
	}else{
		return "堵塞";
	}	
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



