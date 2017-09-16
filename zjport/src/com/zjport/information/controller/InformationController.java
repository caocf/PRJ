package com.zjport.information.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.base.BaseResult;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.zjport.backlog.service.BacklogService;
import com.zjport.common.service.CommonService;
import com.zjport.file.service.FileService;
import com.zjport.information.service.InformationService;
import com.zjport.log.LogService;
import com.zjport.model.*;
import com.zjport.util.CommonConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by TWQ on 2016/7/28.
 */
@Controller
@RequestMapping("/information")
public class InformationController {

    @Resource(name = "inforService")
    InformationService inforService;
    @Resource(name = "commonService")
    CommonService commonService;
    @Resource(name = "fileService")
    FileService fileService;
    @Resource(name = "backlogService")
    BacklogService backlogService;
    @Resource(name = "logService")
    LogService logService;

    BaseResult result;


    /**
     * 我相关的信息main
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/personalInfommain")
    public String personalInfommain(HttpServletRequest request, Model model) {
        return "information/Inform_personal"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 我相关的信息列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/personallist")
    @ResponseBody
    public BaseResult personallist(HttpServletRequest request, Model model) {
        //session 拿部门
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");
        int userId = 0;
        if(user != null) {
            userId = user.getStUserId();
        }
        TDepartment dept = (TDepartment)session.getAttribute("session_dept");
        String deptId = "";
        if(dept != null) {
            deptId = dept.getStDepartmentId();
        }
        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));

        String search = request.getParameter("search");
        String type = request.getParameter("type");
        String state = request.getParameter("state");
        result = BaseResult.newResultOK(this.inforService.selectpersonalInfoList(type, state, search, userId, deptId, rows, page));
        //records = this.inforService.selectSendInfoList();
        //result = result.setObj(this.inforService.selectSendInfoList());
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 我相关的信息查看详情
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/personalDetail")
    public String personalDetail(HttpServletRequest request, Model model) {
        List<TInformation> informList = new ArrayList<TInformation>();
        String id = request.getParameter("id");
        String type = "";
        String webName = "";
        String webColumn = "";
        String sendUser = "";
        String approvalUser = "";
        TInformation info = this.inforService.selectInfoDetail(id);

        type = info.getStType();

        //获取发布网站以及发布网站类目
        if(info.getStWebNameId()!=null && !"".equals(info.getStWebNameId())) {
            webName = this.inforService.getwebName(info.getStWebNameId());
        }
        if(info.getStWebColumnId()!=null && !"".equals(info.getStWebColumnId())) {
            webColumn = this.inforService.getwebColumn(info.getStWebColumnId());
        }

        //获取附件
        List<TAttachment> attachmentList = this.inforService.getAttachmentList(info.getStAttachmentMiddleId());
        //System.out.println(info.getStApprovalUserId());

        sendUser = this.inforService.getUser(info.getStInformFromId());
        if(info.getStApprovalUserId() == null) {
            approvalUser = null;
        } else {
            approvalUser = this.inforService.getUser(info.getStApprovalUserId());
        }

        model.addAttribute("sendUser",sendUser);
        model.addAttribute("approvalUser",approvalUser);
        model.addAttribute("webName",webName);
        model.addAttribute("webColumn",webColumn);
        model.addAttribute("info",info);
        model.addAttribute("attachmentList",attachmentList);
        if(CommonConst.Information_Internet.equals(type)) {
            return "information/Inform_personalDetail_internet";
        } else if(CommonConst.Information_Board.equals(type)) {
            return "information/Inform_personalDetail_board";
        } else if(CommonConst.Information_Message.equals(type)) {
            return "information/Inform_personalDetail_message";
        } else {
            return "WEB-INF/page/errorpage/404";
        }
        // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 我发布的信息main
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/sendmain")
    public String sendMain(HttpServletRequest request, Model model) {

        //FileOperation.contentToTxt("D:\\aa.txt","测试的代码！！");
        return "information/Inform_send"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }


    /**
     * 我发布的信息列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/sendlist")
    @ResponseBody
    public BaseResult sendList(HttpServletRequest request, Model model) {
        //session 拿用户
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");
        int userId = 0;
        if(user != null) {
            userId = user.getStUserId();
        }
        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));
        String search = request.getParameter("search");
        String type = request.getParameter("type");
        String state = request.getParameter("state");
        result = BaseResult.newResultOK(this.inforService.selectSendInfoList(type, state, search, userId, rows, page));
        //records = this.inforService.selectSendInfoList();
        //result = result.setObj(this.inforService.selectSendInfoList());
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 信息发布删除
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteInform")
    public String deleteInform(HttpServletRequest request, Model model) {
        String informId= request.getParameter("id");
        this.inforService.deleteInform(informId);

        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");
        this.logService.saveLog(user.getStUserId(), "删除信息");
        return "information/Inform_send"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }


    /**
     * 信息发布新增跳转
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/sendAdd")
    public String sendAdd(HttpServletRequest request, Model model) {
        String type = request.getParameter("type");
        //System.out.print(type);
        if(CommonConst.Information_Internet.equals(type)) {
            return "information/Inform_add_internet"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
        } else if(CommonConst.Information_Board.equals(type)){
            return "information/Inform_add_board"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
        } else if(CommonConst.Information_Message.equals(type)) {
            return "information/Inform_add_message"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
        } else {
            return "WEB-INF/page/errorpage/404";
        }

    }

    /**
     * 信息发布重新发送 （针对网站以及情报板）
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/sendAgain")
    public String sendAgain(HttpServletRequest request, Model model) {
        /*String id = request.getParameter("id");

        TInformation info = this.inforService.selectInfoDetail(id);
        model.addAttribute("info",info);
        String type = info.getStType();
        if(CommonConst.Information_Internet.equals(type)) {
            return "information/Inform_addAgain_internet"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
        } else if(CommonConst.Information_Board.equals(type)){
            return "information/Inform_addAgain_board"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
        } else {
            return "WEB-INF/page/errorpage/404";
        }*/
        String id = request.getParameter("id");
        String type = "";

        TInformation info = this.inforService.selectInfoDetail(id);
        type = info.getStType();
        String approvalUserName = this.commonService.getUserName(info.getStApprovalUserId());

        List<TScanDepartment> deptList = this.inforService.getDepartmentList(info.getStScanDepartMiddleId());
        String deptName = "";
        if(deptList != null) {
            for(TScanDepartment dept : deptList) {
                deptName += this.commonService.getDeptName(dept.getStDepartmentId())+";";
            }
        }

        model.addAttribute("deptName",deptName);
        model.addAttribute("info",info);
        model.addAttribute("approvalUserName",approvalUserName);

        if(CommonConst.Information_Internet.equals(type)) {
            List<TAttachment> attachmentList = this.inforService.getAttachmentList(info.getStAttachmentMiddleId());
            TWebName web = this.inforService.getWeb(info.getStWebNameId());
            TWebColumn webColumn = this.inforService.getColumn(info.getStWebColumnId());
            model.addAttribute("attachmentList",attachmentList);
            model.addAttribute("web",web);
            model.addAttribute("webColumn",webColumn);
            return "information/Inform_addAgain_internet";
        } else if(CommonConst.Information_Board.equals(type)) {

            return "information/Inform_addAgain_board";
        } else {
            return "WEB-INF/page/errorpage/404";
        } // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }


    /**
     * 我审批的信息main
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/approvalmain")
    public String approvalMain(HttpServletRequest request, Model model) {
        return "information/Inform_approval"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 我审批的信息列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/approvallist")
    @ResponseBody
    public BaseResult approvalList(HttpServletRequest request, Model model) {
        //session 拿用户
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");

        int userId = 0;
        if(user != null) {
            userId = user.getStUserId();
        }
        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));
        String type = request.getParameter("type");
        String search = request.getParameter("search");
        String state = request.getParameter("state");
        result = BaseResult.newResultOK(this.inforService.selectApprovalInfoList(type, state, search, userId, rows, page));
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    //修改暂时去除
    /*@RequestMapping(value = "/editmain")
    public String editMain(HttpServletRequest request, Model model) {
        return "information/Inform_edit"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }*/

    //修改暂时去除
    /*@RequestMapping(value = "/editlist")
    @ResponseBody
    public BaseResult editList(HttpServletRequest request, Model model) {
        String type = request.getParameter("type");
        //System.out.print(type);
        //BaseResult result = new BaseResult();
        result = BaseResult.newResultOK(this.inforService.selectApprovalInfoList(type));
        //records = this.inforService.selectSendInfoList();
        //result = result.setObj(this.inforService.selectSendInfoList());
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }*/

    /**
     * 信息发布详情
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/sendDetail")
    public String sendDetail(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        String type = "";
        String webName = "";
        String webColumn = "";
        String sendUser = "";
        String approvalUser = "";

        TInformation info = this.inforService.selectInfoDetail(id);

        type = info.getStType();

        //获取发布网站以及发布网站类目
        if(info.getStWebNameId()!=null && !"".equals(info.getStWebNameId())) {
            webName = this.inforService.getwebName(info.getStWebNameId());
        }
        if(info.getStWebColumnId()!=null && !"".equals(info.getStWebColumnId())) {
            webColumn = this.inforService.getwebColumn(info.getStWebColumnId());
        }

        //获取附件
        List<TAttachment> attachmentList = this.inforService.getAttachmentList(info.getStAttachmentMiddleId());

        sendUser = this.inforService.getUser(info.getStInformFromId());
        if(info.getStApprovalUserId() == null) {
            approvalUser = null;
        } else {
            approvalUser = this.inforService.getUser(info.getStApprovalUserId());
        }

        model.addAttribute("sendUser",sendUser);
        model.addAttribute("approvalUser",approvalUser);
        model.addAttribute("webName",webName);
        model.addAttribute("webColumn",webColumn);
        model.addAttribute("info",info);
        model.addAttribute("attachmentList",attachmentList);
        if(CommonConst.Information_Internet.equals(type)) {
            return "information/Inform_sendDetail_internet";
        } else if(CommonConst.Information_Board.equals(type)) {
            return "information/Inform_sendDetail_board";
        } else if(CommonConst.Information_Message.equals(type)) {
            return "information/Inform_sendDetail_message";
        } else {
            return "WEB-INF/page/errorpage/404";
        }
         // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 信息发布撤回
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/recall")
    public String recall(HttpServletRequest request, Model model) throws IOException {
        String informId= request.getParameter("id");
        this.inforService.recallInform(informId);
        TInformation info = this.inforService.selectInfoDetail(informId);

        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");
        this.logService.saveLog(user.getStUserId(), "撤回信息");

        model.addAttribute("type",info.getStType());
        return "information/Inform_send"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 信息发布修改页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/editInfo")
    public String editInfo(HttpServletRequest request, Model model) throws IOException {
        String id = request.getParameter("id");
        String type = "";

        TInformation info = this.inforService.selectInfoDetail(id);
        type = info.getStType();
        String approvalUserName = this.commonService.getUserName(info.getStApprovalUserId());

        List<TScanDepartment> deptList = this.inforService.getDepartmentList(info.getStScanDepartMiddleId());
        String deptName = "";
        if(deptList != null) {
            for(TScanDepartment dept : deptList) {
                deptName += this.commonService.getDeptName(dept.getStDepartmentId())+";";
            }
        }

        model.addAttribute("deptName",deptName);
        model.addAttribute("info",info);
        model.addAttribute("approvalUserName",approvalUserName);

        if(CommonConst.Information_Internet.equals(type)) {
            List<TAttachment> attachmentList = this.inforService.getAttachmentList(info.getStAttachmentMiddleId());
            TWebName web = this.inforService.getWeb(info.getStWebNameId());
            TWebColumn webColumn = this.inforService.getColumn(info.getStWebColumnId());
            model.addAttribute("attachmentList",attachmentList);
            model.addAttribute("web",web);
            model.addAttribute("webColumn",webColumn);
            return "information/Inform_edit_internet";
        } else if(CommonConst.Information_Board.equals(type)) {

            return "information/Inform_edit_board";
        } else {
            return "WEB-INF/page/errorpage/404";
        } // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 审批信息详情
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/approvalDetail")
    public String approvalDetail(HttpServletRequest request, Model model) {
        List<TInformation> informList = new ArrayList<TInformation>();
        String id = request.getParameter("id");
        String state = request.getParameter("state");

        //如果信息状态为待审批状态则修改为审批中状态，如果为其他状态则不变
        if(CommonConst.InfoState_Wait_Approval.equals(state)) {
            this.inforService.changeState(id);
        }

        String type = null;
        String webName = "";
        String webColumn = "";
        String sendUser = "";
        String approvalUser = "";

        //获取发布网站以及发布网站类目
        TInformation info = this.inforService.selectInfoDetail(id);
        if(info.getStWebNameId()!=null && !"".equals(info.getStWebNameId())) {
            webName = this.inforService.getwebName(info.getStWebNameId());
        }
        if(info.getStWebColumnId()!=null && !"".equals(info.getStWebColumnId())) {
            webColumn = this.inforService.getwebColumn(info.getStWebColumnId());
        }

        //获取附件
        List<TAttachment> attachmentList = this.inforService.getAttachmentList(info.getStAttachmentMiddleId());

        sendUser = this.inforService.getUser(info.getStInformFromId());
        approvalUser = this.inforService.getUser(info.getStApprovalUserId());

        model.addAttribute("sendUser",sendUser);
        model.addAttribute("approvalUser",approvalUser);
        model.addAttribute("webName",webName);
        model.addAttribute("webColumn",webColumn);
        model.addAttribute("info",info);
        model.addAttribute("attachmentList",attachmentList);


        type = info.getStType();
        if(CommonConst.Information_Internet.equals(type)) {
            return "information/Inform_approvalDetail_internet";
        } else if(CommonConst.Information_Board.equals(type)) {
            return "information/Inform_approvalDetail_board";
        } else if(CommonConst.Information_Message.equals(type)) {
            return "information/Inform_approvalDetail_message";
        } else {
            return "WEB-INF/page/errorpage/404";
        }
        // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 发布网站列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showWeblist")
    @ResponseBody
    public BaseResult showWeblist(HttpServletRequest request, Model model) {
        result = BaseResult.newResultOK(this.inforService.selectWebList());
        /*TWebName web = (TWebName)result.getRecords().getData().get(0);
        System.out.print(web.getStName());*/
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 发布网站栏目列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showWebColumnlist")
    @ResponseBody
    public BaseResult showWebColumnlist(HttpServletRequest request, Model model) {
        String webId = request.getParameter("webId");

        result = BaseResult.newResultOK(this.inforService.selectWebColumnList(webId));
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 显示审批人员列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showApprovalUser")
    @ResponseBody
    public BaseResult showApprovalUser(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");
        result = BaseResult.newResultOK(this.inforService.selectApprovalUserList(user.getStUserId()));
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 信息发布网站提交（由于信息发布网站有附件，所以单独提交）
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/internetSubmit")
    public String internetSubmit(HttpServletRequest request, Model model, @RequestParam("attachment") MultipartFile[] attachment) throws IOException {
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");

        String webName = request.getParameter("webName");      //针对网站新增
        String webColumn = request.getParameter("webColumn");  //针对网站新增
        String title = request.getParameter("title");          //短信以及情报板暂时没有标题
        String keyword = request.getParameter("keyword");      //针对网站新增
        String content = request.getParameter("editor1");      //新增信息的内容
        String type = request.getParameter("type");            //信息类别  1:网站；2：情报板；3：短信
        String deptId = request.getParameter("bmids");         //可见部门
        String approvalId = request.getParameter("approvalUser"); //审批人员ID

        String infoId = request.getParameter("infoId");

        String saveMiddleAttachmentId = "";      //附件中间关联主键
        String scanDepartMiddleId = "";


        TInformation info = new TInformation();
        if(!StringUtils.isEmpty(infoId)) {
            info = this.inforService.selectInfoDetail(infoId);
            // 判断如果为修改，若附件中的值为空则附件未改动若附件中的值不为空则附件改动
            if(attachment!=null&&attachment[0].getSize()>0){  //TODO:附件上传存在一定问题  暂时拿第一个附件的大小进行判断 需修改

                saveMiddleAttachmentId = UUID.randomUUID().toString();
                //循环获取file数组中得文件
                for(int i = 0;i<attachment.length;i++) {
                    MultipartFile file = attachment[i];

                    //保存文件路径
                    String filePath = request.getSession().getServletContext().getRealPath("/") + "upload\\" + file.getOriginalFilename();
                    //保存文件
                    this.fileService.saveFile(file, filePath, saveMiddleAttachmentId);
                }
                this.fileService.delete(info.getStAttachmentMiddleId());
                info.setStAttachmentMiddleId(saveMiddleAttachmentId);
            }

            if(!"".equals(deptId) && deptId != null) {
                scanDepartMiddleId = UUID.randomUUID().toString();

                String[] array = deptId.split(",");
                for(int i = 0 ; i<array.length; i++) {
                    this.inforService.addScanDepart(array[i],scanDepartMiddleId);
                }
                this.inforService.deleteDeptId(info.getStScanDepartMiddleId());
                info.setStScanDepartMiddleId(scanDepartMiddleId);
            }
            info.setStInformKeyword(keyword);
            info.setStInformTitle(title);
            info.setStWebNameId(Integer.valueOf(webName));
            info.setStWebColumnId(Integer.valueOf(webColumn));
            info.setStType(type);
            info.setStInformContent(content);
            info.setStInformFromId(user.getStUserId());
            info.setDtCreate(new Timestamp(System.currentTimeMillis()));
            info.setStIsValid(CommonConst.VALID);
            info.setStState(CommonConst.InfoState_Wait_Approval);
            info.setStApprovalUserId(Integer.valueOf(approvalId));

            this.logService.saveLog(user.getStUserId(), "修改信息");
        } else {
            // 如果新增判断是否有附件，有的话则新增附件
            if(attachment!=null&&attachment[0].getSize()>0){  //TODO:附件上传存在一定问题  暂时拿第一个附件的大小进行判断 需修改
                saveMiddleAttachmentId = UUID.randomUUID().toString();
                //循环获取file数组中得文件
                for(int i = 0;i<attachment.length;i++) {
                    MultipartFile file = attachment[i];

                    //保存文件路径
                    String filePath = request.getSession().getServletContext().getRealPath("/") + "upload\\" + file.getOriginalFilename();
                    //保存文件
                    this.fileService.saveFile(file, filePath, saveMiddleAttachmentId);
                }
            }

            if(!"".equals(deptId) && deptId != null) {
                scanDepartMiddleId = UUID.randomUUID().toString();

                String[] array = deptId.split(",");
                for(int i = 0 ; i<array.length; i++) {
                    this.inforService.addScanDepart(array[i],scanDepartMiddleId);
                }
            }
            info.setStInformKeyword(keyword);
            info.setStInformTitle(title);
            info.setStWebNameId(Integer.valueOf(webName));
            info.setStWebColumnId(Integer.valueOf(webColumn));
            info.setStType(type);
            info.setStInformContent(content);
            info.setStInformFromId(user.getStUserId());
            info.setDtCreate(new Timestamp(System.currentTimeMillis()));
            info.setStIsValid(CommonConst.VALID);
            info.setStState(CommonConst.InfoState_Wait_Approval);
            info.setStAttachmentMiddleId(saveMiddleAttachmentId);
            info.setStScanDepartMiddleId(scanDepartMiddleId);
            info.setStApprovalUserId(Integer.valueOf(approvalId));

            this.logService.saveLog(user.getStUserId(), "发布信息");
        }


        this.inforService.saveInformation(info);

        this.backlogService.addBacklog(info.getStInformTitle(),CommonConst.BACKLOG_INFORM_APPROVAL,info.getStInformId(),info.getStApprovalUserId());
        model.addAttribute("type",type);
        return "information/Inform_send"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 信息发布提交  (用于短信和情报板提交)
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/informationSubmit")
    public String informationSubmit(HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");

        String content = request.getParameter("editor1");      //新增信息的内容
        String type = request.getParameter("type");            //信息类别  1:网站；2：情报板；3：短信
        String color = request.getParameter("color");          //针对情报板
        String deptId = request.getParameter("bmids");         //可见部门
        String informObject = request.getParameter("informObject");//发布对象
        String approvalId = request.getParameter("approvalUser"); //审批人员ID

        String infoId = request.getParameter("infoId");
        String scanDepartMiddleId = "";
        if(!"".equals(deptId) && deptId != null) {
            scanDepartMiddleId = UUID.randomUUID().toString();

            String[] array = deptId.split(",");
            for(int i = 0 ; i<array.length; i++) {
                this.inforService.addScanDepart(array[i],scanDepartMiddleId);
            }

        }

        TInformation info = new TInformation();
        if(!StringUtils.isEmpty(infoId)) {
            info = this.inforService.selectInfoDetail(infoId);
            info.setStType(type);
            info.setStInformContent(content);
            info.setStInformFromId(user.getStUserId());
            info.setDtCreate(new Timestamp(System.currentTimeMillis()));
            info.setStIsValid(CommonConst.VALID);
            info.setStInformColor(color);
            if(CommonConst.Information_Message.equals(type)) {
                //短信新增模板
                String stTemplet = request.getParameter("stTemplet");
                info.setStTemplet(stTemplet);
                info.setStState(CommonConst.InfoState_Send);
            } else {
                info.setStState(CommonConst.InfoState_Wait_Approval);
                info.setStApprovalUserId(Integer.valueOf(approvalId));
            }
            info.setStScanDepartMiddleId(scanDepartMiddleId);
            info.setStInformObject(informObject);

            this.logService.saveLog(user.getStUserId(), "修改信息");
        } else {
            info.setStType(type);
            info.setStInformContent(content);
            info.setStInformFromId(user.getStUserId());
            info.setDtCreate(new Timestamp(System.currentTimeMillis()));
            info.setStIsValid(CommonConst.VALID);
            info.setStInformColor(color);
            if(CommonConst.Information_Message.equals(type)) {
                //短信新增模板
                String stTemplet = request.getParameter("stTemplet");
                info.setStTemplet(stTemplet);
                info.setStState(CommonConst.InfoState_Send);
            } else {
                info.setStState(CommonConst.InfoState_Wait_Approval);
                info.setStApprovalUserId(Integer.valueOf(approvalId));
            }
            info.setStScanDepartMiddleId(scanDepartMiddleId);
            info.setStInformObject(informObject);

            this.logService.saveLog(user.getStUserId(), "发布信息");
        }
        this.inforService.saveInformation(info);
        if(CommonConst.Information_Board.equals(type)) {
            this.backlogService.addBacklog(info.getStInformContent(),CommonConst.BACKLOG_INFORM_APPROVAL,info.getStInformId(),info.getStApprovalUserId());
        } else {
            String re = this.inforService.sendMessage(informObject,content,info.getStInformId());
        }
        model.addAttribute("type",type);
        return "information/Inform_send"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    private String sendMSGAL(String tel,String code) throws Exception
    {
        //发送短信
        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23418011", "9610d7dd1cb4c01a9d1a18895f3fa226");
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        //req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("身份验证");
        JSONObject jb=new JSONObject();
        jb.put("code",code);//变量
        //jb.put("product","注册");
        req.setSmsParamString(jb.toString());
        req.setRecNum(tel);
        req.setSmsTemplateCode("SMS_12740170");
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
        /*//System.out.println(rsp.getBody());
        JSONObject jsonObject=new JSONObject(rsp.getBody());
        JSONObject jsonObject1=jsonObject.getJSONObject("alibaba_aliqin_fc_sms_num_send_response");
        //EncodeTool.convertStreamToString(new String(rsp.getBody()));
        */
        return rsp.getBody();
    }

    /**
     * 审批提交
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/approvalSubmit")
    public String approvalSubmit(HttpServletRequest request, Model model) throws IOException {
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");
        String type = request.getParameter("type");
        String isPass = request.getParameter("isPass");
        String backContent = request.getParameter("editor1");
        String informId = request.getParameter("informId");

        this.inforService.approvalInform(informId,isPass,backContent,user);
        this.backlogService.deleteBacklog(CommonConst.BACKLOG_INFORM_APPROVAL,Integer.valueOf(informId),user.getStUserId());
        model.addAttribute("type",type);

        this.logService.saveLog(user.getStUserId(), "审批信息");
        return "information/Inform_approval"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 显示情报板列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showBoard")
    @ResponseBody
    public BaseResult showBoard(HttpServletRequest request, Model model) throws IOException {
        result = BaseResult.newResultOK(this.inforService.selectBoardList());
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 显示本单位相关部门
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showOrgDepartment")
    @ResponseBody
    public BaseResult showOrgDepartment(HttpServletRequest request, Model model) throws IOException {
        HttpSession session = request.getSession();
        TOrg org = (TOrg)session.getAttribute("session_org");

        result = BaseResult.newResultOK(this.inforService.selectDepartment(org.getStOrgId()));
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }
}
