package com.example.smarttraffic.tripPlan;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.tripPlan.adapter.SelectAdapter;
import com.example.smarttraffic.tripPlan.fragment.TripDrivingSelfFragment;
import com.example.smarttraffic.util.ListviewControl;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * 自驾策略选择界面
 * @author Administrator zhou
 *
 */
public class SelfStategyActivity extends FragmentActivity {

	ListView listView;
	
	SelectAdapter adapter;
	int selectID;
	
	ImageView andvanceImageView;
	ImageView isDangerImageView;
	EditText limitText;
	EditText hightEditText;
	EditText widthEditText;
	EditText lengthEditText;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_self_stategy);
		
		listView=(ListView)findViewById(R.id.trip_driving_self_stategy_select_listview);
		
		HeadNameFragment headNameFragment=new HeadNameFragment();
		headNameFragment.setTitleName("偏好设置");
		
		ManagerFragment.replaceFragment(this, R.id.trip_driving_self_stategy_head, headNameFragment);
	
		adapter=new SelectAdapter(this, CreateStategy(), true);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new onSelectItemClick());
		
		ListviewControl.setListViewHeightBasedOnChildren(listView);
		
		selectID=getIntent().getExtras().getInt(TripDrivingSelfFragment.STATEGY, 0);
		adapter.changeCheck(selectID);
		
		andvanceImageView=(ImageView)findViewById(R.id.trip_driving_self_stategy_more_select);
		isDangerImageView=(ImageView)findViewById(R.id.trip_driving_self_stategy_more_danger_select);
	}
	
	class onSelectItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		{
			adapter.changeCheck(position);		
			selectID=position;
		}
	}
	
	/**
	 * 获取策略选择列表
	 * @return 策略列表
	 */
	public List<String> CreateStategy()
	{
		List<String> result=new ArrayList<String>();		
		String[] names=getResources().getStringArray(R.array.array_trip_stategy);
		
		for(int i=0;i<names.length;i++)
		{
			result.add(names[i]);
		}
		
		return result;
	}
	public static final String FAVOR_RESULT="favor_result";
	
	public void onclick(View v)
	{
		switch (v.getId())
		{
			case R.id.favor_enter:
				StartIntent.resultActivity(SelfStategyActivity.this, 1, FAVOR_RESULT, selectID);
				break;
		}
	}
	
	boolean andvance;			//是否打开高级选项
	boolean isDanger;
	
	/**
	 * 高级选项内容监听
	 * @param v 控件
	 */
	public void moreSelect(View v)
	{
		switch (v.getId())
		{
			case R.id.trip_driving_self_stategy_more_select:
				andvance=!andvance;
				showImage(andvance, andvanceImageView);
				if(andvance)
					findViewById(R.id.trip_driving_self_stategy_andvance_layout).setVisibility(View.VISIBLE);
				else
					findViewById(R.id.trip_driving_self_stategy_andvance_layout).setVisibility(View.GONE);
				
				break;
	
			case R.id.trip_driving_self_stategy_more_danger_select:
				isDanger=!isDanger;
				showImage(isDanger, isDangerImageView);
				
				break;
		}
	}
	
	/**
	 * 显示隐藏高级选项，同时更新自身图标
	 * @param b 是否显示
	 * @param imageView 
	 */
	public void showImage(boolean b,ImageView imageView)
	{
		if(b)
		{
			imageView.setImageResource(R.drawable.switch_on_);
		}
		else
		{
			imageView.setImageResource(R.drawable.switch_off_);
		}
	}
}
