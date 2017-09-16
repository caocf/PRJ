package net.ghpublic.controller;

import com.alibaba.fastjson.JSON;
import framework.modol.BaseRecords;
import framework.modol.BaseResult;
import framework.tool.FileUpLoad;
import framework.tool.HttpFileUpTool;
import framework.tool.HttpRequest;
import net.ghpublic.constant.GlobalVar;
import net.ghpublic.constant.ManagerURL;
import net.ghpublic.constant.Token;
import net.ghpublic.modol.*;
import net.ghpublic.service.ServiceLogin;
import net.ghpublic.service.ServiceShip;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Admin on 2016/5/9.
 */
@Controller
public class ContrShip
{
    @Resource
    ServiceShip shipService;
    //基础查询地址
    public static String  urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_PATH;

    //按用户名查询船舶列表
    @ResponseBody
    @RequestMapping(value = "myshiplist",method = RequestMethod.POST)
    public BaseRecords<ShipEN> myShipList(HttpServletRequest request)
    {
        return new BaseRecords<>(shipService.getMyShips(request.getParameter("Username")));
    }
    //船舶状态检查器
    @ResponseBody
    @RequestMapping(value = "shipcheck",method = RequestMethod.POST)
    public Map CheckShip(HttpServletRequest request) throws Exception
    {
        String shipNum=request.getParameter("Shipname");
        String result="";
        try {
            Service service = new Service();
            Call call = (org.apache.axis.client.Call) service.createCall();
            call.setTargetEndpointAddress(GlobalVar.WEBSERVICE_URL);
            call.setOperationName("huzGetCheckResult");
            call.setTimeout(GlobalVar.HOST_CONNECT_LONG_TIME);
            Object[] datalist = {shipNum};
            //new Object[2];
            call.addParameter("shipNum",
                    org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数

            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
            result= (String) call.invoke(datalist);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //System.out.println(result);
        return shipService.AnalysisAttributeOfXML(result);
    }
    ///按船名获取基本信息
    @ResponseBody
    @RequestMapping(value = "baseinfo",method = RequestMethod.POST)
    public String getBaseInfoByShip(HttpServletRequest request) throws Exception
    {
        //return new BaseRecords<>(shipService.getIllegalsByShip(request.getParameter("Shipname")));

        String token=Token.makeToken();
        HttpFileUpTool post=new HttpFileUpTool();
      //GlobalVar.PORTDATABASE_SERVICECODE_WZ

        String ship=request.getParameter("Shipname");

        String result=post.get(urlPath+"?token="+token+"&serviceCode="+GlobalVar.PORTDATABASE_SERVICECODE_JB+
                "&name="+URLEncoder.encode(ship, "UTF-8")+"&format=json");
        //System.out.println(result);
       // return new String(result.getBytes(),"utf-8");
        return result;
    }
    //按船名获取证书信息
    @ResponseBody
    @RequestMapping(value = "certinfo",method = RequestMethod.POST)
    public String getCertInfoByShip(HttpServletRequest request) throws Exception
    {
        //return new BaseRecords<>(shipService.getIllegalsByShip(request.getParameter("Shipname")));

        String token=Token.makeToken();
        HttpFileUpTool post=new HttpFileUpTool();
        //GlobalVar.PORTDATABASE_SERVICECODE_WZ

        String ship=request.getParameter("Shipname");

        String result=post.get(urlPath+"?token="+token+"&serviceCode="+GlobalVar.PORTDATABASE_SERVICECODE_CZ+
                "&name="+URLEncoder.encode(ship, "UTF-8")+"&format=json"+"&pageIndex="+request.getParameter("Page"));
        //System.out.println(result);
        return result;
    }
    //按船名获取违章信息
    @ResponseBody
    @RequestMapping(value = "illegallist",method = RequestMethod.POST)
    public String getIllegalsByShip(HttpServletRequest request) throws Exception
    {
        //return new BaseRecords<>(shipService.getIllegalsByShip(request.getParameter("Shipname")));
        String token=Token.makeToken();
        HttpFileUpTool post=new HttpFileUpTool();
        String ship=request.getParameter("Shipname");

        String result=post.get(urlPath+"?token="+token+"&serviceCode="+ GlobalVar.PORTDATABASE_SERVICECODE_WZ+
                "&name="+URLEncoder.encode(ship, "UTF-8")+"&format=json"+"&pageIndex="+request.getParameter("Page"));
        //System.out.println(result);

        return result;
    }
    //按船名获取缴费信息
    @ResponseBody
    @RequestMapping(value = "chargelist",method = RequestMethod.POST)
    public String getChargeListByShip(HttpServletRequest request) throws Exception
    {
        //return new BaseRecords<>(shipService.getIllegalsByShip(request.getParameter("Shipname")));
        String token=Token.makeToken();
        HttpFileUpTool post=new HttpFileUpTool();
        String ship=request.getParameter("Shipname");

        String result=post.get(urlPath+"?token="+token+"&serviceCode="+ GlobalVar.PORTDATABASE_SERVICECODE_JF+
                "&name="+URLEncoder.encode(ship, "UTF-8")+"&format=json"+"&pageIndex="+request.getParameter("Page"));
        //System.out.println(result);

        return result;
    }
    //按船名获取检验信息
    @ResponseBody
    @RequestMapping(value = "ShipExameByName",method = RequestMethod.POST)
    public String ShipExameByName(HttpServletRequest request) throws Exception
    {
        //return new BaseRecords<>(shipService.getIllegalsByShip(request.getParameter("Shipname")));
        String token=Token.makeToken();
        HttpFileUpTool post=new HttpFileUpTool();
        String ship=request.getParameter("Shipname");

        String result=post.get(urlPath+"?token="+token+"&serviceCode="+ GlobalVar.PORTDATABASE_SERVICECODE_JY+
                "&name="+URLEncoder.encode(ship, "UTF-8")+"&format=json"+"&pageIndex="+request.getParameter("Page"));
        //System.out.println(result);

        return result;
    }
    //按船名获取ais状态
    @ResponseBody
    @RequestMapping(value = "AISByShipName",method = RequestMethod.POST)
    public String AISByShipName(HttpServletRequest request) throws Exception
    {
        String shipname=request.getParameter("Shipname");

        return "0";
    }
    //按用户名查询已审核船舶列表
    @ResponseBody
    @RequestMapping(value = "myshiplistPass",method = RequestMethod.POST)
    public List<ShipEN> myshiplistPass(HttpServletRequest request)
    {
        return shipService.myshiplistPass(request.getParameter("Username"));
    }
    //到內網
    //获取港口列表
    @ResponseBody
    @RequestMapping(value = "DangerPortList",method = RequestMethod.POST)
    public String DangerPortList(HttpServletRequest request) throws Exception
    {
        String s= HttpRequest.sendPost(ManagerURL.URL+"DangerPortList","");

        return s;
    }
    //获取危险品类别列表
    @ResponseBody
    @RequestMapping(value = "DangerRankList",method = RequestMethod.POST)
    public String DangerRankList(HttpServletRequest request) throws Exception
    {
        String s= HttpRequest.sendPost(ManagerURL.URL+"DangerRankList","");

        return s;
    }
    //获取货物单位列表
    @ResponseBody
    @RequestMapping(value = "DangerGoodsUnit",method = RequestMethod.POST)
    public Object DangerGoodsUnit(HttpServletRequest request) throws Exception
    {
        String s= HttpRequest.sendPost(ManagerURL.URL+"DangerGoodsUnit","");

        return s;
    }
    //提交危货进港报告
    @ResponseBody
    @RequestMapping(value = "CommitDangerShip",method = RequestMethod.POST)
    public String submitDangerReport(HttpServletRequest request) throws Exception
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

        String s= HttpRequest.sendPost(ManagerURL.URL+"CommitDangerShip","Shipname="+URLEncoder.encode(sn,"UTF-8")+"&Goods="+URLEncoder.encode(s3,"UTF-8")
                +"&From="+s1+"&To="+s2+ "&Rank="+s4+"&Tons="+s5+"&Time="+s6+"&Unit="+URLEncoder.encode(s7,"UTF-8")+"&id="+id);
        //System.out.println(s);
       // JSONObject obj= new JSONObject(s);

        return s;
    }
    //按船名获取危货进港报告列表
    @ResponseBody
    @RequestMapping(value = "DangerShipByName",method = RequestMethod.POST)
    public String getDangerListByName(HttpServletRequest request) throws Exception
    {
        String name=request.getParameter("Shipname");
        String s= HttpRequest.sendPost(ManagerURL.URL+"DangerShipByName","Shipname="+URLEncoder.encode(name,"UTF-8"));

        return s;
    }
    //按ID撤回作业报告
    @ResponseBody
    @RequestMapping(value = "com/etl/te_cancel",method = RequestMethod.POST)
    public Object te_cancel(HttpServletRequest request) throws Exception
    {
        String temp = request.getParameter("id");
        String s= HttpRequest.sendPost(ManagerURL.URL+"com/etl/te_cancel","id="+temp);

        return com.alibaba.fastjson.JSONObject.parse(s);
    }
    //取消发布危货
    @ResponseBody
    @RequestMapping(value = "tx_cancel",method = RequestMethod.POST)
    public Object tx_cancel(HttpServletRequest request) throws Exception
    {
        String temp = request.getParameter("id");
        String s= HttpRequest.sendPost(ManagerURL.URL+"tx_cancel","id="+temp);

        return com.alibaba.fastjson.JSONObject.parse(s);
    }
    //根据提示获取船名列表
    @ResponseBody
    @RequestMapping(value = "ShipNamesByTip",method = RequestMethod.POST)
    public String ShipNames(HttpServletRequest request) throws Exception
    {
        String tip=request.getParameter("tip");
        String s= HttpRequest.sendPost(ManagerURL.URL+"ShipNamesByTip","tip="+URLEncoder.encode(tip,"UTF-8"));

        return s;
    }
    //获取普通载货种类列表
    @ResponseBody
    @RequestMapping(value = "EGoodsTypeList",method = RequestMethod.POST)
    public String GoodsTypeList(HttpServletRequest request) throws Exception
    {
        String s= HttpRequest.sendPost(ManagerURL.URL+"GoodsTypeList","");

        return s;
    }

    //提交普通电子报告
    @ResponseBody
    @RequestMapping(value = "CommitEReport",method = RequestMethod.POST)
    public String CommitEReport(HttpServletRequest request) throws Exception
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

        String s= HttpRequest.sendPost(ManagerURL.URL+"CommitEReport",
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

        return s;
    }
    //按状态和船名获取报告列表
    @RequestMapping(value = "ERePortByStatusAndShip", method = RequestMethod.POST)
    @ResponseBody
    public String ERePortByStatusAndShip(HttpServletRequest request) throws Exception
    {
        String id = request.getParameter("StatusID");
        String sn = request.getParameter("Shipname");

        String s= HttpRequest.sendPost(ManagerURL.URL+"ERePortByStatusAndShip","Shipname="+URLEncoder.encode(sn,"UTF-8")+
                                                    "&StatusID="+id);

        return s;
    }

    //按ID修改电子报告
    @ResponseBody
    @RequestMapping(value = "UpDateERePortByID",method = RequestMethod.POST)
    public Object UpDateERePortByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String ToID=request.getParameter("ToID");
        String Time=request.getParameter("Time");
        String s= HttpRequest.sendPost(ManagerURL.URL+"UpDateERePortByID","id="+id+"&ToID="+ToID+"&Time="+Time);

        return JSON.parse(s);
    }

    //按ID删除进港报告
    @RequestMapping(value = "etl/temp/remove", method = RequestMethod.POST)
    @ResponseBody
    public Object remove(HttpServletRequest request) throws IOException
    {
        String id = request.getParameter("id");
        String s= HttpRequest.sendPost(ManagerURL.URL+"etl/temp/remove","id="+id);

        return com.alibaba.fastjson.JSONObject.parse(s);
    }
}
