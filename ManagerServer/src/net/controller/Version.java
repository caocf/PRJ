package net.controller;

import common.BaseResult;
import common.FileDownload;
import common.FileUpLoad;
import net.modol.VersionEN;
import net.service.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Admin on 2016/7/19.
 */
@Controller
public class Version
{
    @Resource
    SystemService systemService;
    //检测最新版本
    @ResponseBody
    @RequestMapping(value = "versioncheck",method = RequestMethod.POST)
    public BaseResult checkVersion(HttpServletRequest request)
    {
        VersionEN latestverstion=systemService.getLatestVerstion();
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

    //全部APP版本信息列表
    @ResponseBody
    @RequestMapping(value = "AppList",method = RequestMethod.POST)
    public BaseResult AppList(HttpServletRequest request)
    {
        BaseResult baseResult= new BaseResult();
        baseResult.setS1(systemService.AppList());
        return baseResult;
    }
    //上传更新
    @RequestMapping(value = "UpdateAPP",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult UpdateAPP(HttpServletRequest request,Model model) throws Exception
    {
        String s1=request.getParameter("versioncode");
        String s2=request.getParameter("versionname");
        String s3=request.getParameter("updatelog");

        VersionEN saved=systemService.AppByVN(Integer.valueOf(s1));
        if(saved!=null)
        {
            return   new BaseResult(-1,"该版本已存在！");
        }

        String apptath= FileUpLoad.uploadFiles(request,"/app/"+s2+"/","appfile").get(0);
        VersionEN commonVersionEN=new VersionEN();
        commonVersionEN.setAddress(apptath);
        commonVersionEN.setUptime(new Date());
        commonVersionEN.setVersionNum(Integer.valueOf(s1));
        commonVersionEN.setVerstionDec(s2);
        commonVersionEN.setLog(s3);

        systemService.saveEN(commonVersionEN);

        return   new BaseResult(1,"上傳成功");
    }

    //下載最新版本
    @ResponseBody
    @RequestMapping(value = "DownLoadLatest",method = RequestMethod.GET)
    public void DownLoadLatest(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        VersionEN latestverstion=systemService.getLatestVerstion();
        String path=latestverstion.getAddress();

        FileDownload.download(request.getServletContext().getRealPath("/"+path),request,response);
    }

    //
    @ResponseBody
    @RequestMapping(value = "test",method = RequestMethod.GET)
    public void  test(HttpServletRequest request,HttpServletResponse response) throws Exception
    {

        FileDownload.download(request.getServletContext().getRealPath("/image/favicon.ico"),request,response);

        //return "usernl";
    }

}
