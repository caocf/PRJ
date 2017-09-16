package net.hxkg.simple;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnDrawListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import net.hxkg.ghmanager.R;
import net.hxkg.lawexcut.LawBaseEN;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.system.DownloadFile;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OfPreview1Activity extends Activity implements OnPageChangeListener,OnDrawListener 
{
	int targetid,penaltyid;
	TextView law;
	LawBaseEN lawbase;
	public static Handler handle;
	public static final int DONE=100;
	public static final int sec=200;
	PDFView pdfView;
	Bitmap bitmap;
	boolean signed=false;
	String idString;
	int type;
	Button button;
	String path="";
	
	ProgressDialog sumDialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview1);		
		
		idString=getIntent().getStringExtra("penaltyid");
		type=getIntent().getIntExtra("type", 0);
		
		bitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.signer);
		button=(Button) findViewById(R.id.button);
		law=(TextView) findViewById(R.id.law);
		
		final String pathString=Environment.getExternalStorageDirectory()+"/GH/"+idString+".pdf";
		pdfView=(PDFView) findViewById(R.id.pdfView);
		/*pdfView//.fromAsset("d.pdf")
		.fromFile(new File(pathString))
		.defaultPage(1)
		.onPageChange(OfPreview1Activity.this)
                .load();*/
		
		sumDialog=new  ProgressDialog(this);
		sumDialog.setCancelable(true);
		sumDialog.setMessage("加载中");
		
		
		
		if(type==0)//未签章
		{
			path="Doc/Pdf/";
		}else {
			signed=true;
			button.setVisibility(View.GONE);
			law.setText("处罚单");
			path="/Doc/Pdf/";
		}
		//System.out.println();
		DownloadFile.downloadPDF(Constants.BaseURL+path+idString+".pdf",pathString );
		handle=new Handler()
		{
			@SuppressLint("WrongCall")
			public void handleMessage(Message msg) 
			{
				switch (msg.what) 
				{
				case DONE:					
				{
					if(sumDialog!=null)
						sumDialog.dismiss();
					
					pdfView.fromFile(new File(pathString))
					//.fromAsset("d.pdf")
					.onDraw(OfPreview1Activity.this)
					.defaultPage(1)
                .onPageChange(OfPreview1Activity.this)
                
                .load();
					break;
				}
				case sec:					
				{
					if(!sumDialog.isShowing())
					sumDialog.show();
					DownloadFile.downloadPDF(Constants.BaseURL+path+idString+".pdf",pathString );
					break;
				}
					

				default:
					break;
				}
			}
		};
		
	}	
	
	
	public void Submit(View view)
	{
		/*Intent intent=new Intent(this,SimpleLaw3Activity.class);
		intent.putExtra("LawBaseEN", lawbase);
		startActivity(intent);
		finish();*/
		//本地盖章
		if(signed)
			return;
		Sign();
		pdfView.invalidate();
		//服务器盖章
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
		{

			@Override
			public void onSuccess(String result) 
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(int httpcode)
			{
				// TODO Auto-generated method stub
				
			}
			
		});
		Map<String, Object> map=new HashMap<>();
		map.put("name", idString);
		httpRequest.post(Constants.BaseURL+"Sign", map);
	}
	
	private void Sign()
	{
		signed=signed?false:true;
	}
	public void onBack(View view)
	{
		finish();
	}

	@Override
	public void onPageChanged(int page, int pageCount) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight,int displayedPage) 
	{
		if(!signed)
			return;
		Bitmap bitmap2=Bitmap.createScaledBitmap(bitmap, (int)(pageWidth*0.2), (int)(pageWidth*0.2), true);
		canvas.drawBitmap(bitmap2, (float)(pageWidth*0.6), (float)(pageHeight*0.65), null);
		
	}
	
	private void showdata()
	{
		/*HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
		{
			
			@Override
			public void onSuccess(String result)
			{
				try 
				{
					JSONObject dataJsonObject=new JSONObject(result);								
					JSONObject obj=dataJsonObject.getJSONObject("obj");
					String s1=obj.getString("name");
					String s2=obj.getString("gender");
					String s3=obj.getString("cert");
					String s4=obj.getString("lawname");
					String s5=obj.getString("duty");
					String s6=obj.getString("tel");
					String s7=obj.getString("location");
					
					man.setText(s1);
					gender.setText(s2);
					duty.setText(s5);
					tel.setText(s6);
					location.setText(s7);
					cert.setText(s3);
					lawman.setText(s4);
					
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onError(int httpcode) 
			{
				// TODO Auto-generated method stub
				
			}
		});
		Map<String, Object> map=new HashMap<>();
		map.put("targetid", targetid);
		httpRequest.post(Constants.BaseURL+"TargetByID", map);
		
		HttpRequest httpRequest1=new HttpRequest(new HttpRequest.onResult()
		{
			
			@Override
			public void onSuccess(String result)
			{
				System.out.println(result);
				try 
				{
					JSONObject dataJsonObject=new JSONObject(result);								
					JSONObject obj=dataJsonObject.getJSONObject("obj");
					String s1=obj.getString("depend");
					String s2=obj.getString("item1");
					String s3=obj.getString("item2");
					String s4=obj.getString("item3");
					String s5=obj.getString("suggest");
					String s6=obj.getString("number");
					
					String contentString="	现依据"+s1+"第"+s2+"条，第"+s3+"款，第"+s4+"项之规定，本机关对你（单位）作以下"+
										"行政处罚：\n"+s5;
					
					content.setText(contentString);
					
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onError(int httpcode) 
			{
				// TODO Auto-generated method stub
				
			}
		});
		Map<String, Object> map1=new HashMap<>();
		map1.put("penaltyid", penaltyid);
		httpRequest1.post(Constants.BaseURL+"PenaltyByID", map1);*/
	}
}
