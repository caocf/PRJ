package net.hxkg.ship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.hxkg.ghmanager.R;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.contants;
import net.hxkg.network.HttpRequest.onResult;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tuen.util.SystemStatic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class LetInRecordActivity extends Activity implements OnClickListener,onResult,OnItemClickListener
{	
	private List<RecordLetIn> recordlist = new ArrayList<RecordLetIn>();
	private RecordLetInAdapter recordAdapter;
	private ListView lv_record;
	private ImageView permission_list_back;
	private RelativeLayout relative_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_letin_record);
		init();
		GetRecord();
		
	}
	void init()
	{
		lv_record=(ListView)findViewById(R.id.listview_record);
		recordAdapter = new RecordLetInAdapter(LetInRecordActivity.this, recordlist);
		lv_record.setOnItemClickListener(this);
		permission_list_back=(ImageView)findViewById(R.id.permission_list_back);
		permission_list_back.setOnClickListener(this);
		
		relative_title=(RelativeLayout)findViewById(R.id.relative_title);
		relative_title.setOnClickListener(this);
	}
	
	
	
	private void GetRecord() 
	{
		String toUrl = contants.dangerlist;
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("Shipname", SystemStatic.searchShipName);
		hr.post(toUrl, map);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.permission_list_back:
			finish();
			break;
			
		case R.id.relative_title:
			finish();
			break;
			
		}
		
	}
	@Override
	public void onSuccess(String result) 
	{
		try {
			JSONObject resultJO = new JSONObject(result);
	
			JSONObject  res = new JSONObject(result);
			JSONArray data = res.getJSONArray("data");
			Log.i("GH_TEXT", data.length()+"我是数据的size");
			
			for(int i = 0;i<data.length();i++){
				JSONObject temp = data.getJSONObject(i);
				RecordLetIn record= new RecordLetIn();
				//RegionList region = new RegionList();
//				
//				 "id": 6,
//			      "start": "杭州",
//			      "berthtime": "2016-6-20",
//			      "status": "待审批",
//			      "target": "上海",
//			      "goods": "散货",
//			      "rank": "爆炸品",
//			      "tons": "100",
//			      "unit": "吨",
////			  "number": "12423000"
				
				
				record.setberthtime(temp.getString("berthtime"));
				record.setgoods(temp.getString("goods"));
				record.setnumber(temp.getString("number"));
				record.setrank(temp.getString("rank"));
				record.setstart(temp.getString("start"));
				record.setstatus(temp.getString("status"));
				record.settarget(temp.getString("target"));
				record.settons(temp.getString("tons"));
				record.setunit(temp.getString("unit"));
				record.setId(temp.getString("id"));
																																						
				recordlist.add(record);
																																	
			}
			lv_record.setAdapter(recordAdapter);												
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
	public void modify(View v)
	{
		
		
		Intent intent=new Intent(this,DangersLetInActivity.class);
		startActivity(intent);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		System.out.println(id);
		
	}
	

}
