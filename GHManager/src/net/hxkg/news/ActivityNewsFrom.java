package net.hxkg.news;

import java.util.ArrayList;
import java.util.List;

import net.hxkg.ghmanager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityNewsFrom extends Activity
{
	private List<RegionList> regionlist = new ArrayList<RegionList>();
	private NewsFromAdapter adapter;
	private TextView news_from_back;
	private TextView news_from_city;
	private ListView news_from_list;
	
	String city;
	
	public static String REGIONS[]={"浙江省","杭州市","嘉兴市","湖州市","舟山市","宁波市","金华市","绍兴市","温州市","衢州市","台州市","丽水市"};

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newsinfo_from);
		
		news_from_city = (TextView) findViewById(R.id.news_from_city);//当前选择
		int city=getIntent().getIntExtra("city",0);
		news_from_city.setText(REGIONS[city]+"港航管理局");
		news_from_city.setOnClickListener(onclick);
		
		news_from_back = (TextView) findViewById(R.id.news_from_back);//返回键
		news_from_back.setOnClickListener(onclick);
		
		news_from_list = (ListView) findViewById(R.id.news_from_list);
		news_from_list.setDividerHeight(0);
		adapter = new NewsFromAdapter(getApplicationContext(), regionlist);
		news_from_list.setAdapter(adapter);		
		news_from_list.setOnItemClickListener(onitem);
		getData();
	}
	public OnClickListener onclick = new OnClickListener() 
	{		
		@Override
		public void onClick(View v) 
		{
			switch (v.getId()) 
			{
			case R.id.news_from_city:				
			case R.id.news_from_back:
				Intent intent = new Intent();
				intent.putExtra("regioncode", 100);
				setResult(1000, intent);
				finish();
				break;
			default:
				break;
			}
		}
	};
	public OnItemClickListener onitem = new OnItemClickListener() 
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			Intent intent = new Intent();
			intent.putExtra("regioncode", position);
			setResult(1000, intent);
			finish();
		}
	};
	
	private void getData()
	{
		regionlist.clear();	
		
		for(String s:REGIONS)
		{
			RegionList mRegionList =new RegionList();
			mRegionList.setRegion(s+"港航管理局");
			regionlist.add(mRegionList);
		}
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent();
		intent.putExtra("regioncode", 100);
		setResult(1000, intent);
		finish();
	}
	
}
