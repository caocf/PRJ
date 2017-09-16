package net.hxkg.cruise;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CruiseAdapter extends BaseAdapter
{
	List<CruiseRecordEN> dataCruiseRecordENs; 
	Context cotext;
	Handler handler=new Handler();
	
	public CruiseAdapter(Context context,List<CruiseRecordEN> dataCruiseRecordENs)
	{
		this.cotext=context;
		this.dataCruiseRecordENs=dataCruiseRecordENs;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataCruiseRecordENs.size();
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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		final CruiseRecordEN cruise=dataCruiseRecordENs.get(position);
		convertView=LinearLayout.inflate(cotext, R.layout.item_cruiselist, null);
		TextView area=(TextView) convertView.findViewById(R.id.area);
		area.setText(cruise.getArea());
		TextView member=(TextView) convertView.findViewById(R.id.member);
		member.setText(cruise.getMember());
		TextView date=(TextView) convertView.findViewById(R.id.date);
		date.setText(cruise.getPeriod());
		//System.out.println();
		final ImageView image=(ImageView) convertView.findViewById(R.id.image);
		
		new Thread(new Runnable()
		{

			@Override
			public void run() 
			{
				InputStream is=null;
				try {
					
					is = new URL(Constants.BaseURL+cruise.getDir()).openStream();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Bitmap bitmap=BitmapFactory.decodeStream(is);				
				final Bitmap bitmap1= ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
				Bitmap bitmap2= ThumbnailUtils.extractThumbnail(bitmap, 950, 600);
				CruiseDetailActivity.map_bitmap.put(position, bitmap2);
				handler.post(new Runnable()
				{
					
					@Override
					public void run() {
						image.setImageBitmap(bitmap1);
						
					}
				});
				
				
			}
			
		}).start();
		return convertView;
	}

}
