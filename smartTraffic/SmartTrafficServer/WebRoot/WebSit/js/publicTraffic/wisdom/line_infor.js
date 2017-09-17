var map;
var StationName;
var direct=1;
$$(document).ready(function() {
	map = new XMap("simpleMap",1);//天地图
	//map.addPanZoomBar();
	InitializeTianMap();
	if($$("#direct").val()!=null)
		direct=$$("#direct").val();
	PageContent($$("#routeId").val());
});


//详情内容
function PageContent(routeId) {
	$$(".layout1_left").empty();
	$$.ajax( {
		url : 'QueryOriginBusLineByID',
		type : 'post',
		dataType : 'json',
		data : {
			'lineID' : routeId,
			'direct':direct
		},
		success : function(data) {
			if(direct==1){
			var list = JSON.parse(data);
			var Line=list.Line;
			var UpList=list.UpList;
			
			var requestBusStationId=$$("#busStationId").val();
			var busRouteName=Line.Name;
			var firstStation=Line.UpStartStationName;
		 	var lastStation=Line.UpEndStationName;
	     	var lon;
	     	var lat;
	     	var maxwindth=UpList.length*32;//ul 宽度
	     	var content = '<div class="map_bus_div6"><a class="map_bus_div6_left" style="visibility:hidden" onclick="BusStationListMove_left(this)"><img src="WebSit/image/common/arrow_left_normal.png"/></a>'+
	     					'<div class="map_bus_div6_content"><ul style="width:'+maxwindth+'px">';
	     	for (var i = 0; i < UpList.length; i++) {
	     		var busStationName = UpList[i].StationName;
	     		var busStationId= UpList[i].StationId;
	     		if(requestBusStationId=="-1"&&i==0){//当没有选定站点时
	     			StationName=busStationName;
	     			requestBusStationId=busStationId;
	     			lon=UpList[i].Longitude;
	     			lat=UpList[i].Latitude;
					 creatBusStation(lon,lat);
	     		}
	     		if(busStationId==requestBusStationId){
	     			StationName=busStationName;
	     			lon=UpList[i].Longitude;
	     			lat=UpList[i].Latitude;
	     			 creatBusStation(lon,lat);
	     		}
	     		content+='<li id="busicon'+i+'" class="busicon"></li>';
	     		content += '<li><a href="javascript:void(0)" onclick=details("'+routeId+'","'+UpList[i].StationId+'",1)>'
	     				+ busStationName + '</a></li>';
	     	}
	     	 
			var html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' + StationName + '</label>'+
			'</div><div class="map_bus_div2"><div class="map_bus_road">途径公交</div>';
			GetBusRouteMap(requestBusStationId,busRouteName);
			html += '</div>';
			
			html+='<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + busRouteName + '</label>'+
			'<div class="map_bus_div3_l1">'+firstStation+'<img src="WebSit/image/common/busStation_srart_end.png"/>'+lastStation+'</div>'+
			'<div class="map_bus_div3_l2"><img src="WebSit/image/common/bus_pass.png" style="margin-right: 5px;vertical-align: middle;">途中'+
			'<img  src="WebSit/image/common/refresh.png" onclick=QueryOriginBusArrive("'+routeId+'","'+ UpList[UpList.length-1].StationId+'","'+UpList.length+'") style="margin-left: 10px;vertical-align: middle;cursor: pointer;"/></div></div>';
			html+='<div class="map_bus_div4"><label>首末班时间：'+Line.LineUpStartTime+'-'+Line.LineUpEndTime+'&nbsp;全程：'+Line.Price+'元</label></div>';
			html+='<div class="map_bus_div5"></div>';
			
			if(maxwindth>600)
				content += '</ul></div><a class="map_bus_div6_right" onclick="BusStationListMove_Right(this)"><img src="WebSit/image/common/arrow_right_normal.png"/></a></div>';
			else
				content += '</ul></div></div>';
			html+=content+'</div>';
			$$(".layout1_left").append(html);
			QueryOriginBusArrive(routeId,UpList[UpList.length-1].StationId,UpList.length);
			}
			if(direct==2){
				var list = JSON.parse(data);
				var Line=list.Line;
				var DownList=list.DownList;
				
				var requestBusStationId=$$("#busStationId").val();
				var busRouteName=Line.Name;
				var firstStation=Line.DownStartStationName;
			 	var lastStation=Line.DownEndStationName;
		     	var lon;
		     	var lat;
		     	var maxwindth=DownList.length*32;//ul 宽度
		     	var content = '<div class="map_bus_div6"><a class="map_bus_div6_left" style="visibility:hidden" onclick="BusStationListMove_left(this)"><img src="WebSit/image/common/arrow_left_normal.png"/></a>'+
		     					'<div class="map_bus_div6_content"><ul style="width:'+maxwindth+'px">';
		     	for (var i = 0; i < DownList.length; i++) {
		     		var busStationName = DownList[i].StationName;
		     		var busStationId= DownList[i].StationId;
		     		if(requestBusStationId=="-1"&&i==0){//当没有选定站点时
		     			StationName=busStationName;
		     			requestBusStationId=busStationId;
		     			lon=DownList[i].Longitude;
		     			lat=DownList[i].Latitude;
						 creatBusStation(lon,lat);
		     		}
		     		if(busStationId==requestBusStationId){
		     			StationName=busStationName;
		     			lon=DownList[i].Longitude;
		     			lat=DownList[i].Latitude;
		     			 creatBusStation(lon,lat);
		     		}
		     		content+='<li id="busicon'+i+'" class="busicon"></li>';
		     		content += '<li><a href="javascript:void(0)" onclick=details("'+routeId+'","'+DownList[i].StationId+'",2)>'
		     				+ busStationName + '</a></li>';
		     	}
		     	 
				var html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' + StationName + '</label>'+
				'<img  class="map_bus_colloct" src="WebSit/image/common/collect.png" onclick=SaveStationFavor(this,"'+requestBusStationId+'","'+StationName+'","'+lon+'","'+lat+'") /><div class="map_bus_save">收藏</div></div><div class="map_bus_div2"><div class="map_bus_road">途径公交</div>';
				GetBusRouteMap(requestBusStationId,busRouteName);
				html += '</div>';
				
				html+='<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + busRouteName + '</label>'+
				'<div class="map_bus_div3_l1">'+firstStation+'<img src="WebSit/image/common/busStation_srart_end.png"/>'+lastStation+'</div>'+
				'<div class="map_bus_div3_l2"><img src="WebSit/image/common/bus_pass.png" style="margin-right: 5px;vertical-align: middle;">途中'+
				'<img  src="WebSit/image/common/refresh.png" onclick=QueryOriginBusArrive("'+routeId+'","'+ DownList[DownList.length-1].StationId+'","'+DownList.length+'") style="margin-left: 10px;vertical-align: middle;cursor: pointer;"/></div></div>';
				html+='<div class="map_bus_div4"><label>首末班时间：'+Line.LineDownStartTime+'-'+Line.LineDownEndTime+'&nbsp;全程：'+Line.Price+'元</label></div>';
				html+='<div class="map_bus_div5"></div>';
				
				if(maxwindth>600)
					content += '</ul></div><a class="map_bus_div6_right" onclick="BusStationListMove_Right(this)"><img src="WebSit/image/common/arrow_right_normal.png"/></a></div>';
				else
					content += '</ul></div></div>';
				html+=content+'</div>';
				$$(".layout1_left").append(html);
				QueryOriginBusArrive(routeId,DownList[DownList.length-1].StationId,DownList.length);
				}
	},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	
	});							
}
//站点详情页面
function details(routeId,busStationId,direct){
	window.open($$("#basePath").val()+"WebSit/page/publicTraffic/wisdom/bus_infor.jsp?routeId="+routeId+"&busStationId="+busStationId+"&direct="+direct);
}
var EndScrollLeft;
function BusStationListMove_left(v) {
	var d = v.nextElementSibling||v.nextSibling; // 获得s的下一个兄弟节点
	var d_borthor=d.nextElementSibling||d.nextSibling;
	if (d.scrollLeft >= 0) {
		var a = eval(20);
		d.scrollLeft -= a;

	}
	if (d.scrollLeft == 0) {
		v.style.visibility = "hidden";
		d_borthor.visibility = "visible";
	} else if (d.scrollLeft == EndScrollLeft) {
		v.style.visibility = "visible";
		d_borthor.style.visibility = "hidden";
	} else {
		v.style.visibility = "visible";
		d_borthor.style.visibility = "visible";
	}
	EndScrollLeft = d.scrollLeft;
}
function BusStationListMove_Right(v) {
	var d = v.previousElementSibling||v.previousSibling; // 得到s的上一个兄弟节点
	var d_borthor=d.previousElementSibling||d.previousSibling;
	if (d.scrollLeft >= 0) {
		var a = eval(20);
		d.scrollLeft += a;
	}
	if (d.scrollLeft == 0) {
		v.style.visibility = "visible";
		d_borthor.style.visibility = "hidden";
	} else if (d.scrollLeft == EndScrollLeft) {
		v.style.visibility = "hidden";
		d_borthor.style.visibility = "visible";
	} else {
		v.style.visibility = "visible";
		d_borthor.style.visibility = "visible";
	}
	EndScrollLeft = d.scrollLeft;
}
function GetBusRouteMap(busStationId,busRouteName) {
	var html2 = "";
	$$.ajax( {
		url : 'QueryOriginBusStationByID',
		type : 'post',
		dataType : 'json',
		data : {
			'stationID' : busStationId
		},
		success : function(data) {
				var list = JSON.parse(data);
				var LineList =list.LineList;
					
					
					for ( var i = 0; i < LineList.length; i++) {
						if (busRouteName == LineList[i].Name) {
							html2 += '<a href="javascript:void(0)"  class="map_bus_div2_select" onclick=PageContent("'+LineList[i].Id+'") >'
									+ LineList[i].Name + '</a>';
						
						} else {
							html2 += '<a href="javascript:void(0)"  class="map_bus_div2_noselect" onclick=PageContent("'+LineList[i].Id+'") >'
									+ LineList[i].Name + '</a>';
						}
					}
				
				},
				complete : function() {
					$$(".map_bus_div2").html($$(".map_bus_div2").html()+html2);
					
				}
			});
	
}
function creatBusStation(lon, lat) {	
	var lonlat = map.createLonlat(lon + "," + lat);
	map.setMapCenter(lon + "," + lat,null);
	var icon = map.createIcon("WebSit/image/graphical/map_point_pressed.png", 30);
	var imgMarker = map.createMarker(lonlat, icon);
	map.addMarkerObject(imgMarker);
}
//收藏--站点
function SaveStationFavor(thisop,id,name,lon,lat){
	if ($$("#LoginUser").val() == "null") {
		alert("请先登录");
	} else {
	//收藏
		$$.ajax( {
			url : 'SaveStationFavor',
			type : 'post',
			dataType : 'json',
			data : {
				'stationID' : id,
				'stationName':name,
				'stationLongtitude':lon,
				'stationLantitude':lat
			},
			success : function(data) {
				//-1 用户未登录、 0 已保存、 1保存成功
				if (data.status == -1) {
					alert("用户未登录");
				} else if (data.status == 0) {
					alert("已收藏");
					thisop.src="WebSit/image/common/favorates.png";
				}else if (data.status == 1) {
					alert("收藏成功！");
					thisop.src="WebSit/image/common/favorates.png";
				}
			},
			error : function(a, b, c) {
				alert("出现错误，请刷新");
			}
		});
	}
}
//实时到站信息
function QueryOriginBusArrive(lineid,stationid,stationlength){
	$$(".busicon").html("");
		$$.ajax( {
			url : 'QueryOriginBusArrive',
			type : 'post',
			dataType : 'json',
			data : {
				'lineID' : lineid,
				'direct':direct,
				'stationID':stationid,
				'count':10
			},
			success : function(data) {
				if(data!=""){
					var list = JSON.parse(data).BusLocationList;
					if(list!=""){
						for(var i=0;i<list.length;i++){
							var linum=Number(stationlength)-Number(list[i].StationIndex);
							$$("#busicon"+linum).html("<img src='WebSit/image/common/bus_pass.png'/>");
						}
					}else{
							//$$(.map_bus_div5").html("进入盲区");
					}
				}
				else{
						//$$(".map_bus_div5").html("进入盲区");
				}
			},
			error : function(a, b, c) {
				alert("出现错误，请刷新");
			}
		});
	
}

