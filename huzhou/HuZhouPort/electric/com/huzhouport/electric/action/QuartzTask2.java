package com.huzhouport.electric.action;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.huzhouport.wharfWork.service.WharfWorkServer;

public class QuartzTask2 extends QuartzJobBean {
	
	private WharfWorkServer wharfWorkServer; 
	public void setWharfWorkServer(WharfWorkServer wharfWorkServer) {
		this.wharfWorkServer = wharfWorkServer;
	}
	@Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {
        System.out.println("-----------------定时程序-----------------------------");
        try {
        	//this.wharfWorkServer.ReturnValueByMonth();//还原码头作业的月定额
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println("-----------------定时程序-----end-----------------------");
    }
	
	
}
