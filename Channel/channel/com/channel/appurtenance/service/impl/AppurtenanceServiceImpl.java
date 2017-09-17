package com.channel.appurtenance.service.impl;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.channel.hangdao.dao.HangDaoDao;
import com.channel.hangduan.dao.HangDuanDao;
import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import com.channel.model.hd.*;
import com.channel.model.user.CXtDpt;
import com.channel.user.dao.impl.DptDaoImpl;
import com.common.dao.impl.HQL;
import com.common.utils.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.channel.appurtenance.dao.AppurtenanceDao;
import com.channel.appurtenance.dao.impl.AppurtenanceResult;
import com.channel.appurtenance.dao.impl.AppurtenanceTypeDaoImpl;
import com.channel.appurtenance.service.AppurtenanceService;
import com.channel.appurtenance.service.AppurtenanceTypeService;
import com.channel.bean.Constants;
import com.channel.bean.PropDescFields;
import com.channel.dic.dao.DicDao;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.dic.service.XzqhdmService;
import com.channel.model.user.CXtUser;
import com.channel.model.user.CZdXzqhdm;
import com.channel.user.dao.UserDao;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.tree.model.Tree;

@Service("appurtenanceservice")
public class AppurtenanceServiceImpl extends BaseService implements
        AppurtenanceService {
    @Resource(name = "hangduandao")
    private HangDuanDao hangDuanDao;
    @Resource(name = "hangdaodao")
    private HangDaoDao hangDaoDao;
    @Resource(name = "xzqhdmservice")
    private XzqhdmService xzqhdmService;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmdao;
    @Resource(name = "appurtenancedao")
    private AppurtenanceDao appurtenanceDao;
    @Resource(name = "appurtenancetypedao")
    private AppurtenanceTypeDaoImpl appurtenancetypedao;
    @Resource(name = "appurtenancetypeservice")
    private AppurtenanceTypeService appurtenanceTypeService;
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "dicdao")
    private DicDao dicDao;
    @Resource(name = "dptdao")
    private DptDaoImpl dptDao;
    @Resource
    private LogService logService;

    @Override
    public BaseResult batchImport(int fswlx, List<File> filelist, List<String> filelistFileName) throws Exception {
        List<Integer> list = new ArrayList<>();
        if (filelist != null && filelist.size() > 0) {
            File infile = null;
            infile = filelist.get(0);
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                    infile));
            XSSFSheet sheet = workbook.getSheet("Sheet1");
            switch (fswlx) {
                case Constants.APP_HDAO:
                    list = importHdao(sheet);
                    break;
                case Constants.APP_HDUAN:
                    list = importHduan(sheet);
                    break;
                case Constants.APP_NAVIGATIONMARK:
                    list = importNavimark(sheet);
                    break;
                case Constants.APP_BRIDGE:
                    list = importBridge(sheet);
                    break;
                case Constants.APP_CABLE:
                    list = importCable(sheet);
                    break;
                case Constants.APP_KYDOCK:
                    list = importKydock(sheet);
                    break;
                case Constants.APP_HYDOCK:
                    list = importHydock(sheet);
                    break;
                case Constants.APP_GWDOCK:
                    list = importGwdock(sheet);
                    break;
            }

        }
        BaseResultOK baseResultOK = new BaseResultOK(list);
        return baseResultOK;
    }

    //date
    public Date jgDateNull(XSSFCell c) {
        return c != null ? c.getDateCellValue() : new Date(0L);
    }

    //integer
    public int jgIntegerNull(XSSFCell c) {
        int rt = 0;
        if (c != null) {
            if (c.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                rt = Integer.parseInt(String.valueOf(c.getNumericCellValue()));
            }
        }
        return rt;
    }

    //double
    public double jgDoubleNull(XSSFCell c) {
        double rt = 0.0;
        if (c != null) {
            if (c.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                rt = c.getNumericCellValue();
            }
        }
        return rt;
    }

    //字符串 空
    public String jgStringNull(XSSFCell c) {
        String s = "";
        if (c != null) {
            switch (c.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    s = String.valueOf(c.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    s = c.getStringCellValue().trim();
                    break;
            }
        }
        return s;
    }

    //字符串
    public String jgStringVal(XSSFCell c) {
        String s = null;
        if (c != null) {
            switch (c.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    s = new BigDecimal(c.getNumericCellValue()).toString();
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    s = c.getStringCellValue().trim();
                    break;
            }
        }
        return s;
    }

    //是否 显示01

    public int getBooleanInt(XSSFCell c) {
        int flag = 0;
        String s = "";
        if (c != null) {
            s = c.getStringCellValue().trim();
            if (s != null && !"".equals(s)) {
                if ("是".equals(s)) {
                    flag = 1;
                }
            }
        }
        return flag;
    }

    //字典
    public int getDicIdx(String name, XSSFCell c) {
        int flag = 0;
        if (c != null && !"".equals(name)) {
            flag = this.dicDao.queryIdByNameDesc(name, c.getStringCellValue().trim());
        }
        return flag;
    }

    //部门
    public CXtDpt getDeptIdXzqh(XSSFCell c) {
        CXtDpt dept = null;
        String s = "";
        if (c != null) {
            s = c.getStringCellValue().trim();
            if (!"".equals(s)) {
                dept = this.dptDao.queryDptByName(s);
            }
        }
        return dept;
    }

    //行政区划
    public int getXzqhByName(XSSFCell c) {
        int xzqh = 0;
        String s = "";
        if (c != null) {
            s = c.getStringCellValue().trim();
            if (!"".equals(s)) {
                CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmByName(s);
                xzqh = xzqhdm.getId();
            }
        }
        return xzqh;
    }

    private List<Integer> importHdao(XSSFSheet sheet) {
        int startindex = 3;
        int lastnum = sheet.getLastRowNum();
        int recnum = lastnum - startindex + 1;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < recnum; i++) {
            XSSFRow row = sheet.getRow(startindex + i);
            try {
                CHdHdaojcxx hdao = new CHdHdaojcxx();
                hdao.setHdbh(jgStringVal(row.getCell(0)));
                hdao.setHdmc(row.getCell(1).getStringCellValue().trim());
                hdao.setSfqshd(getBooleanInt(row.getCell(2)));
                hdao.setSssjbh(getDicIdx("sssjbh", row.getCell(3)));
                hdao.setSfjjx(getBooleanInt(row.getCell(4)));
                hdao.setSfgg(getBooleanInt(row.getCell(5)));
                hdao.setBz(jgStringNull(row.getCell(6)));
                hdao.setCreatetime(new Date());
                hdao.setUpdatetime(new Date());
                this.hangDaoDao.addHangDao(hdao);
            } catch (Exception e) {
                list.add(startindex + i + 1);
                continue;
            }
        }
        return list;
    }

    private List<Integer> importHduan(XSSFSheet sheet) {
        int startindex = 7;
        int lastnum = sheet.getLastRowNum();
        int recnum = lastnum - startindex + 1;
        List<Integer> list = new ArrayList<>();
        int hdbhid = 1;
        int tempid = 0;
        for (int i = 0; i < recnum; i++) {
            XSSFRow row = sheet.getRow(startindex + i);
            try {
                CHdHduanjcxx hduan = new CHdHduanjcxx();
                String sshdbh = jgStringVal(row.getCell(1));
                int sshdid = this.hangDaoDao.queryHangDaoByHdBh(sshdbh).getId();
                if (tempid != 0 && tempid == sshdid) {
                    hdbhid++;
                } else {
                    hdbhid = 1;
                }
                tempid = sshdid;
                hduan.setHdbh(String.format("%03d", hdbhid));
                hduan.setSshdid(sshdid);
                hduan.setHdqdmc(row.getCell(2).getStringCellValue().trim());
                hduan.setHdzdmc(row.getCell(3).getStringCellValue().trim());
                hduan.setHdszxzqh(this.xzqhdmdao.queryXzqhdmByName(row.getCell(4).getStringCellValue().trim()).getId());
                hduan.setHdlc(jgDoubleNull(row.getCell(5)));
                hduan.setCflc(jgDoubleNull(row.getCell(6)));
                hduan.setHdss(jgDoubleNull(row.getCell(7)));
                hduan.setHdsscjsj(jgDateNull(row.getCell(8)));
                hduan.setHdkd(jgDoubleNull(row.getCell(9)));
                hduan.setHdzxwqbj(jgDoubleNull(row.getCell(10)));
                hduan.setZgthsw(jgDoubleNull(row.getCell(11)));
                hduan.setZdthsw(jgDoubleNull(row.getCell(12)));
                hduan.setHdxzjsdj(getDicIdx("jsdj", row.getCell(13)));
                hduan.setHddjjsdj(getDicIdx("jsdj", row.getCell(14)));
                hduan.setZdthswbzl(jgDoubleNull(row.getCell(15)));
                hduan.setCcthhcdbcxzzd(jgDoubleNull(row.getCell(16)));
                hduan.setGhccthhcdbcxzzd(jgDoubleNull(row.getCell(17)));
                hduan.setHdsfth(getBooleanInt(row.getCell(18)));
                hduan.setSfjjxhd(getBooleanInt(row.getCell(19)));
                hduan.setGhjzzxthjg(jgDoubleNull(row.getCell(20)));
                hduan.setGhjzzxthjk(jgDoubleNull(row.getCell(21)));
                hduan.setGhjzmkss(jgDoubleNull(row.getCell(22)));
                hduan.setGhjzxk(jgDoubleNull(row.getCell(23)));
                hduan.setFjhdlx(jgStringNull(row.getCell(24)));
                hduan.setSfyfcfhd(getBooleanInt(row.getCell(25)));
                hduan.setFcfhdlc(jgDoubleNull(row.getCell(26)));
                hduan.setFcfhdxzjsdj(getBooleanInt(row.getCell(27)));
                hduan.setFcfhddjjsdj(getBooleanInt(row.getCell(28)));
                hduan.setFcfhdwhlb(jgStringNull(row.getCell(29)));
                hduan.setFcfhdhbpblb(jgStringNull(row.getCell(30)));
                hduan.setSfyqt(getBooleanInt(row.getCell(31)));
                hduan.setSfzyhd(getBooleanInt(row.getCell(32)));
                hduan.setSfwjhhd(getBooleanInt(row.getCell(33)));
                hduan.setSygllx(getDicIdx("hdsygllx", row.getCell(35)));
                hduan.setHdwhlb(this.dicDao.queryIdByNameDesc("hdwhlb", row.getCell(35).getStringCellValue().trim()));
                hduan.setHddj(this.dicDao.queryIdByNameDesc("hddj", row.getCell(36).getStringCellValue().trim()));
                hduan.setHdsx(getDicIdx("hdsx", row.getCell(36)));
                hduan.setWhss(jgDoubleNull(row.getCell(38)));
                hduan.setWhdk(jgStringNull(row.getCell(39)));
                hduan.setWhwqbj(jgStringNull(row.getCell(40)));
                hduan.setWhssbzl(jgStringNull(row.getCell(41)));
                hduan.setHbsblc(jgDoubleNull(row.getCell(42)));
                hduan.setHbpblb(jgStringNull(row.getCell(43)));
                hduan.setHbwhzcl(jgStringNull(row.getCell(44)));
                hduan.setHldm(jgStringNull(row.getCell(45)));
                hduan.setHlmc(jgStringNull(row.getCell(46)));
                hduan.setHdqdlczh(jgStringNull(row.getCell(47)));
                hduan.setHdzdlczh(jgStringNull(row.getCell(48)));
                hduan.setHdqdzbx(jgDoubleNull(row.getCell(49)));
                hduan.setHdqdzby(jgDoubleNull(row.getCell(50)));
                hduan.setHdzdzbx(jgDoubleNull(row.getCell(51)));
                hduan.setHdzdzby(jgDoubleNull(row.getCell(52)));
                hduan.setQdsfwzkd(getBooleanInt(row.getCell(53)));
                hduan.setQdsfwfjd(getBooleanInt(row.getCell(54)));
                hduan.setQdfjdlb(jgStringNull(row.getCell(55)));
                hduan.setZdsfwzkd(getBooleanInt(row.getCell(56)));
                hduan.setZdsfwfjd(getBooleanInt(row.getCell(57)));
                hduan.setZdfjdlx(jgStringNull(row.getCell(58)));
                CXtDpt dept = getDeptIdXzqh(row.getCell(59));
                hduan.setGljgbh(dept.getId());
                hduan.setGljglxr(jgStringNull(row.getCell(60)));
                hduan.setGljglxdh(jgStringNull(row.getCell(61)));
                hduan.setGljgszxzqh(dept.getXzqh());
                hduan.setBz(jgStringNull(row.getCell(63)));
                hduan.setCreatetime(new Date());
                hduan.setUpdatetime(new Date());
                this.hangDuanDao.addHangDuan(hduan);
            } catch (Exception e) {
                list.add(startindex + i + 1);
                continue;
            }
        }
        return list;
    }

    private List<Integer> importNavimark(XSSFSheet sheet) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        int startindex = 9;
        int lastnum = sheet.getLastRowNum();
        int recnum = lastnum - startindex + 1;
        List<Integer> list = new ArrayList<>();
        int userid = user.getId();
        Date now = new Date();
        for (int i = 0; i < recnum; i++) {
            XSSFRow row = sheet.getRow(startindex + i);
            try {
                CHdHb hb = new CHdHb();
                hb.setBh("10000000000");
                hb.setMc(row.getCell(1).getStringCellValue().trim());
                String bh = jgStringVal(row.getCell(3));
                String hdaobh = bh.substring(0, bh.length() - 3);
                String hduanbh = bh.substring(bh.length() - 3, bh.length());
                CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoByHdBh(hdaobh);
                int hdaoid = hdao.getId();
                CHdHduanjcxx hduan = this.hangDuanDao.queryHangDuanByBh(hduanbh, hdaoid);
                hb.setFswlx(Constants.APP_NAVIGATIONMARK);
                hb.setSshdaoid(hdaoid);
                hb.setSshduanid(hduan.getId());
                hb.setLx(getDicIdx("hblx", row.getCell(4)));
                hb.setJg(getDicIdx("hbjg", row.getCell(6)));
                hb.setZcfs(getDicIdx("hbzcfs", row.getCell(5)));
                hb.setDylb(getDicIdx("hbdylb", row.getCell(7)));
                hb.setYcyk(getBooleanInt(row.getCell(8)));
                hb.setBzys(jgStringNull(row.getCell(9)));
                hb.setDgys(jgStringNull(row.getCell(10)));
                hb.setSgjz(jgStringNull(row.getCell(11)));
                hb.setSgzq(jgStringNull(row.getCell(12)));
                hb.setDgsc(jgStringNull(row.getCell(13)));
                hb.setDqxh(jgStringNull(row.getCell(14)));
                hb.setDzxh(getDicIdx("hbdzxh", row.getCell(15)));
                hb.setGxxz(getDicIdx("hbgxxz", row.getCell(16)));
                hb.setDbys(getDicIdx("hbdbys", row.getCell(17)));
                hb.setHbdgd(jgDoubleNull(row.getCell(18)));
                hb.setSfydb(getBooleanInt(row.getCell(19)));
                hb.setPmcc(jgStringNull(row.getCell(21)));
                hb.setSflsb(getBooleanInt(row.getCell(20)));
                hb.setSzxzqh(getXzqhByName(row.getCell(23)));
                hb.setAb(getDicIdx("ab", row.getCell(24)));
                hb.setZxlczh(jgStringNull(row.getCell(25)));
                hb.setJd(jgDoubleNull(row.getCell(26)));
                hb.setWd(jgDoubleNull(row.getCell(27)));
                hb.setSffcfhds(getBooleanInt(row.getCell(28)));
                hb.setDqsccj(jgStringNull(row.getCell(29)));
                hb.setSbsj(jgDateNull(row.getCell(22)));
                CXtDpt gydept = getDeptIdXzqh(row.getCell(30));
                hb.setGljgmc(jgStringNull(row.getCell(32)));
                hb.setGljglxr(jgStringNull(row.getCell(32)));
                hb.setGljglxdh(jgStringNull(row.getCell(33)));
                hb.setGljgszxzqh(gydept.getXzqh());
                hb.setBz(jgStringNull(row.getCell(34)));
                hb.setCreater(userid);
                hb.setCreatetime(now);
                hb.setUpdatetime(now);
                this.appurtenanceDao.addNavigationmark(hb);
                hb.setBh(String.format("%06d", hb.getId()));
            } catch (Exception e) {
                list.add(startindex + i + 1);
                continue;
            }
        }
        return list;
    }

    private List<Integer> importBridge(XSSFSheet sheet) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        int startindex = 4;
        int lastnum = sheet.getLastRowNum();
        int recnum = lastnum - startindex + 1;
        List<Integer> list = new ArrayList<>();
        int userid = user.getId();
        Date now = new Date();
        for (int i = 0; i < recnum; i++) {
            XSSFRow row = sheet.getRow(startindex + i);
            try {
                CHdQl ql = new CHdQl();
                ql.setBh("30000000000");
                ql.setMc(row.getCell(1).getStringCellValue().trim());
                ql.setSzxzqh(getXzqhByName(row.getCell(2)));
                String bh = jgStringVal(row.getCell(4));
                String hdaobh = bh.substring(0, bh.length() - 3);
                String hduanbh = bh.substring(bh.length() - 3, bh.length());
                CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoByHdBh(hdaobh);
                int hdaoid = hdao.getId();
                CHdHduanjcxx hduan = this.hangDuanDao.queryHangDuanByBh(hduanbh, hdaoid);
                ql.setFswlx(Constants.APP_BRIDGE);
                ql.setSshdaoid(hdaoid);
                ql.setSshduanid(hduan.getId());
                ql.setQmc(jgDoubleNull(row.getCell(5)));
                ql.setQmk(jgDoubleNull(row.getCell(6)));
                ql.setYwbz(getBooleanInt(row.getCell(7)));
                ql.setLdbg(jgDoubleNull(row.getCell(8)));
                ql.setYtfl(getDicIdx("qlytfl", row.getCell(9)));
                ql.setSfdb(getBooleanInt(row.getCell(10)));
                ql.setXxdz(jgStringVal(row.getCell(11)));
                ql.setYwfzss(getBooleanInt(row.getCell(12)));
                ql.setSjzgthsw(jgDoubleNull(row.getCell(13)));
                ql.setSjzgthswhscxq(jgDoubleNull(row.getCell(14)));
                ql.setJgxs(getDicIdx("qljgxs", row.getCell(15)));
                ql.setThks(jgIntegerNull(row.getCell(16)));
                ql.setThjg(jgDoubleNull(row.getCell(17)));
                ql.setThjk(jgDoubleNull(row.getCell(18)));
                ql.setKyjk(jgDoubleNull(row.getCell(19)));
                ql.setKejk(jgDoubleNull(row.getCell(20)));
                ql.setKsanjk(jgDoubleNull(row.getCell(21)));
                ql.setKsijk(jgDoubleNull(row.getCell(22)));
                ql.setKwjk(jgDoubleNull(row.getCell(23)));
                ql.setFxysljj(jgDoubleNull(row.getCell(24)));
                ql.setLghyqjg(jgDoubleNull(row.getCell(25)));
                ql.setLghyqjk(jgDoubleNull(row.getCell(26)));
                ql.setLghhddj(jgStringNull(row.getCell(27)));
                ql.setXbghyqjk(jgDoubleNull(row.getCell(28)));
                ql.setXbghyqjg(jgDoubleNull(row.getCell(29)));
                ql.setXbghhddj(jgStringNull(row.getCell(30)));
                ql.setSffhhddj(getBooleanInt(row.getCell(31)));
                ql.setSfmzlgh(getBooleanInt(row.getCell(32)));
                ql.setSfmzxbgh(getBooleanInt(row.getCell(33)));
                ql.setJzzj(jgDoubleNull(row.getCell(34)));
                ql.setCssw(jgDoubleNull(row.getCell(35)));
                ql.setZjny(jgStringNull(row.getCell(36)));
                ql.setJcnf(jgStringNull(row.getCell(37)));
                ql.setZxlczh(jgStringNull(row.getCell(38)));
                ql.setJd(jgDoubleNull(row.getCell(39)));
                ql.setWd(jgDoubleNull(row.getCell(40)));
                ql.setSffcfhds(getBooleanInt(row.getCell(41)));
                ql.setGljgmc(jgStringNull(row.getCell(42)));
                ql.setGljglxr(jgStringNull(row.getCell(43)));
                ql.setGljglxdh(jgStringNull(row.getCell(44)));
                ql.setGljgszxzqh(getXzqhByName(row.getCell(45)));
                ql.setBz(jgStringNull(row.getCell(46)));
                ql.setCreater(userid);
                ql.setCreatetime(now);
                ql.setUpdatetime(now);
                this.appurtenanceDao.addBridge(ql);
                ql.setBh(String.format("%06d", ql.getId()));
            } catch (Exception e) {
                list.add(startindex + i + 1);
                continue;
            }
        }
        return list;
    }

    private List<Integer> importCable(XSSFSheet sheet) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        int startindex = 3;
        int lastnum = sheet.getLastRowNum();
        int recnum = lastnum - startindex + 1;
        List<Integer> list = new ArrayList<>();
        int userid = user.getId();
        Date now = new Date();
        for (int i = 0; i < recnum; i++) {
            XSSFRow r = sheet.getRow(startindex + i);
            try {
                CHdLx lx = new CHdLx();
                lx.setBh("30000000000");
                lx.setMc(r.getCell(1).getStringCellValue().trim());
                String bh = jgStringVal(r.getCell(3));
                String hdaobh = bh.substring(0, bh.length() - 3);
                String hduanbh = bh.substring(bh.length() - 3, bh.length());
                CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoByHdBh(hdaobh);
                int hdaoid = hdao.getId();
                CHdHduanjcxx hduan = this.hangDuanDao.queryHangDuanByBh(hduanbh, hdaoid);
                lx.setFswlx(Constants.APP_CABLE);
                lx.setSshdaoid(hdaoid);
                lx.setSshduanid(hduan.getId());
                lx.setDbszsd(jgDoubleNull(r.getCell(4)));
                lx.setYwbz(getBooleanInt(r.getCell(5)));
                lx.setJsdw(jgStringNull(r.getCell(6)));
                lx.setJcnf(jgStringNull(r.getCell(7)));
                lx.setZl(getDicIdx("lxzl", r.getCell(8)));
                lx.setKj(jgDoubleNull(r.getCell(9)));
                lx.setJg(jgStringNull(r.getCell(10)));
                lx.setCssw(jgDoubleNull(r.getCell(11)));
                lx.setYtfl(jgStringNull(r.getCell(12)));
                lx.setSfdb(getBooleanInt(r.getCell(13)));
                lx.setYwfzss(getBooleanInt(r.getCell(14)));
                lx.setXxdz(jgStringNull(r.getCell(15)));
                lx.setZajl(jgDoubleNull(r.getCell(16)));
                lx.setYajl(jgDoubleNull(r.getCell(17)));
                lx.setZajd(jgDoubleNull(r.getCell(18)));
                lx.setZawd(jgDoubleNull(r.getCell(19)));
                lx.setYajd(jgDoubleNull(r.getCell(20)));
                lx.setYawd(jgDoubleNull(r.getCell(21)));
                lx.setSzxzqh(getXzqhByName(r.getCell(22)));
                lx.setZxlczh(jgStringNull(r.getCell(23)));
                lx.setJd(jgDoubleNull(r.getCell(24)));
                lx.setWd(jgDoubleNull(r.getCell(25)));
                lx.setSffcfhds(getBooleanInt(r.getCell(26)));
                lx.setGljgmc(jgStringNull(r.getCell(27)));
                lx.setGljgszxzqh(getXzqhByName(r.getCell(28)));
                lx.setGljglxr(jgStringNull(r.getCell(29)));
                lx.setGljglxdh(jgStringNull(r.getCell(30)));
                lx.setBz(jgStringNull(r.getCell(31)));
                lx.setCreater(userid);
                lx.setCreatetime(now);
                lx.setUpdatetime(now);
                this.appurtenanceDao.addCable(lx);
                lx.setBh(String.format("%06d", lx.getId()));
            } catch (Exception e) {
                list.add(startindex + i + 1);
                continue;
            }
        }
        return list;
    }

    private List<Integer> importKydock(XSSFSheet sheet) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        int startindex = 3;
        int lastnum = sheet.getLastRowNum();
        int recnum = lastnum - startindex + 1;
        List<Integer> list = new ArrayList<>();
        int userid = user.getId();
        Date now = new Date();
        for (int i = 0; i < recnum; i++) {
            XSSFRow r = sheet.getRow(startindex + i);
            try {
                CHdKymt mt = new CHdKymt();
                mt.setBh("40000000000");
                mt.setMc(r.getCell(1).getStringCellValue().trim());
                String bh = jgStringVal(r.getCell(3));
                String hdaobh = bh.substring(0, bh.length() - 3);
                String hduanbh = bh.substring(bh.length() - 3, bh.length());
                CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoByHdBh(hdaobh);
                int hdaoid = hdao.getId();
                CHdHduanjcxx hduan = this.hangDuanDao.queryHangDuanByBh(hduanbh, hdaoid);
                mt.setFswlx(Constants.APP_KYDOCK);
                mt.setSshdaoid(hdaoid);
                mt.setSshduanid(hduan.getId());
                mt.setLx(jgStringNull(r.getCell(4)));
                mt.setJglx(getDicIdx("mtjglx", r.getCell(5)));
                mt.setTkdj(jgDoubleNull(r.getCell(6)));
                mt.setSfwxpmt(getBooleanInt(r.getCell(7)));
                mt.setHwzl(jgStringNull(r.getCell(8)));
                mt.setSfzsyz(getBooleanInt(r.getCell(9)));
                mt.setYysj(jgDateNull(r.getCell(10)));
                mt.setSfah(getBooleanInt(r.getCell(11)));
                mt.setKbnl(jgIntegerNull(r.getCell(12)));
                mt.setXlzsl(jgIntegerNull(r.getCell(13)));
                mt.setLthxsl(jgIntegerNull(r.getCell(14)));
                mt.setGsksl(jgIntegerNull(r.getCell(15)));
                mt.setZmdsl(jgIntegerNull(r.getCell(16)));
                mt.setCdsssl(jgIntegerNull(r.getCell(17)));
                mt.setSzxzqh(getXzqhByName(r.getCell(18)));
                mt.setAb(getDicIdx("ab", r.getCell(19)));
                mt.setJd(jgDoubleNull(r.getCell(26)));
                mt.setWd(jgDoubleNull(r.getCell(27)));
                mt.setSsqdlczh(jgStringNull(r.getCell(20)));
                mt.setZyax(jgDoubleNull(r.getCell(21)));
                mt.setZyaxcd(jgDoubleNull(r.getCell(22)));
                mt.setZyaxqdzbx(jgDoubleNull(r.getCell(23)));
                mt.setZyaxqdzby(jgDoubleNull(r.getCell(24)));
                mt.setZyaxzdzbx(jgDoubleNull(r.getCell(25)));
                mt.setZyaxzdzby(jgDoubleNull(r.getCell(26)));
                mt.setSffcfhds(getBooleanInt(r.getCell(29)));
                mt.setBws(jgIntegerNull(r.getCell(30)));
                mt.setJcnf(jgStringNull(r.getCell(31)));
                mt.setGljgmc(jgStringNull(r.getCell(32)));
                mt.setGljglxr(jgStringNull(r.getCell(33)));
                mt.setGljglxdh(jgStringNull(r.getCell(34)));
                mt.setGljgszxzqh(getXzqhByName(r.getCell(35)));
                mt.setBz(jgStringNull(r.getCell(36)));
                mt.setCreater(userid);
                mt.setCreatetime(now);
                mt.setUpdatetime(now);
                this.appurtenanceDao.addKyDock(mt);
                mt.setBh(String.format("%06d", mt.getId()));
            } catch (Exception e) {
                list.add(startindex + i + 1);
                continue;
            }
        }
        return list;
    }

    private List<Integer> importHydock(XSSFSheet sheet) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        int startindex = 3;
        int lastnum = sheet.getLastRowNum();
        int recnum = lastnum - startindex + 1;
        List<Integer> list = new ArrayList<>();
        int userid = user.getId();
        Date now = new Date();
        for (int i = 0; i < recnum; i++) {
            XSSFRow r = sheet.getRow(startindex + i);
            try {
                CHdHymt mt = new CHdHymt();
                mt.setBh("40000000000");
                mt.setMc(r.getCell(1).getStringCellValue().trim());
                String bh = jgStringVal(r.getCell(3));
                String hdaobh = bh.substring(0, bh.length() - 3);
                String hduanbh = bh.substring(bh.length() - 3, bh.length());
                CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoByHdBh(hdaobh);
                int hdaoid = hdao.getId();
                CHdHduanjcxx hduan = this.hangDuanDao.queryHangDuanByBh(hduanbh, hdaoid);
                mt.setFswlx(Constants.APP_KYDOCK);
                mt.setSshdaoid(hdaoid);
                mt.setSshduanid(hduan.getId());
                mt.setLx(jgStringNull(r.getCell(4)));
                mt.setJglx(getDicIdx("mtjglx", r.getCell(5)));
                mt.setTkdj(jgDoubleNull(r.getCell(6)));
                mt.setSfwxpmt(getBooleanInt(r.getCell(7)));
                mt.setHwzl(jgStringNull(r.getCell(8)));
                mt.setSfzsyz(getBooleanInt(r.getCell(9)));
                mt.setYysj(jgDateNull(r.getCell(10)));
                mt.setSfah(getBooleanInt(r.getCell(11)));
                mt.setKbnl(jgIntegerNull(r.getCell(12)));
                mt.setXlzsl(jgIntegerNull(r.getCell(13)));
                mt.setLthxsl(jgIntegerNull(r.getCell(14)));
                mt.setGsksl(jgIntegerNull(r.getCell(15)));
                mt.setZmdsl(jgIntegerNull(r.getCell(16)));
                mt.setCdsssl(jgIntegerNull(r.getCell(17)));
                mt.setSzxzqh(getXzqhByName(r.getCell(18)));
                mt.setAb(getDicIdx("ab", r.getCell(19)));
                mt.setJd(jgDoubleNull(r.getCell(26)));
                mt.setWd(jgDoubleNull(r.getCell(27)));
                mt.setSsqdlczh(jgStringNull(r.getCell(20)));
                mt.setZyax(jgDoubleNull(r.getCell(21)));
                mt.setZyaxcd(jgDoubleNull(r.getCell(22)));
                mt.setZyaxqdzbx(jgDoubleNull(r.getCell(23)));
                mt.setZyaxqdzby(jgDoubleNull(r.getCell(24)));
                mt.setZyaxzdzbx(jgDoubleNull(r.getCell(25)));
                mt.setZyaxzdzby(jgDoubleNull(r.getCell(26)));
                mt.setSffcfhds(getBooleanInt(r.getCell(29)));
                mt.setBws(jgIntegerNull(r.getCell(30)));
                mt.setJcnf(jgStringNull(r.getCell(31)));
                mt.setGljgmc(jgStringNull(r.getCell(32)));
                mt.setGljglxr(jgStringNull(r.getCell(33)));
                mt.setGljglxdh(jgStringNull(r.getCell(34)));
                mt.setGljgszxzqh(getXzqhByName(r.getCell(35)));
                mt.setBz(jgStringNull(r.getCell(36)));
                mt.setCreater(userid);
                mt.setCreatetime(now);
                mt.setUpdatetime(now);
                this.appurtenanceDao.addHyDock(mt);
                mt.setBh(String.format("%06d", mt.getId()));
            } catch (Exception e) {
                list.add(startindex + i + 1);
                continue;
            }
        }
        return list;
    }

    private List<Integer> importGwdock(XSSFSheet sheet) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        int startindex = 3;
        int lastnum = sheet.getLastRowNum();
        int recnum = lastnum - startindex + 1;
        List<Integer> list = new ArrayList<>();
        int userid = user.getId();
        Date now = new Date();
        for (int i = 0; i < recnum; i++) {
            XSSFRow r = sheet.getRow(startindex + i);
            try {
                CHdGwmt mt = new CHdGwmt();
                mt.setBh("40000000000");
                mt.setMc(r.getCell(1).getStringCellValue().trim());
                String bh = jgStringVal(r.getCell(3));
                String hdaobh = bh.substring(0, bh.length() - 3);
                String hduanbh = bh.substring(bh.length() - 3, bh.length());
                CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoByHdBh(hdaobh);
                int hdaoid = hdao.getId();
                CHdHduanjcxx hduan = this.hangDuanDao.queryHangDuanByBh(hduanbh, hdaoid);
                mt.setFswlx(Constants.APP_KYDOCK);
                mt.setSshdaoid(hdaoid);
                mt.setSshduanid(hduan.getId());
                mt.setLx(jgStringNull(r.getCell(4)));
                mt.setJglx(getDicIdx("mtjglx", r.getCell(5)));
                mt.setTkdj(jgDoubleNull(r.getCell(6)));
                mt.setSfwxpmt(getBooleanInt(r.getCell(7)));
                mt.setHwzl(jgStringNull(r.getCell(8)));
                mt.setSfzsyz(getBooleanInt(r.getCell(9)));
                mt.setYysj(jgDateNull(r.getCell(10)));
                mt.setSfah(getBooleanInt(r.getCell(11)));
                mt.setKbnl(jgIntegerNull(r.getCell(12)));
                mt.setXlzsl(jgIntegerNull(r.getCell(13)));
                mt.setLthxsl(jgIntegerNull(r.getCell(14)));
                mt.setGsksl(jgIntegerNull(r.getCell(15)));
                mt.setZmdsl(jgIntegerNull(r.getCell(16)));
                mt.setCdsssl(jgIntegerNull(r.getCell(17)));
                mt.setSzxzqh(getXzqhByName(r.getCell(18)));
                mt.setAb(getDicIdx("ab", r.getCell(19)));
                mt.setJd(jgDoubleNull(r.getCell(26)));
                mt.setWd(jgDoubleNull(r.getCell(27)));
                mt.setSsqdlczh(jgStringNull(r.getCell(20)));
                mt.setZyax(jgDoubleNull(r.getCell(21)));
                mt.setZyaxcd(jgDoubleNull(r.getCell(22)));
                mt.setZyaxqdzbx(jgDoubleNull(r.getCell(23)));
                mt.setZyaxqdzby(jgDoubleNull(r.getCell(24)));
                mt.setZyaxzdzbx(jgDoubleNull(r.getCell(25)));
                mt.setZyaxzdzby(jgDoubleNull(r.getCell(26)));
                mt.setSffcfhds(getBooleanInt(r.getCell(29)));
                mt.setBws(jgIntegerNull(r.getCell(30)));
                mt.setJcnf(jgStringNull(r.getCell(31)));
                mt.setGljgmc(jgStringNull(r.getCell(32)));
                mt.setGljglxr(jgStringNull(r.getCell(33)));
                mt.setGljglxdh(jgStringNull(r.getCell(34)));
                mt.setGljgszxzqh(getXzqhByName(r.getCell(35)));
                mt.setBz(jgStringNull(r.getCell(36)));
                mt.setCreater(userid);
                mt.setCreatetime(now);
                mt.setUpdatetime(now);
                this.appurtenanceDao.addGwDock(mt);
                mt.setBh(String.format("%06d", mt.getId()));
            } catch (Exception e) {
                list.add(startindex + i + 1);
                continue;
            }
        }
        return list;
    }

    @Override
    public BaseResult downloadTemplate(int fswlx) {
        String filename = "";
        switch (fswlx) {
            case Constants.APP_HDAO:
                filename = "航道.xlsx";
                break;
            case Constants.APP_HDUAN:
                filename = "航段.xlsx";
                break;
            case Constants.APP_NAVIGATIONMARK:
                filename = "航标.xlsx";
                break;
            case Constants.APP_BRIDGE:
                filename = "桥梁.xlsx";
                break;
            case Constants.APP_CABLE:
                filename = "缆线.xlsx";
                break;
            case Constants.APP_KYDOCK:
                filename = "码头.xlsx";
                break;
            case Constants.APP_HYDOCK:
                filename = "码头.xlsx";
                break;
            case Constants.APP_GWDOCK:
                filename = "码头.xlsx";
                break;
        }
        try {
            if ("".equals(filename)) {
                return new BaseResultFailed(Constants.RESULT_ATTACH_NOTEXIST,
                        "模板不存在");
            } else {
                BaseResultOK baseResultOK = new BaseResultOK();
                baseResultOK.addToMap("filename", filename);
                return baseResultOK;
            }
        } catch (Exception e) {
            return new BaseResultFailed(Constants.RESULT_ATTACH_NOTEXIST,
                    "模板不存在");
        }
    }


    @Override
    public BaseResult queryMaxAppbh(int fswlx) {
        CZdAppurtenanceRela rela = this.appurtenancetypedao.queryPidBysid(fswlx);
        String strpre = "";
        if (rela != null) {
            int pid = rela.getPid();
            if (pid == 2) {
                strpre = "3";
            } else if (pid == 3) {
                strpre = "4";
            } else if (pid == 4) {
                strpre = "2";
            } else {
                strpre = "0";
            }
        }
        Long records = this.appurtenanceDao.queryMaxAppbh(fswlx);
        String str = "";
        if (records != null && records != -1) {
            long returnhdbh = records + 1;
            if (returnhdbh > 99999999999.0) {
                for (int i = 1; i < 100000000000.0; i++) {
                    String tempstr = String.valueOf(i);
                    int tempn = tempstr.length();
                    for (int j = tempn; j < 10; j++) {
                        tempstr = "0" + tempstr;
                    }
                    tempstr = strpre + tempstr;
                    boolean ifexisted = this.appurtenanceDao.queryAppbhExisted(tempstr, fswlx);
                    if (ifexisted) {
                        str = tempstr;
                        break;
                    }
                }
            } else {
                str = String.valueOf(returnhdbh);
                int n = str.length();
                for (int i = n; i < 10; i++) {
                    str = "0" + str;
                }
                str = strpre + str;
            }
        } else {
            str = strpre + "0000000001";
        }
        BaseResultOK baseResultOK = new BaseResultOK(str);
        return baseResultOK;
    }

    @Override
    public BaseResult queryAppbhExisted(String bh, int fswlx) {
        Boolean records = this.appurtenanceDao.queryAppbhExisted(bh, fswlx);
        if (records == true)
            return new BaseResultOK();
        else
            return new BaseResultFailed();
    }

    @Override
    public BaseResult queryAppPopup(int id, int fswlx) {
        BaseQueryRecords<Object> records = this.appurtenanceDao.queryAppPopup(id, fswlx);
        BaseResultOK baseResultOK = new BaseResultOK(records);
        return baseResultOK;
    }

    @Override
    public BaseResult queryFsw() {
        BaseQueryRecords records = this.appurtenancetypedao._findLeafNodes();
        BaseResultOK baseResultOK = new BaseResultOK(records);
        return baseResultOK;
    }

    @Override
    public BaseResult searchAppurtenances(int xzqhid, int sshdid, int sshduanid, int fswsecondclassid, String content) {
        BaseQueryRecords records = null;
        CZdXzqhdm xzqh = this.xzqhdmdao.queryXzqhdmById(xzqhid);
        if (xzqhid == -1) {
            records = this.appurtenanceDao.searchAppurtenances(null, sshdid, sshduanid, fswsecondclassid, content);
        } else {
            List<CZdXzqhdm> xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqh);
            xzqhs.add(xzqh);
            records = this.appurtenanceDao.searchAppurtenances(xzqhs, sshdid, sshduanid, fswsecondclassid, content);
        }
        return new BaseResultOK(records);
    }

    @Override
    public BaseResult queryAppurtenances(int secondclassid, Integer sshduanid, int page, int rows) {
        BaseResultOK baseResultOK = new BaseResultOK();
        BaseQueryRecords<Object> records = this.appurtenanceDao
                .queryEachApp(secondclassid, sshduanid, page, rows);
        baseResultOK.setRecords(records);
        baseResultOK.addToMap("bhpre", getAppBHPre(secondclassid));
        return baseResultOK;
    }

    @Override
    public BaseResult searchAppByContent(int secondclassid, Integer sshduanid, String content) {
        BaseResultOK baseResultOK = new BaseResultOK();
        BaseQueryRecords<Object> records = this.appurtenanceDao
                .queryEachApp(secondclassid, sshduanid, content);
        baseResultOK.setRecords(records);
        baseResultOK.addToMap("bhpre", getAppBHPre(secondclassid));
        return baseResultOK;
    }

    @Override
    public BaseResult searchAppByXzqh(int xzqhid, int secondclassid, Integer sshduanid, String content) {
        BaseResultOK baseResultOK = new BaseResultOK();
        CZdXzqhdm xzqh = this.xzqhdmdao.queryXzqhdmById(xzqhid);
        BaseQueryRecords<Object> records = new BaseQueryRecords<>();
        if (xzqhid > 1) {
            List<CZdXzqhdm> xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqh);
            xzqhs.add(xzqh);
            records = this.appurtenanceDao
                    .queryEachAppXzqh(xzqhs, secondclassid, sshduanid, content);

        } else {
            records = this.appurtenanceDao
                    .queryEachApp(secondclassid, sshduanid, content);
        }

        baseResultOK.setRecords(records);
        baseResultOK.addToMap("bhpre", getAppBHPre(secondclassid));
        return baseResultOK;
    }

    private void addCell(int srow, int erow, int scel, int ecel, XSSFSheet sheet, CellStyle cellStyle) {
        for (int j = srow; j < erow + 1; j++) {
            sheet.createRow(j);
            XSSFRow row = sheet.getRow(j);
            for (int k = scel; k < ecel + 1; k++) {
                row.createCell(k).setCellStyle(cellStyle);
            }
        }
    }

    public String queryStrXzqh(int id) {
        CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(id);
        if (xzqhdm != null) {
            Tree<CZdXzqhdm> xzqhtree = this.xzqhdmService
                    .findOnlyParentPath(xzqhdm);
            StringBuffer xzqhs = new StringBuffer();
            Tree<CZdXzqhdm> currXzqhTree = xzqhtree;
            while (true) {
                xzqhs.append(currXzqhTree.getNode().getName() + "/");
                if (currXzqhTree.getChildren().size() > 0) {
                    currXzqhTree = currXzqhTree.getChildren().get(0);
                } else {
                    break;
                }
            }
            xzqhs.deleteCharAt(xzqhs.length() - 1);// 去除最后的/
            return xzqhs.toString();
        } else {
            return "";
        }

    }

    @Override
    public BaseResult queryAppurtenanceInfo(int id, int fswlx) {
        AppurtenanceResult result = new AppurtenanceResult();
        List<Object[]> records = null;
        records = this.appurtenanceDao.queryAppurtenanceInfo(id, fswlx);
        int appid = 0;
        String bh = "";
        int typenum = 0;
        if (records != null && records.size() > 0) {
            switch (fswlx) {
                case Constants.APP_NAVIGATIONMARK:
                    CHdHb hb = (CHdHb) records.get(0)[0];
                    if (hb != null)
                        appid = hb.getId();
                    CHdHduanjcxx hbhduan = (CHdHduanjcxx) records.get(0)[1];
                    bh = hb.getBh();
                    typenum = Constants.APP_NAVIGATIONMARK;
                    result.setModelobj(hb);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdHb.class)
                                    .getPropDesc("mc"), hb.getMc(), new PropDescFields(
                                    CHdHb.class).getPropDesc("fswlx"),
                            new PropDescFields(CHdHb.class)
                                    .getPropDesc("hbname"));
                    result.AddLine(
                            new PropDescFields(CHdHb.class).getPropDesc("bh"),
                            getAppBHPre(fswlx) + hb.getBh(),
                            new PropDescFields(CHdHb.class).getPropDesc("lx"),
                            this.dicDao.queryAttrDesc(hb.getLx()));
                    result.AddLine(
                            new PropDescFields(CHdHb.class).getPropDesc("zcfs"),
                            this.dicDao.queryAttrDesc(hb.getZcfs()),
                            new PropDescFields(CHdHb.class).getPropDesc("jg"),
                            this.dicDao.queryAttrDesc(hb.getJg()));
                    result.AddLine(
                            new PropDescFields(CHdHb.class).getPropDesc("dylb"),
                            this.dicDao.queryAttrDesc(hb.getDylb()),
                            new PropDescFields(CHdHb.class).getPropDesc("ycyk"),
                            hb.getYcyk() == 1 ? "是" : "否");
                    result.AddLine(
                            new PropDescFields(CHdHb.class).getPropDesc("bzys"),
                            StringUtil.getIsNull(hb.getBzys()),
                            new PropDescFields(CHdHb.class).getPropDesc("dgys"),
                            StringUtil.getIsNull(hb.getDgys()));
                    result.AddLine(
                            new PropDescFields(CHdHb.class).getPropDesc("sgjz"),
                            StringUtil.getIsNull(hb.getSgjz()),
                            new PropDescFields(CHdHb.class).getPropDesc("sgzq"),
                            StringUtil.getIsNull(hb.getSgzq()));
                    result.AddLine(
                            new PropDescFields(CHdHb.class).getPropDesc("dgsc"),
                            StringUtil.getIsNull(hb.getDgsc()),
                            new PropDescFields(CHdHb.class).getPropDesc("dqxh"),
                            StringUtil.getIsNull(hb.getDqxh()));
                    result.AddLine(
                            new PropDescFields(CHdHb.class).getPropDesc("dzxh"),
                            hb.getDzxh() == null || hb.getDzxh() == 0 ? "" : this.dicDao.queryAttrDesc(hb.getDzxh()),
                            new PropDescFields(CHdHb.class).getPropDesc("gxxz"),
                            this.dicDao.queryAttrDesc(hb.getGxxz()));
                    result.AddLine(new PropDescFields(CHdHb.class)
                            .getPropDesc("dbys"), this.dicDao.queryAttrDesc(hb
                            .getDbys()), new PropDescFields(CHdHb.class)
                            .getPropDesc("hbdgd"), StringUtil.getIsNull(hb.getHbdgd()));
                    result.AddLine(new PropDescFields(CHdHb.class)
                            .getPropDesc("sfydb"), hb.getSfydb() == 1 ? "是"
                            : "否", new PropDescFields(CHdHb.class)
                            .getPropDesc("pmcc"), hb.getPmcc());
                    result.AddLine(
                            new PropDescFields(CHdHb.class)
                                    .getPropDesc("sflsb"),
                            hb.getSflsb() == 1 ? "是" : "否",
                            new PropDescFields(CHdHb.class).getPropDesc("sbsj"),
                            DateTimeUtil.getTimeFmt(hb.getSbsj()).equals(
                                    Constants.DEFAULT_TIME) ? "" : DateTimeUtil
                                    .getTimeFmt(hb.getSbsj(), "yyyy-MM-dd"));

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdHb.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(hb.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdHb.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(hb.getSshduanid()).getHdbh());
                    result.AddLine("所属航段名称", hbhduan.getHdqdmc() + "-"
                            + hbhduan.getHdzdmc(), new PropDescFields(
                            CHdHb.class).getPropDesc("zxlczh"), hb.getZxlczh());
                    result.AddLine(new PropDescFields(CHdHb.class)
                                    .getPropDesc("jd"), StringUtil.getDoubleNull(hb.getJd()),
                            new PropDescFields(CHdHb.class)
                                    .getPropDesc("wd"), StringUtil.getDoubleNull(hb.getWd()));
                    result.AddLine(new PropDescFields(CHdHb.class)
                            .getPropDesc("ab"), hb.getAb() > 0 ? this.dicDao.queryAttrDesc(hb.getAb()) : "", new PropDescFields(
                            CHdHb.class).getPropDesc("sffcfhds"), hb
                            .getSffcfhds() == 1 ? "是" : "否");
                    result.AddLine(new PropDescFields(CHdHb.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(hb
                            .getSzxzqh()));

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdHb.class)
                                    .getPropDesc("dqsccj"), StringUtil.getIsNull(hb.getDqsccj()),
                            new PropDescFields(CHdHb.class)
                                    .getPropDesc("gljgmc"), StringUtil.getIsNull(hb.getGljgmc()));
                    result.AddLine(new PropDescFields(CHdHb.class)
                                    .getPropDesc("gljglxr"), StringUtil.getIsNull(hb.getGljglxr()),
                            new PropDescFields(CHdHb.class)
                                    .getPropDesc("gljglxdh"), StringUtil.getIsNull(hb.getGljglxdh()));
                    result.AddLine(new PropDescFields(CHdHb.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(hb
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdHb.class).getPropDesc("bz"),
                            hb.getBz() == null ? "" : hb.getBz());
                    result.AddLine(new PropDescFields(CHdHb.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(hb.getCreatetime()),
                            new PropDescFields(CHdHb.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(hb.getUpdatetime()));
                    break;
                case Constants.APP_BRIDGE:
                    CHdQl ql = (CHdQl) records.get(0)[0];
                    if (ql != null)
                        appid = ql.getId();
                    CHdHduanjcxx qlhduan = (CHdHduanjcxx) records.get(0)[1];
                    bh = ql.getBh();
                    typenum = Constants.APP_BRIDGE;
                    result.setModelobj(ql);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("mc"), ql.getMc(), new PropDescFields(
                                    CHdQl.class).getPropDesc("fswlx"),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("qlname"));
                    result.AddLine(
                            new PropDescFields(CHdQl.class).getPropDesc("bh"), getAppBHPre(fswlx) +
                                    StringUtil.getStringIsNullObj(ql.getBh()),
                            new PropDescFields(CHdQl.class).getPropDesc("qmc"),
                            StringUtil.getStringIsNullObj(ql.getQmc()));
                    result.AddLine(new PropDescFields(CHdQl.class).getPropDesc("qmk"),
                            StringUtil.getStringIsNullObj(ql.getQmk()), new PropDescFields(
                                    CHdQl.class).getPropDesc("ldbg"), StringUtil.getStringIsNullObj(ql
                                    .getLdbg()));
                    result.AddLine(
                            new PropDescFields(CHdQl.class).getPropDesc("ytfl"),
                            ql.getYtfl() == null ? "" : this.dicDao.queryAttrDesc(ql.getYtfl()),
                            new PropDescFields(CHdQl.class).getPropDesc("sfdb"),
                            ql.getSfdb() == 1 ? "是" : "否");
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("xxdz"), StringUtil.getStringIsNullObj(ql.getXxdz()),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("ywfzss"),
                            ql.getYwfzss() == 1 ? "是" : "否");
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("sjzgthsw"), StringUtil.getStringIsNullObj(ql.getSjzgthsw()),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("sjzgthswhscxq"), StringUtil.getStringIsNullObj(ql
                                    .getSjzgthswhscxq()));
                    result.AddLine(
                            new PropDescFields(CHdQl.class).getPropDesc("jgxs"),
                            this.dicDao.queryAttrDesc(ql.getJgxs()),
                            new PropDescFields(CHdQl.class).getPropDesc("thks"),
                            StringUtil.getStringIsNullObj(ql.getThks()));
                    result.AddLine(
                            new PropDescFields(CHdQl.class).getPropDesc("thjg"),
                            StringUtil.getStringIsNullObj(ql.getThjg()),
                            new PropDescFields(CHdQl.class).getPropDesc("thjk"),
                            StringUtil.getStringIsNullObj(ql.getThjk()));
                    result.AddLine(
                            new PropDescFields(CHdQl.class).getPropDesc("kyjk"),
                            StringUtil.getStringIsNullObj(ql.getKyjk()),
                            new PropDescFields(CHdQl.class).getPropDesc("kejk"),
                            StringUtil.getStringIsNullObj(ql.getKejk()));
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("ksanjk"), StringUtil.getStringIsNullObj(ql.getKsanjk()),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("ksijk"), StringUtil.getStringIsNullObj(ql.getKsijk()));
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("kwjk"), StringUtil.getStringIsNullObj(ql.getKwjk()),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("fxysljj"), StringUtil.getStringIsNullObj(ql.getFxysljj()));
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("lghhddj"), StringUtil.getStringIsNullObj(ql.getLghhddj()),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("lghyqjk"), StringUtil.getStringIsNullObj(ql.getLghyqjk()));
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("lghyqjg"), StringUtil.getStringIsNullObj(ql.getLghyqjg()),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("lghhddj"), StringUtil.getStringIsNullObj(ql.getLghhddj()));
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("xbghhddj"), StringUtil.getStringIsNullObj(ql.getXbghhddj()),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("sfmzlgh"),
                            ql.getSfmzlgh() == 1 ? "是" : "否");
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("xbghyqjk"), StringUtil.getStringIsNullObj(ql.getXbghyqjk()),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("xbghyqjg"), StringUtil.getStringIsNullObj(ql.getXbghyqjg()));
                    result.AddLine(
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("sfmzxbgh"),
                            ql.getSfmzxbgh() == 1 ? "是" : "否",
                            new PropDescFields(CHdQl.class).getPropDesc("jzzj"),
                            StringUtil.getStringIsNullObj(ql.getJzzj()));
                    result.AddLine(new PropDescFields(CHdQl.class).getPropDesc("cssw"), StringUtil.getStringIsNullObj(ql.getCssw()), new PropDescFields(CHdQl.class)
                            .getPropDesc("ywbz"), ql.getYwbz() == 1 ? "是" : "否");
                    result.AddLine(
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("sffhhddj"),
                            ql.getSffhhddj() == 1 ? "是" : "否");

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(ql.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(ql
                                    .getSshduanid()).getHdbh());
                    result.AddLine("所属航段名称", qlhduan.getHdqdmc() + "-"
                            + qlhduan.getHdzdmc(), new PropDescFields(
                            CHdQl.class).getPropDesc("zxlczh"), StringUtil.getStringIsNullObj(ql.getZxlczh()));
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("jd"), StringUtil.getDoubleNull(ql.getJd()),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("wd"), StringUtil.getDoubleNull(ql.getWd()));
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("szxzqh"), this.queryStrXzqh(ql
                                    .getSzxzqh()), new PropDescFields(CHdQl.class)
                                    .getPropDesc("sffcfhds"),
                            ql.getSffcfhds() == 1 ? "是" : "否");

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("jcnf"), ql.getJcnf(),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("gljgmc"), ql.getGljgmc());
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("gljglxr"), ql.getGljglxr(),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("gljglxdh"), ql.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdQl.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(ql
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdQl.class).getPropDesc("bz"),
                            ql.getBz() == null ? "" : ql.getBz());
                    result.AddLine(new PropDescFields(CHdQl.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(ql.getCreatetime()),
                            new PropDescFields(CHdQl.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(ql.getUpdatetime()));
                    break;
                case Constants.APP_AQUEDUCT:
                    CHdDc dc = (CHdDc) records.get(0)[0];
                    if (dc != null)
                        appid = dc.getId();
                    CHdHduanjcxx dchduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(dc);

                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdDc.class)
                                    .getPropDesc("mc"), dc.getMc(), new PropDescFields(
                                    CHdDc.class).getPropDesc("fswlx"),
                            new PropDescFields(CHdDc.class)
                                    .getPropDesc("dcname"));
                    result.AddLine(
                            new PropDescFields(CHdDc.class).getPropDesc("bh"), getAppBHPre(fswlx) +
                                    StringUtil.getStringIsNullObj(dc.getBh()),
                            new PropDescFields(CHdDc.class).getPropDesc("xxdz"),
                            StringUtil.getStringIsNullObj(dc.getXxdz()));
                    result.AddLine(new PropDescFields(CHdDc.class)
                                    .getPropDesc("ywbz"),
                            dc.getYwbz() == 1 ? "是" : "否", new PropDescFields(
                                    CHdDc.class).getPropDesc("ywfzss"), dc
                                    .getYwfzss() == 1 ? "是" : "否");
                    result.AddLine(
                            new PropDescFields(CHdDc.class).getPropDesc("ytfl"),
                            StringUtil.getStringIsNullObj(dc.getYtfl()),
                            new PropDescFields(CHdDc.class).getPropDesc("sfdb"),
                            dc.getSfdb() == 1 ? "是" : "否");

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdDc.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(dc.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdDc.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(dc.getSshduanid()).getHdbh());
                    result.AddLine("所属航段名称", dchduan.getHdqdmc() + "-"
                            + dchduan.getHdzdmc(), new PropDescFields(
                            CHdDc.class).getPropDesc("zxlczh"), StringUtil.getStringIsNullObj(dc.getZxlczh()));
                    result.AddLine(new PropDescFields(CHdDc.class)
                                    .getPropDesc("jd"), StringUtil.getDoubleNull(dc.getJd()),
                            new PropDescFields(CHdDc.class)
                                    .getPropDesc("wd"), StringUtil.getDoubleNull(dc.getWd()));
                    result.AddLine(new PropDescFields(CHdDc.class)
                                    .getPropDesc("szxzqh"), this.queryStrXzqh(dc
                                    .getSzxzqh()), new PropDescFields(CHdDc.class)
                                    .getPropDesc("sffcfhds"),
                            dc.getSffcfhds() == 1 ? "是" : "否");

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdDc.class)
                                    .getPropDesc("jcnf"), dc.getJcnf(),
                            new PropDescFields(CHdDc.class)
                                    .getPropDesc("gljgmc"), dc.getGljgmc());
                    result.AddLine(new PropDescFields(CHdDc.class)
                                    .getPropDesc("gljglxr"), dc.getGljglxr(),
                            new PropDescFields(CHdDc.class)
                                    .getPropDesc("gljglxdh"), dc.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdDc.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(dc
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdDc.class).getPropDesc("bz"),
                            dc.getBz() == null ? "" : dc.getBz());
                    result.AddLine(new PropDescFields(CHdDc.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(dc.getCreatetime()),
                            new PropDescFields(CHdDc.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(dc.getUpdatetime()));

                    break;
                case Constants.APP_CABLE:
                    CHdLx lx = (CHdLx) records.get(0)[0];
                    if (lx != null)
                        appid = lx.getId();
                    CHdHduanjcxx lxhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(lx);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdLx.class)
                                    .getPropDesc("mc"), lx.getMc(), new PropDescFields(
                                    CHdLx.class).getPropDesc("fswlx"),
                            new PropDescFields(CHdLx.class)
                                    .getPropDesc("lxname"));
                    result.AddLine(
                            new PropDescFields(CHdLx.class).getPropDesc("bh"),
                            getAppBHPre(fswlx) + lx.getBh(),
                            new PropDescFields(CHdLx.class).getPropDesc("xxdz"),
                            lx.getXxdz());
                    result.AddLine(new PropDescFields(CHdLx.class)
                                    .getPropDesc("dbszsd"), StringUtil.getStringIsNullObj(lx.getDbszsd()),
                            new PropDescFields(CHdLx.class).getPropDesc("zl"),
                            this.dicDao.queryAttrDesc(lx.getZl()));
                    result.AddLine(
                            new PropDescFields(CHdLx.class).getPropDesc("kj"),
                            StringUtil.getStringIsNullObj(lx.getKj()),
                            new PropDescFields(CHdLx.class).getPropDesc("jg"),
                            StringUtil.getStringIsNullObj(lx.getJg()));
                    result.AddLine(
                            new PropDescFields(CHdLx.class).getPropDesc("cssw"),
                            StringUtil.getStringIsNullObj(lx.getCssw()));
                    result.AddLine(new PropDescFields(CHdLx.class)
                                    .getPropDesc("ywbz"),
                            lx.getYwbz() == 1 ? "是" : "否", new PropDescFields(
                                    CHdLx.class).getPropDesc("ywfzss"), lx
                                    .getYwfzss() == 1 ? "是" : "否");
                    result.AddLine(
                            new PropDescFields(CHdLx.class).getPropDesc("ytfl"),
                            StringUtil.getStringIsNullObj(lx.getYtfl()),
                            new PropDescFields(CHdLx.class).getPropDesc("sfdb"),
                            lx.getSfdb() == 1 ? "是" : "否");

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdLx.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(lx.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdLx.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(lx.getSshduanid()).getHdbh());
                    result.AddLine("所属航段名称", lxhduan.getHdqdmc() + "-"
                            + lxhduan.getHdzdmc(), new PropDescFields(
                            CHdLx.class).getPropDesc("zxlczh"), lx.getZxlczh());

                    result.AddLine(new PropDescFields(CHdLx.class)
                                    .getPropDesc("jd"), StringUtil.getStringIsNullObj(lx.getJd()), new
                                    PropDescFields(CHdLx.class).getPropDesc("wd"),
                            StringUtil.getStringIsNullObj(lx.getWd()));

                    result.AddLine(
                            new PropDescFields(CHdLx.class).getPropDesc("zajl"),
                            StringUtil.getStringIsNullObj(lx.getZajl()),
                            new PropDescFields(CHdLx.class).getPropDesc("yajl"),
                            StringUtil.getStringIsNullObj(lx.getYajl()));
                    result.AddLine(
                            new PropDescFields(CHdLx.class).getPropDesc("zajd"),
                            StringUtil.getStringIsNullObj(lx.getZajd()),
                            new PropDescFields(CHdLx.class).getPropDesc("zawd"),
                            StringUtil.getStringIsNullObj(lx.getZawd()));
                    result.AddLine(
                            new PropDescFields(CHdLx.class).getPropDesc("yajd"),
                            StringUtil.getStringIsNullObj(lx.getYajd()),
                            new PropDescFields(CHdLx.class).getPropDesc("yawd"),
                            StringUtil.getStringIsNullObj(lx.getYawd()));
                    result.AddLine(new PropDescFields(CHdLx.class)
                                    .getPropDesc("szxzqh"), this.queryStrXzqh(lx
                                    .getSzxzqh()), new PropDescFields(CHdLx.class)
                                    .getPropDesc("sffcfhds"),
                            lx.getSffcfhds() == 1 ? "是" : "否");

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdLx.class)
                                    .getPropDesc("jcnf"), lx.getJcnf(),
                            new PropDescFields(CHdLx.class)
                                    .getPropDesc("gljgmc"), lx.getGljgmc());
                    result.AddLine(new PropDescFields(CHdLx.class)
                                    .getPropDesc("gljglxr"), lx.getGljglxr(),
                            new PropDescFields(CHdLx.class)
                                    .getPropDesc("gljglxdh"), lx.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdLx.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(lx
                            .getGljgszxzqh()), new PropDescFields(CHdLx.class)
                            .getPropDesc("jsdw"), lx.getJsdw());
                    result.AddLine(
                            new PropDescFields(CHdLx.class).getPropDesc("bz"),
                            lx.getBz() == null ? "" : lx.getBz());
                    result.AddLine(new PropDescFields(CHdLx.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(lx.getCreatetime()),
                            new PropDescFields(CHdLx.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(lx.getUpdatetime()));
                    break;
                case Constants.APP_PIPELINE:
                    CHdGd gd = (CHdGd) records.get(0)[0];
                    if (gd != null)
                        appid = gd.getId();
                    CHdHduanjcxx gdhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(gd);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdGd.class)
                                    .getPropDesc("mc"), gd.getMc(), new PropDescFields(
                                    CHdGd.class).getPropDesc("fswlx"),
                            new PropDescFields(CHdGd.class)
                                    .getPropDesc("gdname"));
                    result.AddLine(
                            new PropDescFields(CHdGd.class).getPropDesc("bh"),
                            getAppBHPre(fswlx) + gd.getBh(),
                            new PropDescFields(CHdGd.class).getPropDesc("xxdz"),
                            gd.getXxdz());
                    result.AddLine(
                            new PropDescFields(CHdGd.class)
                                    .getPropDesc("dbszsd"),
                            StringUtil.getStringIsNullObj(gd.getDbszsd()),
                            new PropDescFields(CHdGd.class).getPropDesc("jsdw"),
                            gd.getJsdw());
                    result.AddLine(
                            new PropDescFields(CHdGd.class).getPropDesc("cyxs"),
                            gd.getCyxs(),
                            new PropDescFields(CHdGd.class).getPropDesc("cssw"),
                            StringUtil.getStringIsNullObj(gd.getCssw()));
                    result.AddLine(
                            new PropDescFields(CHdGd.class).getPropDesc("kj"),
                            StringUtil.getStringIsNullObj(gd.getKj()),
                            new PropDescFields(CHdGd.class).getPropDesc("aqjj"),
                            StringUtil.getStringIsNullObj(gd.getAqjj()));
                    result.AddLine(
                            new PropDescFields(CHdGd.class).getPropDesc("gxgc"),
                            StringUtil.getStringIsNullObj(gd.getGxgc()),
                            new PropDescFields(CHdGd.class).getPropDesc("mssd"),
                            StringUtil.getStringIsNullObj(gd.getMssd()));
                    result.AddLine(new PropDescFields(CHdGd.class)
                                    .getPropDesc("ywbz"),
                            gd.getYwbz() == 1 ? "是" : "否", new PropDescFields(
                                    CHdGd.class).getPropDesc("ywfzss"), gd
                                    .getYwfzss() == 1 ? "是" : "否");
                    result.AddLine(
                            new PropDescFields(CHdGd.class).getPropDesc("ytfl"),
                            StringUtil.getStringIsNullObj(gd.getYtfl()),
                            new PropDescFields(CHdGd.class).getPropDesc("sfdb"),
                            gd.getSfdb() == 1 ? "是" : "否");

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdGd.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(gd.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdGd.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(gd.getSshduanid()).getHdbh());
                    result.AddLine("所属航段名称", gdhduan.getHdqdmc() + "-"
                            + gdhduan.getHdzdmc(), new PropDescFields(
                            CHdGd.class).getPropDesc("zxlczh"), StringUtil.getStringIsNullObj(gd.getZxlczh()));
                    result.AddLine(new PropDescFields(CHdGd.class)
                                    .getPropDesc("jd"), StringUtil.getDoubleNull(gd.getJd()),
                            new PropDescFields(CHdGd.class)
                                    .getPropDesc("wd"), StringUtil.getDoubleNull(gd.getWd()));
                    result.AddLine(new PropDescFields(CHdGd.class)
                                    .getPropDesc("szxzqh"), this.queryStrXzqh(gd
                                    .getSzxzqh()), new PropDescFields(CHdGd.class)
                                    .getPropDesc("sffcfhds"),
                            gd.getSffcfhds() == 1 ? "是" : "否");
                    result.AddLine(
                            new PropDescFields(CHdGd.class).getPropDesc("za"),
                            StringUtil.getStringIsNullObj(gd.getZa()),
                            new PropDescFields(CHdGd.class).getPropDesc("ya"),
                            StringUtil.getStringIsNullObj(gd.getYa()));
                    result.AddLine(new PropDescFields(CHdGd.class)
                                    .getPropDesc("zazbx"), StringUtil.getStringIsNullObj(gd.getZazbx()),
                            new PropDescFields(CHdGd.class)
                                    .getPropDesc("zazby"), StringUtil.getStringIsNullObj(gd.getZazby()));
                    result.AddLine(new PropDescFields(CHdGd.class)
                                    .getPropDesc("yazbx"), StringUtil.getStringIsNullObj(gd.getYazbx()),
                            new PropDescFields(CHdGd.class)
                                    .getPropDesc("yazby"), StringUtil.getStringIsNullObj(gd.getYazby()));

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdGd.class)
                                    .getPropDesc("jcnf"), gd.getJcnf(),
                            new PropDescFields(CHdGd.class)
                                    .getPropDesc("gljgmc"), gd.getGljgmc());
                    result.AddLine(new PropDescFields(CHdGd.class)
                                    .getPropDesc("gljglxr"), gd.getGljglxr(),
                            new PropDescFields(CHdGd.class)
                                    .getPropDesc("gljglxdh"), gd.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdGd.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(gd
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdGd.class).getPropDesc("bz"),
                            gd.getBz() == null ? "" : gd.getBz());
                    result.AddLine(new PropDescFields(CHdGd.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(gd.getCreatetime()),
                            new PropDescFields(CHdGd.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(gd.getUpdatetime()));
                    break;
                case Constants.APP_TUNNEL:
                    CHdSd sd = (CHdSd) records.get(0)[0];
                    if (sd != null)
                        appid = sd.getId();
                    CHdHduanjcxx sdhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(sd);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdSd.class)
                                    .getPropDesc("mc"), sd.getMc(), new PropDescFields(
                                    CHdSd.class).getPropDesc("fswlx"),
                            new PropDescFields(CHdSd.class)
                                    .getPropDesc("sdname"));
                    result.AddLine(new PropDescFields(CHdSd.class)
                            .getPropDesc("bh"), getAppBHPre(fswlx) + sd.getBh(), new PropDescFields(
                            CHdSd.class).getPropDesc("dbszsd"), StringUtil.getStringIsNullObj(sd.getDbszsd()));
                    result.AddLine(new PropDescFields(CHdSd.class)
                                    .getPropDesc("xxdz"), sd.getXxdz(),
                            new PropDescFields(CHdSd.class)
                                    .getPropDesc("ywfzss"),
                            sd.getYwfzss() == 1 ? "是" : "否");
                    result.AddLine(
                            new PropDescFields(CHdSd.class).getPropDesc("ytfl"),
                            sd.getYtfl(),
                            new PropDescFields(CHdSd.class).getPropDesc("sfdb"),
                            sd.getSfdb() == 1 ? "是" : "否");

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdSd.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(sd.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdSd.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(sd.getSshduanid()).getHdbh());
                    result.AddLine("所属航段名称", sdhduan.getHdqdmc() + "-"
                            + sdhduan.getHdzdmc(), new PropDescFields(
                            CHdSd.class).getPropDesc("zxlczh"), sd.getZxlczh());
                    result.AddLine(new PropDescFields(CHdSd.class)
                                    .getPropDesc("jd"), StringUtil.getDoubleNull(sd.getJd()),
                            new PropDescFields(CHdSd.class)
                                    .getPropDesc("wd"), StringUtil.getDoubleNull(sd.getWd()));
                    result.AddLine(new PropDescFields(CHdSd.class)
                                    .getPropDesc("szxzqh"), this.queryStrXzqh(sd
                                    .getSzxzqh()), new PropDescFields(CHdSd.class)
                                    .getPropDesc("sffcfhds"),
                            sd.getSffcfhds() == 1 ? "是" : "否");

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdSd.class)
                                    .getPropDesc("jcnf"), sd.getJcnf(),
                            new PropDescFields(CHdSd.class)
                                    .getPropDesc("gljgmc"), sd.getGljgmc());
                    result.AddLine(new PropDescFields(CHdSd.class)
                                    .getPropDesc("gljglxr"), sd.getGljglxr(),
                            new PropDescFields(CHdSd.class)
                                    .getPropDesc("gljglxdh"), sd.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdSd.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(sd
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdSd.class).getPropDesc("bz"),
                            sd.getBz() == null ? "" : sd.getBz());
                    result.AddLine(new PropDescFields(CHdSd.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(sd.getCreatetime()),
                            new PropDescFields(CHdSd.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(sd.getUpdatetime()));
                    break;
                case Constants.APP_KYDOCK:
                    CHdKymt kymt = (CHdKymt) records.get(0)[0];
                    if (kymt != null)
                        appid = kymt.getId();
                    CHdHduanjcxx kymthduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(kymt);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdKymt.class)
                                    .getPropDesc("mc"), kymt.getMc(), new PropDescFields(
                                    CHdKymt.class).getPropDesc("fswlx"),
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("kymtname"));
                    result.AddLine(
                            new PropDescFields(CHdKymt.class).getPropDesc("bh"),
                            getAppBHPre(fswlx) + kymt.getBh(),
                            new PropDescFields(CHdKymt.class).getPropDesc("sfah"),
                            kymt.getSfah() == 1 ? "是" : "否");

                    result.AddLine(
                            new PropDescFields(CHdKymt.class).getPropDesc("jglx"),
                            this.dicDao.queryAttrDesc(kymt.getJglx()),
                            new PropDescFields(CHdKymt.class).getPropDesc("tkdj"),
                            StringUtil.getStringIsNullObj(kymt.getTkdj()));
                    result.AddLine(
                            new PropDescFields(CHdKymt.class).getPropDesc("lx"),
                            kymt.getLx(),
                            new PropDescFields(CHdKymt.class).getPropDesc("bws"),
                            StringUtil.getStringIsNullObj(kymt.getBws()));
                    result.AddLine(
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("sfwxpmt"),
                            kymt.getSfwxpmt() == 1 ? "是" : "否",
                            new PropDescFields(CHdKymt.class).getPropDesc("yysj"),
                            DateTimeUtil.getTimeFmt(kymt.getYysj()).equals(
                                    Constants.DEFAULT_TIME) ? "" : DateTimeUtil
                                    .getTimeFmt(kymt.getYysj()));
                    result.AddLine(new PropDescFields(CHdKymt.class)
                                    .getPropDesc("hwzl"), kymt.getHwzl(),
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("sfzsyz"),
                            kymt.getSfzsyz() == 1 ? "是" : "否");
                    result.AddLine(new PropDescFields(CHdKymt.class)
                                    .getPropDesc("kbnl"), StringUtil.getStringIsNullObj(kymt.getKbnl()),
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("xlzsl"), StringUtil.getStringIsNullObj(kymt.getXlzsl()));
                    result.AddLine(new PropDescFields(CHdKymt.class)
                                    .getPropDesc("lthxsl"), StringUtil.getStringIsNullObj(kymt.getLthxsl()),
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("gsksl"), StringUtil.getStringIsNullObj(kymt.getGsksl()));
                    result.AddLine(new PropDescFields(CHdKymt.class)
                                    .getPropDesc("zmdsl"), StringUtil.getStringIsNullObj(kymt.getZmdsl()),
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("cdsssl"), StringUtil.getStringIsNullObj(kymt.getCdsssl()));

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdKymt.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(kymt.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(kymt.getSshduanid()).getHdbh());
                    result.AddLine(new PropDescFields(CHdKymt.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(kymt
                            .getSzxzqh()), "所属航段名称", kymthduan.getHdqdmc() + "-"
                            + kymthduan.getHdzdmc());
                    result.AddLine(new PropDescFields(CHdKymt.class)
                                    .getPropDesc("ssqdlczh"), kymt.getSsqdlczh(),
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("zyaxcd"), StringUtil.getStringIsNullObj(kymt.getZyaxcd()));
                    result.AddLine(new PropDescFields(CHdKymt.class)
                                    .getPropDesc("zyaxqdzbx"), StringUtil.getStringIsNullObj(kymt.getZyaxqdzbx()),
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("zyaxqdzby"), StringUtil.getStringIsNullObj(kymt
                                    .getZyaxqdzby()));
                    result.AddLine(new PropDescFields(CHdKymt.class)
                                    .getPropDesc("zyaxzdzbx"), StringUtil.getStringIsNullObj(kymt.getZyaxzdzbx()),
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("zyaxzdzby"), StringUtil.getStringIsNullObj(kymt
                                    .getZyaxzdzby()));
                    result.AddLine(new PropDescFields(CHdKymt.class)
                                    .getPropDesc("jd"), StringUtil.getDoubleNull(kymt.getJd()),
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("wd"), StringUtil.getDoubleNull(kymt.getWd()));
                    result.AddLine(new PropDescFields(CHdKymt.class)
                                    .getPropDesc("ab"), kymt.getAb() > 0 ? this.dicDao.queryAttrDesc(kymt.getAb()) : "",
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("sffcfhds"),
                            kymt.getSffcfhds() == 1 ? "是" : "否");

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdKymt.class)
                            .getPropDesc("gljgmc"), kymt.getGljgmc());
                    result.AddLine(new PropDescFields(CHdKymt.class)
                                    .getPropDesc("gljglxr"), kymt.getGljglxr(),
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("gljglxdh"), kymt.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdKymt.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(kymt
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdKymt.class).getPropDesc("jcnf"),
                            kymt.getJcnf());
                    result.AddLine(
                            new PropDescFields(CHdKymt.class).getPropDesc("bz"),
                            kymt.getBz() == null ? "" : kymt.getBz());
                    result.AddLine(new PropDescFields(CHdKymt.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(kymt.getCreatetime()),
                            new PropDescFields(CHdKymt.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(kymt.getUpdatetime()));
                    break;
                case Constants.APP_HYDOCK:
                    CHdHymt hymt = (CHdHymt) records.get(0)[0];
                    if (hymt != null)
                        appid = hymt.getId();
                    CHdHduanjcxx hymthduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(hymt);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdHymt.class)
                                    .getPropDesc("mc"), hymt.getMc(), new PropDescFields(
                                    CHdHymt.class).getPropDesc("fswlx"),
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("hymtname"));
                    result.AddLine(
                            new PropDescFields(CHdHymt.class).getPropDesc("bh"),
                            getAppBHPre(fswlx) + hymt.getBh(),
                            new PropDescFields(CHdHymt.class).getPropDesc("sfah"),
                            hymt.getSfah() == 1 ? "是" : "否");

                    result.AddLine(
                            new PropDescFields(CHdHymt.class).getPropDesc("jglx"),
                            this.dicDao.queryAttrDesc(hymt.getJglx()),
                            new PropDescFields(CHdHymt.class).getPropDesc("tkdj"),
                            StringUtil.getStringIsNullObj(hymt.getTkdj()));
                    result.AddLine(
                            new PropDescFields(CHdHymt.class).getPropDesc("lx"),
                            hymt.getLx(),
                            new PropDescFields(CHdHymt.class).getPropDesc("bws"),
                            StringUtil.getStringIsNullObj(hymt.getBws()));
                    result.AddLine(
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("sfwxpmt"),
                            hymt.getSfwxpmt() == 1 ? "是" : "否",
                            new PropDescFields(CHdHymt.class).getPropDesc("yysj"),
                            DateTimeUtil.getTimeFmt(hymt.getYysj()).equals(
                                    Constants.DEFAULT_TIME) ? "" : DateTimeUtil
                                    .getTimeFmt(hymt.getYysj()));
                    result.AddLine(new PropDescFields(CHdHymt.class)
                                    .getPropDesc("hwzl"), hymt.getHwzl(),
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("sfzsyz"),
                            hymt.getSfzsyz() == 1 ? "是" : "否");
                    result.AddLine(new PropDescFields(CHdHymt.class)
                                    .getPropDesc("kbnl"), StringUtil.getStringIsNullObj(hymt.getKbnl()),
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("xlzsl"), StringUtil.getStringIsNullObj(hymt.getXlzsl()));
                    result.AddLine(new PropDescFields(CHdHymt.class)
                                    .getPropDesc("lthxsl"), StringUtil.getStringIsNullObj(hymt.getLthxsl()),
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("gsksl"), StringUtil.getStringIsNullObj(hymt.getGsksl()));
                    result.AddLine(new PropDescFields(CHdHymt.class)
                                    .getPropDesc("zmdsl"), StringUtil.getStringIsNullObj(hymt.getZmdsl()),
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("cdsssl"), StringUtil.getStringIsNullObj(hymt.getCdsssl()));

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdHymt.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(hymt.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(hymt.getSshduanid()).getHdbh());
                    result.AddLine(new PropDescFields(CHdHymt.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(hymt
                            .getSzxzqh()), "所属航段名称", hymthduan.getHdqdmc() + "-"
                            + hymthduan.getHdzdmc());
                    result.AddLine(new PropDescFields(CHdHymt.class)
                                    .getPropDesc("ssqdlczh"), hymt.getSsqdlczh(),
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("zyaxcd"), StringUtil.getStringIsNullObj(hymt.getZyaxcd()));
                    result.AddLine(new PropDescFields(CHdHymt.class)
                                    .getPropDesc("zyaxqdzbx"), StringUtil.getStringIsNullObj(hymt.getZyaxqdzbx()),
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("zyaxqdzby"), StringUtil.getStringIsNullObj(hymt
                                    .getZyaxqdzby()));
                    result.AddLine(new PropDescFields(CHdHymt.class)
                                    .getPropDesc("zyaxzdzbx"), StringUtil.getStringIsNullObj(hymt.getZyaxzdzbx()),
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("zyaxzdzby"), StringUtil.getStringIsNullObj(hymt
                                    .getZyaxzdzby()));
                    result.AddLine(new PropDescFields(CHdHymt.class)
                                    .getPropDesc("jd"), StringUtil.getDoubleNull(hymt.getJd()),
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("wd"), StringUtil.getDoubleNull(hymt.getWd()));
                    result.AddLine(new PropDescFields(CHdHymt.class)
                                    .getPropDesc("ab"), hymt.getAb() > 0 ? this.dicDao.queryAttrDesc(hymt.getAb()) : "",
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("sffcfhds"),
                            hymt.getSffcfhds() == 1 ? "是" : "否");

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdHymt.class)
                            .getPropDesc("gljgmc"), hymt.getGljgmc());
                    result.AddLine(new PropDescFields(CHdHymt.class)
                                    .getPropDesc("gljglxr"), hymt.getGljglxr(),
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("gljglxdh"), hymt.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdHymt.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(hymt
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdHymt.class).getPropDesc("jcnf"),
                            hymt.getJcnf());
                    result.AddLine(
                            new PropDescFields(CHdHymt.class).getPropDesc("bz"),
                            hymt.getBz() == null ? "" : hymt.getBz());
                    result.AddLine(new PropDescFields(CHdHymt.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(hymt.getCreatetime()),
                            new PropDescFields(CHdHymt.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(hymt.getUpdatetime()));
                    break;
                case Constants.APP_GWDOCK:
                    CHdGwmt gwmt = (CHdGwmt) records.get(0)[0];
                    if (gwmt != null)
                        appid = gwmt.getId();
                    CHdHduanjcxx gwmthduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(gwmt);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("mc"), gwmt.getMc(), new PropDescFields(
                                    CHdGwmt.class).getPropDesc("fswlx"),
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("gwmtname"));
                    result.AddLine(
                            new PropDescFields(CHdGwmt.class).getPropDesc("bh"),
                            getAppBHPre(fswlx) + gwmt.getBh(),
                            new PropDescFields(CHdGwmt.class).getPropDesc("sfah"),
                            gwmt.getSfah() == 1 ? "是" : "否");

                    result.AddLine(
                            new PropDescFields(CHdGwmt.class).getPropDesc("jglx"),
                            this.dicDao.queryAttrDesc(gwmt.getJglx()),
                            new PropDescFields(CHdGwmt.class).getPropDesc("tkdj"),
                            StringUtil.getStringIsNullObj(gwmt.getTkdj()));
                    result.AddLine(
                            new PropDescFields(CHdGwmt.class).getPropDesc("lx"),
                            gwmt.getLx(),
                            new PropDescFields(CHdGwmt.class).getPropDesc("bws"),
                            StringUtil.getStringIsNullObj(gwmt.getBws()));
                    result.AddLine(
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("sfwxpmt"),
                            gwmt.getSfwxpmt() == 1 ? "是" : "否",
                            new PropDescFields(CHdGwmt.class).getPropDesc("yysj"),
                            DateTimeUtil.getTimeFmt(gwmt.getYysj()).equals(
                                    Constants.DEFAULT_TIME) ? "" : DateTimeUtil
                                    .getTimeFmt(gwmt.getYysj()));
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("hwzl"), gwmt.getHwzl(),
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("sfzsyz"),
                            gwmt.getSfzsyz() == 1 ? "是" : "否");
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("kbnl"), StringUtil.getStringIsNullObj(gwmt.getKbnl()),
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("xlzsl"), StringUtil.getStringIsNullObj(gwmt.getXlzsl()));
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("lthxsl"), StringUtil.getStringIsNullObj(gwmt.getLthxsl()),
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("gsksl"), StringUtil.getStringIsNullObj(gwmt.getGsksl()));
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("zmdsl"), StringUtil.getStringIsNullObj(gwmt.getZmdsl()),
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("cdsssl"), StringUtil.getStringIsNullObj(gwmt.getCdsssl()));

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(gwmt.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(gwmt.getSshduanid()).getHdbh());
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(gwmt
                            .getSzxzqh()), "所属航段名称", gwmthduan.getHdqdmc() + "-"
                            + gwmthduan.getHdzdmc());
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("ssqdlczh"), gwmt.getSsqdlczh(),
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("zyaxcd"), StringUtil.getStringIsNullObj(gwmt.getZyaxcd()));
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("zyaxqdzbx"), StringUtil.getStringIsNullObj(gwmt.getZyaxqdzbx()),
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("zyaxqdzby"), StringUtil.getStringIsNullObj(gwmt
                                    .getZyaxqdzby()));
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("zyaxzdzbx"), StringUtil.getStringIsNullObj(gwmt.getZyaxzdzbx()),
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("zyaxzdzby"), StringUtil.getStringIsNullObj(gwmt
                                    .getZyaxzdzby()));
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("jd"), StringUtil.getDoubleNull(gwmt.getJd()),
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("wd"), StringUtil.getDoubleNull(gwmt.getWd()));
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("ab"), gwmt.getAb() > 0 ? this.dicDao.queryAttrDesc(gwmt.getAb()) : "",
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("sffcfhds"),
                            gwmt.getSffcfhds() == 1 ? "是" : "否");

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                            .getPropDesc("gljgmc"), gwmt.getGljgmc());
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("gljglxr"), gwmt.getGljglxr(),
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("gljglxdh"), gwmt.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(gwmt
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdGwmt.class).getPropDesc("jcnf"),
                            gwmt.getJcnf());
                    result.AddLine(
                            new PropDescFields(CHdGwmt.class).getPropDesc("bz"),
                            gwmt.getBz() == null ? "" : gwmt.getBz());
                    result.AddLine(new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(gwmt.getCreatetime()),
                            new PropDescFields(CHdGwmt.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(gwmt.getUpdatetime()));
                    break;
                case Constants.APP_SHIPYARD:
                    CHdCc cc = (CHdCc) records.get(0)[0];
                    if (cc != null)
                        appid = cc.getId();
                    CHdHduanjcxx cchduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(cc);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdCc.class)
                                    .getPropDesc("mc"), cc.getMc(), new PropDescFields(
                                    CHdCc.class).getPropDesc("fswlx"),
                            new PropDescFields(CHdCc.class)
                                    .getPropDesc("ccname"));
                    result.AddLine(
                            new PropDescFields(CHdCc.class).getPropDesc("bh"),
                            getAppBHPre(fswlx) + cc.getBh(),
                            new PropDescFields(CHdCc.class).getPropDesc("sfah"),
                            cc.getSfah() == 1 ? "是" : "否");
                    result.AddLine(new PropDescFields(CHdCc.class)
                                    .getPropDesc("xzcwj"), StringUtil.getStringIsNullObj(cc.getXzcwj()),
                            new PropDescFields(CHdCc.class)
                                    .getPropDesc("xshdqyjhdzxxjl"), StringUtil.getStringIsNullObj(cc
                                    .getXshdqyjhdzxxjl()));

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdCc.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(cc.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdCc.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(cc.getSshduanid()).getHdbh());
                    result.AddLine(new PropDescFields(CHdCc.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(cc
                            .getSzxzqh()), "所属航段名称", cchduan.getHdqdmc() + "-"
                            + cchduan.getHdzdmc());
                    result.AddLine(new PropDescFields(CHdCc.class)
                                    .getPropDesc("ssqdlczh"), cc.getSsqdlczh(),
                            new PropDescFields(CHdCc.class)
                                    .getPropDesc("zyaxcd"), StringUtil.getStringIsNullObj(cc.getZyaxcd()));
                    result.AddLine(new PropDescFields(CHdCc.class)
                                    .getPropDesc("zyaxqdzbx"), StringUtil.getStringIsNullObj(cc.getZyaxqdzbx()),
                            new PropDescFields(CHdCc.class)
                                    .getPropDesc("zyaxqdzby"), StringUtil.getStringIsNullObj(cc
                                    .getZyaxqdzby()));
                    result.AddLine(new PropDescFields(CHdCc.class)
                                    .getPropDesc("zyaxzdzbx"), StringUtil.getStringIsNullObj(cc.getZyaxzdzbx()),
                            new PropDescFields(CHdCc.class)
                                    .getPropDesc("zyaxzdzby"), StringUtil.getStringIsNullObj(cc
                                    .getZyaxzdzby()));
                    result.AddLine(new PropDescFields(CHdCc.class)
                                    .getPropDesc("ab"), cc.getAb() > 0 ? this.dicDao.queryAttrDesc(cc.getAb()) : "",
                            new PropDescFields(CHdCc.class)
                                    .getPropDesc("sffcfhds"),
                            cc.getSffcfhds() == 1 ? "是" : "否");

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdCc.class)
                            .getPropDesc("gljgmc"), cc.getGljgmc());
                    result.AddLine(new PropDescFields(CHdCc.class)
                                    .getPropDesc("gljglxr"), cc.getGljglxr(),
                            new PropDescFields(CHdCc.class)
                                    .getPropDesc("gljglxdh"), cc.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdCc.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(cc
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdCc.class).getPropDesc("jcnf"),
                            cc.getJcnf());
                    result.AddLine(
                            new PropDescFields(CHdCc.class).getPropDesc("bz"),
                            cc.getBz() == null ? "" : cc.getBz());
                    result.AddLine(new PropDescFields(CHdCc.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(cc.getCreatetime()),
                            new PropDescFields(CHdCc.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(cc.getUpdatetime()));
                    break;
                case Constants.APP_TAKEOUTFALL:
                    CHdQpsk qpsk = (CHdQpsk) records.get(0)[0];
                    if (qpsk != null)
                        appid = qpsk.getId();
                    CHdHduanjcxx qpskhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(qpsk);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("mc"), qpsk.getMc(),
                            new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("fswlx"), new PropDescFields(
                                    CHdQpsk.class).getPropDesc("qpskname"));
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("bh"), getAppBHPre(fswlx) + qpsk.getBh(),
                            new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("sfah"),
                            qpsk.getSfah() == 1 ? "是" : "否");
                    result.AddLine(
                            new PropDescFields(CHdQpsk.class).getPropDesc("lx"),
                            this.dicDao.queryAttrDesc(qpsk.getLx()),
                            new PropDescFields(CHdQpsk.class).getPropDesc("ll"),
                            StringUtil.getStringIsNullObj(qpsk.getLl()));
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("ls"), StringUtil.getStringIsNullObj(qpsk.getLs()),
                            new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("jhdzxxjl"), StringUtil.getStringIsNullObj(qpsk
                                    .getJhdzxxjl()));
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("gc"), StringUtil.getStringIsNullObj(qpsk.getGc()),
                            new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("zlx"),
                            qpsk.getZlx() == 1 ? "临时" : "连续");

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(qpsk.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(qpsk.getSshduanid()).getHdbh());
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(qpsk
                            .getSzxzqh()), "所属航段名称", qpskhduan.getHdqdmc()
                            + "-" + qpskhduan.getHdzdmc());
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("ssqdlczh"), qpsk.getSsqdlczh(),
                            new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("zyaxcd"), StringUtil.getStringIsNullObj(qpsk.getZyaxcd()));
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("zyaxqdzbx"), StringUtil.getStringIsNullObj(qpsk.getZyaxqdzbx()),
                            new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("zyaxqdzby"), StringUtil.getStringIsNullObj(qpsk
                                    .getZyaxqdzby()));
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("zyaxzdzbx"), StringUtil.getStringIsNullObj(qpsk.getZyaxzdzbx()),
                            new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("zyaxzdzby"), StringUtil.getStringIsNullObj(qpsk
                                    .getZyaxzdzby()));
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("ab"), qpsk.getAb() > 0 ? this.dicDao.queryAttrDesc(qpsk.getAb()) : "",
                            new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("sffcfhds"), qpsk
                                    .getSffcfhds() == 1 ? "是" : "否");

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                            .getPropDesc("gljgmc"), qpsk.getGljgmc());
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("gljglxr"), qpsk.getGljglxr(),
                            new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("gljglxdh"), qpsk
                                    .getGljglxdh());
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(qpsk
                            .getGljgszxzqh()));
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                            .getPropDesc("jcnf"), qpsk.getJcnf());
                    result.AddLine(
                            new PropDescFields(CHdQpsk.class).getPropDesc("bz"),
                            qpsk.getBz() == null ? "" : qpsk.getBz());
                    result.AddLine(new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(qpsk.getCreatetime()),
                            new PropDescFields(CHdQpsk.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(qpsk.getUpdatetime()));
                    break;
                case Constants.APP_HYDROLOGICALSTATION:
                    CHdSwz swz = (CHdSwz) records.get(0)[0];
                    if (swz != null)
                        appid = swz.getId();
                    CHdHduanjcxx swzhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(swz);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdSwz.class)
                                    .getPropDesc("mc"), swz.getMc(),
                            new PropDescFields(CHdSwz.class)
                                    .getPropDesc("fswlx"), new PropDescFields(
                                    CHdSwz.class).getPropDesc("swzname"));
                    result.AddLine(new PropDescFields(CHdSwz.class)
                                    .getPropDesc("bh"), getAppBHPre(fswlx) + swz.getBh(),
                            new PropDescFields(CHdSwz.class)
                                    .getPropDesc("sfah"),
                            swz.getSfah() == 1 ? "是" : "否");

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdSwz.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(swz.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdSwz.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(swz.getSshduanid()).getHdbh());
                    result.AddLine(new PropDescFields(CHdSwz.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(swz
                            .getSzxzqh()), "所属航段名称", swzhduan.getHdqdmc() + "-"
                            + swzhduan.getHdzdmc());
                    result.AddLine(new PropDescFields(CHdSwz.class)
                                    .getPropDesc("ssqdlczh"), swz.getSsqdlczh(),
                            new PropDescFields(CHdSwz.class)
                                    .getPropDesc("zyaxcd"), StringUtil.getStringIsNullObj(swz.getZyaxcd()));
                    result.AddLine(new PropDescFields(CHdSwz.class)
                                    .getPropDesc("zyaxqdzbx"), StringUtil.getStringIsNullObj(swz.getZyaxqdzbx()),
                            new PropDescFields(CHdSwz.class)
                                    .getPropDesc("zyaxqdzby"), StringUtil.getStringIsNullObj(swz
                                    .getZyaxqdzby()));
                    result.AddLine(new PropDescFields(CHdSwz.class)
                                    .getPropDesc("zyaxzdzbx"), StringUtil.getStringIsNullObj(swz.getZyaxzdzbx()),
                            new PropDescFields(CHdSwz.class)
                                    .getPropDesc("zyaxzdzby"), StringUtil.getStringIsNullObj(swz
                                    .getZyaxzdzby()));
                    result.AddLine(new PropDescFields(CHdSwz.class)
                                    .getPropDesc("ab"), swz.getAb() > 0 ? this.dicDao.queryAttrDesc(swz.getAb()) : "",
                            new PropDescFields(CHdSwz.class)
                                    .getPropDesc("sffcfhds"),
                            swz.getSffcfhds() == 1 ? "是" : "否");

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdSwz.class)
                            .getPropDesc("gljgmc"), swz.getGljgmc());
                    result.AddLine(new PropDescFields(CHdSwz.class)
                                    .getPropDesc("gljglxr"), swz.getGljglxr(),
                            new PropDescFields(CHdSwz.class)
                                    .getPropDesc("gljglxdh"), swz.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdSwz.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(swz
                            .getGljgszxzqh()));
                    result.AddLine(new PropDescFields(CHdSwz.class)
                            .getPropDesc("jcnf"), swz.getJcnf());
                    result.AddLine(
                            new PropDescFields(CHdSwz.class).getPropDesc("bz"),
                            swz.getBz() == null ? "" : swz.getBz());
                    result.AddLine(new PropDescFields(CHdSwz.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(swz.getCreatetime()),
                            new PropDescFields(CHdSwz.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(swz.getUpdatetime()));
                    break;

                case Constants.APP_MANAGEMENTSTATION:
                    CHdGlz glz = (CHdGlz) records.get(0)[0];
                    if (glz != null)
                        appid = glz.getId();
                    CHdHduanjcxx glzhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(glz);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdGlz.class)
                                    .getPropDesc("mc"), glz.getMc(),
                            new PropDescFields(CHdGlz.class)
                                    .getPropDesc("fswlx"), new PropDescFields(
                                    CHdGlz.class).getPropDesc("glzname"));
                    result.AddLine(new PropDescFields(CHdGlz.class)
                                    .getPropDesc("bh"), getAppBHPre(fswlx) + glz.getBh(),
                            new PropDescFields(CHdGlz.class)
                                    .getPropDesc("sfah"),
                            glz.getSfah() == 1 ? "是" : "否");

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdGlz.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(glz.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdGlz.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(glz.getSshduanid()).getHdbh());
                    result.AddLine(new PropDescFields(CHdGlz.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(glz
                            .getSzxzqh()), "所属航段名称", glzhduan.getHdqdmc() + "-"
                            + glzhduan.getHdzdmc());
                    result.AddLine(new PropDescFields(CHdGlz.class)
                                    .getPropDesc("ssqdlczh"), glz.getSsqdlczh(),
                            new PropDescFields(CHdGlz.class)
                                    .getPropDesc("zyaxcd"), StringUtil.getStringIsNullObj(glz.getZyaxcd()));
                    result.AddLine(new PropDescFields(CHdGlz.class)
                                    .getPropDesc("zyaxqdzbx"), StringUtil.getStringIsNullObj(glz.getZyaxqdzbx()),
                            new PropDescFields(CHdGlz.class)
                                    .getPropDesc("zyaxqdzby"), StringUtil.getStringIsNullObj(glz
                                    .getZyaxqdzby()));
                    result.AddLine(new PropDescFields(CHdGlz.class)
                                    .getPropDesc("zyaxzdzbx"), StringUtil.getStringIsNullObj(glz.getZyaxzdzbx()),
                            new PropDescFields(CHdGlz.class)
                                    .getPropDesc("zyaxzdzby"), StringUtil.getStringIsNullObj(glz
                                    .getZyaxzdzby()));
                    result.AddLine(new PropDescFields(CHdGlz.class)
                                    .getPropDesc("ab"), glz.getAb() > 0 ? this.dicDao.queryAttrDesc(glz.getAb()) : "",
                            new PropDescFields(CHdGlz.class)
                                    .getPropDesc("sffcfhds"),
                            glz.getSffcfhds() == 1 ? "是" : "否");

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdGlz.class)
                            .getPropDesc("gljgmc"), glz.getGljgmc());
                    result.AddLine(new PropDescFields(CHdGlz.class)
                                    .getPropDesc("gljglxr"), glz.getGljglxr(),
                            new PropDescFields(CHdGlz.class)
                                    .getPropDesc("gljglxdh"), glz.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdGlz.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(glz
                            .getGljgszxzqh()));
                    result.AddLine(new PropDescFields(CHdGlz.class)
                            .getPropDesc("jcnf"), glz.getJcnf());
                    result.AddLine(
                            new PropDescFields(CHdGlz.class).getPropDesc("bz"),
                            glz.getBz() == null ? "" : glz.getBz());
                    result.AddLine(new PropDescFields(CHdGlz.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(glz.getCreatetime()),
                            new PropDescFields(CHdGlz.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(glz.getUpdatetime()));
                    break;
                case Constants.APP_SERVICEAREA:
                    CHdFwq fwq = (CHdFwq) records.get(0)[0];
                    if (fwq != null)
                        appid = fwq.getId();
                    CHdHduanjcxx fwqhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(fwq);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdFwq.class)
                                    .getPropDesc("mc"), fwq.getMc(),
                            new PropDescFields(CHdFwq.class)
                                    .getPropDesc("fswlx"), new PropDescFields(
                                    CHdFwq.class).getPropDesc("fwqname"));
                    result.AddLine(
                            new PropDescFields(CHdFwq.class).getPropDesc("lx"),
                            fwq.getLx(),
                            new PropDescFields(CHdFwq.class).getPropDesc("bh"),
                            getAppBHPre(fswlx) + fwq.getBh());
                    result.AddLine(new PropDescFields(CHdFwq.class)
                            .getPropDesc("sfah"), fwq.getSfah() == 1 ? "是"
                            : "否", new PropDescFields(CHdFwq.class)
                            .getPropDesc("kbnl"), StringUtil.getStringIsNullObj(fwq.getKbnl()));
                    result.AddLine(new PropDescFields(CHdFwq.class)
                                    .getPropDesc("xlzsl"), StringUtil.getStringIsNullObj(fwq.getXlzsl()),
                            new PropDescFields(CHdFwq.class)
                                    .getPropDesc("lthxsl"), StringUtil.getStringIsNullObj(fwq.getLthxsl()));
                    result.AddLine(new PropDescFields(CHdFwq.class)
                                    .getPropDesc("gsksl"), StringUtil.getStringIsNullObj(fwq.getGsksl()),
                            new PropDescFields(CHdFwq.class)
                                    .getPropDesc("zmdsl"), StringUtil.getStringIsNullObj(fwq.getZmdsl()));
                    result.AddLine(new PropDescFields(CHdFwq.class)
                            .getPropDesc("cdsssl"), StringUtil.getStringIsNullObj(fwq.getCdsssl()));

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdFwq.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(fwq.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdFwq.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(fwq.getSshduanid()).getHdbh());
                    result.AddLine(new PropDescFields(CHdFwq.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(fwq
                            .getSzxzqh()), "所属航段名称", fwqhduan.getHdqdmc() + "-"
                            + fwqhduan.getHdzdmc());
                    result.AddLine(new PropDescFields(CHdFwq.class)
                                    .getPropDesc("ssqdlczh"), fwq.getSsqdlczh(),
                            new PropDescFields(CHdFwq.class)
                                    .getPropDesc("zyaxcd"), StringUtil.getStringIsNullObj(fwq.getZyaxcd()));
                    result.AddLine(new PropDescFields(CHdFwq.class)
                                    .getPropDesc("zyaxqdzbx"), StringUtil.getStringIsNullObj(fwq.getZyaxqdzbx()),
                            new PropDescFields(CHdFwq.class)
                                    .getPropDesc("zyaxqdzby"), StringUtil.getStringIsNullObj(fwq
                                    .getZyaxqdzby()));
                    result.AddLine(new PropDescFields(CHdFwq.class)
                                    .getPropDesc("zyaxzdzbx"), StringUtil.getStringIsNullObj(fwq.getZyaxzdzbx()),
                            new PropDescFields(CHdFwq.class)
                                    .getPropDesc("zyaxzdzby"), StringUtil.getStringIsNullObj(fwq
                                    .getZyaxzdzby()));
                    result.AddLine(new PropDescFields(CHdFwq.class)
                                    .getPropDesc("ab"), fwq.getAb() > 0 ? this.dicDao.queryAttrDesc(fwq.getAb()) : "",
                            new PropDescFields(CHdFwq.class)
                                    .getPropDesc("sffcfhds"),
                            fwq.getSffcfhds() == 1 ? "是" : "否");
                    result.AddLine(new PropDescFields(CHdFwq.class)
                                    .getPropDesc("jd"), fwq.getJd(),
                            new PropDescFields(CHdFwq.class)
                                    .getPropDesc("wd"), fwq.getWd());
                    result.AddLine(
                            new PropDescFields(CHdFwq.class).getPropDesc("bws"),
                            fwq.getBws(), new PropDescFields(CHdFwq.class)
                                    .getPropDesc("jcnf"), fwq.getJcnf());

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdFwq.class)
                            .getPropDesc("gljgmc"), fwq.getGljgmc());
                    result.AddLine(new PropDescFields(CHdFwq.class)
                                    .getPropDesc("gljglxr"), fwq.getGljglxr(),
                            new PropDescFields(CHdFwq.class)
                                    .getPropDesc("gljglxdh"), fwq.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdFwq.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(fwq
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdFwq.class).getPropDesc("bz"),
                            fwq.getBz() == null ? "" : fwq.getBz());
                    result.AddLine(new PropDescFields(CHdFwq.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(fwq.getCreatetime()),
                            new PropDescFields(CHdFwq.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(fwq.getUpdatetime()));
                    break;
                case Constants.APP_MOORINGAREA:
                    CHdMbq mbq = (CHdMbq) records.get(0)[0];
                    if (mbq != null)
                        appid = mbq.getId();
                    CHdHduanjcxx mbqhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(mbq);
                    result.AddLine("基本信息");
                    result.AddLine(
                            new PropDescFields(CHdMbq.class).getPropDesc("bh"),
                            getAppBHPre(fswlx) + mbq.getBh(),
                            new PropDescFields(CHdMbq.class).getPropDesc("mc"),
                            mbq.getMc());
                    result.AddLine(new PropDescFields(CHdMbq.class)
                                    .getPropDesc("lx"), mbq.getLx(),
                            new PropDescFields(CHdMbq.class)
                                    .getPropDesc("fswlx"), new PropDescFields(
                                    CHdMbq.class).getPropDesc("mbqname"));
                    result.AddLine(new PropDescFields(CHdMbq.class)
                            .getPropDesc("sfah"), mbq.getSfah() == 1 ? "是"
                            : "否", new PropDescFields(CHdMbq.class)
                            .getPropDesc("kbnl"), StringUtil.getStringIsNullObj(mbq.getKbnl()));
                    result.AddLine(new PropDescFields(CHdMbq.class)
                                    .getPropDesc("xlzsl"), StringUtil.getStringIsNullObj(mbq.getXlzsl()),
                            new PropDescFields(CHdMbq.class)
                                    .getPropDesc("lthxsl"), StringUtil.getStringIsNullObj(mbq.getLthxsl()));
                    result.AddLine(new PropDescFields(CHdMbq.class)
                                    .getPropDesc("gsksl"), StringUtil.getStringIsNullObj(mbq.getGsksl()),
                            new PropDescFields(CHdMbq.class)
                                    .getPropDesc("zmdsl"), StringUtil.getStringIsNullObj(mbq.getZmdsl()));
                    result.AddLine(new PropDescFields(CHdMbq.class)
                            .getPropDesc("cdsssl"), StringUtil.getStringIsNullObj(mbq.getCdsssl()));

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdMbq.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(mbq.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdMbq.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(mbq.getSshduanid()).getHdbh());
                    result.AddLine(new PropDescFields(CHdMbq.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(mbq
                            .getSzxzqh()), "所属航段名称", mbqhduan.getHdqdmc() + "-"
                            + mbqhduan.getHdzdmc());
                    result.AddLine(new PropDescFields(CHdMbq.class)
                                    .getPropDesc("ssqdlczh"), mbq.getSsqdlczh(),
                            new PropDescFields(CHdMbq.class)
                                    .getPropDesc("zyaxcd"), StringUtil.getStringIsNullObj(mbq.getZyaxcd()));
                    result.AddLine(new PropDescFields(CHdMbq.class)
                                    .getPropDesc("zyaxqdzbx"), StringUtil.getStringIsNullObj(mbq.getZyaxqdzbx()),
                            new PropDescFields(CHdMbq.class)
                                    .getPropDesc("zyaxqdzby"), StringUtil.getStringIsNullObj(mbq
                                    .getZyaxqdzby()));
                    result.AddLine(new PropDescFields(CHdMbq.class)
                                    .getPropDesc("zyaxzdzbx"), StringUtil.getStringIsNullObj(mbq.getZyaxzdzbx()),
                            new PropDescFields(CHdMbq.class)
                                    .getPropDesc("zyaxzdzby"), StringUtil.getStringIsNullObj(mbq
                                    .getZyaxzdzby()));
                    result.AddLine(new PropDescFields(CHdMbq.class)
                                    .getPropDesc("ab"), mbq.getAb() > 0 ? this.dicDao.queryAttrDesc(mbq.getAb()) : "",
                            new PropDescFields(CHdMbq.class)
                                    .getPropDesc("sffcfhds"),
                            mbq.getSffcfhds() == 1 ? "是" : "否");
                    result.AddLine(new PropDescFields(CHdMbq.class)
                                    .getPropDesc("jd"), mbq.getJd(),
                            new PropDescFields(CHdMbq.class)
                                    .getPropDesc("wd"), mbq.getWd());
                    result.AddLine(
                            new PropDescFields(CHdMbq.class).getPropDesc("bws"),
                            mbq.getBws(), new PropDescFields(CHdMbq.class)
                                    .getPropDesc("jcnf"), mbq.getJcnf());

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdMbq.class)
                            .getPropDesc("gljgmc"), mbq.getGljgmc());
                    result.AddLine(new PropDescFields(CHdMbq.class)
                                    .getPropDesc("gljglxr"), mbq.getGljglxr(),
                            new PropDescFields(CHdMbq.class)
                                    .getPropDesc("gljglxdh"), mbq.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdMbq.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(mbq
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdMbq.class).getPropDesc("bz"),
                            mbq.getBz() == null ? "" : mbq.getBz());
                    result.AddLine(new PropDescFields(CHdMbq.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(mbq.getCreatetime()),
                            new PropDescFields(CHdMbq.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(mbq.getUpdatetime()));
                    break;
                case Constants.APP_HUB:
                    CHdSn sn = (CHdSn) records.get(0)[0];
                    if (sn != null)
                        appid = sn.getId();
                    CHdHduanjcxx snhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(sn);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("mc"), sn.getMc(), new PropDescFields(
                                    CHdSn.class).getPropDesc("fswlx"),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("snname"));
                    result.AddLine(
                            new PropDescFields(CHdSn.class).getPropDesc("bh"),
                            getAppBHPre(fswlx) + sn.getBh(),
                            new PropDescFields(CHdSn.class).getPropDesc("sjsj"),
                            StringUtil.getIsNull(sn.getSjsj()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("sjzgthswhslc"), StringUtil.getIsNull(sn.getSjzgthswhslc()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("sjzdthswhslc"), StringUtil.getIsNull(sn
                                    .getSjzdthswhslc()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("sfjg"),
                            sn.getSfjg() == 1 ? "是" : "否", new PropDescFields(
                                    CHdSn.class).getPropDesc("thlx"),
                            this.dicDao.queryAttrDesc(sn.getThlx()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("thcbdj"), sn.getThcbdj(),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("sfjyql"),
                            sn.getSfjyql() == 1 ? "是" : "否");
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("sjndxhwtgl"), StringUtil.getIsNull(sn.getSjndxhwtgl()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("sjndxlktgl"), StringUtil.getIsNull(sn
                                    .getSjndxlktgl()));
                    result.AddLine(
                            new PropDescFields(CHdSn.class).getPropDesc("thjg"),
                            StringUtil.getIsNull(sn.getThjg()),
                            new PropDescFields(CHdSn.class).getPropDesc("syqk"),
                            sn.getSyqk() == 1 ? "正常使用" : "不能正常使用");
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("tgnlsfpp"),
                            sn.getTgnlsfpp() == 1 ? "是" : "否");
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("dnsxhwtgl"), StringUtil.getIsNull(sn.getDnsxhwtgl()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("dnxxhwtgl"), StringUtil.getIsNull(sn
                                    .getDnxxhwtgl()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("dnsxlktgl"), StringUtil.getIsNull(sn.getDnsxlktgl()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("dnxxlktgl"), StringUtil.getIsNull(sn
                                    .getDnxxlktgl()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("syyhdcd"), StringUtil.getIsNull(sn.getSyyhdcd()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("xyyhdcd"), StringUtil.getIsNull(sn.getXyyhdcd()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("sysjzgthsw"), StringUtil.getIsNull(sn.getSysjzgthsw()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("sysjzdthsw"), StringUtil.getIsNull(sn
                                    .getSysjzdthsw()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("xysjzgthsw"), StringUtil.getIsNull(sn.getXysjzgthsw()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("xysjzdthsw"), StringUtil.getIsNull(sn
                                    .getXysjzdthsw()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                            .getPropDesc("scjsfjg"), sn.getScjsfjg() == 1 ? "是"
                            : "否", new PropDescFields(CHdSn.class)
                            .getPropDesc("czsfjg"), sn.getCzsfjg() == 1 ? "是"
                            : "否");

                    result.AddLine("升船机有效尺度");
                    result.AddLine(
                            new PropDescFields(CHdSn.class).getPropDesc("xs"),
                            sn.getXs() == null ? "" : this.dicDao.queryAttrDesc(sn.getXs()),
                            new PropDescFields(CHdSn.class).getPropDesc("xjc"),
                            StringUtil.getIsNull(sn.getXjc()));
                    result.AddLine(
                            new PropDescFields(CHdSn.class).getPropDesc("kd"),
                            StringUtil.getIsNull(sn.getKd()),
                            new PropDescFields(CHdSn.class).getPropDesc("ss"),
                            StringUtil.getIsNull(sn.getSs()));

                    result.AddLine("船闸有效尺度");
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("yxczcd"), StringUtil.getIsNull(sn.getYxczcd()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("yxczkd"), StringUtil.getIsNull(sn.getYxczkd()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("yxczzskmkd"), StringUtil.getIsNull(sn.getYxczzskmkd()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("yxczzkss"), StringUtil.getIsNull(sn.getYxczzkss()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("exczcd"), StringUtil.getIsNull(sn.getExczcd()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("exczkd"), StringUtil.getIsNull(sn.getExczkd()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("exczzskmkd"), StringUtil.getIsNull(sn.getExczzskmkd()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("exczzkss"), StringUtil.getIsNull(sn.getExczzkss()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("ylczcd"), StringUtil.getIsNull(sn.getYlczcd()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("ylczkd"), StringUtil.getIsNull(sn.getYlczkd()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("ylczzskmkd"), StringUtil.getIsNull(sn.getYlczzskmkd()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("ylczzkss"), StringUtil.getIsNull(sn.getYlczzkss()));

                    result.AddLine("常开节制闸有效尺度");
                    result.AddLine(
                            new PropDescFields(CHdSn.class).getPropDesc("zmkd"),
                            sn.getZmkd(),
                            new PropDescFields(CHdSn.class).getPropDesc("mkss"),
                            sn.getMkss());
                    result.AddLine(
                            new PropDescFields(CHdSn.class).getPropDesc("ks"),
                            StringUtil.getIsNull(sn.getKs()),
                            new PropDescFields(CHdSn.class).getPropDesc("jg"),
                            sn.getJg());

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(sn.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(sn.getSshduanid()).getHdbh());
                    result.AddLine("所属航段名称", snhduan.getHdqdmc() + "-"
                            + snhduan.getHdzdmc(), new PropDescFields(
                            CHdSn.class).getPropDesc("zxlczh"), StringUtil.getIsNull(sn.getZxlczh()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("jd"), StringUtil.getDoubleNull(sn.getJd()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("wd"), StringUtil.getDoubleNull(sn.getWd()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(sn
                            .getSzxzqh()), new PropDescFields(CHdSn.class)
                            .getPropDesc("gcsswz"), sn.getGcsswz() == null ? ""
                            : this.dicDao.queryAttrDesc(sn.getGcsswz()));

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("jcnf"), sn.getJcnf(),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("gljgmc"), sn.getGljgmc());
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("gljglxr"), StringUtil.getIsNull(sn.getGljglxr()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("gljglxdh"), StringUtil.getIsNull(sn.getGljglxdh()));
                    result.AddLine(new PropDescFields(CHdSn.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(sn
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdSn.class).getPropDesc("bz"),
                            sn.getBz() == null ? "" : sn.getBz());
                    result.AddLine(new PropDescFields(CHdSn.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(sn.getCreatetime()),
                            new PropDescFields(CHdSn.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(sn.getUpdatetime()));
                    break;
                case Constants.APP_DAM:
                    CHdB b = (CHdB) records.get(0)[0];
                    if (b != null)
                        appid = b.getId();
                    CHdHduanjcxx bhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(b);
                    result.AddLine("基本信息");
                    result.AddLine(
                            new PropDescFields(CHdB.class).getPropDesc("mc"),
                            b.getMc(),
                            new PropDescFields(CHdB.class).getPropDesc("fswlx"),
                            new PropDescFields(CHdB.class).getPropDesc("bname"));
                    result.AddLine(
                            new PropDescFields(CHdB.class).getPropDesc("bh"),
                            getAppBHPre(fswlx) + b.getBh());
                    result.AddLine(new PropDescFields(CHdB.class)
                            .getPropDesc("lx"), this.dicDao.queryAttrDesc(b
                            .getLx()), new PropDescFields(CHdB.class)
                            .getPropDesc("jszkdj"), this.dicDao.queryAttrDesc(b
                            .getJszkdj()));
                    result.AddLine(
                            new PropDescFields(CHdB.class).getPropDesc("jg"),
                            b.getJg(),
                            new PropDescFields(CHdB.class).getPropDesc("dgc"),
                            b.getDgc());
                    result.AddLine(
                            new PropDescFields(CHdB.class)
                                    .getPropDesc("jszkdjpdsj"),
                            DateTimeUtil.getTimeFmt(b.getJszkdjpdsj()).equals(
                                    Constants.DEFAULT_TIME) ? "" : DateTimeUtil
                                    .getTimeFmt(b.getJszkdjpdsj()),
                            new PropDescFields(CHdB.class).getPropDesc("cd"), StringUtil.getStringIsNullObj(b
                                    .getCd()));

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdB.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(b.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdB.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(b.getSshduanid()).getHdbh());
                    result.AddLine("所属航段名称",
                            bhduan.getHdqdmc() + "-" + bhduan.getHdzdmc(),
                            new PropDescFields(CHdB.class).getPropDesc("ab"),
                            b.getAb() > 0 ? this.dicDao.queryAttrDesc(b.getAb()) : "");
                    result.AddLine(
                            new PropDescFields(CHdB.class).getPropDesc("qdzbx"),
                            StringUtil.getStringIsNullObj(b.getQdzbx()),
                            new PropDescFields(CHdB.class).getPropDesc("qdzby"),
                            StringUtil.getStringIsNullObj(b.getQdzby()));
                    result.AddLine(new PropDescFields(CHdB.class)
                                    .getPropDesc("zzdzbx"), StringUtil.getStringIsNullObj(b.getZzdzbx()),
                            new PropDescFields(CHdB.class)
                                    .getPropDesc("zzdzby"), StringUtil.getStringIsNullObj(b.getZzdzby()));
                    result.AddLine(
                            new PropDescFields(CHdB.class).getPropDesc("zdzbx"),
                            StringUtil.getStringIsNullObj(b.getZdzbx()),
                            new PropDescFields(CHdB.class).getPropDesc("zdzby"),
                            StringUtil.getStringIsNullObj(b.getZdzbx()));
                    result.AddLine(new PropDescFields(CHdB.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(b
                            .getSzxzqh()), new PropDescFields(CHdB.class)
                            .getPropDesc("ssqdlczh"), b.getSsqdlczh());
                    result.AddLine(new PropDescFields(CHdB.class)
                                    .getPropDesc("sffcfhds"),
                            b.getSffcfhds() == 1 ? "是" : "否");

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdB.class)
                                    .getPropDesc("jcnf"), b.getJcnf(),
                            new PropDescFields(CHdB.class)
                                    .getPropDesc("gljgmc"), b.getGljgmc());
                    result.AddLine(new PropDescFields(CHdB.class)
                                    .getPropDesc("gljglxr"), b.getGljglxr(),
                            new PropDescFields(CHdB.class)
                                    .getPropDesc("gljglxdh"), b.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdB.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(b
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdB.class).getPropDesc("bz"),
                            b.getBz() == null ? "" : b.getBz());
                    result.AddLine(new PropDescFields(CHdB.class)
                            .getPropDesc("createtime"), DateTimeUtil
                            .getTimeFmt(b.getCreatetime()), new PropDescFields(
                            CHdB.class).getPropDesc("updatetime"), DateTimeUtil
                            .getTimeFmt(b.getUpdatetime()));
                    break;
                case Constants.APP_REGULATIONREVEMENT:
                    CHdZzha zzha = (CHdZzha) records.get(0)[0];
                    if (zzha != null)
                        appid = zzha.getId();
                    CHdHduanjcxx zzhahduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(zzha);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdZzha.class)
                                    .getPropDesc("mc"), zzha.getMc(),
                            new PropDescFields(CHdZzha.class)
                                    .getPropDesc("fswlx"), new PropDescFields(
                                    CHdZzha.class).getPropDesc("zzhaname"));
                    result.AddLine(
                            new PropDescFields(CHdZzha.class).getPropDesc("bh"),
                            getAppBHPre(fswlx) + zzha.getBh(),
                            new PropDescFields(CHdZzha.class).getPropDesc("lx"),
                            StringUtil.getStringIsNullObj(zzha.getLx()));
                    result.AddLine(
                            new PropDescFields(CHdZzha.class)
                                    .getPropDesc("jszkdj"),
                            this.dicDao.queryAttrDesc(zzha.getJszkdj()),
                            new PropDescFields(CHdZzha.class)
                                    .getPropDesc("jszkdjpdsj"),
                            DateTimeUtil.getTimeFmt(zzha.getJszkdjpdsj())
                                    .equals(Constants.DEFAULT_TIME) ? ""
                                    : DateTimeUtil.getTimeFmt(zzha
                                    .getJszkdjpdsj()));
                    result.AddLine(
                            new PropDescFields(CHdZzha.class)
                                    .getPropDesc("dgc"),
                            zzha.getDgc(),
                            new PropDescFields(CHdZzha.class).getPropDesc("cd"),
                            StringUtil.getStringIsNullObj(zzha.getCd()));

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdZzha.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(zzha.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdZzha.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(zzha.getSshduanid()).getHdbh());
                    result.AddLine("所属航段名称", zzhahduan.getHdqdmc() + "-"
                            + zzhahduan.getHdzdmc(), new PropDescFields(
                            CHdZzha.class).getPropDesc("ab"), zzha.getAb() > 0 ? this.dicDao.queryAttrDesc(zzha.getAb()) : "");
                    result.AddLine(new PropDescFields(CHdZzha.class)
                                    .getPropDesc("ssqdlczh"), zzha.getSsqdlczh(),
                            new PropDescFields(CHdZzha.class)
                                    .getPropDesc("szxzqh"), this
                                    .queryStrXzqh(zzha.getSzxzqh()));
                    result.AddLine(new PropDescFields(CHdZzha.class)
                                    .getPropDesc("sffcfhds"),
                            zzha.getSffcfhds() == 1 ? "是" : "否");

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdZzha.class)
                                    .getPropDesc("jcnf"), zzha.getJcnf(),
                            new PropDescFields(CHdZzha.class)
                                    .getPropDesc("gljgmc"), zzha.getGljgmc());
                    result.AddLine(new PropDescFields(CHdZzha.class)
                                    .getPropDesc("gljglxr"), zzha.getGljglxr(),
                            new PropDescFields(CHdZzha.class)
                                    .getPropDesc("gljglxdh"), zzha
                                    .getGljglxdh());
                    result.AddLine(new PropDescFields(CHdZzha.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(zzha
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdZzha.class).getPropDesc("bz"),
                            zzha.getBz() == null ? "" : zzha.getBz());
                    result.AddLine(new PropDescFields(CHdZzha.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(zzha.getCreatetime()),
                            new PropDescFields(CHdZzha.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(zzha.getUpdatetime()));
                    break;
                case Constants.APP_LASEROBSERVATION:
                    CHdJgllgcd jgllgcd = (CHdJgllgcd) records.get(0)[0];
                    if (jgllgcd != null)
                        appid = jgllgcd.getId();
                    CHdHduanjcxx jgllgcdhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(jgllgcd);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("mc"), jgllgcd.getMc(),
                            new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("fswlx"), new PropDescFields(
                                    CHdJgllgcd.class)
                                    .getPropDesc("jgllgcdname"));
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                            .getPropDesc("bh"), getAppBHPre(fswlx) + jgllgcd.getBh());

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(jgllgcd.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(jgllgcd.getSshduanid()).getHdbh());
                    result.AddLine("所属航段名称", jgllgcdhduan.getHdqdmc() + "-"
                            + jgllgcdhduan.getHdzdmc(), new PropDescFields(
                            CHdJgllgcd.class).getPropDesc("szxxdz"), jgllgcd
                            .getSzxxdz());
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("jd"), StringUtil.getDoubleNull(jgllgcd.getJd()),
                            new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("wd"), StringUtil.getDoubleNull(jgllgcd.getWd()));

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("cbllgcsbmc"),
                            jgllgcd.getCbllgcsbmc(),
                            new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("cbllgcsbxh"), jgllgcd
                                    .getCbllgcsbxh());
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("sxsbmc"), jgllgcd.getSxsbmc(),
                            new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("sxsbxh"), jgllgcd.getSxsbxh());
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("sxsbazab"), jgllgcd.getSxsbazab(),
                            new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("sbwhdwmc"), jgllgcd
                                    .getSbwhdwmc());
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("sbwhdwlxr"), jgllgcd.getSbwhdwlxr(),
                            new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("sbwhdwlxdh"), jgllgcd
                                    .getSbwhdwlxdh());
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("hldm"), jgllgcd.getHldm(),
                            new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("hlmc"), jgllgcd.getHlmc());
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(jgllgcd
                            .getSzxzqh()), new PropDescFields(CHdJgllgcd.class)
                            .getPropDesc("szlczh"), jgllgcd.getSzlczh());
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                            .getPropDesc("gljgmc"), jgllgcd.getGljgmc());
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("gljglxr"), jgllgcd.getGljglxr(),
                            new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("gljglxdh"), jgllgcd
                                    .getGljglxdh());
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                            .getPropDesc("gljgszxzqh"), this
                            .queryStrXzqh(jgllgcd.getGljgszxzqh()));
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                            .getPropDesc("bz"), jgllgcd.getBz() == null ? ""
                            : jgllgcd.getBz());
                    result.AddLine(new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(jgllgcd.getCreatetime()),
                            new PropDescFields(CHdJgllgcd.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(jgllgcd.getUpdatetime()));
                    break;
                case Constants.APP_VIDEOOBSERVATION:
                    CHdSpgcd spgcd = (CHdSpgcd) records.get(0)[0];
                    if (spgcd != null)
                        appid = spgcd.getId();
                    CHdHduanjcxx spgcdhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(spgcd);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdSpgcd.class)
                                    .getPropDesc("mc"), spgcd.getMc(),
                            new PropDescFields(CHdSpgcd.class)
                                    .getPropDesc("fswlx"), new PropDescFields(
                                    CHdSpgcd.class).getPropDesc("spgcdname"));
                    result.AddLine(new PropDescFields(CHdSpgcd.class)
                            .getPropDesc("bh"), getAppBHPre(fswlx) + spgcd.getBh());

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdSpgcd.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(spgcd.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdSpgcd.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(spgcd.getSshduanid()).getHdbh());
                    result.AddLine("所属航段名称", spgcdhduan.getHdqdmc() + "-"
                            + spgcdhduan.getHdzdmc(), new PropDescFields(
                            CHdSpgcd.class).getPropDesc("ipdz"), spgcd
                            .getIpdz());
                    result.AddLine(new PropDescFields(CHdSpgcd.class)
                                    .getPropDesc("jd"), StringUtil.getDoubleNull(spgcd.getJd()),
                            new PropDescFields(CHdSpgcd.class)
                                    .getPropDesc("wd"), StringUtil.getDoubleNull(spgcd.getWd()));
                    result.AddLine(new PropDescFields(CHdSpgcd.class)
                                    .getPropDesc("szxxdz"), spgcd.getSzxxdz(),
                            new PropDescFields(CHdSpgcd.class)
                                    .getPropDesc("szxzqh"), this
                                    .queryStrXzqh(spgcd.getSzxzqh()));

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdSpgcd.class)
                            .getPropDesc("gljgmc"), spgcd.getGljgmc());
                    result.AddLine(new PropDescFields(CHdSpgcd.class)
                                    .getPropDesc("gljglxr"), spgcd.getGljglxr(),
                            new PropDescFields(CHdSpgcd.class)
                                    .getPropDesc("gljglxdh"), spgcd
                                    .getGljglxdh());
                    result.AddLine(new PropDescFields(CHdSpgcd.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(spgcd
                            .getGljgszxzqh()));
                    result.AddLine(new PropDescFields(CHdSpgcd.class)
                            .getPropDesc("bz"), spgcd.getBz() == null ? ""
                            : spgcd.getBz());
                    result.AddLine(new PropDescFields(CHdSpgcd.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(spgcd.getCreatetime()),
                            new PropDescFields(CHdSpgcd.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(spgcd.getUpdatetime()));
                    break;
                case Constants.APP_MANUALOBSERVATION:
                    CHdRggcd rggcd = (CHdRggcd) records.get(0)[0];
                    if (rggcd != null)
                        appid = rggcd.getId();
                    CHdHduanjcxx rggcdhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(rggcd);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdRggcd.class)
                                    .getPropDesc("mc"), rggcd.getMc(),
                            new PropDescFields(CHdRggcd.class)
                                    .getPropDesc("fswlx"), new PropDescFields(
                                    CHdRggcd.class).getPropDesc("rggcdname"));
                    result.AddLine(new PropDescFields(CHdRggcd.class)
                            .getPropDesc("bh"), getAppBHPre(fswlx) + rggcd.getBh());

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdRggcd.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(rggcd.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdRggcd.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(rggcd.getSshduanid()).getHdbh());
                    result.AddLine("所属航段名称", rggcdhduan.getHdqdmc() + "-"
                            + rggcdhduan.getHdzdmc());
                    result.AddLine(new PropDescFields(CHdRggcd.class)
                                    .getPropDesc("jd"), StringUtil.getStringIsNullObj(rggcd.getJd()),
                            new PropDescFields(CHdRggcd.class)
                                    .getPropDesc("wd"), StringUtil.getStringIsNullObj(rggcd.getWd()));
                    result.AddLine(new PropDescFields(CHdRggcd.class)
                                    .getPropDesc("szxxdz"), rggcd.getSzxxdz(),
                            new PropDescFields(CHdRggcd.class)
                                    .getPropDesc("szxzqh"), this
                                    .queryStrXzqh(rggcd.getSzxzqh()));

                    result.AddLine("辅助信息");
                    result.AddLine(new PropDescFields(CHdRggcd.class)
                            .getPropDesc("gljgmc"), rggcd.getGljgmc());
                    result.AddLine(new PropDescFields(CHdRggcd.class)
                                    .getPropDesc("gljglxr"), rggcd.getGljglxr(),
                            new PropDescFields(CHdRggcd.class)
                                    .getPropDesc("gljglxdh"), rggcd
                                    .getGljglxdh());
                    result.AddLine(new PropDescFields(CHdRggcd.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(rggcd
                            .getGljgszxzqh()));
                    result.AddLine(new PropDescFields(CHdRggcd.class)
                            .getPropDesc("bz"), rggcd.getBz() == null ? ""
                            : rggcd.getBz());
                    result.AddLine(new PropDescFields(CHdRggcd.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(rggcd.getCreatetime()),
                            new PropDescFields(CHdRggcd.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(rggcd.getUpdatetime()));
                    break;
                case Constants.APP_BOLLARD:
                    CHdXlz xlz = (CHdXlz) records.get(0)[0];
                    if (xlz != null)
                        appid = xlz.getId();
                    CHdHduanjcxx xlzhduan = (CHdHduanjcxx) records.get(0)[1];
                    result.setModelobj(xlz);
                    result.AddLine("基本信息");
                    result.AddLine(new PropDescFields(CHdXlz.class)
                                    .getPropDesc("mc"), xlz.getMc(),
                            new PropDescFields(CHdXlz.class)
                                    .getPropDesc("fswlx"), new PropDescFields(
                                    CHdXlz.class).getPropDesc("xlzname"));
                    result.AddLine(
                            new PropDescFields(CHdXlz.class).getPropDesc("bh"),
                            getAppBHPre(fswlx) + xlz.getBh());

                    result.AddLine("地理位置信息");
                    result.AddLine(new PropDescFields(CHdXlz.class)
                                    .getPropDesc("sshdaoid"), this.hangDaoDao.queryHangDaoByHdid(xlz.getSshdaoid()).getHdbh(),
                            new PropDescFields(CHdXlz.class)
                                    .getPropDesc("sshduanid"), this.hangDuanDao.queryHangDuanByHdid(xlz.getSshduanid()).getHdbh());
                    result.AddLine("所属航段名称", xlzhduan.getHdqdmc() + "-"
                            + xlzhduan.getHdzdmc(), new PropDescFields(
                            CHdXlz.class).getPropDesc("szxxdz"), xlz
                            .getSzxxdz());
                    result.AddLine(new PropDescFields(CHdXlz.class)
                                    .getPropDesc("szzbx"), StringUtil.getStringIsNullObj(xlz.getJd()),
                            new PropDescFields(CHdXlz.class)
                                    .getPropDesc("szzby"), StringUtil.getStringIsNullObj(xlz.getWd()));
                    result.AddLine(new PropDescFields(CHdXlz.class)
                            .getPropDesc("szxzqh"), this.queryStrXzqh(xlz
                            .getSzxzqh()));

                    result.AddLine("辅助信息");
                    result.AddLine(
                            new PropDescFields(CHdXlz.class).getPropDesc("cz"),
                            xlz.getCz(),
                            new PropDescFields(CHdXlz.class).getPropDesc("xz"),
                            xlz.getXz());
                    result.AddLine(new PropDescFields(CHdXlz.class)
                            .getPropDesc("sfsz"), xlz.getSfsz() == 1 ? "是"
                            : "否", new PropDescFields(CHdXlz.class)
                            .getPropDesc("sccj"), xlz.getSccj());
                    result.AddLine(new PropDescFields(CHdXlz.class)
                            .getPropDesc("gljgmc"), xlz.getGljgmc());
                    result.AddLine(new PropDescFields(CHdXlz.class)
                                    .getPropDesc("gljglxr"), xlz.getGljglxr(),
                            new PropDescFields(CHdXlz.class)
                                    .getPropDesc("gljglxdh"), xlz.getGljglxdh());
                    result.AddLine(new PropDescFields(CHdXlz.class)
                            .getPropDesc("gljgszxzqh"), this.queryStrXzqh(xlz
                            .getGljgszxzqh()));
                    result.AddLine(
                            new PropDescFields(CHdXlz.class).getPropDesc("bz"),
                            xlz.getBz() == null ? "" : xlz.getBz());
                    result.AddLine(new PropDescFields(CHdXlz.class)
                                    .getPropDesc("createtime"), DateTimeUtil
                                    .getTimeFmt(xlz.getCreatetime()),
                            new PropDescFields(CHdXlz.class)
                                    .getPropDesc("updatetime"), DateTimeUtil
                                    .getTimeFmt(xlz.getUpdatetime()));
                    break;
                default:
                    break;
            }
        }

        List<CHdFj> fjList = this.appurtenanceDao.queryChdfjByApptype(
                appid, fswlx);
        if (fjList != null && fjList.size() > 0) {
            List<Object> objects = new ArrayList<Object>();
            objects.add("附件信息");
            for (CHdFj fj : fjList) {
                objects.add(fj.getResname());
                objects.add(fj.getRessize());
                objects.add(fj.getId());
            }

            result.AddLineList(objects);
        }
        return result;
    }

    @Override
    public BaseResult delAppurtenance(int id, int fswlx) {
        BaseResultOK baseResultOK = new BaseResultOK();
        this.appurtenanceDao.delCHdFj(id, fswlx);
        List<Object[]> records = this.appurtenanceDao.queryAppurtenanceInfo(id, fswlx);
        if (records != null && records.size() > 0) {
            this.appurtenanceDao.delAppurtenance(records.get(0)[0]);
        }
        logService.addLog(ModuleName.HDXXWH, OpName.DEL, "附属物");
        return baseResultOK;
    }

    //删除某条航段上的所有附属物
    @Override
    public BaseResult delAppurtenances(int hduanid) {
        this.appurtenanceDao.delAppurtenances(hduanid);
        return new BaseResultOK();
    }

    @Override
    public BaseResult addNavigationmark(CHdHb chdhb,
                                        List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdhb.setFswlx(Constants.APP_NAVIGATIONMARK);
        chdhb.setCreater(user.getId());
        chdhb.setCreatetime(new Date());
        chdhb.setUpdatetime(new Date());
        this.appurtenanceDao.addNavigationmark(chdhb);
        this.addCHdFj(filelist, filelistFileName, chdhb.getId(),
                Constants.APP_NAVIGATIONMARK, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "航标:" + chdhb.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdhb.getId());
        baseResultOK.addToMap("fswbh", chdhb.getBh());
        baseResultOK.addToMap("fswmc", chdhb.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addBollard(CHdXlz chdxlz,
                                 List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdxlz.setFswlx(Constants.APP_BOLLARD);
        chdxlz.setCreater(user.getId());
        chdxlz.setCreatetime(new Date());
        chdxlz.setUpdatetime(new Date());
        this.appurtenanceDao.addBollard(chdxlz);
        this.addCHdFj(filelist, filelistFileName, chdxlz.getId(),
                Constants.APP_BOLLARD, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "系缆桩:" + chdxlz.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdxlz.getId());
        baseResultOK.addToMap("fswbh", chdxlz.getBh());
        baseResultOK.addToMap("fswmc", chdxlz.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addVideoobservation(CHdSpgcd chdspgcd,
                                          List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdspgcd.setFswlx(Constants.APP_VIDEOOBSERVATION);
        chdspgcd.setCreater(user.getId());
        chdspgcd.setCreatetime(new Date());
        chdspgcd.setUpdatetime(new Date());
        this.appurtenanceDao.addVideoobservation(chdspgcd);
        this.addCHdFj(filelist, filelistFileName, chdspgcd.getId(),
                Constants.APP_VIDEOOBSERVATION, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "视频观测站:" + chdspgcd.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdspgcd.getId());
        baseResultOK.addToMap("fswbh", chdspgcd.getBh());
        baseResultOK.addToMap("fswmc", chdspgcd.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addLaserobservation(CHdJgllgcd chdjgllgcd,
                                          List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdjgllgcd.setFswlx(Constants.APP_LASEROBSERVATION);
        chdjgllgcd.setCreater(user.getId());
        chdjgllgcd.setCreatetime(new Date());
        chdjgllgcd.setUpdatetime(new Date());
        this.appurtenanceDao.addLaserobservation(chdjgllgcd);
        this.addCHdFj(filelist, filelistFileName, chdjgllgcd.getId(),
                Constants.APP_LASEROBSERVATION, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "激光流量观测站:" + chdjgllgcd.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdjgllgcd.getId());
        baseResultOK.addToMap("fswbh", chdjgllgcd.getBh());
        baseResultOK.addToMap("fswmc", chdjgllgcd.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addManualobservation(CHdRggcd chdrggcd, List<File> filelist, List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdrggcd.setFswlx(Constants.APP_MANUALOBSERVATION);
        chdrggcd.setCreater(user.getId());
        chdrggcd.setCreatetime(new Date());
        chdrggcd.setUpdatetime(new Date());
        this.appurtenanceDao.addManualobservation(chdrggcd);
        this.addCHdFj(filelist, filelistFileName, chdrggcd.getId(),
                Constants.APP_MANUALOBSERVATION, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "人工观测站:" + chdrggcd.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdrggcd.getId());
        baseResultOK.addToMap("fswbh", chdrggcd.getBh());
        baseResultOK.addToMap("fswmc", chdrggcd.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addRegulationrevement(CHdZzha chdzzha,
                                            List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdzzha.setFswlx(Constants.APP_REGULATIONREVEMENT);
        chdzzha.setCreater(user.getId());
        chdzzha.setCreatetime(new Date());
        chdzzha.setUpdatetime(new Date());
        this.appurtenanceDao.addRegulationrevement(chdzzha);
        this.addCHdFj(filelist, filelistFileName, chdzzha.getId(),
                Constants.APP_REGULATIONREVEMENT, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "整治护岸:" + chdzzha.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdzzha.getId());
        baseResultOK.addToMap("fswbh", chdzzha.getBh());
        baseResultOK.addToMap("fswmc", chdzzha.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addHub(CHdSn chdsn, List<File> filelist,
                             List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdsn.setFswlx(Constants.APP_HUB);
        chdsn.setCreater(user.getId());
        chdsn.setCreatetime(new Date());
        chdsn.setUpdatetime(new Date());
        this.appurtenanceDao.addHub(chdsn);
        this.addCHdFj(filelist, filelistFileName, chdsn.getId(),
                Constants.APP_HUB, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "枢纽:" + chdsn.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdsn.getId());
        baseResultOK.addToMap("fswbh", chdsn.getBh());
        baseResultOK.addToMap("fswmc", chdsn.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addDam(CHdB chdb, List<File> filelist,
                             List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdb.setFswlx(Constants.APP_DAM);
        chdb.setCreater(user.getId());
        chdb.setCreatetime(new Date());
        chdb.setUpdatetime(new Date());
        this.appurtenanceDao.addDam(chdb);
        this.addCHdFj(filelist, filelistFileName, chdb.getId(),
                Constants.APP_DAM, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "大坝:" + chdb.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdb.getId());
        baseResultOK.addToMap("fswbh", chdb.getBh());
        baseResultOK.addToMap("fswmc", chdb.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addMooringarea(CHdMbq chdmbq, List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdmbq.setFswlx(Constants.APP_MOORINGAREA);
        chdmbq.setCreater(user.getId());
        chdmbq.setCreatetime(new Date());
        chdmbq.setUpdatetime(new Date());
        this.appurtenanceDao.addMooringarea(chdmbq);
        this.addCHdFj(filelist, filelistFileName, chdmbq.getId(),
                Constants.APP_MOORINGAREA, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "锚泊区:" + chdmbq.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdmbq.getId());
        baseResultOK.addToMap("fswbh", chdmbq.getBh());
        baseResultOK.addToMap("fswmc", chdmbq.getMc());
        return baseResultOK;
    }


    @Override
    public BaseResult addManagementstation(CHdGlz chdglz,
                                           List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdglz.setFswlx(Constants.APP_MANAGEMENTSTATION);
        chdglz.setCreater(user.getId());
        chdglz.setCreatetime(new Date());
        chdglz.setUpdatetime(new Date());
        this.appurtenanceDao.addManagementstation(chdglz);
        this.addCHdFj(filelist, filelistFileName, chdglz.getId(),
                Constants.APP_MANAGEMENTSTATION, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "管理站:" + chdglz.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdglz.getId());
        baseResultOK.addToMap("fswbh", chdglz.getBh());
        baseResultOK.addToMap("fswmc", chdglz.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addHydrologicalstation(CHdSwz chdswz,
                                             List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdswz.setFswlx(Constants.APP_HYDROLOGICALSTATION);
        chdswz.setCreater(user.getId());
        chdswz.setCreatetime(new Date());
        chdswz.setUpdatetime(new Date());
        this.appurtenanceDao.addHydrologicalstation(chdswz);
        this.addCHdFj(filelist, filelistFileName, chdswz.getId(),
                Constants.APP_HYDROLOGICALSTATION, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "水文站:" + chdswz.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdswz.getId());
        baseResultOK.addToMap("fswbh", chdswz.getBh());
        baseResultOK.addToMap("fswmc", chdswz.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addShipyard(CHdCc chdcc,
                                  List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdcc.setFswlx(Constants.APP_SHIPYARD);
        chdcc.setCreater(user.getId());
        chdcc.setCreatetime(new Date());
        chdcc.setUpdatetime(new Date());
        this.appurtenanceDao.addShipyard(chdcc);
        this.addCHdFj(filelist, filelistFileName, chdcc.getId(),
                Constants.APP_SHIPYARD, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "船厂:" + chdcc.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdcc.getId());
        baseResultOK.addToMap("fswbh", chdcc.getBh());
        baseResultOK.addToMap("fswmc", chdcc.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addTakeoutfall(CHdQpsk chdqpsk,
                                     List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdqpsk.setFswlx(Constants.APP_TAKEOUTFALL);
        chdqpsk.setCreater(user.getId());
        chdqpsk.setCreatetime(new Date());
        chdqpsk.setUpdatetime(new Date());
        this.appurtenanceDao.addTakeoutfall(chdqpsk);
        this.addCHdFj(filelist, filelistFileName, chdqpsk.getId(),
                Constants.APP_TAKEOUTFALL, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "取排水:" + chdqpsk.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdqpsk.getId());
        baseResultOK.addToMap("fswbh", chdqpsk.getBh());
        baseResultOK.addToMap("fswmc", chdqpsk.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addKyDock(CHdKymt chdmt, List<File> filelist,
                                List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdmt.setFswlx(Constants.APP_KYDOCK);
        chdmt.setCreater(user.getId());
        chdmt.setCreatetime(new Date());
        chdmt.setUpdatetime(new Date());
        this.appurtenanceDao.addKyDock(chdmt);
        this.addCHdFj(filelist, filelistFileName, chdmt.getId(),
                Constants.APP_KYDOCK, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "客运码头:" + chdmt.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdmt.getId());
        baseResultOK.addToMap("fswbh", chdmt.getBh());
        baseResultOK.addToMap("fswmc", chdmt.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addHyDock(CHdHymt chdmt, List<File> filelist,
                                List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdmt.setFswlx(Constants.APP_HYDOCK);
        chdmt.setCreater(user.getId());
        chdmt.setCreatetime(new Date());
        chdmt.setUpdatetime(new Date());
        this.appurtenanceDao.addHyDock(chdmt);
        this.addCHdFj(filelist, filelistFileName, chdmt.getId(),
                Constants.APP_HYDOCK, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "货运码头:" + chdmt.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdmt.getId());
        baseResultOK.addToMap("fswbh", chdmt.getBh());
        baseResultOK.addToMap("fswmc", chdmt.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addGwDock(CHdGwmt chdmt, List<File> filelist,
                                List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdmt.setFswlx(Constants.APP_GWDOCK);
        chdmt.setCreater(user.getId());
        chdmt.setCreatetime(new Date());
        chdmt.setUpdatetime(new Date());
        this.appurtenanceDao.addGwDock(chdmt);
        this.addCHdFj(filelist, filelistFileName, chdmt.getId(),
                Constants.APP_GWDOCK, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "公务码头:" + chdmt.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdmt.getId());
        baseResultOK.addToMap("fswbh", chdmt.getBh());
        baseResultOK.addToMap("fswmc", chdmt.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addTunnel(CHdSd chdsd, List<File> filelist,
                                List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdsd.setFswlx(Constants.APP_TUNNEL);
        chdsd.setCreater(user.getId());
        chdsd.setCreatetime(new Date());
        chdsd.setUpdatetime(new Date());
        this.appurtenanceDao.addTunnel(chdsd);
        this.addCHdFj(filelist, filelistFileName, chdsd.getId(),
                Constants.APP_TUNNEL, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "隧道:" + chdsd.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdsd.getId());
        baseResultOK.addToMap("fswbh", chdsd.getBh());
        baseResultOK.addToMap("fswmc", chdsd.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addPipeline(CHdGd chdgd,
                                  List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdgd.setFswlx(Constants.APP_PIPELINE);
        chdgd.setCreater(user.getId());
        chdgd.setCreatetime(new Date());
        chdgd.setUpdatetime(new Date());
        this.appurtenanceDao.addPipeline(chdgd);
        this.addCHdFj(filelist, filelistFileName, chdgd.getId(),
                Constants.APP_PIPELINE, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "管道:" + chdgd.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdgd.getId());
        baseResultOK.addToMap("fswbh", chdgd.getBh());
        baseResultOK.addToMap("fswmc", chdgd.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addCable(CHdLx chdlx, List<File> filelist,
                               List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdlx.setFswlx(Constants.APP_CABLE);
        chdlx.setCreater(user.getId());
        chdlx.setCreatetime(new Date());
        chdlx.setUpdatetime(new Date());
        this.appurtenanceDao.addCable(chdlx);
        this.addCHdFj(filelist, filelistFileName, chdlx.getId(),
                Constants.APP_CABLE, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "缆线:" + chdlx.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdlx.getId());
        baseResultOK.addToMap("fswbh", chdlx.getBh());
        baseResultOK.addToMap("fswmc", chdlx.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addAqueduct(CHdDc chddc,
                                  List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chddc.setFswlx(Constants.APP_AQUEDUCT);
        chddc.setCreater(user.getId());
        chddc.setCreatetime(new Date());
        chddc.setUpdatetime(new Date());
        this.appurtenanceDao.addAqueduct(chddc);
        this.addCHdFj(filelist, filelistFileName, chddc.getId(),
                Constants.APP_AQUEDUCT, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "渡槽:" + chddc.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chddc.getId());
        baseResultOK.addToMap("fswbh", chddc.getBh());
        baseResultOK.addToMap("fswmc", chddc.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addBridge(CHdQl chdql, List<File> filelist,
                                List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdql.setFswlx(Constants.APP_BRIDGE);
        chdql.setCreater(user.getId());
        chdql.setCreatetime(new Date());
        chdql.setUpdatetime(new Date());
        this.appurtenanceDao.addBridge(chdql);
        this.addCHdFj(filelist, filelistFileName, chdql.getId(),
                Constants.APP_BRIDGE, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "桥梁:" + chdql.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", chdql.getId());
        baseResultOK.addToMap("fswbh", chdql.getBh());
        baseResultOK.addToMap("fswmc", chdql.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult addServicearea(CHdFwq fwq,
                                     List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        fwq.setFswlx(Constants.APP_SERVICEAREA);
        fwq.setCreater(user.getId());
        fwq.setCreatetime(new Date());
        fwq.setUpdatetime(new Date());
        this.appurtenanceDao.addServicearea(fwq);
        this.addCHdFj(filelist, filelistFileName, fwq.getId(),
                Constants.APP_SERVICEAREA, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.ADD, "服务区:" + fwq.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("fswid", fwq.getId());
        baseResultOK.addToMap("fswbh", fwq.getBh());
        baseResultOK.addToMap("fswmc", fwq.getMc());
        return baseResultOK;
    }

    @Override
    public BaseResult updateNavigationmark(CHdHb chdhb,
                                           List<Integer> delfileids, List<File> filelist,
                                           List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdhb.setUpdatetime(new Date());
        this.appurtenanceDao.updateNavigationmark(chdhb);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdhb.getId(), Constants.APP_NAVIGATIONMARK, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "航标:" + chdhb.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateBollard(CHdXlz chdxlz,
                                    List<Integer> delfileids, List<File> filelist,
                                    List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdxlz.setUpdatetime(new Date());
        this.appurtenanceDao.updateBollard(chdxlz);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdxlz.getId(), Constants.APP_BOLLARD, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "系缆桩:" + chdxlz.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateVideoobservation(CHdSpgcd chdspgcd,
                                             List<Integer> delfileids, List<File> filelist,
                                             List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdspgcd.setUpdatetime(new Date());
        this.appurtenanceDao.updateVideoobservation(chdspgcd);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdspgcd.getId(), Constants.APP_VIDEOOBSERVATION, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "视频观测站:" + chdspgcd.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateManualobservation(CHdRggcd chdrggcd, List<Integer> delfileids, List<File> filelist, List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdrggcd.setUpdatetime(new Date());
        this.appurtenanceDao.updateManualobservation(chdrggcd);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdrggcd.getId(), Constants.APP_MANUALOBSERVATION, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "人工观测点:" + chdrggcd.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateLaserobservation(
            CHdJgllgcd chdjgllgcd, List<Integer> delfileids,
            List<File> filelist, List<String> filelistFileName)
            throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        chdjgllgcd.setUpdatetime(new Date());
        this.appurtenanceDao.updateLaserobservation(chdjgllgcd);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdjgllgcd.getId(), Constants.APP_LASEROBSERVATION, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "激光流量观测站:" + chdjgllgcd.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateRegulationrevement(CHdZzha chdzzha,
                                               List<Integer> delfileids, List<File> filelist,
                                               List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdzzha.setUpdatetime(new Date());
        this.appurtenanceDao.updateRegulationrevement(chdzzha);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdzzha.getId(), Constants.APP_REGULATIONREVEMENT, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "整治护岸:" + chdzzha.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateHub(CHdSn chdsn,
                                List<Integer> delfileids, List<File> filelist,
                                List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdsn.setUpdatetime(new Date());
        this.appurtenanceDao.updateHub(chdsn);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdsn.getId(), Constants.APP_HUB, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "枢纽:" + chdsn.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateDam(CHdB chdb,
                                List<Integer> delfileids, List<File> filelist,
                                List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdb.setUpdatetime(new Date());
        this.appurtenanceDao.updateDam(chdb);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdb.getId(), Constants.APP_DAM, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "坝:" + chdb.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateMooringarea(CHdMbq chdmbq,
                                        List<Integer> delfileids, List<File> filelist,
                                        List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdmbq.setUpdatetime(new Date());
        this.appurtenanceDao.updateMooringarea(chdmbq);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdmbq.getId(), Constants.APP_MOORINGAREA, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "锚泊区:" + chdmbq.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }


    @Override
    public BaseResult updateManagementstation(CHdGlz chdglz,
                                              List<Integer> delfileids, List<File> filelist,
                                              List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdglz.setUpdatetime(new Date());
        this.appurtenanceDao.updateManagementstation(chdglz);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdglz.getId(), Constants.APP_MANAGEMENTSTATION, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "管理站:" + chdglz.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateHydrologicalstation(CHdSwz chdswz,
                                                List<Integer> delfileids, List<File> filelist,
                                                List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdswz.setUpdatetime(new Date());
        this.appurtenanceDao.updateHydrologicalstation(chdswz);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdswz.getId(), Constants.APP_HYDROLOGICALSTATION, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "水文站:" + chdswz.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateShipyard(CHdCc chdcc,
                                     List<Integer> delfileids, List<File> filelist,
                                     List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdcc.setUpdatetime(new Date());
        this.appurtenanceDao.updateShipyard(chdcc);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdcc.getId(), Constants.APP_SHIPYARD, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "船厂:" + chdcc.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateTakeoutfall(CHdQpsk chdqpsk,
                                        List<Integer> delfileids, List<File> filelist,
                                        List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdqpsk.setUpdatetime(new Date());
        this.appurtenanceDao.updateTakeoutfall(chdqpsk);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdqpsk.getId(), Constants.APP_TAKEOUTFALL, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "取排水:" + chdqpsk.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateKyDock(CHdKymt chdmt,
                                   List<Integer> delfileids, List<File> filelist,
                                   List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdmt.setUpdatetime(new Date());
        this.appurtenanceDao.updateKyDock(chdmt);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdmt.getId(), Constants.APP_KYDOCK, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "客运码头:" + chdmt.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateHyDock(CHdHymt chdmt,
                                   List<Integer> delfileids, List<File> filelist,
                                   List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdmt.setUpdatetime(new Date());
        this.appurtenanceDao.updateHyDock(chdmt);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdmt.getId(), Constants.APP_HYDOCK, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "货运码头:" + chdmt.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateGwDock(CHdGwmt chdmt,
                                   List<Integer> delfileids, List<File> filelist,
                                   List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdmt.setUpdatetime(new Date());
        this.appurtenanceDao.updateGwDock(chdmt);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdmt.getId(), Constants.APP_GWDOCK, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "公务码头:" + chdmt.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateTunnel(CHdSd chdsd,
                                   List<Integer> delfileids, List<File> filelist,
                                   List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdsd.setUpdatetime(new Date());
        this.appurtenanceDao.updateTunnel(chdsd);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdsd.getId(), Constants.APP_TUNNEL, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "隧道:" + chdsd.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updatePipeline(CHdGd chdgd,
                                     List<Integer> delfileids, List<File> filelist,
                                     List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdgd.setUpdatetime(new Date());
        this.appurtenanceDao.updatePipeline(chdgd);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdgd.getId(), Constants.APP_PIPELINE, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "管道:" + chdgd.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateCable(CHdLx chdlx,
                                  List<Integer> delfileids, List<File> filelist,
                                  List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdlx.setUpdatetime(new Date());
        this.appurtenanceDao.updateCable(chdlx);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdlx.getId(), Constants.APP_CABLE, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "缆线:" + chdlx.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateAqueduct(CHdDc chddc,
                                     List<Integer> delfileids, List<File> filelist,
                                     List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chddc.setUpdatetime(new Date());
        this.appurtenanceDao.updateAqueduct(chddc);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chddc.getId(), Constants.APP_AQUEDUCT, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "渡槽:" + chddc.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateBridge(CHdQl chdql,
                                   List<Integer> delfileids, List<File> filelist,
                                   List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        chdql.setUpdatetime(new Date());
        this.appurtenanceDao.updateBridge(chdql);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                chdql.getId(), Constants.APP_BRIDGE, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "桥梁:" + chdql.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    @Override
    public BaseResult updateServicearea(CHdFwq cHdFwq,
                                        List<Integer> delfileids, List<File> filelist,
                                        List<String> filelistFileName) throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        cHdFwq.setUpdatetime(new Date());
        this.appurtenanceDao.updateServicearea(cHdFwq);
        this.updateCHdFj(delfileids, filelist, filelistFileName,
                cHdFwq.getId(), Constants.APP_SERVICEAREA, user.getId());
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, "服务区:" + cHdFwq.getMc());
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    public void addCHdFj(List<File> filelist, List<String> filelistFileName,
                         int appid, int apptype, int loginid) throws Exception {
        if (filelist != null && filelist.size() > 0) {
            String root = this.getRealPath("upload");// upload存放路径
            File infile = null;
            File outfile = null;
            BufferedInputStream in = null;
            BufferedOutputStream out = null;
            FileUtils.mkdir(root);
            for (int i = 0; i < filelist.size(); i++) {
                // io流操作
                infile = filelist.get(i);
                String infileMd5 = FileMd5.getMd5ByFile(infile);
                String newfilename = "\\" + filelistFileName.get(i);// 上传新文件名字
                String outfilepath = root + newfilename;// 上传新文件路径名字
                outfile = new File(outfilepath);
                // 如果同名且MD5相同的文件不存在
                if (!FileUtils.ifFileExist(outfilepath, infileMd5)) {
                    if (FileUtils.ifFileExist(outfilepath)) { // 如果文件同名，则重命名
                        newfilename = FileUtils.renameFileName(newfilename,
                                RandomCode.genRandomCode(5));
                        outfilepath = root + newfilename;
                    }
                    outfile = new File(outfilepath);
                    in = new BufferedInputStream(new FileInputStream(infile));
                    out = new BufferedOutputStream(
                            new FileOutputStream(outfile));
                    byte[] c = new byte[4096];
                    int len = -1;
                    while ((len = in.read(c, 0, c.length)) != -1) {
                        out.write(c, 0, len);
                    }
                    in.close();
                    out.flush();
                    out.close();
                }

                // 数据库资源插入操作
                CHdFj fj = new CHdFj();
                fj.setAppid(appid);
                fj.setApptype(apptype);
                fj.setResname(outfile.getName());
                fj.setRespath(newfilename);
                fj.setRessize(String.valueOf(outfile.length() * 1.0 / 1000));
                fj.setFilemd5(infileMd5);
                fj.setCreater(loginid);
                fj.setCreatetime(new Date());
                fj.setUpdatetime(new Date());
                this.appurtenanceDao.addCHdFj(fj);
            }
        }
    }

    public void updateCHdFj(List<Integer> delfileids, List<File> filelist,
                            List<String> filelistFileName, int appid, int apptype, int loginid)
            throws Exception {
        addCHdFj(filelist, filelistFileName, appid, apptype, loginid);
        if (delfileids != null) {
            for (Integer id : delfileids) {
                this.appurtenanceDao.delCHdFj(id, appid, apptype);
            }
        }
    }

    @Override
    public BaseResult downloadCHdFj(int id) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        CHdFj fj = this.appurtenanceDao.queryCHdFjById(id);
        if (fj == null) {
            return new BaseResultFailed(Constants.RESULT_ATTACH_NOTEXIST,
                    "附件不存在");
        } else {
            String filename = fj.getRespath().replace("\\", "/");
            try {
                BaseResultOK baseResultOK = new BaseResultOK();
                baseResultOK.addToMap("filename", filename);
                return baseResultOK;
            } catch (Exception e) {
                return new BaseResultFailed(Constants.RESULT_ATTACH_NOTEXIST,
                        "附件不存在");
            }
        }
    }

    @Override
    public BaseResult queryAppBh(int fswlx) {
        switch (fswlx) {
            case Constants.APP_NAVIGATIONMARK:
                List<CHdHb> hb = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdHb obj : hb) {
                    String bh = setappbh("HB", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_BRIDGE:
                List<CHdQl> ql = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdQl obj : ql) {
                    String bh = setappbh("QL", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_AQUEDUCT:
                List<CHdDc> dc = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdDc obj : dc) {
                    String bh = setappbh("DC", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_CABLE:
                List<CHdLx> lx = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdLx obj : lx) {
                    String bh = setappbh("LX", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_PIPELINE:
                List<CHdGd> gd = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdGd obj : gd) {
                    String bh = setappbh("GD", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_TUNNEL:
                List<CHdSd> sd = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdSd obj : sd) {
                    String bh = setappbh("SD", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_KYDOCK:
                List<CHdKymt> kymt = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdKymt obj : kymt) {
                    String bh = setappbh("KYMT", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_HYDOCK:
                List<CHdHymt> hymt = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdHymt obj : hymt) {
                    String bh = setappbh("HYMT", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_GWDOCK:
                List<CHdGwmt> gwmt = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdGwmt obj : gwmt) {
                    String bh = setappbh("GWMT", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_SHIPYARD:
                List<CHdCc> cc = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdCc obj : cc) {
                    String bh = setappbh("CC", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_TAKEOUTFALL:
                List<CHdQpsk> qpsk = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdQpsk obj : qpsk) {
                    String bh = setappbh("QPSK", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                List<CHdSwz> swz = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdSwz obj : swz) {
                    String bh = setappbh("SWZ", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;

            case Constants.APP_MANAGEMENTSTATION:
                List<CHdGlz> glz = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdGlz obj : glz) {
                    String bh = setappbh("GLZ", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_SERVICEAREA:
                List<CHdFwq> fwq = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdFwq obj : fwq) {
                    String bh = setappbh("FWQ", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_MOORINGAREA:
                List<CHdMbq> mbq = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdMbq obj : mbq) {
                    String bh = setappbh("MBQ", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_HUB:
                List<CHdSn> sn = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdSn obj : sn) {
                    String bh = setappbh("SN", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_DAM:
                List<CHdB> b = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdB obj : b) {
                    String bh = setappbh("B", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_REGULATIONREVEMENT:
                List<CHdZzha> zzha = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdZzha obj : zzha) {
                    String bh = setappbh("ZZHA", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_LASEROBSERVATION:
                List<CHdJgllgcd> cHdJgllgcd = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdJgllgcd obj : cHdJgllgcd) {
                    String bh = setappbh("JGLLGCD", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_VIDEOOBSERVATION:
                List<CHdSpgcd> spgcd = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdSpgcd obj : spgcd) {
                    String bh = setappbh("SPGCD", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_MANUALOBSERVATION:
                List<CHdRggcd> rggcd = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdRggcd obj : rggcd) {
                    String bh = setappbh("RGGCD", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            case Constants.APP_BOLLARD:
                List<CHdXlz> xlz = this.appurtenanceDao.queryAppurtenance(fswlx);
                for (CHdXlz obj : xlz) {
                    String bh = setappbh("XLZ", obj.getId(), 6);
                    obj.setBh(bh);
                }
                break;
            default:
                break;
        }
        BaseResultOK baseResultOK = new BaseResultOK();
        return baseResultOK;
    }

    private String setappbh(String prefixstr, int appid, int len) {
        String str = String.valueOf(appid);
        int n = str.length();
        for (int i = n; i < len; i++) {
            str = "0" + str;
        }
        return prefixstr + str;
    }

    public static String getAppBHPre(int fswsecondclass) {
        String pre = "";
        switch (fswsecondclass) {
            case Constants.APP_NAVIGATIONMARK:
                pre = "HB";
                break;
            case Constants.APP_BRIDGE:
                pre = "QL";
                break;
            case Constants.APP_AQUEDUCT:
                pre = "DC";
                break;
            case Constants.APP_CABLE:
                pre = "LX";
                break;
            case Constants.APP_PIPELINE:
                pre = "GD";
                break;
            case Constants.APP_TUNNEL:
                pre = "SD";
                break;
            case Constants.APP_KYDOCK:
                pre = "KYMT";
                break;
            case Constants.APP_HYDOCK:
                pre = "HYMT";
                break;
            case Constants.APP_GWDOCK:
                pre = "GWMT";
                break;
            case Constants.APP_SHIPYARD:
                pre = "CC";
                break;
            case Constants.APP_TAKEOUTFALL:
                pre = "QPS";
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                pre = "SWZ";
                break;
            case Constants.APP_MANAGEMENTSTATION:
                pre = "GLZ";
                break;
            case Constants.APP_SERVICEAREA:
                pre = "FWQ";
                break;
            case Constants.APP_MOORINGAREA:
                pre = "MBQ";
                break;
            case Constants.APP_HUB:
                pre = "SN";
                break;
            case Constants.APP_DAM:
                pre = "B";
                break;
            case Constants.APP_REGULATIONREVEMENT:
                pre = "ZZHA";
                break;
            case Constants.APP_LASEROBSERVATION:
                pre = "JGLLGCD";
                break;
            case Constants.APP_VIDEOOBSERVATION:
                pre = "SPGCD";
                break;
            case Constants.APP_MANUALOBSERVATION:
                pre = "RGGCD";
                break;
            case Constants.APP_BOLLARD:
                pre = "XLZ";
                break;
            default:
                break;
        }
        return pre;
    }

    public static int getAppFswSecondClass(String modelname) {
        int cls = -1;
        switch (modelname) {
            case "CHdHb":
                cls = Constants.APP_NAVIGATIONMARK;
                break;
            case "CHdQl":
                cls = Constants.APP_BRIDGE;
                break;
            case "CHdDc":
                cls = Constants.APP_AQUEDUCT;
                break;
            case "CHdLx":
                cls = Constants.APP_CABLE;
                break;
            case "CHdGd":
                cls = Constants.APP_PIPELINE;
                break;
            case "CHdSd":
                cls = Constants.APP_TUNNEL;
                break;
            case "CHdKymt":
                cls = Constants.APP_KYDOCK;
                break;
            case "CHdHymt":
                cls = Constants.APP_HYDOCK;
                break;
            case "CHdGwmt":
                cls = Constants.APP_GWDOCK;
                break;
            case "CHdCc":
                cls = Constants.APP_SHIPYARD;
                break;
            case "CHdQpsk":
                cls = Constants.APP_TAKEOUTFALL;
                break;
            case "CHdSwz":
                cls = Constants.APP_HYDROLOGICALSTATION;
                break;
            case "CHdGlz":
                cls = Constants.APP_MANAGEMENTSTATION;
                break;
            case "CHdFwq":
                cls = Constants.APP_SERVICEAREA;
                break;
            case "CHdMbq":
                cls = Constants.APP_MOORINGAREA;
                break;
            case "CHdSn":
                cls = Constants.APP_HUB;
                break;
            case "CHdB":
                cls = Constants.APP_DAM;
                break;
            case "CHdZzha":
                cls = Constants.APP_REGULATIONREVEMENT;
                break;
            case "CHdJgllgcd":
                cls = Constants.APP_LASEROBSERVATION;
                break;
            case "CHdSpgcd":
                cls = Constants.APP_VIDEOOBSERVATION;
                break;
            case "CHdRggcd":
                cls = Constants.APP_MANUALOBSERVATION;
                break;
            case "CHdXlz":
                cls = Constants.APP_BOLLARD;
                break;
            default:
                break;
        }
        return cls;
    }

    @Override
    public BaseResult zhcxApp(int xzqh) {
        List list = new ArrayList<>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        String dept = "";
        if (xzqh == -1) {
            xzqhdm = this.xzqhdmdao.queryRootXzqh();
        } else {
            xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
        }
        dept = xzqhdm.getName();
        list = getListVal(xzqhdm);
        BaseQueryRecords records = new BaseQueryRecords();
        records.setData(list);
        BaseResultOK ok = new BaseResultOK(records);
        ok.addToMap("dept", dept);
        return ok;
    }

    private void addCell(int srow, int erow, XSSFSheet sheet, CellStyle cellStyle) {
        for (int j = srow; j < erow; j++) {
            sheet.createRow(j);
            XSSFRow row = sheet.getRow(j);
            for (int k = 2; k < 11; k++) {
                row.createCell(k).setCellStyle(cellStyle);
            }
        }
    }

    @Override
    public BaseResult exportApp(int xzqh) {
        List list = new ArrayList<>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        String dept = "";
        if (xzqh == -1) {
            xzqhdm = this.xzqhdmdao.queryRootXzqh();
        } else {
            xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
        }
        dept = xzqhdm.getName();
        list = getListVal(xzqhdm);
        BaseResultOK baseResultOK = new BaseResultOK();
        String outfilepath = "";
        File inputfile = null;
        File outputfile = null;
        try {
            String path = this.getRealPath("/");
            inputfile = new File(path + "/template/附属物列表.xlsx");
            String filename = inputfile.getName();
            StringBuffer sbf = new StringBuffer(filename);
            sbf.insert(sbf.indexOf("."),
                    DateTimeUtil.getTimeFmt(new Date(), "yyyyMMddHHmmss"));
            outfilepath = path + "baobiao\\" + sbf.toString();
            FileUtils.writeToFile(inputfile, outfilepath);
            outputfile = new File(outfilepath);
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                    outputfile));
            CellStyle cellStyle = workbook.createCellStyle(); // 样式
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

            XSSFSheet sheet = workbook.getSheet("附属物列表");
            sheet.getRow(0).getCell(0).setCellValue(xzqhdm.getName() + "附属物列表");
            if (list != null && list.size() > 0) {
                int len = list.size();
                int n = 6;
                int totalrow = n + len;
                // 航道名称 合计cell
                addCell(n, totalrow, sheet, cellStyle);
                for (int j = n; j < totalrow; j++) {
                    sheet.addMergedRegion(new CellRangeAddress(j, j, 2, 3));
                }
                for (int i = 0; i < len; i++) {
                    Object[] arr = (Object[]) list.get(i);
                    sheet.getRow(n + i).getCell(2).setCellValue(StringUtil.getStringIsNullObj(arr[1]));
                    sheet.getRow(n + i).getCell(4).setCellValue(StringUtil.getStringIsNullObj(arr[2]));
                    sheet.getRow(n + i).getCell(5).setCellValue(StringUtil.getStringIsNullObj(arr[3]));
                    sheet.getRow(n + i).getCell(6).setCellValue(StringUtil.getStringIsNullObj(arr[4]));
                    sheet.getRow(n + i).getCell(7).setCellValue(StringUtil.getStringIsNullObj(arr[5]));
                    sheet.getRow(n + i).getCell(8).setCellValue(StringUtil.getStringIsNullObj(arr[6]));
                    sheet.getRow(n + i).getCell(9).setCellValue(StringUtil.getStringIsNullObj(arr[7]));
                    sheet.getRow(n + i).getCell(10).setCellValue(StringUtil.getStringIsNullObj(arr[8]));
                }
            }
            FileOutputStream outfile = new FileOutputStream(outputfile);
            workbook.write(outfile);
            outfile.close();
            logService.addLog(ModuleName.ZHCX, OpName.EXPORT, "附属物列表");
            baseResultOK.addToMap("filename", outputfile.getName());
            baseResultOK.addToMap("filepath", outfilepath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseResultOK;
    }

    @Override
    public BaseResult zhcxApps(int fswlx, int xzqh, int sshdid, Integer sshduanid, String content, int page, int rows) {
        BaseResultOK baseResultOK = new BaseResultOK();
        String hduanids = "";
        CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
        if (sshduanid != null && sshduanid != -1) {
            hduanids = String.valueOf(sshduanid);
        } else {
            String totalxzqh = "";
            if (xzqhdm != null) {
                boolean ifleaf = this.xzqhdmService.ifNodeLeaf(xzqhdm);
                if (!ifleaf) {//市xzqh
                    List<CZdXzqhdm> list = this.xzqhdmService.findChildrenNodes_r(xzqhdm);
                    if (list != null && list.size() > 0) {
                        totalxzqh = getSqlXzqhList(list);
                    }
                } else {//站
                    totalxzqh = String.valueOf(xzqhdm.getId());
                }
                BaseQueryRecords<CHdHduanjcxx> hduans = this.hangDuanDao.queryHangDuanByXzqhSshdid(totalxzqh, sshdid);
                if (hduans != null) {
                    List<CHdHduanjcxx> hdlist = hduans.getData();
                    if (hdlist != null && hdlist.size() > 0) {
                        StringBuffer sb = new StringBuffer("");
                        for (CHdHduanjcxx hd : hdlist) {
                            sb.append(hd.getId() + ",");
                        }
                        sb.deleteCharAt(sb.length() - 1);
                        hduanids = sb.toString();
                    }
                }
            }

        }
        BaseQueryRecords<Object> records = this.appurtenanceDao
                .zhcxApps(fswlx, hduanids, content, page, rows);
        baseResultOK.setRecords(records);
        baseResultOK.addToMap("bhpre", getAppBHPre(fswlx));
        return baseResultOK;
    }


    private String getSqlXzqh(BaseQueryRecords<CZdXzqhdm> xzqhs) {
        String ret = "";
        if (xzqhs != null) {
            List<CZdXzqhdm> list = xzqhs.getData();
            if (list != null && list.size() > 0) {
                StringBuffer sb = new StringBuffer("");
                for (CZdXzqhdm xzqh : list) {
                    sb.append(xzqh.getId() + ",");
                }
                sb.deleteCharAt(sb.length() - 1);
                ret = sb.toString();
            }
        }
        return ret;
    }

    private String getSqlXzqhList(List<CZdXzqhdm> list) {
        String ret = "";
        if (list != null && list.size() > 0) {
            StringBuffer sb = new StringBuffer("");
            for (CZdXzqhdm xzqh : list) {
                sb.append(xzqh.getId() + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
            ret = sb.toString();
        }

        return ret;
    }

    private List getListVal(CZdXzqhdm xzqhdm) {
        List list = new ArrayList<>();
        if (xzqhdm != null) {
            String totalxzqh = "";
            boolean ifroot = this.xzqhdmService.ifNodeRoot(xzqhdm);
            if (ifroot) {//省xzqh
                List<CZdXzqhdm> shi = this.xzqhdmService.findChildrenNodes(xzqhdm).getData();
                if (shi != null && shi.size() > 0) {
                    for (CZdXzqhdm shixzqh : shi) {//处理市
                        BaseQueryRecords<CZdXzqhdm> temp = this.xzqhdmService.findChildrenNodes(shixzqh);
                        String sqlXzqh = getSqlXzqh(temp);
                        List records = this.appurtenanceDao.zhcxApp(sqlXzqh);
                        setListVal(shixzqh, list, records);
                        if (!"".equals(sqlXzqh)) {
                            totalxzqh = totalxzqh + sqlXzqh + ",";
                        }
                    }
                    if (!"".equals(totalxzqh)) {
                        totalxzqh = totalxzqh.substring(0, totalxzqh.length() - 1);
                    }
                }
            } else {
                boolean ifleaf = this.xzqhdmService.ifNodeLeaf(xzqhdm);
                if (!ifleaf) {//市xzqh
                    BaseQueryRecords<CZdXzqhdm> zhanbqr = this.xzqhdmService.findChildrenNodes(xzqhdm);
                    List<CZdXzqhdm> zhan = zhanbqr.getData();
                    if (zhan != null && zhan.size() > 0) {
                        for (CZdXzqhdm zhanxzqh : zhan) {
                            List records = this.appurtenanceDao.zhcxApp(String.valueOf(zhanxzqh.getId()));
                            setListVal(zhanxzqh, list, records);
                        }
                        totalxzqh = getSqlXzqh(zhanbqr);
                    }
                } else {//站
                    totalxzqh = String.valueOf(xzqhdm.getId());
                    List records = this.appurtenanceDao.zhcxApp(totalxzqh);
                    setListVal(xzqhdm, list, records);
                }
            }
            if (!"".equals(totalxzqh)) {
                List records = this.appurtenanceDao.zhcxApp(totalxzqh);
                CZdXzqhdm heji = new CZdXzqhdm();
                heji.setId(xzqhdm.getId());
                heji.setName("合计");
                setListVal(heji, list, records);
            } else {
                setListVal(xzqhdm, list, null);
            }
        }
        return list;
    }

    private void setListVal(CZdXzqhdm xzqh, List ret, List list) {

        if (list != null && list.size() > 0) {
            String[] arr = {"-1", "合计", "", "", "", "", "", "", ""};
            arr[0] = String.valueOf(xzqh.getId());
            arr[1] = xzqh.getName();
            int sum = 0;
            Object[] obj = (Object[]) list.get(0);
            for (int i = 0; i < 6; i++) {
                arr[2 + i] = String.valueOf(obj[i]);
                sum += Integer.parseInt(String.valueOf(obj[i]));
            }
            arr[8] = String.valueOf(sum);
            ret.add(arr);
        } else {
            String[] arr = {"-1", "合计", "0", "0", "0", "0", "0", "0", "0"};
            arr[0] = String.valueOf(xzqh.getId());
            arr[1] = xzqh.getName();
            ret.add(arr);
        }
    }

    private String setStringval(Object o) {
        String s = "";
        if (o != null) {
            s = String.valueOf(o);
        }
        return s;
    }

    private Integer setIntegerval(Object o) {
        Integer i = 0;
        if (o != null) {
            String temp = String.valueOf(o);
            if (!"".equals(temp)) {
                i = Integer.parseInt(temp);
            }
        }
        return i;
    }

    private Double setDoubleval(Object o) {
        Double d = 0.0;
        if (o != null) {
            String temp = String.valueOf(o);
            if (!"".equals(temp)) {
                d = Double.parseDouble(temp);
            }
        }
        return d;
    }

    @Override
    public BaseResult importQl() {
        //改一个市级编号和like的值
        List<Object[]> list = this.appurtenanceDao.importQl();
        List<String> strlist = new ArrayList<String>();
        Date now = new Date();
        for (Object[] b : list) {
            CHdQl ql = new CHdQl();
            String idid = setStringval(b[0]);
            String hdaobh = setStringval(b[2]);
            CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoByHdBh(hdaobh);
            ql.setSshdaoid(hdao.getId());
            CHdHduanjcxx hduan = this.hangDuanDao.queryHangDuanByQdzdSshdid(hdao.getId(), setStringval(b[16]), setStringval(b[17]));
            if (hduan == null) {
                strlist.add(idid);
            } else {
                ql.setFswlx(10);
                ql.setSshduanid(hduan.getId());
                ql.setSzxzqh(hduan.getHdszxzqh());
                ql.setBh(setStringval(b[3]));
                ql.setMc(setStringval(b[18]));
                ql.setJcnf(setStringval(b[19]));
                ql.setGljgmc(setStringval(b[20]));

                ql.setSfdb(setIntegerval(b[22]));
                ql.setThjg(setDoubleval(b[23]));
                ql.setSffcfhds(setIntegerval(b[27]));
                ql.setSjzgthsw(setDoubleval(b[28]));
                ql.setSjzgthswhscxq(setDoubleval(b[29]));
                ql.setJgxs(setIntegerval(b[30]));
                ql.setYtfl(setIntegerval(b[31]));
                ql.setThks(setIntegerval(b[32]));
                ql.setZjny("");
                ql.setCreater(62);
                ql.setCreatetime(now);
                ql.setUpdatetime(now);

                ql.setQmc(0.0);
                ql.setQmk(0.0);
                ql.setYwbz(0);
                ql.setLdbg(0.0);
                ql.setThjk(0.0);
                ql.setKyjk(0.0);
                ql.setKejk(0.0);
                ql.setKsanjk(0.0);
                ql.setKsijk(0.0);
                ql.setKwjk(0.0);
                ql.setFxysljj(0.0);
                ql.setLghhddj("");
                ql.setLghyqjk(0.0);
                ql.setLghyqjg(0.0);
                ql.setXbghhddj("");
                ql.setXbghyqjk(0.0);
                ql.setXbghyqjg(0.0);
                ql.setSffhhddj(1);
                ql.setSfmzlgh(1);
                ql.setSfmzxbgh(1);
                ql.setJzzj(0.0);
                ql.setCssw(0.0);
                ql.setYwfzss(0);
                ql.setXxdz("");
                ql.setZxlczh("");
                ql.setJd(0.0);
                ql.setWd(0.0);
                ql.setGljglxr("");
                ql.setGljglxdh("");
                ql.setGljgszxzqh(0);
                ql.setBz("");
                //this.appurtenanceDao.addBridge(ql);
            }
        }
        return new BaseResultOK(strlist);
    }

    @Override
    public BaseResult importLx() {
        //改一个市级编号和like的值
        List<Object[]> list = this.appurtenanceDao.importLx();
        List<String> strlist = new ArrayList<String>();
        Date now = new Date();
        for (Object[] b : list) {
            CHdLx lx = new CHdLx();
            String idid = setStringval(b[0]);
            String hdaobh = setStringval(b[2]);
            CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoByHdBh(hdaobh);
            CHdHduanjcxx hduan = this.hangDuanDao.queryHangDuanByQdzdSshdid(hdao.getId(), setStringval(b[16]), setStringval(b[17]));
            if (hduan == null) {
                strlist.add(idid);
            } else {
                lx.setFswlx(12);
                lx.setSshdaoid(hdao.getId());
                lx.setSshduanid(hduan.getId());
                lx.setSzxzqh(hduan.getHdszxzqh());
                lx.setBh(setStringval(b[3]));
                lx.setMc(setStringval(b[18]));
                lx.setJcnf(setStringval(b[19]));
                lx.setGljgmc(setStringval(b[20]));
                lx.setSfdb(setIntegerval(b[22]));
                lx.setSffcfhds(setIntegerval(b[27]));
                lx.setCreater(62);
                lx.setCreatetime(now);
                lx.setUpdatetime(now);

                lx.setDbszsd(0.0);
                lx.setYwbz(0);
                lx.setJsdw("");
                lx.setZl(0);
                lx.setKj(0.0);
                lx.setJg(setStringval(b[24]));
                lx.setCssw(0.0);
                lx.setYwfzss(0);
                lx.setYtfl("");
                lx.setXxdz("");
                lx.setZxlczh("");
                lx.setJd(0.0);
                lx.setWd(0.0);
                lx.setZajl(0.0);
                lx.setZajd(0.0);
                lx.setZawd(0.0);
                lx.setYajl(0.0);
                lx.setYajd(0.0);
                lx.setYawd(0.0);
                lx.setGljglxr("");
                lx.setGljglxdh("");
                lx.setGljgszxzqh(0);
                lx.setBz("");
                //this.appurtenanceDao.addCable(lx);
            }
        }
        return new BaseResultOK(strlist);
    }

    @Override
    public BaseResult importKymt() {
        //改一个市级编号和like的值
        List<Object[]> list = this.appurtenanceDao.importKymt();
        List<String> strlist = new ArrayList<String>();
        Date now = new Date();
        for (Object[] b : list) {
            CHdKymt kymt = new CHdKymt();
            String idid = setStringval(b[0]);
            String hdaobh = setStringval(b[2]);
            CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoByHdBh(hdaobh);
            CHdHduanjcxx hduan = this.hangDuanDao.queryHangDuanByQdzdSshdid(hdao.getId(), setStringval(b[4]), setStringval(b[5]));
            if (hduan == null) {
                strlist.add(idid);
            } else {
                kymt.setFswlx(15);
                kymt.setSshdaoid(hdao.getId());
                kymt.setSshduanid(hduan.getId());
                kymt.setSzxzqh(hduan.getHdszxzqh());
                kymt.setBh(setStringval(b[3]));
                kymt.setMc(setStringval(b[6]));
                kymt.setZyaxcd(setDoubleval(b[9]));
                kymt.setAb(setIntegerval(b[10]));
                kymt.setSfah(setIntegerval(b[11]));
                kymt.setCreater(62);
                kymt.setCreatetime(now);
                kymt.setUpdatetime(now);

                kymt.setJglx(0);
                kymt.setTkdj(0.0);
                kymt.setLx("");
                kymt.setBws(0);
                kymt.setSfwxpmt(0);
                kymt.setYysj(now);
                kymt.setHwzl("");
                kymt.setSfzsyz(1);
                kymt.setKbnl(0);
                kymt.setXlzsl(0);
                kymt.setLthxsl(0);
                kymt.setSffcfhds(0);
                kymt.setGsksl(0);
                kymt.setZmdsl(0);
                kymt.setCdsssl(0);
                kymt.setJcnf("");
                kymt.setSsqdlczh("");
                kymt.setZyax(0.0);
                kymt.setZyaxqdzbx(0.0);
                kymt.setZyaxqdzby(0.0);
                kymt.setZyaxzdzbx(0.0);
                kymt.setZyaxzdzby(0.0);
                kymt.setJd(0.0);
                kymt.setWd(0.0);
                kymt.setJcnf("");
                kymt.setGljgmc("");
                kymt.setGljglxr("");
                kymt.setGljglxdh("");
                kymt.setGljgszxzqh(0);
                kymt.setBz("");
                //this.appurtenanceDao.addKyDock(kymt);
            }
        }
        return new BaseResultOK(strlist);
    }

    @Override
    public BaseResult importSn() {
        //改一个市级编号和like的值
        List<Object[]> list = this.appurtenanceDao.importSn();
        List<String> strlist = new ArrayList<String>();
        Date now = new Date();
        for (Object[] b : list) {
            CHdSn sn = new CHdSn();
            String idid = setStringval(b[0]);
            String hdaobh = setStringval(b[2]);
            CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoByHdBh(hdaobh);
            CHdHduanjcxx hduan = this.hangDuanDao.queryHangDuanByQdzdSshdid(hdao.getId(), setStringval(b[17]), setStringval(b[18]));
            if (hduan == null) {
                strlist.add(idid);
            } else {
                sn.setFswlx(23);
                sn.setSshdaoid(hdao.getId());
                sn.setSshduanid(hduan.getId());
                sn.setSzxzqh(hduan.getHdszxzqh());
                sn.setCreater(62);
                sn.setCreatetime(now);
                sn.setUpdatetime(now);
                sn.setBh(setStringval(b[3]));
                sn.setMc(setStringval(b[19]));
                sn.setJcnf(setStringval(b[20]));
                sn.setGljgmc(setStringval(b[21]));
                sn.setSjsj(setDoubleval(b[23]));
                sn.setSjzgthswhslc(setDoubleval(b[24]));
                sn.setSjzdthswhslc(setDoubleval(b[25]));
                sn.setSfjg(setIntegerval(b[27]));
                //88是船闸
                sn.setThlx(88);
                sn.setThcbdj(setDoubleval(b[29]));
                sn.setSjndxhwtgl(setDoubleval(b[30]));
                sn.setSjndxlktgl(setDoubleval(b[31]));
                sn.setSfjyql(setIntegerval(b[32]));
                sn.setThjg(setDoubleval(b[33]));
                sn.setSyqk(setIntegerval(b[34]));
                sn.setTgnlsfpp(setIntegerval(b[35]));
                sn.setDnsxhwtgl(setDoubleval(b[36]));
                sn.setDnxxhwtgl(setDoubleval(b[37]));
                sn.setDnsxlktgl(setDoubleval(b[38]));
                sn.setDnxxlktgl(setDoubleval(b[39]));
                sn.setSyyhdcd(setDoubleval(b[40]));
                sn.setXyyhdcd(setDoubleval(b[41]));
                sn.setSysjzgthsw(setDoubleval(b[42]));
                sn.setSysjzdthsw(setDoubleval(b[43]));
                sn.setXysjzgthsw(setDoubleval(b[44]));
                sn.setXysjzdthsw(setDoubleval(b[45]));
                sn.setScjsfjg(setIntegerval(b[46]));
                sn.setCzsfjg(setIntegerval(b[47]));
                sn.setXs(setIntegerval(b[48]));
                sn.setXjc(setDoubleval(b[49]));
                sn.setKd(setDoubleval(b[50]));
                sn.setSs(setDoubleval(b[51]));
                sn.setYxczcd(0.0);
                sn.setYxczkd(0.0);
                sn.setYxczzskmkd(0.0);
                sn.setYxczzkss(0.0);
                sn.setExczcd(0.0);
                sn.setExczkd(0.0);
                sn.setExczzskmkd(0.0);
                sn.setExczzkss(0.0);
                sn.setYlczcd(0.0);
                sn.setYlczkd(0.0);
                sn.setYlczzskmkd(0.0);
                sn.setYlczzkss(0.0);
                sn.setZmkd(0.0);
                sn.setMkss(0.0);
                sn.setKs(0);
                sn.setJg(0.0);
                sn.setZxlczh("");
                sn.setJd(0.0);
                sn.setWd(0.0);
                sn.setGcsswz(0);
                sn.setGljglxr("");
                sn.setGljglxdh("");
                sn.setGljgszxzqh(hduan.getHdszxzqh());
                sn.setBz("");
                //  this.appurtenanceDao.addHub(sn);
            }
        }
        return new BaseResultOK(strlist);
    }

    @Override
    public BaseResult exportFswInfo(int id, int fswlx) {
        BaseResultOK baseResultOK = new BaseResultOK();
        String outfilepath = "";
        File inputfile = null;
        File outputfile = null;
        String appname = "";
        switch (fswlx) {
            case Constants.APP_NAVIGATIONMARK:
                appname = "航标";
                break;
            case Constants.APP_BRIDGE:
                appname = "桥梁";
                break;
            case Constants.APP_AQUEDUCT:
                appname = "渡槽";
                break;
            case Constants.APP_CABLE:
                appname = "缆线";
                break;
            case Constants.APP_PIPELINE:
                appname = "管道";
                break;
            case Constants.APP_TUNNEL:
                appname = "隧道";
                break;
            case Constants.APP_KYDOCK:
                appname = "客运码头";
                break;
            case Constants.APP_HYDOCK:
                appname = "货运码头";
                break;
            case Constants.APP_GWDOCK:
                appname = "公务码头";
                break;
            case Constants.APP_SHIPYARD:
                appname = "船厂";
                break;
            case Constants.APP_TAKEOUTFALL:
                appname = "取排水";
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                appname = "水文站";
                break;
            case Constants.APP_MANAGEMENTSTATION:
                appname = "管理站";
                break;
            case Constants.APP_SERVICEAREA:
                appname = "服务区";
                break;
            case Constants.APP_MOORINGAREA:
                appname = "锚泊区";
                break;
            case Constants.APP_HUB:
                appname = "枢纽";
                break;
            case Constants.APP_DAM:
                appname = "坝";
                break;
            case Constants.APP_REGULATIONREVEMENT:
                appname = "整治护岸";
                break;
            case Constants.APP_LASEROBSERVATION:
                appname = "激光观测点";
                break;
            case Constants.APP_VIDEOOBSERVATION:
                appname = "视频观测点";
                break;
            case Constants.APP_MANUALOBSERVATION:
                appname = "人工观测点";
                break;
            case Constants.APP_BOLLARD:
                appname = "系缆桩";
                break;
            default:
                break;
        }
        try {
            String path = this.getRealPath("/");
            inputfile = new File(path + "/template/详情.xlsx");
            String filename = inputfile.getName();
            StringBuffer sbf = new StringBuffer(filename);
            sbf.insert(sbf.indexOf("."),
                    DateTimeUtil.getTimeFmt(new Date(), "yyyyMMddHHmmss"));
            outfilepath = path + "baobiao\\" + appname + sbf.toString();
            FileUtils.writeToFile(inputfile, outfilepath);
            outputfile = new File(outfilepath);
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                    outputfile));
            XSSFSheet sheet = workbook.getSheet("详情");
            CellStyle cellStyle = workbook.createCellStyle(); // 样式
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

            BaseResult fswret = this.queryAppurtenanceInfo(id, fswlx);
            if (fswret != null) {
                ArrayList<List<Object>> arr = (ArrayList<List<Object>>) fswret.getObj();
                if (arr != null) {
                    sheet.getRow(0).getCell(0).setCellValue(appname);
                    int n = 4;
                    for (List<Object> list : arr) {
                        int listlen = list.size();
                        switch (listlen) {
                            case 1:
                                addCell(n, n + 1, 0, 1, sheet, cellStyle);
                                sheet.addMergedRegion(new CellRangeAddress(n, n + 1, 0, 1));
                                break;
                            case 2:
                                addCell(n, n + 1, 0, 7, sheet, cellStyle);
                                sheet.addMergedRegion(new CellRangeAddress(n, n + 1, 0, 1));
                                sheet.addMergedRegion(new CellRangeAddress(n, n + 1, 2, 7));
                                break;
                            default:
                                addCell(n, n + 1, 0, 7, sheet, cellStyle);
                                sheet.addMergedRegion(new CellRangeAddress(n, n + 1, 0, 1));
                                sheet.addMergedRegion(new CellRangeAddress(n, n + 1, 2, 3));
                                sheet.addMergedRegion(new CellRangeAddress(n, n + 1, 4, 5));
                                sheet.addMergedRegion(new CellRangeAddress(n, n + 1, 6, 7));
                                break;
                        }
                        for (int i = 0; i < listlen; i++) {
                            String temp = String.valueOf(list.get(i));
                            sheet.getRow(n).getCell(2 * i).setCellValue(temp);
                        }
                        n = n + 2;
                    }
                }
            }

            FileOutputStream outfile = new FileOutputStream(outputfile);
            workbook.write(outfile);
            outfile.close();
            logService.addLog(ModuleName.HDXXWH, OpName.EXPORT, appname);
            baseResultOK.addToMap("filename", outputfile.getName());
            baseResultOK.addToMap("filepath", outfilepath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseResultOK;
    }

}
