package com.huzhouport.electric.action;

import java.text.SimpleDateFormat;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.huzhouport.wharfSelect.service.WharfSelectService;

public class QuartzTask extends QuartzJobBean {
	
	private WharfSelectService wharfSelectService;
	public void setWharfSelectService(WharfSelectService wharfSelectService) {
		this.wharfSelectService = wharfSelectService;
	}
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	@Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {
        System.out.println("-----------------定时程序-----------------------------");
        try {
        	//GetWbContent();//获取定期签证船舶信息并保存数据库
        	//this.wharfSelectService.addAllPortsFromWebService();//获取码头信息并保存数据库
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println("-----------------定时程序-----end-----------------------");
    }
	/*private AnalysisData ad=new AnalysisData();
	 * private FixedTermReportService fixedTermReportService;	
	public void setFixedTermReportService(
			FixedTermReportService fixedTermReportService) {
		this.fixedTermReportService = fixedTermReportService;
	}
    // webservicde
	public void GetWbContent() throws Exception {
		String XmlString = GetWSByAxis2.sendXmlByhuzGetDQShip(GlobalVar.HOST_CONNECT_HUZGETDQSHIP_TIME,
				GlobalVar.HOST_READ_HUZGETDQSHIP_TIME);
		// 写入数据库
		if(XmlString!=null){
		List<Map<String, String>> list = ad.AnalysisOfXML(XmlString, "Item");
		List<FixedTermReport> list_F = new ArrayList<FixedTermReport>();
		
		for (int i = 0; i < list.size(); i++) {
			FixedTermReport fixedTermReport = new FixedTermReport();
			fixedTermReport.setShipName(list.get(i).get("ShipNum"));
			fixedTermReport.setStartingPort(list.get(i).get("fromPort"));
			fixedTermReport.setArrivalPort(list.get(i).get("destPort"));
			fixedTermReport.setStartTime(df.parse(list.get(i).get(
					"dqqxq").toString()));
			fixedTermReport.setEndTime(df.parse(list.get(i).get(
					"dqqxz").toString()));
			list_F.add(fixedTermReport);
			
		}
		this.fixedTermReportService.AddFixItemByWb(list_F);
		}
	}*/
	
}
