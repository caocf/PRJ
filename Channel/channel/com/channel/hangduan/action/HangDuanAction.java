package com.channel.hangduan.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import javax.annotation.Resource;

import com.common.action.BaseAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.channel.hangduan.service.HangDuanService;
import com.channel.model.hd.CHdHduanjcxx;
import com.common.action.result.BaseResult;

@Controller
@Scope("prototype")
public class HangDuanAction extends BaseAction {
    @Resource(name = "hangduanservice")
    private HangDuanService hangDuanService;

    private int id;// id
    private String hdbh;// hdbh
    private Integer sshdid;// 所属航道编号
    private CHdHduanjcxx chdhduanjcxx;
    private BaseResult result;
    private int page;
    private int rows;
    private String content;
    private int sfgg;
    private int xzqh = -1;
    private int hddj;
    private String sEcho;// datatable
    private String filename;// 文件名
    private long dlFilelength; // 下载文件长度
    private InputStream dlFile; // 下载文件流
    private String dlFileName; // 下载文件名

    public String queryMaxHdbh() {
        result = this.hangDuanService.queryMaxHdbh(sshdid);
        return "success";
    }

    public String queryHdbhExisted() {
        result = this.hangDuanService.queryHdbhExisted(hdbh, sshdid);
        return "success";
    }

    public String searchAllHangDuan() {
        result = this.hangDuanService.searchAllHangDuan(xzqh, sshdid, hddj, content);
        return "success";
    }

    /**
     * 根据所属航道编号查询航段
     */
    public String queryHangDuanBySshdid() {
        result = this.hangDuanService.queryHangDuanBySshdid(sshdid, xzqh);
        return "success";
    }

    /**
     * 根据所属航道编号航段等級查询航段
     */
    public String searchHangDuanHddj() {
        result = this.hangDuanService.searchHangDuanHddj(xzqh, sshdid, hddj, content);
        return "success";
    }

    /**
     * 根据行政区划查询航段
     */
    public String queryHangDuanByXzqh() {
        result = this.hangDuanService.queryHangDuanByXzqh(xzqh);
        return "success";
    }

    /**
     * 查询航段详情
     */
    public String queryHangDuanInfo() {
        result = this.hangDuanService.queryHangDuanInfo(id);
        return "success";
    }

    public String exportHduanInfo() throws Exception{
        result = this.hangDuanService.exportHduanInfo(id);
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
     * 删除航段
     */
    public String delHangDuan() {
        result = this.hangDuanService.delHangDuan(id);
        return "success";
    }

    /**
     * 添加航段
     */
    public String addHangDuan() {
        chdhduanjcxx.setSshdid(sshdid);
        chdhduanjcxx.setCreatetime(new Date());
        chdhduanjcxx.setUpdatetime(new Date());
        result = this.hangDuanService.addHangDuan(chdhduanjcxx);
        return "success";
    }

    /**
     * 更新航段
     */
    public String updateHangDuan() {
        chdhduanjcxx.setUpdatetime(new Date());
        result = this.hangDuanService.updateHangDuan(chdhduanjcxx);
        return "success";
    }

    //综合查询航段信息
    public String zhcxHduan() {
        result = this.hangDuanService.zhcxHduan(xzqh, sshdid, hddj, content);
        return "success";
    }

    //导出航段
    public String exportHduan() throws Exception{
        result = this.hangDuanService.exportHduan(xzqh, sshdid);
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

    //综合查询航段信息
    public String zhcxHduanInfo() {
        result = this.hangDuanService.zhcxHduanInfo(xzqh, sshdid, hddj, content, page, rows);
        result.addToMap("sEcho", sEcho);
        return "success";
    }

    //导入航段
    public String importHduan() {
        result = this.hangDuanService.importHduan();
        return "success";
    }

    public int getXzqh() {
        return xzqh;
    }

    public void setXzqh(int xzqh) {
        this.xzqh = xzqh;
    }

    public String getHdbh() {
        return hdbh;
    }

    public void setHdbh(String hdbh) {
        this.hdbh = hdbh;
    }

    public int getSfgg() {
        return sfgg;
    }

    public void setSfgg(int sfgg) {
        this.sfgg = sfgg;
    }

    public CHdHduanjcxx getChdhduanjcxx() {
        return chdhduanjcxx;
    }

    public int getHddj() {
        return hddj;
    }

    public void setHddj(int hddj) {
        this.hddj = hddj;
    }

    public void setChdhduanjcxx(CHdHduanjcxx chdhduanjcxx) {
        this.chdhduanjcxx = chdhduanjcxx;
    }

    public Integer getSshdid() {
        return sshdid;
    }

    public void setSshdid(Integer sshdid) {
        this.sshdid = sshdid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BaseResult getResult() {
        return result;
    }

    public void setResult(BaseResult result) {
        this.result = result;
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

    public String getSEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
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
}
