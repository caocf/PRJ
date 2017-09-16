package com.zjport.backlog.controller;

import com.common.base.BaseResult;
import com.zjport.backlog.service.BacklogService;
import com.zjport.model.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by TWQ on 2016/10/9.
 */
@Controller
@RequestMapping("/backlog")
public class BacklogController {

    @Resource(name = "backlogService")
    BacklogService backlogService;

    BaseResult result;

    @RequestMapping(value = "/showBacklog")
    @ResponseBody
    public BaseResult showBacklog(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");
        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));
        result = BaseResult.newResultOK(this.backlogService.selectBacklogList(user.getStUserId(),rows,page));
        /*TWebName web = (TWebName)result.getRecords().getData().get(0);
        System.out.print(web.getStName());*/
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }
}
