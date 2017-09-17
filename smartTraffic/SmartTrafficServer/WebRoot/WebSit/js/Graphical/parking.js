var map;
$$(document).ready(function() {
	SelectOption("parkType",212);
	map = new XMap("simpleMap",1);
	map.addPanZoomBar();
	map.setMapCenter("120.737,30.765",9);
	InitializeTianMap();
	InitializeFontColor("searchContent");
	GetParking('searchParkingByNameAndTypes', 1);
});
/*document.onkeydown = function(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) { // enter 键
		if($$("#LoginWindow").css("display")!="block"){
			GetParking('searchParkingByNameAndTypes',1);
		}
		
	}
};*/
function GetParking(actionName, selectedPage) {
	$$(".addTr").empty();
	$$(".addTr").remove();
	$$(".layout1_left_datalist_tishi").remove();
	$$(".addTr_pressed").remove();
	map.delWindow(); 
	var searchContent=SearchContentCheck2("searchContent");
	if(searchContent=="error"){
		return;
	}
	$$.ajax( {
		url : actionName,
		type : 'post',
		dataType : 'json',
		data : {
			'parkTypes' : $$("#parkType .abc").val(),
			'parkName' :searchContent,
			'page':selectedPage,
			'num' : PARKING_PAGESIZE
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			if (data.message.status == 0) {
				TableIsNull();
				$$("#pageDiv").hide();
			} else {
				var list = data.result;
				if(list.length==0){
					TableIsNull();
				}else{
					appendToTable(list,list.length);
				}
				gotoPageMethodName = "gotoPageNo";
				var number=CountItemTrByPageNum(data.result.length,PARKING_PAGESIZE);
				printPage(number, selectedPage, 'GetParking',
						actionName, gotoPageMethodName);
				
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
function searchParking(actionName, selectedPage){
	$$(".addTr").empty();
	$$(".addTr").remove();
	$$(".layout1_left_datalist_tishi").remove();
	$$(".addTr_pressed").remove();
	map.delWindow(); 
	var searchContent=SearchContentCheck2("searchContent");
	if(searchContent=="error"){
		return;
	}
	$$.ajax( {
		url : actionName,
		type : 'post',
		dataType : 'json',
		data : {
			'parkTypes' : $$("#parkType .abc").val(),
			'parkName' :searchContent,
			'page':selectedPage,
			'num' : PARKING_PAGESIZE
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			if (data.message.status == 0) {
				TableIsNull();
				$$("#pageDiv").hide();
			} else {
				var list = data.result;
				if(list.length==0){
					TableIsNull();
				}else{
					appendToTable(list,list.length);
				}
				gotoPageMethodName = "gotoPageNo1";
				var number=CountItemTrByPageNum(data.result.length,PARKING_PAGESIZE);
				printPage(number, selectedPage, 'searchParking',
						actionName, gotoPageMethodName);
				
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
function searchParkingByType(actionName, selectedPage){
	$$(".addTr").empty();
	$$(".addTr").remove();
	$$(".layout1_left_datalist_tishi").remove();
	$$(".addTr_pressed").remove();
	map.delWindow(); 
	var aaa=""
	$$.ajax( {
		url : actionName,
		type : 'post',
		dataType : 'json',
		data : {
			'parkName':aaa,
			'parkTypes' : $$("#parkType .abc").val(),
			'page':selectedPage,
			'num' : PARKING_PAGESIZE
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			if (data.parkingCGList == null) {
				TableIsNull();
				$$("#pageDiv").hide();
			} else {
				var list = data.parkingCGList;
				if(list.length==0){
					TableIsNull();
				}else{
					appendToTable(list,list.length);
				}
				gotoPageMethodName = "gotoPageNo2";
				var number=CountItemTrByPageNum(data.total,PARKING_PAGESIZE);
				printPage(number, selectedPage, 'searchParkingByType',
						actionName, gotoPageMethodName);
				
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
/*//停车实时
function GetParkingReal(num,parkingID,lonlat) {
	$$.ajax( {
		url : 'queryParkingFromCGByID',
		type : 'post',
		dataType : 'json',
		data : {
			'fullInfo' : true,
			'parkingID':parkingID
		},
		success : function(data) {
			if(data.parkingCG!=null){
			var list = data.parkingCG;
			newTr = $$("<div class='addTr' id='addTr"+num+"' onclick='SelectPoint(this,"+num+")'></div>");
			newTr.append($$("<div class='addTr_img'><img src='WebPage/image/newmap_icon/1.png'/></div>"));
			newTr.append($$("<div class='addTr_right'><label class='addTr_name'>"+list.pointname+"</label>" +
					"<label class='addTr_freeCount'>空车位:</label><label class='addTr_item'>"+list.freecount+"</label>" +
					"<label class='addTr_totalCount'>总车位:</label><label  class='addTr_item'>"+list.parklotcount+"</label>" +
							"<br/><label class='addTr_address'>地点:</label><label class='addTr_item_address'>"+list.roadname+"</label></div>"));
			$$(".layout1_left_datalist").append(newTr);
			
			var image="WebPage/image/graphical/map_point_normal.png";
			if(num==0) setCenter(lonlat,null);
			CreatPoirntOnclock(lonlat,num,list.parkpointid);
			}
		},
		error : function(a, b, c) {
			alert("出现错误，请刷新");
		}
	
	});							
}*/
function TableIsNull(){
	$$(".layout1_left_datalist_title").css("visibility","hidden");
	$$(".layout1_left_datalist").append($$("<div class='layout1_left_datalist_tishi'>没有符合条件的结果</div>"));
}
function gotoPageNo(actionName, totalPage) {
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
		GetParking(actionName, pageNo);
	}
}
function gotoPageNo1(actionName, totalPage) {//alert(actionName)
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
		searchParking(actionName, pageNo);
	}
}
function gotoPageNo2(actionName, totalPage) {
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
		searchParkingByType(actionName, pageNo);
	}
}
var datalist;
function appendToTable(list, listLength){
	$$(".layout1_left_datalist_title").css("visibility","visible");
	$$("#searchReasultCount").text(listLength);
	datalist=list;

	for ( var i = 0; i < listLength; i++) {
		var lonlat=list[i][0].gpslo+","+list[i][0].gpsla;
		//var lonlat="120.737"+","+"30.765";
		var image="";
		if(list[i][1].freecount==0){
			image="WebPage/image/newmap_icon/1.png";
		}else if(list[i][1].freecount<=list[i][0].parklotcount*0.2){
			image="WebPage/image/newmap_icon/2.png";
		}else if(list[i][1].freecount>list[i][0].parklotcount*0.2){
			image="WebPage/image/newmap_icon/3.png";
		}
		newTr = $$("<div class='addTr' id='addTr"+i+"' onclick='SelectPoint(this,"+i+")'></div>");
		newTr.append($$("<div class='addTr_img'><img src='"+image+"'/></div>"));
		newTr.append($$("<div class='addTr_right'><label class='addTr_name'>"+list[i][0].pointname+"</label>" +
				"<label class='addTr_freeCount'>空车位:</label><label class='addTr_item'>"+list[i][1].freecount+"</label>" +
				"<label class='addTr_totalCount'>总车位:</label><label  class='addTr_item'>"+list[i][0].parklotcount+"</label>" +
						"<br/><label class='addTr_address'>地点:</label><label class='addTr_item_address'>"+list[i][0].roadname+"</label></div>"));
		$$(".layout1_left_datalist").append(newTr);
		
		if(i==0) setCenter(lonlat,null);
		CreatPoirntOnclock(lonlat,i,list[i][1].parkpointid,image);
		//GetParkingReal(i,list[i].parkpointid,lonlat);
	}
}
function CreatPoirntOnclock(lonlat,num,parkingID,image){
	var LonLat = map.createLonlat(lonlat);
	var icon = map.createIcon(image,26);
	imgMarker = map.createMarker(LonLat, icon);
	map.addMarkerObject(imgMarker);
	imgMarker.events.register("click",null,function(){
		// 清除浮云窗口
		if (currentPopup) {
			map.removePopup(currentPopup);
		}
		$$.ajax( {
			url : 'searchParkingByID',
			type : 'post',
			dataType : 'json',
			data : {
				'isReal' : 1,
				'parkID':parkingID
			},
			success : function(data) {
				if(data.result[0]!=null){
				var list = data.result[0];
				var html="<div id='winInfo'>"
					+"<div id='winTitle' ><label class='addTr_name2'>"+list[0].pointname+"</label></div>"
					+"<div id='winContent'>"
						+"<table>"
							+"<tr>"
							+"<td>总车位：</td>"
							+"<td>"+list[0].parklotcount+"</td>"
							+"<td>空车位：</td>"
							+"<td>"+list[1].freecount+"</td>"
							+"</tr>"
							+"<tr><td colspan='4'>地址："+list[0].roadname+"</td>"
							+"</tr>"
						+"</table>"
					+"</div>"	
				+"</div>";
				map.createPopup(lonlat,200,100,html);
				}
			},
			error : function(a, b, c) {
				alert("出现错误，请刷新");
			}
		
		});							

	});
}
function SelectPoint(thisop,num){
	$$(".addTr_pressed").attr("class","addTr");
	thisop.className = "addTr_pressed";
	var lonlat=datalist[num][0].gpslo+","+datalist[num][0].gpsla;

	//var lonlat="120.737"+","+"30.765";
	 setCenter(lonlat,16);
	var content="<div id='winInfo'>"
		+"<div id='winTitle'>"+datalist[num][0].pointname+"</div>"
		+"<div id='winContent'>"
			+"<table>"
				+"<tr>"
				+"<td>总车位：</td>"
				+"<td>"+datalist[num][0].parklotcount+"</td>"
				+"<td>空车位：</td>"
				+"<td>"+datalist[num][0].freecount+"</td>"
				+"</tr>"
				+"<tr><td colspan='4'>地址："+datalist[num][0].roadname+"</td>"
				+"</tr>"
			+"</table>"
		+"</div>"	
	+"</div>";
	map.createPopup(lonlat,200,100,content);
}
function LeftCssBySelf(num){
	$$(".addTr_pressed").attr("class","addTr");
	var nm=document.getElementById("addTr"+num);
	nm.className = "addTr_pressed";
}


