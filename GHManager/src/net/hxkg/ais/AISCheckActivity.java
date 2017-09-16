package net.hxkg.ais;   
import java.util.List;
import java.util.Map;
import net.hxkg.ghmanager.R; 
import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;  
import com.nci.data.DataDriver;

import android.app.FragmentManager; 
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle; 
import android.view.View; 
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView; 
import android.widget.TextView;
import android.widget.Toast;

public class AISCheckActivity extends AbActivity implements  Runnable
{
	@AbIocView(id = R.id.back,click="btnClick")TextView back;
	@AbIocView(id = R.id.edt_search) EditText edt_search; 
	@AbIocView(id = R.id.fragment_content) FrameLayout frame; 
	@AbIocView(id = R.id.txt_clear,click="btnClick") ImageView txt_clear;
	@AbIocView(id = R.id.search,click="btnClick") ImageView search;
	
	public String shipname,ais;
	ProgressDialog sumDialog = null;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_ais_info); 
	}
	
	public void btnClick(View v)
	{
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.txt_clear:
			frame.setVisibility(View.GONE);
			edt_search.setText("");
			break;
		case R.id.search:
			sumDialog=new  ProgressDialog(this);
			sumDialog.setCancelable(true);
			sumDialog.setMessage("查询中");
			sumDialog.show();
			frame.setVisibility(View.VISIBLE);
			new Thread(this).start();
			break;
		default:
			break;
		}
	}
	  
	

	@Override
	public void run() 
	{
		FragmentManager fragmentManager = getFragmentManager();
		
		DataDriver driver=DataDriver.getInstance();
        driver.setHost("10.100.70.101");
        driver.setPort(8090);
        driver.setUser("csp");
        driver.setPwd("password");
        try {
			driver.connect(); 
			final List<Map> list=driver.getAissbh(edt_search.getText().toString().trim());
			 
			if(list!=null&&list.size()>0)
			{
				Map map=list.get(0);
				if(map!=null&&map.size()>0)
				{
					shipname=(String) map.get("cbmc");
					ais=(String) map.get("aissbh");
					driver.close();
			        
					fragmentManager.beginTransaction().replace(R.id.fragment_content, new FragmentAIS())  .commit();
					this.runOnUiThread(new Runnable()
					{

						@Override
						public void run() {
							if(sumDialog!=null)sumDialog.dismiss();
							
						}
						
					});
					
					return;
				}
				
			}
			 
			
		} catch (Exception e) {
			//System.out.println(e.getMsg());
			e.printStackTrace();
		} 
        driver.close();
       
        this.runOnUiThread(new Runnable()
		{

			@Override
			public void run() {
				if(sumDialog!=null)sumDialog.dismiss();
				 Toast.makeText(AISCheckActivity.this, "无AIS数据", Toast.LENGTH_LONG).show();
			}
			
		});
		fragmentManager.beginTransaction().replace(R.id.fragment_content, new FragmentAISNo())  .commit();
		return;
	}
	
	public void newAIS(View v)
	{
		Intent intent=new Intent(this,EditAISActivity.class);
		intent.putExtra("shipname", edt_search.getText().toString());
		this.startActivity(intent);
	}

}
