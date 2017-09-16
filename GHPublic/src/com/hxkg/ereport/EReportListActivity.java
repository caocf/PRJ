package com.hxkg.ereport;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.ab.view.wheel.AbWheelView;
import com.ab.view.wheel.AbWheelView.AbOnWheelChangedListener; 
import com.hxkg.ghpublic.R; 
import com.hztuen.gh.contact.contants;
import net.hxkg.network.HttpRequest;
import android.app.Activity;  
import android.os.Bundle;  
import android.view.View; 
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

public class EReportListActivity extends Activity implements HttpRequest.onResult,AbOnWheelChangedListener
{	 
	ShipSpinner shipsp;
	StatusSpinner statussp;
	int status,shipname;
	ListView listview;
	EAdapter adapter;
	List<EReportModol> datalist=new ArrayList<>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ereportlsit);  
		initView();
		
	} 
	
	public void  onViewClick(View v)
	{ 
	
		switch(v.getId())
		{
		case R.id.back:finish();
			
		}
	}
	
	public void freshlist()
	{
		if(statussp.getSelectedItemPosition()==0)
			status=-1;
		else
		{
			status=statussp.getSelectedItemPosition();
		}
		RequestData(status,(String)shipsp.getSelectedItem());
	}
	
	private void initView()
	{
		shipsp=(ShipSpinner) this.findViewById(R.id.ship);
		shipsp.setID(""); 
		shipsp.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) 
			{
				freshlist();	
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		statussp=(StatusSpinner) this.findViewById(R.id.statussp);
		statussp.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				freshlist();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		listview=(ListView) this.findViewById(R.id.list);
		adapter=new EAdapter(this,datalist);
		listview.setAdapter(adapter);
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		freshlist();
	}
	
	 
	private void RequestData(int StatusID,String shipname)
	{
		HttpRequest hr=new HttpRequest(this);
		Map<String,Object> map =new HashMap();
		map.put("StatusID", StatusID);
		map.put("Shipname", shipname);
		hr.post(contants.baseUrl+"ERePortByStatusAndShip", map);
	}
 

	@Override
	public void onSuccess(String result) 
	{ 
		datalist.clear();
		String res=result.replace("\\", "");
		String s=res.substring(1,res.length()-1);
		try {
			JSONArray arr=new JSONArray(s);
			for(int i=0;i<arr.length();i++)
			{
				EReportModol modol=new EReportModol();
				JSONObject obj=arr.getJSONObject(i);
				modol.id=obj.getString("id");
				modol.shipname=obj.getString("shipname");
				JSONObject goodsobj=obj.getJSONObject("goodstypeEN");				
				modol.goods=goodsobj.getString("typname");
				JSONObject fromobj=obj.getJSONObject("startport");				
				modol.from=fromobj.getString("portname");
				JSONObject toobj=obj.getJSONObject("endport");				
				modol.to=toobj.getString("portname");
				JSONObject reportobj=obj.getJSONObject("reportclassEN");				
				modol.reportid=reportobj.getString("name");
				JSONObject inoutobj=obj.getJSONObject("reporttypeEN");				
				modol.inout=inoutobj.getString("reporttype");
				modol.time=obj.getString("porttime");
				modol.goodscount=obj.getString("goodscount");
				modol.gascount=obj.getString("lastfuelcount");
				modol.gastime=obj.getString("lastfueltime");
				JSONObject unitobj=obj.getJSONObject("goodsunitEN");
				modol.unit=unitobj.getString("unitname");
				
				JSONArray jsonArray=obj.getJSONArray("shipEreporteditENSet");
				//modol.arr=jsonArray.toString().trim();
				for(int j=0;j<jsonArray.length();j++)
				{
					JSONObject object=jsonArray.getJSONObject(j);
					Map<String, Object> map=new HashMap<>();
					map.put("portEN", object.get("portEN").toString());
					map.put("porttime", object.get("porttime").toString());
					map.put("uptime", object.get("uptime").toString());
					modol.list.add(map);
				}
				
				datalist.add(modol);
			}
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onError(int httpcode) 
	{ 
		
	}
	  

	@Override
	public void onChanged(AbWheelView wheel, int oldValue, int newValue) 
	{
		
	}
}
