package com.channel.cruise;

import com.channel.model.xc.CXcGk;
import com.channel.model.xc.CXcYhgk;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;
import com.common.utils.parser.ParseErrorException;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Controller
public class CruiseAction extends BaseAction {
    private static final long serialVersionUID = 2068510412891357633L;
    private int id;
    private List<Integer> ids;
    private String jsondata;
    private CXcGk gk;
    private CXcGk updategk;
    private CXcYhgk updateyhgk;
    private int hdid;
    private int flag;
    private Date starttime;
    private Date endtime;
    private String content;// 内容
    private List<Integer> delfileids;
    private List<File> filelist;// 文件
    private List<String> filelistFileName;// 文件名
    private String filename;// 文件名
    private long dlFilelength; // 下载文件长度
    private InputStream dlFile; // 下载文件流
    private String dlFileName; // 下载文件名
    private String sEcho;
    private int page = 0;
    private int rows = 0;
    private BaseResult result;

    @Resource(name = "cruiseService")
    private CruiseService cruiseService;

    /**
     * 查询巡查日志
     *
     * @return
     */
    public String queryCruise() {
        result = this.cruiseService.queryCruise(ids, starttime, endtime, page, rows, sEcho);
        return "success";
    }

    /**
     * 查看巡查日志根据id
     *
     * @return
     */
    public String queryCruiseById() {
        result = this.cruiseService.queryCruiseById(id);
        return "success";
    }

    /**
     * 删除巡查日志
     *
     * @return
     */
    public String delCruise() {
        result = this.cruiseService.delCruise(ids);
        return "success";
    }

    /**
     * 添加巡查日志
     *
     * @return
     */
    public String addCruise() throws ParseErrorException {
        result = this.cruiseService.addCruise(gk, jsondata, filelist,
                filelistFileName, flag);
        return "success";
    }

    /**
     * 更新巡查日志
     *
     * @return
     */
    public String updateCruise() throws ParseErrorException {
        result = this.cruiseService.updateCruise(id, updategk, jsondata, delfileids, filelist,
                filelistFileName, flag);
        return "success";
    }

    /**
     * 查询养护联系单
     *
     * @return
     */
    public String queryYhlxd() {
        result = this.cruiseService.queryYhlxd(ids, starttime, endtime, content, page, rows, sEcho);
        return "success";
    }

    /**
     * 删除养护联系
     *
     * @return
     */
    public String delYhlxd() {
        result = this.cruiseService.delYhlxd(ids);
        return "success";
    }

    /**
     * 更新养护联系单
     *
     * @return
     */
    public String updateYhlxd() throws ParseErrorException {
        result = this.cruiseService.updateYhlxd(id, updateyhgk, jsondata, delfileids, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 查询养护联系根据id
     *
     * @return
     */
    public String queryYhlxdById() {
        result = this.cruiseService.queryYhlxdById(id);
        return "success";
    }

    /**
     * 导出养护联系单
     */
    public String exportYhlxd() throws Exception {
        result = this.cruiseService.exportYhlxd(id);
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
     * 下载巡查附件
     *
     * @return
     * @throws FileNotFoundException
     */
    public String downloadCxcfj() throws FileNotFoundException {
        result = this.cruiseService.downloadCxcfj(id);
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
     * 下载养护联系单附件
     *
     * @return
     * @throws FileNotFoundException
     */
    public String downloadCxcyhfj() throws FileNotFoundException {
        result = this.cruiseService.downloadCxcyhfj(id);
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public CXcGk getUpdategk() {
        return updategk;
    }

    public void setUpdategk(CXcGk updategk) {
        this.updategk = updategk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public int getHdid() {
        return hdid;
    }

    public void setHdid(int hdid) {
        this.hdid = hdid;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getFilelistFileName() {
        return filelistFileName;
    }

    public void setFilelistFileName(List<String> filelistFileName) {
        this.filelistFileName = filelistFileName;
    }

    public List<File> getFilelist() {
        return filelist;
    }

    public void setFilelist(List<File> filelist) {
        this.filelist = filelist;
    }

    public String getSEcho() {
        return sEcho;
    }

    public void setSEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public CXcGk getGk() {
        return gk;
    }

    public void setGk(CXcGk gk) {
        this.gk = gk;
    }

    public String getJsondata() {
        return jsondata;
    }

    public void setJsondata(String jsondata) {
        this.jsondata = jsondata;
    }

    public List<Integer> getDelfileids() {
        return delfileids;
    }

    public void setDelfileids(List<Integer> delfileids) {
        this.delfileids = delfileids;
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

    public BaseResult getResult() {
        return result;
    }

    public void setResult(BaseResult result) {
        this.result = result;
    }

    public CXcYhgk getUpdateyhgk() {
        return updateyhgk;
    }

    public void setUpdateyhgk(CXcYhgk updateyhgk) {
        this.updateyhgk = updateyhgk;
    }
}
