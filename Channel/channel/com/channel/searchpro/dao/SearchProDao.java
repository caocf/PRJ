package com.channel.searchpro.dao;

import com.common.dao.BaseQueryRecords;

/**
 * Created by Administrator on 2016/3/7.
 */
public interface SearchProDao {
    public BaseQueryRecords searchPro(String tname, String cdt,int page,int rows);
}
