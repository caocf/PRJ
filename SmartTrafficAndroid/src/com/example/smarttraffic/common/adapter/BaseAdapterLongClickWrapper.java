package com.example.smarttraffic.common.adapter;

import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.common.localDB.HistoryDBOperate;
import com.example.smarttraffic.util.DensityUtil;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

/**
 * 
 * @author Administrator zhou
 *
 */
public class BaseAdapterLongClickWrapper
{
	public static final int HISTORY_LONG_CLICK=1;
	public static final int FAVOR_LONG_CLICK=2;
	
	private ListView listView;
	private int dbKind;						//1：history;2:favor
	
	public BaseAdapterLongClickWrapper(ListView listView)
	{
		this(listView, HISTORY_LONG_CLICK);
	}
	
	public BaseAdapterLongClickWrapper(ListView listView,int dbKind)
	{
		this.listView=listView;
		this.dbKind=dbKind;
		
		this.listView.setOnItemLongClickListener(new LongClick());
	}
	
	class LongClick implements OnItemLongClickListener
	{

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id)
		{
			HistoryBaseAdapter baseAdapter=(HistoryBaseAdapter)parent.getAdapter();
			int locID=baseAdapter.getHID(position);
			
			if(dbKind==HISTORY_LONG_CLICK)
			{
				Dialog dialog;
				TextView textView=new TextView(parent.getContext());
				textView.setText("删除");
				textView.setGravity(Gravity.CENTER);
				
				int dp=DensityUtil.px2dip(parent.getContext(), 10);
				textView.setPadding(0,dp,0,dp);
				textView.setTextSize(20);
				dialog=GetDialog.editDialog(parent.getContext(),"",textView,"",null,"",null);
				textView.setOnClickListener(new onDeleteListener(locID,parent.getContext(),dialog,baseAdapter,position));				
				dialog.show();
			}
			else if(dbKind==FAVOR_LONG_CLICK)
			{
				FavorDBOperate favorDBOperate=new FavorDBOperate(parent.getContext());
				favorDBOperate.delete(locID);
				favorDBOperate.CloseDB();
			}
			
			return true;
		}
		
		class onDeleteListener implements View.OnClickListener
		{
			int id;
			Context context;
			Dialog dialog;
			HistoryBaseAdapter historyBaseAdapter;
			int position;
			
			private onDeleteListener(int id,Context context,Dialog dialog,HistoryBaseAdapter historyBaseAdapter,int position)
			{
				this.id=id;
				this.context=context;
				this.dialog=dialog;
				this.historyBaseAdapter=historyBaseAdapter;
				this.position=position;
			}
			
			@Override
			public void onClick(View v)
			{
				HistoryDBOperate dbOperate=new HistoryDBOperate(context);				
				dbOperate.delete(id);				
				dbOperate.CloseDB();
				
				this.dialog.cancel();
				this.historyBaseAdapter.removeByID(position);
			}
			
		}
	}
	
	
}


