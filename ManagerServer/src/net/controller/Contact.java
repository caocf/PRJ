package net.controller;

import common.BaseResult;
import net.service.ContactService;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/11/1.
 */
@Controller
public class Contact {
    @Resource
    ContactService contactService;

    @ResponseBody
    @RequestMapping(value = "alldept", method = RequestMethod.POST)
    public BaseResult allDept() {
        BaseResult result = new BaseResult();
        result = this.contactService.allDept();
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "deptuser", method = RequestMethod.POST)
    public BaseResult deptUser(HttpServletRequest request) {
        String tempdeptid = request.getParameter("deptid");
        String deptid = tempdeptid == null ? "B4EB412FAFF44223A53456711CA3D5EA" : tempdeptid.trim();
        String tempisleaf = request.getParameter("isleaf");
        int isleaf = tempisleaf == null ? 0 : Integer.parseInt(tempisleaf.trim());
        String temp = request.getParameter("username");
        String username = temp == null ? "" : temp.trim();
        String page = request.getParameter("page");
        BaseResult result = new BaseResult();
        result = this.contactService.deptUser(username,deptid,isleaf, Integer.parseInt(page));
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "queryuserbyid", method = RequestMethod.POST)
    public BaseResult queryUserById(HttpServletRequest request) {
        String id = request.getParameter("id");
        BaseResult result = new BaseResult();
        result = this.contactService.queryUserById(id);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "updateuserbyid", method = RequestMethod.POST)
    public BaseResult updateUserById(HttpServletRequest request) {
        String id = request.getParameter("id");
        String yx = request.getParameter("yx");
        String bgdh = request.getParameter("bgdh");
        String sjhm = request.getParameter("sjhm");
        String xnwh = request.getParameter("xnwh");
        BaseResult result = new BaseResult();
        result = this.contactService.updateUserById(id,yx,bgdh,sjhm,xnwh);
        return result;
    }

    //用户同步
    @ResponseBody
    @RequestMapping(value = "updateRemoteUser", method = RequestMethod.POST)
    public BaseResult updateRemoteUser(HttpServletRequest request) throws Exception
    {
        java.lang.System .out.println("开始同步...");
        String result=contactService.getWs("GetYH");

        String results="["+result+"]";
        JSONArray array=new JSONArray(results);
        java.lang.System.out.println("共"+array.length()+"条数据");
        contactService.UserById(array);

        java.lang.System.out.println("同步完成");
        return BaseResult.newResultOK();
    }

    //组织机构同步
    @ResponseBody
    @RequestMapping(value = "updateRemoteDep", method = RequestMethod.POST)
    public BaseResult updateRemoteDep(HttpServletRequest request) throws Exception
    {
        String result=contactService.getWs("GetZZJG");
        org.json.JSONObject object=new org.json.JSONObject(result);
        contactService.Orgnize(object);
        return BaseResult.newResultOK();
    }
}
