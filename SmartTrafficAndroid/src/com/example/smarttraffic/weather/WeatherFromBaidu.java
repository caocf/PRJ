package com.example.smarttraffic.weather;

import java.util.List;

import android.graphics.Bitmap;

public class WeatherFromBaidu 
{
	private int error;
	private String status;
	private String date;
	private Bitmap bitmap;
	
	private List<WeatherOfCity> results;

	public Bitmap getBitmap()
	{
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap)
	{
		this.bitmap = bitmap;
	}
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






