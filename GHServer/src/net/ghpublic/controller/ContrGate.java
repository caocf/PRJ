package net.ghpublic.controller;

import framework.modol.BaseRecords;
import framework.modol.BaseResult;
import net.ghpublic.modol.GateBaseEN;
import net.ghpublic.service.ServiceGate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Admin on 2016/7/18.
 */
@Controller
public class ContrGate
{
    @Resource
    ServiceGate gateService;
    //按船舶名获取过闸信息
    @ResponseBody
    @RequestMapping(value = "passinfo",method = RequestMethod.POST)
    public BaseResult getPassByShip(HttpServletRequest request)
    {
        GateBaseEN g=gateService.getPassByShip(request.getParameter("Shipname"));
        return new BaseResult(1,"",g);
    }
}
