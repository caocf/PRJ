package com.channel.hangdao.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

import javax.annotation.Resource;

import com.common.action.BaseAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.channel.hangdao.service.HangDaoService;
import com.channel.model.hd.CHdHdaojcxx;
import com.common.action.result.BaseResult;

@Controller
@Scope("prototype")
public class HangDaoAction extends BaseAction{
    @Resource(name = "hangdaoservice")
    private HangDaoService hangDaoService;
    private int xzqh = -1;
    private int id;// id
    private int sfgg = 1;
    private CHdHdaojcxx chdhdaojcxx;
    private BaseResult result;
    private int hdid;
    private int sjbh;
    private int hddj;
    private int page;
    private int rows;
    private String sEcho;// datatable
    private String content;
    private String filename;// 文件名
    private long dlFilelength; // 下载文件长度
    private InputStream dlFile; // 下载文件流
    private String dlFileName; // 下载文件名

    /**
     * 搜索航道
     */
    public String searchAllHangDao() {
        result = this.hangDaoService.searchAllHangDao(sfgg, xzqh, content);
        return "success";
    }

    /**
     * 搜索航道按照市级编号
     */
    public String searchHangDaoSjbh() {
        result = this.hangDaoService.searchHangDaoSjbh(sfgg, sjbh, content);
        return "success";
    }

    public String searchHangDaoHddj() {
        result = this.hangDaoService.searchHangDaoHddj(sfgg, xzqh, hdid, hddj, content);
        return "success";
    }

    /**
     * 查询所有航道
     */
    public String queryAllHangDao() {
        result = this.hangDaoService.queryAllHangDao(sfgg, xzqh);
        return "success";
    }
    /**
     * 查询所有航道
     */
    public String queryHdManXzqh() {
        result = this.hangDaoService.queryHdManXzqh(sfgg, xzqh);
        return "success";
    }

    /**
     * 添加航道
     */
    public String addHangDao() {
        chdhdaojcxx.setCreatetime(new Date());
        chdhdaojcxx.setUpdatetime(new Date());
        result = this.hangDaoService.addHangDao(chdhdaojcxx);
        return "success";
    }

    /**
     * 更新航道
     */
    public String updateHangDao() {
        chdhdaojcxx.setUpdatetime(new Date());
        result = this.hangDaoService.updateHangDao(chdhdaojcxx);
        return "success";
    }


    /**
     * 显示航道详情
     */
    public String queryHangDaoInfo() {
        result = this.hangDaoService.queryHangDaoInfo(id, xzqh);
        return "success";
    }

    /**
     * 删除航道
     */
    public String delHangDao() {
        result = this.hangDaoService.delHangDao(id, xzqh);
        return "success";
    }

    /**
     * 综合查询航道
     */
    public String zhcxHdao() {
        result = this.hangDaoService.zhcxHdao(xzqh);
        return "success";
    }

    /**
     * 导出航道
     */
    public String exportHdao() throws Exception{
        result = this.hangDaoService.exportHdao(xzqh);
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


    public String exportHdaoInfo() throws Exception{
        result = this.hangDaoService.exportHdaoInfo(id,xzqh);
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
     * 综合查询航道信息
     */
    public String zhcxHdaoInfo() {
        result = this.hangDaoService.zhcxHdaoInfo(xzqh, sfgg, content, page, rows);
        result.addToMap("sEcho",sEcho);
        return "success";
    }

    /**
     * 综合查询航道信息
     */
    public String importHdao() {
        result = this.hangDaoService.importHdao();
        return "success";
    }

    public int getHddj() {
        return hddj;
    }

    public void setHddj(int hddj) {
        this.hddj = hddj;
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

    public int getXzqh() {
        return xzqh;
    }

    public void setXzqh(int xzqh) {
        this.xzqh = xzqh;
    }

    public CHdHdaojcxx getChdhdaojcxx() {
        return chdhdaojcxx;
    }

    public void setChdhdaojcxx(CHdHdaojcxx chdhdaojcxx) {
        this.chdhdaojcxx = chdhdaojcxx;
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

    public int getSjbh() {
        return sjbh;
    }

    public void setSjbh(int sjbh) {
        this.sjbh = sjbh;
    }

    public int getHdid() {
        return hdid;
    }

    public void setHdid(int hdid) {
        this.hdid = hdid;
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
