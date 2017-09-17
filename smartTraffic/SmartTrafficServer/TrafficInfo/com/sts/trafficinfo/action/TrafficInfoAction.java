package com.sts.trafficinfo.action;

import java.util.List;

import com.sts.common.model.Message;
import com.sts.common.util.MessageFactory;
import com.sts.trafficinfo.service.TrafficInfoService;

/**
 * 交通服务信息数据接口
 * @author Administrator
 *
 */
public class TrafficInfoAction {

	/*-----------------------注入service 类-----------------------------*/
	
	private TrafficInfoService trafficInfoService;
	
	public void setTrafficInfoService(TrafficInfoService trafficInfoService) {
		this.trafficInfoService = trafficInfoService;
	}
	
	/*-----------------------请求参数-----------------------------*/
	
	private int page;							//页	，从1开始；小于1时或默认时返回所有数据
	private int num;							//数，大于1，默认10
	private int zone;							//五县两区  代码待定，-1不区分区域
	private int type;							//交通点代码 -1全部, 1、服务器 2、加油站 2、收费站
	
	public void setPage(int page) {
		this.page = page;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setZone(int zone) {
		this.zone = zone;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	/*-----------------------返回内容-----------------------------*/
	
	private List<?> result;
	private Message message;
	
	public List<?> getResult() {
		return result;
	}
	public Message getMessage() {
		return message;
	}
	
	/*-----------------------具体action-----------------------------*/
	
	
	/**
	 * ic卡请求信息接口
	 * @return
	 */
	public String queryICcards()
	{
		result=trafficInfoService.queryICcards(zone, page, num);
		message=MessageFactory.createMessage(result);
		
		return "success";
	}
	
	/**
	 * 交通点位置信息接口（服务器、加油站等）
	 * @return
	 */
	public String queryTrafficPoints()
	{
		result=trafficInfoService.queryTrafficPoints(type, page, num);
		message=MessageFactory.createMessage(result);
		
		return "success";
	}
	
	/**
	 * 公交公司接口
	 * @return
	 */
	public String queryTaxiCompanies()
	{
		result=trafficInfoService.queryTaxiCompanies(page, num);
		message=MessageFactory.createMessage(result);
		
		return "success";
	}
	
	/**
	 * 火车票代售点接口
	 * @return
	 */
	public String queryTicketsAgents()
	{
		result=trafficInfoService.queryTicketsAgents(zone, page, num);
		message=MessageFactory.createMessage(result);
		
		return "success";
	}
	
	/**
	 * 机票订票热线接口
	 * @return
	 */
	public String queryTicketHotLines()
	{
		result=trafficInfoService.queryTicketHotLines(page, num);
		message=MessageFactory.createMessage(result);
		
		return "success";
	}
	
}
