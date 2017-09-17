package com.elc.transfer.cache;

import java.util.ArrayList;
import java.util.List;

import com.elc.transfer.entity.Direct;
import com.elc.transfer.entity.Line;
import com.elc.transfer.entity.Station;
import com.elc.transfer.modeling.Loading;
import com.elc.transfer.modeling.MysqlSessionFactoryManager;
import com.elc.transfer.util.TransferUtil;

public class CacheManager {

	public static void initDirectCache() {
		List<Station> allList = Loading.getStations();

		MysqlSessionFactoryManager.openSession();

		for (Station s : allList) {
			for (Station e : allList) {
				if (s.getId() != e.getId()) {
					List<Line> same = TransferUtil.crossLine(s, e);

					if (same.size() > 0) {
						for (Line l : same) {
							MysqlSessionFactoryManager
									.updateSQL("INSERT INTO direct(bt_startstation,bt_endstation,bt_line,bt_startname,bt_endname,bt_linename) VALUES("
											+ s.getId()
											+ ","
											+ e.getId()
											+ ","
											+ l.getId()
											+ ",'"
											+ s.getName()
											+ "','"
											+ e.getName()
											+ "','"
											+ l.getName() + "')");
						}
					}
				}
			}
		}

		MysqlSessionFactoryManager.closeSession();
	}

	public static boolean hasDirect(int s, int e) {
		for (Direct d : Loading.directs) {
			if (d.getStart() == s && d.getEnd() == e)
				return true;
		}

		return false;
	}

	public static List<Object[]> onceCross(int s, int e) {
		List<Object[]> result = new ArrayList<Object[]>();

		for (Direct ds : Loading.directs) {
			for (Direct dt : Loading.directs) {
				if (ds != dt && ds.getStart() == s && dt.getEnd() == e
						&& ds.getEnd() == dt.getStart()) {
					Object[] temp = new Object[10];
					temp[0] = ds.getStart();
					temp[1] = ds.getEnd();
					temp[2] = ds.getLine();
					temp[3] = ds.getStartName();
					temp[4] = ds.getEndName();
					temp[5] = ds.getLineName();
					temp[6] = dt.getEnd();
					temp[7] = dt.getEndName();
					temp[8] = dt.getLine();
					temp[9] = dt.getLineName();
					result.add(temp);
				}
			}
		}

		return result;
	}

//	public static void initOnceCache() {
//		List<Station> allList = Loading.getStations();
//
//		for (Station s : allList) {
//
//			for (Station e : allList) {
//
//				if (s.getId() != e.getId() && !s.getName().equals(e.getName())) {
//
//					if (!hasDirect(s.getId(), e.getId())) {
//						List<?> result = onceCross(s.getId(), e.getId());
//
//						if (result != null)
//							for (int i = 0; i < result.size(); i++) {
//
//								Object[] objects = (Object[]) (result.get(i));
//								MysqlSessionFactoryManager.openSession();
//								MysqlSessionFactoryManager
//										.updateSQL("INSERT into once(startstation,midiumstation,endstation,startstationname,midiumstationname,endstationname,firstline,secondline,firstlinename,secondlinename) VALUES ("
//												+ objects[0]
//												+ ","
//												+ objects[1]
//												+ ","
//												+ objects[6]
//												+ ",'"
//												+ objects[3]
//												+ "','"
//												+ objects[4]
//												+ "','"
//												+ objects[7]
//												+ "',"
//												+ objects[2]
//												+ ","
//												+ objects[8]
//												+ ",'"
//												+ objects[5]
//												+ "','"
//												+ objects[9] + "')");
//								MysqlSessionFactoryManager.closeSession();
//								System.out.println(s.getId());
//
//							}
//					}
//				}
//
//			}
//
//		}
//	}

	public static void initOnceCache() {
		List<Station> allList = Loading.getStations();

		for (Station s : allList) {
			MysqlSessionFactoryManager.openSession();

			for (Station e : allList) {

				if (s.getId() != e.getId() && !s.getName().equals(e.getName())) {

//					List<?> result = MysqlSessionFactoryManager
//							.querySQL("SELECT * from direct a where  a.bt_startstation="
//									+ s.getId()
//									+ " and a.bt_endstation="
//									+ e.getId());

					if (!hasDirect(s.getId(), e.getId())) {
						List<?> result = MysqlSessionFactoryManager
								.querySQL("SELECT a.bt_startstation p1,a.bt_endstation p2,a.bt_line p3,a.bt_startname p4,a.bt_endname p5,a.bt_linename p6,b.bt_endstation p7,b.bt_endname p8,b.bt_line p9,b.bt_linename p10 from direct a, direct b WHERE a.bt_endstation=b.bt_startstation and a.bt_startstation="
										+ s.getId()
										+ " and b.bt_endstation="
										+ e.getId());

						if (result != null)
							for (int i = 0; i < result.size(); i++) {

								Object[] objects = (Object[]) (result.get(i));

								MysqlSessionFactoryManager
										.updateSQL("INSERT into once(startstation,midiumstation,endstation,startstationname,midiumstationname,endstationname,firstline,secondline,firstlinename,secondlinename) VALUES ("
												+ objects[0]
												+ ","
												+ objects[1]
												+ ","
												+ objects[6]
												+ ",'"
												+ objects[3]
												+ "','"
												+ objects[4]
												+ "','"
												+ objects[7]
												+ "',"
												+ objects[2]
												+ ","
												+ objects[8]
												+ ",'"
												+ objects[5]
												+ "','"
												+ objects[9] + "')");

							}
					}
				}

				System.out.println(s.getId() + "-" + e.getId());
			}

			MysqlSessionFactoryManager.closeSession();
		}
	}

}
