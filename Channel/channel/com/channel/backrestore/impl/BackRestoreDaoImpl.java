package com.channel.backrestore.impl;

import com.channel.backrestore.BackRestoreDao;
import com.channel.backrestore.hbmodel.CXtBackHistory;
import com.channel.backrestore.hbmodel.CXtBackJob;
import com.channel.backrestore.model.Table;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.common.dao.impl.StringExpression;
import com.common.utils.PropertyLoader;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by 25019 on 2015/10/22.
 */
@Repository("backRestoreDaomysql")
public class BackRestoreDaoImpl extends BaseDaoDB implements BackRestoreDao {
    private static String SCRIPT_NAME = "backrestore.properties";
    private boolean initOK = false;
    private String backcmd = null;
    private String restorecmd = null;
    private String backrootfolder = null;

    @PostConstruct
    public void init() {
        //先从classpath根目录中查找backrestore.properties
        //如果没有，尝试从com.channel.backrestore.impl中加载属性
        Properties properties = PropertyLoader.getPropertiesFromClassPath(SCRIPT_NAME, "UTF-8");
        if (properties == null) {
            properties = PropertyLoader.getPropertiesFromClassPath("com/channel/backrestore/impl/" + SCRIPT_NAME, "UTF-8");
        }
        if (properties == null) {
            initOK = false;
            System.out.println("备份模块初始化失败");
            return;
        } else {
            backcmd = properties.getProperty("backcmd", null);
            restorecmd = properties.getProperty("restorecmd", null);
            backrootfolder = properties.getProperty("backrootfolder", null);
            if (backcmd == null || restorecmd == null || backrootfolder == null) {
                initOK = false;
                System.out.println("备份模块初始化失败");
                return;
            }
        }
        System.out.println("备份模块初始化成功");
    }

    /**
     * 获取当前有哪些数据表
     *
     * @return
     */
    public BaseQueryRecords<Table> queryTables() {
        String sql = "SELECT table_name, TABLE_COMMENT,TABLE_ROWS FROM information_schema.tables WHERE table_schema='channel' ORDER BY table_name DESC;";
        BaseQueryRecords<Object[]> records = (BaseQueryRecords<Object[]>) super.find(new SQL(sql));
        List<Table> tbs = new ArrayList<>();

        for (int i = 0; i < records.getData().size(); i++) {
            Object[] objs = records.getData().get(i);

            String tablename = (String) objs[0];
            String tablecomment = (String) objs[1];
            String datacnt = ((BigInteger) objs[2]).toString();

            //备份还原的表忽略
            if (tablename.toLowerCase().equals("c_xt_backjob")
                    || tablename.toLowerCase().equals("c_xt_backhistory")) {
                continue;
            }

            tbs.add(new Table(tablename, tablecomment, Integer.parseInt(datacnt)));
        }
        return new BaseQueryRecords<Table>(tbs);
    }

    @Override
    public BaseQueryRecords<CXtBackJob> querySchedulesOnBoot() {
        Session session = super.getSessionFactory().openSession();
        try {
            Criteria criteria = session
                    .createCriteria(CXtBackJob.class);
            return new BaseQueryRecords(criteria.list());
        } catch (Exception e) {
            return new BaseQueryRecords<>();
        } finally {
            session.close();
        }
    }

    @Override
    public CXtBackHistory queryLastBackHistory(int scheduleid) {
        String hql = "select a from CXtBackHistory a where a.backjobid=? order by a.backtime desc";
        return (CXtBackHistory) super.findUnique(new HQL(hql, scheduleid));
    }


    private boolean execcmd(String cmd) {
        System.out.println(cmd);

        BufferedReader br = null;
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(cmd);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            int ret = p.exitValue();
            System.out.println(ret);
            return ret == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean backTables(List<Table> tables, String backupfolder, String backupfilename) {
        String cmd = "cmd /c " + backcmd;
        for (Table table : tables) {
            cmd += " " + table.getTablename();
        }
        cmd += " > " + backupfolder + "/" + backupfilename;
        return execcmd(cmd);
    }

    public boolean backTables(String[] tableNames, String backupfolder, String backupfilename) {
        String cmd = "cmd /c " + backcmd;
        for (String tablename : tableNames) {
            cmd += " " + tablename;
        }
        cmd += " > " + backupfolder + "/" + backupfilename;
        return execcmd(cmd);
    }

    public boolean restoreTables(String backupfilepath) {
        return execcmd("cmd /c" + restorecmd + " < " + backupfilepath);
    }

    @Override
    public String getBackRootFolder() {
        return backrootfolder;
    }
}
