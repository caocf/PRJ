package com.channel.maintenance.service.impl;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import com.channel.model.user.CXtDpt;
import com.channel.model.user.CZdXzqhdm;
import com.channel.model.yh.*;
import com.channel.user.dao.impl.DptDaoImpl;
import com.channel.user.service.DptService;
import com.common.utils.*;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import com.channel.bean.Constants;
import com.channel.dic.dao.DicDao;
import com.channel.maintenance.dao.MaintenanceDao;
import com.channel.maintenance.service.MaintenanceService;
import com.channel.model.user.CXtUser;
import com.channel.user.dao.UserDao;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;

@Service("maintenanceservice")
public class MaintenanceServiceImpl extends BaseService implements
        MaintenanceService {
    @Resource(name = "maintenancedao")
    private MaintenanceDao maintenanceDao;
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "dicdao")
    private DicDao dicDao;
    @Resource(name = "dptdao")
    private DptDaoImpl dptdao;
    @Resource(name = "dptservice")
    private DptService dptService;
    @Resource
    private LogService logService;

    @Override
    public BaseResult queryMainById(int id) {
        BaseQueryRecords records = this.maintenanceDao.queryMainById(id);
        BaseResultOK result = new BaseResultOK(records);
        return result;
    }

    @Override
    public BaseResult queryMaintenances(int sfdg, int dw, List<Integer> hdids,
                                        Date starttime, Date endtime, String sEcho, int page, int rows) {
        String strstarttime = "";
        String strendtime = "";
        String strhdid = "";
        if (starttime != null) {
            strstarttime = DateTimeUtil.getTimeFmt(starttime,
                    "yyyy-MM-dd HH:mm:ss");
        }
        if (endtime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endtime);
            calendar.add(Calendar.MONTH, 1);
            strendtime = DateTimeUtil.getTimeFmt(calendar.getTime(),
                    "yyyy-MM-dd HH:mm:ss");
        }
        if (hdids != null && hdids.size() > 0) {
            for (Integer hdid : hdids) {
                strhdid += hdid + ",";
            }
            StringBuffer sb = new StringBuffer(strhdid);
            sb.deleteCharAt(sb.length() - 1);
            strhdid = sb.toString();
        }

        String dwstr = "";
        if (sfdg == 1) {

            //如果是省
            CXtDpt dwdpt = this.dptService.findNode(new CXtDpt(dw));
            if (dwdpt != null && dwdpt.getType() == Constants.DPT_SHENJU) {
                dwstr = null;
            } else {
                List<CXtDpt> dpts = this.dptService.findChildrenNodes_r(new CXtDpt(dw));
                dpts.add(new CXtDpt(dw));

                for (int i = 0; i < dpts.size(); i++) {
                    dwstr += dpts.get(i).getId();
                    if (i != dpts.size() - 1)
                        dwstr += ",";
                }
            }
        } else {
            dwstr = "" + dw;
        }

        BaseQueryRecords records = this.maintenanceDao.queryMaintenances(dwstr,
                strhdid, strstarttime, strendtime, page, rows);
        BaseResultOK result = new BaseResultOK(records);
        result.addToMap("sEcho", sEcho);
        return result;
    }

    @Override
    public BaseResult delMaintenance(List<Integer> jbxxidlist) {
        for (Integer jbxxid : jbxxidlist) {
            CYhGgjbxx jbxx = this.maintenanceDao.queryJbxxById(jbxxid);
            if (jbxx != null) {
                this.maintenanceDao.delMaintenance(jbxx);
            }
        }
        logService.addLog(ModuleName.HDYH_GG, OpName.DEL, "");
        BaseResultOK result = new BaseResultOK();
        return result;
    }

    @Override
    public BaseResult updateMaintenance(CYhGgjbxx jbxx) {
        int jbxxid = jbxx.getId();
        CYhGgjbxx newjbxx = this.maintenanceDao.queryJbxxById(jbxxid);
        if (newjbxx == null) {
            return new BaseResultFailed(Constants.RESULT_MAIN_NOTEXIST,
                    "骨干台账不存在");
        } else {
            newjbxx.setDw(jbxx.getDw());
            newjbxx.setHdid(jbxx.getHdid());
            newjbxx.setStarttime(jbxx.getStarttime());
            newjbxx.setClsl(jbxx.getClsl());
            newjbxx.setCljg(jbxx.getCljg());
            newjbxx.setClbz(jbxx.getClbz());
            newjbxx.setSjsl(jbxx.getSjsl());
            newjbxx.setSjjg(jbxx.getSjjg());
            newjbxx.setSjbz(jbxx.getSjbz());
            newjbxx.setZzjzwxfsl(jbxx.getZzjzwxfsl());
            newjbxx.setZzjzwxfjg(jbxx.getZzjzwxfjg());
            newjbxx.setZzjzwxfbz(jbxx.getZzjzwxfbz());
            newjbxx.setGlmtxfsl(jbxx.getGlmtxfsl());
            newjbxx.setGlmtxfjg(jbxx.getGlmtxfjg());
            newjbxx.setGlmtxfbz(jbxx.getGlmtxfbz());
            newjbxx.setXlzxfsl(jbxx.getXlzxfsl());
            newjbxx.setXlzxfjg(jbxx.getXlzxfjg());
            newjbxx.setXlzxfbz(jbxx.getXlzxfbz());
            newjbxx.setHbwhzsl(jbxx.getHbwhzsl());
            newjbxx.setHbwhzcsl(jbxx.getHbwhzcsl());
            newjbxx.setHbwhjg(jbxx.getHbwhjg());
            newjbxx.setHbwhbz(jbxx.getHbwhbz());
            newjbxx.setWzahwqcssl(jbxx.getWzahwqcssl());
            newjbxx.setWzahwqcdwsl(jbxx.getWzahwqcdwsl());
            newjbxx.setWzahwqccsl(jbxx.getWzahwqccsl());
            newjbxx.setWzahwqcdsl(jbxx.getWzahwqcdsl());
            newjbxx.setWzahwqcjg(jbxx.getWzahwqcjg());
            newjbxx.setWzahwqcbz(jbxx.getWzahwqcbz());
            newjbxx.setLhyhsl(jbxx.getLhyhsl());
            newjbxx.setLhyhjg(jbxx.getLhyhjg());
            newjbxx.setLhyhbz(jbxx.getLhyhbz());
            newjbxx.setUpdatetime(new Date());
            this.maintenanceDao.updateMaintenance(newjbxx);
            logService.addLog(ModuleName.HDYH_GG, OpName.UPDATE, "");
            BaseResultOK result = new BaseResultOK();
            return result;
        }
    }

    @Override
    public BaseResult queryAllJsdw() {
        BaseQueryRecords records = this.maintenanceDao.queryAllJsdw();
        BaseResultOK result = new BaseResultOK(records);
        return result;
    }

    @Override
    public BaseResult queryZxgcById(int zxgcid) {
        String strdpt = "";
        BaseQueryRecords records = this.maintenanceDao
                .queryZxgcById(zxgcid);
        BaseResultOK result = new BaseResultOK(records);
        BaseQueryRecords<CYhFj> fjs = this.maintenanceDao
                .queryFjByZxgcid(zxgcid);
        BaseQueryRecords<CYhYdjdqk> ybbs = this.maintenanceDao
                .queryYbbByZxgcid(zxgcid);
        CYhZxgc zxgc = this.maintenanceDao.queryZxgcJbxxById(zxgcid);
        if (zxgc != null) {
            CXtDpt dpt = this.dptdao.queryDptById(zxgc.getGldw());
            if (dpt != null) {
                strdpt = dpt.getName();
            }
        }
        result.addToMap("dpt", strdpt);
        result.addToMap("fjs", fjs);
        result.addToMap("ybbs", ybbs);
        return result;
    }

    @Override
    public BaseResult queryZxgcs(int sfdg, Integer gldw, Date starttime,
                                 Date endtime, String content, String sEcho, int page, int rows) {
        String strstarttime = "";
        String strendtime = "";
        if (starttime != null) {
            strstarttime = DateTimeUtil.getTimeFmt(starttime,
                    "yyyy-MM-dd HH:mm:ss");
        }
        if (endtime != null) {
            strendtime = DateTimeUtil.getTimeFmt(endtime, "yyyy-MM-dd");
            strendtime += " 23:59:59";
        }

        String gldwstr = "";
        if (sfdg == 1) {
            CXtDpt dwdpt = this.dptService.findNode(new CXtDpt(gldw));
            if (dwdpt != null && dwdpt.getType() == Constants.DPT_SHENJU) {
                gldwstr = null;
            } else {
                List<CXtDpt> dpts = this.dptService.findChildrenNodes_r(new CXtDpt(gldw));
                dpts.add(new CXtDpt(gldw));

                for (int i = 0; i < dpts.size(); i++) {
                    gldwstr += dpts.get(i).getId();
                    if (i != dpts.size() - 1)
                        gldwstr += ",";
                }
            }

        } else {
            gldwstr = "" + gldw;
        }

        BaseQueryRecords records = this.maintenanceDao.queryZxgcs(
                gldwstr, strstarttime, strendtime, content, page,
                rows);
        BaseResultOK result = new BaseResultOK(records);
        result.addToMap("sEcho", sEcho);
        return result;
    }

    @Override
    public BaseResult delZxgc(List<Integer> zxgcidlist) {
        for (Integer zxgcid : zxgcidlist) {
            CYhZxgc zxgc = this.maintenanceDao.queryZxgcJbxxById(zxgcid);
            this.maintenanceDao.delYbb(zxgcid);
            this.maintenanceDao.delFj(zxgcid);
            if (zxgc != null) {
                this.maintenanceDao.delZxgc(zxgc);
            }
        }
        logService.addLog(ModuleName.HDYH_ZXGC, OpName.DEL, "");
        BaseResultOK result = new BaseResultOK();
        return result;
    }

    @Override
    public BaseResult addZxgc(CYhZxgc zxgc,
                              List<CYhYdjdqk> ybblist, List<CYhFj> fjlist, List<File> filelist,
                              List<String> filelistFileName) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        if (zxgc == null) {
            return new BaseResultFailed(Constants.RESULT_PROJ_NOTEXIST,
                    "专项工程不存在");
        } else {
            zxgc.setStatus(132);
            zxgc.setCreatetime(new Date());
            zxgc.setUpdatetime(new Date());
            this.maintenanceDao.addZxgc(zxgc);
            if (ybblist != null && ybblist.size() > 0) {
                for (CYhYdjdqk ybb : ybblist) {
                    ybb.setCYhZxgc(zxgc);
                    this.maintenanceDao.addCYhYbb(ybb);
                }
            }
            if (fjlist != null && fjlist.size() > 0) {
                try {
                    this.addCYhFj(zxgc, filelist, filelistFileName);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            logService.addLog(ModuleName.HDYH_ZXGC, OpName.ADD, zxgc.getGcmc());
            BaseResultOK result = new BaseResultOK();
            return result;
        }
    }

    @Override
    public BaseResult updateZxgc(CYhZxgc zxgc,
                                 List<CYhYdjdqk> ybblist, List<CYhFj> fjlist,
                                 List<Integer> delybbids, List<Integer> delfileids,
                                 List<File> filelist, List<String> filelistFileName) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        if (zxgc == null) {
            return new BaseResultFailed(Constants.RESULT_PROJ_NOTEXIST,
                    "专项工程不存在");
        } else {
            if (delybbids != null && delybbids.size() > 0) {
                for (Integer delybbid : delybbids) {
                    CYhYdjdqk ybb = this.maintenanceDao.queryYbbByid(delybbid);
                    if (ybb != null) {
                        this.maintenanceDao.delYbb(ybb);
                    }
                }
            }
            if (delfileids != null && delfileids.size() > 0) {
                for (Integer delfileid : delfileids) {
                    CYhFj cyhfj = this.maintenanceDao.queryFjByid(delfileid);
                    if (cyhfj != null) {
                        this.maintenanceDao.delCYhFj(cyhfj);
                    }
                }
            }
            CYhZxgc updateZxgc = this.maintenanceDao.queryZxgcJbxxById(zxgc
                    .getZxgcid());
            if (updateZxgc != null) {
                updateZxgc.setGcmc(zxgc.getGcmc());
                updateZxgc.setStarttime(zxgc.getStarttime());
                updateZxgc.setEndtime(zxgc.getEndtime());
                updateZxgc.setGldw(zxgc.getGldw());
                updateZxgc.setJldw(zxgc.getJldw());
                updateZxgc.setJsdw(zxgc.getJsdw());
                updateZxgc.setSjdw(zxgc.getSjdw());
                updateZxgc.setSgdw(zxgc.getSgdw());
                updateZxgc.setTz(zxgc.getTz());
                // updateZxgc.setStatus(zxgc.getStatus());
                updateZxgc.setBz(zxgc.getBz());
                updateZxgc.setUpdatetime(new Date());
                this.maintenanceDao.updateZxgc(updateZxgc);
            }
            if (ybblist != null && ybblist.size() > 0) {
                for (CYhYdjdqk ybb : ybblist) {
                    ybb.setCYhZxgc(zxgc);
                    this.maintenanceDao.addCYhYbb(ybb);
                }
            }
            if (fjlist != null && fjlist.size() > 0) {
                try {
                    this.addCYhFj(zxgc, filelist, filelistFileName);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            logService.addLog(ModuleName.HDYH_ZXGC, OpName.UPDATE, updateZxgc.getGcmc());
            BaseResultOK result = new BaseResultOK();
            return result;
        }
    }

    public void addCYhFj(CYhZxgc zxgc, List<File> filelist,
                         List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        if (filelist != null && filelist.size() > 0) {
            String root = this.getRealPath("upload");// upload存放路径
            File infile = null;
            FileUtils.mkdir(root);
            int zxgcid = zxgc.getZxgcid();
            for (int i = 0; i < filelist.size(); i++) {
                // io流操作
                infile = filelist.get(i);
                String infileMd5 = FileMd5.getMd5ByFile(infile);
                String filename = FileUtils.writeToFile(infile, infileMd5, root
                        + "\\" + filelistFileName.get(i));
                CYhFj md5fj = this.maintenanceDao.queryFjByMd5(zxgcid,
                        infileMd5);
                // 数据库资源插入操作
                if (md5fj == null) {
                    CYhFj fj = new CYhFj();
                    fj.setCYhZxgc(zxgc);
                    fj.setResname(filename);
                    fj.setRespath("\\" + filename);
                    fj.setRessize(String.valueOf(infile.length() * 1.0 / 1000));
                    fj.setFilemd5(infileMd5);
                    fj.setCreater(user.getId());
                    fj.setCreatetime(new Date());
                    fj.setUpdatetime(new Date());
                    this.maintenanceDao.addCYhFj(fj);
                }
            }
        }
    }

    @Override
    public BaseResult queryBranchById(int id) {
        BaseQueryRecords records = this.maintenanceDao.queryBranchById(id);
        BaseResultOK result = new BaseResultOK(records);
        return result;
    }

    @Override
    public BaseResult queryBranches(int sfdg, Integer dw, Date starttime, Date endtime,
                                    String sEcho, int page, int rows) {
        String strstarttime = "";
        String strendtime = "";
        if (starttime != null) {
            strstarttime = DateTimeUtil.getTimeFmt(starttime,
                    "yyyy-MM-dd HH:mm:ss");
        }
        if (endtime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endtime);
            calendar.add(Calendar.MONTH, 1);
            strendtime = DateTimeUtil.getTimeFmt(calendar.getTime(),
                    "yyyy-MM-dd HH:mm:ss");
        }

        String dwstr = "";
        if (sfdg == 1) {

            //如果是省
            CXtDpt dwdpt = this.dptService.findNode(new CXtDpt(dw));
            if (dwdpt != null && dwdpt.getType() == Constants.DPT_SHENJU) {
                dwstr = null;
            } else {
                List<CXtDpt> dpts = this.dptService.findChildrenNodes_r(new CXtDpt(dw));
                dpts.add(new CXtDpt(dw));

                for (int i = 0; i < dpts.size(); i++) {
                    dwstr += dpts.get(i).getId();
                    if (i != dpts.size() - 1)
                        dwstr += ",";
                }
            }
        } else {
            dwstr = "" + dw;
        }

        BaseQueryRecords records = this.maintenanceDao.queryBranches(dwstr,
                strstarttime, strendtime, page, rows);

        BaseResultOK result = new BaseResultOK(records);
        result.addToMap("sEcho", sEcho);
        return result;
    }

    @Override
    public BaseResult delBranch(List<Integer> zxjbxxidlist) {
        for (Integer zxjbxxid : zxjbxxidlist) {
            CYhZxjbxx zxjbxx = this.maintenanceDao
                    .queryZxjbxxById(zxjbxxid);
            if (zxjbxx != null) {
                this.maintenanceDao.delBranch(zxjbxx);
            }
        }
        logService.addLog(ModuleName.HDYH_ZX, OpName.DEL, "");
        BaseResultOK result = new BaseResultOK();
        return result;
    }

    @Override
    public BaseResult addBranch(CYhZxjbxx zxjbxx) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        zxjbxx.setCreater(user.getId());
        zxjbxx.setCreatetime(new Date());
        zxjbxx.setUpdatetime(new Date());
        this.maintenanceDao.addBranch(zxjbxx);
        logService.addLog(ModuleName.HDYH_ZX, OpName.ADD, this.dptdao.queryDptById(zxjbxx.getDw()).getName());
        BaseResultOK result = new BaseResultOK();
        return result;
    }

    @Override
    public BaseResult updateBranch(CYhZxjbxx zxjbxx) {
        CYhZxjbxx newzxjbxx = this.maintenanceDao.queryZxjbxxById(zxjbxx
                .getId());
        if (newzxjbxx == null) {
            return new BaseResultFailed(Constants.RESULT_BRANCH_NOTEXIST,
                    "支线不存在");
        } else {
            newzxjbxx.setDw(zxjbxx.getDw());
            newzxjbxx.setStarttime(zxjbxx.getStarttime());
            newzxjbxx.setClsl(zxjbxx.getClsl());
            newzxjbxx.setClje(zxjbxx.getClje());
            newzxjbxx.setClbz(zxjbxx.getClbz());
            newzxjbxx.setAhsjsl(zxjbxx.getAhsjsl());
            newzxjbxx.setAhsjje(zxjbxx.getAhsjje());
            newzxjbxx.setAhsjbz(zxjbxx.getAhsjbz());
            newzxjbxx.setZzjzwxfsl(zxjbxx.getZzjzwxfsl());
            newzxjbxx.setZzjzwxfje(zxjbxx.getZzjzwxfje());
            newzxjbxx.setZzjzwxfbz(zxjbxx.getZzjzwxfbz());
            newzxjbxx.setWxglmtsl(zxjbxx.getWxglmtsl());
            newzxjbxx.setWxglmtje(zxjbxx.getWxglmtje());
            newzxjbxx.setWxglmtbz(zxjbxx.getWxglmtbz());
            newzxjbxx.setHbwhzsl(zxjbxx.getHbwhzsl());
            newzxjbxx.setHbwhzcsl(zxjbxx.getHbwhzcsl());
            newzxjbxx.setHbwhje(zxjbxx.getHbwhje());
            newzxjbxx.setHbwhbz(zxjbxx.getHbwhbz());
            newzxjbxx.setDlccssl(zxjbxx.getDlccssl());
            newzxjbxx.setDlccdwsl(zxjbxx.getDlccdwsl());
            newzxjbxx.setDlccje(zxjbxx.getDlccje());
            newzxjbxx.setDlccbz(zxjbxx.getDlccbz());
            newzxjbxx.setQtqzcsl(zxjbxx.getQtqzcsl());
            newzxjbxx.setQtqzdsl(zxjbxx.getQtqzdsl());
            newzxjbxx.setQtqzje(zxjbxx.getQtqzje());
            newzxjbxx.setQtqzbz(zxjbxx.getQtqzbz());
            newzxjbxx.setQtwxgcsl(zxjbxx.getQtwxgcsl());
            newzxjbxx.setQtwxgcje(zxjbxx.getQtwxgcje());
            newzxjbxx.setQtwxgcbz(zxjbxx.getQtwxgcbz());
            newzxjbxx.setUpdatetime(new Date());
            this.maintenanceDao.updateBranch(newzxjbxx);
            logService.addLog(ModuleName.HDYH_ZX, OpName.UPDATE, this.dptdao.queryDptById(zxjbxx.getDw()).getName());
            BaseResultOK result = new BaseResultOK();
            return result;
        }
    }

    @Override
    public BaseResult addMaintenance(CYhGgjbxx jbxx) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        jbxx.setCreater(user.getId());
        jbxx.setCreatetime(new Date());
        jbxx.setUpdatetime(new Date());
        this.maintenanceDao.addMaintenance(jbxx);
        logService.addLog(ModuleName.HDYH_GG, OpName.ADD, "");
        BaseResultOK result = new BaseResultOK();
        return result;
    }

    @Override
    public BaseResult addCyhfj(int zxgcid, List<File> filelist,
                               List<String> filelistFileName) {
        CYhZxgc zxgc = this.maintenanceDao.queryZxgcJbxxById(zxgcid);
        if (zxgc == null) {
            return new BaseResultFailed(Constants.RESULT_PROJ_NOTEXIST,
                    "专项工程不存在");
        } else {
            try {
                this.addCYhFj(zxgc, filelist, filelistFileName);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            BaseResultOK result = new BaseResultOK();
            return result;
        }
    }

    @Override
    public BaseResult downloadCyhfj(int fjid) {
        CYhFj fj = this.maintenanceDao.queryFjByid(fjid);
        if (fj == null) {
            return new BaseResultFailed(Constants.RESULT_ATTACH_NOTEXIST,
                    "附件不存在");
        } else {
            String root = this.getRealPath("upload");// upload存放路径
            String filename = fj.getRespath().replace("\\", "/");
            boolean isexisted = FileUtils.ifFileExist(root + filename);
            try {
                if (isexisted) {
                    BaseResultOK baseResultOK = new BaseResultOK();
                    baseResultOK.addToMap("filename", filename);
                    return baseResultOK;
                } else {
                    return new BaseResultFailed(
                            Constants.RESULT_ATTACH_NOTEXIST, "附件不存在");
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                return new BaseResultFailed(Constants.RESULT_ATTACH_NOTEXIST,
                        "附件不存在");
            }

        }
    }

    @Override
    public BaseResult downloadYjqtgcfj(int fjid) {
        CYhYjqtgcfj fj = this.maintenanceDao.queryYjqtgcfjByid(fjid);
        if (fj == null) {
            return new BaseResultFailed(Constants.RESULT_ATTACH_NOTEXIST,
                    "附件不存在");
        } else {
            String root = this.getRealPath("upload");// upload存放路径
            String filename = fj.getFpath().replace("\\", "/");
            boolean isexisted = FileUtils.ifFileExist(root + filename);
            try {
                if (isexisted) {
                    BaseResultOK baseResultOK = new BaseResultOK();
                    baseResultOK.addToMap("filename", filename);
                    return baseResultOK;
                } else {
                    return new BaseResultFailed(
                            Constants.RESULT_ATTACH_NOTEXIST, "附件不存在");
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                return new BaseResultFailed(Constants.RESULT_ATTACH_NOTEXIST,
                        "附件不存在");
            }

        }
    }

    @Override
    public BaseResult delCyhfj(int zxgcid, List<Integer> delfileids) {
        for (Integer fjid : delfileids) {
            CYhFj fj = this.maintenanceDao.queryFjByid(fjid);
            if (fj != null) {
                this.maintenanceDao.delCYhFj(fj);
            }
        }
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult addCyhybb(int zxgcid, CYhYdjdqk cyhybb) {
        CYhZxgc zxgc = this.maintenanceDao.queryZxgcJbxxById(zxgcid);
        if (zxgc == null) {
            return new BaseResultFailed(Constants.RESULT_PROJ_NOTEXIST,
                    "专项工程不存在");
        } else {
            //时间bug 处理不选择时间 出现30 不是1号
            String temptime = DateTimeUtil.getTimeFmt(cyhybb.getNy(), "yyyy-MM") + "-01 00:00:00";
            cyhybb.setNy(DateTimeUtil.getDateByStringFmt(temptime));
            cyhybb.setCYhZxgc(zxgc);
            cyhybb.setCreatetime(new Date());
            cyhybb.setUpdatetime(new Date());
            this.maintenanceDao.addCYhYbb(cyhybb);
            CYhYdjdqk ydjdqk = this.maintenanceDao.queryLastYdjdqk(zxgcid);
            if (ydjdqk != null) {
                zxgc.setStatus(ydjdqk.getXmzt());
            }
            logService.addLog(ModuleName.HDYH_ZXGC, OpName.ADD, "月度进度情况");
            BaseResultOK result = new BaseResultOK();
            return result;
        }
    }

    @Override
    public BaseResult delCyhybb(int zxgcid, List<Integer> delybbids) {
        CYhZxgc zxgc = this.maintenanceDao.queryZxgcJbxxById(zxgcid);
        if (zxgc == null) {
            return new BaseResultFailed(Constants.RESULT_PROJ_NOTEXIST,
                    "专项工程不存在");
        } else {
            for (Integer ybbid : delybbids) {
                CYhYdjdqk ybb = this.maintenanceDao.queryYbbByid(ybbid);
                if (ybb != null) {
                    this.maintenanceDao.delCYhYbb(ybb);
                }
            }
            CYhYdjdqk ydjdqk = this.maintenanceDao.queryLastYdjdqk(zxgcid);
            if (ydjdqk != null) {
                zxgc.setStatus(ydjdqk.getXmzt());
            } else {
                zxgc.setStatus(132);
            }
            logService.addLog(ModuleName.HDYH_ZXGC, OpName.DEL, "月度进度情况");
            BaseResultOK baseResultOK = new BaseResultOK();
            return baseResultOK;
        }
    }

    @Override
    public BaseResult updateCyhybb(int id, CYhYdjdqk cyhybb) {
        CYhYdjdqk ybb = this.maintenanceDao.queryYbbByid(id);
        if (ybb != null) {
            ybb.setDwmc(cyhybb.getDwmc());
            ybb.setNy(cyhybb.getNy());
            ybb.setBywcje(cyhybb.getBywcje());
            ybb.setXmzt(cyhybb.getXmzt());
            ybb.setXmjdqk(cyhybb.getXmjdqk());
            ybb.setBz(cyhybb.getBz());
            ybb.setUpdatetime(new Date());
            this.maintenanceDao.updateCyhybb(ybb);
        }
        CYhZxgc zxgc = ybb.getCYhZxgc();
        CYhYdjdqk ydjdqk = this.maintenanceDao.queryLastYdjdqk(zxgc
                .getZxgcid());
        if (ydjdqk != null) {
            zxgc.setStatus(ydjdqk.getXmzt());
        }
        logService.addLog(ModuleName.HDYH_ZXGC, OpName.UPDATE, "月度进度情况");
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult queryCyhybb(int id) {
        CYhYdjdqk ybb = this.maintenanceDao.queryYbbByid(id);
        String xmzt = this.dicDao.queryAttrDesc(ybb.getXmzt());
        BaseResultOK baseResultOK = new BaseResultOK(ybb);
        baseResultOK.addToMap("xmzt", xmzt);
        return baseResultOK;
    }

    @Override
    public BaseResult queryLastYd(int zxgcid) {
        CYhYdjdqk yb = this.maintenanceDao.queryLastYdjdqk(zxgcid);
        BaseResultOK baseResultOK = new BaseResultOK(yb);
        return baseResultOK;
    }


    @Override
    public BaseResult queryZxgcStarttime() {
        Date earliestzxgc = this.maintenanceDao.queryEarliesttime();
        Date lastestzxgc = this.maintenanceDao.queryLastesttime();
        BaseResultOK baseResultOK = new BaseResultOK();
        String strnow = DateTimeUtil.getTimeFmt(new Date(), "yyyy");
        baseResultOK.addToMap("starttime", earliestzxgc == null ? strnow
                : DateTimeUtil.getTimeFmt(earliestzxgc, "yyyy"));
        baseResultOK.addToMap("endtime", lastestzxgc == null ? strnow
                : DateTimeUtil.getTimeFmt(lastestzxgc, "yyyy"));
        return baseResultOK;
    }

    @Override
    public BaseResult queryGgStarttime() {
        Date earliestgg = this.maintenanceDao.queryGgEarliesttime();
        Date lastestgg = this.maintenanceDao.queryGgLastesttime();
        BaseResultOK baseResultOK = new BaseResultOK();
        String strnow = DateTimeUtil.getTimeFmt(new Date(), "yyyy");
        baseResultOK.addToMap("starttime", earliestgg == null ? strnow
                : DateTimeUtil.getTimeFmt(earliestgg, "yyyy"));
        baseResultOK.addToMap("endtime", lastestgg == null ? strnow
                : DateTimeUtil.getTimeFmt(lastestgg, "yyyy"));
        return baseResultOK;
    }

    @Override
    public BaseResult queryZxStarttime() {
        Date earliestzx = this.maintenanceDao.queryZxEarliesttime();
        Date lastestzx = this.maintenanceDao.queryZxLastesttime();
        BaseResultOK baseResultOK = new BaseResultOK();
        String strnow = DateTimeUtil.getTimeFmt(new Date(), "yyyy");
        baseResultOK.addToMap("starttime", earliestzx == null ? strnow
                : DateTimeUtil.getTimeFmt(earliestzx, "yyyy"));
        baseResultOK.addToMap("endtime", lastestzx == null ? strnow
                : DateTimeUtil.getTimeFmt(lastestzx, "yyyy"));
        return baseResultOK;
    }

    @Override
    public BaseResult searchYjqtgc(Integer sfdg, Integer gldw, Integer hdaoid, Integer hduanid, Date startdate, Date enddate, String content, String sEcho, int page, int rows) {
        String dw = "";
        String starttime = "";
        String endtime = "";
        if (startdate != null) {
            starttime = DateTimeUtil.getTimeFmt(startdate,
                    "yyyy-MM-dd") + " 00:00:00";
        }
        if (enddate != null) {
            endtime = DateTimeUtil.getTimeFmt(enddate,
                    "yyyy-MM-dd") + " 23:59:59";
        }
        if (sfdg == 1) {
            //如果是省
            if (gldw == null) {
                gldw = 1;
            }
            CXtDpt dwdpt = this.dptService.findNode(new CXtDpt(gldw));
            if (dwdpt != null && dwdpt.getType() == Constants.DPT_SHENJU) {
                dw = null;
            } else {
                List<CXtDpt> dpts = this.dptService.findChildrenNodes_r(new CXtDpt(gldw));
                dpts.add(new CXtDpt(gldw));

                for (int i = 0; i < dpts.size(); i++) {
                    dw += dpts.get(i).getId();
                    if (i != dpts.size() - 1)
                        dw += ",";
                }
            }
        } else {
            dw = "" + dw;
        }
        BaseQueryRecords records = this.maintenanceDao.searchYjqtgc(dw, hdaoid, hduanid,
                starttime, endtime, content, page, rows);
        BaseResultOK result = new BaseResultOK(records);
        result.addToMap("sEcho", sEcho);
        return result;
    }

    @Override
    public BaseResult delYjqtgc(List<Integer> ids) {
        StringBuffer sb = new StringBuffer("");
        if (ids != null && ids.size() > 0) {
            for (int id : ids) {
                sb.append(id + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        if (!"".equals(sb.toString())) {
            this.maintenanceDao.delYjqtgcInId(sb.toString());
            this.maintenanceDao.delYjqtgcfjInFid(sb.toString());
        }
        BaseResultOK s = new BaseResultOK();
        return s;
    }

    @Override
    public BaseResult queryYjqtgcById(int id) {
        BaseQueryRecords gc = new BaseQueryRecords();
        BaseQueryRecords<CYhYjqtgcfj> fjs = new BaseQueryRecords<>();
        if (id > 0) {
            gc = this.maintenanceDao.queryYjqtgcById(id);
            fjs = this.maintenanceDao.queryYjqtgcfjByPid(id);
        }
        BaseResultOK s = new BaseResultOK(gc);
        s.addToMap("fj", fjs);
        return s;
    }

    @Override
    public BaseResult updateYjqtgc(CYhYjqtgc yjqtgc, List<Integer> delfileids, List<File> filelist, List<String> filelistFileName) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        int id = yjqtgc.getId();
        CYhYjqtgc gc = (CYhYjqtgc) this.maintenanceDao.queryYjqtgcByPk(id);
        if (gc != null) {
            gc.setName(yjqtgc.getName());
            gc.setEventdesc(yjqtgc.getEventdesc());
            gc.setStarttime(yjqtgc.getStarttime());
            gc.setMandept(yjqtgc.getMandept());
            gc.setHdaoid(yjqtgc.getHdaoid());
            gc.setHduanid(yjqtgc.getHduanid());
            gc.setAddress(yjqtgc.getAddress());
            gc.setLosecase(yjqtgc.getLosecase());
            gc.setMainreason(yjqtgc.getMainreason());
            gc.setRealcase(yjqtgc.getRealcase());
            gc.setParticipants(yjqtgc.getParticipants());
            gc.setCost(yjqtgc.getCost());
            gc.setRestoretime(yjqtgc.getRestoretime());
            gc.setRemark(yjqtgc.getRemark());
            gc.setUpdatetime(new Date());
            this.maintenanceDao.updateYjqtgc(gc);
        }
        if (delfileids != null && delfileids.size() > 0) {
            StringBuffer sb = new StringBuffer("");
            for (int delid : delfileids) {
                sb.append(delid + ",");
            }
            if (!"".equals(sb.toString())) {
                sb.deleteCharAt(sb.length() - 1);
                this.maintenanceDao.delYjqtgcfjInId(sb.toString());
            }
        }
        addYjqtgcfj(user.getId(), id, filelist, filelistFileName);
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }


    @Override
    public BaseResult addYjqtgc(CYhYjqtgc yjqtgc, List<File> filelist, List<String> filelistFileName) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        if (yjqtgc == null) {
            return new BaseResultFailed("应急抢通工程不存在");
        } else {
            int userid = user.getId();
            yjqtgc.setCreater(userid);
            yjqtgc.setCreatetime(new Date());
            yjqtgc.setUpdatetime(new Date());
            this.maintenanceDao.addYjqtgc(yjqtgc);
            int pid = yjqtgc.getId();
            addYjqtgcfj(userid, pid, filelist, filelistFileName);
            BaseResultOK baseResultOK = new BaseResultOK();
            return baseResultOK;
        }
    }

    private void addYjqtgcfj(int userid, int pid, List<File> filelist, List<String> filelistFileName) {
        if (filelist != null && filelist.size() > 0) {
            String root = this.getRealPath("upload");// upload存放路径
            File file = null;
            FileUtils.mkdir(root);
            for (int i = 0; i < filelist.size(); i++) {
                file = filelist.get(i);
                String infileMd5 = FileMd5.getMd5ByFile(file);
                String filename = FileUtils.writeToFile(file, infileMd5, root
                        + "\\" + filelistFileName.get(i));
                CYhYjqtgcfj md5fj = this.maintenanceDao.queryYjqtgcfjByMd5(pid,
                        infileMd5);
                if (md5fj == null) {
                    CYhYjqtgcfj fj = new CYhYjqtgcfj();
                    fj.setPid(pid);
                    String fname = filelistFileName.get(i);
                    String ext = filename.substring(filename.indexOf(".") + 1, filename.length());
                    fj.setFname(fname);
                    fj.setFpath("\\" + filename);
                    fj.setFsize(String.valueOf(file.length() * 1.0 / 1000));
                    fj.setFmd5(infileMd5);
                    fj.setFtype(FileUtils.judgeFileType(ext));
                    fj.setCreater(userid);
                    fj.setCreatetime(new Date());
                    fj.setUpdatetime(new Date());
                    this.maintenanceDao.addCYhYjqtgcfj(fj);
                }
            }
        }
    }

}
