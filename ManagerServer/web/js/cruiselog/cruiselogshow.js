$(document).ready(function() {
	showCruiseLogShow();
	showLocation();//显示地图
});

function showCruiseLogShow(){
	var ID=$("#ID").val()
	
	$.ajax({
		url:'showCruiseLogAndIllegal',
		type:"post",
		dataType :"json",
		data:{"cruiseLogID" : ID},
		success: function(data){

		showCruiseLogAndIllegal(data.cruiselogbean,data.illegallist);
	}
	});
}
 

function showCruiseLogAndIllegal(cruiselogbean,illegallist){
	var basePath=$("#basePath").val()
	newTr=$("<tr class='cruiseLogshow'></tr>");
	newTr.append("<td class='lefttd'>巡航区域：</td><td>" +cruiselogbean.cruiseLogLoaction+"</td>");
	$("#CruiseLogshow").append(newTr);
	newTr=$("<tr class='cruiseLogshow'></tr>");
	newTr.append("<td class='lefttd'>巡航人员：</td><td>" +cruiselogbean.cruiseLogUserName+"</td>");
	$("#CruiseLogshow").append(newTr);
	newTr=$("<tr class='cruiseLogshow'></tr>");
	newTr.append("<td class='lefttd'>船名号：</td><td>" +cruiselogbean.shipName+"</td>");
	$("#CruiseLogshow").append(newTr);
	newTr=$("<tr class='cruiseLogshow'></tr>");
	newTr.append("<td class='lefttd'>开始时间：</td><td>" +GetShortTime1(cruiselogbean.startTime)+"</td>");
	$("#CruiseLogshow").append(newTr);
	newTr=$("<tr class='cruiseLogshow'></tr>");
	newTr.append("<td class='lefttd'>结束时间：</td><td>" +GetShortTime1(cruiselogbean.endTime)+"</td>");
	$("#CruiseLogshow").append(newTr);
	
	var listLength=illegallist.length;
	
	if(listLength==0){
		$("#illegalList").hide();
	}else{
	  for(var i=0;i<listLength;i++){
	
		newTr=$("<tr class='illegal'></tr>");
		newTr.append("<td class='see1td' colspan=2>违章描述：" +illegallist[i].illegalContent+"</td>");
		$("#illegalListshow").append(newTr);
		newTr=$("<tr class='illegal'></tr>");
		newTr.append("<td class='see1td' >违章时间：" +GetShortTime1(illegallist[i].illegalTime)+"</td>");
		newTr.append("<td class='see1td' ><a class='operation' href='"+basePath+"page/cruiselog/illegal_see.jsp?illegalId="+illegallist[i].illegalId+"'>查看详情</a></td>");
		$("#illegalListshow").append(newTr);
		newTr=$("<tr class='illegal'></tr>");
		newTr.append("<td colspan=2><div class='list_kind'></div></td>");
		$("#illegalListshow").append(newTr);
		}	
	}
		
	
	
	
}






function fanhui(){
	var basePath=$("#basePath").val()
	 str=basePath+"page/cruiselog/cruiselog.jsp";
	  window.location.href=str;
	
	
}
function showLocation(){
var ID=$("#ID").val()
	
	$.ajax({
		url:'showMap',
		type:"post",
		dataType :"json",
		data:{"cruiseLogID" : ID},
		success: function(data){
        //alert("success");
        
		showMap(data.locationList);
	}
	});
}

function showMap(locationList){
	var listLength=locationList.length;
	var arrPois =new Array();
	var j=0;
	
	for(var i=0; i<listLength;i++){
		arrPois[j]=new BMap.Point(locationList[i].longitude/1e6,locationList[i].latitude/1e6);
		j++;
	}
	
	var map = new BMap.Map("allmap"); //地图所在网页中的位置
	var point = new BMap.Point(locationList[0].longitude/1e6,locationList[0].latitude/1e6); //中心坐标
	map.centerAndZoom(point, 20); 

	var polyline = new BMap.Polyline(arrPois, {strokeColor:"blue", strokeWeight:3, strokeOpacity:0.5}); //画线
	map.addOverlay(polyline);
	map.enableScrollWheelZoom(true); //地图缩放
	
	var marker = new BMap.Marker(point);  // 创建标注
	map.addOverlay(marker);              // 将标注添加到地图中
	marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画

	var label = new BMap.Label("起点",{offset:new BMap.Size(20,-10)}); //
	marker.setLabel(label);
	
	var marker1 = new BMap.Marker(new BMap.Point(locationList[listLength-1].longitude/1e6,locationList[listLength-1].latitude/1e6));  // 创建标注
	map.addOverlay(marker1);              // 将标注添加到地图中
	marker1.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画

	var label1 = new BMap.Label("终点",{offset:new BMap.Size(20,-10)});
	marker1.setLabel(label1);
}





