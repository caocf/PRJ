package com.highwaycenter.data.serviceimpl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.service.BaseService;
import com.highwaycenter.bean.ModuleState;
import com.highwaycenter.data.dao.DataDao;
import com.highwaycenter.data.dao.SnopeLogDao;
import com.highwaycenter.data.model.HSnopeLog;
import com.highwaycenter.data.model.HSnopeStepLog;
import com.highwaycenter.data.model.HSnopeTransLog;
import com.highwaycenter.data.service.DataService;

@Service("dataservice")
public class DataServiceImpl extends BaseService implements DataService{
	@Resource(name="datadao")
	private DataDao dataDao;
	@Resource(name="snopelogdao")
	private SnopeLogDao snopelogDao;
	private String[] moduleName={"通阻信息","公路站","交调信息","治超处罚数据","","市政府行政处罚平台"};

	
	@Override
	public BaseResult selectCurrentKettleState() {
		List statelist = new ArrayList<ModuleState>();
		ModuleState gl_module = new ModuleState(2,"公路站",-2);
		ModuleState tz_module = new ModuleState(1,"通阻信息",-2);
		ModuleState jd_module = new ModuleState(3,"交调信息",-2);
		ModuleState zc_module = new ModuleState(4,"治超处罚数据",-2);
		ModuleState jt_module = new ModuleState(5,"智慧交通综合数据库",-2);
		ModuleState xz_module = new ModuleState(6,"市政府行政处罚平台",-2);
			
		List<String> runlist = this.dataDao.selectRunTransname();
		for(String run:runlist){
			String mojc = run.substring(0, 2);
			System.out.println("模块名字&简称--"+run+"--"+mojc);
			System.out.println("qqq"+mojc.indexOf(Constants.MODULE_GONGLU));
			if(mojc.indexOf(Constants.MODULE_GONGLU)>-1){
				//是公路站
				gl_module.setCurrentstate(1);
				gl_module.setMessage("正在同步");
			}else if(mojc.indexOf(Constants.MODULE_TZXX)>-1){
				//是通阻信息
				tz_module.setCurrentstate(1);
				tz_module.setMessage("正在同步");
			}else if(mojc.indexOf(Constants.MODULE_XZZF)>-1){
				xz_module.setCurrentstate(1);
				xz_module.setMessage("正在同步");
			}else if(mojc.indexOf(Constants.MODULE_ZCCF)>-1){
				zc_module.setCurrentstate(1);
				zc_module.setMessage("正在同步");
			}
		}
		gl_module = selectLastRecord(gl_module);
		tz_module = selectLastRecord(tz_module);
		jd_module = selectModuleRecord(jd_module);
		xz_module = selectLastRecord(xz_module);
		zc_module = selectLastRecord(zc_module);
		//如果已经结束，则查询上次的记录
		statelist.add(0);
		statelist.add(1);
		statelist.add(2);
		statelist.add(3);
		statelist.add(4);
		statelist.add(5);
		statelist.set(0, tz_module);
		statelist.set(1, gl_module);
		statelist.set(2, jd_module);
		statelist.set(3, zc_module);
		statelist.set(4, jt_module);
		statelist.set(5, xz_module);
		
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setList(statelist);
		
		return result;
	}


	private ModuleState selectLastRecord(ModuleState module){
		
			HSnopeLog last =  this.snopelogDao.selectLastRecord(module.getModulename());
			if(last!=null){
			if(module.getCurrentstate()!=1){
				String state =last.getStatus();
				Integer stateint = Integer.valueOf(state);
				module.setCurrentstate(stateint);
			}
			module.setLasttime(last.getEnddate());// 0:同步成功，1：正在同步；-1：同步失败
            module.setMessage("上一次同步成功时间："+last.getEnddate());
			}else{
				module.setLasttime("无");
				module.setMessage("上一次同步成功时间：无");
			}
		return module;
	}


	@Override
	public BaseResult selectModuleXq(String type, String time) {
		try{
			if(time==null||time.trim().equals("")){
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				time = sdf.format(d);
			}
		  String startdate = time+" 00:00:00";
		  String enddate = time +" 23:59:59";
		List list = new ArrayList();
		String name = null;
		int typeint=0;
		try{
		    typeint = Integer.valueOf(type);
		}catch(Exception e){
			return new BaseResult(120,"参数类型错误");
		}
		
			name =moduleName[typeint-1];
			list = this.snopelogDao.selectJobloglist(startdate, enddate,name);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
				   HSnopeLog log = (HSnopeLog) list.get(i);
				   int jobid = log.getJobid();
				   //查询translist
				   List<HSnopeTransLog> translist = this.snopelogDao.selectTransloglist(jobid);
				   
				   if(translist!=null&&translist.size()>0){
					   for(int j=0;j<translist.size();j++){
						   HSnopeTransLog translog = translist.get(j);
						   String transname = translog.getTransname();
						   if(transname!=null&&!transname.trim().equals("")){
							   transname = translog.getTransname().substring(2);
						   }
						   translog.setTransname(transname);
						   if(translog.getStatus().equals("start")){
							   translog.setStatus_show("正在同步");
						   }else if(translog.getStatus().equals("stop")){
							   translog.setStatus_show("同步失败");
						   }else if(translog.getStatus().equals("end")){
							   translog.setStatus_show("同步成功");
						   }
						    int batchid = translog.getIdbatch();
						    List<HSnopeStepLog>  steplist = this.snopelogDao.selectSteploglist(batchid);
						    if(steplist!=null&&steplist.size()>0){
								   for(int q=0;q<steplist.size();q++){
									   HSnopeStepLog steplog = steplist.get(q);
									   steplog.setTransname(transname);
									   String stepname = translog.getTransname();
									   if(stepname!=null&&!stepname.trim().equals("")){
										   stepname = steplog.getStepname().substring(0, steplog.getStepname().length()-1);
									   }
									   steplog.setStepname(stepname);
									   steplist.set(q, steplog);
								   }
						    }
						    translog.setSteploglist(steplist);
						    translist.set(j, translog);
					   }
				  
					   log.setTransloglist(translist);
					   
				   }else  if(log.getJobname().equals("交调信息")){
					   log =  transCreat(log,"交调信息转换","交调信息转换输入","交调信息转换插入更新");
				   list.set(i, log);
				   }else if(log.getJobname().equals("市政府行政处罚平台")){
					   log =  transCreat(log,"市政府行政处罚","市政府行政处罚输入","市政府行政处罚插入更新");
				   }else if(log.getJobname().equals("治超处罚数据")){
					   log =  transCreat(log,"治超处罚数据","治超处罚数据输入","治超处罚数据插入更新");
				   }
				
			}
			
		}
		
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setList(list);
		return result;
		}catch(Exception e){
			e.printStackTrace();return null;
		}
	}

	/**
	 * 查询模块详情如果没有trans,则自己写入2条数据与job相同的转换与步骤数据
	 */
	private HSnopeLog transCreat(HSnopeLog log,String transName,String stepNameinsert,String stepNameupdate){
		 Timestamp timestamp = null;
		   //手动增加转换记录与步骤记录
		   if(log.getEnddate()!=null){
		       timestamp = Timestamp.valueOf(log.getEnddate());  
		   }
		   HSnopeStepLog stepLoginsert = new HSnopeStepLog(timestamp,transName,stepNameinsert,new Long(0),log.getTotallinewritten(),
				   log.getTotallineupdate(),log.getErrors());
		   HSnopeStepLog stepLog = new HSnopeStepLog(timestamp,transName,stepNameupdate,log.getTotallineread(),log.getTotallinewritten(),
				   log.getTotallineupdate(),log.getErrors());
		   List<HSnopeStepLog> liststep = new ArrayList<HSnopeStepLog>();
		   List<HSnopeTransLog> listtrans = new ArrayList<HSnopeTransLog>();
		   liststep.add(stepLoginsert);
		   liststep.add(stepLog);
		   HSnopeTransLog translog = new   HSnopeTransLog(transName,log.getTotallineread(),
				   log.getTotallinewritten(), log.getTotallineupdate(), 
				   log.getErrors(), log.getJobid(),
				   liststep,log.getStartdate(), log.getEnddate());
		   listtrans.add(translog);
		   log.setTransloglist(listtrans);
		return log;
	}
	
	
	/**
	 * 针对交调模块查找的状态及上次同步信息
	 * @param module
	 * @return
	 */
	private ModuleState selectModuleRecord(ModuleState module){
		int runcount = this.snopelogDao.selectCurrentCountByname(module.getModulename());
		if(runcount>0){
			module.setCurrentstate(1);
			module.setMessage("正在同步");
		}
		HSnopeLog last =  this.snopelogDao.selectLastRecord(module.getModulename());
		if(last!=null){
		if(module.getCurrentstate()!=1){
			String state =last.getStatus();
			Integer stateint = Integer.valueOf(state);
			module.setCurrentstate(stateint);
		}
		module.setLasttime(last.getEnddate());// 0:同步成功，1：正在同步；-1：同步失败
        module.setMessage("上一次同步成功时间："+last.getEnddate());
		}else{
			module.setLasttime("无");
			module.setMessage("上一次同步成功时间：无");
		}
	return module;
}
	@Override
	public BaseResult selectModuleLine(String moduletype, Integer xnumber) {
		List list = new ArrayList();
		Date dNow = new Date();   //当前时间
		String jobname = null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		String current = sdf.format(dNow);
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		Integer conclucate = (xnumber)*-1;
		calendar.add(Calendar.DAY_OF_MONTH, conclucate);//最初始的查询时间
		int typeint=0;
		try{
		    typeint = Integer.valueOf(moduletype);
		}catch(Exception e){
			return new BaseResult(120,"参数类型错误");
		}
		
			jobname =moduleName[typeint-1];
	
		
		for(int i=0;i<=xnumber-1;i++){//遍历前几天
			list.add(i);
			calendar.add(Calendar.DAY_OF_MONTH,1);
			Date datex = calendar.getTime(); 
			String searchdate = sdf.format(datex);
			System.out.println("查询时间：'"+conclucate+"'---"+searchdate);
			String starttime = searchdate+" 00:00:00";
			String endtime = searchdate+" 23:59:59";
			HSnopeLog logdate = this.snopelogDao.seleteJobDayTotal
					(starttime, endtime, jobname);
			if(logdate==null||logdate.getTotallinewritten()==null){
				logdate = new HSnopeLog();
				logdate.setTotallineread(new Long(0));
				logdate.setTotallinewritten(new Long(0));
				logdate.setTotallineupdate(new Long(0));
				logdate.setErrors(new Long(0));
			}
			list.set(i, logdate);
		}
		//最后加入当天的
		/*String starttime = current+" 00:00:00";
		String endtime = current+" 23:59:59";
		HSnopeLog logcurrent = this.snopelogDao.seleteJobDayTotal
				(starttime, endtime, jobname);
		list.add(xnumber-1);
		list.set(xnumber-1, logcurrent);*/
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setList(list);
		return result;
		
	}


	@Override
	public BaseResult selectSuccessRate(String starttime, String moduletype) {
		String jobname = null;
		try{
		    jobname = moduleName[Integer.valueOf(moduletype)-1];
		}catch(Exception e){
			System.out.println(e.getMessage());
			return new BaseResult(122,"参数类型错误或者参数值超出范围");
		}
		long successSum = this.snopelogDao.selectSuccessCount(jobname, starttime, 1);
		long sum = this.snopelogDao.selectSuccessCount(jobname, starttime, 0);
		long failsum = sum-successSum;
		System.out.println("成功次数："+successSum+"总次数："+sum);
		if(sum==0){
			String rate = "0%";
			BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
			Float[] objs={new Float(0),new Float(0)};
			result.setObj(rate);
			result.setObjs(objs);
			return result;
		}
		double x_double=successSum*1.0;  
		 double tempresult=x_double/sum*100;
		 double failrate = failsum*1.0/sum*100;
		
		 DecimalFormat df1 = new DecimalFormat("##0.00");    
		 String rate= df1.format(tempresult);
		 String failratestr = df1.format(failrate);
		 BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		 String objs[] = {rate,failratestr};
			//result.setObj(rate);
		 result.setObjs(objs);
			return result;
	}


}
