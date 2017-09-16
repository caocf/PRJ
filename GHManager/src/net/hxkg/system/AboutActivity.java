package net.hxkg.system;

import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends Activity
{
	TextView imview;
	String pathString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		pathString=getIntent().getStringExtra("path");
		
		imview=(TextView) findViewById(R.id.text);
		PackageInfo p;
		PackageManager pm=getPackageManager();
		try 
		{
			p = pm.getPackageInfo(getPackageName(), 0);
			imview.setText(p.versionName);
		} catch (NameNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onBack(View v)
	{
		finish();
	}
	
}
