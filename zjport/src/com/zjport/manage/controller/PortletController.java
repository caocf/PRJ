package com.zjport.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.base.BaseResult;
import com.zjport.common.service.CommonService;
import com.zjport.manage.service.PortletService;
import com.zjport.util.ChartData;
import com.zjport.util.ChartsProduceInterface;
import com.zjport.util.DisplayData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TWQ on 2016/11/30.
 */
@Controller
@RequestMapping("/system")
public class PortletController {

    @Resource(name = "commonService")
    CommonService commonService;
    @Resource(name = "portletService")
    PortletService portletService;
    @Resource(name = "chartService")
    ChartsProduceInterface chartService;

    BaseResult result;

    /**
     * 跳转portlet管理页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/portletManage")
    public String personCenter(HttpServletRequest request, Model model) {
        return "system/portletManage";// 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 跳转portlet字典页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/portletLib")
    public String portletLib(HttpServletRequest request, Model model) {
        return "system/portletList";// 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * portlet字典信息列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/portletList")
    @ResponseBody
    public BaseResult portletList(HttpServletRequest request, Model model) {

        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));
        String search = request.getParameter("search");

        result = BaseResult.newResultOK(this.portletService.selectPortletList(search, rows, page));
        //records = this.inforService.selectSendInfoList();
        //result = result.setObj(this.inforService.selectSendInfoList());
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 获取portlet数据源
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getPortletLib")
    @ResponseBody
    public BaseResult getPortletLib(HttpServletRequest request, Model model) {
        String all = request.getParameter("all");

        System.out.println(all);
        result = BaseResult.newResultOK(this.portletService.selectPortletLibList(all));
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 获取portlet数据源
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveLayout")
    @ResponseBody
    public BaseResult saveLayout(HttpServletRequest request, Model model) {

        this.portletService.savePortletPart(request);
        /*result = BaseResult.newResultOK(this.portletService.savePortletPart());*/
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 获取portlet字典详细信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getLibInfo")
    @ResponseBody
    public BaseResult getLibInfo(HttpServletRequest request, Model model) {

        result = BaseResult.newResultOK(this.portletService.getLibInfo(request));
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * portlet字典项新增
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/savePortletLib")
    public String savePortletLib(HttpServletRequest request, Model model) {

        this.portletService.savePortletLib(request);
        return "system/portletList"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * portlet字典项删除
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteLib")
    public String deleteLib(HttpServletRequest request, Model model) {
        String libId= request.getParameter("id");
        this.portletService.deleteLib(libId);

        return "system/portletList"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 统计报表option
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getStaticOption")
    @ResponseBody
    public void getStaticOption(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
        JSONObject jsonObject = new JSONObject();

        DisplayData barData = new DisplayData();
        //barData.setTitle("在编教师统计");
        List<String> xName = new ArrayList<String>();
        List<String> nameList = new ArrayList<String>();

        String form = request.getParameter("form");
        Map<String, Object> dataMap = portletService.getDataMap(request);
        String option = "";
        if("line1".equals(dataMap.get("type")) || "line4".equals(dataMap.get("type"))) {
            xName = (List<String>)dataMap.get("x_name");
            List<ChartData> chartData = (List<ChartData>)dataMap.get("chartData");
            barData.setTitle((String)dataMap.get("title"));
            Map<String,  List<Double>> data = new HashMap<String,  List<Double>>();
            for(int i=0;i<chartData.size();i++){
                nameList.add(chartData.get(i).getName());
                data.put(chartData.get(i).getName(), chartData.get(i).getValue());
            }
            barData.setyName(xName);
            barData.setOneByMorename(nameList);
            barData.setOneByMoredata(data);
            if("hbar".equals(form)) {
                option = this.chartService.CreateBilateralBarChart(barData);
            } else {
                option = this.chartService.CreateStandardBar(barData);
            }

        } else {
            nameList = (List<String>)dataMap.get("x_name");
            List<ChartData> chartData = (List<ChartData>)dataMap.get("chartData");
            barData.setTitle((String)dataMap.get("title"));
            Map<String, Double> data = new HashMap<String, Double>();
            for (int i = 0; i < chartData.size(); i++) {
                for(int j = 0;j<nameList.size();j++){
                    data.put(nameList.get(j),chartData.get(i).getValue().get(j));
                }
            }
            barData.setOneByOnename(nameList);
            barData.setOneByOnedata(data);
            if("hbar".equals(form)) {
                option = this.chartService.CreateCrosswiseBar(barData);
            } else if("pie".equals(form)) {
                option = this.chartService.CreateRosePie(barData);
            } else if("line".equals(form)) {
                option = this.chartService.CreateStandardLine(barData);
            } else {
                DisplayData pieData = new DisplayData();
                option = this.chartService.CreateRainbowBarChart(barData,false,pieData);
            }
        }

        jsonObject.put("option", option);

        response.setContentType("application/json; charset=UTF-8");
        Writer out = response.getWriter();
        out.append(jsonObject.toString());
    }
}
