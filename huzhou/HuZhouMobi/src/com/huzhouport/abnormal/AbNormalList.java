package com.huzhouport.abnormal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AbNormalList extends Activity
{	
	public static int REQUEST_CODE=100;
	public static String CLSNAME="";
	
	public static MainFragmentNormalOn fragmentOn;
	private SharedPreferences sp;
	
	public User user;
	
	CheckBox cbBox;
	BroadcastReceiver receiver=null;
	
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		CLSNAME=this.getClass().getName();
		
		this.setContentView(R.layout.abnormallist_activity);		
		
		sp=this.getSharedPreferences("ABNORMAL", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		boolean offon=sp.getBoolean("offon", false);
		user=(User) this.getIntent().getSerializableExtra("User");
		if(user==null)
		{
			user=new User();
			user.setName(sp.getString("username", ""));
		}
		editor=sp.edit();
		editor.putString("username", user.getName());
		editor.commit();
		
		cbBox=(CheckBox) this.findViewById(R.id.cb_abnormalswitch);		
		cbBox.setChecked(offon);
		
		
		
		final FragmentManager fragmentManager = getFragmentManager();
		if(offon)
		{
			fragmentOn=new MainFragmentNormalOn();
			
			fragmentManager.beginTransaction().replace(R.id.content,fragmentOn ) .commit();
		}
		else 
		{
			
			fragmentManager.beginTransaction().replace(R.id.content, new MainFragmentNormalOff()) .commit();
		}
		cbBox.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked)
			{
				if(isChecked)
				{					
					fragmentOn=new MainFragmentNormalOn();
					
					fragmentManager.beginTransaction().replace(R.id.content,fragmentOn ) .commit();
					
					Intent intent=new Intent(AbNormalList.this,PushAbInfo.class);
					intent.putExtra("User", user);
					//intent.putExtra("potname", fragmentOn)
					AbNormalList.this.startService(intent);	
					
				}
				else
				{
					fragmentManager.beginTransaction().replace(R.id.content, new MainFragmentNormalOff()) .commit();
					
					//Intent intent=new Intent(AbNormalList.this,PushAbInfo.class);
					//AbNormalList.this.stopService(intent);	
				}
				
				SharedPreferences.Editor editor=sp.edit();
				editor.putBoolean("offon", isChecked);
				editor.commit();
				
				new Thread(new Runnable() 
				{					
					@Override
					public void run()
					{
						HttpFileUpTool hft = new HttpFileUpTool();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("excutor.name", user.getName());
						String tString=MainFragmentNormalOn.potname;
						if(tString==null||"".equals(tString))
						{
							SharedPreferences sp=AbNormalList.this.getSharedPreferences("ABNORMAL", Context.MODE_PRIVATE);
							tString=sp.getString("potname", "请选择站点");
							
						}
						map.put("excutor.pot",tString );
						String string=isChecked?"1":"0";
						map.put("excutor.state",string);
						String potsid=sp.getString("potsid", "752009");
						map.put("excutor.potid",potsid);
						
						try {
							hft.post(HttpUtil.BASE_URL+"UpdateExcutorList", map);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}).start();
				
				//
			}
		});		
		
	}
	
	 @Override  
	 protected void onResume() 
	 {  
	        super.onResume();  
	      //推送服务器
			if(cbBox.isChecked()&&(!isServiceRunning()))
			{			
				Intent intent=new Intent(AbNormalList.this,PushAbInfo.class);
				//intent.putExtra("potname", fragmentOn.potname);
				intent.putExtra("User", user);
				this.startService(intent);			
			} 
			
			receiver=new BroadcastReceiver()
			{

				@Override
				public void onReceive(Context context, Intent intent) 
				{
					if(fragmentOn!=null)
						fragmentOn.NewPotFresh(fragmentOn.potname);
					
				}
				
			};
			IntentFilter filter1=new IntentFilter();
			filter1.addAction(PushAbInfo.ACTION);
			registerReceiver(receiver,filter1);
	 }
	
	private boolean isServiceRunning()
	{
		ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE); 
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) 
		{  
	        if ("com.huzhouport.abnormal.PushAbInfo".equals(service.service.getClassName()))
	        {  
	            return true;  
	        }  
	    }
		
		return false;
	}
	
	public void Processed(View V)
	{
		Intent intent =new Intent(AbNormalList.this,AbNormalListDeal.class);
		intent.putExtra("potname", fragmentOn.potname);
		intent.putExtra("User",user);
		this.startActivity(intent);
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {          
       super.onActivityResult(requestCode, resultCode, data);
       if (resultCode==Pots.RESULT_CODE)  
       {  
          //Bundle bundle=data.getExtras();  String str=bundle.getString("back");  
          String s=data.getStringExtra("pot");
          if(s!=null&&!s.equals(""))
          {
        	  fragmentOn.NewPotFresh(s);
        	  new Thread(new Runnable() 
				{					
					@Override
					public void run()
					{
						HttpFileUpTool hft = new HttpFileUpTool();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("excutor.name", user.getName());
						String tString=MainFragmentNormalOn.potname;
						if(tString==null||"".equals(tString))
						{
							SharedPreferences sp=AbNormalList.this.getSharedPreferences("ABNORMAL", Context.MODE_PRIVATE);
							tString=sp.getString("potname", "请选择站点");
						}
						map.put("excutor.pot",tString );
						
						map.put("excutor.state","1");
						String potsid=sp.getString("potsid", "752009");
						map.put("excutor.potid",potsid);
						
						try {
							hft.post(HttpUtil.BASE_URL+"UpdateExcutorList", map);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}).start();
          }
         
       }              
       else if(resultCode==AbNormalView.FINISH)
       {
     	  fragmentOn.NewPotFresh(fragmentOn.potname);
       }
       
      
       fragmentOn.NewPotFresh(fragmentOn.potname);
       
    } 
	
	public void GoBack(View v)
	{
		this.finish();
	}
	
	public void ChoosePot(View v)
	{
		fragmentOn.ChoosePot(v);
	}
	
	@Override  
    protected void onDestroy() 
	{  
        super.onDestroy();  
        unregisterReceiver(receiver); 
    }
}
