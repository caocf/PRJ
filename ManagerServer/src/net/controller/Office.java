package net.controller;

import common.BaseRecords;
import common.BaseResult;
import common.Time;
import net.modol.*;
import net.service.OfficeService;
import net.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 2016/7/26.
 */
@Controller
public class Office
{
    final String approve[]={"待审批","正在审批","准许","驳回"};
    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
    @Resource
    OfficeService officeService;
    SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat sd2=new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @RequestMapping(value = "kqsp_add",method = RequestMethod.GET)
    public String kqsp_add(HttpServletRequest request,Model model) throws Exception
    {
        String id=request.getParameter("id");
        model.addAttribute("id",id);
        return "WEB-INF/calendar/kqsp/kqsp_add";
    }
    @RequestMapping(value = "ccsp_add",method = RequestMethod.GET)
    public String ccsp_add(HttpServletRequest request,Model model) throws Exception
    {
        String id=request.getParameter("id");
        model.addAttribute("id",id);
        return "WEB-INF/calendar/kqsp/ccsp_add";
    }
    @RequestMapping(value = "jbsp_add",method = RequestMethod.GET)
    public String jbsp_add(HttpServletRequest request,Model model) throws Exception
    {
        String id=request.getParameter("id");
        model.addAttribute("id",id);
        return "WEB-INF/calendar/kqsp/jbsp_add";
    }
    @RequestMapping(value = "outerApp_list",method = RequestMethod.GET)
    public String outerApp_list(HttpServletRequest request) throws Exception
    {
        return "WEB-INF/appset/outerApp/outerApp_list";
    }
    @RequestMapping(value = "innerApp_list",method = RequestMethod.GET)
    public String innerApp_list(HttpServletRequest request) throws Exception
    {
        return "WEB-INF/appset/innerApp/innerApp_list";
    }
    @RequestMapping(value = "mykq_list",method = RequestMethod.GET)
    public String mykq_list(HttpServletRequest request) throws Exception
    {
        return "WEB-INF/calendar/mykq/mykq_list";
    }
    @RequestMapping(value = "cq_list",method = RequestMethod.GET)
    public String cq_list(HttpServletRequest request) throws Exception
    {
        return "WEB-INF/calendar/cq/cq_list";
    }
    @RequestMapping(value = "load_list",method = RequestMethod.GET)
    public String load_list(HttpServletRequest request) throws Exception
    {
        return "WEB-INF/appset/load_list/load_list";
    }
    @RequestMapping(value = "filecheck_list",method = RequestMethod.GET)
    public String filecheck_list(HttpServletRequest request) throws Exception
    {
        return "WEB-INF/file/filecheck/filecheck_list";
    }
    @RequestMapping(value = "file_add",method = RequestMethod.GET)
    public String file_add(HttpServletRequest request,Model model) throws Exception
    {
        String type=request.getParameter("type");
        model.addAttribute("type",type);
        return "WEB-INF/file/file_add";
    }
    @RequestMapping(value = "myfile_list",method = RequestMethod.GET)
    public String myfile_list(HttpServletRequest request) throws Exception
    {
        return "WEB-INF/file/myfile/myfile_list";
    }
    @RequestMapping(value = "kqsp_check",method = RequestMethod.GET)
    public String kqsp_check(HttpServletRequest request,Model model) throws Exception
    {
        String type=request.getParameter("type");
        String id=request.getParameter("id");
        model.addAttribute("type",type);
        model.addAttribute("id",id);
        return "WEB-INF/calendar/kqsp/kqsp_check";
    }
    @RequestMapping(value = "app_add",method = RequestMethod.GET)
    public String app_add(HttpServletRequest request,Model model) throws Exception
    {
        String type=request.getParameter("type");
        model.addAttribute("type",type);
        return "WEB-INF/appset/app_add";
    }
    @RequestMapping(value = "mysp_detal",method = RequestMethod.GET)
    public String mysp_detal(HttpServletRequest request,Model model) throws Exception
    {
        String type=request.getParameter("type");
        String id=request.getParameter("id");
        model.addAttribute("type",type);
        model.addAttribute("id",id);
        return "WEB-INF/calendar/mykq/mysp_detal";
    }
    @RequestMapping(value = "kqsp_detal",method = RequestMethod.GET)
    public String kqsp_detal(HttpServletRequest request,Model model) throws Exception
    {
        String type=request.getParameter("type");
        String id=request.getParameter("id");
        model.addAttribute("type",type);
        model.addAttribute("id",id);
        return "WEB-INF/calendar/kqsp/kqsp_detal";
    }
    @RequestMapping(value = "mykq_detal",method = RequestMethod.GET)
    public String mykq_detal(HttpServletRequest request,Model model) throws Exception
    {
        String type=request.getParameter("type");
        String id=request.getParameter("id");
        model.addAttribute("type",type);
        model.addAttribute("id",id);
        return "WEB-INF/calendar/mykq/mykq_detal";
    }
    @RequestMapping(value = "rolemanager_list",method = RequestMethod.GET)
    public String rolemanager_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/system/rolemanager/rolemanager_list";
    }
    //考勤审批
    @RequestMapping(value = "kqsp_list",method = RequestMethod.GET)
    public String kqsp_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/calendar/kqsp/kqsp_list";
    }
    @RequestMapping(value = "file_check",method = RequestMethod.GET)
    public String file_check(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/file/filecheck/file_check";
    }
    @RequestMapping(value = "myfile_check",method = RequestMethod.GET)
    public String myfile_check(HttpServletRequest request,Model model) throws Exception
    {
        String type=request.getParameter("type");
        model.addAttribute("type",type);
        return "WEB-INF/file/myfile/myfile_check";
    }
    @RequestMapping(value = "register_check",method = RequestMethod.GET)
    public String register_check(HttpServletRequest request,Model model) throws Exception
    {
        String type=request.getParameter("type");
        String idoftype=request.getParameter("idoftype");
        model.addAttribute("type",type);
        model.addAttribute("idoftype",idoftype);
        return "WEB-INF/publicUser/register/register_check";
    }
    @RequestMapping(value = "register_list",method = RequestMethod.GET)
    public String register_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/publicUser/register/register_list";
    }
    @RequestMapping(value = "datadictionary_list",method = RequestMethod.GET)
    public String datadictionary_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/system/datadictionary/datadictionary_list";
    }
    @RequestMapping(value = "ninecheck_list",method = RequestMethod.GET)
    public String ninecheck_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/nine/ninecheck_list";
    }
    @RequestMapping(value = "jcjj_list",method = RequestMethod.GET)
    public String jcjj_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/nine/jcjj_list";
    }
    @RequestMapping(value = "user_check",method = RequestMethod.GET)
    public String user_check(HttpServletRequest request,Model model) throws Exception
    {
        String type=request.getParameter("type");
        String idoftype=request.getParameter("idoftype");
        model.addAttribute("type",type);
        model.addAttribute("idoftype",idoftype);
        return "WEB-INF/publicUser/usermanager/user_check";
    }
    @RequestMapping(value = "user_list",method = RequestMethod.GET)
    public String user_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/publicUser/usermanager/user_list";
    }
    @RequestMapping(value = "user_edit",method = RequestMethod.GET)
    public String user_edit(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/publicUser/usermanager/user_edit";
    }
    @RequestMapping(value = "contact_list",method = RequestMethod.GET)
    public String contact_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/contact/contact_list";
    }
    @RequestMapping(value = "message_list",method = RequestMethod.GET)
    public String message_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/envelope/message_list";
    }
    @RequestMapping(value = "message_check",method = RequestMethod.GET)
    public String message_check(HttpServletRequest request,Model model) throws Exception
    {
        String id=request.getParameter("id");
        model.addAttribute("mid",id);
        return "WEB-INF/envelope/message_check";
    }
    @RequestMapping(value = "xinxi_list",method = RequestMethod.GET)
    public String xinxi_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/commenting/xinxisp/xinxi_list";
    }
    @RequestMapping(value = "xinxi_check",method = RequestMethod.GET)
    public String xinxi_check(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/commenting/xinxisp/xinxi_check";
    }
    @RequestMapping(value = "myxinxi_list",method = RequestMethod.GET)
    public String myxinxi_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/commenting/myxinxi/myxinxi_list";
    }
    @RequestMapping(value = "xinxiquery_list",method = RequestMethod.GET)
    public String xinxiquery_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/commenting/xinxiquery/xinxiquery_list";
    }
    @RequestMapping(value = "xinxi_add",method = RequestMethod.GET)
    public String xinxi_add(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/commenting/xinxi_add";
    }
    @RequestMapping(value = "xinxi_detal",method = RequestMethod.GET)
    public String xinxi_detal(HttpServletRequest request,Model model) throws Exception
    {
        String id=request.getParameter("id");
        String mode=request.getParameter("mode");
        model.addAttribute("id",id);
        model.addAttribute("mode",mode);
        return "WEB-INF/commenting/xinxi_detal";
    }
    @RequestMapping(value = "whjgsp_check",method = RequestMethod.GET)
    public String whjgsp_check(HttpServletRequest request,Model model) throws Exception
    {
        String id=request.getParameter("id");
        model.addAttribute("id",id);
        return "WEB-INF/check/whjgsp/whjgsp_check";
    }
    @RequestMapping(value = "whjgsp_detal",method = RequestMethod.GET)
    public String whjgsp_detal(HttpServletRequest request,Model model) throws Exception
    {
        String id=request.getParameter("id");
        model.addAttribute("id",id);
        return "WEB-INF/check/whjgsp/whjgsp_detal";
    }
    @RequestMapping(value = "whjgsp_list",method = RequestMethod.GET)
    public String whjgsp_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/check/whjgsp/whjgsp_list";
    }
    @RequestMapping(value = "whzysp_check",method = RequestMethod.GET)
    public String whzysp_check(HttpServletRequest request,Model model) throws Exception
    {
        String id=request.getParameter("id");
        model.addAttribute("id",id);
        return "WEB-INF/check/whzysp/whzysp_check";
    }
    @RequestMapping(value = "whzysp_list",method = RequestMethod.GET)
    public String whzysp_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/check/whzysp/whzysp_list";
    }
    @RequestMapping(value = "whzysp_detal",method = RequestMethod.GET)
    public String whzysp_detal(HttpServletRequest request,Model model) throws Exception
    {
        String id=request.getParameter("id");
        model.addAttribute("id",id);
        return "WEB-INF/check/whzysp/whzysp_detal";
    }
    @RequestMapping(value = "wzsp_check",method = RequestMethod.GET)
    public String wzsp_check(HttpServletRequest request,Model model) throws Exception
    {
        String id=request.getParameter("id");
        model.addAttribute("id",id);
        return "WEB-INF/check/wzsp/wzsp_check";
    }
    @RequestMapping(value = "wzsp_update",method = RequestMethod.GET)
    public String wzsp_update(HttpServletRequest request,Model model) throws Exception
    {
        String id=request.getParameter("id");
        model.addAttribute("id",id);
        return "WEB-INF/check/wzsp/wzsp_update";
    }
    @RequestMapping(value = "wzsp_detal",method = RequestMethod.GET)
    public String wzsp_detal(HttpServletRequest request,Model model) throws Exception
    {
        String id=request.getParameter("id");
        model.addAttribute("id",id);
        return "WEB-INF/check/wzsp/wzsp_detal";
    }
    @RequestMapping(value = "wzsp_list",method = RequestMethod.GET)
    public String wzsp_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/check/wzsp/wzsp_list";
    }
    @RequestMapping(value = "shouye",method = RequestMethod.GET)
    public String shouye(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/shouye/shouye";
    }
    @RequestMapping(value = "contact_detail", method = RequestMethod.GET)
    public String contact_detail(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        model.addAttribute("id", id);
        return "WEB-INF/contact/contact_detail";
    }
    @RequestMapping(value = "contact_update", method = RequestMethod.GET)
    public String contact_update(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        model.addAttribute("id", id);
        return "WEB-INF/contact/contact_update";
    }
    @RequestMapping(value = "evid_list",method = RequestMethod.GET)
    public String evid_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/evid/evid_list";
    }
    @RequestMapping(value = "evid_detail",method = RequestMethod.GET)
    public String evid_detail(HttpServletRequest request,Model model) throws Exception
    {
        String id = request.getParameter("id");
        model.addAttribute("id", id);
        return "WEB-INF/evid/evid_detail";
    }
    @RequestMapping(value = "jcjjcheck_list",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult jcjjcheck_list(HttpServletRequest request, Model model, HttpSession session) throws Exception
    {
        String id = request.getParameter("id");
        session.setAttribute("jcjjid",id);
        return BaseResult.newResultOK();
    }
    //WEB-INF/nine/jcjjcheck_list
    @RequestMapping(value = "jcjjcheck_list1", method = RequestMethod.GET)
    public String jcjjcheck_list1(HttpServletRequest request, Model model)
    {
        String type = request.getParameter("type");
        model.addAttribute("type", type);
        return "WEB-INF/nine/jcjjcheck_list";
    }
    @RequestMapping(value = "systemlog_list",method = RequestMethod.GET)
    public String systemlog_list(HttpServletRequest request,Model model) throws Exception
    {
        return "WEB-INF/system/log/systemloglist";
    }


    //按审批人获取假条
    @ResponseBody
    @RequestMapping(value = "LeaveAndOvertimefinish",method = RequestMethod.POST)
    public BaseResult LeaveAndOvertimefinish(HttpServletRequest request) throws Exception
    {
        //必选条件
        String s=request.getParameter("userid");
        String page=request.getParameter("page");
        //可选条件
        String s1=request.getParameter("key");
        String key=s1==null?"":s1;
        String s2=request.getParameter("type");
        int type1=s2==null?0:Integer.valueOf(s2);
        int type2=s2==null?4:Integer.valueOf(s2);

        return officeService.LeaveAndOvertimefinish(s,Integer.valueOf(page),key,type1,type2);
    }
    //按申请人获取假条
    @ResponseBody
    @RequestMapping(value = "LeaveAndOvertimeApply",method = RequestMethod.POST)
    public BaseResult LeaveAndOvertimeApply(HttpServletRequest request) throws Exception
    {
        String s=request.getParameter("userid");
        String page=request.getParameter("page");
        //可选条件
        String s1=request.getParameter("key");
        String key=s1==null?"":s1;
        String s2=request.getParameter("type");
        int type1=s2==null?0:Integer.valueOf(s2);
        int type2=s2==null?4:Integer.valueOf(s2);

        return officeService.LeaveAndOvertimeApply(s,Integer.valueOf(page),key,type1,type2);
    }
    //按ID获取假条
    @ResponseBody
    @RequestMapping(value = "showLeaveAndOvertimeApproval",method = RequestMethod.POST)
    public LeaveOrOt showLeaveAndOvertimeApproval(HttpServletRequest request) throws Exception
    {
        String s=request.getParameter("leaveOrOtID1");

        return officeService.showLeaveAndOvertimeApproval(Integer.valueOf(s));
    }
    //新建假条
    @ResponseBody
    @RequestMapping(value = "newLeaveAndOvertime",method = RequestMethod.POST)
    public BaseResult newLeaveAndOvertime(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("applier");
        String s2=request.getParameter("kindName");
        String s3=request.getParameter("beginDate");
        String s4=request.getParameter("endDate");
        int last=officeService.getRealTime(s3,s4);

        String s6=request.getParameter("Reason");
        String s7=request.getParameter("address");
        String s8=request.getParameter("approveid");

        LeaveOrOt leaveOrOt=new LeaveOrOt();
        JcxxYhEN user=userService.getUserByID(s1);
        if(user!=null)
        {
            leaveOrOt.setUser(user);
        }
        LeaveOrOtKind kind=officeService.getKindByID(Integer.valueOf(s2));
        if(kind!=null)
        {
            leaveOrOt.setLeaveOrOtKind(kind);
        }
        leaveOrOt.setBeginDate(sd2.parse(s3));
        leaveOrOt.setEndDate(sd2.parse(s4));
        leaveOrOt.setLastDate(last);
        leaveOrOt.setLeaveOrOtReason(s6);
        leaveOrOt.setAddress(s7);
        JcxxYhEN Approval=userService.getUserByID(s8);
        if(Approval!=null)
        {
            leaveOrOt.setApprovalID1(Approval);
        }

        leaveOrOt.setApprovalResult(approve[0]);//待审批
        leaveOrOt.setLeaveOrOtDate(sd.format(new Date()));
        leaveOrOt.setApprovalResult3(1);//正式提交状态
        String num=String.valueOf(new Random().nextInt(999999));
        leaveOrOt.setNum(num);
        int id=officeService.saveEN(leaveOrOt);

        return new BaseResult(id,"提交成功");
    }


    //审批假条
    @ResponseBody
    @RequestMapping(value = "CheckLeave",method = RequestMethod.POST)
    public BaseResult CheckLeave(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("id");
        String s2=request.getParameter("opinon");
        String s3=request.getParameter("result");

        LeaveOrOt leaveOrOt=officeService.showLeaveAndOvertimeApproval(Integer.valueOf(s1));
        leaveOrOt.setApprovalResult(approve[Integer.valueOf(s3)-1]);
        leaveOrOt.setApprovalOpinion1(s2);
        leaveOrOt.setApprovalTime1(sd.format(new Date()));

        officeService.updateEN(leaveOrOt);

        return new BaseResult(1,"");
    }
    //按id查审批人列表
    @ResponseBody
    @RequestMapping(value = "Approvers",method = RequestMethod.POST)
    public BaseResult Approvers(HttpServletRequest request) throws Exception
    {
        String id=request.getParameter("id");
        return new BaseResult(officeService.Approvers(id));
    }
    //假条撤回
    @ResponseBody
    @RequestMapping(value = "LeavePullBack",method = RequestMethod.POST)
    public BaseResult LeavePullBack(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("id");

        LeaveOrOt leaveOrOt=officeService.showLeaveAndOvertimeApproval(Integer.valueOf(s1));
        leaveOrOt.setApprovalResult3(0);//撤回状态

        officeService.updateEN(leaveOrOt);

        return new BaseResult(1,"");
    }
    //假条重新提交
    @ResponseBody
    @RequestMapping(value = "LeaveReCommit",method = RequestMethod.POST)
    public BaseResult LeaveReCommit(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("id");

        LeaveOrOt leaveOrOt=officeService.showLeaveAndOvertimeApproval(Integer.valueOf(s1));
        leaveOrOt.setApprovalResult3(1);//正式提交状态
        leaveOrOt.setApprovalResult(approve[0]);
        leaveOrOt.setApprovalOpinion1("");
        officeService.updateEN(leaveOrOt);

        return new BaseResult(1,"");
    }
    //假条删除
    @ResponseBody
    @RequestMapping(value = "DeleteLeave",method = RequestMethod.POST)
    public BaseResult DeleteLeave(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("id");

        LeaveOrOt leaveOrOt=officeService.showLeaveAndOvertimeApproval(Integer.valueOf(s1));

        officeService.deleteEN(leaveOrOt);

        return new BaseResult(1,"");
    }
    //按ID查询组织下员工考勤数据
    @ResponseBody
    @RequestMapping(value = "CountByID",method = RequestMethod.POST)
    public BaseResult CountByID(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("id");
        String type=request.getParameter("type");
        String date1=request.getParameter("date1");
        String date2=request.getParameter("date2");

        Duty duty=officeService.CountByID(s1,type,date1,date2);


        return new BaseResult(1,"",duty);
    }
    //获取工作日时长
    @ResponseBody
    @RequestMapping(value = "getRealTime",method = RequestMethod.POST)
    public BaseResult getRealTime(HttpServletRequest request) throws Exception
    {
        String d1=request.getParameter("begin");
        String d2=request.getParameter("end");
        int perid=officeService.getRealTime(d1,d2);
        return  new BaseResult(perid,"");
    }


    //按用户获取日程
    @ResponseBody
    @RequestMapping(value = "AgendaByUser",method = RequestMethod.POST)
    public List<?> AgendaByUser(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("id");


        return officeService.AgendaByUser(s1);
    }
    //按用户和时间获取日程
    @ResponseBody
    @RequestMapping(value = "AgendaByUserAndTime",method = RequestMethod.POST)
    public List<?> AgendaByUserAndTime(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("id");
        String s2=request.getParameter("time");
        Date d1=sd1.parse(s2+" 00:00:00");
        Date d2=new Date(d1.getTime()+1000*60*60*24);

        return officeService.AgendaByUserAndTime(s1,d1,d2);
    }

    //按id获取日程
    @ResponseBody
    @RequestMapping(value = "AgendaByID",method = RequestMethod.POST)
    public ScheduleBaseEN AgendaByID(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("aid");


        return officeService.AgendaByID(Integer.valueOf(s1));
    }

    @Resource
    UserService userService;
    //按用户保存日程
    @ResponseBody
    @RequestMapping(value = "SaveAgendaByUser",method = RequestMethod.POST)
    public BaseResult SaveAgendaByUser(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("eventName");
        String s2=request.getParameter("eventTime");
        String s3=request.getParameter("eventLocation");
        String s4=request.getParameter("eventContent");
        String s5=request.getParameter("userId");
        String s6=request.getParameter("eventKind");
        String s7=request.getParameter("eventRemind");
        String s8=request.getParameter("eventRemindType");

        ScheduleBaseEN scheduleBaseEN=new ScheduleBaseEN();
        scheduleBaseEN.setName(s1);
        scheduleBaseEN.setScheduletime(sd1.parse(s2+":00"));
        scheduleBaseEN.setLocation(s3);
        scheduleBaseEN.setContent(s4);
        scheduleBaseEN.setType(s6);
        scheduleBaseEN.setRemind(s7);
        scheduleBaseEN.setRemindtype(s8);

        JcxxYhEN user=userService.getUserByID(s5);
        scheduleBaseEN.setUser(user);

        long id=officeService.saveEN(scheduleBaseEN);

        return new BaseResult((int)id,"");
    }

}
