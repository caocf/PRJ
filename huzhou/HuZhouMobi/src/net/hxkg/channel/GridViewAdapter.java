package net.hxkg.channel;

import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewAdapter  extends BaseAdapter 
{	
	 private Context mContext;
	 private List<Bitmap> bitmaps;
	 
	 public GridViewAdapter(Context c,List<Bitmap> bitmaps)
	 {
		 mContext=c;
		 this.bitmaps=bitmaps;
	 }
	 @Override
	 public int getCount() {
	  // TODO Auto-generated method stub
	  return bitmaps.size();
	 }

	 @Override
	 public Object getItem(int position) {
	  // TODO Auto-generated method stub
	  return bitmaps.get(position);
	 }

	 @Override
	 public long getItemId(int position) {
	  // TODO Auto-generated method stub
	  return position;
	 }

	@Override
	 public View getView(int position, View convertView, ViewGroup parent) 
	 {	  
		  ImageView imageview=new ImageView(mContext);
		  imageview.setImageBitmap(bitmaps.get(position));
		  imageview.setLayoutParams(new GridView.LayoutParams(210,210));
		  imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
		  imageview.setPadding(8,8,8,8);
		  
	  	return imageview;
	  } 

	}


