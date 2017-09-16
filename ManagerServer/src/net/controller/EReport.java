package net.controller;

import common.BaseResult;
import net.modol.*;
import net.service.DangerReportService;
import net.service.EReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.*;
import java.lang.System;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 2016/11/2.
 */
@Controller
public class EReport
{
    @Resource
    EReportService eReportService;
    @Resource
    DangerReportService dangerReportService;

    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    //获取普通载货种类列表
    @ResponseBody
    @RequestMapping(value = "GoodsTypeList", method = RequestMethod.POST)
    public List<?> GoodsTypeList(HttpServletRequest request) {
        return eReportService.GoodsTypeList();
    }

    //提交电子报告
    @RequestMapping(value = "CommitEReport", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult CommitDangerShip(HttpServletRequest request) throws Exception
    {
        String reportTypeid = request.getParameter("ReportTypeID");//报告类型
        String sn = request.getParameter("Shipname");
        String s1 = request.getParameter("FromID");
        String s2 = request.getParameter("ToID");
        String s3 = request.getParameter("GoodsTypeID");//载货种类
        String s5 = request.getParameter("Tons");
        String s7 = request.getParameter("UnitID");//重量单位
        String s6 = request.getParameter("Time");
        String s4 = request.getParameter("GasMount");
        String gasTime = request.getParameter("GasTime");
        String inout = request.getParameter("InOutID");


        ShipReportclassEN reportclassEN = eReportService.RePortTypeByID(Integer.valueOf(reportTypeid));
        CommonPortEN fromportEN = dangerReportService.DangerPortByID(Integer.valueOf(s1));
        CommonPortEN endportEN = dangerReportService.DangerPortByID(Integer.valueOf(s2));
        CommonGoodstypeEN goodstypeEN = eReportService.GoodsTypeByID(Integer.valueOf(s3));
        CommonGoodsunitEN goodsunitEN = dangerReportService.DangerUnitByID(Integer.valueOf(s7));
        ShipReporttypeEN inoutp = eReportService.InOrOutByID(Integer.valueOf(inout));
        ShipStatusEN statusEN = dangerReportService.DangerStatusByID(4);//正常状态

        ShipEreportEN ereportEN = new ShipEreportEN();
        ereportEN.setReportclassEN(reportclassEN);
        ereportEN.setShipname(sn);
        ereportEN.setStartport(fromportEN);
        ereportEN.setEndport(endportEN);
        ereportEN.setGoodstypeEN(goodstypeEN);
        ereportEN.setGoodscount(s5);
        ereportEN.setGoodsunitEN(goodsunitEN);
        ereportEN.setPorttime(sd.parse(s6));
        ereportEN.setLastfuelcount(s4);
        ereportEN.setLastfueltime(sd.parse(gasTime));
        ereportEN.setReporttypeEN(inoutp);//进出港
        ereportEN.setStatusEN(statusEN);
        ereportEN.setCommitdate(new Date());

        ShipEreporteditEN shipEreporteditEN=new ShipEreporteditEN();
        shipEreporteditEN.setPortEN(endportEN);
        shipEreporteditEN.setPorttime(sd.parse(s6));
        shipEreporteditEN.setUptime(new Date());
        eReportService.saveEN(shipEreporteditEN);

        Set<ShipEreporteditEN> set=new HashSet<>();
        set.add(shipEreporteditEN);

        ereportEN.setShipEreporteditENSet(set);


        int r = eReportService.saveEN(ereportEN);

        return new BaseResult(r, "提交成功");
    }

    //按状态和船名获取报告报告
    @RequestMapping(value = "ERePortByStatusAndShip", method = RequestMethod.POST)
    @ResponseBody
    public List<?> ERePortByStatusAndShip(HttpServletRequest request) throws Exception
    {
        String id = request.getParameter("StatusID");
        String sn = request.getParameter("Shipname");

        return eReportService.ERePortByStatusAndShip(Integer.valueOf(id), sn);
    }

    //按ID修改电子报告
    @RequestMapping(value = "UpDateERePortByID", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult UpDateERePortByID(HttpServletRequest request) throws Exception
    {
        String id = request.getParameter("id");
        String end = request.getParameter("ToID");
        String time = request.getParameter("Time");

        ShipEreportEN obj = eReportService.ereportById(Integer.parseInt(id));


        //System.out.println(time);
        CommonPortEN endp = dangerReportService.DangerPortByID(Integer.valueOf(end));
        obj.setEndport(endp);
        obj.setPorttime(sd.parse(time));


        ShipEreporteditEN shipEreporteditEN=new ShipEreporteditEN();
        shipEreporteditEN.setPortEN(endp);
        shipEreporteditEN.setPorttime(sd.parse(time));
        shipEreporteditEN.setUptime(new Date());
        eReportService.saveEN(shipEreporteditEN);
        Set<ShipEreporteditEN> set=obj.getShipEreporteditENSet();
        set.add(shipEreporteditEN);

        eReportService.updateEN(obj);

        return BaseResult.newResultOK();
    }

    //按船名获取报告(内部APP)
    @RequestMapping(value = "ERePortByTip", method = RequestMethod.POST)
    @ResponseBody
    public List<?> ERePortByTip(HttpServletRequest request) throws Exception {
        String tip = request.getParameter("tip");

        return eReportService.ERePortByTip(tip);
    }

    /*//按船名提示获取电子报告分页
    @RequestMapping(value = "ERePortByShipPage", method = RequestMethod.POST)
    @ResponseBody
    public List<?> ERePortByShipPage(HttpServletRequest request) throws Exception
    {
        String page = request.getParameter("page");
        String sn = request.getParameter("Shipname");
    }*/

    @RequestMapping(value = "dzbg", method = RequestMethod.GET)
    public String qztjList(HttpServletRequest request, Model model) throws Exception {
        return "WEB-INF/ereport/dzbg";
    }

    @RequestMapping(value = "dzbg_detail", method = RequestMethod.GET)
    public String dzbgDetail(HttpServletRequest request, Model model) throws Exception {
        String id = request.getParameter("id");
        model.addAttribute("id", id);
        return "WEB-INF/ereport/dzbg_detail";
    }

    @RequestMapping(value = "dzbgdt", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult dzbgDt(HttpServletRequest request) throws IOException {
        String shipname = request.getParameter("shipname");
        String page = request.getParameter("page");
        BaseResult result = eReportService.dzbgDt(shipname, Integer.parseInt(page));
        return result;
    }

    @RequestMapping(value = "ereportbyid", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult ereportById(HttpServletRequest request) throws IOException {
        String id = request.getParameter("id");
        if (id == null || "".equals(id)) {
            return new BaseResult(null);
        } else {
            ShipEreportEN obj = eReportService.ereportById(Integer.parseInt(id));
            return new BaseResult(obj);
        }

    }
}
