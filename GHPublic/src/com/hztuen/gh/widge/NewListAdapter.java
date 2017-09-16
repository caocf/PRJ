package com.hztuen.gh.widge;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.gh.modol.News;
import com.hxkg.ghpublic.CenApplication;
import com.hxkg.ghpublic.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewListAdapter extends BaseAdapter
{
	public static final String TAG = NewListAdapter.class.getSimpleName();	
	private Context context;
	private List<News> newslist;	
	SharedPreferences spPreferences;
	Set<String> set=new HashSet<>();
	SQLiteDatabase db;
	
	
	public NewListAdapter(Context context,List<News> newslist)
	{
		this.context = context;
		this.newslist = newslist;
		spPreferences=context.getSharedPreferences("Data", 0);
		set=spPreferences.getStringSet("read", set);
		db=CenApplication.db;
	}

	@Override
	public int getCount() 
	{
		
		return newslist.size();
	}	

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return newslist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView==null){
			convertView =LayoutInflater.from(context).inflate(R.layout.activity_newlistitem, null);
			
		}
		ImageView imgImageView=(ImageView) convertView.findViewById(R.id.img);
		imgImageView.setVisibility(View.VISIBLE);
		String id=newslist.get(position).getId();
		
		Cursor cursor=db.query("GPUB", null, 
				"id='"+id+"'",
				null, null, null, null);		
		//寻找是否存在
		while (cursor.moveToNext()) 
		{
				String idc=cursor.getString(cursor.getColumnIndex("id"));
				
				if(id.equals(idc))
				{
					imgImageView.setVisibility(View.INVISIBLE);					
					break;
				}
		}			
		
		TextView newstitle = (TextView) convertView.findViewById(R.id.newstitle);
		TextView newsfrom = (TextView) convertView.findViewById(R.id.newsfrom);
		TextView newsupdatetime = (TextView) convertView.findViewById(R.id.newsupdatetime);
		newstitle.setText(newslist.get(position).getTitle());
		newsfrom.setText(newslist.get(position).getRegion());
		newsupdatetime.setText(newslist.get(position).getUpdatetime());
		
		return convertView;
	}
	public void pre(List<News> newslist)
	{
		//this.newslist.clear();
		this.newslist =newslist;
		notifyDataSetChanged();
	}
}
