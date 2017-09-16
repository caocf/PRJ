package com.hztuen.gh.activity.Adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gh.modol.CredCardModel;
import com.gh.modol.DockModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.CredCardAdapter.ViewHolder;

/*
 * 按地理位置查询港口列表适配器
 */
public class DuckAreaAdapter extends BaseAdapter{
	
	private Context context;
	private List<DockModel> modellist;

	public DuckAreaAdapter(Context context, List<DockModel> modellist) {
		this.context = context;
		this.modellist = modellist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return modellist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return modellist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;

		holder = new ViewHolder();
		convertView = LinearLayout.inflate(context, R.layout.dock_area_item, null);
        holder.wharfname=(TextView)convertView.findViewById(R.id.wharfname);
        holder.wharfnum=(TextView)convertView.findViewById(R.id.wharfnum);
       
        
       
        holder.wharfname.setText(modellist.get(position).getwharfname());
        holder.wharfnum.setText(modellist.get(position).getwharfnum());
        
		return convertView;
	}

	class ViewHolder {

//		  {
//	            "wharfid": 1,
//	            "wharfname": "乌镇码头",
//	            "wharfnum": "1154879",
//	            "operations": "36",
//	            "city": "嘉兴",
//	            "area": "桐乡"
//	        }
	   
		TextView wharfname;
		TextView wharfnum;
		

	}

}
