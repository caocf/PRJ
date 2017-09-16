package com.huzhouport.ais;

import java.util.HashMap;
import java.util.Map;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AISEdit extends Activity 
{
	String shipnameString;
	Button editImageButton,suButton;
	EditText shipTextView;
	TextView aisTextView;
	
	ProgressDialog progressDialog=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
		setContentView(R.layout.activity_aisedit);	
		
		initView();
	}
	
	private void initView()	
	{
		shipnameString =getIntent().getStringExtra("shipname");
		String aisnumString=getIntent().getStringExtra("ais");
		
		shipTextView =(EditText) findViewById(R.id.shipname);
		shipTextView.setText(shipnameString);
		 aisTextView=(TextView) findViewById(R.id.aisnum);
		aisTextView.setText(aisnumString);
		
		editImageButton=(Button) findViewById(R.id.eb);
		suButton=(Button) findViewById(R.id.sumbit);
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
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		  
		switch(v.getId())
		{
		case R.id.basesourch_back:
			this.finish();
			break;
		case R.id.eb:
			if(shipTextView.isEnabled())
			{
				shipTextView.setEnabled(false);
				shipTextView.setFocusableInTouchMode(false);
				shipTextView.setFocusable(false);
				shipTextView.clearFocus();
				shipTextView.setBackgroundResource(R.drawable.editbox1);
				imm.hideSoftInputFromWindow(shipTextView.getWindowToken(), 0); //强制隐藏键盘 
				editImageButton.setText("编辑");
				shipTextView.setText(shipnameString);
				suButton.setVisibility(View.GONE);
			}else 
			{
				shipTextView.setFocusable(true);
				shipTextView.setEnabled(true);
				shipTextView.setFocusableInTouchMode(true);
				shipTextView.requestFocus();
				shipTextView.setBackgroundResource(R.drawable.editbox);
				imm.showSoftInput(shipTextView,InputMethodManager.SHOW_FORCED);
				editImageButton.setText("取消");
				shipTextView.setSelection(shipTextView.getText().length());
				
				shipnameString=shipTextView.getText().toString();//保存编辑之前的文字
				suButton.setVisibility(View.VISIBLE);
			}
			
			break;
		case R.id.sumbit:
			imm.hideSoftInputFromWindow(shipTextView.getWindowToken(), 0); //强制隐藏键盘 
			new Thread(new Runnable() 
			{				
				@Override
				public void run() 
				{
					HttpFileUpTool httpFileUpTool=new HttpFileUpTool();
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("result.aisnum", aisTextView.getText().toString().trim());
					map.put("result.shipname", shipTextView.getText().toString().trim());
					//progressDialog=new ProgressDialog(AISEdit.this);
					try 
					{
						String result=httpFileUpTool.post(HttpUtil.BASE_URL+"updateShipnameByAisNo", map);
						
						if(result!=null||!"".equals(result))
						{
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() 
								{
									Toast.makeText(AISEdit.this, "修改成功", Toast.LENGTH_SHORT).show();	
									
									if(progressDialog!=null)
									{
										progressDialog.dismiss();
										progressDialog=null;
									}
									AISEdit.this.finish();
								}
							});
							
							
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
