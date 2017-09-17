package com.highwaycenter.data.dao;

import java.sql.Timestamp;

import com.highwaycenter.data.model.HSnopeLog;
import com.highwaycenter.data.model.HSnopeTransLog;

public interface SnopelogLocalDao {
	public int updateTranlog(int jobid, String channleid);
	public int selectExitRun(String tranname);
	public Object selectTransKeyValue(String key, String channleid);
	public int addJobError(int jobid);
	public HSnopeLog selectRecord(String stepname, int jobid);
	public Object selectJobKeyValue(String key, String jobid);
	public HSnopeLog selectJobById(int jobid);
	public int updateJobLog(HSnopeLog job);
	public Timestamp selectJobEndTime(int jobid);
	public HSnopeTransLog selectTransRecord(String stepname, String channleid);
	public int updateTransLog(HSnopeTransLog trans);
	public void updateTransLogTotal(HSnopeTransLog trans);
	public void updateLegalState(int state);
	public void updateLegalVersion();
	public void updateStepState(int jobid);
}
