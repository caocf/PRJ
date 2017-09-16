package net.hxkg.channel;

import java.util.ArrayList;
import java.util.List;

import com.example.huzhouport.R;
import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.github.chrisbanes.photoview.PhotoView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class PhotoCheck extends Activity implements OnSingleFlingListener
{
	ArrayList<String> filenameList=new ArrayList<>();
	PhotoView pv;
	TextView titTextView;
	int currentIndex=0;
	List<Bitmap> bitmaps=new ArrayList<>();
	ArrayList<String> deleteList=new ArrayList<>();
	int checkmode=0;
	ImageButton removeButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photocheck);
		filenameList=getIntent().getStringArrayListExtra("namelist");
		currentIndex=getIntent().getIntExtra("currentIndex", 0);
		checkmode=getIntent().getIntExtra("checkmode", 0);
		titTextView=(TextView) findViewById(R.id.law);
		titTextView.setText("查看图片("+(currentIndex+1)+"/"+filenameList.size()+")");
		removeButton=(ImageButton) findViewById(R.id.remove);
		if(checkmode==1)
		{
			removeButton.setVisibility(View.GONE);
		}
		
		pv=	(PhotoView) findViewById(R.id.iv_photo);
		pv.setOnSingleFlingListener(this);
		
		initBitmap();
		
	}
	
	public void onBack(View view)
	{		
		Intent intent=new Intent();
		intent.putStringArrayListExtra("deleteList", deleteList);
		setResult(200, intent);
		finish();
	}
	
	private void initBitmap() 
	{
		for(String name:filenameList)
		{
			Bitmap b=BitmapFactory.decodeFile(name);
			bitmaps.add(b);
		}
		
		pv.setImageBitmap(bitmaps.get(currentIndex));
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) 
	{
		float distance=e2.getX()-e1.getX();
		if(distance<-100)//下一个
		{
			if(currentIndex<filenameList.size()-1)
			{
				currentIndex++;
			}
		}else if(distance>100)//上一个
		{
			if(currentIndex!=0)
			{
				currentIndex--;
			}
		}
		
		pv.setImageBitmap(bitmaps.get(currentIndex));
		titTextView.setText("查看图片("+(currentIndex+1)+"/"+filenameList.size()+")");
		System.out.println(distance);
		return false;
	}
	
	public void onRemove(View view)
	{		
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确定删除此图片?");
		builder.setCancelable(false);
		builder.setPositiveButton("删除", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				remove() ;
				
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		builder.create().show();
		
	}
	
	private void remove() 
	{
		deleteList.add(filenameList.get(currentIndex));
		filenameList.remove(currentIndex);
		bitmaps.remove(currentIndex);
		
		
		if(currentIndex==filenameList.size())
		{
			currentIndex--;
		}	
		if(currentIndex<0)
		{
			pv.setImageBitmap(null);
			titTextView.setText("查看图片");
		}else {
			pv.setImageBitmap(bitmaps.get(currentIndex));
			titTextView.setText("查看图片("+(currentIndex+1)+"/"+filenameList.size()+")");
		}
		
	}
	
	@Override
	public void onBackPressed()
	{
		Intent intent=new Intent();
		
		intent.putStringArrayListExtra("deleteList", deleteList);
		setResult(200, intent);
		finish();
	}
}
