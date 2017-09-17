package com.channel.appurtenance.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;

import com.channel.model.hd.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.channel.appurtenance.service.AppurtenanceService;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

@Controller
public class AppurtenanceAction extends BaseAction {
    /**
     *
     */
    private static final long serialVersionUID = 2068510412891357632L;
    private int pid;// 附属物类型父id
    private int fswlx;// 附属物类型
    private int id;// 附属物id
    private String bh;// 编号
    private String filename;// 文件名
    private long dlFilelength; // 下载文件长度
    private InputStream dlFile; // 下载文件流
    private String dlFileName; // 下载文件名
    private CHdHb chdhb;
    private CHdQl chdql;
    private CHdDc chddc;
    private CHdLx chdlx;
    private CHdGd chdgd;
    private CHdSd chdsd;
    private CHdKymt chdkymt;
    private CHdHymt chdhymt;
    private CHdGwmt chdgwmt;
    private CHdCc chdcc;
    private CHdQpsk chdqpsk;
    private CHdSwz chdswz;
    private CHdGlz chdglz;
    private CHdFwq chdfwq;
    private CHdMbq chdmbq;
    private CHdSn chdsn;
    private CHdB chdb;
    private CHdZzha chdzzha;
    private CHdRggcd chdrggcd;
    private CHdJgllgcd chdjgllgcd;
    private CHdSpgcd chdspgcd;
    private CHdXlz chdxlz;
    private List<File> filelist;// 资源
    private List<String> filelistFileName;// 文件名
    private List<Integer> delfileids;
    private Integer sshduanid;// 所属航段编号
    private BaseResult result;
    private Object object;
    private String content;
    private int sfgg;
    private String sEcho;
    private int page = 0;
    private int rows = 0;
    private int xzqh;
    private int sshdid;
    private int fswsecondclassid;

    @Resource(name = "appurtenanceservice")
    private AppurtenanceService appurtenanceService;


    /**
     * 批量导入
     *
     * @throws Exception
     */
    public String batchImport() throws Exception {
        result = this.appurtenanceService.batchImport(fswlx, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 下载模板
     *
     * @throws Exception
     */
    public String downloadTemplate() throws Exception {
        result = this.appurtenanceService.downloadTemplate(fswlx);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = getContextPath() + "/template/" + filename; // 绝对路径
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
     * 批量导入桥梁
     *
     * @throws Exception
     */
    public String importQl() throws Exception {
        result = this.appurtenanceService.importQl();
        return "success";
    }

    /**
     * 批量导入桥梁
     *
     * @throws Exception
     */
    public String importLx() throws Exception {
        result = this.appurtenanceService.importLx();
        return "success";
    }

    /**
     * 批量导入码头
     *
     * @throws Exception
     */
    public String importKymt() throws Exception {
        result = this.appurtenanceService.importKymt();
        return "success";
    }

    /**
     * 批量导入枢纽
     *
     * @throws Exception
     */
    public String importSn() throws Exception {
        result = this.appurtenanceService.importSn();
        return "success";
    }

    public String queryMaxAppbh() {
        result = this.appurtenanceService.queryMaxAppbh(fswlx);
        return "success";
    }

    public String queryAppbhExisted() {
        result = this.appurtenanceService.queryAppbhExisted(bh, fswlx);
        return "success";
    }

    public String searchAppurtenances() {
        result = this.appurtenanceService.searchAppurtenances(xzqh, sshdid, sshduanid, fswsecondclassid, content);
        return "success";
    }

    public String queryAppBh() {
        result = this.appurtenanceService.queryAppBh(fswlx);
        return "success";
    }

    /**
     * 查询航道下所有附属物
     *
     * @return
     */
    public String queryAppurtenances() {
        result = this.appurtenanceService.queryAppurtenances(pid,
                sshduanid, page, rows);
        result.addToMap("sEcho", sEcho);
        return "success";
    }

    /**
     * 综合查询所有附属物
     *
     * @return
     */
    public String zhcxApps() {
        result = this.appurtenanceService.zhcxApps(fswlx, xzqh, sshdid,
                sshduanid, content, page, rows);
        result.addToMap("sEcho", sEcho);
        return "success";
    }

    /**
     * 查询所有附属物按照内容
     *
     * @return
     */
    public String searchAppByContent() {
        result = this.appurtenanceService.searchAppByContent(pid,
                sshduanid, content);
        return "success";
    }

    /**
     * 查询所有附属物按照内容
     *
     * @return
     */
    public String searchAppByXzqh() {
        result = this.appurtenanceService.searchAppByXzqh(xzqh,pid,
                sshduanid, content);
        return "success";
    }

    /**
     * 查询附属物详情
     *
     * @return
     */
    public String queryAppurtenanceInfo() {
        result = this.appurtenanceService.queryAppurtenanceInfo(id,
                fswlx);
        return "success";
    }

    /**
     * 查询附属物详情(popup)
     *
     * @return
     */
    public String queryAppPopup() {
        result = this.appurtenanceService.queryAppPopup(id,
                fswlx);
        return "success";
    }

    /**
     * 删除附属物
     *
     * @return
     */
    public String delAppurtenance() {
        result = this.appurtenanceService.delAppurtenance(id, fswlx);
        return "success";
    }

    /**
     * 添加航标
     *
     * @return
     * @throws Exception
     */
    public String addNavigationmark() throws Exception {
        result = this.appurtenanceService.addNavigationmark(chdhb,
                filelist, filelistFileName);
        return "success";
    }

    /**
     * 添加桥梁
     *
     * @return
     * @throws Exception
     */
    public String addBridge() throws Exception {
        result = this.appurtenanceService.addBridge(chdql, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 添加渡槽
     *
     * @return
     */
    public String addAqueduct() throws Exception {
        result = this.appurtenanceService.addAqueduct(chddc, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 添加缆线
     *
     * @return
     * @throws Exception
     */
    public String addCable() throws Exception {
        result = this.appurtenanceService.addCable(chdlx, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 添加管道
     *
     * @return
     * @throws Exception
     */
    public String addPipeline() throws Exception {
        result = this.appurtenanceService.addPipeline(chdgd, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 添加隧道
     *
     * @return
     * @throws Exception
     */
    public String addTunnel() throws Exception {
        result = this.appurtenanceService.addTunnel(chdsd, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 添加客运码头
     *
     * @return
     * @throws Exception
     */
    public String addKyDock() throws Exception {
        result = this.appurtenanceService.addKyDock(chdkymt, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 添加货运码头
     *
     * @return
     * @throws Exception
     */
    public String addHyDock() throws Exception {
        result = this.appurtenanceService.addHyDock(chdhymt, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 公务码头
     *
     * @return
     * @throws Exception
     */
    public String addGwDock() throws Exception {
        result = this.appurtenanceService.addGwDock(chdgwmt, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 添加船厂
     *
     * @return
     * @throws Exception
     */
    public String addShipyard() throws Exception {
        result = this.appurtenanceService.addShipyard(chdcc, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 添加取排水口
     *
     * @return
     * @throws Exception
     */
    public String addTakeoutfall() throws Exception {
        result = this.appurtenanceService.addTakeoutfall(chdqpsk,
                filelist, filelistFileName);
        return "success";
    }

    /**
     * 添加水文站
     *
     * @return
     * @throws Exception
     */
    public String addHydrologicalstation() throws Exception {
        result = this.appurtenanceService.addHydrologicalstation(
                chdswz, filelist, filelistFileName);
        return "success";
    }


    /**
     * 添加管理站
     *
     * @return
     * @throws Exception
     */
    public String addManagementstation() throws Exception {
        result = this.appurtenanceService.addManagementstation(chdglz,
                filelist, filelistFileName);
        return "success";
    }

    /**
     * 添加服务区
     *
     * @return
     * @throws Exception
     */
    public String addServicearea() throws Exception {
        result = this.appurtenanceService.addServicearea(chdfwq,
                filelist, filelistFileName);
        return "success";
    }

    /**
     * 添加锚泊区
     *
     * @return
     * @throws Exception
     */
    public String addMooringarea() throws Exception {
        result = this.appurtenanceService.addMooringarea(chdmbq,
                filelist, filelistFileName);
        return "success";
    }

    /**
     * 添加枢纽
     *
     * @return
     * @throws Exception
     */
    public String addHub() throws Exception {
        result = this.appurtenanceService.addHub(chdsn, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 添加大坝
     *
     * @return
     * @throws Exception
     */
    public String addDam() throws Exception {
        result = this.appurtenanceService.addDam(chdb, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 添加整治护岸
     *
     * @return
     * @throws Exception
     */
    public String addRegulationrevement() throws Exception {
        result = this.appurtenanceService.addRegulationrevement(
                chdzzha, filelist, filelistFileName);
        return "success";
    }

    /**
     * 添加激光流量观测点
     *
     * @return
     * @throws Exception
     */
    public String addLaserobservation() throws Exception {
        result = this.appurtenanceService.addLaserobservation(
                chdjgllgcd, filelist, filelistFileName);
        return "success";
    }

    /**
     * 添加人工流量观测点
     *
     * @return
     * @throws Exception
     */
    public String addManualobservation() throws Exception {
        result = this.appurtenanceService.addManualobservation(
                chdrggcd, filelist, filelistFileName);
        return "success";
    }

    /**
     * 添加视频观测点
     *
     * @return
     * @throws Exception
     */
    public String addVideoobservation() throws Exception {
        result = this.appurtenanceService.addVideoobservation(
                chdspgcd, filelist, filelistFileName);
        return "success";
    }

    /**
     * 添加系缆桩
     *
     * @return
     * @throws Exception
     */
    public String addBollard() throws Exception {
        result = this.appurtenanceService.addBollard(chdxlz, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 更新航标
     *
     * @return
     * @throws Exception
     */
    public String updateNavigationmark() throws Exception {
        result = this.appurtenanceService.updateNavigationmark(chdhb,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新桥梁
     *
     * @return
     * @throws Exception
     */
    public String updateBridge() throws Exception {
        result = this.appurtenanceService.updateBridge(chdql,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新渡槽
     *
     * @return
     * @throws Exception
     */
    public String updateAqueduct() throws Exception {
        result = this.appurtenanceService.updateAqueduct(chddc,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新缆线
     *
     * @return
     * @throws Exception
     */
    public String updateCable() throws Exception {
        result = this.appurtenanceService.updateCable(chdlx,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新管道
     *
     * @return
     * @throws Exception
     */
    public String updatePipeline() throws Exception {
        result = this.appurtenanceService.updatePipeline(chdgd,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新隧道
     *
     * @return
     * @throws Exception
     */
    public String updateTunnel() throws Exception {
        result = this.appurtenanceService.updateTunnel(chdsd,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新客运码头
     *
     * @return
     * @throws Exception
     */
    public String updateKyDock() throws Exception {
        result = this.appurtenanceService.updateKyDock(chdkymt,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新货运码头
     *
     * @return
     * @throws Exception
     */
    public String updateHyDock() throws Exception {
        result = this.appurtenanceService.updateHyDock(chdhymt,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新公务码头
     *
     * @return
     * @throws Exception
     */
    public String updateGwDock() throws Exception {
        result = this.appurtenanceService.updateGwDock(chdgwmt,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新船厂
     *
     * @return
     * @throws Exception
     */
    public String updateShipyard() throws Exception {
        result = this.appurtenanceService.updateShipyard(chdcc,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新取排水口
     *
     * @return
     * @throws Exception
     */
    public String updateTakeoutfall() throws Exception {
        result = this.appurtenanceService.updateTakeoutfall(chdqpsk,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新水文站
     *
     * @return
     * @throws Exception
     */
    public String updateHydrologicalstation() throws Exception {
        result = this.appurtenanceService.updateHydrologicalstation(
                chdswz, delfileids, filelist, filelistFileName);
        return "success";
    }


    /**
     * 更新管理站
     *
     * @return
     * @throws Exception
     */
    public String updateManagementstation() throws Exception {
        result = this.appurtenanceService.updateManagementstation(
                chdglz, delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新服务区
     *
     * @return
     * @throws Exception
     */
    public String updateServicearea() throws Exception {
        result = this.appurtenanceService.updateServicearea(chdfwq,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新锚泊区
     *
     * @return
     * @throws Exception
     */
    public String updateMooringarea() throws Exception {
        result = this.appurtenanceService.updateMooringarea(chdmbq,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新枢纽
     *
     * @return
     * @throws Exception
     */
    public String updateHub() throws Exception {
        result = this.appurtenanceService.updateHub(chdsn, delfileids,
                filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新大坝
     *
     * @return
     * @throws Exception
     */
    public String updateDam() throws Exception {
        result = this.appurtenanceService.updateDam(chdb, delfileids,
                filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新整治护岸
     *
     * @return
     * @throws Exception
     */
    public String updateRegulationrevement() throws Exception {
        result = this.appurtenanceService.updateRegulationrevement(
                chdzzha, delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新激光流量观测点
     *
     * @return
     * @throws Exception
     */
    public String updateLaserobservation() throws Exception {
        result = this.appurtenanceService.updateLaserobservation(
                chdjgllgcd, delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新人工流量观测点
     *
     * @return
     * @throws Exception
     */
    public String updateManualobservation() throws Exception {
        result = this.appurtenanceService.updateManualobservation(
                chdrggcd, delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新视频观测点
     *
     * @return
     * @throws Exception
     */
    public String updateVideoobservation() throws Exception {
        result = this.appurtenanceService.updateVideoobservation(
                chdspgcd, delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新系缆桩
     *
     * @return
     * @throws Exception
     */
    public String updateBollard() throws Exception {
        result = this.appurtenanceService.updateBollard(chdxlz,
                delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 下载附属物照片
     *
     * @return
     */
    public String downloadCHdFj() throws Exception {
        result = this.appurtenanceService.downloadCHdFj(id);
        if (result.ifResultOK()) {
            filename = (String) result.getFromMap("filename");
            String filePath = getContextPath() + "/upload/" + filename; // 绝对路径
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
     * 中文文件名编码，解决常用浏览器下载时无法解决获得下载文件名中文问题
     *
     * @param filename
     * @return
     */
    public String filenameEncode(String filename) {
        String userAgent = getHttpServletRequest().getHeader("User-Agent")
                .toLowerCase();
        String rtn = "";
        try {
            String new_filename = URLEncoder.encode(filename, "UTF8");

            // 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
            rtn = "filename=\"" + new_filename + "\"";

            if (userAgent != null) {
                userAgent = userAgent.toLowerCase();
                // IE浏览器，只能采用URLEncoder编码
                if (userAgent.indexOf("msie") != -1) {
                    rtn = "filename=\"" + new_filename + "\"";
                }
                // Opera浏览器只能采用filename*
                else if (userAgent.indexOf("opera") != -1) {
                    rtn = "filename*=UTF-8''" + new_filename;
                }
                // Safari浏览器，只能采用ISO编码的中文输出
                else if (userAgent.indexOf("safari") != -1) {

                    rtn = "filename=\""
                            + new String(filename.getBytes("UTF-8"),
                            "ISO8859-1") + "\"";
                }
                // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
                else if (userAgent.indexOf("applewebkit") != -1) {

                    rtn = "filename=\"" + new_filename + "\"";
                }
                // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
                else if (userAgent.indexOf("mozilla") != -1) {
                    rtn = "filename*=UTF-8''" + new_filename;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }

    /**
     * 所有附属物列表
     *
     * @return
     * @throws Exception
     */
    public String queryFsw() throws Exception {
        result = this.appurtenanceService.queryFsw();
        return "success";
    }

    //综合查询附屬物信息
    public String zhcxApp() {
        result = this.appurtenanceService.zhcxApp(xzqh);
        return "success";
    }

    //导出附屬物
    public String exportApp() throws Exception {
        result = this.appurtenanceService.exportApp(xzqh);
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

    public String exportFswInfo() throws Exception{
        result = this.appurtenanceService.exportFswInfo(id,fswlx);
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setDlFilelength(long dlFilelength) {
        this.dlFilelength = dlFilelength;
    }

    public void setDlFile(InputStream dlFile) {
        this.dlFile = dlFile;
    }

    public void setDlFileName(String dlFileName) {
        this.dlFileName = dlFileName;
    }

    public InputStream getDlFile() {
        return dlFile;
    }

    public long getDlFilelength() {
        return dlFilelength;
    }

    public String getDlFileName() {
        return dlFileName;
    }

    public CHdHb getChdhb() {
        return chdhb;
    }

    public void setChdhb(CHdHb chdhb) {
        this.chdhb = chdhb;
    }

    public CHdQl getChdql() {
        return chdql;
    }

    public void setChdql(CHdQl chdql) {
        this.chdql = chdql;
    }

    public CHdDc getChddc() {
        return chddc;
    }

    public void setChddc(CHdDc chddc) {
        this.chddc = chddc;
    }

    public CHdLx getChdlx() {
        return chdlx;
    }

    public void setChdlx(CHdLx chdlx) {
        this.chdlx = chdlx;
    }

    public CHdGd getChdgd() {
        return chdgd;
    }

    public void setChdgd(CHdGd chdgd) {
        this.chdgd = chdgd;
    }

    public CHdSd getChdsd() {
        return chdsd;
    }

    public void setChdsd(CHdSd chdsd) {
        this.chdsd = chdsd;
    }

    public CHdKymt getChdkymt() {
        return chdkymt;
    }

    public void setChdkymt(CHdKymt chdkymt) {
        this.chdkymt = chdkymt;
    }

    public CHdHymt getChdhymt() {
        return chdhymt;
    }

    public void setChdhymt(CHdHymt chdhymt) {
        this.chdhymt = chdhymt;
    }

    public int getXzqh() {
        return xzqh;
    }

    public void setXzqh(int xzqh) {
        this.xzqh = xzqh;
    }

    public int getSshdid() {
        return sshdid;
    }

    public void setSshdid(int sshdid) {
        this.sshdid = sshdid;
    }

    public void setSshduanid(int sshduanid) {
        this.sshduanid = sshduanid;
    }

    public int getFswsecondclassid() {
        return fswsecondclassid;
    }

    public void setFswsecondclassid(int fswsecondclassid) {
        this.fswsecondclassid = fswsecondclassid;
    }

    public CHdGwmt getChdgwmt() {
        return chdgwmt;
    }

    public void setChdgwmt(CHdGwmt chdgwmt) {
        this.chdgwmt = chdgwmt;
    }

    public CHdCc getChdcc() {
        return chdcc;
    }

    public void setChdcc(CHdCc chdcc) {
        this.chdcc = chdcc;
    }

    public CHdQpsk getChdqpsk() {
        return chdqpsk;
    }

    public void setChdqpsk(CHdQpsk chdqpsk) {
        this.chdqpsk = chdqpsk;
    }

    public CHdSwz getChdswz() {
        return chdswz;
    }

    public void setChdswz(CHdSwz chdswz) {
        this.chdswz = chdswz;
    }

    public CHdGlz getChdglz() {
        return chdglz;
    }

    public void setChdglz(CHdGlz chdglz) {
        this.chdglz = chdglz;
    }

    public CHdFwq getChdfwq() {
        return chdfwq;
    }

    public void setChdfwq(CHdFwq chdfwq) {
        this.chdfwq = chdfwq;
    }

    public CHdMbq getChdmbq() {
        return chdmbq;
    }

    public void setChdmbq(CHdMbq chdmbq) {
        this.chdmbq = chdmbq;
    }

    public CHdSn getChdsn() {
        return chdsn;
    }

    public void setChdsn(CHdSn chdsn) {
        this.chdsn = chdsn;
    }

    public CHdB getChdb() {
        return chdb;
    }

    public void setChdb(CHdB chdb) {
        this.chdb = chdb;
    }

    public CHdZzha getChdzzha() {
        return chdzzha;
    }

    public void setChdzzha(CHdZzha chdzzha) {
        this.chdzzha = chdzzha;
    }

    public CHdJgllgcd getChdjgllgcd() {
        return chdjgllgcd;
    }

    public void setChdjgllgcd(CHdJgllgcd chdjgllgcd) {
        this.chdjgllgcd = chdjgllgcd;
    }

    public CHdSpgcd getChdspgcd() {
        return chdspgcd;
    }

    public void setChdspgcd(CHdSpgcd chdspgcd) {
        this.chdspgcd = chdspgcd;
    }

    public CHdXlz getChdxlz() {
        return chdxlz;
    }

    public void setChdxlz(CHdXlz chdxlz) {
        this.chdxlz = chdxlz;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getFswlx() {
        return fswlx;
    }

    public void setFswlx(int fswlx) {
        this.fswlx = fswlx;
    }

    public Integer getSshduanid() {
        return sshduanid;
    }

    public void setSshduanid(Integer sshduanid) {
        this.sshduanid = sshduanid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public BaseResult getResult() {
        return result;
    }

    public void setResult(BaseResult result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getSEcho() {
        return sEcho;
    }

    public void setSEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<File> getFilelist() {
        return filelist;
    }

    public void setFilelist(List<File> filelist) {
        this.filelist = filelist;
    }

    public List<String> getFilelistFileName() {
        return filelistFileName;
    }

    public void setFilelistFileName(List<String> filelistFileName) {
        this.filelistFileName = filelistFileName;
    }

    public List<Integer> getDelfileids() {
        return delfileids;
    }

    public void setDelfileids(List<Integer> delfileids) {
        this.delfileids = delfileids;
    }

    public CHdRggcd getChdrggcd() {
        return chdrggcd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSfgg() {
        return sfgg;
    }

    public void setSfgg(int sfgg) {
        this.sfgg = sfgg;
    }

    public void setChdrggcd(CHdRggcd chdrggcd) {
        this.chdrggcd = chdrggcd;
    }
}
