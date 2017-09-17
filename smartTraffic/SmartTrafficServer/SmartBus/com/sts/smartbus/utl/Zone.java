package com.sts.smartbus.utl;

import com.sts.smartbus.model.BikeStation;

public class Zone {
	
	public Zone()
	{
		
	}
	
	public Zone(BikeStation s,BikeStation e)
	{
		start=s;
		end=e;
	}
	
	BikeStation start;
	BikeStation end;
	public BikeStation getStart() {
		return start;
	}
	public void setStart(BikeStation start) {
		this.start = start;
	}
	public BikeStation getEnd() {
		return end;
	}
	public void setEnd(BikeStation end) {
		this.end = end;
	}
	
	@Override
	public String toString() {
		return "起点："+start.toString()+" 终点："+end.toString();
	}
	
}
