package com.huzhouport.setup;

import org.jivesoftware.smack.util.StringUtils;

import com.appkefu.lib.interfaces.KFInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.service.KFXmppManager;
import com.appkefu.lib.utils.KFLog;
import com.example.huzhouportpublic.R;
import com.huzhouport.model.User;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Complain extends Activity{
	private EditText cm_text;
	private ImageButton cm_back,cm_sub;
	private User user;

	private TextView 			 mTitle; 

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complain_activity_main);
		mTitle = (TextView) findViewById(R.id.demo_title);

/*		ImageButton img_back = (ImageButton) findViewById(R.id.setup_person_back);
		cm_text = (EditText) findViewById(R.id.complain);
		cm_back = (ImageButton) findViewById(R.id.complainback);
		cm_sub = (ImageButton) findViewById(R.id.complain_sub);
		user = (User) getIntent().getSerializableExtra("User");
		cm_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});*/
		//第一步：登录
		//第一步：登录
		//第一种登录方式
		//KFInterfaces.visitorLogin(this);
		
		//String userid = "linkfull_test4", password = "appkefu";
		//第二种登录方式，传入user_id
		KFInterfaces.loginWithUserID("linkfull_test4", this);
		//第三中登录方式，首先使用user_id调用注册接口注册一个用户，一个user_id只能注册一次，不能重复注册
		//KFInterfaces.registerWithUsernameAndPassword(userid, password, this);
		//注册用户之后，使用user_id调用登录接口登录
		//KFInterfaces.loginWithUsernameAndPassword(userid, password, this);

	}
	@Override
	protected void onStart() {
	    super.onStart();
			
	    IntentFilter intentFilter = new IntentFilter();
		//监听网络连接变化情况
	    intentFilter.addAction(KFMainService.ACTION_XMPP_CONNECTION_CHANGED);
	    //监听消息
	    intentFilter.addAction(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED);
	    //工作组在线状态
	    intentFilter.addAction(KFMainService.ACTION_XMPP_WORKGROUP_ONLINESTATUS);
	 
	    registerReceiver(mXmppreceiver, intentFilter);     
	}
	 
	@Override
	protected void onStop() {
	    super.onStop();
	    
	    unregisterReceiver(mXmppreceiver);
	}
	
	//1.咨询客服
	private void startChat()
	{
		KFInterfaces.startChat(this, 
				"123456", //注意（重要）：在此处填写工作组名
				"客服小秘书",
				"正在联系客服，请稍后...");
		
	}
	
	//监听：连接状态、即时通讯消息、客服在线状态
	private BroadcastReceiver mXmppreceiver = new BroadcastReceiver() 
	{
        public void onReceive(Context context, Intent intent) 
        {
            String action = intent.getAction();
            //监听：连接状态
            if (action.equals(KFMainService.ACTION_XMPP_CONNECTION_CHANGED))//监听链接状态
            {
                updateStatus(intent.getIntExtra("new_state", 0));        
            }
            //监听：即时通讯消息
            else if(action.equals(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED))//监听消息
            {
            	//消息内容
            	String body = intent.getStringExtra("body");
            	//消息来自于
            	String from = StringUtils.parseName(intent.getStringExtra("from"));
            }
            //客服工作组在线状态
            else if(action.equals(KFMainService.ACTION_XMPP_WORKGROUP_ONLINESTATUS))
            {
                String onlineStatus = intent.getStringExtra("onlinestatus");
            	
                KFLog.d("客服工作组:"+ onlineStatus);//online：在线；offline: 离线
                 	            	
                if(onlineStatus.equals("online"))
                {
                 	KFLog.d("online");
                }
                else
                 {
                 	KFLog.d("offline");
                 }
                }            
        }
    };
    
    
    //得到最新连接状态
    private void getStatus(int status){
    	
    }
    
   //根据监听到的连接变化情况更新界面显示
    private void updateStatus(int status) {

    	switch (status) {
            case KFXmppManager.CONNECTED:
            	mTitle.setText("微客服");
            	
            	//查询客服工作组在线状态，返回结果在BroadcastReceiver中返回
            	KFInterfaces.checkKeFuIsOnlineAsync("123456", this);
            	
            	startChat();
                break;
            case KFXmppManager.DISCONNECTED:
            	mTitle.setText("微客服(未连接)");
                break;
            case KFXmppManager.CONNECTING:
            	mTitle.setText("微客服(登录中...)");
            	break;                 
            case KFXmppManager.DISCONNECTING:
            	mTitle.setText("微客服(登出中...)");
                break;
            case KFXmppManager.WAITING_TO_CONNECT:
            case KFXmppManager.WAITING_FOR_NETWORK:
            	mTitle.setText("微客服(等待中)");
                break;
            default:  
                throw new IllegalStateException();
        }
    }
}

