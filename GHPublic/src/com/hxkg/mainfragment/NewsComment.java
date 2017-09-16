package com.hxkg.mainfragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.utils.KJLoger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.gh.modol.NewsCommentModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.hxkg.activity.water.WaterInfoActivity;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.NewsCommentAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.Util;

/**
 * @author zzq
 * @DateTime 2016年7月12日 下午4:18:38
 */
public class NewsComment extends AbActivity{
	private final static String TAG = NewsComment.class.getSimpleName();
	private TextView comment_back;
	private String Newsid;
	private int Page=0;//当前页
	private int totolrows = 0;//数据总量
	private NewsCommentAdapter adapter;
	private List<NewsCommentModel> commentmodel;
	private PullToRefreshListView comment_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newscomment);
		comment_back = (TextView) findViewById(R.id.comment_back);
		comment_list =  (PullToRefreshListView) findViewById(R.id.comment_list);
		//		comment_list.setDividerHeight(0);
		comment_back.setOnClickListener(onclick);
		commentmodel = new ArrayList<NewsCommentModel>();
		adapter = new NewsCommentAdapter(getApplicationContext(), commentmodel);
		comment_list.setAdapter(adapter);
		AbDialogUtil.showProgressDialog(NewsComment.this, 0,
				"精彩评论正在呈现...");
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				init();
			}
		}, 1000);
		
	}
	public void init(){
		Intent intent = getIntent();
		Newsid = intent.getStringExtra("newsid");
		System.out.println(Newsid);
		getComment();
		comment_list.setOnRefreshListener(new OnRefreshListener2<ListView>(){

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				commentmodel.clear();	
				Page=0;
				getComment();
			}
			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				Page++;
				if(totolrows==0){
					Toast.makeText(getApplicationContext(), "已加载完全部数据", Toast.LENGTH_SHORT).show();
					comment_list.postDelayed(new Runnable() {
						@Override
						public void run() {
							comment_list.onRefreshComplete();
						}
					}, 1000);
				}else {
					getComment();
				}
			}

		});
	}
	public void getComment(){
		KJHttp kj = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Page="+Page);
		aa.add("Newsid="+Newsid);
		//访问地址
		org.kymjs.kjframe.http.HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.comment;
		if(params == null){
			//提示参数制造失败
			Util.getTip(getApplicationContext(), "构造参数失败");
		}else if(!toUrl.equals("")){
			kj.post(toUrl, params, false, new HttpCallBack(){

				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					// TODO Auto-generated method stub
					super.onSuccess(headers, t);
					comment_list.onRefreshComplete();
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
					String result = new String(t).trim();
					Log.i(TAG, result);
					if(result==null){
						Toast.makeText(getApplicationContext(), "暂无评论数据", Toast.LENGTH_SHORT).show();
						return;
					}else{
						try {
							JSONObject res = new JSONObject(result);
							totolrows = res.getInt("total");
							//							System.out.println(totolrows);
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
							//							comment_list.setAdapter(adapter);
							adapter.notifyDataSetChanged();
							AbDialogUtil.removeDialog(NewsComment.this);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
					);
		}//else这里结束
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
}
