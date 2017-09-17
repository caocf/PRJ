package com.elc.transfer.core;

import java.util.ArrayList;
import java.util.List;

import com.elc.transfer.entity.BusResult;
import com.elc.transfer.entity.Line;
import com.elc.transfer.entity.Reply;
import com.elc.transfer.entity.Request;
import com.elc.transfer.entity.Station;
import com.elc.transfer.entity.Transfer;
import com.elc.transfer.util.TransferUtil;

public class IndependenceArithmetic implements IArithmeticCore {

	public static final int RESULT_COUNT = 50;
	public static final int DEEP = 3;

	private List<Station> searchedStations;

	private void addSearchedStations(Station s) {
		if (searchedStations == null)
			searchedStations = new ArrayList<Station>();

		if (!searchedStations.contains(s))
			searchedStations.add(s);
	}

	@Override
	public Reply arithmeticTransfer(Request r) {

		Reply result = new Reply();

		// 搜索半径，步行和自行车的搜索半径
		int radius = r.getContainsBike() == 1 ? Params.BIKE_NEARBY_DISTANCE
				: Params.FOOTER_NEARBY_DISTANCE;

		// 获取起点附近点
		List<Station> starts = TransferUtil.queryNearbyStation(r.getLan1(),
				r.getLon1(), radius);

		// 获取终点附近点
		List<Station> ends = TransferUtil.queryNearbyStation(r.getLan2(),
				r.getLon2(), radius);

		for (Station s : starts)
			System.out.println("起点附近点：" + s.getName()+s.getId());

		for (Station s : ends)
			System.out.println("终点附近点：" + s.getName()+s.getId());

		// result = queryDirect(starts, ends);

		for (Station s : starts) {
			for (Station e : ends) {
				TempResult tempResult = new TempResult();
				tempResult.setDeep(DEEP);
				tempResult.setStation(s);

				result.getTransfers().addAll(
						queryMutiLine(tempResult, e, radius, DEEP)
								.getTransfers());
			}
		}

		return result;
	}

	@Override
	public void saveResultToCache(Reply reply) {

	}

	public Reply queryDirect(List<Station> s, List<Station> e) {
		Reply reply = new Reply();

		for (Station start : s) {
			for (Station end : e) {
				List<Line> lines = TransferUtil.crossLine(start, end);

				if (lines.size() > 0) {
					for (Line same : lines) {
						BusResult busResult = new BusResult();
						busResult.setStart(start.getName());
						busResult.setStartID(start.getId());
						busResult.setEnd(end.getName());
						busResult.setEndID(end.getId());
						busResult.setLineName(same.getName());

						Transfer transfer = new Transfer();
						transfer.getBuss().add(busResult);

						reply.getTransfers().add(transfer);
					}
				}
			}
		}

		return reply;
	}

	public Reply queryMutiLine(TempResult s, Station e, int radius, int deep) {
		Reply reply = new Reply();

		if (deep <= 0)
			return reply;

		// 获取起点附近点
		// List<Station> starts = TransferUtil.queryNearbyStation(s.getStation()
		// .getLan(), s.getStation().getLon(), radius);

		List<Station> starts = new ArrayList<Station>();
		starts.add(s.getStation());

		addSearchedStations(e);

		for (Station start : starts) {
			List<Line> lines = TransferUtil.crossLine(start, e);

			addSearchedStations(start);

			if (lines.size() > 0) {
				for (Line same : lines) {

					System.out.println("--------start-------------");
					System.out.println("deep=" + deep);

					Transfer transfer = new Transfer();

					BusResult busResult = new BusResult();
					busResult.setStart(start.getName());
					busResult.setStartID(start.getId());
					busResult.setEnd(e.getName());
					busResult.setEndID(e.getId());
					busResult.setLineName(same.getName());

					System.out.println(start.getName()+start.getId() + "-" + e.getName()+e.getId()
							+ "  : " + same.getName()+same.getDirect());

					transfer.getBuss().add(busResult);

					TempResult temp = s;
					while (true) {
						if (temp.getParentLine() == null)
							break;
						else {
							
							String destAddr=temp.getStation().getName()+temp.getStation().getId();
							
							temp = temp.getParentLine();

							BusResult btemp = new BusResult();
							btemp.setStart(temp.getStation().getName());
							btemp.setStartID(temp.getStation().getId());

							btemp.setLineName(temp.getLine() == null ? ""
									: temp.getLine().getName());
							btemp.setDirect(""+(temp.getLine() == null ? ""
									: temp.getLine().getDirect()));

							System.out.println(btemp.getStart()+btemp.getStartID()
									+ "-" + destAddr + "  : "
									+ btemp.getLineName()+btemp.getDirect());

							transfer.getBuss().add(btemp);

						}
					}

					reply.getTransfers().add(transfer);

					System.out.println("--------end-------------");
					System.out.println("");

				}
			}

		}

		for (Station start : starts)
			for (Line l : start.getLines()) {
				for (Station st2 : l.getStations()) {

					if (searchedStations.contains(st2))
						continue;

					TempResult tempResult = new TempResult();
					tempResult.setDeep(deep - 1);
					tempResult.setStation(st2);
					s.setLine(l);
					tempResult.setParentLine(s);

					reply.getTransfers().addAll(
							queryMutiLine(tempResult, e, radius, deep - 1)
									.getTransfers());
				}

			}

		return reply;
	}
}

/**
 * 结果临时缓存类
 * 
 * @author Administrator
 * 
 */
class TempResult {
	private Station station;
	private Line line;

	private int deep;
	private TempResult parentLine;

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public int getDeep() {
		return deep;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public TempResult getParentLine() {
		return parentLine;
	}

	public void setParentLine(TempResult parentLine) {
		this.parentLine = parentLine;
	}
}
