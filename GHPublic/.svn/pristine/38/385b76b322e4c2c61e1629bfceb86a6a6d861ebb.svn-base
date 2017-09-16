package com.hztuen.gh.activity;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.gh.modol.SearchListItem;
import com.hxkg.ghpublic.R;
import com.hxkg.mainfragment.NewsComment;
import com.hztuen.gh.activity.Adapter.SearchListAdapter;
import com.hztuen.lvyou.utils.SystemStatic;

/**
 * @author zzq
 * @DateTime 2016年7月12日 下午3:56:44
 */
public class SerachListActivity extends AbActivity implements 
OnPoiSearchListener{

	private List<SearchListItem> list = new ArrayList<SearchListItem>();
	private SearchListAdapter adapter;
	private ListView searchlist;
	private TextView search_back;
	private Button list_map;
	private CheckedTextView upanddown;
	private boolean flag = false;
	private PopupWindow popinstance;//弹出框
	private LinearLayout ll;
	private LinearLayout parent_view;
	private String city;
	private int s=5000;
	private PoiResult poiResult; // poi返回的结果
	private int currentPage = 0;// 当前页面，从0开始计数
	private PoiSearch.Query query;// Poi查询条件类
	private LatLonPoint lp ;// 
	private PoiSearch poiSearch;
	private List<PoiItem> poiItems;// poi数据
	private String keyWord = "";
	private String poistyle = "";
	private String searchpoint = "";
	private String citycode;
//	private LatLonPoint llp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_list);

		searchlist = (ListView) findViewById(R.id.searchlist);
		search_back = (TextView) findViewById(R.id.search_back);
		upanddown = (CheckedTextView) findViewById(R.id.upanddown);
		parent_view = (LinearLayout) findViewById(R.id.parent_view);
		list_map = (Button) findViewById(R.id.list_map);
		ll = (LinearLayout) findViewById(R.id.ll);
		adapter = new SearchListAdapter(getApplicationContext(), list);
		searchlist.setAdapter(adapter);
		searchlist.setDividerHeight(0);
		parent_view.setOnClickListener(onclick);
		ll.setOnClickListener(onclick);
		search_back.setOnClickListener(onclick);
		list_map.setOnClickListener(onclick);

		searchlist.setOnItemClickListener(onitem);

		Intent intent = getIntent();
		searchpoint = intent.getStringExtra("point");
		city = intent.getStringExtra("city");
		lp = SystemStatic.lp;

		init();
	}
	/**
	 * listView 的单列点击监听事件
	 */
	public OnItemClickListener onitem = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			switch (parent.getId()){
			case R.id.searchlist:
				expressItemClick(position);//position 代表你点的哪一个
				break;
			}
		}
	};
	private void expressItemClick(int position) {
		// TODO Auto-generated method stub
		SystemStatic.llp = list.get(position).getLlp();
		Intent intent = new Intent();
		intent.putExtra("searchpoint", list.get(position).getPoint());
		intent.putExtra("city", city);
		intent.putExtra("distance", list.get(position).getDistance2());
//		intent.putExtra("s",s);
		intent.putExtra("poistyle", poistyle);
		intent.putExtra("citycode", list.get(position).getCitycode());
		intent.putExtra("phone", list.get(position).getPhone());
		intent.putExtra("tag", 1);
		intent.setClass(getApplicationContext(), SearchMapActivity.class);
		startActivity(intent);
		//看你需不需要返回当前界面，如果点返回需要返回到当前界面，就不用这个
	}
	public OnClickListener onclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.search_back:
				finish();
				break;
			case R.id.ll:
				if(SwitchChanage()){
					initpopupinstance();
					upanddown.setChecked(true);
				}else{
					popinstance.dismiss();
					upanddown.setChecked(false);
				}
				break;
			case R.id.s500:
				s=500;
				init();
				popinstance.dismiss();
				break;
			case R.id.s1000:
				s=1000;
				init();
				popinstance.dismiss();
				break;
			case R.id.s2000:
				s=2000;
				init();
				popinstance.dismiss();
				break;
			case R.id.s5000:
				s=5000;
				init();
				popinstance.dismiss();
				break;
			case R.id.ss:
				s=5000000;
				init();
				popinstance.dismiss();
				break;
			case R.id.sp:
				s=500000000;
				init();
				popinstance.dismiss();
				break;
			case R.id.list_map:
				Intent intent = new Intent();
				intent.putExtra("searchpoint", searchpoint);
				intent.putExtra("city", city);
				intent.putExtra("s",s);
				intent.putExtra("poistyle",poistyle);
				intent.putExtra("tag", 0);
				intent.setClass(getApplicationContext(), SearchMapActivity.class);
				startActivity(intent);
			default:
				break;
			}
		}
	};
	public void initpopupinstance(){
		View contentView = getLayoutInflater().inflate(R.layout.pop_search_point, null);
		ListView parent = (ListView) this.findViewById(R.id.searchlist);//父窗口view  
		TextView s500 = (TextView) contentView.findViewById(R.id.s500);
		TextView s1000 = (TextView) contentView.findViewById(R.id.s1000);
		TextView s2000 = (TextView) contentView.findViewById(R.id.s2000);
		TextView s5000 = (TextView) contentView.findViewById(R.id.s5000);
		TextView ss = (TextView) contentView.findViewById(R.id.ss);
		TextView sp = (TextView) contentView.findViewById(R.id.sp);
		popinstance = new PopupWindow(contentView, parent.getWidth(), ViewGroup.LayoutParams.FILL_PARENT);
		popinstance.setFocusable(false);
		popinstance.setOutsideTouchable(true);
		final LinearLayout pop_parent_view = (LinearLayout) contentView.findViewById(R.id.pop_parent_view);
		pop_parent_view.getBackground().setAlpha(100);
		pop_parent_view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popinstance.dismiss();
			}
		});
		s500.setOnClickListener(onclick);
		s1000.setOnClickListener(onclick);
		s2000.setOnClickListener(onclick);
		s5000.setOnClickListener(onclick);
		ss.setOnClickListener(onclick);
		sp.setOnClickListener(onclick);
		popinstance.showAsDropDown(ll, 0, 0);
	}
	public void init(){
		AbDialogUtil.showProgressDialog(SerachListActivity.this, 0,
				"搜索中...");
		list.clear();
		if(searchpoint.equals("码头")==true){
			search_back.setText("码头");
			keyWord = "码头";
			poistyle = "港口码头";
			doSearchQuery();
		}else if(searchpoint.equals("加油站")==true){
			search_back.setText("加油站");
			keyWord = "加油站";
			poistyle = "加油站";
			doSearchQuery();
		}else if(searchpoint.equals("锚泊区")==true){
			search_back.setText("锚泊区");
			keyWord = "锚泊区";
			doSearchQuery();
		}else if(searchpoint.equals("船厂")==true){
			search_back.setText("船厂");
			keyWord = "船厂";
			poistyle = "工厂";
			doSearchQuery();
		}else if(searchpoint.equals("桥梁")==true){
			search_back.setText("桥梁");
			keyWord = "桥梁";
			doSearchQuery();
		}else if(searchpoint.equals("港航站")==true){
			search_back.setText("港航站");
			keyWord = "港航站";
			doSearchQuery();
		}else if(searchpoint.equals("水上服务")==true){
			search_back.setText("水上服务");
			keyWord = "水上服务";
			doSearchQuery();
		}
		else{
			search_back.setText("搜索");
			keyWord = searchpoint;
			doSearchQuery();
		}
	}
	/*
	 * 控制upordown
	 * */
	public boolean SwitchChanage(){
		if(flag){
			flag = false;
		}else{
			flag = true;
		}
		return flag;		
	}
	/**
	 * 开始进行poi搜索
	 */
	protected void doSearchQuery() {
		currentPage = 0;
		query = new PoiSearch.Query(keyWord, poistyle, city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		query.setPageSize(20);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页
		if (lp != null) {
			poiSearch = new PoiSearch(this, query);
			poiSearch.setOnPoiSearchListener(this);
			poiSearch.setBound(new SearchBound(lp, s, true));//
			// 设置搜索区域为以lp点为圆心，其周围5000米范围
			poiSearch.searchPOIAsyn();// 异步搜索
		}
	}
	@Override
	public void onPoiItemSearched(PoiItem arg0, int arg1) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onPoiSearched(PoiResult result, int rcode) {
		// TODO Auto-generated method stub
		if (rcode == 1000) {
			if (result != null && result.getQuery() != null) {// 搜索poi的结果
				if (result.getQuery().equals(query)) {// 是否是同一条
					poiResult = result;
					poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
					for(int i= 0;i<poiItems.size();i++){
						SearchListItem sli = new SearchListItem();
						sli.setPoint(poiItems.get(i).getTitle());
						double distance = poiItems.get(i).getDistance();
						double ss = poiItems.get(i).getDistance();
						if(distance>=1000){
							sli.setDistance("距您"+format(ss/1000)+"千米");
						}else{
							sli.setDistance(distance+"米");
						}
						sli.setCitycode(poiItems.get(i).getCityCode());
						sli.setLlp(poiItems.get(i).getLatLonPoint());
						sli.setPhone(poiItems.get(i).getTel());
						sli.setDistance2(ss);
						list.add(sli);
					}
					adapter.notifyDataSetChanged();
					if(poiItems.size()==0){
						Toast.makeText(getApplicationContext(), "当前搜索范围内暂无数据哟...", Toast.LENGTH_SHORT).show();
					}
					AbDialogUtil.removeDialog(SerachListActivity.this);
				}
			} else {
				Toast.makeText(getApplicationContext(), "something is wrong...", Toast.LENGTH_SHORT).show();
			}
		}
	}
	/**
	 * 保留小数点后两位
	 * */
	 public static String format(double value) {  
		  
	        NumberFormat nf = NumberFormat.getNumberInstance();  
	        nf.setMaximumFractionDigits(2);  
	        /* 
	         * setMinimumFractionDigits设置成2 
	         *  
	         * 如果不这么做，那么当value的值是100.00的时候返回100 
	         *  
	         * 而不是100.00 
	         */  
	        nf.setMinimumFractionDigits(2);  
	        nf.setRoundingMode(RoundingMode.HALF_UP);  
	        /* 
	         * 如果想输出的格式用逗号隔开，可以设置成true 
	         */  
	        nf.setGroupingUsed(false);  
	        return nf.format(value);  
	    }  
}
