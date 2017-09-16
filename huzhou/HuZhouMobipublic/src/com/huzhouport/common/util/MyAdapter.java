package com.huzhouport.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.*;
import com.example.huzhouportpublic.R;

public class MyAdapter extends BaseAdapter {
	private List<Map<String, Object>> lsmap;
	private Context ctx;
	private LayoutInflater lin;
//	private ImageDownloader imageDownloader = new ImageDownloader();

	public MyAdapter(Context context, List<Map<String, Object>> lsmap) {
		ctx = context;
		this.lsmap = lsmap;
		if (this.lsmap == null) {
			this.lsmap = new ArrayList<Map<String, Object>>();
		}
		lin = LayoutInflater.from(ctx);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lsmap.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lsmap.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = lin.inflate(R.layout.gv_item, null);

			ImageView iv = (ImageView) convertView
					.findViewById(R.id.gv_item_ItemImage);
			TextView tv = (TextView) convertView
					.findViewById(R.id.gv_item_ItemText);

			Map<String, Object> map = lsmap.get(position);
			tv.setText(map.get("itemText").toString());
			if (map.get("itemPath")==null) {
				iv.setImageResource(R.drawable.broken_pic);
			}else iv.setImageBitmap((Bitmap) map.get("itemPath"));// œÚÕº∆¨øÿº˛…Ë÷√¬‘ÀıÕº			
		}

		return convertView;
	}

}
