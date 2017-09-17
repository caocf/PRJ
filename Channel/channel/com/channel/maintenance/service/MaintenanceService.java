package com.channel.maintenance.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.channel.model.yh.*;
import com.common.action.result.BaseResult;

public interface MaintenanceService {

    public BaseResult delMaintenance(List<Integer> jbxxidlist);

    public BaseResult queryMainById(int jbxxid);

    public BaseResult queryMaintenances(int sfdg, int dw, List<Integer> hdids,
                                        Date starttime, Date endtime, String sEcho, int page, int rows);

    public BaseResult queryZxgcById(int zxgcid);

    public BaseResult queryZxgcs(int sfdg, Integer gldw, Date starttime,
                                 Date endtime, String content, String sEcho, int page, int rows);

    public BaseResult delZxgc(List<Integer> zxgcidlist);

    public BaseResult addZxgc(CYhZxgc zxgc,
                              List<CYhYdjdqk> ybblist, List<CYhFj> fjlist, List<File> filelist,
                              List<String> filelistFileName);

    public BaseResult queryBranchById(int id);

    public BaseResult queryBranches(int sfdg, Integer dw, Date starttime, Date endtime,
                                    String sEcho, int page, int rows);

    public BaseResult delBranch(List<Integer> zxjbxxidlist);

    public BaseResult queryAllJsdw();

    public BaseResult updateZxgc(CYhZxgc zxgc,
                                 List<CYhYdjdqk> ybblist, List<CYhFj> fjlist,
                                 List<Integer> delybbids, List<Integer> delfileids,
                                 List<File> filelist, List<String> filelistFileName);

    public BaseResult addBranch(CYhZxjbxx zxjbxx);

    public BaseResult updateBranch(CYhZxjbxx zxjbxx);

    public BaseResult addMaintenance(CYhGgjbxx jbxx);

    public BaseResult updateMaintenance(CYhGgjbxx jbxx);

    public BaseResult addCyhfj(int zxgcid, List<File> filelist,
                               List<String> filelistFileName);

    public BaseResult addCyhybb(int zxgcid, CYhYdjdqk cyhybb);

    public BaseResult downloadCyhfj(int id);

    public BaseResult delCyhfj(int zxgcid, List<Integer> ids);

    public BaseResult delCyhybb(int zxgcid, List<Integer> delybbids);

    public BaseResult updateCyhybb(int id, CYhYdjdqk cyhybb);

    public BaseResult queryCyhybb(int id);

    public BaseResult queryLastYd(int zxgcid);

    public BaseResult queryZxgcStarttime();

    public BaseResult queryGgStarttime();

    public BaseResult queryZxStarttime();

    public BaseResult addYjqtgc(CYhYjqtgc yjqtgc, List<File> filelist, List<String> filelistFileName);

    public BaseResult searchYjqtgc(Integer sfdg,Integer gldw, Integer hdaoid, Integer hduanid, Date starttime, Date endtime, String content, String sEcho, int page, int rows);

    public BaseResult delYjqtgc(List<Integer> ids);

    public BaseResult queryYjqtgcById(int id);

    public BaseResult downloadYjqtgcfj(int id);

    public BaseResult updateYjqtgc(CYhYjqtgc yjqtgc, List<Integer> delfileids, List<File> filelist, List<String> filelistFileName);
}
