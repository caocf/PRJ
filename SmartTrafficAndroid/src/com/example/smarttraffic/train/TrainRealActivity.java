package com.example.smarttraffic.train;

import java.util.List;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.ListView;

/**
 * 铁路实时信息界面
 * @author Administrator zhou
 *
 */
public class TrainRealActivity extends FragmentActivity implements UpdateView
{
	ListView listView;
	RealTrainAdapter adapter;
	
	TrainNoRequest request;
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Object data) 
	{
		if(data instanceof List<?>)
		{
			List<Train> trains=(List<Train>)data;
			if(listView.getAdapter()==null)
			{
				if(request.getType()==1)
				{
					adapter=new RealTrainAdapter(this, trains);
				}
				else if(request.getType()==0)
				{
					adapter=new RealTrainAdapter(this, trains,2);
				}
				listView.setAdapter(adapter);
			}
			else 
			{
				adapter.update(trains);
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_train_time);
		
		Intent intent=this.getIntent();
		request=(TrainNoRequest)intent.getSerializableExtra(SearchTrainFragment.SEARCH_NAME);
		
		HeadNameFragment headFragment=new HeadNameFragment();
		headFragment.setTitleName(request.getBusNo()+"时刻表");
		ManagerFragment.replaceFragment(this, R.id.train_no_time_head, headFragment);
		
		initView();
		
		new HttpThread(new TrainNoSearch(), request,this,this,R.string.error_train_no_real_info).start();
	}
	
	private void initView()
	{
		listView=(ListView)findViewById(R.id.train_no_time_listview);
	}
}
