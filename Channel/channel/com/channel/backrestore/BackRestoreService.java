package com.channel.backrestore;

import com.channel.backrestore.hbmodel.CXtBackHistory;
import com.channel.backrestore.hbmodel.CXtBackJob;
import com.channel.backrestore.impl.BackRestoreDaoImpl;
import com.channel.backrestore.model.Table;
import com.channel.backrestore.schedule.BackRestoreScheduler;
import com.channel.backrestore.schedule.JobStatus;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.StringExpression;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.common.utils.FileMd5;
import com.common.utils.FileUtils;
import org.hibernate.Criteria;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by 25019 on 2015/10/22.
 */
@Service("backRestoreService")
public class BackRestoreService extends BaseService {
    @Resource(name = "backRestoreDaomysql")
    private BackRestoreDao backRestoreDao;
    @Resource(name = "backRestoreScheduler")
    private BackRestoreScheduler scheduler;


    @PostConstruct
    public void init() {
        //启动时，从数据库加载所有的schedule，并对已启动的schedule进行启动
        BaseQueryRecords<CXtBackJob> records = (BaseQueryRecords<CXtBackJob>) this.backRestoreDao.querySchedulesOnBoot();

        for (CXtBackJob job : records.getData()) {
            if (job.getIfenable() == 1) {
                addJob(job);
            }
        }
    }

    private String getRelativeBackFolder(int scheduleid) {
        String root = this.backRestoreDao.getBackRootFolder();
        String path = root + "/DBBACKUP/" + scheduleid;
        FileUtils.mkdir(path);
        return "/DBBACKUP/" + scheduleid;
    }

    private String getAbsoluteBackFolder(int scheduleid) {
        String root = this.backRestoreDao.getBackRootFolder();
        String path = root + "/DBBACKUP/" + scheduleid;
        FileUtils.mkdir(path);
        return root + "/DBBACKUP/" + scheduleid;
    }

    private String getAbsoluteBackFilePath(int scheduleid, String backfilename) {
        String root = this.backRestoreDao.getBackRootFolder();
        String path = root + "/DBBACKUP/" + scheduleid;
        FileUtils.mkdir(path);
        return root + "/DBBACKUP/" + scheduleid + "/" + backfilename;
    }

    public BaseResult queryTables() {
        BaseQueryRecords<Table> tables = this.backRestoreDao.queryTables();
        return new BaseResultOK(tables);
    }

    public BaseResult queryBackSchedules(int page, int rows) {
        BaseQueryRecords<CXtBackJob> records = (BaseQueryRecords<CXtBackJob>) this.backRestoreDao.find(new CXtBackJob(), page, rows);
        for (CXtBackJob job : records.getData()) {
            job.setBackschedule(getBackSchedule(job));
            //如果未启用，状态为未启用
            if (job.getIfenable() == 0) {
                job.setRuntimestatus(JobStatus.DISABLED.getJobstatusdesc());
            } else {
                JobStatus status = scheduler.getJobStatus(job.getId());
                job.setRuntimestatus(status.getJobstatusdesc());
            }
        }

        return new BaseResultOK(records);
    }

    public BaseResult queryBackHistories(int jobid, int page, int rows) {
        CXtBackJob job = (CXtBackJob) this.backRestoreDao.findUnique(new CXtBackJob(), "id", jobid);
        if (job == null)
            return new BaseResultFailed("计划未找到");
        BaseQueryRecords<CXtBackHistory> records = (BaseQueryRecords<CXtBackHistory>) this.backRestoreDao
                .findOrderBy(new CXtBackHistory(), "backjobid", jobid, "backtime", true, page, rows);

        for (CXtBackHistory history : records.getData()) {
            String backfilepath = getAbsoluteBackFilePath(job.getId(), history.getBackfilename());

            if (!FileUtils.ifFileExist(backfilepath)) {
                history.setBackfilestatus("文件不存在");
                history.setBackfilesize(0);
            } else {
                if (!FileUtils.ifFileExist(backfilepath, history.getBackfilemd5())) {
                    history.setBackfilestatus("文件已损坏");
                    history.setBackfilesize(0);
                } else {
                    history.setBackfilestatus("文件正常");
                    history.setBackfilesize(new File(backfilepath).length());
                }
            }
        }

        return new BaseResultOK(records);
    }

    public BaseResult deleteBackHistory(int historyid) {
        CXtBackHistory history = (CXtBackHistory) this.backRestoreDao.findUnique(new CXtBackHistory(), "id", historyid);
        if (history == null)
            return new BaseResultFailed("备份文件不存在");
        this.backRestoreDao.delete(history);
        return new BaseResultOK();
    }

    public BaseResult addBackSchedule(String backname, String backcontent, boolean backall, List<String> tablenames,
                                      String cronexpression, boolean ifenable, int type, int secondsinterval, Date starttime) {

        if (backname == null || backname.equals(""))
            return new BaseResultFailed("备份名不能为空");


        CXtBackJob job = new CXtBackJob();
        job.setIfenable(ifenable == true ? 1 : 0);
        job.setBackname(backname);
        job.setBackcontent(backcontent);
        job.setType(type);
        if (type == 4) {
            job.setStarttime(starttime);
            job.setInterval(secondsinterval);
            job.setCronexpression("");
        } else {
            job.setCronexpression(cronexpression);
            job.setStarttime(new Date());
            job.setInterval(-1);
        }
        job.setUpdatetime(new Date());
        job.setCreatetime(new Date());

        if (backall)
            job.setBacktables("");
        else {
            String str = "";
            for (int i = 0; i < tablenames.size(); i++) {
                str += tablenames.get(i);
                if (i != tablenames.size() - 1) {
                    str += ",";
                }
            }
            job.setBacktables(str);
        }
        job.setBackfolder("");
        this.backRestoreDao.save(job);

        job.setBackfolder(getRelativeBackFolder(job.getId()));

        //如果要执行计划，则执行
        if (ifenable == true) {
            boolean addresult = false;
            addresult = addJob(job);
            if (!addresult) {
                return new BaseResultFailed("计划启动失败");
            }
        }

        return new BaseResultOK();
    }


    public BaseResult updateBackSchedule(int id, String backname, String backcontent, boolean backall, List<String> tablenames,
                                         String cronexpression, int type, int secondsinterval, Date starttime) {
        CXtBackJob job = (CXtBackJob) this.backRestoreDao.findUnique(new CXtBackJob(), "id", id);
        if (job == null)
            return new BaseResultFailed("计划不存在");

        if (backname == null || backname.equals(""))
            return new BaseResultFailed("备份名不能为空");
        job.setBackname(backname);
        job.setBackcontent(backcontent);
        job.setType(type);
        if (type == 4) {
            job.setStarttime(starttime);
            job.setInterval(secondsinterval);
            job.setCronexpression("");
        } else {
            job.setCronexpression(cronexpression);
            job.setStarttime(new Date());
            job.setInterval(-1);
        }
        job.setUpdatetime(new Date());

        if (backall)
            job.setBacktables("");
        else {
            String str = "";
            for (int i = 0; i < tablenames.size(); i++) {
                str += tablenames.get(i);
                if (i != tablenames.size() - 1) {
                    str += ",";
                }
            }
            job.setBacktables(str);
        }
        job.setBackfolder("");
        this.backRestoreDao.update(job);

        //如果要执行计划，则执行
        if (job.getIfenable() == 1) {
            boolean addresult = false;
            addresult = updateJob(job);
            if (!addresult) {
                return new BaseResultFailed("计划重启失败");
            }
        }

        return new BaseResultOK();
    }

    private boolean updateJob(CXtBackJob job) {
        boolean addresult = false;
        if (job.getType() == 4) {
            addresult = scheduler.modifyJobTime(job.getId(), job.getStarttime(), job.getInterval());
        } else {
            addresult = scheduler.modifyJobTime(job.getId(), job.getCronexpression());
        }
        return addresult;
    }

    private boolean addJob(CXtBackJob job) {
        boolean addresult = false;
        if (job.getType() == 4) {
            addresult = scheduler.addJob(job.getId(), job.getStarttime(), job.getInterval());
        } else {
            addresult = scheduler.addJob(job.getId(), job.getCronexpression());
        }
        return addresult;
    }

    public BaseResult disableBackSchedule(int id) {
        CXtBackJob job = (CXtBackJob) this.backRestoreDao.findUnique(new CXtBackJob(), "id", id);
        if (job == null)
            return new BaseResultFailed("计划不存在");
        boolean ret = scheduler.delJob(job.getId());
        if (ret) {
            job.setIfenable(0);
            this.backRestoreDao.update(job);
            return new BaseResultOK();
        } else
            return new BaseResultFailed("计划停止失败");
    }

    public BaseResult enableBackSchedule(int id) {
        CXtBackJob job = (CXtBackJob) this.backRestoreDao.findUnique(new CXtBackJob(), "id", id);
        if (job == null)
            return new BaseResultFailed("计划不存在");

        boolean ret = false;
        ret = addJob(job);
        if (ret) {
            job.setIfenable(1);
            this.backRestoreDao.update(job);
            return new BaseResultOK();
        } else
            return new BaseResultFailed("计划启动失败");
    }

    public BaseResult deleteBackSchedule(int id) {
        CXtBackJob job = (CXtBackJob) this.backRestoreDao.findUnique(new CXtBackJob(), "id", id);
        if (job == null)
            return new BaseResultFailed("计划不存在");
        boolean ret = scheduler.delJob(job.getId());
        if (ret) {
            this.backRestoreDao.delete(job);
            return new BaseResultOK();
        } else
            return new BaseResultFailed("计划停止失败");
    }


    public BaseResult viewBackSchedule(int id) {
        CXtBackJob job = (CXtBackJob) this.backRestoreDao.findUnique(new CXtBackJob(), "id", id);
        if (job == null)
            return new BaseResultFailed("计划不存在");
        //如果未启用，状态为未启用
        if (job.getIfenable() == 0) {
            job.setRuntimestatus(JobStatus.DISABLED.getJobstatusdesc());
        } else {
            JobStatus status = scheduler.getJobStatus(job.getId());
            job.setRuntimestatus(status.getJobstatusdesc());
        }

        String backschedule = "";
        String prebacktime = "";
        String nextbacktime = "";

        //备份计划
        backschedule = getBackSchedule(job);

        //上一次备份时间
        CXtBackHistory lastbackhistory = this.backRestoreDao.queryLastBackHistory(job.getId());
        if (lastbackhistory != null) {
            prebacktime = DateTimeUtil.getTimeFmt(lastbackhistory.getBacktime());
        }

        //下一次备份时间
        if (job.getIfenable() == 1 && !scheduler.getJobStatus(job.getId()).equals(JobStatus.DISABLED)) {
            Date nextTriggerTime = scheduler.getNextScheduleTimee(job.getId());
            if (nextTriggerTime != null) {
                nextbacktime = DateTimeUtil.getTimeFmt(nextTriggerTime);
            }
        }

        BaseResult result = new BaseResultOK();
        result.addToMap("job", job);
        result.addToMap("backschedule", backschedule);
        result.addToMap("prebacktime", prebacktime);
        result.addToMap("nextbacktime", nextbacktime);
        return result;
    }

    private String getBackSchedule(CXtBackJob job) {
        String backschedule = "";
        if (job.getType() == 1) {
            String cron = job.getCronexpression();
            String[] crons = cron.split(" ");
            String second, minutes, hours, dayofmonth, month, dayofweek;
            try {
                second = crons[0];
                minutes = crons[1];
                hours = crons[2];
                dayofmonth = crons[3];
                month = crons[4];
                dayofweek = crons[5];

                backschedule = new StringExpression("每月?日?:?:?进行备份", dayofmonth, hours, minutes, second).toString();

            } catch (Exception e) {

            }
        }
        if (job.getType() == 2) {
            String cron = job.getCronexpression();
            String[] crons = cron.split(" ");
            String second, minutes, hours, dayofmonth, month, dayofweek;
            try {
                second = crons[0];
                minutes = crons[1];
                hours = crons[2];
                dayofmonth = crons[3];
                month = crons[4];
                dayofweek = crons[5];

                backschedule = new StringExpression("每周星期? ?:?:?进行备份", dayofweek, hours, minutes, second).toString();

            } catch (Exception e) {

            }
        }
        if (job.getType() == 3) {
            String cron = job.getCronexpression();
            String[] crons = cron.split(" ");
            String second, minutes, hours, dayofmonth, month, dayofweek;
            try {
                second = crons[0];
                minutes = crons[1];
                hours = crons[2];
                dayofmonth = crons[3];
                month = crons[4];
                dayofweek = crons[5];

                backschedule = new StringExpression("每天?:?:?进行备份", hours, minutes, second).toString();

            } catch (Exception e) {

            }
        }
        if (job.getType() == 4) {
            backschedule = new StringExpression("从?开始，每隔?秒备份一次", DateTimeUtil.getTimeFmt(job.getStarttime()), job.getInterval()).toString();
        }
        return backschedule;
    }

    //备份
    public boolean backup(int jobid) {
        CXtBackJob job = (CXtBackJob) this.backRestoreDao.findUnique(new CXtBackJob(), "id", jobid);
        if (job == null)
            return false;

        boolean ret;
        String backfolder = getAbsoluteBackFolder(job.getId());
        String backname = "" + new Date().getTime() + ".sql";
        if (job.getBacktables() == null || job.getBacktables().equals("")) {
            List<Table> tables = (List<Table>) queryTables().getRecords().getData();
            ret = this.backRestoreDao.backTables(tables, backfolder, backname);
        } else {
            String[] tablenames = job.getBacktables().split(",");
            ret = this.backRestoreDao.backTables(tablenames, backfolder, backname);
        }

        CXtBackHistory history = new CXtBackHistory();
        history.setBackjobid(jobid);
        history.setBacktime(new Date());
        history.setBackfilename(backname);
        history.setBackfilemd5(FileMd5.getMd5ByFile(backfolder + "/" + backname));
        this.backRestoreDao.save(history);
        return ret;
    }

    //恢复
    public BaseResult restore(int backhistoryid) {
        CXtBackHistory history = (CXtBackHistory) this.backRestoreDao.findUnique(new CXtBackHistory(), "id", backhistoryid);
        if (history == null)
            return new BaseResultFailed("备份文件不存在");
        CXtBackJob job = (CXtBackJob) this.backRestoreDao.findUnique(new CXtBackJob(), "id", history.getBackjobid());
        if (job == null)
            return new BaseResultFailed("备份计划不存在");

        this.backRestoreDao.restoreTables(getAbsoluteBackFilePath(job.getId(), history.getBackfilename()));
        return new BaseResultOK();
    }
}
