package net.Intercepter;

import net.modol.SystemLogEN;
import net.service.SystemService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by Admin on 2016/11/25.
 */
public class LogInterceptor implements HandlerInterceptor
{
    @Resource
    SystemService systemService;

    String arry[]={"HomePage","kqsp_add", "ccsp_add","jbsp_add","outerApp_list","innerApp_list","mykq_list","cq_list","load_list","filecheck_list","file_add",
            "myfile_list","kqsp_check","app_add","mysp_detal","kqsp_detal","mykq_detal","kqsp_list","file_check","myfile_check","register_check","register_list",
            "user_check","user_list","user_edit","contact_list","message_list","message_check","xinxi_list","xinxi_check","myxinxi_list","xinxiquery_list","whzysp_list"};
    String opname[]={"登录","请假申请", "出差申请","加班申请","查看公众版app列表","查看内部版app列表","查看我的考勤","cq_list","load_list","filecheck_list","file_add",
            "myfile_list","考勤审批","app_add","mysp_detal","kqsp_detal","mykq_detal","kqsp_list","file_check","myfile_check","register_check","查看公众用户审批列表",
            "user_check","user_list","user_edit","查看通讯录","查看意见箱","message_check","xinxi_list","通知审核","查看已发布的信息","查看通知列表","危货作业审批"};

    @Override
    public boolean preHandle(HttpServletRequest request,  HttpServletResponse response, Object handler) throws Exception
    {
        String path=request.getServletPath();
        String contextPath=request.getContextPath();
        HttpSession session=request.getSession(true);

        for(int i=0;i<arry.length;i++)
        {
            if(path.equals("/"+contextPath+arry[i]))
            {
                System.out.println(arry[i]);
                SystemLogEN systemLogEN=new SystemLogEN();
                String name=(String)session.getAttribute("name");
                if(name==null||"".equals(name))
                {
                    return true;
                }
                systemLogEN.setUsername(name);
                systemLogEN.setOp(opname[i]);
                systemLogEN.setOptime(new Date());
                systemService.saveEN(systemLogEN);
            }
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }
}
