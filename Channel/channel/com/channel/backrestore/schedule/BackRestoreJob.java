package com.channel.backrestore.schedule;

import com.channel.backrestore.BackRestoreDao;
import com.channel.backrestore.BackRestoreService;
import com.common.dao.impl.StringExpression;
import com.common.utils.LogUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by 25019 on 2015/10/22.
 */
public class BackRestoreJob implements Job {
    private Logger logger = LogUtils.getLogger(BackRestoreJob.class);

    private void debug(StringExpression stringExpression) {
        logger.debug(stringExpression.toString());
        System.out.println(stringExpression.toString());
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        BackRestoreService backRestoreService = null;
        try {
            SchedulerContext schCtx = jobExecutionContext.getScheduler().getContext();
            //获取Spring中的上下文
            ApplicationContext appCtx = (ApplicationContext) schCtx.get("applicationContext");
            backRestoreService = (BackRestoreService) appCtx.getBean("backRestoreService");
        } catch (Exception e) {
            debug(new StringExpression("获取backservice失败"));
        }
        if (backRestoreService == null) {
            debug(new StringExpression("backservice为空，获取backservice失败"));
            return;
        }
        if (jobDetail == null) {
            debug(new StringExpression("jobdetail为空，执行失败"));
            return;
        }
        String jobid = jobDetail.getKey().getName();
        boolean ret = backRestoreService.backup(Integer.parseInt(jobid));
        if (ret) {
            debug(new StringExpression("备份成功"));
        } else {
            debug(new StringExpression("备份失败"));
        }
    }
}
