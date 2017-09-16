package net.ghpublic.controller;

import framework.modol.BaseResult;
import framework.tool.FileDownload;
import framework.tool.FileUpLoad;
import net.ghpublic.modol.CommonVersionEN;
import net.ghpublic.service.ServiceCommon;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2016/7/8.
 */
@Controller
public class ContrVersion
{
    @Resource
    ServiceCommon commonService;

    //最新APP版本下载地址
    @ResponseBody
    @RequestMapping(value = "NewestAPPAddress",method = RequestMethod.POST)
    public String NewestAPPAddress(HttpServletRequest request)
    {
        String type=request.getParameter("type");
        return commonService.NewestAPPAddress(type);
    }
    //APP检查版本
    @ResponseBody
    @RequestMapping(value = "versioncheck",method = RequestMethod.POST)
    public BaseResult checkVersion(HttpServletRequest request)
    {
        String type=request.getParameter("type");
        CommonVersionEN latestverstion=commonService.getLatestVerstion(type);
        int androidNum=Integer.valueOf(request.getParameter("VersionNum"));
        if(androidNum>=latestverstion.getVersionNum())
        {
            return new BaseResult(0,"已是最新版本");
        }
        else
        {
            return new BaseResult(1,"检测到新版本",latestverstion);
        }
    }
    //下载最新APP版本安卓
    @ResponseBody
    @RequestMapping(value = "DownLatest",method = RequestMethod.GET)
    public void DownLatest(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
       String type=request.getParameter("type");

        String add= commonService.NewestAPPAddress(type);
        FileDownload.download(request.getServletContext().getRealPath(add),request,  response);
    }
    //全部APP版本信息列表
    @ResponseBody
    @RequestMapping(value = "AppList",method = RequestMethod.POST)
    public BaseResult AppList(HttpServletRequest request)
    {
        BaseResult baseResult= new BaseResult();
        baseResult.setS1(commonService.AppList());
        return baseResult;
    }
    //上传更新
    @RequestMapping(value = "UpdateAPP",method = RequestMethod.POST)
    public String UpdateAPP(HttpServletRequest request,Model model) throws Exception
    {
        String s1=request.getParameter("versioncode");
        String s2=request.getParameter("versionname");
        String s3=request.getParameter("updatelog");

        CommonVersionEN saved=commonService.AppByVN(Integer.valueOf(s1));
        if(saved!=null)
        {
            model.addAttribute("result","该版本已存在！");
            return   "appmanager/result";
        }

        String apptath=FileUpLoad.uploadFiles(request,"app/"+s2+"/","file").get(0);
        CommonVersionEN commonVersionEN=new CommonVersionEN();
        commonVersionEN.setAddress(apptath);
        commonVersionEN.setUptime(new Date());
        commonVersionEN.setVersionNum(Integer.valueOf(s1));
        commonVersionEN.setVerstionDec(s2);
        commonVersionEN.setLog(s3);

        commonService.saveEN(commonVersionEN);

        return   "appmanager/appversioninfo";
    }
}
