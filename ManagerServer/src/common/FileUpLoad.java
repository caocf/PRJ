package common;

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
    public static List<String> uploadFiles(HttpServletRequest request, String fatherdir, String feiledkey) throws IOException
    {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files=multipartRequest.getFiles(feiledkey);
        List<String> relapaths=new ArrayList<>();
        int count=files.size();
        System.out.println(count);
        for (int i=0;i<files.size();i++)
        {
            MultipartFile multipartFile=files.get(i);
            String filename=multipartFile.getOriginalFilename();
            System.out.println(filename);
            if(filename==null||filename.equals(""))
                continue;
            System.out.println(filename);
            relapaths.add(fatherdir+filename);
            String abpath=request.getServletContext().getRealPath(fatherdir+filename);
            System.out.println(abpath);
            File file=new File(abpath);
            System.out.println(abpath);
            if (!file.exists())
            {
                file.getParentFile().mkdirs();
            }
            multipartFile.transferTo(file);
        }

        return relapaths;
    }
}
