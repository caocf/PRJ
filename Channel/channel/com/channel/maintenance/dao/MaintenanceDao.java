package com.channel.maintenance.dao;

import java.util.Date;
import java.util.List;

import com.channel.model.user.CZdXzqhdm;
import com.channel.model.yh.*;
import com.common.dao.BaseQueryRecords;

public interface MaintenanceDao {

    public BaseQueryRecords queryMaintenances(String dwstr, String hdid, String strstarttime,
                                              String strendtime, int page, int rows);

    public void delMaintenance(CYhGgjbxx jbxx);

    public CYhGgjbxx queryJbxxById(int jbxxid);

    public BaseQueryRecords queryMainById(int jbxxid);

    public BaseQueryRecords queryZxgcById(int zxgcid);

    public BaseQueryRecords queryZxgcs(String gldwstr, String strstarttime,
                                       String strendtime, String content, int page, int rows);

    public void delYbb(Integer zxgcid);

    public void delFj(Integer zxgcid);

    public void delZxgc(CYhZxgc zxgc);

    public CYhZxgc queryZxgcJbxxById(Integer zxgcid);

    public void addZxgc(CYhZxgc zxgc);

    public void addCYhFj(CYhFj fj);

    public void addCYhYbb(CYhYdjdqk ybb);

    public BaseQueryRecords queryBranchById(int id);

    public BaseQueryRecords queryBranches(String dwstr, String strstarttime,
                                          String strendtime, int page, int rows);

    public CYhZxjbxx queryZxjbxxById(Integer zxjbxxid);

    public void delBranch(CYhZxjbxx zxjbxx);

    public BaseQueryRecords queryAllJsdw();

    public void updateZxgc(CYhZxgc zxgc);

    public CYhYdjdqk queryYbbByid(Integer ybbid);

    public void delYbb(CYhYdjdqk ybb);

    public CYhFj queryFjByid(Integer fjid);

    public void delCYhFj(CYhFj cyhfj);

    public void updateBranch(CYhZxjbxx zxjbxx);

    public void addBranch(CYhZxjbxx zxjbxx);

    public void addMaintenance(CYhGgjbxx jbxx);

    public void updateMaintenance(CYhGgjbxx jbxx);

    public CYhFj queryFjByMd5(int zxgcid, String infileMd5);

    public void delCYhYbb(CYhYdjdqk ybb);

    public void updateCyhybb(CYhYdjdqk ybb);

    public BaseQueryRecords<CYhFj> queryFjByZxgcid(int zxgcid);

    public BaseQueryRecords<CYhYdjdqk> queryYbbByZxgcid(int zxgcid);

    public CYhYdjdqk queryLastYdjdqk(int zxgcid);

    public CYhYdjdqk queryLastYdjdqk(int zxgcid, String strendtime);

    public BaseQueryRecords queryZxgcTable(String strstarttime,
                                           String strendtime);

    public BaseQueryRecords queryZxgcTable(String s, String strstarttime, String strendtime);

    public Date queryLastesttime();


    public Date queryEarliesttime();

    public List<CYhGgjbxx> queryGgTable(Integer[] department, Date starttime,
                                        Date endtime);

    public Date queryGgLastesttime();

    public Date queryGgEarliesttime();

    public Date queryZxEarliesttime();


    public Date queryZxLastesttime();

    public List<CYhZxjbxx> queryZxTable(Integer[] arr, Date earliesttime, Date endtime);


    public CYhZxjbxx queryLastZx(Date endtime);

    public List<Object[]> querySsTable(List<CZdXzqhdm> xzqhdms);


    void addYjqtgc(CYhYjqtgc yjqtgc);

    public CYhYjqtgcfj queryYjqtgcfjByMd5(int pid, String infileMd5);

    public void addCYhYjqtgcfj(CYhYjqtgcfj fj);

    public BaseQueryRecords searchYjqtgc(String dw, Integer hdaoid, Integer hduanid, String starttime, String endtime, String content, int page, int rows);

    public void delYjqtgcInId(String id);

    public void delYjqtgcfjInFid(String pid);

    public BaseQueryRecords queryYjqtgcById(int id);

    public BaseQueryRecords<CYhYjqtgcfj> queryYjqtgcfjByPid(int id);

    public CYhYjqtgcfj queryYjqtgcfjByid(int id);

    public Object queryYjqtgcByPk(int id);

    public void updateYjqtgc(CYhYjqtgc gc);

    public void delYjqtgcfjInId(String s);
}
