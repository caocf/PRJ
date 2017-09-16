package com.zjport.login.controller;

import com.zjport.backlog.service.BacklogService;
import com.zjport.log.LogService;
import com.zjport.login.service.LoginService;
import com.zjport.manage.service.PortletService;
import com.zjport.model.*;
import com.zjport.util.CommonConst;
import com.zjport.web.UserSession;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by TWQ on 2016/6/30.
 */
@Controller
@RequestMapping("/login")
public class LoginController {


    @Resource(name = "loginService")
    LoginService loginService;

    @Resource(name = "logService")
    LogService logService;

    @Resource(name = "backlogService")
    BacklogService backlogService;

    @Resource(name = "portletService")
    PortletService portletService;

    @RequestMapping(value = "/personal")
    public String userLogin(HttpServletRequest request, Model model)
    {
        String userId = null;
        String orgId = null;

        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        String account = principal.getName();
        //String account = "guest";
        /*if(principal!=null)
        {
            Map attributes = principal.getAttributes();
            userId = attributes.get("id").toString();
            orgId = attributes.get("dwid").toString();
            //account = principal.getName();
        }*/
        TUser daUser = this.loginService.selectUserByAccount(account);

        /*TUser daUser = this.loginService.selectUserById(userId);*/
        TOrg daOrg = this.loginService.selectOrgById(daUser.getStOrgId());
        TDepartment daDept = null;
        if(daUser != null) {
            daDept = this.loginService.selectDeptById(daUser.getStDepartmentId());
        }

        this.logService.saveLog(daUser.getStUserId(), "用户登录");

        UserSession.init(request, daUser, daOrg, daDept);
        HttpSession session = request.getSession();
        //调用各子系统接口判断用户是否有权限访问
        List<TApplication> appList = this.loginService.getExistSystem(daUser.getStUserId(),account);
        session.removeAttribute("appList");
        session.setAttribute("appList", appList);

        //将用户的portlet存入session中
        List<Portlet> portletLeftList = this.portletService.getPortletList(CommonConst.PORTLET_LEFT,daUser.getStUserId());
        List<Portlet> portletRightList = this.portletService.getPortletList(CommonConst.PORTLET_RIGHT,daUser.getStUserId());

        session.removeAttribute("portletLeftList");
        session.removeAttribute("portletRightList");
        session.setAttribute("portletLeftList", portletLeftList);
        session.setAttribute("portletRightList", portletRightList);
        // 用户登录时的权限，用于权限控制
        request.getSession().setAttribute("qx", this.loginService.queryUserNoQx(daUser.getStJs()));

        //List<SsoSystem> ssoSystemList = this.loginService.selectSsoSystem(daUser.getStUserId());
        //List<TBacklog> backlogList = this.backlogService.selectBacklogListByUserId(daUser.getStUserId());
        //int count = this.backlogService.selectCount(daUser.getStUserId());
        //model.addAttribute("ssoSystemList",ssoSystemList);
        //model.addAttribute("backlogList",backlogList);
        //model.addAttribute("count",count);
        return "login/personal"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    @RequestMapping(value = "/person")
    public String personal(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        TUser daUser = (TUser)session.getAttribute("session_user");
        //将用户的portlet存入session中
        List<Portlet> portletLeftList = this.portletService.getPortletList(CommonConst.PORTLET_LEFT,daUser.getStUserId());
        List<Portlet> portletRightList = this.portletService.getPortletList(CommonConst.PORTLET_RIGHT,daUser.getStUserId());
        session.removeAttribute("portletLeftList");
        session.removeAttribute("portletRightList");
        session.setAttribute("portletLeftList", portletLeftList);
        session.setAttribute("portletRightList", portletRightList);
        List<TBacklog> backlogList = this.backlogService.selectBacklogListByUserId(daUser.getStUserId());
        int count = this.backlogService.selectCount(daUser.getStUserId());

        model.addAttribute("backlogList",backlogList);
        model.addAttribute("count",count);
        return "login/personal"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    @RequestMapping(value = "/dealBacklog")
    public String dealBacklog(HttpServletRequest request)
    {
        String type = request.getParameter("type");
        String id = request.getParameter("id");

        if(CommonConst.BACKLOG_INFORM_APPROVAL.equals(type)) {
            return "information/Inform_approval"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
        } else if(CommonConst.BACKLOG_CALENDAR.equals(type)){
            return "officeAssistant/calendar"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
        } else {
            return "WEB-INF/page/errorpage/404";
        }

    }
}
