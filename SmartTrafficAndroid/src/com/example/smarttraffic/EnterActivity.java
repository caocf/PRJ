package com.example.smarttraffic;

import com.example.smarttraffic.R;
import com.example.smarttraffic.network.HttpHandler;
import com.example.smarttraffic.util.SharePreference;
import com.example.smarttraffic.util.StartIntent;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

/**
 * Android入口显示界面,滞留一定时间
 * 
 * @author Administrator zhou
 * 
 */
public class EnterActivity extends Activity
{
	private final int DELAY_TIME = 3000; // 首页停留时间

	public static HttpHandler handler = new HttpHandler(); // 统一 线程处理方法
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_enter);

		handler.setContext(getApplicationContext());
		
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				if (new SharePreference(EnterActivity.this)
						.getValueForBool(SharePreference.READ_STATEMENT))
					StartIntent.startIntentAndFinish(EnterActivity.this, HomeActivity.class);
				 else
					StartIntent.startIntentAndFinish(EnterActivity.this, StatementActivity.class);
					//StartIntent.startIntentAndFinish(EnterActivity.this, HomeActivity.class);
				
			}
		}, DELAY_TIME);
	}
}
