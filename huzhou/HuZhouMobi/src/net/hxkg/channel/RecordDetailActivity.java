package net.hxkg.channel;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.huzhouport.R;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecordDetailActivity extends Activity implements OnTouchListener
{
	MapView mMapView = null;
	BaiduMap mBaiduMap;
	BitmapDescriptor startMarker,endMarker;
	List<LatLng> trackPoints = new ArrayList<LatLng>();
	CruiseRecord cruiseRecord;
	TextView users,time,meters,tool,issuenum;
	LinearLayout infoLayout;
	IssueFragment issFragment;
	final float MAXY=700.0f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recorddetailactivity);
		cruiseRecord=(CruiseRecord) getIntent().getSerializableExtra("CruiseRecord");
		
		try {
			initMap();
			initView();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void onBack(View view)
	{
		this.finish();
	}
	
	private void initMap() throws JSONException 
	{
		mMapView = (MapView) findViewById(R.id.bdmap); 
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMyLocationEnabled(true);
		startMarker = BitmapDescriptorFactory  .fromResource(R.drawable.map_start_point);
		endMarker = BitmapDescriptorFactory  .fromResource(R.drawable.map_end_point);
		JSONArray trackArray=new JSONArray(cruiseRecord.getTrackArray());
		for(int i=0;i<trackArray.length();i++)
		{
			JSONObject trackPoint=trackArray.getJSONObject(i);
			trackPoints.add(new LatLng(trackPoint.getDouble("lat"), trackPoint.getDouble("lon")));
		}
		MapStatus mMapStatus= new MapStatus.Builder()
        .target(new LatLng(30.86, 120.1)) //
        .zoom(16.0F)
        .build();
		if(trackPoints.size()>=3) 
		{
			LatLng startLatLng=trackPoints.get(0);
			LatLng endLatLng=trackPoints.get(trackPoints.size()-1);
			OverlayOptions option1= new MarkerOptions()  
		    .position(startLatLng)  
		    .icon(startMarker);  
			mBaiduMap.addOverlay(option1);
			OverlayOptions option2= new MarkerOptions()  
		    .position(endLatLng)  
		    .icon(endMarker);  			
			mBaiduMap.addOverlay(option2);
			
			OverlayOptions ooPolyline = new PolylineOptions().width(12).color(0xAAFF0000).points(trackPoints);
			//.customTextureList(customList).textureIndex(index);
			mBaiduMap.addOverlay(ooPolyline);
			
			mMapStatus= new MapStatus.Builder()
	        .target(trackPoints.get(trackPoints.size()/2)) 
	        .zoom(16.0F)
	        .build();
		}	
		
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
	}
	private void initView() throws JSONException
	{
		users=(TextView) findViewById(R.id.users);
		time=(TextView) findViewById(R.id.time);
		meters=(TextView) findViewById(R.id.meters);
		tool=(TextView) findViewById(R.id.tool);
		issuenum=(TextView) findViewById(R.id.issuenum);
		if(cruiseRecord==null) return;
		JSONArray userArray=new JSONArray(cruiseRecord.getUserArray());
		String useString="";
		for(int i=0;i<userArray.length();i++)
		{
			JSONObject user=userArray.getJSONObject(i);
			String nameString=user.getString("name");
			if("".equals(useString))
			{
				useString+=nameString;
			}else {
				useString+=","+nameString;
			}
		}
		users.setText(useString);
		time.setText(cruiseRecord.getStartTime()+"~"+cruiseRecord.getEndTime().split(" ")[1]);
		meters.setText(String.valueOf(cruiseRecord.getMeters()));
		tool.setText(cruiseRecord.getTool());
		issuenum.setText(String.valueOf(cruiseRecord.getIssues()));
		
		FragmentManager fragmentManager=getFragmentManager();
		issFragment =new IssueFragment();
		Bundle bundle=new Bundle();
		bundle.putSerializable("CruiseRecord", cruiseRecord);
		issFragment.setArguments(bundle);
		fragmentManager.beginTransaction().replace(R.id.frame, issFragment).commit();
		infoLayout=(LinearLayout) findViewById(R.id.info);
		infoLayout.setOnTouchListener(this);
		infoLayout.setTranslationY(MAXY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==200)
		{
			int num=Integer.parseInt(issuenum.getText().toString());
			issuenum.setText(String.valueOf(num-1));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	float firstY; 
	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			firstY=event.getY();
		}
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			//issFragment.Invalidate();
		}
		return new GestureDetector(new Custom()).onTouchEvent(event);
	}
	
	class Custom extends GestureDetector.SimpleOnGestureListener
	{
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,float distanceX, float distanceY) 
		{
			//=e2.getY();
			float deltY=e2.getY()-firstY;
			float ty= infoLayout.getTranslationY()+deltY;
			System.out.println(ty);
			if(ty<0||ty>MAXY)
			{
				return true;
			}
			infoLayout.setTranslationY(ty);
			//infoLayout.layout(infoLayout.getLeft(), infoLayout.getTop(), infoLayout.getRight(),(int) (infoLayout.getBottom()-infoLayout.getTranslationY()));
			
			return true;
		}

		@Override
		public boolean onDown(MotionEvent e) 
		{
			
			return true;
		}
		
	}	
}
