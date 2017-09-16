package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;

import com.gh.modol.RegionList;
import com.hxkg.ghpublic.HomeActivity;
import com.hxkg.ghpublic.R;
import com.hxkg.mainfragment.MainFramentNewsActivity;
import com.hztuen.gh.activity.Adapter.AreaListAdapter;
import com.hztuen.gh.activity.Adapter.NewsFromAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author zzq
 * @DateTime 2016年7月12日 下午3:10:16
 * 新闻内容来源城市
 */
public class ActivityArea extends Activity{
	private List<RegionList> regionlist = new ArrayList<RegionList>();
	private AreaListAdapter adapter;
	private TextView news_from_back;
	private TextView news_from_city;
	private ListView news_from_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsinfo_from);
		news_from_back = (TextView) findViewById(R.id.news_from_back);
		news_from_city = (TextView) findViewById(R.id.news_from_city);
		news_from_list = (ListView) findViewById(R.id.news_from_list);
		news_from_list.setDividerHeight(0);
		adapter = new AreaListAdapter(getApplicationContext(), regionlist);
		news_from_list.setAdapter(adapter);
		Intent intent = getIntent();
		news_from_city.setText(intent.getStringExtra("city"));
		news_from_city.setOnClickListener(onclick);
		news_from_back.setOnClickListener(onclick);
		news_from_list.setOnItemClickListener(onitem);
		getData();
	}
	public OnClickListener onclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.news_from_city:
				finish();
				break;
			case R.id.news_from_back:
				finish();
				break;
			default:
				break;
			}
		}
	};
	public OnItemClickListener onitem = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			switch (parent.getId()) {
			case R.id.news_from_list:
				expressItemClick(position);//position 代表你点的哪一个
				break;
			default:
				break;
			}
		}
		public void expressItemClick(int position){
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MainFramentNewsActivity.class);
			String cityPostion = regionlist.get(position).getRegion().toString();
			if(cityPostion.equals("杭州市")){
				setResult(1001, intent);
				finish();
			}else if(cityPostion.equals("宁波市")){
				setResult(1002, intent);
				finish();
			}else if(cityPostion.equals("温州市")){
				setResult(1003, intent);
				finish();
			}else if(cityPostion.equals("绍兴市")){
				setResult(1004, intent);
				finish();
			}else if(cityPostion.equals("湖州市")){
				setResult(1005, intent);
				finish();
			}else if(cityPostion.equals("嘉兴市")){
				setResult(1006, intent);
				finish();
			}else if(cityPostion.equals("金华市")){
				setResult(1007, intent);
				finish();
			}else if(cityPostion.equals("衢州市")){
				setResult(1008, intent);
				finish();
			}else if(cityPostion.equals("台州市")){
				setResult(1009, intent);
				finish();
			}else if(cityPostion.equals("丽水市")){
				setResult(1010, intent);
				finish();
			}else if(cityPostion.equals("舟山市")){
				setResult(1011, intent);
				finish();
			}
		}
	};
	/**
	 * 添加数据
	 * */
	public void getData(){
		regionlist.clear();
		RegionList mRegionList =new RegionList();
		mRegionList.setRegion("杭州市");
		regionlist.add(mRegionList);
		
		mRegionList =new RegionList();
		mRegionList.setRegion("宁波市");
		regionlist.add(mRegionList);
		
		mRegionList =new RegionList();
		mRegionList.setRegion("温州市");
		regionlist.add(mRegionList);
		
		mRegionList =new RegionList();
		mRegionList.setRegion("绍兴市");
		regionlist.add(mRegionList);
		
		mRegionList =new RegionList();
		mRegionList.setRegion("湖州市");
		regionlist.add(mRegionList);
		
		mRegionList =new RegionList();
		mRegionList.setRegion("嘉兴市");
		regionlist.add(mRegionList);
		
		mRegionList =new RegionList();
		mRegionList.setRegion("金华市");
		regionlist.add(mRegionList);
		
		mRegionList =new RegionList();
		mRegionList.setRegion("衢州市");
		regionlist.add(mRegionList);
		
		mRegionList =new RegionList();
		mRegionList.setRegion("台州市");
		regionlist.add(mRegionList);
		
		mRegionList =new RegionList();
		mRegionList.setRegion("丽水市");
		regionlist.add(mRegionList);
		
		mRegionList =new RegionList();
		mRegionList.setRegion("舟山市");
		regionlist.add(mRegionList);
	}
	
}
