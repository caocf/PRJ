package net.controller;

import com.alibaba.fastjson.JSONObject;
import common.*;
import net.modol.publicdata.NewsCommentEN;
import net.modol.publicdata.NewsEN;
import net.service.PublicNewsService;
import net.service.websocket.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2016/8/6.
 */
@Controller
public class MSG
{
    @Resource
    PublicNewsService newsServiceP;//zjport数据库
    @Resource
    NewsService newsService;

    SimpleDateFormat sd=new SimpleDateFormat("yyyy-mm-dd");

    //按地区和类别和页码获取新闻列表（转接）
    @ResponseBody
    @RequestMapping(value = "newslist",method = RequestMethod.POST)
    public Object getIndustoryList(HttpServletRequest request)
    {
        String region=request.getParameter("Region");
        String type=request.getParameter("Type");
        int page=Integer.valueOf(request.getParameter("Page"));

        String s=HttpRequest.sendPost(PublicServer.BASEURL+"newslist","Region="+region+"&Type="+type+"&Page="+page);
        Object obj=JSONObject.parse(s);
        return obj;
    }
    //按ID获取新闻（转接）
    @ResponseBody
    @RequestMapping(value = "NewsByID",method = RequestMethod.POST)
    public Object NewsByID(HttpServletRequest request)
    {
        String id = request.getParameter("Newsid");

        String s=HttpRequest.sendPost(PublicServer.BASEURL+"NewsByID","Newsid="+id);
        Object obj=JSONObject.parse(s);
        return  obj;
    }
    //按新闻ID提交评论（转接）
    @ResponseBody
    @RequestMapping(value = "comment",method = RequestMethod.POST)
    public Object sumbComment(HttpServletRequest request)
    {
        int id=Integer.valueOf(request.getParameter("Newsid"));
        String content=request.getParameter("Content");
        String name=request.getParameter("Username");

        String s=HttpRequest.sendPost(PublicServer.BASEURL+"comment","Newsid="+id+"&Content="+content+"&Username="+name);
        Object obj=JSONObject.parse(s);

        return obj;
    }
    //按新闻ID和页码获取评论（转接）
    @ResponseBody
    @RequestMapping(value = "commentlist",method = RequestMethod.POST)
    public Object getCommentList(HttpServletRequest request)
    {
        String s1=request.getParameter("Newsid");
        String s2=request.getParameter("Page");

        String s=HttpRequest.sendPost(PublicServer.BASEURL+"commentlist","Newsid="+s1+"&Page="+s2);
        Object obj=JSONObject.parse(s);

        return obj;
    }
    //按地区和类别获取新闻条数（转接）
    @ResponseBody
    @RequestMapping(value = "NewsCountByRegionType",method = RequestMethod.POST)
    public Object NewsCountByRegionType(HttpServletRequest request)
    {
        String s1=request.getParameter("type");
        String s2=request.getParameter("region");

        String s=HttpRequest.sendPost(PublicServer.BASEURL+"NewsCountByRegionType","type="+s1+"&region="+s2);
        Object obj=JSONObject.parse(s);

        return obj;
    }
    //提交通知(本地)
    @RequestMapping(value = "CommitNews",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult CommitNews(HttpServletRequest request) throws Exception
    {
        //存入数据库
        String s1=request.getParameter("type");
        String s2=request.getParameter("title");
        String s3=request.getParameter("content");
        String s4=request.getParameter("region");

        NewsEN news=new NewsEN();
        news.setNewstype(s1);
        news.setTitle(s2);
        news.setContent(s3);
        news.setRegion(s4);
        news.setUpdatetime(new Date());
        news.setStatus("1");//待审核
        newsServiceP.saveEN(news);

        return new BaseResult(1,"");

        //return newsService.SendMSAll(s1+","+s2);
    }
    //按条件获取通知列表(本地)
    @RequestMapping(value = "NoticeListByItems",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult NoticeListByItems(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("region");
        String s2=request.getParameter("type");
        String s3=request.getParameter("title");
        String s4=request.getParameter("status");
        String d1=request.getParameter("date1");
        String d2=request.getParameter("date2");
        String s5=request.getParameter("page");

        if("".equals(d1))
        {
            d1="1900-1-1";
        }
        if("".equals(d2))
        {
            d2="2050-1-1";
        }

        return newsServiceP.NoticeListByItems(s1,s2,s3,s4,sd.parse(d1),sd.parse(d2),s5);
    }
    //按ID获取通知
    @RequestMapping(value = "NoticeByID",method = RequestMethod.POST)
    @ResponseBody
    public NewsEN NoticeByID(HttpServletRequest request) throws Exception
    {
        String id=request.getParameter("id");
        NewsEN newsEN=newsServiceP.NoticeByID(Integer.valueOf(id));

        return newsEN;
    }
    String types[]={"港航通知","行政通告","航行警告"};
    //审核通知并发布
    @RequestMapping(value = "CheckNotice",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult CheckNotice(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("ID");
        String s2=request.getParameter("status");
        String s3=request.getParameter("opinion");

        NewsEN news=newsServiceP.NoticeByID(Integer.valueOf(s1));
        news.setStatus(s2);
        news.setOpinon(s3);
        newsServiceP.updateEN(news);

        if("3".equals(s2))//若通过审核，将通知发布到外网
        {
            news.setUpdatetime(new Date());//审核后的时间（发布时间）

            String s=HttpRequest.sendPost(PublicServer.BASEURL+"InnerNews","title="+URLEncoder.encode(news.getTitle(),"UTF-8")+
                    "&region="+news.getRegion()+"&type="+news.getNewstype()+"&content="+URLEncoder.encode(news.getContent(),"UTF-8"));
            Object obj=JSONObject.parse(s);
            String result=(String)obj;
            if(Integer.valueOf(result)>0)//发布成功
            {
                //推送到APP
                String ms =types[Integer.valueOf(news.getNewstype())-4]+","+news.getTitle();
                newsService.SendMSAll(ms);
            }

        }

        return new BaseResult(1,"");
    }


    //按ID删除通知
    @RequestMapping(value = "DeleteNoticeByID",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult DeleteNoticeByID(HttpServletRequest request) throws Exception
    {
        String id=request.getParameter("id");
        NewsEN newsEN=newsServiceP.NoticeByID(Integer.valueOf(id));
        newsServiceP.deleteEN(newsEN);

        return new BaseResult(1,"");
    }
    //批量删除通知
    @RequestMapping(value = "DeleteNoticeByIDs",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult DeleteNoticeByIDs(@RequestParam(value = "ids[]") List<String> ids, HttpServletRequest request) throws Exception
    {
        for(String id:ids)
        {
            NewsEN newsEN=newsServiceP.NoticeByID(Integer.valueOf(id));
            newsServiceP.deleteEN(newsEN);
        }

        return new BaseResult(1,"");
    }

    //分页获取本地知识库(外网)
    @ResponseBody
    @RequestMapping(value = "CodexListLocal",method = RequestMethod.POST)
    public Object CodexListLocal(HttpServletRequest request) throws Exception
    {
        String page=request.getParameter("page");

        String s=HttpRequest.sendPost(PublicServer.BASEURL+"CodexListLocal","page="+page);
        Object obj=JSONObject.parse(s);

        return obj;
    }
    //分页获取本地知识库(外网)
    @ResponseBody
    @RequestMapping(value = "CodexListLocalByID",method = RequestMethod.POST)
    public Object CodexListLocalByID(HttpServletRequest request) throws Exception
    {
        String page=request.getParameter("id");

        String s=HttpRequest.sendPost(PublicServer.BASEURL+"CodexListLocalByID","id="+page);
        Object obj=JSONObject.parse(s);

        return obj;
    }
}
