package net.ghpublic.controller;

import framework.modol.BaseRecords;
import framework.modol.BaseResult;
import framework.tool.HttpRequest;
import net.ghpublic.constant.ManagerURL;
import net.ghpublic.modol.*;
import net.ghpublic.service.ServiceCompany;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by wzd on 2016/5/10.
 */
@Controller
public class ContrCompany
{
    @Resource
    ServiceCompany companyService;

    //按用户名获取公司
    @ResponseBody
    @RequestMapping(value = "MyCompany",method = RequestMethod.POST)
    public BaseRecords<?> MyCompany(HttpServletRequest request)
    {
        return new BaseRecords<>(companyService.MyCompany(request.getParameter("Username")));
    }
    //按公司ID获取公司证书列表
    @ResponseBody
    @RequestMapping(value = "CompanyCertByID",method = RequestMethod.POST)
    public BaseResult CompanyCertByID(HttpServletRequest request)
    {
        return new BaseResult(1,"",companyService.getComCertByID(Integer.valueOf(request.getParameter("ID"))));
    }
    //按用户名获取已审核公司
    @ResponseBody
    @RequestMapping(value = "MyCompanyPass",method = RequestMethod.POST)
    public List<?> MyCompanyPass(HttpServletRequest request)
    {
        return companyService.MyCompanyPass(request.getParameter("Username"));
    }
    //按提示获取公司名列表(审核状态)
    @ResponseBody
    @RequestMapping(value = "companynames",method = RequestMethod.POST)
    public List<String> WharfNames(HttpServletRequest request)
    {
        return companyService.CompanyNamesByTip(request.getParameter("Tip"));
    }
    //按公司名获取公司信息
    @ResponseBody
    @RequestMapping(value = "CompanyByName",method = RequestMethod.POST)
    public CompanyBaseEN CompanyByName(HttpServletRequest request)
    {
        return companyService.CompanyByName(request.getParameter("Name"));
    }
    //获取作业方式列表（内网）
    @ResponseBody
    @RequestMapping(value = "WorkWayList",method = RequestMethod.POST)
    public String WorkWayList(HttpServletRequest request) throws IOException
    {
        String s= HttpRequest.sendPost(ManagerURL.URL+"WorkWayList","");

        return s;
    }
    //提交危货作业报告
    @ResponseBody
    @RequestMapping(value = "CommitDangerWork",method = RequestMethod.POST)
    public String submitDangerReport1(HttpServletRequest request) throws Exception
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


        String s= HttpRequest.sendPost(ManagerURL.URL+"CommitDangerWork","Wharfname="+URLEncoder.encode(wharf,"UTF-8")+"&Shipname="+URLEncoder.encode(sn,"UTF-8")
                +"&Goods="+URLEncoder.encode(s3,"UTF-8")+"&WorkManner="+URLEncoder.encode(s9,"UTF-8")
                +"&FromID="+s1+"&ToID="+s2+ "&RankID="+s4+"&Tons="+s5+"&Time="+s6+"&UnitID="+URLEncoder.encode(s7,"UTF-8")
                +"&StartTime="+s6+"&EndTime="+s8+"&id="+id);
        //System.out.println(s);
        // JSONObject obj= new JSONObject(s);

        return s;
    }
    //按公司名获取危货作业列表(企业)
    @ResponseBody
    @RequestMapping(value = "DangerWorkListByComName",method = RequestMethod.POST)
    public String DangerWorkListByComName(HttpServletRequest request) throws IOException
    {
        String name=request.getParameter("Name");
        System.out.println(name);
        String s= HttpRequest.sendPost(ManagerURL.URL+"DangerWorkListByComName","&Name="+URLEncoder.encode(name,"UTF-8"));

        return s;
    }

    //按ID删除作业报告
    @RequestMapping(value = "etl/temp/remove1", method = RequestMethod.POST)
    @ResponseBody
    public Object remove1(HttpServletRequest request) throws IOException
    {
        String id = request.getParameter("id");
        String s= HttpRequest.sendPost(ManagerURL.URL+"etl/temp/remove1","id="+id);

        return com.alibaba.fastjson.JSONObject.parse(s);
    }
}
