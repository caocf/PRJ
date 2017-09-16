package com.example.smarttraffic.news;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.Filter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;

public class NewsJTGZDetailActivity extends FragmentActivity
{
	public static final String DETAIL_NEWS_JTGZ="detail_news_jtgz";
	JTGZ jtgz;
	
	TextView titleTextView;
	TextView authorTextView;
	TextView dateTextView;
	TextView contentTextView;
	
	NewsDetailRequest request;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_detail);
		jtgz =(JTGZ)getIntent().getSerializableExtra(DETAIL_NEWS_JTGZ);
		
		init();
	}
	
	public void init()
	{
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName("新闻详情");
		ManagerFragment.replaceFragment(this, R.id.news_detail_head, fragment);
		
		titleTextView=(TextView)findViewById(R.id.news_detail_title);
		authorTextView=(TextView)findViewById(R.id.news_detail_author);
		dateTextView=(TextView)findViewById(R.id.news_detail_date);
		contentTextView=(TextView)findViewById(R.id.news_detail_content);
		
		if(jtgz!=null)
		{
			titleTextView.setText(jtgz.getBT());
			authorTextView.setText("来源："+jtgz.getFBR());
			dateTextView.setText("日期："+jtgz.getFBSJ());
			contentTextView.setText(Html.fromHtml(Filter.filterHtmlStyle(jtgz.getNR())));
		}
	}
	
}
