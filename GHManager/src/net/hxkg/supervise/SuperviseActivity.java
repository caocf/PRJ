package net.hxkg.supervise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.nci.data.DataDriver;
import com.nci.exception.DataException;
import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoCircle;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.map.CNMKOverlayItem;
import cennavi.cenmapsdk.android.search.CNMKAdminResult;
import cennavi.cenmapsdk.android.search.CNMKCategoryResult;
import cennavi.cenmapsdk.android.search.CNMKCityResult;
import cennavi.cenmapsdk.android.search.CNMKEncryptionResult;
import cennavi.cenmapsdk.android.search.CNMKGeocodingResult;
import cennavi.cenmapsdk.android.search.CNMKPOIAroundResult;
import cennavi.cenmapsdk.android.search.CNMKPoiInfo;
import cennavi.cenmapsdk.android.search.CNMKPoiResult;
import cennavi.cenmapsdk.android.search.CNMKReverseGeocodingResult;
import cennavi.cenmapsdk.android.search.CNMKSearch;
import cennavi.cenmapsdk.android.search.CNMKStepWalkResult;
import cennavi.cenmapsdk.android.search.ICNMKSearchListener;
import cennavi.cenmapsdk.android.search.driver.CNMKDriveRouteResult;
import cennavi.cenmapsdk.android.search.driver.CNMKNewDriveRouteResult;
import cennavi.cenmapsdk.android.search.poi.CNMKPoiCircleReqParam;
import cennavi.cenmapsdk.android.search.poi.CNMKPoiKeyReqParam;
import cennavi.cenmapsdk.android.search.poi.CNMKPoiRectReqParam;
import net.hxkg.cruise.MarkOverLay;
import net.hxkg.ghmanager.CenApplication;
import net.hxkg.ghmanager.HomeActivity;
import net.hxkg.ghmanager.R;
import net.hxkg.network.HttpRequest;
import net.hxkg.ship.SearchShipActivity;
import net.hxkg.ship.SearchShipNameAdapter;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View; 
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SuperviseActivity extends FragmentActivity implements HttpRequest.onResult,RadioGroup.OnCheckedChangeListener
																,ICNMKSearchListener
																,OnItemClickListener,TextWatcher
{   
	//基础地图
	CenApplication app;
	CNMKMapView mMapView = null; 
	//定位
	MarkOverLay mayplaceLay;	
	CNMKLocation lastLocation=null;
	GeoPoint pt=null;
	//搜索	
	CNMKSearch  mSearch = null;
	CNMKPoiKeyReqParam a_oParam1;
	CNMKPoiRectReqParam a_oParam2;
	CNMKPoiCircleReqParam a_oParam3;
	EditText edit_contentEditText;
	ListView listView;
	SimpleAdapter adapter;
	List<Map<String, Object>> searchList=new ArrayList<>();
	LinearLayout linearLayout=null;
	//数据展示控件
	RadioGroup options;
	RadioButton bt1,bt2,bt3;
	FragmentManager fm;
	View frmeView;
	MarkOverLay markOverLay;
	ListView nameListView;
	List<String> namesList=new ArrayList<>();
	SearchShipNameAdapter pad;
	//AIS
	AISOverLay AISOverLay;
	Handler handler=new Handler();
	View mPopView = null;
	ShipFresher sfFresher=null;
	
	DataDriver driver = null;
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	TextView ais,date1,time1,date2,time2;	
	
	int time=5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_supervise);	
				
		fm=this.getFragmentManager();
		app = (CenApplication)this.getApplication();
						
		mMapView = (CNMKMapView)findViewById(R.id.cnmapView);
		mMapView.setBuiltInZoomControls(false);        
        //mMapView.setDrawOverlayWhenZooming(false);//设置在缩放动画过程中也显示overlay,默认为不绘制
		
		// 添加定位图层
        Drawable drawable=getResources().getDrawable(R.drawable.ic_directio2);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mayplaceLay=new MarkOverLay(drawable,this,mMapView);
		mMapView.getOverlays().add(mayplaceLay); 		
		initRadioButton();
		frmeView=findViewById(R.id.fragment);
		
		// 初始化搜索模块，注册事件监听
       mSearch = app.mCNMKAPImgr.getSearcher();
       mSearch.regListener(this);
       edit_contentEditText=(EditText) findViewById(R.id.key);
       edit_contentEditText.addTextChangedListener(this);
       a_oParam1 =new CNMKPoiKeyReqParam();
       a_oParam2=new CNMKPoiRectReqParam();
       a_oParam3=new CNMKPoiCircleReqParam();
       listView=(ListView) findViewById(R.id.list);
       adapter=new SimpleAdapter(this, searchList, R.layout.item_supervise_search,
    		   new String[]{"name","address"}, new int[]{R.id.text,R.id.text1});
       listView.setAdapter(adapter);
       listView.setOnItemClickListener(this);
       linearLayout=(LinearLayout) findViewById(R.id.searchlists);
       Drawable markdrawable=getResources().getDrawable(R.drawable.ic_place);
       markdrawable.setBounds(0, 0, markdrawable.getMinimumWidth(), markdrawable.getMinimumHeight());
       markOverLay=new MarkOverLay(markdrawable,this,mMapView);//添加标记的图层		
       //mMapView.getOverlays().add(markOverLay);       
       //提示名列表
       nameListView=(ListView) findViewById(R.id.namelist);
       pad=new SearchShipNameAdapter(this,namesList);
       nameListView.setAdapter(pad);
       nameListView.setOnItemClickListener(new OnItemClickListener() 
       {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{				
				nameListView.setVisibility(View.GONE);
				
				AISOverLay.SearchToTarget(namesList.get(position));
			
				mMapView.setZoomLevel(17);
				edit_contentEditText.setText("");
			}
       });
	   //AIS数据
       Drawable de=getResources().getDrawable(R.drawable.iconmarka);
       de.setBounds(0, 0, de.getMinimumWidth(), de.getMinimumHeight());
       handler=new Handler();
       AISOverLay=new AISOverLay(de,this,mMapView,handler);
       mMapView.getOverlays().add(AISOverLay);
       sfFresher =new ShipFresher(AISOverLay);
       sfFresher.start();
       mPopView=getLayoutInflater().inflate(R.layout.popview, null);
       mMapView.addView( mPopView, CNMKMapView.newLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, //LayoutParams.WRAP_CONTENT
               		null, CNMKMapView.LayoutParams_TOP_LEFT));	
       mPopView.setVisibility(View.GONE);
       ais=(TextView) mPopView.findViewById(R.id.ais);
       date1=(TextView) mPopView.findViewById(R.id.ais);
       time1=(TextView) mPopView.findViewById(R.id.ais);
       date2=(TextView) mPopView.findViewById(R.id.ais);
       time2=(TextView) mPopView.findViewById(R.id.ais);
       AISOverLay.setPopView(mPopView);
       
       driver = DataDriver.getInstance();
       driver.setHost("10.100.70.101");
       driver.setPort(8090);
       driver.setUser("csp");
       driver.setPwd("password");
       
       Timer tt=new Timer();
       /*tt.schedule(new TimerTask() {
		
		@Override
		public void run() {
			time--;
			if(time<=0)
			{
				if(sfFresher!=null)sfFresher.Stop();
			}
		}
       }, 0,1000);*/
       
       mMapView.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			time=5;
			if(sfFresher!=null&&!sfFresher.isRunning())sfFresher.start();
			return false;
		}
       });
	}
	
	@Override
	public void onStop()
	{
		//if(sfFresher!=null)sfFresher.Stop();
		super.onStop();
	}
	
	public void onTimePick(final View vi)
	{
		TimePickerDialog tp=new TimePickerDialog(this,  new OnTimeSetListener ()
		{
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) 
			{
				TextView text=(TextView)vi;
				text.setText(hourOfDay+":"+minute);
				
			}
			
		}, 12, 0, true);
		tp.show();
	}
	
	public void onDatePick(final View vie)
	{
		Calendar calendar=Calendar.getInstance();
		DatePickerDialog dpd=new DatePickerDialog(this,new OnDateSetListener()
		{

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) 
			{
				TextView text=(TextView)vie;
				text.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
			}
			
		},calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		dpd.show();
	}
	
	public void onCircle(View v)
	{
		Intent intent=new Intent(this,SearchShipActivity.class);
		intent.putExtra("shipnameofmap", ((TextView)(mPopView.findViewById(R.id.shipname))).getText().toString());
		this.startActivity(intent); 
		
	}
	public void onHistory(View v)
	{
		final String aisString=ais.getText().toString().trim();
		final String d1=date1.getText().toString().trim();
		final String t1=time1.getText().toString().trim();
		final String d2=date2.getText().toString().trim();
		final String t2=time2.getText().toString().trim();
		
		if("".equals(aisString))
		{
			Toast.makeText(this, "赞无数据", Toast.LENGTH_SHORT).show();
			return;
		}
		if("".equals(d1))
		{
			Toast.makeText(this, "请选择开始日期", Toast.LENGTH_SHORT).show();
			return;
		}
		if("".equals(t1))
		{
			Toast.makeText(this, "请选择开始时间", Toast.LENGTH_SHORT).show();
			return;
		}
		if("".equals(d2))
		{
			Toast.makeText(this, "请选择结束日期", Toast.LENGTH_SHORT).show();
			return;
		}
		if("".equals(t2))
		{
			Toast.makeText(this, "请选择结束时间", Toast.LENGTH_SHORT).show();
			return;
		}
		
		new Thread(new Runnable() 
		{			
			@Override
			public void run()
			{
				try 
				{
					driver.connect();
					List<Map<String, Object>> list=driver.getShipAisTrack(aisString, 
							simpleDateFormat.parse(d1+" "+t1 ),simpleDateFormat.parse(d2+" "+t2));
					System.out.println(list.size());
					System.out.println(list);
					
				}
				catch (DataException e)
				{ 
					e.printStackTrace();
				}
				catch(ParseException p)
				{
					
				}
				finally
				{
					if(driver!=null){
						driver.close();
					}
				}
				
			}
		}).start(); 
	}
	
	public void PoiSearch(View V)
	{
		if(lastLocation==null) 
		{
			return;
		}
		GeoCircle cir = new GeoCircle(lastLocation.getLatitude(),lastLocation.getLongitude(),50000);
		a_oParam3.setCategory(null);
		a_oParam3.setCircle(cir);
		a_oParam3.setInRadis(1);
		a_oParam3.setLanguage(0);
		//a_oParam3.setAdcode("330000");
		//a_oParam3.setSearchtype("poi");
		
		/*String string;
		try {
			string = URLEncoder.encode(edit_contentEditText.getText().toString(), "utf-8");
			a_oParam3.setKey(string);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		a_oParam3.setKey(edit_contentEditText.getText().toString());
		mSearch.poiSearchInCircle(a_oParam3);
	}
	
	private void  initRadioButton()
	{
		bt1=(RadioButton) findViewById(R.id.b1);
		bt1.setChecked(true);
		bt2=(RadioButton) findViewById(R.id.b2);
		bt3=(RadioButton) findViewById(R.id.b3);
		options=(RadioGroup) findViewById(R.id.ra_group);
		options.setOnCheckedChangeListener(this);
		
		if(bt1.isChecked())
		{
			FragmentShip ship=new FragmentShip();
			fm.beginTransaction().replace(R.id.fragment, ship).commit();
			bt1.setTextColor(Color.parseColor("#1766b1"));
			bt2.setTextColor(Color.parseColor("#901766b1"));
			bt3.setTextColor(Color.parseColor("#901766b1"));
		}
		else if(bt2.isChecked())
		{
			Fragment_Pot pot=new Fragment_Pot();
			fm.beginTransaction().replace(R.id.fragment, pot).commit();
			bt2.setTextColor(Color.parseColor("#1766b1"));
			bt1.setTextColor(Color.parseColor("#901766b1"));
			bt3.setTextColor(Color.parseColor("#901766b1"));
		}
		else
		{
			FragmentWharf wharf=new FragmentWharf();
			fm.beginTransaction().replace(R.id.fragment, wharf).commit();
			//fm.beginTransaction().replace(R.id.fragment, ship).commit();
			bt3.setTextColor(Color.parseColor("#1766b1"));
			bt1.setTextColor(Color.parseColor("#901766b1"));
			bt2.setTextColor(Color.parseColor("#901766b1"));
		}
	}	
	
	
	@Override
	protected void onResume() 
	{
		
		if(sfFresher!=null&&!sfFresher.isRunning())sfFresher.start();
		super.onResume();
		
	}
	
	@Override
	protected void onDestroy() 
	{
		
		if(sfFresher!=null)sfFresher.Stop();
		super.onDestroy();
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {
		
    }	
	
	public void onViewClick(View v)
	{
		switch (v.getId()) 
		{
		case R.id.text:
		case R.id.back:
			this.finish();
			break;
		case R.id.close:
			if(linearLayout!=null)
				linearLayout.setVisibility(View.GONE);
			break;
		case R.id.up:
			if(options.getVisibility()==View.VISIBLE)
			{
				options.setVisibility(View.GONE);
			}
			else {
				options.setVisibility(View.VISIBLE);
			}
			if(frmeView.getVisibility()==View.VISIBLE)
			{
				frmeView.setVisibility(View.GONE);
			}
			else {
				frmeView.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.loc:
			if(HomeActivity.nowLocation==null)return;
			pt=new GeoPoint((int)(HomeActivity.nowLocation.getLatitude()*(1e6*AA.LV)),
					(int)(HomeActivity.nowLocation.getLongitude()*(1e6*AA.LV)));
			if(pt==null)return;
			mayplaceLay.Fresh(new CNMKOverlayItem(pt,"1","1"));
			mMapView.getController().setCenter(pt);	
			mMapView.setZoomLevel(15);
			break;

		default:
			break;
		}
	}
	
	public String saveMyBitmap(Bitmap mBitmap,String fileName)  
	{
        String pathString=Environment
    			.getExternalStorageDirectory()+"/"+fileName + ".png";
		File f = new File(pathString); 
        FileOutputStream fOut = null;
        try {
                fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
                fOut.flush();
        } catch (IOException e) {
                e.printStackTrace();
        }
        try {
                fOut.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        return pathString;
	}

	@Override
	public void onSuccess(String result) 
	{
		
		
	}

	@Override
	public void onError(int httpcode) 
	{ 
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		switch (checkedId) 
		{
		case R.id.b1:
			FragmentShip ship=new FragmentShip();
			fm.beginTransaction().replace(R.id.fragment, ship).commit();
			bt1.setTextColor(Color.parseColor("#1766b1"));
			bt2.setTextColor(Color.parseColor("#901766b1"));
			bt3.setTextColor(Color.parseColor("#901766b1"));
			break;
		case R.id.b2:
			Fragment_Pot pot=new Fragment_Pot();
			fm.beginTransaction().replace(R.id.fragment, pot).commit();
			bt2.setTextColor(Color.parseColor("#1766b1"));
			bt1.setTextColor(Color.parseColor("#901766b1"));
			bt3.setTextColor(Color.parseColor("#901766b1"));
			break;
		case R.id.b3:
			FragmentWharf wharf=new FragmentWharf();
			fm.beginTransaction().replace(R.id.fragment, wharf).commit();
			bt3.setTextColor(Color.parseColor("#1766b1"));
			bt1.setTextColor(Color.parseColor("#901766b1"));
			bt2.setTextColor(Color.parseColor("#901766b1"));
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onGetAdminResult(CNMKAdminResult arg0, int arg1, boolean arg2, String arg3) 
	{ 
		
	}

	@Override
	public void onGetCategorySearchResult(CNMKCategoryResult arg0, int arg1, boolean arg2, String arg3) 
	{ 
		
	}

	@Override
	public void onGetCityResult(CNMKCityResult arg0, int arg1, boolean arg2, String arg3) 
	{ 
		
	}

	@Override
	public void onGetDriverRouteResult(CNMKDriveRouteResult arg0, int arg1, boolean arg2, String arg3) 
	{ 
		
	}

	@Override
	public void onGetEncryptionResult(CNMKEncryptionResult arg0, int arg1, boolean arg2, String arg3)
	{ 
		
	}

	@Override
	public void onGetGeoCodingResult(CNMKGeocodingResult arg0, int arg1, boolean arg2, String arg3) 
	{ 
		
	}

	@Override
	public void onGetPOIAroundResult(CNMKPOIAroundResult arg0, int arg1, boolean arg2, String arg3) 
	{ 
		
	}

	@Override
	public void onGetReverseGeoCodingResult(CNMKReverseGeocodingResult arg0, int arg1, boolean arg2, String arg3) 
	{ 
		
	}

	@Override
	public void onGetTransitCityResult(CNMKCityResult arg0, int arg1, boolean arg2, String arg3)
	{ 
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		
	}

	@Override
	public void onGetPoiResult(CNMKPoiResult result, int arg1, boolean arg2,String arg3) 
	{		 
		if(result==null)
		{
			Toast.makeText(this, "无搜索数据", Toast.LENGTH_LONG).show();
			return;
		} 
		searchList.clear();
		List<CNMKOverlayItem> list=new ArrayList<>();
		for ( int i = 0 ; i < result.getPoiCount() ; i++ )
	    {
			CNMKPoiInfo info=result.getPoi(i); 
			Map<String, Object> map=new HashMap<>();
			map.put("name", info.getName());
			map.put("address", info.getAddress());
			
			searchList.add(map);
			
			GeoPoint point=info.getGeoPoint();
			map.put("point", point);
			CNMKOverlayItem item= new CNMKOverlayItem(point,info.getName() ,String.valueOf(info.getDistance()));
			list.add(item);
	    }
		linearLayout.setVisibility(View.VISIBLE);
		adapter.notifyDataSetChanged();
		
		markOverLay.FreshALL(list);
		mMapView.setZoomLevel(13);
	}

	@Override
	public void onGetNewDriverRouteResult(CNMKNewDriveRouteResult arg0, int arg1, boolean arg2, String arg3) 
	{
		
	}

	@Override
	public void onGetStepWalkResult(CNMKStepWalkResult arg0, int arg1, boolean arg2, String arg3) 
	{ 
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) 
	{
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		
	}

	@Override
	public void afterTextChanged(Editable s)
	{
		if("".equals(s.toString()))
		{
			namesList.clear();
			pad.notifyDataSetChanged();
			nameListView.setVisibility(View.GONE);
			return;
		}
		HttpRequest hr=new HttpRequest(new HttpRequest.onResult() 
		{
			
			@Override
			public void onSuccess(String result) 
			{
				namesList.clear();
				try 
				{
					JSONObject object=new JSONObject(result);
					JSONArray arry=object.getJSONArray("obj");
					for(int i=0;i<arry.length();i++)
					{
						String s=arry.getString(i);
						namesList.add(s);
					}
				} catch (JSONException e)
				{
					e.printStackTrace();
				}
				nameListView.setVisibility(View.VISIBLE);
				pad.notifyDataSetChanged();
				
			}
			
			@Override
			public void onError(int httpcode) 
			{
				
			}
		});
		
		Map<String, Object> map=new HashMap<>();
		map.put("tip", s.toString()); 
		namesList.clear();
		nameListView.setVisibility(View.VISIBLE);
		AISOverLay.SearchNames(namesList, s.toString());
		pad.notifyDataSetChanged();
	}

}
