package com.huzhouport.integratedquery;

/*@author 沈丹丹
 * */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.hxkg.channel.HttpRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.util.ClearEditText;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class ShipSearch extends Activity 
{
	TextView tv_title,prompt;
	ImageButton tv_back, tv_button;
	ClearEditText etext;
	private int iST;
	private ProgressDialog pd;
	private Query query = new Query();
	private String searchType;
	private int msg;
	Map<String, Object> paramsDate = new HashMap<String, Object>();
	private ListView lv;
	private View moreView; // 加载更多页面
	private SimpleAdapter adapter;
	private int cpage = 1, maxpage;
	User user;
	
	private ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 匹配的船舶名称列表

	private Handler handler = new Handler(new Handler.Callback() 
	{
		// 当发出信息时就执行Handler
		public boolean handleMessage(Message msg) 
		{
			pd.dismiss();
			if (msg.what == 1) {
				Toast.makeText(ShipSearch.this, "不存在该船名", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == 2) {
				Toast.makeText(ShipSearch.this, "不存在该船名或该船没有违章信息",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == 3) {
				Toast.makeText(ShipSearch.this, "该接口还未开通", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == 4) {
				Toast.makeText(ShipSearch.this, "无法连接服务端", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == 5) {
				Toast.makeText(ShipSearch.this, "不存在该航运企业", Toast.LENGTH_SHORT)
						.show();
			}
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		Intent intent = getIntent();
		searchType = intent.getStringExtra("searchType"); 
		iST = Integer.parseInt(searchType);
		user=(User)intent.getSerializableExtra("User");

		tv_title = (TextView) findViewById(R.id.basesourch_title);
		tv_back = (ImageButton) findViewById(R.id.basesourch_back);
		tv_button = (ImageButton) findViewById(R.id.BSbutton1);
		etext = (ClearEditText) findViewById(R.id.basesourch_edit01);
		prompt= (TextView) findViewById(R.id.BSprompt);
		lv = (ListView) findViewById(R.id.BSListview);
		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		switch (iST) 
		{
		case 0:
			tv_title.setText(getResources().getString(R.string.bs_menulist01));
			break;
		case 1:
			tv_title.setText(getResources().getString(R.string.bs_menulist02));
			break;
		case 2:
			tv_title.setText(getResources().getString(R.string.bs_menulist03));
			break;
		case 3:
			tv_title.setText(getResources().getString(R.string.bs_menulist04));
			break;
		case 4:
			tv_title.setText(getResources().getString(R.string.bs_menulist05));
			break;
		case 5:
			tv_title.setText(getResources().getString(R.string.bs_menulist06));
			break;
		case 6:
			tv_title.setText(getResources().getString(R.string.bs_menulist08));
			etext.setHint(R.string.mtzyxx_notice);
			break;
		case 7:
			tv_title.setText(getResources().getString(R.string.bs_menulist09));
			etext.setHint(R.string.hyqyxx_notice);
			break;
		case 8:
			tv_title.setText(getResources().getString(R.string.bs_menulist10));
			etext.setHint(R.string.mtqyxx_notice);
			break;
		default:
			break;
		}
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
				if(iST<6){
				if (etext.getText().toString().length() > 2) {
					//3个中文以上进行搜索
					cpage=1;
					new GetShipNameList().execute();
				}else if (list.size()==0){
					prompt.setVisibility(View.GONE);
					lv.setVisibility(View.GONE);
					list.clear();// 匹配的船舶名称列表
					adapter.notifyDataSetChanged();
				}
				}
			}
		});
		tv_back.setOnClickListener(new ImageButton_Back());
		tv_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SearchByShipName(etext.getText().toString());

			}
		});
	}

	public void showListView() {
		list = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(ShipSearch.this, list,
				R.layout.search_item, new String[] { "name" },
				new int[] { R.id.search_item_name });
		lv.addFooterView(moreView); // 添加底部view，一定要在setAdapter之前添加，否则会报错。
		lv.setAdapter(adapter);
		moreView.setVisibility(View.GONE);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
			{
				etext.setText(list.get(arg2).get("name").toString());
				SearchByShipName(etext.getText().toString());
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

	public void showShipNameList(String result) 
	{
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

	class ImageButton_Back implements OnClickListener {
		@Override
		public void onClick(View v) {
			finish();
		}

	}

	@SuppressWarnings("deprecation")
	private void SearchByShipName(final String tip) 
	{
		if (validate()) {
			pd = new ProgressDialog(ShipSearch.this);
			pd.setTitle("搜索");
			pd.setMessage("搜索中·····");
			pd.setCancelable(true);
			pd.setButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (pd != null)
						pd.dismiss();
					handler.sendEmptyMessage(6);

				}
			});
			pd.show();
			new Thread() {
				public void run() {
					switch (iST) {
					case 0:// 船舶基本信息
						//Search(1);
						AISSearch(etext.getText().toString(),1);
						break;
					case 1:// 船舶证书信息
						AISSearch(etext.getText().toString(),2);
						break;
					case 2:// 船舶违章信息
						AISSearch(etext.getText().toString(),5);
						break;
					case 3:// 船舶签证信息
						AISSearch(etext.getText().toString(),6);
						break;
					case 4:// 船舶缴费信息
						AISSearch(etext.getText().toString(),3);
						break;
					case 5:// 船舶船检信息
						AISSearch(etext.getText().toString(),4);
						break;
					case 6:// 码头作业信息
						msg = 3;
						break;
					case 7:// 水运企业信息
						SearchCompane();
						break;
					case 8:// 码头企业信息
						msg = 3;
						break;
					default:
						break;
					}

					handler.sendEmptyMessage(msg);
				}
			}.start();

		}
	}
	
	private void AISSearch(String ais,final int index) 
	{
		String partten="[0-9]*";
		if(ais.matches(partten))
		{
			Looper.prepare();
			HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult() 
			{			
				@Override
				public void onSuccess(String result) 
				{			
					
				}
				
				@Override
				public void onError(int httpcode) 
				{				
					System.out.println(httpcode);					
				}
			});
			Map<String, Object> map=new HashMap<>();
			map.put("aisNo", etext.getText().toString());
			String result=httpRequest.postInThread(HttpUtil.BASE_URL+"queryShipnameByAisNo", map);
			try
			{
				JSONObject object=new JSONObject(result);
				JSONObject resultObject=object.getJSONObject("result");
				String shipName=resultObject.getString("shipname");
				Search(index,shipName);
			}catch(Exception exception)
			{
				Toast.makeText(this, "无船舶信息", Toast.LENGTH_SHORT).show();	
			}
			
					
			
		}else 
		{
			Search(index,etext.getText().toString());
		}
		
		
	}

	// 验证输入
	private boolean validate() {
		if (etext.getText().toString() == null || etext.getText().length() == 0) {
			Toast.makeText(ShipSearch.this, "搜索关键字不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	private void Search(int iMethod,String name) 
	{
		paramsDate.put("cbname",name );
		paramsDate.put("method", iMethod);
		paramsDate.put("scape", 1);
		HttpFileUpTool hfu = new HttpFileUpTool();
		String actionUrl = HttpUtil.BASE_URL + "GetAndPostDate";
		try {
			String result = hfu.post(actionUrl, paramsDate);
			if (iMethod != 5) {
				if (query.CheckShipResult(result)) {
					Intent intent;
					intent = new Intent(ShipSearch.this, Search_tabs.class);
					intent.putExtra("title", etext.getText().toString());
					intent.putExtra("searchType", searchType);
					intent.putExtra("result", result);
					intent.putExtra("User", user);
					startActivity(intent);
					msg = 0;
				} else {
					msg = 1;
				}
			} else {

				if (CheckShipNameByWZ()) {
					Intent intent;
					intent = new Intent(ShipSearch.this, Search_tabs.class);
					intent.putExtra("title", etext.getText().toString());
					intent.putExtra("searchType", searchType);
					intent.putExtra("result", result);
					intent.putExtra("User", user);
					startActivity(intent);
					msg = 0;
				} else {
					msg = 1;
				}

			}

		} catch (IOException e) {
			msg = 4;
			e.printStackTrace();
		} catch (Exception e) {
			msg = 4;
			e.printStackTrace();
		}

	}

	private Boolean CheckShipNameByWZ() 
	{
		paramsDate.put("cbname", etext.getText().toString());
		paramsDate.put("method", 1);
		paramsDate.put("scape", 1);
		HttpFileUpTool hfu = new HttpFileUpTool();
		String actionUrl = HttpUtil.BASE_URL + "GetAndPostDate";
		try {
			String result = hfu.post(actionUrl, paramsDate);

			if (query.CheckShipResult(result)) {
				return true;
			} else {
				return false;
			}

		}

		catch (Exception e) {

			e.printStackTrace();
			return false;
		}

	}

	private void SearchCompane() {

		paramsDate.put("cbname", etext.getText().toString());
		HttpFileUpTool hfu = new HttpFileUpTool();
		String actionUrl = HttpUtil.BASE_URL + "GetShipingCompanies";
		try {
			String result = hfu.post(actionUrl, paramsDate);
			if (query.CheckCompanResult(result)) {
				Intent intent;
				intent = new Intent(ShipSearch.this, HYQYXX.class);
				intent.putExtra("result", result);
				
				startActivity(intent);
				if(user!=null)
				new Log(user.getName(), "查看航运企业", GlobalVar.LOGSEE, "").execute();
				
				msg = 0;
			} else {
				msg = 5;
			}

		} catch (Exception e) {
			msg = 4;
			e.printStackTrace();
		}

	}

}
