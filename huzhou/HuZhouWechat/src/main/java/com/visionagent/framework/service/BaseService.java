package com.visionagent.framework.service;

/**
 * Created by Will on 2016/8/18 11:19.
 */
//@Transactional
public interface BaseService {


    default boolean checkToken(String token){
        return true;
    }
}
