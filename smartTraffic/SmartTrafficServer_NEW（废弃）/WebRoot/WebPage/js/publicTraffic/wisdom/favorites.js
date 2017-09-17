var map;
var Global_busRoutes=new Array();//线路
var Global_busStations;//站点
var Global_coordinate=new Array();//换乘 经纬度 二位数组 第一参数：1.步行（#7fe77c）2.公交（7c9de7） 第一参数：经纬度
var Global_favorList;//换乘
var EndScrollLeft;//滚动
var Gloval_select_lineid=0;//选择的线路id 
var Gloval_select_stationid=0;//最后一个站点id
var Gloval_select_direct=0;//选择
var Gloval_select_stationlength=0;//站点个数
$$(document).ready(function() {
	map = new XMap("simpleMap",1);//天地图
	map.addPanZoomBar();
	$$(".loadingData").hide();
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
					PageContent1(list[i].lineID,list[i].id,list[i].direct,i);//显示每条线路内容
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
function PageContent1(routeId,id,direct,num) {
	$$.ajax( {
		url : 'QueryOriginBusLineByID',
		type : 'post',
		dataType : 'json',
		data : {
			'lineID' :routeId,
			'direct':direct,
			'linedetails':true	
		},
		success : function(data) {
			if(data.length!=0){
				var list = JSON.parse(data);
				if (list != undefined) {
					if(direct==1){
						var Line = list.Line;
						var UpList = list.UpList;
						var LineName = Line.Name;
						var LineId = Line.Id;
						var firstStation = Line.UpStartStationName;
						var lastStation = Line.UpEndStationName;
					 	 Global_busRoutes[num]=list;
						var newTr = $$("<div class='addTr' id='addTr_"+num+"'></div>");
							newTr.append($$("<div class='addTr_div_1' id='addTr_"+num+"_1'><label class='addTr_div_1_title' onclick=busRouteCoordinate('"+num+"',1)>"+LineName+"("+firstStation+"-"+lastStation+")</label>" +
									"<label class='addTr_div_1_link' onclick=Line_details('"+LineId+"','-1',"+direct+")>详情&raquo;</label>"+
									"<img  class='addTr_div_1_colloct' src='WebPage/image/common/favorates.png' onclick=DeleteStationFavor('"+num+"',1,'"+id+"') /></div>"));
							newTr.append($$("<div class='addTr_div_2' id='addTr_"+num+"_2'><label>首末班时间："+Line.LineUpStartTime+'-'+Line.LineUpEndTime+"&nbsp;全程："+Line.Price+"元</label></div>"));
		
							newTr.append($$("<div class='addTr_div_3' id='addTr_"+num+"_3' onclick=openDiv('"+num+"','Favorites_datalist_1')>" +
									"<img id='img_"+num+"' src='WebPage/image/common/plus.png'/>途径站点</label></div>"));
							var newDiv4=$$("<div class='addTr_div_4' id='addTr_"+num+"_4' style='display:none'></div>");
							for ( var j = 0; j < UpList.length; j++) {
								newDiv4.append($$("<label onclick=busStationPoup('"+num+"','"+j+"',1)>"+(j+1)+"."+UpList[j].StationName+"</label>"));
							}
							newTr.append(newDiv4);
							$$(".Favorites_datalist_1").append(newTr);
					}
					if(direct==2){
						var Line=list.Line;
						var DownList=list.DownList;
						var LineName=Line.Name;
						var LineId=Line.Id;
						var firstStation=Line.DownStartStationName;
					 	var lastStation=Line.DownEndStationName;
					 	 Global_busRoutes[num]=list;
							var newTr = $$("<div class='addTr' id='addTr_"+num+"'></div>");
							newTr.append($$("<div class='addTr_div_1' id='addTr_"+num+"_1'><label class='addTr_div_1_title' onclick=busRouteCoordinate('"+num+"',2)>"+LineName+"("+firstStation+"-"+lastStation+")</label>" +
									"<label class='addTr_div_1_link' onclick=Line_details('"+LineId+"','-1',"+direct+")>详情&raquo;</label>"+
									"<img  class='addTr_div_1_colloct' src='WebPage/image/common/favorates.png' onclick=DeleteStationFavor('"+num+"',1,'"+id+"') /></div>"));
							newTr.append($$("<div class='addTr_div_2' id='addTr_"+num+"_2'><label>首末班时间："+Line.LineDownStartTime+'-'+Line.LineDownEndTime+"&nbsp;全程："+Line.Price+"元</label></div>"));
		
							newTr.append($$("<div class='addTr_div_3' id='addTr_"+num+"_3' onclick=openDiv('"+num+"','Favorites_datalist_1')>" +
									"<img id='img_"+num+"' src='WebPage/image/common/plus.png'/>途径站点</label></div>"));
							var newDiv4=$$("<div class='addTr_div_4' id='addTr_"+num+"_4' style='display:none'></div>");
							for ( var j = 0; j < DownList.length; j++) {
								newDiv4.append($$("<label onclick=busStationPoup('"+num+"','"+j+"',2)>"+(j+1)+"."+DownList[j].StationName+"</label>"));
							}
							newTr.append(newDiv4);
							$$(".Favorites_datalist_1").append(newTr);
					}
				}
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	
	});							
}
//站点详情lon, lat, stationId,lineName,lineId,direct
function addPointPoup(lon, lat, stationId,lineName,lineId,direct) {
	var lonlat = map.createLonlat(lon+","+lat);
	var icon = map.createIcon("WebPage/image/common/map_circle.png",11);
	imgMarker = map.createMarker(lonlat, icon);
	map.addMarkerObject(imgMarker);
	imgMarker.events.register("click",null,function(){
		// 清除浮云窗口
		if (currentPopup) {
			map.removePopup(currentPopup);
		}
		$$.ajax( {
			url : 'QueryLineAndStationTogethor',
			type : 'post',
			dataType : 'json',
			data : {
				'stationID' : stationId,
				'lineID':lineId,
				'direct':direct
			},
			success : function(data) {
			if(direct==1){
				var list = data;
				var StationInfo = list.busStationDetail.station;
				var StationLineInfo = list.busStationDetail.lineList;
				var LineInfo = list.busLineDetail;
				var upListInfo=LineInfo.upList;//线路上行信息
	
				var lon=StationInfo.longitude;
				var lat=StationInfo.latitude;
				
				var html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' + StationInfo.name + 
							'</label><label class="map_bus_link" onclick=details("'+lineId+'","'+stationId+'",1)>详情&raquo;</label>'+
							'<img  class="addTr_div_1_colloct" src="WebPage/image/common/collect.png" onclick=SaveStationFavor(this,"'+lineId+'",1) /></div><div class="map_bus_div2">';
				for(var j=0;j<StationLineInfo.length;j++){
					if(StationLineInfo[j].id==lineId){
						html += '<a  href="javascript:void(0)" onclick=busStationPoup("'+StationLineInfo[j].id+'","'+stationId+'","'+direct+'") '+
							' class="map_bus_div2_select">'+StationLineInfo[j].name+ '</a>';
					}else{
						html += '<a  href="javascript:void(0)" onclick=busStationPoup("'+StationLineInfo[j].id+'","'+stationId+'","'+direct+'") '+
							' class="map_bus_div2_noselect">'+ StationLineInfo[j].name+ '</a>';
					}
				}
				html += '</div><div id="poupcontent"></div>';
				var upEndStationName=LineInfo.line.upEndStationName;
				var upStartStationName=LineInfo.line.upStartStationName;
				var div3=$$('<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + LineInfo.line.name + '</label>'+
							'<div class="map_bus_div3_l1">'+upStartStationName+'<img src="WebPage/image/common/busStation_srart_end.png"/>'+
							upEndStationName+'</div><div class="map_bus_div3_l2"><img src="WebPage/image/common/bus_pass.png"/>途中'+
							'<img src="WebPage/image/common/refresh.png" onclick="QueryOriginBusArrive()" style="cursor: pointer;" />刷新</div></div>');
				var div4=$$('<div class="map_bus_div4"><label>首末班时间：'+LineInfo.line.lineUpStartTime+'-'+LineInfo.line.lineUpEndTime+'&nbsp;全程：'+
							LineInfo.line.price+'元</label></div>');
				var div5=$$('<div class="map_bus_div5"></div>');//实时到站
				var maxwindth=upListInfo.length*32;//ul 宽度
				var content = '<div class="map_bus_div6"><a class="map_bus_div6_left" style="visibility:hidden" onclick="BusStationListMove_left(this)">'+
								'<img src="WebPage/image/common/arrow_left_normal.png"/></a><div class="map_bus_div6_content"><ul style="width:'+maxwindth+'px">';
				for (var i = 0; i < upListInfo.length; i++) {
					var busStationName = upListInfo[i].stationName;
					content+='<li id="busicon'+i+'" class="busicon"></li>';
					content += '<li><a href="javascript:void(0)" onclick=details("'+lineId+'","'+upListInfo[i].stationId+'",1)>'
							+ busStationName + '</a></li>';
					
				}
				if(maxwindth>500){
					content += '</ul></div><a class="map_bus_div6_right" onclick="BusStationListMove_Right(this)"><img src="WebPage/image/common/arrow_right_normal.png"/></a></div>';
				}else{
					content += '</ul></div></div>';
				}
				var lonlat=lon+","+lat;
				var searchContent='<div class="map_bus_div7"><span class="map_bus_div7_span1" onclick="ChangeSpan_Way(this,1)"><img src="WebPage/image/common/start_icon.png"/>到这里去</span><span onclick="ChangeSpan_Way(this,2)" class="map_bus_div7_span"><img src="WebPage/image/common/end_icon.png"/>从这里出发</span><span onclick="ChangeSpan_Way(this,3)" class="map_bus_div7_span"><img src="WebPage/image/common/search_icon.png"/>附近找</span>'+
				'<div id="map_bus_div7_content1" class="map_bus_div7_content1"><input type="text" class="input_tex6" id="map_bus_div7_cnt1"/><span class="map_bus_div7_button" onclick=transfer_Way(1,"'+lonlat+'","'+StationInfo.name+'")>公交</span><span class="map_bus_div7_button" onclick=transfer_Way(3,"'+lonlat+'","'+StationInfo.name+'")>自驾</span></div>'+
				'<div id="map_bus_div7_content2" class="map_bus_div7_content"><input type="text" class="input_tex6" id="map_bus_div7_cnt2"/><span class="map_bus_div7_button" onclick=transfer_Way(2,"'+lonlat+'","'+StationInfo.name+'")>公交</span><span class="map_bus_div7_button" onclick=transfer_Way(4,"'+lonlat+'","'+StationInfo.name+'")>自驾</span></div>'+
				'<div id="map_bus_div7_content3" class="map_bus_div7_content"><input type="text" class="input_tex6" id="map_bus_div7_cnt3"/><span class="map_bus_div7_button" onclick="searchKey_Way()">搜索</span></div></div>';
				var popup=map.createPopup(lonlat,500,430,html);
				$$('#poupcontent').empty().append(div3);
				$$('#poupcontent').append(div4);
				$$('#poupcontent').append(div5);
				$$('#poupcontent').append(content);
				$$('#poupcontent').append(searchContent);
				Gloval_select_lineid=lineId;
				Gloval_select_stationid=upListInfo[upListInfo.length-1].stationId;
				Gloval_select_direct=direct;
				Gloval_select_stationlength=upListInfo.length;
				QueryOriginBusArrive();
			}if(direct==2){

				var list = data;
				var StationInfo = list.busStationDetail.station;
				var StationLineInfo = list.busStationDetail.lineList;
				var LineInfo = list.busLineDetail;
				var downListInfo=LineInfo.downList;//线路下行信息

				var lon=StationInfo.longitude;
				var lat=StationInfo.latitude;
				
				var html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' + StationInfo.name + 
							'</label><label class="map_bus_link" onclick=details("'+lineId+'","'+stationId+'",2)>详情&raquo;</label>'+
							'<img  class="addTr_div_1_colloct" src="WebPage/image/common/collect.png" onclick=SaveStationFavor(this,"'+lineId+'",2) /></div><div class="map_bus_div2">';
				for(var j=0;j<StationLineInfo.length;j++){
					if(StationLineInfo[j].id==lineId){
						html += '<a  href="javascript:void(0)" onclick=busStationPoup("'+StationLineInfo[j].id+'","'+stationId+'","'+direct+'") '+
							' class="map_bus_div2_select">'+StationLineInfo[j].name+ '</a>';
					}else{
						html += '<a  href="javascript:void(0)" onclick=busStationPoup("'+StationLineInfo[j].id+'","'+stationId+'","'+direct+'") '+
							' class="map_bus_div2_noselect">'+ StationLineInfo[j].name+ '</a>';
					}
				}
				html += '</div><div id="poupcontent"></div>';
				var downEndStationName=LineInfo.line.downEndStationName;
				var downStartStationName=LineInfo.line.downStartStationName;
				var div3=$$('<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + LineInfo.line.name + '</label>'+
							'<div class="map_bus_div3_l1">'+downStartStationName+'<img src="WebPage/image/common/busStation_srart_end.png"/>'+
							downEndStationName+'</div><div class="map_bus_div3_l2"><img src="WebPage/image/common/bus_pass.png"/>途中'+
							'<img src="WebPage/image/common/refresh.png" onclick="QueryOriginBusArrive()" style="cursor: pointer;" />刷新</div></div>');
				var div4=$$('<div class="map_bus_div4"><label>首末班时间：'+LineInfo.line.lineDownStartTime+'-'+LineInfo.line.lineDownEndTime+'&nbsp;全程：'+
							LineInfo.line.price+'元</label></div>');
				var div5=$$('<div class="map_bus_div5"></div>');//实时到站
				var maxwindth=downListInfo.length*32;//ul 宽度
				var content = '<div class="map_bus_div6"><a class="map_bus_div6_left" style="visibility:hidden" onclick="BusStationListMove_left(this)">'+
								'<img src="WebPage/image/common/arrow_left_normal.png"/></a><div class="map_bus_div6_content"><ul style="width:'+maxwindth+'px">';
				for (var i = 0; i < downListInfo.length; i++) {
					var busStationName = downListInfo[i].stationName;
					content+='<li id="busicon'+i+'" class="busicon"></li>';
					content += '<li><a href="javascript:void(0)" onclick=details("'+lineId+'","'+downListInfo[i].stationId+'",2)>'
							+ busStationName + '</a></li>';
					
				}
				if(maxwindth>500){
					content += '</ul></div><a class="map_bus_div6_right" onclick="BusStationListMove_Right(this)"><img src="WebPage/image/common/arrow_right_normal.png"/></a></div>';
				}else{
					content += '</ul></div></div>';
				}
				var lonlat=lon+","+lat;
				var searchContent='<div class="map_bus_div7"><span class="map_bus_div7_span1" onclick="ChangeSpan_Way(this,1)"><img src="WebPage/image/common/start_icon.png"/>到这里去</span><span onclick="ChangeSpan_Way(this,2)" class="map_bus_div7_span"><img src="WebPage/image/common/end_icon.png"/>从这里出发</span><span onclick="ChangeSpan_Way(this,3)" class="map_bus_div7_span"><img src="WebPage/image/common/search_icon.png"/>附近找</span>'+
				'<div id="map_bus_div7_content1" class="map_bus_div7_content1"><input type="text" class="input_tex6" id="map_bus_div7_cnt1"/><span class="map_bus_div7_button" onclick=transfer_Way(1,"'+lonlat+'","'+StationInfo.name+'")>公交</span><span class="map_bus_div7_button" onclick=transfer_Way(3,"'+lonlat+'","'+StationInfo.name+'")>自驾</span></div>'+
				'<div id="map_bus_div7_content2" class="map_bus_div7_content"><input type="text" class="input_tex6" id="map_bus_div7_cnt2"/><span class="map_bus_div7_button" onclick=transfer_Way(2,"'+lonlat+'","'+StationInfo.name+'")>公交</span><span class="map_bus_div7_button" onclick=transfer_Way(4,"'+lonlat+'","'+StationInfo.name+'")>自驾</span></div>'+
				'<div id="map_bus_div7_content3" class="map_bus_div7_content"><input type="text" class="input_tex6" id="map_bus_div7_cnt3"/><span class="map_bus_div7_button" onclick="searchKey_Way()">搜索</span></div></div>';
				var popup=map.createPopup(lonlat,500,430,html);
				$$('#poupcontent').empty().append(div3);
				$$('#poupcontent').append(div4);
				$$('#poupcontent').append(div5);
				$$('#poupcontent').append(content);
				$$('#poupcontent').append(searchContent);
				Gloval_select_lineid=lineId;
				Gloval_select_stationid=downListInfo[downListInfo.length-1].stationId;
				Gloval_select_direct=direct;
				Gloval_select_stationlength=downListInfo.length;
				QueryOriginBusArrive();
			
			}
						
			},
			error : function(a, b, c) {
				alert("出现错误，请刷新");
			}
		
		});							

	});
}
//地图显示整条线路
function busRouteCoordinate(array_one,direct) {
	map.delWindow();
	if(direct==1){
		var StationsLonlat=Global_busRoutes[array_one].UpList;
		var LineName=Global_busRoutes[array_one].Line.Name;
		var LineId=Global_busRoutes[array_one].Line.Id;
		var coordinates ="";
		for ( var j = 0; j < StationsLonlat.length; j++) {
			coordinates+=StationsLonlat[j].Longitude+","+StationsLonlat[j].Latitude+";";
			addPointPoup(StationsLonlat[j].Longitude, StationsLonlat[j].Latitude, StationsLonlat[j].StationId,LineName,LineId,1);
		}
	}
	if(direct==2){
		var StationsLonlat=Global_busRoutes[array_one].DownList;
		var LineName=Global_busRoutes[array_one].Line.Name;
		var LineId=Global_busRoutes[array_one].Line.Id;
		var coordinates ="";
		for ( var j = 0; j < StationsLonlat.length; j++) {
			coordinates+=StationsLonlat[j].Longitude+","+StationsLonlat[j].Latitude+";";
			addPointPoup(StationsLonlat[j].Longitude, StationsLonlat[j].Latitude, StationsLonlat[j].StationId,LineName,LineId,2);
		}
	}
	map.drawLine(coordinates,DRAWLINE_COLOR,"solid",DRAWLINE_SIZE);
}
//展开或关闭左侧站点列表
function openDiv(array_one,type){
	var image = "";
	image = $$("."+type+" #img_"+array_one).attr("src");
	if (image.indexOf("plus", 0) != -1) {
		$$("."+type+" #addTr_"+array_one+"_4").css("display","");
		$$("."+type+" #img_"+array_one).attr("src","WebPage/image/common/minus.png");
	} else {
		$$("."+type+" #addTr_"+array_one+"_4").css("display","none");
		$$("."+type+" #img_"+array_one).attr("src","WebPage/image/common/plus.png");
	}

}
//详情页面
function details(routeId,busStationId,direct){
	window.open($$("#basePath").val()+"WebPage/page/publicTraffic/wisdom/bus_infor.jsp?routeId="+routeId+"&busStationId="+busStationId+"&direct="+direct);
}

//站点详情页面
function Line_details(routeId,busStationId,direct){
	window.open($$("#basePath").val()+"WebPage/page/publicTraffic/wisdom/line_infor.jsp?routeId="+routeId+"&busStationId="+busStationId+"&direct="+direct);
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


//点击左侧站点列表，显示线路和站点浮云
function busStationPoup(num,uplist_num,direct) {
	busRouteCoordinate(num,direct);//地图显示整条线路
	var Line = Global_busRoutes[num].Line;
	var LineId = Line.Id;
	var firstStation = "";
	var lastStation = "";
	var StationInfo;
 	 if(direct==1){
 		firstStation=Line.UpStartStationName;
 		lastStation=Line.UpEndStationName;                                          
 		StationInfo=Global_busRoutes[num].UpList;
 	 }else{
 		firstStation=Line.DownStartStationName;
 		lastStation=Line.DownEndStationName;
 		StationInfo=Global_busRoutes[num].DownList;
 	 }
	
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
			'direct':direct,
			'linedetails':true		
		},
		success : function(data) {
			if(data.length!=0){
				var list = JSON.parse(data);
				var GetNewLineList = list.LineList;
				if(direct==1){
				var html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' + name + '</label><label class="map_bus_link" onclick=details("'+LineId+'","'+StationId+'")>详情&raquo;</label>'+
				'<img  class="map_bus_colloct" src="WebPage/image/common/collect.png" onclick=SaveStationFavor("'+LineId+'")/></div><div class="map_bus_div2">';
				for(var j=0;j<GetNewLineList.length;j++){
					if(GetNewLineList[j].Id==LineId){
						html += '<a  href="javascript:void(0)" onclick=poupContent("'+GetNewLineList[j].Id+'","'+StationId+'","'+direct+'")  class="map_bus_div2_select">'+ GetNewLineList[j].Name+ '</a>';
					}else{
						html += '<a  href="javascript:void(0)" onclick=poupContent("'+GetNewLineList[j].Id+'","'+StationId+'","'+direct+'") class="map_bus_div2_noselect">'+  GetNewLineList[j].Name+ '</a>';
					}
				}
				html += '</div><div id="poupcontent"></div>';
				
				var div3=$$('<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + Line.Name + '</label>'+
						'<div class="map_bus_div3_l1">'+firstStation+'<img src="WebPage/image/common/busStation_srart_end.png"/>'+lastStation+'</div>'+
						'<div class="map_bus_div3_l2"><img src="WebPage/image/common/bus_pass.png"/>途中<img src="WebPage/image/common/refresh.png"/>刷新</div></div>');
				var div4=$$('<div class="map_bus_div4"><label>首末班时间：'+Line.LineUpStartTime+'-'+Line.LineUpEndTime+'&nbsp;全程：'+Line.Price+'元</label></div>');
				var div5=$$('<div class="map_bus_div5"><img src="WebPage/image/common/bus_end.png"/></div>');
				var maxwindth=StationInfo.length*32;//ul 宽度
				var content = '<div class="map_bus_div6"><a class="map_bus_div6_left" style="visibility:hidden" onclick="BusStationListMove_left(this)"><img src="WebPage/image/common/arrow_left_normal.png"/></a>'+
					'<div class="map_bus_div6_content"><ul style="width:'+maxwindth+'px">';
				for (var i = 0; i < StationInfo.length; i++) {
					var busStationName = StationInfo[i].StationName;
					content+='<li id="busicon'+i+'" class="busicon"></li>';
					content += '<li><a href="javascript:void(0)" onclick=details("'+LineId+'","'+StationInfo[i].StationId+'",'+direct+')>'
							+ busStationName + '</a></li>';
				}
				
				content += '</ul></div><a class="map_bus_div6_right" onclick="BusStationListMove_Right(this)"><img src="WebPage/image/common/arrow_right_normal.png"/></a></div>';
				var lonlat=lon+","+lat;
				var searchContent='<div class="map_bus_div7"><span class="map_bus_div7_span1" onclick="ChangeSpan_Way(this,1)"><img src="WebPage/image/common/start_icon.png"/>到这里去</span><span onclick="ChangeSpan_Way(this,2)" class="map_bus_div7_span"><img src="WebPage/image/common/end_icon.png"/>从这里出发</span><span onclick="ChangeSpan_Way(this,3)" class="map_bus_div7_span"><img src="WebPage/image/common/search_icon.png"/>附近找</span>'+
				'<div id="map_bus_div7_content1" class="map_bus_div7_content1"><input type="text" class="input_tex6" id="map_bus_div7_cnt1"/><span class="map_bus_div7_button" onclick=transfer_Way(1,"'+lonlat+'","'+name+'")>公交</span><span class="map_bus_div7_button" onclick=transfer_Way(3,"'+lonlat+'","'+name+'")>自驾</span></div>'+
				'<div id="map_bus_div7_content2" class="map_bus_div7_content"><input type="text" class="input_tex6" id="map_bus_div7_cnt2"/><span class="map_bus_div7_button" onclick=transfer_Way(2,"'+lonlat+'","'+name+'")>公交</span><span class="map_bus_div7_button" onclick=transfer_Way(4,"'+lonlat+'","'+name+'")>自驾</span></div>'+
				'<div id="map_bus_div7_content3" class="map_bus_div7_content"><input type="text" class="input_tex6" id="map_bus_div7_cnt3"/><span class="map_bus_div7_button" onclick="searchKey_Way()">搜索</span></div></div>';
				var popup=map.createPopup(lonlat,500,430,html);
				$$('#poupcontent').empty().append(div3);
				$$('#poupcontent').append(div4);
				$$('#poupcontent').append(div5);
				$$('#poupcontent').append(content);
				$$('#poupcontent').append(lonlat);
				Gloval_select_lineid=LineId;
				Gloval_select_stationid=StationInfo[StationInfo.length-1].StationId;
				Gloval_select_direct=direct;
				Gloval_select_stationlength=StationInfo.length;
				QueryOriginBusArrive();
				}
				if(direct==2){
					var html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' + name + '</label><label class="map_bus_link" onclick=details("'+LineId+'","'+StationId+'")>详情&raquo;</label>'+
					'<img  class="map_bus_colloct" src="WebPage/image/common/collect.png" onclick=SaveStationFavor("'+LineId+'")/></div><div class="map_bus_div2">';
					for(var j=0;j<GetNewLineList.length;j++){
						if(GetNewLineList[j].Id==LineId){
							html += '<a  href="javascript:void(0)" onclick=poupContent("'+GetNewLineList[j].Id+'","'+StationId+'","'+direct+'") class="map_bus_div2_select">'+ GetNewLineList[j].Name+ '</a>';
						}else{
							html += '<a  href="javascript:void(0)" onclick=poupContent("'+GetNewLineList[j].Id+'","'+StationId+'","'+direct+'") class="map_bus_div2_noselect">'+  GetNewLineList[j].Name+ '</a>';
						}
					}
					html += '</div><div id="poupcontent"></div>';
					
					var div3=$$('<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + Line.Name + '</label>'+
							'<div class="map_bus_div3_l1">'+firstStation+'<img src="WebPage/image/common/busStation_srart_end.png"/>'+lastStation+'</div>'+
							'<div class="map_bus_div3_l2"><img src="WebPage/image/common/bus_pass.png"/>途中<img src="WebPage/image/common/refresh.png"/>刷新</div></div>');
					var div4=$$('<div class="map_bus_div4"><label>首末班时间：'+Line.LineDownStartTime+'-'+Line.LineDownEndTime+'&nbsp;全程：'+Line.Price+'元</label></div>');
					var div5=$$('<div class="map_bus_div5"><img src="WebPage/image/common/bus_end.png"/></div>');
					var maxwindth=StationInfo.length*32;//ul 宽度
					var content = '<div class="map_bus_div6"><a class="map_bus_div6_left" style="visibility:hidden" onclick="BusStationListMove_left(this)"><img src="WebPage/image/common/arrow_left_normal.png"/></a>'+
						'<div class="map_bus_div6_content"><ul style="width:'+maxwindth+'px">';
					for (var i = 0; i < StationInfo.length; i++) {
						var busStationName = StationInfo[i].StationName;
						content+='<li id="busicon'+i+'" class="busicon"></li>';
						content += '<li><a href="javascript:void(0)" onclick=details("'+LineId+'","'+StationInfo[i].StationId+'",'+direct+')>'
								+ busStationName + '</a></li>';
					}
					
					content += '</ul></div><a class="map_bus_div6_right" onclick="BusStationListMove_Right(this)"><img src="WebPage/image/common/arrow_right_normal.png"/></a></div>';
					var lonlat=lon+","+lat;
					var searchContent='<div class="map_bus_div7"><span class="map_bus_div7_span1" onclick="ChangeSpan_Way(this,1)"><img src="WebPage/image/common/start_icon.png"/>到这里去</span><span onclick="ChangeSpan_Way(this,2)" class="map_bus_div7_span"><img src="WebPage/image/common/end_icon.png"/>从这里出发</span><span onclick="ChangeSpan_Way(this,3)" class="map_bus_div7_span"><img src="WebPage/image/common/search_icon.png"/>附近找</span>'+
					'<div id="map_bus_div7_content1" class="map_bus_div7_content1"><input type="text" class="input_tex6" id="map_bus_div7_cnt1"/><span class="map_bus_div7_button" onclick=transfer_Way(1,"'+lonlat+'","'+name+'")>公交</span><span class="map_bus_div7_button" onclick=transfer_Way(3,"'+lonlat+'","'+name+'")>自驾</span></div>'+
					'<div id="map_bus_div7_content2" class="map_bus_div7_content"><input type="text" class="input_tex6" id="map_bus_div7_cnt2"/><span class="map_bus_div7_button" onclick=transfer_Way(2,"'+lonlat+'","'+name+'")>公交</span><span class="map_bus_div7_button" onclick=transfer_Way(4,"'+lonlat+'","'+name+'")>自驾</span></div>'+
					'<div id="map_bus_div7_content3" class="map_bus_div7_content"><input type="text" class="input_tex6" id="map_bus_div7_cnt3"/><span class="map_bus_div7_button" onclick="searchKey_Way()">搜索</span></div></div>';
					var popup=map.createPopup(lonlat,500,430,html);
					$$('#poupcontent').empty().append(div3);
					$$('#poupcontent').append(div4);
					$$('#poupcontent').append(div5);
					$$('#poupcontent').append(content);
					$$('#poupcontent').append(searchContent);
					Gloval_select_lineid=LineId;
					Gloval_select_stationid=StationInfo[StationInfo.length-1].StationId;
					Gloval_select_direct=direct;
					Gloval_select_stationlength=StationInfo.length;
					QueryOriginBusArrive();
					}
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	
	});							
}

//地图上某个线路的点击浮云内容
function poupContent(lineId,stationId,direct) {
	map.delWindow();
	//显示当前站点的浮云
	$$.ajax( {
		url : 'QueryLineAndStationTogethor',
		type : 'post',
		dataType : 'json',
		data : {
			'stationID' : stationId,
			'lineID':lineId,
			'direct':direct
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			//线路上行信息
			if(direct==1){
				var list = data;
				var StationInfo = list.busStationDetail.station;
				var StationLineInfo = list.busStationDetail.lineList;
				var LineInfo = list.busLineDetail;
				var upListInfo=LineInfo.upList;//线路上行信息
				//var coordinates ="";
				CreatsCoordinateBylineId(lineId,direct);
				var lon=StationInfo.longitude;
				var lat=StationInfo.latitude;
				
				var html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' + StationInfo.name + 
							'</label><label class="map_bus_link" onclick=details("'+lineId+'","'+stationId+'",1)>详情&raquo;</label>'+
							'<img  class="addTr_div_1_colloct" src="WebPage/image/common/collect.png" onclick=SaveStationFavor(this,"'+lineId+'",1) /></div><div class="map_bus_div2">';
				for(var j=0;j<StationLineInfo.length;j++){
					if(StationLineInfo[j].id==lineId){
						html += '<a  href="javascript:void(0)" onclick=poupContent("'+StationLineInfo[j].id+'","'+stationId+'","'+direct+'") '+
							' class="map_bus_div2_select">'+StationLineInfo[j].name+ '</a>';
					}else{
						html += '<a  href="javascript:void(0)" onclick=poupContent("'+StationLineInfo[j].id+'","'+stationId+'","'+direct+'") '+
							' class="map_bus_div2_noselect">'+ StationLineInfo[j].name+ '</a>';
					}
				}
				html += '</div><div id="poupcontent"></div>';
				var upEndStationName=LineInfo.line.upEndStationName;
				var upStartStationName=LineInfo.line.upStartStationName
				var div3=$$('<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + LineInfo.line.name + '</label>'+
							'<div class="map_bus_div3_l1">'+upStartStationName+'<img src="WebPage/image/common/busStation_srart_end.png"/>'+
							upEndStationName+'</div><div class="map_bus_div3_l2"><img src="WebPage/image/common/bus_pass.png"/>途中'+
							'<img src="WebPage/image/common/refresh.png" onclick="QueryOriginBusArrive()" style="cursor: pointer;" />刷新</div></div>');
				var div4=$$('<div class="map_bus_div4"><label>首末班时间：'+LineInfo.line.lineUpStartTime+'-'+LineInfo.line.lineUpEndTime+'&nbsp;全程：'+
							LineInfo.line.price+'元</label></div>');
				var div5=$$('<div class="map_bus_div5"></div>');//实时到站
				var maxwindth=upListInfo.length*32;//ul 宽度
				var content = '<div class="map_bus_div6"><a class="map_bus_div6_left" style="visibility:hidden" onclick="BusStationListMove_left(this)">'+
								'<img src="WebPage/image/common/arrow_left_normal.png"/></a><div class="map_bus_div6_content"><ul style="width:'+maxwindth+'px">';
				for (var i = 0; i < upListInfo.length; i++) {
					var busStationName = upListInfo[i].stationName;
					content+='<li id="busicon'+i+'" class="busicon"></li>';
					content += '<li><a href="javascript:void(0)" onclick=details("'+lineId+'","'+upListInfo[i].stationId+'",1)>'
							+ busStationName + '</a></li>';
					
					//coordinates+=upListInfo[i].longitude+","+upListInfo[i].latitude+";";
					addPointPoup(upListInfo[i].longitude, upListInfo[i].latitude, upListInfo[i].stationId, LineInfo.line.name,lineId,direct);
				}
				//map.drawLine(coordinates,"#7b9ee9","solid");
				if(maxwindth>500){
					content += '</ul></div><a class="map_bus_div6_right" onclick="BusStationListMove_Right(this)"><img src="WebPage/image/common/arrow_right_normal.png"/></a></div>';
				}else{
					content += '</ul></div></div>';
				}
				var lonlat=lon+","+lat;
				var searchContent='<div class="map_bus_div7"><span class="map_bus_div7_span1" onclick="ChangeSpan_Way(this,1)"><img src="WebPage/image/common/start_icon.png"/>到这里去</span><span onclick="ChangeSpan_Way(this,2)" class="map_bus_div7_span"><img src="WebPage/image/common/end_icon.png"/>从这里出发</span><span onclick="ChangeSpan_Way(this,3)" class="map_bus_div7_span"><img src="WebPage/image/common/search_icon.png"/>附近找</span>'+
				'<div id="map_bus_div7_content1" class="map_bus_div7_content1"><input type="text" class="input_tex6" id="map_bus_div7_cnt1"/><span class="map_bus_div7_button" onclick=transfer_Way(1,"'+lonlat+'","'+StationInfo.name+'")>公交</span><span class="map_bus_div7_button" onclick=transfer_Way(3,"'+lonlat+'","'+StationInfo.name+'")>自驾</span></div>'+
				'<div id="map_bus_div7_content2" class="map_bus_div7_content"><input type="text" class="input_tex6" id="map_bus_div7_cnt2"/><span class="map_bus_div7_button" onclick=transfer_Way(2,"'+lonlat+'","'+StationInfo.name+'")>公交</span><span class="map_bus_div7_button" onclick=transfer_Way(4,"'+lonlat+'","'+StationInfo.name+'")>自驾</span></div>'+
				'<div id="map_bus_div7_content3" class="map_bus_div7_content"><input type="text" class="input_tex6" id="map_bus_div7_cnt3"/><span class="map_bus_div7_button" onclick="searchKey_Way()">搜索</span></div></div>';
				var popup=map.createPopup(lonlat,500,430,html);
				$$('#poupcontent').empty().append(div3);
				$$('#poupcontent').append(div4);
				$$('#poupcontent').append(div5);
				$$('#poupcontent').append(content);
				$$('#poupcontent').append(searchContent);
				Gloval_select_lineid=lineId;
				Gloval_select_stationid=upListInfo[upListInfo.length-1].stationId;
				Gloval_select_direct=direct;
				Gloval_select_stationlength=upListInfo.length;
				QueryOriginBusArrive();
			}
			//线路下行信息
			if(direct==2){
				var list = data;
				var StationInfo = list.busStationDetail.station;
				var StationLineInfo = list.busStationDetail.lineList;
				var LineInfo = list.busLineDetail;
				var downListInfo=LineInfo.downList;//线路下行信息
				//var coordinates ="";
				CreatsCoordinateBylineId(lineId,direct);
				var lon=StationInfo.longitude;
				var lat=StationInfo.latitude;
				
				var html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' + StationInfo.name + 
							'</label><label class="map_bus_link" onclick=details("'+lineId+'","'+stationId+'",2)>详情&raquo;</label>'+
							'<img  class="addTr_div_1_colloct" src="WebPage/image/common/collect.png" onclick=SaveStationFavor(this,"'+lineId+'",2) /></div><div class="map_bus_div2">';
				for(var j=0;j<StationLineInfo.length;j++){
					if(StationLineInfo[j].id==lineId){
						html += '<a  href="javascript:void(0)" onclick=poupContent("'+StationLineInfo[j].id+'","'+stationId+'","'+direct+'") '+
							' class="map_bus_div2_select">'+StationLineInfo[j].name+ '</a>';
					}else{
						html += '<a  href="javascript:void(0)" onclick=poupContent("'+StationLineInfo[j].id+'","'+stationId+'","'+direct+'") '+
							' class="map_bus_div2_noselect">'+ StationLineInfo[j].name+ '</a>';
					}
				}
				html += '</div><div id="poupcontent"></div>';
				var downEndStationName=LineInfo.line.downEndStationName;
				var downStartStationName=LineInfo.line.downStartStationName
				var div3=$$('<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + LineInfo.line.name + '</label>'+
							'<div class="map_bus_div3_l1">'+downStartStationName+'<img src="WebPage/image/common/busStation_srart_end.png"/>'+
							downEndStationName+'</div><div class="map_bus_div3_l2"><img src="WebPage/image/common/bus_pass.png"/>途中'+
							'<img src="WebPage/image/common/refresh.png" onclick="QueryOriginBusArrive()" style="cursor: pointer;" />刷新</div></div>');
				var div4=$$('<div class="map_bus_div4"><label>首末班时间：'+LineInfo.line.lineDownStartTime+'-'+LineInfo.line.lineDownEndTime+'&nbsp;全程：'+
							LineInfo.line.price+'元</label></div>');
				var div5=$$('<div class="map_bus_div5"></div>');//实时到站
				var maxwindth=downListInfo.length*32;//ul 宽度
				var content = '<div class="map_bus_div6"><a class="map_bus_div6_left" style="visibility:hidden" onclick="BusStationListMove_left(this)">'+
								'<img src="WebPage/image/common/arrow_left_normal.png"/></a><div class="map_bus_div6_content"><ul style="width:'+maxwindth+'px">';
				for (var i = 0; i < downListInfo.length; i++) {
					var busStationName = downListInfo[i].stationName;
					content+='<li id="busicon'+i+'" class="busicon"></li>';
					content += '<li><a href="javascript:void(0)" onclick=details("'+lineId+'","'+downListInfo[i].stationId+'",2)>'
							+ busStationName + '</a></li>';
					
					//coordinates+=downListInfo[i].longitude+","+downListInfo[i].latitude+";";
					addPointPoup(downListInfo[i].longitude, downListInfo[i].latitude, downListInfo[i].stationId, LineInfo.line.name,lineId,direct);
				}
				//map.drawLine(coordinates,"#7b9ee9","solid");
				if(maxwindth>500){
					content += '</ul></div><a class="map_bus_div6_right" onclick="BusStationListMove_Right(this)"><img src="WebPage/image/common/arrow_right_normal.png"/></a></div>';
				}else{
					content += '</ul></div></div>';
				}
				var lonlat=lon+","+lat;
				var searchContent='<div class="map_bus_div7"><span class="map_bus_div7_span1" onclick="ChangeSpan_Way(this,1)"><img src="WebPage/image/common/start_icon.png"/>到这里去</span><span onclick="ChangeSpan_Way(this,2)" class="map_bus_div7_span"><img src="WebPage/image/common/end_icon.png"/>从这里出发</span><span onclick="ChangeSpan_Way(this,3)" class="map_bus_div7_span"><img src="WebPage/image/common/search_icon.png"/>附近找</span>'+
				'<div id="map_bus_div7_content1" class="map_bus_div7_content1"><input type="text" class="input_tex6" id="map_bus_div7_cnt1"/><span class="map_bus_div7_button" onclick=transfer_Way(1,"'+lonlat+'","'+StationInfo.name+'")>公交</span><span class="map_bus_div7_button" onclick=transfer_Way(3,"'+lonlat+'","'+StationInfo.name+'")>自驾</span></div>'+
				'<div id="map_bus_div7_content2" class="map_bus_div7_content"><input type="text" class="input_tex6" id="map_bus_div7_cnt2"/><span class="map_bus_div7_button" onclick=transfer_Way(2,"'+lonlat+'","'+StationInfo.name+'")>公交</span><span class="map_bus_div7_button" onclick=transfer_Way(4,"'+lonlat+'","'+StationInfo.name+'")>自驾</span></div>'+
				'<div id="map_bus_div7_content3" class="map_bus_div7_content"><input type="text" class="input_tex6" id="map_bus_div7_cnt3"/><span class="map_bus_div7_button" onclick="searchKey_Way()">搜索</span></div></div>';
				var popup=map.createPopup(lonlat,500,430,html);
				$$('#poupcontent').empty().append(div3);
				$$('#poupcontent').append(div4);
				$$('#poupcontent').append(div5);
				$$('#poupcontent').append(content);
				$$('#poupcontent').append(searchContent);
				Gloval_select_lineid=lineId;
				Gloval_select_stationid=downListInfo[downListInfo.length-1].stationId;
				Gloval_select_direct=direct;
				Gloval_select_stationlength=downListInfo.length;
				QueryOriginBusArrive();
			}
		},
		complete : function() {
			CloseLoadingDiv();
	}
		
	});							

							
}
function CreatsCoordinateBylineId(lineId,direct){
	$$.ajax( {
		url : 'QueryBaseLineTrace',
		type : 'post',
		dataType : 'json',
		data : {
			'lineID' : lineId,
			'direct':direct
		},
		success : function(data) {
			var list = data;
			if (list != undefined) {
				var coordinates =list.trace;
				map.drawLine(coordinates,DRAWLINE_COLOR,"solid",DRAWLINE_SIZE);
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	});
	
}

//实时到站信息
function QueryOriginBusArrive(){
	$$("#poupcontent .busicon").html("");
		$$.ajax( {
			url : 'QueryOriginBusArrive',
			type : 'post',
			dataType : 'json',
			data : {
				'lineID' : Gloval_select_lineid,
				'direct':Gloval_select_direct,
				'stationID':Gloval_select_stationid,
				'count':10
			},
			success : function(data) {
				if(data!=""){
					var list = JSON.parse(data).BusLocationList;
					if(list!=""){
						for(var i=0;i<list.length;i++){
							var linum=Number(Gloval_select_stationlength)-Number(list[i].StationIndex);
							$$("#poupcontent #busicon"+linum).html("<img src='WebPage/image/common/bus_pass.png'/>");
						}
					}else{
							//$$("#poupcontent .map_bus_div5").html("进入盲区");
					}
				}
				else{
						//$$("#poupcontent .map_bus_div5").html("进入盲区");
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
						"<label class='addTr_div_1_link' onclick=details('"+routId+"','"+busStationId+"',"+LineList[i].Direct+")>详情&raquo;</label>"+
						"<img  class='addTr_div_1_colloct' src='WebPage/image/common/favorates.png' onclick=DeleteStationFavor('"+num+"',2,'"+id+"') /></div>"));
				newTr.append($$("<div class='addTr_div_3' id='addTr_"+num+"_3' onclick=openDiv('"+num+"','Favorites_datalist_2')>" +
						"<img id='img_"+num+"' src='WebPage/image/common/plus.png'/>相关公交线路</label></div>"));
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
			'stationID' : StationId
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
							 '",'+LineList[i].Direct+')>详情&raquo;</label><img class="map_bus_colloct" src="WebPage/image/common/favorates.png" onclick=DeleteStationFavor("'+num+'",2,"'+StationId+'") /></div><div class="map_bus_div2">';
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
									'",'+LineList[i].Direct+')>详情&raquo;</label><img class="map_bus_colloct" src="WebPage/image/common/favorates.png" onclick=DeleteStationFavor("'+num+'",2,"'+StationId +'") /></div><div class="map_bus_div2">';
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
				
				var image="WebPage/image/graphical/map_point_normal.png";
				map.addCustomerMarker(lon+","+lat,image,(num+1)+"",26,"white","Times New Roman");
				map.createPopup(lon+","+lat,500,360,html);
				
				GetPoupContentForHtml(routeid,LineList[i].Direct);
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
			newTr_top.append($$("<div class='addDiv_top_div_2'><label>全程距离约"+list[i].Distance+"米</label><img src='WebPage/image/common/favorates.png' onclick=DeleteStationFavor('"+num+"','3','"+id+"') /></div>"));
			newDiv.append(newTr_top);
			
			var newTr = $$("<div class='addDiv_content' id='addDiv_"+i+"' style='display:none'></div>");
			//起点标志
			newTr.append($$("<div class='addDiv_div_ends'><img src='WebPage/image/graphical/icon_origin.png'/><label>起点名称</label></div>"));
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
			newTr.append($$("<div class='addDiv_div_ends'><img src='WebPage/image/graphical/icon_destenation.png'/><label>终点名称</label></div>"));
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
			addPointPoup_transfer(spstr[spstr.length-2],"WebPage/image/common/map_point_bus.png",21);
			addPointPoup_transfer(spstr[0],"WebPage/image/common/map_point_bus.png",21);
			map.drawLine(coordinate[k][1],DRAWLINE_COLOR,"solid",DRAWLINE_SIZE);
		}
	}
	addPointPoup_transfer(T_startlonlat,"WebPage/image/common/map_point_start.png",36);
	addPointPoup_transfer(T_endlonlat,"WebPage/image/common/map_point_end.png",36);
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
