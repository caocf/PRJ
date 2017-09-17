package com.sts.smartbus.utl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sts.smartbus.model.BikeStation;

public class mapValueComparator implements Comparator<Map.Entry<Zone, List<BikeStation>>>
{
	@Override
	public int compare(Entry<Zone, List<BikeStation>> o1,
			Entry<Zone, List<BikeStation>> o2) {
		return o1.getValue().size()-o2.getValue().size();
	}
}