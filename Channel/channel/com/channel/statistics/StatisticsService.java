package com.channel.statistics;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.annotation.Resource;

import com.channel.appurtenance.dao.AppurtenanceDao;
import com.channel.bean.*;
import com.channel.dic.dao.DicDao;
import com.channel.hangdao.service.HangDaoService;
import com.channel.hangdao.service.impl.HangDaoServiceImpl;
import com.channel.hangduan.dao.HangDuanDao;
import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import com.channel.model.bb.CBbJSYOriBb;
import com.channel.model.hd.CHdSn;
import com.channel.model.hd.CZdAppattribute;
import com.channel.statistics.model.CBfBaoBiao;
import com.channel.user.service.DptService;
import com.channel.user.service.impl.DptServiceImpl;
import com.channel.utils.ExcelRead;
import com.common.utils.KeyValueEntry;
import com.common.utils.StringUtil;
import com.opensymphony.xwork2.ActionContext;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.channel.bean.Constants;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.dic.service.XzqhdmService;
import com.channel.hangdao.dao.HangDaoDao;
import com.channel.hangduan.service.HangDuanService;
import com.channel.maintenance.dao.MaintenanceDao;
import com.channel.model.hd.CHdHdaojcxx;
import com.channel.model.hd.CHdHduanjcxx;
import com.channel.model.user.CXtDpt;
import com.channel.model.user.CXtUser;
import com.channel.model.user.CZdXzqhdm;
import com.channel.model.yh.CYhGgjbxx;
import com.channel.model.yh.CYhYdjdqk;
import com.channel.model.yh.CYhZxgc;
import com.channel.model.yh.CYhZxjbxx;
import com.channel.user.dao.UserDao;
import com.channel.user.dao.impl.DptDaoImpl;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.common.utils.FileUtils;

@Service("StatisticsService")
public class StatisticsService extends BaseService {

    @Resource(name = "StatisticsDao")
    private StatisticsDao statisticsDao;
    @Resource(name = "xzqhdmservice")
    private XzqhdmService xzqhdmService;
    @Resource(name = "hangdaoservice")
    private HangDaoService hangDaoService;
    @Resource(name = "dicdao")
    private DicDao dicDao;
    @Resource(name = "dptdao")
    private DptDaoImpl dptDao;
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmDaoImpl;
    @Resource(name = "maintenancedao")
    private MaintenanceDao maintenanceDao;
    @Resource(name = "hangduanservice")
    private HangDuanService hangDuanService;
    @Resource(name = "hangdaodao")
    private HangDaoDao hangDaoDao;
    @Resource(name = "hangduandao")
    private HangDuanDao hangDuanDao;
    @Resource
    private LogService logService;
    @Resource(name = "appurtenancedao")
    private AppurtenanceDao appurtenanceDao;
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

    /**
     * 简单的导出功能，必须指定templetename、rowdef,coldef及data
     * 报表左上角由斜线分隔rowdef/coldef
     *
     * @param bbname   报表名字
     * @param data     报表数据，
     * @param rowdef   行定义，即表格左上角靠左下位置文字
     * @param coldef   列定义，即表格左上角靠右上位置文字
     * @param title    报表标题
     * @param subtitle 报表副标题，如时间
     * @param left     报表表格上方最左
     * @param right    报表表格上方最右
     * @param footer   报表表格下方最左
     * @return
     */
    private BaseResult exportSimpleTable(String bbname,
                                         List<KeyValueEntry<String, List<String>>> data,
                                         String rowdef,
                                         String coldef,
                                         String title,
                                         String subtitle,
                                         String left,
                                         String right,
                                         String footer) throws Exception {
        BaseResult result = null;
        String templatedir = getContextPath() + "/template/";
        String baobiaodir = getContextPath() + "/baobiao/";
        // 拷贝模板
        String baobiaofilename = null;
        int flag = 0;
        if (data == null || data.size() == 0 || bbname == null || bbname.trim().equals("")) {
            return new BaseResultFailed("无法生成报表，参数不正确");
        }

        if ((rowdef == null || rowdef.trim().equals("")) && (coldef == null || coldef.trim().equals(""))) {
            return new BaseResultFailed("未定义报表表头");
        } else {
            if (rowdef != null && !rowdef.trim().equals("") && coldef != null && !coldef.trim().equals("")) {
                flag = 0;
            } else {
                flag = 1;
            }
        }

        //拷贝模板到临时报表下载目录
        if (flag == 0) {
            baobiaofilename = FileUtils.writeToFile2(new File(templatedir
                    + "/simple.xlsx"), baobiaodir + "/" + bbname + ".xlsx");
        } else {
            baobiaofilename = FileUtils.writeToFile2(new File(templatedir
                    + "/simple2.xlsx"), baobiaodir + "/" + bbname + ".xlsx");
        }
        int tablecolumns = 0;
        int tablerows = 0;
        //获得数据中的表格列数
        tablecolumns = data.get(0).getValue().size() + 1;
        tablerows = data.size();
        if (baobiaofilename != null && tablecolumns != 0 && tablerows != 0) {
            // 初始化poi
            File outputFile = new File(baobiaodir + "/" + baobiaofilename);
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                    outputFile));
            XSSFCellStyle style = null;
            Font font = null;
            XSSFSheet sheet = workbook.getSheetAt(0);

            // 写入标题
            if (title != null && !title.equals("")) {
                WriteCell(sheet, 1, 1, title, null);
                MergeCell(sheet, 1, 1, 1, tablecolumns + 1);
            }
            // 写入副标题
            if (subtitle != null && !subtitle.equals("")) {
                WriteCell(sheet, 2, 1, subtitle, null);
                MergeCell(sheet, 2, 1, 2, tablecolumns + 1);
            }
            //写入left
            if (left != null && !left.equals("")) {
                WriteCell(sheet, 3, 1, left, null);
                MergeCell(sheet, 3, 1, 3, 2);
            }

            //写入right
            if (right != null && !right.equals("")) {
                WriteCell(sheet, 3, tablecolumns + 1, right, null);
            }

            if (flag == 0) {
                //写入表格定义
                WriteCell(sheet, 5, 1, rowdef, null);
                WriteCell(sheet, 4, 2, coldef, null);
                SetCellSimpleStyle(workbook, sheet, 4, 1, BorderStyle.MEDIUM, null, null, BorderStyle.MEDIUM);
                SetCellSimpleStyle(workbook, sheet, 4, 2, BorderStyle.MEDIUM, BorderStyle.THIN, null, null);
                SetCellSimpleStyle(workbook, sheet, 5, 1, null, null, BorderStyle.MEDIUM, BorderStyle.MEDIUM);
                SetCellSimpleStyle(workbook, sheet, 5, 2, null, BorderStyle.THIN, BorderStyle.MEDIUM, null);
            } else {
                //写入表格定义
                String def = (rowdef != null && !rowdef.trim().equals("")) ? rowdef : coldef;
                WriteCell(sheet, 4, 1, def, null);
                MergeCell(sheet, 4, 1, 5, 2);
                SetCellSimpleStyle(workbook, sheet, 4, 1, BorderStyle.MEDIUM, null, null, BorderStyle.MEDIUM);
                SetCellSimpleStyle(workbook, sheet, 4, 2, BorderStyle.MEDIUM, BorderStyle.THIN, null, null);
                SetCellSimpleStyle(workbook, sheet, 5, 1, null, null, BorderStyle.MEDIUM, BorderStyle.MEDIUM);
                SetCellSimpleStyle(workbook, sheet, 5, 2, null, BorderStyle.THIN, BorderStyle.MEDIUM, null);
            }
            //写入数据
            for (int row = 0; row < data.size(); row++) {
                KeyValueEntry<String, List<String>> rowkv = data.get(row);

                //不为第一行，写入rowkv.key，并需要进行合并两格
                if (row != 0) {
                    //写入左侧行定义
                    String rowleft = rowkv.getKey();
                    WriteCell(sheet, row + 5, 1, rowleft, null);
                    MergeCell(sheet, row + 5, 1, row + 5, 2);

                    SetCellSimpleStyle(workbook, sheet, row + 5, 1,
                            null,
                            null,
                            row == data.size() - 1 ? BorderStyle.MEDIUM : BorderStyle.THIN,
                            BorderStyle.MEDIUM);
                    SetCellSimpleStyle(workbook, sheet, row + 5, 2,
                            null,
                            BorderStyle.THIN,
                            row == data.size() - 1 ? BorderStyle.MEDIUM : BorderStyle.THIN,
                            null);

                    //写入表格主体内容
                    for (int col = 0; col < rowkv.getValue().size(); col++) {
                        WriteCell(sheet, row + 5, col + 3, rowkv.getValue().get(col), null);
                        SetCellSimpleStyle(workbook, sheet, row + 5, col + 3,
                                null,
                                col == rowkv.getValue().size() - 1 ? BorderStyle.MEDIUM : BorderStyle.THIN,
                                row == data.size() - 1 ? BorderStyle.MEDIUM : BorderStyle.THIN,
                                null);
                    }
                } else {//第一行为表格头,不写入rowkv.key，上下合并两格
                    //写入表头内容
                    for (int col = 0; col < rowkv.getValue().size(); col++) {
                        WriteCell(sheet, 4, col + 3, rowkv.getValue().get(col), null);
                        MergeCell(sheet, 4, col + 3, 5, col + 3);
                        SetCellSimpleStyle(workbook, sheet, 4, col + 3,
                                BorderStyle.MEDIUM,
                                col == rowkv.getValue().size() - 1 ? BorderStyle.MEDIUM : BorderStyle.THIN,
                                null,
                                BorderStyle.THIN);
                        SetCellSimpleStyle(workbook, sheet, 5, col + 3,
                                null,
                                col == rowkv.getValue().size() - 1 ? BorderStyle.MEDIUM : BorderStyle.THIN,
                                BorderStyle.MEDIUM,
                                BorderStyle.THIN);
                    }
                }
            }

            //写入footer
            if (footer != null && !footer.equals("")) {
                WriteCell(sheet, tablerows + 5, 1, footer, null);
                MergeCell(sheet, tablerows + 5, 1, tablerows + 6, tablecolumns + 1);
            }

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        }
        result = new BaseResultOK();
        result.addToMap("filename", baobiaofilename);
        result.addToMap("filepath", baobiaodir + "/" + baobiaofilename);
        return result;
    }

    public BaseResult queryGgTable(Date starttime, Date endtime,
                                   Integer flag, Integer dptid, Integer dptflag) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        CXtDpt dpt = this.dptDao.queryDptById(dptid);
        List<CYhGgjbxx> ggjbxxs = new ArrayList<CYhGgjbxx>();
        Date earliesttime = DateTimeUtil
                .getDateByStringFmt(
                        (DateTimeUtil.getTimeFmt(starttime, "yyyy") + "-01-01 00:00:00"),
                        "yyyy-MM-dd HH:mm:ss");
        if (dptflag == 0) {
            Integer[] arrInt = new Integer[]{new Integer(dptid)};
            ggjbxxs = this.maintenanceDao.queryGgTable(
                    arrInt, earliesttime, endtime);
        }
        if (dptflag == 1) {
            List<CXtDpt> dptlist = this.dptService.findChildrenNodes_r(dpt);
            int len = dptlist.size();
            Integer[] arrInt = new Integer[len + 1];
            for (int d = 0; d < len; d++) {
                arrInt[d] = new Integer(dptlist.get(d).getId());
            }
            arrInt[len] = new Integer(dptid);
            ggjbxxs = this.maintenanceDao.queryGgTable(
                    arrInt, earliesttime, endtime);
        }
        List<GgForm> returnlist = new ArrayList<GgForm>();
        Map<Integer, List<CYhGgjbxx>> map = new HashMap<Integer, List<CYhGgjbxx>>();
        if (ggjbxxs != null && ggjbxxs.size() > 0) {
            for (CYhGgjbxx gg : ggjbxxs) {
                Integer keyid = gg.getHdid();
                if (map.containsKey(keyid)) {
                    map.get(keyid).add(gg);
                } else {
                    List<CYhGgjbxx> list = new ArrayList<CYhGgjbxx>();
                    list.add(gg);
                    map.put(keyid, list);
                }
            }
        }
        Iterator<Integer> i = map.keySet().iterator();
        double sumclsl = 0;
        int sumcljg = 0;
        int sumsjsl = 0;
        int sumsjjg = 0;
        int sumzzjzwxfsl = 0;
        int sumzzjzwxfjg = 0;
        int sumglmtxfsl = 0;
        int sumglmtxfjg = 0;
        int sumxlzxfsl = 0;
        int sumxlzxfjg = 0;
        int sumhbwhzcsl = 0;
        int sumhbwhzsl = 0;
        int sumhbwhjg = 0;
        int sumwzahwqcssl = 0;
        int sumwzahwqcdwsl = 0;
        int sumwzahwqccsl = 0;
        int sumwzahwqcdsl = 0;
        int sumwzahwqcjg = 0;
        int sumlhyhsl = 0;
        int sumlhyhjg = 0;
        while (i.hasNext()) {
            Integer hdid = (Integer) i.next();
            List<CYhGgjbxx> ggs = map.get(hdid);
            CHdHdaojcxx hangdao = this.hangDaoDao.queryHangDaoById(hdid);
            double clsl = 0;
            int cljg = 0;
            int sjsl = 0;
            int sjjg = 0;
            int zzjzwxfsl = 0;
            int zzjzwxfjg = 0;
            int glmtxfsl = 0;
            int glmtxfjg = 0;
            int xlzxfsl = 0;
            int xlzxfjg = 0;
            int hbwhzcsl = 0;
            int hbwhzsl = 0;
            int hbwhjg = 0;
            int wzahwqcssl = 0;
            int wzahwqcdwsl = 0;
            int wzahwqccsl = 0;
            int wzahwqcdsl = 0;
            int wzahwqcjg = 0;
            int lhyhsl = 0;
            int lhyhjg = 0;
            for (int j = 0; j < ggs.size(); j++) {
                CYhGgjbxx gg = ggs.get(j);
                Date ny = gg.getStarttime();

                if (flag == Constants.STATISTIC_MONTH) {
                    if ((ny.after(starttime) || ny.getTime() == starttime.getTime())
                            && ny.before(endtime)) {
                        clsl += gg.getClsl() == null ? 0 : gg.getClsl();
                        cljg += gg.getCljg() == null ? 0 : gg.getCljg();
                        sjsl += gg.getSjsl() == null ? 0 : gg.getSjsl();
                        sjjg += gg.getSjjg() == null ? 0 : gg.getSjjg();
                        zzjzwxfsl += gg.getZzjzwxfsl() == null ? 0 : gg
                                .getZzjzwxfsl();
                        zzjzwxfjg += gg.getZzjzwxfjg() == null ? 0 : gg
                                .getZzjzwxfjg();
                        glmtxfsl += gg.getGlmtxfsl() == null ? 0 : gg
                                .getGlmtxfsl();
                        glmtxfjg += gg.getGlmtxfjg() == null ? 0 : gg
                                .getGlmtxfjg();
                        xlzxfsl += gg.getXlzxfsl() == null ? 0 : gg
                                .getXlzxfsl();
                        xlzxfjg += gg.getXlzxfjg() == null ? 0 : gg
                                .getXlzxfjg();
                        hbwhzcsl += gg.getHbwhzcsl() == null ? 0 : gg
                                .getHbwhzcsl();
                        hbwhzsl = gg.getHbwhzsl() == null ? 0 : gg
                                .getHbwhzsl();
                        hbwhjg += gg.getHbwhjg() == null ? 0 : gg
                                .getHbwhjg();
                        wzahwqcssl += gg.getWzahwqcssl() == null ? 0 : gg
                                .getWzahwqcssl();
                        wzahwqcdwsl += gg.getWzahwqcdwsl() == null ? 0 : gg
                                .getWzahwqcdwsl();
                        wzahwqccsl += gg.getWzahwqccsl() == null ? 0 : gg
                                .getWzahwqccsl();
                        wzahwqcdsl += gg.getWzahwqcdsl() == null ? 0 : gg
                                .getWzahwqcdsl();
                        wzahwqcjg += gg.getWzahwqcjg() == null ? 0 : gg
                                .getWzahwqcjg();
                        lhyhsl = gg.getLhyhsl() == null ? 0 : gg
                                .getLhyhsl();
                        lhyhjg += gg.getLhyhjg() == null ? 0 : gg
                                .getLhyhjg();
                    }
                }
                if (flag == Constants.STATISTIC_SEASON) {
                    clsl += gg.getClsl() == null ? 0 : gg.getClsl();
                    cljg += gg.getCljg() == null ? 0 : gg.getCljg();
                    sjsl += gg.getSjsl() == null ? 0 : gg.getSjsl();
                    sjjg += gg.getSjjg() == null ? 0 : gg.getSjjg();
                    zzjzwxfsl += gg.getZzjzwxfsl() == null ? 0 : gg
                            .getZzjzwxfsl();
                    zzjzwxfjg += gg.getZzjzwxfjg() == null ? 0 : gg
                            .getZzjzwxfjg();
                    glmtxfsl += gg.getGlmtxfsl() == null ? 0 : gg
                            .getGlmtxfsl();
                    glmtxfjg += gg.getGlmtxfjg() == null ? 0 : gg
                            .getGlmtxfjg();
                    xlzxfsl += gg.getXlzxfsl() == null ? 0 : gg
                            .getXlzxfsl();
                    xlzxfjg += gg.getXlzxfjg() == null ? 0 : gg
                            .getXlzxfjg();
                    hbwhzcsl += gg.getHbwhzcsl() == null ? 0 : gg
                            .getHbwhzcsl();
                    hbwhzsl = gg.getHbwhzsl() == null ? 0 : gg
                            .getHbwhzsl();
                    hbwhjg += gg.getHbwhjg() == null ? 0 : gg.getHbwhjg();
                    wzahwqcssl += gg.getWzahwqcssl() == null ? 0 : gg
                            .getWzahwqcssl();
                    wzahwqcdwsl += gg.getWzahwqcdwsl() == null ? 0 : gg
                            .getWzahwqcdwsl();
                    wzahwqccsl += gg.getWzahwqccsl() == null ? 0 : gg
                            .getWzahwqccsl();
                    wzahwqcdsl += gg.getWzahwqcdsl() == null ? 0 : gg
                            .getWzahwqcdsl();
                    wzahwqcjg += gg.getWzahwqcjg() == null ? 0 : gg
                            .getWzahwqcjg();
                    lhyhsl = gg.getLhyhsl() == null ? 0 : gg.getLhyhsl();
                    lhyhjg += gg.getLhyhjg() == null ? 0 : gg.getLhyhjg();
                }

            }
            GgForm gg = new GgForm();
            gg.setHdname(hangdao.getHdmc());
            gg.setClsl(clsl);
            gg.setCljg(cljg);
            gg.setSjsl(sjsl);
            gg.setSjjg(sjjg);
            gg.setZzjzwxfsl(zzjzwxfsl);
            gg.setZzjzwxfjg(zzjzwxfjg);
            gg.setGlmtxfsl(glmtxfsl);
            gg.setGlmtxfjg(glmtxfjg);
            gg.setXlzxfsl(xlzxfsl);
            gg.setXlzxfjg(xlzxfjg);
            gg.setHbwhzcsl(hbwhzcsl);
            gg.setHbwhzsl(hbwhzsl);
            gg.setHbwhjg(hbwhjg);
            gg.setWzahwqcssl(wzahwqcssl);
            gg.setWzahwqcdwsl(wzahwqcdwsl);
            gg.setWzahwqccsl(wzahwqccsl);
            gg.setWzahwqcdsl(wzahwqcdsl);
            gg.setWzahwqcjg(wzahwqcjg);
            gg.setLhyhsl(lhyhsl);
            gg.setLhyhjg(lhyhjg);
            gg.setTotalamount(cljg + sjjg + zzjzwxfjg + glmtxfjg + xlzxfjg
                    + hbwhjg + wzahwqcjg + lhyhjg);
            sumclsl += clsl;
            sumcljg += cljg;
            sumsjsl += sjsl;
            sumsjjg += sjjg;
            sumzzjzwxfsl += zzjzwxfsl;
            sumzzjzwxfjg += zzjzwxfjg;
            sumglmtxfsl += glmtxfsl;
            sumglmtxfjg += glmtxfjg;
            sumxlzxfsl += xlzxfsl;
            sumxlzxfjg += xlzxfjg;
            sumhbwhzcsl += hbwhzcsl;
            sumhbwhzsl += hbwhzsl;
            sumhbwhjg += hbwhjg;
            sumwzahwqcssl += wzahwqcssl;
            sumwzahwqcdwsl += wzahwqcdwsl;
            sumwzahwqccsl += wzahwqccsl;
            sumwzahwqcdsl += wzahwqcdsl;
            sumwzahwqcjg += wzahwqcjg;
            sumlhyhsl += lhyhsl;
            sumlhyhjg += lhyhjg;
            returnlist.add(gg);
            clsl = 0;
            cljg = 0;
            sjsl = 0;
            sjjg = 0;
            zzjzwxfsl = 0;
            zzjzwxfjg = 0;
            glmtxfsl = 0;
            glmtxfjg = 0;
            xlzxfsl = 0;
            xlzxfjg = 0;
            hbwhzcsl = 0;
            hbwhzsl = 0;
            hbwhjg = 0;
            wzahwqcssl = 0;
            wzahwqcdwsl = 0;
            wzahwqccsl = 0;
            wzahwqcdsl = 0;
            wzahwqcjg = 0;
            lhyhsl = 0;
            lhyhjg = 0;
        }
        List<String> hdmc = new ArrayList<String>();
        List<Double> clsl = new ArrayList<Double>();
        List<Integer> cljg = new ArrayList<Integer>();
        List<Integer> sjsl = new ArrayList<Integer>();
        List<Integer> sjjg = new ArrayList<Integer>();
        List<Integer> zzjzwxfsl = new ArrayList<Integer>();
        List<Integer> zzjzwxfjg = new ArrayList<Integer>();
        List<Integer> glmtxfsl = new ArrayList<Integer>();
        List<Integer> glmtxfjg = new ArrayList<Integer>();
        List<Integer> xlzxfsl = new ArrayList<Integer>();
        List<Integer> xlzxfjg = new ArrayList<Integer>();
        List<String> hbwhsl = new ArrayList<String>();
        List<Integer> hbwhjg = new ArrayList<Integer>();
        List<String> wzahwqcssl = new ArrayList<String>();
        List<String> wzahwqccsl = new ArrayList<String>();
        List<Integer> wzahwqcjg = new ArrayList<Integer>();
        List<Integer> lhyhsl = new ArrayList<Integer>();
        List<Integer> lhyhjg = new ArrayList<Integer>();
        List<Integer> totalamount = new ArrayList<Integer>();
        int zj = 0;//总计
        //处理数据
        for (GgForm ggForm : returnlist) {
            hdmc.add(ggForm.getHdname());
            clsl.add(new BigDecimal(ggForm.getClsl()).setScale(4, RoundingMode.HALF_UP).doubleValue());
            cljg.add(ggForm.getCljg());
            sjsl.add(ggForm.getSjsl());
            sjjg.add(ggForm.getSjjg());
            zzjzwxfsl.add(ggForm.getZzjzwxfsl());
            zzjzwxfjg.add(ggForm.getZzjzwxfjg());
            glmtxfsl.add(ggForm.getGlmtxfsl());
            glmtxfjg.add(ggForm.getGlmtxfjg());
            xlzxfsl.add(ggForm.getXlzxfsl());
            xlzxfjg.add(ggForm.getXlzxfjg());
            hbwhsl.add(ggForm.getHbwhzsl() + "/" + ggForm.getHbwhzcsl());
            hbwhjg.add(ggForm.getHbwhjg());
            wzahwqcssl.add(ggForm.getWzahwqcssl() + "/"
                    + ggForm.getWzahwqcdwsl());
            wzahwqccsl.add(ggForm.getWzahwqccsl() + "/"
                    + ggForm.getWzahwqcdsl());
            wzahwqcjg.add(ggForm.getWzahwqcjg());
            lhyhsl.add(ggForm.getLhyhsl());
            lhyhjg.add(ggForm.getLhyhjg());
            totalamount.add(ggForm.getTotalamount());
            zj += ggForm.getTotalamount();
        }
        totalamount.add(zj);
        //小计
        BaseResultOK baseResultOK = new BaseResultOK(returnlist);
        GgSlJgTotal sljg = new GgSlJgTotal(sumclsl, sumcljg, sumsjsl,
                sumsjjg, sumzzjzwxfsl, sumzzjzwxfjg, sumglmtxfsl,
                sumglmtxfjg, sumxlzxfsl, sumxlzxfjg,
                String.valueOf(sumhbwhzsl) + "/"
                        + String.valueOf(sumhbwhzcsl), sumhbwhjg,
                String.valueOf(sumwzahwqcssl) + "/"
                        + String.valueOf(sumwzahwqcdwsl),
                String.valueOf(sumwzahwqccsl) + "/"
                        + String.valueOf(sumwzahwqcdsl), sumwzahwqcjg,
                sumlhyhsl, sumlhyhjg);
        //处理时间
        if (flag == Constants.STATISTIC_MONTH) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
            String selstart = sdf.format(starttime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endtime);
            calendar.add(Calendar.MONTH, -1);
            String selend = sdf.format(calendar.getTime());
            String selyear = selstart.substring(0, 4);
            baseResultOK.addToMap("selstart", selstart);
            baseResultOK.addToMap("selend", selend);
            baseResultOK.addToMap("selyear", selyear);
        }
        if (flag == Constants.STATISTIC_SEASON) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String selstart = sdf.format(starttime);
            String selyear = selstart.substring(0, 4);
            String s = selstart.substring(4);
            int selmonth = Integer.parseInt(s);
            int selseason = 0;
            switch (selmonth) {
                case 1:
                    selseason = 1;
                    break;
                case 4:
                    selseason = 2;
                    break;
                case 7:
                    selseason = 3;
                    break;
                case 10:
                    selseason = 4;
                    break;
                default:
                    break;
            }
            baseResultOK.addToMap("selyear", selyear);
            baseResultOK.addToMap("selseason", selseason);
        }
        baseResultOK.addToMap("hdmc", hdmc);
        baseResultOK.addToMap("clsl", clsl);
        baseResultOK.addToMap("cljg", cljg);
        baseResultOK.addToMap("sjsl", sjsl);
        baseResultOK.addToMap("sjjg", sjjg);
        baseResultOK.addToMap("zzjzwxfsl", zzjzwxfsl);
        baseResultOK.addToMap("zzjzwxfjg", zzjzwxfjg);
        baseResultOK.addToMap("glmtxfsl", glmtxfsl);
        baseResultOK.addToMap("glmtxfjg", glmtxfjg);
        baseResultOK.addToMap("xlzxfsl", xlzxfsl);
        baseResultOK.addToMap("xlzxfjg", xlzxfjg);
        baseResultOK.addToMap("hbwhsl", hbwhsl);
        baseResultOK.addToMap("hbwhjg", hbwhjg);
        baseResultOK.addToMap("wzahwqcssl", wzahwqcssl);
        baseResultOK.addToMap("wzahwqccsl", wzahwqccsl);
        baseResultOK.addToMap("wzahwqcjg", wzahwqcjg);
        baseResultOK.addToMap("lhyhsl", lhyhsl);
        baseResultOK.addToMap("lhyhjg", lhyhjg);
        baseResultOK.addToMap("totalamount", totalamount);
        baseResultOK.addToMap("sljg", sljg);
        baseResultOK.addToMap("dpt", dpt.getName());
        return baseResultOK;
    }

    public BaseResult exportGgTable(Date starttime, Date endtime,
                                    Integer flag, Integer dptid, Integer dptflag) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        CXtDpt dpt = this.dptDao.queryDptById(dptid);
        List<CYhGgjbxx> ggjbxxs = new ArrayList<CYhGgjbxx>();
        Date earliesttime = DateTimeUtil
                .getDateByStringFmt(
                        (DateTimeUtil.getTimeFmt(starttime, "yyyy") + "-01-01 00:00:00"),
                        "yyyy-MM-dd HH:mm:ss");
        if (dptflag == 0) {
            Integer[] arrInt = new Integer[]{new Integer(dptid)};
            ggjbxxs = this.maintenanceDao.queryGgTable(
                    arrInt, earliesttime, endtime);
        }
        if (dptflag == 1) {
            List<CXtDpt> dptlist = this.dptService.findChildrenNodes_r(dpt);
            int len = dptlist.size();
            Integer[] arrInt = new Integer[len + 1];
            for (int d = 0; d < len; d++) {
                arrInt[d] = new Integer(dptlist.get(d).getId());
            }
            arrInt[len] = new Integer(dptid);
            ggjbxxs = this.maintenanceDao.queryGgTable(
                    arrInt, earliesttime, endtime);
        }
        List<GgForm> returnlist = new ArrayList<GgForm>();
        Map<Integer, List<CYhGgjbxx>> map = new HashMap<Integer, List<CYhGgjbxx>>();
        if (ggjbxxs != null && ggjbxxs.size() > 0) {
            for (CYhGgjbxx gg : ggjbxxs) {
                Integer keyid = gg.getHdid();
                if (map.containsKey(keyid)) {
                    map.get(keyid).add(gg);
                } else {
                    List<CYhGgjbxx> list = new ArrayList<CYhGgjbxx>();
                    list.add(gg);
                    map.put(keyid, list);
                }
            }
        }
        Iterator<Integer> i = map.keySet().iterator();
        double sumclsl = 0;
        int sumcljg = 0;
        int sumsjsl = 0;
        int sumsjjg = 0;
        int sumzzjzwxfsl = 0;
        int sumzzjzwxfjg = 0;
        int sumglmtxfsl = 0;
        int sumglmtxfjg = 0;
        int sumxlzxfsl = 0;
        int sumxlzxfjg = 0;
        int sumhbwhzcsl = 0;
        int sumhbwhzsl = 0;
        int sumhbwhjg = 0;
        int sumwzahwqcssl = 0;
        int sumwzahwqcdwsl = 0;
        int sumwzahwqccsl = 0;
        int sumwzahwqcdsl = 0;
        int sumwzahwqcjg = 0;
        int sumlhyhsl = 0;
        int sumlhyhjg = 0;
        while (i.hasNext()) {
            Integer hdid = (Integer) i.next();
            List<CYhGgjbxx> ggs = map.get(hdid);
            CHdHdaojcxx hangdao = this.hangDaoDao.queryHangDaoById(hdid);
            double clsl = 0;
            int cljg = 0;
            int sjsl = 0;
            int sjjg = 0;
            int zzjzwxfsl = 0;
            int zzjzwxfjg = 0;
            int glmtxfsl = 0;
            int glmtxfjg = 0;
            int xlzxfsl = 0;
            int xlzxfjg = 0;
            int hbwhzcsl = 0;
            int hbwhzsl = 0;
            int hbwhjg = 0;
            int wzahwqcssl = 0;
            int wzahwqcdwsl = 0;
            int wzahwqccsl = 0;
            int wzahwqcdsl = 0;
            int wzahwqcjg = 0;
            int lhyhsl = 0;
            int lhyhjg = 0;
            for (int j = 0; j < ggs.size(); j++) {
                CYhGgjbxx gg = ggs.get(j);
                Date ny = gg.getStarttime();
                if (flag == Constants.STATISTIC_MONTH) {
                    if ((ny.after(starttime) || ny.getTime() == starttime.getTime())
                            && ny.before(endtime)) {
                        clsl += gg.getClsl() == null ? 0 : gg.getClsl();
                        cljg += gg.getCljg() == null ? 0 : gg.getCljg();
                        sjsl += gg.getSjsl() == null ? 0 : gg.getSjsl();
                        sjjg += gg.getSjjg() == null ? 0 : gg.getSjjg();
                        zzjzwxfsl += gg.getZzjzwxfsl() == null ? 0 : gg
                                .getZzjzwxfsl();
                        zzjzwxfjg += gg.getZzjzwxfjg() == null ? 0 : gg
                                .getZzjzwxfjg();
                        glmtxfsl += gg.getGlmtxfsl() == null ? 0 : gg
                                .getGlmtxfsl();
                        glmtxfjg += gg.getGlmtxfjg() == null ? 0 : gg
                                .getGlmtxfjg();
                        xlzxfsl += gg.getXlzxfsl() == null ? 0 : gg
                                .getXlzxfsl();
                        xlzxfjg += gg.getXlzxfjg() == null ? 0 : gg
                                .getXlzxfjg();
                        hbwhzcsl += gg.getHbwhzcsl() == null ? 0 : gg
                                .getHbwhzcsl();
                        hbwhzsl = gg.getHbwhzsl() == null ? 0 : gg
                                .getHbwhzsl();
                        hbwhjg += gg.getHbwhjg() == null ? 0 : gg
                                .getHbwhjg();
                        wzahwqcssl += gg.getWzahwqcssl() == null ? 0 : gg
                                .getWzahwqcssl();
                        wzahwqcdwsl += gg.getWzahwqcdwsl() == null ? 0 : gg
                                .getWzahwqcdwsl();
                        wzahwqccsl += gg.getWzahwqccsl() == null ? 0 : gg
                                .getWzahwqccsl();
                        wzahwqcdsl += gg.getWzahwqcdsl() == null ? 0 : gg
                                .getWzahwqcdsl();
                        wzahwqcjg += gg.getWzahwqcjg() == null ? 0 : gg
                                .getWzahwqcjg();
                        lhyhsl = gg.getLhyhsl() == null ? 0 : gg
                                .getLhyhsl();
                        lhyhjg += gg.getLhyhjg() == null ? 0 : gg
                                .getLhyhjg();
                    }
                }
                //季度
                if (flag == Constants.STATISTIC_SEASON) {
                    clsl += gg.getClsl() == null ? 0 : gg.getClsl();
                    cljg += gg.getCljg() == null ? 0 : gg.getCljg();
                    sjsl += gg.getSjsl() == null ? 0 : gg.getSjsl();
                    sjjg += gg.getSjjg() == null ? 0 : gg.getSjjg();
                    zzjzwxfsl += gg.getZzjzwxfsl() == null ? 0 : gg
                            .getZzjzwxfsl();
                    zzjzwxfjg += gg.getZzjzwxfjg() == null ? 0 : gg
                            .getZzjzwxfjg();
                    glmtxfsl += gg.getGlmtxfsl() == null ? 0 : gg
                            .getGlmtxfsl();
                    glmtxfjg += gg.getGlmtxfjg() == null ? 0 : gg
                            .getGlmtxfjg();
                    xlzxfsl += gg.getXlzxfsl() == null ? 0 : gg
                            .getXlzxfsl();
                    xlzxfjg += gg.getXlzxfjg() == null ? 0 : gg
                            .getXlzxfjg();
                    hbwhzcsl += gg.getHbwhzcsl() == null ? 0 : gg
                            .getHbwhzcsl();
                    hbwhzsl = gg.getHbwhzsl() == null ? 0 : gg
                            .getHbwhzsl();
                    hbwhjg += gg.getHbwhjg() == null ? 0 : gg.getHbwhjg();
                    wzahwqcssl += gg.getWzahwqcssl() == null ? 0 : gg
                            .getWzahwqcssl();
                    wzahwqcdwsl += gg.getWzahwqcdwsl() == null ? 0 : gg
                            .getWzahwqcdwsl();
                    wzahwqccsl += gg.getWzahwqccsl() == null ? 0 : gg
                            .getWzahwqccsl();
                    wzahwqcdsl += gg.getWzahwqcdsl() == null ? 0 : gg
                            .getWzahwqcdsl();
                    wzahwqcjg += gg.getWzahwqcjg() == null ? 0 : gg
                            .getWzahwqcjg();
                    lhyhsl = gg.getLhyhsl() == null ? 0 : gg.getLhyhsl();
                    lhyhjg += gg.getLhyhjg() == null ? 0 : gg.getLhyhjg();
                }
            }
            GgForm gg = new GgForm();
            gg.setHdname(hangdao.getHdmc());
            gg.setClsl(clsl);
            gg.setCljg(cljg);
            gg.setSjsl(sjsl);
            gg.setSjjg(sjjg);
            gg.setZzjzwxfsl(zzjzwxfsl);
            gg.setZzjzwxfjg(zzjzwxfjg);
            gg.setGlmtxfsl(glmtxfsl);
            gg.setGlmtxfjg(glmtxfjg);
            gg.setXlzxfsl(xlzxfsl);
            gg.setXlzxfjg(xlzxfjg);
            gg.setHbwhzcsl(hbwhzcsl);
            gg.setHbwhzsl(hbwhzsl);
            gg.setHbwhjg(hbwhjg);
            gg.setWzahwqcssl(wzahwqcssl);
            gg.setWzahwqcdwsl(wzahwqcdwsl);
            gg.setWzahwqccsl(wzahwqccsl);
            gg.setWzahwqcdsl(wzahwqcdsl);
            gg.setWzahwqcjg(wzahwqcjg);
            gg.setLhyhsl(lhyhsl);
            gg.setLhyhjg(lhyhjg);
            gg.setTotalamount(cljg + sjjg + zzjzwxfjg + glmtxfjg + xlzxfjg
                    + hbwhjg + wzahwqcjg + lhyhjg);
            sumclsl += clsl;
            sumcljg += cljg;
            sumsjsl += sjsl;
            sumsjjg += sjjg;
            sumzzjzwxfsl += zzjzwxfsl;
            sumzzjzwxfjg += zzjzwxfjg;
            sumglmtxfsl += glmtxfsl;
            sumglmtxfjg += glmtxfjg;
            sumxlzxfsl += xlzxfsl;
            sumxlzxfjg += xlzxfjg;
            sumhbwhzcsl += hbwhzcsl;
            sumhbwhzsl += hbwhzsl;
            sumhbwhjg += hbwhjg;
            sumwzahwqcssl += wzahwqcssl;
            sumwzahwqcdwsl += wzahwqcdwsl;
            sumwzahwqccsl += wzahwqccsl;
            sumwzahwqcdsl += wzahwqcdsl;
            sumwzahwqcjg += wzahwqcjg;
            sumlhyhsl += lhyhsl;
            sumlhyhjg += lhyhjg;
            returnlist.add(gg);
            clsl = 0;
            cljg = 0;
            sjsl = 0;
            sjjg = 0;
            zzjzwxfsl = 0;
            zzjzwxfjg = 0;
            glmtxfsl = 0;
            glmtxfjg = 0;
            xlzxfsl = 0;
            xlzxfjg = 0;
            hbwhzcsl = 0;
            hbwhzsl = 0;
            hbwhjg = 0;
            wzahwqcssl = 0;
            wzahwqcdwsl = 0;
            wzahwqccsl = 0;
            wzahwqcdsl = 0;
            wzahwqcjg = 0;
            lhyhsl = 0;
            lhyhjg = 0;
        }
        List<String> hdmc = new ArrayList<String>();
        List<Double> clsl = new ArrayList<Double>();
        List<Integer> cljg = new ArrayList<Integer>();
        List<Integer> sjsl = new ArrayList<Integer>();
        List<Integer> sjjg = new ArrayList<Integer>();
        List<Integer> zzjzwxfsl = new ArrayList<Integer>();
        List<Integer> zzjzwxfjg = new ArrayList<Integer>();
        List<Integer> glmtxfsl = new ArrayList<Integer>();
        List<Integer> glmtxfjg = new ArrayList<Integer>();
        List<Integer> xlzxfsl = new ArrayList<Integer>();
        List<Integer> xlzxfjg = new ArrayList<Integer>();
        List<String> hbwhsl = new ArrayList<String>();
        List<Integer> hbwhjg = new ArrayList<Integer>();
        List<String> wzahwqcssl = new ArrayList<String>();
        List<String> wzahwqccsl = new ArrayList<String>();
        List<Integer> wzahwqcjg = new ArrayList<Integer>();
        List<Integer> lhyhsl = new ArrayList<Integer>();
        List<Integer> lhyhjg = new ArrayList<Integer>();
        List<Integer> totalamount = new ArrayList<Integer>();
        int zj = 0;//总计
        for (GgForm ggForm : returnlist) {
            hdmc.add(ggForm.getHdname());
            clsl.add(new BigDecimal(ggForm.getClsl()).setScale(4, RoundingMode.HALF_UP).doubleValue());
            cljg.add(ggForm.getCljg());
            sjsl.add(ggForm.getSjsl());
            sjjg.add(ggForm.getSjjg());
            zzjzwxfsl.add(ggForm.getZzjzwxfsl());
            zzjzwxfjg.add(ggForm.getZzjzwxfjg());
            glmtxfsl.add(ggForm.getGlmtxfsl());
            glmtxfjg.add(ggForm.getGlmtxfjg());
            xlzxfsl.add(ggForm.getXlzxfsl());
            xlzxfjg.add(ggForm.getXlzxfjg());
            hbwhsl.add(ggForm.getHbwhzsl() + "/" + ggForm.getHbwhzcsl());
            hbwhjg.add(ggForm.getHbwhjg());
            wzahwqcssl.add(ggForm.getWzahwqcssl() + "/"
                    + ggForm.getWzahwqcdwsl());
            wzahwqccsl.add(ggForm.getWzahwqccsl() + "/"
                    + ggForm.getWzahwqcdsl());
            wzahwqcjg.add(ggForm.getWzahwqcjg());
            lhyhsl.add(ggForm.getLhyhsl());
            lhyhjg.add(ggForm.getLhyhjg());
            totalamount.add(ggForm.getTotalamount());
            zj += ggForm.getTotalamount();
        }
        totalamount.add(zj);
        BaseResultOK baseResultOK = new BaseResultOK(returnlist);
        GgSlJgTotal sljg = new GgSlJgTotal(sumclsl, sumcljg, sumsjsl,
                sumsjjg, sumzzjzwxfsl, sumzzjzwxfjg, sumglmtxfsl,
                sumglmtxfjg, sumxlzxfsl, sumxlzxfjg,
                String.valueOf(sumhbwhzsl) + "/"
                        + String.valueOf(sumhbwhzcsl), sumhbwhjg,
                String.valueOf(sumwzahwqcssl) + "/"
                        + String.valueOf(sumwzahwqcdwsl),
                String.valueOf(sumwzahwqccsl) + "/"
                        + String.valueOf(sumwzahwqcdsl), sumwzahwqcjg,
                sumlhyhsl, sumlhyhjg);
        String seltime = "";
        if (flag == Constants.STATISTIC_MONTH) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
            String selstart = sdf.format(starttime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endtime);
            calendar.add(Calendar.MONTH, -1);
            String selend = sdf.format(calendar.getTime());
            String selyear = selstart.substring(0, 4);
            seltime = selstart + "至" + selend;
        }
        if (flag == Constants.STATISTIC_SEASON) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String selstart = sdf.format(starttime);
            String selyear = selstart.substring(0, 4);
            String s = selstart.substring(4);
            int selmonth = Integer.parseInt(s);
            int selseason = 0;
            switch (selmonth) {
                case 1:
                    selseason = 1;
                    break;
                case 4:
                    selseason = 2;
                    break;
                case 7:
                    selseason = 3;
                    break;
                case 10:
                    selseason = 4;
                    break;
                default:
                    break;
            }
            seltime = selyear + "年" + selseason + "季度";
        }
        String outfilepath = "";
        File inputfile = null;
        File outputfile = null;
        try {
            String path = this.getRealPath("/");
            inputfile = new File(path + "/template/内河骨干航道例行养护工作统计表.xlsx");
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

            XSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 24); // 字体高度
            font.setFontName("宋体"); // 字体
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); // 宽度
            //表头
            CellStyle titleStyle = workbook.createCellStyle(); // 样式
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            titleStyle.setFont(font);

            //时间
            CellStyle timeStyle = workbook.createCellStyle(); // 样式
            timeStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            timeStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


            XSSFSheet sheet = workbook.getSheet("内河骨干航道例行养护工作统计表");
            // 表头

            // 主体
            int n = 5;
            int datanum = hdmc.size();
            int totaln = n + 2 * (datanum + 2);

            sheet.addMergedRegion(new CellRangeAddress(0, 3, 1,
                    totaln - 1));
            sheet.getRow(0).getCell(1).setCellValue("内河骨干航道例行养护工作统计表");
            sheet.getRow(0).getCell(1).setCellStyle(titleStyle);
            //单位
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 1,
                    3));
            //时间
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 4,
                    totaln - 1));
            sheet.getRow(4).getCell(1).setCellValue("单位:" + dpt.getName());
            sheet.getRow(4).getCell(4).setCellValue(seltime);
            sheet.getRow(4).getCell(4).setCellStyle(timeStyle);

            // 航道名称 合计cell
            for (int j = 5; j < 26; j++) {
                XSSFRow row = sheet.getRow(j);
                for (int k = n; k < totaln; k++) {
                    row.createCell(k).setCellStyle(cellStyle);
                }
                for (int k = n; k < totaln; k = k + 2) {
                    row.createCell(k).setCellStyle(cellStyle);
                    sheet.addMergedRegion(new CellRangeAddress(5, 6, k,
                            k + 1));
                    sheet.addMergedRegion(new CellRangeAddress(24, 25, k,
                            k + 1));
                }
            }
            for (int j = 7; j < 24; j++) {
                for (int k = n; k < totaln; k = k + 2) {
                    sheet.addMergedRegion(new CellRangeAddress(j, j, k,
                            k + 1));
                }
            }
            sheet.addMergedRegion(new CellRangeAddress(7, 8, totaln - 2,
                    totaln - 1));
            sheet.addMergedRegion(new CellRangeAddress(9, 10, totaln - 2,
                    totaln - 1));
            sheet.addMergedRegion(new CellRangeAddress(11, 12, totaln - 2,
                    totaln - 1));
            sheet.addMergedRegion(new CellRangeAddress(13, 14, totaln - 2,
                    totaln - 1));
            sheet.addMergedRegion(new CellRangeAddress(15, 16, totaln - 2,
                    totaln - 1));
            sheet.addMergedRegion(new CellRangeAddress(17, 18, totaln - 2,
                    totaln - 1));
            sheet.addMergedRegion(new CellRangeAddress(19, 21, totaln - 2,
                    totaln - 1));
            sheet.addMergedRegion(new CellRangeAddress(22, 23, totaln - 2,
                    totaln - 1));
            for (int j = 0; j < hdmc.size(); j++) {
                sheet.getRow(5).getCell(j * 2 + 5)
                        .setCellValue(hdmc.get(j));
            }
            sheet.getRow(5).getCell(totaln - 4).setCellValue("小计");
            sheet.getRow(5).getCell(totaln - 2).setCellValue("备注");
            for (int j = 0; j < clsl.size(); j++) {
                sheet.getRow(7)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                clsl.get(j) == 0 ? "" : String.valueOf(clsl
                                        .get(j)));
            }
            for (int j = 0; j < cljg.size(); j++) {
                sheet.getRow(8)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                cljg.get(j) == 0 ? "" : String.valueOf(cljg
                                        .get(j)));
            }

            for (int j = 0; j < sjsl.size(); j++) {
                sheet.getRow(9)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                sjsl.get(j) == 0 ? "" : String.valueOf(sjsl
                                        .get(j)));
            }
            for (int j = 0; j < sjjg.size(); j++) {
                sheet.getRow(10)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                sjjg.get(j) == 0 ? "" : String.valueOf(sjjg
                                        .get(j)));
            }

            for (int j = 0; j < zzjzwxfsl.size(); j++) {
                sheet.getRow(11)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                zzjzwxfsl.get(j) == 0 ? "" : String
                                        .valueOf(zzjzwxfsl.get(j)));
            }
            for (int j = 0; j < zzjzwxfjg.size(); j++) {
                sheet.getRow(12)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                zzjzwxfjg.get(j) == 0 ? "" : String
                                        .valueOf(zzjzwxfjg.get(j)));
            }

            for (int j = 0; j < glmtxfsl.size(); j++) {
                sheet.getRow(13)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                glmtxfsl.get(j) == 0 ? "" : String
                                        .valueOf(glmtxfsl.get(j)));
            }
            for (int j = 0; j < glmtxfjg.size(); j++) {
                sheet.getRow(14)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                glmtxfjg.get(j) == 0 ? "" : String
                                        .valueOf(glmtxfjg.get(j)));
            }

            for (int j = 0; j < xlzxfsl.size(); j++) {
                sheet.getRow(15)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                xlzxfsl.get(j) == 0 ? "" : String
                                        .valueOf(xlzxfsl.get(j)));
            }
            for (int j = 0; j < xlzxfjg.size(); j++) {
                sheet.getRow(16)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                xlzxfjg.get(j) == 0 ? "" : String
                                        .valueOf(xlzxfjg.get(j)));
            }

            for (int j = 0; j < hbwhsl.size(); j++) {
                sheet.getRow(17)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                "0.0/0.0".equals(hbwhsl.get(j)) || "0/0".equals(hbwhsl.get(j)) ? ""
                                        : hbwhsl.get(j));
            }
            for (int j = 0; j < hbwhjg.size(); j++) {
                sheet.getRow(18)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                hbwhjg.get(j) == 0 ? "" : String
                                        .valueOf(hbwhjg.get(j)));
            }

            for (int j = 0; j < wzahwqcssl.size(); j++) {
                sheet.getRow(19)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                "0.0/0.0".equals(wzahwqcssl.get(j)) || "0/0".equals(wzahwqcssl.get(j)) ? ""
                                        : wzahwqcssl.get(j));
            }
            for (int j = 0; j < wzahwqccsl.size(); j++) {
                sheet.getRow(20)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                "0.0/0.0".equals(wzahwqccsl.get(j)) || "0/0".equals(wzahwqccsl.get(j)) ? ""
                                        : wzahwqccsl.get(j));
            }
            for (int j = 0; j < wzahwqcjg.size(); j++) {
                sheet.getRow(21)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                wzahwqcjg.get(j) == 0 ? "" : String
                                        .valueOf(wzahwqcjg.get(j)));
            }

            for (int j = 0; j < lhyhsl.size(); j++) {
                sheet.getRow(22)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                lhyhsl.get(j) == 0 ? "" : String
                                        .valueOf(lhyhsl.get(j)));
            }
            for (int j = 0; j < lhyhjg.size(); j++) {
                sheet.getRow(23)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                lhyhjg.get(j) == 0 ? "" : String
                                        .valueOf(lhyhjg.get(j)));
            }
            for (int j = 0; j < totalamount.size(); j++) {
                sheet.getRow(24)
                        .getCell(j * 2 + 5)
                        .setCellValue(
                                totalamount.get(j) == 0 ? "" : String
                                        .valueOf(totalamount.get(j)));
            }
            sheet.getRow(7)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumclsl() == 0 ? "" : String
                                    .valueOf(sljg.getSumclsl()));
            sheet.getRow(8)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumcljg() == 0 ? "" : String
                                    .valueOf(sljg.getSumcljg()));
            sheet.getRow(9)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumsjsl() == 0 ? "" : String
                                    .valueOf(sljg.getSumsjsl()));
            sheet.getRow(10)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumsjjg() == 0 ? "" : String
                                    .valueOf(sljg.getSumsjjg()));

            sheet.getRow(11)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumzzjzwxfsl() == 0 ? "" : String
                                    .valueOf(sljg.getSumzzjzwxfsl()));
            sheet.getRow(12)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumzzjzwxfjg() == 0 ? "" : String
                                    .valueOf(sljg.getSumzzjzwxfjg()));
            sheet.getRow(13)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumglmtxfsl() == 0 ? "" : String
                                    .valueOf(sljg.getSumglmtxfsl()));
            sheet.getRow(14)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumglmtxfjg() == 0 ? "" : String
                                    .valueOf(sljg.getSumglmtxfjg()));

            sheet.getRow(15)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumxlzxfsl() == 0 ? "" : String
                                    .valueOf(sljg.getSumxlzxfsl()));
            sheet.getRow(16)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumxlzxfjg() == 0 ? "" : String
                                    .valueOf(sljg.getSumxlzxfjg()));

            sheet.getRow(17)
                    .getCell(totaln - 4)
                    .setCellValue(
                            "0.0/0.0".equals(sljg.getSumhbwhsl()) || "0/0".equals(sljg.getSumhbwhsl()) ? ""
                                    : sljg.getSumhbwhsl());
            sheet.getRow(18)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumhbwhjg() == 0 ? "" : String
                                    .valueOf(sljg.getSumhbwhjg()));
            sheet.getRow(19)
                    .getCell(totaln - 4)
                    .setCellValue(
                            "0.0/0.0".equals(sljg.getSumwzahwqcssl()) || "0/0".equals(sljg.getSumwzahwqcssl()) ? ""
                                    : sljg.getSumwzahwqcssl());
            sheet.getRow(20)
                    .getCell(totaln - 4)
                    .setCellValue(
                            "0.0/0.0".equals(sljg.getSumwzahwqccsl()) || "0/0".equals(sljg.getSumwzahwqccsl()) ? ""
                                    : sljg.getSumwzahwqccsl());
            sheet.getRow(21)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumwzahwqcjg() == 0 ? "" : String
                                    .valueOf(sljg.getSumwzahwqcjg()));

            sheet.getRow(22)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumlhyhsl() == 0 ? "" : String
                                    .valueOf(sljg.getSumlhyhsl()));
            sheet.getRow(23)
                    .getCell(totaln - 4)
                    .setCellValue(
                            sljg.getSumlhyhjg() == 0 ? "" : String
                                    .valueOf(sljg.getSumlhyhjg()));
            FileOutputStream outfile = new FileOutputStream(outputfile);
            workbook.write(outfile);
            outfile.close();
            logService.addLog(ModuleName.TJBB_FD, OpName.EXPORT, "内河骨干航道例行养护工作统计表");
            baseResultOK.addToMap("filename", outputfile.getName());
            baseResultOK.addToMap("filepath", outfilepath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseResultOK;
    }

    public BaseResult queryZxTable(Date starttime, Date endtime,
                                   Integer flag, Integer dptid, Integer dptflag) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        CXtDpt dpt = this.dptDao.queryDptById(dptid);
        List<CYhZxjbxx> zxjbxxs = new ArrayList<CYhZxjbxx>();
        Date earliesttime = DateTimeUtil
                .getDateByStringFmt(
                        (DateTimeUtil.getTimeFmt(starttime, "yyyy") + "-01-01 00:00:00"),
                        "yyyy-MM-dd HH:mm:ss");
        //月度本月
        Calendar calmonth = Calendar.getInstance();
        calmonth.setTime(endtime);
        calmonth.add(Calendar.MONTH, -1);
        Date monthdate = calmonth.getTime();
        //查询出本年1月到结束月支线数据
        if (dptflag == 0) {
            Integer[] arrInt = new Integer[]{new Integer(dptid)};
            zxjbxxs = this.maintenanceDao.queryZxTable(arrInt,
                    earliesttime, endtime);
        }
        if (dptflag == 1) {
            List<CXtDpt> dptlist = this.dptService.findChildrenNodes_r(dpt);
            int len = dptlist.size();
            Integer[] arrInt = new Integer[len + 1];
            for (int d = 0; d < len; d++) {
                arrInt[d] = new Integer(dptlist.get(d).getId());
            }
            arrInt[len] = new Integer(dptid);
            zxjbxxs = this.maintenanceDao.queryZxTable(arrInt,
                    earliesttime, endtime);
        }
        //初始化
        List<String> cl = new ArrayList<String>();
        List<String> ahsj = new ArrayList<String>();
        List<String> zzjzwxf = new ArrayList<String>();
        List<String> wxglmt = new ArrayList<String>();
        List<String> hbwh = new ArrayList<String>();
        List<String> dlcc = new ArrayList<String>();
        List<String> qtqz = new ArrayList<String>();
        List<String> qtwxgc = new ArrayList<String>();
        double sumclsl = 0;
        int sumclje = 0;
        int sumahsjsl = 0;
        int sumahsjje = 0;
        int sumzzjzwxfsl = 0;
        int sumzzjzwxfje = 0;
        int sumwxglmtsl = 0;
        int sumwxglmtje = 0;
        int sumhbwhzsl = 0;
        int sumhbwhzcsl = 0;
        int sumhbwhje = 0;
        int sumdlccssl = 0;
        int sumdlccdwsl = 0;
        int sumdlccje = 0;
        int sumqtqzcsl = 0;
        int sumqtqzdsl = 0;
        int sumqtqzje = 0;
        int sumqtwxgcsl = 0;
        int sumqtwxgcje = 0;
        double totalclsl = 0;
        int totalclje = 0;
        int totalahsjsl = 0;
        int totalahsjje = 0;
        int totalzzjzwxfsl = 0;
        int totalzzjzwxfje = 0;
        int totalwxglmtsl = 0;
        int totalwxglmtje = 0;
        int totalhbwhzsl = 0;
        int totalhbwhzcsl = 0;
        int totalhbwhje = 0;
        int totaldlccssl = 0;
        int totaldlccdwsl = 0;
        int totaldlccje = 0;
        int totalqtqzcsl = 0;
        int totalqtqzdsl = 0;
        int totalqtqzje = 0;
        int totalqtwxgcsl = 0;
        int totalqtwxgcje = 0;
        CYhZxjbxx monthzx = new CYhZxjbxx();
        int totalmonth = 0;
        int totalmutimonth = 0;

        for (CYhZxjbxx zx : zxjbxxs) {
            //基础数据
            Date zxtime = zx.getStarttime();
            double clsl = zx.getClsl() == null ? 0.0 : zx.getClsl();
            int clje = StringUtil.setNullIsZero(zx.getClje());
            int ahsjsl = StringUtil.setNullIsZero(zx.getAhsjsl());
            int ahsjje = StringUtil.setNullIsZero(zx.getAhsjje());
            int zzjzwxfsl = StringUtil.setNullIsZero(zx.getZzjzwxfsl());
            int zzjzwxfje = StringUtil.setNullIsZero(zx.getZzjzwxfje());
            int wxglmtsl = StringUtil.setNullIsZero(zx.getWxglmtsl());
            int wxglmtje = StringUtil.setNullIsZero(zx.getWxglmtje());
            int hbwhzsl = StringUtil.setNullIsZero(zx.getHbwhzsl());
            int hbwhzcsl = StringUtil.setNullIsZero(zx.getHbwhzcsl());
            int hbwhje = StringUtil.setNullIsZero(zx.getHbwhje());
            int dlccssl = StringUtil.setNullIsZero(zx.getDlccssl());
            int dlccdwsl = StringUtil.setNullIsZero(zx.getDlccdwsl());
            int dlccje = StringUtil.setNullIsZero(zx.getDlccje());
            int qtqzcsl = StringUtil.setNullIsZero(zx.getQtqzcsl());
            int qtqzdsl = StringUtil.setNullIsZero(zx.getQtqzdsl());
            int qtqzje = StringUtil.setNullIsZero(zx.getQtqzje());
            int qtwxgcsl = StringUtil.setNullIsZero(zx.getQtwxgcsl());
            int qtwxgcje = StringUtil.setNullIsZero(zx.getQtwxgcje());
            //计算数据


            //月度
            if (zxtime.getTime() >= starttime.getTime() && zxtime.getTime() < endtime.getTime()) {//限定时间
                //月度的累计 或者季度的本季度
                if (flag == Constants.STATISTIC_MONTH) {
                    //本月
                    if (monthdate.getTime() <= zxtime.getTime()) {
                        sumclsl = clsl;
                        sumclje = clje;
                        sumahsjsl = ahsjsl;
                        sumahsjje = ahsjje;
                        sumzzjzwxfsl = zzjzwxfsl;
                        sumzzjzwxfje = zzjzwxfje;
                        sumwxglmtsl = wxglmtsl;
                        sumwxglmtje = wxglmtje;
                        sumhbwhzsl = hbwhzsl;
                        sumhbwhzcsl = hbwhzcsl;
                        sumhbwhje = hbwhje;
                        sumdlccssl = dlccssl;
                        sumdlccdwsl = dlccdwsl;
                        sumdlccje = dlccje;
                        sumqtqzcsl = qtqzcsl;
                        sumqtqzdsl = qtqzdsl;
                        sumqtqzje = qtqzje;
                        sumqtwxgcsl = qtwxgcsl;
                        sumqtwxgcje = qtwxgcje;
                    }
                    //累计
                    totalclsl += clsl;
                    totalclje += clje;
                    totalahsjsl += ahsjsl;
                    totalahsjje += ahsjje;
                    totalzzjzwxfsl += zzjzwxfsl;
                    totalzzjzwxfje += zzjzwxfje;
                    totalwxglmtsl += wxglmtsl;
                    totalwxglmtje += wxglmtje;
                    totalhbwhzsl = hbwhzsl;
                    totalhbwhzcsl += hbwhzcsl;
                    totalhbwhje += hbwhje;
                    totaldlccssl += dlccssl;
                    totaldlccdwsl += dlccdwsl;
                    totaldlccje += dlccje;
                    totalqtqzcsl += qtqzcsl;
                    totalqtqzdsl += qtqzdsl;
                    totalqtqzje += qtqzje;
                    totalqtwxgcsl += qtwxgcsl;
                    totalqtwxgcje += qtwxgcje;
                }
                if (flag == Constants.STATISTIC_SEASON) {
                    //本季度
                    sumclsl += clsl;
                    sumclje += clje;
                    sumahsjsl += ahsjsl;
                    sumahsjje += ahsjje;
                    sumzzjzwxfsl += zzjzwxfsl;
                    sumzzjzwxfje += zzjzwxfje;
                    sumwxglmtsl += wxglmtsl;
                    sumwxglmtje += wxglmtje;
                    sumhbwhzsl = hbwhzsl;
                    sumhbwhzcsl += hbwhzcsl;
                    sumhbwhje += hbwhje;
                    sumdlccssl += dlccssl;
                    sumdlccdwsl += dlccdwsl;
                    sumdlccje += dlccje;
                    sumqtqzcsl += qtqzcsl;
                    sumqtqzdsl += qtqzdsl;
                    sumqtqzje += qtqzje;
                    sumqtwxgcsl += qtwxgcsl;
                    sumqtwxgcje += qtwxgcje;
                }
            }
            //季度 自年初累计
            if (flag == Constants.STATISTIC_SEASON) {
                totalclsl += clsl;
                totalclje += clje;
                totalahsjsl += ahsjsl;
                totalahsjje += ahsjje;
                totalzzjzwxfsl += zzjzwxfsl;
                totalzzjzwxfje += zzjzwxfje;
                totalwxglmtsl += wxglmtsl;
                totalwxglmtje += wxglmtje;
                totalhbwhzsl = hbwhzsl;
                totalhbwhzcsl += hbwhzcsl;
                totalhbwhje += hbwhje;
                totaldlccssl += dlccssl;
                totaldlccdwsl += dlccdwsl;
                totaldlccje += dlccje;
                totalqtqzcsl += qtqzcsl;
                totalqtqzdsl += qtqzdsl;
                totalqtqzje += qtqzje;
                totalqtwxgcsl += qtwxgcsl;
                totalqtwxgcje += qtwxgcje;
            }
        }
        //处理返回数据
        cl.add(sumclsl == 0.0 ? "" : new BigDecimal(sumclsl).setScale(4, RoundingMode.HALF_UP).toString());
        cl.add(totalclsl == 0.0 ? "" : new BigDecimal(totalclsl).setScale(4, RoundingMode.HALF_UP).toString());
        cl.add(StringUtil.getStringRound(sumclje));
        cl.add(StringUtil.getStringRound(totalclje));
        ahsj.add(StringUtil.getStringIsNull(sumahsjsl));
        ahsj.add(StringUtil.getStringIsNull(totalahsjsl));
        ahsj.add(StringUtil.getStringRound(sumahsjje));
        ahsj.add(StringUtil.getStringRound(totalahsjje));
        zzjzwxf.add(StringUtil.getStringIsNull(sumzzjzwxfsl));
        zzjzwxf.add(StringUtil.getStringIsNull(totalzzjzwxfsl));
        zzjzwxf.add(StringUtil.getStringRound(sumzzjzwxfje));
        zzjzwxf.add(StringUtil.getStringRound(totalzzjzwxfje));
        wxglmt.add(StringUtil.getStringIsNull(sumwxglmtsl));
        wxglmt.add(StringUtil.getStringIsNull(totalwxglmtsl));
        wxglmt.add(StringUtil.getStringRound(sumwxglmtje));
        wxglmt.add(StringUtil.getStringRound(totalwxglmtje));
        hbwh.add(StringUtil.getStringIsNull(sumhbwhzsl) + "/"
                + StringUtil.getStringIsNull(sumhbwhzcsl));
        hbwh.add(StringUtil.getStringIsNull(totalhbwhzsl) + "/"
                + StringUtil.getStringIsNull(totalhbwhzcsl));
        hbwh.add(StringUtil.getStringRound(sumhbwhje));
        hbwh.add(StringUtil.getStringRound(totalhbwhje));
        dlcc.add(StringUtil.getStringIsNull(sumdlccssl) + "/"
                + StringUtil.getStringIsNull(sumdlccdwsl));
        dlcc.add(StringUtil.getStringIsNull(totaldlccssl) + "/"
                + StringUtil.getStringIsNull(totaldlccdwsl));
        dlcc.add(StringUtil.getStringRound(sumdlccje));
        dlcc.add(StringUtil.getStringRound(totaldlccje));
        qtqz.add(StringUtil.getStringIsNull(sumqtqzcsl) + "/"
                + StringUtil.getStringIsNull(sumqtqzdsl));
        qtqz.add(StringUtil.getStringIsNull(totalqtqzcsl) + "/"
                + StringUtil.getStringIsNull(totalqtqzdsl));
        qtqz.add(StringUtil.getStringRound(sumqtqzje));
        qtqz.add(StringUtil.getStringRound(totalqtqzje));
        qtwxgc.add(StringUtil.getStringIsNull(sumqtwxgcsl));
        qtwxgc.add(StringUtil.getStringIsNull(totalqtwxgcsl));
        qtwxgc.add(StringUtil.getStringRound(sumqtwxgcje));
        qtwxgc.add(StringUtil.getStringRound(totalqtwxgcje));
        totalmonth = sumclje + sumahsjje + sumzzjzwxfje + sumwxglmtje
                + sumhbwhje + sumdlccje + sumqtqzje + sumqtwxgcje;
        totalmutimonth = totalclje + totalahsjje + totalzzjzwxfje
                + totalwxglmtje + totalhbwhje + totaldlccje
                + totalqtqzje + totalqtwxgcje;

        BaseResultOK baseResultOK = new BaseResultOK();
        baseResultOK.addToMap("cl", cl);
        baseResultOK.addToMap("ahsj", ahsj);
        baseResultOK.addToMap("zzjzwxf", zzjzwxf);
        baseResultOK.addToMap("wxglmt", wxglmt);
        baseResultOK.addToMap("hbwh", hbwh);
        baseResultOK.addToMap("dlcc", dlcc);
        baseResultOK.addToMap("qtqz", qtqz);
        baseResultOK.addToMap("qtwxgc", qtwxgc);
        baseResultOK.addToMap("totalmonth", StringUtil.getStringRound(totalmonth));
        baseResultOK.addToMap("totalmutimonth", StringUtil.getStringRound(totalmutimonth));

        if (flag == Constants.STATISTIC_MONTH) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
            String selstart = sdf.format(starttime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endtime);
            calendar.add(Calendar.MONTH, -1);
            String selend = sdf.format(calendar.getTime());
            String selyear = selstart.substring(0, 4);
            baseResultOK.addToMap("selstart", selstart);
            baseResultOK.addToMap("selend", selend);
            baseResultOK.addToMap("selyear", selyear);
        }
        if (flag == Constants.STATISTIC_SEASON) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String selstart = sdf.format(starttime);
            String selyear = selstart.substring(0, 4);
            String s = selstart.substring(4);
            int selmonth = Integer.parseInt(s);
            int selseason = 0;
            switch (selmonth) {
                case 1:
                    selseason = 1;
                    break;
                case 4:
                    selseason = 2;
                    break;
                case 7:
                    selseason = 3;
                    break;
                case 10:
                    selseason = 4;
                    break;
                default:
                    break;
            }
            baseResultOK.addToMap("selyear", selyear);
            baseResultOK.addToMap("selseason", selseason);

        }
        baseResultOK.addToMap("dpt", dpt.getName());
        return baseResultOK;
    }

    public BaseResult exportZxTable(Date starttime, Date endtime,
                                    Integer flag, Integer dptid, Integer dptflag) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        CXtDpt dpt = this.dptDao.queryDptById(dptid);
        List<CYhZxjbxx> zxjbxxs = new ArrayList<CYhZxjbxx>();
        Date earliesttime = DateTimeUtil
                .getDateByStringFmt(
                        (DateTimeUtil.getTimeFmt(starttime, "yyyy") + "-01-01 00:00:00"),
                        "yyyy-MM-dd HH:mm:ss");
        //月度本月
        Calendar calmonth = Calendar.getInstance();
        calmonth.setTime(endtime);
        calmonth.add(Calendar.MONTH, -1);
        Date monthdate = calmonth.getTime();
        //查询出本年1月到结束月支线数据
        if (dptflag == 0) {
            Integer[] arrInt = new Integer[]{new Integer(dptid)};
            zxjbxxs = this.maintenanceDao.queryZxTable(arrInt,
                    earliesttime, endtime);
        }
        if (dptflag == 1) {
            List<CXtDpt> dptlist = this.dptService.findChildrenNodes_r(dpt);
            int len = dptlist.size();
            Integer[] arrInt = new Integer[len + 1];
            for (int d = 0; d < len; d++) {
                arrInt[d] = new Integer(dptlist.get(d).getId());
            }
            arrInt[len] = new Integer(dptid);
            zxjbxxs = this.maintenanceDao.queryZxTable(arrInt,
                    earliesttime, endtime);
        }
        //初始化
        List<String> cl = new ArrayList<String>();
        List<String> ahsj = new ArrayList<String>();
        List<String> zzjzwxf = new ArrayList<String>();
        List<String> wxglmt = new ArrayList<String>();
        List<String> hbwh = new ArrayList<String>();
        List<String> dlcc = new ArrayList<String>();
        List<String> qtqz = new ArrayList<String>();
        List<String> qtwxgc = new ArrayList<String>();
        double sumclsl = 0;
        int sumclje = 0;
        int sumahsjsl = 0;
        int sumahsjje = 0;
        int sumzzjzwxfsl = 0;
        int sumzzjzwxfje = 0;
        int sumwxglmtsl = 0;
        int sumwxglmtje = 0;
        int sumhbwhzsl = 0;
        int sumhbwhzcsl = 0;
        int sumhbwhje = 0;
        int sumdlccssl = 0;
        int sumdlccdwsl = 0;
        int sumdlccje = 0;
        int sumqtqzcsl = 0;
        int sumqtqzdsl = 0;
        int sumqtqzje = 0;
        int sumqtwxgcsl = 0;
        int sumqtwxgcje = 0;
        double totalclsl = 0;
        int totalclje = 0;
        int totalahsjsl = 0;
        int totalahsjje = 0;
        int totalzzjzwxfsl = 0;
        int totalzzjzwxfje = 0;
        int totalwxglmtsl = 0;
        int totalwxglmtje = 0;
        int totalhbwhzsl = 0;
        int totalhbwhzcsl = 0;
        int totalhbwhje = 0;
        int totaldlccssl = 0;
        int totaldlccdwsl = 0;
        int totaldlccje = 0;
        int totalqtqzcsl = 0;
        int totalqtqzdsl = 0;
        int totalqtqzje = 0;
        int totalqtwxgcsl = 0;
        int totalqtwxgcje = 0;
        CYhZxjbxx monthzx = new CYhZxjbxx();
        int totalmonth = 0;
        int totalmutimonth = 0;

        for (CYhZxjbxx zx : zxjbxxs) {
            //基础数据
            Date zxtime = zx.getStarttime();
            double clsl = zx.getClsl() == null ? 0.0 : zx.getClsl();
            int clje = StringUtil.setNullIsZero(zx.getClje());
            int ahsjsl = StringUtil.setNullIsZero(zx.getAhsjsl());
            int ahsjje = StringUtil.setNullIsZero(zx.getAhsjje());
            int zzjzwxfsl = StringUtil.setNullIsZero(zx.getZzjzwxfsl());
            int zzjzwxfje = StringUtil.setNullIsZero(zx.getZzjzwxfje());
            int wxglmtsl = StringUtil.setNullIsZero(zx.getWxglmtsl());
            int wxglmtje = StringUtil.setNullIsZero(zx.getWxglmtje());
            int hbwhzsl = StringUtil.setNullIsZero(zx.getHbwhzsl());
            int hbwhzcsl = StringUtil.setNullIsZero(zx.getHbwhzcsl());
            int hbwhje = StringUtil.setNullIsZero(zx.getHbwhje());
            int dlccssl = StringUtil.setNullIsZero(zx.getDlccssl());
            int dlccdwsl = StringUtil.setNullIsZero(zx.getDlccdwsl());
            int dlccje = StringUtil.setNullIsZero(zx.getDlccje());
            int qtqzcsl = StringUtil.setNullIsZero(zx.getQtqzcsl());
            int qtqzdsl = StringUtil.setNullIsZero(zx.getQtqzdsl());
            int qtqzje = StringUtil.setNullIsZero(zx.getQtqzje());
            int qtwxgcsl = StringUtil.setNullIsZero(zx.getQtwxgcsl());
            int qtwxgcje = StringUtil.setNullIsZero(zx.getQtwxgcje());
            //计算数据


            //月度
            if (zxtime.getTime() >= starttime.getTime() && zxtime.getTime() < endtime.getTime()) {//限定时间
                //月度的累计 或者季度的本季度
                if (flag == Constants.STATISTIC_MONTH) {
                    //本月
                    if (monthdate.getTime() <= zxtime.getTime()) {
                        sumclsl = clsl;
                        sumclje = clje;
                        sumahsjsl = ahsjsl;
                        sumahsjje = ahsjje;
                        sumzzjzwxfsl = zzjzwxfsl;
                        sumzzjzwxfje = zzjzwxfje;
                        sumwxglmtsl = wxglmtsl;
                        sumwxglmtje = wxglmtje;
                        sumhbwhzsl = hbwhzsl;
                        sumhbwhzcsl = hbwhzcsl;
                        sumhbwhje = hbwhje;
                        sumdlccssl = dlccssl;
                        sumdlccdwsl = dlccdwsl;
                        sumdlccje = dlccje;
                        sumqtqzcsl = qtqzcsl;
                        sumqtqzdsl = qtqzdsl;
                        sumqtqzje = qtqzje;
                        sumqtwxgcsl = qtwxgcsl;
                        sumqtwxgcje = qtwxgcje;
                    }
                    //累计
                    totalclsl += clsl;
                    totalclje += clje;
                    totalahsjsl += ahsjsl;
                    totalahsjje += ahsjje;
                    totalzzjzwxfsl += zzjzwxfsl;
                    totalzzjzwxfje += zzjzwxfje;
                    totalwxglmtsl += wxglmtsl;
                    totalwxglmtje += wxglmtje;
                    totalhbwhzsl = hbwhzsl;
                    totalhbwhzcsl += hbwhzcsl;
                    totalhbwhje += hbwhje;
                    totaldlccssl += dlccssl;
                    totaldlccdwsl += dlccdwsl;
                    totaldlccje += dlccje;
                    totalqtqzcsl += qtqzcsl;
                    totalqtqzdsl += qtqzdsl;
                    totalqtqzje += qtqzje;
                    totalqtwxgcsl += qtwxgcsl;
                    totalqtwxgcje += qtwxgcje;
                }
                if (flag == Constants.STATISTIC_SEASON) {
                    //本季度
                    sumclsl += clsl;
                    sumclje += clje;
                    sumahsjsl += ahsjsl;
                    sumahsjje += ahsjje;
                    sumzzjzwxfsl += zzjzwxfsl;
                    sumzzjzwxfje += zzjzwxfje;
                    sumwxglmtsl += wxglmtsl;
                    sumwxglmtje += wxglmtje;
                    sumhbwhzsl = hbwhzsl;
                    sumhbwhzcsl += hbwhzcsl;
                    sumhbwhje += hbwhje;
                    sumdlccssl += dlccssl;
                    sumdlccdwsl += dlccdwsl;
                    sumdlccje += dlccje;
                    sumqtqzcsl += qtqzcsl;
                    sumqtqzdsl += qtqzdsl;
                    sumqtqzje += qtqzje;
                    sumqtwxgcsl += qtwxgcsl;
                    sumqtwxgcje += qtwxgcje;
                }
            }
            //季度 自年初累计
            if (flag == Constants.STATISTIC_SEASON) {
                totalclsl += clsl;
                totalclje += clje;
                totalahsjsl += ahsjsl;
                totalahsjje += ahsjje;
                totalzzjzwxfsl += zzjzwxfsl;
                totalzzjzwxfje += zzjzwxfje;
                totalwxglmtsl += wxglmtsl;
                totalwxglmtje += wxglmtje;
                totalhbwhzsl = hbwhzsl;
                totalhbwhzcsl += hbwhzcsl;
                totalhbwhje += hbwhje;
                totaldlccssl += dlccssl;
                totaldlccdwsl += dlccdwsl;
                totaldlccje += dlccje;
                totalqtqzcsl += qtqzcsl;
                totalqtqzdsl += qtqzdsl;
                totalqtqzje += qtqzje;
                totalqtwxgcsl += qtwxgcsl;
                totalqtwxgcje += qtwxgcje;
            }
        }
        //处理返回数据
        cl.add(sumclsl == 0.0 ? "" : new BigDecimal(sumclsl).setScale(4, RoundingMode.HALF_UP).toString());
        cl.add(totalclsl == 0.0 ? "" : new BigDecimal(totalclsl).setScale(4, RoundingMode.HALF_UP).toString());
        cl.add(StringUtil.getStringRound(sumclje));
        cl.add(StringUtil.getStringRound(totalclje));
        ahsj.add(StringUtil.getStringIsNull(sumahsjsl));
        ahsj.add(StringUtil.getStringIsNull(totalahsjsl));
        ahsj.add(StringUtil.getStringRound(sumahsjje));
        ahsj.add(StringUtil.getStringRound(totalahsjje));
        zzjzwxf.add(StringUtil.getStringIsNull(sumzzjzwxfsl));
        zzjzwxf.add(StringUtil.getStringIsNull(totalzzjzwxfsl));
        zzjzwxf.add(StringUtil.getStringRound(sumzzjzwxfje));
        zzjzwxf.add(StringUtil.getStringRound(totalzzjzwxfje));
        wxglmt.add(StringUtil.getStringIsNull(sumwxglmtsl));
        wxglmt.add(StringUtil.getStringIsNull(totalwxglmtsl));
        wxglmt.add(StringUtil.getStringRound(sumwxglmtje));
        wxglmt.add(StringUtil.getStringRound(totalwxglmtje));
        hbwh.add(StringUtil.getStringIsNull(sumhbwhzsl) + "/"
                + StringUtil.getStringIsNull(sumhbwhzcsl));
        hbwh.add(StringUtil.getStringIsNull(totalhbwhzsl) + "/"
                + StringUtil.getStringIsNull(totalhbwhzcsl));
        hbwh.add(StringUtil.getStringRound(sumhbwhje));
        hbwh.add(StringUtil.getStringRound(totalhbwhje));
        dlcc.add(StringUtil.getStringIsNull(sumdlccssl) + "/"
                + StringUtil.getStringIsNull(sumdlccdwsl));
        dlcc.add(StringUtil.getStringIsNull(totaldlccssl) + "/"
                + StringUtil.getStringIsNull(totaldlccdwsl));
        dlcc.add(StringUtil.getStringRound(sumdlccje));
        dlcc.add(StringUtil.getStringRound(totaldlccje));
        qtqz.add(StringUtil.getStringIsNull(sumqtqzcsl) + "/"
                + StringUtil.getStringIsNull(sumqtqzdsl));
        qtqz.add(StringUtil.getStringIsNull(totalqtqzcsl) + "/"
                + StringUtil.getStringIsNull(totalqtqzdsl));
        qtqz.add(StringUtil.getStringRound(sumqtqzje));
        qtqz.add(StringUtil.getStringRound(totalqtqzje));
        qtwxgc.add(StringUtil.getStringIsNull(sumqtwxgcsl));
        qtwxgc.add(StringUtil.getStringIsNull(totalqtwxgcsl));
        qtwxgc.add(StringUtil.getStringRound(sumqtwxgcje));
        qtwxgc.add(StringUtil.getStringRound(totalqtwxgcje));
        totalmonth = sumclje + sumahsjje + sumzzjzwxfje + sumwxglmtje
                + sumhbwhje + sumdlccje + sumqtqzje + sumqtwxgcje;
        totalmutimonth = totalclje + totalahsjje + totalzzjzwxfje
                + totalwxglmtje + totalhbwhje + totaldlccje
                + totalqtqzje + totalqtwxgcje;

        BaseResultOK baseResultOK = new BaseResultOK();
        String seltime = "";
        if (flag == Constants.STATISTIC_MONTH) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
            String selstart = sdf.format(starttime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endtime);
            calendar.add(Calendar.MONTH, -1);
            String selend = sdf.format(calendar.getTime());
            String selyear = selstart.substring(0, 4);
            seltime = selstart + "至" + selend;
        }
        if (flag == Constants.STATISTIC_SEASON) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String selstart = sdf.format(starttime);
            String selyear = selstart.substring(0, 4);
            String s = selstart.substring(4);
            int selmonth = Integer.parseInt(s);
            int selseason = 0;
            switch (selmonth) {
                case 1:
                    selseason = 1;
                    break;
                case 4:
                    selseason = 2;
                    break;
                case 7:
                    selseason = 3;
                    break;
                case 10:
                    selseason = 4;
                    break;
                default:
                    break;
            }
            seltime = selyear + "年" + selseason + "季度";
        }
        String outfilepath = "";
        File inputfile = null;
        File outputfile = null;
        try {
            String path = this.getRealPath("/");
            inputfile = new File(path + "/template/全航区其他航道例行养护工作统计表.xlsx");
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

            XSSFSheet sheet = workbook.getSheet("全航区其他航道例行养护工作统计表");
            // 表头
            sheet.getRow(4).getCell(1).setCellValue("单位:" + dpt.getName());
            sheet.getRow(4).getCell(11).setCellValue(seltime);
            // 主体
            int n = 4;
            for (String dval : cl) {
                sheet.getRow(9)
                        .getCell(n)
                        .setCellValue(dval);
                n = n + 2;
            }
            n = 4;
            for (String dval : ahsj) {
                sheet.getRow(11)
                        .getCell(n)
                        .setCellValue(dval);
                n = n + 2;
            }
            n = 4;
            for (String dval : zzjzwxf) {
                sheet.getRow(13)
                        .getCell(n)
                        .setCellValue(dval);
                n = n + 2;
            }
            n = 4;
            for (String dval : wxglmt) {
                sheet.getRow(15)
                        .getCell(n)
                        .setCellValue(dval);
                n = n + 2;
            }
            n = 4;
            for (String dval : hbwh) {
                sheet.getRow(17)
                        .getCell(n)
                        .setCellValue(
                                "/".equals(dval)
                                        ? "" : dval);
                n = n + 2;
            }
            n = 4;
            for (String dval : dlcc) {
                sheet.getRow(19)
                        .getCell(n)
                        .setCellValue(
                                "/".equals(dval)
                                        ? "" : dval);
                n = n + 2;
            }
            n = 4;
            for (String dval : qtqz) {
                sheet.getRow(21)
                        .getCell(n)
                        .setCellValue(
                                "/".equals(dval)
                                        ? "" : dval);
                n = n + 2;
            }
            n = 4;
            for (String dval : qtwxgc) {
                sheet.getRow(23)
                        .getCell(n)
                        .setCellValue(dval);
                n = n + 2;
            }
            sheet.getRow(25)
                    .getCell(8)
                    .setCellValue(
                            totalmonth == 0 ? "" : StringUtil.getStringRound(totalmonth));
            sheet.getRow(25)
                    .getCell(10)
                    .setCellValue(
                            totalmutimonth == 0 ? "" : StringUtil.getStringRound(totalmutimonth));
            FileOutputStream outfile = new FileOutputStream(outputfile);
            workbook.write(outfile);
            outfile.close();
            logService.addLog(ModuleName.TJBB_FD, OpName.EXPORT, "全航区其他航道例行养护工作统计表");
            baseResultOK.addToMap("filename", outputfile.getName());
            baseResultOK.addToMap("filepath", outfilepath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseResultOK;
    }

    public BaseResult queryZxgcTable(Integer gldw, Date starttime, Date endtime,
                                     Integer flag, Integer dptflag) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        //得到部门
        String strgldw = "";
        if (gldw == null) {
            gldw = 12;
        }
        CXtDpt dpt = this.dptDao.queryDptById(gldw);
        StringBuffer sb = new StringBuffer("");
        if (dptflag == 0) {
            strgldw = gldw.toString();
        }
        if (dptflag == 1) {
            List<CXtDpt> dptlist = (List<CXtDpt>) this.dptService.findChildrenNodes_r(dpt);
            if (dptlist != null && dptlist.size() > 0) {
                for (CXtDpt dptentity : dptlist) {
                    sb.append(dptentity.getId() + ",");
                }
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                strgldw = sb.toString() + "," + dpt.getId();
            }
        }


        //处理时间
        String strstarttime = "";
        String strendtime = "";
        Date startseason = null;
        if (starttime != null) {
            strstarttime = DateTimeUtil.getTimeFmt(starttime, "yyyy")
                    + "-01-01 00:00:00";// 第1个月
        }
        if (endtime != null) {
            strendtime = DateTimeUtil.getTimeFmt(endtime,
                    "yyyy-MM-dd HH:mm:ss");
        }
        //查询出所有年数据
        List<CYhYdjdqk> ydjdqks = this.maintenanceDao.queryZxgcTable(strgldw,
                strstarttime, strendtime).getData();
        List<ZxgcForm> returnlist = new ArrayList<ZxgcForm>();
        //map 专项工程和月度进度情况
        Map<Integer, List<CYhYdjdqk>> map = new HashMap<Integer, List<CYhYdjdqk>>();
        if (ydjdqks != null && ydjdqks.size() > 0) {
            for (CYhYdjdqk yd : ydjdqks) {
                Integer keyid = yd.getCYhZxgc().getZxgcid();
                if (map.containsKey(keyid)) {
                    map.get(keyid).add(yd);
                } else {
                    List<CYhYdjdqk> list = new ArrayList<CYhYdjdqk>();
                    list.add(yd);
                    map.put(keyid, list);
                }
            }
        }
        // 根据专项工程id分月度情况集合
        Iterator<Integer> ii = map.keySet().iterator();
        double totaltz = 0;
        double wcje = 0;
        double sumwcje = 0;
        CYhYdjdqk lastyd = new CYhYdjdqk();
        //endtime前一个月为本月
        Calendar lastmonth = Calendar.getInstance();
        lastmonth.setTime(endtime);
        lastmonth.add(Calendar.MONTH, -1);
        Date lmonth = lastmonth.getTime();
        while (ii.hasNext()) {
            Integer zxgcid = (Integer) ii.next();
            List<CYhYdjdqk> yds = map.get(zxgcid);
            CYhZxgc zxgc = this.maintenanceDao.queryZxgcJbxxById(zxgcid);
            for (int j = 0; j < yds.size(); j++) {
                CYhYdjdqk yd = yds.get(j);
                Date ny = yd.getNy();
                if (flag == Constants.STATISTIC_MONTH) {
                    if (ny.getTime() >= lmonth.getTime() && ny.getTime() < endtime.getTime()) {
                        lastyd = yd;
                        wcje = yd.getBywcje();
                    }
                    if (ny.getTime() >= starttime.getTime() && ny.getTime() < endtime.getTime()) {
                        sumwcje += yd.getBywcje();
                    }
                }
                if (flag == Constants.STATISTIC_SEASON) {
                    if (ny.getTime() >= starttime.getTime() && ny.getTime() < endtime.getTime()) {
                        wcje += yd.getBywcje();
                    }
                    if (ny.getTime() == lmonth.getTime()) {
                        lastyd = yd;
                    }
                    sumwcje += yd.getBywcje();
                }
            }

            ZxgcForm zf = new ZxgcForm();// 封装返回值
            zf.setZxgcid(zxgcid);
            zf.setName(zxgc.getGcmc());
            double tz = zxgc.getTz();
            totaltz += tz;
            zf.setInvestment(new BigDecimal(tz).setScale(2, RoundingMode.HALF_UP).doubleValue());
            zf.setProgress(StringUtil.getIsNull(lastyd.getXmjdqk()).toString());
            zf.setAmount(new BigDecimal(wcje).setScale(2, RoundingMode.HALF_UP).doubleValue());
            zf.setTotalamount(new BigDecimal(sumwcje).setScale(2, RoundingMode.HALF_UP).doubleValue());
            zf.setRemark(StringUtil.getIsNull(lastyd.getBz()).toString());
            returnlist.add(zf);
            lastyd = new CYhYdjdqk();
            ;
            wcje = 0;
            sumwcje = 0;
        }

        BaseResultOK baseResultOK = new BaseResultOK(returnlist);// 处理返回值
        if (flag == Constants.STATISTIC_MONTH) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
            String selstart = sdf.format(starttime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endtime);
            calendar.add(Calendar.MONTH, -1);
            String selend = sdf.format(calendar.getTime());
            String selyear = selstart.substring(0, 4);
            baseResultOK.addToMap("selstart", selstart);
            baseResultOK.addToMap("selend", selend);
            baseResultOK.addToMap("selyear", selyear);
        }
        if (flag == Constants.STATISTIC_SEASON) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String selstart = sdf.format(starttime);
            String selyear = selstart.substring(0, 4);
            String s = selstart.substring(4);
            int selmonth = Integer.parseInt(s);
            int selseason = 0;
            switch (selmonth) {
                case 1:
                    selseason = 1;
                    break;
                case 4:
                    selseason = 2;
                    break;
                case 7:
                    selseason = 3;
                    break;
                case 10:
                    selseason = 4;
                    break;
                default:
                    break;
            }
            baseResultOK.addToMap("selyear", selyear);
            baseResultOK.addToMap("selseason", selseason);
        }
        baseResultOK.addToMap("tz", new BigDecimal(totaltz).setScale(2, RoundingMode.HALF_UP).doubleValue());
        baseResultOK.addToMap("dpt", dpt.getName());
        return baseResultOK;
    }

    public BaseResult exportZxgcTable(Integer gldw, Date starttime,
                                      Date endtime, Integer flag, Integer dptflag) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        //得到部门
        String strgldw = "";
        if (gldw == null) {
            gldw = 12;
        }
        CXtDpt dpt = this.dptDao.queryDptById(gldw);
        StringBuffer sb = new StringBuffer("");
        if (dptflag == 0) {
            strgldw = gldw.toString();
        }
        if (dptflag == 1) {
            List<CXtDpt> dptlist = (List<CXtDpt>) this.dptService.findChildrenNodes_r(dpt);
            if (dptlist != null && dptlist.size() > 0) {
                for (CXtDpt dptentity : dptlist) {
                    sb.append(dptentity.getId() + ",");
                }
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                strgldw = sb.toString() + "," + dpt.getId();
            }
        }

        //处理时间
        String strstarttime = "";
        String strendtime = "";
        Date startseason = null;
        if (starttime != null) {
            strstarttime = DateTimeUtil.getTimeFmt(starttime, "yyyy")
                    + "-01-01 00:00:00";// 第1个月
        }
        if (endtime != null) {
            strendtime = DateTimeUtil.getTimeFmt(endtime,
                    "yyyy-MM-dd HH:mm:ss");
        }
        //查询出所有年数据
        List<CYhYdjdqk> ydjdqks = this.maintenanceDao.queryZxgcTable(strgldw,
                strstarttime, strendtime).getData();
        List<ZxgcForm> returnlist = new ArrayList<ZxgcForm>();
        //map 专项工程和月度进度情况
        Map<Integer, List<CYhYdjdqk>> map = new HashMap<Integer, List<CYhYdjdqk>>();
        if (ydjdqks != null && ydjdqks.size() > 0) {
            for (CYhYdjdqk yd : ydjdqks) {
                Integer keyid = yd.getCYhZxgc().getZxgcid();
                if (map.containsKey(keyid)) {
                    map.get(keyid).add(yd);
                } else {
                    List<CYhYdjdqk> list = new ArrayList<CYhYdjdqk>();
                    list.add(yd);
                    map.put(keyid, list);
                }
            }
        }
        // 根据专项工程id分月度情况集合
        Iterator<Integer> ii = map.keySet().iterator();
        double totaltz = 0;
        double wcje = 0;
        double sumwcje = 0;
        CYhYdjdqk lastyd = new CYhYdjdqk();
        //endtime前一个月为本月
        Calendar lastmonth = Calendar.getInstance();
        lastmonth.setTime(endtime);
        lastmonth.add(Calendar.MONTH, -1);
        Date lmonth = lastmonth.getTime();
        while (ii.hasNext()) {
            Integer zxgcid = (Integer) ii.next();
            List<CYhYdjdqk> yds = map.get(zxgcid);
            CYhZxgc zxgc = this.maintenanceDao.queryZxgcJbxxById(zxgcid);
            for (int j = 0; j < yds.size(); j++) {
                CYhYdjdqk yd = yds.get(j);
                Date ny = yd.getNy();
                if (flag == Constants.STATISTIC_MONTH) {
                    if (ny.getTime() >= lmonth.getTime() && ny.getTime() < endtime.getTime()) {
                        lastyd = yd;
                        wcje = yd.getBywcje();
                    }
                    if (ny.getTime() >= starttime.getTime() && ny.getTime() < endtime.getTime()) {
                        sumwcje += yd.getBywcje();
                    }
                }
                if (flag == Constants.STATISTIC_SEASON) {
                    if (ny.getTime() >= starttime.getTime() && ny.getTime() < endtime.getTime()) {
                        wcje += yd.getBywcje();
                    }
                    if (ny.getTime() == lmonth.getTime()) {
                        lastyd = yd;
                    }
                    sumwcje += yd.getBywcje();
                }
            }

            ZxgcForm zf = new ZxgcForm();// 封装返回值
            zf.setZxgcid(zxgcid);
            zf.setName(zxgc.getGcmc());
            double tz = zxgc.getTz();
            totaltz += tz;
            zf.setInvestment(new BigDecimal(tz).setScale(2, RoundingMode.HALF_UP).doubleValue());
            zf.setProgress(StringUtil.getIsNull(lastyd.getXmjdqk()).toString());
            zf.setAmount(new BigDecimal(wcje).setScale(2, RoundingMode.HALF_UP).doubleValue());
            zf.setTotalamount(new BigDecimal(sumwcje).setScale(2, RoundingMode.HALF_UP).doubleValue());
            zf.setRemark(StringUtil.getIsNull(lastyd.getBz()).toString());
            returnlist.add(zf);
            lastyd = new CYhYdjdqk();
            ;
            wcje = 0;
            sumwcje = 0;
        }

        BaseResultOK baseResultOK = new BaseResultOK();// 处理返回值
        String seltime = "";
        if (flag == Constants.STATISTIC_MONTH) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
            String selstart = sdf.format(starttime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endtime);
            calendar.add(Calendar.MONTH, -1);
            String selend = sdf.format(calendar.getTime());
            String selyear = selstart.substring(0, 4);
            seltime = selstart + "至" + selend;
        }
        if (flag == Constants.STATISTIC_SEASON) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String selstart = sdf.format(starttime);
            String selyear = selstart.substring(0, 4);
            String s = selstart.substring(4);
            int selmonth = Integer.parseInt(s);
            int selseason = 0;
            switch (selmonth) {
                case 1:
                    selseason = 1;
                    break;
                case 4:
                    selseason = 2;
                    break;
                case 7:
                    selseason = 3;
                    break;
                case 10:
                    selseason = 4;
                    break;
                default:
                    break;
            }
            seltime = selyear + "年" + selseason + "季度";
        }
        String outfilepath = "";
        File inputfile = null;
        File outputfile = null;
        try {
            String path = this.getRealPath("/");
            inputfile = new File(path + "/template/专项养护工程完成情况工作统计表.xlsx");
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

            XSSFSheet sheet = workbook.getSheet("专项养护工程完成情况工作统计表");
            // 表头
            sheet.getRow(4).getCell(1).setCellValue("单位:" + dpt.getName());
            sheet.getRow(4).getCell(11).setCellValue(seltime);
            // 主体

            int rownum = 8;
            int rowtotal = returnlist.size() + rownum + 1;
            for (int i = rownum; i < rowtotal; i++) {
                XSSFRow row = sheet.createRow(i);
                for (int j = 1; j < 14; j++) {
                    row.createCell(j).setCellStyle(cellStyle);
                }
                sheet.addMergedRegion(new CellRangeAddress(i, i, 1, 2));
                sheet.addMergedRegion(new CellRangeAddress(i, i, 3, 4));
                sheet.addMergedRegion(new CellRangeAddress(i, i, 5, 7));
                sheet.addMergedRegion(new CellRangeAddress(i, i, 8, 9));
                sheet.addMergedRegion(new CellRangeAddress(i, i, 10, 11));
                sheet.addMergedRegion(new CellRangeAddress(i, i, 12, 13));
            }
            for (ZxgcForm zxgc : returnlist) {
                XSSFRow row = sheet.getRow(rownum);
                row.getCell(1).setCellValue(zxgc.getName());
                row.getCell(3).setCellValue(zxgc.getInvestment() == 0 ? "" : new BigDecimal(zxgc.getInvestment()).setScale(2, RoundingMode.HALF_UP).toString());
                row.getCell(5).setCellValue(zxgc.getProgress());
                row.getCell(8).setCellValue(zxgc.getAmount() == 0 ? "" : new BigDecimal(zxgc.getAmount()).setScale(2, RoundingMode.HALF_UP).toString());
                row.getCell(10).setCellValue(zxgc.getTotalamount() == 0 ? "" : new BigDecimal(zxgc.getTotalamount()).setScale(2, RoundingMode.HALF_UP).toString());
                row.getCell(12).setCellValue(zxgc.getRemark());
                rownum++;
            }
            XSSFRow row = sheet.getRow(rownum);
            row.getCell(1).setCellValue("总计");
            row.getCell(3).setCellValue(totaltz == 0.0 ? "" : new BigDecimal(totaltz).setScale(2, RoundingMode.HALF_UP).toString());
            int remarkline = rowtotal + 1 + 3;
            for (int i = rowtotal; i < remarkline; i++) {
                XSSFRow remarkrow = sheet.createRow(i);
                for (int j = 0; j < 15; j++) {
                    remarkrow.createCell(j);
                }
                sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 14));

            }
            sheet.getRow(rowtotal + 1).getCell(0)
                    .setCellValue("注:1.凡列入年度预算的专项养护工程，均需填写本表，从前期工作开始填写。");
            sheet.getRow(rowtotal + 2)
                    .getCell(0)
                    .setCellValue(
                            "   2.工程进度情况应按主要工程量(如护岸砌筑、陆上土方开挖、水下土方疏浚、管理码头桩基、上部结构等)详细说明，工程完工后其主要工程量均应填写完整。");
            sheet.getRow(rowtotal + 3)
                    .getCell(0)
                    .setCellValue(
                            "   3.每年底各市需填写所有专项工程合计完成提高航道等级x公里，新建修复护岸xx米，陆上土方x万方，水下土方xx万方，增设标志标牌x座，新建管理码头x座。");
            FileOutputStream outfile = new FileOutputStream(outputfile);
            workbook.write(outfile);
            outfile.close();
            logService.addLog(ModuleName.TJBB_FD, OpName.EXPORT, "专项养护工程完成情况工作统计表");
            baseResultOK.addToMap("filename", outputfile.getName());
            baseResultOK.addToMap("filepath", outfilepath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseResultOK;
    }


    public BaseResult queryJsy61Table() throws Exception {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        List<List<TableCell>> data = queryJSYTableFromDB(61);
        List<Object[]> trdata = new ArrayList<Object[]>();
        if (data == null) {
            data = new ExcelRead().readExcel(getContextPath() + "/template/交水运61分项表.xlsx", 39, 24);
            saveJSYTableToDB(61, data);
            data = queryJSYTableFromDB(61);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
        String stryear = sdf.format(new Date());
        data.get(0).get(3).setCellvalue("（一）航道维护里程统计分项表\n\n " + stryear);
        data.get(0).get(10).setCellvalue("" +
                "表    号：交水运61表\n" +
                "制表机关：交通运输部\n" +
                "备案机关：国家统计局\n" +
                "备案文号：国统办函[2011]167号\n" +
                "有效期至：" + stryear + "12月");

        Map<String, Object> session = ActionContext.getContext().getSession();
        CZdXzqhdm dptshixzqh = (CZdXzqhdm) session.get("dptshixzqh");
        CXtDpt dept = (CXtDpt) session.get("dpt");
        CXtDpt citydept = (CXtDpt) session.get("shiju");
        String deptname = "";
        if (citydept != null) {
            deptname = citydept.getName();
        } else {
            deptname = dept.getName();
        }
        data.get(0).get(0).setCellvalue("填报单位：" + deptname);
        int startrow = 6;
        int startcol = 4;
        int endcol = 23;
        if (dptshixzqh != null) {
            List<CZdXzqhdm> xzqhs = (List<CZdXzqhdm>) this.xzqhdmDaoImpl._findChildrenNodes(dptshixzqh).getData();
            int xzqhsize = xzqhs.size();
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < xzqhsize; i++) {
                if (i != xzqhsize - 1) {
                    sb.append(xzqhs.get(i).getId() + ",");
                } else {
                    sb.append(xzqhs.get(i).getId() + ",");
                    sb.append(dptshixzqh.getId());
                }
            }
            List<CHdHdaojcxx> hdids = this.hangDaoDao.queryHdidInXzqh(sb.toString());
            int hdidsize = hdids.size();
            StringBuffer hdidsb = new StringBuffer("");
            for (int i = 0; i < hdidsize; i++) {
                int hdid = hdids.get(i).getId();
                Object[] objarr = (Object[]) this.statisticsDao.queryJsy61TableByHdid(hdid).get(0);
                trdata.add(objarr);
                if (i != hdidsize - 1) {
                    hdidsb.append(hdid + ",");
                } else {
                    hdidsb.append(hdid);
                }
            }
            Object[] otherlist = (Object[]) this.statisticsDao.queryJsy61TableByNotHdid(hdidsb.toString(), sb.toString()).get(0);
            trdata.add(otherlist);

            int trsize = trdata.size();
            if (dptshixzqh.getId() == 2) {
                for (int i = 0; i < trsize; i++) {
                    Object[] tddata = trdata.get(i);
                    StringUtil.setListNull(tddata);
                    int rowindex = i + 7;
                    clearTrValue(data, rowindex, startcol, endcol);
                    setJsy61TrValue(tddata, data.get(rowindex));
                }
            } else {
                for (int i = 0; i < trsize; i++) {
                    Object[] tddata = trdata.get(i);
                    StringUtil.setListNull(tddata);
                    int rowindex = i + 7;
                    clearTrValue(data, rowindex, startcol, endcol);
                    data.get(rowindex).get(0).setCellvalue(String.valueOf(i + 1));
                    data.get(rowindex).get(1).setCellvalue(deptname);
                    if (i != trsize - 1) {
                        data.get(rowindex).get(2).setCellvalue(hdids.get(i).getHdmc());
                    } else {
                        data.get(rowindex).get(2).setCellvalue("其他航道");
                    }
                    data.get(rowindex).get(3).setCellvalue("");
                    setJsy61TrValue(tddata, data.get(rowindex));
                }
            }
            clearZero(data, startrow, startrow + trsize, startcol, endcol);
        } else {
            for (int i = 0; i < 8; i++) {
                int rowindex = i + 7;
                clearTrValue(data, rowindex, 0, endcol);
            }
        }
        BaseResultOK result = new BaseResultOK(data);
        return result;
    }


    public BaseResult queryJsy62Table() throws Exception {
        List<List<TableCell>> data = queryJSYTableFromDB(62);
        List<Object[]> trdata = new ArrayList<Object[]>();
        if (data == null) {
            data = new ExcelRead().readExcel(getContextPath() + "/template/交水运62分项表.xlsx", 48, 23);
            saveJSYTableToDB(62, data);
            data = queryJSYTableFromDB(62);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
        String stryear = sdf.format(new Date());
        data.get(0).get(2).setCellvalue("（二）主要河流（海区）航道维护尺度统计分项表\n\n " + stryear);
        data.get(0).get(5).setCellvalue("" +
                "表    号：交水运62表\n" +
                "制表机关：交通运输部\n" +
                "备案机关：国家统计局\n" +
                "备案文号：国统办函[2011]167号\n" +
                "有效期至：" + stryear + "12月");

        Map<String, Object> session = ActionContext.getContext().getSession();
        CZdXzqhdm dptshixzqh = (CZdXzqhdm) session.get("dptshixzqh");
        CXtDpt dept = (CXtDpt) session.get("dpt");
        CXtDpt citydept = (CXtDpt) session.get("shiju");
        String deptname = "";
        if (citydept != null) {
            deptname = citydept.getName();
        } else {
            deptname = dept.getName();
        }
        data.get(0).get(0).setCellvalue("填报单位：" + deptname);
        if (dptshixzqh != null) {
            if (dptshixzqh.getId() == 2) {
                String jhyh = "5,6,7,8";
                Object[] jhyharr = (Object[]) this.statisticsDao.queryJsy62TableById(jhyh).get(0);
                String chsx1 = "4";
                Object[] chsx1arr = (Object[]) this.statisticsDao.queryWhhzById(chsx1).get(0);
                String chsx2 = "3";
                Object[] chsx2arr = (Object[]) this.statisticsDao.queryWhhzById(chsx2).get(0);
                String chsx3 = "10,11";
                Object[] chsx3arr = (Object[]) this.statisticsDao.queryJsy62TableById(chsx3).get(0);
                String chsx4 = "12,13,14";
                Object[] chsx4arr = (Object[]) this.statisticsDao.queryJsy62TableById(chsx4).get(0);
                String chsx5 = "15,16,17";
                Object[] chsx5arr = (Object[]) this.statisticsDao.queryJsy62TableById(chsx5).get(0);
                String hjsx = "18,19,20,21";
                Object[] hjsxarr = (Object[]) this.statisticsDao.queryJsy62TableById(hjsx).get(0);
                String hhxx = "39,40,41,42,43,44,45,46,47,48,49";
                Object[] hhxxarr = (Object[]) this.statisticsDao.queryJsy62TableById(hhxx).get(0);
                String dzx = "27,28,29";
                Object[] dzxarr = (Object[]) this.statisticsDao.queryJsy62TableById(dzx).get(0);
                String dtx1 = "30";
                Object[] dtx1arr = (Object[]) this.statisticsDao.queryJsy62TableById(dtx1).get(0);
                String dtx2 = "31,32,33,34,35,36,37,38";
                Object[] dtx2arr = (Object[]) this.statisticsDao.queryJsy62TableById(dtx2).get(0);
                String mhx1 = "22,23";
                Object[] mhx1arr = (Object[]) this.statisticsDao.queryJsy62TableById(mhx1).get(0);
                String mhx2 = "25,26";
                Object[] mhx2arr = (Object[]) this.statisticsDao.queryJsy62TableById(mhx2).get(0);
                trdata.add(jhyharr);
                trdata.add(chsx1arr);
                trdata.add(chsx2arr);
                trdata.add(chsx3arr);
                trdata.add(chsx4arr);
                trdata.add(chsx5arr);
                trdata.add(hjsxarr);
                trdata.add(hhxxarr);
                trdata.add(dzxarr);
                trdata.add(dtx1arr);
                trdata.add(dtx2arr);
                trdata.add(mhx1arr);
                trdata.add(mhx2arr);

                for (int i = 0; i < trdata.size(); i++) {
                    Object[] tddata = trdata.get(i);
                    StringUtil.setListNull(tddata);
                    int rowindex = i + 5;
                    clearTrValue(data, rowindex, 6, 21);
                    setJsy62TrValue(tddata, data.get(rowindex));
                }
                clearZero(data, 4, 17, 6, 21);
            } else {
                for (int i = 0; i < 13; i++) {
                    int rowindex = i + 5;
                    clearTrValue(data, rowindex, 0, 22);
                }
            }
        } else {
            for (int i = 0; i < 13; i++) {
                int rowindex = i + 5;
                clearTrValue(data, rowindex, 0, 22);
            }
        }
        BaseResultOK result = new BaseResultOK(data);
        return result;
    }

    public BaseResult queryJsy63Table() throws Exception {
        List<List<TableCell>> data = queryJSYTableFromDB(63);
        List<Object[]> trdata = new ArrayList<Object[]>();
        if (data == null) {
            data = new ExcelRead().readExcel(getContextPath() + "/template/交水运63分项表.xlsx", 49, 30);
            saveJSYTableToDB(63, data);
            data = queryJSYTableFromDB(63);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
        String stryear = sdf.format(new Date());
        data.get(0).get(3).setCellvalue("（三）航标维护统计分项表\n\n " + stryear);
        data.get(0).get(8).setCellvalue("" +
                "表    号：交水运63表\n" +
                "制表机关：交通运输部\n" +
                "备案机关：国家统计局\n" +
                "备案文号：国统办函[2011]167号\n" +
                "有效期至：" + stryear + "12月");
        Map<String, Object> session = ActionContext.getContext().getSession();
        CZdXzqhdm dptshixzqh = (CZdXzqhdm) session.get("dptshixzqh");
        CXtDpt dept = (CXtDpt) session.get("dpt");
        CXtDpt citydept = (CXtDpt) session.get("shiju");
        String deptname = "";
        if (citydept != null) {
            deptname = citydept.getName();
        } else {
            deptname = dept.getName();
        }
        data.get(0).get(0).setCellvalue("填报单位：" + deptname);
        int startrow = 7;
        int startcol = 4;
        int endcol = 29;
        if (dptshixzqh != null) {
            List<CZdXzqhdm> xzqhs = (List<CZdXzqhdm>) this.xzqhdmDaoImpl._findChildrenNodes(dptshixzqh).getData();
            int xzqhsize = xzqhs.size();
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < xzqhsize; i++) {
                if (i != xzqhsize - 1) {
                    sb.append(xzqhs.get(i).getId() + ",");
                } else {
                    sb.append(xzqhs.get(i).getId() + ",");
                    sb.append(dptshixzqh.getId());
                }
            }
            List<CHdHdaojcxx> hdids = this.hangDaoDao.queryHdidInXzqh(sb.toString());
            int hdidsize = hdids.size();
            StringBuffer hdidsb = new StringBuffer("");
            for (int i = 0; i < hdidsize; i++) {
                int hdid = hdids.get(i).getId();
                Object[] objarr = (Object[]) this.statisticsDao.queryJsy63TableById(hdid).get(0);
                trdata.add(objarr);
                if (i != hdidsize - 1) {
                    hdidsb.append(hdid + ",");
                } else {
                    hdidsb.append(hdid);
                }
            }
            Object[] otherlist = (Object[]) this.statisticsDao.queryJsy63TableByNotHdid(hdidsb.toString(), sb.toString()).get(0);
            trdata.add(otherlist);
            int trsize = trdata.size();
            if (dptshixzqh.getId() == 2) {
                for (int i = 0; i < trsize; i++) {
                    Object[] tddata = trdata.get(i);
                    StringUtil.setListNull(tddata);
                    int rowindex = i + 8;
                    clearTrValue(data, rowindex, startcol, endcol);
                    setJsy63TrValue(tddata, data.get(rowindex));
                }
            } else {
                for (int i = 0; i < trsize; i++) {
                    Object[] tddata = trdata.get(i);
                    StringUtil.setListNull(tddata);
                    int rowindex = i + 8;
                    clearTrValue(data, rowindex, startcol, endcol);
                    data.get(rowindex).get(0).setCellvalue(String.valueOf(i + 1));
                    data.get(rowindex).get(1).setCellvalue(deptname);
                    if (i != trsize - 1) {
                        data.get(rowindex).get(2).setCellvalue(hdids.get(i).getHdmc());
                    } else {
                        data.get(rowindex).get(2).setCellvalue("其他航道");
                    }
                    data.get(rowindex).get(3).setCellvalue("");
                    setJsy63TrValue(tddata, data.get(rowindex));
                }
            }
            clearZero(data, startrow, startrow + trsize, startcol, endcol);
        } else {
            for (int i = 0; i < 8; i++) {
                int rowindex = i + 8;
                clearTrValue(data, rowindex, 0, endcol);
            }
        }
        BaseResultOK result = new BaseResultOK(data);
        return result;
    }

    public BaseResult queryJsy65Table() throws Exception {
        List<List<TableCell>> data = queryJSYTableFromDB(65);
        List list = new ArrayList<>();
        if (data == null) {
            data = new ExcelRead().readExcel(getContextPath() + "/template/交水运65分项表.xlsx", 39, 30);
            saveJSYTableToDB(65, data);
            data = queryJSYTableFromDB(65);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
        String stryear = sdf.format(new Date());
        data.get(0).get(1).setCellvalue("（五）主要过船建筑物分布与技术状况统计分项表\n" +
                "\n" +
                "\n" +
                "  " + stryear);
        data.get(0).get(4).setCellvalue("" +
                "表    号：交水运65表\n" +
                "制表机关：交通运输部\n" +
                "备案机关：国家统计局\n" +
                "备案文号：国统办函[2011]167号\n");
        Map<String, Object> session = ActionContext.getContext().getSession();
        CZdXzqhdm dptshixzqh = (CZdXzqhdm) session.get("dptshixzqh");
        CXtDpt dept = (CXtDpt) session.get("dpt");
        CXtDpt citydept = (CXtDpt) session.get("shiju");
        String deptname = "";
        data.get(0).get(0).setCellvalue("填报单位：" + deptname);
        int startrow = 5;
        int startcol = 6;
        int endcol = 29;
        if (citydept != null) {
            deptname = citydept.getName();
        } else {
            deptname = dept.getName();
        }
        data.get(0).get(0).setCellvalue("填报单位：" + deptname);

        int len = 0;
        if (dptshixzqh != null) {
            List<CZdXzqhdm> xzqhs = (List<CZdXzqhdm>) this.xzqhdmDaoImpl._findChildrenNodes(dptshixzqh).getData();
            int xzqhsize = xzqhs.size();
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < xzqhsize; i++) {
                if (i != xzqhsize - 1) {
                    sb.append(xzqhs.get(i).getId() + ",");
                } else {
                    sb.append(xzqhs.get(i).getId() + ",");
                    sb.append(dptshixzqh.getId());
                }
            }
            list = this.appurtenanceDao.querySnByThlx(sb.toString());
            len = list.size();
            if (dptshixzqh.getId() == 2) {
                for (int i = 0; i < len; i++) {
                    Object[] arrobj = (Object[]) list.get(i);
                    CHdSn pojo = (CHdSn) arrobj[0];
                    int rowindex = i + 6;
                    clearTrValue(data, rowindex, startcol, endcol);
                    List<TableCell> tc = data.get(rowindex);
                    tc.get(18).setCellvalue(StringUtil.getIsNull(pojo.getJg()).toString());
                    tc.get(19).setCellvalue(StringUtil.getIsNull(pojo.getZmkd()).toString());
                    tc.get(20).setCellvalue(StringUtil.getIsNull(pojo.getMkss()).toString());
                }
            } else {
                for (int i = 0; i < len; i++) {
                    Object[] arrobj = (Object[]) list.get(i);
                    CHdSn pojo = (CHdSn) arrobj[0];
                    CHdHdaojcxx hangdao = (CHdHdaojcxx) arrobj[1];
                    int rowindex = i + 6;
                    clearTrValue(data, rowindex, startcol, endcol);
                    List<TableCell> tc = data.get(rowindex);
                    tc.get(0).setCellvalue(String.valueOf(i + 1));
                    tc.get(1).setCellvalue(deptname);
                    tc.get(2).setCellvalue(pojo.getMc());
                    tc.get(3).setCellvalue(hangdao.getHdmc());
                    tc.get(4).setCellvalue("");
                    tc.get(5).setCellvalue("");
                    tc.get(18).setCellvalue(StringUtil.getIsNull(pojo.getJg()).toString());
                    tc.get(19).setCellvalue(StringUtil.getIsNull(pojo.getZmkd()).toString());
                    tc.get(20).setCellvalue(StringUtil.getIsNull(pojo.getMkss()).toString());
                }
            }
            clearZero(data, startrow, startrow + len, startcol, endcol);
        } else {
            for (int i = 0; i < 2; i++) {
                int rowindex = i + 6;
                clearTrValue(data, rowindex, 0, endcol);
            }
        }
        BaseResultOK result = new BaseResultOK(data);
        return result;
    }

    public BaseResult queryJsy671Table() throws Exception {
        List<List<TableCell>> data = queryJSYTableFromDB(671);
        List<Object[]> trdata = new ArrayList<Object[]>();
        if (data == null) {
            data = new ExcelRead().readExcel(getContextPath() + "/template/交水运67-1分项表.xlsx", 33, 16);
            saveJSYTableToDB(671, data);
            data = queryJSYTableFromDB(671);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
        String stryear = sdf.format(new Date());
        data.get(0).get(1).setCellvalue("（八）航道管理情况统计分项表（拦、过、临河建筑物） \n" +
                "\n" +
                "\n" +
                " " +
                stryear);
        data.get(0).get(3).setCellvalue("" +
                "表    号：交水运67-1表\n" +
                "制表机关：交通运输部\n" +
                "备案机关：国家统计局\n" +
                "备案文号：国统办函[2011]167号\n" + "有效期至：" + stryear + "12月");

        Map<String, Object> session = ActionContext.getContext().getSession();
        CZdXzqhdm dptshixzqh = (CZdXzqhdm) session.get("dptshixzqh");
        CXtDpt dept = (CXtDpt) session.get("dpt");
        CXtDpt citydept = (CXtDpt) session.get("shiju");
        String deptname = "";
        if (citydept != null) {
            deptname = citydept.getName();
        } else {
            deptname = dept.getName();
        }
        data.get(0).get(0).setCellvalue("填报单位：" + deptname);
        if (dptshixzqh != null) {
            if (dptshixzqh.getId() == 2) {
                Integer strjhyh = 1;
                List<BigInteger> jhyh = this.statisticsDao.queryJsy671TableByHdid(strjhyh);
                Integer strchsx = 2;
                List<BigInteger> chsx = this.statisticsDao.queryJsy671TableByHdid(strchsx);
                Integer strhjsx = 3;
                List<BigInteger> hjsx = this.statisticsDao.queryJsy671TableByHdid(strhjsx);
                Integer strhhxx = 4;
                List<BigInteger> hhxx = this.statisticsDao.queryJsy671TableByHdid(strhhxx);
                Integer strdzx = 5;
                List<BigInteger> dzx = this.statisticsDao.queryJsy671TableByHdid(strdzx);
                String strother = "1,2,3,4,5";
                List<CZdXzqhdm> xzqhs = (List<CZdXzqhdm>) this.xzqhdmDaoImpl._findChildrenNodes(dptshixzqh).getData();

                StringBuffer sb = new StringBuffer("");
                int xzqhsize = xzqhs.size();
                for (int i = 0; i < xzqhsize; i++) {
                    if (i != xzqhsize - 1) {
                        sb.append(xzqhs.get(i).getId() + ",");
                    } else {
                        sb.append(xzqhs.get(i).getId() + ",");
                        sb.append(dptshixzqh.getId());
                    }
                }
                List<BigInteger> other = this.statisticsDao.queryJsy671TableByNotHdid(strother, sb.toString());

                trdata.add(getArrObjFromList(jhyh));
                trdata.add(getArrObjFromList(chsx));
                trdata.add(getArrObjFromList(hjsx));
                trdata.add(getArrObjFromList(hhxx));
                trdata.add(getArrObjFromList(dzx));
                trdata.add(getArrObjFromList(other));

                for (int i = 0; i < trdata.size(); i++) {
                    Object[] tddata = trdata.get(i);
                    StringUtil.setListNull(tddata);
                    int rowindex = i + 5;
                    clearTrValue(data, rowindex, 4, 15);
                    setJsy671TrValue(tddata, data.get(rowindex));
                }
                clearZero(data, 4, 10, 4, 15);
            } else {
                for (int i = 0; i < 6; i++) {
                    int rowindex = i + 5;
                    clearTrValue(data, rowindex, 0, 15);
                }
            }
        } else {
            for (int i = 0; i < 6; i++) {
                int rowindex = i + 5;
                clearTrValue(data, rowindex, 0, 15);
            }
        }
        BaseResultOK result = new BaseResultOK(data);
        return result;
    }

    public BaseResult exportJsy61Table() throws Exception {
        BaseResult result = queryJsy61Table();
        return exportJsyTable((List<List<TableCell>>) result.getObj(), "交水运61分项表.xlsx");
    }


    public BaseResult exportJsy62Table() throws Exception {
        BaseResult result = queryJsy62Table();
        return exportJsyTable((List<List<TableCell>>) result.getObj(), "交水运62分项表.xlsx");
    }


    public BaseResult exportJsy63Table() throws Exception {
        BaseResult result = queryJsy63Table();
        return exportJsyTable((List<List<TableCell>>) result.getObj(), "交水运63分项表.xlsx");
    }

    public BaseResult exportJsy65Table() throws Exception {
        BaseResult result = queryJsy65Table();
        return exportJsyTable((List<List<TableCell>>) result.getObj(), "交水运65分项表.xlsx");
    }


    public BaseResult exportJsy671Table() throws Exception {
        BaseResult result = queryJsy671Table();
        return exportJsyTable((List<List<TableCell>>) result.getObj(), "交水运67-1分项表.xlsx");
    }

    private BaseResult exportJsyTable(List<List<TableCell>> data, String templateName) throws Exception {
        String templatedir = getContextPath() + "/template/";
        String baobiaodir = getContextPath() + "/baobiao/";
        // 拷贝模板
        String baobiaofilename = null;

        if (data == null || data.size() == 0 || templateName == null || templateName.equals("")) {
            return new BaseResultFailed("无法生成报表，参数不正确");
        }
        //拷贝模板到临时报表下载目录
        baobiaofilename = FileUtils.writeToFile2(new File(templatedir
                + "/" + templateName), baobiaodir + "/" + templateName);
        // 初始化poi
        File outputFile = new File(baobiaodir + "/" + baobiaofilename);
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                outputFile));
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int row = 0; row < data.size(); row++) {
            for (int col = 0; col < data.get(row).size(); col++) {
                TableCell cell = data.get(row).get(col);
                WriteCell(sheet, cell.getRow(), cell.getCol(), cell.getCellvalue(), null);
            }
        }
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();

        BaseResult result = new BaseResultOK();
        result.addToMap("filename", baobiaofilename);
        result.addToMap("filepath", baobiaodir + "/" + baobiaofilename);
        return result;
    }

    private List<KeyValueEntry<String, List<String>>> queryLcTableData(Integer xzqhid) {
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
        //获取航道等级字典列表
        List<CZdAppattribute> hddjs = this.dicDao.queryDicAttr("hddj").getData();

        //查找这些行政区划对应的航道里程统计信息
        List<KeyValueEntry<String, List<String>>> data = new ArrayList<>();
        //存入第一行表头
        KeyValueEntry<String, List<String>> titlekv = new KeyValueEntry<String, List<String>>("航道等级");
        List<String> titlelist = new ArrayList<>();
        for (CZdAppattribute hddj : hddjs)
            titlelist.add(hddj.getAttrdesc());
        titlelist.add("小计");
        titlekv.setValue(titlelist);
        data.add(titlekv);

        //初始化总计
        KeyValueEntry<String, List<String>> zjkv = new KeyValueEntry<>("总计");
        List<String> zjlist = new ArrayList<>();
        for (int i = 0; i < hddjs.size(); i++)
            zjlist.add("");
        zjkv.setValue(zjlist);

        //存入每个行政区划的数据数据
        for (CZdXzqhdm xzqh : xzqhs) {
            //获取数据
            List<Double> sj = this.queryLcTableXZQH(xzqh, hddjs);
            if (sj == null || sj.size() == 0)
                continue;
            KeyValueEntry<String, List<String>> sjkv = new KeyValueEntry<>(xzqh.getName());
            List<String> sjlist = new ArrayList<>();
            //小计
            Double xj = 0.0;
            for (int i = 0; i < sj.size(); i++) {
                Double sjno = sj.get(i);
                if (sjno != null) {
                    double totallc = new BigDecimal(sjno).setScale(2,
                            BigDecimal.ROUND_HALF_UP).doubleValue();
                    sjlist.add(StringUtil.formatString("" + totallc));
                    xj += totallc;
                } else
                    sjlist.add("");

                //计算总计
                String zjstr = zjkv.getValue().get(i).trim();
                Double zjdb = 0.0;
                if (zjstr != null && !zjstr.equals("")) {
                    zjdb = Double.parseDouble(zjstr);
                }
                zjdb += sjno;
                zjkv.getValue().set(i, StringUtil.formatString("" + new BigDecimal(zjdb).setScale(2,
                        BigDecimal.ROUND_HALF_UP).doubleValue()));
            }
            sjlist.add("" + StringUtil.formatString("" + new BigDecimal(xj).setScale(2,
                    BigDecimal.ROUND_HALF_UP).doubleValue()));

            sjkv.setValue(sjlist);
            data.add(sjkv);
        }

        //计算总计的小计
        Double zjxj = 0.0;
        for (String zjstr : zjlist) {
            if (zjstr != null && !zjstr.trim().equals("")) {
                zjxj += Double.parseDouble(zjstr);
            }
        }
        zjkv.getValue().add("" + new BigDecimal(zjxj).setScale(2,
                BigDecimal.ROUND_HALF_UP).doubleValue());

        data.add(zjkv);
        return data;
    }

    public BaseResult queryLcTable(Integer superxzqh,
                                   Date starttime) {
        BaseResult result = new BaseResultOK();
        //如果时间为今天，则直接进行汇总，否则从历史中查询
        if (DateTimeUtil.getTimeFmt(starttime, "yyyy-MM-dd")
                .equals(DateTimeUtil.getTimeFmt(new Date(), "yyyy-MM-dd"))) {
            List data = queryLcTableData(superxzqh);
            result.setObj(data);
            result.addToMap("day", DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"));
            result.addToMap("xzqhname", this.xzqhdmDaoImpl.queryXzqhName(superxzqh));
        } else {
            if (ifBaobiaoExist("LC" + superxzqh, DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"))) {
                List<KeyValueEntry<String, List<String>>> data = loadBaobiao("LC" + superxzqh, DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"));
                result.setObj(data);
                result.addToMap("day", DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"));
                result.addToMap("xzqhname", this.xzqhdmDaoImpl.queryXzqhName(superxzqh));
            }
        }
        return result;
    }

    public BaseResult exportLcTable(int superxzqh, Date starttime)
            throws Exception {
        List<KeyValueEntry<String, List<String>>> data = (List<KeyValueEntry<String, List<String>>>) this.queryLcTable(superxzqh, starttime).getObj();

        return exportSimpleTable("航道现状等级汇总表",
                data,
                "行政区划",
                "航道等级",
                this.xzqhdmDaoImpl.queryXzqhName(superxzqh) + "航道里程按现状等级汇总表",
                DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"),
                null,
                "计量单位：公里",
                null);
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

    public BaseResult querySsTable(Integer xzqh, Date starttime) {
        BaseResult result = new BaseResultOK();
        //如果时间为今天，则直接进行汇总，否则从历史中查询
        if (DateTimeUtil.getTimeFmt(starttime, "yyyy-MM-dd")
                .equals(DateTimeUtil.getTimeFmt(new Date(), "yyyy-MM-dd"))) {
            List<KeyValueEntry<String, List<String>>> data = this.querySsTableData(xzqh);
            result.setObj(data);
            result.addToMap("day", DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"));
            result.addToMap("xzqhname", this.xzqhdmDaoImpl.queryXzqhName(xzqh));
        } else {
            if (ifBaobiaoExist("SS" + xzqh, DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"))) {
                List<KeyValueEntry<String, List<String>>> data = loadBaobiao("SS" + xzqh, DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"));
                result.setObj(data);
                result.addToMap("day", DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"));
                result.addToMap("xzqhname", this.xzqhdmDaoImpl.queryXzqhName(xzqh));
            }
        }
        return result;
    }

    public BaseResult exportSsTable(int xzqh, Date starttime)
            throws Exception {
        List<KeyValueEntry<String, List<String>>> data = (List<KeyValueEntry<String, List<String>>>) this.querySsTable(xzqh, starttime).getObj();

        return exportSimpleTable("航道主要设施汇总表",
                data,
                "航道名称",
                null,
                this.xzqhdmDaoImpl.queryXzqhName(xzqh) + "航道主要设施汇总表",
                DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"),
                null,
                "计量单位：个",
                null);
    }

    public void saveSsTable(Integer superxzqh, Date day) {
        if (!ifBaobiaoExist("SS" + superxzqh, DateTimeUtil.getTimeFmt(day, "yyyy年MM月dd日"))) {
            List<KeyValueEntry<String, List<String>>> data = querySsTableData(superxzqh);
            saveOrUpdateBaobiao("SS" + superxzqh, DateTimeUtil.getTimeFmt(day, "yyyy年MM月dd日"), data);
        }
    }

    public void saveLcTable(Integer superxzqh, Date day) {
        if (!ifBaobiaoExist("LC" + superxzqh, DateTimeUtil.getTimeFmt(day, "yyyy年MM月dd日"))) {
            List<KeyValueEntry<String, List<String>>> data = queryLcTableData(superxzqh);
            saveOrUpdateBaobiao("LC" + superxzqh, DateTimeUtil.getTimeFmt(day, "yyyy年MM月dd日"), data);
        }
    }

    public void saveLcflTable(Integer superxzqh, Date day) {
        if (!ifBaobiaoExist("LCFL" + superxzqh, DateTimeUtil.getTimeFmt(day, "yyyy年MM月dd日"))) {
            List<KeyValueEntry<String, List<String>>> data = queryLcflTableData(superxzqh);
            saveOrUpdateBaobiao("LCFL" + superxzqh, DateTimeUtil.getTimeFmt(day, "yyyy年MM月dd日"), data);
        }
    }

    private List<Double> queryLcTableXZQH(CZdXzqhdm xzqh, List<CZdAppattribute> hddjs) {
        //查询该行政区划内的所有航道
        List<CHdHduanjcxx> hduans = this.hangDuanService.queryHDuanInXzqh("" + xzqh.getId());

        if (hduans == null || hduans.size() == 0) {
            return null;
        }
        List<Double> data = new ArrayList<>();
        //初始化数据为0
        for (int i = 0; i < hddjs.size(); i++) {
            data.add(0.0);
        }
        for (int i = 0; i < hduans.size(); i++) {
            CHdHduanjcxx hduan = hduans.get(i);

            //查找航段等级所在的index，将航道里程加到data对应的位置中去
            int index = -1;
            for (int j = 0; j < hddjs.size(); j++) {
                if (hduan.getHddj() == hddjs.get(j).getId()) {
                    index = j;
                    break;
                }
            }
            if (index != -1) {
                Double hdlc = hduan.getHdlc();
                Double cflc = hduan.getCflc();

                Double val = data.get(index);
                if (hdlc != null)
                    val += hdlc;
                if (cflc != null)
                    val -= cflc;
                data.set(index, val);
            }
        }
        return data;
    }

    private BaseResult exportHdyxssflTable(int xzqh, int type)
            throws Exception {
        BaseResult result = null;
        String templatedir = getContextPath() + "/template/";
        String baobiaodir = getContextPath() + "/baobiao/";
        String bbtitle = null;
        // 拷贝模板
        String baobiaofilename = null;
        switch (type) {
            case QL:
                bbtitle = "航道沿线桥梁按桥梁用途分类统计表";
                break;
            case HB:
                bbtitle = "航道沿线航标按标志类别分类统计表";
                break;
            case MT:
                bbtitle = "航道沿线码头按码头类型分类统计表";
                break;
            case GX:
                bbtitle = "航道沿线管线按管线种类分类统计表";
                break;
            default:
                return new BaseResultFailed();
        }
        baobiaofilename = FileUtils.writeToFile2(new File(templatedir
                + "/航道沿线主要设施分类汇总表.xlsx"), baobiaodir + "/" + bbtitle + ".xlsx");

        if (baobiaofilename != null) {
            // 获取数据
            BaseResult dataResult = null;
            switch (type) {
                case QL:
                    dataResult = this.queryQlTable(xzqh);
                    break;
                case HB:
                    dataResult = this.queryHbTable(xzqh);
                    break;
                case MT:
                    dataResult = this.queryMtTable(xzqh);
                    break;
                case GX:
                    dataResult = this.queryGxTable(xzqh);
                    break;
                default:
                    return new BaseResultFailed();
            }
            if (dataResult.ifResultOK()) {
                @SuppressWarnings("unchecked")
                List<Entry<String, List<String>>> tbMapList = (List<Entry<String, List<String>>>) dataResult
                        .getFromMap("data");
                @SuppressWarnings("unchecked")
                List<String> columns = (List<String>) dataResult
                        .getFromMap("columns");
                String time = (String) dataResult.getFromMap("time");
                String xzqhname = (String) dataResult.getFromMap("xzqhname");

                // 初始化poi
                File outputFile = new File(baobiaodir + "/" + baobiaofilename);
                XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                        outputFile));
                XSSFCellStyle style = null;
                Font font = null;
                XSSFSheet sheet = workbook.getSheetAt(0);

                // 创建标题
                XSSFRow titlerow = getRow(sheet, 1);
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, columns
                        .size() + 1));
                XSSFCell titlecell = getCell(titlerow, 1);
                titlecell.setCellValue(xzqhname + bbtitle);

                //添加时间副标题
                XSSFRow subtitleRow = getRow(sheet, 2);
                sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, columns
                        .size() + 1));
                XSSFCell subtitleCell = getCell(subtitleRow, 1);
                subtitleCell.setCellValue(time);

                // 创建单位
                XSSFRow unittitleRow = getRow(sheet, 3);
                XSSFCell dwcell = getCell(unittitleRow, columns.size() + 1);
                style = dwcell.getCellStyle();
                style.setAlignment(HorizontalAlignment.RIGHT);
                dwcell.setCellStyle(style);
                switch (type) {
                    case QL:
                        dwcell.setCellValue("计量单位：座");
                        break;
                    case HB:
                        dwcell.setCellValue("计量单位：个");
                        break;
                    case MT:
                        dwcell.setCellValue("计量单位：座");
                        break;
                    case GX:
                        dwcell.setCellValue("计量单位：条");
                        break;
                    default:
                        return new BaseResultFailed();
                }

                // 设置表格头信息
                XSSFRow tableRow = getRow(sheet, 4);
                // 设置表头左一格
                XSSFCell tableTitleFirst = getCell(tableRow, 1);
                String flstr = null;
                switch (type) {
                    case QL:
                        flstr = "用途分类";
                        break;
                    case HB:
                        flstr = "标志类别";
                        break;
                    case MT:
                        flstr = "码头类型";
                        break;
                    case GX:
                        flstr = "管线种类";
                        break;
                    default:
                        return new BaseResultFailed();
                }
                String ss = tableTitleFirst.getStringCellValue().replace("分类",
                        flstr);

                style = tableTitleFirst.getCellStyle();
                style.setBorderTop(BorderStyle.MEDIUM);
                style.setBorderLeft(BorderStyle.MEDIUM);
                style.setBorderRight(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                tableTitleFirst.setCellStyle(style);
                tableTitleFirst.setCellValue(ss);
                // 设置表头右几格
                for (int i = 0; i < columns.size(); i++) {
                    XSSFCell tableCell = getCell(tableRow, i + 2);

                    style = workbook.createCellStyle();
                    style.setBorderTop(BorderStyle.MEDIUM);
                    style.setBorderBottom(BorderStyle.THIN);
                    if (i == columns.size() - 1)
                        style.setBorderRight(BorderStyle.MEDIUM);
                    else
                        style.setBorderRight(BorderStyle.THIN);
                    style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                    style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
                    tableCell.setCellStyle(style);
                    tableCell.setCellValue(columns.get(i));
                }

                // 设置每一行
                for (int i = 0; i < tbMapList.size(); i++) {
                    Entry<String, List<String>> line = tbMapList.get(i);
                    XSSFRow row = getRow(sheet, i + 5);
                    XSSFCell rowTitle = getCell(row, 1);

                    style = workbook.createCellStyle();
                    style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                    style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
                    style.setBorderLeft(BorderStyle.MEDIUM);
                    style.setBorderRight(BorderStyle.THIN);
                    if (i == tbMapList.size() - 1)
                        style.setBorderBottom(BorderStyle.MEDIUM);
                    else
                        style.setBorderBottom(BorderStyle.THIN);
                    rowTitle.setCellStyle(style);
                    rowTitle.setCellValue(line.getKey());

                    for (int j = 0; j < line.getValue().size(); j++) {
                        XSSFCell rowTd = getCell(row, j + 2);
                        String val = line.getValue().get(j).trim();
                        if (val != null && !val.equals("")) {
                            rowTd.setCellValue(Integer.parseInt(val));
                        }

                        if (i == tbMapList.size() - 1) {
                            style = workbook.createCellStyle();
                            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
                            style.setBorderBottom(BorderStyle.MEDIUM);
                            style.setBorderLeft(BorderStyle.THIN);
                            if (j == line.getValue().size() - 1)
                                style.setBorderRight(BorderStyle.MEDIUM);
                            else
                                style.setBorderRight(BorderStyle.THIN);
                            rowTd.setCellStyle(style);
                        } else {
                            style = workbook.createCellStyle();
                            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
                            if (j == line.getValue().size() - 1) {
                                style.setBorderRight(BorderStyle.MEDIUM);
                                style.setBorderBottom(BorderStyle.THIN);
                                rowTd.setCellStyle(style);
                            } else {
                                style.setBorderBottom(BorderStyle.THIN);
                                style.setBorderRight(BorderStyle.THIN);
                                rowTd.setCellStyle(style);
                            }
                        }
                    }
                }

                FileOutputStream outputStream = new FileOutputStream(outputFile);
                workbook.write(outputStream);
                outputStream.close();
                workbook.close();
            }
        }
        logService.addLog(ModuleName.TJBB_SD, OpName.EXPORT, bbtitle);
        result = new BaseResultOK();
        result.addToMap("filename", baobiaofilename);
        result.addToMap("filepath", baobiaodir + "/" + baobiaofilename);
        return result;
    }

    private BaseResult exportGhdzyss(int xzqh, int type) throws IOException {
        BaseResult result = null;
        String templatedir = getContextPath() + "/template/";
        String baobiaodir = getContextPath() + "/baobiao/";
        String bbtitle = null;
        // 拷贝模板
        String baobiaofilename = null;
        switch (type) {
            case QL:
                bbtitle = "各航道桥梁按桥梁用途分类统计表(行政区划)";
                break;
            case HB:
                bbtitle = "各航道航标按标志类别分类统计表(行政区划)";
                break;
            case MT:
                bbtitle = "各航道码头按码头类型分类统计表(行政区划)";
                break;
            case GX:
                bbtitle = "各航道管线按管线种类分类统计表(行政区划)";
                break;
            default:
                return new BaseResultFailed();
        }
        baobiaofilename = FileUtils.writeToFile2(new File(templatedir
                + "/各航道主要设施分类汇总表(行政区划).xlsx"), baobiaodir + "/" + bbtitle + ".xlsx");

        if (baobiaofilename != null) {
            // 获取数据
            BaseResult dataResult = null;
            switch (type) {
                case QL:
                    dataResult = this.queryHdQl(xzqh);
                    break;
                case HB:
                    dataResult = this.queryHdHb(xzqh);
                    break;
                case MT:
                    dataResult = this.queryHdMt(xzqh);
                    break;
                case GX:
                    dataResult = this.queryHdGx(xzqh);
                    break;
                default:
                    return new BaseResultFailed();
            }
            if (dataResult.ifResultOK()) {
                @SuppressWarnings("unchecked")
                List<Entry<String, List<String>>> tbMapList = (List<Entry<String, List<String>>>) dataResult
                        .getFromMap("data");
                @SuppressWarnings("unchecked")
                List<String> columns = (List<String>) dataResult
                        .getFromMap("columns");
                String time = (String) dataResult.getFromMap("time");
                String xzqhname = (String) dataResult.getFromMap("xzqhname");

                // 初始化poi
                File outputFile = new File(baobiaodir + "/" + baobiaofilename);
                XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                        outputFile));
                XSSFCellStyle style = null;
                Font font = null;
                XSSFSheet sheet = workbook.getSheetAt(0);

                // 创建标题
                XSSFRow titlerow = getRow(sheet, 1);
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, columns
                        .size() + 1));
                XSSFCell titlecell = getCell(titlerow, 1);
                titlecell.setCellValue(xzqhname + bbtitle);

                //添加时间副标题
                XSSFRow subtitleRow = getRow(sheet, 2);
                sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, columns
                        .size() + 1));
                XSSFCell subtitleCell = getCell(subtitleRow, 1);
                subtitleCell.setCellValue(time);

                // 创建单位
                XSSFRow unittitleRow = getRow(sheet, 3);
                XSSFCell dwcell = getCell(unittitleRow, columns.size() + 1);
                style = dwcell.getCellStyle();
                style.setAlignment(HorizontalAlignment.RIGHT);
                dwcell.setCellStyle(style);
                switch (type) {
                    case QL:
                        dwcell.setCellValue("计量单位：座");
                        break;
                    case HB:
                        dwcell.setCellValue("计量单位：个");
                        break;
                    case MT:
                        dwcell.setCellValue("计量单位：座");
                        break;
                    case GX:
                        dwcell.setCellValue("计量单位：条");
                        break;
                    default:
                        return new BaseResultFailed();
                }

                // 设置表格头信息
                XSSFRow tableRow = getRow(sheet, 4);
                // 设置表头左一格
                XSSFCell tableTitleFirst = getCell(tableRow, 1);
                String flstr = null;
                switch (type) {
                    case QL:
                        flstr = "用途分类";
                        break;
                    case HB:
                        flstr = "标志类别";
                        break;
                    case MT:
                        flstr = "码头类型";
                        break;
                    case GX:
                        flstr = "管线种类";
                        break;
                    default:
                        return new BaseResultFailed();
                }
                String ss = tableTitleFirst.getStringCellValue().replace("分类",
                        flstr);

                style = tableTitleFirst.getCellStyle();
                style.setBorderTop(BorderStyle.MEDIUM);
                style.setBorderLeft(BorderStyle.MEDIUM);
                style.setBorderRight(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                tableTitleFirst.setCellStyle(style);
                tableTitleFirst.setCellValue(ss);
                // 设置表头右几格
                for (int i = 0; i < columns.size(); i++) {
                    XSSFCell tableCell = getCell(tableRow, i + 2);

                    style = workbook.createCellStyle();
                    style.setBorderTop(BorderStyle.MEDIUM);
                    style.setBorderBottom(BorderStyle.THIN);
                    if (i == columns.size() - 1)
                        style.setBorderRight(BorderStyle.MEDIUM);
                    else
                        style.setBorderRight(BorderStyle.THIN);
                    style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                    style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
                    tableCell.setCellStyle(style);
                    tableCell.setCellValue(columns.get(i));
                }

                // 设置每一行
                for (int i = 0; i < tbMapList.size(); i++) {
                    Entry<String, List<String>> line = tbMapList.get(i);
                    XSSFRow row = getRow(sheet, i + 5);
                    XSSFCell rowTitle = getCell(row, 1);

                    style = workbook.createCellStyle();
                    style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                    style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
                    style.setBorderLeft(BorderStyle.MEDIUM);
                    style.setBorderRight(BorderStyle.THIN);
                    if (i == tbMapList.size() - 1)
                        style.setBorderBottom(BorderStyle.MEDIUM);
                    else
                        style.setBorderBottom(BorderStyle.THIN);
                    rowTitle.setCellStyle(style);
                    rowTitle.setCellValue(line.getKey());

                    for (int j = 0; j < line.getValue().size(); j++) {
                        XSSFCell rowTd = getCell(row, j + 2);
                        String val = line.getValue().get(j).trim();
                        if (val != null && !val.equals("")) {
                            rowTd.setCellValue(Integer.parseInt(val));
                        }

                        if (i == tbMapList.size() - 1) {
                            style = workbook.createCellStyle();
                            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
                            style.setBorderBottom(BorderStyle.MEDIUM);
                            style.setBorderLeft(BorderStyle.THIN);
                            if (j == line.getValue().size() - 1)
                                style.setBorderRight(BorderStyle.MEDIUM);
                            else
                                style.setBorderRight(BorderStyle.THIN);
                            rowTd.setCellStyle(style);
                        } else {
                            style = workbook.createCellStyle();
                            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
                            if (j == line.getValue().size() - 1) {
                                style.setBorderRight(BorderStyle.MEDIUM);
                                style.setBorderBottom(BorderStyle.THIN);
                                rowTd.setCellStyle(style);
                            } else {
                                style.setBorderBottom(BorderStyle.THIN);
                                style.setBorderRight(BorderStyle.THIN);
                                rowTd.setCellStyle(style);
                            }
                        }
                    }
                }

                FileOutputStream outputStream = new FileOutputStream(outputFile);
                workbook.write(outputStream);
                outputStream.close();
                workbook.close();
            }
        }
        logService.addLog(ModuleName.TJBB_SD, OpName.EXPORT, bbtitle);
        result = new BaseResultOK();
        result.addToMap("filename", baobiaofilename);
        result.addToMap("filepath", baobiaodir + "/" + baobiaofilename);
        return result;
    }

    private static final int QL = 1;
    private static final int MT = 2;
    private static final int HB = 3;
    private static final int GX = 4;

    public BaseResult exportHdyxssflQlTable(int xzqh)
            throws Exception {
        return exportHdyxssflTable(xzqh, QL);
    }

    public BaseResult exportHdyxssflHbTable(int xzqh)
            throws Exception {
        return exportHdyxssflTable(xzqh, HB);
    }

    public BaseResult exportHdyxssflMtTable(int xzqh)
            throws Exception {
        return exportHdyxssflTable(xzqh, MT);
    }

    public BaseResult exportHdyxssflGxTable(int xzqh)
            throws Exception {
        return exportHdyxssflTable(xzqh, GX);
    }

    public BaseResult exportHdQl(int xzqh)
            throws Exception {
        return exportGhdzyss(xzqh, QL);
    }

    public BaseResult exportHdHb(int xzqh)
            throws Exception {
        return exportGhdzyss(xzqh, HB);
    }

    public BaseResult exportHdMt(int xzqh)
            throws Exception {
        return exportGhdzyss(xzqh, MT);
    }

    public BaseResult exportHdGx(int xzqh)
            throws Exception {
        return exportGhdzyss(xzqh, GX);
    }

    public BaseResult queryQlTable(int xzqh) {
        Map<String, List<String>> tbMap = new HashMap<String, List<String>>();
        // 用于获得所有的列标题
        List<String> columns = new ArrayList<String>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        xzqhdm.setId(xzqh);

        // 添加一个行政区划排序参照map
        final Map<String, Integer> xzqhref = new HashMap<>();

        try {
            // 查找所有xzqh及对应的子行政区划列表
            xzqhdm = this.xzqhdmService.findNode(xzqhdm);
            List<CZdXzqhdm> xzqhdms = this.xzqhdmService
                    .findChildrenNodes_r(xzqhdm);
            /**
             * 如果没有子行政区划，则默认查当前行政区划
             *
             * 如湖州市有长兴，安吉等，则查的是长兴安吉，如果输入的为长兴，则出来的就是长兴的
             */
            if (xzqhdms == null || xzqhdms.size() == 0) {
                xzqhdms.add(xzqhdm);
            }

            BaseQueryRecords<Object[]> ret = this.statisticsDao
                    .queryQLByXzqhsYtfl(xzqhdms);

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String ytflname = (String) objs[4];
                if (ytflname != null && !IflistContain(columns, ytflname))
                    columns.add(ytflname);
            }
            // 添加小计总计
            columns.add("小计");
            List<String> zjline = new ArrayList<>();
            tbMap.put("总计", zjline);
            for (int j = 0; j < columns.size(); j++) {
                zjline.add(" ");
            }

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String cnt = objs[0].toString();
                String xzqhid = objs[1].toString();
                String xzqhname = (String) objs[2];
                String ytflname = (String) objs[4];

                xzqhref.put(xzqhname, Integer.parseInt(xzqhid));

                if (!tbMap.containsKey(xzqhname)) {
                    List<String> line = new ArrayList<>();
                    tbMap.put(xzqhname, line);
                    for (int j = 0; j < columns.size(); j++) {
                        line.add(" ");
                    }
                }
                List<String> line = tbMap.get(xzqhname);
                if (ytflname == null)
                    continue;
                int colindex = getlistItemIndex(columns, ytflname);
                line.set(colindex, cnt);

                // 计算总计
                int zjVal;
                String zjValStr = tbMap.get("总计").get(colindex).trim();
                if (zjValStr == null || zjValStr.equals("")) {
                    zjVal = 0;
                } else {
                    zjVal = Integer.parseInt(zjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    zjVal += Integer.parseInt(cnt);
                }
                if (zjVal != 0) {
                    tbMap.get("总计").set(colindex, "" + zjVal);
                }

                // 计算小计
                int xjVal;
                String xjValStr = line.get(line.size() - 1).trim();
                if (xjValStr == null || xjValStr.equals("")) {
                    xjVal = 0;
                } else {
                    xjVal = Integer.parseInt(xjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    xjVal += Integer.parseInt(cnt);
                }
                if (xjVal != 0) {
                    line.set(line.size() - 1, "" + xjVal);
                }
            }

            // 计算小计的总计
            List<String> line = tbMap.get("总计");
            int value = 0;
            for (int i = 0; i < line.size(); i++) {
                String itemString = line.get(i).trim();
                if (i != line.size() - 1) {
                    if (itemString != null && itemString.length() > 0
                            && !itemString.equals("")) {
                        value += Integer.parseInt(itemString);
                    }
                } else {
                    if (value != 0)
                        line.set(i, "" + value);
                }
            }

        } catch (Exception e) {

        }

        // 排序tbmap
        List<Entry<String, List<String>>> tbMapList = new ArrayList<>(
                tbMap.entrySet());
        Collections.sort(tbMapList,
                new Comparator<Entry<String, List<String>>>() {

                    @Override
                    public int compare(Entry<String, List<String>> o1,
                                       Entry<String, List<String>> o2) {
                        Integer xzqhid1 = xzqhref.get(o1.getKey());
                        Integer xzqhid2 = xzqhref.get(o2.getKey());
                        if (xzqhid1 != null && xzqhid2 != null) {
                            return xzqhid1 - xzqhid2;
                        } else if (xzqhid1 != null && xzqhid2 == null) {
                            return -1;
                        } else if (xzqhid1 == null && xzqhid2 != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

        BaseResult result = new BaseResultOK();
        result.addToMap("data", tbMapList);
        result.addToMap("columns", columns);
        result.addToMap("time",
                DateTimeUtil.getTimeFmt(new Date(), "yyyy年MM月dd日"));
        result.addToMap("xzqhname", xzqhdm.getName());

        return result;
    }

    public BaseResult queryHbTable(int xzqh) {
        Map<String, List<String>> tbMap = new HashMap<String, List<String>>();
        // 用于获得所有的列标题
        List<String> columns = new ArrayList<String>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        xzqhdm.setId(xzqh);

        // 添加一个行政区划排序参照map
        final Map<String, Integer> xzqhref = new HashMap<>();

        try {
            // 查找所有xzqh及对应的子行政区划列表
            xzqhdm = this.xzqhdmService.findNode(xzqhdm);
            List<CZdXzqhdm> xzqhdms = this.xzqhdmService
                    .findChildrenNodes_r(xzqhdm);
            /**
             * 如果没有子行政区划，则默认查当前行政区划
             *
             * 如湖州市有长兴，安吉等，则查的是长兴安吉，如果输入的为长兴，则出来的就是长兴的
             */
            if (xzqhdms == null || xzqhdms.size() == 0) {
                xzqhdms.add(xzqhdm);
            }

            BaseQueryRecords<Object[]> ret = this.statisticsDao
                    .queryHbByXzqhsBzfl(xzqhdms);

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String ytflname = (String) objs[4];
                if (ytflname != null && !IflistContain(columns, ytflname))
                    columns.add(ytflname);
            }

            // 添加小计总计
            columns.add("小计");
            List<String> zjline = new ArrayList<>();
            tbMap.put("总计", zjline);
            for (int j = 0; j < columns.size(); j++) {
                zjline.add(" ");
            }

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String cnt = objs[0].toString();
                String xzqhid = objs[1].toString();
                String xzqhname = (String) objs[2];
                String ytflname = (String) objs[4];

                xzqhref.put(xzqhname, Integer.parseInt(xzqhid));

                if (!tbMap.containsKey(xzqhname)) {
                    List<String> line = new ArrayList<>();
                    tbMap.put(xzqhname, line);
                    for (int j = 0; j < columns.size(); j++) {
                        line.add(" ");
                    }
                }
                List<String> line = tbMap.get(xzqhname);
                if (ytflname == null)
                    continue;
                int colindex = getlistItemIndex(columns, ytflname);
                line.set(colindex, cnt);

                // 计算总计
                int zjVal;
                String zjValStr = tbMap.get("总计").get(colindex).trim();
                if (zjValStr == null || zjValStr.equals("")) {
                    zjVal = 0;
                } else {
                    zjVal = Integer.parseInt(zjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    zjVal += Integer.parseInt(cnt);
                }
                if (zjVal != 0) {
                    tbMap.get("总计").set(colindex, "" + zjVal);
                }

                // 计算小计
                int xjVal;
                String xjValStr = line.get(line.size() - 1).trim();
                if (xjValStr == null || xjValStr.equals("")) {
                    xjVal = 0;
                } else {
                    xjVal = Integer.parseInt(xjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    xjVal += Integer.parseInt(cnt);
                }
                if (xjVal != 0) {
                    line.set(line.size() - 1, "" + xjVal);
                }
            }

            // 计算小计的总计
            List<String> line = tbMap.get("总计");
            int value = 0;
            for (int i = 0; i < line.size(); i++) {
                String itemString = line.get(i).trim();
                if (i != line.size() - 1) {
                    if (itemString != null && itemString.length() > 0
                            && !itemString.equals("")) {
                        value += Integer.parseInt(itemString);
                    }
                } else {
                    if (value != 0)
                        line.set(i, "" + value);
                }
            }
        } catch (Exception e) {

        }

        // 排序tbmap
        List<Entry<String, List<String>>> tbMapList = new ArrayList<>(
                tbMap.entrySet());
        Collections.sort(tbMapList,
                new Comparator<Entry<String, List<String>>>() {

                    @Override
                    public int compare(Entry<String, List<String>> o1,
                                       Entry<String, List<String>> o2) {
                        Integer xzqhid1 = xzqhref.get(o1.getKey());
                        Integer xzqhid2 = xzqhref.get(o2.getKey());
                        if (xzqhid1 != null && xzqhid2 != null) {
                            return xzqhid1 - xzqhid2;
                        } else if (xzqhid1 != null && xzqhid2 == null) {
                            return -1;
                        } else if (xzqhid1 == null && xzqhid2 != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

        BaseResult result = new BaseResultOK();
        result.addToMap("data", tbMapList);
        result.addToMap("columns", columns);
        result.addToMap("time",
                DateTimeUtil.getTimeFmt(new Date(), "yyyy年MM月dd日"));
        result.addToMap("xzqhname", xzqhdm.getName());
        return result;
    }

    public BaseResult queryMtTable(int xzqh) {
        Map<String, List<String>> tbMap = new HashMap<String, List<String>>();
        // 用于获得所有的列标题
        List<String> columns = new ArrayList<String>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        xzqhdm.setId(xzqh);

        // 添加一个行政区划排序参照map
        final Map<String, Integer> xzqhref = new HashMap<>();

        try {
            // 查找所有xzqh及对应的子行政区划列表
            xzqhdm = this.xzqhdmService.findNode(xzqhdm);
            List<CZdXzqhdm> xzqhdms = this.xzqhdmService
                    .findChildrenNodes_r(xzqhdm);
            /**
             * 如果没有子行政区划，则默认查当前行政区划
             *
             * 如湖州市有长兴，安吉等，则查的是长兴安吉，如果输入的为长兴，则出来的就是长兴的
             */
            if (xzqhdms == null || xzqhdms.size() == 0) {
                xzqhdms.add(xzqhdm);
            }

            BaseQueryRecords<Object[]> ret = this.statisticsDao
                    .queryMtByXzqhsMtlx(xzqhdms);

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String ytflname = (String) objs[2];
                if (ytflname != null && !IflistContain(columns, ytflname))
                    columns.add(ytflname);
            }

            // 添加小计总计
            columns.add("小计");
            List<String> zjline = new ArrayList<>();
            tbMap.put("总计", zjline);
            for (int j = 0; j < columns.size(); j++) {
                zjline.add(" ");
            }

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String cnt = objs[0].toString();
                String xzqhid = objs[3].toString();
                String xzqhname = (String) objs[1];
                String ytflname = (String) objs[2];

                xzqhref.put(xzqhname, Integer.parseInt(xzqhid));

                if (!tbMap.containsKey(xzqhname)) {
                    List<String> line = new ArrayList<>();
                    tbMap.put(xzqhname, line);
                    for (int j = 0; j < columns.size(); j++) {
                        line.add(" ");
                    }
                }
                List<String> line = tbMap.get(xzqhname);
                if (ytflname == null)
                    continue;
                int colindex = getlistItemIndex(columns, ytflname);
                line.set(colindex, cnt);

                // 计算总计
                int zjVal;
                String zjValStr = tbMap.get("总计").get(colindex).trim();
                if (zjValStr == null || zjValStr.equals("")) {
                    zjVal = 0;
                } else {
                    zjVal = Integer.parseInt(zjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    zjVal += Integer.parseInt(cnt);
                }
                if (zjVal != 0) {
                    tbMap.get("总计").set(colindex, "" + zjVal);
                }

                // 计算小计
                int xjVal;
                String xjValStr = line.get(line.size() - 1).trim();
                if (xjValStr == null || xjValStr.equals("")) {
                    xjVal = 0;
                } else {
                    xjVal = Integer.parseInt(xjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    xjVal += Integer.parseInt(cnt);
                }
                if (xjVal != 0) {
                    line.set(line.size() - 1, "" + xjVal);
                }
            }

            // 计算小计的总计
            List<String> line = tbMap.get("总计");
            int value = 0;
            for (int i = 0; i < line.size(); i++) {
                String itemString = line.get(i).trim();
                if (i != line.size() - 1) {
                    if (itemString != null && itemString.length() > 0
                            && !itemString.equals("")) {
                        value += Integer.parseInt(itemString);
                    }
                } else {
                    if (value != 0)
                        line.set(i, "" + value);
                }
            }
        } catch (Exception e) {

        }
        // 排序tbmap
        List<Entry<String, List<String>>> tbMapList = new ArrayList<>(
                tbMap.entrySet());
        Collections.sort(tbMapList,
                new Comparator<Entry<String, List<String>>>() {

                    @Override
                    public int compare(Entry<String, List<String>> o1,
                                       Entry<String, List<String>> o2) {
                        Integer xzqhid1 = xzqhref.get(o1.getKey());
                        Integer xzqhid2 = xzqhref.get(o2.getKey());
                        if (xzqhid1 != null && xzqhid2 != null) {
                            return xzqhid1 - xzqhid2;
                        } else if (xzqhid1 != null && xzqhid2 == null) {
                            return -1;
                        } else if (xzqhid1 == null && xzqhid2 != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

        BaseResult result = new BaseResultOK();
        result.addToMap("data", tbMapList);
        result.addToMap("columns", columns);
        result.addToMap("time",
                DateTimeUtil.getTimeFmt(new Date(), "yyyy年MM月dd日"));
        result.addToMap("xzqhname", xzqhdm.getName());
        return result;
    }

    public BaseResult queryGxTable(int xzqh) {
        Map<String, List<String>> tbMap = new HashMap<String, List<String>>();
        // 用于获得所有的列标题
        List<String> columns = new ArrayList<String>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        xzqhdm.setId(xzqh);

        // 添加一个行政区划排序参照map
        final Map<String, Integer> xzqhref = new HashMap<>();

        try {
            // 查找所有xzqh及对应的子行政区划列表
            xzqhdm = this.xzqhdmService.findNode(xzqhdm);
            List<CZdXzqhdm> xzqhdms = this.xzqhdmService
                    .findChildrenNodes_r(xzqhdm);

            /**
             * 如果没有子行政区划，则默认查当前行政区划
             *
             * 如湖州市有长兴，安吉等，则查的是长兴安吉，如果输入的为长兴，则出来的就是长兴的
             */
            if (xzqhdms == null || xzqhdms.size() == 0) {
                xzqhdms.add(xzqhdm);
            }

            BaseQueryRecords<Object[]> ret = this.statisticsDao
                    .queryGxByXzqhsGxzl(xzqhdms);

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String ytflname = (String) objs[4];
                if (ytflname != null && !IflistContain(columns, ytflname))
                    columns.add(ytflname);
            }

            // 添加小计总计
            columns.add("小计");
            List<String> zjline = new ArrayList<>();
            tbMap.put("总计", zjline);
            for (int j = 0; j < columns.size(); j++) {
                zjline.add(" ");
            }

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String cnt = objs[0].toString();
                String xzqhid = objs[1].toString();
                String xzqhname = (String) objs[2];
                String ytflname = (String) objs[4];

                xzqhref.put(xzqhname, Integer.parseInt(xzqhid));

                if (!tbMap.containsKey(xzqhname)) {
                    List<String> line = new ArrayList<>();
                    tbMap.put(xzqhname, line);
                    for (int j = 0; j < columns.size(); j++) {
                        line.add(" ");
                    }
                }
                List<String> line = tbMap.get(xzqhname);
                if (ytflname == null)
                    continue;
                int colindex = getlistItemIndex(columns, ytflname);
                line.set(colindex, cnt);

                // 计算总计
                int zjVal;
                String zjValStr = tbMap.get("总计").get(colindex).trim();
                if (zjValStr == null || zjValStr.equals("")) {
                    zjVal = 0;
                } else {
                    zjVal = Integer.parseInt(zjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    zjVal += Integer.parseInt(cnt);
                }
                if (zjVal != 0) {
                    tbMap.get("总计").set(colindex, "" + zjVal);
                }

                // 计算小计
                int xjVal;
                String xjValStr = line.get(line.size() - 1).trim();
                if (xjValStr == null || xjValStr.equals("")) {
                    xjVal = 0;
                } else {
                    xjVal = Integer.parseInt(xjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    xjVal += Integer.parseInt(cnt);
                }
                if (xjVal != 0) {
                    line.set(line.size() - 1, "" + xjVal);
                }
            }

            // 计算小计的总计
            List<String> line = tbMap.get("总计");
            int value = 0;
            for (int i = 0; i < line.size(); i++) {
                String itemString = line.get(i).trim();
                if (i != line.size() - 1) {
                    if (itemString != null && itemString.length() > 0
                            && !itemString.equals("")) {
                        value += Integer.parseInt(itemString);
                    }
                } else {
                    if (value != 0)
                        line.set(i, "" + value);
                }
            }
        } catch (Exception e) {

        }
        // 排序tbmap
        List<Entry<String, List<String>>> tbMapList = new ArrayList<>(
                tbMap.entrySet());
        Collections.sort(tbMapList,
                new Comparator<Entry<String, List<String>>>() {

                    @Override
                    public int compare(Entry<String, List<String>> o1,
                                       Entry<String, List<String>> o2) {
                        Integer xzqhid1 = xzqhref.get(o1.getKey());
                        Integer xzqhid2 = xzqhref.get(o2.getKey());
                        if (xzqhid1 != null && xzqhid2 != null) {
                            return xzqhid1 - xzqhid2;
                        } else if (xzqhid1 != null && xzqhid2 == null) {
                            return -1;
                        } else if (xzqhid1 == null && xzqhid2 != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

        BaseResult result = new BaseResultOK();
        result.addToMap("data", tbMapList);
        result.addToMap("columns", columns);
        result.addToMap("time",
                DateTimeUtil.getTimeFmt(new Date(), "yyyy年MM月dd日"));
        result.addToMap("xzqhname", xzqhdm.getName());
        return result;
    }

    private Object[] getArrObjFromList(List<BigInteger> list) {
        int lsize = list.size();
        Object[] olist = new Object[lsize];
        for (int i = 0; i < lsize; i++) {
            olist[i] = list.get(i).toString();
        }
        return olist;
    }

    //清.0操作
    private void clearZero(List<List<TableCell>> data, int rownum, int endrownum, int startnum, int endnum) {
        List<TableCell> tableCells = data.get(rownum);
        for (int j = startnum; j < endnum + 1; j++) {
            TableCell tc = tableCells.get(j);
            tc.setCellvalue(tc.getCellvalue().replace(".0", ""));
        }
        for (int i = rownum + 1; i < endnum; i++) {
            TableCell tc = data.get(i).get(0);
            tc.setCellvalue(tc.getCellvalue().replace(".0", ""));
        }
    }

    //清空某个行 区间tr的数据
    private void clearTrValue(List<List<TableCell>> data, int rownum, int startnum, int endnum) {
        List<TableCell> tableCells = data.get(rownum);
        for (int j = startnum; j < endnum + 1; j++) {
            tableCells.get(j).setCellvalue("");
        }
    }

    //单行设值
    private void setJsy61TrValue(Object[] tddata, List<TableCell> list) {
        list.get(4).setCellvalue(tddata[0].toString());
        list.get(6).setCellvalue(tddata[0].toString());
        list.get(7).setCellvalue(tddata[1].toString());
        list.get(8).setCellvalue(tddata[2].toString());
        list.get(9).setCellvalue(tddata[3].toString());
        list.get(10).setCellvalue(tddata[4].toString());
        list.get(12).setCellvalue(tddata[5].toString());
        list.get(15).setCellvalue(tddata[6].toString());
        list.get(18).setCellvalue(tddata[7].toString());
        list.get(19).setCellvalue(tddata[8].toString());
        list.get(20).setCellvalue(tddata[9].toString());
        list.get(21).setCellvalue(tddata[10].toString());
        list.get(22).setCellvalue(tddata[11].toString());
        list.get(23).setCellvalue(tddata[12].toString());
    }

    //单行设值
    private void setJsy62TrValue(Object[] tddata, List<TableCell> list) {
        list.get(6).setCellvalue(StringUtil.getStringIsNull(tddata[0]));
        list.get(8).setCellvalue(StringUtil.getStringIsNull(tddata[1]));
        list.get(9).setCellvalue(StringUtil.getStringIsNull(tddata[2]));
        list.get(10).setCellvalue(StringUtil.getStringIsNull(tddata[3]));
        list.get(11).setCellvalue(StringUtil.getStringIsNull(tddata[4]));
        list.get(12).setCellvalue(StringUtil.getStringIsNull(tddata[5]));
        list.get(13).setCellvalue(StringUtil.getStringIsNull(tddata[6]));
        list.get(14).setCellvalue(StringUtil.jgPercent(StringUtil.getStringIsNull(tddata[7])));
        list.get(15).setCellvalue(StringUtil.jgPercent(StringUtil.getStringIsNull(tddata[8])));
    }

    //单行设值
    private void setJsy63TrValue(Object[] tddata, List<TableCell> list) {
        list.get(4).setCellvalue(StringUtil.getStringIsNull(tddata[0]));
        list.get(6).setCellvalue(StringUtil.getStringIsNull(tddata[1]));
        list.get(10).setCellvalue(StringUtil.getStringIsNull(tddata[1]));
        list.get(29).setCellvalue(StringUtil.getStringIsNull(tddata[2]));
    }

    //单行设值
    private void setJsy671TrValue(Object[] tddata, List<TableCell> list) {
        list.get(8).setCellvalue(String.valueOf(Integer.parseInt(tddata[0].toString()) + Integer.parseInt(tddata[1].toString())));
        list.get(9).setCellvalue(tddata[0].toString());
        list.get(11).setCellvalue(tddata[1].toString());
        list.get(13).setCellvalue(tddata[2].toString());
        list.get(14).setCellvalue(tddata[2].toString());
    }

    private List<List<TableCell>> queryJSYTableFromDB(int tableindex) throws SQLException, IOException, ClassNotFoundException {
        CBbJSYOriBb bb = this.statisticsDao.queryJSYTableFromDB(tableindex);
        if (bb != null) {
            Blob blob = bb.getTableobj();
            List<List<TableCell>> data = new ArrayList<List<TableCell>>();
            ObjectInputStream in = new ObjectInputStream(blob.getBinaryStream());
            data = (List<List<TableCell>>) in.readObject();
            return data;
        } else {
            return null;
        }
    }

    private void saveJSYTableToDB(int tableindex, List<List<TableCell>> data) throws SQLException, IOException, ClassNotFoundException {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        List<List<TableCell>> list = queryJSYTableFromDB(tableindex);
        if (list != null) {
            this.statisticsDao.delCBbBb(tableindex);
        }
        CBbJSYOriBb bb = new CBbJSYOriBb();
        bb.setTableindex(tableindex);
        bb.setCreater(user.getId());
        bb.setCreattime(new Date());
        bb.setUpdatetime(new Date());
        this.statisticsDao.addCBbBb(bb, data);
    }

    private void updateJSYTableTODB(int tableindex, List<List<TableCell>> data) throws IOException {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        this.statisticsDao.delCBbBb(tableindex);
        CBbJSYOriBb bb = new CBbJSYOriBb();
        bb.setTableindex(tableindex);
        bb.setCreater(user.getId());
        bb.setCreattime(new Date());
        bb.setUpdatetime(new Date());
        this.statisticsDao.addCBbBb(bb, data);
    }

    private boolean editJSYTable(List<List<TableCell>> data, int row, int col, TableCell newcell) {
        boolean flag = false;
        if (data != null) {
            List<TableCell> tr = data.get(row);
            if (tr != null) {
                TableCell td = tr.get(col);
                if (td != null) {
                    td.setCellvalue(newcell.getCellvalue());
                    td.setRspan(newcell.getRspan());
                    td.setCspan(newcell.getCspan());
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**********************
     * 通用报表历史记录保存及读取
     ************************/
    private void saveOrUpdateBaobiao(String baobiaokey, String baobiaotime, List<KeyValueEntry<String, List<String>>> data) {
        if (!ifBaobiaoExist(baobiaokey, baobiaotime)) {
            for (KeyValueEntry<String, List<String>> dt : data) {
                CBfBaoBiao bb = new CBfBaoBiao();
                bb.setBaobiaokey(baobiaokey);
                bb.setBaobiaotime(baobiaotime);
                bb.setDatakey(dt.getKey());
                String value = "";
                for (int i = 0; i < ((List<String>) dt.getValue()).size(); i++) {
                    value += ((List<String>) dt.getValue()).get(i);
                    if (i != ((List<String>) dt.getValue()).size() - 1)
                        value += ",";
                }
                bb.setDatavalue(value);
                this.statisticsDao.save(bb);
            }
        }
    }

    private List<KeyValueEntry<String, List<String>>> loadBaobiao(String baobiaokey, String baobiaotime) {
        if (!ifBaobiaoExist(baobiaokey, baobiaotime))
            return null;

        List<CBfBaoBiao> bbs = this.statisticsDao.loadBaoBiao(baobiaokey, baobiaotime);
        List<KeyValueEntry<String, List<String>>> data = new ArrayList<>();

        int colnum = bbs.get(0).getDatavalue().split(",").length;
        for (CBfBaoBiao bb : bbs) {
            KeyValueEntry<String, List<String>> kv = new KeyValueEntry<>(bb.getDatakey());
            List<String> valuelist = new ArrayList<>();
            String[] valuesplit = bb.getDatavalue().split(",");
            int len = valuesplit.length;
            for (int i = 0; i < colnum; i++) {
                if (i < len) {
                    valuelist.add(valuesplit[i]);
                } else {
                    valuelist.add("");
                }
            }
            kv.setValue(valuelist);
            data.add(kv);
        }

        return data;
    }

    private boolean ifBaobiaoExist(String baobiaokey, String baobiaotime) {
        return this.statisticsDao.ifBaobiaoExist(baobiaokey, baobiaotime);
    }

    public BaseResult queryLcflTable(int superxzqh, Date starttime) {
        BaseResult result = new BaseResultOK();
        //如果时间为今天，则直接进行汇总，否则从历史中查询
        if (DateTimeUtil.getTimeFmt(starttime, "yyyy-MM-dd")
                .equals(DateTimeUtil.getTimeFmt(new Date(), "yyyy-MM-dd"))) {
            List data = queryLcflTableData(superxzqh);
            result.setObj(data);
            result.addToMap("day", DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"));
            result.addToMap("xzqhname", this.xzqhdmDaoImpl.queryXzqhName(superxzqh));
        } else {
            if (ifBaobiaoExist("LCFL" + superxzqh, DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"))) {
                List<KeyValueEntry<String, List<String>>> data = loadBaobiao("LCFL" + superxzqh, DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"));
                result.setObj(data);
                result.addToMap("day", DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"));
                result.addToMap("xzqhname", this.xzqhdmDaoImpl.queryXzqhName(superxzqh));
            }
        }
        return result;
    }

    private List<KeyValueEntry<String, List<String>>> queryLcflTableData(Integer xzqhid) {
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
        //获取航道等级字典列表
        List<CZdAppattribute> hddjs = this.dicDao.queryDicAttr("hddj").getData();

        //查找这些行政区划对应的航道里程统计信息
        List<KeyValueEntry<String, List<String>>> data = new ArrayList<>();
        //存入第一行表头
        KeyValueEntry<String, List<String>> titlekv = new KeyValueEntry<String, List<String>>("航道等级");
        List<String> titlelist = new ArrayList<>();
        titlelist.add("航道里程");
        for (CZdAppattribute hddj : hddjs)
            titlelist.add(hddj.getAttrdesc());
        titlekv.setValue(titlelist);
        data.add(titlekv);
        StringBuffer sb = new StringBuffer("");
        //存入每个行政区划的数据数据
        for (CZdXzqhdm xzqh : xzqhs) {
            //获取数据
            sb.append(xzqh.getId() + ",");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        //骨干
        List gglist = this.statisticsDao.queryLcflGgXZQH(sb.toString());
        //支线
        List zxlist = this.statisticsDao.queryLcflZxXZQH(sb.toString());

        if (gglist != null && gglist.size() > 0) {
            for (int i = 0; i < gglist.size(); i++) {
                Object[] arr = (Object[]) gglist.get(i);
                KeyValueEntry<String, List<String>> kv = new KeyValueEntry<>(arr[0].toString());
                List<String> strlist = new ArrayList<String>();
                strlist.add(StringUtil.getIsNull(arr[1]).toString());
                strlist.add(StringUtil.getIsNull(arr[2]).toString());
                strlist.add(StringUtil.getIsNull(arr[3]).toString());
                strlist.add(StringUtil.getIsNull(arr[4]).toString());
                strlist.add(StringUtil.getIsNull(arr[5]).toString());
                strlist.add(StringUtil.getIsNull(arr[6]).toString());
                strlist.add(StringUtil.getIsNull(arr[7]).toString());
                strlist.add(StringUtil.getIsNull(arr[8]).toString());
                strlist.add(StringUtil.getIsNull(arr[9]).toString());
                kv.setValue(strlist);
                data.add(kv);
            }
        }
        if (zxlist != null && zxlist.size() > 0) {
            for (int i = 0; i < zxlist.size(); i++) {
                Object[] arr = (Object[]) zxlist.get(i);
                KeyValueEntry<String, List<String>> kv = new KeyValueEntry<>(arr[0].toString());
                List<String> strlist = new ArrayList<String>();
                strlist.add(StringUtil.getIsNull(arr[1]).toString());
                strlist.add(StringUtil.getIsNull(arr[2]).toString());
                strlist.add(StringUtil.getIsNull(arr[3]).toString());
                strlist.add(StringUtil.getIsNull(arr[4]).toString());
                strlist.add(StringUtil.getIsNull(arr[5]).toString());
                strlist.add(StringUtil.getIsNull(arr[6]).toString());
                strlist.add(StringUtil.getIsNull(arr[7]).toString());
                strlist.add(StringUtil.getIsNull(arr[8]).toString());
                strlist.add(StringUtil.getIsNull(arr[9]).toString());
                kv.setValue(strlist);
                data.add(kv);
            }
        }

        return data;
    }

    public BaseResult exportLcflTable(int superxzqh, Date starttime) throws Exception {
        List<KeyValueEntry<String, List<String>>> data = (List<KeyValueEntry<String, List<String>>>) this.queryLcflTable(superxzqh, starttime).getObj();
        return exportSimpleTable("航道里程分类统计表", data, "航道名称", null, this.xzqhdmDaoImpl.queryXzqhName(superxzqh) + "航道里程分类统计表", DateTimeUtil.getTimeFmt(starttime, "yyyy年MM月dd日"), null, "计量单位：公里", null);
    }

    public BaseResult queryHdQl(Integer xzqh) {
        Map<String, List<String>> tbMap = new HashMap<String, List<String>>();
        // 用于获得所有的列标题
        List<String> columns = new ArrayList<String>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        xzqhdm.setId(xzqh);

        // 添加一个行政区划排序参照map
        final Map<String, Integer> hdref = new HashMap<>();

        try {
            // 查找所有xzqh及对应的子行政区划列表
            xzqhdm = this.xzqhdmService.findNode(xzqhdm);
            List<CZdXzqhdm> xzqhdms = this.xzqhdmService
                    .findChildrenNodes_r(xzqhdm);
            /**
             * 如果没有子行政区划，则默认查当前行政区划
             *
             * 如湖州市有长兴，安吉等，则查的是长兴安吉，如果输入的为长兴，则出来的就是长兴的
             */
            if (xzqhdms == null || xzqhdms.size() == 0) {
                xzqhdms.add(xzqhdm);
            }

            BaseQueryRecords<Object[]> ret = this.statisticsDao
                    .queryHdQlByYtfl(xzqhdms);

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String ytflname = (String) objs[4];
                if (ytflname != null && !IflistContain(columns, ytflname))
                    columns.add(ytflname);
            }
            // 添加小计总计
            columns.add("小计");
            List<String> zjline = new ArrayList<>();
            tbMap.put("总计", zjline);
            for (int j = 0; j < columns.size(); j++) {
                zjline.add(" ");
            }

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String cnt = objs[0].toString();
                String hdid = objs[1].toString();
                String hdname = (String) objs[2];
                String ytflname = (String) objs[4];

                hdref.put(hdname, Integer.parseInt(hdid));

                if (!tbMap.containsKey(hdname)) {
                    List<String> line = new ArrayList<>();
                    tbMap.put(hdname, line);
                    for (int j = 0; j < columns.size(); j++) {
                        line.add(" ");
                    }
                }
                List<String> line = tbMap.get(hdname);
                if (ytflname == null)
                    continue;
                int colindex = getlistItemIndex(columns, ytflname);
                line.set(colindex, cnt);

                // 计算总计
                int zjVal;
                String zjValStr = tbMap.get("总计").get(colindex).trim();
                if (zjValStr == null || zjValStr.equals("")) {
                    zjVal = 0;
                } else {
                    zjVal = Integer.parseInt(zjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    zjVal += Integer.parseInt(cnt);
                }
                if (zjVal != 0) {
                    tbMap.get("总计").set(colindex, "" + zjVal);
                }

                // 计算小计
                int xjVal;
                String xjValStr = line.get(line.size() - 1).trim();
                if (xjValStr == null || xjValStr.equals("")) {
                    xjVal = 0;
                } else {
                    xjVal = Integer.parseInt(xjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    xjVal += Integer.parseInt(cnt);
                }
                if (xjVal != 0) {
                    line.set(line.size() - 1, "" + xjVal);
                }
            }

            // 计算小计的总计
            List<String> line = tbMap.get("总计");
            int value = 0;
            for (int i = 0; i < line.size(); i++) {
                String itemString = line.get(i).trim();
                if (i != line.size() - 1) {
                    if (itemString != null && itemString.length() > 0
                            && !itemString.equals("")) {
                        value += Integer.parseInt(itemString);
                    }
                } else {
                    if (value != 0)
                        line.set(i, "" + value);
                }
            }

        } catch (Exception e) {

        }

        // 排序tbmap
        List<Entry<String, List<String>>> tbMapList = new ArrayList<>(
                tbMap.entrySet());
        Collections.sort(tbMapList,
                new Comparator<Entry<String, List<String>>>() {

                    @Override
                    public int compare(Entry<String, List<String>> o1,
                                       Entry<String, List<String>> o2) {
                        Integer hdid1 = hdref.get(o1.getKey());
                        Integer hdid2 = hdref.get(o2.getKey());
                        if (hdid1 != null && hdid2 != null) {
                            return hdid1 - hdid2;
                        } else if (hdid1 != null && hdid2 == null) {
                            return -1;
                        } else if (hdid1 == null && hdid2 != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

        BaseResult result = new BaseResultOK();
        result.addToMap("data", tbMapList);
        result.addToMap("columns", columns);
        result.addToMap("time",
                DateTimeUtil.getTimeFmt(new Date(), "yyyy年MM月dd日"));
        result.addToMap("xzqhname", xzqhdm.getName());

        return result;
    }

    public BaseResult queryHdGx(Integer xzqh) {
        Map<String, List<String>> tbMap = new HashMap<String, List<String>>();
        // 用于获得所有的列标题
        List<String> columns = new ArrayList<String>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        xzqhdm.setId(xzqh);

        // 添加一个行政区划排序参照map
        final Map<String, Integer> hdref = new HashMap<>();

        try {
            // 查找所有xzqh及对应的子行政区划列表
            xzqhdm = this.xzqhdmService.findNode(xzqhdm);
            List<CZdXzqhdm> xzqhdms = this.xzqhdmService
                    .findChildrenNodes_r(xzqhdm);

            /**
             * 如果没有子行政区划，则默认查当前行政区划
             *
             * 如湖州市有长兴，安吉等，则查的是长兴安吉，如果输入的为长兴，则出来的就是长兴的
             */
            if (xzqhdms == null || xzqhdms.size() == 0) {
                xzqhdms.add(xzqhdm);
            }

            BaseQueryRecords<Object[]> ret = this.statisticsDao
                    .queryHdGxByGxzl(xzqhdms);

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String ytflname = (String) objs[4];
                if (ytflname != null && !IflistContain(columns, ytflname))
                    columns.add(ytflname);
            }

            // 添加小计总计
            columns.add("小计");
            List<String> zjline = new ArrayList<>();
            tbMap.put("总计", zjline);
            for (int j = 0; j < columns.size(); j++) {
                zjline.add(" ");
            }

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String cnt = objs[0].toString();
                String xzqhid = objs[1].toString();
                String xzqhname = (String) objs[2];
                String ytflname = (String) objs[4];

                hdref.put(xzqhname, Integer.parseInt(xzqhid));

                if (!tbMap.containsKey(xzqhname)) {
                    List<String> line = new ArrayList<>();
                    tbMap.put(xzqhname, line);
                    for (int j = 0; j < columns.size(); j++) {
                        line.add(" ");
                    }
                }
                List<String> line = tbMap.get(xzqhname);
                if (ytflname == null)
                    continue;
                int colindex = getlistItemIndex(columns, ytflname);
                line.set(colindex, cnt);

                // 计算总计
                int zjVal;
                String zjValStr = tbMap.get("总计").get(colindex).trim();
                if (zjValStr == null || zjValStr.equals("")) {
                    zjVal = 0;
                } else {
                    zjVal = Integer.parseInt(zjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    zjVal += Integer.parseInt(cnt);
                }
                if (zjVal != 0) {
                    tbMap.get("总计").set(colindex, "" + zjVal);
                }

                // 计算小计
                int xjVal;
                String xjValStr = line.get(line.size() - 1).trim();
                if (xjValStr == null || xjValStr.equals("")) {
                    xjVal = 0;
                } else {
                    xjVal = Integer.parseInt(xjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    xjVal += Integer.parseInt(cnt);
                }
                if (xjVal != 0) {
                    line.set(line.size() - 1, "" + xjVal);
                }
            }

            // 计算小计的总计
            List<String> line = tbMap.get("总计");
            int value = 0;
            for (int i = 0; i < line.size(); i++) {
                String itemString = line.get(i).trim();
                if (i != line.size() - 1) {
                    if (itemString != null && itemString.length() > 0
                            && !itemString.equals("")) {
                        value += Integer.parseInt(itemString);
                    }
                } else {
                    if (value != 0)
                        line.set(i, "" + value);
                }
            }
        } catch (Exception e) {

        }
        // 排序tbmap
        List<Entry<String, List<String>>> tbMapList = new ArrayList<>(
                tbMap.entrySet());
        Collections.sort(tbMapList,
                new Comparator<Entry<String, List<String>>>() {

                    @Override
                    public int compare(Entry<String, List<String>> o1,
                                       Entry<String, List<String>> o2) {
                        Integer hdid1 = hdref.get(o1.getKey());
                        Integer hdid2 = hdref.get(o2.getKey());
                        if (hdid1 != null && hdid2 != null) {
                            return hdid1 - hdid2;
                        } else if (hdid1 != null && hdid2 == null) {
                            return -1;
                        } else if (hdid1 == null && hdid2 != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

        BaseResult result = new BaseResultOK();
        result.addToMap("data", tbMapList);
        result.addToMap("columns", columns);
        result.addToMap("time",
                DateTimeUtil.getTimeFmt(new Date(), "yyyy年MM月dd日"));
        result.addToMap("xzqhname", xzqhdm.getName());
        return result;
    }

    public BaseResult queryHdHb(Integer xzqh) {
        Map<String, List<String>> tbMap = new HashMap<String, List<String>>();
        // 用于获得所有的列标题
        List<String> columns = new ArrayList<String>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        xzqhdm.setId(xzqh);

        // 添加一个行政区划排序参照map
        final Map<String, Integer> hdref = new HashMap<>();

        try {
            // 查找所有xzqh及对应的子行政区划列表
            xzqhdm = this.xzqhdmService.findNode(xzqhdm);
            List<CZdXzqhdm> xzqhdms = this.xzqhdmService
                    .findChildrenNodes_r(xzqhdm);
            /**
             * 如果没有子行政区划，则默认查当前行政区划
             *
             * 如湖州市有长兴，安吉等，则查的是长兴安吉，如果输入的为长兴，则出来的就是长兴的
             */
            if (xzqhdms == null || xzqhdms.size() == 0) {
                xzqhdms.add(xzqhdm);
            }

            BaseQueryRecords<Object[]> ret = this.statisticsDao
                    .queryHdHbByBzfl(xzqhdms);

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String ytflname = (String) objs[4];
                if (ytflname != null && !IflistContain(columns, ytflname))
                    columns.add(ytflname);
            }

            // 添加小计总计
            columns.add("小计");
            List<String> zjline = new ArrayList<>();
            tbMap.put("总计", zjline);
            for (int j = 0; j < columns.size(); j++) {
                zjline.add(" ");
            }

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String cnt = objs[0].toString();
                String xzqhid = objs[1].toString();
                String xzqhname = (String) objs[2];
                String ytflname = (String) objs[4];

                hdref.put(xzqhname, Integer.parseInt(xzqhid));

                if (!tbMap.containsKey(xzqhname)) {
                    List<String> line = new ArrayList<>();
                    tbMap.put(xzqhname, line);
                    for (int j = 0; j < columns.size(); j++) {
                        line.add(" ");
                    }
                }
                List<String> line = tbMap.get(xzqhname);
                if (ytflname == null)
                    continue;
                int colindex = getlistItemIndex(columns, ytflname);
                line.set(colindex, cnt);

                // 计算总计
                int zjVal;
                String zjValStr = tbMap.get("总计").get(colindex).trim();
                if (zjValStr == null || zjValStr.equals("")) {
                    zjVal = 0;
                } else {
                    zjVal = Integer.parseInt(zjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    zjVal += Integer.parseInt(cnt);
                }
                if (zjVal != 0) {
                    tbMap.get("总计").set(colindex, "" + zjVal);
                }

                // 计算小计
                int xjVal;
                String xjValStr = line.get(line.size() - 1).trim();
                if (xjValStr == null || xjValStr.equals("")) {
                    xjVal = 0;
                } else {
                    xjVal = Integer.parseInt(xjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    xjVal += Integer.parseInt(cnt);
                }
                if (xjVal != 0) {
                    line.set(line.size() - 1, "" + xjVal);
                }
            }

            // 计算小计的总计
            List<String> line = tbMap.get("总计");
            int value = 0;
            for (int i = 0; i < line.size(); i++) {
                String itemString = line.get(i).trim();
                if (i != line.size() - 1) {
                    if (itemString != null && itemString.length() > 0
                            && !itemString.equals("")) {
                        value += Integer.parseInt(itemString);
                    }
                } else {
                    if (value != 0)
                        line.set(i, "" + value);
                }
            }
        } catch (Exception e) {

        }

        // 排序tbmap
        List<Entry<String, List<String>>> tbMapList = new ArrayList<>(
                tbMap.entrySet());
        Collections.sort(tbMapList,
                new Comparator<Entry<String, List<String>>>() {

                    @Override
                    public int compare(Entry<String, List<String>> o1,
                                       Entry<String, List<String>> o2) {
                        Integer hdid1 = hdref.get(o1.getKey());
                        Integer hdid2 = hdref.get(o2.getKey());
                        if (hdid1 != null && hdid2 != null) {
                            return hdid1 - hdid2;
                        } else if (hdid1 != null && hdid2 == null) {
                            return -1;
                        } else if (hdid1 == null && hdid2 != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

        BaseResult result = new BaseResultOK();
        result.addToMap("data", tbMapList);
        result.addToMap("columns", columns);
        result.addToMap("time",
                DateTimeUtil.getTimeFmt(new Date(), "yyyy年MM月dd日"));
        result.addToMap("xzqhname", xzqhdm.getName());
        return result;
    }

    public BaseResult queryHdMt(Integer xzqh) {
        Map<String, List<String>> tbMap = new HashMap<String, List<String>>();
        // 用于获得所有的列标题
        List<String> columns = new ArrayList<String>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        xzqhdm.setId(xzqh);

        // 添加一个行政区划排序参照map
        final Map<String, Integer> hdref = new HashMap<>();

        try {
            // 查找所有xzqh及对应的子行政区划列表
            xzqhdm = this.xzqhdmService.findNode(xzqhdm);
            List<CZdXzqhdm> xzqhdms = this.xzqhdmService
                    .findChildrenNodes_r(xzqhdm);
            /**
             * 如果没有子行政区划，则默认查当前行政区划
             *
             * 如湖州市有长兴，安吉等，则查的是长兴安吉，如果输入的为长兴，则出来的就是长兴的
             */
            if (xzqhdms == null || xzqhdms.size() == 0) {
                xzqhdms.add(xzqhdm);
            }

            BaseQueryRecords<Object[]> ret = this.statisticsDao
                    .queryHdMtByMtlx(xzqhdms);

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String ytflname = (String) objs[2];
                if (ytflname != null && !IflistContain(columns, ytflname))
                    columns.add(ytflname);
            }

            // 添加小计总计
            columns.add("小计");
            List<String> zjline = new ArrayList<>();
            tbMap.put("总计", zjline);
            for (int j = 0; j < columns.size(); j++) {
                zjline.add(" ");
            }

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String cnt = objs[0].toString();
                String xzqhid = objs[3].toString();
                String xzqhname = (String) objs[1];
                String ytflname = (String) objs[2];

                hdref.put(xzqhname, Integer.parseInt(xzqhid));

                if (!tbMap.containsKey(xzqhname)) {
                    List<String> line = new ArrayList<>();
                    tbMap.put(xzqhname, line);
                    for (int j = 0; j < columns.size(); j++) {
                        line.add(" ");
                    }
                }
                List<String> line = tbMap.get(xzqhname);
                if (ytflname == null)
                    continue;
                int colindex = getlistItemIndex(columns, ytflname);
                line.set(colindex, cnt);

                // 计算总计
                int zjVal;
                String zjValStr = tbMap.get("总计").get(colindex).trim();
                if (zjValStr == null || zjValStr.equals("")) {
                    zjVal = 0;
                } else {
                    zjVal = Integer.parseInt(zjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    zjVal += Integer.parseInt(cnt);
                }
                if (zjVal != 0) {
                    tbMap.get("总计").set(colindex, "" + zjVal);
                }

                // 计算小计
                int xjVal;
                String xjValStr = line.get(line.size() - 1).trim();
                if (xjValStr == null || xjValStr.equals("")) {
                    xjVal = 0;
                } else {
                    xjVal = Integer.parseInt(xjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    xjVal += Integer.parseInt(cnt);
                }
                if (xjVal != 0) {
                    line.set(line.size() - 1, "" + xjVal);
                }
            }

            // 计算小计的总计
            List<String> line = tbMap.get("总计");
            int value = 0;
            for (int i = 0; i < line.size(); i++) {
                String itemString = line.get(i).trim();
                if (i != line.size() - 1) {
                    if (itemString != null && itemString.length() > 0
                            && !itemString.equals("")) {
                        value += Integer.parseInt(itemString);
                    }
                } else {
                    if (value != 0)
                        line.set(i, "" + value);
                }
            }
        } catch (Exception e) {

        }
        // 排序tbmap
        List<Entry<String, List<String>>> tbMapList = new ArrayList<>(
                tbMap.entrySet());
        Collections.sort(tbMapList,
                new Comparator<Entry<String, List<String>>>() {

                    @Override
                    public int compare(Entry<String, List<String>> o1,
                                       Entry<String, List<String>> o2) {
                        Integer xzqhid1 = hdref.get(o1.getKey());
                        Integer xzqhid2 = hdref.get(o2.getKey());
                        if (xzqhid1 != null && xzqhid2 != null) {
                            return xzqhid1 - xzqhid2;
                        } else if (xzqhid1 != null && xzqhid2 == null) {
                            return -1;
                        } else if (xzqhid1 == null && xzqhid2 != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

        BaseResult result = new BaseResultOK();
        result.addToMap("data", tbMapList);
        result.addToMap("columns", columns);
        result.addToMap("time",
                DateTimeUtil.getTimeFmt(new Date(), "yyyy年MM月dd日"));
        result.addToMap("xzqhname", xzqhdm.getName());
        return result;
    }

    public BaseResult queryHdmcQl(String ids, Integer xzqh) {
        Map<String, List<String>> tbMap = new HashMap<String, List<String>>();
        // 用于获得所有的列标题
        List<String> columns = new ArrayList<String>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        xzqhdm.setId(xzqh);

        // 添加一个行政区划排序参照map
        final Map<String, Integer> hdref = new HashMap<>();

        try {
            // 查找所有xzqh及对应的子行政区划列表
            xzqhdm = this.xzqhdmService.findNode(xzqhdm);
            List<CZdXzqhdm> xzqhdms = this.xzqhdmService
                    .findChildrenNodes_r(xzqhdm);
            /**
             * 如果没有子行政区划，则默认查当前行政区划
             *
             * 如湖州市有长兴，安吉等，则查的是长兴安吉，如果输入的为长兴，则出来的就是长兴的
             */
            if (xzqhdms == null || xzqhdms.size() == 0) {
                xzqhdms.add(xzqhdm);
            }
            BaseQueryRecords<Object[]> ret = this.statisticsDao
                    .queryHdQlByYtfl(ids.toString(), xzqhdms);

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String ytflname = (String) objs[4];
                if (ytflname != null && !IflistContain(columns, ytflname))
                    columns.add(ytflname);
            }
            // 添加小计总计
            columns.add("小计");
            List<String> zjline = new ArrayList<>();
            tbMap.put("总计", zjline);
            for (int j = 0; j < columns.size(); j++) {
                zjline.add(" ");
            }

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String cnt = objs[0].toString();
                String hdid = objs[1].toString();
                String hdname = (String) objs[2];
                String ytflname = (String) objs[4];

                hdref.put(hdname, Integer.parseInt(hdid));

                if (!tbMap.containsKey(hdname)) {
                    List<String> line = new ArrayList<>();
                    tbMap.put(hdname, line);
                    for (int j = 0; j < columns.size(); j++) {
                        line.add(" ");
                    }
                }
                List<String> line = tbMap.get(hdname);
                if (ytflname == null)
                    continue;
                int colindex = getlistItemIndex(columns, ytflname);
                line.set(colindex, cnt);

                // 计算总计
                int zjVal;
                String zjValStr = tbMap.get("总计").get(colindex).trim();
                if (zjValStr == null || zjValStr.equals("")) {
                    zjVal = 0;
                } else {
                    zjVal = Integer.parseInt(zjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    zjVal += Integer.parseInt(cnt);
                }
                if (zjVal != 0) {
                    tbMap.get("总计").set(colindex, "" + zjVal);
                }

                // 计算小计
                int xjVal;
                String xjValStr = line.get(line.size() - 1).trim();
                if (xjValStr == null || xjValStr.equals("")) {
                    xjVal = 0;
                } else {
                    xjVal = Integer.parseInt(xjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    xjVal += Integer.parseInt(cnt);
                }
                if (xjVal != 0) {
                    line.set(line.size() - 1, "" + xjVal);
                }
            }

            // 计算小计的总计
            List<String> line = tbMap.get("总计");
            int value = 0;
            for (int i = 0; i < line.size(); i++) {
                String itemString = line.get(i).trim();
                if (i != line.size() - 1) {
                    if (itemString != null && itemString.length() > 0
                            && !itemString.equals("")) {
                        value += Integer.parseInt(itemString);
                    }
                } else {
                    if (value != 0)
                        line.set(i, "" + value);
                }
            }

        } catch (Exception e) {

        }

        // 排序tbmap
        List<Entry<String, List<String>>> tbMapList = new ArrayList<>(
                tbMap.entrySet());
        Collections.sort(tbMapList,
                new Comparator<Entry<String, List<String>>>() {

                    @Override
                    public int compare(Entry<String, List<String>> o1,
                                       Entry<String, List<String>> o2) {
                        Integer hdid1 = hdref.get(o1.getKey());
                        Integer hdid2 = hdref.get(o2.getKey());
                        if (hdid1 != null && hdid2 != null) {
                            return hdid1 - hdid2;
                        } else if (hdid1 != null && hdid2 == null) {
                            return -1;
                        } else if (hdid1 == null && hdid2 != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

        BaseResult result = new BaseResultOK();
        result.addToMap("data", tbMapList);
        result.addToMap("columns", columns);
        result.addToMap("time",
                DateTimeUtil.getTimeFmt(new Date(), "yyyy年MM月dd日"));
        result.addToMap("xzqhname", xzqhdm.getName());
        return result;
    }

    public BaseResult queryHdmcGx(String ids, Integer xzqh) {
        Map<String, List<String>> tbMap = new HashMap<String, List<String>>();
        // 用于获得所有的列标题
        List<String> columns = new ArrayList<String>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        xzqhdm.setId(xzqh);

        // 添加一个行政区划排序参照map
        final Map<String, Integer> hdref = new HashMap<>();

        try {
            // 查找所有xzqh及对应的子行政区划列表
            xzqhdm = this.xzqhdmService.findNode(xzqhdm);
            List<CZdXzqhdm> xzqhdms = this.xzqhdmService
                    .findChildrenNodes_r(xzqhdm);

            /**
             * 如果没有子行政区划，则默认查当前行政区划
             *
             * 如湖州市有长兴，安吉等，则查的是长兴安吉，如果输入的为长兴，则出来的就是长兴的
             */
            if (xzqhdms == null || xzqhdms.size() == 0) {
                xzqhdms.add(xzqhdm);
            }

            BaseQueryRecords<Object[]> ret = this.statisticsDao
                    .queryHdGxByGxzl(ids.toString(), xzqhdms);

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String ytflname = (String) objs[4];
                if (ytflname != null && !IflistContain(columns, ytflname))
                    columns.add(ytflname);
            }

            // 添加小计总计
            columns.add("小计");
            List<String> zjline = new ArrayList<>();
            tbMap.put("总计", zjline);
            for (int j = 0; j < columns.size(); j++) {
                zjline.add(" ");
            }

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String cnt = objs[0].toString();
                String xzqhid = objs[1].toString();
                String xzqhname = (String) objs[2];
                String ytflname = (String) objs[4];

                hdref.put(xzqhname, Integer.parseInt(xzqhid));

                if (!tbMap.containsKey(xzqhname)) {
                    List<String> line = new ArrayList<>();
                    tbMap.put(xzqhname, line);
                    for (int j = 0; j < columns.size(); j++) {
                        line.add(" ");
                    }
                }
                List<String> line = tbMap.get(xzqhname);
                if (ytflname == null)
                    continue;
                int colindex = getlistItemIndex(columns, ytflname);
                line.set(colindex, cnt);

                // 计算总计
                int zjVal;
                String zjValStr = tbMap.get("总计").get(colindex).trim();
                if (zjValStr == null || zjValStr.equals("")) {
                    zjVal = 0;
                } else {
                    zjVal = Integer.parseInt(zjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    zjVal += Integer.parseInt(cnt);
                }
                if (zjVal != 0) {
                    tbMap.get("总计").set(colindex, "" + zjVal);
                }

                // 计算小计
                int xjVal;
                String xjValStr = line.get(line.size() - 1).trim();
                if (xjValStr == null || xjValStr.equals("")) {
                    xjVal = 0;
                } else {
                    xjVal = Integer.parseInt(xjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    xjVal += Integer.parseInt(cnt);
                }
                if (xjVal != 0) {
                    line.set(line.size() - 1, "" + xjVal);
                }
            }

            // 计算小计的总计
            List<String> line = tbMap.get("总计");
            int value = 0;
            for (int i = 0; i < line.size(); i++) {
                String itemString = line.get(i).trim();
                if (i != line.size() - 1) {
                    if (itemString != null && itemString.length() > 0
                            && !itemString.equals("")) {
                        value += Integer.parseInt(itemString);
                    }
                } else {
                    if (value != 0)
                        line.set(i, "" + value);
                }
            }
        } catch (Exception e) {

        }
        // 排序tbmap
        List<Entry<String, List<String>>> tbMapList = new ArrayList<>(
                tbMap.entrySet());
        Collections.sort(tbMapList,
                new Comparator<Entry<String, List<String>>>() {

                    @Override
                    public int compare(Entry<String, List<String>> o1,
                                       Entry<String, List<String>> o2) {
                        Integer hdid1 = hdref.get(o1.getKey());
                        Integer hdid2 = hdref.get(o2.getKey());
                        if (hdid1 != null && hdid2 != null) {
                            return hdid1 - hdid2;
                        } else if (hdid1 != null && hdid2 == null) {
                            return -1;
                        } else if (hdid1 == null && hdid2 != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

        BaseResult result = new BaseResultOK();
        result.addToMap("data", tbMapList);
        result.addToMap("columns", columns);
        result.addToMap("time",
                DateTimeUtil.getTimeFmt(new Date(), "yyyy年MM月dd日"));
        result.addToMap("xzqhname", xzqhdm.getName());
        return result;
    }

    public BaseResult queryHdmcHb(String ids, Integer xzqh) {
        Map<String, List<String>> tbMap = new HashMap<String, List<String>>();
        // 用于获得所有的列标题
        List<String> columns = new ArrayList<String>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        xzqhdm.setId(xzqh);

        // 添加一个行政区划排序参照map
        final Map<String, Integer> hdref = new HashMap<>();

        try {
            // 查找所有xzqh及对应的子行政区划列表
            xzqhdm = this.xzqhdmService.findNode(xzqhdm);
            List<CZdXzqhdm> xzqhdms = this.xzqhdmService
                    .findChildrenNodes_r(xzqhdm);
            /**
             * 如果没有子行政区划，则默认查当前行政区划
             *
             * 如湖州市有长兴，安吉等，则查的是长兴安吉，如果输入的为长兴，则出来的就是长兴的
             */
            if (xzqhdms == null || xzqhdms.size() == 0) {
                xzqhdms.add(xzqhdm);
            }

            BaseQueryRecords<Object[]> ret = this.statisticsDao
                    .queryHdHbByBzfl(ids.toString(), xzqhdms);

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String ytflname = (String) objs[4];
                if (ytflname != null && !IflistContain(columns, ytflname))
                    columns.add(ytflname);
            }

            // 添加小计总计
            columns.add("小计");
            List<String> zjline = new ArrayList<>();
            tbMap.put("总计", zjline);
            for (int j = 0; j < columns.size(); j++) {
                zjline.add(" ");
            }

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String cnt = objs[0].toString();
                String xzqhid = objs[1].toString();
                String xzqhname = (String) objs[2];
                String ytflname = (String) objs[4];

                hdref.put(xzqhname, Integer.parseInt(xzqhid));

                if (!tbMap.containsKey(xzqhname)) {
                    List<String> line = new ArrayList<>();
                    tbMap.put(xzqhname, line);
                    for (int j = 0; j < columns.size(); j++) {
                        line.add(" ");
                    }
                }
                List<String> line = tbMap.get(xzqhname);
                if (ytflname == null)
                    continue;
                int colindex = getlistItemIndex(columns, ytflname);
                line.set(colindex, cnt);

                // 计算总计
                int zjVal;
                String zjValStr = tbMap.get("总计").get(colindex).trim();
                if (zjValStr == null || zjValStr.equals("")) {
                    zjVal = 0;
                } else {
                    zjVal = Integer.parseInt(zjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    zjVal += Integer.parseInt(cnt);
                }
                if (zjVal != 0) {
                    tbMap.get("总计").set(colindex, "" + zjVal);
                }

                // 计算小计
                int xjVal;
                String xjValStr = line.get(line.size() - 1).trim();
                if (xjValStr == null || xjValStr.equals("")) {
                    xjVal = 0;
                } else {
                    xjVal = Integer.parseInt(xjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    xjVal += Integer.parseInt(cnt);
                }
                if (xjVal != 0) {
                    line.set(line.size() - 1, "" + xjVal);
                }
            }

            // 计算小计的总计
            List<String> line = tbMap.get("总计");
            int value = 0;
            for (int i = 0; i < line.size(); i++) {
                String itemString = line.get(i).trim();
                if (i != line.size() - 1) {
                    if (itemString != null && itemString.length() > 0
                            && !itemString.equals("")) {
                        value += Integer.parseInt(itemString);
                    }
                } else {
                    if (value != 0)
                        line.set(i, "" + value);
                }
            }
        } catch (Exception e) {

        }

        // 排序tbmap
        List<Entry<String, List<String>>> tbMapList = new ArrayList<>(
                tbMap.entrySet());
        Collections.sort(tbMapList,
                new Comparator<Entry<String, List<String>>>() {

                    @Override
                    public int compare(Entry<String, List<String>> o1,
                                       Entry<String, List<String>> o2) {
                        Integer hdid1 = hdref.get(o1.getKey());
                        Integer hdid2 = hdref.get(o2.getKey());
                        if (hdid1 != null && hdid2 != null) {
                            return hdid1 - hdid2;
                        } else if (hdid1 != null && hdid2 == null) {
                            return -1;
                        } else if (hdid1 == null && hdid2 != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

        BaseResult result = new BaseResultOK();
        result.addToMap("data", tbMapList);
        result.addToMap("columns", columns);
        result.addToMap("time",
                DateTimeUtil.getTimeFmt(new Date(), "yyyy年MM月dd日"));
        result.addToMap("xzqhname", xzqhdm.getName());
        return result;
    }

    public BaseResult queryHdmcMt(String ids, Integer xzqh) {
        Map<String, List<String>> tbMap = new HashMap<String, List<String>>();
        // 用于获得所有的列标题
        List<String> columns = new ArrayList<String>();
        CZdXzqhdm xzqhdm = new CZdXzqhdm();
        xzqhdm.setId(xzqh);

        // 添加一个行政区划排序参照map
        final Map<String, Integer> hdref = new HashMap<>();

        try {
            // 查找所有xzqh及对应的子行政区划列表
            xzqhdm = this.xzqhdmService.findNode(xzqhdm);
            List<CZdXzqhdm> xzqhdms = this.xzqhdmService
                    .findChildrenNodes_r(xzqhdm);
            /**
             * 如果没有子行政区划，则默认查当前行政区划
             *
             * 如湖州市有长兴，安吉等，则查的是长兴安吉，如果输入的为长兴，则出来的就是长兴的
             */
            if (xzqhdms == null || xzqhdms.size() == 0) {
                xzqhdms.add(xzqhdm);
            }

            BaseQueryRecords<Object[]> ret = this.statisticsDao
                    .queryHdMtByMtlx(ids.toString(), xzqhdms);

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String ytflname = (String) objs[2];
                if (ytflname != null && !IflistContain(columns, ytflname))
                    columns.add(ytflname);
            }

            // 添加小计总计
            columns.add("小计");
            List<String> zjline = new ArrayList<>();
            tbMap.put("总计", zjline);
            for (int j = 0; j < columns.size(); j++) {
                zjline.add(" ");
            }

            for (int i = 0; i < ret.getData().size(); i++) {
                Object[] objs = ret.getData().get(i);
                String cnt = objs[0].toString();
                String xzqhid = objs[3].toString();
                String xzqhname = (String) objs[1];
                String ytflname = (String) objs[2];

                hdref.put(xzqhname, Integer.parseInt(xzqhid));

                if (!tbMap.containsKey(xzqhname)) {
                    List<String> line = new ArrayList<>();
                    tbMap.put(xzqhname, line);
                    for (int j = 0; j < columns.size(); j++) {
                        line.add(" ");
                    }
                }
                List<String> line = tbMap.get(xzqhname);
                if (ytflname == null)
                    continue;
                int colindex = getlistItemIndex(columns, ytflname);
                line.set(colindex, cnt);

                // 计算总计
                int zjVal;
                String zjValStr = tbMap.get("总计").get(colindex).trim();
                if (zjValStr == null || zjValStr.equals("")) {
                    zjVal = 0;
                } else {
                    zjVal = Integer.parseInt(zjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    zjVal += Integer.parseInt(cnt);
                }
                if (zjVal != 0) {
                    tbMap.get("总计").set(colindex, "" + zjVal);
                }

                // 计算小计
                int xjVal;
                String xjValStr = line.get(line.size() - 1).trim();
                if (xjValStr == null || xjValStr.equals("")) {
                    xjVal = 0;
                } else {
                    xjVal = Integer.parseInt(xjValStr);
                }
                if (cnt != null && cnt.length() > 0 && !cnt.equals("")) {
                    xjVal += Integer.parseInt(cnt);
                }
                if (xjVal != 0) {
                    line.set(line.size() - 1, "" + xjVal);
                }
            }

            // 计算小计的总计
            List<String> line = tbMap.get("总计");
            int value = 0;
            for (int i = 0; i < line.size(); i++) {
                String itemString = line.get(i).trim();
                if (i != line.size() - 1) {
                    if (itemString != null && itemString.length() > 0
                            && !itemString.equals("")) {
                        value += Integer.parseInt(itemString);
                    }
                } else {
                    if (value != 0)
                        line.set(i, "" + value);
                }
            }
        } catch (Exception e) {

        }
        // 排序tbmap
        List<Entry<String, List<String>>> tbMapList = new ArrayList<>(
                tbMap.entrySet());
        Collections.sort(tbMapList,
                new Comparator<Entry<String, List<String>>>() {

                    @Override
                    public int compare(Entry<String, List<String>> o1,
                                       Entry<String, List<String>> o2) {
                        Integer xzqhid1 = hdref.get(o1.getKey());
                        Integer xzqhid2 = hdref.get(o2.getKey());
                        if (xzqhid1 != null && xzqhid2 != null) {
                            return xzqhid1 - xzqhid2;
                        } else if (xzqhid1 != null && xzqhid2 == null) {
                            return -1;
                        } else if (xzqhid1 == null && xzqhid2 != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

        BaseResult result = new BaseResultOK();
        result.addToMap("data", tbMapList);
        result.addToMap("columns", columns);
        result.addToMap("time",
                DateTimeUtil.getTimeFmt(new Date(), "yyyy年MM月dd日"));
        result.addToMap("xzqhname", xzqhdm.getName());
        return result;
    }

    public BaseResult exportHdmcQl(String ids, int xzqh)
            throws Exception {
        return exportGhdmczyss(ids, xzqh, QL);
    }

    public BaseResult exportHdmcHb(String ids, int xzqh)
            throws Exception {
        return exportGhdmczyss(ids, xzqh, HB);
    }

    public BaseResult exportHdmcMt(String ids, int xzqh)
            throws Exception {
        return exportGhdmczyss(ids, xzqh, MT);
    }

    public BaseResult exportHdmcGx(String ids, int xzqh)
            throws Exception {
        return exportGhdmczyss(ids, xzqh, GX);
    }

    private BaseResult exportGhdmczyss(String ids, int xzqh, int type) throws IOException {
        BaseResult result = null;
        String templatedir = getContextPath() + "/template/";
        String baobiaodir = getContextPath() + "/baobiao/";
        String bbtitle = null;
        // 拷贝模板
        String baobiaofilename = null;
        switch (type) {
            case QL:
                bbtitle = "各航道桥梁按桥梁用途分类统计表(航道名称)";
                break;
            case HB:
                bbtitle = "各航道航标按标志类别分类统计表(航道名称)";
                break;
            case MT:
                bbtitle = "各航道码头按码头类型分类统计表(航道名称)";
                break;
            case GX:
                bbtitle = "各航道管线按管线种类分类统计表(航道名称)";
                break;
            default:
                return new BaseResultFailed();
        }
        baobiaofilename = FileUtils.writeToFile2(new File(templatedir
                + "/各航道主要设施分类汇总表(航道名称).xlsx"), baobiaodir + "/" + bbtitle + ".xlsx");

        if (baobiaofilename != null) {
            // 获取数据
            BaseResult dataResult = null;
            switch (type) {
                case QL:
                    dataResult = this.queryHdmcQl(ids, xzqh);
                    break;
                case HB:
                    dataResult = this.queryHdmcHb(ids, xzqh);
                    break;
                case MT:
                    dataResult = this.queryHdmcMt(ids, xzqh);
                    break;
                case GX:
                    dataResult = this.queryHdmcGx(ids, xzqh);
                    break;
                default:
                    return new BaseResultFailed();
            }
            if (dataResult.ifResultOK()) {
                @SuppressWarnings("unchecked")
                List<Entry<String, List<String>>> tbMapList = (List<Entry<String, List<String>>>) dataResult
                        .getFromMap("data");
                @SuppressWarnings("unchecked")
                List<String> columns = (List<String>) dataResult
                        .getFromMap("columns");
                String time = (String) dataResult.getFromMap("time");
                String xzqhname = (String) dataResult.getFromMap("xzqhname");

                // 初始化poi
                File outputFile = new File(baobiaodir + "/" + baobiaofilename);
                XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                        outputFile));
                XSSFCellStyle style = null;
                Font font = null;
                XSSFSheet sheet = workbook.getSheetAt(0);

                // 创建标题
                XSSFRow titlerow = getRow(sheet, 1);
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, columns
                        .size() + 1));
                XSSFCell titlecell = getCell(titlerow, 1);
                titlecell.setCellValue(xzqhname + bbtitle);

                //添加时间副标题
                XSSFRow subtitleRow = getRow(sheet, 2);
                sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, columns
                        .size() + 1));
                XSSFCell subtitleCell = getCell(subtitleRow, 1);
                subtitleCell.setCellValue(time);

                // 创建单位
                XSSFRow unittitleRow = getRow(sheet, 3);
                XSSFCell dwcell = getCell(unittitleRow, columns.size() + 1);
                style = dwcell.getCellStyle();
                style.setAlignment(HorizontalAlignment.RIGHT);
                dwcell.setCellStyle(style);
                switch (type) {
                    case QL:
                        dwcell.setCellValue("计量单位：座");
                        break;
                    case HB:
                        dwcell.setCellValue("计量单位：个");
                        break;
                    case MT:
                        dwcell.setCellValue("计量单位：座");
                        break;
                    case GX:
                        dwcell.setCellValue("计量单位：条");
                        break;
                    default:
                        return new BaseResultFailed();
                }

                // 设置表格头信息
                XSSFRow tableRow = getRow(sheet, 4);
                // 设置表头左一格
                XSSFCell tableTitleFirst = getCell(tableRow, 1);
                String flstr = null;
                switch (type) {
                    case QL:
                        flstr = "用途分类";
                        break;
                    case HB:
                        flstr = "标志类别";
                        break;
                    case MT:
                        flstr = "码头类型";
                        break;
                    case GX:
                        flstr = "管线种类";
                        break;
                    default:
                        return new BaseResultFailed();
                }
                String ss = tableTitleFirst.getStringCellValue().replace("分类",
                        flstr);

                style = tableTitleFirst.getCellStyle();
                style.setBorderTop(BorderStyle.MEDIUM);
                style.setBorderLeft(BorderStyle.MEDIUM);
                style.setBorderRight(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                tableTitleFirst.setCellStyle(style);
                tableTitleFirst.setCellValue(ss);
                // 设置表头右几格
                for (int i = 0; i < columns.size(); i++) {
                    XSSFCell tableCell = getCell(tableRow, i + 2);

                    style = workbook.createCellStyle();
                    style.setBorderTop(BorderStyle.MEDIUM);
                    style.setBorderBottom(BorderStyle.THIN);
                    if (i == columns.size() - 1)
                        style.setBorderRight(BorderStyle.MEDIUM);
                    else
                        style.setBorderRight(BorderStyle.THIN);
                    style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                    style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
                    tableCell.setCellStyle(style);
                    tableCell.setCellValue(columns.get(i));
                }

                // 设置每一行
                for (int i = 0; i < tbMapList.size(); i++) {
                    Entry<String, List<String>> line = tbMapList.get(i);
                    XSSFRow row = getRow(sheet, i + 5);
                    XSSFCell rowTitle = getCell(row, 1);

                    style = workbook.createCellStyle();
                    style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                    style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
                    style.setBorderLeft(BorderStyle.MEDIUM);
                    style.setBorderRight(BorderStyle.THIN);
                    if (i == tbMapList.size() - 1)
                        style.setBorderBottom(BorderStyle.MEDIUM);
                    else
                        style.setBorderBottom(BorderStyle.THIN);
                    rowTitle.setCellStyle(style);
                    rowTitle.setCellValue(line.getKey());

                    for (int j = 0; j < line.getValue().size(); j++) {
                        XSSFCell rowTd = getCell(row, j + 2);
                        String val = line.getValue().get(j).trim();
                        if (val != null && !val.equals("")) {
                            rowTd.setCellValue(Integer.parseInt(val));
                        }

                        if (i == tbMapList.size() - 1) {
                            style = workbook.createCellStyle();
                            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
                            style.setBorderBottom(BorderStyle.MEDIUM);
                            style.setBorderLeft(BorderStyle.THIN);
                            if (j == line.getValue().size() - 1)
                                style.setBorderRight(BorderStyle.MEDIUM);
                            else
                                style.setBorderRight(BorderStyle.THIN);
                            rowTd.setCellStyle(style);
                        } else {
                            style = workbook.createCellStyle();
                            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
                            if (j == line.getValue().size() - 1) {
                                style.setBorderRight(BorderStyle.MEDIUM);
                                style.setBorderBottom(BorderStyle.THIN);
                                rowTd.setCellStyle(style);
                            } else {
                                style.setBorderBottom(BorderStyle.THIN);
                                style.setBorderRight(BorderStyle.THIN);
                                rowTd.setCellStyle(style);
                            }
                        }
                    }
                }

                FileOutputStream outputStream = new FileOutputStream(outputFile);
                workbook.write(outputStream);
                outputStream.close();
                workbook.close();
            }
        }
        logService.addLog(ModuleName.TJBB_SD, OpName.EXPORT, bbtitle);
        result = new BaseResultOK();
        result.addToMap("filename", baobiaofilename);
        result.addToMap("filepath", baobiaodir + "/" + baobiaofilename);
        return result;
    }

    public BaseResult exportHdsyn(int fswlx, int xzqh) throws ClassNotFoundException, IOException {
        BaseResult result = new BaseResult();
        String templatedir = getContextPath() + "/template/";
        String baobiaodir = getContextPath() + "/baobiao/";
        String bbtitle = "航道信息";
        // 拷贝模板
        String baobiaofilename = null;
        baobiaofilename = FileUtils.writeToFile2(new File(templatedir
                + "/航道信息.txt"), baobiaodir + "/" + bbtitle + ".txt");
        fswlx = 1;
        Class c = Class.forName("com.channel.model.hd.CHdHdaojcxx");
        //获取所有的属性?
        Field[] fs = c.getDeclaredFields();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fs.length; i++) {
            Field field = fs[i];
            String vname = new PropDescFields(c).getPropDesc(field.getName().toString());
            if (!"".equals(vname) && !"创建时间".equals(vname) && !"更新时间".equals(vname)) {
                if (i == fs.length - 1) {
                    sb.append(vname);
                } else {
                    sb.append(vname + ",");
                }
            }
        }
        File file = new File(templatedir + "/航道信息.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter fw = null;
        fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8")); // 指定编码格式，以免读取时中文字符异常
        fw.append(sb.toString());
        fw.newLine();
        BaseQueryRecords<CHdHdaojcxx> hdaos = this.hangDaoService.queryHangDao(-1, xzqh);
        List<CHdHdaojcxx> list = hdaos.getData();
        int len = list.size();
        for (CHdHdaojcxx hd : list) {
            if (hd != null) {
                String s = StringUtil.getStringIsNull(hd.getHdbh()) + "," + StringUtil.getStringIsNull(hd.getHdmc()) + "," + StringUtil.getStringIsNull(hd.getSfqshd()) + "," + StringUtil.getStringIsNull(hd.getSssjbh()) + "," + StringUtil.getStringIsNull(hd.getSfjjx()) + "," + StringUtil.getStringIsNull(hd.getSfgg()) + "," + StringUtil.getStringIsNull(hd.getBz());
                fw.append(s);
                fw.newLine();
            }
        }
        fw.flush(); // 全部写入缓存中的内容
        fw.close();
        result.addToMap("filename", baobiaofilename);
        result.addToMap("filepath", baobiaodir + "/" + baobiaofilename);
        return result;
    }
}
