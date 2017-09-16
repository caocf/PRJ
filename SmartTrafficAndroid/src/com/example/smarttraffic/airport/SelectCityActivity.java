package com.example.smarttraffic.airport;

import java.util.List;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
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


public class SelectCityActivity extends FragmentActivity implements UpdateView{

	ListView hotCityListView;
	TextView defaultCityTextView;
	TextView defaultCityLabelTextView;
	TextView hotCityListLabelTextView;
	
	String[] hotCitys;
	ImageView searchImageView;
	
	public static final String INTENT_PARAM="city";
	EditText inputEditText;
	AirSuggestRequest airSuggestRequest;
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Object data)
	{
		if(data instanceof List<?>)
		{
			List<AirCity> list=(List<AirCity>)data;
			
			String[] str=new String[list.size()+1];
			str[0]=inputEditText.getText().toString();
			for(int i=1;i<(list.size()+1);i++)
			{
				str[i]=list.get(i-1).getName();
			}
		
			hotCityListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, str));
		}
		
	}
	
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
		
		defaultCityLabelTextView=(TextView)findViewById(R.id.bus_search_city_default_name);
		hotCityListLabelTextView=(TextView)findViewById(R.id.bus_search_city_hot_name);
		
		defaultCityTextView.setOnClickListener(new CityOnclick());
		searchImageView.setOnClickListener(new CityOnclick());
		
		inputEditText.addTextChangedListener(new TextChangeListener());
		
		initListview();
	}
	
	class TextChangeListener implements TextWatcher
	{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count)
		{			
			if(s.toString().equals(""))
			{
				defaultCityLabelTextView.setVisibility(View.VISIBLE);
				defaultCityTextView.setVisibility(View.VISIBLE);
				hotCityListLabelTextView.setVisibility(View.VISIBLE);
				hotCityListView.setAdapter(new ArrayAdapter<String>(SelectCityActivity.this, android.R.layout.simple_list_item_1, hotCitys));
				return;
			}
						
			defaultCityLabelTextView.setVisibility(View.GONE);
			defaultCityTextView.setVisibility(View.GONE);
			hotCityListLabelTextView.setVisibility(View.GONE);
			
			if(airSuggestRequest==null)
				airSuggestRequest=new AirSuggestRequest();
			airSuggestRequest.setCityName(s.toString());
			
			new HttpThread(new AirSuggestSearch(),airSuggestRequest , SelectCityActivity.this,SelectCityActivity.this,R.string.error_air_city_info).start();
			
		}

		@Override
		public void afterTextChanged(Editable s)
		{
			
		}
		
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
		@SuppressWarnings("unchecked")
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			ArrayAdapter<String> data=(ArrayAdapter<String>)parent.getAdapter();
			
			StartIntent.resultActivity(SelectCityActivity.this, 1, INTENT_PARAM, data.getItem(position));	
		}
		
	}
}
