package com.huzhouport.slidemenu;

import com.example.huzhouport.R;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class SetPushMsgTimer extends Activity {
	
	private String pushmsgtimer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_set_pushtimersetting);
		
		

		//myHelper = new SetMySql(SetIp.this, "HuZhouPort.db", null, 2); 
		
		pushmsgtimer=Long.toString(GlobalVar.PUSHTIMER); 
	    
		//HttpUtil.BASE_URL="http://"+HttpUtil.BASE_URL_IP+":"+HttpUtil.BASE_URL_PORT+"/HuZhouPort/"; //iP 赋值

		
		ImageButton back=(ImageButton)findViewById(R.id.fragment_set_ip_back);
	    back.setOnClickListener(new Back());
	    

	    TextView tv2=(TextView) findViewById(R.id.fragment_set_pushtime_tv2);
	    tv2.setText(pushmsgtimer);
	    
	    tv2.setOnClickListener(new TV2());
}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
            //可以根据多个请求代码来作相应的操作
            if(20==resultCode)
            {
         	   
         	   finish();
            }
            super.onActivityResult(requestCode, resultCode, data);
    }
	
	
	public class Back implements View.OnClickListener{
		public void onClick(View v){
			finish();
		}
	}
	public class TV2 implements View.OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(SetPushMsgTimer.this, SetPushTimeUpdate.class);
			intent.putExtra("pushmsgtimer", pushmsgtimer);		
			startActivityForResult(intent,100);

		}
	}	
	
	
}
