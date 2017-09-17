package com.sts.smartbus.utl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sts.smartbus.model.BikeStation;

public class Average {

	
	public List<BikeStation> average(BikeStation start, BikeStation end,List<BikeStation> data, int resultNum) {
		Zone zone = new Zone(start, end);

		List<BikeStation> result = search(data, resultNum, zone);

		return result;
	}

	public List<BikeStation> search(List<BikeStation> d, int num, Zone z) {
		if (num == 0)
			return new ArrayList<BikeStation>();
		if (d.size() <= num) {
			return d;
		}

		List<Zone> zs = zoneForFour(z);

		Map<Zone, List<BikeStation>> ll = new HashMap<Zone, List<BikeStation>>();
		for (int i = 0; i < zs.size(); i++)
			ll.put(zs.get(i), new ArrayList<BikeStation>());

		for (BikeStation gt : d) {
			for (int i = 0; i < zs.size(); i++) {
				if (isPointInZone(gt, zs.get(i))) {
					ll.get(zs.get(i)).add(gt);
				}
			}
		}

		ll = sortMapByValue(ll);
		
		List<BikeStation> result = new ArrayList<BikeStation>();

		int i = 0;
		for (Map.Entry<Zone, List<BikeStation>> dd : ll.entrySet()) {
			List<BikeStation> temp = search(dd.getValue(), (num - result.size())
					/ (zs.size() - i), dd.getKey());
			result.addAll(temp);
			i++;
		}

		return result;
	}

	public Map<Zone, List<BikeStation>> sort(
			Map<Zone, List<BikeStation>> d) {
		Map<Zone, List<BikeStation>> result = new HashMap<Zone, List<BikeStation>>();

		int kind = d.size();
		for (int i = 0; i < kind; i++) {
			Map.Entry<Zone, List<BikeStation>> j = findMin(d);
			result.put(j.getKey(), j.getValue());
			d.remove(j.getKey());
		}

		return result;
	}

	public Map.Entry<Zone, List<BikeStation>> findMin(
			Map<Zone, List<BikeStation>> d) {
		Map.Entry<Zone, List<BikeStation>> result = null;

		for (Map.Entry<Zone, List<BikeStation>> p : d.entrySet()) {
			if (result == null)
				result = p;

			if (result.getValue().size() > p.getValue().size())
				result = p;
		}

		return result;
	}

	public Map<Zone, List<BikeStation>> sortMapByValue(
			Map<Zone, List<BikeStation>> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<Zone, List<BikeStation>> sortedMap = new LinkedHashMap<Zone, List<BikeStation>>();
		List<Map.Entry<Zone, List<BikeStation>>> entryList = new ArrayList<Map.Entry<Zone, List<BikeStation>>>(
				map.entrySet());
		Collections.sort(entryList, new mapValueComparator());
		Iterator<Map.Entry<Zone, List<BikeStation>>> iter = entryList
				.iterator();
		Map.Entry<Zone, List<BikeStation>> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}

	public List<Zone> zoneForFour(Zone z) {
		List<Zone> result = new ArrayList<Zone>();

		BikeStation start = z.getStart();
		BikeStation end = z.getEnd();

		double lan1 = (z.getStart().getLatitude() + z.getEnd().getLatitude()) / 2;
		double lon1 = (z.getStart().getLongitude() + z.getEnd().getLongitude()) / 2;

		Zone z1 = new Zone(start, new BikeStation(lan1, lon1));
		result.add(z1);

		Zone z2 = new Zone(new BikeStation(start.getLatitude(), lon1),
				new BikeStation(lan1, end.getLongitude()));
		result.add(z2);

		Zone z3 = new Zone(new BikeStation(lan1, start.getLongitude()),
				new BikeStation(end.getLatitude(), lon1));
		result.add(z3);

		Zone z4 = new Zone(new BikeStation(lan1, lon1), end);
		result.add(z4);

		return result;
	}

	public boolean isPointInZone(BikeStation g, Zone z) {
		BikeStation start = z.getStart();
		BikeStation end = z.getEnd();
		if (g != null && start != null && end != null) {
			if (g.getLatitude() <= start.getLatitude()
					&& g.getLatitude() >= end.getLatitude()
					&& g.getLongitude() >= start.getLongitude()
					&& g.getLongitude() <= end.getLongitude())
				return true;
		}
		return false;
	}
}
