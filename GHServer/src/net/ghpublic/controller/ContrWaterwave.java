package net.ghpublic.controller;

import framework.modol.BaseRecords;
import framework.modol.BaseResult;
import net.ghpublic.modol.WaterInfoEN;
import net.ghpublic.service.ServiceWater;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2016/5/9.
 */
@Controller
public class ContrWaterwave
{
    @Resource
    ServiceWater waterService;

    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");

    //一、水纹列表
    @ResponseBody
    @RequestMapping(value = "waterinfolist",method = RequestMethod.POST)
    public BaseRecords<WaterInfoEN> WaterinfoList(HttpServletRequest request)
    {
        return new BaseRecords<WaterInfoEN>(waterService.WaterinfoByRegion(Integer.valueOf(request.getParameter("CityID"))));
    }
    //二、按观察点获取水纹信息
    @ResponseBody
    @RequestMapping(value = "waterinfo",method = RequestMethod.POST)
    public BaseResult WaterInfo(HttpServletRequest request)
    {
        String whatchpoint=request.getParameter("Whatchpoint");
        return BaseResult.newResultOK(waterService.WaterInfoByPoint(whatchpoint));
    }

    //三、按港口和时间获取潮汐表
    @ResponseBody
    @RequestMapping(value = "GetTide",method = RequestMethod.POST)
    public List<?> GetTide(HttpServletRequest request) throws Exception
    {
        String s1=request.getParameter("port");
        String s2=request.getParameter("day");
        Date d1=sd.parse(s2);
        long t=d1.getTime()+1000*60*60*24;
        Date d2=new Date(t);

        return waterService.GetTide(s1,d1,d2);
    }
}
