package net.hxkg.system;

import net.hxkg.ghmanager.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class CodeActivity extends Activity
{
	ImageView imview;
	String pathString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code);
		pathString=getIntent().getStringExtra("path");
		
		imview=(ImageView) findViewById(R.id.img);
		Bitmap bitmap=BitmapFactory.decodeFile(pathString);
		imview.setImageBitmap(bitmap);
	}
	
	public void onBack(View v)
	{
		finish();
	}
	
}
