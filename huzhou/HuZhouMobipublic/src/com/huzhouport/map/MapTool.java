package com.huzhouport.map;

import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.Geometry;
import com.baidu.mapapi.map.Graphic;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.Ground;
import com.baidu.mapapi.map.GroundOverlay;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MKOfflineMapListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.map.Symbol.Color;
import com.baidu.mapapi.map.Symbol.Stroke;
import com.baidu.mapapi.map.TextItem;
import com.baidu.mapapi.map.TextOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 版本1.3
 * 
 * @author Administrator: Aaron 周
 * @time 2014-3-13
 * 
 */
public class MapTool
{
	/**
	 * 构造函数
	 * 
	 * @param c
	 *            全局context
	 * @param a
	 *            全局application
	 */
	public MapTool(Application a)
	{
		currentContext = a.getApplicationContext();
		currentApplication = a;
		iniMapManager();
	}

	
	
	/**
	 * mapTool类所需的变量
	 */
	public Context			currentContext;		// 上下文
	public static BMapManager		mapManager;			// 地图管理器
	private Application		currentApplication;	// 当前application对象
	public MapLocation		mapLocation;			// 地图定位
	private Activity		currentActivity;		// 当前activity对象
	private MapView			currentMapView;		// 地图
	private MapController	currentMapController;	// 地图控制器
	private MKSearch		mSearch;				// 搜索
	public MapConfig		mapConfig;				// 配置

	public ListView			listView;
	//public OfflineMap       offlineMap;

	/**
	 * 初始化地图管理器，包括key的认证，必须在设置布局之前完成
	 * 
	 */
	public void iniMapManager()
	{
		if (mapManager == null)
		{
			mapManager = new BMapManager(currentApplication);
			
			if (!mapManager.init(BaseMapData.sKey, generalListener))
			{
//				showToast(BaseMapData.WRONG_INI_MANAGER);
			}
		}
		else if (mapManager == null && currentContext == null)
		{
			//
		}

	}

	public void iniLocation()
	{
		mapLocation = new MapLocation(currentContext, null);
	}
	
	/**
	 * 初始化地图
	 * 
	 * @param map
	 *            地图对象
	 * @param activity
	 *            地图对象所在activity
	 */
	public void iniMapView(MapView map,Activity activity)
	{
		mapConfig = new MapConfig();

		currentMapView = map;
		currentMapController = currentMapView.getController();

		currentMapController.enableClick(mapConfig.getbEnableClick());
		currentMapController.setZoom(mapConfig.getfZoomSize());
		
		mSearch = new MKSearch();
		mSearch.init(mapManager, mSearchListener);
		
		//定位到默认位置
		locationDefault();
//		currentMapController.setCenter(mapConfig.getGeoCenterPoint());

		currentMapView.setBuiltInZoomControls(mapConfig.getbZoomControl());
		currentMapView.regMapViewListener(mapManager, currentMapViewListener);

		

		currentActivity = activity;

		mapLocation = new MapLocation(currentContext, currentMapView);
	}
	
	/**
	 * 显示toast信息
	 * 
	 * @param data
	 *            ：要显示的信息
	 */
	public void showToast(String data)
	{
		Toast.makeText(currentContext, data, Toast.LENGTH_LONG).show();
	}

	/**
	 * 显示toast信息
	 * 
	 * @param data
	 *            ：要显示的信息
	 * @param context
	 *            ：要显示的context
	 */
	public void showToast(String data,Context context)
	{
		Toast.makeText(context, data, Toast.LENGTH_LONG).show();
	}

	public MapView getcurrentMapView()
	{
		return currentMapView;
	}

	public void setcurrentMapView(MapView map)
	{
		currentMapView = map;
	}

	public Context getcurrentContext()
	{
		return currentContext;
	}

	public void setcurrentContext(Context context)
	{
		currentContext = context;
	}

	/**
	 * 初始化地图管理器的监听函数
	 */
	private MKGeneralListener	generalListener			= new MKGeneralListener()
														{
															@Override
															public void onGetNetworkState(
																	int iError)
															{
																if (iError == MKEvent.ERROR_NETWORK_CONNECT)
																{
																//	showToast(BaseMapData.WRONG_NET_LINK);
																}
																else if (iError == MKEvent.ERROR_NETWORK_DATA)
																{
																//	showToast(BaseMapData.WRONG_NET_DATA);
																}
															}

															@Override
															public void onGetPermissionState(
																	int iError)
															{
																if (iError != 0)
																{
																	showToast(BaseMapData.WRONG_KEY_PERMISSION);
																	 mapConfig.setbKeyRight(false);
																}
																else
																{
																//	showToast(BaseMapData.RIGHT_KEY_PERMISSION);
																	// mapConfig.setbKeyRight(true);
																}
															}
														};

	/**
	 * 操作地图监听事件，包括移动、加载、点击事件等
	 */
	private MKMapViewListener	currentMapViewListener	= new MKMapViewListener()
														{

															@Override
															public void onMapMoveFinish()
															{
																// TODO
																// Auto-generated
																// method stub

															}

															@Override
															public void onMapLoadFinish()
															{
																// TODO
																// Auto-generated
																// method stub

															}

															@Override
															public void onMapAnimationFinish()
															{
																// TODO
																// Auto-generated
																// method stub

															}

															@Override
															public void onGetCurrentMap(
																	Bitmap arg0)
															{
																// TODO
																// Auto-generated
																// method stub

															}

															@Override
															public void onClickMapPoi(
																	MapPoi arg0)
															{
																// TODO
																// Auto-generated
																// method stub

															}
														};

	/**
	 * 用默认参数设置地图背景图层
	 */
	public void setMapLayout()
	{
		currentMapView.setSatellite(mapConfig.getbSatellite());
		currentMapView.setTraffic(mapConfig.getbTraffic());
	}

	/**
	 * 设置地图背景图层
	 * 
	 * @param satellite
	 *            ：是否是卫星图
	 * @param traffic
	 *            ：是否是交通图
	 */
	public void setMapLayout(boolean satellite,boolean traffic)
	{
		currentMapView.setSatellite(satellite);
		currentMapView.setTraffic(traffic);
	}

	public void setCommonLayout()
	{
		currentMapView.setSatellite(false);
	}

	/**
	 * 设置卫星图
	 */
	public void setSatelliteLayout()
	{
		currentMapView.setSatellite(true);
	}

	/**
	 * 设置交通图
	 */
	public void setTraffic()
	{
		currentMapView.setTraffic(true);
	}

	/**
	 * 搜索监听函数，包括交通、线路、兴趣点等
	 */
	private MKSearchListener	mSearchListener	= new MKSearchListener()
												{

													@Override
													public void onGetWalkingRouteResult(
															MKWalkingRouteResult result,
															int error)
													{
														if (error != 0
																|| result == null)
														{
															showToast(BaseMapData.WRONG_FIND_WALKING);
															return;
														}

														RouteOverlay routeOverlay = new RouteOverlay(
																currentActivity,
																currentMapView);
														routeOverlay
																.setData(result
																		.getPlan(
																				0)
																		.getRoute(
																				0));

														currentMapView
																.getOverlays()
																.clear();
														currentMapView
																.getOverlays()
																.add(routeOverlay);
														currentMapView
																.refresh();
													}

													@Override
													public void onGetTransitRouteResult(
															MKTransitRouteResult result,
															int error)
													{
														if (error != 0
																|| result == null)
														{
															showToast(BaseMapData.WRONG_FIND_TRANSIT);
															return;
														}

														TransitOverlay transitOverlay = new TransitOverlay(
																currentActivity,
																currentMapView);
														transitOverlay
																.setData(result
																		.getPlan(0));

														currentMapView
																.getOverlays()
																.clear();
														currentMapView
																.getOverlays()
																.add(transitOverlay);
														currentMapView
																.refresh();

													}

													@Override
													public void onGetSuggestionResult(
															MKSuggestionResult result,
															int error)
													{
														if (error != 0
																|| result == null)
														{
															showToast(BaseMapData.WRONG_FIND_SUGGESSION);
															return;
														}

														if (listView != null)
														{
															int iSize = result
																	.getSuggestionNum();
															String[] suggestionStrings = new String[iSize];

															for (int i = 0; i < iSize; i++)
															{
																suggestionStrings[i] = result
																		.getSuggestion(i).city
																		+ ":"
																		+ result.getSuggestion(i).key;
															}

															ArrayAdapter<String> suggestionString = new ArrayAdapter<String>(
																	currentActivity,
																	android.R.layout.simple_list_item_1,
																	suggestionStrings);
															listView.setAdapter(suggestionString);

															listView = null;
														}

													}

													@Override
													public void onGetShareUrlResult(
															MKShareUrlResult arg0,
															int arg1,int arg2)
													{
														// TODO Auto-generated
														// method stub

													}

													@Override
													public void onGetPoiResult(
															MKPoiResult result,
															int type,int error)
													{
														if (error == MKEvent.ERROR_RESULT_NOT_FOUND)
														{
															showToast(BaseMapData.NO_FIND_POI);
															return;
														}
														else if (error != 0
																|| result == null)
														{
															showToast(BaseMapData.WRONG_FIND_POI);
															return;
														}

														PoiOverlay poiOverlay = new PoiOverlay(
																currentActivity,
																currentMapView);
														poiOverlay
																.setData(result
																		.getAllPoi());
														currentMapView
																.getOverlays()
																.clear();
														currentMapView
																.getOverlays()
																.add(poiOverlay);
														currentMapView
																.refresh();

														for (MKPoiInfo info : result
																.getAllPoi())
														{
															if (info.pt != null)
															{
																currentMapView
																		.getController()
																		.animateTo(
																				info.pt);
																break;
															}
															// 交通
															if (info.ePoiType == 2)
															{
																mSearch.busLineSearch(
																		info.city,
																		info.uid);
																break;
															}
														}
													}

													@Override
													public void onGetPoiDetailSearchResult(
															int arg0,int arg1)
													{
														// TODO Auto-generated
														// method stub

													}

													@Override
													public void onGetDrivingRouteResult(
															MKDrivingRouteResult result,
															int error)
													{
														if (error != 0
																|| result == null)
														{
															showToast(BaseMapData.WRONG_FIND_DRIVER);
															return;
														}

														RouteOverlay routeOverlay = new RouteOverlay(
																currentActivity,
																currentMapView);
														routeOverlay
																.setData(result
																		.getPlan(
																				0)
																		.getRoute(
																				0));

														currentMapView
																.getOverlays()
																.clear();
														currentMapView
																.getOverlays()
																.add(routeOverlay);
														currentMapView
																.refresh();
													}

													@Override
													public void onGetBusDetailResult(
															MKBusLineResult result,
															int error)
													{
														if (error != 0
																|| result == null)
														{
															showToast(BaseMapData.WRONG_FIND_BUS);
															return;
														}

														RouteOverlay routeOverlay = new RouteOverlay(
																currentActivity,
																currentMapView);
														routeOverlay
																.setData(result
																		.getBusRoute());

														currentMapView
																.getOverlays()
																.clear();
														currentMapView
																.getOverlays()
																.add(routeOverlay);
														currentMapView
																.refresh();
														currentMapController
																.animateTo(result
																		.getBusRoute()
																		.getStart());
													}

													@Override
													public void onGetAddrResult(
															MKAddrInfo result,
															int error)
													{
														if (error != 0)
														{
															showToast(BaseMapData.WRONG_FIND_ADDRESS);
															return;
														}

														currentMapController
																.animateTo(result.geoPt);

														if (result.type == MKAddrInfo.MK_GEOCODE)
														{
														//	showToast(result.geoPt
														//			.toString());
															currentMapController.setCenter(result.geoPt);
															currentMapController.animateTo(result.geoPt);
														}
														else if (result.type == MKAddrInfo.MK_REVERSEGEOCODE)
														{
														//	showToast(result.strAddr);
															currentMapController.setCenter(result.geoPt);
															currentMapController.animateTo(result.geoPt);
														}
													}
												};

	/**
	 * 在指定范围内搜索内容
	 * 
	 * @param searchContent
	 *            ：搜索内容
	 * @param leftTop
	 *            ：左上点坐标
	 * @param rightDown
	 *            ：右下点坐标
	 */
	public void searchInZoom(String searchContent,GeoPoint leftTop,
			GeoPoint rightDown)
	{
		mSearch.poiSearchInbounds(searchContent, leftTop, rightDown);
	}

	/**
	 * 在指定城市内搜索
	 * 
	 * @param city
	 *            ：搜索的城市
	 * @param content
	 *            ：搜索内容
	 */
	public void searchInCity(String city,String content)
	{
		mSearch.poiSearchInCity(city, content);
	}

	/**
	 * 在附近范围搜索
	 * 
	 * @param content
	 *            ：搜索内容
	 * @param cur
	 *            ：搜索中心点
	 * @param radius
	 *            ：搜索半径
	 */
	public void searchInNearBy(String content,GeoPoint cur,int radius)
	{
		mSearch.poiSearchNearBy(content, cur, radius);
	}

	/**
	 * 通过坐标解析地址
	 * 
	 * @param point
	 *            ：坐标点
	 */
	public void searchReverseGeocode(GeoPoint point)
	{
		mSearch.reverseGeocode(point);
	}

	/**
	 * 通过坐标解析地址
	 * 
	 * @param latitude
	 *            ：纬度
	 * @param longtitude
	 *            ：经度
	 */
	public void searchReverseGeocode(int latitude,int longtitude)
	{
		mSearch.reverseGeocode(new GeoPoint(latitude, longtitude));
	}

	/**
	 * 通过名称获取经纬度
	 * 
	 * @param key
	 *            ：地址名称
	 * @param city
	 *            ：地址所在城市
	 */
	public void searchGeocode(String key,String city)
	{
		mSearch.geocode(key, city);
	}

	/**
	 * 搜索内容（此函数待定，应与listview连接）
	 * 
	 * @param key
	 *            ：要搜索的内容
	 * @param city
	 *            ：搜索的城市
	 * @param listView
	 *            ：返回数据显示对象
	 */
	public void searchSuggest(String key,String city,ListView l)
	{
		listView = l;
		mSearch.suggestionSearch(key, city);
	}

	/**
	 * 自驾线路搜索
	 * 
	 * @param start
	 *            ：开始坐标地址
	 * @param end
	 *            ：结束坐标地址
	 */
	public void searchDriver(GeoPoint start,GeoPoint end)
	{
		MKPlanNode first = new MKPlanNode();
		first.pt = start;
		MKPlanNode last = new MKPlanNode();
		last.pt = end;

		mSearch.setDrivingPolicy(mapConfig.getiDriverPolicy());
		mSearch.drivingSearch(null, first, null, last);
	}

	/**
	 * 自驾线路搜索
	 * 
	 * @param startCity
	 *            ：开始城市
	 * @param startName
	 *            ：开始点名称
	 * @param endCity
	 *            ：结束城市
	 * @param endName
	 *            ：结束点名称
	 */
	public void searchDriver(String startCity,String startName,String endCity,
			String endName)
	{
		MKPlanNode first = new MKPlanNode();
		first.name = startName;

		MKPlanNode last = new MKPlanNode();
		last.name = endName;

		mSearch.setDrivingPolicy(mapConfig.getiDriverPolicy());
		mSearch.drivingSearch(startCity, first, endCity, last);
	}

	/**
	 * 步行线路搜索
	 * 
	 * @param start
	 *            ：开始坐标点
	 * @param end
	 *            ：结束坐标点
	 */
	public void searchWalking(GeoPoint start,GeoPoint end)
	{
		MKPlanNode first = new MKPlanNode();
		first.pt = start;
		MKPlanNode last = new MKPlanNode();
		last.pt = end;

		mSearch.setDrivingPolicy(mapConfig.getiDriverPolicy());
		mSearch.walkingSearch(null, first, null, last);
	}

	/**
	 * 步行线路搜索
	 * 
	 * @param startCity
	 *            ：开始城市
	 * @param startName
	 *            ：开始点
	 * @param endCity
	 *            ：结束城市
	 * @param endName
	 *            ：结束点
	 */
	public void searchWalking(String startCity,String startName,String endCity,
			String endName)
	{
		MKPlanNode first = new MKPlanNode();
		first.name = startName;

		MKPlanNode last = new MKPlanNode();
		last.name = endName;

		mSearch.setDrivingPolicy(mapConfig.getiDriverPolicy());
		mSearch.walkingSearch(startCity, first, endCity, last);
	}

	/**
	 * 公交线路信息查询
	 * 
	 * @param city
	 *            ：搜索城市
	 * @param path
	 *            ：公交线路名称
	 */
	public void searchBus(String city,String path)
	{
		mSearch.poiSearchInCity(city, path);
	}

	/**
	 * 公交线路搜索
	 * 
	 * @param city
	 *            ：所在城市
	 * @param start
	 *            ：开始坐标
	 * @param end
	 *            ：结束坐标
	 */
	public void searchBus(String city,GeoPoint start,GeoPoint end)
	{
		MKPlanNode first = new MKPlanNode();
		first.pt = start;
		MKPlanNode last = new MKPlanNode();
		last.pt = end;

		mSearch.setTransitPolicy(mapConfig.getiBusPolicy());
		mSearch.transitSearch(city, first, last);
	}

	/**
	 * 公交线路查询
	 * 
	 * @param city
	 *            ：所在城市
	 * @param start
	 *            ：开始地点
	 * @param end
	 *            ：结束地点
	 */
	public void searchBus(String city,String start,String end)
	{
		MKPlanNode first = new MKPlanNode();
		first.name = start;
		MKPlanNode last = new MKPlanNode();
		last.name = end;

		mSearch.setTransitPolicy(mapConfig.getiBusPolicy());
		mSearch.transitSearch(city, first, last);
	}

	/**
	 * 定位（测试用）
	 */
	public void locationDefault()
	{
		searchGeocode(BaseMapData.POINT_DEFAULT, BaseMapData.CITY_DEFAULT);
	}

	/**
	 * 当前位置定位一次
	 */
	public void locationOnce()
	{
		mapLocation.setLocationOption();
		mapLocation.locationStart(true);
		mapLocation.sendRequest(1);
	}

	/**
	 * 定时定位
	 * 
	 * @param time
	 */
	public void location(int time)
	{
		mapLocation.locationConfig.setiTimeSpan(time);
		mapLocation.setLocationOption();
		mapLocation.locationStart(true);
		mapLocation.sendRequest(1);

	}

	/**
	 * 取消定时定位
	 */
	public void cancelLocation()
	{
		mapLocation.removeListener();
		mapLocation.locationStart(false);
	}
	
	public void cancelLocation(BDLocationListener bdLocationListener)
	{
		mapLocation.removeListener(bdLocationListener);
		mapLocation.locationStart(false);
	}

	/**
	 * 弹出图层设置
	 * 
	 * @param bitmaps
	 *            左中右三张图
	 * @param point
	 *            位置点
	 */
	public void setPopupLayout(Bitmap[] bitmaps,GeoPoint point,int yOffset)
	{
		PopupOverlay popupOverlay = new PopupOverlay(currentMapView,
				popupClickListener);
		popupOverlay.showPopup(bitmaps, point, yOffset);

	}

	PopupClickListener	popupClickListener	= new PopupClickListener()
											{

												@Override
												public void onClickedPopup(
														int index)
												{

												}
											};

	/**
	 * 方形样式（面样式）
	 * 
	 * @param red
	 *            ：填充颜色中红色分量
	 * @param green
	 *            ：填充颜色中绿色分量
	 * @param blue
	 *            ：填充颜色中蓝色分量
	 * @param alpha
	 *            ：填充颜色的透明度
	 * @param index
	 *            ：边框线粗细
	 * @param lineRed
	 *            ：边框线红色
	 * @param lineGreen
	 *            ：边框线绿色
	 * @param lineBlue
	 *            ：边框线蓝色
	 * @param lineAlpha
	 *            ：边框线透明度
	 * @param fill
	 *            ：是否填充0否，1是
	 * @return 返回样式
	 */
	public Symbol setRectSymbol(int red,int green,int blue,int alpha,int index,
			int lineRed,int lineGreen,int lineBlue,int lineAlpha,int fill)
	{
		Symbol symbol = new Symbol();

		Color color = symbol.new Color(alpha, red, green, blue);
		Color lineColor = symbol.new Color(lineAlpha, lineRed, lineGreen,
				lineBlue);
		Stroke stroke = new Stroke(index, lineColor);

		symbol.setSurface(color, fill, 3, stroke);

		return symbol;
	}

	/**
	 * 画方形
	 * 
	 * @param leftTop
	 *            ：左上点坐标
	 * @param rightDown
	 *            ：右下点坐标
	 * @param symbol
	 *            ：样式
	 */
	public void drawRect(GeoPoint leftTop,GeoPoint rightDown,Symbol symbol)
	{
		Geometry geometry = new Geometry();
		geometry.setEnvelope(leftTop, rightDown);

		Graphic graphic = new Graphic(geometry, symbol);

		drawOverlay(graphic);
	}

	/**
	 * 默认样式绘制方形
	 * 
	 * @param leftTop
	 *            ：左上点坐标
	 * @param rightDown
	 *            ：右下点坐标
	 */
	public void drawRect(GeoPoint leftTop,GeoPoint rightDown)
	{
		Symbol symbol = mapConfig.getrectSymbol();

		Geometry geometry = new Geometry();
		geometry.setEnvelope(leftTop, rightDown);

		Graphic graphic = new Graphic(geometry, symbol);

		drawOverlay(graphic);
	}

	/**
	 * 
	 * 点样式
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 * @return 返回点样式
	 */
	public Symbol setPointSymbol(int red,int green,int blue,int alpha)
	{
		Symbol symbol = new Symbol();

		Symbol.Color color = symbol.new Color(alpha, red, green, blue);

		symbol.setPointSymbol(color);
		return symbol;
	}

	/**
	 * 画点
	 * 
	 * @param point
	 *            ：点位置
	 * @param pixel
	 *            ：点粗细
	 * @param symbol
	 *            ：点样式
	 */
	public void drawPoint(GeoPoint point,int pixel,Symbol symbol)
	{
		Geometry geometry = new Geometry();
		geometry.setPoint(point, pixel);

		Graphic graphic = new Graphic(geometry, symbol);

		drawOverlay(graphic);
	}

	/**
	 * 
	 * 线样式
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 * @param width
	 *            ：线宽度
	 * @return
	 */
	public Symbol setLineSymbol(int red,int green,int blue,int alpha,int width)
	{
		Symbol symbol = new Symbol();

		Symbol.Color color = symbol.new Color(alpha, red, green, blue);

		symbol.setLineSymbol(color, width);
		return symbol;
	}

	/**
	 * 默认样式画点
	 * 
	 * @param point
	 *            ：点位置
	 * @param pixel
	 *            ：点大小
	 */
	public void drawPoint(GeoPoint point,int pixel)
	{
		Symbol symbol = mapConfig.getpointSymbol();

		Geometry geometry = new Geometry();
		geometry.setPoint(point, pixel);

		Graphic graphic = new Graphic(geometry, symbol);

		drawOverlay(graphic);
	}

	/**
	 * 默认样式画点
	 * 
	 * @param point
	 *            ：点坐标
	 */
	public void drawPoint(GeoPoint point)
	{
		Symbol symbol = mapConfig.getpointSymbol();

		Geometry geometry = new Geometry();
		geometry.setPoint(point, mapConfig.getiPointThickness());

		Graphic graphic = new Graphic(geometry, symbol);

		drawOverlay(graphic);
	}

	/**
	 * 画多点
	 * 
	 * @param points
	 *            坐标点组
	 */
	public void drawMutiPoint(GeoPoint[] points)
	{
		Symbol symbol = mapConfig.getpointSymbol();

		for (int i = 0; i < points.length; i++)
		{
			Geometry geometry = new Geometry();
			geometry.setPoint(points[i], mapConfig.getiPointThickness());

			Graphic graphic = new Graphic(geometry, symbol);

			drawOverlay(graphic, false);
		}
	}

	/**
	 * 画线
	 * 
	 * @param geoPoint
	 *            ：连线的坐标点组
	 * @param symbol
	 *            ：线样式
	 */
	public void drawLine(GeoPoint[] geoPoint,Symbol symbol)
	{
		Geometry geometry = new Geometry();
		geometry.setPolyLine(geoPoint);

		Graphic graphic = new Graphic(geometry, symbol);
		drawOverlay(graphic);
	}

	/**
	 * 默认样式划线
	 * 
	 * @param geoPoint
	 *            ：坐标点组
	 */
	public void drawLine(GeoPoint[] geoPoint)
	{
		Symbol symbol = mapConfig.getlineSymbol();
		Geometry geometry = new Geometry();
		geometry.setPolyLine(geoPoint);

		Graphic graphic = new Graphic(geometry, symbol);
		drawOverlay(graphic);
	}

	/**
	 * 
	 * 画多边形
	 * 
	 * @param geoPoint
	 *            ：坐标点组
	 * @param symbol
	 *            ：多边形样式
	 */
	public void drawPolygon(GeoPoint[] geoPoint,Symbol symbol)
	{
		Geometry geometry = new Geometry();
		geometry.setPolygon(geoPoint);

		Graphic graphic = new Graphic(geometry, symbol);
		drawOverlay(graphic);
	}

	/**
	 * 默认样式画多边形
	 * 
	 * @param geoPoint
	 *            ：坐标点组
	 */
	public void drawPolygon(GeoPoint[] geoPoint)
	{
		Symbol symbol = mapConfig.getrectSymbol();
		Geometry geometry = new Geometry();
		geometry.setPolygon(geoPoint);

		Graphic graphic = new Graphic(geometry, symbol);
		drawOverlay(graphic);
	}

	/**
	 * 绘制多边形图层
	 * 
	 * @param graphic
	 *            ：图层数据
	 */
	public void drawOverlay(Graphic graphic)
	{
		GraphicsOverlay graphicsOverlay = new GraphicsOverlay(currentMapView);
		graphicsOverlay.setData(graphic);

		currentMapView.getOverlays().clear();
		currentMapView.getOverlays().add(graphicsOverlay);
		currentMapView.refresh();
	}

	public void drawOverlay(Graphic graphic,boolean clearOrigin)
	{
		GraphicsOverlay graphicsOverlay = new GraphicsOverlay(currentMapView);
		graphicsOverlay.setData(graphic);

		if (clearOrigin)
			currentMapView.getOverlays().clear();
		currentMapView.getOverlays().add(graphicsOverlay);
		currentMapView.refresh();
	}

	/**
	 * 移动到指定点
	 * 
	 * @param geoPoint
	 *            ：目的坐标
	 */
	public void moveTo(GeoPoint geoPoint)
	{
		currentMapController.animateTo(geoPoint);
	}

	/**
	 * 将指定点移到当前地图中心
	 * 
	 * @param geoPoint
	 *            ：地图中心点坐标
	 */
	public void setCenter(GeoPoint geoPoint)
	{
		currentMapController.setCenter(geoPoint);
	}

	/**
	 * 通过两个int值，获取经纬度对象
	 * 
	 * @param latitude
	 *            ：纬度
	 * @param longtitude
	 *            ：经度
	 * @return 返回GeoPoint对象
	 */
	public GeoPoint getGeoPoint(int latitude,int longtitude)
	{
		return new GeoPoint(latitude, longtitude);
	}

	/**
	 * 通过两个double值，返回经纬度对象
	 * 
	 * @param latitude
	 *            ：纬度
	 * @param longtitude
	 *            ：经度
	 * @return 返回GeoPoint对象
	 */
	public GeoPoint getGeoPoint(double latitude,double longtitude)
	{
		return new GeoPoint(((int) (latitude * 1e6)),
				((int) (longtitude * 1e6)));
	}

	/**
	 * 返回两点间距离
	 * 
	 * @param point1
	 *            ：点1
	 * @param point2
	 *            ：点2
	 * @return 距离
	 */
	public double GetLength(GeoPoint point1,GeoPoint point2)
	{
		return DistanceUtil.getDistance(point1, point2);
	}

	/**
	 * 清除所有图层
	 */
	public void clearAllLayout()
	{
		currentMapView.getOverlays().clear();
	}

	/**
	 * 设置并获取获取文字颜色
	 * 
	 * @param alpha
	 * @param red
	 * @param green
	 * @param blue
	 * @return 返回颜色
	 */
	public Symbol.Color getTextColor(int alpha,int red,int green,int blue)
	{
		Symbol symbol = new Symbol();
		Symbol.Color color = symbol.new Color(alpha, red, green, blue);
		return color;
	}

	/**
	 * 绘制文本图层
	 * 
	 * @param showText
	 *            展示文字
	 * @param point
	 *            文字坐标
	 * @param fontSize
	 *            字体大小
	 * @param textColor
	 *            文字颜色
	 * @param backColor
	 *            背景颜色
	 */
	public void drawText(String showText,GeoPoint point,int fontSize,
			Symbol.Color textColor,Symbol.Color backColor)
	{
		TextItem textItem = new TextItem();
		textItem.fontColor = textColor;
		textItem.bgColor = backColor;
		textItem.fontSize = fontSize;

		textItem.text = showText;
		textItem.pt = point;

		addTextLayout(textItem);
	}

	/**
	 * 绘制文本图层
	 * 
	 * @param showText
	 *            展示内容
	 * @param point
	 *            文字坐标
	 * @param fontSize
	 *            字体大小
	 */
	public void drawText(String showText,GeoPoint point,int fontSize)
	{
		TextItem textItem = new TextItem();
		textItem.fontColor = mapConfig.gettextSymbol();
		textItem.bgColor = mapConfig.gettextBackSymbol();
		textItem.fontSize = fontSize;

		textItem.text = showText;
		textItem.pt = point;

		addTextLayout(textItem);
	}

	/**
	 * 绘制文本图层
	 * 
	 * @param showText
	 *            展示内容
	 * @param point
	 *            文本坐标
	 */
	public void drawText(String showText,GeoPoint point)
	{
		TextItem textItem = new TextItem();
		textItem.fontColor = mapConfig.gettextSymbol();
		textItem.bgColor = mapConfig.gettextBackSymbol();
		textItem.fontSize = mapConfig.gettextFontSize();

		textItem.text = showText;
		textItem.pt = point;

		addTextLayout(textItem);
	}

	/**
	 * 绘制多文本图层
	 * 
	 * @param showText
	 *            展示内容组
	 * @param points
	 *            文本坐标组
	 */
	public void drawMutiTextLayout(String[] showText,GeoPoint[] points)
	{
		for (int i = 0; i < points.length; i++)
		{
			TextItem textItem = new TextItem();
			textItem.fontColor = mapConfig.gettextSymbol();
			textItem.bgColor = mapConfig.gettextBackSymbol();
			textItem.fontSize = mapConfig.gettextFontSize();

			textItem.text = showText[i];
			textItem.pt = points[i];

			addTextLayout(textItem, false);
		}
	}

	/**
	 * 绘制文本图层
	 * 
	 * @param textItem
	 *            文本项
	 */
	public void addTextLayout(TextItem textItem)
	{
		TextOverlay textOverlay = new TextOverlay(currentMapView);
		textOverlay.addText(textItem);

		currentMapView.getOverlays().clear();
		currentMapView.getOverlays().add(textOverlay);
		currentMapView.refresh();
	}

	public void addTextLayout(TextItem textItem,boolean clearOrigin)
	{
		TextOverlay textOverlay = new TextOverlay(currentMapView);
		textOverlay.addText(textItem);

		if (clearOrigin)
			currentMapView.getOverlays().clear();
		currentMapView.getOverlays().add(textOverlay);
		currentMapView.refresh();
	}

	/**
	 * 获取当前坐标位置，与定位配合，获取信息不一定为实时信息，也可能为上次坐标信息
	 * 
	 * @return 返回坐标（经纬度）
	 */
	public GeoPoint getAddrLocation()
	{
		if (((int) (mapLocation.locationConfig.getdLantitude())) == 0)
			return null;

		return getGeoPoint(mapLocation.locationConfig.getdLantitude(),
				mapLocation.locationConfig.getdlongtitude());
	}

	/**
	 * 获取当前坐标（文字），与定位配合，获取信息不一定为实时，可能为上一次坐标
	 * 
	 * @return 坐标（文字）
	 */
	public String getAddrLocationAddress()
	{
		if (mapLocation.locationConfig.getsAddr() != "")
		{
			return mapLocation.locationConfig.getsAddr();
		}
		return null;
	}

	/**
	 * 图片图层
	 * @param bitmap 使用的图片
	 * @param leftDown 左下点
	 * @param rightTop 右上点
	 */
	public void drawPictureLayout(Bitmap bitmap,GeoPoint leftDown,GeoPoint rightTop)
	{
		GroundOverlay groundOverlay=new GroundOverlay(currentMapView);
		Ground ground=new Ground(bitmap, leftDown, rightTop);
		
		currentMapView.getOverlays().add(groundOverlay);
		groundOverlay.addGround(ground);
		currentMapView.refresh();
	}
	
	/**
	 * 图片图层
	 * @param bitmap 使用图片
	 * @param point 坐标点
	 * @param width 图片宽  单位米
	 * @param height 图片高 单位米
	 */
	public void drawPictureLayout(Bitmap bitmap,GeoPoint point,int width,int height)
	{
		GroundOverlay groundOverlay=new GroundOverlay(currentMapView);
		Ground ground=new Ground(bitmap, point,width,height);
		
		currentMapView.getOverlays().add(groundOverlay);
		groundOverlay.addGround(ground);
		currentMapView.refresh();
	}
	
	
	/**
	 * 多图片图层
	 * @param bitmap 使用的图片
	 * @param leftDown 左下点组
	 * @param rightTop 右上点组
	 */
	public void drawMutiPictureLayout(Bitmap bitmap,GeoPoint[] leftDown,GeoPoint[] rightTop)
	{
		GroundOverlay groundOverlay=new GroundOverlay(currentMapView);
		
		for(int i=0;i<leftDown.length;i++)
		{
			Ground ground=new Ground(bitmap, leftDown[i], rightTop[i]);
			groundOverlay.addGround(ground);
		}
		currentMapView.getOverlays().add(groundOverlay);	
		currentMapView.refresh();
	}
	
	/**
	 * 多图片图层
	 * @param bitmap 使用的图片
	 * @param point 点组
	 * @param width 图片宽（米）
	 * @param height 图片高（米）
	 */
	public void drawMutiPictureLayout(Bitmap bitmap,GeoPoint[] point,int width,int height)
	{
		GroundOverlay groundOverlay=new GroundOverlay(currentMapView);
		
		for(int i=0;i<point.length;i++)
		{
			Ground ground=new Ground(bitmap, point[i], width,height);
			groundOverlay.addGround(ground);
		}
		currentMapView.getOverlays().add(groundOverlay);	
		currentMapView.refresh();
	}
	/**
	 * 自定义监听函数
	 * @param bdLocationListener 监听函数
	 */
	public void registerLocationListen(BDLocationListener bdLocationListener)
	{
		mapLocation.removeListener();
		mapLocation.addListener(bdLocationListener);
	}
	
	/**
	 * 使用离线地图操作
	 * @param listener 下载监听函数（如不需则null）
	 *//*
	public void useOffineMap(MKOfflineMapListener listener)
	{
		offlineMap=new OfflineMap(currentMapController, listener);
	}
	*/
}