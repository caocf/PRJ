package com.channel.traffic;

import com.channel.model.ll.CLlCbllgc;
import com.channel.model.ll.CLlHdllgc;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Created by 25019 on 2015/10/20.
 */
public class TrafficAction extends BaseAction {
    @Resource(name = "trafficService")
    private TrafficService trafficService;
    private Integer id;
    private String gcdbh;
    private CLlHdllgc hdllgc;
    private CLlCbllgc cbllgc;
    private Integer gczid;
    private Date starttime;
    private Date endtime;
    private Integer flag;
    private String strstarttime;
    private String strendtime;
    private List<Integer> ids;
    private List<String> strids;
    private String sEcho;
    private BaseResult result;
    private int page;
    private int rows;
    private List<Integer> hdaoids;
    private int hdaoid = -1;
    private String filename;// 文件名
    private long dlFilelength; // 下载文件长度
    private InputStream dlFile; // 下载文件流
    private String dlFileName; // 下载文件名

    /**
     * 根据航道编号查询观测站
     *
     * @return
     */
    public String queryGcdByHdao() {
        result = this.trafficService.queryGcdByHdao(hdaoid);
        return "json";
    }

    /**
     * 根据航道id查询观测站
     *
     * @return
     */
    public String queryGcdByIds() {
        result = this.trafficService.queryGcdByIds(ids);
        return "json";
    }

    /**
     * 查询观测记录
     *
     * @return
     */
    public String queryTraffic() {
        result = this.trafficService.queryTraffic(strids, starttime, endtime, flag, sEcho, page, rows);
        return "json";
    }

    /**
     * 查询观测记录详情
     *
     * @return
     */
    public String queryTrafficInfo() {
        result = this.trafficService.queryTrafficInfo(id);
        return "json";
    }

    /**
     * 添加观测记录
     *
     * @return
     */
    public String addTraffic() {
        result = this.trafficService.addTraffic(hdllgc);
        return "json";
    }

    /**
     * 更新观测记录
     *
     * @return
     */
    public String updateTraffic() {
        result = this.trafficService.updateTraffic(id, hdllgc);
        return "json";
    }

    /**
     * 删除观测记录
     *
     * @return
     */
    public String delTraffic() {
        result = this.trafficService.delTraffic(ids);
        return "json";
    }

    /**
     * 查询船舶观测记录
     *
     * @return
     */
    public String queryShipTraffic() {
        result = this.trafficService.queryShipTraffic(strids, starttime, endtime, sEcho, page, rows);
        return "json";
    }

    /**
     * 查询船舶观测记录详情
     *
     * @return
     */
    public String queryShipTrafficInfo() {
        result = this.trafficService.queryShipTrafficInfo(id);
        return "json";
    }

    /**
     * 添加船舶观测记录
     *
     * @return
     */
    public String addShipTraffic() {
        result = this.trafficService.addShipTraffic(cbllgc);
        return "json";
    }

    /**
     * 更新船舶观测记录
     *
     * @return
     */
    public String updateShipTraffic() {
        result = this.trafficService.updateShipTraffic(id, cbllgc);
        return "json";
    }

    /**
     * 删除船舶观测记录
     *
     * @return
     */
    public String delShipTraffic() {
        result = this.trafficService.delShipTraffic(ids);
        return "json";
    }

    /**
     * 所有船舶观测点
     *
     * @return
     */
    public String queryCbGcd() {
        result = this.trafficService.queryCbGcd();
        return "json";
    }

    /**
     * 汇总船舶观测数据
     *
     * @return
     */
    public String queryCbll() throws UnsupportedEncodingException {
        result = this.trafficService.queryCbLl(gcdbh, flag, starttime, endtime);
        session.put("cbll", result.getObj());
        session.put("mapinfo", result.getMap());
        return "baobiao";
    }

    /**
     * 导出核查系统船舶流量汇总表
     */
    public String exportCbll() throws Exception {
        result = this.trafficService.exportCbll(gcdbh, flag, starttime, endtime);
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
     * 所有激光观测点
     *
     * @return
     */
    public String queryJgGcd() {
        result = this.trafficService.queryJgGcd();
        return "json";
    }

    /**
     * 汇总激光观测数据
     *
     * @return
     */
    public String queryJgll() {
        result = this.trafficService.queryJgll(gcdbh, flag, starttime, endtime);
        session.put("cbll", result.getObj());
        session.put("mapinfo", result.getMap());
        return "baobiao";
    }

    /**
     * 导出激光流量汇总表
     */
    public String exportJgll() throws Exception {
        result = this.trafficService.exportJgll(gcdbh, flag, starttime, endtime);
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

    public CLlCbllgc getCbllgc() {
        return cbllgc;
    }

    public void setCbllgc(CLlCbllgc cbllgc) {
        this.cbllgc = cbllgc;
    }

    public int getHdaoid() {
        return hdaoid;
    }

    public void setHdaoid(int hdaoid) {
        this.hdaoid = hdaoid;
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

    public CLlHdllgc getHdllgc() {
        return hdllgc;
    }

    public void setHdllgc(CLlHdllgc hdllgc) {
        this.hdllgc = hdllgc;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
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

    public List<Integer> getHdaoids() {
        return hdaoids;
    }

    public void setHdaoids(List<Integer> hdaoids) {
        this.hdaoids = hdaoids;
    }

    public BaseResult getResult() {
        return result;
    }

    public void setResult(BaseResult result) {
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGczid() {
        return gczid;
    }

    public void setGczid(Integer gczid) {
        this.gczid = gczid;
    }

    public List<String> getStrids() {
        return strids;
    }

    public void setStrids(List<String> strids) {
        this.strids = strids;
    }

    public String getGcdbh() {
        return gcdbh;
    }

    public void setGcdbh(String gcdbh) {
        this.gcdbh = gcdbh;
    }

    public String getStrstarttime() {
        return strstarttime;
    }

    public void setStrstarttime(String strstarttime) {
        this.strstarttime = strstarttime;
    }

    public String getStrendtime() {
        return strendtime;
    }

    public void setStrendtime(String strendtime) {
        this.strendtime = strendtime;
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
}
