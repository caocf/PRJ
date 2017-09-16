package com.hztuen.gh.activity.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.gh.modol.ServiceAround;
import com.hxkg.ghpublic.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author zzq
 * @DateTime 2016年7月12日 下午4:09:07
 */
public class ServiceAroundAdapter extends BaseAdapter{
	public final static String TAG = ServiceAroundAdapter.class.getSimpleName();
	private Context context;
	private List<ServiceAround> listservice = new ArrayList<ServiceAround>();
	public ServiceAroundAdapter(Context context,List<ServiceAround> listservice){
		this.listservice = listservice;
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listservice.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listservice.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.gridview_around_service, null);
		}
		ImageView griditem_image = (ImageView) convertView.findViewById(R.id.griditem_image);
		TextView griditem_text = (TextView) convertView.findViewById(R.id.griditem_text);
		griditem_text.setText(listservice.get(position).getServiceName());
		griditem_image.setBackgroundResource(listservice.get(position).getServiceIcon());
		return convertView;
	}
}
