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

public class BreakRulesActivity extends Activity 
{
	private ListView lv_break;
	private List<BreakRulesModel> modellist = new ArrayList<BreakRulesModel>();
	private BreakRulesAdapter breakAdapter;
	private TextView img_no;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_break_rules);
		
		img_no=(TextView)findViewById(R.id.img_no);
		lv_break=(ListView)findViewById(R.id.lv_break);		
		breakAdapter=new BreakRulesAdapter(getApplicationContext(),modellist);
		lv_break.setAdapter(breakAdapter);
		
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
					lv_break.setVisibility(View.GONE);
					img_no.setVisibility(View.VISIBLE);
					return;
				}
				List<Map<String,String>> list=null;
				
				try 
				{
					list = XML2Json.AnalysisOfXML(result,"recordset","record");
					
					for(int i=0;i<list.size();i++)
					{
						Map<String,String> map = list.get(i);
						
						BreakRulesModel model = new BreakRulesModel();

						String timeString=map.get("SLSJ");
						model.setSLSJ(timeString.substring(0,timeString.indexOf(".")));
						model.setFADD(map.get("FADD"));
						model.setWFNR(map.get("WFNR"));
						model.setCFLB(map.get("CFLB"));
						model.setSLH(map.get("SLH"));
						
						model.setSFCF(map.get("SFCF"));//是否处罚
						model.setAY(map.get("AY"));
						String timeString1=map.get("FASJ");
						model.setFASJ(timeString1.substring(0,timeString.indexOf(".")));
						model.setZYSS(map.get("ZYSS"));
						model.setWFTK(map.get("WFTK"));
						model.setCFTK(map.get("CFTK"));
						model.setCFYJ(map.get("CFYJ"));
						model.setSFJA(map.get("SFJA"));
						model.setSFCF(map.get("SFCF"));
						String timeString2=map.get("JARQ");
						model.setJARQ(timeString2.substring(0,timeString.indexOf(".")));
						model.setZWCM(map.get("ZWCM"));
						model.setAJLB(map.get("AJLB"));
						
						modellist.add(model);
						
					}				
				    
					runOnUiThread(new Runnable() 
					{
						@Override
						public void run() 
						{
							if (modellist.size() == 0) 
							{
								lv_break.setVisibility(View.GONE);
								img_no.setVisibility(View.VISIBLE);
							} else 
							{
								lv_break.setVisibility(View.VISIBLE);
								img_no.setVisibility(View.GONE);
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
		shipi.getChargeListByShip(SystemStatic.searchShipName, "CDP_CF_CBWZCFXX",0);
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
