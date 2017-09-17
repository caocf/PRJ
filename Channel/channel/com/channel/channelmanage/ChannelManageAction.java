package com.channel.channelmanage;

import com.channel.model.hz.*;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/29.
 */
public class ChannelManageAction extends BaseAction {
    @Resource(name = "channelmanageservice")
    private ChannelManageService channelManageService;
    private Integer id;
    private Integer dept;
    private List<Integer> ids;
    private List<Integer> delfileids;
    private List<CHzThlzfj> fjlist;
    private Integer xmlx;
    private Integer jzwxz;
    private Integer contenttype;
    private String content;
    private CHzCzpc czpc;
    private List<CHzCzpclx> czpclxs;
    private String jsondata;
    private CHzAdminlicense adminlicense;
    private CHzArgument argument;
    private List<File> filelist;// 资源
    private List<String> filelistFileName;// 文件名
    private Date starttime;
    private Date endtime;
    private String filename;// 文件名
    private long dlFilelength; // 下载文件长度
    private InputStream dlFile; // 下载文件流
    private String dlFileName; // 下载文件名
    private String sEcho;
    private BaseResult result;
    private int page;
    private int rows;


    /**
     * 添加行政许可
     *
     * @return
     */
    public String addChannelManage() {
        result = this.channelManageService.addChannelManage(adminlicense);
        return "success";
    }

    /**
     * 刪除行政许可
     *
     * @return
     */
    public String delChannelManage() {
        result = this.channelManageService.delChannelManage(ids);
        return "success";
    }

    /**
     * 编辑行政许可
     *
     * @return
     */
    public String updateChannelManage() {
        result = this.channelManageService.updateChannelManage(id, adminlicense);
        return "success";
    }


    /**
     * 查看行政许可
     *
     * @return
     */
    public String queryChannelManage() {
        result = this.channelManageService.queryChannelManage(xmlx, jzwxz, starttime, endtime, contenttype, content, sEcho, page, rows);
        return "success";
    }

    /**
     * 根据id查看行政许可
     *
     * @return
     */
    public String queryChannelManageById() {
        result = this.channelManageService.queryChannelManageById(id);
        session.put("xzxk", result.getObj());
        return "success";
    }

    /**
     * 查看行政处罚
     *
     * @return
     */
    public String queryAdminpenalty() {
        result = this.channelManageService.queryAdminpenalty(dept,contenttype, content, sEcho, page, rows);
        return "success";
    }

    /**
     * 根据id查看行政处罚
     *
     * @return
     */
    public String queryAdminpenaltyById() {
        result = this.channelManageService.queryAdminpenaltyById(id);
        session.put("xzcf", result.getObj());
        return "success";
    }


    /**
     * 添加通航论证
     *
     * @return
     */
    public String addArgument() throws Exception {
        result = this.channelManageService.addArgument(argument, filelist, filelistFileName);
        return "success";
    }

    /**
     * 删除通航论证
     *
     * @return
     */
    public String delArgument() throws Exception {
        result = this.channelManageService.delArgument(ids);
        return "success";
    }

    /**
     * 更新通航论证
     *
     * @return
     */
    public String updateArgument() throws Exception {
        result = this.channelManageService.updateArgument(argument, delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 查询通航论证
     *
     * @return
     */
    public String queryArgument() {
        result = this.channelManageService.queryArgument(starttime,
                endtime, content, sEcho, page, rows);
        return "success";
    }

    /**
     * 查询通航论证详情(页面跳转)
     *
     * @return
     */
    public String queryArgumentById() {
        result = this.channelManageService.queryArgumentById(id);
        session.put("thlz", result.getObj());
        session.put("mapinfo", result.getMap());
        return "success";
    }

    /**
     * 查询通航论证详情(json)
     *
     * @return
     */
    public String queryArgumentByIdJson() {
        result = this.channelManageService.queryArgumentByIdJson(id);
        return "success";
    }

    /**
     * 下载通航论证照片
     *
     * @return
     */
    public String downloadCHzFj() throws Exception {
        result = this.channelManageService.downloadCHzFj(id);
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
     * 查询重置赔偿
     *
     * @return
     */
    public String queryReparation() {
        result = this.channelManageService.queryReparation(starttime,
                endtime, content, sEcho, page, rows);
        return "success";
    }

    /**
     * 查询重置赔偿详情
     *
     * @return
     */
    public String queryReparationById() {
        result = this.channelManageService.queryReparationById(id);
        session.put("czpc", result.getObj());
        session.put("mapinfo", result.getMap());
        return "success";
    }


    /**
     * 添加重置赔偿
     *
     * @return
     */
    public String addReparation() throws Exception {
        result = this.channelManageService.addReparation(czpc, jsondata);
        return "success";
    }

    /**
     * 删除重置赔偿
     *
     * @return
     */
    public String delReparation() {
        result = this.channelManageService.delReparation(ids);
        return "success";
    }

    /**
     * 更新通航论证
     *
     * @return
     */
    public String updateReparation() {
        result = this.channelManageService.updateReparation(id, czpc, jsondata);
        return "success";
    }

    public CHzArgument getArgument() {
        return argument;
    }

    public void setArgument(CHzArgument argument) {
        this.argument = argument;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getSEcho() {
        return sEcho;
    }

    public void setSEcho(String sEcho) {
        this.sEcho = sEcho;
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

    public CHzAdminlicense getAdminlicense() {
        return adminlicense;
    }

    public void setAdminlicense(CHzAdminlicense adminlicense) {
        this.adminlicense = adminlicense;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getXmlx() {
        return xmlx;
    }

    public void setXmlx(Integer xmlx) {
        this.xmlx = xmlx;
    }

    public Integer getJzwxz() {
        return jzwxz;
    }

    public void setJzwxz(Integer jzwxz) {
        this.jzwxz = jzwxz;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getContenttype() {
        return contenttype;
    }

    public void setContenttype(Integer contenttype) {
        this.contenttype = contenttype;
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

    public List<CHzThlzfj> getFjlist() {
        return fjlist;
    }

    public void setFjlist(List<CHzThlzfj> fjlist) {
        this.fjlist = fjlist;
    }

    public CHzCzpc getCzpc() {
        return czpc;
    }

    public void setCzpc(CHzCzpc czpc) {
        this.czpc = czpc;
    }

    public List<CHzCzpclx> getCzpclxs() {
        return czpclxs;
    }

    public void setCzpclxs(List<CHzCzpclx> czpclxs) {
        this.czpclxs = czpclxs;
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

    public String getJsondata() {
        return jsondata;
    }

    public void setJsondata(String jsondata) {
        this.jsondata = jsondata;
    }

    public Integer getDept() {
        return dept;
    }

    public void setDept(Integer dept) {
        this.dept = dept;
    }
}
