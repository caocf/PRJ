package net.hxkg.lawexcut;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ReasonActivity extends Activity implements RadioGroup.OnCheckedChangeListener
{
	public static int RESULTCODE=1;
	
	TextView text_r1,text_r2;
	RadioButton bt1,bt2;
	FragmentManager fragmentManager;
	FragmentR2 fr2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reason);
		
		bt1=(RadioButton) findViewById(R.id.btn_0);
		bt2=(RadioButton) findViewById(R.id.btn_1);
		RadioGroup options=(RadioGroup) findViewById(R.id.ra_group);
		options.setOnCheckedChangeListener(this);
		
		
		
		fragmentManager= getFragmentManager();
		if(bt1.isChecked())
		{
			FragmentR1 fragmentP=new FragmentR1();
			
			fragmentManager.beginTransaction().replace(R.id.frame,fragmentP ) .commit();
			bt1.setTextColor(Color.parseColor("#1766b1"));
			bt2.setTextColor(Color.parseColor("#901766b1"));
		}
		else
		{
			fr2=new FragmentR2();
			
			fragmentManager.beginTransaction().replace(R.id.frame,fr2 ) .commit();
			bt2.setTextColor(Color.parseColor("#1766b1"));
			bt1.setTextColor(Color.parseColor("#901766b1"));
		}
	}
	
	public void onViewClick(View v)
	{
		switch (v.getId())
		{
		case R.id.reason:
		case R.id.back:
			setResult(2);
			this.finish();
			break;
		case R.id.toreason:
			Intent intent=new Intent(this,ReasonActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	public void onClick(View view)
	{
		//System.out.println("fasdfdasfdasgfg");
		fr2.onClick();	
		
	}
	
	class FragmentR2 extends Fragment
	{
		EditText edit_reason;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.fragment2_reason, container,false);	
			
			edit_reason=(EditText)rootView. findViewById(R.id.edit_reason);
			
			
			return rootView;
		}
		
		public void onClick()
		{
			String string=edit_reason.getText().toString();
			//System.out.println(string);
			if(string==null||"".equals(string))
			{
				Toast.makeText(getActivity(), "请输入案由", Toast.LENGTH_LONG).show();
				return;
			}
			
			Intent intent=new Intent();
			intent.putExtra("reason", string); 
			getActivity().setResult(RESULTCODE, intent);
			getActivity().finish();
		}
	}
	class FragmentR1 extends Fragment implements OnItemClickListener,onResult
	{
		
		ListView listView;
		List<Map<String, Object>> list=new ArrayList<>();
		SimpleAdapter adapter;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.fragment1_reason, container,false);	
			listView=(ListView) rootView.findViewById(R.id.list);
			
			adapter=new SimpleAdapter(this.getActivity(),list,R.layout.fragment_item
					,new String[]{"text"}
					,new int[]{R.id.item});
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
			Fresh();
			
			return rootView;
		}
		
		private void Fresh()
		{
			HttpRequest request=new HttpRequest(this);
			Map<String, Object> map=new HashMap<>();
			//map.put("reason", value);
			request.post(Constants.BaseURL+"LawReasons", map);
		}
		
		

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		{
			Intent intent=new Intent();
			intent.putExtra("reason",(String)list.get(position).get("text"));
			getActivity().setResult(RESULTCODE, intent);
			getActivity().finish();
			
		}

		@Override
		public void onSuccess(String result) 
		{
			try {
				JSONArray jsonArray=new JSONArray(result);
				list.clear();
				for(int i=0;i<jsonArray.length();i++)
				{
					JSONObject object=jsonArray.getJSONObject(i);
					String s1=object.getString("reason");
					
					Map<String, Object> map=new HashMap<>();
					map.put("text", s1);
					list.add(map);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			adapter.notifyDataSetChanged();
			
		}

		@Override
		public void onError(int httpcode) {
			
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) 
	{
		switch (checkedId) 
		{
		case R.id.btn_0:
			FragmentR1 fragment1=new FragmentR1();
			
			fragmentManager.beginTransaction().replace(R.id.frame,fragment1 ) .commit();
			bt1.setTextColor(Color.parseColor("#1766b1"));
			bt2.setTextColor(Color.parseColor("#901766b1"));
			break;
		case R.id.btn_1:
			fr2=new FragmentR2();
			
			fragmentManager.beginTransaction().replace(R.id.frame,fr2) .commit();
			bt2.setTextColor(Color.parseColor("#1766b1"));	
			bt1.setTextColor(Color.parseColor("#901766b1"));
			break;

		default:
			break;
		}
		
	}
}
