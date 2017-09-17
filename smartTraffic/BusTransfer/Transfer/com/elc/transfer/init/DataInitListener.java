package com.elc.transfer.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.elc.transfer.modeling.Loading;


public class DataInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
//		Loading.load();

		// for(Line l:Loading.getLines())
		// {
		// System.out.println(l.getName());
		//
		// for(Station s:l.getUpStations())
		// System.out.println(" "+s.getName());
		// }
		//
		//
		// for(Station s:Loading.getStations())
		// System.out.println(s.getName()+":"+s.getLan()+" "+s.getLon());

//		Date start = new Date();
//
//		CacheManager.initDirectCache();
//		Loading.loadDirect();
//		
//		Date midDate=new Date();
//		
//		System.out.println("加载直达数据时间：" + (midDate.getTime() - start.getTime()));
//		System.out.println("直达总数："+Loading.directs.size());
//		
//		CacheManager.initOnceCache();
//
//		Date end = new Date();
//
//		System.out.println("直达数据计算时间：" + (end.getTime() - midDate.getTime()));
//
//		// 嘉兴一中
//		Request request = new Request();
//		request.setLon1(120.766572841759);
//		request.setLan1(30.7427218417712);
//
//		// 嘉兴火车站
//		request.setLon2(120.758085060509);
//		request.setLan2(30.7668991274433);
//
//		Reply reply = new TransferAction().transfer(request);
//
//		for (Transfer t : reply.getTransfers()) {
//			System.out.println("-------------");
//			for (BusResult b : t.getBuss())
//				System.out.println("起点：" + b.getStart() + b.getStartID()
//						+ " 终点：" + b.getEnd() + b.getEndID() + "方案："
//						+ b.getLineName());
//		}

//		//府南花园二期
//		Request request = new Request();
//		request.setLon1(120.74712690652);
//		request.setLan1(30.7319376715141);
//
//		// 新湖绿都
//		request.setLon2(120.762083698962);
//		request.setLan2(30.7629200000223);
//
//		Date start=new Date();
//		
//		new TransferAction().transfer(request);
//
//		Date end=new Date();
//		
//		System.out.println("换乘计算耗时："+(end.getTime()-start.getTime()));
	}
}
