package net.hxkg.ship;

import java.util.List;

import net.hxkg.ghmanager.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchShipNameAdapter extends BaseAdapter {

	private Context context;
	private List<String> nameList;
	Drawable drawable;

	public SearchShipNameAdapter(Context context, List<String> nameList) {
		this.context = context;
		this.nameList = nameList;
		drawable=context.getResources().getDrawable(R.drawable.ic_gray_search);
	}
	
	public void setHistory(boolean is)
	{
		if(is)
		{
			drawable=context.getResources().getDrawable(R.drawable.ic_historynotes);
		}
		else {
			drawable=context.getResources().getDrawable(R.drawable.ic_gray_search);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return nameList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return nameList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		final ViewHolder holder;

		holder = new ViewHolder();
		convertView = LinearLayout.inflate(context, R.layout.search_ship_name_item, null);
        holder.shipname=(TextView)convertView.findViewById(R.id.text1_context);
        holder.img=(ImageView)convertView.findViewById(R.id.img_search);
        holder.shipname.setText(nameList.get(position));
        holder.img.setImageDrawable(drawable);
        
		return convertView;
	}

	class ViewHolder {

		ImageView img;
		TextView shipname; //

	}

}
