package com.sts.weather.model.baidu;

import java.util.List;

public class WeatherFromBaidu 
{
	private int error;
	private String status;
	private String date;
	
	private List<WeatherOfCity> results;

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<WeatherOfCity> getResults() {
		return results;
	}

	public void setResults(List<WeatherOfCity> results) {
		this.results = results;
	}
}






