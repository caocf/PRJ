package net.controller;

import com.alibaba.fastjson.JSONObject;
import common.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2016/9/8.
 */
@Controller
public class Public
{
    //public static String URL="http://localhost:6080/";

    //分页获取绑定信息列表
    @RequestMapping(value ="UsersToCheck",method = RequestMethod.POST )
    @ResponseBody
    public Object ALLShips(HttpServletRequest request) throws IOException
    {
        String page=request.getParameter("page");
        String name=request.getParameter("tip");
        String type=request.getParameter("type");


        String s= HttpRequest.sendPost(PublicServer.BASEURL+"UsersToCheck","page="+page+"&tip="+URLEncoder.encode(name,"UTF-8")
                                        +"&type="+type);

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按id获取船舶
    @RequestMapping(value ="ShipByID",method = RequestMethod.POST )
    @ResponseBody
    public Object ShipByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        HttpFileUpTool hf=new HttpFileUpTool();
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        String s=hf.post(PublicServer.BASEURL+"ShipByID",map);

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按船舶id船舶证书
    @RequestMapping(value ="ShipCerttByID",method = RequestMethod.POST )
    @ResponseBody
    public Object ShipCerttByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        HttpFileUpTool hf=new HttpFileUpTool();
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        String s=hf.post(PublicServer.BASEURL+"ShipCerttByID",map);

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按船舶id提交船舶审核结果
    @RequestMapping(value ="ShipCheckByID",method = RequestMethod.POST )
    @ResponseBody
    public BaseResult  ShipCheckByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String mark=request.getParameter("mark");
        String status=request.getParameter("status");

        String s= HttpRequest.sendPost(PublicServer.BASEURL+"ShipCheckByID","id="+id+"&mark="+URLEncoder.encode(mark,"UTF-8")+"&status="+status);//

        return new BaseResult(1,"");
    }
    //按企业id获取企业
    @RequestMapping(value ="CompanyByID",method = RequestMethod.POST )
    @ResponseBody
    public Object CompanyByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        HttpFileUpTool hf=new HttpFileUpTool();
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        String s=hf.post(PublicServer.BASEURL+"CompanyByID",map);

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按企业id获取证书
    @RequestMapping(value ="CompanyCerttByID",method = RequestMethod.POST )
    @ResponseBody
    public Object CompanyCerttByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        HttpFileUpTool hf=new HttpFileUpTool();
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        String s=hf.post(PublicServer.BASEURL+"CompanyCerttByID",map);

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按企业id提交审核结果
    @RequestMapping(value ="CompanyCheckByID",method = RequestMethod.POST )
    @ResponseBody
    public BaseResult  CompanyCheckByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String mark=request.getParameter("mark");
        String status=request.getParameter("status");

        String s= HttpRequest.sendPost(PublicServer.BASEURL+"CompanyCheckByID","id="+id+"&mark="+URLEncoder.encode(mark,"UTF-8")+"&status="+status);//

        return new BaseResult(1,"");
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
        String s= HttpRequest.sendPost(PublicServer.BASEURL+"UsersByKey","key="+URLEncoder.encode(key,"UTF-8")+"&type="+type+"&page="+page+"&status="+status);

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按ID查询用户详情
    @RequestMapping(value ="UserInfoByID",method = RequestMethod.POST )
    @ResponseBody
    public Object  UserInfoByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String s= HttpRequest.sendPost(PublicServer.BASEURL+"UserInfoByID","id="+URLEncoder.encode(id,"UTF-8"));//

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按用户名查询船舶列表
    @RequestMapping(value ="myshiplist",method = RequestMethod.POST )
    @ResponseBody
    public Object  myshiplist(HttpServletRequest request) throws IOException
    {
        String Username=request.getParameter("Username");
        String s= HttpRequest.sendPost(PublicServer.BASEURL+"myshiplist","Username="+URLEncoder.encode(Username,"UTF-8"));//

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按用户名查询公司列表
    @RequestMapping(value ="MyCompany",method = RequestMethod.POST )
    @ResponseBody
    public Object  mycompany(HttpServletRequest request) throws IOException
    {
        String Username=request.getParameter("Username");
        String s= HttpRequest.sendPost(PublicServer.BASEURL+"MyCompany","Username="+URLEncoder.encode(Username,"UTF-8"));//

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按ID设置用户状态
    @RequestMapping(value ="setUserStatusByID",method = RequestMethod.POST )
    @ResponseBody
    public BaseResult  setUserStatusByID(HttpServletRequest request) throws IOException
    {
        String status=request.getParameter("status");
        String id=request.getParameter("id");
        String s= HttpRequest.sendPost(PublicServer.BASEURL+"setUserStatusByID","id="+URLEncoder.encode(id,"UTF-8")+"&status="+status);//

        return new BaseResult(1,"更新成功");
    }


    //分页按提示获取意见
    @RequestMapping(value ="AdviceByTip",method = RequestMethod.POST )
    @ResponseBody
    public Object  AdviceByTip(HttpServletRequest request) throws IOException
    {
        String tip=request.getParameter("tip");
        String page=request.getParameter("page");
        String s= HttpRequest.sendPost(PublicServer.BASEURL+"AdviceByTip","tip="+URLEncoder.encode(tip,"UTF-8")+"&page="+page);//

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //按ID获取意见
    @RequestMapping(value ="AdviceByID",method = RequestMethod.POST )
    @ResponseBody
    public Object  AdviceByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String s= HttpRequest.sendPost(PublicServer.BASEURL+"AdviceByID","id="+URLEncoder.encode(id,"UTF-8"));//

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
        String s= HttpRequest.sendPost(PublicServer.BASEURL+"changepsw","pswold="+URLEncoder.encode(s1,"UTF-8")+"&pswnew="+URLEncoder.encode(s2,"UTF-8")+"&username="+URLEncoder.encode(name,"UTF-8"));//

        Object obj= JSONObject.parse(s);
        return obj;
    }
    //文件下载
    @ResponseBody
    @RequestMapping(value = "CertDown",method = RequestMethod.GET)
    public void CertDown(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        String s1=request.getParameter("path");
        //String s= HttpRequest.sendPost(URL+"CertDown","path="+URLEncoder.encode(s1,"UTF-8") );//
        HttpRequest.RequestFile(PublicServer.BASEURL+"CertDown","path="+URLEncoder.encode(s1,"UTF-8"),request.getServletContext().getRealPath(s1));

        FileDownload.download(request.getServletContext().getRealPath(s1),request,response);
    }
    //统计注册待审核个数
    @ResponseBody
    @RequestMapping(value = "UserCheckCount",method = RequestMethod.POST)
    public Object UserCheckCount(HttpServletRequest request) throws Exception
    {
        String s= HttpRequest.sendPost(PublicServer.BASEURL+"UserCheckCount","");//

        Object obj= JSONObject.parse(s);
        return obj;
    }

    //获取知识库
    @RequestMapping(value ="Knowlage",method = RequestMethod.POST )
    @ResponseBody
    public Object NewsKnowlage(HttpServletRequest request) throws IOException
    {
        String page=request.getParameter("Page");
        String s=HttpRequest.sendPost(PublicServer.BASEURL+"Knowlage","Page="+ page);
        Object obj= JSONObject.parse(s);
        return obj;
    }
}
