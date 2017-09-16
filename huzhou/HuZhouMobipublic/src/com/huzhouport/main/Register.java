package com.huzhouport.main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouportpublic.R;
import com.huzhouport.common.GetSDTreeActivity;
import com.huzhouport.common.util.GetFileFromPhone;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.StringMatch;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity implements CompoundButton.OnCheckedChangeListener
{
	CheckBox cb=null;	
	CheckBox cb1=null;
	
	Map<String, Object> map = new HashMap<String, Object>();
	Map<String, File> map_file = new HashMap<String, File>();
	
	EditText etPassword=null;
	EditText etRPPassword=null;
	//EditText etUsername=null;
	EditText etPhoneNumber=null;
	EditText etShipName=null;
	EditText etShipCheckName=null;
	
	TextView im_tv;
	
	ImageButton selectF;
	ImageButton selectC;
	ImageButton img;
	
	Button btnRegister=null;
	
	boolean fileselected=false;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.setContentView(R.layout.activity_register);
		
		cb=(CheckBox) this.findViewById(R.id.cbShowHidePwd);
		cb.setOnCheckedChangeListener(this);
		//cb.setChecked(false);
		
		
		cb1=(CheckBox) this.findViewById(R.id.cbRPShowHidePwd);
		cb1.setOnCheckedChangeListener(this);
		//cb1.setChecked(false);
		
		
		//etUsername = (EditText) findViewById(R.id.etUsername);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etRPPassword=(EditText) findViewById(R.id.repeatpsw);
        etRPPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etShipName = (EditText) findViewById(R.id.etShipName);
        etShipCheckName = (EditText) findViewById(R.id.etShipCheckName);
        
        selectF=(ImageButton) this.findViewById(R.id.permit_add_file);
        selectF.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				addFile();				
			}
        	
        });
        
        selectC=(ImageButton) this.findViewById(R.id.permit_add_photograph);
        selectC.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				addCP();				
			}
        	
        });
        
        img=(ImageButton) this.findViewById(R.id.selimg);
        img.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{				 
				if(fileselected==true)
				dialog_down((String) map.get("fname"));				
			}
        	
        });
        
        im_tv=(TextView) this.findViewById(R.id.im_tx);
        
		
	}
	
	public void dialog_down(String kt)
	{
		new AlertDialog.Builder(this).setTitle("�Ƴ��ļ�")
				.setMessage("�Ƴ�" + kt + "��")
				.setPositiveButton("��", new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which)
					{
						fileselected=false;
						//img.setBackgroundResource(R.drawable.imgbt_regselect);
						Resources res=getResources();
						Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.btn_add_pic_normal);
						img.setImageBitmap(bmp);
						im_tv.setText("");
					}
				}).setNegativeButton("��", null).show();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{

		if (requestCode == 1 && resultCode == com.huzhouport.common.GetSDTreeActivity.RESULT_CODE)
		{
			if (data != null)
			{
				String path=data.getStringExtra(GetSDTreeActivity.RESULT_PATH);
				String name=data.getStringExtra(GetSDTreeActivity.RESULT_NAME);
				if(path!=null && !path.equals("")&&name!=null && !name.equals(""))
				{					
					File file=new File(path);
					
					map_file.put("upfile", file);
					map.put("fname", name);
					
					fileselected=true;
					//img.setBackgroundResource(R.drawable.get_sd_jpg);
					//img.setBackgroundDrawable(new BitmapDrawable());
					img.setImageBitmap(getImageThumbnail(path,200,200));
					im_tv.setText(name);
				}					
			}
		}
		
		// ����
		if (requestCode == 2 && resultCode == RESULT_OK)
		{			
			String[] oStrings = GetFileFromPhone.getImageFromPhone(data);
			
			File f=new File(oStrings[1]);
			map_file.put("upfile", f);
			map.put("fname", oStrings[0] + ".jpg");
			
			fileselected=true;
			//img.setBackgroundResource(R.drawable.get_sd_jpg);
			img.setImageBitmap(getImageThumbnail(f.getAbsolutePath(),200,200));//(new BitmapDrawable());
			im_tv.setText(oStrings[0] + ".jpg");
		}

		//creatGV();
	}
	
	public void addFile()
	{
		Intent intent = new Intent();
		intent.setClass(this, com.huzhouport.common.GetSDTreeActivity.class);
		startActivityForResult(intent, 1);
	}
	
	public void addCP()
	{
		Intent getImageByCamera = new Intent(
				"android.media.action.IMAGE_CAPTURE");
		startActivityForResult(getImageByCamera, 2);
	}
	
	public void GoBack(View v)
	{
		this.finish();
	}
	
	public void DoRegister(View v)
	{
		String sCheckRet = checkInput();
        if (!sCheckRet.equals("")) 
        {
        	Toast.makeText(this, sCheckRet, Toast.LENGTH_SHORT).show();
        	return;
        }
        
        //ע��.
        new Reging(this).execute();
        
	}

	@Override
	public void onCheckedChanged(CompoundButton id, boolean isChecked) 
	{		
		switch(id.getId())
		{
			case R.id.cbShowHidePwd:
			{
				if (!isChecked)
					etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
		        else
		        	etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				break;
			}				
				
			case R.id.cbRPShowHidePwd:
			{
				if (!isChecked)
					etRPPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
		        else
		        	etRPPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				break;
			}				
		}		
	}
	
	private String checkInput() 
    {
		/*if (etUsername.getText().toString().trim().length() < 4)
            return "�û���������4���ַ�";
		if (!StringMatch.isStringReg(etUsername.getText().toString().trim(), StringMatch.NO_UNICODE_STRING))
            return "�û���ֻ�ܺ����ֻ���ĸ";*/
		if (etPhoneNumber.getText().toString().trim().length() < 11)
            return "�ֻ����������";
		if (!StringMatch.isStringReg(etPhoneNumber.getText().toString().trim(), StringMatch.NUMBER_STRING))
            return "�ֻ����������";
		String s=etPhoneNumber.getText().toString().substring(0, 3);
		if(!s.equals("139")||!s.equals("137")||!s.equals("138")||!s.equals("135")||!s.equals("186")||!s.equals("187")
				||!s.equals("183")||!s.equals("151")||!s.equals("157")||!s.equals("158")||!s.equals("189"))
			return "�ֻ����������";
		if (etPassword.getText().toString().trim().length() < 6)
            return "���볤�Ȳ�������λ";
		if (!StringMatch.isStringReg(etPassword.getText().toString().trim(), StringMatch.NO_UNICODE_STRING))
            return "����ֻ�ܺ����ֻ���ĸ";
        if (!etPassword.getText().toString().equals(etRPPassword.getText().toString()))
            return "�������벻��";        
        if (etShipName.getText().toString().trim().length() <= 0)
            return "�����봬����";
        if (etShipCheckName.getText().toString().trim().length() <= 0)
            return "�����봬���ǼǺ�";  
        
        File f=map_file.get("upfile");
        //String spath=(String) map.get("fpath");
        if(f==null||!f.exists())
        	return "���ϴ�֤��ͼƬ";
        
        
        return "";
    }
	
	private Bitmap getImageThumbnail(String imagePath, int width, int height) 
	{  
        Bitmap bitmap = null;  
        BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;  
        // ��ȡ���ͼƬ�Ŀ�͸ߣ�ע��˴���bitmapΪnull  
        bitmap = BitmapFactory.decodeFile(imagePath, options);  
        options.inJustDecodeBounds = false; // ��Ϊ false  
        // �������ű�  
        int h = options.outHeight;  
        int w = options.outWidth;  
        int beWidth = w / width;  
        int beHeight = h / height;  
        int be = 1;  
        if (beWidth < beHeight) {  
            be = beWidth;  
        } else {  
            be = beHeight;  
        }  
        if (be <= 0) {  
            be = 1;  
        }  
        options.inSampleSize = be;  
        // ���¶���ͼƬ����ȡ���ź��bitmap��ע�����Ҫ��options.inJustDecodeBounds ��Ϊ false  
        bitmap = BitmapFactory.decodeFile(imagePath, options);  
        // ����ThumbnailUtils����������ͼ������Ҫָ��Ҫ�����ĸ�Bitmap����  
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,  
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);  
        return bitmap;  
    }
	
	class Reging extends AsyncTask<Void, Void, Integer> 
	{
		ProgressDialog pDialog = null;

		public Reging() 
		{
			
		}

		//@SuppressWarnings("deprecation")
		public Reging(Context content) 
		{
			pDialog = new ProgressDialog(Register.this);
			pDialog.setTitle("ע��");
			pDialog.setMessage("ע���С���������");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener()
			{
				
				@Override
				public void onCancel(DialogInterface dialog)
				{
					if (pDialog != null)
						pDialog.dismiss();
					 if (Reging.this != null && Reging.this.getStatus() == AsyncTask.Status.RUNNING) 
						 Reging.this.cancel(true);
					
				}
			});
			pDialog.setButton("ȡ��", new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog,int which)
				{
					if (pDialog != null)
						pDialog.dismiss();
					 if (Reging.this != null && Reging.this.getStatus() == AsyncTask.Status.RUNNING) 
						 Reging.this.cancel(true);
					
				}
			});
			pDialog.show();	
		}

		protected Integer doInBackground(Void... params) 
		{			
			return SubmitToServer();
		}

		protected void onPostExecute(Integer result) 
		{			
			if (pDialog != null)
				pDialog.dismiss();
			if (Reging.this != null && Reging.this.getStatus() == AsyncTask.Status.RUNNING) 
				 Reging.this.cancel(true);
			
			switch (result) 
			{
				case 0:
				{					
					Intent intent=new Intent(Register.this,RegResult.class);
					intent.putExtra("result", "���û����Ѵ��ڣ�");
					Register.this.startActivity(intent);
					break;
				}
			
				case 1:
				{
					Intent intent=new Intent(Register.this,RegResult.class);
					intent.putExtra("result", "ע��ɹ�����̨�����");
					Register.this.startActivity(intent);
					Register.this.finish();
					break;
				}
				
			default:
				
				break;
			}
			super.onPostExecute(result);
		}

	}
	
	public int SubmitToServer()
	{
		map.put("userName", etPhoneNumber.getText());
		map.put("psd", etPassword.getText());
		map.put("phoneNumber", etPhoneNumber.getText());
		map.put("BindName", etShipName.getText());
		map.put("iccid", etShipCheckName.getText());
		
		String actionUrl = HttpUtil.BASE_URL + "Register";
		
		String result = null;
		HttpFileUpTool upload=new HttpFileUpTool();	
		try {
			result=upload.post(actionUrl, map,map_file, "");
		} catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int jsonObject1 = 0;
		try 
		{
			if (result != null) 
			{
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data = null;
				data = (JSONObject) jsonParser.nextValue();
				
				jsonObject1 = data.getInt("result");								
			} 
		}
		catch (Exception e) 
		{
			//jsonObject1=0;
		}
		
		return jsonObject1;
	}
}
