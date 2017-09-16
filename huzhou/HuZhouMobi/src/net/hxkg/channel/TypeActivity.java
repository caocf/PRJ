package net.hxkg.channel; 
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TypeActivity extends Activity implements HttpRequest.onResult,OnItemClickListener,OnCheckedChangeListener
{
	TextView issuenameTextView,channelTextView;
	EditText kzEditText,markEditText;
	
	EvidenceGridView evidenceGridView;
	GridViewAdapter gridViewAdapter;
	List<Bitmap> bitmaps=new ArrayList<>();
	Bitmap addBitmap;
	File photofile=null;
	
	final int TYPE=1;
	final int CHANNEL=2;
	final int PIC=3;
	final int PHOTO=4;	
	final int PHOTOCHECK=5;	
	
	ArrayList<String> filenameList=new ArrayList<>();
	int recordid=-1;
	int type=-1,channelid=-1,leftorright=-1;
	
	RadioGroup radioGroup;
	ProgressDialog progressDialog=null;
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	IssueEntity issueEntity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_typelist);
		recordid=getIntent().getIntExtra("recordid", -1);
		initView();
		initGridView();
	}
	private void initView() 
	{
		issuenameTextView=(TextView) findViewById(R.id.issuename);
		channelTextView=(TextView) findViewById(R.id.channel);
		kzEditText=(EditText) findViewById(R.id.KNum);
		markEditText=(EditText) findViewById(R.id.reason);
		issueEntity=(IssueEntity) getIntent().getSerializableExtra("IssueEntity");
		if(issueEntity==null)return;
		
		issuenameTextView.setText(issueEntity.getPtypename()+">>"+issueEntity.getTypename());
		type=issueEntity.getTypeid();
		channelTextView.setText(issueEntity.getChannelname());
		channelid=issueEntity.getChannelid();
		kzEditText.setText(issueEntity.getKzString());
		markEditText.setText(issueEntity.getMarkString());
		leftorright=issueEntity.getLeftorright();
	}
	
	private void initGridView()
	{	
		radioGroup=(RadioGroup) findViewById(R.id.ra_group);
		radioGroup.setOnCheckedChangeListener(this);
		RadioButton btn_0=(RadioButton) findViewById(R.id.btn_0);
		RadioButton btn_1=(RadioButton) findViewById(R.id.btn_1);
		if(leftorright==1)
		{
			btn_0.setChecked(true);
		}else if(leftorright==2)
		{
			btn_1.setChecked(true);
		}
		
		evidenceGridView=(EvidenceGridView) findViewById(R.id.evidence);
		gridViewAdapter=new GridViewAdapter(this, bitmaps);
		evidenceGridView.setAdapter(gridViewAdapter);
		addBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.btn_add_normal);		
		bitmaps.add(addBitmap);
		filenameList=getIntent().getStringArrayListExtra("filenameList");
		if(filenameList==null)
			filenameList=new ArrayList<>();
		else 
		{
			for(String filepath:filenameList)
			{
				File file=new File(filepath);
				updateEvidence(file);
			}
		}
		evidenceGridView.setOnItemClickListener(this);
		gridViewAdapter.notifyDataSetChanged();
	}
	
	public void Sure(View v)
	{	
		if(type==-1)
		{
			Toast.makeText(this, "请选择问题类型", Toast.LENGTH_SHORT).show();
			return;
		}
		if(channelid==-1)
		{
			Toast.makeText(this, "请选择航道", Toast.LENGTH_SHORT).show();
			return;
		}
		if(leftorright==-1)
		{
			Toast.makeText(this, "请选择左岸/右岸", Toast.LENGTH_SHORT).show();
			return;
		}
		if("".equals(kzEditText.getText().toString().trim()))
		{
			Toast.makeText(this, "请输入K桩号", Toast.LENGTH_SHORT).show();
			return;
		}
		
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("cruiseIssue.type", type);
		map.put("cruiseIssue.leftorright", leftorright);
		map.put("cruiseIssue.channelid", channelid);
		map.put("cruiseIssue.recordid", recordid);
		map.put("cruiseIssue.kz", kzEditText.getText().toString().trim());
		map.put("cruiseIssue.mark", markEditText.getText().toString().trim());
		map.put("cruiseIssue.uptime", simpleDateFormat.format(new Date()));
		
		Map<String, File> file_mapMap=new HashMap<>();
		for(String fname:filenameList)
		{
			File file=new File(fname);
			if(file!=null)
			{
				file_mapMap.put(file.getName(), file);
			}
		}
		
		progressDialog=new ProgressDialog(this);
		progressDialog.setMessage("数据上传中");
		progressDialog.show();
		String requst="commitIssue";
		if(issueEntity!=null)//编辑
		{
			requst="updateIssue";
			map.put("cruiseIssue.id", issueEntity.getIssueid());
		}
		try {
			httpRequest.post(HttpUtil.BASE_URL+requst, map,file_mapMap,"issueFile");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 
	public void  onChannelChoose(View v)
	{
		Intent intent=new Intent(this,ChannelChooseActivity.class);
		startActivityForResult(intent, CHANNEL);
	}
	
	@Override
	protected void onResume() 
	{ 
		super.onResume();
	}
	
	public void onViewClick(View v)
	{
		 finish();
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    { 
		switch (requestCode) 
		{
		case TYPE:
			if(resultCode!=200) return;
			String nameString=data.getStringExtra("name");
			String pname=data.getStringExtra("pname");
			issuenameTextView.setText(pname+nameString);
			type=data.getIntExtra("id", -1);
			break;
		case CHANNEL:
			if(resultCode!=200) return;	
			String nameString2=data.getStringExtra("channelname");
			channelTextView.setText(nameString2);
			channelid=data.getIntExtra("id", -1);
			break;
		case PIC:
			if(resultCode!=RESULT_OK)return;
			Uri uri = data.getData();
			String path="";
			String[] projection = {MediaStore.Images.Media.DATA};
	        Cursor cursor = null;
	        try 
	        {
	            cursor = this.getContentResolver().query(uri, projection,null, null, null);
	            int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
	            if (cursor.moveToFirst()) 
	            {            	
	            	path=cursor.getString(column_index);
	            	
	            }
	        } catch (Exception e) 
	        {
	            e.printStackTrace();
	        }

	        File file = new File(path);
	       
	        filenameList.add(file.getAbsolutePath());
	        updateEvidence(file);
			break;
		case PHOTO://拍照	
			if(resultCode!=RESULT_OK)return;
			if(photofile==null)
				return;			
			
			filenameList.add(photofile.getAbsolutePath());
			updateEvidence(photofile);
			break;
		case PHOTOCHECK:
			ArrayList<String> list= data.getStringArrayListExtra("deleteList");
			for(String name:list)
			{
				int index=filenameList.indexOf(name);
				filenameList.remove(index);
				removeEvidence(index);
			}
			
			break;

		}
		
    }
	
	private void  updateEvidence(File file)
	{
		Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
		bitmaps.remove(bitmaps.size()-1);
		bitmaps.add(bitmap);
		bitmaps.add(addBitmap);
		gridViewAdapter.notifyDataSetChanged();
	}
	
	private void  removeEvidence(int index) 
	{
		bitmaps.remove(index);
		gridViewAdapter.notifyDataSetChanged();
	}
	 
	public void onTypeChoose(View view) 
	{
		Intent intent=new Intent(this,MatterTypeActivity.class);
		startActivityForResult(intent, TYPE);
	}
 

	@Override
	public void onSuccess(String result) 
	{	
		if(progressDialog!=null)progressDialog.dismiss();
		Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
		setResult(200);
		this.finish();
	}
	
	private void getPhoto()
	{
		Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
		String path = Environment.getExternalStorageDirectory() + "/HZGH/";
		File filepath = new File(path);
		if (!filepath.exists()) 
		{

			filepath.mkdirs();

        }
		String  camera_photo_name = System.currentTimeMillis() + ".jpg";

		photofile= new File(filepath, camera_photo_name);
		getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photofile));
		startActivityForResult(getImageByCamera, PHOTO);	
	}
	
	private void getPic()
	{
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);		
		 
		try 
		{ 
		  startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"),PIC);
		} catch (android.content.ActivityNotFoundException ex)
		{
		    	 
		}
	}

	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		if(position==bitmaps.size()-1)
		{
			evidenceOption();
		}
		else
		{
			Intent intent=new Intent(this,PhotoCheck.class);
			intent.putStringArrayListExtra("namelist", filenameList);
			intent.putExtra("currentIndex", position);
			intent.putExtra("currentName", filenameList.get(position));
			startActivityForResult(intent,PHOTOCHECK);
			
		}
		
	}
	
	private void evidenceOption() 
	{
		final Dialog d_choose=new Dialog(this);
		d_choose.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d_choose.setContentView(R.layout.ab_dialog1);
		
		View v1=d_choose.findViewById(R.id.l_photo);	
		v1.setOnClickListener(new OnClickListener() 
		{				
			@Override
			public void onClick(View v) 
			{
				getPhoto();
				d_choose.dismiss();
			}
		});
		View v2=d_choose.findViewById(R.id.l_pic);	
		v2.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				getPic();
				d_choose.dismiss();
			}
		});
		
		View v5=d_choose.findViewById(R.id.cancel);	
		v5.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				d_choose.dismiss();
			}
		});
		
		d_choose.show();
		d_choose.setCanceledOnTouchOutside(true);
		WindowManager mwidow = getWindowManager();  
		Display d = mwidow.getDefaultDisplay();  //为获取屏幕宽、高  
		android.view.WindowManager.LayoutParams p = d_choose.getWindow().getAttributes();  //获取对话框当前的参数值 		
		p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.5 
		d_choose.getWindow().setAttributes(p);     //设置生效 
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		switch (checkedId) 
		{
		case R.id.btn_0:
			leftorright=1;//左岸
			break;
		case R.id.btn_1:
			leftorright=2;//右岸
			break;
		}
		
	}
}
