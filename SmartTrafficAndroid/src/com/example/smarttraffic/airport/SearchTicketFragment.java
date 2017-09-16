package com.example.smarttraffic.airport;

import java.util.Calendar;
import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.adapter.BaseAdapterLongClickWrapper;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.HistoryDBOperate;
import com.example.smarttraffic.favor.TwoItemsAdapter;
import com.example.smarttraffic.favor.TwoItemsData;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.SharePreference;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.util.ViewSetter;
import com.example.smarttraffic.view.ViewAnim;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchTicketFragment extends Fragment
{
	TextView startTextView;
	TextView endTextView;
	TextView searchTimeView;
	View searchTimeTextView;
	TextView searchWeekTextView;
	TextView searchKindTextView;
	Button searchEnterButton;
	ListView searchHistoryListView;
	TwoItemsAdapter twoItemsAdapter;
		
	ImageView exchangeCityImageView;
	
	TextView textViewItem1;
	TextView textViewItem2;
	TextView textViewItem3;
	
	AirTicketRequest request;
	
	boolean bConnenct;
	boolean bReal;
	boolean bTicket;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_air_search_ticket,container, false);
		
		initView(rootView);
		request=new AirTicketRequest();
		request.setBusType(0);
		request.setSearchType(3);
		
		bConnenct=true;
		
		return rootView;
	}
	
	private void initView(View rootView)
	{		
		startTextView=(TextView)rootView.findViewById(R.id.search_start_city);
		endTextView=(TextView)rootView.findViewById(R.id.search_end_city);
		searchKindTextView=(TextView)rootView.findViewById(R.id.search_kind);
		searchTimeTextView=rootView.findViewById(R.id.search_time);
		searchWeekTextView=(TextView)rootView.findViewById(R.id.search_week);
		searchTimeView=(TextView)rootView.findViewById(R.id.search_time_date);
		searchEnterButton=(Button)rootView.findViewById(R.id.search_ticket_button);
		searchHistoryListView=(ListView)rootView.findViewById(R.id.search_ticket_history);
			
		new BaseAdapterLongClickWrapper(searchHistoryListView);
		
		exchangeCityImageView=(ImageView)rootView.findViewById(R.id.bus_search_exchange);
		
		textViewItem1=(TextView)rootView.findViewById(R.id.search_bus_ticket_fuzzy_station);
		textViewItem2=(TextView)rootView.findViewById(R.id.search_bus_ticket_connect);
		textViewItem3=(TextView)rootView.findViewById(R.id.search_bus_ticket_left);
		
		ViewOnclickListener listener=new ViewOnclickListener();
		
		searchTimeTextView.setOnClickListener(listener);
		searchTimeView.setOnClickListener(listener);
		searchWeekTextView.setOnClickListener(listener);
		startTextView.setOnClickListener(listener);
		endTextView.setOnClickListener(listener);
		searchEnterButton.setOnClickListener(listener);
		searchKindTextView.setOnClickListener(listener);
		textViewItem1.setOnClickListener(listener);
		textViewItem2.setOnClickListener(listener);
		textViewItem3.setOnClickListener(listener);
		exchangeCityImageView.setOnClickListener(listener);
		
		calendar=Calendar.getInstance();
		SharePreference sharePreference=new SharePreference(getActivity());
		int i=sharePreference.getValueForInt(SharePreference.AIR_SHARE_DEFAULT_DATE);
		calendar.add(Calendar.DAY_OF_MONTH,i);
		
		updateTime();
		
		searchKindTextView.setText(getActivity().getResources().getStringArray(R.array.bus_kind)[0]);
	}
	
	private void updateTime()
	{
		searchTimeView.setText(String.format(getResources().getString(R.string.format_string_air_time_label), calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH)));
		searchWeekTextView.setText(String.format(getResources().getString(R.string.format_string_air_time_week_lable), DoString.GetWeekName(calendar)));
	}
	
	class onItemSelectClick implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			Bundle param=new Bundle();
			
			request.setStartCity(twoItemsAdapter.getData().get(position).getStart());
			request.setEndCity(twoItemsAdapter.getData().get(position).getEnd());
			
			param.putSerializable(SEARCH_AIR_TICKET_NAME, request);
			
			StartIntent.startIntentWithParam(getActivity(), AirTicketsInfoActivity.class,param);
		}
		
	}
		
	class ViewOnclickListener implements View.OnClickListener
	{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.search_ticket_button:				
				search();
				break;
	
			case R.id.search_start_city:
				StartIntent.startIntentForResult(getActivity(), SelectCityActivity.class,1);
				break;
				
			case R.id.search_end_city:
				StartIntent.startIntentForResult(getActivity(), SelectCityActivity.class,2);
				break;
			case R.id.search_kind:
				onCreateDialog(1).show();
				break;
			case R.id.search_time:
			case R.id.search_time_date:
			case R.id.search_week:
				onCreateDialog(2).show();
				
				break;
			case R.id.search_bus_ticket_fuzzy_station:
				bConnenct=exchange(textViewItem1,bConnenct);
				break;
			case R.id.search_bus_ticket_connect:
				bReal=exchange(textViewItem2,bReal);
				break;
			case R.id.search_bus_ticket_left:
				bTicket=exchange(textViewItem3,bTicket);
				break;
				
			case R.id.bus_search_exchange:
				if(!firstExchange)
				{
					firstExchange=true;
					ViewSetter.exchangeTextValue(startTextView, endTextView);
				}
				new ViewAnim().exchangeAnim(startTextView, endTextView);
				ViewSetter.exchangeTextValue(startTextView, endTextView);
				break;
		}	
		}		
	}
	boolean firstExchange;
	
	public static final String IS_TICKET_SEARCH="is_ticket_search";
	private void search()
	{
		if(bTicket)
		{
			Toast.makeText(getActivity(), "暂无票务信息", Toast.LENGTH_SHORT).show();
			Bundle param=getSearchInfo();
			param.putBoolean(IS_TICKET_SEARCH, true);
			StartIntent.startIntentWithParam(getActivity(), AirTicketsInfoActivity.class,param);
		}
		else if(bReal)
		{
			Toast.makeText(getActivity(), "暂无正晚点信息", Toast.LENGTH_SHORT).show();
			Bundle param=getSearchInfo();
			StartIntent.startIntentWithParam(getActivity(), AirRealInfoActivity.class,param);
		}
		else if(!bTicket && !bReal)
		{
			Bundle param=getSearchInfo();			
			StartIntent.startIntentWithParam(getActivity(), AirTicketsInfoActivity.class,param);
		}
				
		updateDB();
	}
	
	private boolean exchange(TextView textView,int[] drawable,boolean status)
	{
		Drawable drawable1;
		if(status)
			drawable1= getResources().getDrawable(drawable[0]); 
		else
			drawable1=getResources().getDrawable(drawable[1]);
		drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
		
		textView.setCompoundDrawables(drawable1, null, null, null);
		
		return !status;
	}
	
	private  boolean exchange(TextView textView,boolean status)
	{
		int[] resource=new int[]{R.drawable.item_uncheck,R.drawable.item_check};
		return exchange(textView, resource,status);
	}
	
	public static final String SEARCH_AIR_TICKET_NAME="search_air_tickets_request";

	
	public Bundle getSearchInfo()
	{
		Bundle resultInfo=new Bundle();
		
		request.setCalendar(calendar);
		request.setStartCity(startTextView.getText().toString());
		request.setEndCity(endTextView.getText().toString());
		
		resultInfo.putSerializable(SEARCH_AIR_TICKET_NAME, request);
		
		return resultInfo;
	}
	
	public void updateDB()
	{
		String start=startTextView.getText().toString();
		String end=endTextView.getText().toString();
		
		if(!start.equals("") && !end.equals(""))
		{
			HistoryDBOperate dbOperate=new HistoryDBOperate(getActivity());
			
			if(!dbOperate.isHistory(ContentType.AIR_STATION_SEARCH_HISROTY,start,end))
				dbOperate.insert(ContentType.AIR_STATION_SEARCH_HISROTY,start,end);
			
			dbOperate.CloseDB();
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if(requestCode==1)
		{
			if(resultCode==1)
			{
				String startCity=data.getExtras().getString(SelectCityActivity.INTENT_PARAM);
				ViewSetter.setViewValue(startTextView, startCity);
			}
		}
		else if(requestCode==2)
		{
			if(resultCode==1)
			{
				String endCity=data.getExtras().getString(SelectCityActivity.INTENT_PARAM);
				ViewSetter.setViewValue(endTextView, endCity);
			}
		}
	}

	
	Calendar calendar;
	public Dialog onCreateDialog(int i)
	{
		Dialog d=null;
		if(i==2)
		{
			d=GetDialog.getDateDialog(getActivity(), new TimeOnclickListener(), calendar);
		}
		else if(i==1)
		{
			d=GetDialog.getRadioDialog(getActivity(),getResources().getString(R.string.string_air_type), R.array.array_air_kind, new RadioOnclick(), "确定",null);
		}
		return d;
	}
	
	class TimeOnclickListener implements OnDateSetListener
	{
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth)
		{
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, monthOfYear);
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			
			updateTime();
		}
	}
	
	class RadioOnclick implements OnClickListener
	{

		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
			searchKindTextView.setText(getActivity().getResources().getStringArray(R.array.array_air_kind)[which]);
			request.setBusType(which);
		}
	}
	
	@Override
	public void onResume()
	{
		HistoryDBOperate dbOperate=new HistoryDBOperate(getActivity());	
		List<TwoItemsData> data=dbOperate.selectForTwoItem(ContentType.AIR_STATION_SEARCH_HISROTY);
		
		if(twoItemsAdapter==null)
			twoItemsAdapter=new TwoItemsAdapter(getActivity(), data);
		dbOperate.CloseDB();
		
		if(searchHistoryListView.getAdapter()==null)
		{
			searchHistoryListView.setAdapter(twoItemsAdapter);
		}
		else
		{
			twoItemsAdapter.update(data);
		}
		searchHistoryListView.setOnItemClickListener(new onItemSelectClick());
		super.onResume();
	}
}
