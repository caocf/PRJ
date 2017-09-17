var markerS, markerE;
	var map, routeQuery, linkQuery, langQuery;
	var id_count = 0;
	var bypass_array = [], avoidarea_array = [], avoidlink_array = [];
	var GET_ROUTE_INS = {};
	var langData;
	var passarr = new Array();
	var oldpassarr = new Array();//途径点中文
	var markerStart="";
	var markerEnd="";
	//修改路况地址
	TRAFFIC_PIV_URL = "http://115.231.73.253:8098/ROOT1/",
$$(function(){
	setHeight();
	map=new XMap("simpleMap",2);
	map.addPanZoomBar();
	//添加卫星地图
	map.addNavType();
	//添加右键功能
	//addRight();
	//创建语言控制
	var langQuery=map.createLanguage();
	//执行语言搜索
	langQuery.executeSearch(1);
});
function setHeight(){
	var h=document.documentElement.clientHeight-225;
	if(h<502){
		document.getElementById("main_content1").style.height=502+"px";
	}else{
		document.getElementById("main_content1").style.height=h+"px";
	}
}
window.onresize=function(){
	var h=document.documentElement.clientHeight-225;
	if(h<502){
		document.getElementById("main_content1").style.height=502+"px";
	}else{
		document.getElementById("main_content1").style.height=h+"px";
	}
}
/**
*创建右键菜单
*/
function addRight(){
	var rightMenu = new WebtAPI.WControl.WContextMenu({id:"rightMenu"});
	map.addContextMenu(rightMenu);
	
	var item1 = new WebtAPI.WMenuItem({
		id: "fromHere",
		showPic: true,
		picUrl: "qidian2.png",
		width: 100,
		text: "从这里出发",
		callback: function(){
			var lonlat = rightMenu.getCurrentLonlat();
			rightRoutePoint("start",lonlat);
		}
	});
	
	var item2 = new WebtAPI.WMenuItem({
		id: "toHere",
		width: 100,
		text: "到这里去",
		callback: function(){
			var lonlat = rightMenu.getCurrentLonlat();
			//geoSearch(lonlat, "endPoint");
			rightRoutePoint("end",lonlat);
		}
	});
	
	var item3 = new WebtAPI.WMenuItem({
		id: "avoid",
		width: 100,
		text: "途经点",
		callback: function(){
			var lonlat = rightMenu.getCurrentLonlat();
			rightRoutePoint("pass",lonlat);
		}
	});
	rightMenu.addMenuItem(item1);
	rightMenu.addMenuItem(item2);
	rightMenu.addMenuItem(item3);
	rightMenu.activate();
}
	
/**地理编码查询**/
function geoSearch(lonlat, args) {
	var lonlat=map.createLonlat2Second128(lonlat);
	$$("#"+args+"").val(lonlat);
}

function executeSearch(thisop,type){
	//ShowNameList("searchpoibykey",1,"startPoint",1);
	if(thisop!=null){
	$$(".main_left_select").attr("class","main_left_select_no");
	thisop.className="main_left_select";
	}
	if(type!=0){
		ordernum=type;
	}
	if(!ChangePoint()){
		return;
	}
	map.clearLines();
	
	//EmptyDate();
	var start=$$("#startPointLonLat").val();
	var end=$$("#endPointLonLat").val();
	var pass="";
	$$(".passPointLonlat").each(function() {
		var passPoint = $$(this).val();
		if(pass==""){
			pass=passPoint;
		}else{
			pass += "," + passPoint;
		}
	});
	//pass=pass.substr(1,pass.length);
	$$(".main_left_middle").empty();
	map.clearLines();
	//判断是否有途经点
	//创建routeSearch
	var point = "";
	//起点
	point += start;
	if(pass!=""){
		point += "," + pass;
	}
	//终点
	point += "," + end;
	var avoid = "";
	//规避点
	$$(".areaValue").each(function() {
		var avoidArea = $$(this).val();
		if (avoidArea != null) {
			avoid += avoidArea + ",";
		}
	});
	var length = avoid.length - 1;
	var str = avoid.substring(0, length);
	//自驾方式
	//var costModel = $$("#costModel").val();
	//创建驾车查询
	routeQuery = map.createRouteQuery(point,type,str);
	routeQuery.setArgs({
		type : "route",
		callback : "routeSearchCallback"
	});
	routeQuery.executeSearch();
}
/**驾车规划回调函数**/
function routeSearchCallback(data, args) {//alert(JSON.stringify(data));
	var html;
	if(data["routeInfo"].length > 0){
		html = "<div class='route_item'>" +
		"	<div class='route_distance'>" +
		"		<span><b class='color_3'>全程</b>&nbsp;约" + data["routeInfo"][0]["distsum"]["dist"] + data["routeInfo"][0]["distsum"]["unit"] + "</span>" +
		"	</div>" +
		"	<ul class='route_list2'>";
		var distsum = data["routeInfo"][0]["distsum"]["dist"]
				+ data["routeInfo"][0]["distsum"]["unit"];
		var segmt = data["routeInfo"][0]["segmt"].length;
		for ( var i = 0; i < segmt; i++) {
			var coor = data["routeInfo"][0]["segmt"][i].clist.geometry.coordinates;
			map.drawLine(coor+"","blue","solid");
		}
		var dataList = data["routeInfo"][0]["segmt"];
		for ( var i = 0, len = dataList.length; i < len; i++) {
			var segmt = dataList[i];
			html += "<li>"
					+ "	<table cellpading='0' cellspacing='0' border='0' width='100%'>"
					+ "		<tr>" + "			<th>"
					+ (i + 1)
					+ "</th>"
					+ "			<td>"
					+ (i == 0 ? "从" : "沿")
					+ "<b style='color:#3A90FF;'>"
					+ ((i == 0) ? "起点"
							: (segmt["roadName"] ? (segmt["roadName"]["1"] ? segmt["roadName"]["1"]
									: (segmt["roadName"]["2"] ? segmt["roadName"]["2"]
											: ""))
									: "")
									+ "</b>")
					+ "向<b style='color:#F27489;'>"
					+ langData["dir"][segmt["dir"]]
					+ "</b>"
					+ "行驶<b style='font-weight:bold;'>"
					+ segmt["dist"]["dist"]
					+ "</b>"
					+ segmt["dist"]["unit"]
					+ "，"
					+ "<b>"
					+ langData["action"][segmt["action"]]
					+ "</b>"
					+ ((i + 1) == len ? ""
							: "进入<b style='color:#3A90FF;'>"
									+ (dataList[i + 1]["roadName"] ? (dataList[i + 1]["roadName"]["1"] ? dataList[i + 1]["roadName"]["1"]
											: (dataList[i + 1]["roadName"]["2"] ? dataList[i + 1]["roadName"]["2"]
													: ""))
											: "") + "</b>")
					+ ((segmt["addinfo"] != 0 && segmt["addinfo"] != undefined) ? ","
							+ langData["addinfo"][segmt["addinfo"]]
							: "")
					+ ((segmt["passinfo"] != 0 && segmt["passinfo"] != undefined) ? ",然后"
							+ langData["passinfo"][segmt["passinfo"]]
							: "")
					+ "			</td>"
					+ "		</tr>"
					+ "	</table>"
					+ "</li>";
		}
		$$(".main_left_middle").append(html);
	}
}
var num=0;
function switchPoint(type) {
	var pointImg = document.getElementById("routeSearchPointImg");
	if (!map || !type)
		return;
		//当传入点为起终点时，要清空页面原有起终点，保持唯一起终点
		var markers = wmap.markersLayer.markers;
		if (type.indexOf("start") != -1) {
			num++;
			if (markerS != null) {
				wmap.markersLayer.removeMarker(markerS);
			}
			//$$("#startPoint").val("地图上的点");
			pointImg.value = "WebPage/image/driving/" + type + ".png";
		}
		if (type.indexOf("end") != -1) {
			num++;
			if (markerE != null) {
				wmap.markersLayer.removeMarker(markerE);
			}
			//$$("#endPoint").val("地图上的点");
			pointImg.value = "WebPage/image/driving/" + type + ".png";
		}
		if (type.indexOf("pass") != -1) {
			num++;
			pointImg.value = "WebPage/image/driving/" + type + ".png";
		}
	//为地图注册标注事件
	if(num==1){
		wmap.registerEvents("click", markerRoutePoint);
	}
	
}
//地图标注时间------鼠标右键
function rightRoutePoint(type,lonlat){
	var pointImg = document.getElementById("routeSearchPointImg");
	if (!map || !type)
	return;
	//当传入点为起终点时，要清空页面原有起终点，保持唯一起终点
	var markers = wmap.markersLayer.markers;
	if (type.indexOf("start") != -1) {
		if (markerS != null) {
			wmap.markersLayer.removeMarker(markerS);
		}
		$$("#startPoint").val("地图上的点");
		pointImg.value = "WebPage/image/driving/" + type + ".png";
	}
	if (type.indexOf("end") != -1) {
		if (markerE != null) {
			wmap.markersLayer.removeMarker(markerE);
		}
		$$("#endPoint").val("地图上的点");
		pointImg.value = "WebPage/image/driving/" + type + ".png";
	}
	if (type.indexOf("pass") != -1) {
		pointImg.value = "WebPage/image/driving/" + type + ".png";
	}
		
	var type = document.getElementById("routeSearchPointImg").value;
	//创建图标对象
	var icon=map.createIcon(type,30);
	if (type.indexOf("start") != -1) {
		$$("[name=start]").val(lonlat.lon+","+lonlat.lat);
		$$("#startPointLonLat").val("地图上的点");
		//创建标注
		markerS=map.createMarker(lonlat,icon);
		//在map中添加标注
		map.addMarkerObject(markerS);
		//转为128秒
		var lonlatstart=map.createLonlat2Second128(lonlat);
		$$("#staPoint").val(lonlatstart);
	} else if (type.indexOf("end") != -1) {
		$$("[name=end]").val(lonlat.lon+","+lonlat.lat);
		$$("#endPointLonLat").val("地图上的点");
		markerE=map.createMarker(lonlat,icon);
		map.addMarkerObject(markerE);
		var lonlatend=map.createLonlat2Second128(lonlat);
		$$("#endPoint").val(lonlatend);
	} else if (type.indexOf("pass") != -1) {
		var lonlatpass=map.createLonlat2Second128(lonlat);
		$$("#byPassInput")
				.append(
						"<div class='bypass_item'>"
								+ "<input type='text' id='passPoint"+passarnum+"' class='passPoint' value='地图上的点' readonly='readonly'>"
								+ "<input type='hidden' id='passPointLonlat"+passarnum+"' class='passPointLonlat' value='"+ lonlatpass + "'>"
							    + "<span class='item_delete' onclick='deleteByPass(this.parentNode)'></span>"
							    + "</div>");
		var marker=map.createMarker(lonlat,icon);
		map.addMarkerObject(marker);
		//将途经点存入数组
		passarr.push(marker);
		var passarnum=oldpassarr.length;
		oldpassarr.push("");
		//添加一个标记管理器
		//map.passMarkerLayer.addMarker(marker);
	}
}
//地图标注事件------鼠标点击
function markerRoutePoint(e) {
	num=0;
	var position = this.events.getMousePosition(e);
	var lonlat=map.getWGS84(position);
	var type = document.getElementById("routeSearchPointImg").value;
	//创建图标对象
	var icon=map.createIcon(type,30);
	if (type.indexOf("start") != -1) {
		$$("[name=start]").val(lonlat.lon+","+lonlat.lat);
		//创建标注
		markerS=map.createMarker(lonlat,icon);
		//在map中添加标注
		map.addMarkerObject(markerS);
		//转为128秒
		var lonlatstart=map.createLonlat2Second128(lonlat);
		$$("#staPoint").val(lonlatstart);
		$$("#startPoint").val("地图上的点");
		$$("#startPointLonLat").val(lonlatstart);
	} else if (type.indexOf("end") != -1) {
		$$("[name=end]").val(lonlat.lon+","+lonlat.lat);
		markerE=map.createMarker(lonlat,icon);
		map.addMarkerObject(markerE);
		var lonlatend=map.createLonlat2Second128(lonlat);
		//$$("#endPoint").val(lonlatend);
		$$("#endPoint").val("地图上的点");
		$$("#endPointLonLat").val(lonlatend);
	} else if (type.indexOf("pass") != -1) {
		var lonlatpass=map.createLonlat2Second128(lonlat);
		$$("#byPassInput")
				.append(
						"<div class='bypass_item'>"
								+ "<input type='text' id='passPoint"+passarnum+"' class='passPoint' value='地图上的点' readonly='readonly'>"
								+ "<input type='hidden' id='passPointLonlat"+passarnum+"' class='passPointLonlat' value='"+ lonlatpass + "'>"
							    + "<span class='item_delete' onclick='deleteByPass(this.parentNode)'></span>"
							    + "</div>");
		var marker=map.createMarker(lonlat,icon);
		map.addMarkerObject(marker);
		//将途经点存入数组
		passarr.push(marker);
		var passarnum=oldpassarr.length;
		oldpassarr.push("");
		//添加一个标记管理器
		//map.passMarkerLayer.addMarker(marker);
	}
	clearSwitchPoint();
}

//清空选点相关信息
function clearSwitchPoint() {
	var pointDiv = document.getElementById("routeSearchPoint");
	pointDiv.style.cssText = "";
	document.body.onmousemove = null;
	document.body.onkeydown = null;
	map.unregisterEvents("click", markerRoutePoint);
}
//删除途径点
function deleteByPass(obj) {
	var idx = $$("#byPassInput div.bypass_item").index(obj);
	//删除图标上的途经点
	var marker = passarr[idx];
	map.removeMarker(marker);
	//删除数组中的Marker
	passarr.splice(idx,1);
	oldpassarr.splice(idx,1);
	$$("#byPassInput").get(0).removeChild(
			$$("#byPassInput div.bypass_item").eq(idx).get(0));
}
function startPointFocus(){
	if($$("#startPointLonLat").val()!=""&&$$("#startPoint").val()!="地图上的点"){
		$$("#startPointLonLat").val("");
	}
}
function endPointFocus(){
	if($$("#endPointLonLat").val()!=""&&$$("#endPoint").val()!="地图上的点"){
		$$("#endPointLonLat").val("");
	}
}
function PassPointFocus(passarnum){
	if($$("#passPoint"+passarnum).val()!=""&&$$("#passPoint"+passarnum).val()!="地图上的点"){
		$$("#passPointLonlat"+passarnum).val("");
	}
}
//检查其余
function ChangePoint(){
	if($$("#startPoint").val()==""){
		alert("请输入起点");
		return false;
	}
	if($$("#endPoint").val()==""){
		alert("请输入终点");
		return false;
	}
	
	/*$$(".passPoint").each(function() {
		var passPointText = $$(this).val();
		if(passPointText!=oldpassarr[passNum]&&passPointText!="地图上的点"){
			ShowNameList("searchpoibykey",1,"startPoint",passNum+2);
			passNum++;
			return false;
		}
		
	});*/
	if($$("#startPointLonLat").val()==""){
		ShowNameList("searchpoibykey",1,"startPoint",1);
		return false;
	}
	if($$("#endPointLonLat").val()==""){
		ShowNameList("searchpoibykey",1,"endPoint",2);
		return false;
	}
	/*for(var i=0;i<oldpassarr.length;i++){
		var passPointText = $$("#passPoint"+i).val();
		if(passPointText!=oldpassarr[i]&&passPointText!="地图上的点"){
			ShowNameList("searchpoibykey",1,"passPoint"+i,i+3);
			return false;
		}
	}*/
	if($$("#endPointLonLat").val()==$$("#startPointLonLat").val()){
		alert("请起终点不能是同一个位置");
		return false;
	}
	return true;
}
function ShowNameList(actionName,selectedPage,id,type){
	$$(".main_left_middle").empty();
	 $$.ajax({  
	        url: actionName,  
	        type: "post",  
	        data: {   
	           'sp.key' : encodeURI(trim($$("#"+id).val())),
	           'sp.pageNumber':KEY_PAGESIZE,
	           'sp.startPosition':selectedPage,
	           'sp.json':0
	        }, 
	        beforeSend : function() {
				ShowLoadingDiv();
			}, 
	        success:function(data){//alert(JSON.stringify(data))
	        	var recodeString=data.recodeString.replace(/^\n+|\n+$/g,"");
	        	if(trim(recodeString)!="null"){
	            var list = $$.parseJSON(data.recodeString);
	            var featureMember = list.FeatureCollection.featureMember;
	            
	            if(featureMember!=null){
	            	var str ="<table cellpadding='0' cellspacing='0' style='width:95%'>";
					for ( var i = 0; i < featureMember.length; i++) {
						var JXPOI = featureMember[i].JXPOI;
						var lonlat=JXPOI[0].GEOMETRY[0].Point[0].coordinates+"";
						var name=JXPOI[0].NAME;
						var address=JXPOI[0].ADDRESS;
						var pointtishi;
						if(type==1){
							pointtishi="起点";
						}else if(type==2){
							pointtishi="终点";
						}else {
							pointtishi="途径";
						}
						str += "<tr><td id='addLi_"+ i +"'  class='addLi'>" +
						"<div style='float:left'><div class='addLi_img'><img  src='WebPage/image/newmap_icon/1.png'/></div>"+
						"<label class='addLi_select_pointname' >"+ name+"</label></div>" +
								"<div class='addLi_right' style='float:right;'><label class='addLi_select_point' onclick=SelectPoint("+type+",'"+ name+"','"+address+"','"+lonlat+"') >选为"+pointtishi+"</label></div></td></tr>";
					
					}
					  str+="</table>";
			            $$(".main_left_middle").html(str);
	            }else{
	            	TableIsNull();
	            }
	          
	        }  
	      	else{
	      	alert("无法获取数据");
	      	}
	      },
			complete : function() {
				CloseLoadingDiv();
		}
	    });
}
function TableIsNull(){
	$$(".main_left_middle").append($$("<div class='layout1_left_datalist_tishi' style='text-align:center;'>没有符合条件的结果</div>"));
}

function SelectPoint(type,name,address,lonlat){
	var Lon=lonlat.split(",");
	var jd=String(Lon[0]*460800).split(".")[0];
	var wd=String(Lon[1]*460800).split(".")[0];
	var lonlatstart=0+","+jd+","+wd;
	//alert("lon="+Lon[0].substring(0,Lon[0].length-1)+","+"lat="+Lon[1].substring(0,Lon[1].length-1));
	//var lonlatstart=map.createLonlat2Second128("lon="+Lon[0].substring(0,Lon[0].length-1)+","+"lat="+Lon[1].substring(0,Lon[1].length-1));
	//alert(lonlatstart);
	if(type==1){
		$$("#startPoint").val(name);
		$$("#startPointLonLat").val(lonlatstart);
		//map.removeMarker(markerStart);
		if (markerS != null) {
			wmap.markersLayer.removeMarker(markerS);
		}
		markerS=map.drawMarker(lonlat,"WebPage/image/driving/start.png",30);
		if($$("#endPointLonLat").val()==""&&$$("#endPoint").val()!="地图上的点"){
			ShowNameList("searchpoibykey",1,"endPoint",2);
		}/*else{
			executeSearch(null,0);
		}*/
	}else if(type==2){
		$$("#endPoint").val(name);
		$$("#endPointLonLat").val(lonlatstart);
		//map.removeMarker(markerEnd);
		if (markerE != null) {
			wmap.markersLayer.removeMarker(markerE);
		}
		markerE=map.drawMarker(lonlat,"WebPage/image/driving/end.png",30);
	}
	/*var pnum=0;
	for(var i=0;i<oldpassarr.length;i++){
		var passPointText = $$("#passPoint"+i).val();
		if(passPointText!=oldpassarr[i]&&passPointText!="地图上的点"){
			ShowNameList("searchpoibykey",1,"passPoint"+i,i+3);
			pnum++;
		}
	}
	if(pnum==0)*/ executeSearch(null,0);
}