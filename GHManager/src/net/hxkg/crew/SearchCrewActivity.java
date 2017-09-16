package net.hxkg.crew;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import org.json.JSONArray;
import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SearchCrewActivity extends AbActivity implements HttpRequest.onResult
{
	@AbIocView(id = R.id.back,click="btnClick")TextView back;
	@AbIocView(id = R.id.edt_search) EditText edt_search;
	@AbIocView(id = R.id.listview) ListView listview;
	@AbIocView(id = R.id.txt_clear,click="btnClick") ImageView txt_clear;

	private List<CrewBaseEN> CIE = new ArrayList<CrewBaseEN>();//listview 的实体类
	private String sea_name;
	private CrewSearchAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_crew_info);
		//监听文字变化
		edt_search.addTextChangedListener(watcher);
		adapter = new CrewSearchAdapter(this,CIE);
		//listview.setDividerHeight(0);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(onitem);
	}
	public TextWatcher watcher = new TextWatcher() 
	{

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) 
		{
			// TODO Auto-generated method stub
			
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,int after) 
		{
			// TODO Auto-generated method stub
		}
		@Override
		public void afterTextChanged(Editable s) 
		{
			if(s.toString().equals(""))
			{
				CIE.clear();
				adapter.notifyDataSetChanged();
				
				return;
			}
			
			sea_name = edt_search.getText().toString();
			
			initData();
			
		}
	};
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.txt_clear:
			edt_search.setText("");
			break;
		default:
			break;
		}
	}
	public OnItemClickListener onitem = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			switch (parent.getId()){
			case R.id.listview:
				expressItemClick(position);//position 代表你点的哪一个
				break;
			}
		}
		private void expressItemClick(int position) 
		{
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), CrewInfoActivity.class);
			intent.putExtra("crewName", CIE.get(position).getName());
			startActivity(intent);
		}
	};
	public void initData()
	{
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("tip", sea_name);
		
		httpRequest.post(Constants.BaseURL+"CrewnamesByTip", map);
		
	}
	@Override
	public void onSuccess(String result) { 
		try
		{ 
			JSONArray data = new JSONArray(result);
			CIE.clear();
			for(int i = 0;i<data.length();i++)
			{
				String name= (String) data.opt(i);
				
				CrewBaseEN cne = new CrewBaseEN();
				cne.setName(name);
				CIE.add(cne);
			}
			adapter.notifyDataSetChanged();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
}
