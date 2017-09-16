package com.huzhouport.complain.activity;

import java.util.ArrayList;

import org.jivesoftware.smack.util.StringUtils;


import com.appkefu.lib.interfaces.KFInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.service.KFXmppManager;
import com.appkefu.lib.utils.KFLog;
import com.example.huzhouportpublic.R;
import com.huzhouport.complain.adapter.ApiAdapter;
import com.huzhouport.complain.entity.ApiEntity;
import com.huzhouport.model.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


/**
 * 微客服(AppKeFu.com)
 * 
 * 微客服，集成到您App里的在线客服
 * 国内首款App里的在线客服，支持文字、表情、图片、语音聊天。 立志为移动开发者提供最好的在线客服
 *   
 * 技术交流QQ群:48661516
 *  
 * 客服开发文档： http://appkefu.com/AppKeFu/tutorial-android2.html
 *          
 * @author jack ning, http://github.com/pengjinning 
 *                                                    
 */ 

public class ComplainMainActivity extends Activity {

	/**                       
	 * 提示：如果已经运行过旧版的Demo，请先在手机上删除原先的App再重新运行此工程
	 * 更多使用帮助参见：http://appkefu.com/AppKeFu/tutorial-android2.html
	 */  
	
	private TextView 			 mTitle; 
	
	private ListView 			 mApiListView;
	private ArrayList<ApiEntity> mApiArray;
	private ApiAdapter 			 mAdapter;
	private User user;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complain_activity_main);
		
		initView();
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");
	
		//第一步：登录
		//第一种登录方式
		KFInterfaces.visitorLogin(this);
		
		//String userid = "linkfull_test4", password = "appkefu";
		//第二种登录方式，传入user_id
		//KFInterfaces.loginWithUserID(user.getUserName(), this);
		
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
	

	private void initView() {

		//界面标题
		mTitle = (TextView) findViewById(R.id.demo_title);
				
		mApiListView = (ListView)findViewById(R.id.api_list_view);
		mApiArray = new ArrayList<ApiEntity>();
		
		mAdapter = new ApiAdapter(this,  mApiArray);
		mApiListView.setAdapter(mAdapter);
		
		ApiEntity entity = new ApiEntity(1, getString(R.string.chat_with_kefu));
		mApiArray.add(entity);
/*			entity = new ApiEntity(2, getString(R.string.set_user_tags));
		mApiArray.add(entity);
			entity = new ApiEntity(3, getString(R.string.clear_message_records));
		mApiArray.add(entity);*/
		entity = new ApiEntity(2, getString(R.string.clear_message_records));
		mApiArray.add(entity);
	
		mAdapter.notifyDataSetChanged();
		mApiListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
				// TODO Auto-generated method stub
				
				ApiEntity entity = mApiArray.get(index);
				
				switch(entity.getId()) {
				case 1:
					startChat();
					break;
/*				case 2:
					showTagList();
					break;
				case 3:
					clearMessages();*/
				case 2:
					clearMessages();
				default:
					break;
				};
			}
		});
	}

	
	//1.咨询客服
	private void startChat()
	{if(user!=null){
		KFInterfaces.setTagNickname(user.getUserName());
		KFInterfaces.startChat(this, 
				"123456", 
				"湖州港航客服",
				"正在联系客服，请稍后");
	}
	}
	
	//显示标签列表
	private void showTagList()
	{
		Intent intent = new Intent(this, TagListActivity.class);
		startActivity(intent);
	}
	
	//清空聊天记录
	private void clearMessages()
	{
		KFInterfaces.clearMessageRecords("123456", //此处填写 客服工作组名称
											this);
		
		Toast.makeText(this, "清空聊天记录", Toast.LENGTH_LONG).show();
	}
	
	//
	
	
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
            	String fromWorkgroupName = intent.getStringExtra("from");
            	
            	String onlineStatus = intent.getStringExtra("onlinestatus");
            	
            	KFLog.d("客服工作组:"+fromWorkgroupName +" 在线状态:"+ onlineStatus);//online：在线；offline: 离线
            	
            	ApiEntity entity = mApiArray.get(0);
            	
            	if(onlineStatus.equals("online"))
            	{
            		
            		entity.setApiName(getString(R.string.chat_with_kefu) + "(在线)");
            		
            		KFLog.d("online:"+entity.getApiName());
            	}
            	else
            	{
            		
            		entity.setApiName(getString(R.string.chat_with_kefu) + "(离线)");
            		
            		KFLog.d("offline:"+entity.getApiName());
            	}
            	
            	mApiArray.set(0, entity);
            	mAdapter.notifyDataSetChanged();
            }
            	
        }
    };
    
   //根据监听到的连接变化情况更新界面显示
    private void updateStatus(int status) {

    	switch (status) {
            case KFXmppManager.CONNECTED:
            	mTitle.setText("湖州港航客服");
            	
            	//查询客服工作组在线状态，返回结果在BroadcastReceiver中返回
            	KFInterfaces.checkKeFuIsOnlineAsync("123456", this);
            	
                break;
            case KFXmppManager.DISCONNECTED:
            	mTitle.setText("湖州港航客服(未连接)");
            	mApiListView.setVisibility(View.GONE);
                break;
            case KFXmppManager.CONNECTING:
            	mTitle.setText("湖州港航客服(登录中...)");
            	mApiListView.setVisibility(View.VISIBLE);
            	break;                 
            case KFXmppManager.DISCONNECTING:
            	mTitle.setText("微客服(登出中...)");
            	mApiListView.setVisibility(View.VISIBLE);
                break;
            case KFXmppManager.WAITING_TO_CONNECT:
            case KFXmppManager.WAITING_FOR_NETWORK:
            	mTitle.setText("湖州港航客服(等待中)");
            	mApiListView.setVisibility(View.VISIBLE);
                break;
            default:  
                throw new IllegalStateException();
        }
    }	

}

