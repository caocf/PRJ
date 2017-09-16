package framework.tool;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzedong on 2016/5/6.
 */
public class FileUpLoad
{
    public static List<String> uploadFiles(HttpServletRequest request, String fatherdir,String feiledkey) throws IOException
    {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files=multipartRequest.getFiles(feiledkey);
        List<String> relapaths=new ArrayList<>();
        int count=files.size();
        for (int i=0;i<files.size();i++)
        {
            MultipartFile multipartFile=files.get(i);
            String filename=multipartFile.getOriginalFilename();
            if(filename==null||filename.equals(""))
                continue;
            relapaths.add(fatherdir+filename);
            String abpath=request.getSession().getServletContext().getRealPath(fatherdir+filename);
            File file=new File(abpath);
            if (!file.exists())
            {
                file.mkdirs();
            }
            multipartFile.transferTo(file);
        }

        return relapaths;
    }
   /* public static List<String> uploadFiles1(HttpServletRequest request, String fatherdir,String feiledkey) throws IOException
    {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file=multipartRequest.getFile(feiledkey);
        List<String> relapaths=new ArrayList<>();
        int count=files.size();
        for (int i=0;i<files.size();i++)
        {
            MultipartFile multipartFile=files.get(i);
            String filename=multipartFile.getOriginalFilename();
            if(filename==null||filename.equals(""))
                continue;
            relapaths.add(fatherdir+filename);
            String abpath=request.getSession().getServletContext().getRealPath(fatherdir+filename);
            File file=new File(abpath);
            if (!file.exists())
            {
                file.mkdirs();
            }
            multipartFile.transferTo(file);
        }

        return relapaths;
    }*/
}
