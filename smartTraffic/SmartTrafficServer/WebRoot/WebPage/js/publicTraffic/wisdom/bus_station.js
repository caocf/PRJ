var map;
var Gloval_select_lineid=0;//选择的线路id 
var Gloval_select_stationid=0;//最后一个站点id
var Gloval_select_direct=0;//选择
var Gloval_select_stationlength=0;//站点个数

$$(document).ready(function() {
	map = new XMap("simpleMap", 1);
	map.addPanZoomBar();
	InitializeTianMap();
	InitializeFontColor("searchContent");
	OnLoadPage();
	
});
function OnLoadPage(){
	if($$("#searchcontent").val()!="null"){
		$$("#stationName").val($$("#searchcontent").val());
		$$("#stationName").removeAttr("onBlur");
		$$("#stationName").removeAttr("onFocus");
		$$("#stationName").css("color","#000");
		queryBusStation(1);
	}
}
document.onkeydown = function(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13&&e.srcElement.id=="stationName") { // enter 键
		queryBusStation(1);
	}
};
function queryBusStation(selectedPage) {
	$$.ajax( {
		url :'QueryOriginBusStation',
		type : 'post',
		dataType : 'json',
		data : {
			'stationName':$$("#stationName").val()
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			if(data.length!=0){
				searchCallback(JSON.parse(data));
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
function gotoPageNo(totalPage) {
	var pageNo = $$(".indexCommon").attr("value");
	var str = $$.trim(pageNo);
	var reg = /^\d+$/;
	if (!reg.test(str)) {
		alert("请输入数字");
		$$(".indexCommon").val("");
		return true;
	}
	if (parseInt(pageNo) > parseInt(totalPage)) {
		alert("请输入1到" + totalPage + "之间的页码");
	} else {
		GetParking(pageNo);
	}
}

// 搜索回调函数
function searchCallback(data) {
	$$(".layout1_left_datalist ul").empty();
	$$(".layout1_left_datalist ul").remove();
	$$(".layout1_left_datalist_tishi").remove();
	$$(".layout1_left_datalist_title").css("visibility","visible");
	$$(".layout1_left_trodu").empty();
	$$(".layout1_left_trodu").remove();
	var StationList=data.StationList;
	$$("#searchReasultCount").text(StationList.length);

		map.removeAll();
		if (StationList.length == 0) {
			TableIsNull();
		}
		if (StationList && StationList.length > 0) {
			var len = StationList.length;
			var str = "";
			str += "<ul>";
			for ( var i = 0; i < len; i++) {
				var obj = StationList[i];
				if(i<100){
					str += "<li id='addLi_"+ i +"' onclick=stationPoup(this,'"+obj.Id+"','"+obj.List[0].Id+"','"+obj.List[0].Direct+"') class='addLi' style='float:left;'>" +
						"<div class='addLi_img'><img id='img_"+i+"' src='WebPage/image/newmap_icon/1.png'/></div>"+
						"<div class='addLi_right'><label class='addLi_name' >"+ obj.Name+"</label>" +
								"<img  class='addTr_div_1_colloct' src='WebPage/image/common/collect.png' onclick=SaveStationFavor(this,'"+obj.Id+"','"+ obj.Name+"','"+obj.Longitude+"','"+obj.Latitude+"') /></div></li>";
					//onclick=
				}else{
					str += "<li id='addLi_"+ i +"' onclick=stationPoup(this,'"+obj.Id+"','"+obj.List[0].Id+"','"+obj.List[0].Direct+"') class='addLi' style='float:left;'>" +
						"<div class='addLi_img'><img id='img_"+i+"' src='WebPage/image/graphical/map_point_normal.png'/><label>"+(i+1)+"</label></div>"+
						"<div class='addLi_right'><label class='addLi_name' >"+ obj.Name+"</label>" +
								"<img  class='addTr_div_1_colloct' src='WebPage/image/common/collect.png' onclick=SaveStationFavor(this,'"+obj.Id+"','"+ obj.Name+"','"+obj.Longitude+"','"+obj.Latitude+"') /></div></li>";
				}
				str +="<div class='lineName"+obj.Id+"' style='width:100%;float:left;'></div>";
				showLineInfo(obj.Id,obj.List[0].Id,obj.List[0].Direct);
				var lonlat = obj.Longitude+","+obj.Latitude;
				LeftPoup(i,obj.Id,obj.List[0].Id,lonlat,obj.List[0].Direct);
			}
			//map.createSuitable();
			str += "</ul>";
			$$(".layout1_left_datalist").append(str);
		}

	
}
/**
 * 显示站点下面的线路
 */
function showLineInfo(stationId,lineId,direct){
	$$.ajax({
		url:'QueryLineAndStationTogethor',
		type:'post',
		dataType:'json',
		data:{
			'stationID' : stationId,
			'lineID':lineId,
			'direct':direct
		},
		success:function(data){
			var html="";
			var list = data;
			var StationLineInfo = list.busStationDetail.lineList;
			if(direct==1){
				for(var j=0;j<StationLineInfo.length;j++){///
					//if(html==""){
						html+="<span class='anewLine'><a href='javascript:void(0)' onclick=showLinePopInfo("+stationId+","+StationLineInfo[j].id+","+direct+")>"+StationLineInfo[j].name+"</a></span>";
					//}else{
						html+=","+"<a>StationLineInfo[j].name</a>"
					//}
				}
			}
			if(direct==2){
				for(var j=0;j<StationLineInfo.length;j++){///
					//if(html==""){
						html+="<span class='anewLine'><a href='javascript:void(0)' onclick=showLinePopInfo("+stationId+","+StationLineInfo[j].id+","+direct+")>"+StationLineInfo[j].name+"</a></span>";
					//}else{
						html+=","+StationLineInfo[j].name
					//}
				}
			}
			$$(".lineName"+stationId).append($$("<div style='float:left;margin-left:18px;line-height:20px;color:#666666;margin-right:10px;word-wrap:break-word;overflow:hidden;'>"+html+"</div>"));
		}
	});
}
function showLinePopInfo(stationId,lineId,direct){
	stationPoup(null,stationId,lineId,direct);
}
function stationPoup(v,stationId,lineId,direct){
	if(v!=null){
		$$(".addLi_pressed").attr("class","addLi");
		v.className = "addLi_pressed";
	}
	if (currentPopup) {
		map.removePopup(currentPopup);
	}
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
						'</div><div class="map_bus_div2">';
			for(var j=0;j<StationLineInfo.length;j++){///
				if(StationLineInfo[j].id==lineId){
					html += '<a  href="javascript:void(0)" onclick=busStationPoup(this,"'+StationLineInfo[j].id+'","'+stationId+'",1) '+
						' class="map_bus_div2_select">'+StationLineInfo[j].name+ '</a>';
				}else{
					html += '<a  href="javascript:void(0)" onclick=busStationPoup(this,"'+StationLineInfo[j].id+'","'+stationId+'",1) '+
						' class="map_bus_div2_noselect">'+ StationLineInfo[j].name+ '</a>';
				}
			}
			html += '</div><div id="poupcontent"></div>';
			var upStartStationName=LineInfo.line.upStartStationName;
			var upEndStationName=LineInfo.line.upEndStationName;
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
					content += '<li><a href="javascript:void(0)"  onclick=details("'+lineId+'","'+upListInfo[i].stationId+'",1) class=BusSelectStation>'
					+ busStationName + '</a></li>';	
				}else{
					content += '<li><a href="javascript:void(0)"  onclick=details("'+lineId+'","'+upListInfo[i].stationId+'",1)>'
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
		}
		if(direct==2){
			var list = data;
			var StationInfo = list.busStationDetail.station;
			var StationLineInfo = list.busStationDetail.lineList;
			var LineInfo = list.busLineDetail;
			var downListInfo=LineInfo.downList;//线路上行信息
			
			var lon=StationInfo.longitude;
			var lat=StationInfo.latitude;
			
			var html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' + StationInfo.name + 
						'</label><label class="map_bus_link" onclick=details("'+lineId+'","'+stationId+'",2)>详情&raquo;</label>'+
						'</div><div class="map_bus_div2">';
			for(var j=0;j<StationLineInfo.length;j++){
				if(StationLineInfo[j].id==lineId){
					html += '<a  href="javascript:void(0)" onclick=busStationPoup(this,"'+StationLineInfo[j].id+'","'+stationId+'",2) '+
						' class="map_bus_div2_select">'+StationLineInfo[j].name+ '</a>';
				}else{
					html += '<a  href="javascript:void(0)" onclick=busStationPoup(this,"'+StationLineInfo[j].id+'","'+stationId+'",2) '+
						' class="map_bus_div2_noselect">'+ StationLineInfo[j].name+ '</a>';
				}
			}
			html += '</div><div id="poupcontent"></div>';
			var downStartStationName=LineInfo.line.downStartStationName;
			var downEndStationName=LineInfo.line.downEndStationName;
			var div3=$$('<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + LineInfo.line.name + '</label>'+
						'<div class="map_bus_div3_l1">'+downStartStationName+'<img src="WebPage/image/common/busStation_srart_end.png"/>'+
						downEndStationName+'</div><div class="map_bus_div3_l2"><img src="WebPage/image/common/bus_pass.png"/>途中'+
						'<img src="WebPage/image/common/refresh.png" onclick="QueryOriginBusArrive()" style="cursor: pointer;" />刷新</div></div>');
			var div4=$$('<div class="map_bus_div4"><label>首末班时间：'+LineInfo.line.lineUpStartTime+'-'+LineInfo.line.lineUpEndTime+'&nbsp;全程：'+
						LineInfo.line.price+'元</label></div>');
			var div5=$$('<div class="map_bus_div5"></div>');//实时到站
			var maxwindth=downListInfo.length*32;//ul 宽度
			var content = '<div class="map_bus_div6"><a class="map_bus_div6_left" style="visibility:hidden" onclick="BusStationListMove_left(this)">'+
							'<img src="WebPage/image/common/arrow_left_normal.png"/></a><div class="map_bus_div6_content"><ul style="width:'+maxwindth+'px">';
			for (var i = 0; i < downListInfo.length; i++) {
				var busStationName = downListInfo[i].stationName;
				content+='<li id="busicon'+i+'" class="busicon"></li>';
				if(downListInfo[i].stationId==stationId){
					content += '<li><a href="javascript:void(0)"  onclick=details("'+lineId+'","'+downListInfo[i].stationId+'",2) class=BusSelectStation >'
					+ busStationName + '</a></li>';	
				}else{
					content += '<li><a href="javascript:void(0)"  onclick=details("'+lineId+'","'+downListInfo[i].stationId+'",2)>'
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
	complete : function() {
		CloseLoadingDiv();
}
	
});							
	
}
function LeftPoup(num,stationId,lineId,lonlat,direct){
	if(num==0) map.setMapCenter(lonlat,16);
	var LonLat=map.createLonlat(lonlat);
	var icon = map.createIcon("WebPage/image/newmap_icon/1.png",26);
	imgMarker = map.createMarker(LonLat, icon);
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
								'</div><div class="map_bus_div2">';
					for(var j=0;j<StationLineInfo.length;j++){
						if(StationLineInfo[j].id==lineId){
							html += '<a  href="javascript:void(0)" onclick=busStationPoup(this,"'+StationLineInfo[j].id+'","'+stationId+'",1) '+
								' class="map_bus_div2_select">'+StationLineInfo[j].name+ '</a>';
						}else{
							html += '<a  href="javascript:void(0)" onclick=busStationPoup(this,"'+StationLineInfo[j].id+'","'+stationId+'",1) '+
								' class="map_bus_div2_noselect">'+ StationLineInfo[j].name+ '</a>';
						}
					}
					html += '</div><div id="poupcontent"></div>';
					var upStartStationName=LineInfo.line.upStartStationName;
					var upEndStationName=LineInfo.line.upEndStationName;
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
							content += '<li><a href="javascript:void(0)"  onclick=details("'+lineId+'","'+upListInfo[i].stationId+'",1) class=BusSelectStation >'
							+ busStationName + '</a></li>';	
						}else{
							content += '<li><a href="javascript:void(0)"  onclick=details("'+lineId+'","'+upListInfo[i].stationId+'",1)>'
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
				}
				if(direct==2){
					var list = data;
					var StationInfo = list.busStationDetail.station;
					var StationLineInfo = list.busStationDetail.lineList;
					var LineInfo = list.busLineDetail;
					var downListInfo=LineInfo.downList;//线路上行信息
		
					var lon=StationInfo.longitude;
					var lat=StationInfo.latitude;
					
					var html = '<div class="map_bus_div"><div class="map_bus_div1"><label class="map_bus_title">' + StationInfo.name + 
								'</label><label class="map_bus_link" onclick=details("'+lineId+'","'+stationId+'",2)>详情&raquo;</label>'+
								'</div><div class="map_bus_div2">';
					for(var j=0;j<StationLineInfo.length;j++){
						if(StationLineInfo[j].id==lineId){
							html += '<a  href="javascript:void(0)" onclick=busStationPoup(this,"'+StationLineInfo[j].id+'","'+stationId+'",2) '+
								' class="map_bus_div2_select">'+StationLineInfo[j].name+ '</a>';
						}else{
							html += '<a  href="javascript:void(0)" onclick=busStationPoup(this,"'+StationLineInfo[j].id+'","'+stationId+'",2) '+
								' class="map_bus_div2_noselect">'+ StationLineInfo[j].name+ '</a>';
						}
					}
					html += '</div><div id="poupcontent"></div>';
					var downStartStationName=LineInfo.line.downStartStationName;
					var downEndStationName=LineInfo.line.downEndStationName;
					var div3=$$('<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + LineInfo.line.name + '</label>'+
								'<div class="map_bus_div3_l1">'+downStartStationName+'<img src="WebPage/image/common/busStation_srart_end.png"/>'+
								downEndStationName+'</div><div class="map_bus_div3_l2"><img src="WebPage/image/common/bus_pass.png"/>途中'+
								'<img src="WebPage/image/common/refresh.png" onclick="QueryOriginBusArrive()" style="cursor: pointer;" />刷新</div></div>');
					var div4=$$('<div class="map_bus_div4"><label>首末班时间：'+LineInfo.line.lineUpStartTime+'-'+LineInfo.line.lineUpEndTime+'&nbsp;全程：'+
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

function busStationPoup(v,lineId,stationId,direct) {
	$$(".map_bus_div2_select").attr("class","map_bus_div2_noselect");
	v.className = "map_bus_div2_select";
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
			if(direct==1){
				var list = data;
				var StationInfo = list.busStationDetail.station;
				var StationLineInfo = list.busStationDetail.lineList;
				var LineInfo = list.busLineDetail;
				var upListInfo=LineInfo.upList;//线路上行信息
				
				var div3=$$('<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + LineInfo.line.name + '</label>'+
							'<div class="map_bus_div3_l1">'+LineInfo.line.upEndStationName+'<img src="WebPage/image/common/busStation_srart_end.png"/>'+
							LineInfo.line.upStartStationName+'</div><div class="map_bus_div3_l2"><img src="WebPage/image/common/bus_pass.png"/>途中'+
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
					
				}
			
				if(maxwindth>500){
					content += '</ul></div><a class="map_bus_div6_right" onclick="BusStationListMove_Right(this)"><img src="WebPage/image/common/arrow_right_normal.png"/></a></div>';
				}else{
					content += '</ul></div></div>';
				}
				
				$$('#poupcontent').empty().append(div3);
				$$('#poupcontent').append(div4);
				$$('#poupcontent').append(div5);
				$$('#poupcontent').append(content);
				Gloval_select_lineid=lineId;
				Gloval_select_stationid=upListInfo[upListInfo.length-1].stationId;
				Gloval_select_direct=direct;
				Gloval_select_stationlength=upListInfo.length;
				QueryOriginBusArrive();
			}
			if(direct==2){
				var list = data;
				var StationInfo = list.busStationDetail.station;
				var StationLineInfo = list.busStationDetail.lineList;
				var LineInfo = list.busLineDetail;
				var downListInfo=LineInfo.downList;//线路上行信息
				
				var div3=$$('<div class="map_bus_div3"><label class="poupcontent_busRouteName">' + LineInfo.line.name + '</label>'+
							'<div class="map_bus_div3_l1">'+LineInfo.line.downEndStationName+'<img src="WebPage/image/common/busStation_srart_end.png"/>'+
							LineInfo.line.downStartStationName+'</div><div class="map_bus_div3_l2"><img src="WebPage/image/common/bus_pass.png"/>途中'+
							'<img src="WebPage/image/common/refresh.png" onclick="QueryOriginBusArrive()" style="cursor: pointer;" />刷新</div></div>');
				var div4=$$('<div class="map_bus_div4"><label>首末班时间：'+LineInfo.line.lineUpStartTime+'-'+LineInfo.line.lineUpEndTime+'&nbsp;全程：'+
							LineInfo.line.price+'元</label></div>');
				var div5=$$('<div class="map_bus_div5"></div>');//实时到站
				var maxwindth=downListInfo.length*32;//ul 宽度
				var content = '<div class="map_bus_div6"><a class="map_bus_div6_left" style="visibility:hidden" onclick="BusStationListMove_left(this)">'+
								'<img src="WebPage/image/common/arrow_left_normal.png"/></a><div class="map_bus_div6_content"><ul style="width:'+maxwindth+'px">';
				for (var i = 0; i < downListInfo.length; i++) {
					var busStationName = downListInfo[i].stationName;
					content+='<li id="busicon'+i+'" class="busicon"></li>';
					if(downListInfo[i].stationId==stationId){
					content += '<li><a href="javascript:void(0)" onclick=details("'+lineId+'","'+downListInfo[i].stationId+'",2)  class=BusSelectStation >'
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
				
				$$('#poupcontent').empty().append(div3);
				$$('#poupcontent').append(div4);
				$$('#poupcontent').append(div5);
				$$('#poupcontent').append(content);
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
function TableIsNull(){
	$$(".layout1_left_datalist").append($$("<div class='layout1_left_datalist_tishi'>没有符合条件的结果</div>"));
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
//详情页面
function details(routeId,busStationId,direct){
	window.open($$("#basePath").val()+"WebPage/page/publicTraffic/wisdom/bus_infor.jsp?routeId="+routeId+"&busStationId="+busStationId+"&direct="+direct);
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
function LeftCssBySelf(num){
	$$(".addLi_pressed").attr("class","addLi");
	var nm=document.getElementById("addLi_"+num);
	nm.className = "addLi_pressed";
}
