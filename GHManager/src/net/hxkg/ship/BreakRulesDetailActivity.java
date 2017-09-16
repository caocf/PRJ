package net.hxkg.ship;



import net.hxkg.ghmanager.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BreakRulesDetailActivity extends Activity implements OnClickListener{

	private RelativeLayout relative_title_final;
	private TextView tv_date,tv_ship,tv_address,tv_content,tv_type,tv_chu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_break_rules_detail);
		init();
		
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);		
		relative_title_final.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
	private void init()
	{
		tv_date=(TextView)findViewById(R.id.text1_context);
		tv_ship=(TextView)findViewById(R.id.text2_context);
		tv_address=(TextView)findViewById(R.id.text3_context);
		tv_content=(TextView)findViewById(R.id.text4_context);
		tv_type=(TextView)findViewById(R.id.text5_context);
		tv_chu=(TextView)findViewById(R.id.text6_context);
		
		
		
		Intent in = getIntent();
		String SLSJ = in.getStringExtra("SLSJ");
		String FADD = in.getStringExtra("FADD");
		String WFNR = in.getStringExtra("WFNR");
		String CFLB = in.getStringExtra("CFLB");
		String SFCF = in.getStringExtra("SFCF");
		
		
		if("1".equals(SFCF))
		{
			SFCF="已处理";
		}else if("0".equals(SFCF))
		{
			SFCF="未处理";
			tv_chu.setTextColor(Color.parseColor("#ff2323"));
		}
		tv_date.setText(SLSJ);
		tv_ship.setText("");
		tv_address.setText(FADD);
		tv_content.setText(WFNR);
		tv_type.setText(CFLB);
		tv_chu.setText(SFCF);
		
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	

}
