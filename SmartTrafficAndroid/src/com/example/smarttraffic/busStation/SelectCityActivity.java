package com.example.smarttraffic.busStation;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class SelectCityActivity extends FragmentActivity {

	ListView hotCityListView;
	TextView defaultCityTextView;
	String[] hotCitys;
	ImageView searchImageView;
	
	public static final String INTENT_PARAM="city";
	EditText inputEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_city);
		
		initHead(savedInstanceState);
		
		hotCityListView=(ListView)findViewById(R.id.bus_hot_city_list);
		defaultCityTextView=(TextView)findViewById(R.id.bus_search_city_default);
		searchImageView=(ImageView)findViewById(R.id.bus_search_city_enter);
		inputEditText=(EditText)findViewById(R.id.bus_search_city_edit);
		
		defaultCityTextView.setOnClickListener(new CityOnclick());
		searchImageView.setOnClickListener(new CityOnclick());
		
		initListview();
	}
	
	class CityOnclick implements OnClickListener
	{

		@Override
		public void onClick(View v) 
		{
			switch (v.getId())
			{
				case R.id.bus_search_city_default:
					StartIntent.resultActivity(SelectCityActivity.this, 1, INTENT_PARAM, defaultCityTextView.getText().toString());
					break;
					
				case R.id.bus_search_city_enter:					
					if(!inputEditText.getText().toString().equals(""))
					StartIntent.resultActivity(SelectCityActivity.this, 1, INTENT_PARAM, inputEditText.getText().toString());
					break;

			}			
		}		
	}
	
	private void initHead(Bundle savedInstanceState)
	{
		if (savedInstanceState == null) 
        {
    		HeadNameFragment headFragment=new HeadNameFragment();
    		
    		String nameString="城市选择";
    		headFragment.setTitleName(nameString);
    		
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.select_city_head, headFragment)
                    .commit();
        }
	}
	
	private void initListview()
	{
		hotCitys=getResources().getStringArray(R.array.hotCity);
		hotCityListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hotCitys));
		hotCityListView.setOnItemClickListener(new hotCityListListener());
	}
	
	class hotCityListListener implements OnItemClickListener
	{
		public hotCityListListener()
		{
			if(hotCitys.length>0)
				citys=hotCitys;
			else 
				citys=getResources().getStringArray(R.array.hotCity);
				
		}
		String[] citys;
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			StartIntent.resultActivity(SelectCityActivity.this, 1, INTENT_PARAM, citys[position]);	
		}
		
	}
}
