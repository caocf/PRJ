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

public class CheckListActivity extends Activity 
{
	private ListView lv_break;
	private List<CheckInfo> modellist = new ArrayList<CheckInfo>();
	private CheckInfoAdapter breakAdapter;	
	TextView tipTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checklist);
		
		lv_break=(ListView)findViewById(R.id.lv_break);		
		breakAdapter=new CheckInfoAdapter(getApplicationContext(),modellist);
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
					Toast.makeText(CheckListActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
					return;
				}
				List<Map<String,String>> list=null;
				
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
						
						CheckInfo model = new CheckInfo();

						model.setJYBH((map.get("JYBH")));
						model.setCJDJH(map.get("CJDJH"));
						model.setZWCM(map.get("ZWCM"));
						model.setJYDD(map.get("JYDD"));
						model.setJYDWMC(map.get("JYDWMC"));
						model.setJYBM(map.get("JYBM"));
						model.setSQR(map.get("SQR"));
						
						
						String t1=map.get("JYKSRQ");
						model.setJYKSRQ(t1.trim().substring(0,10));
						String t2=map.get("JYWCRQ");
						model.setJYWCRQ(t2.trim().substring(0,10));
						String t3=map.get("XCJYRQ");
						model.setXCJYRQ(t3.trim().substring(0,10));
						
						
						model.setJYZL(map.get("JYZL"));
						model.setSFWC(map.get("SFWC"));
						model.setBZ(map.get("BZ"));					
						
						modellist.add(model);
						
					}				
				    
					runOnUiThread(new Runnable() 
					{
						@Override
						public void run() 
						{
							if (modellist.size() == 0) 
							{
								Toast.makeText(CheckListActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
							}
							
							breakAdapter.notifyDataSetChanged();
							
						}
					});
					
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			
			@Override
			public void onError() {
				
			}
		});
		shipi.getChargeListByShip(SystemStatic.searchShipName, "CDP_CJ_CBJYXX",0);
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
