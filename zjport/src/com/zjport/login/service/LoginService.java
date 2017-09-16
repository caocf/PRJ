package com.zjport.login.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.base.service.BaseService;
import com.common.utils.Md5Secure;
import com.zjport.login.dao.LoginDao;
import com.zjport.manage.dao.ManageDao;
import com.zjport.model.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TWQ on 2016/8/10.
 */
@Service("loginService")
public class LoginService extends BaseService {

    public String secret = "";

    @Resource(name = "loginDao")
    private LoginDao loginDao;

    @Resource(name = "manageDao")
    private ManageDao manageDao;

    public TUser selectUserById(String id)
    {
        return this.loginDao.selectUser(id);
    }

    public TOrg selectOrgById(String id)
    {
        return this.loginDao.selectOrg(id);
    }

    public TDepartment selectDeptById(String id)
    {
        return this.loginDao.selectDept(id);
    }

    public String queryUserNoQx(Integer jsbh)
    {
        return this.manageDao.queryUserQx(jsbh);
    }

    public TUser selectUserByAccount(String account) {return this.loginDao.selectUserByAccount(account);}

    /*public List<SsoSystem> selectSsoSystem(int userId) {return this.loginDao.selectSsoSystem(userId);}*/

    public List<TApplication> getExistSystem(int userId,String account) {
        List<TUserApplication> appList = this.loginDao.selectUserApp(userId);
        for(TUserApplication app : appList) {
            this.loginDao.delete(app);
        }

        List<TApplication> applicationList = new ArrayList<TApplication>();

        /* 航道系统验证用户是否存在 */
        //String result = call("http://192.168.1.251:8080/Channel/user/userIsExist","13905721385");
        String channelResult = call("http://10.100.70.126/Channel/user/userIsExist",account,"1");
        JSONObject channelObj = JSON.parseObject(channelResult);
        if(channelObj != null) {
            if ("1".equals(channelObj.getString("resultcode"))) {
                TUserApplication userApp = new TUserApplication();
                userApp.setStAppId(1);
                userApp.setStUserId(userId);
                this.loginDao.saveOrUpdate(userApp);
                appList.add(userApp);
                //System.out.println("有此用户");

                TApplication application = this.loginDao.selectApplication(1);
                applicationList.add(application);
            }
        }

        /* 省港航移动平台验证用户是否存在 */
        //String result1 = call("http://192.168.1.126:6080/QeuryAccount","13905721385");
        String movePlatformResult = call("http://10.100.70.127/QeuryAccount",account,"4");
        JSONObject moveObj = JSON.parseObject(movePlatformResult);
        if(moveObj != null) {
            if ("0".equals(moveObj.getString("resultcode"))) {
                TUserApplication userApp = new TUserApplication();
                userApp.setStAppId(4);
                userApp.setStUserId(userId);
                this.loginDao.saveOrUpdate(userApp);
                appList.add(userApp);
                //System.out.println("有此用户");

                TApplication application = this.loginDao.selectApplication(4);
                applicationList.add(application);
            }
        }

        /* 视频综合平台验证用户是否存在 */
        //暂时视频平台跟着用户走（仅允许陈超和何芊易有此功能）
        if("18158122976".equals(account) || "18957175659".equals(account) || "guest".equals(account)) {
            TUserApplication userApp = new TUserApplication();
            userApp.setStAppId(6);
            userApp.setStUserId(userId);
            this.loginDao.saveOrUpdate(userApp);
            appList.add(userApp);

            TApplication application = this.loginDao.selectApplication(6);
            applicationList.add(application);
        }

        /* 稽征系统验证用户是否存在 */
        String shipChargeResult = call("http://10.100.70.21/shipCharge/checkuser.aspx",account,"2");
        shipChargeResult = shipChargeResult.replaceAll(" ","");//去掉字符串里的所以空格

        JSONObject shipObj = JSON.parseObject(shipChargeResult);
        if(shipObj != null) {
            if("valid".equals(shipObj.getString("checked"))) {
                TUserApplication userApp = new TUserApplication();
                userApp.setStAppId(2);
                userApp.setStUserId(userId);
                this.loginDao.saveOrUpdate(userApp);
                appList.add(userApp);
                //System.out.println("有此用户");

                TApplication application = this.loginDao.selectApplication(2);
                applicationList.add(application);
                secret = shipObj.getString("secret");
            }
        }

        return applicationList;
    }

    public  String getSecret() {
        return secret;
    }

    //用于省航道系统和移动平台验证
    private String call(String url, String account, String type) {
        String responseMsg = "";
        HttpClient httpClient=new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(2000);//设置2秒的连接超时

        PostMethod postMethod=new PostMethod(url);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
        //当前时间戳
        String times = String.valueOf(System.currentTimeMillis());
        if("2".equals(type)) {
            postMethod.addParameter("user",account);
            postMethod.addParameter("ts",times);
            postMethod.addParameter("token",this.getToken(account,times,"19406e177e4f4d719e0c0639ce89830e"));
        } else {
            postMethod.addParameter("account", account);
        }

        try {
            httpClient.executeMethod(postMethod);
            responseMsg = postMethod.getResponseBodyAsString().trim();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }

        return responseMsg;
    }

    //稽征系统需要提供的凭证，三个参数进行拼接，并进行md5加密
    private String getToken(String A, String B, String C) {
        return Md5Secure.encode(A+B+C).toLowerCase();
    }
}
