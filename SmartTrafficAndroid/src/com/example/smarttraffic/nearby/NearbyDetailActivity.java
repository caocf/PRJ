package com.example.smarttraffic.nearby;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class NearbyDetailActivity extends FragmentActivity
{

	public static final String NEAR_BY_TYPE_ID="near_by_type_id";
	public static final String NEAR_BY_TYPE_NAME="near_by_type_name";
	
	int id;
	String name;
	
	ListView content;
	String tempData;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_nearby_detail);
		
		id=getIntent().getIntExtra(NEAR_BY_TYPE_ID, 0);
		name=getIntent().getStringExtra(NEAR_BY_TYPE_NAME);
		
		initHead(name);
		content=(ListView)findViewById(R.id.nearby_detail_content);
		
		request();
	}
	
	private void initHead(String name)
	{
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName(name);
		fragment.setRightName("地图");
		fragment.setRightListen(new View.OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				Bundle bundle=new Bundle();
				bundle.putString(NearByDetailMapActivity.NEAR_BY_DETATIL_STRING_DATA, tempData);
				
				StartIntent.startIntentWithParam(NearbyDetailActivity.this, NearByDetailMapActivity.class,bundle);			
			}
		});
		
		ManagerFragment.replaceFragment(this, R.id.nearby_detail_head, fragment);
	}
	
	private void request()
	{
		new HttpThread(new BaseSearch(){

			@Override
			public Object parse(String data)
			{
				tempData=data;
				
				List<NearBy> result=new ArrayList<NearBy>();
							
				try
				{
					JSONObject jsonObject=new JSONObject(data);
					JSONArray array=jsonObject.getJSONArray("circles");
					
					for(int i=0;i<array.length();i++)
					{
						NearBy temp=new NearBy();
						
						jsonObject=array.getJSONObject(i);
						
						temp.setAddress(jsonObject.getString("address"));
						temp.setId(jsonObject.getString("id"));
						temp.setLan(jsonObject.getDouble("lan"));
						temp.setLon(jsonObject.getDouble("lon"));
						temp.setName(jsonObject.getString("name"));
						temp.setRegion(jsonObject.getString("region"));
						temp.setStreet(jsonObject.getString("street"));
						temp.setTime(jsonObject.getString("time"));
						
						result.add(temp);
					}
				} catch (JSONException e)
				{
					e.printStackTrace();
				}
								
				return result;
			}}, new BaseRequest(){

				@Override
				public String CreateUrl()
				{
					return HttpUrlRequestAddress.SMART_BUS_CIRCLE_URL+"?id="+id;
				}}, new UpdateView()
		   {
			
			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				content.setAdapter(new NearbyDetailListAdapter(NearbyDetailActivity.this, (List<NearBy>)data));
			}
		},this,R.string.error_nearby_station_info).start();
	}
}
