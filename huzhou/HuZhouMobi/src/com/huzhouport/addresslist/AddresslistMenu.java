package com.huzhouport.addresslist;


import com.example.huzhouport.R;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.RelativeLayout.LayoutParams;

public class AddresslistMenu  extends TabActivity{
  TabHost tab;
  Context context;
  RadioGroup rg;
  public static int WIDHT=54;
  int left=0;
  ImageView iv;
  public void onCreate(Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);
	  System.out.println("init123()");
	  setContentView(R.layout.addresslist_menu);
	  tab=getTabHost();
	  context=this;
	  System.out.println("init()");
	  init();
	  System.out.println("registListener()");
	  registListener();
  }
  private void init(){
	  tab.addTab(tab.newTabSpec("A").setIndicator("A").setContent(new Intent(context,AddresslistMain.class)));
	  tab.addTab(tab.newTabSpec("B").setIndicator("B").setContent(new Intent(context,AddresslistView.class)));
      iv=(ImageView) findViewById(R.id.addresslist_iv);
      rg=(RadioGroup) findViewById(R.id.addresslist_rg);
      WIDHT = getWindowManager().getDefaultDisplay().getWidth() / 6;  //每个菜单的宽度
  }
  
  private void registListener(){
	  rg.setOnCheckedChangeListener(new OnCheckedChangeListener(){
	      public void onCheckedChanged(RadioGroup group, int checkedId){
			  int idx=-1;
			  if(checkedId==R.id.addresslist_rb01){
				  idx=0;
			  }else {
				  idx=1;
			  }
			  switchActivity(idx);
			  /* 位置移动动画   关键代码 */
			  TranslateAnimation animation=new TranslateAnimation(left,idx*WIDHT,0,0);
			  animation.setDuration(400);
			  animation.setFillAfter(true);
			  animation.setFillBefore(true);
			  iv.startAnimation(animation);
			  final int i = idx;
				animation.setAnimationListener(new AnimationListener() {

					public void onAnimationEnd(Animation animation) {
					}

					public void onAnimationRepeat(Animation animation) {

					}

					public void onAnimationStart(Animation animation) {
						LayoutParams lp = (LayoutParams) iv.getLayoutParams();
						lp.leftMargin = 0;
						iv.setLayoutParams(lp);
						left = i * WIDHT;
					}
		  });
	  }
  });
 }
  protected void switchActivity(int idx) {
	  int n=tab.getCurrentTab();
	  if(idx<n){
		  tab.getCurrentTabView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right_out));
	  }
	  else if (idx > n) {
			tab.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right_out));
		}
	  tab.setCurrentTab(idx);
	  if (idx < n) {
			tab.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_left_in));
		} else if (idx > n) {
			tab.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right_in));
		}
	  RadioButton rb = (RadioButton) rg.getChildAt(idx);
		rb.setChecked(true);
  }
  
  
  
}
