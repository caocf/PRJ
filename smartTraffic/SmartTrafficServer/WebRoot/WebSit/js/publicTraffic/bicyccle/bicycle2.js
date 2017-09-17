var map;
var bicyclePointName;
var datalist;
var bicycleRegionID;
var Goval_lonlat;
var Goval_bikeCount;
var Goval_range=0;
$$(document).ready(function() {
	map = new XMap("simpleMap", 1);
	map.addPanZoomBar();
	map.addMousePosition();
	Goval_lonlat=getCenter();
	contextMenu= map.createContextMenu();
	map.addControl(contextMenu);
	var menuItem1=new XMapMenuItem({
		text:"搜索周边自行车点",
		width:100,
		callback:function(){
		map.delWindow();
		Goval_range=2000;
		Goval_lonlat = $$(".mousePositionCtrl").text();
			if(Goval_lonlat.length!=0){
		SearchBicycleByLatLon("QueryOriginNearByBikeStation",1);}
		else{
			alert("坐标获取失败！");}
		}
	});
	contextMenu.addItem(menuItem1);
	contextMenu.addSeparator();
	contextMenu.activate();
});
//鼠标右击查找搜索周边自行车点
function SearchBicycleByLatLon(actionName, selectedPage){
	$$("#msg_2").hide();
	$$(".layout1_left_datalist2").show();
	$$(".layout1_left_datalist_title").css("visibility","visible");
	$$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data:{
			'longtitude':Goval_lonlat.split(",")[0],
			'lantitude':Goval_lonlat.split(",")[1],
			'distance':Goval_range,
			'count':BICYCLE_PAGESIZE
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
		if(data.length!=0){
			var list = JSON.parse(data);
			 list =list.StationList;
			$$("#searchReasultCount").text(list.length);
			appendToTable(list, list.length);
			/*gotoPageMethodName = "gotoPageNo1";
			var number=CountItemTrByPageNum(list.length,PARKING_PAGESIZE);
			printPage(number, selectedPage, 'SearchBicycleByLatLon',
					actionName, gotoPageMethodName);*/
	
		}else{
			$$("#searchReasultCount").text("0");
			TableIsNull();
			CloseLoadingDiv();
		}
		},
		complete : function() {
			//CloseLoadingDiv();
		}
	});


}
//按名称搜索
function SearchBicycle(textContent){
	map.delWindow();
	bicyclePointName=textContent;
	SearchBicycleByName('QueryOriginBikeStation',1);
}
function SearchBicycle2(){
	map.delWindow();
	bicyclePointName=SearchContentCheck2("searchContent");
	if(bicyclePointName=="error"){
		return;
	}else if(bicyclePointName==""){
		Goval_range=500000;
		SearchBicycleByLatLon("QueryOriginNearByBikeStation",1);
	}else{
		SearchBicycleByName('QueryOriginBikeStation',1);
	}
}
function SearchBicycleByName(actionName, selectedPage){
	$$("#msg_2").hide();
	$$(".layout1_left_datalist2").show();
	$$(".layout1_left_datalist_title").css("visibility","visible");
	$$.ajax( {
		url : actionName,
		type : "post",
		dataType : "json",
		data:{
		'stationName':bicyclePointName
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			
		if(data.length!=0){
			var list = JSON.parse(data);
			
			 list =list.StationList;
			$$("#searchReasultCount").text(list.length);
			appendToTable(list, list.length);
			/*gotoPageMethodName = "gotoPageNo";
			var number=CountItemTrByPageNum(list.length,PARKING_PAGESIZE);
			printPage(number, selectedPage, 'SearchBicycleByName',
					actionName, gotoPageMethodName);*/
	
		}else{
			$$("#searchReasultCount").text("0");
			TableIsNull();
			//$$("#pageDiv").hide();
		}
		},
		complete : function() {
			CloseLoadingDiv();
		}
	});


}
function TableIsNull(){
	$$(".layout1_left_datalist2").empty();
	$$(".layout1_left_datalist2").append($$("<div class='layout1_left_datalist_tishi'>没有符合条件的结果</div>"));
}
/*function gotoPageNo(actionName, totalPage) {
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
		SearchBicycleByName(actionName, pageNo);
	}
}*/
function appendToTable(list, listLength){
	$$(".layout1_left_datalist2").empty();
	datalist=list;
	var bikeList="";
	for ( var i = 0; i < listLength; i++) {
		bikeList+=list[i].Id+",";
	}
	SearchBicycleById(bikeList,list, listLength);
}
function appendToTable2(list, listLength){
	$$(".layout1_left_datalist2").empty();
	datalist=list;
	for ( var i = 0; i < listLength; i++) {
		var lonlat=list[i].Longitude+","+list[i].Latitude;
		var IsAllDay;
		if(list[i].IsAllDay=="false"){
			IsAllDay="否";
		}else{
			IsAllDay="是";
		}
		var rentamount=0;
		var backamount=0;
		if(Goval_bikeCount[i].Stationid==list[i].Id){
			rentamount=Goval_bikeCount[i].Count;
			backamount=Number(list[i].Count)-Number(rentamount);
		}else{
			for(var j=0;j<Goval_bikeCount.length;j++){
				if(Goval_bikeCount[j].Stationid==list[i].Id){
					rentamount=Goval_bikeCount[j].Count;
					backamount=Number(list[i].Count)-Number(rentamount);
					break;
				}
			}
		}
		var image="WebPage/image/newmap_icon/3.png";
		if(backamount>list[i].Count*0.3){
			image="WebPage/image/newmap_icon/2.png";
		}else if(backamount>list[i].Count*0.6){
			image="WebPage/image/newmap_icon/1.png";
		}
		newTr = $$("<div class='addTr' id='addTr"+i+"' onclick='SelectPoint(this,"+i+")'></div>");
		newTr.append($$("<div class='addTr_img'><img src='"+image+"' /></div>"));
		newTr.append($$("<div class='addTr_right'><label class='addTr_name'>"+list[i].Name+"</label>" +
						"<label class='addTr_freeCount'>总数:</label><label  class='addTr_item'>"+list[i].Count+"</label>" +
						"<label class='addTr_totalCount'>区域名:</label><label class='addTr_item'>"+list[i].AreaName+"</label>" +
						"<br/><label class='addTr_freeCount'>可还数:</label><label  class='addTr_item'>"+backamount+"</label>" +
						"<label class='addTr_totalCount'>服务电话:</label><label class='addTr_item'>"+list[i].StationPhone+"</label>" +
						"<br/><label class='addTr_address'>地点:</label><label class='addTr_item_address'>"+list[i].Address+"</label></div>"));
		$$(".layout1_left_datalist2").append(newTr);
		
		
		if(i==0) map.setMapCenter(lonlat,null);
		addPointPoup(lonlat,i,list[i].Id,image);
		//map.addPopupForFeature(lonlat,image,(i+1)+"",26,"white","Times New Roman",content,250,130);
	}
	CloseLoadingDiv();
}
//地图上的点
function addPointPoup(lonlat,num, bikeId,image) {
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
			url : 'QueryOriginBikeParkingCount',
			type : "post",
			dataType : "json",
			data:{
			'bikeList':bikeId
			},
			success : function(data) {
				 var IsAllDay;
					if(datalist[num].IsAllDay=="false"){
						IsAllDay="否";
					}else{
						IsAllDay="是";
					}
				var listarr = JSON.parse(data).List;
				var rentamount=listarr[0].Count;
				var backamount=Number(datalist[num].Count)-Number(rentamount);
				var content="<div id='winInfo'>"
					+"<div id='winTitle' ><label class='addTr_name2'>"+datalist[num].Name+"</label></div>"
					+"<div id='winContent'>"
						+"<table>"
							+"<tr><td>总数：</td><td>"+datalist[num].Count+"</td><td>区域名：</td><td>"+datalist[num].AreaName+"</td></tr>"
							+"<tr><td>可租数：</td><td>"+rentamount+"</td><td>可还数：</td><td>"+backamount+"</td></tr>"
							+"<tr><td>全天：</td><td>"+IsAllDay+"</td><td>服务电话：</td><td>"+datalist[num].StationPhone+"</td></tr>"
							+"<tr><td colspan='4'>地址："+datalist[num].Address+"</td></tr>"
						+"</table>"
					+"</div>"	
				+"</div>";
				map.createPopup(lonlat,270,130,content);
			}
		});
			

	});
}

function SelectPoint(thisop,num){
	$$(".addTr_pressed").attr("class","addTr");
	thisop.className = "addTr_pressed";
	var lonlat=datalist[num].Longitude+","+datalist[num].Latitude;
	map.setMapCenter(lonlat,null);
	 var IsAllDay;
		if(datalist[num].IsAllDay=="false"){
			IsAllDay="否";
		}else{
			IsAllDay="是";
		}
		
	$$.ajax( {
		url : 'QueryOriginBikeParkingCount',
		type : "post",
		dataType : "json",
		data:{
		'bikeList':datalist[num].Id
		},
		success : function(data) {
			var listarr = JSON.parse(data).List;
			var rentamount=listarr[0].Count;
			var backamount=Number(datalist[num].Count)-Number(rentamount);
			var content="<div id='winInfo'>"
				+"<div id='winTitle' ><label class='addTr_name2'>"+datalist[num].Name+"</label></div>"
				+"<div id='winContent'>"
					+"<table>"
						+"<tr><td>总数：</td><td>"+datalist[num].Count+"</td><td>区域名：</td><td>"+datalist[num].AreaName+"</td></tr>"
						+"<tr><td>可租数：</td><td>"+rentamount+"</td><td>可还数：</td><td>"+backamount+"</td></tr>"
						+"<tr><td>全天：</td><td>"+IsAllDay+"</td><td>服务电话：</td><td>"+datalist[num].StationPhone+"</td></tr>"
						+"<tr><td colspan='4'>地址："+datalist[num].Address+"</td></tr>"
					+"</table>"
				+"</div>"	
			+"</div>";
			map.createPopup(lonlat,270,130,content);
		}
	});
}
function LeftCssBySelf(num){
	$$(".addTr_pressed").attr("class","addTr");
	var nm=document.getElementById("addTr"+num);
	nm.className = "addTr_pressed";
}
/*function gotoPageNo1(actionName, totalPage) {
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
		SearchBicycleByLatLon(actionName, pageNo);
	}
}*/
//可租车数
function SearchBicycleById(bikeList,list, listLength){
	$$.ajax( {
		url : 'QueryOriginBikeParkingCount',
		type : "post",
		dataType : "json",
		data:{
		'bikeList':bikeList
		},
		success : function(data) {
			
		if(data.length!=0){
			var listarr = JSON.parse(data);
			Goval_bikeCount=listarr.List;
		}else{
			Goval_bikeCount=0;
		}
		appendToTable2(list, listLength);
		}
	});
}