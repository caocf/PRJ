package com.example.smarttraffic;

import com.example.smarttraffic.R;
import com.example.smarttraffic.util.GetArray;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 侧栏适配器 布局包括左侧图片+右侧文字
 * 
 * @author Administrator zhou
 * 
 */
public class ListLeftItemAdapter extends BaseAdapter
{
	Context context;
	String[] items;
	int[] imagesResource;

	public ListLeftItemAdapter(Context context)
	{
		this(context, GetArray.getStringArrayByID(R.array.array_left_item_name,
				context), new int[] { R.drawable.item_person_center,
				// R.drawable.item_complain,
				R.drawable.item_suggest, R.drawable.item_share,
				R.drawable.item_check_update, R.drawable.item_about_us,
				// R.drawable.item_help,
				R.drawable.item_exit });
	}

	public ListLeftItemAdapter(Context context, String[] items, int[] itemImages)
	{
		this.context = context;
		this.items = items;
		this.imagesResource = itemImages;
	}

	@Override
	public int getCount()
	{
		return items.length;
	}

	@Override
	public Object getItem(int position)
	{
		return items[position];
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		convertView = LayoutInflater.from(context).inflate(
				R.layout.listview_left_item, null);

		Holder holder = new Holder();
		holder.nameView = (TextView) convertView.findViewById(R.id.item_name);
		holder.imageView = (ImageView) convertView
				.findViewById(R.id.item_image);

		holder.nameView.setText(items[position]);

		if (imagesResource != null && imagesResource[position] != 0)
		{
			holder.imageView.setImageResource(imagesResource[position]);
		}

		return convertView;
	}

	class Holder
	{
		TextView nameView;
		ImageView imageView;
	}
}
