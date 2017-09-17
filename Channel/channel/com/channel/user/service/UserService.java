package com.channel.user.service;

import com.common.action.result.BaseResult;

import java.io.UnsupportedEncodingException;

public interface UserService {

    public BaseResult login(String username, String password);

    public BaseResult queryVersion();

    public BaseResult logout();

    public BaseResult disableUser(int id);

    public BaseResult delUser(String strids);

    public BaseResult queryUser(int department, int dptflag, String content, int page, int rows, String Echo);

    public BaseResult enableUser(int id);

    public BaseResult updateUser(int id, String username, String password,
                                 String name, String tel, String email, int title, int jstatus,
                                 String lawid, String roles);

    public BaseResult initPerm() throws UnsupportedEncodingException;
}
