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
import net.hxkg.user.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
import android.widget.ImageView.ScaleType;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class IllegalCheckActivity extends Activity implements OnItemClickListener,HttpRequest.onResult
																, RadioGroup.OnCheckedChangeListener
{	
	TextView edit_firstman,secman,target,desc,location,text_reason,time,checkman,lawtype;
	LawBaseEN lawBaseEN;
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	GridViewAdapter adapter;
	List<Bitmap> bitmaps=new ArrayList<>();
	List<Bitmap> oragion_bitmaps=new ArrayList<>();//原始图片
	List<String> list_url=new ArrayList<>();
	Bitmap vedio,audio,pic;	
	
	RadioButton bt1,bt2;
	EditText edit_descr;
	
	Map<String, Object> map_params=new HashMap<>();//提交审核
	String isillegalBoolean=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_illegalcheck);
		lawBaseEN=(LawBaseEN) getIntent().getSerializableExtra("LawBaseEN");
		
		initView();
		initGridview();	
		initRadioButton();
	}
	
	
	public void onClick(View v)
	{
		finish();
	}
	
	private void  initRadioButton()
	{
		bt1=(RadioButton) findViewById(R.id.btn_0);
		bt2=(RadioButton) findViewById(R.id.btn_1);
		RadioGroup options=(RadioGroup) findViewById(R.id.ra_group);
		options.setOnCheckedChangeListener(this);		
	}
	
	private void initView()
	{	
		edit_firstman=(TextView) findViewById(R.id.firstman);
		edit_firstman.setText(lawBaseEN.getFirstman());
		secman=(TextView) findViewById(R.id.secman);
		secman.setText(lawBaseEN.getSecman());
		lawtype=(TextView) findViewById(R.id.lawtype);
		lawtype.setText(lawBaseEN.typename);
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
		checkman=(TextView) findViewById(R.id.checkman);
		checkman.setText(User.name);
		
		edit_descr=(EditText) findViewById(R.id.edit_descr);
		
	}
	
	public void Submit(View v)
	{
		
		if(isillegalBoolean==null)
		{
			Toast.makeText(this, "请选择是否构成违章", Toast.LENGTH_LONG).show();
			return;
		}
		
		String s=edit_descr.getText().toString();
		final String deString=s==null?"":s;
		
		AlertDialog.Builder builder = new Builder(this);		
		builder.setMessage("确认提交？");
		builder.setPositiveButton("提交", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(final DialogInterface dialog, int which)
			{
				map_params.put("id", lawBaseEN.getId());
				map_params.put("isillegalBoolean", isillegalBoolean);
				map_params.put("descr", deString);
				map_params.put("checker", User.name);
				//map_params.put("checker", s7);
				
				HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
				{

					@Override
					public void onSuccess(String result) 
					{
						dialog.dismiss();
						Toast.makeText(IllegalCheckActivity.this, "提交成功", Toast.LENGTH_LONG).show();
						IllegalCheckActivity.this.finish();
						
					}

					@Override
					public void onError(int httpcode) {
						
					}
					
				});
				
				try 
				{
					httpRequest.post(Constants.BaseURL+"postcheck", map_params);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
				
			}
		});
		builder.setNegativeButton("取消",  new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();			
			}
		});
		
		builder.create().show();
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
		HttpRequest httpRequest=new HttpRequest(IllegalCheckActivity.this);
		
		try 
		{
			httpRequest.post(Constants.BaseURL+"evidencedir", map);
		} catch (Exception e) 
		{
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
				Bitmap bitmap=oragion_bitmaps.get(position);
				final Dialog d_choose=new Dialog(IllegalCheckActivity.this);
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
			        System.out.println(Constants.BaseURL+string);
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
			        System.out.println(Constants.BaseURL+string);
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
				
				IllegalCheckActivity.this.runOnUiThread(new Runnable()
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

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) 
	{
		switch (checkedId) 
		{
		case R.id.btn_0:
			isillegalBoolean="构成违章";
			break;
		case R.id.btn_1:
			isillegalBoolean="不构成违章";
			break;

		default:
			break;
		}
		
	}
}
