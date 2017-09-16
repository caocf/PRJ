package com.huzhouport.integratedquery;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huzhouport.R;
import com.huzhouport.common.tool.CountTime;


public class HYQYXX extends Activity {

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hyqyxx);
		ImageButton img_back = (ImageButton) findViewById(R.id.hyqyxx_back);
		
		String getResult=getIntent().getStringExtra("result");
		initDate(getResult);
		
		
		img_back.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				finish();
				
			}
		});
	}
	public void initDate(String getResult){
		TextView tv_title = (TextView) findViewById(R.id.hyqyxx_title);
		TextView tv_qymc = (TextView) findViewById(R.id.hyqyxx_qymc);
		TextView tv_qydz = (TextView) findViewById(R.id.hyqyxx_qydd);
		TextView tv_frdb= (TextView) findViewById(R.id.hyqyxx_frdb);
		TextView tv_dhhm = (TextView) findViewById(R.id.hyqyxx_dhhm);
		TextView tv_hyfl = (TextView) findViewById(R.id.hyqyxx_hyfl);
		TextView tv_zykyhq = (TextView) findViewById(R.id.hyqyxx_zykyhq);
		TextView tv_zyhyhq = (TextView) findViewById(R.id.hyqyxx_zyhyhq);
		TextView tv_zykyfw = (TextView) findViewById(R.id.hyqyxx_zykyfw);
		TextView tv_zyhyfw = (TextView) findViewById(R.id.hyqyxx_zyhyfw);
		TextView tv_jyfw = (TextView) findViewById(R.id.hyqyxx_jyfw);
		TextView tv_pzrq = (TextView) findViewById(R.id.hyqyxx_pzrq);
		

		try
		{
			JSONTokener jsonParser = new JSONTokener(getResult);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			// 接下来的就是JSON对象的操作了
			JSONArray jsonArray = data.getJSONArray("list");
			JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);
			tv_title.setText(jsonObject2.getString("QYMC"));
			tv_qymc.setText(jsonObject2.getString("QYMC"));
			tv_qydz.setText(jsonObject2.getString("QYDZ"));
			tv_frdb.setText(jsonObject2.getString("FRDB"));
			tv_dhhm.setText(jsonObject2.getString("DHHM"));
			tv_hyfl.setText(jsonObject2.getString("HYFL"));
			tv_zyhyhq.setText(jsonObject2.getString("ZYKYHQ"));
			tv_zykyhq.setText(jsonObject2.getString("ZYHYHQ"));
			tv_zykyfw.setText(jsonObject2.getString("ZYKYFW"));
			tv_zyhyfw.setText(jsonObject2.getString("ZYHYFW"));
			tv_jyfw.setText(jsonObject2.getString("JYFW"));
			tv_pzrq.setText(CountTime.FormatTimeToDay(jsonObject2.getString("PZRQ")));
			

		}
		catch (Exception e)
		{
			e.printStackTrace();
			Toast.makeText(this, "搜索失败", Toast.LENGTH_LONG).show();
		}
	
	}
}

