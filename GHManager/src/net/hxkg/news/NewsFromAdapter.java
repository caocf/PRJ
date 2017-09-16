package net.hxkg.news;
import java.util.List;

import net.hxkg.ghmanager.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsFromAdapter extends BaseAdapter{
	private final static String TAG = NewsFromAdapter.class.getSimpleName();
	private Context context;
	private List<RegionList> regionlist;
	public NewsFromAdapter(Context context,List<RegionList> regionlist) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.regionlist = regionlist;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return regionlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return regionlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView =LayoutInflater.from(context).inflate(R.layout.activity_city_list, null);	
		}
		TextView registion = (TextView) convertView.findViewById(R.id.city);
		registion.setText(regionlist.get(position).getRegion());
		return convertView;
	}

}
