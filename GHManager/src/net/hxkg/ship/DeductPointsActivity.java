package net.hxkg.ship;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.hxkg.ghmanager.R;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.contants;
import net.hxkg.network.HttpRequest.onResult;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tuen.util.SystemStatic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DeductPointsActivity extends Activity implements OnClickListener,onResult
{	
	private RelativeLayout relative_title_final;
	private List<DeductPointsModel> modellist = new ArrayList<DeductPointsModel>();
	private DeductPointsAdapter deductAdapter;
	private ListView list_deduct_points;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_deduct_points);
		init();
	}
	private void init()
	{		
		deductAdapter = new DeductPointsAdapter(getApplicationContext(), modellist);
		list_deduct_points=(ListView)findViewById(R.id.list_deduct_points);
		GetCredCardTask();
		
		
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);		
		relative_title_final.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	// 获取扣分列表
	private void GetCredCardTask() {

		String toUrl = contants.creditlist;
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("Shipname", SystemStatic.searchShipName);
		map.put("Page", "0");
		hr.post(toUrl, map);

		
	}
	@Override
	public void onSuccess(String result) {
		try {

			//JSONObject resultJO = new JSONObject(result);
			// String resultMsg = resultJO.getString("resultMsg");
			// Log.i(TAG+":kymjs---resultMsg", resultJO.toString());
			JSONObject res = new JSONObject(result);
			JSONArray data = res.getJSONArray("data");
			Log.i("GH_TEXT", data.length() + "我是数据的size");
			if (data.length() == 0) {
				Toast.makeText(getApplicationContext(), "无扣分记录",
						Toast.LENGTH_SHORT).show();
			} else {
				for (int i = 0; i < data.length(); i++) {
					JSONObject temp = data.getJSONObject(i);
					DeductPointsModel model = new DeductPointsModel();
					// {"data":[{"id":2,"penalty":-5,"reason":"分数","time":null}],"total":1,"page":1,"rows":1,"pages":1}
					model.setpenalty(temp.getString("penalty"));
					model.setreason(temp.getString("reason"));
					model.settime(temp.getString("time"));

					//
					// "id": 14,
					// "title": "嘉兴货源",
					// "content": "备注",
					// "price": "1500元",
					// "tradetype": "货源",
					// "postime": "2016-05-19 10:19:02"
					//

					//
					//
					//
					modellist.add(model);

				}

				list_deduct_points.setAdapter(deductAdapter);
			}
			// newsAdapter.pre(newslist);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}

}
