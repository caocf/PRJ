package net.controller;

import common.HttpRequest;
import common.PublicServer;
import mypackage.ApcksobulkAddSumbit;
import mypackage.ArrayOfSecApcksobulkDtl;
import mypackage.SeDgoodSportop;
import mypackage.SecApcksobulk;
import net.modol.CrewBaseEN;
import net.service.CrewService;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.XMLGregorianCalendar;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2016/7/28.
 */
@Controller
public class Crew
{
    @Resource
    CrewService crewService;

    //按提示获取船员名字列表
    @RequestMapping(value = "CrewnamesByTip",method = RequestMethod.POST)
    @ResponseBody
    public List<String> CrewnamesByTip(HttpServletRequest request)
    {
        String s=request.getParameter("tip");
        return crewService.CrewnamesByTip(s);
    }
    //按船员名获取基本信息
    @RequestMapping(value = "CrewinfoByName",method = RequestMethod.POST)
    @ResponseBody
    public CrewBaseEN CrewinfoByName(HttpServletRequest request)
    {
        String s=request.getParameter("name");
        return crewService.CrewinfoByName(s);
    }

    //按船员名获取基本信息
    @RequestMapping(value = "tttt",method = RequestMethod.POST)
    @ResponseBody
    public String tttt(HttpServletRequest request) throws Exception
    {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("entid",0301);
        jsonObject.put("contact",0301);
        jsonObject.put("contel",0301);
        jsonObject.put("appjoncon",0301);
        jsonObject.put("jobprincipal",0301);
        jsonObject.put("pritel",0301);
        jsonObject.put("appjobadd",0301);
        jsonObject.put("icoptime",0301);
        jsonObject.put("esoptime",0301);
        jsonObject.put("namecarrier",0301);
        jsonObject.put("Voyage",0301);
        jsonObject.put("vessel",0301);
        jsonObject.put("shipdwt",0301);
        jsonObject.put("lunport",0301);
        jsonObject.put("adtime",0301);
        jsonObject.put("cname",0301);
        jsonObject.put("unid",0301);
        jsonObject.put("quantity",0301);
        jsonObject.put("pcpro",0301);
        jsonObject.put("package",0301);
        jsonObject.put("appstatus",0301);
        jsonObject.put("appcon",0301);
        jsonObject.put("apptime",0301);
        jsonObject.put("appemp",0301);

        jsonObject.put("endstatus",0301);
        jsonObject.put("encon",0301);
        jsonObject.put("endtime",0301);
        jsonObject.put("endemp",0301);
        jsonObject.put("spreins",0301);
        jsonObject.put("sapre",0301);
        jsonObject.put("repofficer",0301);
        jsonObject.put("reppath",0301);
        jsonObject.put("filltime",0301);
        jsonObject.put("fillemp",0301);
        jsonObject.put("portsig",0301);
        jsonObject.put("dtype",0301);
        jsonObject.put("def1",0301);
        jsonObject.put("def2",0301);
        jsonObject.put("def3",0301);
        jsonObject.put("def4",0301);
        jsonObject.put("def5",0301);
        jsonObject.put("tdef6",0301);
        jsonObject.put("tdef7",0301);
        jsonObject.put("tdef8",0301);
        jsonObject.put("ndef9",0301);

        jsonObject.put("ndef10",0301);
        jsonObject.put("DeclNO",0301);
        jsonObject.put("Workers",0301);
        jsonObject.put("DecTime",0301);
        jsonObject.put("WorkAmount",0301);
        jsonObject.put("IsOutTime",0301);


        String s= HttpRequest.sendPost("http://101.68.92.118:88/"+"services/SynchUser.asmx/DgoodsportopAddSumbit","dgoodsportop="+jsonObject.toString() );

        Object obj= com.alibaba.fastjson.JSONObject.parse(s);

        return "dd";
    }

    public String getWs(String opname)
    {
        String result = "";
        try {
            Service service = new Service();
            Call call = (org.apache.axis.client.Call) service.createCall();
            call.setTargetEndpointAddress("http://101.68.92.118:88/services/SynchUser.asmx");
            call.setOperationName(opname);
            call.setSOAPActionURI("http://tempuri.org/" + opname);
            call.setTimeout(Integer.valueOf(60 * 1000));
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
           // ApcksobulkAddSumbit sumbit=new ApcksobulkAddSumbit();

            SecApcksobulk secApcksobulk=new SecApcksobulk();
            secApcksobulk.setAgentimg("1");
            secApcksobulk.setAppcon("  1");
            secApcksobulk.setAppemp("1");
            secApcksobulk.setAppstatus("1");
           // secApcksobulk.setApptime();
           // secApcksobulk.setArrtime(new Date());
            secApcksobulk.setAutvisa("fdfd");
            secApcksobulk.setBert("fdf");
            secApcksobulk.setDeclNO("115");
           // sumbit.setApcksobulk(secApcksobulk);

            SeDgoodSportop dgoodSportop=new SeDgoodSportop();
            JSONObject jsonObject=new JSONObject();
            JSONArray jsonArray=new JSONArray();

            call.addParameter("apcksobulk",
                    org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);
            call.addParameter("list",
                    org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);
            /*call.addParameter("list",
                    org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);*/
            result = (String) call.invoke(new Object[]{jsonObject.toString(),jsonArray.toString()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
