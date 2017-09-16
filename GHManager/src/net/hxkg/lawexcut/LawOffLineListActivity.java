package net.hxkg.lawexcut;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList; 
import java.util.List; 
import net.hxkg.ghmanager.CenApplication;
import net.hxkg.ghmanager.R; 
import net.hxkg.network.HttpRequest; 
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View; 
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LawOffLineListActivity extends Activity implements OnItemClickListener,HttpRequest.onResult
{	
	ListView listView;
	offlineAdapter adpter;
	List<LawBaseEN> datalist=new ArrayList<>();
	List<LawBaseEN> list=new ArrayList<>();
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 
	final int REQUEST=21;
	
	public SQLiteDatabase database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_lawofflinellist);
		database=((CenApplication)getApplication()).getData();
		initListView();
		
	}
	
	private void  initListView()
	{
		listView=(ListView) findViewById(R.id.list);
		adpter=new offlineAdapter(this,datalist);
		listView.setAdapter(adpter);
		//listView.setOnItemClickListener(this);
		freshData();
	}
	
	public void onClick(View v)
	{
		finish();
	}
	
	public void  freshData()
	{		
		datalist.clear();
		Cursor cursor=database.query("Lawbase", null, 
				null,
				null, null, null, null);
		while (cursor.moveToNext()) 
		{
			String firstman=cursor.getString(cursor.getColumnIndex("firstman"));
			String secman=cursor.getString(cursor.getColumnIndex("secman"));
			String target=cursor.getString(cursor.getColumnIndex("target"));
			String reason=cursor.getString(cursor.getColumnIndex("reason"));
			String detail=cursor.getString(cursor.getColumnIndex("detail"));
			String location=cursor.getString(cursor.getColumnIndex("location"));
			String type=cursor.getString(cursor.getColumnIndex("type")); 			
			Double lon=cursor.getDouble(cursor.getColumnIndex("lon"));
			Double lat=cursor.getDouble(cursor.getColumnIndex("lat"));
			String commitdate=cursor.getString(cursor.getColumnIndex("commitdate"));
			int localstatus=cursor.getInt(cursor.getColumnIndex("status"));
			
			LawBaseEN lawBaseEN=new LawBaseEN(); 
			lawBaseEN.setFirstman(firstman);
			lawBaseEN.setSecman(secman);
			lawBaseEN.setTarget(target);
			lawBaseEN.setReason(reason);
			lawBaseEN.setDetail(detail);
			lawBaseEN.setLocation(location);   
			lawBaseEN.lat=Double.valueOf(lat);
			lawBaseEN.lon=Double.valueOf(lon);
			lawBaseEN.typeid=Integer.valueOf(type); 
			lawBaseEN.localstatus=localstatus;
			lawBaseEN.commitdate=commitdate;
			datalist.add(lawBaseEN);
			
			
			String[] se=new String[1];
			se[0]=commitdate;
			Cursor cursorfile=database.rawQuery("select dir from Lawfile" +
					" where lawid=?" ,se );
			//System.out.println(cursorfile.getCount());
			while (cursorfile.moveToNext()) 
			{
				String dir=cursorfile.getString(0);
				//System.out.println(dir);
				File file=new File(dir);
				if(file!=null&&file.exists())
					lawBaseEN.filelist.add(file);
			}
		}	
		adpter.notifyDataSetChanged();
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{		
		String statusString=list.get(position).getStatus();
		if(LawBaseEN.CHECKING.equals(statusString))//待审核
		{
			Intent intent=new Intent(this,LawDetailActivity.class);
			intent.putExtra("LawBaseEN", list.get(position));
			intent.putExtra("mode", 1);
			startActivityForResult(intent, REQUEST);
		}
		else//已审核
		{
			Intent intent=new Intent(this,LawDetailActivity.class);
			intent.putExtra("LawBaseEN", list.get(position));
			intent.putExtra("mode", 0);
			startActivityForResult(intent, REQUEST);
		}
		
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    { 
		freshData();
    }

	@Override
	public void onSuccess(String result) 
	{		
		
	}

	@Override
	public void onError(int httpcode) 
	{
		// TODO Auto-generated method stub
		
	}
}
