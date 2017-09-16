package net.controller;

import com.alibaba.fastjson.JSONObject;
import net.tools.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Admin on 2016/11/30.
 */
@Controller
public class Inner
{
    public static String innerURL="http://10.100.70.127/";

    //获取港口列表
    @ResponseBody
    @RequestMapping(value = "DangerPortList",method = RequestMethod.POST)
    public Object DangerPortList(HttpServletRequest request) throws Exception
    {
        String s= HttpRequest.sendPost(innerURL+"DangerPortList","");

        Object obj= JSONObject.parse(s);

        return obj;
    }
    //获取危险品类别列表
    @ResponseBody
    @RequestMapping(value = "DangerRankList",method = RequestMethod.POST)
    public Object DangerRankList(HttpServletRequest request) throws Exception
    {
        String s= HttpRequest.sendPost(innerURL+"DangerRankList","");

        return JSONObject.parse(s);
    }
    //获取货物单位列表
    @ResponseBody
    @RequestMapping(value = "DangerGoodsUnit",method = RequestMethod.POST)
    public Object DangerGoodsUnit(HttpServletRequest request) throws Exception
    {
        String s= HttpRequest.sendPost(innerURL+"DangerGoodsUnit","");

        return JSONObject.parse(s);
    }
    //按ID撤回进港报告
    @ResponseBody
    @RequestMapping(value = "com/etl/te_cancel",method = RequestMethod.POST)
    public Object te_cancel(HttpServletRequest request) throws Exception
    {
        String temp = request.getParameter("id");
        String s= HttpRequest.sendPost(innerURL+"com/etl/te_cancel","id="+temp);

        return JSONObject.parse(s);
    }
    //获取货物单位列表
    @ResponseBody
    @RequestMapping(value = "tx_cancel",method = RequestMethod.POST)
    public Object tx_cancel(HttpServletRequest request) throws Exception
    {
        String temp = request.getParameter("id");
        String s= HttpRequest.sendPost(innerURL+"tx_cancel","id="+temp);

        return JSONObject.parse(s);
    }
    //提交危货进港报告
    @ResponseBody
    @RequestMapping(value = "CommitDangerShip",method = RequestMethod.POST)
    public Object submitDangerReport(HttpServletRequest request) throws Exception
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

        String s= HttpRequest.sendPost(innerURL+"CommitDangerShip","Shipname="+ URLEncoder.encode(sn,"UTF-8")+"&Goods="+URLEncoder.encode(s3,"UTF-8")
                +"&From="+s1+"&To="+s2+ "&Rank="+s4+"&Tons="+s5+"&Time="+s6+"&Unit="+URLEncoder.encode(s7,"UTF-8")+"&id="+id);
        //System.out.println(s);
        // JSONObject obj= new JSONObject(s);

        return JSONObject.parse(s);
    }
    //按船名获取危货进港报告列表
    @ResponseBody
    @RequestMapping(value = "DangerShipByName",method = RequestMethod.POST)
    public Object getDangerListByName(HttpServletRequest request) throws Exception
    {
        String name=request.getParameter("Shipname");
        String s= HttpRequest.sendPost(innerURL+"DangerShipByName","Shipname="+URLEncoder.encode(name,"UTF-8"));

        return JSONObject.parse(s);
    }
    //根据提示获取船名列表
    @ResponseBody
    @RequestMapping(value = "ShipNamesByTip",method = RequestMethod.POST)
    public Object ShipNames(HttpServletRequest request) throws Exception
    {
        String tip=request.getParameter("tip");
        String s= HttpRequest.sendPost(innerURL+"ShipNamesByTip","tip="+URLEncoder.encode(tip,"UTF-8"));

        return JSONObject.parse(s);
    }
    //获取普通载货种类列表
    @ResponseBody
    @RequestMapping(value = "GoodsTypeList",method = RequestMethod.POST)
    public Object GoodsTypeList(HttpServletRequest request) throws Exception
    {
        String s= HttpRequest.sendPost(innerURL+"GoodsTypeList","");
        return JSONObject.parse(s);
    }

    //提交普通电子报告
    @ResponseBody
    @RequestMapping(value = "CommitEReport",method = RequestMethod.POST)
    public Object CommitEReport(HttpServletRequest request) throws Exception
    {
        String reportTypeid=request.getParameter("ReportTypeID");//报告类型
        String sn=request.getParameter("Shipname");
        String s1=request.getParameter("FromID");
        String s2=request.getParameter("ToID");
        String s3=request.getParameter("GoodsTypeID");//载货种类
        String s5=request.getParameter("Tons");
        String s7=request.getParameter("UnitID");//重量单位
        String s6=request.getParameter("Time");
        String s4=request.getParameter("GasMount");
        String gasTime=request.getParameter("GasTime");
        String inout=request.getParameter("InOutID");

        String s= HttpRequest.sendPost(innerURL+"CommitEReport",
                "ReportTypeID="+reportTypeid+
                        "&Shipname="+URLEncoder.encode(sn,"UTF-8")+
                        "&FromID="+s1+"&ToID="+s2+
                        "&GoodsTypeID="+s3+
                        "&Tons="+s5+
                        "&UnitID="+s7+
                        "&Time="+s6+
                        "&GasMount="+s4+
                        "&GasTime="+gasTime+
                        "&InOutID="+inout);
        //System.out.println(s);
        // JSONObject obj= new JSONObject(s);

        return JSONObject.parse(s);
    }
    //按状态和船名获取报告列表
    @RequestMapping(value = "ERePortByStatusAndShip", method = RequestMethod.POST)
    @ResponseBody
    public Object ERePortByStatusAndShip(HttpServletRequest request) throws Exception
    {
        String id = request.getParameter("StatusID");
        String sn = request.getParameter("Shipname");

        String s= HttpRequest.sendPost(innerURL+"ERePortByStatusAndShip","Shipname="+URLEncoder.encode(sn,"UTF-8")+
                "&StatusID="+id);

        return JSONObject.parse(s);
    }
    //获取作业方式列表（内网）
    @ResponseBody
    @RequestMapping(value = "WorkWayList",method = RequestMethod.POST)
    public Object WorkWayList(HttpServletRequest request) throws IOException
    {
        String s= HttpRequest.sendPost(innerURL+"WorkWayList","");

        return JSONObject.parse(s);
    }
    //提交危货作业报告
    @ResponseBody
    @RequestMapping(value = "CommitDangerWork",method = RequestMethod.POST)
    public Object submitDangerReport1(HttpServletRequest request) throws Exception
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

        String s= HttpRequest.sendPost(innerURL+"CommitDangerWork","Wharfname="+URLEncoder.encode(wharf,"UTF-8")+"&Shipname="+URLEncoder.encode(sn,"UTF-8")
                +"&Goods="+URLEncoder.encode(s3,"UTF-8")+"&WorkManner="+URLEncoder.encode(s9,"UTF-8")
                +"&FromID="+s1+"&ToID="+s2+ "&RankID="+s4+"&Tons="+s5+"&Time="+s6+"&UnitID="+URLEncoder.encode(s7,"UTF-8")
                +"&StartTime="+s6+"&EndTime="+s8+"&id="+id);
        //System.out.println(s);
        // JSONObject obj= new JSONObject(s);

        return JSONObject.parse(s);
    }
    //按公司名获取危货作业列表(企业)
    @ResponseBody
    @RequestMapping(value = "DangerWorkListByComName",method = RequestMethod.POST)
    public Object DangerWorkListByComName(HttpServletRequest request) throws IOException
    {
        String name=request.getParameter("Name");
        System.out.println(name);
        String s= HttpRequest.sendPost(innerURL+"DangerWorkListByComName","Name="+URLEncoder.encode(name,"UTF-8"));

        return JSONObject.parse(s);
    }

    //按ID修改电子报告
    @ResponseBody
    @RequestMapping(value = "UpDateERePortByID",method = RequestMethod.POST)
    public Object UpDateERePortByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String ToID=request.getParameter("ToID");
        String Time=request.getParameter("Time");
        String s= HttpRequest.sendPost(innerURL+"UpDateERePortByID","id="+id+"&ToID="+ToID+"&Time="+Time);

        return JSONObject.parse(s);
    }

    //按ID删除进港报告
    @RequestMapping(value = "etl/temp/remove", method = RequestMethod.POST)
    @ResponseBody
    public Object remove(HttpServletRequest request) throws IOException
    {
        String id = request.getParameter("id");
        String s= HttpRequest.sendPost(innerURL+"etl/temp/remove","id="+id);

        return JSONObject.parse(s);
    }
    //按ID删除作业报告
    @RequestMapping(value = "etl/temp/remove1", method = RequestMethod.POST)
    @ResponseBody
    public Object remove1(HttpServletRequest request) throws IOException
    {
        String id = request.getParameter("id");
        String s= HttpRequest.sendPost(innerURL+"etl/temp/remove1","id="+id);

        return JSONObject.parse(s);
    }
}
