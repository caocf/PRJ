package com.wechat.service;

import com.visionagent.framework.service.BaseService;
import com.visionagent.utils.PropertiesLoader;
import com.visionagent.utils.xml.XMLObject;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zoumy on 2017/3/23 11:36.
 */
public abstract class WeChatService implements BaseService {

    /**
     * The constant properties.
     */
    protected static Properties properties;

    static{
        init();
    }

    private static void init(){
        properties =  PropertiesLoader.getPropertiesFromClassPath("huzhou.properties");
    }


    /**
     * 查询航行通告列表
     *
     * @param page the page
     * @param rows the rows
     * @return the list
     * @Auth Will
     * @Date 2017 -03-23 14:36:27
     */
    public abstract List<Map<String,String>> queryPortAnnouncement(int page, int rows);

    /**
     * 港航动态列表
     *
     * @param page the page
     * @param rows the rows
     * @return the list
     * @Auth Will
     * @Date 2017 -03-24 13:01:27
     */
    public abstract List<Map<String,String>> queryDynamics(int page, int rows);

    /**
     * 详情
     *
     * @param url the url
     * @return the string
     * @Auth Will
     * @Date 2017 -03-24 13:01:27
     */
    public abstract String queryDetail(String url);

    /**
     * 法律法规列表
     *
     * @param page the page
     * @param rows the rows
     * @return the list
     * @Auth Will
     * @Date 2017 -03-24 13:01:27
     */
    public abstract List<Map<String,String>> queryLaws(int page, int rows);

    /**
     * 行政许可列表
     *
     * @param page the page
     * @param rows the rows
     * @return the list
     * @Auth Will
     * @Date 2017 -03-24 13:01:27
     */
    public abstract List<Map<String,String>> queryLicensing(int page, int rows);

    /**
     * 行政处罚列表
     *
     * @param page the page
     * @param rows the rows
     * @return the list
     * @Auth Will
     * @Date 2017 -03-24 13:01:27
     */
    public abstract List<Map<String,String>> queryPunish(int page, int rows);


    /**
     * Query violation object.
     *
     * @param shipName the ship name
     * @param code     the code
     * @return the object
     * @Auth Will
     * @Date 2017 -03-24 13:54:20
     */
    public abstract List<XMLObject> queryViolation(String shipName, String code);
}
