$(document).ready(function() {
	showCruiseLogShow();
	//showLocation();//显示地图
});

function showCruiseLogShow(){
	var ID=$("#ID").val()
	
	$.ajax({
		url:'viewWharfWork',
		type:"post",
		dataType :"json",
		data:{"id" : ID},
		success: function(data){
		showWharfwork(data.wharfbean);
	}
	});
}
 

function showWharfwork(wharfbean){
	var basePath=$("#basePath").val()
	newTr=$("<tr class='wharfworkshow'></tr>");
	newTr.append("<td class='lefttd'>码头编号：</td><td>" +wharfbean.wharfID+"</td>");
	$("#Wharfworkshow").append(newTr);
	
	newTr=$("<tr class='wharfworkshow'></tr>");
	newTr.append("<td class='lefttd'>码头名称：</td><td>" +wharfbean.wharfName+"</td>");
	$("#Wharfworkshow").append(newTr);
	
	newTr=$("<tr class='wharfworkshow'></tr>");
	newTr.append("<td class='lefttd'>码头定量：</td><td>" +wharfbean.wharfWorkNorm+"</td>");
	$("#Wharfworkshow").append(newTr);
	
	
	newTr=$("<tr class='wharfworkshow'></tr>");
	newTr.append("<td class='lefttd'>负责人：</td><td>" +wharfbean.name+"</td>");
	$("#Wharfworkshow").append(newTr);

	newTr=$("<tr class='wharfworkshow'></tr>");
	newTr.append("<td class='lefttd'>船舶名称：</td><td>" +wharfbean.shipName+"</td>");
	$("#Wharfworkshow").append(newTr);
	
	newTr=$("<tr class='wharfworkshow'></tr>");
	newTr.append("<td class='lefttd'>起运港：</td><td>" +wharfbean.startingPortName+"</td>");
	$("#Wharfworkshow").append(newTr);
	
	newTr=$("<tr class='wharfworkshow'></tr>");
	newTr.append("<td class='lefttd'>目的港：</td><td>" +wharfbean.arrivalPortName+"</td>");
	$("#Wharfworkshow").append(newTr);
	
	
	newTr=$("<tr class='wharfworkshow'></tr>");
	if(wharfbean.portMart==1){
	newTr.append("<td class='lefttd'>进出港标志：</td><td>进港</td>");
	}else{
		newTr.append("<td class='lefttd'>进出港标志：</td><td>出港</td>");
	}
	$("#Wharfworkshow").append(newTr);
	
	newTr=$("<tr class='wharfworkshow'></tr>");
	newTr.append("<td class='lefttd'>货物名称：</td><td>" +wharfbean.cargoTypes+"</td>");
	$("#Wharfworkshow").append(newTr);
	
	newTr=$("<tr class='wharfworkshow'></tr>");
	newTr.append("<td class='lefttd'>载重：</td><td>" +wharfbean.carrying+wharfbean.uniti+"</td>");
	$("#Wharfworkshow").append(newTr);
	
	newTr=$("<tr class='wharfworkshow'></tr>");
	newTr.append("<td class='lefttd'>作业时间：</td><td>" +GetShortTime1(wharfbean.workTime)+"</td>");
	$("#Wharfworkshow").append(newTr);
	
	newTr=$("<tr class='wharfworkshow'></tr>");
	newTr.append("<td class='lefttd'>作业位置：</td><td>" +wharfbean.locationName+"</td>");
	$("#Wharfworkshow").append(newTr);
	
	newTr=$("<tr class='wharfworkshow'></tr>");
	newTr.append("<td class='lefttd'>申报时间：</td><td>" +GetShortTime1(wharfbean.declareTime)+"</td>");
	$("#Wharfworkshow").append(newTr);
	
	
	//附件
	var name=wharfbean.workPhoto;
	if(name==""||name==null){
	$("#WharfworkPhoto").css("display","none");
	}else{
	var type=name.substring(name.lastIndexOf('.')+ 1);
	
	var divhtml="";
	if(type=="jpg"){
		 divhtml+="<div class='fileitem_div'><a href='downloadFile?fileName="+wharfbean.workPhoto+"'>" +
				"<img src ='File/"+wharfbean.workPhoto+"'></a>" +
						"<br /><a class='txt'>"+wharfbean.workPhoto+"</a></div>";
	
	}
	$("#Photodiv1").html(divhtml);
	}
	
	
	
	//alert(wharfbean.longitude+"  "+wharfbean.latitude);
	//地图	
			//	$("#locationName").text(data.list[0].locationName);
				var map = new BMap.Map("allmap");
				var point = new BMap.Point(wharfbean.longitude,wharfbean.latitude);
				map.centerAndZoom(point, 20);
				var marker = new BMap.Marker(point);  // 创建标注
				map.addOverlay(marker);              // 将标注添加到地图中
				marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
				var label = new BMap.Label("作业地点",{offset:new BMap.Size(20,-10)});
				marker.setLabel(label);		
				map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT, type: BMAP_NAVIGATION_CONTROL_ZOOM}));  //右下角，仅包含缩放按钮
				
			
	



}






function fanhui(){
	var basePath=$("#basePath").val()
	 str=basePath+"page/wharfwork/wharfworkList.jsp";
	  window.location.href=str;
	
	
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





