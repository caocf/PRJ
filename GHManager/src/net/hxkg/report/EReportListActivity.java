package net.hxkg.report;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.ab.view.wheel.AbWheelView;
import com.ab.view.wheel.AbWheelView.AbOnWheelChangedListener; 
import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest; 
import android.app.Activity;  
import android.os.Bundle;   
import android.view.View;  
import android.widget.EditText;
import android.widget.ListView;

public class EReportListActivity extends Activity implements HttpRequest.onResult,AbOnWheelChangedListener
{	  
	int status,shipname;
	ListView listview;
	EAdapter adapter;
	List<EReportModol> datalist=new ArrayList<>();
	
	EditText tipeEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ereportlsit);  
		initView();
		RequestData(tipeEditText.getText().toString().trim());
	} 
	
	public void  onViewClick(View v)
	{ 
	
		switch(v.getId())
		{
		case R.id.back:finish();
			
		}
	}
	
	public void onSearch(View v)
	{
		RequestData(tipeEditText.getText().toString().trim());
	}
	
	
	private void initView()
	{		
		listview=(ListView) this.findViewById(R.id.list);
		adapter=new EAdapter(this,datalist);
		listview.setAdapter(adapter);
		
		tipeEditText=(EditText) findViewById(R.id.edit);
		
	}
	
	 
	private void RequestData(String tip)
	{
		HttpRequest hr=new HttpRequest(this);
		Map<String,Object> map =new HashMap();
		map.put("tip", tip);  
		hr.post(Constants.BaseURL+"ERePortByTip", map);
	}
 

	@Override
	public void onSuccess(String result) 
	{ 
		datalist.clear();
		//String res=result.replace("\\", "");
		//String s=res.substring(1,res.length()-1);
		try {
			JSONArray arr=new JSONArray(result.trim());
			for(int i=0;i<arr.length();i++)
			{
				EReportModol modol=new EReportModol();
				JSONObject obj=arr.getJSONObject(i);
				modol.shipname=obj.getString("shipname");
				JSONObject goodsobj=obj.getJSONObject("goodstypeEN");				
				modol.goods=goodsobj.getString("typname");
				JSONObject fromobj=obj.getJSONObject("startport");				
				modol.from=fromobj.getString("portname");
				JSONObject toobj=obj.getJSONObject("endport");				
				modol.to=toobj.getString("portname");
				JSONObject reportobj=obj.getJSONObject("reportclassEN");				
				modol.reportid=reportobj.getString("name");
				JSONObject inoutobj=obj.getJSONObject("reporttypeEN");				
				modol.inout=inoutobj.getString("reporttype");
				modol.time=obj.getString("commitdate");
				modol.goodscount=obj.getString("goodscount");
				modol.gascount=obj.getString("lastfuelcount");
				modol.gastime=obj.getString("lastfueltime");
				JSONObject unitobj=obj.getJSONObject("goodsunitEN");
				modol.unit=unitobj.getString("unitname");
				
				datalist.add(modol);
			}
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onError(int httpcode) 
	{ 
		
	}
	  

	@Override
	public void onChanged(AbWheelView wheel, int oldValue, int newValue) 
	{
		
	}
}
