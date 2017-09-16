package com.wechat.controller;

import com.visionagent.framework.controller.BaseController;
import com.visionagent.framework.controller.Result;
import com.wechat.service.WeChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zoumy on 2017/3/23 11:17.
 */
@RequestMapping("/wechat")
@Controller
public class WeChatController extends BaseController{

    private static final Logger log = LoggerFactory.getLogger(WeChatController.class);

    @Resource(name="weChatService")
    private WeChatService weChatService;

    /**
     * 查询航行通告
     *
     * @param page the page
     * @param rows the rows
     * @return the result
     * @Auth Will
     * @Date 2017 -03-24 12:59:34
     */
    @ResponseBody
    @RequestMapping(value = "/queryPortAnnouncement",method = RequestMethod.GET)
    public Result queryPortAnnouncement(int page,int rows){
        List<Map<String,String>> portAnnouncements = weChatService.queryPortAnnouncement(page,rows);
        Result result = resultOK(portAnnouncements);
        return result;
    }


    /**
     * 查询港航动态
     *
     * @param page the page
     * @param rows the rows
     * @return the result
     * @Auth Will
     * @Date 2017 -03-24 12:59:34
     */
    @ResponseBody
    @RequestMapping(value = "/queryDynamics",method = RequestMethod.GET)
    public Result queryDynamics(int page,int rows){
        List<Map<String,String>> portAnnouncements = weChatService.queryDynamics(page,rows);
        Result result = resultOK(portAnnouncements);
        return result;
    }

    /**
     * 查询详情
     *
     * @param url the url
     * @return the result
     * @Auth Will
     * @Date 2017 -03-24 12:59:35
     */
    @ResponseBody
    @RequestMapping(value = "/queryDetail",method = RequestMethod.GET)
    public Result queryDetail(String url){
        String portAnnouncements = weChatService.queryDetail(url);
        Result result = resultOK(portAnnouncements);
        return result;
    }

    /**
     * 查询法律法规
     *
     * @param page the page
     * @param rows the rows
     * @return the result
     * @Auth Will
     * @Date 2017 -03-24 12:59:35
     */
    @ResponseBody
    @RequestMapping(value = "/queryLaws",method = RequestMethod.GET)
    public Result queryLaws(int page,int rows){
        List<Map<String,String>> portAnnouncements = weChatService.queryLaws(page,rows);
        Result result = resultOK(portAnnouncements);
        return result;
    }

    /**
     * 查询行政许可
     *
     * @param page the page
     * @param rows the rows
     * @return the result
     * @Auth Will
     * @Date 2017 -03-24 12:59:35
     */
    @ResponseBody
    @RequestMapping(value = "/queryLicensing",method = RequestMethod.GET)
    public Result queryLicensing(int page,int rows){
        List<Map<String,String>> portAnnouncements = weChatService.queryLicensing(page,rows);
        Result result = resultOK(portAnnouncements);
        return result;
    }

    /**
     * 查询行政处罚
     *
     * @param page the page
     * @param rows the rows
     * @return the result
     * @Auth Will
     * @Date 2017 -03-24 12:59:35
     */
    @ResponseBody
    @RequestMapping(value = "/queryPunish",method = RequestMethod.GET)
    public Result queryPunish(int page,int rows){
        List<Map<String,String>> portAnnouncements = weChatService.queryPunish(page,rows);
        Result result = resultOK(portAnnouncements);
        return result;
    }

     /**
      * 违章查询
     *
     * @param shipName the shipName
     * @param code the code
     * @return the result
     * @Auth Will
     * @Date 2017 -03-24 12:59:35
     */
    @ResponseBody
    @RequestMapping(value = "/queryViolation",method = RequestMethod.GET)
    public Result queryViolation(String shipName,String code){
        Object violation = weChatService.queryViolation(shipName,code);
        Result result = resultOK(violation);
        return result;
    }
}
