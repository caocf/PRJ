package com.huzhouport.abnormal;

import java.util.ArrayList;

import com.example.huzhouport.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class AbNormalImage extends Activity implements OnGestureListener  
{	
	public static int REQUEST_CODE=100;
	
	public static int index;
	GestureDetector gestureDetector;
	TextView textView;
	ImageView imView;
	ArrayList<String> file_pathList=new ArrayList<String>();
	
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.setContentView(R.layout.abnormalimage_activity);
		
		index=this.getIntent().getIntExtra("index", 0);	
		file_pathList=this.getIntent().getStringArrayListExtra("images");
		
		FrameLayout layout=(FrameLayout) this.findViewById(R.id.id_gallery);
			
		imView=new ImageView(this);
		
		setImage(imView,index);
		layout.addView(imView);
		
		textView=(TextView) this.findViewById(R.id.im_text);
		textView.setText((index+1)+"/"+file_pathList.size());
		
		gestureDetector=new GestureDetector(this,this);
	}	
 
	
	public void GoBack(View v)
	{
		Intent in = new Intent();  
        in.putStringArrayListExtra("file_pathList", file_pathList); 
        setResult( RESULT_OK, in );
		this.finish();
	}
	
	private void setImage(ImageView imv,int index)
	{
		Bitmap bitmap=AbNormalProcess.getImageThumbnail(file_pathList.get(index),1080, 1920);
		imv.setImageBitmap(bitmap);
		
		imv.setScaleType(ScaleType.FIT_XY);	
		
	}
	
	public void Delete(View v)
	{
		if(file_pathList.size()>0)
		{
			file_pathList.remove(index);
			index--;
			
			if(file_pathList.size()>0)
			{
				textView.setText((index+1)+"/"+file_pathList.size());
				setImage(imView,index);
			}
			else 
			{
				Intent in = new Intent();  
	            in.putStringArrayListExtra("file_pathList", file_pathList); 
	            setResult( RESULT_OK, in );
				this.finish();
			}
		}		
	}
	
	@Override  
    public boolean onTouchEvent(MotionEvent event) 
	{
		
		
		return gestureDetector.onTouchEvent(event);
		
	}


	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

		
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,float distanceY) 
	{
		
		
		return false;
	}


	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) 
	{
		
		int indexend=file_pathList.size()-1;
		
		if(e2.getX()-e1.getX()>15)
		{
			if(index>0)
			index--;
			
		}
		else
		if(e2.getX()-e1.getX()<-15)
		{
			if(index<indexend)
			{
				index++;
				
			}
			
		}
		
		textView.setText((index+1)+"/"+(indexend+1));
		setImage(imView,index);
		
		return false;
	}
	
	@Override  
    public void onBackPressed() 
	{  
		Intent in = new Intent();  
        in.putStringArrayListExtra("file_pathList", file_pathList); 
        setResult( RESULT_OK, in );
		super.onBackPressed();  
        //System.out.println("°´ÏÂÁËback¼ü   onBackPressed()");         
    } 

}
