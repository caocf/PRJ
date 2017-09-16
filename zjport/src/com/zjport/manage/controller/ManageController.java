package com.zjport.manage.controller;

import com.common.base.BaseResult;
import com.zjport.manage.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping("/manage")
public class ManageController
{

    @Resource(name = "manageService")
    ManageService manageService;

    BaseResult result;

    @RequestMapping(value = "/rolelist")
    @ResponseBody
    public BaseResult queryRoles(String jsmc, Integer page, Integer row)
    {
        result = this.manageService.queryRoles(jsmc, page, row);
        return result;
    }

    @RequestMapping(value = "/addrole")
    @ResponseBody
    public BaseResult saveRole(String jsmc, String qxstr)
    {
        result = this.manageService.saveRole(jsmc, qxstr);
        return result;
    }

    @RequestMapping(value = "/viewrole")
    @ResponseBody
    public BaseResult roleDetail(Integer jsbh)
    {
        result = this.manageService.roleDetail(jsbh);
        return result;
    }

    @RequestMapping(value = "/modifyrole")
    @ResponseBody
    public BaseResult updateRole(Integer jsbh, String jsmc, String qxstr)
    {
        result = this.manageService.updateRole(jsbh, jsmc, qxstr);
        return result;
    }

    @RequestMapping(value = "/delrole")
    @ResponseBody
    public BaseResult deleteRole(Integer jsbh)
    {
        result = this.manageService.deleteRole(jsbh);
        return result;
    }

    @RequestMapping(value = "/qxlist")
    @ResponseBody
    public BaseResult qxList()
    {
        this.result = this.manageService.queryQxList();
        return result;
    }
}
