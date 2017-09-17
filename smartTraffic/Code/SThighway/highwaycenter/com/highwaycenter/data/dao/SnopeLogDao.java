package com.highwaycenter.data.dao;

import java.sql.Timestamp;
import java.util.List;

import com.highwaycenter.data.model.HSnopeLog;
import com.highwaycenter.data.model.HSnopeStepLog;
import com.highwaycenter.data.model.HSnopeTransLog;

public interface SnopeLogDao {
	public Object saveAndReturn(HSnopeLog snopelog);
	public void update(HSnopeLog snopelog);
	public void deleteTransLog(int jobid);
	public void deleteStepLog(String transids);
    public List<HSnopeTransLog> selectTransloglist(int jobid);
    public List<HSnopeLog> selectJobloglist(String startdate,String enddate,String jobname);
    public List<HSnopeStepLog> selectSteploglist(int ID_BATCH);
    public HSnopeLog seleteJobDayTotal(String starttime,String endtime,String jobname);
    public long selectSuccessCount(String jobname,String starttime,int isSuccess);
    public HSnopeLog selectLastRecord(String jobname);
    public int selectCurrentCountByname(String jobname);
    public void updateResult(int jobid,String status,Long readall,Long writeall,
    		Long updateall,Long errors,String enddate);
    public void updateTransLogJobid(int jobid, String channle_id);
    public HSnopeTransLog selectTransRecord(String stepname, String channleid) ;
    public void updateTransLogTotal(HSnopeTransLog trans) ;
	public HSnopeLog selectRecord(String stepname, int jobid);
	public Timestamp selectJobEndTime(int jobid) ;
	public void addJobError(int jobid);
	public HSnopeLog selectJobById(int jobid);
    
}
