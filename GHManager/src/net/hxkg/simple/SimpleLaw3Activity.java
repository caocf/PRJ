package net.hxkg.simple;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import net.hxkg.ghmanager.R;
import net.hxkg.lawexcut.EvidenceGridView;
import net.hxkg.lawexcut.GridViewAdapter;
import net.hxkg.lawexcut.LawBaseEN;
import net.hxkg.lawexcut.ReasonActivity;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleLaw3Activity extends Activity implements OnItemClickListener,HttpRequest.onResult
{	
	TextView edit_firstman,secman,target,desc,location,text_reason,time,status;
	LawBaseEN lawBaseEN;
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	GridViewAdapter adapter;
	List<Bitmap> bitmaps=new ArrayList<>();
	List<String> list_url=new ArrayList<>();
	Bitmap vedio;
	Bitmap audio;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simplelaw3);
		lawBaseEN=(LawBaseEN) getIntent().getSerializableExtra("LawBaseEN");
		
		initView();
		initGridview();
	}
	
	private void initView()
	{	
		edit_firstman=(TextView) findViewById(R.id.firstman);
		edit_firstman.setText(lawBaseEN.getFirstman());
		secman=(TextView) findViewById(R.id.secman);
		secman.setText(lawBaseEN.getSecman());
		//checker=(TextView) findViewById(R.id.checker);
		//checker.setText(lawBaseEN.getChecker());
		target=(TextView) findViewById(R.id.target);
		target.setText(lawBaseEN.getTarget());
		text_reason=(TextView) findViewById(R.id.reason);
		text_reason.setText(lawBaseEN.getReason());
		desc=(TextView) findViewById(R.id.desc);
		desc.setText(lawBaseEN.getDetail());
		location=(TextView) findViewById(R.id.location);
		location.setText(lawBaseEN.getLocation());
		time=(TextView) findViewById(R.id.time);
		time.setText(sf.format(lawBaseEN.getSumbdate()));
		status=(TextView) findViewById(R.id.status);
		status.setText(lawBaseEN.getStatus());
		
	}
	
	private void initGridview() 
	{
		vedio=BitmapFactory.decodeResource(getResources(), R.drawable.ic_videofile);		
		audio=BitmapFactory.decodeResource(getResources(), R.drawable.ic_audiofile);
		EvidenceGridView gridView=(EvidenceGridView) findViewById(R.id.gridView);
		adapter=new GridViewAdapter(this,bitmaps);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		FreshData();
	}
	
	public void onViewClick(View v)
	{
		switch (v.getId())
		{
		case R.id.detail:		
		case R.id.back:
			this.finish();
			break;
		case R.id.toreason:
			Intent intent=new Intent(this,ReasonActivity.class);
			startActivityForResult(intent, 100);
			break;
		case R.id.myevidence:
			Intent intent1=new Intent(this,ReasonActivity.class);
			startActivity(intent1);
			break;
		default:
			break;
		}
	}	
	
	private void FreshData()
	{
		Map<String, Object> map=new HashMap<>();
		map.put("id", lawBaseEN.getId());
		HttpRequest httpRequest=new HttpRequest(SimpleLaw3Activity.this);
		
		try 
		{
			httpRequest.post(Constants.BaseURL+"evidencedir", map);
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		final String string=list_url.get(position);
		if(string!=null&&!"".equals(string))
		{			
			String s=string.substring(string.indexOf(".")+1);
			//
			if(s.equals("jpg")||s.equals("png")||s.equals("jepg")||s.equals("bmp")||s.equals("ico"))
			{
				
				new Thread(new Runnable() 
				{
					
					@Override
					public void run() 
					{
						InputStream is=null;
						try {
							is = new URL(URLDecoder.decode(Constants.BaseURL+string,"UTF-8")).openStream();
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Bitmap bitmap=BitmapFactory.decodeStream(is);				
						final Bitmap bitmap1= ThumbnailUtils.extractThumbnail(bitmap, 1080, 1920);
						
						SimpleLaw3Activity.this.runOnUiThread(new Runnable()
						{
							
							@Override
							public void run() {
								final Dialog d_choose=new Dialog(SimpleLaw3Activity.this);
								d_choose.requestWindowFeature(Window.FEATURE_NO_TITLE);
								ImageView imvImageView=new ImageView(SimpleLaw3Activity.this);
								imvImageView.setScaleType(ScaleType.FIT_XY);
								imvImageView.setImageBitmap(bitmap1);				
								d_choose.setContentView(imvImageView);				
								d_choose.show();
								
							}
						});
						
					}
				}).start();			
			}
			else//视频或音频
			{
				if(s.equals("amr"))
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
			        String type = "audio/* ";
			        System.out.println(Constants.BaseURL+string);
			        Uri uri;
					try {
						uri = Uri.parse(URLDecoder.decode(Constants.BaseURL+string,"UTF-8"));
						 intent.setDataAndType(uri, type);
					        startActivity(intent);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				else 
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
			        String type = "video/* ";
			        System.out.println(Constants.BaseURL+string);
			        Uri uri;
					try {
						uri = Uri.parse(URLDecoder.decode(Constants.BaseURL+string,"UTF-8"));
						 intent.setDataAndType(uri, type);
					        startActivity(intent);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				       
		       
			}
		}		
	}


	@Override
	public void onSuccess(final String result) 
	{		
		new Thread(new Runnable() 
		{
			@Override
			public void run()
			{
				try 
				{
					JSONArray dataArray=new JSONArray(result);
					for(int i=0;i<dataArray.length();i++)
					{
						final String s1=(String)dataArray.get(i);
						list_url.add(s1);
						
						
						String s=s1.substring(s1.indexOf(".")+1);
						//System.out.println(s);
						if(s.equals("jpg")||s.equals("png")||s.equals("jepg")||s.equals("bmp")||s.equals("ico"))
						{
									InputStream is=null;
									try {
										
										is = new URL(URLDecoder.decode((Constants.BaseURL+s1), "UTF-8")).openStream();
									} catch (MalformedURLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Bitmap bitmap=BitmapFactory.decodeStream(is);				
									Bitmap bitmap1= ThumbnailUtils.extractThumbnail(bitmap, 200, 200);
									bitmaps.add(bitmap1);
									SimpleLaw3Activity.this.runOnUiThread(new Runnable()
									{
										
										@Override
										public void run() 
										{
											adapter.notifyDataSetChanged();
											
										}
									});
							
						}
						else//视频或音频
						{
							bitmaps.add(vedio);
						}				
					}	
				} catch (Exception e)
				{
						// TODO: handle exception
				}
					
			}
		}).start();			
			
		adapter.notifyDataSetChanged();	
	}

	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
}
