package net.ghpublic.controller;

import framework.tool.FileUpLoad;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Admin on 2016/6/21.
 */
@Controller
public class FileTest
{
    static String[] folders={"FolderFiles/shipfile/","FolderFiles/wharfile/","FolderFiles/coastalfile/"};
    @RequestMapping(value = "filetest")
    @ResponseBody
    public String FileTest(HttpServletRequest request) throws Exception
    {
        //System.out.println("dddd");
        List<String> dirs= FileUpLoad.uploadFiles(request,folders[0],"img");
        return "aaaa";
    }
}
