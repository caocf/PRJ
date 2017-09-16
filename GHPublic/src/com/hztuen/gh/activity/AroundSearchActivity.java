package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener; 
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener; 
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView; 
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip; 
import com.amap.map3d.demo.util.AMapUtil;
import com.amap.map3d.demo.util.ToastUtil;
import com.gh.modol.AroundSearchHistory;
import com.gh.modol.TipSearch;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.AroundSerachHistoryAdapter;
import com.hztuen.gh.activity.Adapter.TipListAdapter;
/**
 * @author zzq
 * @DateTime 2016年7月12日 下午3:15:05
 * 周边搜索
 */
public class AroundSearchActivity extends Activity implements TextWatcher,
InputtipsListener
//,OnPoiSearchListener
{
	 
	private ImageView around_back;
	private TextView search_text;
	private TextView lishi;
	private ListView search_history;
	private ListView tiplist;
	private List<AroundSearchHistory> list = new ArrayList<AroundSearchHistory>();
	private List<TipSearch> tipsearch = new ArrayList<TipSearch>();
	private AroundSerachHistoryAdapter arr_adapter;
	//	private ArrayAdapter<String> arr_adapter;

	private TipListAdapter tipadapter;
	private AutoCompleteTextView search;
	private Button btn_search;
	private String city = "";
	private View footView;
	private TextView clear;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_around_search);
		around_back = (ImageView) findViewById(R.id.around_back);

		search_text = (TextView) findViewById(R.id.search_text);
		search_text.setVisibility(View.GONE);

		footView =  LinearLayout.inflate(getApplicationContext(), R.layout.searchlist_bottom, null);
		clear = (TextView) footView.findViewById(R.id.clear);
		clear.setOnClickListener(onclick);
		/*search部分设置可见，然后设置文字变化的监听事件*/
		search = (AutoCompleteTextView) findViewById(R.id.search);
		search.setVisibility(View.VISIBLE);
		search.addTextChangedListener(this);

		btn_search = (Button) findViewById(R.id.btn_search);
		btn_search.setVisibility(View.VISIBLE);

		search_history = (ListView) findViewById(R.id.search_history);
		search_history.setDividerHeight(0);
		search_history.addFooterView(footView);
		search_history.setOnItemClickListener(onitem);
		//		search_history.setVisibility(View.VISIBLE);

		tiplist = (ListView) findViewById(R.id.tiplist);
		tiplist.setDividerHeight(0);
		tipadapter = new TipListAdapter(getApplicationContext(), tipsearch);
		tiplist.setAdapter(tipadapter);
		tiplist.setOnItemClickListener(onitem);

		lishi = (TextView) findViewById(R.id.lishi);

		around_back.setOnClickListener(onclick);
		btn_search.setOnClickListener(onclick);
		arr_adapter = new AroundSerachHistoryAdapter(getApplicationContext(), list);
		search_history.setAdapter(arr_adapter);
		Intent intent = getIntent();
		city = intent.getStringExtra("city");
		getHistory();

	}
	public OnItemClickListener onitem = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			switch (parent.getId()) {
			case R.id.tiplist:
				expressItemClick(position);//position 代表你点的哪一个
				break;
			case R.id.search_history:
				expressItemClick2(position);//position 代表你点的哪一个
				break;
			default:
				break;
			}
		}
		private void expressItemClick(int position) {
			// TODO Auto-generated method stub
			String name = tipsearch.get(position).getName();
			search.setText(name);
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), SearchMapActivity.class);
			intent.putExtra("searchpoint", name);
			intent.putExtra("city",city);
			intent.putExtra("s", 50000000);
			intent.putExtra("tag", 0);
			startActivity(intent);
			save();
		}
		private void expressItemClick2(int position) {
			// TODO Auto-generated method stub
			String name = list.get(position).getAreaName();
			search.setText(name);
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), SearchMapActivity.class);
			intent.putExtra("searchpoint", name);
			intent.putExtra("city",city);
			intent.putExtra("s", 50000000);
			intent.putExtra("tag", 0);
			startActivity(intent);
			save();
		}
	};
	public OnClickListener onclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.around_back:
				finish();
				break;
			case R.id.btn_search:
				if(search.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "请输入您需要搜索的内容呢...", Toast.LENGTH_LONG).show();
				}else{
					Intent intent = new Intent();
					String ss = search.getText().toString();
					intent.putExtra("city", city);
					intent.putExtra("point", ss);
					intent.putExtra("poistyle", "");
					intent.setClass(getApplicationContext(), SerachListActivity.class);
					startActivity(intent);
					save();
				}
				break;
			case R.id.clear:
				cleanHistory();
				break;
			default:
				break;
			}
		}
	};

	public void getHistory(){
		// 初始化
		list.clear();
		// 获取搜索记录文件内容
		SharedPreferences sp = getSharedPreferences("search_history", 0);
		String history = sp.getString("history", "暂时没有搜索记录,");
		System.out.println(history);
		// 用逗号分割内容返回数组
		String[] history_arr = history.split(",");

		// 新建适配器，适配器数据为搜索历史文件内容
		//        arr_adapter = new ArrayAdapter<String>(this,
		//                android.R.layout.simple_dropdown_item_1line, history_arr);

		// 保留前50条数据
		if(history_arr.length>1){
			clear.setText("清空搜索历史");
		}else{
			clear.setText("暂无历史记录");
		}
		if (history_arr.length > 15) {
			String[] newArrays = new String[15];
			// 实现数组之间的复制
		}
		for(int i = history_arr.length-1; i >= 1 ; i--){
			AroundSearchHistory ash = new AroundSearchHistory();
			ash.setAreaName(history_arr[i]);
			list.add(ash);
		}
		arr_adapter.notifyDataSetChanged();
		// 设置适配器
		//        search.setAdapter(arr_adapter);
	}



	@Override
	public void onGetInputtips(List<Tip> tipList, int rCode) {
		if (rCode == 1000) {// 正确返回
			tipsearch.clear();
			for (int i = 0; i < tipList.size(); i++) {
				TipSearch tip = new TipSearch();
				tip.setName(tipList.get(i).getName());
				tipsearch.add(tip);
			}
			tipadapter.notifyDataSetChanged();
		} else {
			ToastUtil.showerror(AroundSearchActivity.this, rCode);
		}
	}
	/**
	 * 搜索部分文字变化监听事件
	 */
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		String newText = s.toString().trim();
		if(!newText.equals("")){
			tiplist.setVisibility(View.VISIBLE);
			lishi.setVisibility(View.GONE);
			search_history.setVisibility(View.GONE);
			if (!AMapUtil.IsEmptyOrNullString(newText)) {
				InputtipsQuery inputquery = new InputtipsQuery(newText, city);
				Inputtips inputTips = new Inputtips(AroundSearchActivity.this, inputquery);
				inputTips.setInputtipsListener(this);
				inputTips.requestInputtipsAsyn();
			}
		}else{
			lishi.setVisibility(View.VISIBLE);
			tiplist.setVisibility(View.GONE);
			search_history.setVisibility(View.VISIBLE);
			getHistory();
		}
	}
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}


	public void save() {
		// 获取搜索框信息
		String text = search.getText().toString();
		SharedPreferences mysp = getSharedPreferences("search_history", 0);
		String old_text = mysp.getString("history", "暂时没有搜索记录,");

		// 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
		StringBuilder builder = new StringBuilder(old_text);
		builder.append(text + ",");

		// 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
		if (!old_text.contains(text + ",")) {
			SharedPreferences.Editor myeditor = mysp.edit();
			myeditor.putString("history", builder.toString());
			myeditor.commit();
//			Toast.makeText(this, text + "添加成功", Toast.LENGTH_SHORT).show();
		} else {
//			Toast.makeText(this, text + "已存在", Toast.LENGTH_SHORT).show();
		}

	}

	//清除搜索记录
	public void cleanHistory(){
		SharedPreferences sp =getSharedPreferences("search_history",0);
		SharedPreferences.Editor editor=sp.edit();
		editor.clear();
		editor.commit();
		Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
		list.clear();
		getHistory();
		super.onDestroy();
	}	

}
