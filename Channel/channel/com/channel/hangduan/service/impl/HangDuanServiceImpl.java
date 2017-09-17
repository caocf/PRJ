package com.channel.hangduan.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.channel.appurtenance.dao.impl.AppurtenanceGroup;
import com.channel.bean.HdaoBean;
import com.channel.hangdao.dao.HangDaoDao;
import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import com.channel.model.hd.CHdHdaojcxx;
import com.common.dao.BaseQueryRecords;
import com.common.utils.FileUtils;
import com.common.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.channel.appurtenance.dao.impl.AppurtenanceResult;
import com.channel.appurtenance.service.AppurtenanceService;
import com.channel.bean.Constants;
import com.channel.bean.HangDuanDT;
import com.channel.bean.PropDescFields;
import com.channel.dic.dao.DicDao;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.dic.service.XzqhdmService;
import com.channel.hangduan.dao.HangDuanDao;
import com.channel.hangduan.service.HangDuanService;
import com.channel.model.hd.CHdHduanjcxx;
import com.channel.model.user.CXtDpt;
import com.channel.model.user.CZdXzqhdm;
import com.channel.user.dao.UserDao;
import com.channel.user.dao.impl.DptDaoImpl;
import com.channel.user.service.DptService;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;

@Service("hangduanservice")
public class HangDuanServiceImpl extends BaseService implements HangDuanService {
    @Resource(name = "hangduandao")
    private HangDuanDao hangDuanDao;
    @Resource(name = "hangdaodao")
    private HangDaoDao hangDaoDao;
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "dicdao")
    private DicDao dicDao;
    @Resource(name = "appurtenanceservice")
    private AppurtenanceService appurtenanceService;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmdao;
    @Resource(name = "xzqhdmservice")
    private XzqhdmService xzqhdmService;
    @Resource(name = "dptdao")
    private DptDaoImpl dptdao;
    @Resource(name = "dptservice")
    private DptService dptService;
    @Resource
    private LogService logService;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmDaoImpl;

    @Override
    public BaseResult queryMaxHdbh(Integer sshdid) {
        CHdHduanjcxx records = this.hangDuanDao.queryMaxHdbh(sshdid);
        String str = "";
        if (records != null && !"".equals(records.getHdbh())) {
            long returnhdbh = Long.parseLong(records.getHdbh()) + 1;
            if (returnhdbh > 99999999999.0) {
                for (long i = 1; i < 100000000000.0; i++) {
                    String tempstr = String.valueOf(i);
                    int tempn = tempstr.length();
                    for (int j = tempn; j < 10; j++) {
                        tempstr = "0" + tempstr;
                    }
                    tempstr = "1" + tempstr;
                    boolean ifexisted = this.hangDuanDao.queryHdbhExisted(tempstr, sshdid);
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
                str = "1" + str;
            }
        } else {
            str = "10000000001";
        }
        BaseResultOK baseResultOK = new BaseResultOK(str);
        return baseResultOK;
    }

    @Override
    public BaseResult queryHdbhExisted(String hdbh, Integer sshdid) {
        Boolean records = this.hangDuanDao.queryHdbhExisted(hdbh, sshdid);
        if (records == true)
            return new BaseResultOK();
        else
            return new BaseResultFailed();
    }


    @Override
    public BaseResult searchAllHangDuan(int xzqhid, int sshdid, int hddj, String content) {
        BaseQueryRecords<CHdHduanjcxx> records = null;
        CZdXzqhdm xzqh = this.xzqhdmdao.queryXzqhdmById(xzqhid);
        if (xzqhid == -1) {
            records = this.hangDuanDao.searchAllHangDuan(null, sshdid, hddj, content);
        } else {
            List<CZdXzqhdm> xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqh);
            xzqhs.add(xzqh);
            records = this.hangDuanDao.searchAllHangDuan(xzqhs, sshdid, hddj, content);
        }
        BaseResultOK baseResultOK = new BaseResultOK(records);
        return baseResultOK;
    }

    @Override
    public BaseResult searchHangDuanHddj(int xzqhid, Integer sshdid, int hddj, String content) {
        List<HangDuanDT> list = new ArrayList<HangDuanDT>();
        List<CHdHduanjcxx> records = new ArrayList<>();
        CZdXzqhdm xzqh = this.xzqhdmdao.queryXzqhdmById(xzqhid);
        if (xzqhid == -1) {
            records = this.hangDuanDao
                    .searchHangDuanHddj(sshdid, null, hddj, content).getData();
        } else {
            if (xzqh != null) {
                List<CZdXzqhdm> xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqh);
                xzqhs.add(xzqh);
                records = this.hangDuanDao
                        .searchHangDuanHddj(sshdid, xzqhs, hddj, content).getData();
            }
        }

        if (records != null && records.size() > 0) {
            for (CHdHduanjcxx cHdHduanjcxx : records) {
                CZdXzqhdm xzqhdm = this.xzqhdmdao
                        .queryXzqhdmById(cHdHduanjcxx.getHdszxzqh());
                HangDuanDT hangduanxzqh = null;
                if (xzqhdm != null) {
                    hangduanxzqh = new HangDuanDT(cHdHduanjcxx, xzqhdm.getName());
                } else {
                    hangduanxzqh = new HangDuanDT(cHdHduanjcxx, "");
                }
                list.add(hangduanxzqh);
            }
        }
        BaseResultOK baseResultOK = new BaseResultOK(list);
        return baseResultOK;
    }

    private List getListVal(CZdXzqhdm xzqh, int sshdid, int hddj, String content, List list) {
        List total = new ArrayList();
        if (xzqh.getId() == -1) {
            list = this.hangDuanDao.countAppByHdid(sshdid, null, hddj, content);
            total = this.hangDuanDao.countTotalByHdid(sshdid, null, hddj, content);
        } else {
            if (xzqh != null) {
                List<CZdXzqhdm> xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqh);
                xzqhs.add(xzqh);
                list = this.hangDuanDao.countAppByHdid(sshdid, xzqhs, hddj, content);
                total = this.hangDuanDao.countTotalByHdid(sshdid, xzqhs, hddj, content);
            }
        }
        list.add(total.get(0));
        return list;
    }

    @Override
    public BaseResult zhcxHduan(int xzqhid, int sshdid, int hddj, String content) {
        BaseQueryRecords records = new BaseQueryRecords();
        List list = new ArrayList();
        CZdXzqhdm xzqh = this.xzqhdmdao.queryXzqhdmById(xzqhid);
        list = getListVal(xzqh, sshdid, hddj, content, list);
        records.setData(list);
        BaseResultOK baseResultOK = new BaseResultOK(records);
        baseResultOK.addToMap("dept", xzqh.getName());
        return baseResultOK;
    }

    private void addCell(int srow, int erow, XSSFSheet sheet, CellStyle cellStyle) {
        for (int j = srow; j < erow; j++) {
            sheet.createRow(j);
            XSSFRow row = sheet.getRow(j);
            for (int k = 0; k < 14; k++) {
                row.createCell(k).setCellStyle(cellStyle);
            }
        }
    }

    @Override
    public BaseResult exportHduan(int xzqhid, Integer sshdid) {
        List list = new ArrayList();
        CZdXzqhdm xzqh = this.xzqhdmdao.queryXzqhdmById(xzqhid);
        list = getListVal(xzqh, sshdid, -1, "", list);
        BaseResultOK baseResultOK = new BaseResultOK();
        String outfilepath = "";
        File inputfile = null;
        File outputfile = null;
        try {
            String path = this.getRealPath("/");
            inputfile = new File(path + "/template/航段列表.xlsx");
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

            XSSFSheet sheet = workbook.getSheet("航段列表");
            sheet.getRow(0).getCell(0).setCellValue(xzqh.getName() + "航段列表");
            if (list != null && list.size() > 0) {
                int len = list.size();
                int n = 6;
                int totalrow = n + len;
                // 航道名称 合计cell
                addCell(n, totalrow, sheet, cellStyle);
                for (int j = n; j < totalrow; j++) {
                    sheet.addMergedRegion(new CellRangeAddress(j, j, 0, 1));
                    sheet.addMergedRegion(new CellRangeAddress(j, j, 2, 3));
                }
                for (int i = 0; i < len; i++) {
                    if (i == len - 1) {
                        Object[] total = (Object[]) list.get(i);
                        sheet.getRow(n + i).getCell(0).setCellValue("合计");
                        sheet.getRow(n + i).getCell(8).setCellValue(StringUtil.getStringIsNullObj(total[0]));
                        sheet.getRow(n + i).getCell(9).setCellValue(StringUtil.getStringIsNullObj(total[1]));
                        sheet.getRow(n + i).getCell(10).setCellValue(StringUtil.getStringIsNullObj(total[2]));
                        sheet.getRow(n + i).getCell(11).setCellValue(StringUtil.getStringIsNullObj(total[3]));
                        sheet.getRow(n + i).getCell(12).setCellValue(StringUtil.getStringIsNullObj(total[4]));
                        sheet.getRow(n + i).getCell(13).setCellValue(StringUtil.getStringIsNullObj(total[5]));
                    } else {
                        Object[] arr = (Object[]) list.get(i);
                        sheet.getRow(n + i).getCell(0).setCellValue(StringUtil.getStringIsNullObj(arr[1]));
                        sheet.getRow(n + i).getCell(2).setCellValue(StringUtil.getStringIsNullObj(arr[2]));
                        sheet.getRow(n + i).getCell(4).setCellValue(StringUtil.getStringIsNullObj(arr[3]));
                        sheet.getRow(n + i).getCell(5).setCellValue(StringUtil.getStringIsNullObj(arr[4]));
                        sheet.getRow(n + i).getCell(6).setCellValue(StringUtil.getStringIsNullObj(arr[5]));
                        sheet.getRow(n + i).getCell(7).setCellValue(StringUtil.getStringIsNullObj(arr[6]));
                        sheet.getRow(n + i).getCell(8).setCellValue(StringUtil.getStringIsNullObj(arr[7]));
                        sheet.getRow(n + i).getCell(9).setCellValue(StringUtil.getStringIsNullObj(arr[8]));
                        sheet.getRow(n + i).getCell(10).setCellValue(StringUtil.getStringIsNullObj(arr[9]));
                        sheet.getRow(n + i).getCell(11).setCellValue(StringUtil.getStringIsNullObj(arr[10]));
                        sheet.getRow(n + i).getCell(12).setCellValue(StringUtil.getStringIsNullObj(arr[11]));
                        sheet.getRow(n + i).getCell(13).setCellValue(StringUtil.getStringIsNullObj(arr[12]));
                    }
                }
            }
            FileOutputStream outfile = new FileOutputStream(outputfile);
            workbook.write(outfile);
            outfile.close();
            logService.addLog(ModuleName.ZHCX, OpName.EXPORT, "航段列表");
            baseResultOK.addToMap("filename", outputfile.getName());
            baseResultOK.addToMap("filepath", outfilepath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseResultOK;
    }

    @Override
    public BaseResult zhcxHduanInfo(int xzqhid, Integer sshdid, int hddj, String content, int page, int rows) {
        BaseQueryRecords records = new BaseQueryRecords();
        List ret = new ArrayList<>();
        CZdXzqhdm xzqh = this.xzqhdmdao.queryXzqhdmById(xzqhid);
        if (xzqhid == -1) {
            records = this.hangDuanDao.zhcxHduanInfo(sshdid, null, hddj, content, page, rows);
            ret = this.hangDuanDao.countAppByHdid(sshdid, null, hddj, content, page, rows);
        } else {
            if (xzqh != null) {
                List<CZdXzqhdm> xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqh);
                xzqhs.add(xzqh);
                records = this.hangDuanDao.zhcxHduanInfo(sshdid, xzqhs, hddj, content, page, rows);
                ret = this.hangDuanDao.countAppByHdid(sshdid, xzqhs, hddj, content, page, rows);
            }
        }
        records.setData(ret);
        BaseResultOK baseResultOK = new BaseResultOK(records);
        return baseResultOK;
    }

    @Override
    public BaseResult importHduan() {
        List<String> strlist = new ArrayList<String>();
        List<Object[]> list = this.hangDuanDao.importHduan();
        Date now = new Date();
        for (Object[] b : list) {
            CHdHduanjcxx h = new CHdHduanjcxx();
            String idid = setStringval(b[0]);
            String hdbh = setStringval(b[2]);
            String hduanbh = setStringval(b[3]);
            int sshdid = 0;
            CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoByHdBh(hdbh);
            if (hdao != null) {
                sshdid = hdao.getId();
            } else {
                strlist.add(idid);
            }
          /*   boolean flag = this.hangDuanDao.queryHdbhExisted(hduanbh, sshdid);
            if (flag) {*/
            h.setSshdid(sshdid);
            h.setHdbh(hduanbh);
            //对照行政区划表 先字典里面添加
            h.setHdszxzqh(setIntegerval(b[4]));
            h.setHdqdmc(setStringval(b[5]));
            h.setQdsfwzkd(setIntegerval(b[6]));
            h.setQdsfwfjd(setIntegerval(b[7]));
            h.setQdfjdlb(setStringval(b[8]));
            h.setHdzdmc(setStringval(b[9]));
            h.setZdsfwzkd(setIntegerval(b[10]));
            h.setZdsfwfjd(setIntegerval(b[11]));
            h.setZdfjdlx(setStringval(b[12]));
            h.setSfwjhhd(setIntegerval(b[13]));
            h.setFjhdlx(setStringval(b[14]));
            h.setSfyfcfhd(setIntegerval(b[15]));
            h.setSfyqt(setIntegerval(b[16]));
            int sfcf = setIntegerval(b[17]);
            if (sfcf == 1) {
                h.setCflc(setDoubleval(b[18]));
            } else {
                h.setCflc(0.0);
            }
            h.setHdlc(setDoubleval(b[18]));
            h.setHdss(setDoubleval(b[19]));
            h.setHdkd(setDoubleval(b[20]));
            h.setHdsscjsj(now);

            h.setHdzxwqbj(setDoubleval(b[21]));
            int dj = setIntegerval(b[22]);
            h.setHdxzjsdj(dj);
            h.setHddj(dj + 70);
            h.setHddjjsdj(setIntegerval(b[23]));
            h.setHdsx(setIntegerval(b[24]));
            //手动懒得加了
            h.setGljgbh(setIntegerval(b[25]));
            h.setGljgszxzqh(setIntegerval(b[26]));
            h.setHdsfth(setIntegerval(b[27]));
            h.setZdthswbzl(0.0);
            h.setGhccthhcdbcxzzd(0.0);
            h.setGhjzzxthjg(0.0);
            h.setGhjzzxthjk(0.0);
            h.setGhjzmkss(0.0);
            h.setGhjzxk(0.0);
            h.setHldm("");
            h.setHlmc("");
            h.setHdqdlczh("");
            h.setHdzdlczh("");
            h.setHdqdzbx(0.0);
            h.setHdqdzby(0.0);
            h.setHdzdzbx(0.0);
            h.setHdzdzby(0.0);
            h.setSygllx(0);
            h.setGljglxr("");
            h.setGljglxdh("");
            h.setCreatetime(now);
            h.setUpdatetime(now);
            double dbzl = setDoubleval(b[28]);
            String bzl = "";
            if (dbzl < 1) {
                dbzl = dbzl * 100;
            } else {
                dbzl = dbzl;
            }
            bzl = String.valueOf(dbzl) + "%";
            h.setWhssbzl(bzl);
            h.setSfzyhd(setIntegerval(b[29]));
            h.setSfjjxhd(setIntegerval(b[30]));
            h.setHdwhlb(setIntegerval(b[31]));
            h.setHbpblb(setStringval(b[32]));
            h.setCcthhcdbcxzzd(setDoubleval(b[33]));
            h.setBz(setStringval(b[34]));
            //this.hangDuanDao.addHangDuan(h);
          /*  }else{
                strlist.add(idid);
            }*/
        }
        return new BaseResultOK(strlist);
    }

    @Override
    public BaseResult exportHduanInfo(int id) {
        BaseResultOK baseResultOK = new BaseResultOK();
        String outfilepath = "";
        File inputfile = null;
        File outputfile = null;
        try {
            String path = this.getRealPath("/");
            inputfile = new File(path + "/template/详情.xlsx");
            String filename = inputfile.getName();
            StringBuffer sbf = new StringBuffer(filename);
            sbf.insert(sbf.indexOf("."),
                    DateTimeUtil.getTimeFmt(new Date(), "yyyyMMddHHmmss"));
            outfilepath = path + "baobiao\\" + "航段" + sbf.toString();
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

            BaseResult hduanret = this.queryHangDuanInfo(id);
            if (hduanret != null) {
                ArrayList<List<Object>> arr = (ArrayList<List<Object>>) hduanret.getObj();
                if (arr != null) {
                    sheet.getRow(0).getCell(0).setCellValue("航段详情");
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
            logService.addLog(ModuleName.HDXXWH, OpName.EXPORT, "航段详情");
            baseResultOK.addToMap("filename", outputfile.getName());
            baseResultOK.addToMap("filepath", outfilepath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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


    private void setListVal(List<String> temp, Object[] arr) {
        if (arr != null && arr.length > 0) {
            for (Object o : arr) {
                String str = String.valueOf(o);
                if ("0".equals(str)) {
                    temp.add("");
                } else {
                    temp.add(str);
                }
            }
        }
    }

    @Override
    public BaseResult queryHangDuanBySshdid(int sshdid, int xzqhid) {
        List<HangDuanDT> list = new ArrayList<HangDuanDT>();
        List<CHdHduanjcxx> records = new ArrayList<>();
        CZdXzqhdm xzqh = this.xzqhdmdao.queryXzqhdmById(xzqhid);
        if (xzqhid == -1) {
            records = this.hangDuanDao
                    .queryHangDuanBySshdid(sshdid, null).getData();
        } else {
            if (xzqh != null) {
                List<CZdXzqhdm> xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqh);
                xzqhs.add(xzqh);
                records = this.hangDuanDao
                        .queryHangDuanBySshdid(sshdid, xzqhs).getData();
            }
        }

        if (records != null && records.size() > 0) {
            for (CHdHduanjcxx cHdHduanjcxx : records) {
                CZdXzqhdm xzqhdm = this.xzqhdmdao
                        .queryXzqhdmById(cHdHduanjcxx.getHdszxzqh());
                HangDuanDT hangduanxzqh = null;
                if (xzqhdm != null) {
                    hangduanxzqh = new HangDuanDT(cHdHduanjcxx, xzqhdm.getName());
                } else {
                    hangduanxzqh = new HangDuanDT(cHdHduanjcxx, "");
                }
                list.add(hangduanxzqh);
            }
        }
        BaseResultOK baseResultOK = new BaseResultOK(list);
        return baseResultOK;
    }

    @Override
    public BaseResult queryHangDuanByXzqh(int xzqhid) {
        List records = new ArrayList<>();
        CZdXzqhdm xzqh = this.xzqhdmdao.queryXzqhdmById(xzqhid);
        if (xzqhid == 1) {
            records = this.hangDuanDao.queryHangDuanByXzqh(null).getData();
        } else {
            if (xzqh != null) {
                List xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqh);
                xzqhs.add(xzqh);
                records = this.hangDuanDao.queryHangDuanByXzqh(xzqhs).getData();
            }
        }
        BaseResultOK baseResultOK = new BaseResultOK(records);
        return baseResultOK;
    }


    @Override
    public BaseResult queryHangDuanInfo(int id) {
        CHdHduanjcxx hangduan = this.hangDuanDao.queryHangDuanById(id);
        if (hangduan == null) {
            return new BaseResultFailed(Constants.RESULT_HDUAN_NOTEXIST,
                    "航段不存在");
        } else {
            String hdbh = hangduan.getHdbh();
            // HDuanStatistic statistic = this.queryHDuanStatistic(hdbh);
            AppurtenanceResult result = new AppurtenanceResult();
            result.setModelobj(hangduan);

            AppurtenanceGroup jbxxgroup = new AppurtenanceGroup("基本信息");
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdbh"), this.hangDaoDao.queryHangDaoByHdid(hangduan.getSshdid()).getHdbh() + hangduan.getHdbh());
            jbxxgroup.addItems("航段名称", hangduan.getHdqdmc() + "-" + hangduan.getHdzdmc());
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("sshdid"), this.hangDaoDao.queryHangDaoByHdid(hangduan.getSshdid()).getHdbh());
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdszxzqh"), this.appurtenanceService.queryStrXzqh(hangduan.getHdszxzqh()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdlc"), hangduan.getHdlc());
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("cflc"), StringUtil.getStringIsNullObj(hangduan.getCflc()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdss"), StringUtil.getStringIsNullObj(hangduan.getHdss()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdsscjsj"), DateTimeUtil.getTimeFmt(hangduan.getHdsscjsj()).equals(Constants.DEFAULT_TIME) ? "" : DateTimeUtil.getTimeFmt(hangduan.getHdsscjsj()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdkd"), StringUtil.getStringIsNullObj(hangduan.getHdkd()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdzxwqbj"), StringUtil.getStringIsNullObj(hangduan.getHdzxwqbj()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("zgthsw"), StringUtil.getStringIsNullObj(hangduan.getZgthsw()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("zdthsw"), StringUtil.getStringIsNullObj(hangduan.getZdthsw()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdxzjsdj"), hangduan.getHdxzjsdj() == null ? "" : this.dicDao.queryAttrDesc(hangduan.getHdxzjsdj()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hddjjsdj"), hangduan.getHddjjsdj() == null ? "" : this.dicDao.queryAttrDesc(hangduan.getHddjjsdj()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("zdthswbzl"), StringUtil.getStringIsNullObj(hangduan.getZdthswbzl()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("ccthhcdbcxzzd"), StringUtil.getStringIsNullObj(hangduan.getCcthhcdbcxzzd()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("ghccthhcdbcxzzd"), StringUtil.getStringIsNullObj(hangduan.getGhccthhcdbcxzzd()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdsfth"), hangduan.getHdsfth() == 1 ? "是" : "否");
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("sfjjxhd"), hangduan.getSfjjxhd() == 1 ? "是" : "否");
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("ghjzzxthjg"), StringUtil.getStringIsNullObj(hangduan.getGhjzzxthjg()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("ghjzzxthjk"), StringUtil.getStringIsNullObj(hangduan.getGhjzzxthjk()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("ghjzmkss"), StringUtil.getStringIsNullObj(hangduan.getGhjzmkss()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("ghjzxk"), StringUtil.getStringIsNullObj(hangduan.getGhjzxk()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("fjhdlx"), StringUtil.getStringIsNullObj(hangduan.getFjhdlx()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("sfyfcfhd"), hangduan.getSfyfcfhd() == 1 ? "是" : "否");
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("fcfhdlc"), StringUtil.getStringIsNullObj(hangduan.getFcfhdlc()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("fcfhdxzjsdj"), hangduan.getFcfhdxzjsdj() == null ? "" : this.dicDao.queryAttrDesc(hangduan.getFcfhdxzjsdj()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("fcfhddjjsdj"), hangduan.getFcfhddjjsdj() == null ? "" : this.dicDao.queryAttrDesc(hangduan.getFcfhddjjsdj()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("fcfhdwhlb"), StringUtil.getStringIsNullObj(hangduan.getFcfhdwhlb()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("fcfhdhbpblb"), StringUtil.getStringIsNullObj(hangduan.getFcfhdhbpblb()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("sfyqt"), hangduan.getSfyqt() == 1 ? "是" : "否");
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("sfzyhd"), hangduan.getSfzyhd() == 1 ? "是" : "否");
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("sfwjhhd"), hangduan.getSfwjhhd() == 1 ? "是" : "否");
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("sygllx"), hangduan.getSygllx() == null ? "" : this.dicDao.queryAttrDesc(hangduan.getSygllx()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdwhlb"), hangduan.getHdwhlb() == null ? "" : this.dicDao.queryAttrDesc(hangduan.getHdwhlb()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hddj"), hangduan.getHddj() == null ? "" : this.dicDao.queryAttrDesc(hangduan.getHddj()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdsx"), hangduan.getHdsx() == null ? "" : this.dicDao.queryAttrDesc(hangduan.getHdsx()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("whss"), StringUtil.getStringIsNullObj(hangduan.getWhss()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("whdk"), StringUtil.getStringIsNullObj(hangduan.getWhdk()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("whwqbj"), StringUtil.getStringIsNullObj(hangduan.getWhwqbj()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("whssbzl"), StringUtil.getStringIsNullObj(hangduan.getWhssbzl()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hbsblc"), StringUtil.getStringIsNullObj(hangduan.getHbsblc()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hbpblb"), StringUtil.getStringIsNullObj(hangduan.getHbpblb()));
            jbxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hbwhzcl"), StringUtil.getStringIsNullObj(hangduan.getHbwhzcl()));
            result.addGroup(jbxxgroup);

            AppurtenanceGroup dlxxgroup = new AppurtenanceGroup("地理位置信息");
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hldm"), StringUtil.getStringIsNullObj(hangduan.getHldm()));
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hlmc"), StringUtil.getStringIsNullObj(hangduan.getHlmc()));
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdqdlczh"), StringUtil.getStringIsNullObj(hangduan.getHdqdlczh()));
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdzdlczh"), StringUtil.getStringIsNullObj(hangduan.getHdzdlczh()));
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdqdzbx"), StringUtil.getStringIsNullObj(hangduan.getHdqdzbx()));
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdqdzby"), StringUtil.getStringIsNullObj(hangduan.getHdqdzby()));
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdzdzbx"), StringUtil.getStringIsNullObj(hangduan.getHdzdzbx()));
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("hdzdzby"), StringUtil.getStringIsNullObj(hangduan.getHdzdzby()));
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("qdsfwzkd"), hangduan.getQdsfwzkd() == 1 ? "是" : "否");
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("zdsfwzkd"), hangduan.getZdsfwzkd() == 1 ? "是" : "否");
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("qdsfwfjd"), hangduan.getQdsfwfjd() == 1 ? "是" : "否");
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("qdfjdlb"), StringUtil.getStringIsNullObj(hangduan.getQdfjdlb()));
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("zdsfwfjd"), hangduan.getZdsfwfjd() == 1 ? "是" : "否");
            dlxxgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("zdfjdlx"), StringUtil.getStringIsNullObj(hangduan.getZdfjdlx()));

            result.addGroup(dlxxgroup);

            AppurtenanceGroup fzgroup = new AppurtenanceGroup("辅助信息");
            fzgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("gljgbh"));
            fzgroup.addItems(this.dptService.queryDptName(hangduan.getGljgbh()));
            fzgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("gljglxr"), hangduan.getGljglxr());
            fzgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("gljglxdh"), hangduan.getGljglxdh());
            fzgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("gljgszxzqh"));
            fzgroup.addItems(this.appurtenanceService.queryStrXzqh(hangduan.getGljgszxzqh()));
            fzgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("bz"));
            fzgroup.addItems(hangduan.getBz() == null ? "" : hangduan.getBz());
            fzgroup.addNewLine();
            fzgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("createtime"), DateTimeUtil.getTimeFmt(hangduan.getCreatetime()));
            fzgroup.addItems(new PropDescFields(CHdHduanjcxx.class).getPropDesc("updatetime"), DateTimeUtil.getTimeFmt(hangduan.getUpdatetime()));
            result.addGroup(fzgroup);


            return result;
        }

    }


    @Override
    public BaseResult delHangDuan(int id) {
        CHdHduanjcxx hangduan = this.hangDuanDao.queryHangDuanById(id);
        if (hangduan == null) {
            return new BaseResultFailed(Constants.RESULT_HDUAN_NOTEXIST,
                    "航段不存在");
        } else {
            logService.addLog(ModuleName.HDXXWH, OpName.DEL, hangduan.getHdqdmc() + "-" + hangduan.getHdzdmc());
            //删除该航段下的所有附属物
            this.appurtenanceService.delAppurtenances(id);

            this.hangDuanDao.delHangDuan(hangduan);
            BaseResultOK result = new BaseResultOK();
            return result;
        }
    }

    @Override
    public BaseResult delHangDuans(int hdaoid, List<CZdXzqhdm> xzqhdms) {
        List<CHdHduanjcxx> hduans = this.hangDuanDao.queryHangDuanBySshdid(hdaoid, xzqhdms).getData();
        for (CHdHduanjcxx hduan : hduans) {
            this.appurtenanceService.delAppurtenances(hduan.getId());
            this.hangDuanDao.delHangDuan(hduan);
        }
        return new BaseResultOK();
    }

    @Override
    public BaseResult addHangDuan(CHdHduanjcxx hangduan) {
        CHdHduanjcxx existedhduan = this.hangDuanDao
                .queryHangDuanByHdid(hangduan.getId());
        if (existedhduan != null) {
            return new BaseResultFailed(Constants.RESULT_HDUAN_EXISTED, "航段已存在");
        } else {
            CXtDpt dpt = this.dptdao.queryDptInfo(hangduan.getGljgbh());
            hangduan.setGljgszxzqh(dpt.getXzqh());
            this.hangDuanDao.addHangDuan(hangduan);
            BaseResultOK baseResultOK = new BaseResultOK();
            baseResultOK.addToMap("hduanid", hangduan.getId());
            baseResultOK.addToMap("hduanbh", hangduan.getHdbh());
            baseResultOK.addToMap("hduanmc", hangduan.getHdqdmc() + "-"
                    + hangduan.getHdzdmc());
            baseResultOK.addToMap("hduanxzqhid", hangduan.getHdszxzqh());
            CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(hangduan
                    .getHdszxzqh());
            if (xzqhdm != null)
                baseResultOK.addToMap("hduanxzqhname", xzqhdm.getName());
            else
                baseResultOK.addToMap("hduanxzqhname", "");
            logService.addLog(ModuleName.HDXXWH, OpName.ADD, hangduan.getHdqdmc() + "-" + hangduan.getHdzdmc());
            return baseResultOK;
        }
    }

    @Override
    public BaseResult updateHangDuan(CHdHduanjcxx hangduan) {
        CXtDpt dpt = this.dptdao.queryDptInfo(hangduan.getGljgbh());
        hangduan.setGljgszxzqh(dpt.getXzqh());
        this.hangDuanDao.updateHangDuan(hangduan);
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("hduanid", hangduan.getId());
        baseResultOK.addToMap("hduanbh", hangduan.getHdbh());
        baseResultOK.addToMap("hduanmc", hangduan.getHdqdmc() + "-"
                + hangduan.getHdzdmc());
        baseResultOK.addToMap("hduanxzqhid", hangduan.getHdszxzqh());
        CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(hangduan
                .getHdszxzqh());
        if (xzqhdm != null)
            baseResultOK.addToMap("hduanxzqhname", xzqhdm.getName());
        else
            baseResultOK.addToMap("hduanxzqhname", "");
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, hangduan.getHdqdmc() + "-" + hangduan.getHdzdmc());
        return baseResultOK;
    }

    @Override
    public List<CHdHduanjcxx> queryHDuanInXzqh(String strin) {
        return this.hangDuanDao.queryHDuanInXzqh(strin);
    }


}
