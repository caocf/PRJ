package com.hxkg.mainfragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.gh.modol.News;
import com.hxkg.ghpublic.CenApplication;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.ActivityNewsFrom;
import com.hztuen.gh.contact.contants;
import com.hztuen.gh.widge.HomeSecondHeaderView;
import com.hztuen.gh.widge.NewListAdapter;

public class MainFramentNewsActivity extends Activity implements onResult,OnRefreshListener2<ListView>
{
	private TextView newslistback;
	private Button newsfrom;
	private PullToRefreshListView newslistview;
	private List<News> newslist = new ArrayList<News>();
	private NewListAdapter newsAdapter;

	private HomeSecondHeaderView importantnews;
	private HomeSecondHeaderView vocationstate;
	private HomeSecondHeaderView mediaforcus;
	ImageView img[];
	int Type;
	int Region;
	int model;
	SQLiteDatabase db;
	SharedPreferences preferences;
	
	
	final String TITLES[]={"港航新闻","通知公告"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newlist);		
		
		preferences= getSharedPreferences("NewsData", 0);
		//地区字段初始化
		Region=preferences.getInt("Region", 0);//默认浙江省
		//地区选择按钮
		newsfrom = (Button) findViewById(R.id.newsfrom);
		newsfrom.setText(ActivityNewsFrom.REGIONS[Region]);
		newsfrom.setOnClickListener(onclicklistener);
		//新闻类别 0.新闻  1.公告
		model=getIntent().getIntExtra("model", 0);
		newslistback = (TextView) findViewById(R.id.newslist_back);
		newslistback.setText(TITLES[model]);//页面标题	
		//要闻or通告
		importantnews = (HomeSecondHeaderView) findViewById(R.id.importantnews);
		importantnews.setType(model, 0);//类别，标题
		importantnews.setIsClick(true);//默认第一个标签选中
		//动态or航行		
		vocationstate = (HomeSecondHeaderView) findViewById(R.id.vocationstate);	
		vocationstate.setType(model, 1);//类别，标题
		//媒体or预警
		mediaforcus = (HomeSecondHeaderView) findViewById(R.id.mediaforcus);		
		mediaforcus.setType(model, 2);//类别，标题
		//新闻二级标题单机事件
		importantnews.setOnClickListener(onclicklistener);
		vocationstate.setOnClickListener(onclicklistener);
		mediaforcus.setOnClickListener(onclicklistener);
		//新闻类别,默认第一个标签
		Type=importantnews.getType();			
		//新闻列表
		newslistview = (PullToRefreshListView) findViewById(R.id.newslistview);		
		newsAdapter = new NewListAdapter(MainFramentNewsActivity.this, newslist);
		newslistview.setAdapter(newsAdapter);		
		newslistview.setOnItemClickListener(onitemclicklistener);
		newslistview.setOnRefreshListener(this);//下上拉刷新监听
		newslistview.setMode(Mode.PULL_UP_TO_REFRESH);//刷新模式
		
		//刷新样式
        /*ILoadingLayout proxyPullDown = newslistview.getLoadingLayoutProxy(true,false);
         * proxyPullDown.setPullLabel("下拉刷新");
        proxyPullDown.setReleaseLabel("放开以刷新");
        proxyPullDown.setRefreshingLabel("正在玩命地加载....");
        proxyPullDown.setLoadingDrawable(getResources().getDrawable(R.drawable.ic_launcher));*/
        ILoadingLayout proxyPullUp = newslistview.getLoadingLayoutProxy(false,true);
        proxyPullUp.setPullLabel("上拉加载更多");
        proxyPullUp.setReleaseLabel("放开以加载");
        proxyPullUp.setRefreshingLabel("加载中....");
        //proxyPullUp.setLoadingDrawable(getResources().getDrawable(android.R.drawable.ic_menu_camera));
		
		//获取已读数据库
		db=((CenApplication)getApplication()).getData();		
		img=new ImageView[]{(ImageView) importantnews.findViewById(R.id.img),
				(ImageView) vocationstate.findViewById(R.id.img),(ImageView) mediaforcus.findViewById(R.id.img)};
		//初始化数据
		newslist.clear();
		RefrashTask(1);
	}
	
	@Override
	public void onResume()
	{			
		super.onResume();
		//刷新未读标记
		newsAdapter.notifyDataSetInvalidated();
		initTab();
	}
	
	public void onBack(View v)
	{
		finish();
	}
	
	
	public OnItemClickListener onitemclicklistener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{			
			String newsid = newslist.get(position-1).getId();
			Intent intent = new Intent();
			intent.putExtra("infonewsid", newsid);
			intent.setClass(MainFramentNewsActivity.this,FramentNewsInfo.class);
			startActivity(intent);			
		}
		
	};
	//标签点击事件
	public OnClickListener onclicklistener = new OnClickListener()
	{
		public void onClick(View v) 
		{			
			switch (v.getId())
			{
			case R.id.importantnews:
				Type=importantnews.getType();
				newslist.clear();//清空重新获取
				RefrashTask(1);	
				importantnews.setIsClick(true);
				vocationstate.setIsClick(false);
				mediaforcus.setIsClick(false);
				break;
			case R.id.vocationstate:
				Type=vocationstate.getType();
				newslist.clear();//清空重新获取
				RefrashTask(1);
				importantnews.setIsClick(false);
				vocationstate.setIsClick(true);
				mediaforcus.setIsClick(false);
				break;
			case R.id.mediaforcus:
				Type=mediaforcus.getType();
				newslist.clear();//清空重新获取
				RefrashTask(1);
				importantnews.setIsClick(false);
				vocationstate.setIsClick(false);
				mediaforcus.setIsClick(true);
				break;
			case R.id.newsfrom://选择地区
				Intent intent = new Intent(MainFramentNewsActivity.this,ActivityNewsFrom.class);
				intent.putExtra("city", Region);
				startActivityForResult(intent, 1000);
				break;
			default:
				break;
			}
		}		
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{	
		int code=data.getIntExtra("regioncode", 0);
		if(code==100)
		{	
			return;
		}
		Region=code;
		newsfrom.setText(ActivityNewsFrom.REGIONS[code]);	
		newslist.clear();
		RefrashTask(1);		
	}
	
	@Override
	protected void onDestroy() 
	{
		
		super.onDestroy();
	}
	
	@Override
	protected void onPause() 
	{
		SharedPreferences.Editor editor=preferences.edit();
		editor.putInt("Region", Region);
		editor.commit();
		super.onPause();
	}
	
	private void RefrashTask(int page) 
	{
		//访问地址
		String toUrl =contants.baseUrl+ "newslist";
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("Region", Region+1);
		map.put("Type", Type+1);
		map.put("Page", page);
		hr.post(toUrl, map); 
		
	}
	@Override
	public void onSuccess(String result) 
	{		
		try 
		{
			JSONObject  res = new JSONObject(result);
			JSONArray data = res.getJSONArray("s1");
			
			for(int i = 0;i<data.length();i++)
			{
				JSONArray temp = data.getJSONArray(i);
				News news= new News();
				news.setId(temp.getString(0));
				news.setTitle(temp.getString(1));	
				news.setRegion(temp.getString(3));
				String times= temp.getString(2);
				if(times.length()>10)
				{
					times = times.substring(0, 10);
				}
				news.setUpdatetime(times);	
				
				newslist.add(news);
				
			}
			
			newsAdapter.pre(newslist);
			//FreshRead();
			//initTab();			
			
		} catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		newslistview.onRefreshComplete();
		if(newslist.size()==0)//列表无数据
		{
			Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
		}
	}
	
	//每个标签页未读
		private void initTab()
		{
			for(int i=0;i<3;i++)
			{
				img[i].setVisibility(View.INVISIBLE);
			}
			
			HttpRequest hpHttpRequest=new HttpRequest(new onResult() 
			{			
				@Override
				public void onSuccess(String result) 
				{
					try 
					{
						JSONObject object=new JSONObject(result);
						int serverCount=object.getInt("resultcode");//服务器端总条数
						String stypeString=object.getString("resultdesc");
						Cursor cursor=db.query("GPUB", null, 
								"type="+stypeString+" and region="+(Region+1),
								null, null, null, null);
						int localCount=cursor.getCount();//本地已读条数
						if(serverCount>localCount)//有未读新闻
						{
							if(Integer.valueOf(stypeString)==1||Integer.valueOf(stypeString)==4)
							{
								img[0].setVisibility(View.VISIBLE);
							}
							if(Integer.valueOf(stypeString)==2||Integer.valueOf(stypeString)==5)
							{
								img[1].setVisibility(View.VISIBLE);
							}
							if(Integer.valueOf(stypeString)==3||Integer.valueOf(stypeString)==6)
							{
								img[2].setVisibility(View.VISIBLE);
							}
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				@Override
				public void onError(int httpcode) {
					// TODO Auto-generated method stub
					
				}
			});	
			
			Map<String, Object> map1=new HashMap<>();
			map1.put("type", importantnews.getType()+1);
			map1.put("region", Region+1);
			hpHttpRequest.post(contants.baseUrl+"NewsCountByRegionType", map1);
			Map<String, Object> map2=new HashMap<>();
			map2.put("region", Region+1);
			map2.put("type", vocationstate.getType()+1);
			hpHttpRequest.post(contants.baseUrl+"NewsCountByRegionType", map2);
			Map<String, Object> map3=new HashMap<>();
			map3.put("region", Region+1);
			map3.put("type", mediaforcus.getType()+1);
			hpHttpRequest.post(contants.baseUrl+"NewsCountByRegionType", map3);
		}
	
	@Override
	public void onError(int httpcode) 
	{
		
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) 
	{
		
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) 
	{
		int page=newslist.size()/10;
		int items=newslist.size()%10;
		
		if(items==0)
		RefrashTask(page+1);
		else
			RefrashTask(page+2);		
	}
}
