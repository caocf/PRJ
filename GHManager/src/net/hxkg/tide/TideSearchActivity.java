package net.hxkg.tide;

import net.hxkg.ghmanager.Login;
import net.hxkg.ghmanager.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class TideSearchActivity extends Activity
{
	TextView text_user;
	TextView text_tel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tidesearch);
		
	}
	
	public void onView(View v)
	{
		switch (v.getId()) 
		{
		case R.id.count:
		case R.id.back:
			finish();
			break;
		case R.id.logout:
			Intent intent =new Intent(TideSearchActivity.this,Login.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.startActivity(intent);
			this.finish();
			break;	

		default:
			break;
		}
		
	}
}
