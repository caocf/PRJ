package com.huzhouport.setup;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.model.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class SetUpPerson extends Activity {
	private TextView tv_user, tv_phone, tv_binding, tv_bindingtime;
	private User user;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_person);

		ImageButton img_back = (ImageButton) findViewById(R.id.setup_person_back);
		tv_user = (TextView) findViewById(R.id.setup_person_username);
		tv_phone = (TextView) findViewById(R.id.setup_person_phone);
		tv_binding = (TextView) findViewById(R.id.setup_person_binding);
		tv_bindingtime = (TextView) findViewById(R.id.setup_person_bindingtime);
		
		user = (User) getIntent().getSerializableExtra("User");
		tv_user.setText(user.getUserName());
		tv_phone.setText(user.getPhoneNumber());
		if(user.getShipBindingList()!=null){
			tv_binding.setText(user.getShipBindingList().get(user.getBindnum()).getShipNum());//注意只获得了第一个绑定的船舶名称
			tv_bindingtime.setText(CountTime.FormatTimeToDay(user.getShipBindingList().get(user.getBindnum()).getBindingTime()));//注意只获得了第一个绑定的船舶名称
		}
		if(user.getWharfBindingList()!=null){
			tv_binding.setText(user.getWharfBindingList().get(user.getBindnum()).getWharfNum());//注意只获得了第一个绑定的码头名称
			tv_bindingtime.setText(CountTime.FormatTimeToDay(user.getWharfBindingList().get(user.getBindnum()).getBindingTime()));//注意只获得了第一个绑定的码头名称
		}
		
		
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		
	}
	
}
