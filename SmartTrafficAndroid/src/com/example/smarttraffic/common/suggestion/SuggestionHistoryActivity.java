package com.example.smarttraffic.common.suggestion;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.common.localDB.BaseHistoryDBoperation;
import com.example.smarttraffic.common.localDB.BaseHistoryData;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.fragment.ManagerFragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class SuggestionHistoryActivity extends FragmentActivity
{

	ListView contentListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_suggestion_history);
		
		initHead();
		initList();
	}
	
	public void initHead()
	{
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName("建议历史记录");
		fragment.setRightName("清空");
		fragment.setRightListen(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				GetDialog.messageDialog(SuggestionHistoryActivity.this, "提示", "确定清除历史记录？", "是", new OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						BaseHistoryDBoperation db=new BaseHistoryDBoperation(SuggestionHistoryActivity.this);
						
						db.deleteAll(ContentType.SUGGESTION_HISTORY);
						
						db.CloseDB();
						
						Toast.makeText(SuggestionHistoryActivity.this, "清除成功", Toast.LENGTH_LONG).show();
						
						initList();
						
						dialog.cancel();
						
						
					}
				}, "否", new OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.cancel();
					}
				}).show();
			}
		});
		ManagerFragment.replaceFragment(this, R.id.suggestion_history_head, fragment);
	}
	
	public void initList()
	{
		contentListView=(ListView)findViewById(R.id.suggestion_history_content);
		
		BaseHistoryDBoperation db=new BaseHistoryDBoperation(this);
		
		List<BaseHistoryData> datas=db.selectForBaseData(ContentType.SUGGESTION_HISTORY);
		
		List<Suggestion> d=new ArrayList<Suggestion>();
		
		for(BaseHistoryData b:datas)
		{
			Suggestion s=JSON.parseObject(b.getContent(),Suggestion.class);
			s.setId(b.getId());
			
			d.add(s);
		}
		
		contentListView.setAdapter(new SuggestionHistoryAdapter(this, d));
		
		db.CloseDB();
		
	}
}