package com.example.smarttraffic.news;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;

public class NewsDetailActivity extends FragmentActivity implements UpdateView
{
	@Override
	public void update(Object data)
	{
		if(data instanceof DetailNews)
		{
			DetailNews news=(DetailNews)data;
			
			titleTextView.setText(news.getTitle());
			authorTextView.setText("来源:"+news.getAuthor());
			dateTextView.setText("日期:"+news.getDate().replace("T"," "));
			contentTextView.setText(Html.fromHtml(news.getContent()));		
		}
	}
	
	public static final String DETAIL_NEWS_ID="detail_news_id";
	int id;
	
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
		id=getIntent().getIntExtra(DETAIL_NEWS_ID, -1);
		
		request=new NewsDetailRequest();
		request.setId(id);
		init();
		
		new HttpThread(new NewsDetailSearch(), request, this,this,R.string.error_news_detail_info).start();
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
	}
	
}
