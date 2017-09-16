package com.zjport.common.controller;

import com.common.base.BaseResult;
import com.common.utils.Md5Secure;
import com.hikvision.ivms6.ws.client.ClientUtil;
import com.zjport.common.service.CommonService;
import com.zjport.file.service.FileService;
import com.zjport.login.service.LoginService;
import com.zjport.model.TApplication;
import com.zjport.model.TAttachment;
import com.zjport.model.TUser;
import org.apache.axis2.AxisFault;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Iterator;

/**
 * Created by TWQ on 2016/9/5.
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    @Resource(name = "commonService")
    CommonService commonService;
    @Resource(name = "fileService")
    FileService fileService;
    @Resource(name = "loginService")
    LoginService loginService;

    BaseResult result;

    /**
     * 显示所有单位
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showOrg")
    @ResponseBody
    public BaseResult showOrg(HttpServletRequest request, Model model) throws IOException {
        result = BaseResult.newResultOK(this.commonService.selectAllOrg());
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 显示所有部门
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showDept")
    @ResponseBody
    public BaseResult showDept(HttpServletRequest request, Model model) throws IOException {
        result = BaseResult.newResultOK(this.commonService.selectAllDept());
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 显示所有人员
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/showUser")
    @ResponseBody
    public BaseResult showUser(HttpServletRequest request, Model model) throws IOException {
        result = BaseResult.newResultOK(this.commonService.selectAllUser());
        return result; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 附件下载
     * @return
     */
    @RequestMapping(value = "/downloadFile")
    public void/*ResponseEntity<byte[]>*/ downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*String path=new String(request.getParameter("path").getBytes("ISO-8859-1"),"UTF-8");

        File file=new File(path);
        HttpHeaders headers = new HttpHeaders();
        String fileName=new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);*/

        String attachmentId = request.getParameter("id");
        TAttachment attach = this.fileService.getAttachmentById(Integer.valueOf(attachmentId));

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        bis = new BufferedInputStream(new FileInputStream(attach.getStFileSource()));
        bos = new BufferedOutputStream(response.getOutputStream());

        long fileLength = new File(attach.getStFileSource()).length();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        /*
        * 解决各浏览器的中文乱码问题
        */
        String fileName=attach.getStFileName();//为了解决中文名称乱码问题
        String userAgent = request.getHeader("User-Agent");
        byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes()
                : fileName.getBytes("UTF-8"); // fileName.getBytes("UTF-8")处理safari的乱码问题
        fileName = new String(bytes, "ISO-8859-1");

        /*String fileName=request.getParameter("name");
        byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8"); // fileName.getBytes("UTF-8")处理safari的乱码问题
        fileName = new String(bytes, "UTF8"); // 各浏览器基本都支持ISO编码
        System.out.println(fileName);*/

        /*String fileName=request.getParameter("name");
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        String rtn = "";
        try
        {
            String new_filename = URLEncoder.encode(fileName, "UTF8");

            // 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
            rtn = "filename=\"" + new_filename + "\"";

            if (userAgent != null)
            {
                userAgent = userAgent.toLowerCase();

                // IE浏览器，只能采用URLEncoder编码
                if (userAgent.indexOf("msie") != -1)
                {
                    rtn = "filename=\"" + new_filename + "\"";
                }
                // Opera浏览器只能采用filename*
                else if (userAgent.indexOf("opera") != -1)
                {
                    rtn = "filename*=UTF-8''" + new_filename;
                }

                // Safari浏览器，只能采用ISO编码的中文输出
                else if (userAgent.indexOf("safari") != -1)
                {
                    rtn = "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"";
                }

                // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
                else if (userAgent.indexOf("applewebkit") != -1)
                {
                    rtn = "filename=\"" + new_filename + "\"";
                }

                // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
                else if (userAgent.indexOf("mozilla") != -1)
                {
                    rtn = "filename*=UTF-8''" + new_filename;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

        response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));

        response.setHeader("Content-Length", String.valueOf(fileLength));
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
    }

    /**
     * 跳转
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/turnTo")
    public void turnTo(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        HttpSession session = request.getSession();
        TUser daUser = (TUser)session.getAttribute("session_user");
        String appId = request.getParameter("appId");
        TApplication app = this.commonService.getAppById(appId);
        
        if(app.getStApplicationId() == 6) {
            String st = this.getSt();
            response.sendRedirect(app.getStAppUrl()+"?ticket="+st);
        } else if(app.getStApplicationId() == 2) {
            response.sendRedirect(app.getStAppUrl()+"?mobile="+daUser.getStAccount()+"&secret="+this.loginService.getSecret()+"&token="+this.getToken(daUser.getStAccount(),this.loginService.getSecret(),"8631fcba62514d859f857eb0b5def127"));
        } else {
            response.sendRedirect(app.getStAppUrl()+"?account="+daUser.getStAccount()+"&code=E10ADC3949BA59ABBE56E057F20F883E");
        }
    }

    private String getToken(String A, String B, String C) {
        return Md5Secure.encode(A+B+C).toLowerCase();
    }

    public String getSt() throws IOException {
        String url = "https://10.100.70.202/cms/services/IAuthService";
        String methodName = "applySt";
        String nameSpace = "http://ws.cms.ivms6.hikvision.com";
        Object[] params = new Object[]{"zjsgh","23f7c4e681abe5649dde2f9da6c17eafb139fcdb5024ad73f2a45743a666a67e","","",""};
        String st = "";
        try {
            String res = ClientUtil.callWs(url,methodName,nameSpace,params);

            Document dom = DocumentHelper.parseText(res);
            Element et = dom.getRootElement();

            for(Iterator iter = et.elementIterator(); iter.hasNext();) {
                Element element = (Element)iter.next();

                for(Iterator iterInner = element.elementIterator();iterInner.hasNext();) {
                    Element elementOption = (Element)iterInner.next();
                    if("row".equals(elementOption.getName())) {
                        st = elementOption.attributeValue("st");
                    }
                }
            }
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return st;
    }
}
