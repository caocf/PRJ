package net.hxkg.channel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import net.hxkg.channel.HttpRequest.onResult;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.slidemenu.MainActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CruiseMapAcitvity extends Activity implements BDLocationListener,OnCheckedChangeListener,onResult,OnLongClickListener
{
	MapView mMapView = null;
	BaiduMap mBaiduMap;
	LocationClient locationClient;
	BitmapDescriptor mCurrentMarker;
	boolean location=true;
	
	TextView speedTextView,timeTextView,cover;
	long startTime,nowTime;
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static boolean isRunning=false;
	boolean isPausing=false;
	Handler timeHandler=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
			String nowString=simpleDateFormat.format(nowTime-startTime);
			
			timeTextView.setText(nowString);
			super.handleMessage(msg);
		}		
	};
	List<LatLng> trackPoints = new ArrayList<LatLng>();
	List<LatLng> tempPoints = new ArrayList<LatLng>();
	Overlay trackOverlay=null;
	int totalMeters=0;
	CheckBox checkBox;
	Thread timing;
	
	String userids,tool,usernames;
	
	int id;
	final int ISSUE_REQUEST=1;
	final int ISSUE_LIST=2;
	Map<String,Object> commitMap=new HashMap<>();
	ProgressDialog progressDialog;
	LatLng currentP;
	TextView ilistTextView;
	int inum=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.cruisemap_activity);
		initMap();
		initView();
		nowTime=startTime=new Date().getTime();
		commitRecord();//初始化完毕
		isRunning=true;
		
		timeTick();
	}
	
	private void  initView() 
	{
		speedTextView=(TextView) findViewById(R.id.speed);
		timeTextView=(TextView) findViewById(R.id.time);
		cover=(TextView) findViewById(R.id.cover);
		cover.setOnTouchListener(new View.OnTouchListener()
		{			
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				return true;
			}
		});
		checkBox=(CheckBox) findViewById(R.id.pause);
		checkBox.setOnCheckedChangeListener(this);
		ilistTextView=(TextView) findViewById(R.id.ilist);
	}
	
	private void timeTick() 
	{		
		timing= new Thread(new Runnable() 
		{			
			@Override
			public void run() 
			{
				while(isRunning&&!isPausing)
				{
					try 
					{
						nowTime+=500;
						timeHandler.sendEmptyMessage(1);
						Thread.sleep(500);
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
								
			}
		});
		timing.start();
	}

	private void initMap()
	{
		mMapView = (MapView) findViewById(R.id.bdmap); 
		mMapView.setOnLongClickListener(this);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMyLocationEnabled(true);
		mCurrentMarker = BitmapDescriptorFactory  .fromResource(R.drawable.positioning);
		locationClient=new LocationClient(getApplicationContext());
		initLocation();
		if(!isOPen())
		{
			openLocationSet();
		}
		locationClient.start();	
		
	}
	
	private void  drawTrack() 
	{
		if(currentP!=null)
		{
			trackPoints.add(currentP);
		}
		if(trackPoints.size()<3)return;
		if(trackOverlay!=null)trackOverlay.remove();		
		OverlayOptions ooPolyline = new PolylineOptions().width(12).color(0xAAFF0000).points(trackPoints);
		//.customTextureList(customList).textureIndex(index);
		trackOverlay=mBaiduMap.addOverlay(ooPolyline);	
		trackPoints.remove(trackPoints.size()-1);
	}
	

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{		
		super.onActivityResult(requestCode, resultCode, data);
		if(!isOPen())
		{
			openLocationSet();
		}
		if(requestCode==ISSUE_REQUEST&&resultCode==200)
		{
			inum+=1;
			String numString=inum>0?String.valueOf(inum):"";
			ilistTextView.setText("问题列表"+numString);
		}
	}

	public void onBack(View view)
	{
		Intent intent=new Intent(this,MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(intent);
	}
	
	public void onLocation(View view)
	{
		if(isOPen())
		{
			location=true;
		}
		else {
			Toast.makeText(this, "无法定位,请设置定位权限", Toast.LENGTH_SHORT).show();
		}
	}
	
	public   void openLocationSet() 
	{
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("GPS定位权限未开启");
		builder.setPositiveButton("去开启", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		        startActivityForResult(intent, 0);
			}
		});
		builder.setNegativeButton("结束", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				isRunning=false;
				CruiseMapAcitvity.this.finish();
				
			}
		});
		builder.setCancelable(false);
		builder.create().show();  
    }
	
	private void initLocation()
	{
	    LocationClientOption option = new LocationClientOption();
	    //option.setLocationMode(MyLocationConfiguration.LocationMode.NORMAL);
	    //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
	 
	    option.setCoorType("bd09ll");
	    //可选，默认gcj02，设置返回的定位结果坐标系
	 
	    int span=5000;
	    option.setScanSpan(span);
	    //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
	 
	    option.setIsNeedAddress(true);
	    //可选，设置是否需要地址信息，默认不需要
	 
	    option.setOpenGps(true);
	    //可选，默认false,设置是否使用gps
	 
	    option.setLocationNotify(true);
	    //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
	    
	 
	    option.setIsNeedLocationDescribe(true);
	    //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
	 
	    option.setIsNeedLocationPoiList(true);
	    //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
	 
	    option.setIgnoreKillProcess(false);
	    //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死  
	 
	    option.SetIgnoreCacheException(false);
	    //可选，默认false，设置是否收集CRASH信息，默认收集
	 
	    option.setEnableSimulateGps(false);
	    //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
	 
	    locationClient.setLocOption(option);
	    locationClient.registerLocationListener( this );
	    
	    MapStatus mMapStatus = new MapStatus.Builder()
	    .target(new LatLng(30.86, 120.1))
        .zoom(18f)        
        .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
	}
	
	@Override  
    protected void onDestroy() 
	{  
		if(isRunning)
		{
			Toast.makeText(this, "巡航结束,数据已保存", Toast.LENGTH_SHORT).show();
		}
        super.onDestroy();  
        locationClient.stop();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy(); 
    }  
    @Override  
    protected void onResume() 
    {    	
    	
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
    }
    
    @Override  
    protected void onPause()
    {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
    }

	@Override
	public void onConnectHotSpotMessage(String arg0, int arg1)
	{
		
	}
	
	public void  onMatter(View view)
	{
		//
		Intent intent=new Intent(this,TypeActivity.class);
		intent.putExtra("recordid", id);
		startActivityForResult(intent, ISSUE_REQUEST);
	}
	
	public  boolean isOPen() 
	{  
         LocationManager locationManager  = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  
         // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）  
         boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);  
         // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）  
         ///boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);  
         if (gps) {  
             return true;  
         }  
   
         return false;  
     }

	@Override
	public void onReceiveLocation(final BDLocation location)
	{
		if(!isOPen())//定位功能未开启
		{
			//
			return;	
		}
		currentP=new LatLng(location.getLatitude(),location.getLongitude());
		LatLng nowLatLng=new LatLng(location.getLatitude(),location.getLongitude());
		// 构造定位数据  
		MyLocationData locData = new MyLocationData.Builder()  
		    .accuracy(location.getRadius())  
		    // 此处设置开发者获取到的方向信息，顺时针0-360  
		    /*.direction(100)*/.latitude(location.getLatitude())  
		    .longitude(location.getLongitude()).build();  
		// 设置定位数据  
		mBaiduMap.setMyLocationData(locData);  
		// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）	
		MyLocationConfiguration config = new MyLocationConfiguration(LocationMode.NORMAL , true, mCurrentMarker);		
		mBaiduMap.setMyLocationConfiguration(config);		
		
		MapStatus mMapStatus = new MapStatus.Builder()
        .target(nowLatLng)               
        .build();
		
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        if(this.location)
        {
        	mBaiduMap.setMapStatus(mMapStatusUpdate);
        	this.location=false;
        }
        
        runOnUiThread(new Runnable() 
        {			
			@Override
			public void run() 
			{
				speedTextView.setText(String.valueOf(location.getSpeed()));
				
			}
		}) ;
        
    	if(!isPausing)
    	{    		
    		tempPoints.add(nowLatLng);
    		long distance=ieratorDistance();
    		if(distance>500)
    		{
    			trackPoints.add(new LatLng(location.getLatitude(),location.getLongitude()));    			
    			tempPoints.clear();
    			totalMeters+=500;
    		}
    		drawTrack();
    		    		
    		runOnUiThread(new Runnable() 
            {			
    			@Override
    			public void run() 
    			{
    				speedTextView.setText(String.valueOf(location.getSpeed())/*distance+"-"+totalMeters
    						+"-"+lastLatLng.latitude+"-"+lastLatLng.longitude*/);
    				
    			}
    		});
    	}
             
	} 
	private long ieratorDistance()
	{
		long distance=0;
		for(int i=1;i<tempPoints.size();i++)
		{
			distance+=getDistance(tempPoints.get(i-1),tempPoints.get(i));
			
		}
		
		return distance;
	}
	
	public void onFinishCruise(View view)
	{
		String tipString=totalMeters>1000?"确定结束此次巡航?":"确定结束此次巡航?";
		final int status=totalMeters>1000?1:1;
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(tipString);
		builder.setCancelable(false);
		builder.setPositiveButton("结束并保存", new OnClickListener() 
		{			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				commitData(status);				
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() 
		{			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				
			}
		});
		builder.create().show();
		
	}
	
	  public double getDistance(LatLng start,LatLng end){  
	        double lat1 = (Math.PI/180)*start.latitude;  
	        double lat2 = (Math.PI/180)*end.latitude;  
	          
	        double lon1 = (Math.PI/180)*start.longitude;  
	        double lon2 = (Math.PI/180)*end.longitude;  
	          
//	      double Lat1r = (Math.PI/180)*(gp1.getLatitudeE6()/1E6);  
//	      double Lat2r = (Math.PI/180)*(gp2.getLatitudeE6()/1E6);  
//	      double Lon1r = (Math.PI/180)*(gp1.getLongitudeE6()/1E6);  
//	      double Lon2r = (Math.PI/180)*(gp2.getLongitudeE6()/1E6);  
	          
	        //地球半径  
	        double R = 6371;  
	          
	        //两点间距离 km，如果想要米的话，结果*1000就可以了  
	        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;  
	          
	        return d*1000;  
	  }
	
	private void commitData(int status) 
	{
		updateRecord(status);
		
	}
	
	public void onBackPressed()
	{
		Intent intent=new Intent(this,MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(intent);
		//moveTaskToBack(true);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		isPausing=isChecked;
		
		/*if(!isChecked&&timing.isAlive())
		{
			//checkBox.setChecked(!isChecked);
			return;
		}*/
		if(isChecked)//暂停
		{
			checkBox.setText("继续巡航");
			cover.setVisibility(View.VISIBLE);
			checkBox.setEnabled(false);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while (timing.isAlive())
					{				
						
					}
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							checkBox.setEnabled(true);
							
						}
					});
					
				}
			}).start();
			
		}
		else //继续 false
		{
			checkBox.setText("暂停巡航");
			cover.setVisibility(View.GONE);
			timeTick() ;
		}		
		
	}
	
	private void commitRecord() 
	{				
		userids=getIntent().getStringExtra("userids");
		usernames=getIntent().getStringExtra("usernames");
		if(userids==null)return;
		tool=getIntent().getStringExtra("tool");
		HttpRequest httpRequest=new HttpRequest(this);
	
		String sidString[]=userids.split(",");
		for(int i=0;i<sidString.length;i++)
		{
			commitMap.put("userids["+i+"].userId", sidString[i]);
		}
		commitMap.put("cruiseRecord.tool", tool);
		commitMap.put("cruiseRecord.status", 0);
		commitMap.put("cruiseRecord.startTime", simpleDateFormat1.format(new Date(startTime)));
		
		httpRequest.post(HttpUtil.BASE_URL+"commitCruise", commitMap);
	}
	
	public void onIssueList(View view) throws JSONException
	{
		Intent intent=new Intent(this,IssueListActivity.class);
		intent.putExtra("recordid", id);
		CruiseRecord cruiseRecord=new CruiseRecord();
		cruiseRecord.setId(id);
		String names[]=usernames.split(",");
		JSONArray nameArray=new JSONArray();		
		for(String name:names)
		{
			JSONObject user=new JSONObject();
			user.put("name", name);
			nameArray.put(user);
		}		
		cruiseRecord.setUserArray(nameArray.toString());
		cruiseRecord.setStartTime(simpleDateFormat1.format(new Date(startTime)));
		cruiseRecord.setEndTime(simpleDateFormat1.format(new Date(nowTime)));
		intent.putExtra("CruiseRecord", cruiseRecord);
		startActivityForResult(intent, ISSUE_LIST);
	}
	
	private void updateRecord(int status)
	{
		HttpRequest httpRequest=new HttpRequest(new onResult() 
		{			
			@Override
			public void onSuccess(String result) 
			{
				if(progressDialog!=null)progressDialog.dismiss();
				Toast.makeText(CruiseMapAcitvity.this, "提交成功", Toast.LENGTH_SHORT).show();
				CruiseMapAcitvity.isRunning=false;
				setResult(200);
				NotificationManager notifym=(NotificationManager)CruiseMapAcitvity.this.getSystemService(NOTIFICATION_SERVICE);
				notifym.cancel(1001);
				CruiseMapAcitvity.this.finish();
				
			}
			
			@Override
			public void onError(int httpcode) 
			{
				
			}
		});
		
		commitMap.put("cruiseRecord.id", id);
		commitMap.put("cruiseRecord.meters", totalMeters);
		commitMap.put("cruiseRecord.status", status);
		commitMap.put("cruiseRecord.startTime", simpleDateFormat1.format(new Date(startTime)));
		commitMap.put("cruiseRecord.endTime", simpleDateFormat1.format(new Date(nowTime)));
		//int step
		for(int i=0;i<trackPoints.size();i++)
		{
			commitMap.put("tracks["+i+"].lat", trackPoints.get(i).latitude);
			commitMap.put("tracks["+i+"].lon", trackPoints.get(i).longitude);
		}
		
		httpRequest.post(HttpUtil.BASE_URL+/*"http://192.168.1.135:6080/HuZhouPort/"+*/"updateCruise", commitMap);
		progressDialog=new ProgressDialog(this);
		progressDialog.setMessage("提交中...");
		progressDialog.show();
	}

	@Override
	public void onSuccess(String result) 
	{
		try {
			JSONObject object=new JSONObject(result.trim());
			JSONObject recordJsonObject=object.getJSONObject("cruiseRecord");
			id=recordJsonObject.getInt("id");
			
		} catch (Exception e) 
		{
		}
		
	}

	@Override
	public void onError(int httpcode)
	{
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("数据传输失败,请检查网络");
		
		builder.setNegativeButton("结束", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				isRunning=false;
				CruiseMapAcitvity.this.finish();
				
			}
		});
		builder.setCancelable(false);
		builder.create().show();
		
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onLongClick(View v)
	{
		onFinishCruise(null);
		return false;
	}

	
}
