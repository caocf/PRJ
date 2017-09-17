var map;
var StationName;
var direct=1;
$$(document).ready(function() {
	map = new XMap("simpleMap",1);//天地图
	//map.addPanZoomBar();
	if($$("#direct").val()!=null)
		direct=$$("#direct").val();
	PageContent($$("#routeId").val());
});


//详情内容
function PageContent(routeId) {
	$$(".layout1_left_top").empty();
	$$.ajax( {
		url : 'QueryOriginBusLineByID',
		type : 'post',
		dataType : 'json',
		data : {
			'lineID' : routeId,
			'direct':direct
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			var list = JSON.parse(data);
			var Line=list.Line;
			if(direct==1){
			
			var UpList=list.UpList;
			var up_firstStation=UpList[0].StationName;
	     	var up_lastStation=UpList[UpList.length-1].StationName;
	     	
			var requestBusStationId=$$("#busStationId").val();
			var busRouteName=Line.Name;
			var lon;
	     	var lat;
	     
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
	     	}
			var html = '<div class="map_bus_div">';
			html+='<div class="map_bus_div1"><label class="map_bus_title">' + StationName + '</label>'+
			'<div class="map_bus_save"><img  class="map_bus_colloct" src="WebPage/image/common/collect.png" onclick=SaveStationFavor(this,"'+requestBusStationId+
			'","'+StationName+'","'+lon+'","'+lat+'") />收藏</div></div>';
			html+='<div class="map_bus_div2"><div class="map_bus_road">途径公交</div>';
			GetBusRouteMap(requestBusStationId,busRouteName);
			html += '</div>';
			
			html+='<div class="map_bus_div3">' + busRouteName + '('+up_firstStation+"-"+up_lastStation+
			')<img  src="WebPage/image/common/refresh.png" onclick=QueryOriginBusArrive("'+routeId+'","'+requestBusStationId+'") style="margin-left: 10px;cursor: pointer;"/></div></div>';
			
		
			$$(".layout1_left_top").append(html);
			QueryOriginBusArrive(routeId,requestBusStationId);
			}
			if(direct==2){
				
				var DownList=list.DownList;
				var down_firstStation=DownList[0].StationName;
		     	var down_lastStation=DownList[DownList.length-1].StationName;
				var requestBusStationId=$$("#busStationId").val();
				var busRouteName=Line.Name;
				var lon;
		     	var lat;
		     
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
		     	}
				var html = '<div class="map_bus_div">';
				html+='<div class="map_bus_div1"><label class="map_bus_title">' + StationName + '</label>'+
				'<div class="map_bus_save"><img  class="map_bus_colloct" src="WebPage/image/common/collect.png" onclick=SaveStationFavor(this,"'+requestBusStationId+
				'","'+StationName+'","'+lon+'","'+lat+'") />收藏</div></div>';
				html+='<div class="map_bus_div2"><div class="map_bus_road">途径公交</div>';
				GetBusRouteMap(requestBusStationId,busRouteName);
				html += '</div>';
				
				html+='<div class="map_bus_div3">' + busRouteName + '('+down_firstStation+"-"+down_lastStation+
				')<img  src="WebPage/image/common/refresh.png" onclick=QueryOriginBusArrive("'+routeId+'","'+requestBusStationId+'") style="margin-left: 10px;cursor: pointer;"/></div></div>';
				
			
				$$(".layout1_left_top").append(html);
				QueryOriginBusArrive(routeId,requestBusStationId);
				}
			
	},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		},
		complete : function() {
			CloseLoadingDiv();
	}
	
	});							
}


function GetBusRouteMap(busStationId,busRouteName) {
	var html2 = "";
	$$.ajax( {
		url : 'QueryOriginBusStationByID',
		type : 'post',
		dataType : 'json',
		data : {
			'stationID' : busStationId,
			'direct':direct
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
	var icon = map.createIcon("WebPage/image/graphical/map_point_pressed.png", 30);
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
					thisop.src="WebPage/image/common/favorates.png";
				}else if (data.status == 1) {
					alert("收藏成功！");
					thisop.src="WebPage/image/common/favorates.png";
				}
			},
			error : function(a, b, c) {
				alert("出现错误，请刷新");
			}
		});
	}
}
//实时到站信息
function QueryOriginBusArrive(lineid,stationid){
	$$(".listTable .addTr").empty();
	$$(".listTable .addTr").remove();
		$$.ajax( {
			url : 'QueryOriginBusArrive',
			type : 'post',
			dataType : 'json',
			data : {
				'lineID' : lineid,
				'direct':direct,
				'stationID':stationid,
				'count':20
			},
			success : function(data) {
			if(data!=""){
					var list = JSON.parse(data).BusLocationList;
					if(list!=""){
						for(var i=0;i<list.length;i++){
							var newTr=$$("<tr class='addTr'></tr>");
							newTr.append($$("<td>"+list[i].CarNumber+"</td>"));
							newTr.append($$("<td>"+list[i].Distance+"米/"+list[i].StationIndex+"站</td>"));
							//newTr.append($$("<td>"+BUS_STATUS[list[i].CongestionDegree]+"</td>"));
							newTr.append($$("<td>"+BUS_STATUS[list[i].DriveState]+"</td>"));
							
							$$(".listTable").append(newTr);
						}
					}else{
							//$$(".listTable").append($$("<tr  class='addTr'><td colspan='4'>进入盲区</td></tr>"));
					}
				}
				else{
					//$$(".listTable").append($$("<tr  class='addTr'><td colspan='4'>进入盲区</td></tr>"));
				}
			},
			error : function(a, b, c) {
				alert("出现错误，请刷新");
			}
		});
	
}

