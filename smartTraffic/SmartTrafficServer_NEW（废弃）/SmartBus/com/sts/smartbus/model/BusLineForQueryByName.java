package com.sts.smartbus.model;

import java.util.List;

public class BusLineForQueryByName 
{
	private List<BusLine> FuzzyLineList;
	private List<BusLine> LineList;
	
	public List<BusLine> getFuzzyLineList() {
		return FuzzyLineList;
	}
	public void setFuzzyLineList(List<BusLine> fuzzyLineList) {
		FuzzyLineList = fuzzyLineList;
	}
	public List<BusLine> getLineList() {
		return LineList;
	}
	public void setLineList(List<BusLine> lineList) {
		LineList = lineList;
	}
}
