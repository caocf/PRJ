package com.huzhouport.car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import net.hxkg.channel.HttpRequest;
import net.hxkg.channel.HttpRequest.onResult;

import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class CarArrange extends Activity implements onResult,OnItemClickListener
{
	CarGridView gridView;
	ArrangeAdapter adapter;
	List<CarInfo> list=new ArrayList<>();
	List<Integer> unvaliableCarids=new ArrayList<>();
	String d1,d2;
	
	View lastView=null;
	int selectedid=-1;
	String senameString="";
	int number;
	
	ScrollView srcScrollView;
	TextView tipTextView;
	String driver,tel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.cararrange);
		d1=getIntent().getStringExtra("d1");
		d2=getIntent().getStringExtra("d2");
		number=getIntent().getIntExtra("cap", 0);
		
		initView();
		getData();
	}
	
	
	private void initView()
	{
		srcScrollView=(ScrollView) findViewById(R.id.scrollview);
		tipTextView=(TextView) findViewById(R.id.tip);
		
		adapter=new ArrangeAdapter(this, list);
		gridView=(CarGridView) findViewById(R.id.gridview);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
	}
	
	private void getData() {
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String,Object> map=new HashMap<>();
		
		map.put("d1", d1);
		map.put("d2", d2);
		//httpRequest.post("http://192.168.1.135:6080/HuZhouPort/findCarInfo", map);
		httpRequest.post(HttpUtil.BASE_URL+"findCarInfo", map);
		srcScrollView.setVisibility(View.VISIBLE);
		tipTextView.setVisibility(View.GONE);
	}


	@Override
	public void onSuccess(String result) 
	{
		list.clear();
		unvaliableCarids.clear();
		try {
			JSONObject object=new JSONObject(result.trim());
			JSONArray allcar=object.getJSONArray("allcars");
			for(int i=0;i<allcar.length();i++)
			{
				JSONObject car=allcar.getJSONObject(i);
				String name=car.getString("name");
				String tel=car.getString("tel");
				String driver=car.getString("driver");
				int capacity=car.getInt("capcity");
				int id=car.getInt("id");
				
				CarInfo carInfo=new CarInfo();
				carInfo.setId(id);
				carInfo.setCapcity(capacity);
				carInfo.setName(name);
				carInfo.setDriver(driver);
				carInfo.setTel(tel);
				
				if(capacity>=number)
				{
					list.add(carInfo);
				}			
				
			}
			JSONArray list=object.getJSONArray("list");
			for(int i=0;i<list.length();i++)
			{
				JSONArray arr=list.getJSONArray(i);
				JSONObject carobj=arr.getJSONObject(0);
				int id=carobj.getInt("id");
				unvaliableCarids.add(id);
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		adapter.setUnvaliableCarids(unvaliableCarids);
		adapter.notifyDataSetChanged();
		if(list.size()<=0)
		{
			srcScrollView.setVisibility(View.GONE);
			tipTextView.setVisibility(View.VISIBLE);
		}
	}


	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		int carid=list.get(position).getId();
		for(int uid:unvaliableCarids)
		{
			if(carid==uid)
			{
				Toast.makeText(this, "此车辆在选定时间内已有安排", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		
		
		if(lastView!=null) 
		{
			lastView.setBackgroundResource(R.drawable.caritem2);
			ImageView img1=(ImageView) lastView.findViewById(R.id.check);
			img1.setVisibility(View.INVISIBLE);
		}		
		
		view.setBackgroundResource(R.drawable.caritem1);
		ImageView img=(ImageView) view.findViewById(R.id.check);
		img.setVisibility(View.VISIBLE);
		lastView=view;
		
		selectedid=list.get(position).getId();
		senameString=list.get(position).getName();
		driver=list.get(position).getDriver();
		tel=list.get(position).getTel();
	}
	
	public void onChoose(View view)
	{
		if(selectedid==-1||senameString.equals(""))
		{
			Toast.makeText(this, "请先选择车辆", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent=new Intent();
		intent.putExtra("id", selectedid);
		intent.putExtra("name", senameString);
		intent.putExtra("driver", driver);
		intent.putExtra("tel", tel);
		setResult(200, intent);
		this.finish();
	}
	
	public void onBack(View view)
	{
		this.finish();
	}

}
