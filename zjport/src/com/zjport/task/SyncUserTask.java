package com.zjport.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 * Created by TWQ on 2017/1/17.
 */
@Component
public class SyncUserTask extends TimerTask{

    @Autowired
    SyncTaskService syncService;

    public static void main(String [] args) {
        //syncUser();
    }

    @Scheduled(cron = "0 30 1 * * ?")//每天的凌晨2点钟执行定时任务
    public void run() {
        this.syncUser();
        this.syncOrgDept();
    }

    public void syncUser() {
        String result = this.syncService.getData("GetYH");
        result = "["+result+"]";
        JSONArray data = JSONArray.parseArray(result);
        this.syncService.saveUpdateUser(data);
    }

    public void syncOrgDept() {
        String result = this.syncService.getData("GetZZJG");
        JSONObject obj = JSON.parseObject(result);
        this.syncService.saveUpdateOrgDept(obj);
        String[] resultNum = this.syncService.getOrgDeptNum();

        System.out.println("*************本次同步总共新增单位数据："+resultNum[0]+"条，更新单位数据："+resultNum[1]+"条;新增部门数据："+resultNum[2]+"条，更新部门数据："+resultNum[3]+"条***************");
    }
}
