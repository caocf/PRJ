//var hyperlinks="http://115.231.73.253/zhjtapi";//四维链接地址
//相关图标
var aMarker = [ "WebPage/image/common/map_point_start.png", 
                "http://115.231.73.253/zhjtapi/images/bus/map_walk.png",
                "http://115.231.73.253/zhjtapi/images/bus/map_bus.png", 
                "http://115.231.73.253/zhjtapi/images/bus/map_metro.png",
                "WebPage/image/common/map_point_end.png" ];
// 定义当前图层组
var currentGroup;
var map; // 定义全局的map
var wmap;//web-t的map
var maplet;//图吧地图的map
var menu;//图吧的右键
var vectoryGroup; // 矢量图层组
var baseLayerGroup; // 基础图层组
var imageGroup; // 影像图层组

var panZoomBarTitle; // 鱼骨控件
var overviewMap; // 鹰眼控件
var scaleLine; // 比例尺控件

var markers; // 标记集合
var currentPopup; // 当前浮动窗口
var selectControl, selectedFeature;

var single; // 鼠标单击事件
var contextMenu; // 鼠标右键

var markerS = null; // 定义起点Marker，整个页面中只要一个
var markerE = null; // 定义终点Marker，整个页面中只要一个

var AutoSizeFramedCloud;// 定义浮云对象
var sketchSymbolizers;  /** 定义全局的点，线，面样式 */
var style;// 定义样式规则对象
var styleMap;// 定义地图的默认样式
var distance;// 距离
var measure;// 面积
var vectorLayer;// 定义矢量要素图层 ----------------->根据经纬度画出来的点，线，面
var markers;// 定义图形标记对象
var ploygonStyles;// 定义点面等要素的样式
var lineStyles;// 定义线要素的样式
var polygonStyleLayer;// 构造自定义样式图层 -------------------->相对于点和面
var lineStyleLayer;// 构造自定义样式图层 -------------------->相对于线
var dot;// 画点,画线，画面的工具
var line;
var circle;

var dragPan;//定义拖拽对象
var navigation;//创建导航控件对象，设置鼠标滚轮缩放地图的属性为关闭
var Cfg;
/**
 * 天地图相关参数设置
 */
function createTdtParam(){
	//定义地图中点线面的样式
	sketchSymbolizers = {"Point" : {
				pointRadius : 4,
				graphicName : "square",
				fillColor : "white",
				fillOpacity : 1,
				strokeWidth : 1,
				strokeOpacity : 1,
				strokeColor : "#333333"
			},
			"Line" : {
				strokeWidth : 3,
				strokeOpacity : 1,
				strokeColor : "#666666",
				strokeDashstyle : "dash"
			},
			"Polygon" : {
				strokeWidth : 2,
				strokeOpacity : 1,
				strokeColor : "#666666",
				fillColor : "red",
				fillOpacity : 0.3
			}
	
		};
	style = new Geo.Style();
	style.addRules([ new Geo.Rule({
		symbolizer : sketchSymbolizers
	}) ]);
	styleMap = new Geo.StyleMap({
		"default" : style
	});
	AutoSizeFramedCloud = Geo.Class(Geo.View2D.Popup.FramedCloud, { 
        'autoSize': false 
    }); 
	Geo.View2D.Popup.FramedCloud.prototype.autoSize = false; 
	//----------------------距离工具
	distance = new Geo.View2D.Control.Measure.DistanceMeasure({
		// 设置为true,清除之前的绘制
		persist : true,
		handlerOptions : {
			layerOptions : {
				// 定义测量的样式
				styleMap : styleMap
			}
		}
	});
	measure = new Geo.View2D.Control.Measure.AreaMeasure({
		// 设置为true,清除之前的绘制
		persist : true,
		handlerOptions : {
			layerOptions : {
				// 定义测量的样式
				styleMap : styleMap
			}
		}
	});
	vectorLayer = new Geo.View2D.Layer.Vector("Vector Layer", {
		styleMap : styleMap
	});
	// 定义标记
	markers = new Geo.View2D.Layer.Markers("zibo");
	/** **********************************点线面要素的样式************************************ */
	polygonstyle={
			pointRadius : 6,
			fillColor : "#ffffff",
			strokeColor : "#666666",
			strokeWidth : 2,
			graphicZIndex : 1,
			fillOpacity : 0.7
	}
	ploygonStyles = new Geo.StyleMap({
		"default" :new Geo.Style(polygonstyle),
		"select" : new Geo.Style({
			fillColor : "#66ccff",
			strokeColor : "#3399ff",
			graphicZIndex : 2
		})
	});
	lineStyles = new Geo.StyleMap({
		"default" : new Geo.Style({
			pointRadius : 6,
			strokeColor : "#D92126",
			strokeWidth : 5,
			graphicZIndex : 1,
			fillOpacity : 0.7
		})
	});
	polygonStyleLayer = new Geo.View2D.Layer.Vector("style Geometry", {
		styleMap : ploygonStyles,
		rendererOptions : {
			zIndexing : true
		}
	});
	lineStyleLayer = new Geo.View2D.Layer.Vector("line style Geometry", {
		styleMap : lineStyles
	});
	
	dot = new Geo.View2D.Control.DrawFeature(polygonStyleLayer,
			Geo.View2D.Handler.Point);
	line = new Geo.View2D.Control.DrawFeature(lineStyleLayer,
			Geo.View2D.Handler.Path);
	// 定义圆的样式
	circle = new Geo.View2D.Control.DrawFeature(polygonStyleLayer,
			Geo.View2D.Handler.RegularPolygon, {
				handlerOptions : {
					sides : 40
				}
			});
	//定义拖拽对象
	dragPan = new Geo.View2D.Control.DragPan();
	// 创建导航控件对象，设置鼠标滚轮缩放地图的属性为关闭
	navigation = new Geo.View2D.Control.Navigation({
		zoomWheelEnable : false
	});
	
}

/**
 * 加载天地图瓦片数据封装地图
 */
function loadTdt(){
	// 定义全局的地图属性
	/**
	 * 加载天地图的wmts服务：注意 1-14级调用全国的服务 15-17级调用浙江的服务 18-19级调用嘉兴的服务 默认加载的是全国天地图
	 */
	// 定义配置对象
	Cfg = {};
	// 定位的中心点
	Cfg.lonLat = "120.75121946334839,30.747786235809325";
	// 定位的缩放级别
	Cfg.zoom = "14";
	// 每英寸对应的像素值
	Cfg.DOTS_PER_INCH = 100;
	// 设置dpi
	Geo.setDPI(Cfg.DOTS_PER_INCH);
	// 比例尺
	Cfg.mapWMTSLayer_Scales = "2.958293554545656E8,1.479146777272828E8,7.39573388636414E7,3.69786694318207E7,1.848933471591035E7,9244667.357955175,4622333.678977588,2311166.839488794,1155583.419744397,577791.7098721985,288895.85493609926,144447.92746804963,72223.96373402482,36111.98186701241,18055.990933506204,9027.995466753102,4513.997733376551,2256.998866688275,1128.499433441375,564.2497167206875";
	// 比例尺转分辨率
	function getRes(scales) {
		var res = [];
		var scaleStr = scales.split(",");
		for ( var i = 0, j = scaleStr.length; i < j; i++) {
			res
					.push(map.pyramid
							.getResolutionForScale(parseFloat(scaleStr[i])));
		}
		return res;
	}
	/** ************************嘉兴市18-20级矢量底图**************************************** */
	var wmtsLayer_Res = getRes(Cfg.mapWMTSLayer_Scales);
	// 创建WMTS底图图层
	var wmtsLayer = new Geo.View2D.Layer.WMTS({
		name : "嘉兴18-20级wmts矢量地图服务",
		url : "http://220.191.220.71/JXEMAP/wmts",
		matrixSet : "Matrix_0",
		style : "default",
		layer : "DT_18_20",
		resolutions : wmtsLayer_Res,
		format : "image/png",
		zoomOffset : 1,
		maxResolution : wmtsLayer_Res[17],// 最大分辨率
		minResolution : wmtsLayer_Res[wmtsLayer_Res.length - 1],// 最小分辨率
		tileFullExtent : Geo.Bounds.fromString("-180.0,-90.0,180.0,90.0")
	});
	/** ************************嘉兴市18-20级矢量标注底图**************************************** */
	var wmtsLayerAnno_Res = getRes(Cfg.mapWMTSLayer_Scales);
	// 创建WMTS底图图层
	var wmtsLayerAnno = new Geo.View2D.Layer.WMTS({
		name : "嘉兴18-20级wmts矢量标注地图服务",
		url : "http://220.191.220.71/JXEMAPAnno/wmts",
		matrixSet : "Matrix_0",
		style : "default",
		layer : "DTBZ_18_20",
		resolutions : wmtsLayerAnno_Res,
		format : "image/png",
		zoomOffset : 1,
		maxResolution : wmtsLayer_Res[17],// 最大分辨率
		minResolution : wmtsLayer_Res[wmtsLayer_Res.length - 1],// 最小分辨率
		tileFullExtent : Geo.Bounds.fromString("-180.0,-90.0,180.0,90.0")
	});
	/** ************************浙江省15-17级矢量底图**************************************** */
	var wmtsLayer_Res_zj = getRes(Cfg.mapWMTSLayer_Scales);
	// 创建WMTS底图图层
	var wmtsLayer_zj = new Geo.View2D.Layer.WMTS({
		name : "15-17级浙江WMTS矢量底图",
		url : "http://srv.zjditu.cn:80/ZJEMAP_2D/wmts",
		matrixSet : "ZJEMAP",
		style : "default",
		layer : "TitleMatrixSet0",
		resolutions : wmtsLayer_Res_zj,
		format : "image/png",
		zoomOffset : 1,
		maxResolution : wmtsLayer_Res[14],// 最大分辨率
		minResolution : wmtsLayer_Res[16],// 最小分辨率
		tileFullExtent : Geo.Bounds.fromString("-180.0,-90.0,180.0,90.0")
	});
	/** ************************浙江省15-17级矢量标注底图**************************************** */
	var wmtsLayerAnno_Res_zj = getRes(Cfg.mapWMTSLayer_Scales);
	// 创建WMTS底图图层
	var wmtsLayerAnno_zj = new Geo.View2D.Layer.WMTS({
		name : "15-17级WMTS矢量注记底图",
		url : "http://srv.zjditu.cn:80/ZJEMAPANNO_2D/wmts",
		matrixSet : "ZJEMAP",
		style : "default",
		layer : "TitleMatrixSet0",
		resolutions : wmtsLayerAnno_Res_zj,
		format : "image/png",
		zoomOffset : 1,
		maxResolution : wmtsLayer_Res[14],// 最大分辨率
		minResolution : wmtsLayer_Res[16],// 最小分辨率
		tileFullExtent : Geo.Bounds.fromString("-180.0,-90.0,180.0,90.0")
	});
	/** ************************全国1-14级矢量底图**************************************** */
	var wmtsLayer_Res_qg = getRes(Cfg.mapWMTSLayer_Scales);
	// 创建WMTS底图图层
	var wmtsLayer_qg = new Geo.View2D.Layer.WMTS({
		name : "1-14级全国WMTS矢量底图",
		url : "http://t0.tianditu.com/vec_c/wmts",
		matrixSet : "c",
		style : "default",
		layer : "vec",
		resolutions : wmtsLayer_Res_qg,
		format : "tiles",
		zoomOffset : 1,
		maxResolution : wmtsLayer_Res[0],// 最大分辨率
		minResolution : wmtsLayer_Res[13],// 最小分辨率
		tileFullExtent : Geo.Bounds.fromString("-180.0,-90.0,180.0,90.0")
	});
	/** ************************全国1-14级矢量标注底图**************************************** */
	var wmtsLayerAnno_Res_qg = getRes(Cfg.mapWMTSLayer_Scales);
	// 创建WMTS底图图层
	var wmtsLayerAnno_qg = new Geo.View2D.Layer.WMTS({
		name : "1-14级全国WMTS矢量注记底图",
		url : "http://t0.tianditu.com/cva_c/wmts",
		matrixSet : "c",
		style : "default",
		layer : "cva",
		resolutions : wmtsLayerAnno_Res_qg,
		format : "tiles",
		zoomOffset : 1,
		maxResolution : wmtsLayer_Res[0],// 最大分辨率
		minResolution : wmtsLayer_Res[13],// 最小分辨率
		tileFullExtent : Geo.Bounds.fromString("-180.0,-90.0,180.0,90.0")
	});
	/** ************************全国1-17级影像底图**************************************** */
	// 1-17级全国影像图层
	var wmtsImgLayer_Res_qg = getRes(Cfg.mapWMTSLayer_Scales);
	// 创建影像WMTS底图图层
	var wmtsImageLayer_qg = new Geo.View2D.Layer.WMTS({
		name : "http://t0.tianditu.com/img_c/wmts",
		url : "http://t0.tianditu.com/img_c/wmts",
		matrixSet : "c",
		style : "default",
		layer : "img",
		zoomOffset : 1,
		resolutions : wmtsImgLayer_Res_qg,
		maxResolution : wmtsLayer_Res[0],// 最大分辨率
		minResolution : wmtsLayer_Res[16],// 最小分辨率
		format : "tiles",
		tileFullExtent : Geo.Bounds.fromString("-180.0,-90.0,180.0,90.0")
	});
	/** ************************全国1-17级影像标注底图**************************************** */
	// 1-17级全国影像标注图层
	var wmtsAnnoImgLayer_Res_qg = getRes(Cfg.mapWMTSLayer_Scales);
	// 创建影像WMTS底图图层
	var wmtsAnnoImageLayer_qg = new Geo.View2D.Layer.WMTS({
		name : "全国1-17级影像标注底图",
		url : "http://t0.tianditu.com/cia_c/wmts",
		matrixSet : "c",
		style : "default",
		layer : "cia",
		zoomOffset : 1,
		resolutions : wmtsAnnoImgLayer_Res_qg,
		maxResolution : wmtsLayer_Res[0],// 最大分辨率
		minResolution : wmtsLayer_Res[16],// 最小分辨率
		format : "tiles",
		tileFullExtent : Geo.Bounds.fromString("-180.0,-90.0,180.0,90.0")
	});

	/** ************************嘉兴市18-20级影像底图**************************************** */
	var wmtsImgLayer_Res_jx = getRes(Cfg.mapWMTSLayer_Scales);
	// 创建影像WMTS底图图层
	var wmtsImageLayer_jx = new Geo.View2D.Layer.WMTS({
		name : "http://220.191.220.71/JXIMG/wmts",
		url : "http://220.191.220.71/JXIMG/wmts",
		matrixSet : "Matrix_0",
		style : "default",
		layer : "YX_18_20",
		zoomOffset : 1,
		resolutions : wmtsImgLayer_Res_jx,
		maxResolution : wmtsLayer_Res[17],// 最大分辨率
		minResolution : wmtsLayer_Res[19],// 最小分辨率
		format : "image/png",
		tileFullExtent : Geo.Bounds.fromString("-180.0,-90.0,180.0,90.0")
	});
	/** ************************嘉兴市18-20级影像标注底图**************************************** */
	var wmtsAnnoImgLayer_Res_jx = getRes(Cfg.mapWMTSLayer_Scales);
	// 创建影像WMTS底图图层
	var wmtsAnnoImageLayer_jx = new Geo.View2D.Layer.WMTS({
		name : "嘉兴市18-20级影像标注底图",
		url : "http://220.191.220.71/JXIMGAnno/wmts",
		matrixSet : "Matrix_0",
		style : "default",
		layer : "YXBZ_18_20",
		zoomOffset : 1,
		resolutions : wmtsAnnoImgLayer_Res_jx,
		maxResolution : wmtsLayer_Res[17],// 最大分辨率
		minResolution : wmtsLayer_Res[19],// 最小分辨率
		format : "image/png",
		tileFullExtent : Geo.Bounds.fromString("-180.0,-90.0,180.0,90.0")
	});
	
	// 定义影像图层组
	imageGroup = new Geo.View2D.LayerGroup({
		layers : [ wmtsImageLayer_qg, wmtsImageLayer_jx, wmtsAnnoImageLayer_jx,
				wmtsAnnoImageLayer_qg ]
	});
	// 定义基础图层组
	baseLayerGroup = new Geo.View2D.BaseLayerGroup({
		layers : [ wmtsLayer, wmtsLayerAnno, wmtsLayer_zj, wmtsLayerAnno_zj,
					wmtsLayer_qg, wmtsLayerAnno_qg, polygonStyleLayer,
					lineStyleLayer, vectorLayer]
	});
}
/**
 * 构建XMap
 * @param mapDiv  div
 * @param type 控制地图类型
 * @param json 主要针对地图事件
 * @returns
 */
function XMap(mapDiv,type,json) {
	//若不输入type，则默认访问天地图
	if(typeof(type)=='undefined'){
		type=1;
	}
	if(type==1){
		createTdtParam();
		if(typeof(json)=="undefined"){
			map = new Geo.View2D.Map(mapDiv);
			/*map = new Geo.View2D.Map(mapDiv, {
				controls : [ dragPan, navigation,
						new Geo.View2D.Control.MagnifyingGlass() ]
			});*/
		}else{
			map=new Geo.View2D.Map(mapDiv,json);
		}
		loadTdt();
		
		// 加载基础图层组
		map.loadLayerGroup(baseLayerGroup);
		// 定位到中心点和缩放级别
		map.setCenter(new Geo.LonLat(Cfg.lonLat.split(",")[0], Cfg.lonLat
				.split(",")[1]), Cfg.zoom);
		/**
		 * 设置地图中心点
		 * @param lonlat  指定经纬度
		 * @param zoom    缩放级别
		 */
		map.setMapCenter = function setMapCenter(lonlat, zoom) {
			var lonlat = map.createLonlat(lonlat);
			if(zoom==null||zoom==undefined){
				zoom=map.getZoom();
			}
			map.setCenter(lonlat, zoom);
		}
		/**
		 * 添加比例尺控件
		 */
		map.addScaleLine = function addScaleLine() {
			map.closeScaleLine();
			if (scaleLine == null) {
				// 创建比例尺对象，修改比例尺的默认显示单位
				scaleLine = new Geo.View2D.Control.ScaleLine({
					topOutUnits : "公里",
					topInUnits : "米",
					bottomOutUnits : "英里",
					bottomInUnits : "英寸"
				});
				// 加载比例尺控件到地图
				map.addControl(scaleLine);
			}
		}
		/**
		 * 删除比例尺控件
		 */
		map.closeScaleLine = function closeScaleLine() {
			if (scaleLine) {
				map.removeControl(scaleLine);
				scaleLine = null;
			}
		}
		/**
		 * 添加鹰眼控件
		 */
		map.addOverview = function addOverViewMap() {
			map.closeOverview();
			if (overviewMap == null) {
				overviewMap = new Geo.View2D.Control.GeoOverviewMap({
					maximized : true,
					autoPan : true
				});
				map.addControl(overviewMap);
				overviewMap.displayClass = "overview";
			}
		}
		/**
		 * 关闭鹰眼控件
		 */
		map.closeOverview = function closeOverview() {
			if (overviewMap) {
				// 移除缩略图，移除其他控件也是调用removeControl方法
				map.removeControl(overviewMap);
				overviewMap = null;
			}
		}
		/**
		 * 添加鱼骨控件
		 */
		map.addPanZoomBar = function addPanZoomBar() {
			map.closePanZoomBar();
			if (panZoomBarTitle == null) {
				panZoomBarTitle = new Geo.View2D.Control.PanZoomBar();
				map.addControl(panZoomBarTitle);
			}
		}
		/**
		 * 删除鱼骨控件
		 */
		map.closePanZoomBar = function closePanZoomBar() {
			if (panZoomBarTitle) {
				map.removeControl(panZoomBarTitle);
				panZoomBarTitle = null;
			}
		}
		/**
		 * 添加鼠标位置控件
		 */
		map.addMousePosition = function addMousePosition() {
			var mousePosition = new Geo.View2D.Control.MousePosition({
				displayClass : "mousePositionCtrl"
			});
			map.addControl(mousePosition);
		}
		/**
		 * 卫星地图与电子地图的切换
		 */
		map.addNavType = function addNavType() {
			$$(document.body).append("<div id='nav'></div>");
			$$("#nav").click(
					function() {
						var image = "";
						image = $$("#nav").css("background-image");
						var dianziditub = "dianziditub.png";
						if (image.indexOf("weixingditub", 0) != -1) {
							switchGroup(imageGroup);
							$$("#nav").css("background-image",
									"url(http://115.231.73.253/zhjtapi/images/dianziditub.png)");
						} else {
							switchGroup(baseLayerGroup);
							$$("#nav").css("background-image",
									"url(http://115.231.73.253/zhjtapi/images/weixingditub.png)");
						}
					});
		}
		/**
		 * 开启地图可拖拽
		 */
		map.openPanMaps = function openPanMaps() {
			dragPan.activate();
		}
		/**
		 * 关闭地图可拖拽
		 */
		map.closePanMaps = function closePanMaps() {
			dragPan.deactivate();
		}
		// 默认开启地图拖拽
		map.closePanMaps();
		/**
		 * 测距工具
		 */
		map.lineTool = function lineTool() {
			if (measure != null) {
				measure.deactivate();
			}
			map.addControl(distance);
			distance.activate();
		}
		/**
		 * 侧面工具
		 */
		map.measureTool = function measureTool() {
			if (distance != null) {
				distance.deactivate();
			}
			map.addControl(measure);
			measure.activate();
		}
		/**
		 * 关闭工具
		 */
		map.closeTool = function closeTool() {
			measure.deactivate();
			distance.deactivate();
		}
		/**
		 * 画点工具
		 */
		map.addDot = function addDot() {
			if (dot != null) {
				map.closeDrawTool();
			}
			map.addControl(dot);
			dot.activate();
		}
		/**
		 * 画线工具
		 */
		map.addLine = function addLine() {
			if (line != null) {
				map.closeDrawTool()
			}
			map.addControl(line);
			line.activate();
		}
		/**
		 * 画圆工具
		 */
		map.addCircle = function addCircle() {
			if (circle != null) {
				map.closeDrawTool();
			}
			map.addControl(circle);
			circle.activate();
		}
		/**
		 * 删除指定标注----------------------标准方法
		 * @param marker 标注对象
		 */
		map.removeMarker=function removeMarker(marker){
			markers.removeMarker(marker);
		}
		/**
		 * 删除所有标注----------------------标准方法
		 */
		map.removeAllMarker=function removeAllMarker(){
			markers.clearMarkers();
		}
		/**
		 * 删除指定覆盖物---------------------标准方法
		 * @param vector 覆盖物对象(包含自定义标注)
		 */
		map.removeFeature = function removeFeature(vector) {
			vectorLayer.removeFeatures(vector);
		}
		/**
		 * 删除地图上所有的覆盖物----------------标准方法
		 */
		map.removeAllOverlay=function removeAllOverlay(){
			map.removeAllMarker();
			map.closeFeatures();
			
		}
		
		/**
		 * 清除地图上所有的要素
		 */
		map.closeFeatures = function closeFeatures() {
			lineStyleLayer.removeFeatures(lineStyleLayer.features);
			vectorLayer.removeFeatures(vectorLayer.features);
			polygonStyleLayer.removeFeatures(polygonStyleLayer.features);
		}
		/**
		 * 关闭点线面工具
		 */
		map.closeDrawTool = function closeDrawTool() {
			line.deactivate();
			dot.deactivate();
			circle.deactivate();
		}
		/**
		 * 删除feature对象----------------------过时方法
		 */
		map.clearLines=function clearLines(){
			vectorLayer.removeFeatures(vectorLayer.features);
		}
		/** *****************创建对象的方法********************** */
		//定义选择要素控件    
        selectControl = new Geo.View2D.Control.SelectFeature(vectorLayer, {    
            onSelect: onFeatureSelect,    
            onUnselect: onFeatureUnselect    
        });  
        //加载选择要素控件至地图中 
        map.addControl(selectControl);
     
		/**
		 * 创建经纬度对象
		 * @param lonlat  经纬度字符串
		 * @returns {Geo.LonLat}
		 */
		map.createLonlat = function createLonlat(lonlat) {
			var lonlat = new Geo.LonLat(lonlat.split(",")[0], lonlat.split(",")[1]);
			return lonlat;
		}
		/**
		 * 创建图标对象
		 * @param imageIcon  图标地址
		 * @param size  图标大小
		 * @returns {Geo.View2D.Icon}
		 */
		map.createIcon = function createIcon(imageIcon, size) {
			if (typeof (imageIcon) == 'undefined') {
				imageIcon = "http://115.231.73.253/zhjtapi/images/marker/marker2.png";
			}
			// size默认为30
			if (typeof (size) == 'undefined') {
				size = 30;
			}
			var icon = new Geo.View2D.Icon(imageIcon, new Geo.Size(size, size));
			return icon;
		}
		/**
		 * 创建标注对象
		 * @param lonlat  经纬度对象
		 * @param icon    图标对象
		 * @returns {Geo.Marker}
		 */
		map.createMarker = function createMarker(lonlat, icon) {
			var marker = new Geo.Marker(lonlat, icon);
			return marker;
		}
		/**
		 * 添加标注对象
		 * @param marker 标注对象  
		 */
		map.addMarkerObject = function addMarkerObject(marker) {
			markers.addMarker(marker);
			map.addLayer(markers);
		}
		/**
		 * 添加图标--------------------标准方法
		 * @param lonlat	经纬度字符串
		 * @param imageIcon	图标地址
		 * @param size		图标大小
		 */
		map.drawMarker=function drawMarker(lonlat, imageIcon, size) {
			if (typeof (imageIcon) == 'undefined') {
				imageIcon = "http://115.231.73.253/zhjtapi/images/marker/marker2.png";
				size = 30;
			}
			if (typeof (size) == 'undefined') {
				size = 30;
			}
			var lonlat = new Geo.LonLat(lonlat.split(",")[0], lonlat.split(",")[1]);
			var icon = new Geo.View2D.Icon(imageIcon, new Geo.Size(size, size));
			var marker = new Geo.Marker(lonlat, icon);
			markers.addMarker(marker);
			map.addLayer(markers);
		}
		/**
		 * 创建浮云对象
		 * @param id			浮云id  
		 * @param lonlat		经纬度对象
		 * @param contentSize	文本大小
		 * @param contentHTML	文本内容
		 * @param anchor		锚点
		 * @param closeBox
		 * @returns {Geo.View2D.Popup.FramedCloud}
		 */
		map.createPopupObject = function createPopupObject(id, lonlat, contentSize,
				contentHTML, anchor, closeBox) {
			var popup = new Geo.View2D.Popup.FramedCloud(id, lonlat, contentSize,
					contentHTML, anchor, closeBox);
			return popup;
		}
		/**
		 * 创建浮云大小
		 * @param width  高度
		 * @param height 宽度
		 * @returns {Geo.Size}
		 */
		map.createPopupSize = function createPopupSize(width, height) {
			var size = new Geo.Size(width, height);
			return size;
		}
		/**
		 * 创建右键对象
		 * @returns {Geo.View2D.Control.MapContextMenu}
		 */
		map.createContextMenu = function createContextMenu() {
			var contextMenu = new Geo.View2D.Control.MapContextMenu();
			return contextMenu;
		}
		/**
		 * 构建地图边界对象
		 * @param left 		西南角经度
		 * @param bottom	西南角纬度
		 * @param right		东北角经度
		 * @param top		东北角纬度
		 */
		map.createBound=function createBound(left, bottom, right, top){
			var bound=new Geo.Bounds(left, bottom, right, top);
			return bound;
		}
		/**
		 * 设置地图视野
		 * @param bound
		 * @param bool true,false
		 */
		map.setMapExtent=function setMapExtent(bound,bool){
			 map.zoomToExtent(bound,bool);
		}
		/**
		 * 线条自适应
		 */
		map.createLineSuitable=function createLineSuitable(){
			var bound=vectorLayer.getExtent();
			map.setMapExtent(bound,false);
		}
		/**
		 * 出现问题
		 */
		map.createSuitable=function createSuitable(){
			var bound=markers.getExtent();
			map.setMapExtent(bound,false);
		}
		/**
		 * 拓展功能  添加图标-------------------------过时方法，请使用drawMarker()
		 * @param lonlat  		经纬度字符串
		 * @param imageIcon  	图标地址
		 * @param size			图标大小
		 */
		map.addMarker = function addMarker(lonlat, imageIcon, size) {
			if (typeof (imageIcon) == 'undefined') {
				imageIcon = "http://115.231.73.253/zhjtapi/images/marker/marker2.png";
				size = 30;
			}
			if (typeof (size) == 'undefined') {
				size = 30;
			}
			var lonlat = new Geo.LonLat(lonlat.split(",")[0], lonlat.split(",")[1]);
			var icon = new Geo.View2D.Icon(imageIcon, new Geo.Size(size, size));
			var marker = new Geo.Marker(lonlat, icon);
			markers.addMarker(marker);
			map.addLayer(markers);
		}
		/**
         * 为要素添加泡泡窗口
         * @param lonlat  经纬度
         * @param imageIcon  图标
         * @param label 标签
         * @param size  大小
         * @param color 颜色
         * @param font  字体大小
         * @param html  文本
         * @param htmlwidth  泡泡窗口宽度
         * @param htmlheight 泡泡窗口高度
         */
        map.addPopupForFeature=function addPopupForFeature(lonlat,imageIcon,label,size,color,font,html,htmlwidth,htmlheight){ 
			polygonFeature=map.addCustomerMarker(lonlat,imageIcon,label,size,color,font);
            //添加要素到图层中    
            var popsize=new Geo.Size(htmlwidth,htmlheight);
            polygonFeature.popupClass=AutoSizeFramedCloud;
            polygonFeature.data.popupSize=popsize;
				polygonFeature.data.popupContentHTML = html;
            vectorLayer.addFeatures([polygonFeature]); 
            //激活控件 
            selectControl.activate(); 
        } 
		/**
		 * 添加自定义标注
		 * @param lonlat	经纬度字符串
		 * @param image		图标地址
		 * @param label		图标文本
		 * @param imageSize	图标大小
		 * @param fontColor	文本颜色
		 * @param fontFamily	文本字体
		 * @returns {Geo.Feature.Vector}
		 */
		map.addCustomerMarker = function addCustomerMarker(lonlat, image, label,
				imageSize, fontColor, fontFamily) {
			var marker = new Geo.Geometry.Point(lonlat.split(",")[0], lonlat
					.split(",")[1]);
			var style = {
				graphic : true,
				externalGraphic : image,
				graphicWidth : imageSize,
				graphicHeight : imageSize,
				graphicOpacity : 1,
				graphicZIndex : 1,
				labelYOffset : 3,
				label : label,
				fontColor : fontColor,
				fontFamily : fontFamily
			};
			var vector = new Geo.Feature.Vector(marker, null, style);
			vectorLayer.addFeatures(vector);
			return vector;
		}
		
		/**
		 * 创建单独浮云框
		 * @param lonlat	经纬度字符串
		 * @param width		浮云框宽度
		 * @param height	浮云框长度
		 * @param contentHTML	浮云内容
		 */
		map.createPopup = function createPopup(lonlat, width, height, contentHTML) {
			//map.setMapCenter(lonlat,null);
			var lonlat = map.createLonlat(lonlat);
			var size = map.createPopupSize(width, height);
			currentPopup = map.createPopupObject(null, lonlat, size, contentHTML,
					null, true);
			var feature = new Geo.Feature(markers, lonlat);
			feature.closeBox = true;
			feature.popupClass = AutoSizeFramedCloud;
			feature.data.popupContentHTML = contentHTML;
			feature.data.overflow = (null) ? "auto" : "hidden";
			feature.data.popupSize = size;
			$$(".olPopup").css("display", "none");
			if (feature.popup == null) {
				feature.popup = feature.createPopup(true);
				map.addPopup(feature.popup);
				feature.popup.show();
			} else {
				feature.popup.toggle();
			}
			
			currentPopup = feature.popup;
		}
		/**
		 * 给marker添加浮云框
		 * @param lonlat	经纬度字符串
		 * @param content	浮云框内容
		 * @param imageIcon	图标地址
		 * @param imageSize	图标大小
		 * @param width		浮云宽度
		 * @param height	浮云高度
		 */
		map.createPopupForMarker = function createPopupForMarker(lonlat, content,
				imageIcon, imageSize, width, height) {
			var lonlat = map.createLonlat(lonlat);
			var size = map.createPopupSize(width, height);
			currentPopup = map.createPopupObject(null, lonlat, size, content, null,
					true);
			var feature = new Geo.Feature(markers, lonlat);
			feature.closeBox = true;
			feature.popupClass = AutoSizeFramedCloud;
			feature.data.popupContentHTML = content;
			feature.data.overflow = (null) ? "auto" : "hidden";
			feature.data.popupSize = size;
			var icon = map.createIcon(imageIcon, imageSize);
			var marker = map.createMarker(lonlat, icon);
			var markerClick = function(evt) {
				$$(".olPopup").css("display", "none");
				if (feature.popup == null) {
					feature.popup = feature.createPopup(true);
					map.addPopup(feature.popup);
					feature.popup.show();
				} else {
					feature.popup.toggle();
				}
				currentPopup = feature.popup;
				Geo.View2D.Event.stop(evt);
			};
			marker.events.register("mousedown", feature, markerClick);
			markers.addMarker(marker);
			map.addLayer(markers);
		}
		/**
		 * 删除所有要素
		 */
		map.removeAll = function removeAll() {
			// 清除标记
			markers.clearMarkers();
			// 清除矢量图层已存在的要素
			vectorLayer.removeFeatures(vectorLayer.features);
		}
		
		/**
		 * 删除所有覆盖物
		 */
		map.delWindow = function delLTinfoWindowOverlay() {
			if (currentPopup) {
				// 清除浮云窗口
				map.removePopup(currentPopup);
			}
			// 清除标记
			markers.clearMarkers();
			// 清除矢量图层已存在的要素
			vectorLayer.removeFeatures(vectorLayer.features);
		}
		/**
		 * 画点
		 * @param lonlat	经纬度
		 */
		map.drawDot = function drawDot(lonlat) {
			var point = new Geo.Geometry.Point(lonlat.split(",")[0], lonlat
					.split(",")[1]);
			var pointFeature = new Geo.Feature.Vector(point);
			vectorLayer.addFeatures(pointFeature);
		}
		/**
		 * 画线
		 * @param lineString	线条坐标串系列
		 * @param color			线条颜色
		 * @param dashstyle		线条虚实
		 * @param strokeWidth   线条粗细
		 * @returns {Geo.Feature.Vector}
		 */
		map.drawLine = function drawLine(lineString, color, dashstyle,strokeWidth) {
			testI = 0;
			
			
			if(typeof(strokeWidth)=='undefined'){
				strokeWidth=3;
			}
			var lineFeature;
			var line = lineString;
			var lonlats = line.split(";");
			var lonlatslength = lonlats.length;
			var xmin = null;
			var ymin = null;
			var xmax = null;
			var ymax = null;
			//console.log("[数组长度]=" + lonlatslength);
			if (lonlatslength > 0) {
				var lineArr = new Array();
				for ( var i = 0; i < lonlatslength; i++) {
					var lonlat = lonlats[i];
					if (lonlat == "") {
						continue;
					}
					var lon = lonlat.split(",")[0];
					var lat = lonlat.split(",")[1];
					if(i == 0){
						ymin = lon;
						ymax = lon;
						xmin = lat;
						xmax = lat;
					
					}
					if(lat < xmin){
						xmin = lat;
					}else if(lat > xmax){
						xmax = lat;
					}
					
					if(lon < ymin){
						ymin = lon;
					}else if(lon > ymax){
						ymax = lon;
					}
					
					
					
					lineArr.push(new Geo.Geometry.Point(lon, lat));
					//setCenter(lon + "," + lat, 14);
				}
				var line1 = new Geo.Geometry.LineString(lineArr);
				var style1 = {
					strokeWidth : strokeWidth,
					strokeOpacity : 1,
					strokeColor : color,
					strokeDashstyle : dashstyle
				};
				lineFeature = new Geo.Feature.Vector(line1, null, style1);
				vectorLayer.addFeatures(lineFeature);
				 //定位视野范围 
                map.zoomToExtent(new Geo.Bounds(ymin, xmin, ymax, xmax), false);   
			}
			return lineFeature;
		}

	/*	map.drawLine = function drawLine(lineString, color, dashstyle,strokeWidth) {
			if(typeof(strokeWidth)=='undefined'){
				strokeWidth=3;
			}
			var lineFeature;
			var line = lineString;
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
					lineArr.push(new Geo.Geometry.Point(lon, lat));
					//setCenter(lon + "," + lat, Cfg.zoom);
				}
				var line1 = new Geo.Geometry.LineString(lineArr);
				var style1 = {
					strokeWidth : strokeWidth,
					strokeOpacity : 1,
					strokeColor : color,
					strokeDashstyle : dashstyle
				};
				lineFeature = new Geo.Feature.Vector(line1, null, style1);
				vectorLayer.addFeatures(lineFeature);
			}
			return lineFeature;
		}*/
		/**
		 * 画圆，针对周边查询的圆查询
		 * @param loc 中心点坐标
		 * @param m 查询范围
		 */
		map.drawRadio = function drawRadio(loc, m, color, dashstyle) {
			var lonlat = new Geo.Geometry.Point(loc.split(",")[0],
					loc.split(",")[1]);
			var polygon = new Geo.Geometry.Polygon.createRegularPolygon(lonlat, m,
					40, 360);
			style = {
				strokeWidth : 2,
				strokeOpacity : 1,
				strokeColor : "#666666",
				fillColor : color,
				fillOpacity : 0.3,
				strokeDashstyle : dashstyle
			}
			var polygonFeature = new Geo.Feature.Vector(polygon, null, style);
			vectorLayer.addFeatures(polygonFeature);
			return polygonFeature;
		}
		/**
		 * 添加多边形控件并开启
		 * @param number  多边形的边数
		 */
		map.addRect=function addRect(number,color){
			polygonstyle.fillColor=color;
			var rect=new Geo.View2D.Control.DrawFeature(polygonStyleLayer,Geo.View2D.Handler.RegularPolygon,{ 
                handlerOptions:{ 
                    sides:number, 
                    irregular:true 
                } 
            });
			map.addControl(rect);
			rect.activate();
			return rect;
		}
		/**
		 * 关闭多边形控件
		 * @param rect  多边形控件
		 */
		map.closeRect=function closeRect(rect){
			rect.deactivate();
		}
		/**
		 * 画多边形
		 * @param lineString  多边形经纬度
		 * @param color   颜色
		 */
		map.drawPolygon=function drawPolygon(lineString,color){
			sketchSymbolizers.Polygon.fillColor=color;
			var lonlat=lineString.split(";");
			var length=lonlat.length;
			var points=new Array();
			for(var i=0;i<length;i++){
				var lon=lonlat[i].split(",")[0];
				var lat=lonlat[i].split(",")[1];
				points.push(new Geo.Geometry.Point(lon, lat));
			}
			var linearRing=new Geo.Geometry.LinearRing(points);
			var polygon=new Geo.Geometry.Polygon([linearRing]);
			var polygonFeature=new Geo.Feature.Vector(polygon);
			vectorLayer.addFeatures([polygonFeature]);
		}
		// 鼠标单击添加图标
		map.clickToAddMarker = function clickToAddMarker(imageIcon) {
			// 定义鼠标单击双击对象
			Geo.View2D.Control.Click = Geo.Class(Geo.View2D.Control,
					{
						defaultHandlerOptions : {
							'single' : true,
							'double' : false,
							'delay' : 0
						},
						initialize : function(options) {
							this.handlerOptions = Geo.Util.extend({},
									this.defaultHandlerOptions);
							Geo.View2D.Control.prototype.initialize.apply(this,
									arguments);
							this.handler = new Geo.View2D.Handler.Click(this, {
								'click' : this.onClick,
								'dblclick' : this.onDblclick
							}, this.handlerOptions);
						},
						onClick : function(evt) {
							var lonlat = $$(".mousePositionCtrl").text();
							var lonlat = new Geo.LonLat(lonlat.split(",")[0],
									lonlat.split(",")[1]);
							var icon = new Geo.View2D.Icon(imageIcon, new Geo.Size(
									30, 30));
							var marker = new Geo.Marker(lonlat, icon);
							markers.addMarker(marker);
							map.addLayer(markers);
							// alert("鼠标点击点的坐标："+$$(".mousePositionCtrl").text());
						}
					});
			// 在map中添加鼠标事件
			single = new Geo.View2D.Control.Click({
				handlerOptions : {
					"single" : true
				}
			});
			map.addControl(single);
			single.activate();
		}
		// 删除单击事件
		map.deleteClick = function deleteClick() {
			single.deactivate();
		}
	}
	if(type==2){
		// 实例化地图对象
		wmap = new WebtAPI.WMap($(mapDiv));
		// 设置地图初始位置
		var lonlat = new OpenLayers.LonLat(120.737967,30.760247);
		//设置地图中心
		wmap.setCenterByLonLat(lonlat,12);
		/********************创建对象*************************/
		//创建转化工具
		wmap.createCoordinateSearch=function createCoordinateSearch(){
			var cooQuery = new WebtAPI.WSearch.CoordinateSearch();
			return cooQuery;
		}
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
				imageIcon="http://115.231.73.253/zhjtapi/images/marker/marker2.png";
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
			var bound = wmap.markersLayer.getDataExtent();
			wmap.zoomToExtent(bound, false);
		}
		//the most suitable size of the line
		wmap.createLineSuitable=function createLineSuitable(){
			var bound=wmap.dynamicVectorLayer.getDataExtent();
		    wmap.zoomToExtent(bound,false);
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
			if(zoom==null||zoom==undefined){
				zoom=map.getZoom();
			}
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
					lineStyle["strokeDashstyle"] = dash;
					var lineFeature = new WebtAPI.LineFeature(lineArr, lineStyle);
					map.dynamicVectorLayer.addFeatures(lineFeature);
				}
			}
			
		}
		/**
		 * 删除所有覆盖物--------------标准方法
		 */
		wmap.removeAllOverlay=function removeAllOverlay(){
			wmap.dynamicVectorLayer.removeAllFeatures();
		}
		/**
		 * 删除所有标注---------------标准方法
		 */
		wmap.removeAllMarker=function removeAllMarker(){
			wmap.markersLayer.clearMarkers();
		}
		/**
		 * 删除指定图标---------------标准方法
		 * @param marker
		 */
		wmap.removeMarker=function removeMarker(marker){
			wmap.markersLayer.removeMarker(marker);
		}
		//删除线--------------------过时方法，建议使用removeAllOverlay()
		wmap.clearLines=function clearLines(){
			wmap.dynamicVectorLayer.removeAllFeatures();
		}
		//删除所有marker
		wmap.clearMarkers=function clearMarkers(){
			wmap.markersLayer.clearMarkers();
		}
		/**
		 * 添加标注
		 * @param lonlat  	经纬度字符串
		 * @param imageIcon 图标地址
		 * @param size		图标大小
		 * @returns
		 */
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
			popup.setOffset(60,60);
			wmap.addPopup(popup,true);
			return popup;
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
			lineStyle["strokeColor"]="blue";
			lineStyle["strokeOpacity"]=1;
			lineStyle["strokeWidth"]=3;
			lineStyle["strokeDashstyle"]="solid";
			lineStyle["fillColor"]=color;
			lineStyle["strokeDashstyle"]=dash;
			wmap.dynamicVectorLayer.addFeatures(new OpenLayers.Feature.Vector(
					ringFeature,null,lineStyle
			));
		} 
		/**
		 * 添加多边形，并且开启
		 * @param number  多边形数量
		 */
		wmap.addRect=function addRect(number){
			polygonControl=new OpenLayers.Control.DrawFeature(wmap.dynamicVectorLayer,
					OpenLayers.Handler.RegularPolygon,{
						handlerOptions:{sides:number,irregular:true}});
					wmap.addControl(polygonControl);
					polygonControl.activate();
			return polygonControl;
		}
		/**
		 * 关闭画多边形控件
		 * @param polygonControl  多边形控件
		 */
		wmap.closeRect=function closeRect(polygonControl){
			polygonControl.deactivate();
		}
		/**
		 * 开启实时路况
		 */
		wmap.openEye=function openEye(){
			map.trafficLayer.setVisibility(true);
		}
		/**
		 * 关闭实时路况
		 */
		wmap.closeEye=function closeEye(){
			map.trafficLayer.setVisibility(false);
		}
		
		/**
		 * 添加面
		 */
		wmap.drawPolygon=function drawPolygon(lineString,color){
			var lonlat=lineString.split(";");
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
		    var feature = new WebtAPI.PolygonFeature(points, style, {code: "110000", id: "xxx"});
		     
		    control = new WebtAPI.WControl.PolygonPainter({callback:{"rightclick":rightCallback}});
		    wmap.addControl(control);
		    control.activate();
		    control.drawPolygon(feature);
		    control.register("click", function(evt, feature){
		        if(feature.hasOwnProperty("id")){
		        	wmap.createPopup(value, feature["id"], 150);
		        }
		    });
		     
		    function rightCallback (evt, feature){
		        if(feature.hasOwnProperty("code")){
		            alert(feature["code"]);
		        }
		    }
		     
		}
	}
	if(type==3){
		maplet=new Maplet(mapDiv);
	    maplet.centerAndZoom(new MPoint(120.75121946334839,30.747786235809325), 8);  
		/**
		 * 创建经纬度对象
		 * @param lonlat  经纬度(经纬度字符串)
		 */
		maplet.createLonlat=function createLonlat(lonlat){
			var object=new MPoint(lonlat.split(",")[0]+","+lonlat.split(",")[1]);
			return object;
		}
		/**
		 * 设置地图中心点坐标及缩放级别
		 * @param lonlat  经纬度（经纬度字符串）
		 * @param zoom
		 */
		maplet.setMapCenter=function setMapCenter(lonlat,zoom){
			var lonlat=maplet.createLonlat(lonlat);
			maplet.centerAndZoom(lonlat,zoom);
		}
		/**
		 * 添加地图控件
		 */
		maplet.addMapControl=function addControl(){
			maplet.addControl(new MStandardControl);
		}
		/**
		 * 设置平移缩放控件的可见性
		 * @param visible  boolean true 可见 false 不可见
		 */
		maplet.setPanZoomBarVisible=function setPanZoomBarVisible(visible){
			maplet.showControl(visible);
		}
		/**
		 * 设置比例尺控件可见性
		 * @param visible true 可见 false 不可见
		 */
		maplet.setScaleVisible=function setScaleVisible(visible){
			maplet.showScale(visible);
		}
		/**
		 * 测距工具
		 */
		maplet.measureTool=function measureTool(){
			map.setMode("measure");
		}
		/**
		 * 侧面工具
		 */
		maplet.lineTool=function lineTool(){
			map.setMode("measarea");
		}
		/**
		 * 设置鹰眼控件可见性及最大化和最小化
		 * @param visible  true 可见 false 不可见
		 * @param state    true 最大化 false 最小化
		 */
		maplet.setOverviewVisible=function setOverviewVisible(visible,state){
			maplet.showOverview(visible,state);
		}
		/**
		 * 在地图上添加折线
		 * @param lineString  坐标系列
		 * @param color       线条颜色
		 * @param dashstyle   线条虚实
		 * @param strokeWidth	线条宽度
		 */
		maplet.drawLine = function drawLine(lineString, color, dashstyle,strokeWidth) {
			if(dashstyle=="solid"){
				dashstyle==0;
			}
			if(dashstyle=="dash"){
				dashstyle==1;
			}
			if(typeof(strokeWidth)=="undefined"){
				strokeWidth=3;
			}
			var polyline;
			var line = lineString;
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
					lineArr.push(new MPoint(lon,lat));
				}
				//创建折线画笔
				var brush=new MBrush(color);
				brush.style=dashstyle;       //显示虚实  1表示虚线  0表示实线
				brush.stroke=strokeWidth;              //线条粗细
				polyline = new MPolyline(lineArr,brush,new MInfoWindow("中山东路","全长---"));
				maplet.addOverlay(polyline);
			}
			return polyline;
		}
		/**
		 * 添加面
		 * @param lineString   面的坐标序列
		 * @param brush        画笔工具
		 */
		maplet.drawPolygon=function drawPolygon(lineString,brush){
			var polygon;
			var line = lineString;
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
					lineArr.push(new MPoint(lon,lat));
				}
				//创建折线画笔
				polygon = new MPolyline(lineArr,brush);
				maplet.addOverlay(polygon);
			}
			return polygon;
		}
		/**
		 * 删除指定要素------------------标准方法
		 * @param feature
		 */
		maplet.removeFeature=function removeFeature(feature){
			maplet.removeOverlay(feature);
		}
		/**
		 * 删除所有的覆盖物----------------标准方法
		 */
		maplet.removeAllOverlay=function removeAllOverlay(){
			maplet.clearOverlays(true);
		}
		/**
		 * 删除指定添加层----------------该方法过时，建议使用removeFeature(feature)
		 * @param lay 叠加层对象
		 */
		maplet.removeLay=function removeLay(lay){
			maplet.removeOverlay(lay);
		}
		/**
		 * 删除指定折线
		 * @param polyline  折线对象-----------该方法过时，建议使用removeFeature(feature)
		 */
		maplet.removeLine=function removeLine(polyline){
			maplet.removeOverlay(polyline);
		}
		/**
		 * 创建图标对象
		 * @param icon  图标图片
		 * @param size  图标大小
		 * @returns {MIcon}  图标对象
		 */
		maplet.createIcon=function createIcon(icon,size){
			var icon=new MIcon(icon,size,size);
			return icon;
		}
		/**
		 * 创建标注对象
		 * @param lonlat  经纬度对象
		 * @param icon    icon对象
		 * @returns {MMarker}  标注对象
		 */
		maplet.createMarker=function createMarker(lonlat,icon){
			var marker=new MMarker(lonlat,icon);
			return marker;
		}
		/**
		 * 在地图上添加标注对象
		 *@param marker 标注对象
		 * @returns {MMarker}  标注对象
		 */
		maplet.addMarkerObject=function addMarkerObject(marker){
			maplet.addOverlay(marker);
		}
		/**
		 * 添加图标
		 * @param lonlat	经纬度字符串
		 * @param icon		图标地址
		 * @param size		图标大小
		 * @returns
		 */
		maplet.drawMarker=function drawMarker(lonlat,icon,size){
			var lonlat=maplet.createLonlat(lonlat);
			var icon=maplet.createIcon(icon, size);
			var marker=maplet.createMarker(lonlat, icon);
			maplet.drawMarker(marker);
			return marker;
		}
		/**
		 * 添加图标		注意，该方法过时，请使用drawMarker()
		 * @param lonlat
		 * @param icon
		 * @param size
		 * @returns
		 */
		maplet.addMarker=function addMarker(lonlat,icon,size){
			var lonlat=maplet.createLonlat(lonlat);
			var icon=maplet.createIcon(icon, size);
			var marker=maplet.createMarker(lonlat, icon);
			maplet.drawMarker(marker);
			return marker;
		}
		/**
		 * 创建画笔
		 * @param color  边框颜色
		 * @param dash   边框虚实
		 * @param stroke 边框粗细
		 * @param bgcolor 填充颜色
		 * @param isfill  是否填充
		 * @returns {___brush1}
		 */
		maplet.createBrush=function createBrush(color,dash,stroke,bgcolor,isfill){
			var brush=new MBrush(color);
			brush.style=dash;       
			brush.stroke=stroke; 
			brush.bgcolor =bgcolor;  
			brush.fill = isfill; 
			return brush;
		}
		/**
		 * 添加矩形对象
		 * @param lonlat1  矩形左上角坐标
		 * @param lonlat2  矩形右下角坐标
		 * @param arcsize  矩形边的弧度  0-0.5
		 * @param brush    画笔对象
		 */
		maplet.drawRect=function drawRect(lonlat1,lonlat2,arcsize,brush){
			var minpt=maplet.createLonlat(lonlat1);
			var maxpt=maplet.createLonlat(lonlat2);
			var rect=new MRoundRect(minpt,maxpt,arcsize,brush);
			maplet.addOverlay(rect);
			return rect;
		}
		
		maplet.createInfoWindow=function createInfoWindow(){
			
		}
		/**
		 * popup框所属的编号
		 * @param index  编号 int
		 */
		maplet.createPopup=function createPopup(index){
			maplet.getMarkers()[index].openInfoWindow();
		}
		/**
		 * 给图标添加泡泡窗
		 * @param lonlat
		 * @param content
		 * @param imageIcon
		 * @param imageSize
		 * @param width
		 * @param height
		 */
		maplet.createPopupForMarker=function createPopupForMarker(lonlat, content,imageIcon, imageSize, width, height){
			var lonlat=maplet.createLonlat(lonlat);
			var icon=maplet.createIcon(imageIcon, imageSize);
			var infoWin=new MInfoWindow(content);
			var marker=new MMarker(lonlat,icon,infoWin);
			maplet.addOverlay(marker);
			maplet.setIwStdSize(width,height);
		}
		/**
		 * 添加右键菜单
		 * @param arr  右键菜单中包含的内容  数组格式
		 */
		maplet.addContextMenu=function addContextMenu(arr){
			menu=new MContextMenu();
			map.setContextMenu(menu);
			for(var i=0;i<arr.length;i++){
				menu.addItem(new MContextMenuItem(arr[i]));
			}
			return menu;
		}
		/**
		 * 清空菜单项集合
		 * @param menu  菜单项对象
		 */
		maplet.removeMenu=function removeMenu(){
			maplet.removeContextMenu();
		}
		/**
		 * 
		 * @param lonlat  中心点经纬度
		 * @param radio   圆形半径
		 * @param brush   画笔工具
		 */
		maplet.drawRadio=function drawRadio(lonlat,m,brush){
			var lonlat=maplet.createLonlat(lonlat);
			var radio=new MEllipse(lonlat,m,m,brush);
			maplet.addOverlay(radio);
			return radio;
		}
	}
	if(type==1||typeof(type)=='undefined'){
		return map;
	}
	if(type==2){
		return wmap;
	}
	if(type==3){
		return maplet;
	}
}
// 加载图层组到地图
function switchGroup(group) {
	if (currentGroup != group) {
		map.loadLayerGroup(group);
		currentGroup = group;
	}
}
// 放大地图
function zoomIn1() {
	map.zoomIn();
}
// 缩小地图
function zoomOut() {
	map.zoomOut();
}
// 设置中心点及缩放级别
function setCenter(lonlat, zoom) {
	var point = new Geo.LonLat(lonlat.split(",")[0], lonlat.split(",")[1]);
	map.setCenter(point, zoom);
}
function getCenter() {
	var center=map.getCenter();
	 return center.lon+","+center.lat;
}
function getBound() {
	var bound = map.getExtent();
	var left = bound.left.toFixed(3);
	var right = bound.right.toFixed(3);
	var top = bound.top.toFixed(3);
	var bottom = bound.bottom.toFixed(3);
	alert(left + "--" + right + "--" + top + "--" + bottom);
}

// 添加起终点标记
function addSEMarker(type, lonlat) {
	if (type.indexOf("start") != -1) {
		if (markerS != null) {
			markers.removeMarker(markerS);
		}
		var lonlat = new Geo.LonLat(lonlat.split(",")[0], lonlat.split(",")[1]);
		var icon = new Geo.View2D.Icon(aMarker[0], new Geo.Size(30, 30));
		markerS = new Geo.Marker(lonlat, icon);
		markers.addMarker(markerS);
		map.addLayer(markers);
	}
	if (type.indexOf("end") != -1) {
		if (markerE != null) {
			markers.removeMarker(markerE);
		}
		var lonlat = new Geo.LonLat(lonlat.split(",")[0], lonlat.split(",")[1]);
		var icon = new Geo.View2D.Icon(aMarker[4], new Geo.Size(30, 30));
		markerE = new Geo.Marker(lonlat, icon);
		markers.addMarker(markerE);
		map.addLayer(markers);
	}
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

//选择要素时弹出浮云窗口 
        function onFeatureSelect(feature){    
            selectedFeature = feature;    
            currentPopup = new Geo.View2D.Popup.FramedCloud("chicken", feature.geometry.getBounds().getCenterLonLat(), feature.data.popupSize, feature.data.popupContentHTML, null, true, onPopupClose);    
            feature.popup = currentPopup;    
            map.addPopup(currentPopup); 
            LeftCssBySelf(feature.style.label);//自己添加部分
        }    
        //未选择要素函数    
        function onFeatureUnselect(feature){    
            map.removePopup(feature.popup);    
            feature.popup.destroy();    
            feature.popup = null;    
        } 
        //浮云关闭    
        function onPopupClose(evt){    
            selectControl.unselect(selectedFeature);    
        } 