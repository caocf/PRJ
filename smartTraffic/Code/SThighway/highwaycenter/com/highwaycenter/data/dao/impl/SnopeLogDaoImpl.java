package com.highwaycenter.data.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.data.dao.SnopeLogDao;
import com.highwaycenter.data.model.HSnopeLog;
import com.highwaycenter.data.model.HSnopeStepLog;
import com.highwaycenter.data.model.HSnopeTransLog;

@Repository("snopelogdao")
public class SnopeLogDaoImpl extends BaseDaoDB<HSnopeLog> implements SnopeLogDao{

	@Override
	public void deleteTransLog(int jobid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStepLog(String transids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<HSnopeTransLog> selectTransloglist(int jobid) {
		String sql = "select ID_BATCH as idbatch,CHANNEL_ID as channelid,TRANSNAME as transname,STATUS as status,LINES_READ as readline,  "
				+ "LINES_WRITTEN as writeline,LINES_UPDATED as updateline,LINES_REJECTED as rejectline,ERRORS as errorsline,ENDDATE as startdate,"
				+ "LOGDATE as enddate ,JOB_ID as jobid "
				+ "from h_snope_tranlog where JOB_ID ="+jobid;
		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("idbatch",StandardBasicTypes.INTEGER);
			 q.addScalar("channelid",StandardBasicTypes.STRING);
			 q.addScalar("transname", StandardBasicTypes.STRING);
			 q.addScalar("status",StandardBasicTypes.STRING);
			 q.addScalar("readline", StandardBasicTypes.LONG);
			 q.addScalar("writeline", StandardBasicTypes.LONG);
			 q.addScalar("updateline", StandardBasicTypes.LONG);
			 q.addScalar("rejectline", StandardBasicTypes.LONG);
			 q.addScalar("errorsline", StandardBasicTypes.LONG);
			 q.addScalar("startdate", StandardBasicTypes.TIMESTAMP);
			 q.addScalar("enddate", StandardBasicTypes.TIMESTAMP);
			 q.addScalar("jobid",StandardBasicTypes.INTEGER);
			
			 q.setResultTransformer(Transformers.aliasToBean(HSnopeTransLog.class));
			 List<HSnopeTransLog> lists = q.list();
			 return q.list();
			} catch (Exception e) {
	         	e.printStackTrace();
		       throw e;
	}
		

	}

	@Override
	public List<HSnopeLog> selectJobloglist(String startdate,String enddate,String jobname) { 
		String hql = String.format("from HSnopeLog where startdate > '%s' and startdate < '%s' and jobname='%s' and status <> -1",startdate,enddate,jobname);
		return (List<HSnopeLog>) super.find(new HQL(hql)).getData();
	}

	@Override
	public List<HSnopeStepLog> selectSteploglist(int ID_BATCH) {
		String sql = "select ID_BATCH as idbatch,CHANNEL_ID as channelid,LOG_DATE as logdate,TRANSNAME as transname,"
				+ "STEPNAME as stepname,STEP_COPY as stepcopy"
				+ ",LINES_READ as readline,  "
				+ "LINES_WRITTEN as writtenline,LINES_UPDATED as updateline,LINES_REJECTED as rejectline,ERRORS as errorsline "
				+ "from h_snope_steplog where ID_BATCH ="+ID_BATCH;
		System.out.println("sql"+sql);
		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("idbatch",StandardBasicTypes.INTEGER);
			 q.addScalar("channelid",StandardBasicTypes.STRING);
			 q.addScalar("transname", StandardBasicTypes.STRING);
			 q.addScalar("stepname",StandardBasicTypes.STRING);
			 q.addScalar("stepcopy", StandardBasicTypes.INTEGER);
			 q.addScalar("readline", StandardBasicTypes.LONG);
			 q.addScalar("writtenline", StandardBasicTypes.LONG);
			 q.addScalar("updateline", StandardBasicTypes.LONG);
			 q.addScalar("rejectline", StandardBasicTypes.LONG);
			 q.addScalar("errorsline", StandardBasicTypes.LONG);
		     q.addScalar("logdate",StandardBasicTypes.TIMESTAMP);
			
			 q.setResultTransformer(Transformers.aliasToBean(HSnopeStepLog.class));
			 List<HSnopeStepLog> lists = q.list();
			 return q.list();
			} catch (Exception e) {
	         	e.printStackTrace();
		       throw e;
	}
		
	}

	@Override
	public HSnopeLog selectLastRecord(String jobname) {
		List list = super.findOrderBy(new HSnopeLog(), "jobname", jobname,"enddate" , true).getData();
		if(list!=null&&list.size()>0){
			return (HSnopeLog) list.get(0);
		}else{
			return null;
		}
		//return (HSnopeLog) super.findUnique(new HQL(hql));
	}

	@Override
	public HSnopeLog seleteJobDayTotal(String starttime, String endtime,
			String jobname) {
		String sqlformat = "select SUM(TOTALLINEREAD) as totallineread,"
				+ "SUM(TOTALLINEWRITTEN) as totallinewritten,"
				+ "SUM(TOTALLINEUPDATED) as totallineupdate,"
				+ "SUM(TOTALLINEUPDATED) as totallinerejected,"
				+ "SUM(ERRORS) as  errors from h_snope_log "
				+ "where JOBNAME='%s' and STARTDATE >'%s' and STARTDATE<'%s'";
		String sql = String.format(sqlformat, jobname,starttime,endtime);
		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("totallineread",StandardBasicTypes.LONG);
			 q.addScalar("totallinewritten",StandardBasicTypes.LONG);
			 q.addScalar("totallineupdate", StandardBasicTypes.LONG);
			 q.addScalar("totallinerejected",StandardBasicTypes.LONG);
			 q.addScalar("errors", StandardBasicTypes.LONG);
			 q.setResultTransformer(Transformers.aliasToBean(HSnopeLog.class));
			 List list =  q.list();
			 if(list!=null&&list.size()>0){
				 return (HSnopeLog) q.list().get(0);
			 }else{
			     return null;
			 }
			} catch (Exception e) {
	         	e.printStackTrace();
	         	return null;
	}
	}

	@Override
	public long selectSuccessCount(String jobname, String starttime,
			int isSuccess) {
		String sqlfromat = "select count(*) from h_snope_log where JOBNAME='%s' ";
		String sql = String.format(sqlfromat, jobname);
		if(starttime!=null){
			sql = sql +" and STARTDATE > '"+starttime+"' ";
		}
		if(isSuccess==1){//成功率
			sql = sql +" and STATUS = '0'";
		}else{
			sql = sql +" and STATUS in ('0','-1')";
		}
		return ((BigInteger)super.findUnique(new SQL(sql))).intValue();
	}

	@Override
	public void updateResult(int jobid, String status, Long readall,
			Long writeall, Long updateall, Long errors, String enddate) {
		String sql = String.format("update h_snope_log set STATUS='%s' ,TOTALLINEREAD=%d,TOTALLINEWRITTEN"
				+ "=%d,TOTALLINEUPDATED=%d,ERRORS=%d,ENDDATE='%s' where JOBID=%d", 
				status,readall,writeall,updateall,errors,enddate,jobid);
		super.update(new SQL(sql));
		
	}

	@Override
	public int selectCurrentCountByname(String jobname) {
		String sql = String.format("select count(*) from h_snope_log where JOBNAME='%s' "
		+" and STATUS = '1' ",jobname);
		
		return ((BigInteger)super.findUnique(new SQL(sql))).intValue();
	}

	@Override
	public void updateTransLogJobid(int jobid, String channle_id) {
		String sql = String.format("update h_snope_tranlog set JOB_ID = %d where CHANNEL_ID = '%s'",
				jobid,channle_id);
		super.executeSql(new SQL(sql));
		
	}

	@Override
	public HSnopeTransLog selectTransRecord(String stepname, String channleid) {
		HSnopeTransLog  trans = new  HSnopeTransLog  ();	 
		String sql = String.format("select SUM(a.LINES_READ) as readline,SUM(a.LINES_WRITTEN) as writeline,SUM(a.LINES_UPDATED)"
				+ " as updateline,SUM(a.LINES_REJECTED) as rejectline,SUM(a.ERRORS) as errorsline"
				+ " from h_snope_steplog a , h_snope_tranlog b where a.ID_BATCH=b.ID_BATCH and b.CHANNEL_ID = '%s' "
				+ " and right(STEPNAME,1) = '%s' ",channleid,stepname);
		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("readline", StandardBasicTypes.LONG);
			 q.addScalar("writeline", StandardBasicTypes.LONG);
			 q.addScalar("updateline", StandardBasicTypes.LONG);
			 q.addScalar("rejectline", StandardBasicTypes.LONG);
			 q.addScalar("errorsline", StandardBasicTypes.LONG);
			
			
			 q.setResultTransformer(Transformers.aliasToBean(HSnopeTransLog.class));
			 List list =  q.list();
			 if(list!=null&&list.size()>0){
				 return (HSnopeTransLog) q.list().get(0);
			 }else{
			     return null;
			 }
			} catch (Exception e) {
	         	e.printStackTrace();
	         	return null;
	}
	}

	@Override
	public void updateTransLogTotal(HSnopeTransLog trans) {
		String sql = String.format("update h_snope_tranlog set JOB_ID = %d , LINES_READ=%d,LINES_WRITTEN"
				+ "=%d,LINES_UPDATED=%d,LINES_REJECTED=%d,ERRORS=%d  where CHANNEL_ID= '%s'", trans.getJobid(),
				trans.getReadline(),trans.getWriteline(),trans.getUpdateline(),trans.getRejectline(),
				trans.getErrorsline(),trans.getChannelid());
		super.update(new SQL(sql));
	}



	@Override
	public HSnopeLog selectRecord(String stepname, int jobid) {
		String sql = String.format("select SUM(a.LINES_READ) as totallineread,SUM(a.LINES_WRITTEN) as totallinewritten,"
				+ "SUM(a.LINES_UPDATED) as totallineupdate,SUM(a.LINES_REJECTED) as totallinerejected,SUM(a.ERRORS) as "
				+ "errors "
				+ " from h_snope_steplog a,h_snope_tranlog b where a.ID_BATCH =b.ID_BATCH and b.JOB_ID =%d "
				+ " and right(STEPNAME,1) = '%s' ",jobid,stepname);
		
		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("totallineread",StandardBasicTypes.LONG);
			 q.addScalar("totallinewritten",StandardBasicTypes.LONG);
			 q.addScalar("totallineupdate", StandardBasicTypes.LONG);
			 q.addScalar("totallinerejected",StandardBasicTypes.LONG);
			 q.addScalar("errors", StandardBasicTypes.LONG);
			 q.setResultTransformer(Transformers.aliasToBean(HSnopeLog.class));
			 List list =  q.list();
			 if(list!=null&&list.size()>0){
				 return (HSnopeLog) q.list().get(0);
			 }else{
			     return null;
			 }
			} catch (Exception e) {
	         	e.printStackTrace();
	         	return null;
	}
		
	}

	@Override
	public Timestamp selectJobEndTime(int jobid) {
		String sql = "select max(LOGDATE) from h_snope_tranlog where JOB_ID = "+jobid;
		return (Timestamp) super.findUnique(new SQL(sql));
	}

	@Override
	public void addJobError(int jobid) {
		String sql = "update h_snope_log set ERRORS =ERRORS+1 where JOBID = "+jobid;
		super.executeSql(new SQL(sql));
		
	}

	@Override
	public HSnopeLog selectJobById(int jobid) {
		return super.findUnique(new HSnopeLog(), "jobid",jobid);
		
	}

}
