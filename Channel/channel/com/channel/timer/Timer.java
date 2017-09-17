package com.channel.timer;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.channel.maintenance.service.MaintenanceService;
import com.channel.statistics.StatisticsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.channel.appurtenance.dao.AppurtenanceDao;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.hangdao.dao.HangDaoDao;
import com.channel.hangduan.service.HangDuanService;
import com.channel.maintenance.dao.MaintenanceDao;

@Component
public class Timer {
    @Resource(name = "hangduanservice")
    private HangDuanService hangDuanService;
    @Resource(name = "hangdaodao")
    private HangDaoDao hangDaoDao;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmDaoImpl;
    @Resource(name = "appurtenancedao")
    private AppurtenanceDao appurtenanceDao;
    @Resource(name = "maintenancedao")
    private MaintenanceDao maintenanceDao;
    @Resource(name = "maintenanceservice")
    private MaintenanceService maintenanceService;
    @Resource(name = "StatisticsService")
    private StatisticsService statisticsService;

    /**
     * 每天晚上统计航段里程信息，在报表统计模块中，将引用此信息
     */
    @Scheduled(cron = "0 0 1 * * ?")
    // @Scheduled(fixedRate = 5000)
    public void queryMeleage() {
        //湖州的
        this.statisticsService.saveLcTable(2, new Date());
        this.statisticsService.saveSsTable(2, new Date());
        this.statisticsService.saveLcflTable(2, new Date());
    }
}
