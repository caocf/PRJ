package com.zjport.electronicmap.controller;

import com.common.base.BaseRecords;
import com.common.base.BaseResult;
import com.zjport.electronicmap.service.ElectronicMapService;
import com.zjport.model.ZAcCameraInfo;
import com.zjport.model.ZAcRegionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Will on 2016/11/4 13:43.
 */
@Controller
@RequestMapping("/eleMap")
public class ElectronicMapController {
    private static final Logger log = LoggerFactory.getLogger(ElectronicMapController.class);

    @Resource(name = "electronicMapService")
    private ElectronicMapService service;

    @ResponseBody
    @RequestMapping(value = "/queryRegionList",method = RequestMethod.POST)
    public BaseResult queryRegionList(){
        BaseResult result = null;

        try{
            BaseRecords<ZAcRegionInfo> br = service.queryRegionList(284);
            result = BaseResult.newResultOK(br);
        }catch (Exception e){

            result = BaseResult.newResultFailed();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "queryCameras",method = RequestMethod.POST)
    public BaseResult queryCameras(Integer regionId){
        BaseResult result = null;

        try{
            BaseRecords<ZAcCameraInfo> br = service.queryCameras(regionId);
            result = BaseResult.newResultOK(br);
        }catch (Exception e){

            result = BaseResult.newResultFailed();
        }
        return result;

    }
}
