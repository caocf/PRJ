package com.huzhouport.slidemenu;

import com.example.huzhouport.R;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpUtil;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class SetPushTimeUpdate extends Activity {
	
	private String ip,port,pushmsgtimer;
	private EditText eText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_set_pushtime_update);
		
		Intent intent = getIntent();
		pushmsgtimer = intent.getStringExtra("pushmsgtimer");
		
		ImageButton back=(ImageButton)findViewById(R.id.fragment_set_ip_update_back);
	    back.setOnClickListener(new Back());
	    eText= (EditText) findViewById(R.id.fragment_set_ip_update_e);
	    eText.setText(pushmsgtimer);   	
	 
	    
	    
	    ImageButton img= (ImageButton) findViewById(R.id.fragment_set_ip_update_submit);
	    img.setOnClickListener(new Img());
	    
	   TextView title=(TextView) findViewById(R.id.fragment_set_ip_update_title);
	   title.setText("修改信息推送时间");
	
}
	public class Back implements View.OnClickListener{
		public void onClick(View v){
			finish();
		}
	}
	
	public class Img implements View.OnClickListener{
		public void onClick(View v){
		
				if(eText.getText().toString().equals("")){
					Toast.makeText(SetPushTimeUpdate.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
				}
				else{
					
					

					GlobalVar.PUSHTIMER=Long.parseLong(eText.getText().toString());	
					Intent intent=new Intent(SetPushTimeUpdate.this, SetPushMsgTimer.class);
					startActivity(intent);
					setResult(20);
					finish();
					
					Editor oEditor = getSharedPreferences("IpData", 0).edit();
					oEditor.clear();
					oEditor.putString("Ip",HttpUtil.BASE_URL_IP );
					oEditor.putString("Port",HttpUtil.BASE_URL_PORT);
					oEditor.putString("PushMsgTimer",Long.toString(GlobalVar.PUSHTIMER));
					oEditor.putBoolean("IsOne", true);
					oEditor.commit();
				}
		}
	}
	
	
	
}
