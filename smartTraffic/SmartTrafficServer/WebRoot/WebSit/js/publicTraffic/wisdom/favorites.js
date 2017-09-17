var map;
var Global_busRoutes=new Array();//线路
var Global_busStations;//站点
var Global_coordinate=new Array();//换乘 经纬度 二位数组 第一参数：1.步行（#7fe77c）2.公交（7c9de7） 第一参数：经纬度
var Global_favorList;//换乘
var EndScrollLeft;//滚动
$$(document).ready(function() {
	map = new XMap("simpleMap",1);//天地图
	map.addPanZoomBar();
	
	$$(".Favorites_datalist_1").css("display","block");
	LogAndExitRefresh=true;//登录退出时是否刷新页面
	if ($$("#LoginUser").val() == "null") {
		LoginWeb();
	} else {
		QueryAllLineFavor();//获取收藏的全部线路
		QueryAllStationFavor();//获取收藏的全部站点
		QueryAllTransferFavor();//获取收藏的全部换乘
	}
});

//切换收藏的内容
function FavoritesList(thisop,type){
	$$(".layout3_left_layout2_label_select").attr("class","layout3_left_layout2_label");
	thisop.className="layout3_left_layout2_label_select";
	if($$(".Favorites_datalist_"+type).css("display")=="none"){
		$$(".Favorites_datalist_1,.Favorites_datalist_2,.Favorites_datalist_3").css("display","none");
		$$(".Favorites_datalist_"+type).css("display","block");
		map.delWindow(); 
	}
	
}
//空值
function TableIsNull(type){
	if(type==1){
		$$(".Favorites_datalist_1").append($$("<div class='layout1_left_datalist_tishi'>还没有收藏相关的线路</div>"));
	}else if(type==2){
		$$(".Favorites_datalist_2").append($$("<div class='layout1_left_datalist_tishi'>还没有收藏相关的站点</div>"));
	}else if(type==3){
		$$(".Favorites_datalist_3").append($$("<div class='layout1_left_datalist_tishi'>还没有收藏相关的换乘</div>"));
	}
	
}
//取消关注
function DeleteStationFavor(thisop,type,id){
	if (confirm("是否确定取消关注？")) {
		if(type==1){
			$$.ajax( {
				url :'DeleteForLine',
				type : 'post',
				dataType : 'json',
				data:{
					'lineIDs':id
				},
				success : function(data) {
					var list =data.success;
					if (list) {
						alert("取消收藏成功！");
						$$(".Favorites_datalist_1 #addTr_"+thisop).remove();
					} else {
						alert("取消收藏失败！");
					}
				},
				error : function(a, b, c) {
					alert("出现错误，请刷新");
				}
			});
		}else if(type==2){
			$$.ajax( {
				url :'DeleteForStation',
				type : 'post',
				dataType : 'json',
				data:{
					'stationIDs':id
				},
				success : function(data) {
					var list =data.success;
					if (list) {
						alert("取消收藏成功！");
						$$(".Favorites_datalist_2 #addTr_"+thisop).remove();
					} else {
						alert("取消收藏失败！");
					}
				},
				error : function(a, b, c) {
					alert("出现错误，请刷新");
				}
			});
		}else if(type==3){
			$$.ajax( {
				url :'DeleteForTransfer',
				type : 'post',
				dataType : 'json',
				data:{
					'transferIDs':id
				},
				success : function(data) {
					var list =data.success;
					if (list) {
						alert("取消收藏成功！");
						$$(".Favorites_datalist_3 #addDiv_"+thisop).remove();
					} else {
						alert("取消收藏失败！");
					}
				},
				error : function(a, b, c) {
					alert("出现错误，请刷新");
				}
			});
		}

	}
}
//----------------------------公共部分  end------------------------------
//----------------------------线路 start--------------------------------
//获取收藏的全部线路
function QueryAllLineFavor(){
	$$.ajax( {
		url : 'QueryAllLineFavor',
		type : 'post',
		dataType : 'json',
		beforeSend : function() {
			$$(".Favorites_datalist_1 .loadingData").show();
		},
		success : function(data) {
			var list =data.lineFavors;
			if (list.length == 0) {
				TableIsNull(1);
			} else {
				for(var i=0;i<list.length;i++){
					PageContent1(list[i].lineID,list[i].id,i);//显示每条线路内容
				}
				 
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		},
		complete : function() {
			$$(".Favorites_datalist_1 .loadingData").hide();
	}

	});
}
//显示每条线路内容    左侧内容：线路id，收藏id，数据编号
function PageContent1(routeId,id,num) {
	$$.ajax( {
		url : 'QueryOriginBusLineByID',
		type : 'post',
		dataType : 'json',
		data : {
			'lineID' :routeId,
			'direct':1,
			'linedetails':true	
		},
		success : function(data) {
			if(data.length!=0){
				var list = JSON.parse(data);
				if (list != undefined) {
					var Line=list.Line;
					var UpList=list.UpList;
					var LineName=Line.Name;
					var LineId=Line.Id;
					var firstStation=Line.UpStartStationName;
				 	 var lastStation=Line.UpEndStationName;
				 	 Global_busRoutes[num]=list;
						var newTr = $$("<div class='addTr' id='addTr_"+num+"'></div>");
						newTr.append($$("<div class='addTr_div_1' id='addTr_"+num+"_1'><label class='addTr_div_1_title' onclick=busRouteCoordinate('"+num+"')>"+LineName+"("+firstStation+"-"+lastStation+")</label>" +
								"<label class='addTr_div_1_link' onclick=details('"+LineId+"','-1')>详情&raquo;</label>"+
								"<img  class='addTr_div_1_colloct' src='WebSit/image/common/favorates.png' onclick=DeleteStationFavor('"+num+"',1,'"+id+"') /></div>"));
						newTr.append($$("<div class='addTr_div_2' id='addTr_"+num+"_2'><label>首末班时间："+Line.LineUpStartTime+'-'+Line.LineUpEndTime+"&nbsp;全程："+Line.Price+"元</label></div>"));
	
						newTr.append($$("<div class='addTr_div_3' id='addTr_"+num+"_3' onclick=openDiv('"+num+"','Favorites_datalist_1')>" +
								"<img id='img_"+num+"' src='WebSit/image/common/plus.png'/>途径站点</label></div>"));
						var newDiv4=$$("<div class='addTr_div_4' id='addTr_"+num+"_4' style='display:none'></div>");
						for ( var j = 0; j < UpList.length; j++) {
							newDiv4.append($$("<label onclick=busStationPoup('"+num+"','"+j+"')>"+(j+1)+"."+UpList[j].StationName+"</label>"));
						}
						newTr.append(newDiv4);
						$$(".Favorites_datalist_1").append(newTr);
				
				}
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	
	});							
}
//站点详情
function addPointPoup(lon, lat, stationName, id,busRouteName,routeId) {
	var lonlat = map.createLonlat(lon+","+lat);
	var icon = map.createIcon("WebSit/image/common/map_circle.png",11);
	imgMarker = map.createMarker(lonlat, icon);
	map.addMarkerObject(imgMarker);
	imgMarker.events.register("click",null,function(){
		if (currentPopup) {
			// 清除浮云窗口
			map.removePopup(currentPopup);
		}
		$$.ajax( {
			url : 'QueryOriginBusStationByID',
			type : 'post',
			dataType : 'json',
			data : {
				'stationID' : id,
				'direct':1,
				'linedetails':true		
			},
			success : function(data) {
				if(data.length!=0){
					var list = JSON.parse(data);
					var StationInfor =list.Station;
					var html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' + stationName + '</label><label class="map_bus_link" onclick=details("'+routeId+
					'","'+id+'")>详情&raquo;</label></div><div class="map_bus_div2">';
					var routeid;
					var LineList = list.LineList;
					for ( var i = 0; i < LineList.length; i++) {
						if(busRouteName==LineList[i].Name)
							{html += '<a href="javascript:void(0)"  class="map_bus_div2_select" onclick="poupContent(this)" routeid='
								+ LineList[i].Id+ ' >'+ LineList[i].Name+ '</a>'+ " ";
							routeid=LineList[i].Id;
							}
						else{
							html += '<a href="javascript:void(0)"  class="map_bus_div2_noselect" onclick="poupContent(this)" routeid='
								+ LineList[i].Id+ ' >'+ LineList[i].Name+ '</a>'+ " ";
						}
						}
					html += '</div><div id="poupcontent"></div></div>';
					map.createPopup(lon+","+lat,500,360,html);
					GetPoupContentForHtml(routeid);
				}
			},
			error : function(a, b, c) {
				alert("出现错误，请刷新");
			}
		
		});							

	});
}
//地图显示整条线路
function busRouteCoordinate(array_one) {
	map.delWindow();
	var StationsLonlat=Global_busRoutes[array_one].UpList;
	var LineName=Global_busRoutes[array_one].Line.Name;
	var LineId=Global_busRoutes[array_one].Line.Id;
	var coordinates ="";
	for ( var j = 0; j < StationsLonlat.length; j++) {
		coordinates+=StationsLonlat[j].Longitude+","+StationsLonlat[j].Latitude+";";
		addPointPoup(StationsLonlat[j].Longitude, StationsLonlat[j].Latitude, StationsLonlat[j].StationName, StationsLonlat[j].StationId,LineName,LineId);
	}
	map.drawLine(coordinates,DRAWLINE_COLOR,"solid",DRAWLINE_SIZE);
}
//展开或关闭左侧站点列表
function openDiv(array_one,type){
	var image = "";
	image = $$("."+type+" #img_"+array_one).attr("src");
	if (image.indexOf("plus", 0) != -1) {
		$$("."+type+" #addTr_"+array_one+"_4").css("display","");
		$$("."+type+" #img_"+array_one).attr("src","WebSit/image/common/minus.png");
	} else {
		$$("."+type+" #addTr_"+array_one+"_4").css("display","none");
		$$("."+type+" #img_"+array_one).attr("src","WebSit/image/common/plus.png");
	}

}
//详情页面
function details(routeId,busStationId){
	window.open($$("#basePath").val()+"WebSit/page/publicTraffic/wisdom/bus_infor.jsp?routeId="+routeId+"&busStationId="+busStationId);
}
//页面滚动
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
//地图上显示的某个站点的线路
function GetPoupContentForHtml(routeid){
	$$.ajax( {
		url : 'QueryOriginBusLineByID',
		type : 'post',
		dataType : 'json',
		data : {
			'lineID' : routeid,
			'direct' : 1	
		},
		success : function(data) {
			if(data.length!=0){
				var list = JSON.parse(data);	
				var Line=list.Line;
				var UpList=list.UpList;
			
				var div3=$$('<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + Line.Name + '</label>'+
						'<div class="map_bus_div3_l1">'+Line.UpStartStationName+'<img src="WebSit/image/common/busStation_srart_end.png"/>'+Line.UpEndStationName+'</div>'+
					'<div class="map_bus_div3_l2"><img src="WebSit/image/common/bus_end.png"/>到站<img src="WebSit/image/common/bus_pass.png"/>途中<img src="WebSit/image/common/refresh.png"/>刷新</div></div>');
				var div4=$$('<div class="map_bus_div4"><label>首末班时间：'+Line.LineUpStartTime+'-'+Line.LineUpEndTime+'&nbsp;全程：'+Line.Price+'元</label></div>');
				var div5=$$('<div class="map_bus_div5"><img src="WebSit/image/common/bus_end.png"/></div>');
				var maxwindth=UpList.length*21;
				var content = '<div class="map_bus_div6"><a class="map_bus_div6_left" style="visibility:hidden" onclick="BusStationListMove_left(this)"><img src="WebSit/image/common/arrow_left_normal.png"/></a>'+
				'<div class="map_bus_div6_content"><ul style="width:'+maxwindth+'px">';
				for (var i = 0; i < UpList.length; i++) {
					var busStationName = UpList[i].StationName;
					content += '<li><a href="javascript:void(0)"><img src="WebSit/image/common/map_station_icon_line.png"/>'
							+ busStationName + '</a></li>';
				}
				
				content += '</ul></div><a class="map_bus_div6_right" onclick="BusStationListMove_Right(this)"><img src="WebSit/image/common/arrow_right_normal.png"/></a></div>';
				$$('#poupcontent').empty().append(div3);
				$$('#poupcontent').append(div4);
				$$('#poupcontent').append(div5);
				$$('#poupcontent').append(content);
			}
		}
	});
}

//点击左侧站点列表，显示线路和站点浮云
function busStationPoup(num,uplist_num) {
	busRouteCoordinate(num);//地图显示整条线路
	var Line=Global_busRoutes[num].Line;
	var LineId= Line.Id;
	 var firstStation=Line.UpStartStationName;
 	 var lastStation=Line.UpEndStationName;
 	 
	var StationInfo=Global_busRoutes[num].UpList;
	var name =StationInfo[uplist_num].StationName;
	var StationId =StationInfo[uplist_num].StationId;
	var lon=StationInfo[uplist_num].Longitude;
	var lat=StationInfo[uplist_num].Latitude;
	
     //线路的某个查询
	$$.ajax( {
		url : 'QueryOriginBusStationByID',
		type : 'post',
		dataType : 'json',
		data : {
			'stationID' : StationId,
			'direct':1,
			'linedetails':true		
		},
		success : function(data) {
			if(data.length!=0){
				var list = JSON.parse(data);
				var GetNewLineList = list.LineList;
				var html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' + name + '</label><label class="map_bus_link" onclick=details("'+LineId+'","'+StationId+'")>详情&raquo;</label>'+
				'</div><div class="map_bus_div2">';
				for(var j=0;j<GetNewLineList.length;j++){
					if(GetNewLineList[j].Id==LineId){
						html += '<a  href="javascript:void(0)" onclick="poupContent(this)" routeid='+ GetNewLineList[j].Id+ ' class="map_bus_div2_select">'+ GetNewLineList[j].Name+ '</a>';
					}else{
						html += '<a  href="javascript:void(0)" onclick="poupContent(this)" routeid='+ GetNewLineList[j].Id+ ' class="map_bus_div2_noselect">'+  GetNewLineList[j].Name+ '</a>';
					}
				}
				html += '</div><div id="poupcontent"></div>';
				
				var div3=$$('<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + Line.Name + '</label>'+
						'<div class="map_bus_div3_l1">'+firstStation+'<img src="WebSit/image/common/busStation_srart_end.png"/>'+lastStation+'</div>'+
						'<div class="map_bus_div3_l2"><img src="WebSit/image/common/bus_end.png"/>到站<img src="WebSit/image/common/bus_pass.png"/>途中<img src="WebSit/image/common/refresh.png"/>刷新</div></div>');
				var div4=$$('<div class="map_bus_div4"><label>首末班时间：'+Line.LineUpStartTime+'-'+Line.LineUpEndTime+'&nbsp;全程：'+Line.Price+'元</label></div>');
				var div5=$$('<div class="map_bus_div5"><img src="WebSit/image/common/bus_end.png"/></div>');
				var maxwindth=StationInfo.length*21;//ul 宽度
				var content = '<div class="map_bus_div6"><a class="map_bus_div6_left" style="visibility:hidden" onclick="BusStationListMove_left(this)"><img src="WebSit/image/common/arrow_left_normal.png"/></a>'+
					'<div class="map_bus_div6_content"><ul style="width:'+maxwindth+'px">';
				for (var i = 0; i < StationInfo.length; i++) {
					var busStationName = StationInfo[i].StationName;
					content += '<li><a href="javascript:void(0)"><img src="WebSit/image/common/map_station_icon_line.png"/>'
							+ busStationName + '</a></li>';
				}
				
				content += '</ul></div><a class="map_bus_div6_right" onclick="BusStationListMove_Right(this)"><img src="WebSit/image/common/arrow_right_normal.png"/></a></div>';
				var popup=map.createPopup(lon+","+lat,500,360,html);
				$$('#poupcontent').empty().append(div3);
				$$('#poupcontent').append(div4);
				$$('#poupcontent').append(div5);
				$$('#poupcontent').append(content);
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	
	});							
}

//地图上某个线路的点击浮云内容
function poupContent(v) {
	$$(".map_bus_div2_select").attr("class","map_bus_div2_noselect");
	v.className="map_bus_div2_select";
	var routeid = $$(v).attr("routeid");
	$$.ajax( {
		url : 'QueryOriginBusLineByID',
		type : 'post',
		dataType : 'json',
		data : {
		'lineID' : routeid,
		'direct':1,
		'linedetails':true
		},
		success : function(data) {
			if(data.length!=0){
				var list = JSON.parse(data);		
				if (list != undefined) {
					var Line=list.Line;
					var UpList=list.UpList;
					var LineName=Line.Name;
					var LineId=Line.Id;
					
					var div3=$$('<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + LineName + '</label>'+
							'<div class="map_bus_div3_l1">'+Line.UpStartStationName+'<img src="WebSit/image/common/busStation_srart_end.png"/>'+Line.UpEndStationName+'</div>'+
							'<div class="map_bus_div3_l2"><img src="WebSit/image/common/bus_end.png"/>到站<img src="WebSit/image/common/bus_pass.png"/>途中<img src="WebSit/image/common/refresh.png"/>刷新</div></div>');
					var div4=$$('<div class="map_bus_div4"><label>首末班时间：'+Line.LineUpStartTime+'-'+Line.LineUpEndTime+'&nbsp;全程：'+Line.Price+'元</label></div>');
					var div5=$$('<div class="map_bus_div5"><img src="WebSit/image/common/bus_end.png"/></div>');
					var maxwindth=UpList.length*21;
					var content = '<div class="map_bus_div6"><a class="map_bus_div6_left" style="visibility:hidden" onclick="BusStationListMove_left(this)"><img src="WebSit/image/common/arrow_left_normal.png"/></a>'+
					'<div class="map_bus_div6_content"><ul style="width:'+maxwindth+'px">';
					markers.clearMarkers();
					vectorLayer.removeFeatures(vectorLayer.features);
					var coordinates="";
					for (var j = 0; j < UpList.length;j++) {
						var busStationName = UpList[j].StationName;
						content += '<li><a href="javascript:void(0)"><img src="WebSit/image/common/map_station_icon_line.png"/>'
								+ busStationName + '</a></li>';
						coordinates+=UpList[j].Longitude+","+UpList[j].Latitude+";";
						addPointPoup(UpList[j].Longitude, UpList[j].Latitude, UpList[j].StationName, UpList[j].StationId,LineName,LineId);
					}
					
					content += '</ul></div><a class="map_bus_div6_right" onclick="BusStationListMove_Right(this)"><img src="WebSit/image/common/arrow_right_normal.png"/></a></div>';
					$$('#poupcontent').empty().append(div3);
					$$('#poupcontent').append(div4);
					$$('#poupcontent').append(div5);
					$$('#poupcontent').append(content);
	
					map.drawLine(coordinates,DRAWLINE_COLOR,"solid",DRAWLINE_SIZE);
				}
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	
	});							
}


//----------------------------线路  end-------------------------------

//----------------------------站点 start--------------------------------
//获取收藏的全部站点
function QueryAllStationFavor(){
	$$.ajax( {
		url : 'QueryAllStationFavor',
		type : 'post',
		dataType : 'json',
		beforeSend : function() {
			$$(".Favorites_datalist_2 .loadingData").show();
		},
		success : function(data) {
			var list =data.stationFavors;
			if (list.length == 0) {
				TableIsNull(2);
			} else {
				Global_busStations=list;
				for(var i=0;i<list.length;i++){
					PageContent2(list[i],i);//显示每条线路内容
				}
				 
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		},
		complete : function() {
			$$(".Favorites_datalist_2 .loadingData").hide();
	}

	});
}
//显示每条线路内容    左侧内容：站点id，收藏id，数据编号
function PageContent2(list,num) {
	var busStationId=list.stationID;
	var id=list.id;
	var name=list.stationName;
	var lonlat=list.longtitude+","+list.lantitude;
	$$.ajax( {
		url : 'QueryOriginBusStationByID',
		type : 'post',
		dataType : 'json',
		data : {
			'stationID' : busStationId,
			'direct':1		
		},
		success : function(data) {
			if(data.length!=0){
				var list = JSON.parse(data);
				var StationInfor =list.Station;
				var routId="";
				var LineList = list.LineList;
				
				var newDiv4=$$("<div class='addTr_div_4' id='addTr_"+num+"_4' style='display:none'></div>");
				var j=1;
				for ( var i = 0; j < LineList.length; j++) {
					newDiv4.append($$("<label onclick=stationPoup("+num+",'"+ LineList[i].Id+ "') >"+j+"、&nbsp;"+LineList[i].Name+"</label>"));
					if(j==1){
						routId=LineList[i].Id;
					}
						j++;
				}
				
				var newTr = $$("<div class='addTr' id='addTr_"+num+"'></div>");
				newTr.append($$("<div class='addTr_div_1' id='addTr_"+num+"_1'><label class='addTr_div_1_title' onclick=stationPoup("+num+",0)  >"+name+"</label>" +
						"<label class='addTr_div_1_link' onclick=details('"+routId+"','"+busStationId+"')>详情&raquo;</label>"+
						"<img  class='addTr_div_1_colloct' src='WebSit/image/common/favorates.png' onclick=DeleteStationFavor('"+num+"',2,'"+id+"') /></div>"));
				newTr.append($$("<div class='addTr_div_3' id='addTr_"+num+"_3' onclick=openDiv('"+num+"','Favorites_datalist_2')>" +
						"<img id='img_"+num+"' src='WebSit/image/common/plus.png'/>相关公交线路</label></div>"));
				newTr.append(newDiv4);
				$$(".Favorites_datalist_2").append(newTr);
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	
	});			
}
function stationPoup(num,routeid) {
	map.delWindow();
	// 清除浮云窗口
	if (currentPopup) {
		map.removePopup(currentPopup);
	}
	var StationName = Global_busStations[num].stationName;
	var StationId = Global_busStations[num].stationID;
	var lon = Global_busStations[num].longtitude;
	var lat = Global_busStations[num].lantitude;
	
	$$.ajax( {
		url : 'QueryOriginBusStationByID',
		type : 'post',
		dataType : 'json',
		data : {
			'stationID' : StationId,
			'direct':1	
			
		},
		success : function(data) {
			if(data.length!=0){
				var list = JSON.parse(data);
				var StationInfor =list.Station;
				var LineList = list.LineList;
				
				var html ="";
				var firstBusStation=0;
				for ( var i = 0; i < LineList.length; i++) {
					if(routeid==0) {
						if(firstBusStation==0)
							{
							 html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' + StationName + '</label><label class="map_bus_link" onclick=details("'+LineList[i].Id+'","'+StationId+
							 '")>详情&raquo;</label><img class="map_bus_colloct" src="WebSit/image/common/favorates.png" onclick=DeleteStationFavor("'+num+'",2,"'+StationId+'") /></div><div class="map_bus_div2">';
							html += '<a href="javascript:void(0)"  class="map_bus_div2_select" onclick="poupContent(this)" routeid='
									+ LineList[i].Id+ ' >'+ LineList[i].Name+ '</a>';
							routeid=LineList[i].Id;
							firstBusStation++;
							}
						else{
							html += '<a href="javascript:void(0)"  class="map_bus_div2_noselect" onclick="poupContent(this)" routeid='
								+ LineList[i].Id+ ' >'+ LineList[i].Name+ '</a>';
						}
					}
					else{
						if(routeid==LineList[i].Id)
						{
							html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' +StationName  + '</label><label class="map_bus_link" onclick=details("'+LineList[i].Id+'","'+StationId +
									'")>详情&raquo;</label><img class="map_bus_colloct" src="WebSit/image/common/favorates.png" onclick=DeleteStationFavor("'+num+'",2,"'+StationId +'") /></div><div class="map_bus_div2">';
							html += '<a href="javascript:void(0)"  class="map_bus_div2_select" onclick="poupContent(this)" routeid='
									+ LineList[i].Id+ ' >'+ LineList[i].Name+ '</a>';
							firstBusStation++;
						}
					else{
						html += '<a href="javascript:void(0)"  class="map_bus_div2_noselect" onclick="poupContent(this)" routeid='
							+ LineList[i].Id+ ' >'+ LineList[i].Name+ '</a>';
					}
					}
					}
				html += '</div><div id="poupcontent"></div></div>';
				
				var image="WebSit/image/graphical/map_point_normal.png";
				map.addCustomerMarker(lon+","+lat,image,(num+1)+"",26,"white","Times New Roman");
				map.createPopup(lon+","+lat,500,360,html);
				//map.addPopupForFeature(lon+","+lat,image,(num+1)+"",26,"white","Times New Roman",html,500,360);
				/*
				map.createPopupForMarker(lon+","+lat,html,image,26,500,360);*/
				GetPoupContentForHtml(routeid);
			}
		}
	});
}
//不可删
function LeftCssBySelf(label){
	
}
//----------------------------站点 end--------------------------------

//----------------------------换乘 start--------------------------------
//获取收藏的全部换乘
function QueryAllTransferFavor(){
	$$.ajax( {
		url : 'QueryAllTransferFavor',
		type : 'post',
		dataType : 'json',
		beforeSend : function() {
			$$(".Favorites_datalist_3 .loadingData").show();
		},
		success : function(data) {
			var list =data.transferFavors;
			if (list.length == 0) {
				TableIsNull(3);
			} else {
				Global_favorList=list;
				for(var i=0;i<list.length;i++){
					PageContent3(list[i],i);//显示每条线路内容
				}
				 
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		},
		complete : function() {
			$$(".Favorites_datalist_3 .loadingData").hide();
	}

	});
}
function PageContent3(transferFavors,num){
	$$.ajax( {
		url : 'QueryOriginTrafficTrasfer',
		type : 'post',
		dataType : 'json',
		data : {
			'startLontitude' :transferFavors.startLongtitude,
			'startLantitude' : transferFavors.startLantitude,
			'endLontitude' :transferFavors.endLongtitude,
			'endLantitude' : transferFavors.endLantitude,
			'order' :transferFavors.transferProject,
			'count':PAGESIZE
		},
		success : function(data) {
			if (data.length != 0) {
				var list = JSON.parse(data);
				list = list.List;
				appendToTable(list, num, transferFavors);
			}

		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}

	});


	
}
function appendToTable(list,num,transferFavors){
	var projectName=transferFavors.projectName;
	var id=transferFavors.id;
	for ( var i = 0; i < list.length; i++) {
		if(list[i].LineList[0]==projectName){
			var coordinate=new Array();
			var all_length=list[i].NewList.length;//总次数
			var newDiv = $$("<div class='addDiv' id='addDiv_"+num+"'></div>");
			var newTr_top = $$("<div class='addDiv_top' ></div>");
			newTr_top.append($$("<div class='addDiv_top_div_1' onclick=routeTransfer('"+num+"')><label class='addDiv_top_div_num'>"+(i+1)+"</label><label class='addDiv_top_div_name'>"+list[i].LineList[0]+
									"</label>&nbsp;&nbsp;&nbsp;<label class='addDiv_top_div_time'>全程约"+list[i].Time+"分钟</label></div>"));
			newTr_top.append($$("<div class='addDiv_top_div_2'><label>全程距离约"+list[i].Distance+"米</label><img src='WebSit/image/common/favorates.png' onclick=DeleteStationFavor('"+num+"','3','"+id+"') /></div>"));
			newDiv.append(newTr_top);
			
			var newTr = $$("<div class='addDiv_content' id='addDiv_"+i+"' style='display:none'></div>");
			//起点标志
			newTr.append($$("<div class='addDiv_div_ends'><img src='WebSit/image/graphical/icon_origin.png'/><label>起点名称</label></div>"));
			for(var j=0;j<all_length;j++){
				var NewList=list[i].NewList[j];
				coordinate[j]=new Array();
				//0步行,1公交，2自行车,3地铁，4水上巴士
				if(NewList.Type==0){
					if(NewList.StationName==undefined){
						var stationName="目的地";
						if(j==all_length-1){
							stationName=$$("#endLocation").val();
						}
						newTr.append($$("<div class='addDiv_div_walk'>"+(j+1)+".步行往<label class='addDiv_label_notice'>"+NewList.Direction+
								"</label>至<label class='addDiv_label_notice'>"+stationName+
								"</label><label class='addDiv_label_right'>"+NewList.Distance+"米</label></div>"));
					}else{
					newTr.append($$("<div class='addDiv_div_walk'>"+(j+1)+".步行往<label class='addDiv_label_notice'>"+NewList.Direction+
							"</label>至<label class='addDiv_label_notice'>"+NewList.StationName+
							"</label><label class='addDiv_label_right'>"+NewList.Distance+"米</label></div>"));
					}
					
					coordinate[j][0]=0;
					coordinate[j][1]=NewList.Longitude+","+NewList.Latitude+";";
				
				}
				else if(NewList.Type==1){
					newTr.append($$("<div class='addDiv_div_bus_1'>"+(j+1)+".乘坐<label class='addDiv_label_notice'>"+NewList.LineDetails.Name+
							"</label>到下车<label class='addDiv_label_notice'>"+NewList.StationName+"</label><label  class='addDiv_label_right'>"+NewList.LineDetails.StationCount+"站</label></div>"));
					newTr.append($$("<div class='addDiv_div_bus_2'><label class='addDiv_label_businfor'>首末班时间："+NewList.LineDetails.StartTime+"-"+NewList.LineDetails.EndTime+"&nbsp;全程："+NewList.LineDetails.Price+"元</label></div>"));
					coordinate[j][0]=1;
					var coordinateString="";
					for(var n=0;n<NewList.LineTrailsList.length;n++){
						coordinateString+=NewList.LineTrailsList[n].Longitude+","+NewList.LineTrailsList[n].Latitude+";";
					}
					coordinate[j][1]=coordinateString;
				}
				else if(NewList.Type==2){
					//alert("2自行车");
				}
				else if(NewList.Type==3){
					//alert("3地铁");
				}
				else if(NewList.Type==4){
					//alert("4水上巴士");
				}
				
			}
			//终点标志
			newTr.append($$("<div class='addDiv_div_ends'><img src='WebSit/image/graphical/icon_destenation.png'/><label>终点名称</label></div>"));
			newDiv.append(newTr);
			$$(".Favorites_datalist_3").append(newDiv);
			Global_coordinate[num]=coordinate;//经纬度
		}
	}
}
//地图显示整条线路
function routeTransfer(num) {
	OpenDiv3(num);//展开子菜单
	map.delWindow();
	var coordinate=Global_coordinate[num];
	var T_startlonlat=Global_favorList[num].startLongtitude+","+Global_favorList[num].startLantitude;
	var T_endlonlat=Global_favorList[num].endLongtitude+","+Global_favorList[num].endLantitude;
	for (var k = 0; k < coordinate.length; k++) {
		if(coordinate[k][0]==0){
			var coordinate_line;
			if(k==0){
				coordinate_line=T_startlonlat+";"+coordinate[k][1];
			}else{
				var spstr = coordinate[k-1][1].split(";");
				coordinate_line=spstr[spstr.length-2]+";"+coordinate[k][1];
			}
			map.drawLine(coordinate_line,DRAWLINE_COLOR,"solid",DRAWLINE_SIZE);
		}else{
			var spstr = coordinate[k][1].split(";");
			addPointPoup_transfer(spstr[spstr.length-2],"WebSit/image/common/map_point_bus.png",21);
			addPointPoup_transfer(spstr[0],"WebSit/image/common/map_point_bus.png",21);
			map.drawLine(coordinate[k][1],DRAWLINE_COLOR,"solid",DRAWLINE_SIZE);
		}
	}
	addPointPoup_transfer(T_startlonlat,"WebSit/image/common/map_point_start.png",36);
	addPointPoup_transfer(T_endlonlat,"WebSit/image/common/map_point_end.png",36);
	//the most suitable size of the map
	//map.createLineSuitable();
}
//图上标点起终点
function addPointPoup_transfer(lonlat,image,imageSize) {
	var lonlat = map.createLonlat(lonlat);
	var icon = map.createIcon(image,imageSize);
	imgMarker = map.createMarker(lonlat, icon);
	map.addMarkerObject(imgMarker);
}
function OpenDiv3(num){
	$$(".Favorites_datalist_3 .addDiv_content").css("display","none");
	$$(".Favorites_datalist_3 #addDiv_"+num).css("display","block");
}
//----------------------------换乘 end--------------------------------
