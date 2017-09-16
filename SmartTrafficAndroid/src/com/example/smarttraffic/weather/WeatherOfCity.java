package com.example.smarttraffic.weather;

import java.util.List;

public class WeatherOfCity
{
	private String currentCity;
	private int pm25;
	
	private List<WeatherIndex> index;
	private List<WeatherInfo> weather_data;
	
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public int getPm25() {
		return pm25;
	}
	public void setPm25(int pm25) {
		this.pm25 = pm25;
	}
	public List<WeatherIndex> getIndex() {
		return index;
	}
	public void setIndex(List<WeatherIndex> index) {
		this.index = index;
	}
	public List<WeatherInfo> getWeather_data() {
		return weather_data;
	}
	public void setWeather_data(List<WeatherInfo> weatherData) {
		weather_data = weatherData;
	}
	
	
	
}
