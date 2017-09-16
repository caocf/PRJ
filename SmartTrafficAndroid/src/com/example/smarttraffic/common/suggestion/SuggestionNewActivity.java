package com.example.smarttraffic.common.suggestion;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.user.Message;
import com.example.smarttraffic.user.UserControl;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class SuggestionNewActivity extends FragmentActivity
{
	TextView titleTextView;
	TextView contentTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_new_suggestion);

		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("意见建议");

		ManagerFragment.replaceFragment(this, R.id.suggestion_head, fragment);

		titleTextView = (TextView) findViewById(R.id.suggestion_title);
		contentTextView = (TextView) findViewById(R.id.suggestion_content);
	}

	public void submit(View v)
	{
		switch (v.getId())
		{
		case R.id.suggestion_submit:
			check();

			break;
		}
	}

	public void check()
	{
		if (UserControl.getUser() == null)
		{
			Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
		} else if (titleTextView.getText().toString().equals(""))
		{
			Toast.makeText(this, "标题不要为空", Toast.LENGTH_SHORT).show();
		} else if (contentTextView.getText().toString().equals(""))
		{
			Toast.makeText(this, "内容不要为空", Toast.LENGTH_SHORT).show();
		} else
		{
			new HttpThread(new BaseSearch()
			{
				@Override
				public Object parse(String data)
				{
					return JSON.parseObject(JSON.parseObject(data).getJSONObject("message").toJSONString(),Message.class);
				}
			}, new BaseRequest()
			{
				@Override
				public PostParams CreatePostParams()
				{
					PostParams postParams = new PostParams();

					postParams.setUrl(HttpUrlRequestAddress.SUGGESTION_ADD_URL);

					Map<String, Object> p = new HashMap<String, Object>();
					p.put("userid", UserControl.getUser().getUserid());
					p.put("title", titleTextView.getText().toString());
					p.put("content", contentTextView.getText().toString());

					postParams.setParams(p);

					return postParams;
				}
			}, new UpdateView()
			{
				@Override
				public void update(Object data)
				{
					Message message=(Message) data;
					
					Toast.makeText(SuggestionNewActivity.this, message.getStatus()==1?"提交成功":"提交失败",Toast.LENGTH_SHORT).show();
				
					SuggestionNewActivity.this.finish();
				}
			}, SuggestionNewActivity.this, "提交意见建议失败").start();
		}
	}
}
