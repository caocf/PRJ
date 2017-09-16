package net.hxkg.user;

import net.hxkg.ghmanager.Login;
import net.hxkg.ghmanager.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class CountActivity extends Activity
{
	TextView text_user;
	TextView text_tel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_count);
		
		text_user=(TextView) findViewById(R.id.user);
		text_user.setText(User.name);
		text_tel=(TextView) findViewById(R.id.tel);
		text_tel.setText(User.sjhm);
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
			Intent intent =new Intent(CountActivity.this,Login.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
			this.startActivity(intent);
			this.finish();
			break;
		case R.id.changepsw:
			Intent intent1 =new Intent(CountActivity.this,ChangePSWActivity.class);
			this.startActivity(intent1);
			break;

		default:
			break;
		}
		
	}
}
