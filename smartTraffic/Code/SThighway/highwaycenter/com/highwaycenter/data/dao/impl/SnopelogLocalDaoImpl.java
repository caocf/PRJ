package com.highwaycenter.data.dao.impl;

import java.sql.ResultSet;
import java.sql.Timestamp;

import com.highwaycenter.common.dao.impl.LocalConnectionDB;
import com.highwaycenter.data.dao.SnopelogLocalDao;
import com.highwaycenter.data.model.HSnopeLog;
import com.highwaycenter.data.model.HSnopeTransLog;

public class SnopelogLocalDaoImpl extends LocalConnectionDB implements SnopelogLocalDao{

	@Override
	public int updateTranlog(int jobid, String channleid) {
		String sql = "update h_snope_tranlog set JOB_ID =? where CHANNEL_ID = ? ";
	return super.executeUpdate(sql, jobid,channleid);
	}

	@Override
	public int selectExitRun(String tranname) {
		String sql = "select count(*) from h_snope_tranlog where left(TRANSNAME,2)=? and "
				     + "STATUS ='start'";
		Long count = (Long) super.executeQuerySingle(sql, tranname);
		if(count!=null){
		 return  count.intValue();
		}else{
			return 0;
		}
	} 

	@Override
	public Object selectTransKeyValue(String key, String channleid) {
		String sql = String.format("select %s  from h_snope_tranlog where  CHANNEL_ID = ? ",key);
		
		return super.executeQuerySingle(sql, channleid);
	}

	@Override
	public int addJobError(int jobid) {
		String sql = "update h_snope_log set ERRORS =ERRORS+1 where JOBID = ? ";
		return super.executeUpdate(sql,jobid);
	}

	@Override
	public HSnopeLog selectRecord(String stepname, int jobid) {
		 HSnopeLog  log = new  HSnopeLog  ();	 
		String sql = "select SUM(a.LINES_READ) ,SUM(a.LINES_WRITTEN),SUM(a.LINES_UPDATED),SUM(a.LINES_REJECTED),SUM(a.ERRORS)"
				+ " from h_snope_steplog a,h_snope_tranlog b where a.ID_BATCH =b.ID_BATCH and b.JOB_ID =?"
				+ " and right(STEPNAME,1) = ? ";
		try{
		 ResultSet rs = super.executeQueryRS(sql, jobid,stepname);
		 if(rs==null){
			 log.setTotallineread(new Long(0));
			 log.setTotallinewritten(new Long(0));
			 log.setTotallineupdate(new Long(0));
			 log.setTotallinerejected(new Long(0));
			 log.setErrors(new Long(0));
			 
		 }else
		    if(rs.next()) {
			 log.setTotallineread(rs.getLong(1));
			 log.setTotallinewritten(rs.getLong(2));
			 log.setTotallineupdate(rs.getLong(3));
			 log.setTotallinerejected(rs.getLong(4));
			 log.setErrors(rs.getLong(5));
			  }else{
				  log.setTotallineread(new Long(0));
					 log.setTotallinewritten(new Long(0));
					 log.setTotallineupdate(new Long(0));
					 log.setTotallinerejected(new Long(0));
					 log.setErrors(new Long(0));
					// return log;
			  }
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return log;
		
	}

	@Override
	public Object selectJobKeyValue(String key, String jobid) {
         String sql =String.format( "select %s  from h_snope_log where  JOBID = ? ",key);
		return super.executeQuerySingle(sql, jobid);
	}

	@Override
	public HSnopeLog selectJobById(int jobid) {
		 HSnopeLog  log = new  HSnopeLog  ();	
		 String sql = "select * from h_snope_log where JOBID=?";
		try{
			 ResultSet rs = super.executeQueryRS(sql,jobid);
			 if(rs==null){
				 log.setTotallineread(new Long(0));
				 log.setTotallinewritten(new Long(0));
				 log.setTotallineupdate(new Long(0));
				 log.setTotallinerejected(new Long(0));
				 log.setErrors(new Long(0));
				 return log;
			 }else
			    if(rs.next()) {
				 log.setJobid(rs.getInt(1));
				 log.setJobname(rs.getString(2));
				 log.setStatus(rs.getString(3));
				 log.setTotallineread(rs.getLong(4));
				 log.setTotallinewritten(rs.getLong(5));
				 log.setTotallineupdate(rs.getLong(6));
				 log.setTotallinerejected(rs.getLong(7));
				 log.setErrors(rs.getLong(8));
				 log.setStartdate(rs.getString(9));
				 log.setEnddate(rs.getString(10));
				 return log;
				  }else{
					  log.setTotallineread(new Long(0));
						 log.setTotallinewritten(new Long(0));
						 log.setTotallineupdate(new Long(0));
						 log.setTotallinerejected(new Long(0));
						 log.setErrors(new Long(0));
						 return log;
				  }
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public int updateJobLog(HSnopeLog job) {
		String sql = "update h_snope_log set STATUS=?,TOTALLINEREAD=?,TOTALLINEWRITTEN="
				+ "?,TOTALLINEUPDATED=?,TOTALLINEREJECTED=?,ERRORS=?,ENDDATE=? where JOBID=?";
		return super.executeUpdate(sql, job.getStatus(),
				job.getTotallineread(),job.getTotallinewritten(),job.getTotallineupdate()
				,job.getTotallinerejected(),job.getErrors(),job.getEnddate(),job.getJobid());
	}

	@Override
	public Timestamp selectJobEndTime(int jobid) {
		String sql = "select max(LOGDATE) from h_snope_tranlog where JOB_ID = ?";
		return (Timestamp) super.executeQuerySingle(sql, jobid);
	}

	@Override
	public HSnopeTransLog selectTransRecord(String stepname, String channleid) {
		HSnopeTransLog  trans = new  HSnopeTransLog  ();	 
			String sql = "select SUM(a.LINES_READ) ,SUM(a.LINES_WRITTEN),SUM(a.LINES_UPDATED),SUM(a.LINES_REJECTED),SUM(a.ERRORS)"
					+ " from h_snope_steplog a , h_snope_tranlog b where a.ID_BATCH=b.ID_BATCH and b.CHANNEL_ID =? "
					+ " and right(STEPNAME,1) = ? ";
			try{
			 ResultSet rs = super.executeQueryRS(sql, channleid,stepname);
			 if(rs.next()) {
				 trans.setReadline(rs.getLong(1));
				 trans.setWriteline(rs.getLong(2));
				 trans.setUpdateline(rs.getLong(3));
				 trans.setRejectline(rs.getLong(4));
				 trans.setErrorsline(rs.getLong(5));
				  }else{
					  return null;
				  }
			}catch(Exception e){
				e.printStackTrace();
			}
			return trans;
			
	}

	@Override
	public int updateTransLog(HSnopeTransLog trans) {
		String sql = "update h_snope_tranlog set LINES_READ=?,LINES_WRITTEN="
				+ "?,LINES_UPDATED=?,LINES_REJECTED=?,ERRORS=? where CHANNEL_ID=?";
		return super.executeUpdate(sql, trans.getReadline(),trans.getWriteline(),trans.getUpdateline(),trans.getRejectline(),trans.getErrorsline(),trans.getChannelid());
	}

	@Override
	public void updateTransLogTotal(HSnopeTransLog trans) {
		String sql = "update h_snope_tranlog set JOB_ID = ? , LINES_READ= ?,LINES_WRITTEN"
				+ "= ?,LINES_UPDATED= ?,LINES_REJECTED= ?,ERRORS= ?  where CHANNEL_ID= ?";
	 super.executeUpdate(sql,trans.getJobid(), trans.getReadline(),trans.getWriteline(),trans.getUpdateline(),trans.getRejectline(),trans.getErrorsline(),trans.getChannelid());
	}

	@Override
	public void updateLegalState(int state) {
		String sql = "update h_xz_punishment set state= ? where  xzcfjgdm ='402881fb4c2a9bb8014c2a9c43c00000' and state> ?";
		super.executeUpdate(sql,state,state);
	}

	@Override
	public void updateLegalVersion() {
		String sql = "update h_xz_punishment set dateversion= dateversion+1 where state>0 and  xzcfjgdm ='402881fb4c2a9bb8014c2a9c43c00000' ";
		super.executeUpdate(sql);
	}

	@Override
	public void updateStepState(int jobid) {
		String sql = "update h_snope_tranlog set STATUS='end' where JOB_ID ="+jobid+" and STATUS = 'start' ";
		super.executeUpdate(sql);
	}

}
