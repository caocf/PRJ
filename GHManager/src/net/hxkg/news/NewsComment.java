package net.hxkg.news;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsComment extends Activity implements OnRefreshListener2<ListView>, onResult
{
	private TextView comment_back;
	private String Newsid;
//	private ListView comment_list;
	private int Page=0;//当前页
	private int totolrows = 0;//数据总量
//	private int pageSize = 10;//每页默认条数
	private NewsCommentAdapter adapter;
	private List<NewsCommentModel> commentmodel;
	private PullToRefreshListView comment_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newscomment);

		comment_back = (TextView) findViewById(R.id.comment_back);
		comment_list =  (PullToRefreshListView) findViewById(R.id.comment_list);
//		comment_list.setDividerHeight(0);
		comment_back.setOnClickListener(onclick);
		commentmodel = new ArrayList<NewsCommentModel>();
		adapter = new NewsCommentAdapter(getApplicationContext(), commentmodel);
		comment_list.setAdapter(adapter);
		init();
//		getComment();
	}
	public void init(){
		Intent intent = getIntent();
		Newsid = intent.getStringExtra("newsid");
		System.out.println(Newsid);
		getComment();
		comment_list.setOnRefreshListener(this);
		
	}
	public void getComment()
	{
		//访问地址
		String toUrl = "http://192.168.1.101:6080/"+"commentlist";
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("Page", Page);
		map.put("Newsid", Newsid);
		hr.post(toUrl, map);
		

	}
	public OnClickListener onclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.comment_back:
				finish();
				break;
			default:
				break;
			}
		}
	};
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) 
	{
		comment_list.postDelayed(new Runnable() {
            @Override
            public void run() {
            	comment_list.onRefreshComplete();
            }
        }, 100);
	}
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		Page++;
		if(totolrows==0){
			Toast.makeText(getApplicationContext(), "已加载完全部数据", Toast.LENGTH_SHORT).show();
			comment_list.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	comment_list.onRefreshComplete();
	            }
	        }, 100);
		}else {
			getComment();
		}
		
	}
	@Override
	public void onSuccess(String result) {
		
		if(result==null)
		{
			totolrows =0;
			Toast.makeText(getApplicationContext(), "暂无评论数据", Toast.LENGTH_SHORT).show();
			return;
		}else{
			try {
				JSONObject res = new JSONObject(result);
				
//				System.out.println(totolrows);
				JSONArray data = res.getJSONArray("data");
				String submitTime;
				for(int i = 0;i<data.length();i++){
					NewsCommentModel ncm = new NewsCommentModel();
					JSONObject temp = data.getJSONObject(i);
					submitTime = temp.getString("sumbtime");
					if(submitTime.length()>16){
						submitTime = submitTime.substring(5, 16);
					}
					ncm.setSumbtime(submitTime);
					ncm.setUsername(temp.getString("username"));
					ncm.setContent(temp.getString("content"));
					commentmodel.add(ncm);
				}
//				comment_list.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
}
