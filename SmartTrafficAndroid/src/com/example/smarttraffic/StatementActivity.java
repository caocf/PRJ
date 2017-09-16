package com.example.smarttraffic;

import com.example.smarttraffic.R;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.SharePreference;
import com.example.smarttraffic.util.StartIntent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author Administrator zhou
 * 
 */
public class StatementActivity extends Activity
{
	TextView content;
	Button agree;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_statement);
		
		content=(TextView)findViewById(R.id.statement_content);
		agree=(Button)findViewById(R.id.statement_agree);
		
		request();
	}
	
	private void request()
	{
		new HttpThread(new BaseSearch(){@Override
		public Object parse(String data)
		{
			return data.substring(1, data.length()-2).replace("\\r\\n", "\n");
		}}, new BaseRequest(){
			@Override
			public String CreateUrl()
			{
				return HttpUrlRequestAddress.STATEMENT_URL;
			}
		}, new UpdateView()
		{
			
			@Override
			public void update(Object data)
			{
				System.out.println(data);
				
				content.setText((String)data);
				
				agree.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						new SharePreference(StatementActivity.this).saveValeForBool(SharePreference.READ_STATEMENT, true);
						
						StartIntent.startIntentAndFinish(StatementActivity.this, HomeActivity.class);
					}
				});
			}
		},"请求免责声明失败").start();
	}
}
