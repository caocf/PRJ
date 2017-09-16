package net.hxkg.lawexcut;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LawDetailActivity extends Activity implements OnItemClickListener,HttpRequest.onResult
{	
	TextView edit_firstman,secman,target,desc,location,text_reason,time,status,checkman,result,lawtype;
	LawBaseEN lawBaseEN;
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	GridViewAdapter adapter;
	List<Bitmap> bitmaps=new ArrayList<>();
	List<Bitmap> oragion_bitmaps=new ArrayList<>();//原始图片
	
	List<String> list_url=new ArrayList<>();
	Bitmap vedio,audio,pic;	
	
	int mode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lawdetail);
		lawBaseEN=(LawBaseEN) getIntent().getSerializableExtra("LawBaseEN");
		mode=getIntent().getIntExtra("mode", 0);
		bitmaps.clear();
		list_url.clear();
		oragion_bitmaps.clear();
		
		initView();
		initGridview();
	}
	
	/*public void modify(View v)
	{
		Intent intent=new Intent(this,LawModifyActivity.class);
		intent.putExtra("LawBaseEN",lawBaseEN);
		
		startActivity(intent);
		finish();
	}*/
	
	private void initView()
	{	
		LinearLayout l1=(LinearLayout) findViewById(R.id.d1);
		LinearLayout l2=(LinearLayout) findViewById(R.id.d2);
		Button but1=(Button) findViewById(R.id.but1);
		
		if(mode==1)//待审核，修改模式
		{
			//but1.setVisibility(View.VISIBLE);
			
		}else //已审核，查看模式
		{
			l1.setVisibility(View.VISIBLE);
			l2.setVisibility(View.VISIBLE);
		}		
		
		edit_firstman=(TextView) findViewById(R.id.firstman);
		edit_firstman.setText(lawBaseEN.getFirstman());
		secman=(TextView) findViewById(R.id.secman);
		secman.setText(lawBaseEN.getSecman());
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
		checkman=(TextView) findViewById(R.id.checkman);
		checkman.setText(lawBaseEN.getChecker());
		result=(TextView) findViewById(R.id.result);
		result.setText(lawBaseEN.getIsllegal());
		lawtype=(TextView) findViewById(R.id.lawtype);
		lawtype.setText(lawBaseEN.typename);
		
	}
	
	private void initGridview() 
	{
		vedio=BitmapFactory.decodeResource(getResources(), R.drawable.ic_videofile);		
		audio=BitmapFactory.decodeResource(getResources(), R.drawable.ic_audiofile);
		pic=BitmapFactory.decodeResource(getResources(), R.drawable.icon_preloading);
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
	//按ID获取证据数据
	private void FreshData()
	{
		Map<String, Object> map=new HashMap<>();
		map.put("id", lawBaseEN.getId());
		HttpRequest httpRequest=new HttpRequest(LawDetailActivity.this);
		
		try 
		{
			httpRequest.post(Constants.BaseURL+"evidencedir", map);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position,long id) 
	{
		final String string=list_url.get(position);
		if(string!=null&&!"".equals(string))
		{			
			String s=string.substring(string.length()-4);
			//
			if(s.contains("jpg")||s.contains("png")||s.contains("jepg")||s.contains("bmp")||s.contains("ico"))
			{
				Bitmap bitmap=oragion_bitmaps.get(position);
				final Dialog d_choose=new Dialog(LawDetailActivity.this);			
				
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
			        Uri uri;
					try {
						uri = Uri.parse(URLDecoder.decode(Constants.BaseURL+string,"UTF-8"));
						 intent.setDataAndType(uri, type);
					        startActivity(intent);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} 
				}
				else 
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
			        String type = "video/* ";
			        Uri uri;
					try {
						uri = Uri.parse(URLDecoder.decode(Constants.BaseURL+string,"UTF-8"));
						 intent.setDataAndType(uri, type);
					        startActivity(intent);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} 
				}	       
			}
		}		
	}	


	@Override
	public void onSuccess(final String result) 
	{		
		try //根据附件数量加载附件图标
		{
			JSONArray dataArray=new JSONArray(result);
								
			for(int i=0;i<dataArray.length();i++)
			{
				final String s1=(String)dataArray.get(i);
				list_url.add(s1);				
										
				String s=s1.substring(s1.length()-4);
				
				if(s.contains("jpg")||s.contains("png")||s.contains("jepg")||s.contains("bmp")||s.contains("ico"))//图片
				{
					bitmaps.add(pic);
					oragion_bitmaps.add(pic);
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
		}
		adapter.notifyDataSetChanged();
		
		new Thread(new Runnable() //异步加载真实附件
		{
			@Override
			public void run()
			{
				List<Bitmap> list=new ArrayList<>();
				List<Bitmap> list1=new ArrayList<>();
				for (String s1 : list_url)
				{					
					String s=s1.substring(s1.length()-4);					
					if(s.contains("jpg")||s.contains("png")||s.contains("jepg")||s.contains("bmp")||s.contains("ico"))//图片
					{							
						
						InputStream is=null;
						try 
						{										
							is = new URL(URLDecoder.decode((Constants.BaseURL+s1), "UTF-8")).openStream();
						} 
						catch (Exception e)
						{
							e.printStackTrace();
						}	
						Bitmap bitmap=BitmapFactory.decodeStream(is);				
						Bitmap bitmap1= ThumbnailUtils.extractThumbnail(bitmap, 200, 200);
						
						list.add(bitmap1);
						list1.add(bitmap);	
					}
					else//视频或音频
					{
						list.add(vedio);
						list1.add(vedio);
					}
					
				}
				bitmaps.clear();
				oragion_bitmaps.clear();
				bitmaps.addAll(list);
				oragion_bitmaps.addAll(list1);
				
				LawDetailActivity.this.runOnUiThread(new Runnable()
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

	@Override
	public void onError(int httpcode) {
		
	}
}
