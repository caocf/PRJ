package com.huzhouport.integratedquery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.ClearEditText;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
public class ShipSearchNinecode extends Activity 
{
	ImageButton tv_back, tv_button;
	ClearEditText etext;
	private Query query = new Query();
	Map<String, Object> paramsDate = new HashMap<String, Object>();
	private int msg;
	private String searchType;
	User user;
	private String shipname;
	private ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 匹配的船舶名称列表
	private ListView lv;
	private TextView prompt;
	private SimpleAdapter adapter;
	private int cpage = 1, maxpage;
	private View moreView; // 加载更多页面
	private Handler handler = new Handler(new Handler.Callback() {
		// 当发出信息时就执行Handler
		public boolean handleMessage(Message msg) {
			if (msg.what == 1) {
				Toast.makeText(ShipSearchNinecode.this, "不存在该船名", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == 2) {
				Toast.makeText(ShipSearchNinecode.this, "不存在该船名或该船没有违章信息",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == 3) {
				Toast.makeText(ShipSearchNinecode.this, "该接口还未开通", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == 4) {
				Toast.makeText(ShipSearchNinecode.this, "无法连接服务端", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == 5) {
				Toast.makeText(ShipSearchNinecode.this, "不存在该航运企业", Toast.LENGTH_SHORT)
						.show();
			}
			return false;
		}
	});
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_ninecode);
		Intent intent = getIntent();
		searchType = intent.getStringExtra("searchType");
		user=(User)intent.getSerializableExtra("User");
		prompt= (TextView) findViewById(R.id.BSprompt);
		lv = (ListView) findViewById(R.id.BSListview);
		TextView tv_title = (TextView) findViewById(R.id.basesourch_title);
		tv_title.setText("船舶九位码");
		tv_back = (ImageButton) findViewById(R.id.basesourch_back);
		tv_button = (ImageButton) findViewById(R.id.BSbutton1);
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		etext = (ClearEditText) findViewById(R.id.basesourch_edit01);
		showListView();// 匹配的船舶名称
		etext.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (etext.getText().toString().length() > 2) {
					//3个中文以上进行搜索
					cpage=1;
					System.out.println("cpage====="+cpage);
					new GetShipNameList().execute();
				}else if (list.size()==0){
					prompt.setVisibility(View.GONE);
					lv.setVisibility(View.GONE);
					list.clear();// 匹配的船舶名称列表
					adapter.notifyDataSetChanged();
				}
			}
		});
		tv_back.setOnClickListener(new ImageButton_Back());
		tv_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SearchByNineCode();

			}
		});
	}
	class ImageButton_Back implements OnClickListener {
		@Override
		public void onClick(View v) {
			finish();
		}

	}
	private void SearchByNineCode() {

		if (validate()) {
			ListTask task = new ListTask(this); // 异步
			task.execute();
		}
	}
	// 验证输入
		private boolean validate() {
			if (etext.getText().toString() == null || etext.getText().length() == 0) {
				Toast.makeText(ShipSearchNinecode.this, "搜索关键字不能为空", Toast.LENGTH_SHORT)
						.show();
				return false;
			}
			return true;
		}
		class ListTask extends AsyncTask<String, Integer, String>
		{
			ProgressDialog pDialog = null;

			public ListTask(Context context)
			{
				pDialog = new WaitingDialog().createDefaultProgressDialog(context,
						ListTask.this);
				pDialog.show();
			}
			protected String doInBackground(String... params)
			{
				if (isCancelled())
					return null;// 取消异步
				HttpFileUpTool hfu = new HttpFileUpTool();
				String actionUrl = HttpUtil.BASE_URL + "queryShipnameByShipname";
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("ais.name", etext.getText().toString().trim());
				String resultString="";
				try {
					resultString=hfu.post(actionUrl, map);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//return query.ShipSearchNicecode(etext.getText().toString().trim());
				return resultString;
			}

			@Override
			protected void onPostExecute(String result)
			{
				if (pDialog != null)
					pDialog.dismiss();
                 JSONTokener jsonParser_User = new JSONTokener(result);
         		try
         		{
         			JSONObject data = (JSONObject) jsonParser_User.nextValue();
         			int resultcode = data.getInt("resultCode");
         			if(resultcode==0){//有数据，无修改数据
         				JSONObject ship=(JSONObject)data.getJSONObject("result");
         				Intent intent=new Intent(ShipSearchNinecode.this,ShipSearchNinecodeSee.class);
         				intent.putExtra("shipnum",ship.getString("aisnum"));
         				intent.putExtra("shipname",etext.getText().toString().trim());
         				intent.putExtra("searchType", searchType);
        				intent.putExtra("User", user);
        				intent.putExtra("type", "0");
         				startActivity(intent);
         			}else if(resultcode==-4){//有数据，也有修改数据
         				JSONObject ais=(JSONObject)data.getJSONObject("editResult");
         				JSONObject ship=(JSONObject)data.getJSONObject("result");
         				Intent intent=new Intent(ShipSearchNinecode.this,ShipSearchNinecodeSee.class);
         				intent.putExtra("shipnum",ship.getString("aisnum"));
         				intent.putExtra("shipname",etext.getText().toString().trim());
         				intent.putExtra("searchType", searchType);
        				intent.putExtra("User", user);
        				intent.putExtra("type", "4");
        				intent.putExtra("shipnum1",ais.getString("num"));
         				intent.putExtra("shipname1",ais.getString("name"));
         				intent.putExtra("picpath",ais.getString("picpath"));
         				intent.putExtra("picname",ais.getString("picname"));
         				startActivity(intent);
         			}else if(resultcode==-3){//有修改数据 无数据
         				JSONObject ais=(JSONObject)data.getJSONObject("editResult");
         				Intent intent=new Intent(ShipSearchNinecode.this,ShipSearchNinecodeSee.class);
        				intent.putExtra("type", "3");
        				intent.putExtra("shipnum1",ais.getString("num"));
         				intent.putExtra("shipname1",ais.getString("name"));
         				intent.putExtra("picpath",ais.getString("picpath"));
         				intent.putExtra("picname",ais.getString("picname"));
         				startActivity(intent);
         			}else{//无数据 都无数据
         				Intent intent=new Intent(ShipSearchNinecode.this,ShipSearchNinecodeAdd.class);
         				intent.putExtra("shipname",etext.getText().toString().trim());
         				startActivity(intent);
         			}
         		}catch(Exception e){
         			e.printStackTrace();
         			Toast.makeText(ShipSearchNinecode.this, "网络异常",
        					Toast.LENGTH_SHORT).show();
         		}
				super.onPostExecute(result);
			}
		}
		
		private void SearchByShipName() {
				new Thread() {
					public void run() {
							Search(1);
						handler.sendEmptyMessage(msg);
					}
				}.start();
			}
private void Search(int iMethod) {
	paramsDate.put("cbname", shipname);
	paramsDate.put("method", iMethod);
	paramsDate.put("scape", 1);
	HttpFileUpTool hfu = new HttpFileUpTool();
	String actionUrl = HttpUtil.BASE_URL + "GetAndPostDate";
	try {
		String result = hfu.post(actionUrl, paramsDate);
			if (query.CheckShipResult(result)) {
				Intent intent;
				intent = new Intent(ShipSearchNinecode.this, Search_tabs.class);
				intent.putExtra("title", shipname);
				intent.putExtra("shipnum", etext.getText().toString().trim());
				intent.putExtra("searchType", searchType);
				intent.putExtra("result", result);
				intent.putExtra("User", user);
				startActivity(intent);
				msg = 0;
			} else {
				msg = 1;
			}
	} catch (IOException e) {
		msg = 4;
		e.printStackTrace();
	} catch (Exception e) {
		msg = 4;
		e.printStackTrace();
	}
}
public void showListView() {
	list = new ArrayList<Map<String, Object>>();
	adapter = new SimpleAdapter(ShipSearchNinecode.this, list,
			R.layout.search_item, new String[] { "name" },
			new int[] { R.id.search_item_name });
	lv.addFooterView(moreView); // 添加底部view，一定要在setAdapter之前添加，否则会报错。
	lv.setAdapter(adapter);
	moreView.setVisibility(View.GONE);
	lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			etext.setText(list.get(arg2).get("name").toString());
			prompt.setVisibility(View.GONE);
			lv.setVisibility(View.GONE);
		}

	});
	lv.setOnScrollListener(new OnScrollListener() {

		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// 当不滚动时
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {

				// 判断滚动到底部
				if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
					// 然后 经行一些业务操作
					if (cpage < maxpage) {
						moreView.findViewById(R.id.progressBar2)
								.setVisibility(View.VISIBLE);
						((TextView) moreView
								.findViewById(R.id.loadmore_text))
								.setText(R.string.more);
						cpage += 1;
						new GetShipNameList().execute();
					}
				}
			}

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		}
	});
}
class GetShipNameList extends AsyncTask<Void, Void, String> {

	@Override
	protected String doInBackground(Void... params) {
		HttpFileUpTool hft = new HttpFileUpTool();
		Map<String, Object> p = new HashMap<String, Object>();
		String result = null;
		try {
			p.put("shipName", etext.getText().toString());
			result = hft.post(HttpUtil.BASE_URL
					+ "SearchShipName", p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		if(result!=null){
		showShipNameList(result);
		adapter.notifyDataSetChanged();
		
		moreView.findViewById(R.id.progressBar2).setVisibility(View.GONE);
		((TextView) moreView.findViewById(R.id.loadmore_text))
				.setText(R.string.moreing);
		if(list.size()>0){
			prompt.setVisibility(View.VISIBLE);
			lv.setVisibility(View.VISIBLE);
		}
		if (cpage >= maxpage) {
			moreView.setVisibility(View.GONE);
			lv.removeFooterView(moreView); // 移除底部视图
		}else{
			moreView.setVisibility(View.VISIBLE);
			lv.addFooterView(moreView); 
			
		}
		}else{
			prompt.setVisibility(View.GONE);
			lv.setVisibility(View.GONE);
		}
		super.onPostExecute(result);
	}

}
public void showShipNameList(String result) {
	JSONTokener jsonParser = new JSONTokener(result);
	JSONObject data;
	try {
		data = (JSONObject) jsonParser.nextValue();
		maxpage = data.getInt("totalPage");
		if(cpage==1){
			list.clear();// 匹配的船舶名称列表
		}
		JSONArray jsonArray = data.getJSONArray("list");
		for (int i = 0; i < jsonArray.length(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject shipName = (JSONObject) jsonArray.getJSONObject(i);
			map.put("name", shipName.getString("shipName"));
			list.add(map);
		}
	} catch (Exception e) {
		/*Toast.makeText(ShipSearch.this, "没有搜索到相关数据", Toast.LENGTH_SHORT)
				.show();*/
		e.printStackTrace();
	}
}
}
