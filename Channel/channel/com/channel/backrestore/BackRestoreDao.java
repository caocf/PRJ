package com.channel.backrestore;

import com.channel.backrestore.hbmodel.CXtBackHistory;
import com.channel.backrestore.hbmodel.CXtBackJob;
import com.channel.backrestore.model.Table;
import com.common.dao.BaseDao;
import com.common.dao.BaseQueryRecords;

import java.util.List;

/**
 * Created by 25019 on 2015/10/22.
 */
public interface BackRestoreDao extends BaseDao {

    //查询备份计划，项目启动时调用
    public BaseQueryRecords<CXtBackJob> querySchedulesOnBoot();

    //查找可备份的表信息
    public BaseQueryRecords<Table> queryTables();

    //查寻上一次备份历史记录
    public CXtBackHistory queryLastBackHistory(int scheduleid);

    //备份指定表
    public boolean backTables(String[] tableNames, String backupfolder, String backupfilename);

    public boolean backTables(List<Table> tables, String backupfolder, String backupfilename);

    //恢复指定表
    public boolean restoreTables(String backupfilepath);

    //获得备份根目录
    public String getBackRootFolder();
}
