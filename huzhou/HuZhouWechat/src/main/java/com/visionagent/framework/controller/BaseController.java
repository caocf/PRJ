package com.visionagent.framework.controller;

import com.visionagent.constant.ControllerCode;
import com.visionagent.constant.ExceptionCode;
import com.visionagent.exception.VisionagentException;

/**
 * Created by Will on 2016/8/17 16:12.
 */
public class BaseController {

    /**
     * 构造正确返回
     * @param data
     * @return
     */
    protected Result resultOK(Object data){
        return new Result(ControllerCode.OK,data);
    }

    /**
     * 构造错误返回
     * @param code
     * @return
     */
    protected Result resultError(ControllerCode code){
        if(ControllerCode.OK.equals(code)){
            throw new VisionagentException(ExceptionCode.CONTROLLER_RETURN_ERROR);
        }
        return new Result(code);
    }

}
