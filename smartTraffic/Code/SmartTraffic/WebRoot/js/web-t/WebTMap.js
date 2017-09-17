//初始化地图
var wmap;
var rightMenu;
var panZoom;
var overview;
var scaleLine;
/*var markerS;
var markerE;*/
var aMarker = ["images/bus/start.png","images/bus/map_walk.png","images/bus/map_bus.png","images/bus/map_metro.png","images/bus/end.png"];
/**
 * 构建四维地图
 * @param mapDiv  地图所在的div
 */
function WebTMap(mapDiv){
	// 实例化地图对象
	wmap = new WebtAPI.WMap($(mapDiv));
	
	// 设置地图初始位置
	var lonlat = new OpenLayers.LonLat(120.759341,30.7553079);
	//设置地图中心
	wmap.setCenterByLonLat(lonlat,3);
	/********************创建对象*************************/
	//创建经纬度对象
	wmap.createLonlat=function createLonlat(lonlat){
		var lonlat=new OpenLayers.LonLat(lonlat.split(",")[0],lonlat.split(",")[1]);
		return lonlat;
	}
	//根据边界获取四维经纬度
	wmap.getWGS84ByBound=function getWGS84ByBound(lon,lat){
		var lonlat=WebtAPI.WUtil._toWGS84(wmap.createLonlat(lon+","+lat));
		return lonlat;
	}
	//创建图标对象
	wmap.createIcon=function createIcon(imageIcon,size1){
		if(typeof(imageIcon)=='undefined'){
			imageIcon="images/marker/marker2.png";
		}
		if(typeof(size1)=='undefined'){
			size1=30;
		}
		var calculateOffset = function(size) {
	        return new OpenLayers.Pixel(-(size.w/2), -size.h);
	    };
		var icon=new WebtAPI.WIcon(imageIcon,
				new OpenLayers.Size(size1,size1),
				null,
				calculateOffset
		);
		return icon;
	}
	//创建图标对象
	wmap.createMarker=function createMarker(lonlat,icon){
		var marker= new WebtAPI.WMarker(lonlat, icon);
		return marker;
	}
	//添加Marker
	wmap.addMarkerObject=function addMarkerObject(marker){
		wmap.markersLayer.addMarker(marker);
	}
	//创建关键字查询对象
	wmap.createKeyQuery=function createKeyQuery(key,pageCount,pageNumber){
		var keyQuery = new WebtAPI.WSearch.KeywordSearch();
		keyQuery.setOption("adcode", 330400);
		keyQuery.setOption("searchtype", "all");
		keyQuery.setOption("pageCount", pageCount);
		keyQuery.setOption("pageNumber", pageNumber);
		keyQuery.setOption("key", key);
		return keyQuery;
	}
	//创建分类查询对象
	wmap.createTypeQuery=function createTypeQuery(type,pageCount,pageNumber){
		var typeQuery=new WebtAPI.WSearch.KeywordSearch();
		typeQuery.setOption("adcode", 330400);
		typeQuery.setOption("searchtype", "all");
		typeQuery.setOption("pageCount", pageCount);
		typeQuery.setOption("pageNumber", pageNumber);
		typeQuery.setOption("datatype",type);
		return typeQuery;
	}
	//创建周边查询对象
	wmap.createSurroundQuery=function createSurroundQuery(lon,lat,key,radius,pageCount,pageNumber){
		var surroundQuery=new WebtAPI.WSearch.SurroundSearch();
		surroundQuery.setOption("adcode",330400);
		surroundQuery.setOption("centerLon",lon);
		surroundQuery.setOption("centerLat",lat);
		surroundQuery.setOption("searchtype","all");
		surroundQuery.setOption("radius",radius);
		surroundQuery.setOption("inRadius",1);
		surroundQuery.setOption("key",key);
		surroundQuery.setOption("pageCount",pageCount);
		surroundQuery.setOption("pageNumber",pageNumber);
		return surroundQuery;
		
	}
	//创建驾车查询对象
	wmap.createRouteQuery=function createRouteQuery(point,costModel,criteria,avoidArea){
		var routeQuery= new WebtAPI.WSearch.RouteSearch();
		routeQuery.setOption("point",point);
		routeQuery.setOption("shpflag",1);
		routeQuery.setOption("costModel",costModel);
		routeQuery.setOption("criteria",criteria);
		routeQuery.setOption("avoidarea",avoidArea);
		return routeQuery;
	}
	//驾车查询中添加规避图层
	wmap.createAvoidLayer=function createAvoidLayer(){
		var layer = new OpenLayers.Layer.Vector("avoidAreaLayer");
		return layer;
	}
	//创建途经点图层
	wmap.createPassMarkerLayer=function createPassMarkerLayer(){
		var layer=new WebtAPI.MarkerLayer("passMarkerLayer");
		return layer;
	}
	//创建语言控制器
	wmap.createLanguage=function createLanguage(){
		var langQuery = new WebtAPI.GetLangInfo({
			callback : "GetLongInfoCallback"
		});
		langQuery.URL = DEFAULT_GIS_URL + "routequery/routelangquery.json";
		return langQuery;
	}
	//在地图中添加矩形
	wmap.createPolygon=function createPolygon(layer){
		//新增矩形绘制控件到地图
		var polygonControl = new OpenLayers.Control.DrawFeature(layer,
				OpenLayers.Handler.RegularPolygon,
				//irregular:是否可以自由拖拽矩形
				{
					handlerOptions : {
						irregular : true,
						snapAngle : 0
					},
					id : "polygonControl",
					featureAdded : avoidAreaAdded
				});
		wmap.addControl(polygonControl);
	}
	//POI最合适比例
	wmap.createSuitable=function createSuitable(){
		var bound = map.markersLayer.getDataExtent();
		map.zoomToExtent(bound, false);
	}
	//返回字符串，为经纬度的128秒
	wmap.createLonlat2Second128=function createLonlat2Second128(lonlat) {
		return "0,"
				+ [ Math.round(lonlat.lon * 128 * 3600),
						Math.round(lonlat.lat * 128 * 3600) ];
	}
	//返回数组，为经纬度的128秒
	wmap.createLonlat2Second128Lonlat=function createLonlat2Second128Lonlat(lonlat){
		return [Math.round(lonlat.lon * 128 * 3600), Math.round(lonlat.lat * 128 * 3600)];
	}
	/********************创建对象*************************/
	//获取84经纬度
	wmap.getWGS84=function getWGS84(position){
		var lonlat = WebtAPI.WUtil._toWGS84(map.getLonLatFromViewPortPx(position));
		return lonlat;
	}
	//添加右键功能
	//rightMenu = new WebtAPI.WControl.WContextMenu({showItemIcon: false});
	//rightMenu.displayClass = "contextMenu";
	//wmap.addContextMenu(rightMenu);
	//鱼骨控件
	panZoom = new WebtAPI.WControl.WPanZoom();
	//比例尺鹰眼控件
	scaleLine=new WebtAPI.WControl.ScaleLine();
	//鹰眼控件
	overview=new WebtAPI.WControl.WOverviewMap();
	//鼠标右键功能
	//rightToGetLonlat();
	/*//是鼠标右键生效
	wmap.addContextMenu=function addContextMenu(){
		rightMenu.activate();
	}
	//关闭鼠标右键
	wmap.closeContextMenu=function closeContextMenu(){
		rightMenu.deactivate();
	}*/
	//添加比例尺
	wmap.addScaleLine=function addScaleLine(){
		wmap.addControl(scaleLine);
	}
	//添加鹰眼
	wmap.addOverview=function addOverview(){
		wmap.addControl(overview);
	}
	//添加鱼骨
	wmap.addPanZoomBar=function addPanZoomBar(){
		wmap.addControl(panZoom);
	}
	//wmap.addPanZoomBar();
	//添加地图类型
	wmap.addNavType=function addNavType(){
		var selectMenu = new WebtAPI.WControl.WSelectMenu();
		wmap.addControl(selectMenu);
		var layerControl = new WebtAPI.WControl.LayersSwitcher();
		wmap.addControl(layerControl);
		var layer = [
		    wmap.chiDayLayer = new WebtAPI.TileCacheLayer("chiDayLayer", "http://192.168.10.192/fmapimg_chi/", {isBaseLayer: false, visibility: false}),
		    wmap.engDayLayer = new WebtAPI.TileCacheLayer("engDayLayer", "http://192.168.10.192/fmapimg_eng/", {isBaseLayer: false, visibility: false}),
		    wmap.chiNightLayer = new WebtAPI.TileCacheLayer("chiNightLayer", "http://192.168.10.192/fmapimg_chi_n/", {isBaseLayer: false, visibility: false}),
			wmap.engNightLayer = new WebtAPI.TileCacheLayer("engNightLayer", "http://192.168.10.192/fmapimg_eng_n/", {isBaseLayer: false, visibility: false})
		];
		wmap.addLayers(layer);
	}
	//设置中心点
	wmap.setMapCenter=function setMapCenter(lonlat,zoom){
		var lonlat=wmap.createLonlat(lonlat);
		wmap.setCenterByLonLat(lonlat,zoom);
	}
	/**
	 * 在地图上添加折线
	 * @param lineString  坐标系列
	 * @param color       线条颜色
	 * @param dash		  线条虚实
	 * @param strokeWidth	线条宽度
	 */
	wmap.drawLine=function drawLine(lineString,color,dash,strokeWidth){
		if(typeof(strokeWidth)=="undefined"){
			strokeWidth=3;
		}
		var line = lineString+"";
		if(line.indexOf(";")!=-1){
			var lonlats = line.split(";");
			var lonlatslength = lonlats.length;
			if (lonlatslength > 0) {
				var lineArr = new Array();
				for ( var i = 0; i < lonlatslength; i++) {
					var lonlat = lonlats[i];
					if (lonlat == "") {
						continue;
					}
					var lon = lonlat.split(",")[0];
					var lat = lonlat.split(",")[1];
					lineArr.push(new OpenLayers.LonLat(lon, lat));
				}
				var lineStyle=WebtAPI.style["line"];
				lineStyle["strokeColor"]=color;
				lineStyle["strokeWidth"]=strokeWidth;
				lineStyle["strokeOpacity"]=1;
				lineStyle["strokeDashstyle"]=dash;
				var lineFeature=new WebtAPI.LineFeature(lineArr,lineStyle);
				wmap.dynamicVectorLayer.addFeatures(lineFeature);
				/*var bound=wmap.dynamicVectorLayer.getDataExtent();
				wmap.zoomToExtent(bound,false);	*/
			}
		}else{
			var lonlats = line.split(",");
			var lonlatslength = lonlats.length;
			if (lonlatslength > 0) {
				var lineArr = new Array();
				for ( var i = 0; i < lonlatslength; i = i + 2) {
					var lon = lonlats[i];
					var lat = lonlats[i + 1];
					lineArr.push(new OpenLayers.LonLat(lon, lat));
				}
				var lineStyle = WebtAPI.style["line"];
				lineStyle["strokeColor"] = color;
				lineStyle["strokeOpacity"] = 1;
				lineStyle["strokeWidth"]=3;
				lineStyle["strokeDashstyle"] = dash;
				var lineFeature = new WebtAPI.LineFeature(lineArr, lineStyle);
				wmap.dynamicVectorLayer.addFeatures(lineFeature);
				/*var bound=wmap.dynamicVectorLayer.getDataExtent();
				wmap.zoomToExtent(bound,false);	*/
			}
		}
		
	}
	//删除线
	wmap.clearLines=function clearLines(){
		wmap.dynamicVectorLayer.removeAllFeatures();
	}
	//删除所有marker
	wmap.clearMarkers=function clearMarkers(){
		wmap.markersLayer.clearMarkers();
	}
	//添加标记叠加层
	wmap.drawMarker=function drawMarker(lonlat,imageIcon,size){
		var lonlat=new OpenLayers.LonLat(lonlat.split(",")[0], lonlat.split(",")[1]);
		var marker=wmap.addMarker(lonlat, imageIcon, size);
		return marker;
	}
	//添加标记
	wmap.addMarker=function addMarker(lonlat,imageIcon,size){
		var calculateOffset = function(size) {
	        return new OpenLayers.Pixel(-(size.w/2), -size.h);
	    };
		var icon=new WebtAPI.WIcon(imageIcon,
				new OpenLayers.Size(30,30),
				null,
				calculateOffset
		);
		var marker= new WebtAPI.WMarker(lonlat, icon);
		wmap.markersLayer.addMarker(marker);
		return marker;
	}
	//添加单独的popup
	wmap.createPopup=function createPopup(html,lonlat,width){
		var lonlat = new OpenLayers.LonLat(lonlat.split(",")[0], lonlat.split(",")[1]);
		var popup=new WebtAPI.WPopup(null,lonlat,width,html,true);
		popup.setToolbarDisplay(false);
		wmap.addPopup(popup,true);
	}
	/**
	 * 添加带有效果的面
	 * @param lineString  经纬度字符串
	 * @param color    面的填充色
	 * @param dash     面的虚实
	 * @param name     面的中心点经纬度
	 * @param zoom	   缩放级别
	 */
	wmap.drawPolygon=function drawPolygon(lineString,color,dash,name,zoom){
		var lonlat=lineString.split(";");
		wmap.setMapCenter(name, zoom);
	/*	if(typeof(name)!='undefined'){
			if(type==0){
				if(areaName=="行政中心"){
					wmap.setMapCenter(name,5);
				}else{
					wmap.setMapCenter(name, 6);
				}
			}else{
				if(areaName=="内环内"){
					wmap.setMapCenter(name, 5);
				}else{
					wmap.setMapCenter(name, 3);
				}
			}
		}*/
		var length=lonlat.length;
		var points=new Array();
		for(var i=0;i<length;i++){
			var lon=lonlat[i].split(",")[0];
			var lat=lonlat[i].split(",")[1];
			points.push(new OpenLayers.LonLat(lon, lat));
		}
	    var style = WebtAPI.WStyle.clone();
	    style["strokeColor"]="black";
	    style["strokeWidth"]=1;
		style["strokeOpacity"]=1;
		style["strokeDashstyle"]="solid";
		style["fillColor"]=color;
	    var feature = new WebtAPI.PolygonFeature(points, style, {code: "110000", id: name});
	     
	    control = new WebtAPI.WControl.PolygonPainter({callback:{"mousedown":rightCallback}});
	    wmap.addControl(control);
	    control.activate();
	    control.drawPolygon(feature);
	    function rightCallback (evt, feature){
	    	wmap.setMouseDragControlActivate(true);
	    	wmap.setKineticDragControlActivate(true);
	    }
	    control.register("mouseup", function(evt, feature){
	    	wmap.setMouseDragControlActivate(true);
	    });
	    control.register("zoomend",function(evt,feature){
	    	alert("zoomed");
	    })
	    return feature;
	}
	//给marker添加popup
	wmap.createPopupForMarker=function createPopupForMarker(lonlat,content,imageIcon,imageSize,width){
		//var marker=wmap.addMarker(lonlat,imageIcon,imageSize);
		var marker=wmap.drawMarker(lonlat, imageIcon, imageSize);
		marker.register("click",function(){
			var html = content;
	        var popup = new WebtAPI.WPopup(null, marker,width, html, true);
	        popup.setToolbarDisplay(false);
	        //添加POPUP到地图中。
	        wmap.addPopup(popup, true);
		});
	}
	//实时路况
	wmap.setVisibility=function setVisibility(bool){
		wmap.trafficLayer.setVisibility(bool);
		/*wmap.trafficLayer.setTimeInterval(60000);*/
	}
	//根据中心点画圆
	wmap.drawRadio=function drawRadio(loc,m,color,dash){
		var lon=loc.split(",")[0];
		var lat=loc.split(",")[1];
		var lonlat=new OpenLayers.LonLat(lon,lat);
		var aroLL = WebtAPI.WUtil._toMerc(lonlat);
		var ringFeature=OpenLayers.Geometry.Polygon.createRegularPolygon(
				new OpenLayers.Geometry.Point(aroLL.lon, aroLL.lat),
				m, 40, 360
		);
		var lineStyle=WebtAPI.style["line"];
		lineStyle["strokeColor"]="pink";
		lineStyle["strokeOpacity"]=1;
		lineStyle["strokeWidth"]=3;
		lineStyle["strokeDashstyle"]="solid";
		lineStyle["fillColor"]="green";
		lineStyle["strokeDashstyle"]=dash;
		wmap.dynamicVectorLayer.addFeatures(new OpenLayers.Feature.Vector(
				ringFeature,null,lineStyle
		));
	} 
	return wmap;
}

/*********************************右键添加起点终点途经点*************************************/
function addRoutPoint(lonlat,type){
	var calculateOffset = function(size) {
		return new OpenLayers.Pixel(-(size.w/2), -size.h);
	};
	if(type.indexOf("start")!=-1){
		if(markerS!=null){
			wmap.markersLayer.removeMarker(markerS);
		}
		var icon=new WebtAPI.WIcon(aMarker[0],
				new OpenLayers.Size(30,30),
				null,
				calculateOffset
		);
		markerS= new WebtAPI.WMarker(lonlat, icon);
		wmap.markersLayer.addMarker(markerS);
	}
	if(type.indexOf("end")!=-1){
		if(markerE!=null){
			wmap.markersLayer.removeMarker(markerE);
		}
		var icon=new WebtAPI.WIcon(aMarker[4],
				new OpenLayers.Size(30,30),
				null,
				calculateOffset
		);
		markerE= new WebtAPI.WMarker(lonlat, icon);
		wmap.markersLayer.addMarker(markerE);
	}
	if(type.indexOf("pass")!=-1){
		wmap.addMarker(lonlat,"images/bus/pass.png",30);
	}
}
/************************************鼠标点击设置起点终点途经点*************************************/
function switchPoint(type){
	var pointImg = document.getElementById("routeSearchPointImg");
	if(!wmap || !type) return;
	//当传入点为起终点时，要清空页面原有起终点，保持唯一起终点
	var markers = wmap.markersLayer.markers;
	if(type.indexOf("start")!=-1){
		if(markerS!=null){
			wmap.markersLayer.removeMarker(markerS);
		}
		pointImg.value = "images/bus/"+type+".png";
	}
	if(type.indexOf("end")!=-1){
		if(markerE!=null){
			wmap.markersLayer.removeMarker(markerE);
		}
		pointImg.value = "images/bus/"+type+".png";
	}
	if(type.indexOf("pass")!=-1){
		pointImg.value = "images/bus/"+type+".png";
	}
	//为地图注册标注事件
	wmap.registerEvents("click", markerRoutePoint);
}
//地图标注事件
function markerRoutePoint(e){
	var position = this.events.getMousePosition(e);
	var lonlat = WebtAPI.WUtil._toWGS84(wmap.getLonLatFromViewPortPx(position));
	var calculateOffset = function(size) {
    	return new OpenLayers.Pixel(-(size.w/2), -size.h);
  	};
  	var type = document.getElementById("routeSearchPointImg").value;
  	var size = new OpenLayers.Size(32, 33);
  	var icon = new WebtAPI.WIcon(type, size, null, calculateOffset);
  	if(type.indexOf("start")!=-1){
  		markerS = new WebtAPI.WMarker(lonlat, icon);
  		wmap.markersLayer.addMarker(markerS);
  	}
  	else if(type.indexOf("end")!=-1){
  		markerE = new WebtAPI.WMarker(lonlat, icon);
  		wmap.markersLayer.addMarker(markerE);
  	}else if(type.indexOf("pass")!=-1){
  		var marker=new WebtAPI.WMarker(lonlat, icon);
  		wmap.markersLayer.addMarker(marker);
  	}
	clearClick();
}
//清空选点相关信息
//注销鼠标监听事件
function clearClick(){
	//alert(document.body.click);
	//alert(document.body.onclick);
	document.body.onmousemove = null;
	document.body.onkeydown = null;
	wmap.unregisterEvents("click", markerRoutePoint);
}
//语言查询器
function GetLongInfoCallback(json) {
	var data = json["response"]["result"]["langInfo"];
	langData = data["1"];
}
function rightCallback (evt, feature){
    if(feature.hasOwnProperty("code")){
        alert(feature["code"]);
    }
}