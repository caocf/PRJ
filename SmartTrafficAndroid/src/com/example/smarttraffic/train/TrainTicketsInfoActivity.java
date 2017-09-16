package com.example.smarttraffic.train;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.ListCompare;
import com.example.smarttraffic.util.ListviewControl;
import com.example.smarttraffic.util.StartIntent;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 铁路车票信息显示界面
 * @author Administrator zhou
 *
 */
public class TrainTicketsInfoActivity extends FragmentActivity implements UpdateView
{
	ListView ticketsListView;
	TrainTicketsAdapter ticketsAdapter;

	TextView beforeTextView;
	TextView afterTextView;
	TextView todayTextView;
	
	TrainTicketRequest info;
	
	DrawerLayout drawerLayout;
	
	ListView condition1;
	ListView condition2;
	ListView condition3;
		
	@SuppressWarnings("unchecked")
	@Override
	public void update(Object data) 
	{
		if(data instanceof List<?>)
		{
			List<TrainTickets> dataList=(List<TrainTickets>)data;
			
			if(ticketsListView.getAdapter()==null)
			{
				if(getIntent().getBooleanExtra(IS_TRAIN_TICKETS_SEARCH, false))
				{
					ticketsAdapter=new TrainTicketsAdapter(this, dataList,2);
				}
				else
				{
					ticketsAdapter=new TrainTicketsAdapter(this, dataList);
				}
				ticketsListView.setAdapter(ticketsAdapter);
			}
			else 
			{
				ticketsAdapter.update(dataList);
				setText();
			}
		}
	}

	public static final String IS_TRAIN_TICKETS_SEARCH="is_train_tickets_search";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_train_tickets_info);
			
		dealParam();
		initHead();
		initView();
		
		requestURL();
	}
	
	private void dealParam()
	{
		info=(TrainTicketRequest)getIntent().getSerializableExtra(SearchTicketFragment.SEARCH_TRAIN_TICKET_NAME);		
	}

	class OnTicketListItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{			
			Bundle bundle=new Bundle();
			bundle.putString(TrainNoTicketsDetailActivity.TRAIN_SEARCH_NO, ticketsAdapter.getData().get(position).getTrainNumber());
			bundle.putString(TrainNoTicketsDetailActivity.TRAIN_SEARCH_START_POINT, ticketsAdapter.getData().get(position).getStartCity());
			bundle.putString(TrainNoTicketsDetailActivity.TRAIN_SEARCH_END_POINT, ticketsAdapter.getData().get(position).getEndCity());
					
			StartIntent.startIntentWithParam(TrainTicketsInfoActivity.this, TrainNoTicketsDetailActivity.class, bundle);
			
		}
	}
	
	private void initView()
	{
		ticketsListView=(ListView)findViewById(R.id.bus_ticket_list);
		ticketsListView.setOnItemClickListener(new OnTicketListItemClick());
		beforeTextView=(TextView)findViewById(R.id.ticket_before_date);
		todayTextView=(TextView)findViewById(R.id.ticket_now_date);
		afterTextView=(TextView)findViewById(R.id.ticket_after_date);
		
		drawerLayout=(DrawerLayout)findViewById(R.id.train_right_drawerlayout);
		
		
		List<String> data=new ArrayList<String>();
		data.add("不限");
		data.add("上午：6-12点");
		data.add("中午：12-14点");
		data.add("下午：14-18点");
		data.add("晚上：18-6点");
		
		condition1=(ListView)findViewById(R.id.ticket_select_start_listview);
		condition2=(ListView)findViewById(R.id.ticket_select_end_listview);
		condition3=(ListView)findViewById(R.id.ticket_select_train_type_listview);
		
		
		condition1.setAdapter(new RightSelectAdapter(this, data, true));
		ListviewControl.setListViewHeightBasedOnChildren(condition1);
		condition1.setOnItemClickListener(new RightSelectOnItemClick());
		
		condition2.setAdapter(new RightSelectAdapter(this, data, true));
		ListviewControl.setListViewHeightBasedOnChildren(condition2);
		condition2.setOnItemClickListener(new RightSelectOnItemClick());
		
		condition3.setAdapter(new RightSelectAdapter(this, getResources().getStringArray(R.array.train_kind_array), true));
		ListviewControl.setListViewHeightBasedOnChildren(condition3);
		condition3.setOnItemClickListener(new RightSelectOnItemClick());
		
		findViewById(R.id.ticket_search_submit).setOnClickListener(new View.OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				search(v);				
			}
		});
		
		setText();
	}
	
	class RightSelectOnItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			if(position==0)
				((RightSelectAdapter)(parent.getAdapter())).selectAll(true);
			else
				((RightSelectAdapter)(parent.getAdapter())).changeCheckAndClearFirst(position);
		}
	}
	
	private void initHead()
	{
		HeadNameFragment headFragment=new HeadNameFragment();
		
		String nameString;
		if(info.isFromStation())
		{
			nameString="途径"+info.getStartCity()+"列车";			
		}
		else 
		{
			nameString=info.getStartCity()+"-"+info.getEndCity();
		}
		headFragment.setTitleName(nameString);
		
		
		ManagerFragment.replaceFragment(this, R.id.bus_ticket_head, headFragment);
	}	
	
	public void search(View v)
	{
		switch (v.getId()) 
		{
			case R.id.ticket_before_date:
				modifyInfo(1);
				ticketsAdapter.clear();
				requestURL();
				break;
			case R.id.ticket_after_date:
				modifyInfo(2);	
				ticketsAdapter.clear();
				requestURL();
				break;	
				
			case R.id.ticket_search_reset:
				drawerLayout.closeDrawer(Gravity.RIGHT);
				break;
				
			case R.id.ticket_search_submit:
				int i1=((RightSelectAdapter)(condition1.getAdapter())).getSelectForIntResult();
				int i2=((RightSelectAdapter)(condition2.getAdapter())).getSelectForIntResult();
				int i3=((RightSelectAdapter)(condition3.getAdapter())).getSelectForIntResult();
				
				info.setStartTimeType(i1);
				info.setEndTImeType(i2);
				info.setTrainType(i3);
				
				new HttpThread(new TrainTicketSearch(), info, this,this,R.string.error_train_tickets_info).start();
				drawerLayout.closeDrawer(Gravity.RIGHT);
				
				break;
		}
	}
	
	private void requestURL()
	{
		new HttpThread(new TrainTicketSearch(), info, this,this,R.string.error_train_tickets_info).start();
	}
	private void setText()
	{
		todayTextView.setText((info.getMonth()+1)+"月"+info.getDay()+"日");
	}
	
	private void modifyInfo(int i)
	{
		Calendar calendar=Calendar.getInstance();
		
		if(i==1)
		{		
			calendar.set(info.getYear(), info.getMonth(), info.getDay()-1);		
		}
		else
		{
			calendar.set(info.getYear(), info.getMonth(), info.getDay()+1);
		}
		
		info.setYear(calendar.get(Calendar.YEAR));
		info.setMonth(calendar.get(Calendar.MONTH));
		info.setDay(calendar.get(Calendar.DAY_OF_MONTH));
				
	}
	
	public void control(View v)
	{
		switch (v.getId()) {
		case R.id.bus_tickets_sort:		
			GetDialog.getRadioDialog(this, "排序", R.array.bus_search_sort_name, new sortOnclick(), "", null,selectType).show();
			break;

		case R.id.bus_tickets_select:		
			drawerLayout.openDrawer(Gravity.RIGHT);
			break;
		}
	}
	
	int selectType;
	class sortOnclick implements OnClickListener
	{

		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
			List<TrainTickets> tempList=ticketsAdapter.getData();
			switch (which) {
				case 0:
					Collections.sort(tempList,new ListCompare().getCompareTrainTicketsByTime(1));
					break;
				case 1:
					Collections.sort(tempList,new ListCompare().getCompareTrainTicketsByTime(2));
					break;
				case 2:
					Collections.sort(tempList,new ListCompare().getCompareTrainTicketsByPrice(1));		
					break;
				case 3:				
					Collections.sort(tempList,new ListCompare().getCompareTrainTicketsByPrice(2));
					break;

			}
			selectType=which;
			ticketsAdapter.update(tempList);
			dialog.cancel();
		}
	}

}
