package net.ghpublic.controller;
import framework.modol.BaseRecords;
import framework.modol.BaseResult;
import framework.modol.DataList;
import net.ghpublic.modol.CommonAdviceEN;
import net.ghpublic.modol.CommonPermissionEN;
import net.ghpublic.modol.ModelDynmicNews;
import net.ghpublic.service.ServiceCommon;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangzedong on 2016/4/19.
 */
@Controller
public class ContrCommon
{
    @Resource(name = "serviceAdvice")
    ServiceCommon commonService;
    //提交意见
    @ResponseBody
    @RequestMapping(value = "sendadvice",method = RequestMethod.POST)
    public BaseResult sendAdvice(HttpServletRequest request)
    {
        CommonAdviceEN av=new CommonAdviceEN();
        av.setCity(request.getParameter("city"));
        av.setContact(request.getParameter("tel"));
        av.setCotent(request.getParameter("content"));
        av.setName(request.getParameter("name"));
        av.setTime(new Date());

        int result=commonService.saveEN(av);
        //System.out.println("ssssssss");
        return new BaseResult(result,"");
    }
    //分页按提示获取意见
    @ResponseBody
    @RequestMapping(value = "AdviceByTip",method = RequestMethod.POST)
    public DataList AdviceByTip(HttpServletRequest request)
    {
        String tip=request.getParameter("tip");
        String page=request.getParameter("page");
        //System.out.println("ssssssss");
        return commonService.AdviceByTip(tip,Integer.valueOf(page));
    }
    //按ID获取意见
    @RequestMapping(value ="AdviceByID",method = RequestMethod.POST )
    @ResponseBody
    public CommonAdviceEN  AdviceByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        return commonService.AdviceByID(Integer.valueOf(id));
    }
    //获取许可列表
    @ResponseBody
    @RequestMapping(value = "permissionlist",method = RequestMethod.POST)
    public BaseRecords<CommonPermissionEN> getPermitByUser(HttpServletRequest request)
    {
        return new BaseRecords<>(commonService.getPermitByUser(Integer.valueOf(request.getParameter("Userid"))));
    }
    //获取地区列表
    @ResponseBody
    @RequestMapping(value = "AreaList",method = RequestMethod.POST)
    public List<?> AreaList(HttpServletRequest request)
    {
        return commonService.AreaList();
    }

    //获取知识库
    @ResponseBody
    @RequestMapping(value = "CodexList",method = RequestMethod.POST)
    public void CodexList() throws Exception
    {
        List<ModelDynmicNews> list=new ArrayList<>();
        try
        {
            URL url;
            HttpURLConnection httpConn = null;
            InputStreamReader input = null;
            BufferedReader bufReader = null;
            StringBuilder contentBuf = null;
            String line;
            String str = "http://zjgh.zjt.gov.cn/col/col6718/index.html";
            url = new URL(str);
            httpConn = (HttpURLConnection) url.openConnection();
            input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
            bufReader = new BufferedReader(input);

            contentBuf = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                contentBuf.append(line);
            }
            String buf = contentBuf.toString();
            String[] herf = buf.split("urls\\[i\\]=\\'");
            String[] headers = buf.split("headers\\[i\\]=\\'");
            String[] year = buf.split("year\\[i\\]=\\'");
            String[] month = buf.split("month\\[i\\]=\\'");
            String[] day = buf.split("day\\[i\\]=\\'");
            for (int i = 1; i < herf.length; i++)
            {
                ModelDynmicNews model = new ModelDynmicNews();
                model.setUrl("http://zjgh.zjt.gov.cn"
                        + herf[i].split("\\';")[0]);
                model.setTitile(headers[i].split("\\';")[0]);
                model.setDate(year[i].split("\\';")[0] + "-"
                        + month[i].split("\\';")[0] + "-"
                        + day[i].split("\\';")[0]);
                //System.out.println(model.getUrl() + "-" + model.getTitile());
                list.add(model);
                //System.out.println(headers[i].split("\\';")[0]);

            }
        } catch (Exception e)
        {
            // throw new Exception("网站正在维护中，暂无相关数据......");
        }
        for(ModelDynmicNews modelDynmicNews:list)
        {
            findDetailInfo(modelDynmicNews);
        }
    }

    public void findDetailInfo(ModelDynmicNews modelPortDynmicNews) throws Exception
    {
        URL url;
        HttpURLConnection httpConn = null;
        InputStreamReader input = null;
        BufferedReader bufReader = null;
        StringBuilder contentBuf = null;
        String line;
        try {
            url = new URL(modelPortDynmicNews.getUrl());
            httpConn = (HttpURLConnection) url.openConnection();
            input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
            bufReader = new BufferedReader(input);

            contentBuf = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                contentBuf.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        String buf = contentBuf.toString();

        String content = buf
                .split("\\<table width=\\\"100%\\\" border=\\\"0\\\" cellpadding=\\\"0\\\" cellspacing=\\\"0\\\" style=\\\"border-bottom\\:solid 1px \\#CCC\\;\\\"\\>")[1]
                .split("\\<\\!--\\<\\$\\[信息内容\\]\\>end--\\>\\<\\/td\\>\\<\\/tr\\>\\<\\/table\\>")[0];
        content = "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-bottom:solid 1px #CCC;\">"
                + content + "</td></tr></table>";
        content = content
                .replace(">发布日期",
                        " style='font-size:17px;line-height:27px; padding-top:10px;'>发布日期");
        content = content
                .replace(
                        "字号：[ <a href='javascript:doZoom(16)' class='hui'>大</a> <a href='javascript:doZoom(14)' class='hui'>中</a> <a href='javascript:doZoom(12)' class='hui'>小</a> ]",
                        "")
                .replace("border-bottom:1px #ccc solid;", "")
                .replace("border-bottom:solid 1px #CCC;", "")
                .replace("font-size:16px", "font-size:22px")
                .replace("class=zhengwen",
                        "style='font-size:17px;line-height:27px; padding:10px;'")
                .replace("style=\"line-height:22px; padding:10px;\"",
                        "style='font-size:17px;line-height:27px; padding:10px;'")
                .replace("<P style=\"LINE-HEIGHT: 200%\"><SPAN style=\"FONT-SIZE: 10.5pt; COLOR: black; LINE-HEIGHT: 200%\">",
                        "");

        modelPortDynmicNews.setContenct(content);
        commonService.saveEN(modelPortDynmicNews);
    }

    //分页获取本地知识库
    @ResponseBody
    @RequestMapping(value = "CodexListLocal",method = RequestMethod.POST)
    public List<ModelDynmicNews> CodexListLocal(HttpServletRequest request) throws Exception
    {
        String page=request.getParameter("page");

        return  commonService.CodexListLocal(Integer.valueOf(page));
    }

    //分页获取本地知识库
    @ResponseBody
    @RequestMapping(value = "CodexListLocalByID",method = RequestMethod.POST)
    public  ModelDynmicNews CodexListLocalByID(HttpServletRequest request) throws Exception
    {
        String id=request.getParameter("id");

        return  commonService.CodexListLocalByID(Integer.valueOf(id));
    }

}
