package net.hxkg.company;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.hxkg.ghmanager.R; 
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.ship.ShipInfo;
import net.hxkg.ship.ShipInfoListener;
import net.hxkg.ship.XML2Json;
import org.json.JSONArray;
import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class CompanyInfoActivity extends AbActivity implements HttpRequest.onResult
{ 
	@AbIocView(id = R.id.back,click="btnClick")TextView back;
	@AbIocView(id = R.id.edt_search) EditText edt_search;
	@AbIocView(id = R.id.listview) ListView listview;
	@AbIocView(id = R.id.txt_clear,click="btnClick") ImageView txt_clear;

	private List<CompanyNameEntity> CIE = new ArrayList<CompanyNameEntity>();//listview 的实体类
	private String sea_name;
	private CompanySearchAdapter adapter;
	SharedPreferences spPreferences;
	Set<String> set=new HashSet<>();
	SharedPreferences.Editor editor;
	
	View viewfooter;
	TextView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_company_info);
		//监听文字变化
		edt_search.addTextChangedListener(watcher);
		adapter = new CompanySearchAdapter(getApplicationContext(), CIE);
		viewfooter= getLayoutInflater().inflate(R.layout.list_footer_clear, null);
		listview.addFooterView(viewfooter);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(onitem);
		
		spPreferences=getSharedPreferences("Comname", 0);
		set=spPreferences.getStringSet("comname", set);
		editor=spPreferences.edit();
		
		imageView=(TextView) findViewById(R.id.tip);
		
		
		for(String name:set)
		{
			CompanyNameEntity cm=new CompanyNameEntity();
			cm.setCompanyName(name);
			CIE.add(cm);
		}
		if(CIE.size()>0)
		viewfooter.setVisibility(View.VISIBLE);
		else {
			viewfooter.setVisibility(View.GONE);
		}
		adapter.setHistory(true);
		adapter.notifyDataSetChanged();
	}
	public TextWatcher watcher = new TextWatcher() 
	{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		@Override
		public void afterTextChanged(Editable s)
		{
			sea_name = edt_search.getText().toString();
			listview.setVisibility(View.VISIBLE);
			imageView.setVisibility(View.GONE);
			if(sea_name.equals(""))
			{
				CIE.clear();
				for(String name:set)
				{
					CompanyNameEntity cm=new CompanyNameEntity();
					cm.setCompanyName(name);
					CIE.add(cm);
				}
				if(CIE.size()>0)
				viewfooter.setVisibility(View.VISIBLE);
				else {
					viewfooter.setVisibility(View.GONE);
				}
				adapter.setHistory(true);
				adapter.notifyDataSetChanged();
				return;
			}
			
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
	public OnItemClickListener onitem = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		{
			if(position==CIE.size())
			{
				set.clear();
				CIE.clear();
				adapter.notifyDataSetChanged();
				viewfooter.setVisibility(View.GONE);
				return;
			}
			
			set.add(CIE.get(position).getCompanyName());
			onSearch(CIE.get(position).getCompanyName());
		}
		
	};
	
	public void onSearchName(View view)
	{
		set.add(sea_name);
		onSearch(sea_name);
		
	}
	
	public void onSearch(String name)
	{
		ShipInfo shipi=new ShipInfo(new ShipInfoListener()
		{
			@Override
			public void onShipInofo(String result)
			{
				List<Map<String,String>> list=null;
				
				try 
				{
					list=XML2Json.AnalysisOfXML(result, "record");
					
					Map<String,String> map = list.get(0);
					if(map==null||map.size()<=0)
					{
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								imageView.setVisibility(View.VISIBLE);
								listview.setVisibility(View.GONE);
								
							}
						});
						
						return;
					}
					final String QYMC=map.get("QYMC");
					final String QYDZ=map.get("QYDZ");
					final String FRDB=map.get("FRDB");
					final String DHHM=map.get("DHHM");
					final String HYFL=map.get("HYFL");					
					final String ZYKYHQ=map.get("ZYKYHQ");
					final String ZYHYHQ=map.get("ZYHYHQ");
					final String ZYKYFW=map.get("ZYKYFW");
					final String ZYHYFW=map.get("ZYHYFW");
					final String JYFW=map.get("JYFW");
					final String PZRQ=map.get("PZRQ"); 
					
					CompanyNameEntity comEntity=new CompanyNameEntity();
					comEntity.QYMC=QYMC;
					comEntity.QYDZ=QYDZ;
					comEntity.FRDB=FRDB;
					comEntity.DHHM=DHHM;
					comEntity.HYFL=HYFL;
					comEntity.ZYKYHQ=ZYKYHQ;
					comEntity.ZYHYHQ=ZYHYHQ;
					comEntity.ZYKYFW=ZYKYFW;
					comEntity.ZYHYFW=ZYHYFW;
					comEntity.JYFW=JYFW;
					comEntity.PZRQ=PZRQ;
					
					Intent intent=new Intent(CompanyInfoActivity.this,CompanyDetialActivity.class);
					intent.putExtra("CompanyNameEntity", comEntity);
					CompanyInfoActivity.this.startActivity(intent);
				
				} catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void onError() 
			{
				
			}
			
		});
		
		shipi.getChargeListByShip(name, "CDP_XK_YZ_HYQYXX", 0);
	}
	
	public void initData()
	{
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("Tip", sea_name);
		
		httpRequest.post(Constants.BaseURL+"companynames", map); 
	
		
		
	}
	@Override
	public void onSuccess(String result)
	{ 
		try{ 
			JSONArray data = new JSONArray(result);
			CIE.clear();
			for(int i = 0;i<data.length();i++)
			{
				CompanyNameEntity cne = new CompanyNameEntity();
				cne.setCompanyName(data.getString(i));
				CIE.add(cne);
			}
			viewfooter.setVisibility(View.GONE);
			adapter.setHistory(false);
			adapter.notifyDataSetChanged();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onDestroy() 
	{
		editor.putStringSet("comname", set);
		editor.commit();
		super.onDestroy();
	}
}
