package com.huzhouport.leaveandovertime;


import com.example.huzhouport.R;
import com.huzhouport.time.WheelMain;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;



public class LeaveandovertimeNewListAddTime extends Activity {
	private Button btn;
	private PopupWindow dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaveandovertime_newlist_time);
		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDateTimePicker(v);
			}
		});
	}

	/**
	 * @Description: TODO µ¯³öÈÕÆÚÊ±¼äÑ¡ÔñÆ÷
	 */
	private void showDateTimePicker(View v) {

		
		//WindowManager wm = this.getWindowManager(); 
       // int width = wm.getDefaultDisplay().getWidth(); 
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);   
		int width = wm.getDefaultDisplay().getWidth();//

        System.out.println("width="+width);
		
		dialog = new PopupWindow(  
                findViewById(R.id.mainlayout), width, 500);
		
		// ÕÒµ½dialogµÄ²¼¾ÖÎÄ¼þ
		LayoutInflater inflater = (LayoutInflater) getSystemService

(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate

(R.layout.leaveandovertime_time_layout, null);
		
		final WheelMain main = new WheelMain(dialog, view);
		main.showDateTimePicker(v);
		
		Button btn_sure = (Button) view.findViewById

(R.id.btn_datetime_sure);
		Button btn_cancel = (Button) view
				.findViewById(R.id.btn_datetime_cancel);
		// È·¶¨
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				

				dialog.dismiss();
				System.out.println("time1==="+main.getTime());
			}
		});
		// È¡Ïû
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
	}

		

}