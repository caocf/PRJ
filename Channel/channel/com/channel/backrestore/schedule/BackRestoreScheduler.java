package com.channel.backrestore.schedule;

import com.common.dao.impl.StringExpression;
import com.common.utils.LogUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by 25019 on 2015/10/22.
 */
@Service("backRestoreScheduler")
public class BackRestoreScheduler {
    private Logger logger = LogUtils.getLogger(this.getClass());

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    private void debug(StringExpression stringExpression) {
        logger.debug(stringExpression.toString());
        System.out.println(stringExpression.toString());
    }

    private JobDetail createJob(String jobname) {
        JobDetail job = JobBuilder.newJob(BackRestoreJob.class).withIdentity(jobname).build();
        return job;
    }

    private Trigger createSimpleTrigger(String triggername, Date starttime, int secondsinterval) {
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggername).startAt(starttime)
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(secondsinterval)).build();
        return trigger;
    }

    private Trigger createCronTrigger(String triggername, String cronexpression) throws Exception {
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggername)
                .withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression(cronexpression))).build();
        return trigger;
    }

    public boolean addJob(int jobid, String cronexpression) {
        try {
            scheduler.scheduleJob(createJob("" + jobid), createCronTrigger("" + jobid, cronexpression));
            debug(new StringExpression("添加任务?，?成功", "" + jobid, cronexpression));
        } catch (Exception e) {
            debug(new StringExpression("添加任务?，?失败", "" + jobid, cronexpression));
            return false;
        }
        return true;
    }

    public boolean addJob(int jobid, Date starttime, int secondsinterval) {
        try {
            scheduler.scheduleJob(createJob("" + jobid), createSimpleTrigger("" + jobid, starttime, secondsinterval));
            debug(new StringExpression("添加任务?，?s成功", "" + jobid, secondsinterval));
        } catch (Exception e) {
            debug(new StringExpression("添加任务?，?s失败", "" + jobid, secondsinterval));
            return false;
        }
        return true;
    }


    public boolean modifyJobTime(int jobid, String newcronexpression) {
        try {
            JobKey jobKey = JobKey.jobKey("" + jobid);
            TriggerKey triggerKey = TriggerKey.triggerKey("" + jobid);

            JobDetail job = scheduler.getJobDetail(jobKey);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(new CronExpression(newcronexpression));

            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey, trigger);

            debug(new StringExpression("修改任务?时间为?成功", "" + jobid, newcronexpression));
            return true;
        } catch (Exception e) {
            debug(new StringExpression("修改任务?时间为?失败", "" + jobid, newcronexpression));
            return false;
        }
    }

    public boolean modifyJobTime(int jobid, Date starttime, int secondsinterval) {
        try {
            JobKey jobKey = JobKey.jobKey("" + jobid);
            TriggerKey triggerKey = TriggerKey.triggerKey("" + jobid);

            JobDetail job = scheduler.getJobDetail(jobKey);

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("" + jobid).startAt(starttime)
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(secondsinterval)).build();
            scheduler.rescheduleJob(triggerKey, trigger);

            debug(new StringExpression("修改任务?时间为?:?成功", "" + jobid, starttime, secondsinterval));
            return true;
        } catch (Exception e) {
            debug(new StringExpression("修改任务?时间为?:?失败", "" + jobid, starttime, secondsinterval));
            return false;
        }
    }

    public boolean delJob(int jobid) {
        try {
            JobKey jobKey = JobKey.jobKey("" + jobid);
            scheduler.deleteJob(jobKey);

            debug(new StringExpression("删除任务?成功", "" + jobid));
            return true;
        } catch (Exception e) {
            debug(new StringExpression("删除任务?失败", "" + jobid));
            return false;
        }
    }

    public JobStatus getJobStatus(int jobid) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey("" + jobid);
            Trigger.TriggerState state = scheduler.getTriggerState(triggerKey);
            if (state.equals(Trigger.TriggerState.NONE))
                return JobStatus.DISABLED;
            else if (state.equals(Trigger.TriggerState.NORMAL))
                return JobStatus.NORMAL;
            else if (state.equals(Trigger.TriggerState.BLOCKED))
                return JobStatus.BLOCKED;
            else if (state.equals(Trigger.TriggerState.COMPLETE))
                return JobStatus.COMPLETE;
            else if (state.equals(Trigger.TriggerState.ERROR))
                return JobStatus.ERROR;
            else if (state.equals(Trigger.TriggerState.PAUSED))
                return JobStatus.PAUSED;
            else
                return JobStatus.DISABLED;
        } catch (Exception e) {
            return JobStatus.DISABLED;
        }
    }

    public Date getNextScheduleTimee(int jobid) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey("" + jobid);
            Trigger trigger = scheduler.getTrigger(triggerKey);
            return trigger.getNextFireTime();
        } catch (Exception e) {
            return null;
        }
    }
}
