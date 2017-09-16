package net.hxkg.channel;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.huzhouport.schedule.SelectPicPopupWindow;

import net.hxkg.ghmanager.R;
import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class TypeActivity extends Activity implements HttpRequest.onResult
{	 
	HASpinner haSpinner;
	MTSpinner mtSpinner;
	HBSpinner hbSpinner;
	LHSpinner lhSpinner;
	AHSpinner ahSpinner;
	WZSpinner wzSpinner;
	QTSpinner qtSpinner;
	
	Spinner nowspinner;
	String itemname;
	int itemindex;
	RadioGroup rg;
	String abString;
	EditText location,reason;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_typelist);
		abString=getIntent().getStringExtra("ab");
		initView();
	}
	
	public void onDate(View v)
	{
		TextView txTextView=(TextView) findViewById(R.id.time1);
		SelectPicPopupWindow menuWindow = new SelectPicPopupWindow(
				this, v, txTextView);
		menuWindow.showAtLocation(v, Gravity.BOTTOM
				| Gravity.CENTER_HORIZONTAL, 0, 0); 
	}
	
	public void Sure(View v)
	{
		
		/*try {
			JSONArray array=new JSONArray();
			//护岸情况
			if(!"无".equals((String)haSpinner.getSelectedItem()))
			{
				JSONObject ha =new  JSONObject();
				ha.put("qk", "1");
				ha.put("qksm", "护岸情况");
				JSONArray haarray=new JSONArray();
				JSONObject item=new  JSONObject();
				item.put("wt", haSpinner.getSelectedItemPosition());
				item.put("wtsm", (String)haSpinner.getSelectedItem());
				JSONArray array1=new JSONArray();
				JSONObject i1=new  JSONObject();
				i1.put("ab", abString);
				i1.put("jtwz", "");
				i1.put("ms", "");
				array1.put(i1);
				item.put("sjs", array1);
				haarray.put(item);
				ha.put("wts", haarray);
				array.put(ha);
				//码头情况
				JSONObject mt =new  JSONObject();
				mt.put("qk", "2");
				mt.put("qksm", "码头情况");
				JSONArray mtarray=new JSONArray();
				JSONObject item2=new  JSONObject();
				item2.put("wt", mtSpinner.getSelectedItemPosition());
				item2.put("wtsm", (String)mtSpinner.getSelectedItem());
				
				JSONArray array2=new JSONArray();
				JSONObject i2=new  JSONObject();
				i2.put("ab", abString);
				i2.put("jtwz", "");
				i2.put("ms", "");
				array2.put(i2);
				item2.put("sjs", array2);
				
				mtarray.put(item2);
				mt.put("wts", mtarray);
				array.put(mt);
			}
			
			//航标情况
			if(!"无".equals((String)hbSpinner.getSelectedItem()))
			{
				JSONObject hb =new  JSONObject();
				hb.put("qk", "3");
				hb.put("qksm", "航标情况");
				JSONArray hbarray=new JSONArray();
				JSONObject item3=new  JSONObject();
				item3.put("wt", hbSpinner.getSelectedItemPosition());
				item3.put("wtsm", (String)hbSpinner.getSelectedItem());
				
				JSONArray array3=new JSONArray();
				JSONObject i3=new  JSONObject();
				i3.put("ab", abString);
				i3.put("jtwz", "");
				i3.put("ms", "");
				array3.put(i3);
				item3.put("sjs", array3);
				
				hbarray.put(item3);
				hb.put("wts", hbarray);
				array.put(hb);
			}
			//绿化情况
			if(!"无".equals((String)lhSpinner.getSelectedItem()))
			{
				JSONObject lh =new  JSONObject();
				lh.put("qk", "4");
				lh.put("qksm", "绿化情况");
				JSONArray lharray=new JSONArray();
				JSONObject item4=new  JSONObject();
				item4.put("wt", lhSpinner.getSelectedItemPosition());
				item4.put("wtsm", (String)lhSpinner.getSelectedItem());
				
				JSONArray array4=new JSONArray();
				JSONObject i4=new  JSONObject();
				i4.put("ab", abString);
				i4.put("jtwz", "");
				i4.put("ms", "");
				array4.put(i4);
				item4.put("sjs", array4);
				
				lharray.put(item4);
				lh.put("wts", lharray);
				array.put(lh);
			}			
			
			//碍航情况
			if(!"无".equals((String)ahSpinner.getSelectedItem()))
			{
				JSONObject ah =new  JSONObject();
				ah.put("qk", "5");
				ah.put("qksm", "碍航情况");
				JSONArray aharray=new JSONArray();
				JSONObject item5=new  JSONObject();
				item5.put("wt", ahSpinner.getSelectedItemPosition());
				item5.put("wtsm", (String)ahSpinner.getSelectedItem());
				
				JSONArray array5=new JSONArray();
				JSONObject i5=new  JSONObject();
				i5.put("ab", abString);
				i5.put("jtwz", "");
				i5.put("ms", "");
				array5.put(i5);
				item5.put("sjs", array5);
				
				
				aharray.put(item5);
				ah.put("wts", aharray);
				array.put(ah);
			}
			
			//违章情况
			if(!"无".equals((String)wzSpinner.getSelectedItem()))
			{
				JSONObject wz =new  JSONObject();
				wz.put("qk", "6");
				wz.put("qksm", "违章情况");
				JSONArray wzarray=new JSONArray();
				JSONObject item6=new  JSONObject();
				item6.put("wt", wzSpinner.getSelectedItemPosition());
				item6.put("wtsm", (String)wzSpinner.getSelectedItem());
				
				JSONArray array6=new JSONArray();
				JSONObject i6=new  JSONObject();
				i6.put("ab", abString);
				i6.put("jtwz", "");
				i6.put("ms", "");
				array6.put(i6);
				item6.put("sjs", array6);
				
				wzarray.put(item6);
				wz.put("wts", wzarray);
				array.put(wz);
			}
			
			//其他情况
			if(!"无".equals((String)qtSpinner.getSelectedItem()))
			{
				JSONObject qt =new  JSONObject();
				qt.put("qk", "7");
				qt.put("qksm", "其他情况");
				JSONArray qtarray=new JSONArray();
				JSONObject item7=new  JSONObject();
				item7.put("wt", qtSpinner.getSelectedItemPosition());
				item7.put("wtsm", (String)qtSpinner.getSelectedItem());
				
				JSONArray array7=new JSONArray();
				JSONObject i7=new  JSONObject();
				i7.put("ab", abString);
				i7.put("jtwz", "");
				i7.put("ms", "");
				array7.put(i7);
				item7.put("sjs", array7);
				
				qtarray.put(item7);
				qt.put("wts", qtarray);
				array.put(qt);
			}
			
			
			JSONObject object =new  JSONObject();
			object.put("qks", array);
			
			System.out.println(object.toString());
			Intent intent=new Intent();
			intent.putExtra("detail", object.toString().trim());
			setResult(200, intent);
			finish();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		try 
		{
			
			JSONObject i1=new  JSONObject();
			i1.put("ab", abString);
			i1.put("jtwz", location.getText().toString().trim());
			i1.put("ms", reason.getText().toString().trim());
			Intent intent=new Intent();
			intent.putExtra("index", nowspinner.getSelectedItemPosition()+1);		
			intent.putExtra("name", (String)nowspinner.getSelectedItem()); 
			intent.putExtra("itemindex", itemindex);			
			intent.putExtra("itemname", itemname); 
			intent.putExtra("json",i1.toString().trim());
			setResult(200, intent);
			finish();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	 
	
	private void initView()
	{
		haSpinner=(HASpinner) findViewById(R.id.HA);
		mtSpinner=(MTSpinner) findViewById(R.id.MT);
		hbSpinner=(HBSpinner) findViewById(R.id.HB);		
		lhSpinner=(LHSpinner) findViewById(R.id.LH);
		ahSpinner=(AHSpinner) findViewById(R.id.AH);
		wzSpinner=(WZSpinner) findViewById(R.id.WZ);
		qtSpinner=(QTSpinner) findViewById(R.id.QT);
		
		rg=(RadioGroup) findViewById(R.id.ra_group);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) 
			{
				switch(checkedId)
				{
				case R.id.btn_0:abString="1";break;//左岸
				case R.id.btn_1:abString="2";break;//右岸
				}
				
			}
		});
		
		location=(EditText) findViewById(R.id.location);
		reason=(EditText) findViewById(R.id.reason);
		
		int index=getIntent().getIntExtra("item", 0);			
		switch(index)
		{
		case 0:
			findViewById(R.id.l1).setVisibility(View.VISIBLE);
			nowspinner=haSpinner;
			itemname="护岸情况";
			itemindex=1;
			break;
		case 1:findViewById(R.id.l2).setVisibility(View.VISIBLE);
			nowspinner=mtSpinner;
			itemname="码头情况";
			itemindex=2;
			break;
		case 2:findViewById(R.id.l3).setVisibility(View.VISIBLE);
			nowspinner=hbSpinner;
			itemname="航标情况";
			itemindex=3;
			break;
		case 3:findViewById(R.id.l4).setVisibility(View.VISIBLE);
			nowspinner=lhSpinner;
			itemname="绿化情况";
			itemindex=4;
		break;
		case 4:findViewById(R.id.l5).setVisibility(View.VISIBLE);
			nowspinner=ahSpinner;
			itemname="碍航情况";
			itemindex=5;
		break;
		case 5:findViewById(R.id.l6).setVisibility(View.VISIBLE);
			nowspinner=wzSpinner;
			itemname="违章情况";
			itemindex=6;
			break;
		}
		
		
	}
	 
	
	@Override
	protected void onResume() 
	{ 
		super.onResume();
	}
	
	public void onViewClick(View v)
	{
		 
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    { 
		 
		
    }
	 

 

	@Override
	public void onSuccess(String result) 
	{
		 
		
		
	}

	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
}
