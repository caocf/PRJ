package com.example.smarttraffic.smartBus;

import java.util.List;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.news.News;
import com.example.smarttraffic.news.NewsAdapter;
import com.example.smarttraffic.news.NewsSearch;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SmartBusNewsActivity extends FragmentActivity
{

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart_bus_news);

		initHead();
		listView = (ListView) findViewById(R.id.smart_bus_news_listview);

		search();
	}

	private void initHead()
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("公交快讯");
		ManagerFragment.replaceFragment(this, R.id.smart_bus_news_head,
				fragment);
	}

	private void search()
	{
		new HttpThread(new NewsSearch(), new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return HttpUrlRequestAddress.SMART_BUS_NEWS_LIST_URL;
			}
		}, new UpdateView()
		{

			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				List<News> news = (List<News>) data;
				listView.setAdapter(new NewsAdapter(SmartBusNewsActivity.this,
						news));
				listView.setOnItemClickListener(new OnItemClickListener()
				{
					public void onItemClick(
							android.widget.AdapterView<?> parent, View view,
							int position, long id)
					{
						int newsID = ((News) (parent.getAdapter()
								.getItem(position))).getId();
						Bundle bundle = new Bundle();
						bundle.putInt(
								SmartBusNewsDetailActivity.DETAIL_NEWS_ID,
								newsID);

						StartIntent.startIntentWithParam(
								SmartBusNewsActivity.this,
								SmartBusNewsDetailActivity.class, bundle);
					};
				});
			}
		}, this, R.string.error_smart_bus_news_info).start();
	}
}
