package com.channel.hangdao.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import javax.annotation.Resource;

import com.channel.bean.HdaoBean;
import com.channel.bean.XzqhDT;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.dic.service.XzqhdmService;
import com.channel.hangduan.service.HangDuanService;
import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import com.channel.model.user.CXtDpt;
import com.channel.model.user.CZdXzqhdm;
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
import com.channel.bean.PropDescFields;
import com.channel.dic.dao.DicDao;
import com.channel.hangdao.dao.HangDaoDao;
import com.channel.hangdao.service.HangDaoService;
import com.channel.hangduan.dao.HangDuanDao;
import com.channel.model.hd.CHdHdaojcxx;
import com.channel.model.hd.CHdHduanjcxx;
import com.channel.user.dao.UserDao;
import com.channel.user.dao.impl.DptDaoImpl;
import com.channel.user.service.DptService;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;

@Service("hangdaoservice")
public class HangDaoServiceImpl extends BaseService implements HangDaoService {
    @Resource(name = "hangdaodao")
    private HangDaoDao hangDaoDao;
    @Resource(name = "hangduandao")
    private HangDuanDao hangDuanDao;
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "dicdao")
    private DicDao dicDao;
    @Resource(name = "appurtenanceservice")
    private AppurtenanceService appurtenanceService;
    @Resource(name = "hangduanservice")
    private HangDuanService hangDuanService;
    @Resource(name = "dptdao")
    private DptDaoImpl dptdao;
    @Resource(name = "dptservice")
    private DptService dptService;
    @Resource
    private LogService logService;

    @Resource(name = "xzqhdmservice")
    private XzqhdmService xzqhdmService;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmdao;

    @Override
    public BaseResult searchAllHangDao(int sfgg, int xzqh, String content) {
        BaseQueryRecords<CHdHdaojcxx> records = new BaseQueryRecords<>();
        CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
        if (xzqh == -1) {
            records = this.hangDaoDao
                    .searchAllHangDao(sfgg, null, content);
        } else {
            if (xzqhdm != null) {
                List<CZdXzqhdm> xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqhdm);
                xzqhs.add(xzqhdm);
                records = this.hangDaoDao.searchAllHangDao(sfgg, xzqhs, content);
            }
        }
        return new BaseResultOK(records);
    }

    @Override
    public BaseResult searchHangDaoSjbh(int sfgg, int sjbh, String content) {
        BaseQueryRecords<CHdHdaojcxx> records = new BaseQueryRecords<>();
        records = this.hangDaoDao.searchHangDaoSjbh(sfgg, sjbh, content);
        return new BaseResultOK(records);
    }

    @Override
    public BaseResult searchHangDaoHddj(int sfgg, int xzqh, int hdid, int hddj, String content) {
        BaseQueryRecords<CHdHdaojcxx> records = new BaseQueryRecords<>();
        CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
        if (xzqh == -1) {
            records = this.hangDaoDao.searchHangDaoHddj(sfgg, null, hdid, hddj, content);
        } else {
            if (xzqhdm != null) {
                List<CZdXzqhdm> xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqhdm);
                xzqhs.add(xzqhdm);
                records = this.hangDaoDao.searchHangDaoHddj(sfgg, xzqhs, hdid, hddj, content);
            }
        }
        BaseResultOK baseResultOK = new BaseResultOK(records);
        return baseResultOK;
    }

    @Override
    public BaseResult zhcxHdao(int xzqh) {
        List<HdaoBean> list = new ArrayList<>();
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

    @Override
    public BaseResult exportHdao(int xzqh) {
        List<HdaoBean> list = new ArrayList<>();
        CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
        list = getListVal(xzqhdm);
        BaseResultOK baseResultOK = new BaseResultOK(list);
        String outfilepath = "";
        File inputfile = null;
        File outputfile = null;
        try {
            String path = this.getRealPath("/");
            inputfile = new File(path + "/template/航道列表.xlsx");
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

            XSSFSheet sheet = workbook.getSheet("航道列表");
            sheet.getRow(0).getCell(0).setCellValue(xzqhdm.getName() + "航道列表");
            if (list != null && list.size() > 0) {
                int len = list.size();
                int n = 9;
                int totalrow = n + 2 * len;
                // 航道名称 合计cell
                addCell(n, totalrow, sheet, cellStyle);
                for (int j = n; j < totalrow; j = j + 2) {
                    sheet.addMergedRegion(new CellRangeAddress(j, j + 1, 0, 1));
                    sheet.addMergedRegion(new CellRangeAddress(j, j + 1, 2, 3));
                    sheet.addMergedRegion(new CellRangeAddress(j, j + 1, 4, 5));
                    sheet.addMergedRegion(new CellRangeAddress(j, j + 1, 6, 7));
                    sheet.addMergedRegion(new CellRangeAddress(j, j + 1, 8, 9));
                    sheet.addMergedRegion(new CellRangeAddress(j, j + 1, 10, 11));
                    sheet.addMergedRegion(new CellRangeAddress(j, j + 1, 12, 13));
                }
                for (int i = 0; i < len; i++) {
                    HdaoBean bean = list.get(i);
                    sheet.getRow(n + 2 * i).getCell(0).setCellValue(bean.getName());
                    sheet.getRow(n + 2 * i).getCell(2).setCellValue(bean.getGgnum());
                    sheet.getRow(n + 2 * i).getCell(4).setCellValue(bean.getGglc());
                    sheet.getRow(n + 2 * i).getCell(6).setCellValue(bean.getZxnum());
                    sheet.getRow(n + 2 * i).getCell(8).setCellValue(bean.getZxlc());
                    sheet.getRow(n + 2 * i).getCell(10).setCellValue(bean.getTotalnum());
                    sheet.getRow(n + 2 * i).getCell(12).setCellValue(bean.getTotallc());
                }
            }
            FileOutputStream outfile = new FileOutputStream(outputfile);
            workbook.write(outfile);
            outfile.close();
            logService.addLog(ModuleName.ZHCX, OpName.EXPORT, "航道列表");
            baseResultOK.addToMap("filename", outputfile.getName());
            baseResultOK.addToMap("filepath", outfilepath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseResultOK;
    }

    @Override
    public BaseResult zhcxHdaoInfo(int xzqh, int sfgg, String content, int page, int rows) {
        List<HdaoBean> list = new ArrayList<>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        String dept = "";
        if (xzqh == -1) {
            xzqhdm = this.xzqhdmdao.queryRootXzqh();
        } else {
            xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
        }
        dept = xzqhdm.getName();
        BaseQueryRecords records = new BaseQueryRecords();
        records = getDtVal(xzqhdm, sfgg, content, page, rows);
        BaseResultOK ok = new BaseResultOK(records);
        ok.addToMap("dept", dept);
        return ok;
    }

    @Override
    public BaseResult importHdao() {
        //改一个市级编号和like的值
        List<Object[]> list = this.hangDaoDao.importHdao();
        Date now = new Date();
        for (Object[] b : list) {
            CHdHdaojcxx h = new CHdHdaojcxx();
            String hdbh = setStringval(b[2]);
            CHdHdaojcxx hdao = this.hangDaoDao.queryHangDaoByHdBh(hdbh);
            if (hdao == null) {
                h.setHdbh(hdbh);
                String s = hdbh.substring(0, 1);
                if ("0".equals(s)) {
                    h.setSfqshd(1);
                    h.setSfgg(1);
                } else {
                    h.setSfqshd(0);
                    h.setSfgg(0);
                }
                int sjbh = 0;
                h.setSssjbh(sjbh);
                h.setHdmc(setStringval(b[3]));
                h.setSfjjx(0);
                h.setBz("");
                h.setCreatetime(now);
                h.setUpdatetime(now);
                //this.hangDaoDao.addHangDao(h);
            }
        }
        return null;
    }

    @Override
    public BaseResult exportHdaoInfo(int id, int xzqh) {
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
            outfilepath = path + "baobiao\\" + "航道" + sbf.toString();
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

            BaseResult hdaoret = this.queryHangDaoInfo(id, xzqh);
            if (hdaoret != null) {
                ArrayList<List<Object>> arr = (ArrayList<List<Object>>) hdaoret.getObj();
                if (arr != null) {
                    sheet.getRow(0).getCell(0).setCellValue("航道详情");
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
            logService.addLog(ModuleName.HDXXWH, OpName.EXPORT, "航道详情");
            baseResultOK.addToMap("filename", outputfile.getName());
            baseResultOK.addToMap("filepath", outfilepath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseResultOK;
    }


    private String setStringval(Object o) {
        String s = "";
        if (o != null) {
            s = String.valueOf(o);
        }
        return s;
    }

    private BaseQueryRecords getDtVal(CZdXzqhdm xzqhdm, int sfgg, String content, int page, int rows) {
        BaseQueryRecords records = new BaseQueryRecords();
        List<CZdXzqhdm> xzqhs = new ArrayList<>();
        if (xzqhdm != null) {
            xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqhdm);
            String sqlXzqh = "";
            if (xzqhs != null && xzqhs.size() > 0) {
                sqlXzqh = getSqlXzqh(xzqhs);
            } else {
                sqlXzqh = String.valueOf(xzqhdm.getId());
            }
            records = this.hangDaoDao.zhcxHdaoInfo(sqlXzqh, sfgg, content, page, rows);
        }
        return records;
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

    private void addCell(int srow, int erow, int scel, int ecel, XSSFSheet sheet, CellStyle cellStyle) {
        for (int j = srow; j < erow + 1; j++) {
            sheet.createRow(j);
            XSSFRow row = sheet.getRow(j);
            for (int k = scel; k < ecel + 1; k++) {
                row.createCell(k).setCellStyle(cellStyle);
            }
        }
    }

    private List<HdaoBean> getListVal(CZdXzqhdm xzqhdm) {
        BaseQueryRecords<CZdXzqhdm> xzqhs = new BaseQueryRecords();
        List<HdaoBean> list = new ArrayList<>();
        if (xzqhdm != null) {
            String totalxzqh = "";
            boolean ifroot = this.xzqhdmService.ifNodeRoot(xzqhdm);
            if (ifroot) {//省xzqh
                List<CZdXzqhdm> shi = this.xzqhdmService.findChildrenNodes(xzqhdm).getData();
                if (shi != null && shi.size() > 0) {
                    for (CZdXzqhdm shixzqh : shi) {//处理市
                        BaseQueryRecords<CZdXzqhdm> temp = this.xzqhdmService.findChildrenNodes(shixzqh);
                        String sqlXzqh = getSqlXzqh(temp);
                        BaseQueryRecords records = this.hangDaoDao.zhcxHdao(sqlXzqh);
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
                            BaseQueryRecords records = this.hangDaoDao.zhcxHdao(String.valueOf(zhanxzqh.getId()));
                            setListVal(zhanxzqh, list, records);
                        }
                        totalxzqh = getSqlXzqh(zhanbqr);
                    }
                } else {//站
                    totalxzqh = String.valueOf(xzqhdm.getId());
                    BaseQueryRecords records = this.hangDaoDao.zhcxHdao(totalxzqh);
                    setListVal(xzqhdm, list, records);
                }
            }
            if (!"".equals(totalxzqh)) {
                BaseQueryRecords records = this.hangDaoDao.zhcxHdao(totalxzqh);
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

    private void setListVal(CZdXzqhdm xzqh, List<HdaoBean> list, BaseQueryRecords records) {
        if (records != null) {
            List<Object[]> temp = records.getData();
            HdaoBean bean = new HdaoBean(xzqh.getId(), "合计", 0, 0, 0, 0, 0, 0);
            if (temp != null && temp.size() == 3) {
                bean.setXzqh(xzqh.getId());
                bean.setName(xzqh.getName());
                bean.setGgnum(getIntegerVal(temp.get(0)[0]));
                bean.setGglc(getRoundDouble(temp.get(0)[1]));
                bean.setZxnum(getIntegerVal(temp.get(1)[0]));
                bean.setZxlc(getRoundDouble(temp.get(1)[1]));
                bean.setTotalnum(getIntegerVal(temp.get(2)[0]));
                bean.setTotallc(getRoundDouble(temp.get(2)[1]));
            }
            list.add(bean);
        }
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

    private String getSqlXzqh(List<CZdXzqhdm> list) {
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

    private int getIntegerVal(Object o) {
        int ret = 0;
        if (o != null) {
            String temp = String.valueOf(o);
            boolean isflag = temp.matches("-?\\d+\\.?\\d*");
            if (isflag) {
                ret = Integer.parseInt(temp);
            }
        }
        return ret;
    }

    private double getDoubleVal(Object o) {
        double ret = 0;
        if (o != null) {
            String temp = String.valueOf(o);
            boolean isflag = temp.matches("-?\\d+\\.?\\d*");
            if (isflag) {
                ret = Double.parseDouble(temp);
            }
        }
        return ret;
    }

    private double getRoundDouble(double d) {
        double ret = 0.0;
        if (d != 0.0) {
            ret = new BigDecimal(d).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        return ret;
    }

    private double getRoundDouble(Object o) {
        double ret = 0;
        if (o != null) {
            String tempobj = String.valueOf(o);
            boolean isflag = tempobj.matches("-?\\d+\\.?\\d*");
            if (isflag) {
                double temp = Double.parseDouble(tempobj);
                ret = new BigDecimal(temp).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
        }
        return ret;
    }

    @Override
    public BaseResult queryAllHangDao(int sfgg, int xzqh) {
        List<CHdHdaojcxx> list = new ArrayList<>();
        BaseQueryRecords<CHdHdaojcxx> records = new BaseQueryRecords<>();
        BaseQueryRecords<CHdHdaojcxx> zerohds = new BaseQueryRecords<>();
        String name = "";
        if (xzqh == -1 || xzqh == 1) {
            records = this.hangDaoDao.queryAllHangDao(sfgg, null);
        } else {
            CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
            List<CZdXzqhdm> sssj = this.xzqhdmService.findParentNodes(xzqhdm, 2).getData();
            if (sssj != null && sssj.size() > 0) {
                name = sssj.get(0).getName();
            } else {
                name = xzqhdm.getName();
            }
            if (xzqhdm != null) {
                List<CZdXzqhdm> xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqhdm);
                xzqhs.add(xzqhdm);
                records = this.hangDaoDao.queryAllHangDao(sfgg, xzqhs);
            }
            zerohds = this.hangDaoDao.queryHangDaoZero(sfgg, name);
        }

        for (CHdHdaojcxx hdao : records.getData()) {
            list.add(hdao);
        }
        for (CHdHdaojcxx hdao : zerohds.getData()) {
            list.add(hdao);
        }
        Collections.sort(list, new Comparator<CHdHdaojcxx>() {
            @Override
            public int compare(CHdHdaojcxx o1, CHdHdaojcxx o2) {
                int sfgg = o2.getSfgg() - o1.getSfgg();
                if (sfgg != 0) {
                    return sfgg;
                }
                return o1.getHdbh().compareTo(o2.getHdbh());
                //return (int) (o1.getCreatetime().getTime() - o2.getCreatetime().getTime());
            }
        });
        BaseResultOK baseResultOK = new BaseResultOK(new BaseQueryRecords<CHdHdaojcxx>(list));
        return baseResultOK;
    }

    @Override
    public BaseResult queryHdManXzqh(int sfgg, int xzqh) {
        List<CHdHdaojcxx> list = new ArrayList<>();
        BaseQueryRecords<CHdHdaojcxx> records = new BaseQueryRecords<>();
        BaseQueryRecords<CHdHdaojcxx> zerohds = new BaseQueryRecords<>();
        String name = "";
        if (xzqh == -1 || xzqh == 1) {
            records = this.hangDaoDao.queryAllHangDao(sfgg, null);
        } else {
            CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
            List<CZdXzqhdm> sssj = this.xzqhdmService.findParentNodes(xzqhdm, 2).getData();
            if (sssj != null && sssj.size() > 0) {
                name = sssj.get(0).getName();
            } else {
                name = xzqhdm.getName();
            }
            if (xzqhdm != null) {
                CXtDpt dept = (CXtDpt) getSession().getAttribute("dpt");
                if (dept != null) {
                    String manxzqh = dept.getManagexzqh();
                    int isexisted = manxzqh.indexOf(",");
                    if (isexisted > -1) {
                        records = this.hangDaoDao.queryHangDaoInXzqh(sfgg, manxzqh);
                    } else {
                        List<CZdXzqhdm> xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqhdm);
                        xzqhs.add(xzqhdm);
                        records = this.hangDaoDao.queryAllHangDao(sfgg, xzqhs);
                    }
                }
            }

            zerohds = this.hangDaoDao.queryHangDaoZero(sfgg, name);
        }

        for (CHdHdaojcxx hdao : records.getData()) {
            list.add(hdao);
        }
        for (CHdHdaojcxx hdao : zerohds.getData()) {
            list.add(hdao);
        }
        Collections.sort(list, new Comparator<CHdHdaojcxx>() {
            @Override
            public int compare(CHdHdaojcxx o1, CHdHdaojcxx o2) {
                int sfgg = o2.getSfgg() - o1.getSfgg();
                if (sfgg != 0) {
                    return sfgg;
                }
                return o1.getHdbh().compareTo(o2.getHdbh());
                //return (int) (o1.getCreatetime().getTime() - o2.getCreatetime().getTime());
            }
        });
        BaseResultOK baseResultOK = new BaseResultOK(new BaseQueryRecords<CHdHdaojcxx>(list));
        return baseResultOK;
    }

    @Override
    public BaseQueryRecords<CHdHdaojcxx> queryHangDao(int sfgg, int xzqh) {
        BaseQueryRecords<CHdHdaojcxx> records = new BaseQueryRecords<>();
        if (xzqh == -1 || xzqh == 1) {
            records = this.hangDaoDao.queryAllHangDao(sfgg, null);
        } else {
            CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
            List<CZdXzqhdm> sssj = this.xzqhdmService.findParentNodes(xzqhdm, 2).getData();
            if (xzqhdm != null) {
                List<CZdXzqhdm> xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqhdm);
                xzqhs.add(xzqhdm);
                records = this.hangDaoDao.queryAllHangDao(sfgg, xzqhs);
            }
        }
        return records;
    }

    @Override
    public BaseResult addHangDao(CHdHdaojcxx hdao) {
        CHdHdaojcxx existedhdao = this.hangDaoDao.queryHangDaoByHdid(hdao
                .getId());
        if (existedhdao != null) {
            return new BaseResultFailed(Constants.RESULT_HDAO_EXISTED, "航道已存在");
        } else {
            this.hangDaoDao.addHangDao(hdao);
            logService.addLog(ModuleName.HDXXWH, OpName.ADD, hdao.getHdmc());
            BaseResultOK baseResultOK = new BaseResultOK();
            baseResultOK.addToMap("id", hdao.getId());
            baseResultOK.addToMap("bh", hdao.getHdbh());
            baseResultOK.addToMap("mc", hdao.getHdmc());
            return baseResultOK;
        }
    }

    @Override
    public BaseResult updateHangDao(CHdHdaojcxx hdao) {
        this.hangDaoDao.updateHangDao(hdao);
        logService.addLog(ModuleName.HDXXWH, OpName.UPDATE, hdao.getHdmc());
        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("id", hdao.getId());
        baseResultOK.addToMap("bh", hdao.getHdbh());
        baseResultOK.addToMap("mc", hdao.getHdmc());
        return baseResultOK;
    }

    @Override
    public BaseResult queryHangDaoInfo(int id, int xzqh) {
        CHdHdaojcxx hangdao = this.hangDaoDao.queryHangDaoById(id);
        if (hangdao == null) {
            return new BaseResultFailed(Constants.RESULT_HDAO_NOTEXIST, "航道不存在");
        } else {
            List<CZdXzqhdm> xzqhs = new ArrayList<CZdXzqhdm>();
            if (xzqh > 0) {
                CZdXzqhdm xzqhentity = this.xzqhdmdao.queryXzqhdmById(xzqh);
                if (xzqhentity != null) {
                    xzqhs = (List<CZdXzqhdm>) this.xzqhdmService.findChildrenNodes_r(xzqhentity);
                    xzqhs.add(xzqhentity);
                }
            }
            List<CHdHduanjcxx> list = this.hangDuanDao.queryHangDuanBySshdid(hangdao.getId(), xzqhs).getData();
            int hduannum = list.size();// 航段分段
            double hdlc = 0.0;//航道里程
            double thlc = 0.0;//通航里程
            double cflc = 0.0;//重复里程
            double zxss = 0.0;//最小水深
            double zxwqbj = 0.0;//最小弯曲半径
            double zdthsw = 0.0;//最低通航水位
            double zgthsw = 0.0;//最高通航水位
            List<CHdHduanjcxx> qdhd = new ArrayList<CHdHduanjcxx>();
            List<CHdHduanjcxx> zdhd = new ArrayList<CHdHduanjcxx>();
            if (hduannum != 0) {
                for (int i = 0; i < hduannum; i++) {
                    CHdHduanjcxx hduan = list.get(i);
                    hdlc += hduan.getHdlc();
                    if (hduan.getHdsfth() == 1) {
                        thlc += hduan.getHdlc();
                    }
                    if (hduan.getCflc() != null) {
                        cflc += hduan.getCflc();
                    }
                    double temphdss = hduan.getHdss() == null ? 0 : hduan.getHdss();
                    double tempzxwqbj = hduan.getHdzxwqbj() == null ? 0 : hduan.getHdzxwqbj();
                    double tempzdthsw = hduan.getZdthsw() == null ? 0 : hduan.getZdthsw();
                    double tempzgthsw = hduan.getZgthsw() == null ? 0 : hduan.getZgthsw();
                    if (i == 0) {
                        zxss = temphdss;
                        zxwqbj = tempzxwqbj;
                        zdthsw = tempzdthsw;
                        zgthsw = tempzgthsw;
                    } else {
                        if (temphdss < zxss && temphdss > 0) {
                            zxss = temphdss;
                        }
                        if (tempzxwqbj < zxwqbj && tempzxwqbj > 0) {
                            zxwqbj = tempzxwqbj;
                        }
                        if (tempzgthsw > zgthsw) {
                            zdthsw = tempzdthsw;
                        }
                        if (tempzdthsw < zdthsw && tempzdthsw > 0) {
                            zgthsw = tempzgthsw;
                        }
                    }

                    boolean isqd = getIsqd(hduan.getHdqdmc().trim(), list);
                    if (isqd) {
                        qdhd.add(hduan);
                    }
                    boolean iszd = getIszd(hduan.getHdzdmc().trim(), list);
                    if (iszd) {
                        zdhd.add(hduan);
                    }
                }
            }
            CHdHduanjcxx qd = new CHdHduanjcxx("", "", 0.0, 0.0, 0.0, 0.0, 0, 0, "", 0, 0, "");
            CHdHduanjcxx zd = new CHdHduanjcxx("", "", 0.0, 0.0, 0.0, 0.0, 0, 0, "", 0, 0, "");
            if (qdhd.size() == 1) {
                qd = qdhd.get(0);
            }
            if (zdhd.size() == 1) {
                zd = zdhd.get(0);
            }
            AppurtenanceResult result = new AppurtenanceResult();
            result.setModelobj(hangdao);
            result.AddLine("基本信息");
            result.AddLine(
                    new PropDescFields(CHdHdaojcxx.class).getPropDesc("hdbh"),
                    hangdao.getHdbh(),
                    new PropDescFields(CHdHdaojcxx.class).getPropDesc("hdmc"),
                    hangdao.getHdmc());
            result.AddLine("航道里程", formatDouble(hdlc), "通航里程", formatDouble(thlc));
            result.AddLine("重复里程", formatDouble(cflc), new PropDescFields(CHdHdaojcxx.class).getPropDesc("sfqshd"),
                    hangdao.getSfqshd() == 1 ? "是" : "否");
            result.AddLine(new PropDescFields(CHdHdaojcxx.class)
                    .getPropDesc("sssjbh"), hangdao.getSssjbh() == 0 ? "" : this.dicDao.queryAttrDesc(hangdao.getSssjbh()));
            result.AddLine(new PropDescFields(CHdHdaojcxx.class)
                            .getPropDesc("sfjjx"), hangdao.getSfjjx() == 1 ? "是" : "否",
                    new PropDescFields(CHdHdaojcxx.class).getPropDesc("sfgg"),
                    hangdao.getSfgg() == 1 ? "是" : "否");
            result.AddLine("最小水深", StringUtil.getDoubleNull(zxss),
                    "最小弯曲半径",
                    StringUtil.getDoubleNull(zxwqbj));
            result.AddLine("最高通航水位", StringUtil.getDoubleNull(zgthsw),
                    "最低通航水位",
                    StringUtil.getDoubleNull(zdthsw));

            result.AddLine("地理位置信息");
            result.AddLine("起点名称", qd.getHdqdmc() == null ? "" : qd.getHdqdmc(), "终点名称", zd.getHdzdmc() == null ? "" : zd.getHdzdmc());
            result.AddLine("起点里程桩号", qd.getHdqdlczh(), "终点里程桩号", zd.getHdzdlczh());
            result.AddLine("起点经度", StringUtil.getDoubleNull(qd.getHdqdzbx()), "起点纬度", StringUtil.getDoubleNull(qd.getHdqdzby()));
            result.AddLine("终点经度", StringUtil.getDoubleNull(zd.getHdzdzbx()), "终点纬度", StringUtil.getDoubleNull(zd.getHdzdzby()));
            result.AddLine("起点是否为主控点", qd.getQdsfwzkd() == 1 ? "是" : "否", "终点是否为主控点", zd.getQdsfwzkd() == 1 ? "是" : "否");
            result.AddLine("起点是否为分界点", qd.getQdsfwfjd() == 1 ? "是" : "否", "终点是否为分界点", zd.getQdsfwfjd() == 1 ? "是" : "否");
            result.AddLine("起点分界点类别", qd.getQdfjdlb(), "终点分界点类别", zd.getZdfjdlx());

            result.AddLine("辅助信息");
            result.AddLine(
                    new PropDescFields(CHdHdaojcxx.class).getPropDesc("bz"),
                    hangdao.getBz() == null ? "" : hangdao.getBz());
            result.AddLine(new PropDescFields(CHdHdaojcxx.class)
                    .getPropDesc("createtime"), DateTimeUtil.getTimeFmt(hangdao
                    .getCreatetime()), new PropDescFields(CHdHdaojcxx.class)
                    .getPropDesc("updatetime"), DateTimeUtil.getTimeFmt(hangdao
                    .getUpdatetime()));

            return result;
        }
    }

    private boolean getIsqd(String qdmc, List<CHdHduanjcxx> list) {
        for (CHdHduanjcxx hd : list) {
            if (qdmc.equals(hd.getHdzdmc().trim())) {
                return false;
            }
        }
        return true;
    }

    private boolean getIszd(String zdmc, List<CHdHduanjcxx> list) {
        for (CHdHduanjcxx hd : list) {
            if (zdmc.equals(hd.getHdqdmc().trim())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public BaseResult delHangDao(int id, int xzqh) {
        /**
         * 删除航道应该是删除该行政区划中的航段，如果整条航道都没有一条航段的话，那再删除航段
         */
        CHdHdaojcxx hangdao = this.hangDaoDao.queryHangDaoById(id);
        if (hangdao == null) {
            return new BaseResultFailed(Constants.RESULT_HDAO_NOTEXIST, "航道不存在");
        } else {
            logService.addLog(ModuleName.HDXXWH, OpName.DEL, hangdao.getHdmc());

            CZdXzqhdm xzqhdm = this.xzqhdmdao.queryXzqhdmById(xzqh);
            if (xzqh == -1) {
                //删除该航道下的所有航段
                this.hangDuanService.delHangDuans(id, null);
            } else {
                if (xzqhdm != null) {
                    List<CZdXzqhdm> xzqhs = this.xzqhdmService.findChildrenNodes_r(xzqhdm);
                    xzqhs.add(xzqhdm);
                    //删除该航道下的所有航段
                    this.hangDuanService.delHangDuans(id, xzqhs);
                } else {
                    return new BaseResultFailed("行政区划不存在");
                }
            }
            //判断该航道是否还有航段存在
            BaseQueryRecords<CHdHduanjcxx> hduans = this.hangDuanDao.queryHangDuanBySshdid(id, null);
            if (hduans != null && hduans.getData() != null && hduans.getData().size() == 0)
                this.hangDaoDao.delHangDao(hangdao);
            return new BaseResultOK();
        }
    }

    /**
     * 保留小数点后两位
     */
    private String formatDouble(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.toString();
    }
}
