package com.hztuen.gh.activity;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.gh.modol.SearchListItem;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.SearchListAdapter;
import com.hztuen.lvyou.utils.SystemStatic;

/**
 * @author zzq
 * 搜索地图列表
 */
public class AroundMapListActivity extends AbActivity implements OnPoiSearchListener{
	@AbIocView(id = R.id.search_back ,click = "click") TextView search_back;
	@AbIocView(id = R.id.searchlist) ListView searchlist;
	private String searchpoint = "";
	private String poistyle = "";
	private PoiResult poiResult; // poi返回的结果
	private int currentPage = 0;// 当前页面，从0开始计数
	private PoiSearch.Query query;// Poi查询条件类
	private LatLonPoint lp ;// 
	private PoiSearch poiSearch;
	private List<PoiItem> poiItems;// poi数据
	private int s=5000;

	private List<SearchListItem> list = new ArrayList<SearchListItem>();
	private SearchListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aroundmaplist);
		initView();
		initDate();
	}


	private void initView() {
		// TODO Auto-generated method stub
		searchlist.setDividerHeight(0);
		adapter = new SearchListAdapter(getApplicationContext(), list);
		searchlist.setAdapter(adapter);
		searchlist.setOnItemClickListener(onitem);
	}


	private void initDate() {
		// TODO Auto-generated method stub
		Intent mIntent = getIntent();
		searchpoint = mIntent.getStringExtra("searchpoint");
		poistyle = mIntent.getStringExtra("poistyle");//如果是直接搜索获取的数据可能会出现为空 null
		s = mIntent.getIntExtra("s", 5000);
		search_back.setText(searchpoint);
		lp = SystemStatic.lp;
		if(null != poistyle){
			doSearchQuery();
		}else{
			doSearchQuery2();
		}

	}


	public void click(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search_back:
			finish();
			break;

		default:
			break;
		}
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
		intent.putExtra("city", SystemStatic.city);
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
	/**
	 * 开始进行poi搜索
	 */
	protected void doSearchQuery() {
		currentPage = 0;
		query = new PoiSearch.Query(searchpoint, poistyle, SystemStatic.city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
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
	protected void doSearchQuery2() {
		currentPage = 0;
		query = new PoiSearch.Query(searchpoint, "", SystemStatic.city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
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
						Toast.makeText(getApplicationContext(), "当前搜索暂无数据哟...", Toast.LENGTH_SHORT).show();
					}
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
