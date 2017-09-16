package net.controller;

import common.BaseResult;
import net.modol.*;
import net.service.DangerReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2016/11/2.
 */
@Controller
public class DangerReport
{
    @Resource
    DangerReportService dangerReportService;

    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm");

    //获取港口列表
    @RequestMapping(value = "DangerPortList", method = RequestMethod.POST)
    @ResponseBody
    public List<?> DangerPortList(HttpServletRequest request) throws IOException
    {
        return dangerReportService.DangerPortList();
    }
    //获取危险品类别列表
    @RequestMapping(value = "DangerRankList", method = RequestMethod.POST)
    @ResponseBody
    public List<?> DangerRankList(HttpServletRequest request) throws IOException
    {
        return dangerReportService.DangerRankList();
    }
    //获取货物单位列表
    @RequestMapping(value = "DangerGoodsUnit", method = RequestMethod.POST)
    @ResponseBody
    public List<?> DangerGoodsUnit(HttpServletRequest request) throws IOException
    {
        return dangerReportService.DangerGoodsUnit();
    }
    //提交危货进港报告
    @RequestMapping(value = "CommitDangerShip", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult CommitDangerShip(HttpServletRequest request) throws IOException
    {
        String sn=request.getParameter("Shipname");
        String s1=request.getParameter("From");
        String s2=request.getParameter("To");
        String s3=request.getParameter("Goods");
        String s4=request.getParameter("Rank");
        String s5=request.getParameter("Tons");
        String s6=request.getParameter("Time");
        String s7=request.getParameter("Unit");
        String id=request.getParameter("id");

        CommonPortEN startportEN=dangerReportService.DangerPortByID(Integer.valueOf(s1));
        CommonPortEN endportEN=dangerReportService.DangerPortByID(Integer.valueOf(s2));
        CommonDangerrankEN dangerrankEN=dangerReportService.DangerRankByID(Integer.valueOf(s4));
        CommonGoodsunitEN goodsunitEN=dangerReportService.DangerUnitByID(Integer.valueOf(s7));
        ShipStatusEN statusEN=dangerReportService.DangerStatusByID(1);

        ShipDangerreportEN shipDangerreportEN=null;
        Object obj=dangerReportService.dangrousPortById(Integer.valueOf(id)).getObj();
        if(obj==null)
            shipDangerreportEN=new ShipDangerreportEN();
        else
            shipDangerreportEN=(ShipDangerreportEN)obj;
        shipDangerreportEN.setShipname(sn);
        shipDangerreportEN.setStartportEN(startportEN);
        shipDangerreportEN.setEndportEN(endportEN);
        shipDangerreportEN.setStatus(statusEN);
        shipDangerreportEN.setGoods(s3);
        shipDangerreportEN.setGoodsunitEN(goodsunitEN);
        shipDangerreportEN.setTons(s5);
        shipDangerreportEN.setBerthtime(s6);
        shipDangerreportEN.setDangerrankEN(dangerrankEN);
        shipDangerreportEN.setNumber(dangerReportService.CheckNum());
        shipDangerreportEN.setCommittime(new Date());
        int r = dangerReportService.updateORSave(shipDangerreportEN);

        return new BaseResult(r,"提交成功");
    }
    //按船名获取危货进港报告列表(船户)
    @ResponseBody
    @RequestMapping(value = "DangerShipByName",method = RequestMethod.POST)
    public List<?> getDangerListByName(HttpServletRequest request)
    {
        String name=request.getParameter("Shipname");
        return dangerReportService.DangerShipByName(name);
    }
    //按状态获取危货进港报告列表(内部APP)
    @ResponseBody
    @RequestMapping(value = "DangerShipByTip",method = RequestMethod.POST)
    public List<?> DangerShipByTip(HttpServletRequest request)
    {
        String status=request.getParameter("status");
        return dangerReportService.DangerShipByTip(Integer.valueOf(status));
    }
    //按提示获取危货进港报告列表(内部APP)
    @ResponseBody
    @RequestMapping(value = "DangerShipByKey",method = RequestMethod.POST)
    public List<?> DangerShipByKey(HttpServletRequest request)
    {
        String tip=request.getParameter("tip");
        return dangerReportService.DangerShipByKey(tip);
    }
    //获取作业方式列表
    @ResponseBody
    @RequestMapping(value = "WorkWayList",method = RequestMethod.POST)
    public List<?> WorkWayList(HttpServletRequest request)
    {
        return dangerReportService.WorkWayList();
    }
    //提交危货作业报告
    @RequestMapping(value = "CommitDangerWork", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult CommitDangerWork(HttpServletRequest request) throws IOException
    {
        String wharf=request.getParameter("Wharfname");
        String sn=request.getParameter("Shipname");
        String s1=request.getParameter("FromID");
        String s2=request.getParameter("ToID");
        String s3=request.getParameter("Goods");
        String s4=request.getParameter("RankID");
        String s5=request.getParameter("Tons");
        String s6=request.getParameter("StartTime");
        String s8=request.getParameter("EndTime");
        String s7=request.getParameter("UnitID");
        String s9=request.getParameter("WorkManner");
        String id=request.getParameter("id");


        CommonPortEN startportEN=dangerReportService.DangerPortByID(Integer.valueOf(s1));
        CommonPortEN endportEN=dangerReportService.DangerPortByID(Integer.valueOf(s2));
        CommonDangerrankEN dangerrankEN=dangerReportService.DangerRankByID(Integer.valueOf(s4));
        CommonGoodsunitEN goodsunitEN=dangerReportService.DangerUnitByID(Integer.valueOf(s7));
        ShipStatusEN statusEN=dangerReportService.DangerStatusByID(1);//待审核

        WharfDangerreportEN wharfDangerreportEN=null;
        Object obj= dangerReportService.workPortById(Integer.valueOf(id)).getObj();
        if(obj==null)
            wharfDangerreportEN=new WharfDangerreportEN();
        else
            wharfDangerreportEN=(WharfDangerreportEN)obj;
        wharfDangerreportEN.setWharfEN(wharf);
        wharfDangerreportEN.setShip(sn);
        wharfDangerreportEN.setStartport(startportEN);
        wharfDangerreportEN.setTargetport(endportEN);
        wharfDangerreportEN.setGoodsname(s3);
        wharfDangerreportEN.setRank(dangerrankEN);
        wharfDangerreportEN.setMount(s5);
        wharfDangerreportEN.setUnit(goodsunitEN);
        wharfDangerreportEN.setWorkway(s9);
        wharfDangerreportEN.setPortime(s6);
        wharfDangerreportEN.setEndtime(s8);
        wharfDangerreportEN.setStatus(statusEN);

        int r = dangerReportService.updateORSave(wharfDangerreportEN);

        return new BaseResult(r,"提交成功");
    }
    //按公司名获取危货作业列表(企业)
    @ResponseBody
    @RequestMapping(value = "DangerWorkListByComName",method = RequestMethod.POST)
    public List<?> DangerWorkListByComName(HttpServletRequest request)
    {
        String s=request.getParameter("Name");
        return dangerReportService.DangerWorkListByComName(s);
    }
    //按状态获取危货作业报告列表(内部APP)
    @ResponseBody
    @RequestMapping(value = "DangerWorkByStatus",method = RequestMethod.POST)
    public List<?> DangerWorkByStatus(HttpServletRequest request)
    {
        String status=request.getParameter("status");
        return dangerReportService.DangerWorkByStatus(Integer.valueOf(status));
    }
    //按提示获取危货作业报告列表(内部APP)
    @ResponseBody
    @RequestMapping(value = "DangerWorkByTip",method = RequestMethod.POST)
    public List<?> DangerWorkByTip(HttpServletRequest request)
    {
        String tip=request.getParameter("tip");
        return dangerReportService.DangerWorkByTip(tip);
    }
    //危货进港列表分页（后台WEB）
    @RequestMapping(value = "dangrousport", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult dangrousPort(HttpServletRequest request) throws IOException {
        String temp = request.getParameter("shipname");
        String shipname = temp == null ? "" : temp.trim();
        String page = request.getParameter("page");
        BaseResult result = dangerReportService.dangrousPort(shipname, Integer.parseInt(page));
        return result;
    }

    //按ID获取危货进港报告
    @RequestMapping(value = "dangrousportbyid", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult dangrousPortById(HttpServletRequest request) throws IOException
    {
        String temp = request.getParameter("id");
        int id = temp == null ? 0 : Integer.parseInt(temp.trim());
        BaseResult result = dangerReportService.dangrousPortById(id);
        return result;
    }
    //按ID撤回进港报告
    @RequestMapping(value = "tx_cancel", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult tx_cancel(HttpServletRequest request) throws IOException
    {
        String temp = request.getParameter("id");
        int id = temp == null ? 0 : Integer.parseInt(temp.trim());
        BaseResult result = dangerReportService.dangrousPortById(id);
        ShipDangerreportEN shipDangerreportEN=(ShipDangerreportEN)result.getObj();
        ShipStatusEN statusEN=shipDangerreportEN.getStatus();
        statusEN.setId(0);
        dangerReportService.updateEN(shipDangerreportEN);


        return BaseResult.newResultOK();
    }
    //按ID删除进港报告
    @RequestMapping(value = "etl/temp/remove", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult remove(HttpServletRequest request) throws IOException
    {
        String id = request.getParameter("id");
        BaseResult result = dangerReportService.dangrousPortById(Integer.valueOf(id));
        ShipDangerreportEN shipDangerreportEN=(ShipDangerreportEN)result.getObj();

        dangerReportService.deleteEN(shipDangerreportEN);

        return BaseResult.newResultOK();
    }
    //提交进港报告审核
    @RequestMapping(value = "approvaldangrousport", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult approvalDangrousPort(HttpServletRequest request) throws IOException
    {
        String id = request.getParameter("id");
        String statusid = request.getParameter("statusid");
        String reason = request.getParameter("reason");
        String checker = request.getParameter("checker");
        BaseResult result = dangerReportService.dangrousPortById(Integer.valueOf(id));
        if (result.getObj() != null)
        {
            ShipDangerreportEN en = (ShipDangerreportEN) result.getObj();
            ShipStatusEN sen = dangerReportService.DangerStatusByID(Integer.valueOf(statusid));
            en.setStatus(sen);
            en.setReason(reason);
            en.setChecker(checker);
            dangerReportService.updateEN(en);
        }
        return result;
    }
    //危货作业报告列表分页（web）
    @RequestMapping(value = "workport", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult workPort(HttpServletRequest request) throws IOException
    {
        String temp = request.getParameter("shipname");
        String shipname = temp == null ? "" : temp.trim();
        String page = request.getParameter("page");
        BaseResult result = dangerReportService.workPort(shipname, Integer.parseInt(page));
        return result;
    }

    //按ID获取危货作业报告
    @RequestMapping(value = "workportbyid", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult workPortById(HttpServletRequest request) throws IOException {
        String temp = request.getParameter("id");
        int id = temp == null ? 0 : Integer.parseInt(temp.trim());
        BaseResult result = dangerReportService.workPortById(id);
        return result;
    }
    //按ID撤回作业报告
    @RequestMapping(value = "com/etl/te_cancel", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult te_cancel(HttpServletRequest request) throws IOException
    {
        String temp = request.getParameter("id");
        int id = temp == null ? 0 : Integer.parseInt(temp.trim());
        BaseResult result = dangerReportService.workPortById(id);
        WharfDangerreportEN wharfDangerreportEN=(WharfDangerreportEN)result.getObj();
        ShipStatusEN statusEN=wharfDangerreportEN.getStatus();
        statusEN.setId(0);
        dangerReportService.updateEN(wharfDangerreportEN);


        return BaseResult.newResultOK();
    }
    //按ID删除进港报告
    @RequestMapping(value = "etl/temp/remove1", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult remove1(HttpServletRequest request) throws IOException
    {
        String id = request.getParameter("id");
        BaseResult result = dangerReportService.workPortById(Integer.valueOf(id));
        WharfDangerreportEN wharfDangerreportEN=(WharfDangerreportEN)result.getObj();

        dangerReportService.deleteEN(wharfDangerreportEN);

        return BaseResult.newResultOK();
    }

    //提交违章审核
    @RequestMapping(value = "approvalworkport", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult approvalWorkPort(HttpServletRequest request) throws IOException {
        String id = request.getParameter("id");
        String statusid = request.getParameter("statusid");
        String reason = request.getParameter("reason");
        String checker = request.getParameter("checker");
        BaseResult result = dangerReportService.workPortById(Integer.valueOf(id));
        if (result.getObj() != null) {
            WharfDangerreportEN en = (WharfDangerreportEN) result.getObj();
            ShipStatusEN sen = dangerReportService.DangerStatusByID(Integer.valueOf(statusid));
            en.setStatus(sen);
            en.setReason(reason);
            en.setChecker(checker);
            dangerReportService.updateEN(en);
        }
        return result;
    }

    //统计危货进港报告数量
    @RequestMapping(value = "DangerShipCount", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult DangerShipCount(HttpServletRequest request) throws IOException
    {
        int result = dangerReportService.DangerShipCount();
        return new BaseResult(result,"进港数量");
    }
    //统计危货作业报告数量
    @RequestMapping(value = "DangerComCount", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult DangerComCount(HttpServletRequest request) throws IOException
    {
        int result = dangerReportService.DangerComCount();
        return new BaseResult(result,"作业数量");
    }

}
