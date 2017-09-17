package com.channel.outinterface;

import com.channel.bean.Constants;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.dic.service.XzqhdmService;
import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import com.channel.maintenance.dao.MaintenanceDao;
import com.channel.model.user.CXtDpt;
import com.channel.model.user.CXtUser;
import com.channel.model.user.CZdXzqhdm;
import com.channel.user.dao.UserDao;
import com.channel.user.dao.impl.DptDaoImpl;
import com.channel.user.service.DptService;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.service.BaseService;
import com.common.utils.FileUtils;
import com.common.utils.KeyValueEntry;
import com.common.utils.Md5Secure;
import com.opensymphony.xwork2.ActionContext;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Service("OutInterfaceService")
public class OutInterfaceService extends BaseService {
    @Resource(name = "xzqhdmservice")
    private XzqhdmService xzqhdmService;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmDaoImpl;
    @Resource(name = "maintenancedao")
    private MaintenanceDao maintenanceDao;
    @Resource
    private LogService logService;
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "dptdao")
    private DptDaoImpl dptdao;
    @Resource(name = "dptservice")
    private DptService dptService;


    private boolean IflistContain(List<String> list, String str) {
        for (String val : list) {
            if (val.equals(str))
                return true;
        }
        return false;
    }

    private int getlistItemIndex(List<String> list, String str) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(str))
                return i;
        }
        return -1;
    }

    private XSSFRow getRow(XSSFSheet sheet, int row) {
        XSSFRow rw = null;
        rw = sheet.getRow(row);
        if (rw == null)
            rw = sheet.createRow(row);
        return rw;
    }

    private XSSFCell getCell(XSSFRow row, int col) {
        XSSFCell cell = null;
        cell = row.getCell(col);
        if (cell == null)
            cell = row.createCell(col);
        return cell;
    }

    private XSSFCell getCell(XSSFSheet sheet, int row, int col) {
        XSSFRow xssfRow = getRow(sheet, row);
        if (xssfRow != null) {
            return getCell(xssfRow, col);
        }
        return null;
    }

    private void SetCellSimpleStyle(XSSFWorkbook wb, XSSFSheet sheet, int row, int col,
                                    BorderStyle top, BorderStyle right, BorderStyle bottom, BorderStyle left) {
        XSSFCell cell = getCell(sheet, row, col);
        if (cell != null) {
            XSSFCellStyle style = wb.createCellStyle();
            if (top != null)
                style.setBorderTop(top);
            if (right != null)
                style.setBorderRight(right);
            if (bottom != null)
                style.setBorderBottom(bottom);
            if (left != null)
                style.setBorderLeft(left);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            cell.setCellStyle(style);
        }
    }

    private void WriteCell(XSSFSheet sheet, int row, int col, String value, XSSFCellStyle style) {
        XSSFRow xssfRow = getRow(sheet, row);
        if (xssfRow != null) {
            XSSFCell cell = getCell(xssfRow, col);
            cell.setCellValue(value);
            if (style != null)
                cell.setCellStyle(style);
        }
    }

    private void MergeCell(XSSFSheet sheet, int leftrow, int leftcol, int rightrow, int rightcol) {
        sheet.addMergedRegion(new CellRangeAddress(
                leftrow > rightrow ? rightrow : leftrow,
                leftrow > rightrow ? leftrow : rightrow,
                leftcol > rightcol ? rightcol : leftcol,
                leftcol > rightcol ? leftcol : rightcol));
    }

    private List<KeyValueEntry<String, List<String>>> querySsTableData(Integer xzqhid) {
        //处理行政区划
        List<CZdXzqhdm> xzqhs = new ArrayList<>();
        CZdXzqhdm superxzqhdm = new CZdXzqhdm();
        superxzqhdm.setId(xzqhid);
        if (!xzqhdmService.ifNodeLeaf(superxzqhdm)) {
            List<CZdXzqhdm> leafxzqhs = this.xzqhdmService.findChildrenNodes_r(superxzqhdm);
            xzqhs.addAll(leafxzqhs);
        } else {
            superxzqhdm = this.xzqhdmDaoImpl.queryXzqhdmById(xzqhid);
            xzqhs.add(superxzqhdm);
        }

        List<Object[]> records = this.maintenanceDao.querySsTable(xzqhs);
        List<KeyValueEntry<String, List<String>>> data = new ArrayList<>();

        //添加表格头
        KeyValueEntry<String, List<String>> tabletitle = new KeyValueEntry<>("航道名称");
        List<String> tabletitlelist = new ArrayList<>();
        tabletitlelist.add("桥梁");
        tabletitlelist.add("管线");
        tabletitlelist.add("航标");
        tabletitlelist.add("码头");//"锚泊区"跟"服务区"合并统计在码头中
        tabletitlelist.add("渡槽");
        tabletitlelist.add("管道");
        tabletitlelist.add("隧道");
        tabletitlelist.add("船厂");
        tabletitlelist.add("取排水");
        tabletitlelist.add("水文站");
        tabletitlelist.add("管理站");
        tabletitlelist.add("枢纽");
        tabletitlelist.add("坝");
        tabletitlelist.add("激光流量观测点");
        tabletitlelist.add("人工观测点");
        tabletitlelist.add("视频观测点");
        tabletitlelist.add("系缆桩");
        tabletitlelist.add("整治护岸");
        tabletitle.setValue(tabletitlelist);
        data.add(tabletitle);

        List<Integer> coltotal = new ArrayList<>();
        for (int i = 0; i < tabletitlelist.size(); i++)
            coltotal.add(0);

        for (Object[] hdaoss : records) {
            KeyValueEntry<String, List<String>> kv = new KeyValueEntry<>((String) hdaoss[1]);
            List<String> kvlist = new ArrayList<>();
            int qlnum, lxnum, hbnum, mtnum, dcnum, gdnum, sdnum, ccnum, qpsknum, swznum, jyznum, glznum, snnum, bnum, jgllgcdnum, rggcdnum, spgcdnum, xlznum, zzhanum;
            qlnum = ((BigDecimal) hdaoss[3]).intValue();
            lxnum = ((BigDecimal) hdaoss[4]).intValue();
            hbnum = ((BigDecimal) hdaoss[5]).intValue();
            mtnum = ((BigDecimal) hdaoss[6]).intValue();
            dcnum = ((BigDecimal) hdaoss[7]).intValue();
            gdnum = ((BigDecimal) hdaoss[8]).intValue();
            sdnum = ((BigDecimal) hdaoss[9]).intValue();
            ccnum = ((BigDecimal) hdaoss[10]).intValue();
            qpsknum = ((BigDecimal) hdaoss[11]).intValue();
            swznum = ((BigDecimal) hdaoss[12]).intValue();
            glznum = ((BigDecimal) hdaoss[13]).intValue();
            snnum = ((BigDecimal) hdaoss[14]).intValue();
            bnum = ((BigDecimal) hdaoss[15]).intValue();
            jgllgcdnum = ((BigDecimal) hdaoss[16]).intValue();
            rggcdnum = ((BigDecimal) hdaoss[17]).intValue();
            spgcdnum = ((BigDecimal) hdaoss[18]).intValue();
            xlznum = ((BigDecimal) hdaoss[19]).intValue();
            zzhanum = ((BigDecimal) hdaoss[20]).intValue();

            //如果行无数据，则不显示 该行
            int fswsl = qlnum + lxnum + hbnum
                    + mtnum + dcnum + gdnum
                    + sdnum + ccnum + qpsknum
                    + swznum + glznum
                    + snnum + bnum + jgllgcdnum + rggcdnum
                    + spgcdnum + xlznum + zzhanum;
            if (fswsl == 0) {
                continue;
            }

            kvlist.add("" + (qlnum == 0 ? "" : qlnum));
            coltotal.set(0, coltotal.get(0) + qlnum);
            kvlist.add("" + (lxnum == 0 ? "" : lxnum));
            coltotal.set(1, coltotal.get(1) + lxnum);
            kvlist.add("" + (hbnum == 0 ? "" : hbnum));
            coltotal.set(2, coltotal.get(2) + hbnum);
            kvlist.add("" + (mtnum == 0 ? "" : mtnum));
            coltotal.set(3, coltotal.get(3) + mtnum);
            kvlist.add("" + (dcnum == 0 ? "" : dcnum));
            coltotal.set(4, coltotal.get(4) + dcnum);
            kvlist.add("" + (gdnum == 0 ? "" : gdnum));
            coltotal.set(5, coltotal.get(5) + gdnum);
            kvlist.add("" + (sdnum == 0 ? "" : sdnum));
            coltotal.set(6, coltotal.get(6) + sdnum);
            kvlist.add("" + (ccnum == 0 ? "" : ccnum));
            coltotal.set(7, coltotal.get(7) + ccnum);
            kvlist.add("" + (qpsknum == 0 ? "" : qpsknum));
            coltotal.set(8, coltotal.get(8) + qpsknum);
            kvlist.add("" + (swznum == 0 ? "" : swznum));
            coltotal.set(9, coltotal.get(9) + swznum);
            kvlist.add("" + (glznum == 0 ? "" : glznum));
            coltotal.set(10, coltotal.get(10) + glznum);
            kvlist.add("" + (snnum == 0 ? "" : snnum));
            coltotal.set(11, coltotal.get(11) + snnum);
            kvlist.add("" + (bnum == 0 ? "" : bnum));
            coltotal.set(12, coltotal.get(12) + bnum);
            kvlist.add("" + (jgllgcdnum == 0 ? "" : jgllgcdnum));
            coltotal.set(13, coltotal.get(13) + jgllgcdnum);
            kvlist.add("" + (rggcdnum == 0 ? "" : rggcdnum));
            coltotal.set(14, coltotal.get(14) + rggcdnum);
            kvlist.add("" + (spgcdnum == 0 ? "" : spgcdnum));
            coltotal.set(15, coltotal.get(15) + spgcdnum);
            kvlist.add("" + (xlznum == 0 ? "" : xlznum));
            coltotal.set(16, coltotal.get(16) + xlznum);
            kvlist.add("" + (zzhanum == 0 ? "" : zzhanum));
            coltotal.set(17, coltotal.get(17) + zzhanum);

            kv.setValue(kvlist);
            data.add(kv);
        }

        for (int i = 0; i < coltotal.size(); i++) {
            if (coltotal.get(i) == 0) {
                for (KeyValueEntry<String, List<String>> kv : data) {
                    List<String> kvval = kv.getValue();
                    kvval.remove(i);
                }
                coltotal.remove(i);
                i = 1;
            }
        }

        //添加总计
        List<String> totallist = new ArrayList<>();
        KeyValueEntry<String, List<String>> kv = new KeyValueEntry<>("总计", totallist);
        for (Integer coltotalval : coltotal) {
            totallist.add("" + coltotalval);
        }
        data.add(kv);

        return data;
    }

    public BaseResult outSsTable(Integer xzqh) {
        BaseResult result = new BaseResultOK();
        List<KeyValueEntry<String, List<String>>> data = this.querySsTableData(xzqh);
        result.setObj(data);
        result.addToMap("xzqhname", this.xzqhdmDaoImpl.queryXzqhName(xzqh));
        return result;
    }

    public String login(String username) {
        CXtUser user = this.userDao.queryUserByUsername(username);
        if (user == null) {
            return "usererror";
        } else if (user.getUstatus() == Constants.USTATUS_DISABLE) {
            return "usererror";
        } else if (user.getUstatus() == Constants.USTATUS_DELETE) {
            return "usererror";
        } else {
            ActionContext actionContext = ActionContext.getContext();
            Map<String, Object> session = actionContext.getSession();
            //将当前用户信息存入session
            session.put("user", user);

            //将用户所在部门信息存入session
            CXtDpt dpt = this.dptdao.queryDptById(user.getDepartment());
            session.put("dpt", dpt);

            CZdXzqhdm dptxzqh = this.xzqhdmDaoImpl.queryXzqhdmById(dpt.getXzqh());
            session.put("dptxzqh", dptxzqh);

            //将所在部门所处的市行政区划存入
            CZdXzqhdm dptshixzqh = this.xzqhdmService.queryShiXzqh(dpt.getXzqh());
            session.put("dptshixzqh", dptshixzqh);

            //将用户所在部门所在的市局存入session,如果存在的话，省局用户不存在
            CXtDpt shiju = this.dptService.queryShiDpt(dpt.getId());
            session.put("shiju", shiju);

            //将用户所在的处（地方局）存入session，如果存在的话，省市局用户不存在
            CXtDpt chuju = this.dptService.queryChuDpt(dpt.getId());
            session.put("chuju", chuju);

            //将用户所在部门管辖行政区划信息存入session
            List<CZdXzqhdm> manxzqh = this.dptService.queryDptManXzqh(dpt.getId());
            session.put("manxzqh", manxzqh);

            BaseResultOK baseResultOK = new BaseResultOK();
            baseResultOK.addToMap("user", user);
            user.setUpdatetime(new Date());

            logService.addLog(ModuleName.XXWH_YYGL, OpName.LOGIN, "");
            return "success";
        }
    }
}
