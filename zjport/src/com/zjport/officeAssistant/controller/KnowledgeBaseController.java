package com.zjport.officeAssistant.controller;

import com.common.base.BaseResult;
import com.zjport.common.service.CommonService;
import com.zjport.file.service.FileService;
import com.zjport.model.TAttachment;
import com.zjport.model.TKnowledgeBase;
import com.zjport.model.TUser;
import com.zjport.officeAssistant.service.OfficeAssistantService;
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
import java.util.List;
import java.util.UUID;

/**
 * Created by TWQ on 2016/9/6.
 */
@Controller
@RequestMapping("/officeAssistant")
public class KnowledgeBaseController {

    @Resource(name = "officeAssistantService")
    OfficeAssistantService officeAssistantService;
    @Resource(name = "fileService")
    FileService fileService;
    @Resource(name = "commonService")
    CommonService commonService;

    BaseResult result;

    /**
     * 知识库main
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/knowledgemain")
    public String knowledgemain(HttpServletRequest request, Model model) {
        return "officeAssistant/knowledgeBase"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 知识库结构
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showStructure")
    @ResponseBody
    public BaseResult showStructure(HttpServletRequest request, Model model) {

        result = BaseResult.newResultOK(this.officeAssistantService.selectStructure());
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 知识库结构下的文件
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showKnowledgeFile")
    @ResponseBody
    public BaseResult showKnowledgeFile(HttpServletRequest request, Model model) {

        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));
        int structureId = Integer.valueOf(request.getParameter("structureId"));
        String search = request.getParameter("search");

        result = BaseResult.newResultOK(this.officeAssistantService.selectKnowledgeBaseList(structureId, search, rows, page));
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 新建文档
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/addFile")
    public String addFile(HttpServletRequest request, Model model) {
        String structureId = request.getParameter("structureId");
        model.addAttribute("structureId",structureId);
        return "officeAssistant/Office_add_file"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 新建文档提交
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/fileSubmit")
    public String fileSubmit(HttpServletRequest request, Model model, @RequestParam("attachment") MultipartFile[] attachment) throws IOException {
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");

        String title = request.getParameter("title");          //文档标题（主题）
        String keyword = request.getParameter("keyword");      //文档关键词
        String content = request.getParameter("editor1");      //文档内容（描述）
        String structureId = request.getParameter("structureId"); //文档所属目录

        String knowledgeBaseId = request.getParameter("knowledgeBaseId"); //文档主键 （用于区分是新增还是更新）

        String saveMiddleAttachmentId = "";      //附件中间关联主键


        TKnowledgeBase base = new TKnowledgeBase();
        if(!StringUtils.isEmpty(knowledgeBaseId)) {
            base = this.officeAssistantService.getKnowledgeBase(knowledgeBaseId);
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
                this.fileService.delete(base.getStAttachmentMiddleId());
                base.setStAttachmentMiddleId(saveMiddleAttachmentId);
            }
            base.setStBaseTitle(title);
            base.setStBaseKeyword(keyword);
            base.setStBaseContent(content);
            base.setStUserId(user.getStUserId());
            base.setDtSend(new Timestamp(System.currentTimeMillis()));
            base.setStStructureId(Integer.valueOf(structureId));
            base.setStIsValid(CommonConst.VALID);
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
            base.setStBaseTitle(title);
            base.setStBaseKeyword(keyword);
            base.setStBaseContent(content);
            base.setStUserId(user.getStUserId());
            base.setDtSend(new Timestamp(System.currentTimeMillis()));
            base.setStStructureId(Integer.valueOf(structureId));
            base.setStAttachmentMiddleId(saveMiddleAttachmentId);
            base.setStIsValid(CommonConst.VALID);
        }


        this.officeAssistantService.saveKnowledgeBase(base);

        return "officeAssistant/knowledge"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 查看文档
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/baseDetail")
    public String basDetail(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        TKnowledgeBase base = this.officeAssistantService.getKnowledgeBase(id);

        String structureName = this.officeAssistantService.getStructureName(base.getStStructureId());
        String fromUser = this.commonService.getUserName(base.getStUserId());

        //获取附件
        List<TAttachment> attachmentList = this.fileService.getAttachmentList(base.getStAttachmentMiddleId());

        model.addAttribute("knowledgeBase",base);
        model.addAttribute("fromUser",fromUser);
        model.addAttribute("structureName",structureName);
        model.addAttribute("attachmentList",attachmentList);
        return "officeAssistant/Office_view_file"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 编辑文档
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/baseEdit")
    public String baseEdit(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        TKnowledgeBase base = this.officeAssistantService.getKnowledgeBase(id);

        //获取附件
        List<TAttachment> attachmentList = this.fileService.getAttachmentList(base.getStAttachmentMiddleId());

        model.addAttribute("knowledgeBase",base);
        model.addAttribute("attachmentList",attachmentList);
        return "officeAssistant/Office_edit_file"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 新增修改目录
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/structureSubmit")
    @ResponseBody
    public BaseResult structureSubmit(HttpServletRequest request, Model model) {
        String structureId = request.getParameter("structure");
        String parentStructure = request.getParameter("parentStructure");
        String structureName = request.getParameter("structureName");
        String structureDescribe = request.getParameter("structureDescribe");

        this.officeAssistantService.saveKnowledgeBaseStructure(structureId,parentStructure,structureName,structureDescribe);
        //System.out.println("成功进入后台"+parentStructure);
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 删除目录
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/structureDelete")
    @ResponseBody
    public BaseResult structureDelete(HttpServletRequest request, Model model) {
        String structureId = request.getParameter("id");

        this.officeAssistantService.deleteStructure(structureId);
        //System.out.println("成功进入后台"+parentStructure);
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 批量删除文件
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public BaseResult batchDelete(HttpServletRequest request, Model model) {
        String fileAll = request.getParameter("fileAll");

        String[] fileArray = fileAll.split(",");
        int num = fileArray.length;
        for(int i = 0; i<num; i++) {
            this.officeAssistantService.deleteFile(fileArray[i]);
        }
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 批量移动文件
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/batchMove")
    @ResponseBody
    public BaseResult batchMove(HttpServletRequest request, Model model) {
        String moveAll = request.getParameter("moveAll");
        String parentStructure = request.getParameter("parentId");

        String[] moveArray = moveAll.split(",");
        int num = moveArray.length;
        for(int i = 0; i<num; i++) {
            this.officeAssistantService.moveFile(moveArray[i],parentStructure);
        }
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }
}
