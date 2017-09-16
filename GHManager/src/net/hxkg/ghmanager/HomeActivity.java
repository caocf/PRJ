package net.hxkg.ghmanager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;
import net.hxkg.company.CompanyInfoActivity; 
import net.hxkg.cruise.CruiseActivity;
import net.hxkg.lawexcut.IllegalListActivity;
import net.hxkg.lawexcut.LawActivity;  
import net.hxkg.network.MService;
import net.hxkg.news.MainFramentNewsActivity;
import net.hxkg.office.OfficeActivity;
import net.hxkg.patrolboat.SearchBoatActivity; 
import net.hxkg.report.EReportListActivity;
import net.hxkg.report.OptionActivity;
import net.hxkg.ship.SearchShipActivity;
import net.hxkg.simple.SimpleLawActivity;
import net.hxkg.supervise.SuperviseActivity;
import net.hxkg.user.User;
import net.hxkg.user.UserActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomeActivity extends Activity implements OnItemClickListener,LocationListener,ICNMKLocationListener
{
	GridView gridView;	
	MainAdapter adapter;
    List<Map<String, Object>> data_list;
    final String[] fromdata={"icon","text"};
    final int[] toview={R.id.griditem_image,R.id.griditem_text};
    final int[] icon ={R.drawable.ic_home_mobilecruising,R.drawable.ic_home_violate,R.drawable.ic_home_goodstask
			,R.drawable.ic_home_ereport,R.drawable.ic_home_boatsearch,R.drawable.ic_home_companysearch,
			/*,R.drawable.ic_home_aissearch,R.drawable.icon_xunhang,*/R.drawable.ic_home_otherboat
			,R.drawable.ic_home_infoshipping,R.drawable.ic_home_notify,R.drawable.ic_home_daily};
    final String[] iconName ={"移动巡航","违章审核","危货申报管理","电子报告","船舶查询","企业查询",
    		/*"AIS核查","船员查询","航道巡查",*/"非营运船舶"
			,"港航新闻","通知公告","日常办公"};
    
    final Class<?>[] actionPage = {
    		CruiseActivity.class,
    							   IllegalListActivity.class,
    							   OptionActivity.class,
    							   EReportListActivity.class,
    							   SearchShipActivity.class,
    							   CompanyInfoActivity.class,
    							   //AISCheckActivity.class,    							  
    							   //Channel1Activity.class,    							  
    							   SearchBoatActivity.class,
    							   MainFramentNewsActivity.class,
    							   MainFramentNewsActivity.class,
    							   OfficeActivity.class};    
 
    int out=2;
    ProgressDialog locationDialog = null;
    LocationManager l;
	public static CNMKLocation nowLocation=null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_home);
		
		gridView=(GridView) findViewById(R.id.gridView);
		data_list=getData();		
		adapter=new MainAdapter(this,data_list);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);		
		
		Intent intent=new Intent(this,MService.class);
		startService(intent);
		//定位
		l=(LocationManager) getSystemService(Context.LOCATION_SERVICE);	
		if(CenApplication.mApp!=null)
		{
			CenApplication.mApp.mCNMKAPImgr.getLocationManager().requestLocationUpdates(this);
			CenApplication.mApp.mCNMKAPImgr.start();
		}else {
			
			l.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, this);
		}
		
	}	
	
	public void onViewClick(View v)
	{
		switch (v.getId()) 
		{
		case R.id.user:
			Intent intent =new Intent(HomeActivity.this,UserActivity.class);
			startActivity(intent);
			break;
		case R.id.law:
			if(!User.checkpermit(LawActivity.class.getSimpleName()))
			{
				Toast.makeText(this, "您没有相关权限！", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent1 =new Intent(HomeActivity.this,LawActivity.class);
			startActivity(intent1);
			break;
		case R.id.simple:
			if(!User.checkpermit(SimpleLawActivity.class.getSimpleName()))
			{
				Toast.makeText(this, "您没有相关权限！", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent2 =new Intent(HomeActivity.this,SimpleLawActivity.class);			
			startActivity(intent2);
			
			break;
		case R.id.supervize:
			if(!User.checkpermit(SuperviseActivity.class.getSimpleName()))
			{
				Toast.makeText(this, "您没有相关权限！", Toast.LENGTH_SHORT).show();
				return;
			}
			locationDialog= new ProgressDialog(this);
			locationDialog.setMessage("地图加载中");
			locationDialog.setCancelable(false);
			locationDialog.show();
			Intent intent3 =new Intent(HomeActivity.this,SuperviseActivity.class);
			startActivity(intent3);
			break;

		default:
			break;
		}
	}	
	
	@Override
	protected void onResume() 
	{
		if(locationDialog!=null)
			locationDialog.dismiss();
		adapter.setRegion();
		adapter.notifyDataSetChanged();
		
		super.onResume();
	}
	@Override
	protected void onDestroy() 
	{
		if(CenApplication.mApp!=null)
		{
			CenApplication.mApp.mCNMKAPImgr.getLocationManager().removeUpdates(this);
			CenApplication.mApp.mCNMKAPImgr.stop();
		}else {
			l.removeUpdates(this);
		}		
		
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		Intent intent=new Intent();
		if(position==7)			
		{
			intent.putExtra("model", 0);
		}
		else if(position==8)//通知公告
		{
			intent.putExtra("model", 1);
		}
		
		if(!User.checkpermit(actionPage[position].getSimpleName()))
		{
			Toast.makeText(this, "您没有相关权限！", Toast.LENGTH_SHORT).show();
			return;
		}
		intent.setClass(this, actionPage[position]);
		startActivity(intent);
		
	}
	
	private List<Map<String, Object>> getData()
	{
		List<Map<String, Object>> data_list=new ArrayList<Map<String, Object>>();
		for(int i=0;i<icon.length;i++)
		{
			Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
		}		
		return data_list;
	} 
	
	@Override
	public void onBackPressed()
	{
		out--;
		if(out>=1)
		{
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
		}
		else if(out==0)
		{
			finish();
		}
		
	}

	@Override
	public void onLocationChanged(Location location) 
	{
		//if(location==null)return;
		
		//nowLocation=location;	
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGPSStatusChanged(GpsStatus arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(CNMKLocation arg0) {
		if(arg0==null)return;
		
		nowLocation=arg0;
		
	}
}
