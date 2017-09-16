package com.huzhouport.car.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huzhouport.car.model.CarApplication;
import com.huzhouport.car.service.CarService;
import com.huzhouport.illegal.service.impl.AbnormalpushService;
import com.huzhouport.user.model.User;
import com.meeting.service.MeetingService;

public class CarAction 
{
	CarService carService;
	MeetingService meetingService;
	private AbnormalpushService pushservice;//=new AbnormalpushService();
	
	
	public AbnormalpushService getPushservice() {
		return pushservice;
	}

	public void setPushservice(AbnormalpushService pushservice) {
		this.pushservice = pushservice;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	
	CarApplication carApplication;
	User user;
	List<?> list;
	int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CarApplication getCarApplication() {
		return carApplication;
	}

	public void setCarApplication(CarApplication carApplication) {
		this.carApplication = carApplication;
	}
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public String applyCar()
	{		
		carApplication.setAptime(new Date());
		carApplication.setStatus1id(1);
		carApplication.setStatus2id(1);
		carApplication.setMark1("");
		carApplication.setMark2("");
		carApplication.setIsread(1);//默认已读
		
		int depid=user.getPartOfDepartment();
		if(depid==263)//如果是局领导
		{
			carApplication.setApprover1id(user.getUserId());
			carApplication.setStatus1id(2);
			carApplication.setAptime1(new Date());
		}else
		{
			User header=meetingService.getDepHeader(user.getPartOfDepartment());
			if(header!=null)
			{
				carApplication.setApprover1id(header.getUserId());
				if(carApplication.getApprover1id()==carApplication.getUserid())
				{
					carApplication.setStatus1id(2);
					carApplication.setAptime1(new Date());
				}
			}
		}		
		
		//int officeid=meetingService.getOfficeID(user.getPartOfDepartment());
		carApplication.setApprover2id(2988);
		
		Date date1=new Date();
		Date date2=new Date();
		try {
			date1=simpleDateFormat.parse(d1);
			date2=simpleDateFormat.parse(d2);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		carApplication.setStarttime(date1);
		carApplication.setEndtime(date2);
		carService.applyCar( carApplication);		
		
		if(carApplication.getApprover1id()==carApplication.getUserid())//直接给办公室
		{
			JSONObject msg=new JSONObject();
			JSONArray ids=new JSONArray();
			ids.add(carApplication.getApprover2id());
			/*List<?> list=meetingService.findIDsByDepid(officeid);
			for(int i=0;i<list.size();i++)
			{
				int id=(Integer) list.get(i);
				ids.add(id);
			}*/				
			msg.put("ids",ids);
			msg.put("title", "您有新的用车审批");//待科长审批
			msg.put("tip", "");
			
			msg.put("mstype", 2);//用车消息
			msg.put("type", 2);//给审核者
			msg.put("status", 1);
			msg.put("id", carApplication.getId());
			String s1=msg.toString();				
			pushservice.pushAbnormal(s1);
		}else//给科长
		{
			JSONObject msg=new JSONObject();
			JSONArray ids=new JSONArray();
			ids.add(carApplication.getApprover1id());				
			msg.put("ids",ids);
			msg.put("title", "您有新的用车审核");//待科长审批
			msg.put("tip", "");
			
			msg.put("mstype", 2);//用车消息
			msg.put("type", 2);//给审核者
			msg.put("status", 1);
			msg.put("id", carApplication.getId());
			String s1=msg.toString();				
			pushservice.pushAbnormal(s1);
		}			
			
		
		return "success";
	}
	
	public String findMyCarAps()
	{
		list=carService.findMyCarAps(user.getUserId(),status);
		return "success";		
	}
	public String findMyCarCheck1()
	{
		list=carService.findMyCarCheck1(carApplication.getApprover1id(),status);
		return "success";		
	}
	public String findMyCarCheck2()
	{
		list=carService.findMyCarCheck2(carApplication.getApprover2id(),status);
		return "success";		
	}
	
	int checktype;
	public int getChecktype() {
		return checktype;
	}

	public void setChecktype(int checktype) {
		this.checktype = checktype;
	}

	public String commitCarCheck()
	{
		CarApplication car=carService.findCarApplicationByID(carApplication.getId());
		if(car==null)return "error";		
		
		car.setStatus1id(carApplication.getStatus1id());
		car.setMark1(carApplication.getMark1());
		car.setStatus2id(carApplication.getStatus2id());
		car.setMark2(carApplication.getMark2());		
		
		if(checktype==1)//科长审批
		{
			car.setAptime1(new Date());
		}else//办公室审批
		{
			car.setAptime2(new Date());
		}
		
		
		if(pushservice!=null)
		{
			JSONObject msg=new JSONObject();
			JSONArray ids=new JSONArray();
			
			if(checktype==1)//科长审批
			{
				if(carApplication.getStatus1id()==3)//驳回
				{
					ids.add(car.getUserid());
					msg.put("ids", ids);
					msg.put("title", "您有新的用车审批结果");
					msg.put("tip", "申请已驳回");
					
					msg.put("mstype", 2);//用车消息
					msg.put("type", 1);//给申请者
					msg.put("status", 3);
					msg.put("id", car.getId());
					pushservice.pushAbnormal(msg.toJSONString());
					car.setIsread(0);
				}else//通过
				{
					/*List<?> list=meetingService.findIDsByDepid(car.getApprover2id());
					for(int i=0;i<list.size();i++)
					{
						int id=(Integer) list.get(i);
						ids.add(id);
					}*/	
					ids.add(car.getApprover2id());
					msg.put("ids",ids);
					msg.put("title", "您有新的用车审批");//待办公室审批
					msg.put("tip", "");
					
					msg.put("mstype", 2);//用车消息
					msg.put("type", 2);//给审核者
					msg.put("status", 1);
					msg.put("id", car.getId());
					String s1=msg.toString();				
					pushservice.pushAbnormal(s1);
				}
			}else//办公室审批
			{
				ids.add(car.getUserid());
				msg.put("ids", ids);
				msg.put("title", "您有新的用车审批结果");
				car.setIsread(0);
				
				if(carApplication.getStatus1id()==3)//办公室驳回
				{
					msg.put("tip", "申请已驳回");
					msg.put("status", 3);
				}else//办公室通过
				{
					msg.put("tip", "申请已驳回");
					msg.put("status", 2);
				}
				msg.put("mstype", 2);//用车消息
				msg.put("type", 1);//给申请者				
				msg.put("id", car.getId());
				pushservice.pushAbnormal(msg.toJSONString());
			}
		}
		
		carService.commitCheck(car);
		carApplication=car;
		return "success";
	}
	
	List<?> allcars;
	String d1,d2;
	
	public String getD1() {
		return d1;
	}

	public void setD1(String d1) {
		this.d1 = d1;
	}

	public String getD2() {
		return d2;
	}

	public void setD2(String d2) {
		this.d2 = d2;
	}

	public List<?> getAllcars() {
		return allcars;
	}

	public void setAllcars(List<?> allcars) {
		this.allcars = allcars;
	}

	public String findCarInfo()
	{
		list=carService.findUnvaliableCars(d1, d2);
		allcars=carService.findAllCars();
		return "success";
	}
	
	int ap;
	int mycheck;
	int officecheck;
	
	public int getAp() {
		return ap;
	}

	public void setAp(int ap) {
		this.ap = ap;
	}

	public int getMycheck() {
		return mycheck;
	}

	public void setMycheck(int mycheck) {
		this.mycheck = mycheck;
	}

	public int getOfficecheck() {
		return officecheck;
	}

	public void setOfficecheck(int officecheck) {
		this.officecheck = officecheck;
	}

	public String tipCount()
	{
		ap=carService.countAp(user.getUserId());
		mycheck=carService.countMyCheck(user.getUserId());
		officecheck=carService.countOfficeCheck(user.getUserId());
		return "success";
	}

}
