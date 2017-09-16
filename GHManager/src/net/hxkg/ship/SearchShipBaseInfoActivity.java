package net.hxkg.ship;
import java.util.List;
import java.util.Map;
import net.hxkg.ghmanager.R;
import com.tuen.util.SystemStatic;
import android.app.Activity;
import android.os.Bundle;
import android.view.View; 
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
/*
 * 搜索船舶基本信息
 */
public class SearchShipBaseInfoActivity extends Activity
{

	private RelativeLayout relative_title_final;
	private TextView zwcm,cbdjh,cjdjh,cjg,cblx,syr,jyr,jyrdh,ckzhl,cbzc,zdw,zgl;
	TextView tipTextView;
	ScrollView dataScrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_search_ship_base_info);
		init();
		
	}
	
	public void onBack(View view)
	{
		finish();
	}
	private void init()
	{
		ShipInfo shipi=new ShipInfo(new ShipInfoListener() {
			
			@Override
			public void onShipInofo(String result) 
			{
				List<Map<String,String>> list=null;
				
				try {
					list=XML2Json.AnalysisOfXML(result, "record");
					Map<String,String> map = list.get(0);
					if(map==null||map.size()<=0)
					{
						
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								tipTextView.setVisibility(View.VISIBLE);
								dataScrollView.setVisibility(View.GONE);
								
							}
						});
						return;
					}
					final String CM=map.get("ZWCM");
					final String CBDJH=map.get("CBDJH");
					final String CJDJH=map.get("CJDJH");
					final String CJG=map.get("CJG");
					final String CBLX=map.get("CBLX");
					
					final String SYR=map.get("SYR");
					final String JYR=map.get("JYR");
					final String JYRDH=map.get("JYRDH");
					final String CKZHL=map.get("CKZHL");
					final String ZC=map.get("ZC");
					final String ZDW=map.get("ZDW");
					final String ZGL=map.get("ZJZGL");
				    
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() 
						{
							zwcm.setText(CM);
							cbdjh.setText(CBDJH);
							cjdjh.setText(CJDJH);
							cjg.setText(CJG);
							cblx.setText(CBLX);
							
							syr.setText(SYR);
							jyr.setText(JYR);
							jyrdh.setText(JYRDH);
							ckzhl.setText(CKZHL);
							cbzc.setText(ZC);
							zdw.setText(ZDW);
							zgl.setText(ZGL);
							
						}
					}); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onError() {
				
			}
		});
		shipi.getChargeListByShip(SystemStatic.searchShipName, "CDP_JC_CBJBXX",0);
		
		zwcm=(TextView)findViewById(R.id.text0_context);//船名
		cbdjh=(TextView)findViewById(R.id.text1_context);
		cjdjh=(TextView)findViewById(R.id.text2_context);
		cjg=(TextView)findViewById(R.id.text3_context);
		cblx=(TextView)findViewById(R.id.text4_context);
		
		syr=(TextView)findViewById(R.id.text6_context);
		jyr=(TextView)findViewById(R.id.text8_context);
		jyrdh=(TextView)findViewById(R.id.text9_context);
		ckzhl=(TextView)findViewById(R.id.text10_context);
		cbzc=(TextView)findViewById(R.id.text11_context);
		zdw=(TextView)findViewById(R.id.tons);
		zgl=(TextView)findViewById(R.id.gl);
		
		tipTextView=(TextView)findViewById(R.id.tip);
		dataScrollView=(ScrollView) findViewById(R.id.data);
		
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);		
		/*relative_title_final.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});*/
	}
}
