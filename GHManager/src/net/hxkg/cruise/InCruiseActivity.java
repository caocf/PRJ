package net.hxkg.cruise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.control.CNMKAPImgr;
import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.map.CNMKOverlayItem;
import net.hxkg.ghmanager.CenApplication;
import net.hxkg.ghmanager.R; 
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.GpsStatus;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class InCruiseActivity extends FragmentActivity implements ICNMKLocationListener,HttpRequest.onResult
{   
	CenApplication app;
	CNMKMapView mMapView = null;
	ICNMKLocationListener mLocationListener = null;
	MarkOverLay mayplaceLay;
	
	CruiseRecordEN cruiseRecordEN;
	Date lastDate=new Date();
	long totaltime=0,totalmiles=0,totalthings=0;
	
	public static List<GeoPoint> points=new ArrayList<>();	
	CNMKLocation lastLocation=null;
	String status="0";
	
	TextView miles,things; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_incruise);	
		
		cruiseRecordEN=(CruiseRecordEN) getIntent().getSerializableExtra("CruiseRecordEN");
				
		
		miles=(TextView) findViewById(R.id.miles);
		things=(TextView) findViewById(R.id.things);
		things.setText("0");
		
		app = (CenApplication)this.getApplication();
		if (app.mCNMKAPImgr == null) {
			app.mCNMKAPImgr = CNMKAPImgr.createMgr(getApplication());
			app.mCNMKAPImgr.init(app.mStrKey, new CenApplication.MyGeneralListener());
		}
		app.mCNMKAPImgr.start();		
		mMapView = (CNMKMapView)findViewById(R.id.cnmapView);
		mMapView.setBuiltInZoomControls(false);        
        //mMapView.setDrawOverlayWhenZooming(false);//设置在缩放动画过程中也显示overlay,默认为不绘制
		// 添加定位图层
        Drawable drawable=getResources().getDrawable(R.drawable.ic_directio2);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mayplaceLay=new MarkOverLay(drawable,this,mMapView);
		mMapView.getOverlays().add(mayplaceLay);
		app.mCNMKAPImgr.getLocationManager().requestLocationUpdates(this);
				
	}
	
	
	
	@Override
	protected void onResume() 
	{
		super.onResume();
	}
	
	@Override
	protected void onDestroy() 
	{
		app.mCNMKAPImgr.getLocationManager().removeUpdates(mLocationListener);
		app.mCNMKAPImgr.stop();
		super.onDestroy();
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {
		if(resultCode==800)
		{
			totalthings+=1;
			things.setText(String.valueOf(totalthings));
		}
    }
	
	
	public void onViewClick(View v)
	{
		switch (v.getId()) 
		{
		case R.id.text:
		case R.id.back:
			FinishCruise();
			break;
		case R.id.record:		
			Intent intent1 =new Intent(InCruiseActivity.this,RecordActivity.class);
			intent1.putExtra("cruiseid", cruiseRecordEN.getId());
			startActivityForResult(intent1, 1);
			break;
		case R.id.end:
		
			FinishCruise();
		break;

		default:
			break;
		}
	}
	
	public String saveMyBitmap(Bitmap mBitmap,String fileName)  
	{
        String pathString=Environment
    			.getExternalStorageDirectory()+"/"+fileName + ".png";
		File f = new File(pathString);
		//f.mkdirs();
        FileOutputStream fOut = null;
        try {
                fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
                fOut.flush();
        } catch (IOException e) {
                e.printStackTrace();
        }
        try {
                fOut.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        return pathString;
}
	
	private void FinishCruise()
	{
		String string="";
		if(totalmiles/1000<1)
		{
			string="此次巡航距离不足1公里，无法形成巡航日志，";
			status="0";
		}else
		{
			status="1";
		}
		
		AlertDialog.Builder builder = new Builder(this);		
		builder.setMessage(string+"您确定要结束巡航吗？"); 
		builder.setPositiveButton("提交", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Map<String, Object> map_params=new HashMap<>();
				map_params.put("period", totaltime);
				map_params.put("miles", totalmiles);
				map_params.put("status", status);
				map_params.put("id", cruiseRecordEN.getId());
				
				mMapView.setDrawingCacheEnabled(true);
				
				Bitmap obmp = Bitmap.createBitmap(mMapView.getDrawingCache());
				mMapView.setDrawingCacheEnabled(false);
				String fpath= saveMyBitmap(obmp,String.valueOf(new Date().getTime()));
				File file=new File(fpath);
				
				Map<String, File> map_file=new HashMap<>();
				map_file.put(file.getName(), file);
				
				HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
				{

					@Override
					public void onSuccess(String result) 
					{
						Toast.makeText(InCruiseActivity.this, "提交成功", Toast.LENGTH_LONG).show();				
						InCruiseActivity.this.finish();
						
					}

					@Override
					public void onError(int httpcode) 
					{
						// TODO Auto-generated method stub
						
					}
					
				});
				
				try 
				{
					httpRequest.post(Constants.BaseURL+"updatecruise", map_params,map_file,"map");
				} catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog.dismiss();
				
			}
		});
		builder.setNegativeButton("取消",  new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();			
			}
		});
		
		builder.create().show();
	}

	@Override
	public void onGPSStatusChanged(GpsStatus arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(final CNMKLocation location) 
	{		
		GeoPoint pt = new GeoPoint((int)(location.getLatitude()*(1e6*AA.LV)),
				(int)(location.getLongitude()*(1e6*AA.LV)));
		
		
		Date nowDate=new Date();
		long time=(nowDate.getTime()-lastDate.getTime())/(1000*60);
				
		lastDate=nowDate;
		totaltime+=time;
		if(points.size()>0)
		{
			double dmiles=Distance.Distance(location.getLongitude(), location.getLatitude(),
						//lastLocation.getLongitude(),lastLocation.getLatitude());
						points.get(points.size()-1).lon*1e-6, points.get(points.size()-1).lat*1e-6);
				
				totalmiles+=dmiles;
				//System.out.println(dmiles);
		}
			
			lastLocation=location;
			points.add(pt);
			
		
		//points.add(pt);
		mayplaceLay.Fresh(new CNMKOverlayItem(pt,"1","1"));		
		mMapView.getController().setCenter(pt);		
		miles.setText(String.valueOf(totalmiles));	
		mMapView.mMapMgr.flashMap();
	}
	
	@Override
	public void onBackPressed()
	{
		//System.exit(0);
		FinishCruise();
	}

	@Override
	public void onSuccess(String result) 
	{
		
	}

	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}

}
