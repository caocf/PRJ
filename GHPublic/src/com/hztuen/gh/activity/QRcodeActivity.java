package com.hztuen.gh.activity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.umengdemo.utils.UmengShareUtils;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.QRCodeUtil; 
/*
 * 二维码界面
 */
public class QRcodeActivity extends Activity implements OnClickListener,onResult
{
	private RelativeLayout relative_title_final;
	private ImageView img_qr;
	private Button btn_share;
	private UmengShareUtils umengShareUtils;
	String filePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrcode);
		init();
		
	}
	private void init()
	{
		filePath = Environment.getExternalStorageDirectory()+ File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";
		
		img_qr=(ImageView)findViewById(R.id.img_qr);         
        btn_share=(Button)findViewById(R.id.btn_share);
        btn_share.setOnClickListener(this);
        relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);
		
		QRCodeUtil.createQRImage(contants.baseUrl+"DownLatest?type=1", 800, 800,
                BitmapFactory.decodeResource(getResources(), R.drawable.appicon),
                filePath);
		img_qr.setImageBitmap(BitmapFactory.decodeFile(filePath));
		
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("type", 1);
		//hr.post(contants.baseUrl+"DownLatest", map);
	}
	
	@Override
	public void onClick(View v)
	{ 
		switch (v.getId()) {
		//点击分享按钮
		case R.id.btn_share: 
			break;
		//点击返回
		case R.id.relative_title_final:
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		umengShareUtils.authSSO(requestCode, resultCode, data);
	}
	@Override
	public void onSuccess( String result)
	{
		final String string=result.substring(1,result.trim().length()-1);
		
		new Thread(new Runnable() 
        {
            @Override
            public void run() 
            {            	
                boolean success = QRCodeUtil.createQRImage(string, 800, 800,
                        BitmapFactory.decodeResource(getResources(), R.drawable.appicon),
                        filePath);

                if (success) 
                {
                    runOnUiThread(new Runnable() 
                    {
                        @Override
                        public void run() {
                        	img_qr.setImageBitmap(BitmapFactory.decodeFile(filePath));
                        }
                    });
                }
            }
        }).start();
		
	}
	@Override
	public void onError(int httpcode) 
	{
		
	}
	

}
