package com.huzhouport.ais;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.example.huzhouport.R;
import com.huzhouport.common.util.ClearEditText;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class AISMain extends Activity 
{
	ClearEditText cledit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
		setContentView(R.layout.activity_ais);	
		
		initView();
	}
	
	private void initView()
	{
		cledit=(ClearEditText) findViewById(R.id.basesourch_edit01);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{			
			this.finish();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void onViewClick(View v)
	{
		switch(v.getId())
		{
		case R.id.basesourch_back:
			this.finish();
			break;
		case R.id.BSbutton1:
			new Thread(new Runnable() 
			{				
				@Override
				public void run() 
				{
					HttpFileUpTool httpFileUpTool=new HttpFileUpTool();
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("aisNo", cledit.getText().toString().trim());
					try 
					{
						String result=httpFileUpTool.post(HttpUtil.BASE_URL+"queryShipnameByAisNo", map);
						if(result==null||"".equals(result))
						{
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									Toast.makeText(AISMain.this, "暂无数据", Toast.LENGTH_SHORT).show();									
								}
							});
							
							
						}
						else 
						{
							JSONObject object=new JSONObject(result);
							JSONObject aisobj=object.getJSONObject("result");
							String shipname=aisobj.getString("shipname");
							
							Intent intent=new Intent(AISMain.this,AISEdit.class);
							intent.putExtra("shipname", shipname);
							intent.putExtra("ais", cledit.getText().toString().trim());
							AISMain.this.startActivity(intent);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
			
			
			
			break;
		}
	}

}
