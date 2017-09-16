package net.hxkg.cruise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray; 
import org.json.JSONObject;
import net.hxkg.ghmanager.R; 
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest; 
import android.app.Activity;  
import android.content.Intent;
import android.graphics.Bitmap; 
import android.os.Bundle;
import android.view.View; 
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView; 
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CruiseDetailActivity extends Activity implements OnItemClickListener,HttpRequest.onResult
{	
	public static Map<Integer,Bitmap> map_bitmap=new HashMap<>();
	int position;
	CruiseRecordEN cruiseRecordEN=null;	
	
	ListView listView;
	SimpleAdapter cruiseAdapter;
	List<Map<String, Object>> datalist=new ArrayList<>(); 
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final int REQUEST=41;
	
	List<LogModel> loglistList=new ArrayList<>(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_cruisedetail);
		
		cruiseRecordEN=(CruiseRecordEN) getIntent().getSerializableExtra("CruiseRecordEN");
		position=getIntent().getIntExtra("position", 0);
		initView();
		initListView();
	}
	
	private void  initView()
	{
		((TextView)findViewById(R.id.time)).setText(cruiseRecordEN.getPeriod());
		((TextView)findViewById(R.id.miles)).setText(cruiseRecordEN.getMiles());		
		((ImageView)findViewById(R.id.image)).setImageBitmap(map_bitmap.get(position));
	}
	
	private void  initListView()
	{
		listView=(ListView) findViewById(R.id.list);
		cruiseAdapter=new SimpleAdapter(this,datalist,R.layout.lawlist_item
									,new String[]{"title","status","content"}
									,new int[]{R.id.title,R.id.status,R.id.content});
		listView.setAdapter(cruiseAdapter);
		listView.setOnItemClickListener(this);
		freshData();
	}
	
	public void onClick(View v)
	{
		finish();
	}
	
	private void  freshData()
	{
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("id",cruiseRecordEN.getId());
		httpRequest.post(Constants.BaseURL+"RecordsByCruiseID", map);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		Intent intent=new Intent(this,RecordInfoActivity.class);
		intent.putExtra("LogModel", loglistList.get(position));
		startActivity(intent);
	}

	@Override
	public void onSuccess(String result) 
	{
		JSONArray dataArray;
		datalist.clear();
		try
		{
			dataArray=new JSONArray(result); 
			for(int i=0;i<dataArray.length();i++)
			{
				JSONObject item=(JSONObject)dataArray.get(i);
				String s1=item.getString("id");
				String s2=item.getString("title");
				String s3=item.getString("content");
				String s4=item.getString("recordtime"); 
				
				JSONArray filesArray=item.getJSONArray("cruiseFileENs");
				
				LogModel lmLogModel=new LogModel();
				lmLogModel.title=s2;
				lmLogModel.location=item.getString("location");
				lmLogModel.content=s3;
				for(int j=0;j<filesArray.length();j++)
				{
					String string=filesArray.getJSONObject(i).getString("dir");
					lmLogModel.fileArray.add(string);
				}
				
				loglistList.add(lmLogModel);
				
				Map<String, Object> map=new HashMap<>();
				map.put("title", s2);
				map.put("status", s4);
				map.put("content", s3);
				datalist.add(map);
			}
			
			cruiseAdapter.notifyDataSetChanged();
			((TextView)findViewById(R.id.record)).setText(String.valueOf(dataArray.length()));
			
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onError(int httpcode) 
	{
		// TODO Auto-generated method stub
		
	}
}
