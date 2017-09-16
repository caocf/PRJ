package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.AMap.CancelableCallback; 
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.gh.modol.ServiceAround;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.ServiceAroundAdapter;
import com.hztuen.lvyou.utils.SystemStatic;

public class MainFragmentAround extends Activity implements LocationSource,AMapLocationListener,OnClickListener
{
	private MapView mapView;
	private AMap aMap;
	/**
	 * 地图定位相关 
	 * */
	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;
	private ServiceAroundAdapter adapter;
	private List<ServiceAround> listservice = new ArrayList<ServiceAround>();
	RelativeLayout parent;
	private CheckedTextView upordown;
	private TextView search_text;
	private ImageView around_back;
	private int i=4;//
	private boolean flag = false;
	private String city = null;
	private ImageView dingwei;
	private LatLng dangqian;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{	
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_around);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		dingwei = (ImageView) findViewById(R.id.dingwei);
		dingwei.setClickable(false);
		dingwei.setOnClickListener(this);

		if(null != SystemStatic.lp){
			dangqian = new LatLng(SystemStatic.lp.getLatitude(), SystemStatic.lp.getLongitude());// 当前经纬度
		}else{
			dingwei.setClickable(false);
		}
		init();
		around_back = (ImageView) findViewById(R.id.around_back);
		search_text = (TextView) findViewById(R.id.search_text);
		upordown = (CheckedTextView) findViewById(R.id.upordown);
		GridView serviceAround = (GridView) findViewById(R.id.servicearound);
		adapter = new ServiceAroundAdapter(getApplicationContext(), listservice);
		serviceAround.setAdapter(adapter);
		getDate();
		search_text.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{ 
				Intent intent = new Intent();
				intent.putExtra("city", city); 
				intent.setClass(getApplicationContext(), AroundSearchActivity.class);
				startActivity(intent);
			}
		});
		around_back.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		upordown.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				if(SwitchChanage()){
					upordown.setChecked(true);
					i = 7;
					getDate();
				}else{
					upordown.setChecked(false);
					i = 4;
					getDate();
				}
			}
		});
		serviceAround.setOnItemClickListener(onitemclick);
	}
	public OnItemClickListener onitemclick = new OnItemClickListener() 
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{ 
			Intent intent = new Intent();
			switch (position+1) {
			case 1:
				if(city==null){
					Toast.makeText(getApplicationContext(), "定位中...", Toast.LENGTH_SHORT).show();
					break;
				}
				intent.putExtra("point", "码头");
				intent.putExtra("city", city);
				intent.setClass(getApplicationContext(), SerachListActivity.class);
				startActivity(intent);
				break;
			case 2:
				if(city==null){
					Toast.makeText(getApplicationContext(), "定位中...", Toast.LENGTH_SHORT).show();
					break;
				}
				intent.putExtra("point", "加油站");
				intent.putExtra("city", city);
				intent.setClass(getApplicationContext(), SerachListActivity.class);
				startActivity(intent);
				break;
			case 3:
				if(city==null)
				{
					Toast.makeText(getApplicationContext(), "定位中...", Toast.LENGTH_SHORT).show();
					break;
				}
				intent.putExtra("point", "锚泊区");
				intent.putExtra("city", city);
				intent.setClass(getApplicationContext(), SerachListActivity.class);
				startActivity(intent);
				break;
			case 4:
				if(city==null){
					Toast.makeText(getApplicationContext(), "定位中...", Toast.LENGTH_SHORT).show();
					break;
				}
				intent.putExtra("point", "船厂");
				intent.putExtra("city", city);
				intent.setClass(getApplicationContext(), SerachListActivity.class);
				startActivity(intent);
				break;
			case 5:
				if(city==null){
					Toast.makeText(getApplicationContext(), "定位中...", Toast.LENGTH_SHORT).show();
					break;
				}
				intent.putExtra("point", "桥梁");
				intent.putExtra("city", city);
				intent.setClass(getApplicationContext(), SerachListActivity.class);
				startActivity(intent);
				break;
			case 6:
				if(city==null){
					Toast.makeText(getApplicationContext(), "定位中...", Toast.LENGTH_SHORT).show();
					break;
				}
				intent.putExtra("point", "港航站");
				intent.putExtra("city", city);
				intent.setClass(getApplicationContext(), SerachListActivity.class);
				startActivity(intent);
				break;
			case 7:
				if(city==null){
					Toast.makeText(getApplicationContext(), "定位中...", Toast.LENGTH_SHORT).show();
					break;
				}
				intent.putExtra("point", "水上服务");
				intent.putExtra("city", city);
				intent.setClass(getApplicationContext(), SerachListActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};
	/**
	 * 控制upordown
	 * */
	public boolean SwitchChanage(){
		if(flag){
			flag = false;
		}else{
			flag = true;
		}
		return flag;		
	}
	/**
	 * 初始化参数
	 * */
	public void getDate()
	{
		listservice.clear();
		String serviceName[] = {"码头","加油站","锚泊区","船厂","桥梁","港航站","水上服务区"};
		int icon[] = {R.drawable.ic_circum_wharf,R.drawable.ic_circum_gl,
				R.drawable.ic_circum_anchor,R.drawable.ic_circum_shipyard,
				R.drawable.ic_circum_bridge,R.drawable.ic_circum_station,
				R.drawable.ic_circum_serives};
		for(int j = 0;j<i;j++){
			ServiceAround sa = new ServiceAround();
			sa.setServiceName(serviceName[j]);
			sa.setServiceIcon(icon[j]);
			listservice.add(sa);
			adapter.notifyDataSetChanged();
		}
	}
	public void init(){
		if (aMap == null) {
			aMap = mapView.getMap();
			aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
			setUpMap();
		}
	}
	/**
	 * 
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.ic_directio2));// 设置小蓝点的图标
		//		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
		// myLocationStyle.anchor(int,int)//设置小蓝点的锚点

		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
		//		aMap.getUiSettings().set
		aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase 
		//		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// aMap.setMyLocationType()
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
//		aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
	}
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
	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
				//				city = amapLocation.getCity();//城市信息
				SystemStatic.city = amapLocation.getCity();//城市信息
				city = SystemStatic.city;
				dingwei.setClickable(true);
				SystemStatic.lp = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
				dangqian = new LatLng(SystemStatic.lp.getLatitude(), SystemStatic.lp.getLongitude());// 当前经纬度
				//				Toast.makeText(getApplicationContext(), city, Toast.LENGTH_SHORT).show();
			} else {
				String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
				Log.e("AmapErr",errText);
			}
		}
	}
	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
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
	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
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
		default:
			break;
		}
	}
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
