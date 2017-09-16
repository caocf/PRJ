package net.hxkg.lawexcut;

import java.io.File;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class offlineAdapter extends BaseAdapter
{
	List<LawBaseEN> datalist=new ArrayList<>();
	Context context; 
	
	Drawable d1,d2;
	ProgressDialog sumDialog = null;
	
	public offlineAdapter(Context context,List<LawBaseEN> datalist)
	{
		this.context=context;
		this.datalist=datalist;
		
		d1=context.getResources().getDrawable(R.drawable.address_jiantou);
		d1.setBounds(0, 0, d1.getMinimumWidth(), d1.getMinimumHeight());
		d2=context.getResources().getDrawable(R.drawable.check);
		d2.setBounds(0, 0, d2.getMinimumWidth(), d2.getMinimumHeight());
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datalist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datalist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) 
	{
		view=View.inflate(context, R.layout.offlineitem, null);
		TextView target=(TextView) view.findViewById(R.id.target);
		TextView reason=(TextView) view.findViewById(R.id.reason);
		TextView status=(TextView) view.findViewById(R.id.status);
		
		final LawBaseEN law=datalist.get(position);
		target.setText(law.getTarget());	
		reason.setText(law.getReason()); 		
		
		if(law.localstatus==0)//未提交
		{
			status.setText("续传");
			status.setCompoundDrawables(null, null, d1, null);
			status.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View arg0) 
				{
					//判断网络
					ConnectivityManager connection=(ConnectivityManager)context. getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo info=connection.getActiveNetworkInfo();
					if(!(info!=null&&info.isConnected()&&info.getState()==NetworkInfo.State.CONNECTED))
					{
						Toast.makeText(context, "网络不通，请检查网络", Toast.LENGTH_LONG).show();
						return;
					}
					
					HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult() 
					{
						
						@Override
						public void onSuccess(String result)
						{
							if(sumDialog!=null)
							{
								sumDialog.dismiss();
							}
							Toast.makeText(context, "上传成功", Toast.LENGTH_LONG).show();
							((LawOffLineListActivity)context).database.execSQL
							("REPLACE INTO Lawbase VALUES (?,?,?,?,?,?,?,?,?,?,?)",
							new Object[] {law.getFirstman(),law.getSecman(),law.getTarget(),law.getReason()
									,law.getDetail(),law.getLocation(),law.typeid,law.lon,law.lat,
									law.commitdate,1});//1 已提交状态
							((LawOffLineListActivity)context).freshData();
							
						}
						
						@Override
						public void onError(int httpcode) {
							// TODO Auto-generated method stub
							
						}
					});
					Map<String, Object> map_params=new HashMap<>();		
					
					map_params.put("lat", law.lat);
					map_params.put("lon", law.lon);
					map_params.put("firstman", law.getFirstman());
					map_params.put("secman", law.getSecman());
					map_params.put("target", law.getTarget());
					map_params.put("reason", law.getReason());
					map_params.put("detail", law.getDetail());
					map_params.put("location", law.getLocation());
					map_params.put("type", law.typeid);
					map_params.put("issimple", "0");
					
					Map<String,File> map_file=new HashMap<>();
					for(File file:law.filelist )
					{
						map_file.put(file.getName(), file);
					}
					try 
					{
						httpRequest.post(Constants.BaseURL+"evidence",map_params, map_file,"evidence");
						sumDialog=new  ProgressDialog(context);
						sumDialog.setCancelable(false);
						sumDialog.setMessage("数据上传中");
						sumDialog.show();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
					
				}
				
			});
		}
		else//已提交
		{
			status.setText("已提交");
			status.setCompoundDrawables(null, null, d2, null);
			status.setOnClickListener(null);
		}
		
		
		
		return view;
	}

}
