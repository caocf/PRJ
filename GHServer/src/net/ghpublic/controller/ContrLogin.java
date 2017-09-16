package net.ghpublic.controller;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import framework.modol.BaseResult;
import framework.modol.DataList;
import framework.tool.EncodeTool;
import framework.tool.FileDownload;
import framework.tool.FileUpLoad;
import net.ghpublic.modol.*;
import net.ghpublic.service.ServiceLogin;
import net.ghpublic.service.ServiceShip;
import org.json.JSONObject;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by wangzedong on 2016/4/6.
 */
@Controller
public class ContrLogin
{
    //不同类型用户证件的存储路径
    static String[] folders={"FolderFiles/shipfile/","FolderFiles/wharfile/","FolderFiles/coastalfile/"};
    @Resource(name = "serviceLogin")
    ServiceLogin loginService;

    //用户登录
    @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public BaseResult Login(HttpServletRequest request, HttpServletResponse response)
    {
        String user=request.getParameter("username");
        String psw=request.getParameter("password");

        PublicUserEN publicUserEN=loginService.getPublicUserByName(user);
        if(publicUserEN==null)
        {
            return new BaseResult(-1,"用户不存在");
        }
        if(!publicUserEN.getPassword().equals(psw))
        {
            return new BaseResult(-2,"密码错误");
        }
        //String UT=publicUserEN.getUsertype().getTypename();
        return new BaseResult(1,"",publicUserEN);//登录成功

    }
    //获取用户类型列表
    @ResponseBody
    @RequestMapping(value = "UserTypes",method = RequestMethod.POST)
    public List<?> UserTypes(HttpServletRequest request)
    {
        return loginService.UserTypes();
    }
    //获取地区列表
    @ResponseBody
    @RequestMapping(value = "Regions",method = RequestMethod.POST)
    public List<?> Regions(HttpServletRequest request)
    {
        return loginService.Regions();
    }
    //发送验证码
    @ResponseBody
    @RequestMapping(value = "getmessage",method = RequestMethod.POST)
    public BaseResult getMessage(HttpServletRequest request) throws Exception
    {
        //获取手机号码
        String tel=request.getParameter("tel");
        //生成验证码
        String code=loginService.generateCode();
        //发送短信
        loginService.sendMSGAL(tel,code);
        //先查找数据库，如果没有这个手机号
        PublicUserValidationEN datavalidation= loginService.getValidationByTel(tel);
        if(datavalidation==null)
        {
            //生成配对实例
            datavalidation=new PublicUserValidationEN();
            datavalidation.setTel(tel);
            datavalidation.setCode(code);
            loginService.saveEN(datavalidation);
        }
        else//如果已有，更新掉
        {
            datavalidation.setCode(code);
            loginService.updateEN(datavalidation);
        }

        return new BaseResult(1,"1" );
    }
    //验证信息
    @ResponseBody
    @RequestMapping(value = "validate",method = RequestMethod.POST)
    public BaseResult Validate(HttpServletRequest request)
    {
        PublicUserEN userEN=loginService.getPublicUserByName(request.getParameter("username"));
        if(userEN!=null)
        {
            return new BaseResult(-3,"用户名已存在");
        }

        String tel=request.getParameter("tel");//获取手机号码
        PublicUserValidationEN validation= loginService.getValidationByTel(tel);
        if(validation==null)
        {
            return new BaseResult(-2,"手机号码有误");
        }

        if(!validation.getCode().equals(request.getParameter("code")))
        {
            return new BaseResult(-1,"验证码输入错误");
        }

        return new BaseResult(1,"有效信息");
    }
    //用户注册提交
    @ResponseBody
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public BaseResult ShipRegister(HttpServletRequest request) throws Exception
    {
        //保存基本用户
        PublicUserEN user=loginService.newPublicUser(request);
        loginService.saveEN(user);

        int result=-2;
        if (user.getUsertype().getId()==1) {
            ShipEN ship = new ShipEN();
            ship.setPublicUserEN(user);
            ship.setShipname(request.getParameter("shipname"));
            ship.setRegnumber(request.getParameter("shipnum"));
            PublicuserStatusEN statusEN=loginService.PublicStatusByID(1);//待审核
            ship.setShipstatus(statusEN);
            ship.setBinddate(new Date());
            result = loginService.saveEN(ship);
            //文件上传
            List<String> dirs = FileUpLoad.uploadFiles(request, folders[user.getUsertype().getId() - 1], "GDPC");
            List<String> dirs1 = FileUpLoad.uploadFiles(request, folders[user.getUsertype().getId() - 1], "CON");

            ShipcertEN shipcertEN = new ShipcertEN();
            shipcertEN.setShipEN(ship);
            shipcertEN.setGppc(dirs.get(0));
            shipcertEN.setCon(dirs1.get(0));

            loginService.saveEN(shipcertEN);

        }
        else
            {
                CompanyBaseEN companyBaseEN=new CompanyBaseEN();
                companyBaseEN.setName(request.getParameter("company"));
                companyBaseEN.setRegnumber(request.getParameter("BLnumber"));
                companyBaseEN.setUserEN(user);
                PublicuserStatusEN statusEN=loginService.PublicStatusByID(1);//待审核
                companyBaseEN.setStatusEN(statusEN);//待审核状态
                companyBaseEN.setBinddate(new Date());
                result=loginService.saveEN(companyBaseEN);

                List<String> dirs = FileUpLoad.uploadFiles(request, folders[2], "LS");
                for(int i=0;i<dirs.size();i++)
                {
                    CompanyCertEN companyCertEN=new CompanyCertEN();
                    companyCertEN.setDir(dirs.get(i));
                    companyCertEN.setCompanyBaseEN(companyBaseEN);
                    loginService.saveEN(companyCertEN);
                }
            }

        return new BaseResult(result,"");
    }
    //按用户名添加船舶
    @ResponseBody
    @RequestMapping(value = "addmyship",method = RequestMethod.POST)
    public BaseResult addMyShip(HttpServletRequest request) throws Exception
    {
        String s=request.getParameter("Shipname");

        PublicUserEN userEN=loginService.getPublicUserByName(request.getParameter("Username"));

        ShipEN ship=new ShipEN();
        ship.setPublicUserEN(userEN);
        ship.setShipname(s);
        ship.setRegnumber(request.getParameter("Shipnum"));
        PublicuserStatusEN statusEN=loginService.PublicStatusByID(1);//待审核
        ship.setShipstatus(statusEN);
        ship.setBinddate(new Date());

        int result=loginService.saveEN(ship);

        List<String> dirs = FileUpLoad.uploadFiles(request, folders[userEN.getUsertype().getId() - 1], "GDPC");
        List<String> dirs1 = FileUpLoad.uploadFiles(request, folders[userEN.getUsertype().getId() - 1], "CON");

        ShipcertEN shipcertEN = new ShipcertEN();
        shipcertEN.setShipEN(ship);
        shipcertEN.setGppc(dirs.get(0));
        shipcertEN.setCon(dirs1.get(0));

        loginService.saveEN(shipcertEN);

        return new BaseResult(result,"");
    }
    //按手机号修改密码
    @ResponseBody
    @RequestMapping(value = "findpsw",method = RequestMethod.POST)
    public BaseResult FindPSW(HttpServletRequest request) throws IOException
    {
        //获取手机号码
        String tel=request.getParameter("tel");
        PublicUserEN user=loginService.getPublicUserByTel(tel);
        if(user==null)
            return new BaseResult(-2,"此号码未注册");

        PublicUserValidationEN validationEN=loginService.getValidationByTel(tel);
        if(!validationEN.getCode().equals(request.getParameter("code")))
        {
            return new BaseResult(-1,"验证码错误");
        }
        String p=request.getParameter("newpsw");
        int id=user.getUserid();
        user.setPassword(p);
        loginService.updateEN(user);

        return new BaseResult(1,"密码修改成功");
    }
    //按原密码修改密码
    @ResponseBody
    @RequestMapping(value = "changepsw",method = RequestMethod.POST)
    public BaseResult changePSW(HttpServletRequest request)
    {
        String s1=request.getParameter("pswold");
        String s2=request.getParameter("pswnew");
        PublicUserEN userEN=loginService.getPublicUserByName(request.getParameter("username"));
        if(s1.equals(userEN.getPassword()))
        {
            userEN.setPassword(s2);
            loginService.updateEN(userEN);
            return new BaseResult(1,"修改成功");
        }
        else
        {
            return new BaseResult(0,"原密码错误");
        }
    }
    //换绑手机
    @ResponseBody
    @RequestMapping(value = "changetel",method = RequestMethod.POST)
    public BaseResult ChangeTel(HttpServletRequest request)
    {
        PublicUserEN userEN=loginService.getPublicUserByName(request.getParameter("username"));
        if (userEN==null)
        {
            return new BaseResult(-2,"用户名不存在");
        }
        String tel=request.getParameter("tel");
        //验证新号码有效性
        if(!request.getParameter("code").equals(loginService.getValidationByTel(tel).getCode()))
        {
            return new BaseResult(-1,"验证码错误");
        }

        userEN.setTel(tel);
        loginService.updateEN(userEN);

        return new BaseResult(1,"换帮成功");
    }
    //下载二维码图片
    @RequestMapping(value = "CodeImg",method = RequestMethod.GET)
    public void CodeImg(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        //String path = request.getServletContext().getRealPath("barcode.jpg");
        FileDownload.download(request.getServletContext().getRealPath("barcode.png"),request,response);
    }
    //分页获取绑定信息列表
    @ResponseBody
    @RequestMapping(value = "UsersToCheck",method = RequestMethod.POST)
    public DataList UsersToCheckByShip(HttpServletRequest request)
    {
        String name=request.getParameter("tip");
        String page=request.getParameter("page");
        String type=request.getParameter("type");

        return loginService.UsersToCheckByShip(name,Integer.valueOf(page),Integer.valueOf(type));

    }
    //按id获取船舶
    @RequestMapping(value ="ShipByID",method = RequestMethod.POST )
    @ResponseBody
    public ShipEN  ShipByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        return loginService.ShipByID(Integer.valueOf(id));
    }
    //按船舶id船舶证书
    @RequestMapping(value ="ShipCerttByID",method = RequestMethod.POST )
    @ResponseBody
    public ShipcertEN  ShipCerttByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        return loginService.ShipCerttByID(Integer.valueOf(id));
    }
    //按船舶id提交船舶审核结果
    @RequestMapping(value ="ShipCheckByID",method = RequestMethod.POST )
    @ResponseBody
    public BaseResult  ShipCheckByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String mark=request.getParameter("mark");
        String status=request.getParameter("status");

        ShipEN ship=loginService.ShipByID(Integer.valueOf(id));
        PublicuserStatusEN statusEN=loginService.StatusByID(Integer.valueOf(status));
        ship.setShipstatus(statusEN);
        loginService.updateEN(ship);

        return new BaseResult(1,"");
    }
    //按id获取企业
    @RequestMapping(value ="CompanyByID",method = RequestMethod.POST )
    @ResponseBody
    public CompanyBaseEN  CompanyByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        return loginService.CompanyByID(Integer.valueOf(id));
    }
    //按id获取企业证书
    @RequestMapping(value ="CompanyCerttByID",method = RequestMethod.POST )
    @ResponseBody
    public CompanyCertEN  CompanyCerttByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        return loginService.CompanyCerttByID(Integer.valueOf(id));
    }
    //按企业id提交审核结果
    @RequestMapping(value ="CompanyCheckByID",method = RequestMethod.POST )
    @ResponseBody
    public BaseResult  CompanyCheckByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        String mark=request.getParameter("mark");
        String status=request.getParameter("status");

        CompanyBaseEN companyBaseEN=loginService.CompanyByID(Integer.valueOf(id));
        PublicuserStatusEN statusEN=loginService.StatusByID(Integer.valueOf(status));
        companyBaseEN.setStatusEN(statusEN);
        loginService.updateEN(companyBaseEN);

        return new BaseResult(1,"");
    }
    //分页按类型和条件查询用户
    @RequestMapping(value ="UsersByKey",method = RequestMethod.POST )
    @ResponseBody
    public Object  UsersByKey(HttpServletRequest request) throws IOException
    {
        String key=request.getParameter("key");
        String type=request.getParameter("type");
        String page=request.getParameter("page");
        String status=request.getParameter("status");

        if("".equals(status))
        {

        }

        return loginService.UsersByKey(key,Integer.valueOf(type),Integer.valueOf(page),Integer.valueOf(status));
    }
    //按ID查询用户详情
    @RequestMapping(value ="UserInfoByID",method = RequestMethod.POST )
    @ResponseBody
    public Object  UserInfoByID(HttpServletRequest request) throws IOException
    {
        String id=request.getParameter("id");
        return loginService.getPublicUserByID(Integer.valueOf(id));
    }
    //按ID设置用户状态
    @RequestMapping(value ="setUserStatusByID",method = RequestMethod.POST )
    @ResponseBody
    public BaseResult  setUserStatusByID(HttpServletRequest request) throws IOException
    {
        String status=request.getParameter("status");
        String id=request.getParameter("id");

        PublicUserEN userEN= loginService.getPublicUserByID(Integer.valueOf(id));
        userEN.setStatus(Integer.valueOf(status));
        loginService.updateEN(userEN);

        return new BaseResult(1,"更新成功");
    }
    //文件下载
    @ResponseBody
    @RequestMapping(value = "CertDown",method = RequestMethod.POST)
    public void CertDown(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        String s1=request.getParameter("path");

        FileDownload.download(request.getServletContext().getRealPath(s1),request,response);
    }
    //统计注册待审核个数
    @RequestMapping(value ="UserCheckCount",method = RequestMethod.POST )
    @ResponseBody
    public BaseResult  UserCheckCount(HttpServletRequest request) throws IOException
    {

        int shipcount= loginService.ShipCheckCount();
        int comcount= loginService.CompanyCheckCount();

        return new BaseResult(comcount+shipcount,"统计个数");
    }



















    private String sendMSG(String tel,String code) throws IOException
    {
        //发送短信
        StringBuffer sb = new StringBuffer("http://web.duanxinwang.cc/asmx/smsservice.aspx?");
        sb.append("name=dxwFoipngoc");
        sb.append("&pwd=3944EBAB03297C753438C58A9D68");
        sb.append("&mobile="+tel);
        //sb.append("&content="+ URLEncoder.encode(content,"UTF-8"));
        sb.append("&content="+URLEncoder.encode("您的验证码是："+code,"UTF-8"));
        sb.append("&stime="+"");//为空表示即时发送
        sb.append("&sign="+URLEncoder.encode("浙江港航","UTF-8"));
        sb.append("&type=pt&extno="+"");//type为固定值pt  extno为扩展码，必须为数字 可为空
        URL url = new URL(sb.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        InputStream is =url.openStream();

        return EncodeTool.convertStreamToString(is);
    }
    @ResponseBody
    @RequestMapping(value = "filetest",method = RequestMethod.POST)
    public BaseResult  filetest(HttpServletRequest request) throws Exception
    {
        List<String> dirs = FileUpLoad.uploadFiles(request, "TestFiles/shipfile/", "GDPC");
        List<String> dirs1 = FileUpLoad.uploadFiles(request,  "TestFiles/shipfile/","CON");
        BaseResult bs=new BaseResult();
        bs.setS1(dirs);
        bs.setS2(dirs1);
        return bs;
    }
}
