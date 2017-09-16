package net.hxkg.cruise;
 
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection; 
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList; 
import java.util.List;  
import net.hxkg.ghmanager.R;
import net.hxkg.lawexcut.EvidenceGridView;
import net.hxkg.lawexcut.GridViewAdapter; 
import net.hxkg.network.Constants; 
import net.hxkg.user.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent; 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory; 
import android.net.Uri;
import android.os.Bundle; 
import android.view.View;
import android.view.Window; 
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType; 
import android.widget.ImageView;
import android.widget.TextView; 

public class RecordInfoActivity extends Activity implements OnItemClickListener
{	
	//文件添加
	GridViewAdapter adapter;
	List<Bitmap> bitmaps=new ArrayList<>();  
	Bitmap vedio;
	Bitmap audio; 
	List<String> listfilename=new ArrayList<>();  
	public static final int REQUEST_PHOTO=11;
	public static final int REQUEST_PIC=12;
	public static final int REQUEST_VEDIO=13;
	public static final int REQUEST_RECORD=14;	
	public static final int REQUEST_LOCATION=16;   
	 
	TextView edit_firstman,desc,location;//text_checker;	
	Double lat=0.0,lon=0.0; 
	ProgressDialog locationDialog = null;
	ProgressDialog sumDialog = null;
	
	LogModel logModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_recordinfo); 
		logModel=(LogModel) getIntent().getSerializableExtra("LogModel");
		initView();
		initGridview(); 
	}
	
	
	private void initView()
	{
		edit_firstman=(TextView) findViewById(R.id.firstman);
		edit_firstman.setText(logModel.title); 
		desc=(TextView) findViewById(R.id.desc); 
		desc.setText(logModel.content);
		location=(TextView) findViewById(R.id.location); 
		location.setText(logModel.location);
	}
	
	private void initGridview() 
	{ 
		vedio=BitmapFactory.decodeResource(getResources(), R.drawable.ic_videofile);		
		audio=BitmapFactory.decodeResource(getResources(), R.drawable.ic_audiofile);
		EvidenceGridView gridView=(EvidenceGridView) findViewById(R.id.gridView); 
		adapter=new GridViewAdapter(this,bitmaps);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		FreshData(logModel.fileArray);
	}
	
	@Override
	protected void onResume() 
	{
		if(locationDialog!=null)
			locationDialog.cancel();
		super.onResume();
	}
	
	public void onViewClick(View v)
	{
		switch (v.getId())
		{
		case R.id.back:
		case R.id.law:
			this.finish();
			break;  
		
		default:
			break;
		}
	}
	
	
	private void FreshData(List<String> arry)
	{		
		for(final String dir:arry)
		{
			String s=dir.substring(dir.length()-3,dir.length());
			if(s.equals("jpg")||s.equals("png")||s.equals("jepg")||s.equals("bmp")||s.equals("ico"))
			{
					new Thread(new Runnable()
					{						
						@Override
						public void run() 
						{							
							try 
							{
								URL url;
								url = new URL(Constants.BaseURL+dir);
								HttpURLConnection conn = (HttpURLConnection) url.openConnection();
								conn.setDoInput(true);  
					            conn.connect(); 
								InputStream ipustr=conn.getInputStream();
								Bitmap bitmap=BitmapFactory.decodeStream(ipustr);
								bitmaps.add(bitmap);
								listfilename.add(dir);
							} catch (Exception e) 
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							runOnUiThread(new Runnable() 
							{								
								@Override
								public void run() 
								{
									adapter.notifyDataSetChanged();
									
								}
							});
							
						}
					}).start();		
			}
			else 
			{
				bitmaps.add(vedio);
				listfilename.add(dir);
				adapter.notifyDataSetChanged();
			}
		}		
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position,long id) 
	{		
		if(bitmaps.get(position).equals(vedio))
		{
			Intent intent = new Intent(Intent.ACTION_VIEW);
	        String type = "video/* ";
	        //System.out.println(Constants.BaseURL+string);
	        Uri uri;
			try {
				uri = Uri.parse(URLDecoder.decode(Constants.BaseURL+listfilename.get(position),"UTF-8"));
				 intent.setDataAndType(uri, type);
			        startActivity(intent);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		else 
		{
			Dialog d_choose=new Dialog(RecordInfoActivity.this);
			d_choose.requestWindowFeature(Window.FEATURE_NO_TITLE);			
			ImageView imvImageView=new ImageView(RecordInfoActivity.this);
			imvImageView.setScaleType(ScaleType.FIT_XY);
			imvImageView.setImageBitmap(bitmaps.get(position));				
			d_choose.setContentView(imvImageView);
			d_choose.show();
		}				
	}
}
