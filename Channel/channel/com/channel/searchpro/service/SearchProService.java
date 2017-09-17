package com.channel.searchpro.service;

import com.common.action.result.BaseResult;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7.
 */
public interface SearchProService {
    public BaseResult loadField(int fswlx) throws ClassNotFoundException;

    public BaseResult searchPro(int fswlx, List<String> contents,int page,int rows,String secho);
}
