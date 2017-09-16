package net.controller;

import common.BaseResult;
import common.DataList;
import net.modol.CrewBaseEN;
import net.modol.SystemLogEN;
import net.service.CrewService;
import net.service.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2016/7/28.
 */
@Controller
public class System
{
    @Resource
    SystemService systemService;

    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

    //保存一条日志
    @RequestMapping(value = "saveSystemLog",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult saveSystemLog(HttpServletRequest request)
    {
        String s=request.getParameter("name");
        String op=request.getParameter("op");

        SystemLogEN systemLogEN=new SystemLogEN();
        systemLogEN.setUsername(s);
        systemLogEN.setOp(op);
        systemLogEN.setOptime(new Date());
        systemService.saveEN(systemLogEN);

        return BaseResult.newResultOK();
    }
    //按ID删除一条日志
    @RequestMapping(value = "delSystemLogByID",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult delSystemLogByID(HttpServletRequest request)
    {
        String s=request.getParameter("id");

        SystemLogEN systemLogEN= systemService.SystemLogByID(Integer.valueOf(s));
        systemService.deleteEN(systemLogEN);

        return BaseResult.newResultOK();
    }
    //按用户名提示分页日志
    @RequestMapping(value = "SystemLogsByUser",method = RequestMethod.POST)
    @ResponseBody
    public DataList SystemLogsByUser(HttpServletRequest request) throws Exception
    {
        String s=request.getParameter("name");
        String page=request.getParameter("page");
        String date1=request.getParameter("date1");
        String date2=request.getParameter("date2");

        Date d1="".equals(date1)?simpleDateFormat.parse("1900-1-1"):simpleDateFormat.parse(date1);
        Date d2="".equals(date2)?simpleDateFormat.parse("2200-1-1"):simpleDateFormat.parse(date2);
        return systemService.SystemLogsByUser(s,Integer.valueOf(page),d1,d2);
    }

}
