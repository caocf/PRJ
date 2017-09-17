package com.channel.cruise;


import com.channel.bean.*;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.dic.service.XzqhdmService;
import com.channel.hangdao.dao.HangDaoDao;
import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import com.channel.model.hd.CHdHdaojcxx;
import com.channel.model.user.CXtDpt;
import com.channel.model.user.CXtUser;
import com.channel.model.user.CZdXzqhdm;
import com.channel.model.xc.*;
import com.channel.user.dao.UserDao;
import com.channel.user.dao.impl.DptDaoImpl;
import com.channel.user.dao.impl.UserDaoImpl;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.common.utils.FileMd5;
import com.common.utils.FileUtils;
import com.common.utils.parser.JSONArrayParser;
import com.common.utils.parser.JSONBeanParser;
import com.common.utils.parser.JSONParser;
import com.common.utils.parser.ParseErrorException;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("cruiseService")
public class CruiseService extends BaseService {

    @Resource(name = "cruiseDao")
    private CruiseDao cruiseDao;
    @Resource
    private LogService logService;
    @Resource(name = "hangdaodao")
    private HangDaoDao hangDaoDao;
    @Resource(name = "dptdao")
    private DptDaoImpl dptDao;
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "xzqhdmservice")
    private XzqhdmService xzqhdmService;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmdao;

    public BaseResult queryCruise(List<Integer> ids, Date starttime, Date endtime, int page, int rows, String sEcho) {
        String strids = "";
        String strstarttime = "";
        String strendtime = "";
        CXtDpt udept = (CXtDpt) getSession().getAttribute("dpt");
        int userxzqh = udept.getXzqh();
        if (ids != null && ids.size() > 0) {
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < ids.size(); i++) {
                sb.append(String.valueOf(ids.get(i)) + ",");
            }
            if (!"".equals(sb.toString()) && sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                strids = sb.toString();
            }
        }
        if (starttime != null) {
            strstarttime = DateTimeUtil.getTimeFmt(starttime, "yyyy-MM-dd") + " 00:00:00";
        }
        if (endtime != null) {
            strendtime = DateTimeUtil.getTimeFmt(endtime, "yyyy-MM-dd") + " 23:59:59";
        }
        BaseQueryRecords records = new BaseQueryRecords();
        CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(userxzqh);
        int xzqhtype = xzqhdm.getType();
        if (xzqhtype == 2) {
            List<CZdXzqhdm> xzqhlist = this.xzqhdmService.findChildrenNodes_r(xzqhdm);
            StringBuffer sb=new StringBuffer("");
            if (xzqhlist!=null&&xzqhlist.size()>0){
                for (CZdXzqhdm d:xzqhlist){
                    sb.append(d.getId()+",");
                }
                sb.append(userxzqh);
            }
            records = this.cruiseDao.queryCruiseXzqh(strids, strstarttime, strendtime, sb.toString(), page, rows);
        } else {
            records = this.cruiseDao.queryCruise(strids, strstarttime, strendtime, userxzqh, page, rows);
        }
        List<Object[]> list = records.getData();
        List<XcDt> ret = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Object[] arr = list.get(i);
            CXcGk gk = (CXcGk) arr[0];
            CHdHdaojcxx hdao = (CHdHdaojcxx) arr[1];
            CXtDpt dept = (CXtDpt) arr[2];
            XcDt xd = new XcDt();
            xd.setId(gk.getId());
            xd.setHdmc(hdao.getHdmc());
            xd.setQd(gk.getQd());
            xd.setZd(gk.getZd());
            xd.setQdzh(gk.getQdzh());
            xd.setZdzh(gk.getZdzh());
            xd.setDeptname(dept.getName());
            int ztqkidx = gk.getZtqk();
            String ztqk = "好";
            switch (ztqkidx) {
                case 1:
                    ztqk = "好";
                    break;
                case 2:
                    ztqk = "较好";
                    break;
                case 3:
                    ztqk = "一般";
                    break;
                case 4:
                    ztqk = "差";
                    break;
            }
            xd.setZtqk(ztqk);
            xd.setStarttime(gk.getStarttime());
            xd.setEndtime(gk.getEndtime());
            CXcYhgk yhgk = this.cruiseDao.queryYhgkByXcid(gk.getId());
            xd.setYhgkid(0);
            if (yhgk != null) {
                xd.setYhgkid(yhgk.getId());
            }
            ret.add(xd);
        }
        records.setData(ret);
        BaseResultOK result = new BaseResultOK(records);
        result.addToMap("sEcho", sEcho);
        return result;
    }

    private List<CXcFj> queryCXcFj(Integer id) {
        List<CXcFj> fjlist = this.cruiseDao.queryFjByPid(id).getData();
        List<CXcFj> lists = new ArrayList<CXcFj>();
        if (fjlist != null && fjlist.size() > 0) {
            for (CXcFj temp : fjlist) {
                CXcFj fj = new CXcFj();
                fj.setId(temp.getId());
                fj.setXcid(id);
                fj.setFname(temp.getFname());
                fj.setFmd5(temp.getFmd5());
                fj.setFpath(temp.getFpath());
                fj.setFsize(temp.getFsize());
                fj.setFtype(temp.getFtype());
                lists.add(fj);
            }
        }
        return lists;
    }

    public BaseResult queryCruiseById(int id) {
        String dept = "";
        List<XcBean> records = this.cruiseDao.queryCruiseById(id);
        List<CXcFj> lists = new ArrayList<CXcFj>();
        lists = this.queryCXcFj(id);
        if (records != null && records.size() > 0) {
            XcBean bean = records.get(0);
            CXtDpt dpt = this.dptDao.queryDptById(bean.getDept());
            if (dpt != null) {
                dept = dpt.getName();
            }
        }
        BaseResultOK result = new BaseResultOK(records);
        result.addToMap("dept", dept);
        result.addToMap("fjlist", lists);
        return result;
    }

    public BaseResult delCruise(List<Integer> ids) {
        if (ids != null && ids.size() > 0) {
            for (Integer id : ids) {
                delCruiseById(id);
            }
        }
        BaseResultOK result = new BaseResultOK();
        return result;
    }

    private void delCruiseById(Integer id) {
        CXcGk gk = this.cruiseDao.queryGkById(id);
        if (gk != null) {
            List<CXcQk> qkfls = this.cruiseDao.queryQkflsById(id);
            if (qkfls != null && qkfls.size() > 0) {
                StringBuffer qkids = new StringBuffer();
                for (CXcQk qkfl : qkfls) {
                    qkids.append(qkfl.getId() + ",");
                }
                if (qkids.length() > 0) {
                    qkids.deleteCharAt(qkids.length() - 1);
                }
                List<CXcWt> qkmses = this.cruiseDao.queryWtsByIds(qkids.toString());
                if (qkmses != null && qkmses.size() > 0) {
                    StringBuffer msids = new StringBuffer();
                    for (CXcWt qkms : qkmses) {
                        msids.append(qkms.getId() + ",");
                    }
                    if (msids.length() > 0) {
                        msids.deleteCharAt(msids.length() - 1);
                        this.cruiseDao.delSj(msids.toString());
                        this.cruiseDao.delWt(msids.toString());
                    }
                }
                this.cruiseDao.delQk(qkids.toString());
            }
            this.cruiseDao.delFjByXcid(id);
            this.cruiseDao.delObj(gk);
        }
    }

    private void delCruiseByIdNoFj(Integer id) {
        CXcGk gk = this.cruiseDao.queryGkById(id);
        if (gk != null) {
            List<CXcQk> qkfls = this.cruiseDao.queryQkflsById(id);
            if (qkfls != null && qkfls.size() > 0) {
                StringBuffer qkids = new StringBuffer();
                for (CXcQk qkfl : qkfls) {
                    qkids.append(qkfl.getId() + ",");
                }
                if (qkids.length() > 0) {
                    qkids.deleteCharAt(qkids.length() - 1);
                }
                List<CXcWt> qkmses = this.cruiseDao.queryWtsByIds(qkids.toString());
                if (qkmses != null && qkmses.size() > 0) {
                    StringBuffer msids = new StringBuffer();
                    for (CXcWt qkms : qkmses) {
                        msids.append(qkms.getId() + ",");
                    }
                    if (msids.length() > 0) {
                        msids.deleteCharAt(msids.length() - 1);
                        this.cruiseDao.delSj(msids.toString());
                        this.cruiseDao.delWt(msids.toString());
                    }
                }
                this.cruiseDao.delQk(qkids.toString());
            }
            this.cruiseDao.delObj(gk);
        }
    }

    private int saveCruise(CXtUser user, CXcGk tempgk, GkBean gk, List<File> filelist, List<String> filelistFileName) {
        int returnid = 0;
        try {
            if (gk != null) {
                int userid = user.getId();
                Date now = new Date();
                tempgk.setCreater(userid);
                tempgk.setCreatetime(now);
                tempgk.setUpdatetime(now);
                this.cruiseDao.save(tempgk);
                returnid = tempgk.getId();
                int xcid = tempgk.getId();
                List<QkBean> qks = gk.getQks();
                if (qks != null && qks.size() > 0) {
                    for (QkBean qkbean : qks) {
                        CXcQk qk = new CXcQk();
                        qk.setXcid(xcid);
                        qk.setQk(qkbean.getQk());
                        qk.setQksm(qkbean.getQksm());
                        qk.setCreater(userid);
                        qk.setCreatetime(now);
                        qk.setUpdatetime(now);
                        this.cruiseDao.save(qk);
                        List<WtBean> wts = qkbean.getWts();
                        if (wts != null && wts.size() > 0) {
                            int qkid = qk.getId();
                            for (WtBean wtbean : wts) {
                                CXcWt wt = new CXcWt();
                                wt.setQkid(qkid);
                                wt.setWt(wtbean.getWt());
                                wt.setWtsm(wtbean.getWtsm());
                                wt.setClyj(wtbean.getClyj());
                                wt.setCljg(wtbean.getCljg());
                                wt.setCreater(userid);
                                wt.setCreatetime(now);
                                wt.setUpdatetime(now);
                                this.cruiseDao.save(wt);
                                List<CXcSj> sjs = wtbean.getSjs();
                                if (sjs != null && sjs.size() > 0) {
                                    int wtid = wt.getId();
                                    for (CXcSj sj : sjs) {
                                        sj.setWtid(wtid);
                                        sj.setCreater(userid);
                                        sj.setCreatetime(now);
                                        sj.setUpdatetime(now);
                                        this.cruiseDao.save(sj);
                                    }
                                }
                            }
                        }
                    }
                }

                //附件处理
                if (filelist != null && filelist.size() > 0) {
                    String root = this.getRealPath("upload");// upload存放路径
                    File infile = null;
                    FileUtils.mkdir(root);
                    for (int i = 0; i < filelist.size(); i++) {
                        // io流操作
                        infile = filelist.get(i);
                        String infileMd5 = FileMd5.getMd5ByFile(infile);
                        String tempfilename = filelistFileName.get(i);
                        String filename = FileUtils.writeToFile(infile, infileMd5, root
                                + "\\" + tempfilename);
                        String ext = FileUtils.getFileExtension(tempfilename);
                        CXcFj md5fj = this.cruiseDao.queryFjByMd5(xcid,
                                infileMd5);
                        // 数据库资源插入操作
                        if (md5fj == null) {
                            CXcFj fj = new CXcFj();
                            fj.setXcid(xcid);
                            fj.setFname(filename);
                            fj.setFtype(FileUtils.judgeFileType(ext));
                            fj.setFpath("\\" + filename);
                            fj.setFsize(String.valueOf(infile.length() * 1.0 / 1000));
                            fj.setFmd5(infileMd5);
                            fj.setCreater(user.getId());
                            fj.setCreatetime(new Date());
                            fj.setUpdatetime(new Date());
                            this.cruiseDao.save(fj);
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnid;
    }

    private int saveUpdateCruise(CXtUser curuser, CXtUser user, CXcGk tempgk, GkBean gk, List<File> filelist, List<String> filelistFileName) {
        int returnid = 0;
        try {
            if (gk != null) {
                int userid = user.getId();
                Date now = new Date();
                tempgk.setCreater(userid);
                tempgk.setCreatetime(user.getCreatetime());
                tempgk.setUpdatetime(now);
                this.cruiseDao.save(tempgk);
                returnid = tempgk.getId();
                int xcid = tempgk.getId();
                List<QkBean> qks = gk.getQks();
                if (qks != null && qks.size() > 0) {
                    for (QkBean qkbean : qks) {
                        CXcQk qk = new CXcQk();
                        qk.setXcid(xcid);
                        qk.setQk(qkbean.getQk());
                        qk.setQksm(qkbean.getQksm());
                        qk.setCreater(userid);
                        qk.setCreatetime(user.getCreatetime());
                        qk.setUpdatetime(now);
                        this.cruiseDao.save(qk);
                        List<WtBean> wts = qkbean.getWts();
                        if (wts != null && wts.size() > 0) {
                            int qkid = qk.getId();
                            for (WtBean wtbean : wts) {
                                CXcWt wt = new CXcWt();
                                wt.setQkid(qkid);
                                wt.setWt(wtbean.getWt());
                                wt.setWtsm(wtbean.getWtsm());
                                wt.setClyj(wtbean.getClyj());
                                wt.setCljg(wtbean.getCljg());
                                wt.setCreater(userid);
                                wt.setCreatetime(user.getCreatetime());
                                wt.setUpdatetime(now);
                                this.cruiseDao.save(wt);
                                List<CXcSj> sjs = wtbean.getSjs();
                                if (sjs != null && sjs.size() > 0) {
                                    int wtid = wt.getId();
                                    for (CXcSj sj : sjs) {
                                        sj.setWtid(wtid);
                                        sj.setCreater(userid);
                                        sj.setCreatetime(user.getCreatetime());
                                        sj.setUpdatetime(now);
                                        this.cruiseDao.save(sj);
                                    }
                                }
                            }
                        }
                    }
                }

                //附件处理
                if (filelist != null && filelist.size() > 0) {
                    String root = this.getRealPath("upload");// upload存放路径
                    File infile = null;
                    FileUtils.mkdir(root);
                    for (int i = 0; i < filelist.size(); i++) {
                        // io流操作
                        infile = filelist.get(i);
                        String infileMd5 = FileMd5.getMd5ByFile(infile);
                        String tempfilename = filelistFileName.get(i);
                        String filename = FileUtils.writeToFile(infile, infileMd5, root
                                + "\\" + tempfilename);
                        String ext = FileUtils.getFileExtension(tempfilename);
                        CXcFj md5fj = this.cruiseDao.queryFjByMd5(xcid,
                                infileMd5);
                        // 数据库资源插入操作
                        if (md5fj == null) {
                            CXcFj fj = new CXcFj();
                            fj.setXcid(xcid);
                            fj.setFname(filename);
                            fj.setFtype(FileUtils.judgeFileType(ext));
                            fj.setFpath("\\" + filename);
                            fj.setFsize(String.valueOf(infile.length() * 1.0 / 1000));
                            fj.setFmd5(infileMd5);
                            fj.setCreater(curuser.getId());
                            fj.setCreatetime(new Date());
                            fj.setUpdatetime(new Date());
                            this.cruiseDao.save(fj);
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnid;
    }

    public BaseResult addCruise(CXcGk tempgk, String jsondata, List<File> filelist, List<String> filelistFileName, Integer flag) throws ParseErrorException {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        GkBean gk = new GkBean();
        JSONParser<GkBean> parser = new JSONBeanParser<GkBean>(GkBean.class).addAttrParser("qks",
                new JSONArrayParser<QkBean>().addArrayItemParser(new JSONBeanParser<QkBean>(QkBean.class).addAttrParser("wts",
                        new JSONArrayParser<WtBean>().addArrayItemParser(new JSONBeanParser<WtBean>(WtBean.class).addAttrParser("sjs", new JSONArrayParser<CXcSj>().addArrayItemParser(new JSONBeanParser<CXcSj>(CXcSj.class)))))));
        gk = parser.parse(jsondata);
        setUndifinedZh(tempgk);
        this.saveCruise(user, tempgk, gk, filelist, filelistFileName);
        //养护联系单处理
        if (flag == 1) {
            addYhlxd(user, tempgk, gk, filelist, filelistFileName);
        }
        BaseResultOK result = new BaseResultOK();
        return result;
    }

    private void setUndifinedZh(CXcGk gk) {
        if ("undefined".equals(gk.getQdzh())) {
            gk.setQdzh("");
        }
        if ("undefined".equals(gk.getZdzh())) {
            gk.setZdzh("");
        }
    }

    private void setUndifinedYhZh(CXcYhgk gk) {
        if ("undefined".equals(gk.getQdzh())) {
            gk.setQdzh("");
        }
        if ("undefined".equals(gk.getZdzh())) {
            gk.setZdzh("");
        }
    }

    public BaseResult updateCruise(Integer id, CXcGk tempgk, String jsondata, List<Integer> delfileids, List<File> filelist, List<String> filelistFileName, Integer flag) throws ParseErrorException {
        CXtUser curuser = (CXtUser) getSession().getAttribute("user");
        CXtUser user = new CXtUser();
        CXcGk oldgk = (CXcGk) this.cruiseDao.findUnique(new CXcGk(), "id", id);
        user.setId(oldgk.getCreater());
        user.setDepartment(oldgk.getDept());
        user.setCreatetime(oldgk.getCreatetime());
        CXcYhgk oldyhgk = this.cruiseDao.queryYhgkByXcid(id);
        if (delfileids != null && delfileids.size() > 0) {
            for (Integer delfileid : delfileids) {
                if (delfileid != null) {
                    this.cruiseDao.delCXcFjById(delfileid);
                }
            }
        }
        this.delCruiseByIdNoFj(id);
        GkBean gk = new GkBean();
        JSONParser<GkBean> parser = new JSONBeanParser<GkBean>(GkBean.class).addAttrParser("qks",
                new JSONArrayParser<QkBean>().addArrayItemParser(new JSONBeanParser<QkBean>(QkBean.class).addAttrParser("wts",
                        new JSONArrayParser<WtBean>().addArrayItemParser(new JSONBeanParser<WtBean>(WtBean.class).addAttrParser("sjs", new JSONArrayParser<CXcSj>().addArrayItemParser(new JSONBeanParser<CXcSj>(CXcSj.class)))))));
        gk = parser.parse(jsondata);
        setUndifinedZh(tempgk);
        int newxcid = this.saveUpdateCruise(curuser, user, tempgk, gk, filelist, filelistFileName);
        this.cruiseDao.updateXcId(id, newxcid);
        this.cruiseDao.updateFjId(id, newxcid);

        if (flag == 1) {
            if (oldyhgk != null) {
                this.delYhlxdById(oldyhgk.getId());
            }
            int newyhid = addUpdateYhlxd(curuser, user, tempgk, gk, null, null);
            BaseQueryRecords<CXcFj> fjs = this.cruiseDao.queryFjByPid(newxcid);
            if (fjs != null) {
                List<CXcFj> fjlist = fjs.getData();
                Date now = new Date();
                if (fjlist != null && fjlist.size() > 0) {
                    for (CXcFj fj : fjlist) {
                        CXcYhfj yhfj = new CXcYhfj();
                        yhfj.setYhid(newyhid);
                        yhfj.setFname(fj.getFname());
                        yhfj.setFtype(fj.getFtype());
                        yhfj.setFpath(fj.getFpath());
                        yhfj.setFsize(fj.getFsize());
                        yhfj.setFmd5(fj.getFmd5());
                        yhfj.setCreater(curuser.getId());
                        yhfj.setCreatetime(now);
                        yhfj.setUpdatetime(now);
                        this.cruiseDao.addCXcYhfj(yhfj);
                    }
                }
            }
        }
        BaseResultOK result = new BaseResultOK();
        return result;
    }

    public Integer addUpdateYhlxd(CXtUser curuser, CXtUser user, CXcGk gk, GkBean gkbean, List<File> filelist, List<String> filelistFileName) {
        int yhid = 0;
        if (gk != null) {
            int userid = user.getId();
            String dptname = "";
            CXtDpt dpt = this.dptDao.queryDptById(user.getDepartment());
            if (dpt != null) {
                dptname = dpt.getName();
            }
            Date now = new Date();
            CXcYhgk retgk = new CXcYhgk();
            retgk.setHdid(gk.getHdid());
            retgk.setZtqk(gk.getZtqk());
            retgk.setStarttime(gk.getStarttime());
            retgk.setEndtime(gk.getEndtime());
            retgk.setQd(gk.getQd());
            retgk.setZd(gk.getZd());
            retgk.setQdzh(gk.getQdzh());
            retgk.setZdzh(gk.getZdzh());
            retgk.setXhth(gk.getXhth());
            retgk.setCyr(gk.getCyr());
            retgk.setDept(gk.getDept());
            retgk.setBgbm(dptname);
            retgk.setXcid(gk.getId());
            retgk.setCreater(userid);
            retgk.setCreatetime(user.getCreatetime());
            retgk.setUpdatetime(now);
            this.cruiseDao.save(retgk);
            int xcid = retgk.getId();
            yhid = retgk.getId();
            List<QkBean> qks = gkbean.getQks();
            if (qks != null && qks.size() > 0) {
                for (QkBean qkbean : qks) {
                    CXcYhqk qk = new CXcYhqk();
                    qk.setXcid(xcid);
                    qk.setQk(qkbean.getQk());
                    qk.setQksm(qkbean.getQksm());
                    qk.setCreater(userid);
                    qk.setCreatetime(user.getCreatetime());
                    qk.setUpdatetime(now);
                    this.cruiseDao.save(qk);
                    List<WtBean> wts = qkbean.getWts();
                    if (wts != null && wts.size() > 0) {
                        int qkid = qk.getId();
                        for (WtBean wtbean : wts) {
                            CXcYhwt wt = new CXcYhwt();
                            wt.setQkid(qkid);
                            wt.setWt(wtbean.getWt());
                            wt.setWtsm(wtbean.getWtsm());
                            wt.setClyj(wtbean.getClyj());
                            wt.setCljg(wtbean.getCljg());
                            wt.setCreater(userid);
                            wt.setCreatetime(user.getCreatetime());
                            wt.setUpdatetime(now);
                            this.cruiseDao.save(wt);
                            List<CXcSj> sjs = wtbean.getSjs();
                            if (sjs != null && sjs.size() > 0) {
                                int wtid = wt.getId();
                                for (CXcSj tempsj : sjs) {
                                    CXcYhsj sj = new CXcYhsj();
                                    sj.setWtid(wtid);
                                    sj.setJtwz(tempsj.getJtwz());
                                    sj.setAb(tempsj.getAb());
                                    sj.setMs(tempsj.getMs());
                                    sj.setCreater(userid);
                                    sj.setCreatetime(user.getCreatetime());
                                    sj.setUpdatetime(now);
                                    this.cruiseDao.save(sj);
                                }
                            }
                        }
                    }
                }
            }
            //附件处理
            if (filelist != null && filelist.size() > 0) {
                String root = this.getRealPath("upload");// upload存放路径
                File infile = null;
                FileUtils.mkdir(root);
                for (int i = 0; i < filelist.size(); i++) {
                    // io流操作
                    infile = filelist.get(i);
                    String infileMd5 = FileMd5.getMd5ByFile(infile);
                    String tempfilename = filelistFileName.get(i);
                    String filename = FileUtils.writeToFile(infile, infileMd5, root
                            + "\\" + tempfilename);
                    String ext = FileUtils.getFileExtension(tempfilename);
                    CXcYhfj md5fj = this.cruiseDao.queryYhfjByMd5(yhid,
                            infileMd5);
                    // 数据库资源插入操作
                    if (md5fj == null) {
                        CXcYhfj fj = new CXcYhfj();
                        fj.setYhid(yhid);
                        fj.setFname(filename);
                        fj.setFtype(FileUtils.judgeFileType(ext));
                        fj.setFpath("\\" + filename);
                        fj.setFsize(String.valueOf(infile.length() * 1.0 / 1000));
                        fj.setFmd5(infileMd5);
                        fj.setCreater(curuser.getId());
                        fj.setCreatetime(new Date());
                        fj.setUpdatetime(new Date());
                        this.cruiseDao.save(fj);
                    }
                }
            }

        }
        return yhid;
    }

    public BaseResult downloadCxcfj(int id) {
        CXcFj fj = this.cruiseDao.queryFjByid(id);
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

    public BaseResult downloadCxcyhfj(int id) {
        CXcYhfj fj = this.cruiseDao.queryYhfjByid(id);
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

    public Integer addYhlxd(CXtUser user, CXcGk gk, GkBean gkbean, List<File> filelist, List<String> filelistFileName) {
        int yhid = 0;
        if (gk != null) {
            int userid = user.getId();
            String dptname = "";
            CXtDpt dpt = this.dptDao.queryDptById(user.getDepartment());
            if (dpt != null) {
                dptname = dpt.getName();
            }
            Date now = new Date();
            CXcYhgk retgk = new CXcYhgk();
            retgk.setHdid(gk.getHdid());
            retgk.setZtqk(gk.getZtqk());
            retgk.setStarttime(gk.getStarttime());
            retgk.setEndtime(gk.getEndtime());
            retgk.setQd(gk.getQd());
            retgk.setZd(gk.getZd());
            retgk.setQdzh(gk.getQdzh());
            retgk.setZdzh(gk.getZdzh());
            retgk.setXhth(gk.getXhth());
            retgk.setCyr(gk.getCyr());
            retgk.setDept(gk.getDept());
            retgk.setBgbm(dptname);
            retgk.setXcid(gk.getId());
            retgk.setCreater(userid);
            retgk.setCreatetime(now);
            retgk.setUpdatetime(now);
            this.cruiseDao.save(retgk);
            int xcid = retgk.getId();
            yhid = retgk.getId();
            List<QkBean> qks = gkbean.getQks();
            if (qks != null && qks.size() > 0) {
                for (QkBean qkbean : qks) {
                    CXcYhqk qk = new CXcYhqk();
                    qk.setXcid(xcid);
                    qk.setQk(qkbean.getQk());
                    qk.setQksm(qkbean.getQksm());
                    qk.setCreater(userid);
                    qk.setCreatetime(now);
                    qk.setUpdatetime(now);
                    this.cruiseDao.save(qk);
                    List<WtBean> wts = qkbean.getWts();
                    if (wts != null && wts.size() > 0) {
                        int qkid = qk.getId();
                        for (WtBean wtbean : wts) {
                            CXcYhwt wt = new CXcYhwt();
                            wt.setQkid(qkid);
                            wt.setWt(wtbean.getWt());
                            wt.setWtsm(wtbean.getWtsm());
                            wt.setClyj(wtbean.getClyj());
                            wt.setCljg(wtbean.getCljg());
                            wt.setCreater(userid);
                            wt.setCreatetime(now);
                            wt.setUpdatetime(now);
                            this.cruiseDao.save(wt);
                            List<CXcSj> sjs = wtbean.getSjs();
                            if (sjs != null && sjs.size() > 0) {
                                int wtid = wt.getId();
                                for (CXcSj tempsj : sjs) {
                                    CXcYhsj sj = new CXcYhsj();
                                    sj.setWtid(wtid);
                                    sj.setJtwz(tempsj.getJtwz());
                                    sj.setAb(tempsj.getAb());
                                    sj.setMs(tempsj.getMs());
                                    sj.setCreater(userid);
                                    sj.setCreatetime(now);
                                    sj.setUpdatetime(now);
                                    this.cruiseDao.save(sj);
                                }
                            }
                        }
                    }
                }
            }
            //附件处理
            if (filelist != null && filelist.size() > 0) {
                String root = this.getRealPath("upload");// upload存放路径
                File infile = null;
                FileUtils.mkdir(root);
                for (int i = 0; i < filelist.size(); i++) {
                    // io流操作
                    infile = filelist.get(i);
                    String infileMd5 = FileMd5.getMd5ByFile(infile);
                    String tempfilename = filelistFileName.get(i);
                    String filename = FileUtils.writeToFile(infile, infileMd5, root
                            + "\\" + tempfilename);
                    String ext = FileUtils.getFileExtension(tempfilename);
                    CXcYhfj md5fj = this.cruiseDao.queryYhfjByMd5(yhid,
                            infileMd5);
                    // 数据库资源插入操作
                    if (md5fj == null) {
                        CXcYhfj fj = new CXcYhfj();
                        fj.setYhid(yhid);
                        fj.setFname(filename);
                        fj.setFtype(FileUtils.judgeFileType(ext));
                        fj.setFpath("\\" + filename);
                        fj.setFsize(String.valueOf(infile.length() * 1.0 / 1000));
                        fj.setFmd5(infileMd5);
                        fj.setCreater(user.getId());
                        fj.setCreatetime(new Date());
                        fj.setUpdatetime(new Date());
                        this.cruiseDao.save(fj);
                    }
                }
            }

        }
        return yhid;
    }

    public Integer addYhlxd(CXtUser user, CXcYhgk gk, GkBean gkbean, List<File> filelist, List<String> filelistFileName) {
        int yhid = 0;
        if (gk != null) {
            int userid = user.getId();
            Date now = new Date();
            gk.setUpdatetime(now);
            this.cruiseDao.update(gk);
            yhid = gk.getId();
            List<QkBean> qks = gkbean.getQks();
            if (qks != null && qks.size() > 0) {
                for (QkBean qkbean : qks) {
                    CXcYhqk qk = new CXcYhqk();
                    qk.setXcid(yhid);
                    qk.setQk(qkbean.getQk());
                    qk.setQksm(qkbean.getQksm());
                    qk.setCreater(userid);
                    qk.setCreatetime(now);
                    qk.setUpdatetime(now);
                    this.cruiseDao.save(qk);
                    List<WtBean> wts = qkbean.getWts();
                    if (wts != null && wts.size() > 0) {
                        int qkid = qk.getId();
                        for (WtBean wtbean : wts) {
                            CXcYhwt wt = new CXcYhwt();
                            wt.setQkid(qkid);
                            wt.setWt(wtbean.getWt());
                            wt.setWtsm(wtbean.getWtsm());
                            wt.setClyj(wtbean.getClyj());
                            wt.setCljg(wtbean.getCljg());
                            wt.setCreater(userid);
                            wt.setCreatetime(now);
                            wt.setUpdatetime(now);
                            this.cruiseDao.save(wt);
                            List<CXcSj> sjs = wtbean.getSjs();
                            if (sjs != null && sjs.size() > 0) {
                                int wtid = wt.getId();
                                for (CXcSj tempsj : sjs) {
                                    CXcYhsj sj = new CXcYhsj();
                                    sj.setWtid(wtid);
                                    sj.setJtwz(tempsj.getJtwz());
                                    sj.setAb(tempsj.getAb());
                                    sj.setMs(tempsj.getMs());
                                    sj.setCreater(userid);
                                    sj.setCreatetime(now);
                                    sj.setUpdatetime(now);
                                    this.cruiseDao.save(sj);
                                }
                            }
                        }
                    }
                }
            }
            //附件处理
            if (filelist != null && filelist.size() > 0) {
                String root = this.getRealPath("upload");// upload存放路径
                File infile = null;
                FileUtils.mkdir(root);
                for (int i = 0; i < filelist.size(); i++) {
                    // io流操作
                    infile = filelist.get(i);
                    String infileMd5 = FileMd5.getMd5ByFile(infile);
                    String tempfilename = filelistFileName.get(i);
                    String filename = FileUtils.writeToFile(infile, infileMd5, root
                            + "\\" + tempfilename);
                    String ext = FileUtils.getFileExtension(tempfilename);
                    CXcYhfj md5fj = this.cruiseDao.queryYhfjByMd5(yhid,
                            infileMd5);
                    // 数据库资源插入操作
                    if (md5fj == null) {
                        CXcYhfj fj = new CXcYhfj();
                        fj.setYhid(yhid);
                        fj.setFname(filename);
                        fj.setFtype(FileUtils.judgeFileType(ext));
                        fj.setFpath("\\" + filename);
                        fj.setFsize(String.valueOf(infile.length() * 1.0 / 1000));
                        fj.setFmd5(infileMd5);
                        fj.setCreater(user.getId());
                        fj.setCreatetime(new Date());
                        fj.setUpdatetime(new Date());
                        this.cruiseDao.save(fj);
                    }
                }
            }

        }
        return yhid;
    }

    public BaseResult queryYhlxd(List<Integer> ids, Date starttime, Date endtime, String content, int page, int rows, String sEcho) {
        String strids = "";
        String strstarttime = "";
        String strendtime = "";
        CXtDpt udept = (CXtDpt) getSession().getAttribute("dpt");
        int userxzqh = udept.getXzqh();
        if (ids != null && ids.size() > 0) {
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < ids.size(); i++) {
                sb.append(String.valueOf(ids.get(i)) + ",");
            }
            if (!"".equals(sb.toString()) && sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                strids = sb.toString();
            }

        }
        if (starttime != null) {
            strstarttime = DateTimeUtil.getTimeFmt(starttime, "yyyy-MM-dd") + " 00:00:00";
        }
        if (endtime != null) {
            strendtime = DateTimeUtil.getTimeFmt(endtime, "yyyy-MM-dd") + " 23:59:59";
        }
        BaseQueryRecords records = this.cruiseDao.queryYhlxd(strids, strstarttime, strendtime, content, userxzqh, page, rows);
        records.setData(records.getData());
        BaseResultOK result = new BaseResultOK(records);
        result.addToMap("sEcho", sEcho);
        return result;
    }

    private void delYhlxdById(Integer id) {
        CXcYhgk gk = this.cruiseDao.queryYhgkById(id);
        if (gk != null) {
            List<CXcYhqk> qks = this.cruiseDao.queryYhqksById(id);
            if (qks != null && qks.size() > 0) {
                StringBuffer qkids = new StringBuffer();
                for (CXcYhqk qk : qks) {
                    qkids.append(qk.getId() + ",");
                }
                if (qkids.length() > 0) {
                    qkids.deleteCharAt(qkids.length() - 1);
                }
                List<CXcYhwt> qkwts = this.cruiseDao.queryYhwtsByIds(qkids.toString());
                if (qkwts != null && qkwts.size() > 0) {
                    StringBuffer wtids = new StringBuffer();
                    for (CXcYhwt qkwt : qkwts) {
                        wtids.append(qkwt.getId() + ",");
                    }
                    if (wtids.length() > 0) {
                        wtids.deleteCharAt(wtids.length() - 1);
                        this.cruiseDao.delYhsj(wtids.toString());
                        this.cruiseDao.delYhwt(wtids.toString());
                    }
                }
                this.cruiseDao.delYhqk(qkids.toString());
            }
            this.cruiseDao.delFjByYhid(id);
            this.cruiseDao.delObj(gk);
        }
    }

    public BaseResult delYhlxd(List<Integer> ids) {
        if (ids != null && ids.size() > 0) {
            for (Integer id : ids) {
                this.delYhlxdById(id);
            }
        }
        BaseResultOK result = new BaseResultOK();
        return result;
    }

    public BaseResult queryYhlxdById(int id) {
        String dept = "";
        List<CXcYhfj> lists = new ArrayList<CXcYhfj>();
        CXcYhgk fjgk = this.cruiseDao.queryYhgkById(id);
        if (fjgk != null) {
            lists = this.cruiseDao.queryYhfjByPid(id).getData();
        }
        List<YhlxdBean> records = (List<YhlxdBean>) this.cruiseDao.queryYhlxdById(id);
        if (records != null && records.size() > 0) {
            YhlxdBean bean = records.get(0);
            CXtDpt dpt = this.dptDao.queryDptById(bean.getDept());
            if (dpt != null) {
                dept = dpt.getName();
            }
        }
        BaseResultOK result = new BaseResultOK(records);
        result.addToMap("dept", dept);
        result.addToMap("fjlist", lists);
        return result;
    }

    public BaseResult exportYhlxd(int id) {
        String dept = "";
        List<YhlxdBean> records = (List<YhlxdBean>) this.cruiseDao.queryYhlxdById(id);
        if (records != null && records.size() > 0) {
            YhlxdBean bean = records.get(0);
            CXtDpt dpt = this.dptDao.queryDptById(bean.getDept());
            if (dpt != null) {
                dept = dpt.getName();
            }
        }
        List<CXcYhfj> fjs = this.cruiseDao.queryYhfjByPid(id).getData();
        BaseResultOK result = new BaseResultOK();
        String outfilepath = "";
        File inputfile = null;
        File outputfile = null;
        try {
            String path = this.getRealPath("/");
            inputfile = new File(path + "/template/航道养护联系单.xlsx");
            String filename = inputfile.getName();
            StringBuffer sbf = new StringBuffer(filename);
            sbf.insert(sbf.indexOf("."),
                    DateTimeUtil.getTimeFmt(new Date(), "yyyyMMddHHmmss"));
            outfilepath = path + "baobiao\\" + sbf.toString();
            FileUtils.writeToFile(inputfile, outfilepath);
            outputfile = new File(outfilepath);
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                    outputfile));
            CellStyle cellStyle = workbook.createCellStyle(); // cell下划线样式
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

            XSSFFont font = workbook.createFont();
            font.setUnderline(XSSFFontFormatting.U_SINGLE);
            CellStyle fontstyle = workbook.createCellStyle(); // 样式
            fontstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            fontstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            fontstyle.setFont(font);

            XSSFSheet sheet = workbook.getSheet("航道养护联系单");
            int len = records.size();
            // 航道名称 合计cell
            int startrow = 9;
            int endrow = startrow + 3 * len + 24;
            createTableCell(sheet, startrow, endrow, 0, 6);

            if (records != null && len > 0) {
                //表头
                YhlxdBean p = records.get(0);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                String strtime = "____年__月__日";
                String strhdmc = "______";
                List<YhlxdBean> yjlist = new ArrayList<>();
                List<YhlxdBean> hamtlist = new ArrayList<>();
                List<YhlxdBean> hblist = new ArrayList<>();
                List<YhlxdBean> ahlist = new ArrayList<>();
                List<YhlxdBean> lhlist = new ArrayList<>();
                List<YhlxdBean> otherlist = new ArrayList<>();
                if (p.getStarttime() != null) {
                    strtime = sdf.format(p.getStarttime());
                }
                CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoByHdid(p.getHdid());
                if (hdao != null) {
                    strhdmc = hdao.getHdmc();
                }
                sheet.getRow(3).getCell(0).setCellValue("湖州航道养护中心:");
                sheet.getRow(3).getCell(0).setCellStyle(fontstyle);
                sheet.getRow(5).getCell(1).setCellValue("我部门与" + strtime + ",在" + strhdmc + "航道巡查时,发现下列航道缺陷,请安排养护");
                sheet.getRow(7).getCell(5).setCellValue(p.getBgbm());
                sheet.getRow(7).getCell(5).setCellStyle(fontstyle);

                //主体 情况
                //情况分类list
                for (int i = 0; i < len; i++) {
                    YhlxdBean e = records.get(i);
                    String jtwz = e.getJtwz();
                    String ms = e.getMs();
                    if (!"".equals(jtwz) && !"".equals(ms)) {
                        switch (e.getQk()) {
                            case 1:
                                hamtlist.add(e);
                                break;
                            case 2:
                                hamtlist.add(e);
                                break;
                            case 3:
                                hblist.add(e);
                                break;
                            case 4:
                                lhlist.add(e);
                                break;
                            case 5:
                                ahlist.add(e);
                                break;
                            case 7:
                                otherlist.add(e);
                                break;
                        }
                    }
                }
                int linenum = 9;
                setYhlxdQk(yjlist, 1, linenum, sheet, cellStyle);
                linenum = setLineNum(yjlist.size(), linenum);
                setYhlxdQk(hamtlist, 2, linenum, sheet, cellStyle);
                linenum = setLineNum(hamtlist.size(), linenum);
                setYhlxdQk(hblist, 3, linenum, sheet, cellStyle);
                linenum = setLineNum(hblist.size(), linenum);
                setYhlxdQk(lhlist, 4, linenum, sheet, cellStyle);
                linenum = setLineNum(lhlist.size(), linenum);
                setYhlxdQk(ahlist, 5, linenum, sheet, cellStyle);
                linenum = setLineNum(ahlist.size(), linenum);
                setYhlxdQk(otherlist, 6, linenum, sheet, cellStyle);
                linenum = setLineNum(otherlist.size(), linenum);
                if (fjs != null && fjs.size() > 0) {
                    MergeCell(sheet, linenum, linenum, 0, 5);
                    setCellValue(sheet, linenum, 0, "图片信息");
                    linenum++;
                    for (int i = 0; i < fjs.size(); i++) {
                        CXcYhfj tempfj = fjs.get(i);
                        String jpgaddr = this.getRealPath("upload") + tempfj.getFpath();
                        InputStream imgstream = new FileInputStream(jpgaddr);
                        byte[] bytes = IOUtils.toByteArray(imgstream);
                        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
                        CreationHelper helper = workbook.getCreationHelper();
                        Drawing drawing = sheet.createDrawingPatriarch();
                        ClientAnchor anchor = helper.createClientAnchor();
                        MergeCell(sheet, linenum, linenum + 8, 0, 3);
                        MergeCell(sheet, linenum, linenum + 8, 4, 5);
                        if (i % 2 == 0) {
                            anchor.setRow1(linenum);
                            anchor.setCol1(0);
                        } else {
                            anchor.setRow1(linenum);
                            anchor.setCol1(4);
                            linenum = linenum + 9;
                        }
                        Picture pict = drawing.createPicture(anchor, pictureIdx);
                        pict.resize();

                    }
                }
                deleteRow(sheet, linenum);
            }
            FileOutputStream outfile = new FileOutputStream(outputfile);
            workbook.write(outfile);
            outfile.close();
            logService.addLog(ModuleName.HDXC, OpName.EXPORT, "航道养护联系单");
            result.addToMap("filename", outputfile.getName());
            result.addToMap("filepath", outfilepath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    private void setCellValue(XSSFSheet sheet, int rownum, int colnum, String strval) {
        sheet.getRow(rownum).getCell(colnum).setCellValue(strval);
    }

    private void deleteRow(XSSFSheet sheet, int rowIndex) {
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < lastRowNum)
            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);//将行号为rowIndex+1一直到行号为lastRowNum的单元格全部上移一行，以便删除rowIndex行
        if (rowIndex == lastRowNum) {
            XSSFRow removingRow = sheet.getRow(rowIndex);
            if (removingRow != null)
                sheet.removeRow(removingRow);
        }
    }

    private void createTableCell(XSSFSheet sheet, int startrow, int endrow, int startcol, int endcol) {
        for (int j = startrow; j < endrow; j++) {
            sheet.createRow(j);
            XSSFRow row = sheet.getRow(j);
            for (int k = startcol; k < endcol; k++) {
                row.createCell(k);
            }
        }
    }

    //每个情况的行号
    private int setLineNum(int size, int linenum) {
        if (size > 1) {
            return linenum + size * 3 + 1;
        } else {
            return linenum + 4;
        }
    }

    //单元格赋值(情况分类)
    private void setYhlxdQk(List<YhlxdBean> list, int qktype, int linenum, XSSFSheet sheet, CellStyle cellStyle) {
        int len = list.size();
        switch (qktype) {
            case 1:
                sheet.getRow(linenum).getCell(0).setCellValue("1.航道淤积缺陷");
                break;
            case 2:
                sheet.getRow(linenum).getCell(0).setCellValue("2.护岸、管理码头缺陷");
                break;
            case 3:
                sheet.getRow(linenum).getCell(0).setCellValue("3.航标缺陷");
                break;
            case 4:
                sheet.getRow(linenum).getCell(0).setCellValue("4.绿化缺陷");
                break;
            case 5:
                sheet.getRow(linenum).getCell(0).setCellValue("5.碍航物缺陷");
                break;
            case 6:
                sheet.getRow(linenum).getCell(0).setCellValue("6.其他情况");
                break;
        }
        if (list.size() == 0) {
            sheet.getRow(linenum + 1).getCell(0).setCellValue("地点:");
            sheet.getRow(linenum + 2).getCell(0).setCellValue("概况:");
            MergeCell(sheet, linenum + 1, linenum + 1, 1, 5);
            MergeCell(sheet, linenum + 2, linenum + 2, 1, 5);
            setCssLine(sheet, linenum + 1, cellStyle, 1, 5);
            setCssLine(sheet, linenum + 2, cellStyle, 1, 5);
        } else {
            for (int i = 0; i < len; i++) {
                YhlxdBean e = list.get(i);
                String jtwz = e.getJtwz();
                String wtsm = e.getWtsm();
                String ms = e.getMs();
                String ab = e.getAb() == 1 ? "左岸" : "右岸";
                sheet.getRow(linenum + 1).getCell(0).setCellValue("地点:");
                MergeCell(sheet, linenum + 1, linenum + 1, 1, 5);
                setCssLine(sheet, linenum + 1, cellStyle, 1, 5);
                sheet.getRow(linenum + 1).getCell(1).setCellValue(jtwz + " " + ab);
                sheet.getRow(linenum + 2).getCell(0).setCellValue("概况:");
                MergeCell(sheet, linenum + 2, linenum + 2, 1, 5);
                setCssLine(sheet, linenum + 2, cellStyle, 1, 5);
                sheet.getRow(linenum + 2).getCell(1).setCellValue(wtsm + ":" + ms);
                linenum = linenum + 3;
            }
        }
    }

    private void setCssLine(XSSFSheet sheet, int i, CellStyle cellStyle, int startnum, int endnum) {
        for (int s = startnum; s <= endnum; s++) {
            sheet.getRow(i).getCell(s).setCellStyle(cellStyle);
        }
    }

    private void MergeCell(XSSFSheet sheet, int leftrow, int rightrow, int leftcol, int rightcol) {
        sheet.addMergedRegion(new CellRangeAddress(leftrow, rightrow, leftcol,
                rightcol));
    }

    public BaseResult updateYhlxd(int id, CXcYhgk tempgk, String jsondata, List<Integer> delfileids, List<File> filelist, List<String> filelistFileName) throws ParseErrorException {
        CXtUser curuser = (CXtUser) getSession().getAttribute("user");
        //删除附件
        if (delfileids != null && delfileids.size() > 0) {
            for (Integer delfileid : delfileids) {
                if (delfileid != null) {
                    this.cruiseDao.delCXcYhfjById(delfileid);
                }
            }
        }
        CXtUser user = new CXtUser();
        //更新概况
        CXcYhgk yhgk = this.cruiseDao.queryYhgkById(id);
        yhgk.setHdid(tempgk.getHdid());
        yhgk.setZtqk(tempgk.getZtqk());
        yhgk.setStarttime(tempgk.getStarttime());
        yhgk.setEndtime(tempgk.getEndtime());
        yhgk.setQd(tempgk.getQd());
        yhgk.setZd(tempgk.getZd());
        yhgk.setQdzh(tempgk.getQdzh());
        yhgk.setZdzh(tempgk.getZdzh());
        yhgk.setXhth(tempgk.getXhth());
        yhgk.setCyr(tempgk.getCyr());
        yhgk.setDept(tempgk.getDept());
        //删除养护联系单(除了概况和附件)
        this.delYhlxdByIdNoFj(id);
        GkBean gk = new GkBean();
        JSONParser<GkBean> parser = new JSONBeanParser<GkBean>(GkBean.class).addAttrParser("qks",
                new JSONArrayParser<QkBean>().addArrayItemParser(new JSONBeanParser<QkBean>(QkBean.class).addAttrParser("wts",
                        new JSONArrayParser<WtBean>().addArrayItemParser(new JSONBeanParser<WtBean>(WtBean.class).addAttrParser("sjs", new JSONArrayParser<CXcSj>().addArrayItemParser(new JSONBeanParser<CXcSj>(CXcSj.class)))))));
        gk = parser.parse(jsondata);
        user.setId(yhgk.getCreater());
        user.setCreatetime(yhgk.getCreatetime());
        user.setDepartment(tempgk.getDept());
        //更新概况 添加养护联系单
        setUndifinedYhZh(yhgk);
        this.addUpdateLxd(curuser, user, yhgk, gk, filelist, filelistFileName);
        BaseResultOK result = new BaseResultOK();
        return result;
    }

    private void delYhlxdByIdNoFj(int id) {
        List<CXcYhqk> qks = this.cruiseDao.queryYhqksById(id);
        if (qks != null && qks.size() > 0) {
            StringBuffer qkids = new StringBuffer();
            for (CXcYhqk qk : qks) {
                qkids.append(qk.getId() + ",");
            }
            if (qkids.length() > 0) {
                qkids.deleteCharAt(qkids.length() - 1);
            }
            List<CXcYhwt> qkwts = this.cruiseDao.queryYhwtsByIds(qkids.toString());
            if (qkwts != null && qkwts.size() > 0) {
                StringBuffer wtids = new StringBuffer();
                for (CXcYhwt qkwt : qkwts) {
                    wtids.append(qkwt.getId() + ",");
                }
                if (wtids.length() > 0) {
                    wtids.deleteCharAt(wtids.length() - 1);
                    this.cruiseDao.delYhsj(wtids.toString());
                    this.cruiseDao.delYhwt(wtids.toString());
                }
            }
            this.cruiseDao.delYhqk(qkids.toString());
        }
    }

    public Integer addUpdateLxd(CXtUser curuser, CXtUser user, CXcYhgk gk, GkBean gkbean, List<File> filelist, List<String> filelistFileName) {
        int yhid = 0;
        if (gk != null) {
            int userid = user.getId();
            Date now = new Date();
            gk.setUpdatetime(now);
            this.cruiseDao.update(gk);
            yhid = gk.getId();
            List<QkBean> qks = gkbean.getQks();
            if (qks != null && qks.size() > 0) {
                for (QkBean qkbean : qks) {
                    CXcYhqk qk = new CXcYhqk();
                    qk.setXcid(yhid);
                    qk.setQk(qkbean.getQk());
                    qk.setQksm(qkbean.getQksm());
                    qk.setCreater(userid);
                    qk.setCreatetime(user.getCreatetime());
                    qk.setUpdatetime(now);
                    this.cruiseDao.save(qk);
                    List<WtBean> wts = qkbean.getWts();
                    if (wts != null && wts.size() > 0) {
                        int qkid = qk.getId();
                        for (WtBean wtbean : wts) {
                            CXcYhwt wt = new CXcYhwt();
                            wt.setQkid(qkid);
                            wt.setWt(wtbean.getWt());
                            wt.setWtsm(wtbean.getWtsm());
                            wt.setClyj(wtbean.getClyj());
                            wt.setCljg(wtbean.getCljg());
                            wt.setCreater(userid);
                            wt.setCreatetime(user.getCreatetime());
                            wt.setUpdatetime(now);
                            this.cruiseDao.save(wt);
                            List<CXcSj> sjs = wtbean.getSjs();
                            if (sjs != null && sjs.size() > 0) {
                                int wtid = wt.getId();
                                for (CXcSj tempsj : sjs) {
                                    CXcYhsj sj = new CXcYhsj();
                                    sj.setWtid(wtid);
                                    sj.setJtwz(tempsj.getJtwz());
                                    sj.setAb(tempsj.getAb());
                                    sj.setMs(tempsj.getMs());
                                    sj.setCreater(userid);
                                    sj.setCreatetime(user.getCreatetime());
                                    sj.setUpdatetime(now);
                                    this.cruiseDao.save(sj);
                                }
                            }
                        }
                    }
                }
            }
            //附件处理
            if (filelist != null && filelist.size() > 0) {
                String root = this.getRealPath("upload");// upload存放路径
                File infile = null;
                FileUtils.mkdir(root);
                for (int i = 0; i < filelist.size(); i++) {
                    // io流操作
                    infile = filelist.get(i);
                    String infileMd5 = FileMd5.getMd5ByFile(infile);
                    String tempfilename = filelistFileName.get(i);
                    String filename = FileUtils.writeToFile(infile, infileMd5, root
                            + "\\" + tempfilename);
                    String ext = FileUtils.getFileExtension(tempfilename);
                    CXcYhfj md5fj = this.cruiseDao.queryYhfjByMd5(yhid,
                            infileMd5);
                    // 数据库资源插入操作
                    if (md5fj == null) {
                        CXcYhfj fj = new CXcYhfj();
                        fj.setYhid(yhid);
                        fj.setFname(filename);
                        fj.setFtype(FileUtils.judgeFileType(ext));
                        fj.setFpath("\\" + filename);
                        fj.setFsize(String.valueOf(infile.length() * 1.0 / 1000));
                        fj.setFmd5(infileMd5);
                        fj.setCreater(curuser.getId());
                        fj.setCreatetime(new Date());
                        fj.setUpdatetime(new Date());
                        this.cruiseDao.save(fj);
                    }
                }
            }

        }
        return yhid;
    }
}
