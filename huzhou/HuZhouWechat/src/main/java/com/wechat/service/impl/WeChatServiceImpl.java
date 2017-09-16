package com.wechat.service.impl;

import com.visionagent.utils.*;
import com.visionagent.utils.xml.XMLObject;
import com.wechat.service.WeChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by zoumy on 2017/3/23 11:38 10:02.
 */
@Service("weChatService")
public class WeChatServiceImpl extends WeChatService {
    private static final Logger log = LoggerFactory.getLogger(WeChatServiceImpl.class);

    @Autowired
    private HttpServletRequest request;

    @Override
    public List<Map<String, String>> queryPortAnnouncement(int page, int rows) {
        String url = properties.getProperty("servicePath") + properties.getProperty("portAnnouncementAction");
        return getLis(url, page);
    }


    @Override
    public List<Map<String, String>> queryDynamics(int page, int rows) {
        String url = properties.getProperty("servicePath") + properties.getProperty("dynamics");
        return getLis(url, page);
    }

    @Override
    public String queryDetail(String url) {
        String html = properties.getProperty("servicePath") + url;
        String div =  HttpRequest.getHtmlById(html, "content");
        div = replaceImg(div);
        return replaceA(div);
    }


    @Override
    public List<Map<String, String>> queryLaws(int page, int rows) {
        String url = properties.getProperty("servicePath") + properties.getProperty("laws");
        return getLis(url, page);
    }

    @Override
    public List<Map<String, String>> queryLicensing(int page, int rows) {
        String url = properties.getProperty("servicePath") + properties.getProperty("licensing");
        return getLis(url, page);
    }

    @Override
    public List<Map<String, String>> queryPunish(int page, int rows) {
        String url = properties.getProperty("servicePath") + properties.getProperty("punish");
        return getLis(url, page);
    }

    @Override
    public List<XMLObject> queryViolation(String shipName, String code) {

        List<XMLObject> list = Collections.EMPTY_LIST;
        try {
            AnalysisData ad = new AnalysisData();
            String token = ad.GetToken();// 获取令牌

            shipName = URLEncoder.encode(shipName, "UTF-8");

            String urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_PATH + "?"
                    + GlobalVar.PORTDATABASE_SERVICECODE
                    + GlobalVar.PORTDATABASE_SERVICECODE_JB + "&name=" + shipName
                    + "&token=" + token;
            String XmlString = ad.GetPostDataByXML(urlPath,
                    GlobalVar.HOST_CONNECT_TIME, GlobalVar.HOST_READ_TIME);
            list = ad.AnalysisOfXML(XmlString, "record");
            if(checkCode(list,code)){
                urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_PATH + "?"
                        + GlobalVar.PORTDATABASE_PAGE + 0 + "&"
                        + GlobalVar.PORTDATABASE_SERVICECODE
                        + GlobalVar.PORTDATABASE_SERVICECODE_WZ + "&name=" + shipName
                        + "&token=" + token;

                XmlString = ad.GetPostDataByXML(urlPath,
                        GlobalVar.HOST_CONNECT_TIME, GlobalVar.HOST_READ_TIME);
                list = ad.AnalysisOfXML(XmlString, "record");
            }else{
                XMLObject object = new XMLObject();
                XMLObject value = new XMLObject();
                value.put("value","船名或船舶登记码错误");
                object.put("shipCodeError",value);
                list = new ArrayList<>();
                list.add(object);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return list;
    }

    private boolean checkCode(List list, String code) {
        if(CollectionUtils.isEmpty(list)){
            return false;
        }
        if(code.length() != 6){
            return false;
        }
//        log.info("{}\n{}",code,list);
        String fallCode = String.valueOf(((XMLObject)list.get(0)).getXMLObject("CBDJH").getValue());
//        log.info("{}:{}",code,fallCode);
        return fallCode.endsWith(code);
    }

    private String replaceImg(String html){
        int startImg = html.indexOf("<img");
        while (startImg > 0){
            int index = html.indexOf("src",startImg);
            int end = html.indexOf("\"",index + 5);
            String temp = html.substring(index + 5,end);
            html = html.replace(temp,properties.getProperty("servicePath")+temp);
            startImg = html.indexOf("<img",end);
        }
        return html;
    }

    private String replaceA(String html) {
        int startA = html.indexOf("<a");
        while (startA > 0){
            int index = html.indexOf("href",startA);
            int end = html.indexOf("\"",index + 6);
            String temp = html.substring(index + 6,end);
            if(temp.trim().endsWith("pdf")){
                ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
                String realPath = context.getRealPath("/");
                String contextPath = context.getContextPath();
                FileUtils.downloadFile(realPath + temp,properties.getProperty("servicePath")+temp);

                String temp2 = html.substring(startA,html.indexOf("</a>",startA)+4);
                html = html.replace(temp2,"<a href='"+contextPath+"/viewpdf/web/viewer.html?file="+contextPath + temp+"'>预览附件</a>");
                //                String
            }else{
                String temp2 = html.substring(startA,html.indexOf("</a>",startA)+4);
                //http://dcsapi.com?k=123456&url=http://abc.com/123.doc
                html = html.replace(temp2,"<a href='"+properties.getProperty("officeApi")+properties.getProperty("servicePath")+temp+"'>预览附件</a>");
//                html = html.replace(temp,properties.getProperty("servicePath")+temp + "\" class=\"media");
            }
            startA = html.indexOf("<a",end);
        }
        return html;
    }
    /**
     * 解析li中内容
     *
     * @param url  请求url
     * @param page 请求页码
     * @return
     */
    private List<Map<String, String>> getLis(String url, int page) {
        url = url.replace("?", String.valueOf(page));
        String str = HttpRequest.sendGet(url);
        ArrayList<Map<String, String>> result = new ArrayList<>();
        int start = str.indexOf("<li>");
        int end = str.indexOf("</li>");
        while (end > 0 && start > 0) {
            HashMap<String, String> map = new HashMap<>();
            //取得一个li标签内内容
            String s = str.substring(start + 4, end);

            String temp = s.substring(s.indexOf("href") + 6, s.indexOf("shtml") + 5);
            map.put("url", temp);//请求详情url

            temp = s.substring(s.indexOf(">", s.indexOf("shtml")) + 1, s.indexOf("</a>"));
            map.put("content", temp);//标题内容

            temp = s.substring(s.indexOf(">", s.indexOf("date")) + 1, s.indexOf("</span>"));
            map.put("date", temp);//发布日期

            //移动指向下一个li标签
            start = str.indexOf("<li>", end);
            end = str.indexOf("</li>", start);

            result.add(map);
        }
        return result;
    }
}
