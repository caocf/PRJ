package net.controller;

import common.BaseResult;
import common.DataList;
import common.FileDownload;
import common.RandomImage;
import common.md5.MD5;
import net.modol.JcxxYhEN;
import net.modol.UserBaseEN;
import net.modol.UserPermissionEN;
import net.modol.UserRoleEN;
import net.service.UserService;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.*;
import java.lang.System;
import java.util.*;

/**
 * Created by WZD on 2016/7/7.
 */
@Controller
public class User
{
    @Resource
    UserService userService;
    //登录
    @RequestMapping(value = "LoginPhone",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult LoginPhone(HttpServletRequest request,HttpSession session)
    {
        /*String sid=request.getParameter("sessionid");
        System.out.println(request.getSession().getId());
        if("".equals(sid)sid.equals(request.getSession().getId()))
        {

        }*/

        String s1=request.getParameter("Username");
        String s2=request.getParameter("PSW");
        String s2md5= MD5.string2MD5(s2);
        System.out.println(s2md5);
        JcxxYhEN userBaseEN=userService.getUserByName(s1);
        if(userBaseEN==null)
        {
            return new BaseResult(-1,"用户或密码错误");
        }
        if(!userBaseEN.getMm().toLowerCase().equals(s2md5))
        {
            return new BaseResult(-2,"用户或密码错误");
        }
        else
        {

            //session.setAttribute("zh",s);
            session.setAttribute("id",userBaseEN.getId());
            session.setAttribute("name",userBaseEN.getXm());
            session.setAttribute("dep",userBaseEN.getBmmc());
            session.setAttribute("bm",userBaseEN.getBm());
            session.setAttribute("dz",userBaseEN.getPersonal());
            int id=userBaseEN.getUserRoleEN().getId();
            session.setAttribute("js",String.valueOf(id));

            return new BaseResult(1,"登录成功",userBaseEN);
        }
    }
    @RequestMapping(value = "ko",method = RequestMethod.GET)

    public void ko(HttpServletRequest request)
    {
        String a=null;
        a.length();
    }
    //修改密码
    @RequestMapping(value = "changepsw",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult ChangePSW(HttpServletRequest request,HttpSession session)
    {
        String s1=request.getParameter("Username");
        String s2=request.getParameter("Pswold");
        String s2md5=MD5.string2MD5(s2);

        String username=(String) session.getAttribute("name");
        if(username==null||s1==null||!s1.equals(username))
        {
            return new BaseResult(-3,"没有修改权限");
        }
        String s3=request.getParameter("Pswnew");
        String s3md5=MD5.string2MD5(s3);
        JcxxYhEN userBaseEN=userService.getUserByID(s1);
        if(userBaseEN==null)
        {
            return new BaseResult(-1,"用户名不存在");
        }
        if(!userBaseEN.getMm().toLowerCase().equals(s2md5))
        {
            return new BaseResult(-2,"密码错误");
        }
        userBaseEN.setMm(s3md5);
        userService.updateEN(userBaseEN);
        return new BaseResult(1,"修改成功");
    }
    //获取全部用户列表
    @RequestMapping(value = "AllUsers",method = RequestMethod.POST)
    @ResponseBody
    public List<?> AllUsers(HttpServletRequest request)
    {
        return userService.AllUsers();
    }
    /*//登录页
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String Login(HttpServletRequest request)
    {
        return "Login/login";
    }*/

    //按层级获取用户列表
    @RequestMapping(value = "UsersByLevel",method = RequestMethod.POST)
    @ResponseBody
    public List<?> UsersByLevel(HttpServletRequest request)
    {
        String s=request.getParameter("level");
        return userService.UsersByLevel(s);
    }
    //按pid获取用户列表
    @RequestMapping(value = "UsersByPID",method = RequestMethod.POST)
    @ResponseBody
    public List<?> UsersByPID(HttpServletRequest request)
    {
        String s=request.getParameter("pid");
        return userService.UsersByPID(s);
    }
    //按名字搜索用户组织
    @RequestMapping(value = "DirectoryByName",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult DirectoryByName(HttpServletRequest request)
    {
        String s=request.getParameter("Name");
        return userService.DirectoryByName(s);
    }
    //按提示搜索用户名字列表
    @RequestMapping(value = "NamesByTip",method = RequestMethod.POST)
    @ResponseBody
    public List<?> NamesByTip(HttpServletRequest request)
    {
        String s=request.getParameter("tip");
        return userService.NamesByTip(s);
    }

    //按层级获取部门列表
    @RequestMapping(value = "DepByLevel",method = RequestMethod.POST)
    @ResponseBody
    public List<?> DepByLevel(HttpServletRequest request)
    {
        String s=request.getParameter("level");
        return userService.DepByLevel(s);
    }
    //按pid获部门列表
    @RequestMapping(value = "DepByPID",method = RequestMethod.POST)
    @ResponseBody
    public List<?> DepByPID(HttpServletRequest request)
    {
        String s=request.getParameter("pid");
        return userService.DepByPID(s);
    }

    //按提示搜索部门名字列表
    @RequestMapping(value = "DepByTip",method = RequestMethod.POST)
    @ResponseBody
    public List<?> DepByTip(HttpServletRequest request)
    {
        String s=request.getParameter("tip");
        return userService.DepByTip(s);
    }

    //按名字搜索部门组织
    @RequestMapping(value = "DepsByName",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult DepsByName(HttpServletRequest request)
    {
        String s=request.getParameter("Name");
        return userService.DepsByName(s);
    }

    //网页随机验证码图片
    @RequestMapping(value = "Code",method = RequestMethod.GET)
    public void Code(HttpServletRequest request,HttpServletResponse response,HttpSession session)
    {
        //System.out.println("dddd");
        RandomImage validateImage = new RandomImage("0123456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ",
               4,134, 52);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
            ImageIO.write(validateImage.getValidateImage(), "JPEG", ios);
            InputStream inputStream = new ByteArrayInputStream(bos.toByteArray());
            bos.close();
            FileDownload.download(inputStream,response);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        //String s=session.getId();

        String validateString=validateImage.getValidateString().toLowerCase();
        session.setAttribute("code",validateString);
        //WebUtils.setSessionAttribute(request,"code",validateString);
    }

    //网页登录
    @RequestMapping(value = "PageLogin",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult PageLogin(HttpServletRequest request,HttpSession httpSession)
    {
        String s=request.getParameter("username");
        String s1=request.getParameter("password");
        String s2=request.getParameter("email");

        Object objtimes= request.getServletContext().getAttribute(s);
        int times=0;
        if(objtimes!=null)
        {
            Object obj=(Object)request.getServletContext().getAttribute(s+"T");
            long time=(long)obj;
            times=(int)objtimes;
            if(times>=3)
            {
                if(new Date().getTime()-time<10*60*1000)
                    return new BaseResult(-3,"连续多次登陆错误，账号已被锁定");
            }
        }
        String s1md5=MD5.string2MD5(s1);
        //String ss=httpSession.getId();

        JcxxYhEN userBaseEN=userService.getUserByName(s);
        if(userBaseEN==null)
        {
            return new BaseResult(-1,"用户或密码错误");
        }
        if(!userBaseEN.getMm().toLowerCase().equals(s1md5))
        {
            Object timeOj=request.getServletContext().getAttribute(s);
            if(timeOj==null)
            {
                request.getServletContext().setAttribute(s,1);
            }else
            {
                int t=(int)timeOj;
                request.getServletContext().setAttribute(s,++t);
            }
            request.getServletContext().setAttribute(s+"T",new Date().getTime());
            return new BaseResult(-2,"用户或密码错误");
        }
        else
        {
            String code=(String) httpSession.getAttribute("code");
            if(s2.toLowerCase().equals(code))
            {
                httpSession.setAttribute("zh",s);
                httpSession.setAttribute("id",userBaseEN.getId());
                httpSession.setAttribute("name",userBaseEN.getXm());
                httpSession.setAttribute("dep",userBaseEN.getBmmc());
                httpSession.setAttribute("bm",userBaseEN.getBm());
                httpSession.setAttribute("dz",userBaseEN.getPersonal());
                int id=userBaseEN.getUserRoleEN().getId();
                httpSession.setAttribute("js",String.valueOf(id));

                httpSession.removeAttribute("code");

                return new BaseResult(1,"登录成功",userBaseEN);

            }
            else
            {

                httpSession.setAttribute("usertimes",++times);
                return new BaseResult(-3,"验证码错误",userBaseEN);
            }
        }
    }
    //内部登录
    @RequestMapping(value = "QeuryAccount",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult QeuryAccount(HttpServletRequest request,HttpSession httpSession)
    {
        String s=request.getParameter("account");
        JcxxYhEN userBaseEN=userService.getUserByAccount(s);

        if(userBaseEN==null)
        {
            return BaseResult.newResultFailed();
        }else return BaseResult.newResultOK();
    }
    //内部登录
    @RequestMapping(value = "InnerLogin",method = RequestMethod.GET)
    public String InnerLogin(HttpServletRequest request,HttpSession httpSession)
    {
        String s=request.getParameter("account");
        String s1=request.getParameter("code");

        String s1md5=MD5.string2MD5("123456");
        if(!s1md5.equals(s1.toLowerCase()))
        {
            return "redirect:/";
        }

        JcxxYhEN userBaseEN=userService.getUserByAccount(s);
        if(userBaseEN==null)
        {
            return "redirect:/";
        }
        else
        {
            httpSession.setAttribute("zh",s);
            httpSession.setAttribute("id",userBaseEN.getId());
            httpSession.setAttribute("name",userBaseEN.getXm());
            httpSession.setAttribute("dep",userBaseEN.getBmmc());
            httpSession.setAttribute("bm",userBaseEN.getBm());
            httpSession.setAttribute("dz",userBaseEN.getPersonal());
            int id=userBaseEN.getUserRoleEN().getId();
            httpSession.setAttribute("js",String.valueOf(id));

            return "redirect:HomePage";
        }
    }
    @RequestMapping(value = "HomePage",method = RequestMethod.GET)
    public String HomePage(HttpServletRequest request,HttpSession httpSession)
    {
        String account=request.getParameter("account");

        JcxxYhEN userBaseEN=userService.getUserByName(account);
        if(userBaseEN!=null)
        {
            httpSession.setAttribute("zh",account);
            httpSession.setAttribute("id",userBaseEN.getId());
            httpSession.setAttribute("name",userBaseEN.getXm());
            httpSession.setAttribute("dep",userBaseEN.getBmmc());
            httpSession.setAttribute("bm",userBaseEN.getBm());
            httpSession.setAttribute("dz",userBaseEN.getPersonal());
            int id=userBaseEN.getUserRoleEN().getId();
            httpSession.setAttribute("js",String.valueOf(id));
        }


        return "WEB-INF/shouye/shouye";
    }
    //退出
    @RequestMapping(value = "LogOut",method = RequestMethod.GET)
    public void LogOut(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws IOException
    {
        session.removeAttribute("zh");
        session.removeAttribute("id");
        session.removeAttribute("name");
        session.removeAttribute("dep");
        session.removeAttribute("js");
        response.sendRedirect("Login/login.jsp");
        //return "Login/login";
    }
    //分页获取角色列表
    @RequestMapping(value = "UserRoles",method = RequestMethod.POST)
    @ResponseBody
    public DataList UserRoles(HttpServletRequest request)
    {
        String page=request.getParameter("page");
        String name=request.getParameter("name");
        return userService.UserRoles(Integer.valueOf(page),name);
    }
    //按ID获取角色
    @RequestMapping(value = "UserRoleByID",method = RequestMethod.POST)
    @ResponseBody
    public UserRoleEN UserRoleByID(HttpServletRequest request)
    {
        String id=request.getParameter("id");
        return userService.UserRoleByID(Integer.valueOf(id));
    }
    //按ID修改角色
    @RequestMapping(value = "updateUserRoleByID",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateUserRoleByID(@RequestParam(value = "Rids[]") List<String> ids, HttpServletRequest request)
    {
        String id=request.getParameter("id");
        String name=request.getParameter("rolename");

        UserRoleEN userRoleEN=userService.UserRoleByID(Integer.valueOf(id));
        userRoleEN.setRole(name);
        Set<UserPermissionEN> set=new HashSet<>();
        for(String rid:ids)
        {
            UserPermissionEN userPermissionEN=userService.PermissionByID(Integer.valueOf(rid));
            if(userPermissionEN!=null)
                set.add(userPermissionEN);
        }
        userRoleEN.setPermissionENs(set);

        userService.updateEN(userRoleEN);

        return BaseResult.newResultOK();
    }

   //获取全部权限列表
    @RequestMapping(value = "PermissionList",method = RequestMethod.POST)
    @ResponseBody
    public List<?> PermissionList(HttpServletRequest request)
    {
        return userService.PermissionList();
    }
    //新建角色
    @RequestMapping(value = "NewRole",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult NewRole(@RequestParam(value = "Rids[]") List<String> ids,HttpServletRequest request)
    {
        String rolename=request.getParameter("rolename");

        UserRoleEN userRoleEN=new UserRoleEN();
        userRoleEN.setRole(rolename);
        Set<UserPermissionEN> set=new HashSet<>();
        for(String rid:ids)
        {
            UserPermissionEN userPermissionEN=userService.PermissionByID(Integer.valueOf(rid));
            if(userPermissionEN!=null)
                set.add(userPermissionEN);
        }
        userRoleEN.setPermissionENs(set);
        userService.saveEN(userRoleEN);
        return BaseResult.newResultOK();
    }
    //获取全部权限组别
    @RequestMapping(value = "PermissionGroup",method = RequestMethod.POST)
    @ResponseBody
    public List<?> PermissionGroup(HttpServletRequest request)
    {
        return userService.PermissionGroup();
    }

    //保存定制
    @RequestMapping(value = "CommitSet",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult CommitSet(HttpServletRequest request,HttpSession session)
    {
        String set=request.getParameter("setting");
        String userid=request.getParameter("id");

        JcxxYhEN yhEN=userService.getUserByID(userid);
        yhEN.setPersonal(set);
        userService.updateEN(yhEN);

        session.setAttribute("dz",set);

        return BaseResult.newResultOK();
    }

}
