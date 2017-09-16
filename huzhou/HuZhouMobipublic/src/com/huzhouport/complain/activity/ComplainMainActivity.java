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
 * ΢�ͷ�(AppKeFu.com)
 * 
 * ΢�ͷ������ɵ���App������߿ͷ�
 * �����׿�App������߿ͷ���֧�����֡����顢ͼƬ���������졣 ��־Ϊ�ƶ��������ṩ��õ����߿ͷ�
 *   
 * ��������QQȺ:48661516
 *  
 * �ͷ������ĵ��� http://appkefu.com/AppKeFu/tutorial-android2.html
 *          
 * @author jack ning, http://github.com/pengjinning 
 *                                                    
 */ 

public class ComplainMainActivity extends Activity {

	/**                       
	 * ��ʾ������Ѿ����й��ɰ��Demo���������ֻ���ɾ��ԭ�ȵ�App���������д˹���
	 * ����ʹ�ð����μ���http://appkefu.com/AppKeFu/tutorial-android2.html
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
	
		//��һ������¼
		//��һ�ֵ�¼��ʽ
		KFInterfaces.visitorLogin(this);
		
		//String userid = "linkfull_test4", password = "appkefu";
		//�ڶ��ֵ�¼��ʽ������user_id
		//KFInterfaces.loginWithUserID(user.getUserName(), this);
		
		//�����е�¼��ʽ������ʹ��user_id����ע��ӿ�ע��һ���û���һ��user_idֻ��ע��һ�Σ������ظ�ע��
		//KFInterfaces.registerWithUsernameAndPassword(userid, password, this);
		//ע���û�֮��ʹ��user_id���õ�¼�ӿڵ�¼
		//KFInterfaces.loginWithUsernameAndPassword(userid, password, this);
	}
	 
	@Override
	protected void onStart() {
		super.onStart();

		IntentFilter intentFilter = new IntentFilter();
		//�����������ӱ仯���
        intentFilter.addAction(KFMainService.ACTION_XMPP_CONNECTION_CHANGED);
        //������Ϣ
        intentFilter.addAction(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED);
        //����������״̬
        intentFilter.addAction(KFMainService.ACTION_XMPP_WORKGROUP_ONLINESTATUS);
        
        registerReceiver(mXmppreceiver, intentFilter); 
	} 

	@Override
	protected void onStop() {
		super.onStop();

        unregisterReceiver(mXmppreceiver);
	}
	

	private void initView() {

		//�������
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

	
	//1.��ѯ�ͷ�
	private void startChat()
	{if(user!=null){
		KFInterfaces.setTagNickname(user.getUserName());
		KFInterfaces.startChat(this, 
				"123456", 
				"���ݸۺ��ͷ�",
				"������ϵ�ͷ������Ժ�");
	}
	}
	
	//��ʾ��ǩ�б�
	private void showTagList()
	{
		Intent intent = new Intent(this, TagListActivity.class);
		startActivity(intent);
	}
	
	//��������¼
	private void clearMessages()
	{
		KFInterfaces.clearMessageRecords("123456", //�˴���д �ͷ�����������
											this);
		
		Toast.makeText(this, "��������¼", Toast.LENGTH_LONG).show();
	}
	
	//
	
	
	//����������״̬����ʱͨѶ��Ϣ���ͷ�����״̬
	private BroadcastReceiver mXmppreceiver = new BroadcastReceiver() 
	{
        public void onReceive(Context context, Intent intent) 
        {
            String action = intent.getAction();
            //����������״̬
            if (action.equals(KFMainService.ACTION_XMPP_CONNECTION_CHANGED))//��������״̬
            {
                updateStatus(intent.getIntExtra("new_state", 0));        
            }
            //��������ʱͨѶ��Ϣ
            else if(action.equals(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED))//������Ϣ
            {
            	//��Ϣ����
            	String body = intent.getStringExtra("body");
            	//��Ϣ������
            	String from = StringUtils.parseName(intent.getStringExtra("from"));
            	
            }
            //�ͷ�����������״̬
            else if(action.equals(KFMainService.ACTION_XMPP_WORKGROUP_ONLINESTATUS))
            {
            	String fromWorkgroupName = intent.getStringExtra("from");
            	
            	String onlineStatus = intent.getStringExtra("onlinestatus");
            	
            	KFLog.d("�ͷ�������:"+fromWorkgroupName +" ����״̬:"+ onlineStatus);//online�����ߣ�offline: ����
            	
            	ApiEntity entity = mApiArray.get(0);
            	
            	if(onlineStatus.equals("online"))
            	{
            		
            		entity.setApiName(getString(R.string.chat_with_kefu) + "(����)");
            		
            		KFLog.d("online:"+entity.getApiName());
            	}
            	else
            	{
            		
            		entity.setApiName(getString(R.string.chat_with_kefu) + "(����)");
            		
            		KFLog.d("offline:"+entity.getApiName());
            	}
            	
            	mApiArray.set(0, entity);
            	mAdapter.notifyDataSetChanged();
            }
            	
        }
    };
    
   //���ݼ����������ӱ仯������½�����ʾ
    private void updateStatus(int status) {

    	switch (status) {
            case KFXmppManager.CONNECTED:
            	mTitle.setText("���ݸۺ��ͷ�");
            	
            	//��ѯ�ͷ�����������״̬�����ؽ����BroadcastReceiver�з���
            	KFInterfaces.checkKeFuIsOnlineAsync("123456", this);
            	
                break;
            case KFXmppManager.DISCONNECTED:
            	mTitle.setText("���ݸۺ��ͷ�(δ����)");
            	mApiListView.setVisibility(View.GONE);
                break;
            case KFXmppManager.CONNECTING:
            	mTitle.setText("���ݸۺ��ͷ�(��¼��...)");
            	mApiListView.setVisibility(View.VISIBLE);
            	break;                 
            case KFXmppManager.DISCONNECTING:
            	mTitle.setText("΢�ͷ�(�ǳ���...)");
            	mApiListView.setVisibility(View.VISIBLE);
                break;
            case KFXmppManager.WAITING_TO_CONNECT:
            case KFXmppManager.WAITING_FOR_NETWORK:
            	mTitle.setText("���ݸۺ��ͷ�(�ȴ���)");
            	mApiListView.setVisibility(View.VISIBLE);
                break;
            default:  
                throw new IllegalStateException();
        }
    }	

}

