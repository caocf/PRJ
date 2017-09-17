package com.channel.dic.service;

import com.common.action.result.BaseResult;

public interface DicService {

    public BaseResult queryAllTitle();

    public BaseResult queryAllJStatus();

    public BaseResult queryDicAttr(String name);

    public BaseResult queryAttrDesc(int id);

    public BaseResult queryNameDesc();

    public BaseResult addDic(String name, String namedesc,
                             String attrdesc);

    public BaseResult delDic(String name, int id);

    public BaseResult updateDic(int id, String name,
                                String namedesc, String attrdesc);

    public BaseResult queryDic(int id);

    public BaseResult queryCity();

    public BaseResult queryCityXzqh(int xzqh);
}
