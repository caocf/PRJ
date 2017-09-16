package com.example.smarttraffic.view;

import com.example.smarttraffic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 自定义控件内容适配器
 * @author Administrator zhou
 *
 */
public class InputSelectAdapter extends BaseAdapter
{
	Context context;
	String[] items;
	int[] imagesResource;
		
	public InputSelectAdapter(Context context,String[] items,int[] itemImages)
	{
		this.context=context;
		this.items=items;
		this.imagesResource=itemImages;
	}
	
	@Override
	public int getCount() {
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.listview_input_item, null);
		
		Holder holder=new Holder();
		holder.nameView=(TextView)convertView.findViewById(R.id.item_name);
		holder.imageView=(ImageView)convertView.findViewById(R.id.item_image);
		
		holder.nameView.setText(items[position]);
		
		if(imagesResource!=null && imagesResource[position]!=0)
		{
			holder.imageView.setImageResource(imagesResource[position]);
		}
		
		return convertView;
	}
	
	public void update(String[] name,int[] images)
	{
		this.items=name;
		this.imagesResource=images;
		notifyDataSetChanged();
	}

	class Holder
	{
		TextView nameView;
		ImageView imageView;
	}
}
