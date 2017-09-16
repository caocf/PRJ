package com.example.smarttraffic.train;

import com.example.smarttraffic.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TrainStationInfoFragment extends Fragment
{
	
	String url;
		
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	WebView webView;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_air_station_info,container, false);
		webView=(WebView)rootView.findViewById(R.id.air_station_webview);
		
		if(!url.equals(""))
		{
			webView.getSettings().setJavaScriptEnabled(true);
	
			webView.loadUrl(url);
	
			webView.requestFocus();
		
			webView.setWebViewClient(new WebViewClient());
		}
		return rootView;
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
}
