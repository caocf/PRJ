package com.hztuen.gh.activity;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.CancelableCallback;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.amap.map3d.demo.util.AMapUtil;
import com.amap.map3d.demo.util.Constants;
import com.hxkg.ghpublic.R;
import com.hztuen.lvyou.utils.SystemStatic;

/**
 * @author zzq
 * @DateTime 2016年7月12日 下午3:41:35
 */
public class SearchMapActivity extends AbActivity implements 
LocationSource,
OnGeocodeSearchListener,
AMapLocationListener,
InfoWindowAdapter, 
OnPoiSearchListener,
OnClickListener{
	private MapView mapView;
	private AMap aMap;
	/*
	 * 地图定位相关 
	 * */
	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;
	private String city = "";
	private String searchpoint = "";
	private String poistyle = "";
	private String citycode;
	private int s=5000;
	private LatLonPoint lp ;
	/*geosearch*/
	private Marker geoMarker;
	private Marker regeoMarker;
	private GeocodeSearch geocoderSearch;
	private LatLonPoint latLonPoint;

	private List<MarkerOptions> markers=new ArrayList<MarkerOptions>();
	private List<Marker> mList=new ArrayList<Marker>();

	private int currentPage = 0;// 当前页面，从0开始计数
	private PoiSearch.Query query;// Poi查询条件类
	private PoiSearch poiSearch;// POI搜索
	private PoiResult poiResult; // poi返回的结果

	private Marker markerd = null;
	private String name;
	private double distance = 0.0;
	private double distance2 = 0.0;
	private long opentime;
	private String phone;
	private String phone2;
	private ImageView around_back;
	private TextView search_row;
	private PopupWindow popinfo = null;
	private int keytag = 0;//设置标记变量,用于标记弹出框
	private int mTag = 0;//标记标签,
	private int tag2 = 0;//标记标签，用来处理搜索的次序，先定位，然后在搜索

	private ImageView dingwei;
	private LatLng dangqian;
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_map);
		mapView = (MapView) findViewById(R.id.map);
		search_row = (TextView) findViewById(R.id.search_row);
		search_row.setOnClickListener(this);
		//		{
		//			@Override
		//			public void onClick(View v) {
		//				//				finish();
		//				Intent mIntent = new Intent();
		//				mIntent.setClass(getApplicationContext(), AroundMapListActivity.class);
		//				mIntent.putExtra("searchpoint", searchpoint);
		//				mIntent.putExtra("poistyle", poistyle);
		//				startActivity(mIntent);
		//			}
		//		});
		around_back = (ImageView) findViewById(R.id.around_back);
		around_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		dingwei = (ImageView) findViewById(R.id.dingwei);
		dingwei.setClickable(false);
		dingwei.setOnClickListener(this);

		if(null != SystemStatic.lp){
			dangqian = new LatLng(SystemStatic.lp.getLatitude(), SystemStatic.lp.getLongitude());// 当前经纬度
		}else{
			dingwei.setClickable(false);
		}
		title = (TextView) findViewById(R.id.title);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		Intent intent = getIntent();
		searchpoint = intent.getStringExtra("searchpoint");
		city = intent.getStringExtra("city");
		s = intent.getIntExtra("s", 5000);
		mTag = intent.getIntExtra("tag", 0);
		poistyle = intent.getStringExtra("poistyle");
		citycode = intent.getStringExtra("citycode");
		phone2 = intent.getStringExtra("phone");
		distance2 = intent.getDoubleExtra("distance", 0.0);
		if(poistyle!=null){
			title.setText(poistyle);
		}else{
			title.setText(searchpoint);
		}

		init();
		lp = SystemStatic.lp;
	}

	public void initPopupWindow(){
		View contentView = getLayoutInflater().inflate(R.layout.pop_map_search, null);
		TextView pointname = (TextView) contentView.findViewById(R.id.point_name);
		TextView pointdistance = (TextView) contentView.findViewById(R.id.point_distance);
		TextView pointtime = (TextView) contentView.findViewById(R.id.point_time);
		ImageView pointphone = (ImageView) contentView.findViewById(R.id.point_phone);
		LinearLayout parent = (LinearLayout) findViewById(R.id.popparent);
		pointname.setText(name);
		//		pointtime.setText(Long.toString(opentime));
		pointphone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone.equals("")){
					Toast.makeText(getApplicationContext(), "暂无商家联系方式...", Toast.LENGTH_SHORT).show();
				}else{
					/* 打电话 */
					Uri uri = Uri.parse("tel:"+phone);
					Intent dialntent = new Intent(Intent.ACTION_DIAL,uri);
					startActivity(dialntent);
				}
			}
		});
		if(distance<=1000.0&&distance>0){
			pointdistance.setText(distance+"米");
		}else if(distance>1000){
			pointdistance.setText(format(distance/1000)+"千米");
		}else{
			pointdistance.setText("暂时无法获取距离");
		}
		popinfo = new PopupWindow(contentView, parent.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
		popinfo.setFocusable(false);
		popinfo.setOutsideTouchable(true);
		popinfo.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	}
	public void init(){
		if (aMap == null) {
			aMap = mapView.getMap();
			aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
			geoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
			regeoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
			setUpMap();
		}
	}
	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.ic_directio2));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
		//		myLocationStyle.anchor(10,10);//设置小蓝点的锚点
		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase 
		aMap.setInfoWindowAdapter(this);
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
		if(mTag != 1){
			aMap.setOnMarkerClickListener(onmark);// 添加点击marker监听事件
		}
		aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
		//		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lp.getLatitude(), lp.getLongitude()), 14));
	}
	public OnMarkerClickListener onmark = new OnMarkerClickListener() {
		@Override
		public boolean onMarkerClick(Marker marker) {
			// TODO Auto-generated method stub\
			if(markerd == null){
				markerd = marker;
				marker.setIcon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			}else{
				if(markerd == marker){

				}else{
					marker.setIcon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED));
					markerd.setIcon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
					markerd = marker;
				}
			}

			if(Math.abs(marker.getPosition().latitude-SystemStatic.lp.getLongitude())<=0.000005 &&Math.abs(marker.getPosition().longitude-SystemStatic.lp.getLatitude())<=0.000005){
				if(popinfo!=null){
					popinfo.dismiss();
				}
			}else{
				name = marker.getTitle();
				//用于标记弹出框
				keytag=1;
				doSearchQuery2();

			}
			return false;
		}
	};
	/**
	 * 方法必须重写
	 * 
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}
	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}
	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}
	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO Auto-generated method stub
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
				opentime = amapLocation.getTime(); 

				SystemStatic.city = amapLocation.getCity();//城市信息
				city = SystemStatic.city;
				SystemStatic.lp = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
				dangqian = new LatLng(SystemStatic.lp.getLatitude(), SystemStatic.lp.getLongitude());// 当前经纬度

				dingwei.setClickable(true);
				if(tag2 == 0){
					if(mTag==1){
						//			title.setText(poistyle);
						geocoderSearch = new GeocodeSearch(this);
						geocoderSearch.setOnGeocodeSearchListener(this);
						//			getLatlon(searchpoint);
						latLonPoint = SystemStatic.llp;
						getAddress(latLonPoint);
					}else{
						title.setText(searchpoint);
						doSearchQuery();
					}
					tag2 = 1;
				}
			} else {
				String errText = "定位失败," + amapLocation.getErrorCode()+ ":" + amapLocation.getErrorInfo();
				Log.e("AmapErr",errText);
			}
		}
	}
	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			//设置是否只定位一次,默认为false
			mLocationOption.setOnceLocation(false);         
			//设置是否强制刷新WIFI，默认为强制刷新
			mLocationOption.setWifiActiveScan(true);
			//设置定位监听
			mlocationClient.setLocationListener(this);
			//设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			//设置定位参数
			mLocationOption.setInterval(100);
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();
		}
	}
	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}
	/**
	 * 开始进行poi搜索
	 */
	protected void doSearchQuery2() {
		//		showProgressDialog();// 显示进度框
		currentPage = 0;
		query = new PoiSearch.Query(name, poistyle, city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		query.setPageSize(1);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页
		//		query.setCityLimit(true);
		if (lp != null) {
			poiSearch = new PoiSearch(this, query);
			poiSearch.setOnPoiSearchListener(this);

			poiSearch.setBound(new SearchBound(lp, s, true));//
			// 设置搜索区域为以lp点为圆心，其周围5000米范围
			poiSearch.searchPOIAsyn();// 异步搜索
		}
	}

	protected void doSearchQuery() {
		currentPage = 0;
		if(poistyle==null){
			query = new PoiSearch.Query(searchpoint, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
			poiSearch = new PoiSearch(this, query);
			poiSearch.setOnPoiSearchListener(this);
			poiSearch.searchPOIAsyn();
		}else {
			query = new PoiSearch.Query(searchpoint, poistyle, city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
			poiSearch = new PoiSearch(this, query);
			poiSearch.setOnPoiSearchListener(this);
			poiSearch.setBound(new SearchBound(lp, s, true));//
			// 设置搜索区域为以lp点为圆心，其周围5000米范围
			poiSearch.searchPOIAsyn();// 异步搜索	
		}
	}
	@Override
	public void onPoiItemSearched(PoiItem arg0, int arg1) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onPoiSearched(PoiResult result, int rCode) {
		// TODO Auto-generated method stub
		if (rCode == 1000) {
			if (result != null && result.getQuery() != null) {// 搜索poi的结果
				if (result.getQuery().equals(query)) {// 是否是同一条
					poiResult = result;
					// 取得搜索到的poiitems有多少页
					List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
					distance = poiItems.get(0).getDistance();
					phone = poiItems.get(0).getTel();
					List<SuggestionCity> suggestionCities = poiResult
							.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
					if (poiItems != null && poiItems.size() > 0 && keytag != 1) {
						//						aMap.clear();// 清理之前的图标
						PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
						poiOverlay.removeFromMap();
						poiOverlay.addToMap();
						poiOverlay.zoomToSpan();
					} else if (suggestionCities != null
							&& suggestionCities.size() > 0) {
						showSuggestCity(suggestionCities);
					} else {
						//						Toast.makeText(getApplicationContext(), "暂无搜索数据....", Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				Toast.makeText(getApplicationContext(), "something is wrong ....", Toast.LENGTH_SHORT).show();
			}
			if(keytag==0){
				//不做处理
			}else{
				if(popinfo!=null){
					popinfo.dismiss();
				}
				initPopupWindow();

			}
		} else {
			Toast.makeText(getApplicationContext(), rCode, Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * poi没有搜索到数据，返回一些推荐城市的信息
	 */
	private void showSuggestCity(List<SuggestionCity> cities) {
		String infomation = "推荐城市\n";
		for (int i = 0; i < cities.size(); i++) {
			infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
					+ cities.get(i).getCityCode() + "城市编码:"
					+ cities.get(i).getAdCode() + "\n";
		}
		Toast.makeText(getApplicationContext(), infomation, Toast.LENGTH_SHORT).show();
	}
	@Override
	public View getInfoContents(Marker marker) {
		// TODO Auto-generated method stub
		View view = getLayoutInflater().inflate(R.layout.hide, null);
		LinearLayout title_hide = (LinearLayout) view.findViewById(R.id.title_hide);
		title_hide.setVisibility(View.GONE);
		return view;
	}
	@Override
	public View getInfoWindow(final Marker marker) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 保留小数点后两位
	 * */
	public static String format(double value) {  

		NumberFormat nf = NumberFormat.getNumberInstance();  
		nf.setMaximumFractionDigits(2);  
		/* 
		 * setMinimumFractionDigits设置成2 
		 *  
		 * 如果不这么做，那么当value的值是100.00的时候返回100 
		 *  
		 * 而不是100.00 
		 */  
		nf.setMinimumFractionDigits(2);  
		nf.setRoundingMode(RoundingMode.HALF_UP);  
		/* 
		 * 如果想输出的格式用逗号隔开，可以设置成true 
		 */  
		nf.setGroupingUsed(false);  
		return nf.format(value);  
	}
	/**
	 * 响应地理编码
	 */
	public void getLatlon(final String name) {
		//		showDialog();
		GeocodeQuery query2 = new GeocodeQuery(searchpoint, citycode);// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，

		try{
			geocoderSearch.getFromLocationNameAsyn(query2);// 设置同步地理编码请求
		}catch(Exception e){
			e.getMessage();
		}
	}
	/**
	 * 响应逆地理编码
	 */
	public void getAddress(final LatLonPoint latLonPoint) {
		//		showDialog();
		RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
				GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
	}
	/**
	 * 地理编码查询回调
	 */
	@Override
	public void onGeocodeSearched(GeocodeResult result, int rCode) {
		//		dismissDialog();
		if (rCode == 1000) {
			if (result != null && result.getGeocodeAddressList() != null
					&& result.getGeocodeAddressList().size() > 0) {
				GeocodeAddress address = result.getGeocodeAddressList().get(0);
				aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						AMapUtil.convertToLatLng(address.getLatLonPoint()), 15));
				geoMarker.setPosition(AMapUtil.convertToLatLng(address
						.getLatLonPoint()));
				//				addressName = "经纬度值:" + address.getLatLonPoint() + "\n位置描述:"
				//						+ address.getFormatAddress();
				//				ToastUtil.show(GeocoderActivity.this, addressName);
			} else {
				//				ToastUtil.show(GeocoderActivity.this, R.string.no_result);
			}

		} else {
			//			ToastUtil.showerror(GeocoderActivity.this, rCode);
		}
	}



	/**
	 * 逆地理编码回调
	 */
	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
		//		dismissDialog();
		if (rCode == 1000) {
			if (result != null && result.getRegeocodeAddress() != null
					&& result.getRegeocodeAddress().getFormatAddress() != null) {
				name = searchpoint;
				distance = distance2;
				phone = phone2;
				aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						AMapUtil.convertToLatLng(latLonPoint), 15));
				regeoMarker.setPosition(AMapUtil.convertToLatLng(latLonPoint));
				initPopupWindow();
				//				ToastUtil.show(GeocoderActivity.this, addressName);
			} else {
				//				ToastUtil.show(GeocoderActivity.this, R.string.no_result);
			}
		} else {
			//			ToastUtil.showerror(GeocoderActivity.this, rCode);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dingwei:
			changeCamera(
					CameraUpdateFactory.newCameraPosition(new CameraPosition(
							dangqian, 20, 0, 0)), null);
			break;
		case R.id.search_row:
			Intent mIntent = new Intent();
			mIntent.setClass(getApplicationContext(), AroundMapListActivity.class);
			mIntent.putExtra("searchpoint", searchpoint);
			mIntent.putExtra("poistyle", poistyle);
			mIntent.putExtra("s", s);
			startActivity(mIntent);
			break;
		default:
			break;
		}
	}

	//	private void changeCamera(CameraUpdate newCameraPosition, Object object) {
	//		// TODO Auto-generated method stub
	//		
	//	}

	/**
	 * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
	 */
	private void changeCamera(CameraUpdate update, CancelableCallback callback) {
		//		boolean animated = ((CompoundButton) findViewById(R.id.animate))
		//				.isChecked();
		//		if (animated) {
		aMap.animateCamera(update, 1000, callback);
		//		} else {
		//			aMap.moveCamera(update);
		//		}
	}


}
