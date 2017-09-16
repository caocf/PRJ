package com.zjport.file.controller;

import com.common.base.BaseResult;
import com.zjport.file.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by TWQ on 2016/10/14.
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Resource(name = "fileService")
    FileService fileService;

    BaseResult result;

    /**
     * 通讯录main
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/fileDownload")
    @ResponseBody
    public BaseResult fileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String fileName = "example.xls".toString(); // 文件的默认保存名
        //File file = new File(Constant.OPLOAD_PAHT,fileName);
        // 读到流中
        InputStream inStream = new FileInputStream("D://example.xls");// 文件的存放路径
        // 设置输出的格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;

        while ((len = inStream.read(b)) > 0)
            response.getOutputStream().write(b, 0, len);
        inStream.close();
        return result;
    }

    @RequestMapping(value = "/download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,String filePath){
        String rootPath = request.getServletContext().getRealPath("/");
        File file = new File(rootPath+filePath);
        String fileName = filePath.substring(filePath.indexOf("/")+1);
        try(InputStream is = new FileInputStream(file)) {
            OutputStream os = response.getOutputStream();
            // 设置输出的格式
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data");
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 循环取出流中的数据
            byte[] b = new byte[1024];
            int len;

            while ((len = is.read(b)) > 0){
                os.write(b, 0, len);
            }
            os.flush();
        }catch (IOException e){

        }
    }

    @RequestMapping(value = "/excalImport")
    @ResponseBody
    public BaseResult excalImport(HttpServletRequest request, @RequestParam("uploadFile") MultipartFile uploadFile, HttpServletResponse response) throws Exception{
        System.out.println("测试是否进后台！！");

        return this.fileService.importFile(uploadFile);
    }
}
