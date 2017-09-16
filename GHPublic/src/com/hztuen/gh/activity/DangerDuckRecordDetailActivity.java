package com.hztuen.gh.activity;

import com.hxkg.ghpublic.R;
import com.hztuen.lvyou.utils.SystemStatic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener; 
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DangerDuckRecordDetailActivity extends Activity implements OnClickListener{

	
	private TextView ship_name,from,to,goods,rank,unit,time,time_end,tons,edit_ship,status_add,wharf;
    private RelativeLayout relative_title_final;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_danger_duck_record_detail);
		init();
	}
	private void init()
	{
		ship_name=(TextView)findViewById(R.id.text1_context);//作业码头
		ship_name.setText(SystemStatic.Wharfname);
		//承运船舶
	   edit_ship=(TextView)findViewById(R.id.text30_context);
				
				//结束作业时间
		time_end=(TextView)findViewById(R.id.text10_context);
				
		wharf=(TextView)findViewById(R.id.text1_context);
		from=(TextView)findViewById(R.id.text2_context);//始发港
		to=(TextView)findViewById(R.id.text3_context);//目的港
		goods=(TextView)findViewById(R.id.text4_context);//危险品货物名称
		rank=(TextView)findViewById(R.id.text5_context);//危货类型
		tons=(TextView)findViewById(R.id.text6_context);//危货重量
		unit=(TextView)findViewById(R.id.unit);//重量单位
		time=(TextView)findViewById(R.id.text8_context);//开始作业时间
		
		status_add=(TextView)findViewById(R.id.text11_context);//状态信息
		
		
		Intent in = getIntent();
		
		
			
			
		String wharfString=in.getStringExtra("wharf");
			String ship = in.getStringExtra("ship");
			String startport = in.getStringExtra("startport");
			String targetport = in.getStringExtra("targetport");
			String goodss = in.getStringExtra("goods");
			String goodsweight = in.getStringExtra("goodsweight");
			String goodstype = in.getStringExtra("goodstype");
			String portime = in.getStringExtra("portime");
			String status= in.getStringExtra("status");
			
			String endtime= in.getStringExtra("endtime");
			
			String units= in.getStringExtra("unit");
			
			edit_ship.setText(ship);
			from.setText(startport);
			to.setText(targetport);
			goods.setText(goodss);
			rank.setText(goodstype);
			tons.setText(goodsweight);
			unit.setText(units);
			wharf.setText(wharfString);
			
			if("0".equals(status))
			{
				status_add.setText("待审批");
				status_add.setTextColor(Color.parseColor("#f89513"));
			}else if("1".equals(status))
			{
				status_add.setText("不批准");
				status_add.setTextColor(Color.RED);
			}else if("2".equals(status))
			{
				status_add.setText("批准");
				status_add.setTextColor(Color.GREEN);
			}else{
				status_add.setText(status);
				
				status_add.setTextColor(Color.parseColor("#f89513"));
				
			}
			
			
			
			time.setText(portime);
			time_end.setText(endtime);
			
			relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);
			relative_title_final.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
					
				}
			});
			
			
		}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
		
		
	
	

}
