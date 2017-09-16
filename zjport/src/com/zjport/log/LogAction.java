package com.zjport.log;

import com.common.base.BaseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@RequestMapping("/log")
@Controller
public class LogAction
{

    @Resource(name = "logService")
    LogService logService;

    BaseResult result;

    /**
     * 删除日志
     * @param ids   日志ID 多个ID用“,”区分
     */
    @RequestMapping(value = "/deleteLogs")
    @ResponseBody
    public BaseResult deleteLogs(HttpServletRequest request, String ids)
    {
        result = this.logService.deleteLogs(ids);

        Object userId = request.getSession().getAttribute("session_userid");
        if (userId != null)
        {
            // 记录日志
            this.logService.saveLog((int) userId, "删除日志");
        }

        return result;
    }

    /**
     * 查询日志
     *
     * @param optUser   用户名相关查询
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param page       页数
     * @return
     */
    @RequestMapping(value = "/queryLogs")
    @ResponseBody
    public BaseResult queryLogs(String optUser, String startTime, String endTime, Integer page)
    {
        if(page == null)
        {
            page = 1;
        }

        return logService.queryLogs(optUser, startTime, endTime, page);
    }

    /**
     * 导出日志
     *
     * @param optUser
     * @param startTime
     * @param endTime
     */
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void uploadExcel(HttpServletRequest request, HttpServletResponse response, String optUser, String startTime, String endTime)
    {
        //解决中文乱码问题
        String userAgent = request.getHeader("User-Agent");
        String filename = "日志记录.xls";
        String encodeName = filenameEncode(filename, request) ;
        if(encodeName==null)
        {
            try
            {
                encodeName = "file=" + (URLEncoder.encode(filename, "UTF-8")).getBytes();
            }
            catch (Exception e)
            {
                encodeName = "file=log.xls";
            }
        }

        try
        {
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            InputStream fis = (InputStream) logService.excelCreat(optUser, startTime, endTime).getObj();
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();

            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes()));

            response.setContentType("application/x-download");
            response.setHeader("Content-disposition", "attachment;" + encodeName);

            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        }
        catch ( IOException  e)
        {
            e.printStackTrace();
        }
        finally
        {
            Object userId = request.getSession().getAttribute("session_userid");
            if (userId != null)
            {
                // 记录日志
                this.logService.saveLog((int) userId, "导出日志");
            }
        }
    }

    /**
     * 中文文件名编码，解决常用浏览器下载时无法解决获得下载文件名中文问题
     * @param filename
     * @return
     */
    private String filenameEncode(String filename,HttpServletRequest request)
    {
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        String rtn = "";
        try
        {
            String new_filename = URLEncoder.encode(filename, "UTF8");

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
                    rtn = "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
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
        }
        return rtn;
    }
}
