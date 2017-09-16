package net.hxkg.news;

import net.hxkg.ghmanager.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TextView;

public class SharePopupWindow extends Activity{
	private TextView weixin;
	private TextView qq;
	private TextView weibo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
		setContentView(R.layout.news_share_pop);
		if (getActionBar() != null) {
			getActionBar().hide();
		}
	
		
		weixin = (TextView) findViewById(R.id.weixin);
		qq = (TextView) findViewById(R.id.qq);
		weibo = (TextView) findViewById(R.id.weibo);
		
		
		weixin.setOnClickListener(onclicklistener);
		qq.setOnClickListener(onclicklistener);
		weibo.setOnClickListener(onclicklistener);
		
	}
	
	@Override  
    public boolean onTouchEvent(MotionEvent event){  
        finish();  
        return true;  
    }  
	
	public OnClickListener onclicklistener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch (v.getId()) {
			case R.id.weixin:
				//实现微信分享
				break;
			case R.id.qq:
				//实现qq分享
				break;
			case R.id.weibo:
				break;
				//实现微博分享
//			case R.id.layout_pop:
//				break;
			default:
				break;
			}
			finish();
		}
		
	};
}
