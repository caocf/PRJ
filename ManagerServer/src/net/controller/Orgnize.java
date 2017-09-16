package net.controller;

import com.alibaba.fastjson.JSON;
import common.BaseResult;
import net.modol.JcxxYhEN;
import net.modol.JcxxZzjgEN;
import net.service.OrgnizeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2016/9/9.
 */
@Controller
public class Orgnize
{
    @Resource
    OrgnizeService orgnizeService;

    //查找上级是该id的项
    @RequestMapping(value = "ItemsByPid",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult ItemsByPid(HttpServletRequest request) throws IOException
    {
        String pid=request.getParameter("pid");
        return new BaseResult(orgnizeService.ItemsByPid(pid));
    }
    //按部门ID查询人员列表
    @RequestMapping(value = "CrewsByDepID",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult CrewsByDepID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("pid");
        return new BaseResult(orgnizeService.CrewsByDepID(id));
    }
    //按人员ID查询人员
    @RequestMapping(value = "DepByCrewID",method = RequestMethod.POST)
    @ResponseBody
    public JcxxZzjgEN DepByCrewID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("crewid");
        JcxxYhEN crew=orgnizeService.CrewByCrewID(id);
        JcxxZzjgEN zzjgEN=new JcxxZzjgEN();
        if(crew!=null)
        {
            zzjgEN=orgnizeService.DepartmentByID(crew.getDw());
        }
        return zzjgEN;
    }
    //按ID查询组织
    @RequestMapping(value = "DepartmentByID",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult DepartmentByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        return new BaseResult(orgnizeService.DepartmentByID(id));
    }
    //按提示查询人员名字
    @RequestMapping(value = "UserNamesByTip",method = RequestMethod.POST)
    @ResponseBody
    public List<?> UserNamesByTip(HttpServletRequest request) throws IOException
    {
        String tip=request.getParameter("tip");
        return orgnizeService.UserNamesByTip(tip);
    }
    //按名字查询人员信息
    @RequestMapping(value = "UserByName",method = RequestMethod.POST)
    @ResponseBody
    public List<?> UserByName(HttpServletRequest request) throws IOException
    {
        String name=request.getParameter("name");
        return orgnizeService.UserByName(name);
    }

    //按名字查询人员信息
    @RequestMapping(value = "orgtest",method = RequestMethod.POST)
    @ResponseBody
    public Object orgtest(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        Map<String,Object> map=new HashMap<>();
        orgnizeService.OrgTree(id,map);
        return JSON.toJSON(map);
    }
}
