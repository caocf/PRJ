package net.hxkg.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;

public class ChannelChooseActivity extends Activity implements OnItemClickListener,OnCheckedChangeListener
{
	ListView listView;
	SimpleAdapter simpleAdapter;
	List<Map<String, Object>> dataList=new ArrayList<>();
	
	RadioGroup rg;
	RadioButton r1,r2;
	int sfgg=1;
	
	ProgressDialog progressDialog=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channelchoose);	
		initListView();
	}
	
	private void initListView()
	{
		rg=(RadioGroup) findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(this);
		r1=(RadioButton) findViewById(R.id.r1);
		r2=(RadioButton) findViewById(R.id.r2);
		
		listView=(ListView) findViewById(R.id.listview);
		simpleAdapter=new SimpleAdapter(this, dataList, R.layout.channelitem,  new String[]{"hdmc"}, new int[]{R.id.text});
		listView.setAdapter(simpleAdapter);
		listView.setOnItemClickListener(this);
	}
	
	@Override
	public void onResume()
	{
		getData(sfgg);
		super.onResume();
	}
	
	private void getData(int sfgg)
	{
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult() {
			
			@Override
			public void onSuccess(String result) {
				dataList.clear();
				try {
					JSONObject object=new JSONObject(result.trim());
					JSONArray dataListArray=object.getJSONArray("dataList");
					for(int i=0;i<dataListArray.length();i++)
					{
						JSONObject hd=dataListArray.getJSONObject(i);
						
						int id=hd.getInt("id");
						int bh=hd.getInt("hdbh");
						String hdmcString=hd.getString("hdmc");
						
						Map<String, Object> map=new HashMap<>();
						map.put("id", id);
						map.put("hdbh", bh);
						map.put("hdmc", hdmcString);
						dataList.add(map);
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				if(progressDialog!=null)progressDialog.dismiss();
				simpleAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
		});
		Map<String,Object> map=new HashMap<>();
		map.put("pid", sfgg);
		progressDialog=new ProgressDialog(this);
		progressDialog.setMessage("数据加载中");
		progressDialog.show();
		httpRequest.post(HttpUtil.BASE_URL+"findChannelsByType", map);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		Map<String, Object> map=dataList.get(position);
		String nameString=(String) map.get("hdmc");
		int cid=(int) map.get("id");
		Intent intent=new Intent();
		intent.putExtra("channelname", nameString);
		intent.putExtra("id", cid);
		setResult(200, intent);
		finish();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId)
		{
		case R.id.r1://骨干航道
			
			sfgg=1;
			
			break;
		case R.id.r2://支线航道
			sfgg=0;
		}
		
		getData(sfgg);
		
	}
}
