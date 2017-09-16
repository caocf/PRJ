package com.example.smarttraffic.driverguide;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.smarttraffic.R;

/**
 * 
 * @author Administrator
 * 
 */
public class DrivingGuideIndexFragment extends Fragment
{

	private String[] list = new String[] {
			"http://115.231.73.253/jxtpi/qlw.jsp",
			"http://115.231.73.253/jxtpi/xzq.jsp",
			"http://115.231.73.253/jxtpi/nhq.jsp",
			"http://115.231.73.253/jxtpi/zhn.jsp",
			"http://115.231.73.253/jxtpi/nhn.jsp" };

	private TextView[] zones;
	
	TextView zone1;
	TextView zone2;
	TextView zone3;
	TextView zone4;
	TextView zone5;

	WebView webView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_drving_guide_index,
				container, false);

		initView(rootView);
		
		

		return rootView;
	}

	private void initView(View root)
	{
		zone1 = (TextView) root.findViewById(R.id.zone1);
		zone2 = (TextView) root.findViewById(R.id.zone2);
		zone3 = (TextView) root.findViewById(R.id.zone3);
		zone4 = (TextView) root.findViewById(R.id.zone4);
		zone5 = (TextView) root.findViewById(R.id.zone5);

		webView = (WebView) root.findViewById(R.id.index_webview);

		webView.getSettings().setJavaScriptEnabled(true);
		
//		webView.getSettings().setUseWideViewPort(true);
//		webView.getSettings().setLoadWithOverviewMode(true);
		
		zone1.setOnClickListener(new clickListener(0));
		zone2.setOnClickListener(new clickListener(1));
		zone3.setOnClickListener(new clickListener(2));
		zone4.setOnClickListener(new clickListener(3));
		zone5.setOnClickListener(new clickListener(4));
		
		zones=new TextView[5];
		zones[0]=zone1;
		zones[1]=zone2;
		zones[2]=zone3;
		zones[3]=zone4;
		zones[4]=zone5;
		
		new clickListener(0).onClick(null);
		
	}

	class clickListener implements OnClickListener
	{

		int i;
		public clickListener(int i)
		{
			this.i=i;
		}
		
		@Override
		public void onClick(View v)
		{
			for(int j=0;j<zones.length;j++)
			{
				if(i==j)
					zones[j].setTextColor(0xff2746a2);
				else
					zones[j].setTextColor(0xff666666);
			}
			
			webView.loadUrl(list[i]);
		}
	}
	


	@Override
	public void onResume()
	{
		super.onResume();
	}

}
