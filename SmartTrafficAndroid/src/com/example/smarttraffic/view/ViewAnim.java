package com.example.smarttraffic.view;

import android.os.Handler;
import android.os.Message;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class ViewAnim
{
	public static Handler handler=new Handler()
	{
		public void handleMessage(Message msg) 
		{
			switch (msg.what)
			{
				case 101:
					if(msg.obj!=null)
						((AnimListener)msg.obj).exchange();
					
					break;
			}
		};
	};
	
	AnimListener animListener;
	
	
	public void exchangeAnim(TextView start,TextView end)
	{		
		int[] startLocation=new int[2];
		start.getLocationOnScreen(startLocation);
		
		int[] endLocation=new int[2];
		end.getLocationOnScreen(endLocation);
		
		TranslateAnimation toRight=new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, endLocation[0]-startLocation[0], Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);		
	
		toRight.setDuration(500);
		toRight.setZAdjustment(Animation.ZORDER_TOP);
		toRight.setFillAfter(true);
		toRight.setInterpolator(new AccelerateDecelerateInterpolator());
		
		TranslateAnimation toLeft=new TranslateAnimation(Animation.ABSOLUTE,0, Animation.ABSOLUTE,startLocation[0]-endLocation[0],Animation.ABSOLUTE,0,Animation.ABSOLUTE, 0);
		toLeft.setDuration(500);
		toLeft.setZAdjustment(Animation.ZORDER_TOP);
		toLeft.setFillAfter(true);
		toLeft.setInterpolator(new AccelerateDecelerateInterpolator());
			
		end.startAnimation(toLeft);
		start.startAnimation(toRight);
	}
	
	
	public void exchangeAnim(TextView start,TextView end,AnimListener amAnimListener)
	{
		this.animListener=amAnimListener;
		
		int[] startLocation=new int[2];
		start.getLocationOnScreen(startLocation);
		
		int[] endLocation=new int[2];
		end.getLocationOnScreen(endLocation);
		
		TranslateAnimation toRight=new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, endLocation[0]-startLocation[0], Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);		
	
		toRight.setDuration(500);
		toRight.setZAdjustment(Animation.ZORDER_TOP);
		toRight.setFillAfter(true);
		toRight.setInterpolator(new AccelerateDecelerateInterpolator());
		
		toRight.setAnimationListener(new Animation.AnimationListener()
		{
			
			@Override
			public void onAnimationStart(Animation animation)
			{

			}
			
			@Override
			public void onAnimationRepeat(Animation animation)
			{

			}
			
			@Override
			public void onAnimationEnd(Animation animation)
			{
				new Thread()
				{
					public void run() 
					{
						Message message=new Message();
						message.what=101;
						message.obj=ViewAnim.this.animListener;
						handler.sendMessage(message);

					};
				}.start();
			}
		});
		
		TranslateAnimation toLeft=new TranslateAnimation(Animation.ABSOLUTE,0, Animation.ABSOLUTE,startLocation[0]-endLocation[0],Animation.ABSOLUTE,0,Animation.ABSOLUTE, 0);
		toLeft.setDuration(500);
		toLeft.setZAdjustment(Animation.ZORDER_TOP);
		toLeft.setFillAfter(true);
		toLeft.setInterpolator(new AccelerateDecelerateInterpolator());
			
		end.startAnimation(toLeft);
		start.startAnimation(toRight);

	}
}

