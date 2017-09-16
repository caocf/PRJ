package net.controller;

import common.BaseResult;
import common.DataList;
import net.modol.CommonDangerrankEN;
import net.modol.CommonGoodsunitEN;
import net.modol.CommonPortEN;
import net.modol.LawTypeEN;
import net.service.DangerReportService;
import net.service.DictoryService;
import net.service.LawService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Admin on 2016/11/22.
 */
@Controller
public class Dictory
{
    @Resource
    DictoryService dictoryService;
    @Resource
    DangerReportService dangerReportService;
    @Resource
    LawService lawService;

    //获取字典组别
    @RequestMapping(value = "DictoryGroup", method = RequestMethod.POST)
    @ResponseBody
    public List<?> DictoryGroup(HttpServletRequest request) throws IOException
    {
        return dictoryService.DictoryGroup();
    }
    //按分组ID和提示查询字典列表(分页)
    @RequestMapping(value = "DictoryListByGroupID", method = RequestMethod.POST)
    @ResponseBody
    public DataList DictoryListByGroupID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String tip=request.getParameter("tip");
        String page=request.getParameter("page");
        return dictoryService.DictoryListByGroupID(Integer.valueOf(id),tip,Integer.valueOf(page));
    }

    //港口列表分页
    @RequestMapping(value = "PortListPage", method = RequestMethod.POST)
    @ResponseBody
    public DataList PortListPage(HttpServletRequest request) throws IOException
    {
        String page=request.getParameter("page");

        return dictoryService.PortListPage(Integer.valueOf(page));
    }
    //编辑港口字典-修改
    @RequestMapping(value = "PortUp", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult PortUp(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String word=request.getParameter("word");

         CommonPortEN p=dangerReportService.DangerPortByID(Integer.valueOf(id));
        p.setPortname(word);
        dangerReportService.updateEN(p);

        return BaseResult.newResultOK();
    }
    //编辑港口字典-增
    @RequestMapping(value = "PorAdd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult PorAdd(HttpServletRequest request) throws IOException
    {
        String word=request.getParameter("word");
        CommonPortEN portEN=new CommonPortEN();
        portEN.setPortname(word);
        dangerReportService.saveEN(portEN);

        return  BaseResult.newResultOK();
    }

    //执法类别列表分页
    @RequestMapping(value = "LawTypeListPage", method = RequestMethod.POST)
    @ResponseBody
    public DataList LawTypeListPage(HttpServletRequest request) throws IOException
    {
        String page=request.getParameter("page");

        return dictoryService.LawTypeListPage(Integer.valueOf(page));
    }
    //编辑执法类别字典-修改
    @RequestMapping(value = "LawTypeUp", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult LawTypeUp(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String word=request.getParameter("word");

        LawTypeEN lawTypeEN=lawService.LawTypeByID(Integer.valueOf(id));
        lawTypeEN.setStatus(word);
        dangerReportService.updateEN(lawTypeEN);

        return BaseResult.newResultOK();
    }
    //编辑执法类别字典-增
    @RequestMapping(value = "LawTypeAdd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult LawTypeAdd(HttpServletRequest request) throws IOException
    {
        String word=request.getParameter("word");
        LawTypeEN lawTypeEN=new LawTypeEN();
        lawTypeEN.setStatus(word);
        dangerReportService.saveEN(lawTypeEN);

        return  BaseResult.newResultOK();
    }

    //危货品类别列表分页
    @RequestMapping(value = "DangerListPage", method = RequestMethod.POST)
    @ResponseBody
    public DataList DangerListPage(HttpServletRequest request) throws IOException
    {
        String page=request.getParameter("page");

        return dictoryService.DangerListPage(Integer.valueOf(page));
    }
    //编辑执危货品别字典-修改
    @RequestMapping(value = "DangerUp", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult DangerUp(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String word=request.getParameter("word");

        CommonDangerrankEN dangerrankEN=dangerReportService.DangerRankByID(Integer.valueOf(id));
        dangerrankEN.setRankname(word);
        dangerReportService.updateEN(dangerrankEN);

        return BaseResult.newResultOK();
    }
    //编辑危货品类别字典-增
    @RequestMapping(value = "DangerAdd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult DangerAdd(HttpServletRequest request) throws IOException
    {
        String word=request.getParameter("word");
        CommonDangerrankEN dangerrankEN=new CommonDangerrankEN();
        dangerrankEN.setRankname(word);
        dangerReportService.saveEN(dangerrankEN);

        return  BaseResult.newResultOK();
    }

    //重量单位类别列表分页
    @RequestMapping(value = "UnitListPage", method = RequestMethod.POST)
    @ResponseBody
    public DataList UnitListPage(HttpServletRequest request) throws IOException
    {
        String page=request.getParameter("page");

        return dictoryService.UnitListPage(Integer.valueOf(page));
    }
    //编辑执重量单位类别字典-修改
    @RequestMapping(value = "UnitUp", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult UnitUp(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String word=request.getParameter("word");

        CommonGoodsunitEN goodsunitEN=dangerReportService.DangerUnitByID(Integer.valueOf(id));
        goodsunitEN.setUnitname(word);
        dangerReportService.updateEN(goodsunitEN);

        return BaseResult.newResultOK();
    }
    //编辑危货品类别字典-增
    @RequestMapping(value = "UnitAdd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult UnitAdd(HttpServletRequest request) throws IOException
    {
        String word=request.getParameter("word");
        CommonGoodsunitEN goodsunitEN=new CommonGoodsunitEN();
        goodsunitEN.setUnitname(word);
        dangerReportService.saveEN(goodsunitEN);

        return  BaseResult.newResultOK();
    }

}
