package net.hxkg.ghmanager;

import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter
{
	 List<Map<String, Object>> data_list;
	 Context context;
	 SQLiteDatabase db;
	 SharedPreferences spPreferences;
	 int region;
	
	public MainAdapter(Context context, List<Map<String, Object>> data_list)
	{
		this.context=context;
		this.data_list=data_list;
		db=((CenApplication)((Activity)context).getApplication()).getData();
		spPreferences=context.getSharedPreferences("NewsData", 0);
		
	}
	
	private void  initNews(ImageView img)
	{		
		//System.out.println("1--"+regionString);
		/*String selectionString="region='"+region+"' and ("+" type='港航要闻' or type='行业动态' or type='媒体聚焦')";
		Cursor cursor=db.query("GHM1", null, 
				selectionString,
				null, null, null, null);
		while(cursor.moveToNext())
		{
			int s=cursor.getInt(cursor.getColumnIndex("status"));
			if(s==1)				
			{
				//img.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_home_new));
				img.setImageResource((int)R.drawable.ic_home_infoshipping_news);
				break;
			}
		}*/
	}
	private void  initNews1(ImageView img)
	{		
		/*//regionString=spPreferences.getString("Region", "杭州");
		String selectionString="region='"+region+"' and ("+" type='航行通告' or type='行政许可' or type='安全预警')";
		Cursor cursor=db.query("GHM1", null, 
				selectionString,
				null, null, null, null);
		while(cursor.moveToNext())
		{
			int s=cursor.getInt(cursor.getColumnIndex("status"));
			if(s==1)				
			{
				//img.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_home_new));
				img.setImageResource((int)R.drawable.ic_home_notify_news);
				break;
			}
		}*/
	}
	public void setRegion()
	{
		region=spPreferences.getInt("Region", 0);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		convertView=View.inflate(context, R.layout.gridview_item_main, null);
		TextView  textView=(TextView) convertView.findViewById(R.id.griditem_text);
		textView.setText((String)data_list.get(position).get("text"));
		ImageView imgImageView=(ImageView) convertView.findViewById(R.id.griditem_image);
		//imgImageView.setImageDrawable(context.getResources().getDrawable((int)data_list.get(position).get("icon")));
		imgImageView.setImageResource((int)data_list.get(position).get("icon"));
		
		if(position==9)
		{
			 initNews(imgImageView);
			 //imgImageView.setImageResource((int)R.drawable.ic_home_infoshipping_news);
			//imgImageView.(context.getResources().getDrawable(R.drawable.ic_home_new));
		}
		if(position==10)
		{
			initNews1(imgImageView);
		}
		
		return convertView;
	}

}
