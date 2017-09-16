package net.controller;

import common.BaseRecords;
import common.BaseResult;
import common.FileDownload;
import common.FileUpLoad;
import net.modol.*;
import net.service.CruiseService;
import net.service.UserService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2016/11/11.
 */
@Controller
public class Cruise {
    @Resource
    CruiseService cruiseService;
    @Resource
    UserService userService;

    String filepath = "/Cruise/";
    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

    //添加巡航日志
    @RequestMapping(value = "addcruise", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addcruise(HttpServletRequest request) {
        String s1 = request.getParameter("area");
        String s2 = request.getParameter("tools");
        String s3 = request.getParameter("member");
        String s4 = request.getParameter("userid");
        String s5 = request.getParameter("status");

        JcxxYhEN user = userService.getUserByID(s4);
        CruiseRecordEN cruiseRecordEN = new CruiseRecordEN();
        cruiseRecordEN.setUserBaseEN(user);
        cruiseRecordEN.setArea(s1);
        cruiseRecordEN.setTools(s2);
        cruiseRecordEN.setMember(s3);
        cruiseRecordEN.setRecords(s5);//状态 0:生成 1 正式建立
        cruiseRecordEN.setStarttime(new Date());

        int id = cruiseService.saveEN(cruiseRecordEN);

        return new BaseResult(id, "id");
    }

    //按巡航日志ID添加事件记录
    @RequestMapping(value = "AddRecord", method = RequestMethod.POST)
    @ResponseBody
    public String AddRecord(HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        String s1 = request.getParameter("title");
        String s2 = request.getParameter("content");
        String s3 = request.getParameter("location");

        CruiseRecordEN cruiseRecordEN = cruiseService.getCruiseByID(Integer.valueOf(id));
        CruiseLogEN cruiseLogEN = new CruiseLogEN();
        cruiseLogEN.setTitle(s1);
        cruiseLogEN.setContent(s2);
        cruiseLogEN.setLocation(s3);
        cruiseLogEN.setRecordtime(new Date());
        cruiseLogEN.setCruiseRecordEN(cruiseRecordEN);
        cruiseService.saveEN(cruiseLogEN);

        List<String> list = FileUpLoad.uploadFiles(request, filepath + id + "/", "record");
        for (String s : list) {
            CruiseFileEN fileEN = new CruiseFileEN();
            fileEN.setDir(s);
            fileEN.setCruiseLogEN(cruiseLogEN);
            cruiseService.saveEN(fileEN);
        }

        return "ok";
    }

    //更新巡航日志
    @RequestMapping(value = "updatecruise", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updatecruise(HttpServletRequest request) throws IOException {
        String s1 = request.getParameter("period");
        String s2 = request.getParameter("miles");
        String s3 = request.getParameter("id");
        String s4 = request.getParameter("status");

        List<String> list = FileUpLoad.uploadFiles(request,"/"+ filepath + s3 + "/", "map");

        CruiseRecordEN cruiseRecordEN = cruiseService.getCruiseByID(Integer.valueOf(s3));
        cruiseRecordEN.setPeriod(s1);
        cruiseRecordEN.setMiles(Double.valueOf(s2));
        cruiseRecordEN.setDir(list.get(0));
        cruiseRecordEN.setRecords(s4);
        cruiseService.updateEN(cruiseRecordEN);

        return new BaseResult(1, "");
    }

    //按包含人员获取巡航日志列表
    @RequestMapping(value = "CruisesByMember", method = RequestMethod.POST)
    @ResponseBody
    public BaseRecords CruisesByMember(HttpServletRequest request) throws IOException {
        String s3 = request.getParameter("name");

        return new BaseRecords(cruiseService.CruisesByMember(s3));
    }

    //按巡航日志ID获取事件记录列表
    @RequestMapping(value = "RecordsByCruiseID", method = RequestMethod.POST)
    @ResponseBody
    public List<?> RecordsByCruiseID(HttpServletRequest request) throws IOException {
        String id = request.getParameter("id");

        return cruiseService.RecordsByCruiseID(Integer.valueOf(id));
    }

    //巡航统计（WEB）
    @RequestMapping(value = "xctj", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult xctj(HttpServletRequest request) {
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        BaseResult result = cruiseService.xctj(starttime, endtime);
        return result;
    }

    //巡航统计次数、里程、事件数（APP）
    @RequestMapping(value = "CruiseCount", method = RequestMethod.POST)
    @ResponseBody
    public Object CruiseCount(HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        List<CruiseRecordEN> list = cruiseService.CruisesByMemberAndTime(name, sd.parse(starttime), sd.parse(endtime));

        int count = 0, records = 0;
        Double miles = 0.0;
        for (CruiseRecordEN cruiseRecordEN : list) {
            miles += cruiseRecordEN.getMiles();
            count++;
            records = cruiseService.CountRecordsByCruiseID(cruiseRecordEN.getId());

        }

        CruiseData cruiseData = new CruiseData();
        cruiseData.setEvidnum(records);
        cruiseData.setTimes(count);
        cruiseData.setToatlmiles(miles);
        return cruiseData;
    }

    @RequestMapping(value = "xhrzdt", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult xhrzDt(HttpServletRequest request) throws IOException {
        String member = request.getParameter("member");
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        String page = request.getParameter("page");
        BaseResult result = cruiseService.xhrzDt(member, starttime, endtime, Integer.parseInt(page));
        return result;
    }

    @RequestMapping(value = "xhrz_detail", method = RequestMethod.GET)
    public String xhrz_detail(HttpServletRequest request, Model model) throws IOException {
        String id = request.getParameter("id");
        model.addAttribute("id", id);
        return "WEB-INF/ship/xhrz_detail";
    }

    @RequestMapping(value = "showxhrz", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult showXhrz(HttpServletRequest request) throws IOException {
        String id = request.getParameter("id");
        BaseResult result = cruiseService.showXhrz(Integer.valueOf(id));
        return result;
    }

    @RequestMapping(value = "showxhfj", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult showXhfj(HttpServletRequest request) throws IOException {
        String id = request.getParameter("id");
        BaseResult result = cruiseService.showXhfj(Integer.valueOf(id));
        return result;
    }

    /**
     * 文件下载
     *
     * @return
     */
    @RequestMapping("/shipdownload")
    public void shipDownload(HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        CruiseFileEN en = cruiseService.showFileById(Integer.valueOf(id));
        String path = request.getRealPath("/");
        String filepath = path + en.getDir();
        filepath.replace("/", "\\");
        filepath.replace("\\", "\\\\");
        FileDownload.download(filepath, request, response);
    }
}
