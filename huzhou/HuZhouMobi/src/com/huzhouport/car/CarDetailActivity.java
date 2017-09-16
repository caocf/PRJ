package com.huzhouport.car;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.huzhouport.R;
import com.huzhouport.main.User;

public class CarDetailActivity extends Activity
{
	TextView tx1,tx2, starttime,endtime,number,location,target,reason,tel,flow1,flow2,mark1,mark2,sign1,sign2;

	ImageView img,img1,img2;
	User user;
	CarApplication record;
	LinearLayout container,flowl2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cardetail_activity);
		user=(User) getIntent().getSerializableExtra("User");
		record=(CarApplication) getIntent().getSerializableExtra("CarApplication");
		initView();
	}
	
	private void initView()
	{
		tx1=(TextView) findViewById(R.id.tx1);
		tx2=(TextView) findViewById(R.id.tx2);
		starttime=(TextView) findViewById(R.id.starttime);
		endtime=(TextView) findViewById(R.id.endtime);
		number=(TextView) findViewById(R.id.number);
		location=(TextView) findViewById(R.id.location);
		target=(TextView) findViewById(R.id.target);
		reason=(TextView) findViewById(R.id.reason);
		tel=(TextView) findViewById(R.id.tel);
		img=(ImageView) findViewById(R.id.img);
		img1=(ImageView) findViewById(R.id.img1);
		img2=(ImageView) findViewById(R.id.img2);
		flow1=(TextView) findViewById(R.id.flow1);
		flow2=(TextView) findViewById(R.id.flow2);
		mark1=(TextView) findViewById(R.id.mark1);
		mark2=(TextView) findViewById(R.id.mark2);
		sign1=(TextView) findViewById(R.id.sign1);
		sign2=(TextView) findViewById(R.id.sign2);
		container=(LinearLayout) findViewById(R.id.container);
		flowl2=(LinearLayout) findViewById(R.id.flowl2);
		
		if(record==null)return;
		tx1.setText("申请人:  "+record.getUsername());
		tx2.setText("申请时间:"+record.getAptime().replace("T", " "));
		
		starttime.setText(record.getStarttime().replace("T", " "));
		endtime.setText(record.getEndtime().replace("T", " "));
		number.setText(String.valueOf(record.getNumber()));
		location.setText(record.getLocation());
		target.setText(record.getTarget());
		reason.setText(record.getReason());
		tel.setText(record.getTel());
				
		if(record.getStatusShow()==1)
			img.setBackgroundResource(R.drawable.meeting_approving);
		else if(record.getStatusShow()==2)
			img.setBackgroundResource(R.drawable.meeting_approved);
		else if(record.getStatusShow()==3)
			img.setBackgroundResource(R.drawable.meeting_reject);
		else {
			img.setBackgroundResource(R.drawable.pollback);
		}
		
		if(record.getStatus1id()==2)
		{
			flow1.setText("同意");
			flow1.setTextColor(Color.GREEN);
			img1.setBackgroundResource(R.drawable.ic_state_agree);
			mark1.setVisibility(View.VISIBLE);
			mark1.setText(record.getMark1());
			sign1.setVisibility(View.VISIBLE);
			sign1.setText(record.getApname()+" "+record.getAptime1().replace("T", " "));
			
		}
		else if(record.getStatus1id()==3)
		{
			flow1.setText("驳回");
			flow1.setTextColor(Color.RED);
			img1.setBackgroundResource(R.drawable.ic_state_reject);
			mark1.setVisibility(View.VISIBLE);
			mark1.setText(record.getMark1());
			sign1.setVisibility(View.VISIBLE);
			sign1.setText(record.getApname()+" "+record.getAptime1().replace("T", " "));
			
			flowl2.setVisibility(View.GONE);
		}
		
		if(record.getStatus2id()==2&&record.getStatus1id()!=3)
		{
			flow2.setText("同意");
			flow2.setTextColor(Color.GREEN);
			img2.setBackgroundResource(R.drawable.ic_state_agree);
			mark2.setVisibility(View.VISIBLE);
			mark2.setText(record.getMark2());
			sign2.setVisibility(View.VISIBLE);
			sign2.setText("办公室"+" "+record.getAptime2().replace("T", " "));
			
		}else if(record.getStatus2id()==3&&record.getStatus1id()!=3)
		{
			flow2.setText("驳回");
			flow2.setTextColor(Color.RED);
			img2.setBackgroundResource(R.drawable.ic_state_reject);
			mark2.setVisibility(View.VISIBLE);
			mark2.setText(record.getMark2());
			sign2.setVisibility(View.VISIBLE);
			sign2.setText("办公室"+" "+record.getAptime2().replace("T", " "));
		}
	}
	
	public void onViewClick(View view)
	{
		switch (view.getId()) 
		{
		case R.id.setup_back:
		case R.id.title:
					finish();		
					break;		
		
		default:
			break;
		}
	}	
	
}
