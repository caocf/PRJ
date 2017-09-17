var map;
var oldMarker_self;
var oldLonlat_self;
$$(document).ready(function() {
	map = new XMap("simpleMap",1);
	map.addPanZoomBar();
	GetParking("queryparkingplacecoordinate", 1);

});
function GetParking(actionName, selectedPage) {
	$$(".addTr").empty();
	$$(".addTr").remove();
	$$(".layout1_left_datalist_tishi").remove();
	$$(".addTr_pressed").remove();
	map.delWindow();   
	$$.ajax( {
		url : actionName,
		type : 'post',
		dataType : 'json',
		data : {
			'parking.parkType' : encodeURI($$("#parkType").val()),
			'parking.regionCode' : encodeURI($$("#regionCode").val()),
			'parking.chargeWay' : encodeURI($$("#chargeWay").val()),
			'parking.pageSize':100,
			'parking.pno' : selectedPage,
			'parking.coId' :'3'
		},
		beforeSend : function() {
			ShowLoadingDiv();
		},
		success : function(data) {
			if (data.recodelist[0].count == 0) {
				TableIsNull();
			} else {
				var list = data.recodelist;
				appendToTable(list,list.length);// 跳转页码的方法名称
				gotoPageMethodName = "gotoPageNo";
				var number=CountItemTrByPageNum(list[0].count,100);
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
function TableIsNull(){
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
var datalist;
function appendToTable(list, listLength){
	datalist=list;
	for ( var i = 1; i < listLength; i++) {
		var lonlat=list[i].lon+","+list[i].lat;
		var content="<div id='winInfo'>"
			+"<div id='winTitle' ><label class='addTr_name2'>"+list[i].name+"</label></div>"
			+"<div id='winContent'>"
				+"<table>"
					+"<tr>"
					+"<td>总车位：</td>"
					+"<td>"+list[i].totalCount+"</td>"
					+"<td>空车位：</td>"
					+"<td>"+list[i].freeCount+"</td>"
					+"</tr>"
					+"<tr><td colspan='4'>地址："+list[i].address+"</td>"
					+"</tr>"
				+"</table>"
			+"</div>"	
		+"</div>";
		
		newTr = $$("<div class='addTr' id='addTr"+i+"' onclick='SelectPoint(this,"+i+")'></div>");
		newTr.append($$("<div class='addTr_img'><img src='WebSit/image/graphical/map_point_normal.png'/><label>"+i+"</label></div>"));
		newTr.append($$("<div class='addTr_right'><label class='addTr_name'>"+list[i].name+"</label>" +
				"<label class='addTr_freeCount'>空车位:</label><label class='addTr_item'>"+list[i].freeCount+"</label>" +
				"<label class='addTr_totalCount'>总车位:</label><label  class='addTr_item'>"+list[i].totalCount+"</label>" +
						"<br/><label class='addTr_address'>地点:</label><label class='addTr_item_address'>"+list[i].address+"</label></div>"));
		$$(".layout1_left_datalist").append(newTr);
		
		var image="WebSit/image/graphical/map_point_normal.png";
		if(i==1) setCenter(lonlat,16);
		//map.createPopupForMarker(lonlat,content,image,36,200,100);
		createPopupForMarker_self(lonlat,content,image,36,200,100,i);
	}
}
function closeMarkPopup_self(){
	//map.removePopup(currentPopup);  
}
function SelectPoint(thisop,num){
	/*if(oldMarker_self!=undefined){
		markers.removeMarker(oldMarker_self);
		 map.addMarker(oldLonlat_self,"WebSit/image/graphical/map_point_normal.png",36);
	}*/
	//$$(".addTr_pressed .addTr_img img").attr("src","WebSit/image/graphical/map_point_normal.png");
	$$(".addTr_pressed").attr("class","addTr");
	thisop.className = "addTr_pressed";
	//$$(".addTr_pressed .addTr_img img").attr("src","WebSit/image/graphical/map_point_pressed.png");
	var lonlat=datalist[num].lon+","+datalist[num].lat;
	 setCenter(lonlat,16);
	var content="<div id='winInfo'>"
		+"<div id='winTitle'>"+datalist[num].name+"</div>"
		+"<div id='winContent'>"
			+"<table>"
				+"<tr>"
				+"<td>总车位：</td>"
				+"<td>"+datalist[num].totalCount+"</td>"
				+"<td>空车位：</td>"
				+"<td>"+datalist[num].freeCount+"</td>"
				+"</tr>"
				+"<tr><td colspan='4'>地址："+datalist[num].address+"</td>"
				+"</tr>"
			+"</table>"
		+"</div>"	
	+"</div>";
	/*oldLonlat_self=lonlat;
	oldMarker_self=addMarker_self(lonlat,"WebSit/image/graphical/map_point_pressed.png",36);*/
	//markers.addMarker(marker);
	map.createPopup(lonlat,200,100,content);
}

//给Marker创建浮云框2
function createPopupForMarker_self(lonlat,content,imageIcon,imageSize,width,height,num){
	var lonlat=map.createLonlat(lonlat);
	var size=map.createPopupSize(width, height);
	currentPopup=map.createPopupObject(null,lonlat,size,content,null,true);
	var feature=new Geo.Feature(markers,lonlat);
	feature.closeBox = true;
    feature.popupClass = AutoSizeFramedCloud;
    feature.data.popupContentHTML = content;
	feature.data.overflow = (null) ? "auto" : "hidden";
	feature.data.popupSize = size;
	var icon=map.createIcon(imageIcon, imageSize);
	var marker=map.createMarker(lonlat, icon);
	var markerClick=function(evt){
		$$(".addTr_pressed").attr("class","addTr");
		var nm=document.getElementById("addTr"+num);
		nm.className = "addTr_pressed";
		$$(".olPopup").css("display","none");
		if(feature.popup==null){
			feature.popup=feature.createPopup(true);
			map.addPopup(feature.popup);
			feature.popup.show();
		}else{
			feature.popup.toggle();
		}
		currentPopup=feature.popup;
		Geo.View2D.Event.stop(evt);
	};
	marker.events.register("mousedown",feature,markerClick);
	markers.addMarker(marker);
    map.addLayer(markers);
}
//添加图标
function addMarker_self(lonlat,imageIcon,size){
	if(typeof(imageIcon)=='undefined'){
		imageIcon="http://115.231.73.253/zhjtapi/images/marker/marker2.png";
		size=30;
	}
	if(typeof(size)=='undefined'){
		size=30;
	}
	var lonlat=new Geo.LonLat(lonlat.split(",")[0],lonlat.split(",")[1]);
	var icon=new Geo.View2D.Icon(imageIcon,new Geo.Size(size,size));
	var marker=new Geo.Marker(lonlat,icon);
	markers.addMarker(marker);
	map.addLayer(markers);
	return marker;
}
