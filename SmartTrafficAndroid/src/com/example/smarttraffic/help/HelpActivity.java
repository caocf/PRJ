package com.example.smarttraffic.help;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HelpActivity extends FragmentActivity
{
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_help);
		
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName("使用帮助");
		ManagerFragment.replaceFragment(this,R.id.help_head, fragment);
		listView=(ListView)findViewById(R.id.help_content_listview);
		
		ArrayAdapter<String> data=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.array_help));
		listView.setAdapter(data);
	}
}
