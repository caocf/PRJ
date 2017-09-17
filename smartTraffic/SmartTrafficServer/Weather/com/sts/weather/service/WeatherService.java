package com.sts.weather.service;

import net.sf.json.JSONObject;

import org.ksoap2.serialization.SoapObject;

import com.alibaba.fastjson.JSON;
import com.sts.util.DoString;
import com.sts.weather.model.Weather;
import com.sts.weather.model.Weather2;
import com.sts.weather.model.baidu.WeatherFromBaidu;

public class WeatherService 
{
	public static Weather parse(SoapObject object)
	{
		Weather weather=new Weather();
		
		try 
		{
			weather.setCity(object.getProperty(1).toString());
			weather.setProvince(object.getProperty(0).toString());
			weather.setTemperature(object.getProperty(5).toString());
			weather.setWeather(object.getProperty(6).toString().split(" ")[1]);
			weather.setWind(object.getProperty(7).toString());
			weather.setPicID(Integer.valueOf(DoString.splitSpecial(object.getProperty(9).toString(), '.')));
			weather.setInfo(object.getProperty(11).toString());
		} 
		catch (Exception e) 
		{
		}
		
		return weather;
	}
	
	public static Weather parse(String data)
	{
		Weather weather=new Weather();
		
		JSONObject jsonObject=JSONObject.fromObject(data);
		jsonObject=jsonObject.getJSONObject("weatherinfo");
		
		weather.setCity(jsonObject.getString("city"));
		weather.setProvince("");
		weather.setTemperature(jsonObject.getString("temp2")+jsonObject.getString("temp1"));
		weather.setWeather(jsonObject.getString("weather"));
		weather.setWind("");
//		weather.setPicID(jsonObject.getInt("img2"));
		weather.setInfo("");
		
		return weather;
	}
	
	public static WeatherFromBaidu parseFromBaidu(String data)
	{
		WeatherFromBaidu weather=new WeatherFromBaidu();
		
		weather=JSON.parseObject(data, WeatherFromBaidu.class);
		
		return weather;
	}
	
	public static Weather2 parseWeather2(String data)
	{
		return JSON.parseObject(com.alibaba.fastjson.JSONObject.parseObject(data).getJSONObject("retData").toJSONString(),Weather2.class);
	}
}
