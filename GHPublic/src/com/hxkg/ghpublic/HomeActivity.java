package com.hxkg.ghpublic;
import net.hxkg.network.MService;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle; 
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ab.activity.AbActivity; 
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearch.OnWeatherSearchListener;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.amap.map3d.demo.util.ToastUtil;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.position.PositionActivity;

public class HomeActivity extends AbActivity implements  OnWeatherSearchListener
{	
	public static String city=""; 
	private SharedPreferences preferences;
	private Button cityButton;//城市选择按钮 
	/**
	 * 天气相关
	 * */
	private WeatherSearchQuery mquery;
	private WeatherSearch mweathersearch;
	private LocalWeatherLive weatherlive; 
	
	private long exitTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_home); 		
		/*启动推送服务*/
		initService();
		//获取上次选择的城市
		preferences= getSharedPreferences("Data", 0);
		city=preferences.getString("cityname", "杭州");
		
		cityButton=(Button) this.findViewById(R.id.ivTitleBtnLeft);	
		cityButton.setOnClickListener(new View.OnClickListener() 
		{			
			@Override
			public void onClick(View arg0) 
			{
				//这里替换为城市选择页面
				Intent intent = new Intent(); 
				intent.setClass(HomeActivity.this,PositionActivity.class);
				intent.putExtra("city", city);
				startActivityForResult(intent, 1);
			}
		});
		//根据用户类型设置主视图
		FragmentManager fragmentManager = getFragmentManager();
		switch(Integer.valueOf(SystemStatic.usertypeId))
		{
		case 0://游客
		{
			fragmentManager.beginTransaction().replace(R.id.fragment_content, new MainFragmentVisitor()) 
			.replace(R.id.fragment_optionbar, new OptionFragmentShipAndWharf()).commit();
			break;
		}
		case 1://船户
		{
			fragmentManager.beginTransaction().replace(R.id.fragment_content, new MainFragmentShip()) 
			.replace(R.id.fragment_optionbar, new OptionFragmentShipAndWharf()).commit();
			break;
		}
		case 2://企业		
		{
			fragmentManager.beginTransaction().replace(R.id.fragment_content, new MainFragmentCoastal()) 
			.replace(R.id.fragment_optionbar, new OptionFragmentShipAndWharf()).commit();
			break;
		}
		}
		
		searchliveweather() ;
	}
	/**
	 * 启动推送消息服务服务
	 */
	private void initService() 
	{
		Intent intent = new Intent(HomeActivity.this, MService.class);  
		// 启动服务  
		startService(intent);  
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		super.onSaveInstanceState(outState);
	}
	@Override
	protected void onResume()
	{
		super.onResume();

		//设置城市text
		cityButton.setText(city); 

		//天气查询		
		searchliveweather();
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode==1001)
		{
			city=data.getStringExtra("city");
			cityButton.setText(city);
			
			SharedPreferences.Editor edit=preferences.edit();
			edit.putString("cityname", city);
			edit.commit();
		}
		
	}

	@Override  
	protected void onDestroy()
	{  
		super.onDestroy();  
		//保存选择的城市
		SharedPreferences.Editor edit=preferences.edit();
		edit.putString("cityname", city);
		edit.commit();
	} 


	/**
	 * 按键点击回调事件,当按下回退键时显示是否退出
	 */

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) 
	{
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getRepeatCount() == 0) {
			if (System.currentTimeMillis() - exitTime > 3000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else
			{
				finish();
			}
			return false;
		}
		return super.dispatchKeyEvent(event);
	}
	/**
	 * 查新实时天气
	 * */
	private void searchliveweather() 
	{
		mquery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);//检索参数为城市和天气类型，实时天气为1、天气预报为2
		mweathersearch=new WeatherSearch(this);
		mweathersearch.setOnWeatherSearchListener(this);
		mweathersearch.setQuery(mquery);
		mweathersearch.searchWeatherAsyn(); //异步搜索   
	}
	@Override
	public void onWeatherForecastSearched(LocalWeatherForecastResult arg0, int arg1) 
	{

	}
	@Override
	public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult ,int rCode) 
	{
		View v = getLayoutInflater().inflate(R.layout.framelayout_tianqi, null);
		FrameLayout weatherLayout=(FrameLayout) this.findViewById(R.id.weatherviewcontaner); 
		TextView wendu = (TextView) v.findViewById(R.id.wendu);
		TextView tianqi = (TextView) v.findViewById(R.id.tianqi);
		TextView shijian = (TextView) v.findViewById(R.id.shijian);
		if (rCode == 1000) {
			if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
				weatherlive = weatherLiveResult.getLiveResult();
				shijian.setText("更新于: "+weatherlive.getReportTime().substring(5, 16));
				tianqi.setText(weatherlive.getWeather()+"  "+weatherlive.getWindDirection()+"风   "+weatherlive.getWindPower()+"级");
				wendu.setText(weatherlive.getTemperature()+"℃");
				/**
				 * 天气
				 * */

				if(weatherlive.getWeather().equals("晴")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww0);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}else if(weatherlive.getWeather().equals("多云")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww1);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("阴")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww2);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("阵雨")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww3);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("雷阵雨")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww4);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("雷阵雨伴有冰雹")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww5);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("雨夹雪")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww6);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("小雨")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww7);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("中雨")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww8);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("大雨")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww9);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("暴雨")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww10);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("大暴雨")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww10);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("特大暴雨")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww10);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("阵雪")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww13);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("小雪")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww14);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("中雪")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww15);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("大雪")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww16);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("暴雪")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww17);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("雾")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww18);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("冻雨")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww19);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("沙尘暴")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww20);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("浮尘")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww29);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("扬沙")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww30);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("强沙尘暴")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww30);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				else if(weatherlive.getWeather().equals("霾")){
					Drawable drawable= getResources().getDrawable(R.drawable.ww50);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					tianqi.setCompoundDrawables(drawable, null, null, null);  
					tianqi.setCompoundDrawablePadding(10);//设置图片和text之间的间距   
				}
				weatherLayout.addView(v);
			}else {
				Toast.makeText(getApplicationContext(), "天气加载失败", Toast.LENGTH_SHORT).show();
			}
		}else {
			ToastUtil.showerror(HomeActivity.this, rCode);
		} 
	}	
}
