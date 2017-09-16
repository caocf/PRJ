package com.hxkg.meeting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import net.hxkg.channel.HttpRequest;
import net.hxkg.channel.HttpRequest.onResult;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;

public class MeetingRoomChooseActivity extends Activity  implements onResult,OnItemClickListener
{
	ListView listView;
	SimpleAdapter simpleAdapter;
	List<Map<String, Object>> list=new ArrayList<>();
	List<Integer> ids=new ArrayList<>();
	TextView titleTextView;
	String date;
	String time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetingroomchoose_activity);
		date=getIntent().getStringExtra("date");
		time=getIntent().getStringExtra("time");
		initView();
		getData();
	}
	
	private void initView()
	{
		titleTextView=(TextView) findViewById(R.id.title);
		titleTextView.setText("选择会议室  "+date+" "+time);
		
		listView=(ListView) findViewById(R.id.roomlist);
		simpleAdapter=new SimpleAdapter(this, list, R.layout.meetingroom_item, new String[]{"name"}, new int[]{R.id.name})
		{
			@Override
			public View getView(final int position, View convertView, ViewGroup parent)
			{
				convertView=super.getView(position, convertView, parent);
				TextView nameTextView=(TextView) convertView.findViewById(R.id.name);
				TextView statusTextView=(TextView) convertView.findViewById(R.id.status);
				ImageView imageView=(ImageView) convertView.findViewById(R.id.media);
				int typeid=(int) list.get(position).get("typeid");
				if(typeid==1)//多媒体
				{
					imageView.setVisibility(View.VISIBLE);
				}else {
					imageView.setVisibility(View.GONE);
				}
				
				int vailable=(int) list.get(position).get("vailable");
				if(vailable==1)//空闲可用
				{
					nameTextView.setTextColor(Color.parseColor("#0067ac"));
					statusTextView.setText("(闲)");
					statusTextView.setTextColor(Color.parseColor("#0067ac"));
				}else 
				{
					nameTextView.setTextColor(Color.parseColor("#d7d7d7"));
					statusTextView.setText("(忙)");
					statusTextView.setTextColor(Color.parseColor("#d7d7d7"));
				}
				
				TextView detailTextView=(TextView) convertView.findViewById(R.id.detail);
				detailTextView.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v) 
					{
						Intent intentrecord=new Intent(MeetingRoomChooseActivity.this,MeetingRecordActivity.class);
						intentrecord.putExtra("roomid", ids.get(position));
						String nameString= (String) list.get(position).get("name");
						intentrecord.putExtra("roomname",nameString.substring(0,nameString.indexOf("(")));
						
						String d[]=date.split("-");
						intentrecord.putExtra("year", Integer.parseInt(d[0]));
						intentrecord.putExtra("month", Integer.parseInt(d[1]));
						intentrecord.putExtra("day", Integer.parseInt(d[2]));
						startActivity(intentrecord);
						
					}
				})	;
				
				return convertView;
			}
			
		};
		listView.setAdapter(simpleAdapter);
		listView.setOnItemClickListener(this);
	}
	
	private void getData() 
	{
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		String times[]=time.split("-");
		map.put("date1", date+" "+times[0]);
		map.put("date2", date+" "+times[1]);
		httpRequest.post(HttpUtil.BASE_URL+"queryMeetingRooms" , map);
		//httpRequest.post("http://192.168.1.135:6080/HuZhouPort/queryMeetingRooms" , map);
	}
	
	public void onBack(View view)
	{
		finish();
	}

	@Override
	public void onSuccess(String result) 
	{
		if(result==null||"".equals(result.trim()))
		{
			Toast.makeText(this, "无数据", Toast.LENGTH_SHORT).show();
			return ;
			
		}
		try 
		{
			JSONObject resultobj=new JSONObject(result.trim());
			JSONObject roomids=resultobj.getJSONObject("roomids");
			JSONArray meetingRooms=resultobj.getJSONArray("list");
			list.clear();
			for(int i=0;i<meetingRooms.length();i++)
			{
				JSONArray array=meetingRooms.getJSONArray(i);
				JSONObject meetingtype=array.getJSONObject(0);
				JSONObject meetingroomtype=array.getJSONObject(1);
				String typenameString=meetingtype.getString("typename")+"会议室(";
				String countString=meetingtype.getString("capacity");
				int type=meetingroomtype.getInt("id");				
				
				Map<String, Object> map=new HashMap<>();
				map.put("name", typenameString+countString+"人)");
				map.put("typeid", type);
				map.put("vailable", 1);//空闲
				
				int id=meetingtype.getInt("id");
				Iterator<String> iter=roomids.keys();
				while(iter.hasNext())
				{
					String keyString=iter.next();
					if(id==Integer.parseInt(keyString))
					{
						map.put("vailable", 0);//繁忙
						break;
					}
				}
				list.add(map);
				ids.add(id);
			}
			simpleAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			Toast.makeText(this, "数据异常", Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	public void onError(int httpcode) {
		Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Intent intent=new Intent();
		intent.putExtra("id", ids.get(position));
		intent.putExtra("name", (String)list.get(position).get("name"));
		this.setResult(100,intent);
		finish();
		
	}
}
