package com.example.smarttraffic.busStation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
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

public class TicketsInfoActivity extends FragmentActivity implements UpdateView
{

	ListView ticketsListView;
	TicketsAdapter ticketsAdapter;

	TextView beforeTextView;
	TextView afterTextView;
	TextView todayTextView;
	
	BusTicketRequest info;
	BusNoRequest request;
	
	DrawerLayout drawerLayout;
	ListView condition1;
	ListView condition2;
		
	
	public static final String IS_SEARCH_TICKETS="is_search_tickets";
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Object data) 
	{
		if(data instanceof List<?>)
		{
			List<Ticket> tickets=(List<Ticket>)data;
			if(ticketsListView.getAdapter()==null)
			{
				if(!isTickets)
				{
					ticketsAdapter=new TicketsAdapter(this, tickets,1);
				}
				else
				{
					ticketsAdapter=new TicketsAdapter(this, tickets);
				}
				ticketsListView.setAdapter(ticketsAdapter);
				ticketsListView.setOnItemClickListener(new onItemSearchClick());
			}
			else 
			{
				ticketsAdapter.update(tickets);
				setText();
			}
		}
	}

	class onItemSearchClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		{
			BusNoRequest request=new BusNoRequest();
			request.setBusNo(ticketsAdapter.getData().get(position).getCarNumber());
			request.setType(1);
			Bundle bundle=new Bundle();
			bundle.putSerializable(SearchBusFragment.SEARCH_NAME, request);
			
			StartIntent.startIntentWithParam(TicketsInfoActivity.this,BusRealActivity.class,bundle);			
		}
	}
	
	boolean isTickets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tickets_info);
			
		isTickets=getIntent().getBooleanExtra(IS_SEARCH_TICKETS, false);
		
		dealParam();
		initHead();
		initView();
		
		requestURL();
	}
	
	private void dealParam()
	{
		if(getIntent().getSerializableExtra(SearchBusFragment.SEARCH_STATION_NAME)!=null)
		{
			request=(BusNoRequest)getIntent().getSerializableExtra(SearchBusFragment.SEARCH_STATION_NAME);
		}
		else
		{
			info=(BusTicketRequest)getIntent().getSerializableExtra(SearchTicketFragment.SEARCH_TICKET_NAME);
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
		String nameString;
		if(request!=null)
		{
			nameString="途径"+request.getBusNo()+"列车";			
		}
		else 
		{
			nameString=info.getStartCity()+"-"+info.getEndCity();
		}
		
		if(isTickets)
		{	
			HeadNameFragment headFragment=new HeadNameFragment();
					
			headFragment.setTitleName(nameString);
					
			ManagerFragment.replaceFragment(this, R.id.bus_ticket_head, headFragment);
		}
		else 
		{
			HeadFavorFragment fragment=new HeadFavorFragment();
			fragment.setTitleName(nameString);
			fragment.setRightName("余票");
			fragment.setRightListen(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					Bundle resultInfo=new Bundle();
					resultInfo.putSerializable(SearchTicketFragment.SEARCH_TICKET_NAME, info);
					resultInfo.putBoolean(IS_SEARCH_TICKETS, true);
					StartIntent.startIntentWithParam(TicketsInfoActivity.this, TicketsInfoActivity.class,resultInfo);
				}
			});
			
			ManagerFragment.replaceFragment(this, R.id.bus_ticket_head,fragment);
			
		}
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
				
			case R.id.ticket_search_reset:
				drawerLayout.closeDrawer(Gravity.RIGHT);
				
				break;
			case R.id.ticket_search_submit:
				drawerLayout.closeDrawer(Gravity.RIGHT);
				break;
		}
	}
	
	private void requestURL()
	{
		if(request!=null)
		{
			
			new HttpThread(new BusTicketSearch(), new BaseRequest(){@Override
			public PostParams CreatePostParams()
			{
				PostParams postParams=new PostParams();
				postParams.setUrl(HttpUrlRequestAddress.SMART_BUS_QUERY_BUS_STATION_TICKETES_BY_STATION_URL);
				Map<String, Object> m=new HashMap<String, Object>();
				m.put("stationName", request.getBusNo());
				postParams.setParams(m);
				
				return postParams;
			}}, this,this,R.string.error_bus_tickets_info).start();
		}
		else if(info!=null)
		{
			new HttpThread(new BusTicketSearch(), info, this,this,R.string.error_bus_tickets_info).start();
		}
	}
	private void setText()
	{
		if(request!=null)
		{
			todayTextView.setText(request.getCalendar().get(Calendar.MONTH)+1+"月"+request.getCalendar().get(Calendar.DAY_OF_MONTH)+"日");
		}
		else 
		{
			todayTextView.setText(info.getCalendar().get(Calendar.MONTH)+1+"月"+info.getCalendar().get(Calendar.DAY_OF_MONTH)+"日");
		}
	}
	
	private void modifyInfo(int i)
	{
		Calendar calendar=Calendar.getInstance();
		
		
		if(request!=null)
		{
			if(i==1)
			{		
				calendar=request.getCalendar();
				calendar.add(Calendar.DAY_OF_MONTH, -1);		
			}
			else
			{
				calendar=request.getCalendar();
				calendar.add(Calendar.DAY_OF_MONTH,1);	
			}
			
			request.setCalendar(calendar);
		}
		else 
		{
			if(i==1)
			{		
				calendar=info.getCalendar();
				calendar.add(Calendar.DAY_OF_MONTH, -1);
			}
			else
			{
				calendar=info.getCalendar();
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
			
			info.setCalendar(calendar);
		}
		
	}
	
	public void control(View v)
	{
		switch (v.getId()) {
		case R.id.bus_tickets_sort:		
			GetDialog.getRadioDialog(this, "排序", R.array.bus_search_sort_name, new sortOnclick(), "",null,selectType).show();
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
			List<Ticket> tempList=ticketsAdapter.getData();
			switch (which) {
				case 0:
					Collections.sort(tempList,new ListCompare().getCompareBusTicketsByTime(1));
					break;
				case 1:
					Collections.sort(tempList,new ListCompare().getCompareBusTicketsByTime(2));
					break;
				case 2:
					Collections.sort(tempList,new ListCompare().getCompareBusTicketsByPrice(1));		
					break;
				case 3:				
					Collections.sort(tempList,new ListCompare().getCompareBusTicketsByPrice(2));
					break;

			}
			selectType=which;
			ticketsAdapter.update(tempList);
			dialog.cancel();
		}
	}
}
