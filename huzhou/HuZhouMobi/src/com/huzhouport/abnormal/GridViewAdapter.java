package com.huzhouport.abnormal;

import java.util.ArrayList;
import java.util.List;

import com.example.huzhouport.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GridViewAdapter  extends BaseAdapter 
{
	
	 private Context mContext;
	 private List<Bitmap> drawables;
	 private int itemlayout;
	 private int imgid;
	 
	 public GridViewAdapter(Context c,List<Bitmap> drawables)
	 {
		 mContext=c;
		 this.drawables=drawables;
		 this.itemlayout=itemlayout;
		 this.imgid=imgid;
	 }
	 @Override
	 public int getCount() {
	  // TODO Auto-generated method stub
	  return drawables.size();
	 }

	 @Override
	 public Object getItem(int position) {
	  // TODO Auto-generated method stub
	  return drawables.get(position);
	 }

	 @Override
	 public long getItemId(int position) {
	  // TODO Auto-generated method stub
	  return position;
	 }

	 

	 //@SuppressLint("NewApi")
	@Override
	 public View getView(int position, View convertView, ViewGroup parent) 
	 {
	  // TODO Auto-generated method stub
	  
	  ImageView imageview=new ImageView(mContext);
	  imageview.setImageBitmap(drawables.get(position));
	  imageview.setLayoutParams(new GridView.LayoutParams(210,210));
	  imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
	  imageview.setPadding(8,8,8,8);
		  
	  		return imageview;
	  } 

	}


