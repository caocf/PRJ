package com.channel.statistics;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.channel.bean.Constants;
import com.channel.bean.TableCell;
import com.channel.dic.service.XzqhdmService;
import com.channel.model.user.CXtDpt;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

public class StatisticsAction extends BaseAction {
    private Integer xzqh = -1;
    private Integer dptid;
    private Integer dptflag;
    private Date starttime;
    private Date endtime;
    private Integer gldw;
    private Integer flag;// 标志位
    private String filename;// 文件名
    private long dlFilelength; // 下载文件长度
    private InputStream dlFile; // 下载文件流
    private String dlFileName; // 下载文件名
    private BaseResult result;
    private String ids;
    private static final long serialVersionUID = -4561016447982566253L;

    @Resource(name = "StatisticsService")
    private StatisticsService statisticsService;


    public int initXzqh(int xzqh) {
        int ret = 2;
        if (xzqh < 1) {
            CXtDpt dept = (CXtDpt) session.get("dpt");
            if (dept != null && dept.getXzqh() > 0) {
                ret = dept.getXzqh();
            }
        } else {
            ret = xzqh;
        }
        return ret;
    }

    /**
     * 法定报表-骨干航道养护报表
     */
    public String queryGgTable() {
        result = this.statisticsService.queryGgTable(starttime,
                endtime, flag, dptid, dptflag);
        if (flag == Constants.STATISTIC_MONTH) {
            session.put("gg", result.getObj());
            session.put("mapinfo", result.getMap());
            return "yuebao";
        }
        if (flag == Constants.STATISTIC_SEASON) {
            session.put("gg", result.getObj());
            session.put("mapinfo", result.getMap());
            return "jibao";
        }
        return null;
    }

    /**
     * 导出法定报表-骨干航道养护报表
     */
    public String exportGgTable() throws Exception {
        result = this.statisticsService.exportGgTable(starttime,
                endtime, flag, dptid, dptflag);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 法定报表-全区其它-支线养护报表
     */
    public String queryZxTable() {
        result = this.statisticsService.queryZxTable(starttime,
                endtime, flag, dptid, dptflag);
        if (flag == Constants.STATISTIC_MONTH) {
            session.put("zx", result.getObj());
            session.put("mapinfo", result.getMap());
            return "yuebao";
        }
        if (flag == Constants.STATISTIC_SEASON) {
            session.put("zx", result.getObj());
            session.put("mapinfo", result.getMap());
            return "jibao";
        }
        return null;
    }

    /**
     * 导出法定报表-全区其它-支线养护报表
     */
    public String exportZxTable() throws Exception {
        result = this.statisticsService.exportZxTable(starttime,
                endtime, flag, dptid, dptflag);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 法定报表-专项工程报表
     */
    public String queryZxgcTable() {
        result = this.statisticsService.queryZxgcTable(gldw, starttime,
                endtime, flag, dptflag);
        if (flag == Constants.STATISTIC_MONTH) {
            session.put("zxgc", result.getObj());
            session.put("mapinfo", result.getMap());
            return "yuebao";
        }
        if (flag == Constants.STATISTIC_SEASON) {
            session.put("zxgc", result.getObj());
            session.put("mapinfo", result.getMap());
            return "jibao";
        }
        return null;
    }

    /**
     * 导出法定报表-专项工程报表
     */
    public String exportZxgcTable() throws Exception {
        result = this.statisticsService.exportZxgcTable(gldw, starttime,
                endtime, flag, dptflag);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 法定报表 -交水运表61
     */
    public String queryJsy61Table() throws Exception {
        result = this.statisticsService.queryJsy61Table();
        session.put("data", result.getObj());
        return "success";
    }

    /**
     * 导出法定报表 -交水运表61
     */
    public String exportJsy61Table() throws Exception {
        result = this.statisticsService.exportJsy61Table();
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 法定报表 -交水运表62
     */
    public String queryJsy62Table() throws Exception {
        result = this.statisticsService.queryJsy62Table();
        session.put("data", result.getObj());
        return "success";
    }

    /**
     * 导出法定报表 -交水运表62
     */
    public String exportJsy62Table() throws Exception {
        result = this.statisticsService.exportJsy62Table();
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 法定报表 -交水运表63
     */
    public String queryJsy63Table() throws Exception {
        result = this.statisticsService.queryJsy63Table();
        session.put("data", result.getObj());
        return "success";
    }

    /**
     * 导出法定报表 -交水运表63
     */
    public String exportJsy63Table() throws Exception {
        result = this.statisticsService.exportJsy63Table();
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 法定报表 -交水运表65
     */
    public String queryJsy65Table() throws Exception {
        result = this.statisticsService.queryJsy65Table();
        session.put("data", result.getObj());
        return "success";
    }

    /**
     * 导出法定报表 -交水运表65
     */
    public String exportJsy65Table() throws Exception {
        result = this.statisticsService.exportJsy65Table();
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 法定报表 -交水运表671
     */
    public String queryJsy671Table() throws Exception {
        result = this.statisticsService.queryJsy671Table();
        session.put("data", result.getObj());
        return "success";
    }

    /**
     * 导出法定报表 -交水运表671
     */
    public String exportJsy671Table() throws Exception {
        result = this.statisticsService.exportJsy671Table();
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 设定报表-航道主要设施汇总表
     */
    public String querySsTable() {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.querySsTable(xzqh, starttime);
        session.put("ss", result.getObj());
        session.put("mapinfo", result.getMap());
        return "success";

    }

    /**
     * 导出设定报表-航道主要设施汇总表
     */
    public String exportSsTable() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportSsTable(xzqh, starttime);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 设定报表-航道现状等级汇总表
     */
    public String queryLcTable() {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.queryLcTable(xzqh, starttime);
        session.put("lc", result.getObj());
        session.put("mapinfo", result.getMap());
        return "success";

    }

    /**
     * 导出设定报表-航道现状等级汇总表
     */
    public String exportLcTable() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportLcTable(xzqh, starttime);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 设定报表-航道沿线主要设施分类汇总表-桥梁
     */
    public String queryQlTable() {
        xzqh = initXzqh(xzqh);
        BaseResult ret = this.statisticsService.queryQlTable(xzqh);
        setSessionParam("ret", ret);
        return "baobiao";
    }

    /**
     * 设定报表-各航道主要设施分类汇总表-桥梁
     */
    public String queryHdQl() {
        xzqh = initXzqh(xzqh);
        BaseResult ret = this.statisticsService.queryHdQl(xzqh);
        setSessionParam("ret", ret);
        return "baobiao";
    }

    /**
     * 导出设定报表-航道沿线主要设施分类汇总表-桥梁
     */
    public String exportHdyxssflQlTable() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportHdyxssflQlTable(xzqh);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 导出设定报表-航道沿线主要设施分类汇总表-桥梁
     */
    public String exportHdQl() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportHdQl(xzqh);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 设定报表 航道沿线主要设施分类汇总表-航标
     */
    public String queryHbTable() {
        xzqh = initXzqh(xzqh);
        BaseResult ret = this.statisticsService.queryHbTable(xzqh);
        setSessionParam("ret", ret);
        return "baobiao";
    }

    /**
     * 设定报表 航道沿线主要设施分类汇总表-航标
     */
    public String queryHdHb() {
        xzqh = initXzqh(xzqh);
        BaseResult ret = this.statisticsService.queryHdHb(xzqh);
        setSessionParam("ret", ret);
        return "baobiao";
    }

    /**
     * 导出设定报表 航道沿线主要设施分类汇总表-航标
     */
    public String exportHdyxssflHbTable() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportHdyxssflHbTable(xzqh);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 导出设定报表 航道沿线主要设施分类汇总表-航标
     */
    public String exportHdHb() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportHdHb(xzqh);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 设定报表 航道沿线主要设施分类汇总表-码头
     */
    public String queryMtTable() {
        xzqh = initXzqh(xzqh);
        BaseResult ret = this.statisticsService.queryMtTable(xzqh);
        setSessionParam("ret", ret);
        return "baobiao";
    }

    /**
     * 设定报表 航道沿线主要设施分类汇总表-码头
     */
    public String queryHdMt() {
        xzqh = initXzqh(xzqh);
        BaseResult ret = this.statisticsService.queryHdMt(xzqh);
        setSessionParam("ret", ret);
        return "baobiao";
    }


    /**
     * 导出设定报表 航道沿线主要设施分类汇总表-码头
     */
    public String exportHdyxssflMtTable() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportHdyxssflMtTable(xzqh);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }


    /**
     * 导出设定报表 航道沿线主要设施分类汇总表-码头
     */
    public String exportHdMt() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportHdMt(xzqh);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 设定报表 航道沿线主要设施分类汇总表-管线
     */
    public String queryGxTable() {
        xzqh = initXzqh(xzqh);
        BaseResult ret = this.statisticsService.queryGxTable(xzqh);
        setSessionParam("ret", ret);
        return "baobiao";
    }

    /**
     * 设定报表 航道沿线主要设施分类汇总表-管线
     */
    public String queryHdGx() {
        xzqh = initXzqh(xzqh);
        BaseResult ret = this.statisticsService.queryHdGx(xzqh);
        setSessionParam("ret", ret);
        return "baobiao";
    }

    /**
     * 导出设定报表 航道沿线主要设施分类汇总表-码头
     */
    public String exportHdyxssflGxTable() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportHdyxssflGxTable(xzqh);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 导出设定报表 航道沿线主要设施分类汇总表-码头
     */
    public String exportHdGx() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportHdyxssflGxTable(xzqh);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 设定报表-航道里程分类统计表
     */
    public String queryLcflTable() {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.queryLcflTable(xzqh, starttime);
        session.put("lc", result.getObj());
        session.put("mapinfo", result.getMap());
        return "success";

    }

    /**
     * 导出设定报表-航道里程分类统计表
     */
    public String exportLcflTable() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportLcflTable(xzqh, starttime);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 设定报表-各航道主要设施分类汇总表-桥梁
     */
    public String queryHdmcQl() {
        xzqh = initXzqh(xzqh);
        BaseResult ret = this.statisticsService.queryHdmcQl(ids, xzqh);
        setSessionParam("ret", ret);
        return "baobiao";
    }

    public String queryHdmcHb() {
        xzqh = initXzqh(xzqh);
        BaseResult ret = this.statisticsService.queryHdmcHb(ids, xzqh);
        setSessionParam("ret", ret);
        return "baobiao";
    }

    public String queryHdmcMt() {
        xzqh = initXzqh(xzqh);
        BaseResult ret = this.statisticsService.queryHdmcMt(ids, xzqh);
        setSessionParam("ret", ret);
        return "baobiao";
    }

    public String queryHdmcGx() {
        xzqh = initXzqh(xzqh);
        BaseResult ret = this.statisticsService.queryHdmcGx(ids, xzqh);
        setSessionParam("ret", ret);
        return "baobiao";
    }

    /**
     * 导出设定报表-航道沿线主要设施分类汇总表-桥梁
     */
    public String exportHdmcQl() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportHdmcQl(ids,xzqh);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }
    /**
     * 导出设定报表-航道沿线主要设施分类汇总表-桥梁
     */
    public String exportHdmcHb() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportHdmcHb(ids, xzqh);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }
    /**
     * 导出设定报表-航道沿线主要设施分类汇总表-桥梁
     */
    public String exportHdmcGx() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportHdmcGx(ids, xzqh);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }
    /**
     * 导出设定报表-航道沿线主要设施分类汇总表-桥梁
     */
    public String exportHdmcMt() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportHdmcMt(ids,xzqh);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    /**
     * 导出航道信息
     */
    public String exportHdsyn() throws Exception {
        xzqh = initXzqh(xzqh);
        result = this.statisticsService.exportHdsyn(flag,xzqh);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = (String) result.getFromMap("filepath"); // 绝对路径
            dlFile = new FileInputStream(filePath);
            dlFileName = new File(filePath).getName();
            dlFilelength = new File(filePath).length();
            dlFileName = filenameEncode(dlFileName); // 文件名编码,安卓端需要用urldecode进行解码
            return "file";
        } else {
            return "json";
        }
    }

    public Integer getXzqh() {
        return xzqh;
    }

    public void setXzqh(Integer xzqh) {
        this.xzqh = xzqh;
    }

    public Integer getDptid() {
        return dptid;
    }

    public void setDptid(Integer dptid) {
        this.dptid = dptid;
    }

    public Integer getDptflag() {
        return dptflag;
    }

    public void setDptflag(Integer dptflag) {
        this.dptflag = dptflag;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getGldw() {
        return gldw;
    }

    public void setGldw(Integer gldw) {
        this.gldw = gldw;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getDlFilelength() {
        return dlFilelength;
    }

    public void setDlFilelength(long dlFilelength) {
        this.dlFilelength = dlFilelength;
    }

    public InputStream getDlFile() {
        return dlFile;
    }

    public void setDlFile(InputStream dlFile) {
        this.dlFile = dlFile;
    }

    public String getDlFileName() {
        return dlFileName;
    }

    public void setDlFileName(String dlFileName) {
        this.dlFileName = dlFileName;
    }

    public BaseResult getResult() {
        return result;
    }

    public void setResult(BaseResult result) {
        this.result = result;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
