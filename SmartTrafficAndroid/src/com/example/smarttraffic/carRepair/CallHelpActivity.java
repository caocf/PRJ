package com.example.smarttraffic.carRepair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.HistoryDBOperate;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.view.VoiceEditTextListener;

import android.location.GpsStatus;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class CallHelpActivity extends FragmentActivity implements UpdateView
{
	public void update(Object data) 
	{
		uploadInfo("您的信息已被提交，请耐心等待...");
	};

	ListView dialogListView;
	CallHelpAdapter adapter;
	EditText contentTextView;

	ICNMKLocationListener locationListener;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_call_help);

		initHead();

		dialogListView = (ListView) findViewById(R.id.call_help_dialog_listview);
		adapter = new CallHelpAdapter(this, loadInfo());
		dialogListView.setAdapter(adapter);

		contentTextView = (EditText) findViewById(R.id.call_help_content);
	}

	public List<String> loadInfo()
	{
		HistoryDBOperate dbOperate = new HistoryDBOperate(this);
		List<String> result = dbOperate
				.selectForString(ContentType.CALL_HELP_DATE);
		dbOperate.CloseDB();

		if (result == null)
			result = new ArrayList<String>();

		result.add(0, getResources().getString(R.string.string_call_help));

		return result;
	}

	public void initHead()
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("补胎呼救");
		ManagerFragment.replaceFragment(this, R.id.car_repair_call_help_head,
				fragment);
	}

	public void voice(View v)
	{
		new VoiceEditTextListener(this, contentTextView).inputListener();
	}

	MapControl mapControl;

	public void location(View v)
	{
		if (mapControl == null)
			mapControl = new MapControl(this);

		locationListener = new ICNMKLocationListener()
		{
			@Override
			public void onLocationChanged(CNMKLocation arg0)
			{
				contentTextView.setText("您的当前位置：" + arg0.getLatitude() + "-"
						+ arg0.getLongitude());
				mapControl.removeLocationWithoutView();
				send(null);
			}

			@Override
			public void onGPSStatusChanged(GpsStatus arg0)
			{
			}
		};

		mapControl.setLocationListener(locationListener);
		mapControl.addLocationWithoutView();
	}

	public void uploadInfo(String str)
	{
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}

	String content;

	public void send(View v)
	{
		content = contentTextView.getText().toString();
		if (!content.equals(""))
		{
			HistoryDBOperate dbOperate = new HistoryDBOperate(this);
			dbOperate.insert(ContentType.CALL_HELP_DATE, content);
			dbOperate.CloseDB();

			adapter.add(content);
			dialogListView.setSelection(dialogListView.getBottom());

			contentTextView.setText("");

			new HttpThread(new BaseSearch(){@Override
			public Object parse(String data)
			{
				return data;
			}}, new BaseRequest()
			{
				@Override
				public PostParams CreatePostParams()
				{
					PostParams result = new PostParams();

					result.setUrl(HttpUrlRequestAddress.CALL_HELP_URL);

					Map<String, Object> r = new HashMap<String, Object>();
					r.put("content", content);
					result.setParams(r);

					return result;
				}
			}, this).start();

			

		} else
		{
			Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onPause()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.stop();
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.start();
		super.onResume();
	}
}
