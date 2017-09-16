package com.example.smarttraffic.taxi;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.TextViewUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

/**
 * 出租车详情
 * 
 * @author Administrator
 * 
 */
public class TaxiDetailActivity extends FragmentActivity
{
	public static final String TAXI_NAME = "taxi_name";
	String taxiName;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_taxi_detail);

		taxiName = getIntent().getStringExtra(TAXI_NAME);

		initHead();

		request(taxiName);
	}

	private void initHead()
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("出租车详情");

		ManagerFragment.replaceFragment(this, R.id.taxi_list_head, fragment);
	}

	TaxiDetail detail;
	TaxiPhone phone;

	private void request(final String name)
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				return JSON.parseObject(
						JSON.parseObject(data).getJSONArray("results")
								.getJSONObject(0).toJSONString(),
						TaxiDetail.class);
			}
		}, new BaseRequest()
		{
			@Override
			public PostParams CreatePostParams()
			{
				PostParams params = new PostParams();

				params.setUrl(HttpUrlRequestAddress.TAXI_DETAIL_URL);
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("taxiName", name);

				params.setParams(p);

				return params;
			}

		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				detail = (TaxiDetail) data;

				show();
			}
		}).start();
	}

	private void show()
	{
		if (detail != null)
		{
			TextViewUtil
					.setText(this, R.id.taxi_detail_color, detail.getCpys());
			TextViewUtil.setText(this, R.id.taxi_detail_kind, detail.getCp());
			TextViewUtil.setText(this, R.id.taxi_detail_cph, detail.getCphm());
			TextViewUtil.setText(this, R.id.taxi_detail_company,
					detail.getYhmc());
		}
	}

	public void call(View v)
	{
		GetDialog.messageDialog(this, "提示：", "是否拨号？", "是",
				new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						if(phone==null)
							requestPhone(taxiName);
						else
							call();

						dialog.cancel();
					}
				}, "否", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.cancel();
					}
				}).show();
	}

	private void requestPhone(final String name)
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				System.out.println(data);

				return JSON.parseObject(
						JSON.parseObject(data).getJSONArray("results")
								.getJSONObject(0).toJSONString(),
						TaxiPhone.class);
			}
		}, new BaseRequest()
		{
			@Override
			public PostParams CreatePostParams()
			{
				PostParams params = new PostParams();

				params.setUrl(HttpUrlRequestAddress.TAXI_PHONE_URL);
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("taxiName", name);

				params.setParams(p);

				return params;
			}
		}, new UpdateView()
		{
			@Override
			public void update(Object data)
			{
				phone = (TaxiPhone) data;

				call();
			}
		}, "未查到此出租车联系方式").start();
	}
	
	private void call()
	{
		if(phone!=null)
		{
			String phone_number = phone.getPhone();
			phone_number = phone_number.trim();

			if (phone_number != null && !phone_number.equals(""))
			{
				Intent intent = new Intent(Intent.ACTION_CALL,
						Uri.parse("tel:" + phone_number));
				TaxiDetailActivity.this.startActivity(intent);// 内部类
			}
		}
	}
}
