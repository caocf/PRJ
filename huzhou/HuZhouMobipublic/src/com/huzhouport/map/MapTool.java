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
 * �汾1.3
 * 
 * @author Administrator: Aaron ��
 * @time 2014-3-13
 * 
 */
public class MapTool
{
	/**
	 * ���캯��
	 * 
	 * @param c
	 *            ȫ��context
	 * @param a
	 *            ȫ��application
	 */
	public MapTool(Application a)
	{
		currentContext = a.getApplicationContext();
		currentApplication = a;
		iniMapManager();
	}

	
	
	/**
	 * mapTool������ı���
	 */
	public Context			currentContext;		// ������
	public static BMapManager		mapManager;			// ��ͼ������
	private Application		currentApplication;	// ��ǰapplication����
	public MapLocation		mapLocation;			// ��ͼ��λ
	private Activity		currentActivity;		// ��ǰactivity����
	private MapView			currentMapView;		// ��ͼ
	private MapController	currentMapController;	// ��ͼ������
	private MKSearch		mSearch;				// ����
	public MapConfig		mapConfig;				// ����

	public ListView			listView;
	//public OfflineMap       offlineMap;

	/**
	 * ��ʼ����ͼ������������key����֤�����������ò���֮ǰ���
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
	 * ��ʼ����ͼ
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param activity
	 *            ��ͼ��������activity
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
		
		//��λ��Ĭ��λ��
		locationDefault();
//		currentMapController.setCenter(mapConfig.getGeoCenterPoint());

		currentMapView.setBuiltInZoomControls(mapConfig.getbZoomControl());
		currentMapView.regMapViewListener(mapManager, currentMapViewListener);

		

		currentActivity = activity;

		mapLocation = new MapLocation(currentContext, currentMapView);
	}
	
	/**
	 * ��ʾtoast��Ϣ
	 * 
	 * @param data
	 *            ��Ҫ��ʾ����Ϣ
	 */
	public void showToast(String data)
	{
		Toast.makeText(currentContext, data, Toast.LENGTH_LONG).show();
	}

	/**
	 * ��ʾtoast��Ϣ
	 * 
	 * @param data
	 *            ��Ҫ��ʾ����Ϣ
	 * @param context
	 *            ��Ҫ��ʾ��context
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
	 * ��ʼ����ͼ�������ļ�������
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
	 * ������ͼ�����¼��������ƶ������ء�����¼���
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
	 * ��Ĭ�ϲ������õ�ͼ����ͼ��
	 */
	public void setMapLayout()
	{
		currentMapView.setSatellite(mapConfig.getbSatellite());
		currentMapView.setTraffic(mapConfig.getbTraffic());
	}

	/**
	 * ���õ�ͼ����ͼ��
	 * 
	 * @param satellite
	 *            ���Ƿ�������ͼ
	 * @param traffic
	 *            ���Ƿ��ǽ�ͨͼ
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
	 * ��������ͼ
	 */
	public void setSatelliteLayout()
	{
		currentMapView.setSatellite(true);
	}

	/**
	 * ���ý�ͨͼ
	 */
	public void setTraffic()
	{
		currentMapView.setTraffic(true);
	}

	/**
	 * ��������������������ͨ����·����Ȥ���
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
															// ��ͨ
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
	 * ��ָ����Χ����������
	 * 
	 * @param searchContent
	 *            ����������
	 * @param leftTop
	 *            �����ϵ�����
	 * @param rightDown
	 *            �����µ�����
	 */
	public void searchInZoom(String searchContent,GeoPoint leftTop,
			GeoPoint rightDown)
	{
		mSearch.poiSearchInbounds(searchContent, leftTop, rightDown);
	}

	/**
	 * ��ָ������������
	 * 
	 * @param city
	 *            �������ĳ���
	 * @param content
	 *            ����������
	 */
	public void searchInCity(String city,String content)
	{
		mSearch.poiSearchInCity(city, content);
	}

	/**
	 * �ڸ�����Χ����
	 * 
	 * @param content
	 *            ����������
	 * @param cur
	 *            ���������ĵ�
	 * @param radius
	 *            �������뾶
	 */
	public void searchInNearBy(String content,GeoPoint cur,int radius)
	{
		mSearch.poiSearchNearBy(content, cur, radius);
	}

	/**
	 * ͨ�����������ַ
	 * 
	 * @param point
	 *            �������
	 */
	public void searchReverseGeocode(GeoPoint point)
	{
		mSearch.reverseGeocode(point);
	}

	/**
	 * ͨ�����������ַ
	 * 
	 * @param latitude
	 *            ��γ��
	 * @param longtitude
	 *            ������
	 */
	public void searchReverseGeocode(int latitude,int longtitude)
	{
		mSearch.reverseGeocode(new GeoPoint(latitude, longtitude));
	}

	/**
	 * ͨ�����ƻ�ȡ��γ��
	 * 
	 * @param key
	 *            ����ַ����
	 * @param city
	 *            ����ַ���ڳ���
	 */
	public void searchGeocode(String key,String city)
	{
		mSearch.geocode(key, city);
	}

	/**
	 * �������ݣ��˺���������Ӧ��listview���ӣ�
	 * 
	 * @param key
	 *            ��Ҫ����������
	 * @param city
	 *            �������ĳ���
	 * @param listView
	 *            ������������ʾ����
	 */
	public void searchSuggest(String key,String city,ListView l)
	{
		listView = l;
		mSearch.suggestionSearch(key, city);
	}

	/**
	 * �Լ���·����
	 * 
	 * @param start
	 *            ����ʼ�����ַ
	 * @param end
	 *            �����������ַ
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
	 * �Լ���·����
	 * 
	 * @param startCity
	 *            ����ʼ����
	 * @param startName
	 *            ����ʼ������
	 * @param endCity
	 *            ����������
	 * @param endName
	 *            ������������
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
	 * ������·����
	 * 
	 * @param start
	 *            ����ʼ�����
	 * @param end
	 *            �����������
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
	 * ������·����
	 * 
	 * @param startCity
	 *            ����ʼ����
	 * @param startName
	 *            ����ʼ��
	 * @param endCity
	 *            ����������
	 * @param endName
	 *            ��������
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
	 * ������·��Ϣ��ѯ
	 * 
	 * @param city
	 *            ����������
	 * @param path
	 *            ��������·����
	 */
	public void searchBus(String city,String path)
	{
		mSearch.poiSearchInCity(city, path);
	}

	/**
	 * ������·����
	 * 
	 * @param city
	 *            �����ڳ���
	 * @param start
	 *            ����ʼ����
	 * @param end
	 *            ����������
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
	 * ������·��ѯ
	 * 
	 * @param city
	 *            �����ڳ���
	 * @param start
	 *            ����ʼ�ص�
	 * @param end
	 *            �������ص�
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
	 * ��λ�������ã�
	 */
	public void locationDefault()
	{
		searchGeocode(BaseMapData.POINT_DEFAULT, BaseMapData.CITY_DEFAULT);
	}

	/**
	 * ��ǰλ�ö�λһ��
	 */
	public void locationOnce()
	{
		mapLocation.setLocationOption();
		mapLocation.locationStart(true);
		mapLocation.sendRequest(1);
	}

	/**
	 * ��ʱ��λ
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
	 * ȡ����ʱ��λ
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
	 * ����ͼ������
	 * 
	 * @param bitmaps
	 *            ����������ͼ
	 * @param point
	 *            λ�õ�
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
	 * ������ʽ������ʽ��
	 * 
	 * @param red
	 *            �������ɫ�к�ɫ����
	 * @param green
	 *            �������ɫ����ɫ����
	 * @param blue
	 *            �������ɫ����ɫ����
	 * @param alpha
	 *            �������ɫ��͸����
	 * @param index
	 *            ���߿��ߴ�ϸ
	 * @param lineRed
	 *            ���߿��ߺ�ɫ
	 * @param lineGreen
	 *            ���߿�����ɫ
	 * @param lineBlue
	 *            ���߿�����ɫ
	 * @param lineAlpha
	 *            ���߿���͸����
	 * @param fill
	 *            ���Ƿ����0��1��
	 * @return ������ʽ
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
	 * ������
	 * 
	 * @param leftTop
	 *            �����ϵ�����
	 * @param rightDown
	 *            �����µ�����
	 * @param symbol
	 *            ����ʽ
	 */
	public void drawRect(GeoPoint leftTop,GeoPoint rightDown,Symbol symbol)
	{
		Geometry geometry = new Geometry();
		geometry.setEnvelope(leftTop, rightDown);

		Graphic graphic = new Graphic(geometry, symbol);

		drawOverlay(graphic);
	}

	/**
	 * Ĭ����ʽ���Ʒ���
	 * 
	 * @param leftTop
	 *            �����ϵ�����
	 * @param rightDown
	 *            �����µ�����
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
	 * ����ʽ
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 * @return ���ص���ʽ
	 */
	public Symbol setPointSymbol(int red,int green,int blue,int alpha)
	{
		Symbol symbol = new Symbol();

		Symbol.Color color = symbol.new Color(alpha, red, green, blue);

		symbol.setPointSymbol(color);
		return symbol;
	}

	/**
	 * ����
	 * 
	 * @param point
	 *            ����λ��
	 * @param pixel
	 *            �����ϸ
	 * @param symbol
	 *            ������ʽ
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
	 * ����ʽ
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 * @param width
	 *            ���߿��
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
	 * Ĭ����ʽ����
	 * 
	 * @param point
	 *            ����λ��
	 * @param pixel
	 *            �����С
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
	 * Ĭ����ʽ����
	 * 
	 * @param point
	 *            ��������
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
	 * �����
	 * 
	 * @param points
	 *            �������
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
	 * ����
	 * 
	 * @param geoPoint
	 *            �����ߵ��������
	 * @param symbol
	 *            ������ʽ
	 */
	public void drawLine(GeoPoint[] geoPoint,Symbol symbol)
	{
		Geometry geometry = new Geometry();
		geometry.setPolyLine(geoPoint);

		Graphic graphic = new Graphic(geometry, symbol);
		drawOverlay(graphic);
	}

	/**
	 * Ĭ����ʽ����
	 * 
	 * @param geoPoint
	 *            ���������
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
	 * �������
	 * 
	 * @param geoPoint
	 *            ���������
	 * @param symbol
	 *            ���������ʽ
	 */
	public void drawPolygon(GeoPoint[] geoPoint,Symbol symbol)
	{
		Geometry geometry = new Geometry();
		geometry.setPolygon(geoPoint);

		Graphic graphic = new Graphic(geometry, symbol);
		drawOverlay(graphic);
	}

	/**
	 * Ĭ����ʽ�������
	 * 
	 * @param geoPoint
	 *            ���������
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
	 * ���ƶ����ͼ��
	 * 
	 * @param graphic
	 *            ��ͼ������
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
	 * �ƶ���ָ����
	 * 
	 * @param geoPoint
	 *            ��Ŀ������
	 */
	public void moveTo(GeoPoint geoPoint)
	{
		currentMapController.animateTo(geoPoint);
	}

	/**
	 * ��ָ�����Ƶ���ǰ��ͼ����
	 * 
	 * @param geoPoint
	 *            ����ͼ���ĵ�����
	 */
	public void setCenter(GeoPoint geoPoint)
	{
		currentMapController.setCenter(geoPoint);
	}

	/**
	 * ͨ������intֵ����ȡ��γ�ȶ���
	 * 
	 * @param latitude
	 *            ��γ��
	 * @param longtitude
	 *            ������
	 * @return ����GeoPoint����
	 */
	public GeoPoint getGeoPoint(int latitude,int longtitude)
	{
		return new GeoPoint(latitude, longtitude);
	}

	/**
	 * ͨ������doubleֵ�����ؾ�γ�ȶ���
	 * 
	 * @param latitude
	 *            ��γ��
	 * @param longtitude
	 *            ������
	 * @return ����GeoPoint����
	 */
	public GeoPoint getGeoPoint(double latitude,double longtitude)
	{
		return new GeoPoint(((int) (latitude * 1e6)),
				((int) (longtitude * 1e6)));
	}

	/**
	 * ������������
	 * 
	 * @param point1
	 *            ����1
	 * @param point2
	 *            ����2
	 * @return ����
	 */
	public double GetLength(GeoPoint point1,GeoPoint point2)
	{
		return DistanceUtil.getDistance(point1, point2);
	}

	/**
	 * �������ͼ��
	 */
	public void clearAllLayout()
	{
		currentMapView.getOverlays().clear();
	}

	/**
	 * ���ò���ȡ��ȡ������ɫ
	 * 
	 * @param alpha
	 * @param red
	 * @param green
	 * @param blue
	 * @return ������ɫ
	 */
	public Symbol.Color getTextColor(int alpha,int red,int green,int blue)
	{
		Symbol symbol = new Symbol();
		Symbol.Color color = symbol.new Color(alpha, red, green, blue);
		return color;
	}

	/**
	 * �����ı�ͼ��
	 * 
	 * @param showText
	 *            չʾ����
	 * @param point
	 *            ��������
	 * @param fontSize
	 *            �����С
	 * @param textColor
	 *            ������ɫ
	 * @param backColor
	 *            ������ɫ
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
	 * �����ı�ͼ��
	 * 
	 * @param showText
	 *            չʾ����
	 * @param point
	 *            ��������
	 * @param fontSize
	 *            �����С
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
	 * �����ı�ͼ��
	 * 
	 * @param showText
	 *            չʾ����
	 * @param point
	 *            �ı�����
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
	 * ���ƶ��ı�ͼ��
	 * 
	 * @param showText
	 *            չʾ������
	 * @param points
	 *            �ı�������
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
	 * �����ı�ͼ��
	 * 
	 * @param textItem
	 *            �ı���
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
	 * ��ȡ��ǰ����λ�ã��붨λ��ϣ���ȡ��Ϣ��һ��Ϊʵʱ��Ϣ��Ҳ����Ϊ�ϴ�������Ϣ
	 * 
	 * @return �������꣨��γ�ȣ�
	 */
	public GeoPoint getAddrLocation()
	{
		if (((int) (mapLocation.locationConfig.getdLantitude())) == 0)
			return null;

		return getGeoPoint(mapLocation.locationConfig.getdLantitude(),
				mapLocation.locationConfig.getdlongtitude());
	}

	/**
	 * ��ȡ��ǰ���꣨���֣����붨λ��ϣ���ȡ��Ϣ��һ��Ϊʵʱ������Ϊ��һ������
	 * 
	 * @return ���꣨���֣�
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
	 * ͼƬͼ��
	 * @param bitmap ʹ�õ�ͼƬ
	 * @param leftDown ���µ�
	 * @param rightTop ���ϵ�
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
	 * ͼƬͼ��
	 * @param bitmap ʹ��ͼƬ
	 * @param point �����
	 * @param width ͼƬ��  ��λ��
	 * @param height ͼƬ�� ��λ��
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
	 * ��ͼƬͼ��
	 * @param bitmap ʹ�õ�ͼƬ
	 * @param leftDown ���µ���
	 * @param rightTop ���ϵ���
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
	 * ��ͼƬͼ��
	 * @param bitmap ʹ�õ�ͼƬ
	 * @param point ����
	 * @param width ͼƬ���ף�
	 * @param height ͼƬ�ߣ��ף�
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
	 * �Զ����������
	 * @param bdLocationListener ��������
	 */
	public void registerLocationListen(BDLocationListener bdLocationListener)
	{
		mapLocation.removeListener();
		mapLocation.addListener(bdLocationListener);
	}
	
	/**
	 * ʹ�����ߵ�ͼ����
	 * @param listener ���ؼ����������粻����null��
	 *//*
	public void useOffineMap(MKOfflineMapListener listener)
	{
		offlineMap=new OfflineMap(currentMapController, listener);
	}
	*/
}