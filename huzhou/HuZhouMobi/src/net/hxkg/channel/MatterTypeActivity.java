package net.hxkg.channel; 
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MatterTypeActivity extends Activity implements OnItemClickListener
{	
	ListView itemListView;
	List<Map<String, Object>> itemlist=new ArrayList<>();
	//
	Sadapter simpleAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mattertype);	
		initListView();
	}
	
	private void  initListView()
	{
		itemListView=(ListView) findViewById(R.id.list);
		simpleAdapter=new Sadapter(this, itemlist, R.layout.channelitem, new String[]{"name"}, new int[]{R.id.text});
		itemListView.setAdapter(simpleAdapter);
		itemListView.setOnItemClickListener(this);
	}
	
	class Sadapter  extends SimpleAdapter
	{
		
		public Sadapter(Context context, List<? extends Map<String, ?>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			convertView=super.getView(position, convertView, parent);
			TextView textView=(TextView) convertView.findViewById(R.id.text);
			Map<String, Object> map=itemlist.get(position);
			int level=(int) map.get("level");
			textView.setPadding(20+20*level, 0, 0, 0);
			textView.setTextColor(Color.BLUE);
			if(level>1)
			{
				textView.setText(">>"+textView.getText().toString());
				textView.setTextColor(Color.BLACK);
			}
			return convertView;
			
		}		
	}
	
	public void initData(final int position,final int pid)
	{
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult() {
			
			@Override
			public void onSuccess(String result) {
				List<Map<String, Object>> itemlist_child=new ArrayList<>();
				try {
					JSONObject object=new JSONObject(result.trim());
					JSONArray dataListArray=object.getJSONArray("dataList");
					for(int i=0;i<dataListArray.length();i++)
					{
						JSONObject issueJsonObject=dataListArray.getJSONObject(i);
						String name=issueJsonObject.getString("name");
						String pname=issueJsonObject.getString("pname");
						int id=issueJsonObject.getInt("id");
						int level=issueJsonObject.getInt("level");
						int pid=issueJsonObject.getInt("pid");
						int haschild=issueJsonObject.getInt("haschild");
						
						Map<String, Object> map=new HashMap<>();
						map.put("name", name);
						map.put("pname", pname);
						map.put("id", id);
						map.put("level", level);
						map.put("pid", pid);
						map.put("haschild", haschild);
						map.put("expand", false);
						
						itemlist_child.add(map);
						
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				itemlist.addAll(position, itemlist_child);
				if(position-1>=0)
				{
					itemlist.get(position-1).put("children", itemlist_child);
				}
				simpleAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
		});
		Map<String, Object> map=new HashMap<>();
		map.put("pid", pid);
		httpRequest.post(HttpUtil.BASE_URL+"findIssuesByPid", map);
		
	} 
	
	@Override
	protected void onResume() 
	{ 
		super.onResume();
		initData(0,-1);
	}
	
	public void onBack(View view)
	{
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		Map<String, Object> map=itemlist.get(position);
		String name=(String) map.get("name");
		String pname=(String) map.get("pname");
		int level=(int) map.get("level");
		int haschild=(int) map.get("haschild");
		int itemid=(int) map.get("id");
		boolean expand=(boolean) map.get("expand");
		if(haschild==1)
		{
			if(expand)//已展开，收起
			{
				itemlist.removeAll((Collection<?>) map.get("children"));
				simpleAdapter.notifyDataSetChanged();
				map.put("expand", false);
			}else //已收起，展开
			{				
				initData(position+1,itemid);
				map.put("expand", true);
			}
		}else
		{
			Intent intent=new Intent();
			intent.putExtra("name", name);
			if(itemid!=7)
			intent.putExtra("pname", pname+">>");
			else {
				intent.putExtra("pname", pname);
			}
			intent.putExtra("id", itemid);			
			setResult(200, intent);
			this.finish();
		}
		
		
	}
}
