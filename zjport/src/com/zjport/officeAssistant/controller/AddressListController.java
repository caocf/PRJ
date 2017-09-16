package com.zjport.officeAssistant.controller;

import com.common.base.BaseResult;
import com.zjport.common.service.CommonService;
import com.zjport.file.service.FileService;
import com.zjport.model.TAddressListGroup;
import com.zjport.officeAssistant.service.OfficeAssistantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by TWQ on 2016/9/8.
 */
@Controller
@RequestMapping("/officeAssistant")
public class AddressListController {
    @Resource(name = "officeAssistantService")
    OfficeAssistantService officeAssistantService;
    @Resource(name = "fileService")
    FileService fileService;
    @Resource(name = "commonService")
    CommonService commonService;

    BaseResult result;

    /**
     * 通讯录main
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/addressList")
    public String addressList(HttpServletRequest request, Model model) {
        return "officeAssistant/addressList"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 单位/部门下的人员列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showUserList")
    @ResponseBody
    public BaseResult showUserList(HttpServletRequest request, Model model) {
        String type = request.getParameter("type");   //TYPE用户判断是单位下的还是部门下的
        String structureId = request.getParameter("structureId"); // structureId为单位或者部门ID
        String search = request.getParameter("search");
        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));

        result = BaseResult.newResultOK(this.officeAssistantService.selectUserIn(type,structureId,search,page,rows));
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 人员详细信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showUserDetail")
    @ResponseBody
    public BaseResult showUserDetail(HttpServletRequest request, Model model) {
        String userId = request.getParameter("userId");   //TYPE用户判断是单位下的还是部门下的

        result = BaseResult.newResultOK(this.officeAssistantService.selectUserById(userId));
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 通讯录群组信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showAddressGroup")
    @ResponseBody
    public BaseResult showAddressGroup(HttpServletRequest request, Model model) {
        result = BaseResult.newResultOK(this.officeAssistantService.selectAddressGroup());
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }


    /**
     * 通讯录群组用户信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showAddressGroupList")
    @ResponseBody
    public BaseResult showAddressGroupList(HttpServletRequest request, Model model) {
        String groupId = request.getParameter("groupId");
        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));
        result = BaseResult.newResultOK(this.officeAssistantService.selectAddressGroupList(groupId,page,rows));
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 保存修改用户
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveUser")
    @ResponseBody
    public BaseResult saveUser(HttpServletRequest request, Model model) {
        String lawcode = request.getParameter("lawcode");
        String order = request.getParameter("order");
        String userId = request.getParameter("userId");
        this.officeAssistantService.saveUserInfo(userId,lawcode,order);
        return result;
    }

    /**
     * 查找群组
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/editGroup")
    @ResponseBody
    public BaseResult editGroup(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");

        result = BaseResult.newResultOK(this.officeAssistantService.showGroupDetail(id));
        return result;
    }

    /**
     * 保存群组
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveGroup")
    @ResponseBody
    public BaseResult saveGroup(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        String groupName = request.getParameter("groupName");
        String userIds = request.getParameter("userIds");
        String[] users = userIds.split(";");

        TAddressListGroup addressListGroup = new TAddressListGroup();
        if(!StringUtils.isEmpty(id)) {
            addressListGroup = this.officeAssistantService.getAddressListGroup(id);
            addressListGroup.setStGroupName(groupName);
            this.officeAssistantService.deleteUserMiddle(addressListGroup.getStAddressListGroupId());
        } else {
            addressListGroup.setStGroupName(groupName);
        }
        this.officeAssistantService.saveGroup(addressListGroup);

        for(int i = 0; i<users.length; i++) {
            this.officeAssistantService.saveUserMiddle(users[i],addressListGroup.getStAddressListGroupId());
        }

        return result;
    }

    /**
     * 删除群组
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/addressListDelete")
    @ResponseBody
    public BaseResult addressListDelete(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");

        this.officeAssistantService.deleteAddressListGroup(id);
        return result;
    }

}
