package com.sts.weather.action;

import java.util.Date;

import com.sts.parking.action.PostDateFromSIWEI;
import com.sts.weather.model.baidu.WeatherFromBaidu;
import com.sts.weather.service.WeatherService;

public class WeatherAction 
{
//	Weather weather;
	String city;
	
	WeatherFromBaidu weatherFromBaidu;

	public static WeatherFromBaidu defaultWeather;
	public static Date lastUpdateTime;
 	
	public String getCity() {
		return city;
	}

	public WeatherFromBaidu getWeatherFromBaidu() {
		return weatherFromBaidu;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

//	public Weather getWeather() {
//		return weather;
//	}
//
//	public void setWeather(Weather weather) {
//		this.weather = weather;
//	}
	
//	private final String WEBSERVICE_URL="http://webservice.webxml.com.cn/WebServices/WeatherWebService.asmx";
//	private final String WEBSERVICE_NAMESPACE="http://WebXml.com.cn/";
//	private final String WEBSERVICE_METHOD="getWeatherbyCityName";
//	
//	private final String WEBSERVICE_PARAM_NAME="theCityName";
//	private final String WEBSERVICE_PARAM_VALUE="嘉兴";
	
	//http://m.weather.com.cn/data/101210301.html
	
//	private final String WEATHER_URL="http://www.weather.com.cn/data/cityinfo/101210301.html";
	
	private final String WEATHER_URL="http://api.map.baidu.com/telematics/v3/weather?location=%E5%98%89%E5%85%B4&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";
	
	public String TodayWeather()
	{
		
		if(lastUpdateTime==null)
			lastUpdateTime=new Date();
		
		Date tempDate=new Date();
		if((tempDate.getTime()-lastUpdateTime.getTime())<60*60*1000)
		{
			if(defaultWeather!=null)
			{
				weatherFromBaidu=defaultWeather;			
				return "success";
			}
		}
		
		String data=new PostDateFromSIWEI().GetData(WEATHER_URL);
		
		if(data!=null)
		{
			weatherFromBaidu=WeatherService.parseFromBaidu(data);
			defaultWeather=weatherFromBaidu;
			lastUpdateTime=new Date();
			System.out.println("1");
		}
	
		return "success";
	}
	
//	public String TodayWeather()
//	{
//		
//		if(lastUpdateTime==null)
//			lastUpdateTime=new Date();
//		
//		Date tempDate=new Date();
//		if((tempDate.getTime()-lastUpdateTime.getTime())<60*60*1000)
//		{
//			if(defaultWeather!=null)
//			{
//				weather=defaultWeather;			
//				return "success";
//			}
//		}
//		
//		
//		Map<String, Object> params=new HashMap<String, Object>();
//		
//		if(city!=null &&!city.equals(""))
//			params.put(WEBSERVICE_PARAM_NAME, city);
//		else
//		{
//			city=WEBSERVICE_PARAM_VALUE;
//			params.put(WEBSERVICE_PARAM_NAME, WEBSERVICE_PARAM_VALUE);
//		}
//		
//		Object object=Webservice.CallWebService(WEBSERVICE_URL, WEBSERVICE_NAMESPACE, WEBSERVICE_METHOD, params);
//		
//		if(object!=null && object instanceof SoapObject)
//		{
//			weather=WeatherService.parse((SoapObject)object);
//			defaultWeather=weather;
//			lastUpdateTime=new Date();
//			System.out.println("1");
//		}

//		return "success";
//	}
	
	public String TomorrowWeather()
	{
		return "success";
	}
	
	public String TheDayAferTomorrowWeather()
	{
		return "success";
	}
}
