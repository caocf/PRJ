package com.huzhouport.integratedquery;

/*@author 沈丹丹
 * */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.ManagerDialog;
import com.huzhouport.leaveandovertime.MyFragmentPagerAdapter;
import com.huzhouport.main.User;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;

public class Search_tabs extends FragmentActivity
{
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;

	private ImageButton back;
	private TextView title, tv_cbjbxx, tv_cbzsxx, tv_cbwzxx, tv_cbqzxx,
			tv_cbjfxx, tv_cbjyxx;
	private HorizontalScrollView hs;
	private String isearchType; // 搜索类型整形
	private int scrolwidth = 160;
	
	User user;
	
	private String qzyc="0",jfyc="0",zsyc="0",wzyc="0";   //1是异常 0是不异常
	private String actionUrl, param1;
	private String shipname;
	private String shipnum;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_tab);

		user=(User)getIntent().getSerializableExtra("User");
		
		tv_cbjbxx = (TextView) findViewById(R.id.search_tab_cbjbxx);
		tv_cbzsxx = (TextView) findViewById(R.id.search_tab_cbzsxx);
		tv_cbwzxx = (TextView) findViewById(R.id.search_tab_cbwzxx);
		tv_cbqzxx = (TextView) findViewById(R.id.search_tab_cbqzxx);
		tv_cbjfxx = (TextView) findViewById(R.id.search_tab_cbjfxx);
		tv_cbjyxx = (TextView) findViewById(R.id.search_tab_cbjyxx);
		back = (ImageButton) findViewById(R.id.search_back);
		title = (TextView) findViewById(R.id.search_name);
		hs = (HorizontalScrollView) findViewById(R.id.search_tab_hs);
		Display mDisplay = getWindowManager().getDefaultDisplay();
		int W = mDisplay.getWidth();
		if (W >= 1080)
		{
			scrolwidth = 200;
		} else
		{
			scrolwidth = 160;
		}

		hs.setSmoothScrollingEnabled(true);

		Intent intent = getIntent();
		String getResult = intent.getStringExtra("result");
		String sTitle = intent.getStringExtra("title");
		isearchType = intent.getStringExtra("searchType");
		shipname= intent.getStringExtra("title");
		shipnum= intent.getStringExtra("shipnum");
		title.setText(sTitle);
		
		mPager = (ViewPager) findViewById(R.id.search_tab_viewPage);
		fragmentsList = new ArrayList<Fragment>();
		Fragment f_cbjbxx, f_cbzsxx, f_cbwzxx, f_cbqzxx, f_cbjfxx, f_cbjyxx;
		if (isearchType.equals("0"))
		{
			f_cbjbxx = BS_CBJBXX.newInstance(getResult, sTitle);
		} else
		{
			f_cbjbxx = BS_CBJBXX.newInstance("", sTitle);
		}
		if (isearchType.equals("1"))
		{
			f_cbzsxx = BS_CBZSXX.newInstance(getResult, sTitle);
		} else
		{
			f_cbzsxx = BS_CBZSXX.newInstance("", sTitle);
		}
		if (isearchType.equals("2"))
		{
			f_cbwzxx = BS_CBWZXX2.newInstance("", sTitle);
		} else
		{
			f_cbwzxx = BS_CBWZXX2.newInstance("", sTitle);
		}
		if (isearchType.equals("3"))
		{
			f_cbqzxx = BS_CBQZXX.newInstance(getResult, sTitle);
		} else
		{
			f_cbqzxx = BS_CBQZXX.newInstance("", sTitle);
		}
		if (isearchType.equals("4"))
		{
			f_cbjfxx = BS_CBJFXX.newInstance(getResult, sTitle);
		} else
		{
			f_cbjfxx = BS_CBJFXX.newInstance("", sTitle);
		}
		if (isearchType.equals("5"))
		{
			f_cbjyxx = BS_CBCJXX.newInstance(getResult, sTitle);
		} else
		{
			f_cbjyxx = BS_CBCJXX.newInstance("", sTitle);
		}
		fragmentsList.add(f_cbjbxx);
		fragmentsList.add(f_cbzsxx);
		fragmentsList.add(f_cbwzxx);
		fragmentsList.add(f_cbqzxx);
		fragmentsList.add(f_cbjfxx);
		fragmentsList.add(f_cbjyxx);
		mPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentsList));
		mPager.setCurrentItem(Integer.parseInt(isearchType));
		ChangeStyle(Integer.parseInt(isearchType));
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());

		tv_cbjbxx.setOnClickListener(new MyChoose(0));
		tv_cbzsxx.setOnClickListener(new MyChoose(1));
		tv_cbwzxx.setOnClickListener(new MyChoose(2));
		tv_cbqzxx.setOnClickListener(new MyChoose(3));
		tv_cbjfxx.setOnClickListener(new MyChoose(4));
		tv_cbjyxx.setOnClickListener(new MyChoose(5));
		back.setOnClickListener(new back());	
		new CheckShip().execute();
		if(user!=null)
		new Log(user.getName(), "查看船舶信息", GlobalVar.LOGSEE, getIntent()
				.getStringExtra("title")).execute();

	}

	String checkResult;

	class CheckShip extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{
			Map<String, Object> paramsDate = new HashMap<String, Object>();
			paramsDate.put("electricReportNew.shipName", getIntent()
					.getStringExtra("title"));

			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = "http://192.168.1.122:8080//HuZhouPort/" + "CheckElectricReport";
					//HttpUtil.BASE_URL + "CheckElectricReport";
			try
			{
				checkResult = hfu.post(actionUrl, paramsDate);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			initData(checkResult);
			super.onPostExecute(result);
		}

	}

	public void initData(String result)
	{
		System.out.println("check:" + result);

		if (result == null || result.equals("") || result.equals("null"))
			return;

		List<CheckResult> checkResults=new ArrayList<CheckResult>();
		
		try
		{
			JSONTokener jsonParser = new JSONTokener(result);

			JSONArray array = (JSONArray) jsonParser.nextValue();
			
			for(int i=0;i<array.length();i++)
			{
				JSONObject jsonObject = (JSONObject) array.opt(i);
				
				CheckResult checkResult=new CheckResult();
				
				checkResult.setAlarmLevel(jsonObject.getInt("alarmLevel"));

				if(jsonObject.has("description"))
					checkResult.setDescription(jsonObject.getString("description"));
				checkResult.setFilterName(jsonObject.getString("filterName"));
				
				checkResults.add(checkResult);
			}
		} catch (Exception e)
		{
		}

		String content="";
		
		for(int i=0;i<checkResults.size();i++)
		{
			CheckResult cr=checkResults.get(i);
			
			if(cr.getAlarmLevel()!=0)
			{
				if(cr.getFilterName().equals("违章检查器"))
				{
					content+="违章异常："+cr.getDescription()+"\n";
					wzyc="1";
				}
				else if(cr.getFilterName().equals("证书检查器"))
				{
					content+="证书异常："+cr.getDescription()+"\n";
					zsyc="1";
				}
				else if(cr.getFilterName().equals("规费检查器"))
				{
					content+="缴费异常："+cr.getDescription()+"\n";
					jfyc="1";
				}
				else if(cr.getFilterName().equals("签证检查器"))
				{
					content+="签证异常："+cr.getDescription()+"\n";
					qzyc="1";
				}	
			}
		}
		
		if(!content.equals(""))
		ManagerDialog.messageDialog(this, "船舶检查", content, "确定", null, "取消", null).show();
       
	}

	public class MyChoose implements OnClickListener
	{
		private int index = 0;

		public MyChoose(int i)
		{
			index = i;
		}

		@Override
		public void onClick(View v)
		{
			mPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageSelected(int arg0)
		{
			ChangeStyle(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
			hs.smoothScrollTo(arg0 * scrolwidth, 0);
		}

		@Override
		public void onPageScrollStateChanged(int arg0)
		{

		}
	}

	public void ChangeStyle(int number)
	{

		Drawable img_off;
		Resources res = getResources();
		img_off = res.getDrawable(R.drawable.fragment_title_bottom);
		img_off.setBounds(0, 0, img_off.getMinimumWidth(),
				img_off.getMinimumHeight());
		switch (number)
		{
		case 0:
			tv_cbjbxx.setCompoundDrawables(null, null, null, img_off);
			tv_cbjbxx.setTextColor(getResources().getColor(
					R.color.fragment_title));
			tv_cbzsxx.setCompoundDrawables(null, null, null, null);
			tv_cbzsxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbwzxx.setCompoundDrawables(null, null, null, null);
			tv_cbwzxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbqzxx.setCompoundDrawables(null, null, null, null);
			tv_cbqzxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjfxx.setCompoundDrawables(null, null, null, null);
			tv_cbjfxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjyxx.setCompoundDrawables(null, null, null, null);
			tv_cbjyxx.setTextColor(getResources().getColor(R.color.Light_gray));
			break;
		case 1:
			tv_cbzsxx.setCompoundDrawables(null, null, null, img_off);
			tv_cbzsxx.setTextColor(getResources().getColor(
					R.color.fragment_title));
			tv_cbjbxx.setCompoundDrawables(null, null, null, null);
			tv_cbjbxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbwzxx.setCompoundDrawables(null, null, null, null);
			tv_cbwzxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbqzxx.setCompoundDrawables(null, null, null, null);
			tv_cbqzxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjfxx.setCompoundDrawables(null, null, null, null);
			tv_cbjfxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjyxx.setCompoundDrawables(null, null, null, null);
			tv_cbjyxx.setTextColor(getResources().getColor(R.color.Light_gray));
			break;
		case 2:
			tv_cbwzxx.setCompoundDrawables(null, null, null, img_off);
			tv_cbwzxx.setTextColor(getResources().getColor(
					R.color.fragment_title));
			tv_cbzsxx.setCompoundDrawables(null, null, null, null);
			tv_cbzsxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjbxx.setCompoundDrawables(null, null, null, null);
			tv_cbjbxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbqzxx.setCompoundDrawables(null, null, null, null);
			tv_cbqzxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjfxx.setCompoundDrawables(null, null, null, null);
			tv_cbjfxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjyxx.setCompoundDrawables(null, null, null, null);
			tv_cbjyxx.setTextColor(getResources().getColor(R.color.Light_gray));
			break;
		case 3:
			tv_cbqzxx.setCompoundDrawables(null, null, null, img_off);
			tv_cbqzxx.setTextColor(getResources().getColor(
					R.color.fragment_title));
			tv_cbzsxx.setCompoundDrawables(null, null, null, null);
			tv_cbzsxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbwzxx.setCompoundDrawables(null, null, null, null);
			tv_cbwzxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjbxx.setCompoundDrawables(null, null, null, null);
			tv_cbjbxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjfxx.setCompoundDrawables(null, null, null, null);
			tv_cbjfxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjyxx.setCompoundDrawables(null, null, null, null);
			tv_cbjyxx.setTextColor(getResources().getColor(R.color.Light_gray));
			break;
		case 4:
			tv_cbjfxx.setCompoundDrawables(null, null, null, img_off);
			tv_cbjfxx.setTextColor(getResources().getColor(
					R.color.fragment_title));
			tv_cbzsxx.setCompoundDrawables(null, null, null, null);
			tv_cbzsxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbwzxx.setCompoundDrawables(null, null, null, null);
			tv_cbwzxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbqzxx.setCompoundDrawables(null, null, null, null);
			tv_cbqzxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjbxx.setCompoundDrawables(null, null, null, null);
			tv_cbjbxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjyxx.setCompoundDrawables(null, null, null, null);
			tv_cbjyxx.setTextColor(getResources().getColor(R.color.Light_gray));
			break;
		case 5:
			tv_cbjyxx.setCompoundDrawables(null, null, null, img_off);
			tv_cbjyxx.setTextColor(getResources().getColor(
					R.color.fragment_title));
			tv_cbzsxx.setCompoundDrawables(null, null, null, null);
			tv_cbzsxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbwzxx.setCompoundDrawables(null, null, null, null);
			tv_cbwzxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbqzxx.setCompoundDrawables(null, null, null, null);
			tv_cbqzxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjfxx.setCompoundDrawables(null, null, null, null);
			tv_cbjfxx.setTextColor(getResources().getColor(R.color.Light_gray));
			tv_cbjbxx.setCompoundDrawables(null, null, null, null);
			tv_cbjbxx.setTextColor(getResources().getColor(R.color.Light_gray));
			break;

		}

	}

	// 返回
	class back implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			finish();

		}

	}

	class ListTask4 extends AsyncTask<String, Integer, String>
	{
		public ListTask4(Context context)
		{
		}

		@Override
		protected String doInBackground(String... params)
		{

			if (isCancelled())
				return null;// 取消异步
			HttpFileUpTool hfu = new HttpFileUpTool();
			String result = "";

			try
			{
				hfu.sendPost1(actionUrl, param1);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			super.onPostExecute(result);
		}

	}
	
	/**
	 * 添加监听器
	 */
	/*private BDLocationListener locationListener = new BDLocationListener() {

		@Override
		public void onReceivePoi(BDLocation location) {

		}

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			
			double lon = (double) location.getLongitude();
			double lat = (double) location.getLatitude();
			
			actionUrl = HttpUtil.BASE_URL + "saveShipQueryLog";
			if(shipnum==null){
			param1 = "shipQueryLog.logUserid="+user.getUserId()+"&shipQueryLog.logUserName="+user.getName()
					+"&shipQueryLog.shipname="+shipname+"&shipQueryLog.qzyc="+qzyc+"&shipQueryLog.zsyc="+zsyc+"&shipQueryLog.jfyc="+jfyc+"&shipQueryLog.wzyc="+wzyc
					+"&shipQueryLog.lon="+lon+"&shipQueryLog.lan="+lat;
		    ListTask4 task = new ListTask4(
					Search_tabs.this); // 异步
			task.execute();
			}else{
			param1 = "shipQueryLog.logUserid="+user.getUserId()+"&shipQueryLog.logUserName="+user.getName()
					+"&shipQueryLog.shipname="+shipname+"&shipQueryLog.shipnum="+shipnum+"&shipQueryLog.qzyc="+qzyc+"&shipQueryLog.zsyc="+zsyc+"&shipQueryLog.jfyc="+jfyc+"&shipQueryLog.wzyc="+wzyc
			        +"&shipQueryLog.lon="+lon+"&shipQueryLog.lan="+lat;
			ListTask4 task = new ListTask4(
					Search_tabs.this); // 异步
			task.execute();
			}

		}
	};*/
	
}

class CheckResult
{
	int alarmLevel;
	String filterName;
	String description;
	public int getAlarmLevel()
	{
		return alarmLevel;
	}
	public void setAlarmLevel(int alarmLevel)
	{
		this.alarmLevel = alarmLevel;
	}
	public String getFilterName()
	{
		return filterName;
	}
	public void setFilterName(String filterName)
	{
		this.filterName = filterName;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
}
