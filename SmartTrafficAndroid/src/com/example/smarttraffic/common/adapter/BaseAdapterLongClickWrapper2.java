package com.example.smarttraffic.common.adapter;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.common.localDB.HistoryDBOperate;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

/**
 * 
 * @author Administrator zhou
 *
 */
public class BaseAdapterLongClickWrapper2
{
	public static final int HISTORY_LONG_CLICK=1;
	public static final int FAVOR_LONG_CLICK=2;
	
	private ListView listView;
	private int dbKind;						//1：history;2:favor
	
	private FragmentActivity activity;
	private PopListener addListener;
	
	public BaseAdapterLongClickWrapper2(ListView listView,FragmentActivity activity,PopListener addListener)
	{
		this(listView, HISTORY_LONG_CLICK,activity,addListener);
	}
	
	public BaseAdapterLongClickWrapper2(ListView listView,int dbKind,FragmentActivity activity,PopListener addListener)
	{
		this.listView=listView;
		this.dbKind=dbKind;
		this.activity=activity;
		this.addListener=addListener;
		
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
				
				View v=activity.getLayoutInflater().inflate(R.layout.listview_pop, null);
				TextView add=(TextView)v.findViewById(R.id.pop_add);
				TextView delete=(TextView)v.findViewById(R.id.pop_delete);
				
				dialog=GetDialog.editDialog(parent.getContext(),"",v,"",null,"",null);
				delete.setOnClickListener(new onDeleteListener(locID,parent.getContext(),dialog,baseAdapter,position));				
				add.setOnClickListener(new onAddListener(parent.getAdapter().getItem(position),dialog));
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
		
		class onAddListener implements OnClickListener
		{
			Object object;
			Dialog dialog;
			
			public onAddListener(Object o,Dialog d)
			{
				this.object=o;
				this.dialog=d;
			}
			
			@Override
			public void onClick(View v)
			{
				addListener.click(object);
				this.dialog.cancel();
			}
			
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



