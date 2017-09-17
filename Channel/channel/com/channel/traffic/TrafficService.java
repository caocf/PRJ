package com.channel.traffic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.channel.bean.Constants;
import com.channel.bean.GcdBean;
import com.channel.log.LogService;
import com.channel.log.ModuleName;
import com.channel.log.OpName;
import com.channel.model.hd.CHdJgllgcd;
import com.channel.model.hd.CHdRggcd;
import com.channel.model.ll.CLlCbllgc;
import com.channel.model.ll.CLlCbsjhz;
import com.channel.model.ll.CLlGcd;
import com.channel.model.ll.CLlHdllgc;
import com.channel.model.user.CXtUser;
import com.channel.user.dao.UserDao;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.common.utils.FileUtils;
import com.common.utils.HttpPost;
import com.common.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 25019 on 2015/10/20.
 */
@Service("trafficService")
public class TrafficService extends BaseService {
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource
    private LogService logService;
    @Resource(name = "trafficDao")
    private TrafficDao trafficDao;

    public BaseResult queryGcdByIds(List<Integer> ids) {
        BaseQueryRecords<CHdRggcd> rggcds = null;
        BaseQueryRecords<CHdJgllgcd> jggcds = null;
        if (ids != null && ids.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int id : ids) {
                sb.append(id + ",");
            }
            if (sb != null && !"".equals(sb)) {
                sb.deleteCharAt(sb.length() - 1);
            }
            rggcds = this.trafficDao.queryRgGcdByIds(sb.toString());
            jggcds = this.trafficDao.queryJgGcdByIds(sb.toString());
        }
        BaseResult result = new BaseResultOK();
        result.addToMap("rggcds", rggcds);
        result.addToMap("jggcds", jggcds);
        return result;
    }

    public BaseResult queryGcdByHdao(Integer hdaoid) {
        BaseQueryRecords<CHdRggcd> rggcds = this.trafficDao.queryRgGcdByHdao(hdaoid);
        BaseQueryRecords<CHdJgllgcd> jggcds = this.trafficDao.queryJgGcdByHdao(hdaoid);
        BaseResult result = new BaseResultOK();
        result.addToMap("rggcds", rggcds);
        result.addToMap("jggcds", jggcds);
        return result;
    }

    public BaseResult addTraffic(CLlHdllgc hdllgc) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        hdllgc.setCreater(user.getId());
        hdllgc.setCreattime(new Date());
        hdllgc.setUpdatetime(new Date());
        if (hdllgc.getTimeflag() == 2) {
            Date tempstarttime = hdllgc.getStarttime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tempstarttime);
            calendar.add(Calendar.MONTH, 1);
            hdllgc.setEndtime(calendar.getTime());
        }
        this.trafficDao.addTraffic(hdllgc);
        logService.addLog(ModuleName.LLGC, OpName.ADD, "");
        BaseResult result = new BaseResultOK();
        return result;
    }

    public BaseResult delTraffic(List<Integer> ids) {
        for (Integer id : ids) {
            CLlHdllgc hdllgc = this.trafficDao.queryHdllgcByID(id);
            if (hdllgc != null) {
                this.trafficDao.delTraffic(hdllgc);
            }
        }
        logService.addLog(ModuleName.LLGC, OpName.DEL, "");
        BaseResult result = new BaseResultOK();
        return result;
    }

    public BaseResult queryTraffic(List<String> strids, Date starttime, Date endtime, Integer flag, String sEcho, int page, int rows) {
        String strstarttime = "";
        String strendtime = "";
        String strjgs = "";
        String strrgs = "";
        if (starttime != null) {
            strstarttime = DateTimeUtil.getTimeFmt(starttime);
        }
        if (endtime != null) {
            strendtime = DateTimeUtil.getTimeFmt(endtime);
        }
        if (strids != null && strids.size() > 0) {
            StringBuffer jgsb = new StringBuffer();
            StringBuffer rgsb = new StringBuffer();
            for (String strid : strids) {
                int midindex = strid.indexOf("_");
                int gcdtype = Integer.parseInt(strid.substring(0, midindex));
                String gcdid = strid.substring(midindex + 1, strid.length());
                if (gcdtype == Constants.GCZ_JG) {
                    jgsb.append(gcdid + ",");
                } else if (gcdtype == Constants.GCZ_RG) {
                    rgsb.append(gcdid + ",");
                }
            }

            if (jgsb.length() > 0) {
                jgsb.deleteCharAt(jgsb.length() - 1);
            }
            if (rgsb.length() > 0) {
                rgsb.deleteCharAt(rgsb.length() - 1);
            }
            strjgs = jgsb.toString();
            strrgs = rgsb.toString();
        }
        BaseQueryRecords records = this.trafficDao.queryTraffic(strjgs, strrgs, strstarttime, strendtime, flag, page, rows);
        BaseResult result = new BaseResultOK(records);
        result.addToMap("sEcho", sEcho);
        return result;
    }

    public BaseResult updateTraffic(Integer id, CLlHdllgc newhdllgc) {
        CLlHdllgc hdllgc = this.trafficDao.queryHdllgcByID(id);
        if (hdllgc == null) {
            return new BaseResultFailed(Constants.RESULT_TRAFFIC_NOTEXIST, "流量监测不存在");
        } else {
            hdllgc.setGczid(newhdllgc.getGczid());
            hdllgc.setType(newhdllgc.getType());
            hdllgc.setStarttime(newhdllgc.getStarttime());
            hdllgc.setEndtime(newhdllgc.getEndtime());
            hdllgc.setUpcbnum(newhdllgc.getUpcbnum());
            hdllgc.setUptlnum(newhdllgc.getUptlnum());
            hdllgc.setUpjdbnum(newhdllgc.getUpjdbnum());
            hdllgc.setUpfjdbnum(newhdllgc.getUpfjdbnum());
            hdllgc.setUpqtcnum(newhdllgc.getUpqtcnum());
            hdllgc.setDowncbnum(newhdllgc.getDowncbnum());
            hdllgc.setDowntlnum(newhdllgc.getDowntlnum());
            hdllgc.setDownjdbnum(newhdllgc.getDownjdbnum());
            hdllgc.setDownfjdbnum(newhdllgc.getDownfjdbnum());
            hdllgc.setDownqtcnum(newhdllgc.getDownqtcnum());
            hdllgc.setUpcbton(newhdllgc.getUpcbton());
            hdllgc.setUptlton(newhdllgc.getUptlton());
            hdllgc.setUpjdbton(newhdllgc.getUpjdbton());
            hdllgc.setUpfjdbton(newhdllgc.getUpfjdbton());
            hdllgc.setUpqtcton(newhdllgc.getUpqtcton());
            hdllgc.setDowncbton(newhdllgc.getDowncbton());
            hdllgc.setDowntlton(newhdllgc.getDowntlton());
            hdllgc.setDownjdbton(newhdllgc.getDownjdbton());
            hdllgc.setDownfjdbton(newhdllgc.getDownfjdbton());
            hdllgc.setDownqtcton(newhdllgc.getDownqtcton());
            hdllgc.setUpgoodston(newhdllgc.getUpgoodston());
            hdllgc.setUpmtton(newhdllgc.getUpmtton());
            hdllgc.setUpkjclton(newhdllgc.getUpkjclton());
            hdllgc.setUpqtton(newhdllgc.getUpqtton());
            hdllgc.setDowngoodston(newhdllgc.getDowngoodston());
            hdllgc.setDownmtton(newhdllgc.getDownmtton());
            hdllgc.setDownkjclton(newhdllgc.getDownkjclton());
            hdllgc.setDownqtton(newhdllgc.getDownqtton());
            hdllgc.setUpdatetime(new Date());
            logService.addLog(ModuleName.LLGC, OpName.UPDATE, "");
            BaseResult result = new BaseResultOK();
            return result;
        }
    }

    public BaseResult queryTrafficInfo(Integer id) {
        CLlHdllgc hdllgc = this.trafficDao.queryHdllgcByID(id);
        if (hdllgc == null) {
            return new BaseResultFailed(Constants.RESULT_TRAFFIC_NOTEXIST, "流量监测不存在");
        } else {
            BaseQueryRecords records = this.trafficDao.queryTrafficInfo(hdllgc);
            BaseResult result = new BaseResultOK(records);
            return result;
        }
    }

    public BaseResult addShipTraffic(CLlCbllgc cbllgc) {
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        cbllgc.setCreater(user.getId());
        cbllgc.setCreattime(new Date());
        cbllgc.setUpdatetime(new Date());
        this.trafficDao.save(cbllgc);
        logService.addLog(ModuleName.LLGC, OpName.ADD, "");
        BaseResult result = new BaseResultOK();
        return result;
    }

    public BaseResult queryShipTraffic(List<String> strids, Date starttime, Date endtime, String sEcho, int page, int rows) {
        String strstarttime = "";
        String strendtime = "";
        String strjgs = "";
        String strrgs = "";
        if (starttime != null) {
            strstarttime = DateTimeUtil.getTimeFmt(starttime);
        }
        if (endtime != null) {
            strendtime = DateTimeUtil.getTimeFmt(endtime);
        }
        if (strids != null && strids.size() > 0) {
            StringBuffer jgsb = new StringBuffer();
            StringBuffer rgsb = new StringBuffer();
            for (String strid : strids) {
                int midindex = strid.indexOf("_");
                int gcdtype = Integer.parseInt(strid.substring(0, midindex));
                String gcdid = strid.substring(midindex + 1, strid.length());
                if (gcdtype == Constants.GCZ_JG) {
                    jgsb.append(gcdid + ",");
                } else if (gcdtype == Constants.GCZ_RG) {
                    rgsb.append(gcdid + ",");
                }
            }

            if (jgsb.length() > 0) {
                jgsb.deleteCharAt(jgsb.length() - 1);
            }
            if (rgsb.length() > 0) {
                rgsb.deleteCharAt(rgsb.length() - 1);
            }
            strjgs = jgsb.toString();
            strrgs = rgsb.toString();
        }
        BaseQueryRecords records = this.trafficDao.queryShipTraffic(strjgs, strrgs, strstarttime, strendtime, page, rows);
        BaseResult result = new BaseResultOK(records);
        result.addToMap("sEcho", sEcho);
        return result;
    }

    public BaseResult delShipTraffic(List<Integer> ids) {
        for (Integer id : ids) {
            CLlCbllgc cbllgc = this.trafficDao.queryCbllgcByID(id);
            if (cbllgc != null) {
                this.trafficDao.delete(cbllgc);
            }
        }
        logService.addLog(ModuleName.LLGC, OpName.DEL, "");
        BaseResult result = new BaseResultOK();
        return result;
    }

    public BaseResult queryShipTrafficInfo(Integer id) {
        CLlCbllgc cbllgc = this.trafficDao.queryCbllgcByID(id);
        if (cbllgc == null) {
            return new BaseResultFailed(Constants.RESULT_TRAFFIC_NOTEXIST, "流量监测不存在");
        } else {
            BaseQueryRecords records = this.trafficDao.queryShipTrafficInfo(cbllgc);
            BaseResult result = new BaseResultOK(records);
            return result;
        }
    }

    public BaseResult updateShipTraffic(Integer id, CLlCbllgc temp) {
        CLlCbllgc cbllgc = this.trafficDao.queryCbllgcByID(id);
        if (cbllgc == null) {
            return new BaseResultFailed(Constants.RESULT_TRAFFIC_NOTEXIST, "流量监测不存在");
        } else {
            cbllgc.setGcdid(temp.getGcdid());
            cbllgc.setType(temp.getType());
            cbllgc.setStarttime(temp.getStarttime());
            cbllgc.setEndtime(temp.getEndtime());
            cbllgc.setShipname(temp.getShipname());
            cbllgc.setShipdire(temp.getShipdire());
            cbllgc.setShiptype(temp.getShiptype());
            cbllgc.setShipton(temp.getShipton());
            cbllgc.setShipempty(temp.getShipempty());
            cbllgc.setGoodstype(temp.getGoodstype());
            cbllgc.setGoodston(temp.getGoodston());
            cbllgc.setUpdatetime(new Date());
            logService.addLog(ModuleName.LLGC, OpName.UPDATE, "");
            BaseResult result = new BaseResultOK();
            return result;
        }
    }

    public BaseResult queryCbGcd() {
        List<GcdBean> list = new ArrayList<>();
        String url = "http://172.21.25.53/realMonitor/queryAllSite";
        String jsondata = HttpPost.queryData(url);
        JSONObject jsonobj = JSONObject.parseObject(jsondata);
        JSONObject rejson = jsonobj.getJSONObject("records");
        JSONArray arr = rejson.getJSONArray("data");
        Iterator<Object> i = arr.iterator();
        while (i.hasNext()) {
            JSONObject obj = (JSONObject) i.next();
            String id = obj.getString("site");
            String tag = obj.getString("tag");
            String name = obj.getString("hdmc");
            GcdBean gcd = new GcdBean();
            gcd.setId(id);
            gcd.setTag(tag);
            gcd.setName(name);
            list.add(gcd);
        }
        BaseResult result = new BaseResultOK(list);
        return result;
    }

    public BaseResult queryCbLl(String gcdbh, Integer flag, Date starttime, Date endtime) throws UnsupportedEncodingException {
        CLlCbsjhz hz = new CLlCbsjhz();
        String stime = "";
        String etime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        switch (flag) {
            case 1:
                stime = sdf.format(now) + " 00:00:00";
                etime = sdf.format(now) + " 23:59:59";
                break;
            case 10:
                if (starttime != null) {
                    stime = sdf.format(starttime) + " 00:00:00";
                }
                if (endtime != null) {
                    etime = sdf.format(endtime) + " 23:59:59";
                }
                break;
            case 7:
                etime = sdf.format(now) + " 23:59:59";
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                stime = sdf.format(calendar.getTime()) + " 00:00:00";
                break;
            case 30:
                etime = sdf.format(now) + " 23:59:59";
                calendar.add(Calendar.MONTH, -1);
                stime = sdf.format(calendar.getTime()) + " 00:00:00";
                break;
            case 365:
                etime = sdf.format(now) + " 23:59:59";
                calendar.add(Calendar.YEAR, -1);
                stime = sdf.format(calendar.getTime()) + " 00:00:00";
                break;

        }
        String url = "http://172.21.25.53/realMonitor/querySiteLl?siteId=" + gcdbh;
        if (stime != null && !"".equals(stime)) {
            url += "&start=" + URLEncoder.encode(stime, "UTF8");
        }
        if (etime != null && !"".equals(etime)) {
            url += "&end=" +URLEncoder.encode(etime, "UTF8");
        }
        String jsondata = HttpPost.queryData(url);
        JSONObject jsonobj = JSONObject.parseObject(jsondata);
        JSONObject mapjson = jsonobj.getJSONObject("map");
        String name = mapjson.getString("name");
        String cbup = mapjson.getString("up");
        String cbdown = mapjson.getString("down");
        String cbtotal = mapjson.getString("total");
        hz.setName(name);
        hz.setSxzsl(Integer.parseInt(cbup));
        hz.setXxzsl(Integer.parseInt(cbdown));
        hz.setZsl(Integer.parseInt(cbtotal));
        int zsl = 0;
        zsl = hz.getZsl();
        int emptyflag = 0;
        if (zsl > 0) {
            emptyflag = 1;
        }
        BaseResult result = new BaseResultOK(hz);
        result.addToMap("emptyflag", emptyflag);
        result.addToMap("starttime", stime);
        result.addToMap("endtime", etime);
        return result;
    }

    public BaseResult exportCbll(String gcdbh, Integer flag, Date starttime, Date endtime) throws UnsupportedEncodingException {
        CLlCbsjhz hz = new CLlCbsjhz();
        String stime = "";
        String etime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        switch (flag) {
            case 1:
                stime = sdf.format(now) + " 00:00:00";
                etime = sdf.format(now) + " 23:59:59";
                break;
            case 10:
                if (starttime != null) {
                    stime = sdf.format(starttime) + " 00:00:00";
                }
                if (endtime != null) {
                    etime = sdf.format(endtime) + " 23:59:59";
                }
                break;
            case 7:
                etime = sdf.format(now) + " 23:59:59";
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                stime = sdf.format(calendar.getTime()) + " 00:00:00";
                break;
            case 30:
                etime = sdf.format(now) + " 23:59:59";
                calendar.add(Calendar.MONTH, -1);
                stime = sdf.format(calendar.getTime()) + " 00:00:00";
                break;
            case 365:
                etime = sdf.format(now) + " 23:59:59";
                calendar.add(Calendar.YEAR, -1);
                stime = sdf.format(calendar.getTime()) + " 00:00:00";
                break;

        }
        String url = "http://172.21.25.53/realMonitor/querySiteLl?siteId=" + gcdbh;
        if (stime != null && !"".equals(stime)) {
            url += "&start=" + URLEncoder.encode(stime, "UTF8");
        }
        if (etime != null && !"".equals(etime)) {
            url += "&end=" + URLEncoder.encode(etime, "UTF8");
        }
        String jsondata = HttpPost.queryData(url);
        System.out.println(jsondata);
        JSONObject jsonobj = JSONObject.parseObject(jsondata);
        JSONObject mapjson = jsonobj.getJSONObject("map");
        String name = mapjson.getString("name");
        String cbup = mapjson.getString("up");
        String cbdown = mapjson.getString("down");
        String cbtotal = mapjson.getString("total");
        hz.setName(name);
        hz.setSxzsl(Integer.parseInt(cbup));
        hz.setXxzsl(Integer.parseInt(cbdown));
        hz.setZsl(Integer.parseInt(cbtotal));
        int zsl = 0;
        zsl = hz.getZsl();
        int emptyflag = 0;
        if (zsl > 0) {
            emptyflag = 1;
        }
        BaseResultOK result = new BaseResultOK();
        String outfilepath = "";
        File inputfile = null;
        File outputfile = null;
        try {
            String path = this.getRealPath("/");
            inputfile = new File(path + "/template/核查系统船舶流量汇总表.xlsx");
            String filename = inputfile.getName();
            StringBuffer sbf = new StringBuffer(filename);
            sbf.insert(sbf.indexOf("."),
                    DateTimeUtil.getTimeFmt(new Date(), "yyyyMMddHHmmss"));
            outfilepath = path + "baobiao\\" + sbf.toString();
            FileUtils.writeToFile(inputfile, outfilepath);
            outputfile = new File(outfilepath);
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                    outputfile));
            CellStyle cellStyle = workbook.createCellStyle(); // cell下划线样式
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            XSSFSheet sheet = workbook.getSheet("Sheet1");
            if (name != null && !"".equals(name)) {
                sheet.getRow(4).getCell(14).setCellValue("观测点:" + name);
            }
            sheet.getRow(9).getCell(0).setCellValue(stime + "---" + etime);
            sheet.getRow(9).getCell(6).setCellValue(hz.getSxzsl());
            sheet.getRow(11).getCell(6).setCellValue(hz.getXxzsl());
            sheet.getRow(13).getCell(6).setCellValue(hz.getZsl());
            FileOutputStream outfile = new FileOutputStream(outputfile);
            workbook.write(outfile);
            outfile.close();
            logService.addLog(ModuleName.TJBB_SD, OpName.EXPORT, "核查系统船舶流量汇总表");
            result.addToMap("filename", outputfile.getName());
            result.addToMap("filepath", outfilepath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    public BaseResult queryJgGcd() {
        BaseQueryRecords records = this.trafficDao.queryCbGcd();
        BaseResult result = new BaseResultOK(records);
        return result;
    }

    public BaseResult queryJgll(String gcdbh, Integer flag, Date starttime, Date endtime) {
        CLlCbsjhz hz = new CLlCbsjhz();
        String stime = "";
        String etime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        switch (flag) {
            case 1:
                stime = sdf.format(now) + " 00:00:00";
                etime = sdf.format(now) + " 23:59:59";
                break;
            case 10:
                if (starttime != null) {
                    stime = sdf.format(starttime) + " 00:00:00";
                }
                if (endtime != null) {
                    etime = sdf.format(endtime) + " 23:59:59";
                }
                break;
            case 7:
                etime = sdf.format(now) + " 23:59:59";
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                stime = sdf.format(calendar.getTime()) + " 00:00:00";
                break;
            case 30:
                etime = sdf.format(now) + " 23:59:59";
                calendar.add(Calendar.MONTH, -1);
                stime = sdf.format(calendar.getTime()) + " 00:00:00";
                break;
            case 365:
                etime = sdf.format(now) + " 23:59:59";
                calendar.add(Calendar.YEAR, -1);
                stime = sdf.format(calendar.getTime()) + " 00:00:00";
                break;

        }
        BaseQueryRecords records = this.trafficDao.queryCbLl(gcdbh, stime, etime);
        List<Object[]> list = records.getData();
        int emptyflag = 0;
        if (list != null && list.size() > 0) {
            Object[] objs = list.get(0);
            int zsl = Integer.parseInt(String.valueOf(objs[12]));
            if (zsl > 0) {
                emptyflag = 1;
            }
            CLlGcd gcd = null;
            if (!"-1".equals(gcdbh)) {
                gcd = this.trafficDao.queryGcdByID(gcdbh);
            }
            if (gcd != null) {
                hz.setName(gcd.getGcdmc());
            }

            hz.setSxkzzsl(Integer.parseInt(String.valueOf(objs[0])));
            hz.setSxkzzdw(StringUtil.getDoubleScale(objs[1]));
            hz.setSxzzzsl(Integer.parseInt(String.valueOf(objs[2])));
            hz.setSxzzzdw(StringUtil.getDoubleScale(objs[3]));
            hz.setSxzsl(Integer.parseInt(String.valueOf(objs[4])));
            hz.setSxzdw(StringUtil.getDoubleScale(objs[5]));
            hz.setXxkzzsl(Integer.parseInt(String.valueOf(objs[6])));
            hz.setXxkzzdw(StringUtil.getDoubleScale(objs[7]));
            hz.setXxzzzsl(Integer.parseInt(String.valueOf(objs[8])));
            hz.setXxzzzdw(StringUtil.getDoubleScale(objs[9]));
            hz.setXxzsl(Integer.parseInt(String.valueOf(objs[10])));
            hz.setXxzdw(StringUtil.getDoubleScale(objs[11]));
            hz.setZsl(zsl);
            hz.setZdw(StringUtil.getDoubleScale(objs[13]));
        }
        BaseResult result = new BaseResultOK(hz);
        result.addToMap("emptyflag", emptyflag);
        result.addToMap("starttime", stime);
        result.addToMap("endtime", etime);
        return result;
    }

    public BaseResult exportJgll(String gcdbh, Integer flag, Date starttime, Date endtime) {
        CLlCbsjhz hz = new CLlCbsjhz();
        String stime = "";
        String etime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        switch (flag) {
            case 1:
                stime = sdf.format(now) + " 00:00:00";
                etime = sdf.format(now) + " 23:59:59";
                break;
            case 10:
                if (starttime != null) {
                    stime = sdf.format(starttime) + " 00:00:00";
                }
                if (endtime != null) {
                    etime = sdf.format(endtime) + " 23:59:59";
                }
                break;
            case 7:
                etime = sdf.format(now) + " 23:59:59";
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                stime = sdf.format(calendar.getTime()) + " 00:00:00";
                break;
            case 30:
                etime = sdf.format(now) + " 23:59:59";
                calendar.add(Calendar.MONTH, -1);
                stime = sdf.format(calendar.getTime()) + " 00:00:00";
                break;
            case 365:
                etime = sdf.format(now) + " 23:59:59";
                calendar.add(Calendar.YEAR, -1);
                stime = sdf.format(calendar.getTime()) + " 00:00:00";
                break;

        }
        BaseQueryRecords records = this.trafficDao.queryCbLl(gcdbh, stime, etime);
        List<Object[]> list = records.getData();
        CLlGcd gcd = null;
        if (list != null && list.size() > 0) {
            Object[] objs = list.get(0);
            if (!"-1".equals(gcdbh)) {
                gcd = this.trafficDao.queryGcdByID(gcdbh);
            }
            hz.setSxkzzsl(Integer.parseInt(String.valueOf(objs[0])));
            hz.setSxkzzdw(StringUtil.getDoubleScale(objs[1]));
            hz.setSxzzzsl(Integer.parseInt(String.valueOf(objs[2])));
            hz.setSxzzzdw(StringUtil.getDoubleScale(objs[3]));
            hz.setSxzsl(Integer.parseInt(String.valueOf(objs[4])));
            hz.setSxzdw(StringUtil.getDoubleScale(objs[5]));
            hz.setXxkzzsl(Integer.parseInt(String.valueOf(objs[6])));
            hz.setXxkzzdw(StringUtil.getDoubleScale(objs[7]));
            hz.setXxzzzsl(Integer.parseInt(String.valueOf(objs[8])));
            hz.setXxzzzdw(StringUtil.getDoubleScale(objs[9]));
            hz.setXxzsl(Integer.parseInt(String.valueOf(objs[10])));
            hz.setXxzdw(StringUtil.getDoubleScale(objs[11]));
            hz.setZsl(Integer.parseInt(String.valueOf(objs[12])));
            hz.setZdw(StringUtil.getDoubleScale(objs[13]));
        }
        BaseResultOK result = new BaseResultOK();
        String outfilepath = "";
        File inputfile = null;
        File outputfile = null;
        try {
            String path = this.getRealPath("/");
            inputfile = new File(path + "/template/激光流量汇总表.xlsx");
            String filename = inputfile.getName();
            StringBuffer sbf = new StringBuffer(filename);
            sbf.insert(sbf.indexOf("."),
                    DateTimeUtil.getTimeFmt(new Date(), "yyyyMMddHHmmss"));
            outfilepath = path + "baobiao\\" + sbf.toString();
            FileUtils.writeToFile(inputfile, outfilepath);
            outputfile = new File(outfilepath);
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                    outputfile));
            CellStyle cellStyle = workbook.createCellStyle(); // cell下划线样式
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            XSSFSheet sheet = workbook.getSheet("Sheet1");
            if (gcd != null) {
                sheet.getRow(4).getCell(14).setCellValue("观测点:" + gcd.getGcdmc());
            }
            sheet.getRow(9).getCell(0).setCellValue(stime + "---" + etime);
            sheet.getRow(9).getCell(6).setCellValue(hz.getSxzsl());
            sheet.getRow(9).getCell(8).setCellValue(hz.getSxzdw());
            sheet.getRow(9).getCell(10).setCellValue(hz.getSxkzzsl());
            sheet.getRow(9).getCell(12).setCellValue(hz.getSxkzzdw());
            sheet.getRow(9).getCell(14).setCellValue(hz.getSxzzzsl());
            sheet.getRow(9).getCell(16).setCellValue(hz.getSxzzzdw());

            sheet.getRow(11).getCell(6).setCellValue(hz.getXxzsl());
            sheet.getRow(11).getCell(8).setCellValue(hz.getXxzdw());
            sheet.getRow(11).getCell(10).setCellValue(hz.getXxkzzsl());
            sheet.getRow(11).getCell(12).setCellValue(hz.getXxkzzdw());
            sheet.getRow(11).getCell(14).setCellValue(hz.getXxzzzsl());
            sheet.getRow(11).getCell(16).setCellValue(hz.getXxzzzdw());

            sheet.getRow(13).getCell(6).setCellValue(hz.getZsl());
            sheet.getRow(13).getCell(8).setCellValue(hz.getZdw());
            sheet.getRow(13).getCell(10).setCellValue(hz.getSxkzzsl() + hz.getXxkzzsl());
            sheet.getRow(13).getCell(12).setCellValue(hz.getSxkzzdw() + hz.getXxkzzdw());
            sheet.getRow(13).getCell(14).setCellValue(hz.getSxzzzsl() + hz.getXxzzzsl());
            sheet.getRow(13).getCell(16).setCellValue(hz.getSxzzzdw() + hz.getXxzzzdw());
            FileOutputStream outfile = new FileOutputStream(outputfile);
            workbook.write(outfile);
            outfile.close();
            logService.addLog(ModuleName.TJBB_SD, OpName.EXPORT, "激光流量汇总表");
            result.addToMap("filename", outputfile.getName());
            result.addToMap("filepath", outfilepath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}


