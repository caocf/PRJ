package com.hztuen.position;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import com.gh.modol.RegionList;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.PositionAdapter; 

public class PositionActivity extends Activity
{
	private ListView cityList;
	private TextView postion_defult;
	private PositionAdapter postionadapter;
	private List<RegionList> regionlist = new ArrayList<RegionList>(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_postion);
		cityList = (ListView) findViewById(R.id.positionlist);
		postion_defult = (TextView) findViewById(R.id.postion_city);
		cityList.setDividerHeight(0);
		//准备数据
		getCity(); 
		postion_defult.setText(getIntent().getStringExtra("city")); 
		
		postionadapter = new PositionAdapter(getApplicationContext(), regionlist);
		cityList.setAdapter(postionadapter);
		cityList.setOnItemClickListener(onitemclicklistener);
	}
	public OnItemClickListener onitemclicklistener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{ 
			switch (parent.getId())
			{
			case R.id.positionlist:
				Intent intent = new Intent();
				String cityPostion = regionlist.get(position).getRegion().toString();
				intent.putExtra("city", cityPostion);
				setResult(1001, intent);
				finish();
				break;
			}
		}
	};
	
	public void onViewClick(View v)
	{			
		switch(v.getId()){
		case R.id.postion_back:
			finish();
			break;
		default:
			break;
		}
	}
	
	/*
	 * 添加数据
	 * */
	public void getCity()
	{
		regionlist.clear();
		RegionList mRegionList =new RegionList();
		mRegionList.setRegion("杭州");
		regionlist.add(mRegionList);

		mRegionList =new RegionList();
		mRegionList.setRegion("宁波");
		regionlist.add(mRegionList);

		mRegionList =new RegionList();
		mRegionList.setRegion("温州");
		regionlist.add(mRegionList);

		mRegionList =new RegionList();
		mRegionList.setRegion("绍兴");
		regionlist.add(mRegionList);

		mRegionList =new RegionList();
		mRegionList.setRegion("湖州");
		regionlist.add(mRegionList);

		mRegionList =new RegionList();
		mRegionList.setRegion("嘉兴");
		regionlist.add(mRegionList);

		mRegionList =new RegionList();
		mRegionList.setRegion("金华");
		regionlist.add(mRegionList);

		mRegionList =new RegionList();
		mRegionList.setRegion("衢州");
		regionlist.add(mRegionList);

		mRegionList =new RegionList();
		mRegionList.setRegion("台州");
		regionlist.add(mRegionList);

		mRegionList =new RegionList();
		mRegionList.setRegion("丽水");
		regionlist.add(mRegionList);

		mRegionList =new RegionList();
		mRegionList.setRegion("舟山");
		regionlist.add(mRegionList);
	}

}
