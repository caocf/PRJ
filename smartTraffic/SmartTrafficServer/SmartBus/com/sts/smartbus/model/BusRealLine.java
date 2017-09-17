package com.sts.smartbus.model;

import java.util.List;

public class BusRealLine extends BusLine
{
	List<BusLocation> BusLocationList;

	public List<BusLocation> getBusLocationList() {
		return BusLocationList;
	}

	public void setBusLocationList(List<BusLocation> busLocationList) {
		BusLocationList = busLocationList;
	}
	
}
