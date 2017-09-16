package com.huzhouport.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.huzhouport.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyAdapterListviewCheckbox extends BaseAdapter{
	private LayoutInflater mInflater;    
    public List<Map<String, Object>> mData;    
    private Context ctx;
    
    public MyAdapterListviewCheckbox(Context ctx1,List<Map<String, Object>> mData) { 
    	this.ctx = ctx1; 
        this.mData = mData;
		if (this.mData == null) {
			this.mData = new ArrayList<Map<String, Object>>();
		}
		 mInflater = LayoutInflater.from(ctx);
    }    
    
  
    
    public int getCount() {    
        return mData.size();    
    }    
    
    public Object getItem(int position) {    
        return null;    
    }    
    
    public long getItemId(int position) {    
        return 0;    
    }    
    
    public View getView(int position, View convertView, ViewGroup parent) {    
        ViewHolder holder = null;    
        //convertView为null的时候初始化convertView。    
        if (convertView == null) {    
            holder = new ViewHolder();    
            convertView = mInflater.inflate(R.layout.schedule_item_checkbox, null);    
            holder.name = (TextView) convertView.findViewById(R.id.schedule_checkbox_username);  
            holder.id = (TextView) convertView.findViewById(R.id.schedule_checkbox_userid);  
            holder.cBox = (CheckBox) convertView.findViewById(R.id.schedule_checkbox_checkbox);    
            convertView.setTag(holder);    
        } else {    
            holder = (ViewHolder) convertView.getTag();    
        }    
        holder.name.setText(mData.get(position).get("userName").toString());  
        holder.id.setText(mData.get(position).get("userId").toString());  
        holder.cBox.setChecked((Boolean) mData.get(position).get("userselect"));    
        return convertView;    
    }    
    
    public final class ViewHolder {     
        public TextView name;    
        public TextView id;    
        public CheckBox cBox;    
    }    
}
