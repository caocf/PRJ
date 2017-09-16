package com.zjport.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.base.BaseRecords;
import com.common.base.service.BaseService;
import com.zjport.manage.dao.PortletDao;
import com.zjport.model.*;
import com.zjport.util.ChartData;
import com.zjport.util.CommonConst;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by TWQ on 2016/12/1.
 */
@Service("portletService")
public class PortletService extends BaseService{

    @Resource(name = "portletDao")
    private PortletDao portletDao;

    public List<Portlet> getPortletList(String position, int userId) {
        List<Portlet> portletsList = this.portletDao.getPortletList(position, userId);
        return portletsList;
    }

    public BaseRecords selectPortletList(String search, int rows, int page) {
        BaseRecords records = this.portletDao.selectPortletList(search, rows, page);
        return records;
    }

    public BaseRecords selectPortletLibList(String all) {
        List<Integer> out = new ArrayList<Integer>();
        //添加常用功能、待办事项、相关系统
        String[] array = all.split(",");
        for (int i = 0; i<array.length; i++) {
            String[] id = array[i].split("_");
            out.add(Integer.valueOf(id[0]));
        }

        return this.portletDao.selectPortletLibList(out);
    }

    public void savePortletPart(HttpServletRequest request) {

        HttpSession session = request.getSession();

        int userId = (int)session.getAttribute("session_userid");
        String leftId = request.getParameter("left");
        String rightId = request.getParameter("right");

        TPortlet portlet = this.portletDao.getPortlet(userId);
        String portletMiddleId = UUID.randomUUID().toString();
        if(portlet == null) {
            portlet = new TPortlet();
            portlet.setStUserId(userId);
            portlet.setStPortletPartMiddleId(portletMiddleId);
            portlet.setDtOperate(new Timestamp(System.currentTimeMillis()));
            this.portletDao.save(portlet);
        } else {
            portletMiddleId = portlet.getStPortletPartMiddleId();

            List<TPortletPart> partList = this.portletDao.getpartListByMiddleId(portletMiddleId);
            for(TPortletPart part:partList) {
                this.portletDao.delete(part);
            }
            //this.portletDao.delete(new ObjectQuery(TPortletPart.class,"stPortletPartMiddleId",portletMiddleId));
        }
        if(!"".equals(leftId)) {
            String[] left = leftId.split(",");
            for (int i = 0; i < left.length; i++) {
                TPortletPart part = new TPortletPart();
                String[] array = left[i].split("_");
                part.setStPortletPartMiddleId(portletMiddleId);
                part.setStColor(array[1]);
                part.setStDisplayForm(array[2]);
                part.setStOrder(i + 1);
                part.setStPortletLibId(Integer.valueOf(array[0]));
                part.setStPosition(CommonConst.PORTLET_LEFT);
                this.portletDao.saveOrUpdate(part);

            }
        }
        if(!"".equals(rightId)) {
            String[] right = rightId.split(",");
            for(int i=0; i<right.length; i++) {
                TPortletPart part = new TPortletPart();
                String[] array = right[i].split("_");
                part.setStPortletPartMiddleId(portletMiddleId);
                part.setStColor(array[1]);
                part.setStDisplayForm(array[2]);
                part.setStOrder(i+1);
                part.setStPortletLibId(Integer.valueOf(array[0]));
                part.setStPosition(CommonConst.PORTLET_RIGHT);
                this.portletDao.save(part);
            }
        }


        List<Portlet> portletLeftList = this.getPortletList(CommonConst.PORTLET_LEFT,userId);
        List<Portlet> portletRightList = this.getPortletList(CommonConst.PORTLET_RIGHT,userId);
    }

    public void savePortletLib(HttpServletRequest request) {
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");

        String moduleName = request.getParameter("moduleName");
        String moduleId = request.getParameter("moduleId");
        String url = request.getParameter("url");
        String describe = request.getParameter("describe");

        TPortletLib lib = new TPortletLib();
        lib.setStModuleName(moduleName);
        lib.setStModuleId(moduleId);
        lib.setStDescribe(describe);
        lib.setStIcon("");
        lib.setStUrl(url);
        lib.setStUserName(user.getStUserName());
        lib.setDtCreate(new Timestamp(System.currentTimeMillis()));
        this.portletDao.saveOrUpdate(lib);
    }

    public BaseRecords getLibInfo(HttpServletRequest request) {
        String libId = request.getParameter("libId");
        return this.portletDao.getPortletLibInfoById(Integer.valueOf(libId));
    }

    public void deleteLib(String libId) {
        TPortletLib lib = this.getPortletLibById(libId);
        this.portletDao.delete(lib);
    }

    public TPortletLib getPortletLibById(String libId) {
        return this.portletDao.getPortletLibById(Integer.valueOf(libId));
    }

    public TPortletLib getPortletLibByModuleId(String moduleId) {
        return this.portletDao.getPortletLibByModuleId(moduleId);
    }

    public Map<String, Object> getDataMap(HttpServletRequest request) {

        String moduleId = request.getParameter("moduleId");
        TPortletLib lib = this.getPortletLibByModuleId(moduleId);

        String url =lib.getStUrl();
        String json= this.getHttpResponse(url);

        if("line1".equals(lib.getStType())) {
            return this.line1(json,lib.getStModuleName(),lib.getStType());
        } else if("line2".equals(lib.getStType())) {
            return this.line2(json,lib.getStModuleName(),lib.getStType());
        } else if("line3".equals(lib.getStType())){
            return this.line3(json,lib.getStModuleName(),lib.getStType());
        } else if("line4".equals(lib.getStType())){
            return this.line4(json,lib.getStModuleName(),lib.getStType());
        } else if("line5".equals(lib.getStType())){
            return this.line5(json,lib.getStModuleName(),lib.getStType());
        } else {
            return new HashMap<String, Object>();
        }
    }

    //货物吞吐量
    public Map<String, Object> line1(String json, String title, String type) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<ChartData> chartData = new ArrayList<ChartData>();
        List<String> x_name = new ArrayList<String>();
        ChartData chart = new ChartData();
        List<Double> chart_value = new ArrayList<Double>();
        List<String> y_name = new ArrayList<String>();

        JSONObject obj = JSON.parseObject(json);
        JSONObject objResult = obj.getJSONObject("result");
        JSONArray name = objResult.getJSONArray("list");
        JSONArray array = objResult.getJSONArray("list2");

        for(int i = 0 ;i<name.size(); i++) {
            x_name.add(name.get(i).toString());
        }
        chart = new ChartData();
        chart.setName("总量");
        chart_value = new ArrayList<Double>();
        for(int i = 0 ;i<array.size(); i++) {
            JSONObject obj2 = array.getJSONObject(i);
            chart_value.add(Double.valueOf(obj2.get("total").toString()));
        }
        chart.setValue(chart_value);
        chartData.add(chart);

        chart = new ChartData();
        chart.setName("进港流量");
        chart_value = new ArrayList<Double>();
        for(int i = 0 ;i<array.size(); i++) {
            JSONObject obj2 = array.getJSONObject(i);
            chart_value.add(Double.valueOf(obj2.get("jg").toString()));
        }
        chart.setValue(chart_value);
        chartData.add(chart);

        /*chart = new ChartData();
        chart.setName("jzx");
        chart_value = new ArrayList<Double>();
        for(int i = 0 ;i<array.size(); i++) {
            JSONObject obj2 = array.getJSONObject(i);
            chart_value.add(Double.valueOf(obj2.get("jzx").toString()));
        }
        chart.setValue(chart_value);
        chartData.add(chart);*/

        chart = new ChartData();
        chart.setName("出港流量");
        chart_value = new ArrayList<Double>();
        for(int i = 0 ;i<array.size(); i++) {
            JSONObject obj2 = array.getJSONObject(i);
            chart_value.add(Double.valueOf(obj2.get("cg").toString()));
        }
        chart.setValue(chart_value);
        chartData.add(chart);


        dataMap.put("title",title);
        dataMap.put("type",type);
        dataMap.put("x_name",x_name);
        dataMap.put("chartData",chartData);
        return dataMap;
    }

    //注册运量
    public Map<String, Object> line2(String json, String title, String type) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<ChartData> chartData = new ArrayList<ChartData>();
        List<String> x_name = new ArrayList<String>();
        ChartData chart = new ChartData();
        List<Double> chart_value = new ArrayList<Double>();

        JSONObject obj = JSON.parseObject(json);
        JSONObject objResult = obj.getJSONObject("result");
        JSONObject record = objResult.getJSONObject("records");
        JSONArray data = record.getJSONArray("data");
        for(int i = 0 ;i<data.size(); i++) {
            JSONObject obj2 = data.getJSONObject(i);
            x_name.add(obj2.get("year").toString());

            chart_value.add(Double.valueOf(obj2.get("weight").toString()));
        }

        chart.setValue(chart_value);
        chartData.add(chart);

        dataMap.put("title",title);
        dataMap.put("type",type);
        dataMap.put("x_name",x_name);
        dataMap.put("chartData",chartData);
        return dataMap;
    }

    //航道里程
    public Map<String, Object> line3(String json, String title, String type) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<ChartData> chartData = new ArrayList<ChartData>();
        List<String> x_name = new ArrayList<String>();
        ChartData chart = new ChartData();
        List<Double> chart_value = new ArrayList<Double>();

        JSONObject obj = JSON.parseObject(json);
        JSONObject objResult = obj.getJSONObject("result");
        JSONObject records = objResult.getJSONObject("records");
        JSONArray data = records.getJSONArray("data");
        for(int i = 0 ;i<data.size(); i++) {
            JSONObject obj2 = data.getJSONObject(i);
            x_name.add(obj2.get("hddj").toString());
            chart_value.add(Double.valueOf(obj2.get("hdlc").toString()));
        }

        chart.setValue(chart_value);
        chartData.add(chart);

        dataMap.put("title",title);
        dataMap.put("type",type);
        dataMap.put("x_name",x_name);
        dataMap.put("chartData",chartData);
        return dataMap;
    }

    //企业泊位
    public Map<String, Object> line4(String json, String title, String type) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<ChartData> chartData = new ArrayList<ChartData>();
        List<String> x_name = new ArrayList<String>();
        ChartData chart = new ChartData();
        List<Double> chart_value = new ArrayList<Double>();

        JSONObject obj = JSON.parseObject(json);
        JSONArray data = obj.getJSONArray("list2");

        for(int i = 0 ;i<data.size(); i++) {
            JSONObject obj2 = data.getJSONObject(i);
            x_name.add(obj2.get("gqmc").toString());
        }

        chart = new ChartData();
        chart.setName("企业");
        chart_value = new ArrayList<Double>();
        for(int i = 0 ;i<data.size(); i++) {
            JSONObject obj2 = data.getJSONObject(i);
            chart_value.add(Double.valueOf(obj2.get("qy").toString()));
        }
        chart.setValue(chart_value);
        chartData.add(chart);

        chart = new ChartData();
        chart.setName("泊位");
        chart_value = new ArrayList<Double>();
        for(int i = 0 ;i<data.size(); i++) {
            JSONObject obj2 = data.getJSONObject(i);
            chart_value.add(Double.valueOf(obj2.get("bw").toString()));
        }
        chart.setValue(chart_value);
        chartData.add(chart);

        dataMap.put("title",title);
        dataMap.put("type",type);
        dataMap.put("x_name",x_name);
        dataMap.put("chartData",chartData);
        return dataMap;
    }

    //集装箱吞吐量
    public Map<String, Object> line5(String json, String title, String type) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<ChartData> chartData = new ArrayList<ChartData>();
        List<String> x_name = new ArrayList<String>();
        ChartData chart = new ChartData();
        List<Double> chart_value = new ArrayList<Double>();

        JSONObject obj = JSON.parseObject(json);
        JSONObject objResult = obj.getJSONObject("result");
        JSONArray name = objResult.getJSONArray("list");
        JSONArray array = objResult.getJSONArray("list2");

        for(int i = 0 ;i<name.size(); i++) {
            x_name.add(name.get(i).toString());
        }
        for(int i = 0 ;i<array.size(); i++) {
            JSONObject obj2 = array.getJSONObject(i);
            chart_value.add(Double.valueOf(obj2.get("jzx").toString()));
        }
        chart.setValue(chart_value);
        chartData.add(chart);

        chart.setValue(chart_value);
        chartData.add(chart);

        dataMap.put("title",title);
        dataMap.put("type",type);
        dataMap.put("x_name",x_name);
        dataMap.put("chartData",chartData);
        return dataMap;
    }

    public static String getHttpResponse(String allConfigUrl) {
        BufferedReader in = null;
        StringBuffer result = null;
        try {

            URI uri = new URI(allConfigUrl);
            URL url = uri.toURL();
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            connection.connect();

            result = new StringBuffer();
            //读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return null;

    }
}
