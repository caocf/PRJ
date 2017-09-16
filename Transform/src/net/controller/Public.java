package net.controller;

import com.alibaba.fastjson.JSONObject;
import net.tools.FileDownload;
import net.tools.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2016/11/30.
 */
@Controller
public class Public
{
    public static String publicURL="http://120.55.193.1:8080/GH/";

    //分页获取绑定信息列表
    @RequestMapping(value ="UsersToCheck",method = RequestMethod.POST )
    @ResponseBody
    public Object ALLShips(HttpServletRequest request) throws IOException
    {
        String page=request.getParameter("page");
        String name=request.getParameter("tip");
        String type=request.getParameter("type");


        String s= HttpRequest.sendPost(publicURL+"UsersToCheck","page="+page+"&tip="+ URLEncoder.encode(name,"UTF-8")
                +"&type="+type);

        Object obj= JSONObject.parse(s);

        return obj;
    }
    //获取知识库
    @RequestMapping(value ="Knowlage",method = RequestMethod.POST )
    @ResponseBody
    public Object NewsKnowlage(HttpServletRequest request) throws IOException
    {
        String page=request.getParameter("Page");
        String s=HttpRequest.sendPost(publicURL+"Knowlage","Page="+ page);
        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按id获取船舶
    @RequestMapping(value ="ShipByID",method = RequestMethod.POST )
    @ResponseBody
    public Object ShipByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        System.out.println(id);
        String s=HttpRequest.sendPost(publicURL+"ShipByID","id="+ id);
        System.out.println(s);
        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按船舶id船舶证书
    @RequestMapping(value ="ShipCerttByID",method = RequestMethod.POST )
    @ResponseBody
    public Object ShipCerttByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");

        String s=HttpRequest.sendPost(publicURL+"ShipCerttByID","id="+ id);

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按船舶id提交船舶审核结果
    @RequestMapping(value ="ShipCheckByID",method = RequestMethod.POST )
    @ResponseBody
    public Object  ShipCheckByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String mark=request.getParameter("mark");
        String status=request.getParameter("status");

        String s= HttpRequest.sendPost(publicURL+"ShipCheckByID","id="+id+"&mark="+URLEncoder.encode(mark,"UTF-8")+"&status="+status);//

        return JSONObject.parse(s);
    }
    //按企业id获取企业
    @RequestMapping(value ="CompanyByID",method = RequestMethod.POST )
    @ResponseBody
    public Object CompanyByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");

        String s=HttpRequest.sendPost(publicURL+"CompanyByID","id="+ id);

        Object obj= JSONObject.parse(s);
        return obj;
    }

    //按企业id获取证书
    @RequestMapping(value ="CompanyCerttByID",method = RequestMethod.POST )
    @ResponseBody
    public Object CompanyCerttByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String s=HttpRequest.sendPost(publicURL+"CompanyCerttByID","id="+ id);

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按企业id提交审核结果
    @RequestMapping(value ="CompanyCheckByID",method = RequestMethod.POST )
    @ResponseBody
    public Object  CompanyCheckByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String mark=request.getParameter("mark");
        String status=request.getParameter("status");

        String s= HttpRequest.sendPost(publicURL+"CompanyCheckByID","id="+id+"&mark="+URLEncoder.encode(mark,"UTF-8")+"&status="+status);//

        return JSONObject.parse(s);
    }
    //按类型和条件查询用户
    @RequestMapping(value ="UsersByKey",method = RequestMethod.POST )
    @ResponseBody
    public Object  UsersByKey(HttpServletRequest request) throws IOException
    {
        String key=request.getParameter("key");
        String type=request.getParameter("type");
        String page=request.getParameter("page");
        String status=request.getParameter("status");
        String s= HttpRequest.sendPost(publicURL+"UsersByKey","key="+URLEncoder.encode(key,"UTF-8")+"&type="+type+"&page="+page+"&status="+status);

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按ID查询用户详情
    @RequestMapping(value ="UserInfoByID",method = RequestMethod.POST )
    @ResponseBody
    public Object  UserInfoByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String s= HttpRequest.sendPost(publicURL+"UserInfoByID","id="+URLEncoder.encode(id,"UTF-8"));//

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按用户名查询船舶列表
    @RequestMapping(value ="myshiplist",method = RequestMethod.POST )
    @ResponseBody
    public Object  myshiplist(HttpServletRequest request) throws IOException
    {
        String Username=request.getParameter("Username");
        String s= HttpRequest.sendPost(publicURL+"myshiplist","Username="+URLEncoder.encode(Username,"UTF-8"));//

        Object obj= JSONObject.parse(s);
        return obj;
    }

   //按用户名查询公司列表
    @RequestMapping(value ="MyCompany",method = RequestMethod.POST )
    @ResponseBody
    public Object  mycompany(HttpServletRequest request) throws IOException
    {
        String Username=request.getParameter("Username");
        String s= HttpRequest.sendPost(publicURL+"MyCompany","Username="+URLEncoder.encode(Username,"UTF-8"));//

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按ID设置用户状态
    @RequestMapping(value ="setUserStatusByID",method = RequestMethod.POST )
    @ResponseBody
    public Object  setUserStatusByID(HttpServletRequest request) throws IOException
    {
        String status=request.getParameter("status");
        String id=request.getParameter("id");
        String s= HttpRequest.sendPost(publicURL+"setUserStatusByID","id="+URLEncoder.encode(id,"UTF-8")+"&status="+status);//

        return  JSONObject.parse(s);
    }


    //分页按提示获取意见
    @RequestMapping(value ="AdviceByTip",method = RequestMethod.POST )
    @ResponseBody
    public Object  AdviceByTip(HttpServletRequest request) throws IOException
    {
        String tip=request.getParameter("tip");
        String page=request.getParameter("page");
        String s= HttpRequest.sendPost(publicURL+"AdviceByTip","tip="+URLEncoder.encode(tip,"UTF-8")+"&page="+page);//

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按ID获取意见
    @RequestMapping(value ="AdviceByID",method = RequestMethod.POST )
    @ResponseBody
    public Object  AdviceByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String s= HttpRequest.sendPost(publicURL+"AdviceByID","id="+URLEncoder.encode(id,"UTF-8"));//

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按原密码修改密码
    @ResponseBody
    @RequestMapping(value = "changepublicpsw",method = RequestMethod.POST)
    public Object changePSW(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("pswold");
        String s2=request.getParameter("pswnew");
        String name=request.getParameter("username");
        String s= HttpRequest.sendPost(publicURL+"changepsw","pswold="+URLEncoder.encode(s1,"UTF-8")+"&pswnew="+URLEncoder.encode(s2,"UTF-8")+"&username="+URLEncoder.encode(name,"UTF-8"));//

        Object obj= JSONObject.parse(s);
        return obj;
    }

    //文件下载
    @ResponseBody
    @RequestMapping(value = "CertDown",method = RequestMethod.POST)
    public void CertDown(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        String s1=request.getParameter("path");
        //String s= HttpRequest.sendPost(URL+"CertDown","path="+URLEncoder.encode(s1,"UTF-8") );//
        HttpRequest.RequestFile(publicURL+"CertDown","path="+URLEncoder.encode(s1,"UTF-8"),request.getServletContext().getRealPath(s1));

        FileDownload.download(request.getServletContext().getRealPath("/"+s1),request,response);
    }
    //统计注册待审核个数
    @ResponseBody
    @RequestMapping(value = "UserCheckCount",method = RequestMethod.POST)
    public Object UserCheckCount(HttpServletRequest request) throws Exception
    {
        String s= HttpRequest.sendPost(publicURL+"UserCheckCount","");//

        Object obj= JSONObject.parse(s);
        return obj;
    }

    //按地区和类别和页码获取新闻列表（转接）
    @ResponseBody
    @RequestMapping(value = "newslist",method = RequestMethod.POST)
    public Object getIndustoryList(HttpServletRequest request)
    {
        String region=request.getParameter("Region");
        String type=request.getParameter("Type");
        int page=Integer.valueOf(request.getParameter("Page"));

        String s=HttpRequest.sendPost(publicURL+"newslist","Region="+region+"&Type="+type+"&Page="+page);
        Object obj=JSONObject.parse(s);
        return obj;
    }
    //按ID获取新闻（转接）
    @ResponseBody
    @RequestMapping(value = "NewsByID",method = RequestMethod.POST)
    public Object NewsByID(HttpServletRequest request)
    {
        String id = request.getParameter("Newsid");

        String s=HttpRequest.sendPost(publicURL+"NewsByID","Newsid="+id);
        Object obj=JSONObject.parse(s);
        return  obj;
    }
    //按新闻ID提交评论（转接）
    @ResponseBody
    @RequestMapping(value = "comment",method = RequestMethod.POST)
    public Object sumbComment(HttpServletRequest request) throws Exception
    {
        int id=Integer.valueOf(request.getParameter("Newsid"));
        String content=request.getParameter("Content");
        String name=request.getParameter("Username");

        String s=HttpRequest.sendPost(publicURL+"comment","Newsid="+id+"&Content="+URLEncoder.encode(content,"UTF-8")+"&Username="+URLEncoder.encode(name,"UTF-8"));
        Object obj=JSONObject.parse(s);

        return obj;
    }
    //按新闻ID和页码获取评论（转接）
    @ResponseBody
    @RequestMapping(value = "commentlist",method = RequestMethod.POST)
    public Object getCommentList(HttpServletRequest request)
    {
        String s1=request.getParameter("Newsid");
        String s2=request.getParameter("Page");//从0开始

        String s=HttpRequest.sendPost(publicURL+"commentlist","Newsid="+s1+"&Page="+s2);
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

        String s=HttpRequest.sendPost(publicURL+"NewsCountByRegionType","type="+s1+"&region="+s2);
        Object obj=JSONObject.parse(s);

        return obj;
    }

    String types[]={"港航通知","行政通告","航行警告"};
    //审核通知并发布
    @RequestMapping(value = "InnerNews",method = RequestMethod.POST)
    @ResponseBody
    public Object CheckNotice(HttpServletRequest request) throws Exception
    {
        String title=request.getParameter("title");
        String region=request.getParameter("region");
        String type=request.getParameter("type");
        String content=request.getParameter("content");

            String s=HttpRequest.sendPost(publicURL+"InnerNews","title="+URLEncoder.encode(title,"UTF-8")+
                    "&region="+region+"&type="+type+"&content="+URLEncoder.encode(content,"UTF-8"));
            Object obj=JSONObject.parse(s);

        return obj;
    }


    //分页获取本地知识库(外网)
    @ResponseBody
    @RequestMapping(value = "CodexListLocal",method = RequestMethod.POST)
    public Object CodexListLocal(HttpServletRequest request) throws Exception
    {
        String page=request.getParameter("page");

        String s=HttpRequest.sendPost(publicURL+"CodexListLocal","page="+page);
        Object obj=JSONObject.parse(s);

        return obj;
    }

    @ResponseBody
    @RequestMapping(value = "CodexListLocalByID",method = RequestMethod.POST)
    public Object CodexListLocalByID(HttpServletRequest request) throws Exception
    {
        String page=request.getParameter("id");

        String s=HttpRequest.sendPost(publicURL+"CodexListLocalByID","id="+page);
        Object obj=JSONObject.parse(s);

        return obj;
    }
}
