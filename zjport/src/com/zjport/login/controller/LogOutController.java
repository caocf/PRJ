package com.zjport.login.controller;

import com.zjport.log.LogService;
import com.zjport.web.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by TWQ on 2016/10/20.
 */
@Controller
@RequestMapping("/logout")
public class LogOutController {
    @Resource(name = "logService")
    LogService logService;

    @RequestMapping(value = "/exit")
    public void LoginOut(HttpServletRequest request, HttpServletResponse res) throws Exception{
        Object userId = request.getSession().getAttribute("session_userid");
        if (userId != null)
        {
            this.logService.saveLog((int) userId, "用户注销");
        }

        HttpSession session1 = request.getSession();

        session1.invalidate();
        UserSession.clear(request);

        /*request.getSession().removeAttribute("qx");*/
        /*res.sendRedirect("http://172.26.24.160/casZjgh/logout?service=http://localhost:8080/ZjPort");*/
        //return new ModelAndView("http://220.189.207.21:8290/casEG/logout?service=http://localhost:8080/ZjPort/login/personal");
    }
}
