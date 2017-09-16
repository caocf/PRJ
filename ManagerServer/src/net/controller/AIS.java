package net.controller;

import common.BaseResult;
import common.DataList;
import common.FileDownload;
import common.FileUpLoad;
import net.modol.AisEditEN;
import net.modol.AisStatusEN;
import net.service.AISService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2016/10/18.
 */
@Controller
public class AIS
{
    @Resource
    AISService aisService;

    //按提示获取船名列表
    @ResponseBody
    @RequestMapping(value = "getAIS", method = RequestMethod.GET)
    public List<?> getAIS(HttpServletRequest request)
    {

        return aisService.LocalAIS();
    }
    //按船名查9位码
    @ResponseBody
    @RequestMapping(value = "AISByShipName", method = RequestMethod.POST)
    public Object AISByShipName(HttpServletRequest request) throws Exception
    {
        String shipname=request.getParameter("shipname");
        List<?> list=aisService.AISByShipName(shipname);
        return list.get(0);
    }
    //获取9位码列表分页
    @ResponseBody
    @RequestMapping(value = "AISList", method = RequestMethod.POST)
    public DataList AISList(HttpServletRequest request) throws Exception
    {
        String shipname=request.getParameter("tip");
        String page=request.getParameter("page");

        return aisService.AISList(Integer.valueOf(page),shipname);
    }
    //提交待核查9位码
    @ResponseBody
    @RequestMapping(value = "AISCommit", method = RequestMethod.POST)
    public BaseResult AISCommit(HttpServletRequest request) throws Exception
    {
        String shipname=request.getParameter("shipname");
        String code=request.getParameter("code");
        AisEditEN aisEditEN =aisService.AISByShip(shipname);
        if(aisEditEN==null)
        {
            aisEditEN=new AisEditEN();
            aisEditEN.setShipnameid(shipname);
        }
        if(aisEditEN.getStatus().getId()==2)
        {
            return   BaseResult.newResultFailed();
        }
        List<String> dirs = FileUpLoad.uploadFiles(request, "AISFile"+"/"+shipname+"/", "aisfile");
        aisEditEN.setDir(dirs.get(0));
        aisEditEN.setBh(code);
        AisStatusEN aisStatusEN=aisService.AISStatusByID(1);
        aisEditEN.setStatus(aisStatusEN);

        aisService.updateORSave(aisEditEN);
        return BaseResult.newResultOK();
    }
    //按船名查9位码编辑
    @ResponseBody
    @RequestMapping(value = "AISByShipNameEdit", method = RequestMethod.POST)
    public AisEditEN AISByShipNameEdit(HttpServletRequest request) throws Exception
    {
        String shipname=request.getParameter("shipname");
        AisEditEN aisEditEN=aisService.AISByShip(shipname);
        return aisEditEN;
    }
    //按船名下载AIS证书
    @ResponseBody
    @RequestMapping(value = "AISCeretByShipName", method = RequestMethod.POST)
    public void AISCeretByShipName(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        String shipname=request.getParameter("shipname");
        AisEditEN aisEditEN=aisService.AISByShip(shipname);
        FileDownload.download(request.getServletContext().getRealPath(aisEditEN.getDir()),request,response);
    }
    //提交审核
    @ResponseBody
    @RequestMapping(value = "AISCheck", method = RequestMethod.POST)
    public BaseResult AISCheck(HttpServletRequest request) throws Exception
    {
        String shipname=request.getParameter("shipname");
        String status=request.getParameter("status");
        String opin=request.getParameter("opinion");
        AisEditEN aisEditEN=aisService.AISByShip(shipname);
        AisStatusEN aisStatusEN=aisService.AISStatusByID(Integer.valueOf(status));
        aisEditEN.setStatus(aisStatusEN);
        aisEditEN.setCommittime(new Date());
        aisEditEN.setOpinion(opin);
        aisService.updateEN(aisEditEN);
        return BaseResult.newResultOK();
    }
}
