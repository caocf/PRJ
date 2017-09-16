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
import android.widget.Toast;


public class SetPushMsgTimerUpdate extends Activity {
	
	private String kind,ip,port,pushmsgtimer;
	private EditText eText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_set_ip_update);
		
		Intent intent = getIntent();
		kind = intent.getStringExtra("kind");
		ip = intent.getStringExtra("ip");
		port = intent.getStringExtra("port");
		pushmsgtimer = intent.getStringExtra("pushmsgtimer");
		
		ImageButton back=(ImageButton)findViewById(R.id.fragment_set_ip_update_back);
	    back.setOnClickListener(new Back());
	    eText= (EditText) findViewById(R.id.fragment_set_ip_update_e);
	    
	    if(kind.equals("1")){
	    	eText.setText(ip);
	    }
	    else if(kind.equals("3"))
	    {
	    	eText.setText(pushmsgtimer);   	
	    }
	    else{
	    	eText.setText(port);
	    }
	    
	    
	    
	    ImageButton img= (ImageButton) findViewById(R.id.fragment_set_ip_update_submit);
	    img.setOnClickListener(new Img());
	    
	   
	
}
	public class Back implements View.OnClickListener{
		public void onClick(View v){
			finish();
		}
	}
	
	public class Img implements View.OnClickListener{
		public void onClick(View v){
			if(kind.equals("1")){
				if(eText.getText().toString().equals("")){
					Toast.makeText(SetPushMsgTimerUpdate.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
				}else{
					HttpUtil.BASE_URL_IP=eText.getText().toString();
					HttpUtil.BASE_URL_PORT=port;
					HttpUtil.BASE_URL="http://"+HttpUtil.BASE_URL_IP+":"+HttpUtil.BASE_URL_PORT+"/HuZhouPort/"; //iP 赋值
					
					Editor oEditor = getSharedPreferences("IpData", 0).edit();
					oEditor.clear();
					oEditor.putString("Ip",HttpUtil.BASE_URL_IP );
					oEditor.putString("Port",HttpUtil.BASE_URL_PORT);
					oEditor.putString("PushMsgTimer",Long.toString(GlobalVar.PUSHTIMER));
					oEditor.putBoolean("IsOne", true);
					oEditor.commit();
					
					Intent intent=new Intent(SetPushMsgTimerUpdate.this, SetIp.class);
					startActivity(intent);
					setResult(20);
					finish();
				}
			}
			
			else if(kind.equals("3"))
			{
				if(eText.getText().toString().equals("")){
					Toast.makeText(SetPushMsgTimerUpdate.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
				}
				else{
					
					

					GlobalVar.PUSHTIMER=Long.parseLong(eText.getText().toString());	
					Intent intent=new Intent(SetPushMsgTimerUpdate.this, SetPushMsgTimer.class);
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
			else{
				if(eText.getText().toString().equals("")){
					Toast.makeText(SetPushMsgTimerUpdate.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
				}else{
					HttpUtil.BASE_URL_IP=ip;
					HttpUtil.BASE_URL_PORT=eText.getText().toString();
					HttpUtil.BASE_URL="http://"+HttpUtil.BASE_URL_IP+":"+HttpUtil.BASE_URL_PORT+"/HuZhouPort/"; //iP 赋值
					
					Editor oEditor = getSharedPreferences("IpData", 0).edit();
					oEditor.clear();
					oEditor.putString("Ip",HttpUtil.BASE_URL_IP );
					oEditor.putString("Port",HttpUtil.BASE_URL_PORT);
					oEditor.putString("PushMsgTimer",Long.toString(GlobalVar.PUSHTIMER));
					oEditor.putBoolean("IsOne", true);
					oEditor.commit();
					
					Intent intent=new Intent(SetPushMsgTimerUpdate.this, SetIp.class);
					startActivity(intent);
					setResult(20);
					finish();
				}
			}
		}
	}
	
	
	
}
