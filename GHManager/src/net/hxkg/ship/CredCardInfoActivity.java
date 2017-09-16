package net.hxkg.ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.hxkg.ghmanager.R;
import com.tuen.util.SystemStatic;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CredCardInfoActivity extends Activity implements OnClickListener,ShipInfoListener
{
	private RelativeLayout relative_title_final;
	private ListView list_cred_info;
	private List<CredCardModel> modellist = new ArrayList<CredCardModel>();
	private CredCardAdapter credAdapter;
	TextView tipTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cred_info);
		init();
	}

	private void init() {
		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);

		list_cred_info = (ListView) findViewById(R.id.list_cred_info);

		credAdapter = new CredCardAdapter(getApplicationContext(), modellist);
		tipTextView=(TextView)findViewById(R.id.tip);

		ShipInfo shipi = new ShipInfo(new ShipInfoListener() {

			@Override
			public void onShipInofo(String result) {
				List<Map<String, String>> list = null;

				try {
					list = XML2Json.AnalysisOfXML(result, "recordset", "record");
					if(list==null||list.size()<=0)
					{
						
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								tipTextView.setVisibility(View.VISIBLE);
								list_cred_info.setVisibility(View.GONE);
								
							}
						});
						return;
					}

					for (int i = 0; i < list.size(); i++) 
					{
						Map<String, String> map = list.get(i);

						CredCardModel model = new CredCardModel();

						String string0=map.get("FZRQ");
						String FZRQ[]=string0.split(" ");						
						model.setFZRQ(FZRQ[0]);
						
						String string1=map.get("YXRQ");
						String YXRQ[]=string1.split(" ");
						model.setYXRQ(YXRQ[0]);
						
						model.setZSMC(map.get("ZSMC"));
						model.setZSBH(map.get("ZSBH"));

						modellist.add(model);

					}

					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							list_cred_info.setAdapter(credAdapter);
						}
					});

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub

			}
		});
		shipi.getChargeListByShip(SystemStatic.searchShipName, "CDP_ZH_CBCZXX",0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}

	}

	@Override
	public void onShipInofo(String result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub

	}

}
