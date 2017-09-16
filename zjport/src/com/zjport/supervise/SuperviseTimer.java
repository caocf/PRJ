package com.zjport.supervise;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SuperviseTimer
{

	@Resource(name="superviseService")
	SuperviseService superviseService;

	@Scheduled(cron ="0 0 0/2 * * ?")
	public void bcShipInfo()
	{
		// 船舶相关信息补充
		this.superviseService.bcShipInfo();
	}

	@Scheduled(cron ="0 0/10 * * * ?")
	public void wxWarn()
	{
		// 危险品船舶报警
		this.superviseService.wxcbWarn();
	}

	@Scheduled(cron ="0 0/10 * * * ?")
	public void statusWarn()
	{
		// 船舶状态异常报警
		this.superviseService.statusWarn();
	}
}
