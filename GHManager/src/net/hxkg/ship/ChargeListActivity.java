package net.hxkg.ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.hxkg.ghmanager.R;
import com.tuen.util.SystemStatic;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChargeListActivity extends Activity 
{
	private ListView lv_break;
	private List<ChargeModol> modellist = new ArrayList<ChargeModol>();
	private ChargeListAdapter breakAdapter;	
	TextView tipTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chargelist);
		
		lv_break=(ListView)findViewById(R.id.lv_break);		
		breakAdapter=new ChargeListAdapter(getApplicationContext(),modellist);
		lv_break.setAdapter(breakAdapter);
		tipTextView=(TextView)findViewById(R.id.tip);
		
		
		init();
	}
	private void init()
	{	
		ShipInfo shipi=new ShipInfo(new ShipInfoListener() 
		{			
			@Override
			public void onShipInofo(String result) 
			{
				if("".equals(result)||result==null)
				{
					Toast.makeText(ChargeListActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
					return;
				}
				List<Map<String,String>> list=null;
				System.out.println(result);
				
				try 
				{
					list = XML2Json.AnalysisOfXML(result,"recordset","record");
					if(list==null||list.size()<=0)
					{
						
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								tipTextView.setVisibility(View.VISIBLE);
								lv_break.setVisibility(View.GONE);
								
							}
						});
						return;
					}
					
					for(int i=0;i<list.size();i++)
					{
						Map<String,String> map = list.get(i);
						
						ChargeModol model = new ChargeModol();

						model.setXLH((map.get("XLH")));
						model.setJFXMDM(map.get("JFXMDM"));
						model.setJFXMMC(map.get("JFXMMC"));
						model.setSFFSDM(map.get("SFFSDM"));
						model.setSFFSMC(map.get("SFFSMC"));
						model.setYJZE(map.get("YJZE"));
						model.setSJZE(map.get("SJZE"));
						String t1=map.get("YXQQ");
						model.setYXQQ(t1.trim().substring(0,10));
						String t2=map.get("YXQZ");
						model.setYXQZ(t2.trim().substring(0,10));
						String t3=map.get("KPRQ");
						model.setKPRQ(t3.trim().substring(0,10));
						model.setKPDSMC(map.get("KPDWMC"));
						model.setKPXLH(map.get("KPXLH"));
						model.setKPRXM(map.get("KPRXM"));
						model.setKPDWMC(map.get("KPRXM"));
						model.setKPDSMC(map.get("KPRXM"));
						
						
						modellist.add(model);
						
					}				
				    
					runOnUiThread(new Runnable() 
					{
						@Override
						public void run() 
						{
							if (modellist.size() == 0) 
							{
								Toast.makeText(ChargeListActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
							}
							
							breakAdapter.notifyDataSetChanged();
							
						}
					});
					
				} catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
		});
		shipi.getChargeListByShip(SystemStatic.searchShipName, "CDP_JZ_CBJFXX",0);
	}
	
	public void onViewClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

}
