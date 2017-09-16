package com.example.smarttraffic.airport;

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
import com.example.smarttraffic.train.RightSelectAdapter;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AirRealInfoActivity extends FragmentActivity implements UpdateView
{
	ListView ticketsListView;
	AirRealAdapter ticketsAdapter;

	TextView todayTextView;
	
	AirTicketRequest info;
	
	DrawerLayout drawerLayout;
	
	ListView condition1;
	ListView condition2;
		
	@SuppressWarnings("unchecked")
	@Override
	public void update(Object data) 
	{
		
		if(data instanceof List<?>)
		{		
			List<AirTickets> dataAirTickets=(List<AirTickets>)data;
			if(ticketsListView.getAdapter()==null)
			{
				ticketsAdapter=new AirRealAdapter(this, dataAirTickets);
				ticketsListView.setAdapter(ticketsAdapter);
				ticketsListView.setOnItemClickListener(ticketclickListener);
			}
			else 
			{
				ticketsAdapter.update(dataAirTickets);
				setText();
			}
		}
	}
	
	private OnItemClickListener ticketclickListener=new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			Bundle resultInfo=new Bundle();
			resultInfo.putString(AirNoRealActivity.AIR_REAL_NO_PARAM,ticketsAdapter.getData().get(position).getAirNumber());
			StartIntent.startIntentWithParam(AirRealInfoActivity.this, AirNoRealActivity.class,resultInfo);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_air_real_info);
			
		dealParam();
		initHead();
		initView();
		
		requestURL();
	}
	
	private void dealParam()
	{
		info=(AirTicketRequest)getIntent().getSerializableExtra(SearchTicketFragment.SEARCH_AIR_TICKET_NAME);
		if(info.getCalendar()==null)
			info.setCalendar(Calendar.getInstance());
	}
	
	private void initView()
	{
		ticketsListView=(ListView)findViewById(R.id.bus_ticket_list);
		todayTextView=(TextView)findViewById(R.id.ticket_now_date);
		
		drawerLayout=(DrawerLayout)findViewById(R.id.air_right_drawerlayout);
			
		List<String> data=new ArrayList<String>();
		data.add("不限");
		data.add("上午：6-12点");
		data.add("中午：12-14点");
		data.add("下午：14-18点");
		data.add("晚上：18-6点");
		
		condition1=(ListView)findViewById(R.id.ticket_select_start_listview);
		condition2=(ListView)findViewById(R.id.ticket_select_end_listview);
		
		condition1.setAdapter(new RightSelectAdapter(this, data, true));
		ListviewControl.setListViewHeightBasedOnChildren(condition1);
		condition1.setOnItemClickListener(new RightSelectOnItemClick());
		
		condition2.setAdapter(new RightSelectAdapter(this, data, true));
		ListviewControl.setListViewHeightBasedOnChildren(condition2);
		condition2.setOnItemClickListener(new RightSelectOnItemClick());
		
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

		nameString=info.getStartCity()+"-"+info.getEndCity();
		
		headFragment.setTitleName(nameString);
			
		ManagerFragment.replaceFragment(this, R.id.bus_ticket_head, headFragment);
	}	
	
	private void requestURL()
	{
		new HttpThread(new AirTicketSearch(), info, this,this,R.string.error_air_real_info).start();
	}
	private void setText()
	{

		todayTextView.setText(String.format(getResources().getString(R.string.format_string_air_time_label),info.getCalendar().get(Calendar.MONTH)+1,info.getCalendar().get(Calendar.DAY_OF_MONTH)));
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
			List<AirTickets> tempList=ticketsAdapter.getData();
			switch (which) {
				case 0:
					Collections.sort(tempList,new ListCompare().getCompareAirTicketsByTime(1));
					break;
				case 1:
					Collections.sort(tempList,new ListCompare().getCompareAirTicketsByTime(2));
					break;
				case 2:
					Collections.sort(tempList,new ListCompare().getCompareAirTicketsByPrice(1));		
					break;
				case 3:				
					Collections.sort(tempList,new ListCompare().getCompareAirTicketsByPrice(2));
					break;

			}
			selectType=which;
			ticketsAdapter.update(tempList);
			dialog.cancel();
		}
	}

}
