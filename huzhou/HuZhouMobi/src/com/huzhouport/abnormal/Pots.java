package com.huzhouport.abnormal;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.huzhouport.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Pots extends Activity implements OnItemClickListener
{
	private static String[] potsStrings={"ºéÇÅ","ÄÏä±","Ë«ÁÖ","ÐÂÊÐ","Ç¬Ôª","Ã·Ïª","Ì«ºþ","ºÍÔÆÇÅ","ºþÖÝ´¬Õ¢"};
	private static String[] potsid={"572001","572002","572003","572004","572005","572006","572007","572008","572009"};
	public static int RESULT_CODE=201;
	private SharedPreferences sp;
	
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.setContentView(R.layout.abnormallist_pots_activity);
		sp=this.getSharedPreferences("ABNORMAL", Context.MODE_PRIVATE);		
		setList();
	}
	
	public void GoBack(View v)
	{
		this.finish();
	}
	
	private void setList()
	{
		ListView lv=(ListView) this.findViewById(R.id.lv_pots);
		SimpleAdapter adapter=new SimpleAdapter(this,getData(),R.layout.abnormalpots_item
												,new String[]{"name"},new int[]{R.id.tx_item_pot});
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}
	
	private ArrayList<HashMap<String, Object>> getData()
	{
		ArrayList<HashMap<String, Object>> dataArrayList=new ArrayList<HashMap<String, Object>>();
		
		
		for(int i=0;i<potsStrings.length;i++)
		{
			HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("name", potsStrings[i]);
			dataArrayList.add(map);
		}
		
		return dataArrayList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		Intent intent=new Intent();  
        intent.putExtra("pot", potsStrings[position]);  
        intent.putExtra("potsid", potsid[position]);
        setResult(RESULT_CODE, intent); 
        
        SharedPreferences.Editor editor=sp.edit();
		editor.putString("potsid", potsid[position]);
		editor.commit();
        finish(); 
		
	}
}
