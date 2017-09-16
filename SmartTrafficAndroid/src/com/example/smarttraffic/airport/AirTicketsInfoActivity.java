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

public class AirTicketsInfoActivity extends FragmentActivity implements UpdateView
{

	ListView ticketsListView;
	AirTicketsAdapter ticketsAdapter;

	TextView beforeTextView;
	TextView afterTextView;
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
			boolean isTickets=getIntent().getBooleanExtra(SearchTicketFragment.IS_TICKET_SEARCH, false);
			int kind=isTickets?2:0;
			
			List<AirTickets> dataAirTickets=(List<AirTickets>)data;
			if(ticketsListView.getAdapter()==null)
			{
				ticketsAdapter=new AirTicketsAdapter(this, dataAirTickets,kind);
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
			resultInfo.putString(AirNoActivity.SEARCH_AIR_NO,ticketsAdapter.getData().get(position).getAirNumber());
			StartIntent.startIntentWithParam(AirTicketsInfoActivity.this, AirNoActivity.class,resultInfo);
		}
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tickets_info);
			
		dealParam();
		initHead();
		initView();
		
		requestURL();
	}
	
	public static final String IS_AIR_PORT_SEARCH="is_air_port_search";
	public static final String AIR_PORT_NAME="air_port_name";
	
	boolean fromStation;
	String stationName;
	private void dealParam()
	{
		fromStation=getIntent().getBooleanExtra(IS_AIR_PORT_SEARCH, false);
		if(fromStation)
		{
			stationName=getIntent().getStringExtra(AIR_PORT_NAME);
			info=new AirTicketRequest();
			info.setStartCity(stationName);
			info.setByStation(true);
		}
		else
		{
			info=(AirTicketRequest)getIntent().getSerializableExtra(SearchTicketFragment.SEARCH_AIR_TICKET_NAME);
			if(info.getCalendar()==null)
				info.setCalendar(Calendar.getInstance());
		}	
	}
	
	private void initView()
	{
		ticketsListView=(ListView)findViewById(R.id.bus_ticket_list);
		beforeTextView=(TextView)findViewById(R.id.ticket_before_date);
		todayTextView=(TextView)findViewById(R.id.ticket_now_date);
		afterTextView=(TextView)findViewById(R.id.ticket_after_date);
		
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
		
		String nameString="";
		if(fromStation)
		{
			nameString="途径"+stationName+"列车";			
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
				if(ticketsAdapter!=null)
					ticketsAdapter.clear();
				requestURL();
				break;
			case R.id.ticket_after_date:
				modifyInfo(2);	
				if(ticketsAdapter!=null)
					ticketsAdapter.clear();
				requestURL();
				break;	
		}
	}
	
	private void requestURL()
	{
		if(info!=null)
		{
			new HttpThread(new AirTicketSearch(), info, this,this,R.string.error_air_tickets_info).start();
		}
	}
	private void setText()
	{
		if(info.getCalendar()==null)
			info.setCalendar(Calendar.getInstance());
		todayTextView.setText(String.format(getResources().getString(R.string.format_string_air_time_label),info.getCalendar().get(Calendar.MONTH)+1,info.getCalendar().get(Calendar.DAY_OF_MONTH)));		
	}
	
	private void modifyInfo(int i)
	{
		Calendar calendar=Calendar.getInstance();
				

		if(i==1)
		{		
			calendar=info.getCalendar();
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
		}
		else
		{
			calendar=info.getCalendar();
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
		}
		
		info.setCalendar(calendar);		
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
