package com.huzhouport.schedule;

import net.hxkg.ghmanager.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class SelectPicPopupWindow extends PopupWindow {


	private Button btn_sure, btn_cancel;
	private View mMenuView;
	public String timeString;

	public SelectPicPopupWindow(Activity context,View v, final TextView tv_day) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.leaveandovertime_time_layout, null);
		
		
		
		this.setContentView(mMenuView);        
        this.setWidth(LayoutParams.MATCH_PARENT);       
        this.setHeight(350);        
        this.setFocusable(true);         
        ColorDrawable dw = new ColorDrawable(0xb0000000); 
        
        this.setBackgroundDrawable(dw);  
        
        final SelectDateTime main = new SelectDateTime(this, mMenuView);
       
        	main.showDateTimePicker(v,tv_day.getText().toString());
        
		
		
		btn_sure = (Button) mMenuView.findViewById(R.id.btn_datetime_sure);
		btn_cancel = (Button) mMenuView
				.findViewById(R.id.btn_datetime_cancel);
		// ȷ��
				btn_sure.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						timeString = main.getTime();
						tv_day.setText(CountTime.FormatTime2(timeString));
						dismiss();
					}
				});
				// ȡ��
				btn_cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dismiss();
					}
				});
	}

}

