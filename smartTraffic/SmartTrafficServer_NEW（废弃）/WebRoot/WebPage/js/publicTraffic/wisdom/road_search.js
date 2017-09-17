var map;
var linenum=0;
var Gloval_select_lineid=0;//选择的线路id 
var Gloval_select_stationid=0;//最后一个站点id
var Gloval_select_direct=0;//选择
var Gloval_select_stationlength=0;//站点个数
$$(document).ready(function() {
	map = new XMap("simpleMap",1);//天地图
	map.addPanZoomBar();
	InitializeTianMap();
	InitializeFontColor("searchContent");
	OnLoadPage();
	
});
function OnLoadPage(){
	if($$("#searchcontent").val()!="null"){
		$$("#searchContent").val($$("#searchcontent").val());
		$$("#searchContent").removeAttr("onBlur");
		$$("#searchContent").removeAttr("onFocus");
		$$("#searchContent").css("color","#000");
		queryBusRoute();
	}
}
//线路搜索
function queryBusRoute() {
	if(!SearchContentCheck("searchContent")){
		return;
	}
	$$(".addTr").empty();
	$$(".addTr").remove();
	$$(".layout1_left_datalist_tishi").remove();
	$$(".addTr_pressed").remove();
	$$(".layout1_left_datalist_title span").css("visibility","visible");
	$$(".layout1_left_trodu").empty();
	$$(".layout1_left_trodu").remove();
	map.delWindow(); 
	linenum=0;
	$$.ajax( {
		url : 'QueryLineDetailInfoByName',
		type : 'post',
		dataType : 'json',
		data : {
			'lineName' : $$("#searchContent").val()		
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			if(data.length!=0){
			var list = data;
				if (list == undefined) {
					$$("#searchReasultCount").text("0");
					TableIsNull();
				} else {
					$$("#searchReasultCount").text(list.length);
					QueryOriginBusLineByName(list,list.length);
				}
			}else{
				$$("#searchReasultCount").text("0");
				TableIsNull();
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
//左侧线路显示
function QueryOriginBusLineByName(list, listlength,direct) {
	for ( var i = 0; i < listlength; i++) {
		var lineInfo=list[i].line;
		var upListInfo=list[i].upList;
		var downListInfo=list[i].downList;
		var up_firstStation=lineInfo.upStartStationName;
     	var up_lastStation=lineInfo.upEndStationName;
     	var down_firstStation=lineInfo.downStartStationName;
    	var down_lastStation=lineInfo.downEndStationName;
    	//上行
    	if(upListInfo.length!=undefined||upListInfo.length!=0){
    		
	    	var html="<div class='addTr' id='addTr_"+linenum+"'>";
			html+="<div class='addTr_div_1' id='addTr_"+linenum+"_1'><label class='addTr_div_1_title' onclick=busRouteCoordinate('"+lineInfo.id+"',1)>"+
					lineInfo.name+"("+up_firstStation+"-"+up_lastStation+")</label><label class='addTr_div_1_link' onclick=Line_details('"+lineInfo.id+"','-1',1)>详情&raquo;</label>" +
					"<img  class='addTr_div_1_colloct' src='WebPage/image/common/collect.png' onclick=SaveStationFavor(this,'"+lineInfo.id+"',1) /></div>";
			html+="<div class='addTr_div_2' id='addTr_"+linenum+"_2'><label>首末班时间："+lineInfo.lineUpStartTime+"-"+lineInfo.lineUpEndTime+
					"&nbsp;全程："+lineInfo.price+"元</label></div>";
			html+="<div class='addTr_div_3' id='addTr_"+linenum+"_3' ><label onclick=openDiv('"+linenum+"') >" +
					"<img id='img_"+linenum+"' src='WebPage/image/common/plus.png'/>途径站点</label>";
			html+="<div class='addTr_div_4' id='addTr_"+linenum+"_4' style='display:none'>";
			
				for ( var j = 0; j < upListInfo.length; j++) {
					html+="<label onclick=busStationPoup('"+lineInfo.id+"','"+upListInfo[j].stationId+"',1) >"+(j+1)+"."+upListInfo[j].stationName+"</label>";
				}
			
			html+="</div></div></div>";
			$$(".layout1_left_datalist").html($$(".layout1_left_datalist").html()+html);
			linenum++;
    	}
		
		//下行
	if(downListInfo.length!=undefined||downListInfo.length!=0){
		
    	var html="<div class='addTr' id='addTr_"+linenum+"'>";
		html+="<div class='addTr_div_1' id='addTr_"+linenum+"_1'><label class='addTr_div_1_title' onclick=busRouteCoordinate('"+lineInfo.id+"',2)>"+
				lineInfo.name+"("+down_firstStation+"-"+down_firstStation+")</label><label class='addTr_div_1_link' onclick=Line_details('"+lineInfo.id+"','-1',1)>详情&raquo;</label>" +
				"<img  class='addTr_div_1_colloct' src='WebPage/image/common/collect.png' onclick=SaveStationFavor(this,'"+lineInfo.id+"',2) /></div>";
		html+="<div class='addTr_div_2' id='addTr_"+linenum+"_2'><label>首末班时间："+lineInfo.lineDownStartTime+"-"+lineInfo.lineDownEndTime+
				"&nbsp;全程："+lineInfo.price+"元</label></div>";
		html+="<div class='addTr_div_3' id='addTr_"+linenum+"_3' ><label onclick=openDiv('"+linenum+"') >" +
				"<img id='img_"+linenum+"' src='WebPage/image/common/plus.png'/>途径站点</label>";
		html+="<div class='addTr_div_4' id='addTr_"+linenum+"_4' style='display:none'>";
		
			for ( var j = 0; j < downListInfo.length; j++) {
				html+="<label onclick=busStationPoup('"+lineInfo.id+"','"+downListInfo[j].stationId+"',2) >"+(j+1)+"."+downListInfo[j].stationName+"</label>";
			}
		
		html+="</div></div></div>";
		$$(".layout1_left_datalist").html($$(".layout1_left_datalist").html()+html);
		linenum++;
	}
	
	}
$$("#searchReasultCount").text(linenum);
}
function TableIsNull(){
	$$(".layout1_left_datalist").append($$("<div class='layout1_left_datalist_tishi'>没有符合条件的结果</div>"));
}


//地图显示整条线路和上面的点    参数线路id
function busRouteCoordinate(lineId,direct) {
	map.delWindow();

	$$.ajax( {
		url : 'QueryOriginBusLineByID',
		type : 'post',
		dataType : 'json',
		data : {
			'lineID' : lineId,
			'direct':direct,
			'linedetails':true
		},
		success : function(data) {
			var list = JSON.parse(data);
			if (list != undefined) {
				if(direct==1){
					var UpList=list.UpList;
					for ( var j = 0; j < UpList.length; j++) {
						var k=1;
						if(j==0) k=0;
						if(j==UpList.length-1) k=2;
						addPointPoup(UpList[j].Longitude, UpList[j].Latitude,UpList[j].StationId,list.Line.Name,lineId,direct,k);
					}
				}
				if(direct==2){
					var DownList=list.DownList;
					for ( var j = 0; j < DownList.length; j++) {
						var k=1;
						if(j==0) k=0;
						if(j==DownList.length-1) k=2;
						addPointPoup(DownList[j].Longitude, DownList[j].Latitude,DownList[j].StationId,list.Line.Name,lineId,direct,k);
					}
				}
				
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	});

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
			//map.createLineSuitable();
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
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
				//map.createLineSuitable();
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	});
	
}

//左侧点击站点，显示线路和站点浮云
function busStationPoup(lineId,stationId,direct) {
	//busRouteCoordinate(lineId);//地图显示整条线路
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
						html += '<a  href="javascript:void(0)" onclick=busStationPoup("'+StationLineInfo[j].id+'","'+stationId+'","'+direct+'") '+
							' class="map_bus_div2_select">'+StationLineInfo[j].name+ '</a>';
					}else{
						html += '<a  href="javascript:void(0)" onclick=busStationPoup("'+StationLineInfo[j].id+'","'+stationId+'","'+direct+'") '+
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
					if(upListInfo[i].stationId==stationId){
						content += '<li><a href="javascript:void(0)" onclick=details("'+lineId+'","'+upListInfo[i].stationId+'",1) class=BusSelectStation >'
						+ busStationName + '</a></li>';	
					}else{
						content += '<li><a href="javascript:void(0)" onclick=details("'+lineId+'","'+upListInfo[i].stationId+'",1)  >'
						+ busStationName + '</a></li>';
					}
					var k=1;
					if(i==0) k=0;
					if(i==upListInfo.length-1) k=2;
					addPointPoup(upListInfo[i].longitude, upListInfo[i].latitude, upListInfo[i].stationId, LineInfo.line.name,lineId,direct,k);
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
				var popup=map.createPopup(lonlat,500,480,html);
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
						html += '<a  href="javascript:void(0)" onclick=busStationPoup("'+StationLineInfo[j].id+'","'+stationId+'","'+direct+'") '+
							' class="map_bus_div2_select">'+StationLineInfo[j].name+ '</a>';
					}else{
						html += '<a  href="javascript:void(0)" onclick=busStationPoup("'+StationLineInfo[j].id+'","'+stationId+'","'+direct+'") '+
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
					
					if(downListInfo[i].stationId==stationId){
						content += '<li><a href="javascript:void(0)" onclick=details("'+lineId+'","'+downListInfo[i].stationId+'",2) class=BusSelectStation >'
						+ busStationName + '</a></li>';	
					}else{
						content += '<li><a href="javascript:void(0)" onclick=details("'+lineId+'","'+downListInfo[i].stationId+'",2)  >'
						+ busStationName + '</a></li>';
					}
					
					var k=1;
					if(i==0) k=0;
					if(i==downListInfo.length-1) k=2;
					addPointPoup(downListInfo[i].longitude, downListInfo[i].latitude, downListInfo[i].stationId, LineInfo.line.name,lineId,direct,k);
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
				var popup=map.createPopup(lonlat,500,480,html);
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

//站点imageKind 0:起点 1：中间点 2：重点
function addPointPoup(lon, lat, stationId,lineName,lineId,direct,imageKind) {
	var lonlat = map.createLonlat(lon+","+lat);
	var icon;
	if(imageKind==0){
		icon = map.createIcon("WebPage/image/common/map_point_start.png",36);
	}else if(imageKind==2){
		icon = map.createIcon("WebPage/image/common/map_point_end.png",36);
	}
	else{
		icon = map.createIcon("WebPage/image/common/map_circle.png",11);
	}
	/*if(imageKind==0||imageKind==2){
		map.drawMarker(lon+","+lat,"WebPage/image/common/map_circle.png",11);
	}*/
	imgMarker = map.createMarker(lonlat, icon);
	map.addMarkerObject(imgMarker);
	/*imgMarker.events.register("mouseover",null,function(){
		map.createPopup(lon+","+lat,100,30,lineName);
	});*/
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
			beforeSend : function() {
				ShowLoadingDiv();
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
					if(stationId==upListInfo[i].stationId){
						content += '<li><a href="javascript:void(0)" onclick=details("'+lineId+'","'+upListInfo[i].stationId+'",1) class=BusSelectStation >'
						+ busStationName + '</a></li>';
					}else{
						content += '<li><a href="javascript:void(0)" onclick=details("'+lineId+'","'+upListInfo[i].stationId+'",1) >'
						+ busStationName + '</a></li>';
					}
					
					
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
				var popup=map.createPopup(lonlat,500,480,html);
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
					if(stationId==downListInfo[i].stationId){
						content += '<li><a href="javascript:void(0)" onclick=details("'+lineId+'","'+downListInfo[i].stationId+'",2) class=BusSelectStation >'
						+ busStationName + '</a></li>';
					}else{
						content += '<li><a href="javascript:void(0)" onclick=details("'+lineId+'","'+downListInfo[i].stationId+'",2)>'
						+ busStationName + '</a></li>';
					}
				
					
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
				var popup=map.createPopup(lonlat,500,480,html);
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
			},
			complete : function() {
				CloseLoadingDiv();
		}
		
		});							

	});
}

//站点详情页面
function details(routeId,busStationId,direct){
	window.open($$("#basePath").val()+"WebPage/page/publicTraffic/wisdom/bus_infor.jsp?routeId="+routeId+"&busStationId="+busStationId+"&direct="+direct);
}

//站点详情页面
function Line_details(routeId,busStationId,direct){
	window.open($$("#basePath").val()+"WebPage/page/publicTraffic/wisdom/line_infor.jsp?routeId="+routeId+"&busStationId="+busStationId+"&direct="+direct);
}

function openDiv(num){
	var image = "";
	image = $$("#img_"+num).attr("src");
	if (image.indexOf("plus", 0) != -1) {
		$$("#addTr_"+num+"_4").css("display","");
		$$("#img_"+num).attr("src","WebPage/image/common/minus.png");
	} else {
		$$("#addTr_"+num+"_4").css("display","none");
		$$("#img_"+num).attr("src","WebPage/image/common/plus.png");
	}

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
//收藏--线路
function SaveStationFavor(thisop,id,direct){
	if ($$("#LoginUser").val() == "null") {
		alert("请先登录");
	} else {
	//收藏
		$$.ajax( {
			url : 'SaveLineFavor',
			type : 'post',
			dataType : 'json',
			data : {
				'lineID' : id,
				'direct':direct
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

