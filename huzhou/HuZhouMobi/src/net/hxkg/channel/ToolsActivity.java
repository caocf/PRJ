package net.hxkg.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ToolsActivity extends Activity implements OnItemClickListener,TextWatcher
{
	ListView listView;
	ArrayAdapter<String> simpleAdapter;
	List<String> nameList=new ArrayList<>();
	String selectString="";
	EditText tipEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tool);	
		initListView();
	}
	
	private void initListView()
	{	
		tipEditText=(EditText) findViewById(R.id.edit);
		tipEditText.addTextChangedListener(this);
		listView=(ListView) findViewById(R.id.listview);
		simpleAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,nameList);
		listView.setAdapter(simpleAdapter);
		listView.setOnItemClickListener(this);
		getData("");
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
	
	private void getData(String s)
	{
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult() {
			
			@Override
			public void onSuccess(String result) 
			{
				try {
					nameList.clear();
					JSONObject object=new JSONObject(result.trim());
					JSONArray array=object.getJSONArray("dataList"); 
					for(int i=0;i<array.length();i++)
					{
						JSONObject object2=array.getJSONObject(i);
						String nameString=object2.getString("name");
						nameList.add(nameString);
						
					}
				} catch (Exception e) 
				{
					
				}
				simpleAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
		});
		Map<String,Object> params=new HashMap<>();
		params.put("tip", s.trim());
		
		httpRequest.post(HttpUtil.BASE_URL+"findCruiseToolsByTip", params);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		selectString=nameList.get(position);
		//tipEditText.setText(selectString);
		
	}
	
	public void onAdd(View view)
	{
		if(selectString.equals(""))
		{
			Toast.makeText(this, "请选择巡航工具!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent intent=new Intent();
		intent.putExtra("name", selectString);
		setResult(200, intent);
		finish();
		
	}
	
	public void onBack(View view)
	{
		this.finish();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTextChanged(Editable s) 
	{
		getData(s.toString());
		
	}

}
