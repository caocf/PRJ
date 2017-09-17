package com.channel.user.service;

import com.channel.model.user.CXtUser;
import com.common.action.result.BaseResult;

public interface UserInfoService {
    public BaseResult queryUserInfo(int id);

    public BaseResult updateUserInfo(int id, String name, String tel,
                                     String email, String lawid);

    public BaseResult updateUserPassword(int id, String password,
                                         String oldpassword);

    public BaseResult addUserInfo(CXtUser user,String roles);

    public BaseResult queryUserInfoByName(int department, String content, int page, int rows);

}
