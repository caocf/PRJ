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
		//��һ������¼
		//��һ������¼
		//��һ�ֵ�¼��ʽ
		//KFInterfaces.visitorLogin(this);
		
		//String userid = "linkfull_test4", password = "appkefu";
		//�ڶ��ֵ�¼��ʽ������user_id
		KFInterfaces.loginWithUserID("linkfull_test4", this);
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
	
	//1.��ѯ�ͷ�
	private void startChat()
	{
		KFInterfaces.startChat(this, 
				"123456", //ע�⣨��Ҫ�����ڴ˴���д��������
				"�ͷ�С����",
				"������ϵ�ͷ������Ժ�...");
		
	}
	
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
                String onlineStatus = intent.getStringExtra("onlinestatus");
            	
                KFLog.d("�ͷ�������:"+ onlineStatus);//online�����ߣ�offline: ����
                 	            	
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
    
    
    //�õ���������״̬
    private void getStatus(int status){
    	
    }
    
   //���ݼ����������ӱ仯������½�����ʾ
    private void updateStatus(int status) {

    	switch (status) {
            case KFXmppManager.CONNECTED:
            	mTitle.setText("΢�ͷ�");
            	
            	//��ѯ�ͷ�����������״̬�����ؽ����BroadcastReceiver�з���
            	KFInterfaces.checkKeFuIsOnlineAsync("123456", this);
            	
            	startChat();
                break;
            case KFXmppManager.DISCONNECTED:
            	mTitle.setText("΢�ͷ�(δ����)");
                break;
            case KFXmppManager.CONNECTING:
            	mTitle.setText("΢�ͷ�(��¼��...)");
            	break;                 
            case KFXmppManager.DISCONNECTING:
            	mTitle.setText("΢�ͷ�(�ǳ���...)");
                break;
            case KFXmppManager.WAITING_TO_CONNECT:
            case KFXmppManager.WAITING_FOR_NETWORK:
            	mTitle.setText("΢�ͷ�(�ȴ���)");
                break;
            default:  
                throw new IllegalStateException();
        }
    }
}

