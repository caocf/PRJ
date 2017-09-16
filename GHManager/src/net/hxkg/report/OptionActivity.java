package net.hxkg.report;

import net.hxkg.ghmanager.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class OptionActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_optionactivity);
	}
	
	public void onViewClick(View v)
	{
		switch (v.getId()) 
		{
		case R.id.goods:
			Intent intent1=new Intent(this,WharfDangerListActivity.class);
			startActivity(intent1);
			break;
		case R.id.ships:
			Intent intent2=new Intent(this,ShipDangerListActivity.class);
			startActivity(intent2);
			break;
		case R.id.text:
		case R.id.back:
			this.finish();
			break;

		default:
			break;
		}
	}
}
