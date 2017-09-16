package com.zjport.manage.controller;

import com.common.base.BaseResult;
import com.zjport.common.service.CommonService;
import com.zjport.manage.service.ManageService;
import com.zjport.model.TUser;
import com.zjport.util.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by TWQ on 2016/10/26.
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    @Resource(name = "manageService")
    ManageService manageService;
    @Resource(name = "commonService")
    CommonService commonService;

    BaseResult result;

    @RequestMapping(value = "/personCenter")
    public String personCenter(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");
        model.addAttribute("user",user);
        return "system/personCenter";// 返回的的视图会由springmvc-servlet中的视图解析器解析
    }


    @RequestMapping(value = "/userManage")
    public String userManage(HttpServletRequest request, Model model) {
        return "system/userManage";// 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    @RequestMapping(value = "/orgManage")
    public String orgManage(HttpServletRequest request, Model model) {
        return "system/orgManage";// 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    @RequestMapping(value = "/showOrgDetail")
    @ResponseBody
    public BaseResult showOrgDetail(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        result = BaseResult.newResultOK(this.manageService.getOrgDetail(id));
        //result.addToMap("",)
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    @RequestMapping(value = "/orgDetailSubmit")
    @ResponseBody
    public BaseResult orgDetailSubmit(HttpServletRequest request, Model model) {

        this.commonService.updateOrgDetail(request);
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    @RequestMapping(value = "/showDetail")
    public String showDetail(HttpServletRequest request, Model model) {

        String id = request.getParameter("id");
        TUser user = this.commonService.selectUserById(id);
        model.addAttribute("user",user);
        return "system/userDetail";// 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    @RequestMapping(value = "/showAreaList")
    @ResponseBody
    public BaseResult showAreaList(HttpServletRequest request, Model model) {

        result = BaseResult.newResultOK(this.manageService.getAreaList());
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    @RequestMapping(value = "/showJsList")
    @ResponseBody
    public BaseResult showJsList(HttpServletRequest request, Model model) {

        result = BaseResult.newResultOK(this.manageService.getJsList());
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    @RequestMapping(value = "/userDetailSubmit")
    @ResponseBody
    public BaseResult userDetailSubmit(HttpServletRequest request, Model model) {

        this.commonService.updateUserDetail(request);
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    @RequestMapping(value = "/userInfoSubmit")
    @ResponseBody
    public BaseResult userInfoSubmit(HttpServletRequest request, Model model) {

        this.commonService.updateUserInfo(request);
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    @RequestMapping(value = "/selectQx")
    @ResponseBody
    public BaseResult selectQx(HttpServletRequest request, Model model) {
        String js = request.getParameter("js");
        result = BaseResult.newResultOK(this.manageService.getQxList(js));
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    @RequestMapping(value = "/showUserDetail")
    @ResponseBody
    public BaseResult showUserDetail(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        result = BaseResult.newResultOK(this.manageService.getUserDetail(id));
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    @RequestMapping(value = "/showUserCenterDetail")
    @ResponseBody
    public BaseResult showUserCenterDetail(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");
        String id = request.getParameter("id");
        if(Value.of(id,Value.INTEGER_NULL) == user.getStUserId()) {
            result = BaseResult.newResultOK(this.manageService.getUserDetail(id));
        }
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }
}
