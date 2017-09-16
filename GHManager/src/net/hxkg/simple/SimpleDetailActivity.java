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
import com.github.chrisbanes.photoview.PhotoView;
import net.hxkg.ghmanager.R;
import net.hxkg.lawexcut.EvidenceGridView;
import net.hxkg.lawexcut.GridViewAdapter;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleDetailActivity extends Activity implements OnItemClickListener,HttpRequest.onResult
{	
	SimpleModel model;
	
	TextView edit_firstman,secman,target,desc,location,text_reason,time,status,checkman,result,lawtype,penalty;

	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	GridViewAdapter adapter;
	public static List<Bitmap> bitmaps=new ArrayList<>();
	public static List<Bitmap> oragion_bitmaps=new ArrayList<>();//原始图片
	public static List<String> list_url=new ArrayList<>();
	Bitmap vedio;
	Bitmap audio;	
	
	ProgressDialog loadDialog = null;
	boolean loaded=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simpledetail);
		model=(SimpleModel) getIntent().getSerializableExtra("SimpleModel");
		
		bitmaps.clear();
		list_url.clear();
		oragion_bitmaps.clear();
		
		initView();
		initGridview();
	}
	
	private void initView()
	{	
		LinearLayout l1=(LinearLayout) findViewById(R.id.d1);
		LinearLayout l2=(LinearLayout) findViewById(R.id.d2);
		Button but1=(Button) findViewById(R.id.but1);		
		
		edit_firstman=(TextView) findViewById(R.id.firstman);
		edit_firstman.setText(model.getFirstman());
		secman=(TextView) findViewById(R.id.secman);
		secman.setText(model.getSecman());
		target=(TextView) findViewById(R.id.target);
		target.setText(model.getTarget());
		text_reason=(TextView) findViewById(R.id.reason);
		text_reason.setText(model.getReason());
		desc=(TextView) findViewById(R.id.desc);
		desc.setText(model.getDetail());
		location=(TextView) findViewById(R.id.location);
		location.setText(model.getLocation());
		time=(TextView) findViewById(R.id.time);
		time.setText(model.getSumbdate());
		
		lawtype=(TextView) findViewById(R.id.lawtype);
		lawtype.setText(model.typename);
		
		penalty=(TextView) findViewById(R.id.penalty);
		penalty.setText(model.penaltyString);
		
		status=(TextView) findViewById(R.id.status);
		status.setText(model.getStatus());
		
		if("待审核".equals(model.getStatus()))
		{
			but1.setVisibility(View.GONE);
		}
		
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
		case R.id.targetdetail:
			Intent intent=new Intent(this,SimpleLawTargetActivitySee.class);
			intent.putExtra("targetname", model.getTarget());
			startActivity(intent);
			break;
		case R.id.but1:
			Intent intent1=new Intent(this,OfPreview1Activity.class);
			intent1.putExtra("type", 1);//查看已签章的文件
			intent1.putExtra("penaltyid", model.getPid());
			startActivity(intent1);
			break;
		default:
			break;
		}
	}	
	//按ID获取证据数据
	private void FreshData()
	{
		Map<String, Object> map=new HashMap<>();
		map.put("id", model.baseID);
		HttpRequest httpRequest=new HttpRequest(SimpleDetailActivity.this);
		
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
	public void onItemClick(AdapterView<?> parent, View view, final int position,long id) 
	{
		final String string=list_url.get(position);
		if(string!=null&&!"".equals(string))
		{			
			String s=string.substring(string.indexOf(".")+1);
			//
			if(s.equals("jpg")||s.equals("png")||s.equals("jepg")||s.equals("bmp")||s.equals("ico"))
			{
				Bitmap bitmap=oragion_bitmaps.get(position);
				final Dialog d_choose=new Dialog(SimpleDetailActivity.this);
				d_choose.requestWindowFeature(Window.FEATURE_NO_TITLE);	
				d_choose.setContentView(R.layout.dialog_imgview);
				
				final PhotoView pv=	(PhotoView) d_choose.findViewById(R.id.iv_photo);
				pv.setImageBitmap(bitmap);
				
				ImageButton b=(ImageButton) d_choose.findViewById(R.id.button);
				b.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						d_choose.dismiss();
						
					}
				});
				
				ImageButton b2=(ImageButton) d_choose.findViewById(R.id.rotate);
				b2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						pv.setRotationBy(90);
						
					}
				});
				
				pv.setScaleType(ScaleType.FIT_CENTER);
			
				//d_choose.setContentView(pv);
				d_choose.show();	
				WindowManager wmManager=d_choose.getWindow().getWindowManager();
				LayoutParams lParams=d_choose.getWindow().getAttributes();
				lParams.width=(int) (wmManager.getDefaultDisplay().getWidth()*0.9);
				lParams.height=(int) (wmManager.getDefaultDisplay().getHeight()*0.9);
				d_choose.getWindow().setAttributes(lParams);
				d_choose.setCanceledOnTouchOutside(false);							
			}
			else//视频或音频
			{
				if(s.equals("amr"))
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
			        String type = "audio/* ";
			        //System.out.println(Constants.BaseURL+string);
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
			        //System.out.println(Constants.BaseURL+string);
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
	protected void onResume() 
	{
		/*if(!loaded)
		{
			loadDialog=new ProgressDialog(SimpleDetailActivity.this);
			loadDialog.setCancelable(false);
			loadDialog.setMessage("数据加载中");
			loadDialog.show();
		}*/
		
		super.onResume();
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
					if(dataArray.length()==0&&loadDialog!=null)
					{
						loaded=true;
						loadDialog.cancel();
					}
					for(int i=0;i<dataArray.length();i++)
					{
						final String s1=(String)dataArray.get(i);
						list_url.add(s1);//System.out.println(s1);
						InputStream is=null;
						try 
						{										
							is = new URL(URLDecoder.decode((Constants.BaseURL+s1), "UTF-8")).openStream();
						} 
						catch (MalformedURLException e)
						{
							e.printStackTrace();
						} catch (IOException e)
						{
							e.printStackTrace();
						}
												
						String s=s1.substring(s1.indexOf(".")+1);
						
						if(s.equals("jpg")||s.equals("png")||s.equals("jepg")||s.equals("bmp")||s.equals("ico"))
						{							
							Bitmap bitmap=BitmapFactory.decodeStream(is);				
							Bitmap bitmap1= ThumbnailUtils.extractThumbnail(bitmap, 200, 200);
							bitmaps.add(bitmap1);
							oragion_bitmaps.add(bitmap);					
							
						}
						else//视频或音频
						{
							bitmaps.add(vedio);
							oragion_bitmaps.add(vedio);
						}				
					}	
				} 
				catch (Exception e)
				{
						// TODO: handle exception
				}
				
				SimpleDetailActivity.this.runOnUiThread(new Runnable()
				{										
					@Override
					public void run() 
					{
						adapter.notifyDataSetChanged();	
						if(loadDialog!=null)
						{
							loadDialog.dismiss();
							loaded=true;
						}
					}
				});
					
			}
		}).start();			
			
		adapter.notifyDataSetChanged();
		//
	}

	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
}
