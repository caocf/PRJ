package com.channel.maintenance.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.channel.model.yh.*;
import org.springframework.stereotype.Controller;
import com.channel.maintenance.service.MaintenanceService;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

@Controller
public class MaintenanceAction extends BaseAction {
    @Resource(name = "maintenanceservice")
    private MaintenanceService maintenanceService;
    private int pid;// 附属物类型父id
    private int id;// id
    private int jbxxid;// 养护id
    private List<Integer> jbxxidlist;// 养护id列表
    private List<Integer> zxjbxxidlist;// 支线养护id列表
    private List<Integer> zxgcidlist;// 专项工程id列表
    private int zxgcid;// 专项工程id
    private Integer dw;
    private Integer xzqh;// 行政区划
    private Integer hdaoid;
    private Integer hduanid;
    private Integer flag;// 标志位
    private String dwmc;
    private List<Integer> hdids;
    private Integer hdid;
    private Integer hdlx;// 航道类型
    private Integer gldw;// 管理单位
    private Date starttime;
    private Date endtime;
    private String content;// 内容
    private CYhFj cyhfj;
    private CYhYjqtgc yjqtgc;
    private CYhYdjdqk cyhybb;
    private CYhGgjbxx jbxx;// 骨干
    private CYhZxgc zxgc;// 专项工程
    private CYhZxjbxx zxjbxx;// 支线
    private List<CYhYdjdqk> ybblist;
    private List<CYhFj> fjlist;
    private String filename;// 文件名
    private long dlFilelength; // 下载文件长度
    private InputStream dlFile; // 下载文件流
    private String dlFileName; // 下载文件名
    private List<File> filelist;// 资源
    private List<String> filelistFileName;// 文件名
    private List<Integer> delybbids;
    private List<Integer> delfileids;
    private List<Integer> ids;
    private BaseResult result;
    private int sfdg = 0;
    private String sEcho;// datatable
    private int page;
    private int rows;

    /**
     * 统计骨干年月
     *
     * @return
     */
    public String queryGgStarttime() {
        result = this.maintenanceService.queryGgStarttime();
        return "success";
    }


    /**
     * 统计支线年月
     *
     * @return
     */
    public String queryZxStarttime() {
        result = this.maintenanceService.queryZxStarttime();
        return "success";
    }


    /**
     * 统计专项工程年月
     *
     * @return
     */
    public String queryZxgcStarttime() {
        result = this.maintenanceService.queryZxgcStarttime();
        return "success";
    }


    /**
     * 根据id查询养护信息(单条)
     *
     * @return
     */
    public String queryMainById() {
        result = this.maintenanceService.queryMainById(jbxxid);
        return "success";
    }

    /**
     * 根据条件查询所有养护(多条)
     *
     * @return
     */
    public String queryMaintenances() {
        result = this.maintenanceService.queryMaintenances(sfdg, dw, hdids,
                starttime, endtime, sEcho, page, rows);
        return "success";
    }

    /**
     * 删除养护信息
     *
     * @return
     */
    public String delMaintenance() {
        result = this.maintenanceService.delMaintenance(jbxxidlist);
        return "success";
    }

    /**
     * 添加骨干养护信息
     *
     * @return
     */
    public String addMaintenance() {
        result = this.maintenanceService.addMaintenance(jbxx);
        return "success";
    }

    /**
     * 更新骨干养护信息
     *
     * @return
     */
    public String updateMaintenance() {
        result = this.maintenanceService.updateMaintenance(jbxx);
        return "success";
    }

    /**
     * 根据id查询支线养护信息(单条)
     *
     * @return
     */
    public String queryBranchById() {
        result = this.maintenanceService.queryBranchById(id);
        return "success";
    }

    /**
     * 根据条件查询所有支线(多条)
     *
     * @return
     */
    public String queryBranches() {
        result = this.maintenanceService.queryBranches(sfdg, dw, starttime,
                endtime, sEcho, page, rows);
        return "success";
    }

    /**
     * 删除支线养护信息
     *
     * @return
     */
    public String delBranch() {
        result = this.maintenanceService.delBranch(zxjbxxidlist);
        return "success";
    }

    /**
     * 添加支线养护信息
     *
     * @return
     */
    public String addBranch() {
        result = this.maintenanceService.addBranch(zxjbxx);
        return "success";
    }

    /**
     * 更新支线养护信息
     *
     * @return
     */
    public String updateBranch() {
        result = this.maintenanceService.updateBranch(zxjbxx);
        return "success";
    }

    /**
     * 查询专项工程建设单位信息
     *
     * @return
     */
    public String queryAllJsdw() {
        result = this.maintenanceService.queryAllJsdw();
        return "success";
    }

    /**
     * 根据id查询专项工程信息(单条)
     *
     * @return
     */
    public String queryZxgcById() {
        result = this.maintenanceService.queryZxgcById(zxgcid);
        return "success";
    }

    /**
     * 查询所有专项工程
     *
     * @return
     */
    public String queryZxgcs() {
        result = this.maintenanceService.queryZxgcs(sfdg, gldw, starttime,
                endtime, content, sEcho, page, rows);
        return "success";
    }

    /**
     * 删除专项工程
     *
     * @return
     */
    public String delZxgc() {
        result = this.maintenanceService.delZxgc(zxgcidlist);
        return "success";
    }

    /**
     * 添加专项工程
     *
     * @return
     */
    public String addZxgc() {
        result = this.maintenanceService.addZxgc(zxgc, ybblist,
                fjlist, filelist, filelistFileName);
        return "success";
    }

    /**
     * 更新专项工程
     *
     * @return
     */
    public String updateZxgc() {
        result = this.maintenanceService.updateZxgc(zxgc, ybblist,
                fjlist, delybbids, delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 添加专项工程附件
     *
     * @return
     */
    public String addCyhfj() {
        result = this.maintenanceService.addCyhfj(zxgcid, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 删除专项工程附件
     *
     * @return
     */
    public String delCyhfj() {
        result = this.maintenanceService.delCyhfj(zxgcid, delfileids);
        return "success";
    }

    /**
     * 查询最后月度
     *
     * @return
     */
    public String queryLastYd() {
        result = this.maintenanceService.queryLastYd(zxgcid);
        return "success";
    }

    /**
     * 显示专项工程月报表
     *
     * @return
     */
    public String queryCyhybb() {
        result = this.maintenanceService.queryCyhybb(id);
        return "success";
    }

    /**
     * 添加专项工程月报表
     *
     * @return
     */
    public String addCyhybb() {
        result = this.maintenanceService.addCyhybb(zxgcid, cyhybb);
        return "success";
    }

    /**
     * 更新专项工程月报表
     *
     * @return
     */
    public String updateCyhybb() {
        result = this.maintenanceService.updateCyhybb(id, cyhybb);
        return "success";
    }

    /**
     * 删除专项工程月报表
     *
     * @return
     */
    public String delCyhybb() {
        result = this.maintenanceService.delCyhybb(zxgcid, delybbids);
        return "success";
    }

    /**
     * 下载专项工程附件
     *
     * @return
     * @throws FileNotFoundException
     */
    public String downloadCyhfj() throws FileNotFoundException {
        result = this.maintenanceService.downloadCyhfj(id);
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
     * 下载应急抢通工程附件
     *
     * @return
     * @throws FileNotFoundException
     */
    public String downloadYjqtgcfj() throws FileNotFoundException {
        result = this.maintenanceService.downloadYjqtgcfj(id);
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
     * 查询应急抢通工程
     *
     * @return
     */
    public String queryYjqtgcById() {
        result = this.maintenanceService.queryYjqtgcById(id);
        return "success";
    }

    /**
     * 添加应急抢通工程
     *
     * @return
     */
    public String addYjqtgc() {
        result = this.maintenanceService.addYjqtgc(yjqtgc, filelist,
                filelistFileName);
        return "success";
    }

    /**
     * 更新应急抢通工程
     *
     * @return
     */
    public String updateYjqtgc() {
        result = this.maintenanceService.updateYjqtgc(yjqtgc, delfileids, filelist, filelistFileName);
        return "success";
    }

    /**
     * 删除应急抢通工程
     *
     * @return
     */
    public String delYjqtgc() {
        result = this.maintenanceService.delYjqtgc(ids);
        return "success";
    }

    /**
     * 搜索应急抢通工程
     *
     * @return
     */
    public String searchYjqtgc() {
        result = this.maintenanceService.searchYjqtgc(sfdg, gldw, hdaoid, hduanid, starttime,
                endtime, content, sEcho, page, rows);
        return "success";
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getDw() {
        return dw;
    }

    public void setDw(Integer dw) {
        this.dw = dw;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public int getJbxxid() {
        return jbxxid;
    }

    public void setJbxxid(int jbxxid) {
        this.jbxxid = jbxxid;
    }

    public List<Integer> getJbxxidlist() {
        return jbxxidlist;
    }

    public void setJbxxidlist(List<Integer> jbxxidlist) {
        this.jbxxidlist = jbxxidlist;
    }

    public List<Integer> getZxjbxxidlist() {
        return zxjbxxidlist;
    }

    public void setZxjbxxidlist(List<Integer> zxjbxxidlist) {
        this.zxjbxxidlist = zxjbxxidlist;
    }

    public List<Integer> getZxgcidlist() {
        return zxgcidlist;
    }

    public void setZxgcidlist(List<Integer> zxgcidlist) {
        this.zxgcidlist = zxgcidlist;
    }

    public int getZxgcid() {
        return zxgcid;
    }

    public void setZxgcid(int zxgcid) {
        this.zxgcid = zxgcid;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public List<Integer> getHdids() {
        return hdids;
    }

    public void setHdids(List<Integer> hdids) {
        this.hdids = hdids;
    }

    public Integer getHdid() {
        return hdid;
    }

    public void setHdid(Integer hdid) {
        this.hdid = hdid;
    }

    public Integer getHdlx() {
        return hdlx;
    }

    public int getSfdg() {
        return sfdg;
    }

    public void setSfdg(int sfdg) {
        this.sfdg = sfdg;
    }

    public void setHdlx(Integer hdlx) {
        this.hdlx = hdlx;
    }

    public Integer getGldw() {
        return gldw;
    }

    public void setGldw(Integer gldw) {
        this.gldw = gldw;
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

    public CYhFj getCyhfj() {
        return cyhfj;
    }

    public void setCyhfj(CYhFj cyhfj) {
        this.cyhfj = cyhfj;
    }

    public CYhYdjdqk getCyhybb() {
        return cyhybb;
    }

    public void setCyhybb(CYhYdjdqk cyhybb) {
        this.cyhybb = cyhybb;
    }

    public CYhGgjbxx getJbxx() {
        return jbxx;
    }

    public void setJbxx(CYhGgjbxx jbxx) {
        this.jbxx = jbxx;
    }

    public CYhZxgc getZxgc() {
        return zxgc;
    }

    public void setZxgc(CYhZxgc zxgc) {
        this.zxgc = zxgc;
    }

    public CYhZxjbxx getZxjbxx() {
        return zxjbxx;
    }

    public void setZxjbxx(CYhZxjbxx zxjbxx) {
        this.zxjbxx = zxjbxx;
    }

    public List<CYhYdjdqk> getYbblist() {
        return ybblist;
    }

    public void setYbblist(List<CYhYdjdqk> ybblist) {
        this.ybblist = ybblist;
    }

    public List<CYhFj> getFjlist() {
        return fjlist;
    }

    public void setFjlist(List<CYhFj> fjlist) {
        this.fjlist = fjlist;
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

    public List<Integer> getDelybbids() {
        return delybbids;
    }

    public void setDelybbids(List<Integer> delybbids) {
        this.delybbids = delybbids;
    }

    public List<Integer> getDelfileids() {
        return delfileids;
    }

    public void setDelfileids(List<Integer> delfileids) {
        this.delfileids = delfileids;
    }

    public BaseResult getResult() {
        return result;
    }

    public void setResult(BaseResult result) {
        this.result = result;
    }

    public String getSEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
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

    public Integer getXzqh() {
        return xzqh;
    }

    public void setXzqh(Integer xzqh) {
        this.xzqh = xzqh;
    }

    public CYhYjqtgc getYjqtgc() {
        return yjqtgc;
    }

    public void setYjqtgc(CYhYjqtgc yjqtgc) {
        this.yjqtgc = yjqtgc;
    }

    public Integer getHdaoid() {
        return hdaoid;
    }

    public void setHdaoid(Integer hdaoid) {
        this.hdaoid = hdaoid;
    }

    public Integer getHduanid() {
        return hduanid;
    }

    public void setHduanid(Integer hduanid) {
        this.hduanid = hduanid;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
