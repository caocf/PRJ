package com.huzhouport.slidemenu;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.huzhouport.R;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpUtil;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class SetIpUpdate extends Activity {
	
	private String ip,port;
	private EditText eText1,eText2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_set_ip_update);
		
		Intent intent = getIntent();
		ip = intent.getStringExtra("ip");
		port = intent.getStringExtra("port");
		ImageButton back=(ImageButton)findViewById(R.id.fragment_set_ip_update_back);
	    back.setOnClickListener(new Back());
	    eText1= (EditText) findViewById(R.id.fragment_set_ip_update_e1);
	    eText2= (EditText) findViewById(R.id.fragment_set_ip_update_e2);
	    eText1.setText(ip);
	    eText2.setText(port);
	    
	    
	    ImageButton img= (ImageButton) findViewById(R.id.fragment_set_ip_update_submit);
	    img.setOnClickListener(new Img());
}
	public class Back implements View.OnClickListener{
		public void onClick(View v){
			finish();
		}
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String val = data.getString("value");
	        if(val.equals("1")){
	        	HttpUtil.BASE_URL_IP=eText1.getText().toString();
				HttpUtil.BASE_URL_PORT=eText2.getText().toString();
				HttpUtil.BASE_URL="http://"+HttpUtil.BASE_URL_IP+":"+HttpUtil.BASE_URL_PORT+"/HuZhouPort/"; //iP 赋值
				Editor oEditor = getSharedPreferences("IpData", 0).edit();
				oEditor.clear();
				oEditor.putString("Ip1",HttpUtil.BASE_URL_IP );
				oEditor.putString("Port1",HttpUtil.BASE_URL_PORT);
				oEditor.putString("PushMsgTimer",Long.toString(GlobalVar.PUSHTIMER));
				oEditor.putBoolean("IsOne", true);
				oEditor.commit();
				Toast.makeText(SetIpUpdate.this, "修改成功", Toast.LENGTH_SHORT).show();
				
				Intent intent=new Intent(SetIpUpdate.this, SetIp.class);
				startActivity(intent);
				setResult(20);
				finish();
	        }else{
	        	Toast.makeText(SetIpUpdate.this, "无法连接到该地址,请确认后重新输入", Toast.LENGTH_SHORT).show();
	        }
	    }
	};
	
	public class Img implements View.OnClickListener{
		public void onClick(View v){

				if(eText1.getText().toString().equals("")){
					Toast.makeText(SetIpUpdate.this, "网络地址不能为空", Toast.LENGTH_SHORT).show();
				}else if(eText2.getText().toString().equals("")){
					Toast.makeText(SetIpUpdate.this, "网络端口不能为空", Toast.LENGTH_SHORT).show();
				}else if(!checkIP(eText1.getText().toString())){
					Toast.makeText(SetIpUpdate.this, "网络地址格式不正确", Toast.LENGTH_SHORT).show();
				}else{
			         new Thread(new Runnable(){
			                @Override
			                public void run() {
			                	//判断是否连在上 
								String url="http://"+eText1.getText().toString()+":"+eText2.getText().toString()+"/HuZhouPort/";
								System.out.println("url===="+url);
								HttpClient httpclient = new DefaultHttpClient();
								HttpResponse response;
								HttpPost httppost = new HttpPost(url);	
								try {
									response = httpclient.execute(httppost);
									int code = response.getStatusLine().getStatusCode();
									System.out.println("code===="+code);
									if(code==200){
									//handler.sendEmptyMessage(1);
									 Message msg = new Message();
								     Bundle data = new Bundle();
								     data.putString("value","1");
								     msg.setData(data);
								     handler.sendMessage(msg);
										
									}else{
										 Message msg = new Message();
									     Bundle data = new Bundle();
									     data.putString("value","2");
									     msg.setData(data);
									     handler.sendMessage(msg);
									}	
								} catch (ClientProtocolException e) {
									e.printStackTrace();
									 Message msg = new Message();
								     Bundle data = new Bundle();
								     data.putString("value","2");
								     msg.setData(data);
								     handler.sendMessage(msg);
								} catch (IOException e) {
									e.printStackTrace();
									 Message msg = new Message();
								     Bundle data = new Bundle();
								     data.putString("value","2");
								     msg.setData(data);
								     handler.sendMessage(msg);
								}
			                }
			            }).start();
				}
		}
	}
	
	// 判断输入的IP是否合法
    private boolean checkIP(String str) {
        Pattern pattern = Pattern
                .compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]"
                        + "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
        return pattern.matcher(str).matches();
    }
}
