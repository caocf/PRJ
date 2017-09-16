package com.zjport.officeAssistant.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.base.BaseResult;
import com.zjport.common.service.CommonService;
import com.zjport.file.service.FileService;
import com.zjport.model.TAttachment;
import com.zjport.model.TCalendar;
import com.zjport.model.TCalendarAttendUser;
import com.zjport.model.TUser;
import com.zjport.officeAssistant.service.OfficeAssistantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/**
 * Created by TWQ on 2016/9/13.
 */
@Controller
@RequestMapping("/officeAssistant")
public class CalendarController {
    @Resource(name = "officeAssistantService")
    OfficeAssistantService officeAssistantService;
    @Resource(name = "fileService")
    FileService fileService;
    @Resource(name = "commonService")
    CommonService commonService;

    BaseResult result;

    /**
     * 日程安排主页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendar")
    public String calendar(HttpServletRequest request, Model model) {
        return "officeAssistant/calendar"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }


    /**
     * 日程显示
     * @param request
     * @param response
     */
    @RequestMapping("showCalendar")
    public void showCalendar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");

        int listNum = 0;// 存list内个数
        List<TCalendar> calendarList = officeAssistantService.selectCalendar(user.getStUserId());
        listNum = calendarList.size();
        DateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat sdftime = new SimpleDateFormat("HH:mm:ss");
        String[] datestart = new String[listNum + 1];// 新建数组防止溢出多设一位
        String[] dateend = new String[listNum + 1];
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < listNum; i++) {
            JSONObject jsonObj = new JSONObject();
            String dateSstr = sdfdate.format(calendarList.get(i).getDtStart());
            String timeSstr = sdftime.format(calendarList.get(i).getDtEnd());
            datestart[i] = dateSstr + "T" + timeSstr;
            String dateEstr = null;
            String timeEstr = null;
            // 拼出页面时间控件需要的格式 yyyy-MM-ddTHH:mm:ss
            if ( calendarList.get(i).getDtEnd() == null ) {
                dateEstr = sdfdate.format(calendarList.get(i).getDtStart());
                timeEstr = "23:59:59";
            } else {
                dateEstr = sdfdate.format(calendarList.get(i).getDtEnd());
                timeEstr = sdftime.format(calendarList.get(i).getDtEnd());
            }
            dateend[i] = dateEstr + "T" + timeEstr;
            jsonObj.put("id", calendarList.get(i).getStCalendarId());
            jsonObj.put("title", calendarList.get(i).getStCalendarTitle());
            jsonObj.put("start", datestart[i]);
            jsonObj.put("end", dateend[i]);

            jsonArray.add(jsonObj);
        }
        response.setContentType("application/json; charset=UTF-8");
        Writer out = response.getWriter();
        out.append(jsonArray.toString());
    }

    /**
     * 日程安排新增跳转
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendarAdd")
    public String calendarAdd(HttpServletRequest request, Model model) {
        return "officeAssistant/calendar_add"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 日程安排新增保存
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendarSubmit")
    public String calendarSubmit(HttpServletRequest request, Model model, @RequestParam("attachment") MultipartFile[] attachment) throws IOException {
        HttpSession session = request.getSession();
        TUser user = (TUser)session.getAttribute("session_user");
        String type = request.getParameter("calendarType");
        String title = request.getParameter("title");
        String isUrgent = request.getParameter("calendarIsUrgent");
        String userIds = request.getParameter("useridstr");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String content = request.getParameter("editor1");
        String isAlert = request.getParameter("isAlert");
        String calendarId = request.getParameter("calendarId");

        String saveMiddleAttachmentId = "";      //附件中间关联主键


        String calendarMiddleId = UUID.randomUUID().toString();



        TCalendar calendar = new TCalendar();
        if(!StringUtils.isEmpty(calendarId)) {
            calendar = this.officeAssistantService.getCalendar(calendarId);
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
                this.fileService.delete(calendar.getStAttachmentMiddleId());
                calendar.setStAttachmentMiddleId(saveMiddleAttachmentId);
            }
            if(!StringUtils.isEmpty(userIds)) {
                String[] userId = userIds.split(",");
                for(int i = 0; i < userId.length; i++) {
                    TCalendarAttendUser attendUser = new TCalendarAttendUser();
                    attendUser.setStCalendarMiddleId(calendarMiddleId);
                    attendUser.setStUserId(Integer.valueOf(userId[i]));

                    this.officeAssistantService.saveAttendUser(attendUser);
                }
                this.officeAssistantService.deleteAttendUser(calendar.getStCalendarMiddleId());
                calendar.setStCalendarMiddleId(calendarMiddleId);
            }
            calendar.setDtStart(Timestamp.valueOf(beginTime));
            calendar.setStUserId(user.getStUserId());
            calendar.setDtEnd(Timestamp.valueOf(endTime));
            calendar.setStContent(content);
            calendar.setStType(type);
            calendar.setStUrgentState(isUrgent);
            calendar.setStIsAlert(Boolean.valueOf(isAlert));

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
            if(!StringUtils.isEmpty(userIds)) {
                String[] userId = userIds.split(",");
                for(int i = 0; i < userId.length; i++) {
                    TCalendarAttendUser attendUser = new TCalendarAttendUser();
                    attendUser.setStCalendarMiddleId(calendarMiddleId);
                    attendUser.setStUserId(Integer.valueOf(userId[i]));

                    this.officeAssistantService.saveAttendUser(attendUser);
                }
            }
            calendar.setStCalendarTitle(title);
            calendar.setDtStart(Timestamp.valueOf(beginTime));
            calendar.setStUserId(user.getStUserId());
            calendar.setDtEnd(Timestamp.valueOf(endTime));
            calendar.setStContent(content);
            calendar.setStType(type);
            calendar.setStUrgentState(isUrgent);
            calendar.setStIsAlert(Boolean.valueOf(isAlert));
            calendar.setStAttachmentMiddleId(saveMiddleAttachmentId);
            calendar.setStCalendarMiddleId(calendarMiddleId);
        }

        this.officeAssistantService.saveCalendar(calendar);

        return "officeAssistant/calendar"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 日程安排查看跳转
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendarView")
    public String calendarView(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        TCalendar calendar = this.officeAssistantService.getCalendar(id);

        String userNames = this.officeAssistantService.getNamesByMiddleId(calendar.getStCalendarMiddleId());
        //获取附件
        List<TAttachment> attachmentList = this.fileService.getAttachmentList(calendar.getStAttachmentMiddleId());
        String createUser = this.commonService.getUserName(calendar.getStUserId());

        model.addAttribute("calendar",calendar);
        model.addAttribute("userNames",userNames);
        model.addAttribute("createUser",createUser);
        model.addAttribute("attachmentList",attachmentList);
        return "officeAssistant/calendar_view"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 日程安排编辑跳转
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendarEdit")
    public String calendarEdit(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        TCalendar calendar = this.officeAssistantService.getCalendar(id);

        String userNames = this.officeAssistantService.getNamesByMiddleId(calendar.getStCalendarMiddleId());
        String userIds = this.officeAssistantService.getIdsByMiddleId(calendar.getStCalendarMiddleId());
        //获取附件
        List<TAttachment> attachmentList = this.fileService.getAttachmentList(calendar.getStAttachmentMiddleId());

        model.addAttribute("calendar",calendar);
        model.addAttribute("userNames",userNames);
        model.addAttribute("userIds",userIds);
        model.addAttribute("attachmentList",attachmentList);
        return "officeAssistant/calendar_edit"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 日程安排编辑跳转
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteCalendar")
    public String deleteCalendar(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        TCalendar calendar = this.officeAssistantService.getCalendar(id);
        this.officeAssistantService.deleteCalendar(calendar);
        return "officeAssistant/calendarTurn"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

}
