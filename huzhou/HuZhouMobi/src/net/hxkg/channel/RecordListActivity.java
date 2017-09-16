package net.hxkg.channel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import net.hxkg.channel.HttpRequest.onResult;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RecordListActivity extends Activity implements OnItemClickListener,onResult
{
	ListView listView;
	RecordAdapter recordAdapter;
	List<CruiseRecord> dataList=new ArrayList<>();	
	
	User user;
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	TextView tipTextView;
	ProgressDialog progressDialog=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recordlistactivity);
		user=(User) getIntent().getSerializableExtra("User");
		initListView();
		
	}
	@Override
	public void onResume() {
		initData() ;
		super.onResume();
	}
	
	private void initListView()
	{
		tipTextView=(TextView) findViewById(R.id.tip);	
		listView=(ListView) findViewById(R.id.listview);
		recordAdapter=new RecordAdapter(this,dataList);
		listView.setAdapter(recordAdapter);
		listView.setOnItemClickListener(this);
	}
	
	private void  initData() 
	{
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("pid", user.getUserId());
		httpRequest.post(HttpUtil.BASE_URL+"findRecordsByUserid", map);
		progressDialog=new ProgressDialog(this);
		progressDialog.setMessage("数据加载中..");
		progressDialog.show();
	}
	
	
	public void onBack(View view)
	{
		this.finish();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		Intent intent=new Intent(this,RecordDetailActivity.class);
		intent.putExtra("CruiseRecord", dataList.get(position));
		
		startActivity(intent);
	}

	@Override
	public void onSuccess(String result) 
	{
		try 
		{
			tipTextView.setVisibility(View.GONE);
			dataList.clear();
			JSONObject object=new JSONObject(result.trim());
			JSONArray array=object.getJSONArray("dataList");
			for(int i=0;i<array.length();i++)
			{
				JSONObject recordJsonObject=array.getJSONObject(i);
				int meters=recordJsonObject.getInt("meters");
				int id=recordJsonObject.getInt("id");
				String starttime=recordJsonObject.getString("startTime");
				String endtime=recordJsonObject.getString("endTime");
				String tool=recordJsonObject.getString("tool");
				JSONArray tacks=recordJsonObject.getJSONArray("tacks");
				JSONArray users=recordJsonObject.getJSONArray("userids");
				int issues=recordJsonObject.getInt("issues");
				
				CruiseRecord cruiseRecord=new CruiseRecord();
				cruiseRecord.setId(id);
				cruiseRecord.setMeters(meters);
				cruiseRecord.setStartTime(starttime);
				cruiseRecord.setEndTime(endtime);
				cruiseRecord.setUserArray(users.toString());
				cruiseRecord.setTrackArray(tacks.toString());
				cruiseRecord.setTool(tool);
				cruiseRecord.setIssues(issues);
				
				dataList.add(cruiseRecord);
			}
		} catch (Exception e) 
		{
			// TODO: handle exception
		}
		recordAdapter.notifyDataSetChanged();
		if(dataList.size()<=0)
		{
			tipTextView.setVisibility(View.VISIBLE);
		}
		if(progressDialog!=null)
			progressDialog.dismiss();
	}

	@Override
	public void onError(int httpcode) 
	{
		if(progressDialog!=null)
			progressDialog.dismiss();
		
	}
	
	
}
