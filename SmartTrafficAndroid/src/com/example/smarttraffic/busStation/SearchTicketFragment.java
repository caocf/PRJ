package com.example.smarttraffic.busStation;

import java.util.Calendar;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.adapter.BaseAdapterLongClickWrapper;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.HistoryDBOperate;
import com.example.smarttraffic.favor.TwoItemsAdapter;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.SharePreference;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.util.ViewSetter;
import com.example.smarttraffic.view.ViewAnim;

import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
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

public class SearchTicketFragment extends Fragment
{
	TextView startTextView;
	TextView endTextView;

	View searchTimeLayout;
	TextView searchTimeTextView;
	TextView searchKindTextView;
	Button searchEnterButton;
	ListView searchHistoryListView;
	TwoItemsAdapter twoItemsAdapter;

	TextView textView1;
	TextView textView2;
	TextView textView3;

	ImageView exchangeImageView;

	BusTicketRequest request;

	boolean bConnenct;
	boolean bFuzzy;
	boolean bTicket;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_search_ticket,
				container, false);

		initView(rootView);
		request = new BusTicketRequest();
		request.setBusType(0);
		request.setSearchType(3);

		return rootView;
	}

	private void initView(View rootView)
	{
		startTextView = (TextView) rootView
				.findViewById(R.id.search_start_city);
		endTextView = (TextView) rootView.findViewById(R.id.search_end_city);
		searchKindTextView = (TextView) rootView.findViewById(R.id.search_kind);
		searchTimeLayout = rootView.findViewById(R.id.search_time_layout);
		searchTimeTextView = (TextView) rootView.findViewById(R.id.search_time);
		searchEnterButton = (Button) rootView
				.findViewById(R.id.search_ticket_button);
		searchHistoryListView = (ListView) rootView
				.findViewById(R.id.search_ticket_history);

		new BaseAdapterLongClickWrapper(searchHistoryListView);

		textView1 = (TextView) rootView
				.findViewById(R.id.search_bus_ticket_fuzzy_station);
		textView2 = (TextView) rootView
				.findViewById(R.id.search_bus_ticket_connect);
		textView3 = (TextView) rootView
				.findViewById(R.id.search_bus_ticket_left);

		exchangeImageView = (ImageView) rootView
				.findViewById(R.id.bus_search_exchange);

		onclickListener listener = new onclickListener();

		searchTimeLayout.setOnClickListener(listener);
		searchTimeTextView.setOnClickListener(listener);
		startTextView.setOnClickListener(listener);
		endTextView.setOnClickListener(listener);
		searchEnterButton.setOnClickListener(listener);
		searchKindTextView.setOnClickListener(listener);
		textView1.setOnClickListener(listener);
		textView2.setOnClickListener(listener);
		textView3.setOnClickListener(listener);
		exchangeImageView.setOnClickListener(listener);

		SharePreference sharePreference = new SharePreference(getActivity());
		int i = sharePreference
				.getValueForInt(SharePreference.BUS_SHARE_DEFAULT_DATE);
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, i);

		updateTime();
		searchKindTextView.setText(getActivity().getResources().getStringArray(
				R.array.bus_kind)[0]);
	}

	Calendar calendar;

	private void updateTime()
	{
		searchTimeTextView.setText(String.format(
				getResources().getString(
						R.string.format_string_air_time_full_label),
				calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH),
				DoString.GetWeekName(calendar)));
	}

	class onItemSelectClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			Bundle resultInfo = new Bundle();

			request.setCalendar(calendar);

			request.setStartCity(twoItemsAdapter.getData().get(position)
					.getStart());
			request.setEndCity(twoItemsAdapter.getData().get(position).getEnd());

			resultInfo.putSerializable(SEARCH_TICKET_NAME, request);
			StartIntent.startIntentWithParam(getActivity(),
					TicketsInfoActivity.class, resultInfo);
		}
	}

	class onclickListener implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.search_ticket_button:
				search();
				updateDB();
				break;

			case R.id.search_start_city:
				StartIntent.startIntentForResult(getActivity(),
						SelectCityActivity.class, 1);
				break;

			case R.id.search_end_city:
				StartIntent.startIntentForResult(getActivity(),
						SelectCityActivity.class, 2);
				break;
			case R.id.search_kind:
				onCreateDialog(1).show();
				break;
			case R.id.search_time_layout:
			case R.id.search_time:
				onCreateDialog(2).show();

				break;
			case R.id.search_bus_ticket_fuzzy_station:
				bConnenct = exchange(textView1, bConnenct);
				break;
			case R.id.search_bus_ticket_connect:
				bFuzzy = exchange(textView2, bFuzzy);
				break;
			case R.id.search_bus_ticket_left:
				bTicket = exchange(textView3, bTicket);
				break;

			case R.id.bus_search_exchange:
				if (firstExchange == 0)
				{
					firstExchange++;
					ViewSetter.exchangeTextValue(startTextView, endTextView);
				}

				firstExchange++;
				new ViewAnim().exchangeAnim(startTextView, endTextView);
				ViewSetter.exchangeTextValue(startTextView, endTextView);

				break;
			}
		}
	}

	int firstExchange;

	private boolean exchange(TextView textView, int[] drawable, boolean status)
	{
		Drawable drawable1;
		if (status)
			drawable1 = getResources().getDrawable(drawable[0]);
		else
			drawable1 = getResources().getDrawable(drawable[1]);
		drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
				drawable1.getMinimumHeight());

		textView.setCompoundDrawables(drawable1, null, null, null);

		return !status;
	}

	private boolean exchange(TextView textView, boolean status)
	{
		int[] resource = new int[] { R.drawable.item_uncheck,
				R.drawable.item_check };
		return exchange(textView, resource, status);
	}

	public static final String SEARCH_TICKET_NAME = "search_tickets_request";

	public void search()
	{
		Bundle resultInfo = new Bundle();

		request.setCalendar(calendar);

		if (firstExchange % 2 == 0)
		{
			request.setStartCity(startTextView.getText().toString());
			request.setEndCity(endTextView.getText().toString());
		} else
		{
			request.setStartCity(endTextView.getText().toString());
			request.setEndCity(startTextView.getText().toString());
		}

		resultInfo.putSerializable(SEARCH_TICKET_NAME, request);

		if (bTicket)
		{
			resultInfo.putBoolean(TicketsInfoActivity.IS_SEARCH_TICKETS, true);
		} else
		{

		}
		StartIntent.startIntentWithParam(getActivity(),
				TicketsInfoActivity.class, resultInfo);

	}

	public void updateDB()
	{
		String start = startTextView.getText().toString();
		String end = endTextView.getText().toString();
		if (start != "" && end != "")
		{
			HistoryDBOperate dbOperate = new HistoryDBOperate(getActivity());
			if (!dbOperate.isHistory(ContentType.BUS_STATION_SEARCH_HISROTY,
					start, end))
				dbOperate.insert(ContentType.BUS_STATION_SEARCH_HISROTY, start,
						end);
			dbOperate.CloseDB();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 1)
		{
			if (resultCode == 1)
			{
				String startCity = data.getExtras().getString(
						SelectCityActivity.INTENT_PARAM);
				ViewSetter.setViewValue(startTextView, startCity);
			}
		} else if (requestCode == 2)
		{
			if (resultCode == 1)
			{
				String endCity = data.getExtras().getString(
						SelectCityActivity.INTENT_PARAM);
				ViewSetter.setViewValue(endTextView, endCity);
			}
		}
	}

	public Dialog onCreateDialog(int i)
	{
		Dialog d = null;
		if (i == 2)
		{
			d = GetDialog.getDateDialog(getActivity(),
					new TimeOnclickListener(), calendar);
		} else if (i == 1)
		{
			d = GetDialog.getRadioDialog(getActivity(), "客车类型",
					R.array.bus_kind, new radioOnclick(), "确定", null);
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

	class radioOnclick implements OnClickListener
	{

		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			searchKindTextView.setText(getActivity().getResources()
					.getStringArray(R.array.bus_kind)[which]);
			request.setBusType(which);
		}
	}

	@Override
	public void onResume()
	{
		HistoryDBOperate dbOperate = new HistoryDBOperate(getActivity());

		if (searchHistoryListView.getAdapter() == null)
		{
			twoItemsAdapter = new TwoItemsAdapter(
					getActivity(),
					dbOperate
							.selectForTwoItem(ContentType.BUS_STATION_SEARCH_HISROTY));
			searchHistoryListView.setAdapter(twoItemsAdapter);
		} else
		{
			twoItemsAdapter.update(dbOperate
					.selectForTwoItem(ContentType.BUS_STATION_SEARCH_HISROTY));
		}

		searchHistoryListView.setOnItemClickListener(new onItemSelectClick());
		dbOperate.CloseDB();

		super.onResume();
	}
}
