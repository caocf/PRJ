package com.example.smarttraffic.tripPlan.fragment;

import java.util.Calendar;

import com.example.smarttraffic.HeadFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.adapter.BaseAdapterLongClickWrapper;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.smartBus.SmartBusTransferListActivity;
import com.example.smarttraffic.tripPlan.CommonSelfStategyActivity;
import com.example.smarttraffic.tripPlan.TripFavorActivity;
import com.example.smarttraffic.tripPlan.adapter.HistoryAdapter;
import com.example.smarttraffic.tripPlan.bean.CommonDrivingPlan;
import com.example.smarttraffic.tripPlan.bean.History;
import com.example.smarttraffic.tripPlan.db.TripHistoryDB;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.util.ViewSetter;
import com.example.smarttraffic.view.InputListView;
import com.example.smarttraffic.view.InputListViewListener;
import com.example.smarttraffic.view.VoiceListener;

import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 公共交通出行
 * @author Administrator zhou
 *
 */
public class CommonDrivingFragment extends Fragment
{
	TextView timeTextView;						//出行时间
	TextView methodTextView;					//出行策略
	HistoryAdapter historyAdapter;
	ListView historyListView;
	InputListView startInputListView;
	InputListView endInputListView;
	Button searchButton;
	ImageView exchangeImageView;
	
	CommonDrivingPlan plan;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_trip_driving_common,container, false);
	
		initHead();			
		plan=new CommonDrivingPlan();			
		initView(rootView);
		return rootView;
	}
	
	/**
	 * 初始化标题
	 */
	private void initHead()
	{
		HeadFragment headFragment=new HeadFragment();
		headFragment.setTitleName("公共规划");		
		ManagerFragment.replaceFragment(getActivity(), R.id.trip_driving_self_head, headFragment);
	}
	
	/**
	 * 初始化视图
	 * @param rootView
	 */
	private void initView(View rootView)
	{
		timeTextView=(TextView)rootView.findViewById(R.id.trip_driving_self_date);
		methodTextView=(TextView)rootView.findViewById(R.id.trip_driving_self_method);
		historyListView=(ListView)rootView.findViewById(R.id.trip_driving_self_history);
		searchButton=(Button)rootView.findViewById(R.id.trip_driving_self_search);
		endInputListView=(InputListView)rootView.findViewById(R.id.trip_driving_self_end);
		startInputListView=(InputListView)rootView.findViewById(R.id.trip_driving_self_start);		
		exchangeImageView=(ImageView)rootView.findViewById(R.id.bus_search_exchange);
		
		startInputListView.setHint(getResources().getString(R.string.string_trip_enter_start_city_label));
		endInputListView.setHint(getResources().getString(R.string.string_trip_enter_end_city_label));
		
		onclick click=new onclick();
		timeTextView.setOnClickListener(click);
		methodTextView.setOnClickListener(click);
		searchButton.setOnClickListener(click);
		exchangeImageView.setOnClickListener(click);
		
		plan.setStrategy(1);
		historyListView.setOnItemClickListener(new onHistoryItemClick());
		new BaseAdapterLongClickWrapper(historyListView);
		
		startInputListView.setListeners(new VoiceListener(getActivity(), startInputListView),0);
		endInputListView.setListeners(new VoiceListener(getActivity(), endInputListView), 0);	
		startInputListView.setListeners(new inputFavorListener(),1);
		endInputListView.setListeners(new inputFavorListener(), 1);		
		startInputListView.setListeners(new inputLocationListener(startInputListView,1), 2);
		endInputListView.setListeners(new inputLocationListener(endInputListView,2), 2);
		
		plan.setCalendar(Calendar.getInstance());
		updateTime();
	}
	
	/**
	 * 更新时间
	 */
	public void updateTime()
	{	
		timeTextView.setText(plan.getCalendar().get(Calendar.MONTH)+1+"月"+plan.getCalendar().get(Calendar.DAY_OF_MONTH)+"日 ");
		if(plan.getCalendar().get(Calendar.HOUR_OF_DAY)<10)
			timeTextView.setText(timeTextView.getText()+"0");
		timeTextView.setText(timeTextView.getText().toString()+plan.getCalendar().get(Calendar.HOUR_OF_DAY)+":");
		if(plan.getCalendar().get(Calendar.MINUTE)<10)
			timeTextView.setText(timeTextView.getText()+"0");
		timeTextView.setText(timeTextView.getText().toString()+plan.getCalendar().get(Calendar.MINUTE));
	}
	
	/**
	 * 历史记录内容点击事件监听
	 */
	class onHistoryItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		{
			History data=historyAdapter.getData().get(position);
			TripHistoryDB dbOperate=new TripHistoryDB(getActivity(),true);
			dbOperate.toUpdate(data);
			
			search(data.getStart(), data.getEnd());
		}
	}
	
	public static final String DRIVING_INFO="driving_info";
	public static final String STATEGY="stategy";
	
	/**
	 * 视图点击监听事件
	 *
	 */
	class onclick implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.trip_driving_self_date:
				oncreateDialog(2).show();
				oncreateDialog(1).show();				
				break;
			
			case R.id.trip_driving_self_method:
				Bundle bundleStategy=new Bundle();
				bundleStategy.putInt(STATEGY, plan.getStrategy());
				StartIntent.startIntentForResultWithParams(getActivity(), CommonSelfStategyActivity.class,2,bundleStategy);
				
				break;
				
			case R.id.bus_search_exchange:
				ViewSetter.exchangeInputlistValue(startInputListView, endInputListView);
				break;
				
			case R.id.trip_driving_self_search:
				saveHistory();
				search(startInputListView.getText(), endInputListView.getText());
				break;
			}			
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode==2 && resultCode==2)
		{
			plan.setStrategy(data.getExtras().getInt(CommonSelfStategyActivity.FAVOR_RESULT, 0));
			methodTextView.setText(getResources().getStringArray(R.array.array_trip_common_stategy)[plan.getStrategy()]);
		}
	}
	
	/**
	 * 创建时间对话框
	 * @param i 选项
	 * @return 对话框
	 */
	public Dialog oncreateDialog(int i)
	{
		if(plan.getCalendar()==null)
		{
			Calendar calendar=Calendar.getInstance();
			plan.setCalendar(calendar);
		}
		
		if(i==1)
		{
			return GetDialog.getDateDialog(getActivity(), new dateListener(), plan.getCalendar().get(Calendar.YEAR), plan.getCalendar().get(Calendar.MONTH), plan.getCalendar().get(Calendar.DAY_OF_MONTH));
		}
		else
		{
			return GetDialog.getTimeDialog(getActivity(), new timeListener(), plan.getCalendar().get(Calendar.HOUR_OF_DAY), plan.getCalendar().get(Calendar.MINUTE), true);
		}
	}
	
	/**
	 * 日期对话框结果监听函数
	 *
	 */
	class dateListener implements OnDateSetListener
	{

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
		{
			plan.getCalendar().set(Calendar.YEAR, year);
			plan.getCalendar().set(Calendar.MONTH, monthOfYear);
			plan.getCalendar().set(Calendar.DAY_OF_MONTH, dayOfMonth);
		}
		
	}
	
	/**
	 * 时间对话框监听函数
	 *
	 */
	class timeListener implements OnTimeSetListener
	{
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute)
		{
			plan.getCalendar().set(Calendar.HOUR_OF_DAY, hourOfDay);
			plan.getCalendar().set(Calendar.MINUTE, minute);
			updateTime();
		}
	}
	
	private void search(String start,String end)
	{	
		if(start.equals(""))
		{
			Toast.makeText(getActivity(), "起点不能为空", Toast.LENGTH_SHORT).show();
		}
		else if(end.equals(""))
		{
			Toast.makeText(getActivity(), "终点不能为空", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Bundle bundle=new Bundle();
			bundle.putString(SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_NAME, start);
			bundle.putString(SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_NAME, end);
			
			StartIntent.startIntentWithParam(getActivity(), com.example.smarttraffic.smartBus.SelectPointActivity.class, bundle);
		}
	}
	
	/**
	 * 加载搜索历史记录
	 */
	private void loadHistory()
	{
		TripHistoryDB dbOperate=new TripHistoryDB(getActivity(),true);
		
		if(historyListView.getAdapter()==null)
		{
			historyAdapter=new HistoryAdapter(getActivity(), dbOperate.selectForHistory());
			historyAdapter.setClearClickListener(new onClearListener());
			historyListView.setAdapter(historyAdapter);
		}
		else
		{
			historyAdapter.update(dbOperate.selectForHistory());
		}
		
		dbOperate.CloseDB();
	}
	
	/**
	 * 保存搜索历史记录
	 */
	private void saveHistory()
	{
		String start=startInputListView.getText();
		String end=endInputListView.getText();
		
		if(!start.equals("") && !end.equals(""))
		{
			TripHistoryDB dbOperate=new TripHistoryDB(getActivity(),true);
			
			History history=new History();
			history.setStart(start);
			history.setEnd(end);
			
			if(!dbOperate.isHistory(history))
			{
				dbOperate .insert(history);				
			}
			dbOperate.CloseDB();
		}
	}
	
	/**
	 * 删除事件
	 * @author Administrator
	 *
	 */
	class onClearListener implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			historyAdapter.Clear(getActivity());
			loadHistory();
			showAndHiddenHistory();
		}
	}
	
	public static final String MY_LOCATION="我的位置";
	
	/**
	 * 输入选项监听函数
	 *
	 */
	class inputLocationListener implements InputListViewListener
	{
		private InputListView inputListView;
		private int id;
		
		public inputLocationListener(InputListView inputListView,int id)
		{
			this.inputListView=inputListView;
			this.id=id;
		}
		
		@Override
		public void inputSelectListener()
		{
			inputListView.setText(MY_LOCATION);	
			inputListView.showList(false);
			myLocationExchangeLock(id);
		}
	}
	
	/**
	 * 
	 * @param i
	 */
	public void myLocationExchangeLock(int i)
	{
		String start=startInputListView.getText();
		String end=endInputListView.getText();
		
		if(i==1 && end.equals(MY_LOCATION))
		{
			endInputListView.setText("");
		}
		else if(i==2 && start.equals(MY_LOCATION))
		{
			startInputListView.setText("");
		}
	}
	
	class inputFavorListener implements InputListViewListener
	{		
		@Override
		public void inputSelectListener()
		{
			StartIntent.startIntent(getActivity(), TripFavorActivity.class);
		}
	}
	
	/**
	 * 显示隐藏历史记录
	 */
	private void showAndHiddenHistory()
	{
		if(historyAdapter.getCount()==1)
		{
			getActivity().findViewById(R.id.layout_trip_driving_self).setVisibility(View.GONE);
		}
		else
		{
			getActivity().findViewById(R.id.layout_trip_driving_self).setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onResume()
	{
		loadHistory();	
		showAndHiddenHistory();
		
		super.onResume();
	}
}
