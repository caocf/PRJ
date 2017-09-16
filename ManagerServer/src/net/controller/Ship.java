package net.controller;

import com.alibaba.fastjson.JSONObject;
import common.BaseResult;
import common.GlobalVar;
import common.HttpFileUpTool;
import common.Token;
import net.service.ShipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.*;
import java.lang.System;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Admin on 2016/10/13.
 */
@Controller
public class Ship {
    public static String urlPath = GlobalVar.PORTDATABASE_IP +"/"+ GlobalVar.PORTDATABASE_PATH;
    @Resource
    ShipService shipService;

    //按提示获取船名列表
    @ResponseBody
    @RequestMapping(value = "ShipNamesByTip", method = RequestMethod.POST)
    public BaseResult ShipNamesByTip(HttpServletRequest request) {
        String tip = request.getParameter("tip");
        if (tip == null || "".equals(tip)) {
            return new BaseResult(null);
        } else {
            Object obj = shipService.ShipNamesByTip(tip);
            return new BaseResult(obj);
        }
    }

    //按提示获取企业名列表
    @ResponseBody
    @RequestMapping(value = "companynames", method = RequestMethod.POST)
    public List<?> companynames(HttpServletRequest request) {
        String tip = request.getParameter("Tip");
        return shipService.companynames(tip);
    }

    @RequestMapping(value = "cbcx_list", method = RequestMethod.GET)
    public String cbcxList(HttpServletRequest request, Model model) throws Exception {
        return "WEB-INF/ship/cbcxlist";
    }

    @RequestMapping(value = "xhrz", method = RequestMethod.GET)
    public String xcrzList(HttpServletRequest request, Model model) throws Exception {
        return "WEB-INF/ship/xhrz";
    }

    @RequestMapping(value = "qztj", method = RequestMethod.GET)
    public String qztjList(HttpServletRequest request, Model model) throws Exception {
        return "WEB-INF/ship/qztj";
    }

    @RequestMapping(value = "xctj", method = RequestMethod.GET)
    public String xctjList(HttpServletRequest request, Model model) throws Exception {
        return "WEB-INF/ship/xctj";
    }

    @RequestMapping(value = "shipinfo", method = RequestMethod.GET)
    public String shipInfo(HttpServletRequest request, Model model, String shipname) throws Exception {
        model.addAttribute("shipname", shipname);
        return "WEB-INF/ship/shipinfo";
    }

    //三、按船名获取基本信息
    @ResponseBody
    @RequestMapping(value = "baseinfo", method = RequestMethod.POST)
    public BaseResult getBaseInfoByShip(HttpServletRequest request) throws Exception {
        String token = Token.makeToken();
        HttpFileUpTool post = new HttpFileUpTool();
        //request.setCharacterEncoding();

        String ship = request.getParameter("shipname");
        String s = new String(ship.getBytes(), "UTF-8");
        String jsondata = post.get(urlPath + "?token=" + token + "&serviceCode=" + GlobalVar.PORTDATABASE_SERVICECODE_JB +
                "&name=" + URLEncoder.encode(ship, "UTF-8") + "&format=json");
        System.out.println(urlPath);
        JSONObject jsonobj = JSONObject.parseObject(jsondata);
        JSONObject retjson = jsonobj.getJSONObject("record");
        BaseResult result = new BaseResult();
        result.setObj(retjson);
        return result;
    }

    //四、按船名获取证书信息
    @ResponseBody
    @RequestMapping(value = "certinfo", method = RequestMethod.POST)
    public String getCertInfoByShip(HttpServletRequest request) throws Exception {
        String token = Token.makeToken();
        HttpFileUpTool post = new HttpFileUpTool();
        String ship = request.getParameter("shipname");
        String result = post.get(urlPath + "?token=" + token + "&serviceCode=" + GlobalVar.PORTDATABASE_SERVICECODE_CZ +
                "&name=" + URLEncoder.encode(ship, "UTF-8") + "&format=json" + "&pageSize=10&pageIndex=" + request.getParameter("page"));
        return result;
    }

    //五、按船名获取违章信息
    @ResponseBody
    @RequestMapping(value = "illegalinfo", method = RequestMethod.POST)
    public String getIllegalsByShip(HttpServletRequest request) throws Exception {
        String token = Token.makeToken();
        HttpFileUpTool post = new HttpFileUpTool();
        String ship = request.getParameter("shipname");
        String result = post.get(urlPath + "?token=" + token + "&serviceCode=" + GlobalVar.PORTDATABASE_SERVICECODE_WZ +
                "&name=" + URLEncoder.encode(ship, "UTF-8") + "&format=json" + "&pageSize=10&pageIndex=" + request.getParameter("page"));
        return result;
    }

    //六、按船名获取缴费信息
    @ResponseBody
    @RequestMapping(value = "chargeinfo", method = RequestMethod.POST)
    public String getChargeListByShip(HttpServletRequest request) throws Exception {
        String token = Token.makeToken();
        HttpFileUpTool post = new HttpFileUpTool();
        String ship = request.getParameter("shipname");
        String result = post.get(urlPath + "?token=" + token + "&serviceCode=" + GlobalVar.PORTDATABASE_SERVICECODE_JF +
                "&name=" + URLEncoder.encode(ship, "UTF-8") + "&format=json" + "&pageSize=10&pageIndex=" + request.getParameter("page"));
        return result;
    }
    //六、按船名获取缴费信息
    @ResponseBody
    @RequestMapping(value = "inspectinfo", method = RequestMethod.POST)
    public String getInspectListByShip(HttpServletRequest request) throws Exception {
        String token = Token.makeToken();
        HttpFileUpTool post = new HttpFileUpTool();
        String ship = request.getParameter("shipname");
        String result = post.get(urlPath + "?token=" + token + "&serviceCode=" + GlobalVar.PORTDATABASE_SERVICECODE_JY +
                "&name=" + URLEncoder.encode(ship, "UTF-8") + "&format=json" + "&pageSize=10&pageIndex=" + request.getParameter("page"));
        return result;
    }
}
