package com.visionagent.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by zoumy on 2017/3/23 12:03.
 */
public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private static final String PREFIX = "--";
    private static final String LINEND = "\r\n";
    private static final String MULTIPART_FROM_DATA = "multipart/form-data";
    private static final String CHARSET = "UTF-8";

    /**
     * 发送get请求
     *
     * @param url the url
     * @return the string
     * @Auth Will
     * @Date 2017 -03-23 14:41:25
     */
    public static String sendGet(String url){
        StringBuffer result = new StringBuffer();
        BufferedReader reader = null;
        try{
            URL realUrl = new URL(url);
            //打开链接
            URLConnection connection = realUrl.openConnection();
            //设置请求属性
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("connection","Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36 OPR/43.0.2442.1144");
            connection.connect();//建立链接
            Map<String,List<String>> map = connection.getHeaderFields();
            log.info("========================================");
            for(Map.Entry<String,List<String>> entry :map.entrySet()){
                log.info("{}:{}",entry.getKey(), String.valueOf(entry.getValue()));
            }
            log.info("========================================");
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(),CHARSET);
            reader = new BufferedReader(inputStreamReader);
            String line;
            while((line = reader.readLine()) != null){
                result.append(line);
            }

        }catch (IOException e){
            log.error("==========={}==========","发送GET请求失败");
            log.error(e.getMessage(),e);
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    /**
     * 发送post请求
     *
     * @param url   the url
     * @param param the param  key1=value1&key2=value2
     * @return the string
     * @Auth Will
     * @Date 2017 -03-23 14:41:25
     */
    public static String sendPost(String url,Map<String,Object> param){
        StringBuffer result = new StringBuffer();
        BufferedReader reader = null;
        PrintWriter out = null;
        URLConnection connection = null;
        try{
            URL realUrl = new URL(url);
            //打开链接
            connection = realUrl.openConnection();
            //设置请求属性
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("connection","Keep-Alive");
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36 OPR/43.0.2442.1144");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //POST 必须加
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String,Object> entry: param.entrySet()) {
//                sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                sb.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(String.valueOf(entry.getValue()),CHARSET));
            }

            out = new PrintWriter(connection.getOutputStream());
            out.print(sb.substring(1));
            out.flush();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),CHARSET));
            String line;
            while((line = reader.readLine()) != null){
                result.append(line);
            }

        }catch (IOException e){
            log.error("==========={}==========","发送GET请求失败");
            log.error(e.getMessage(),e);
        }finally {
            try {
                if(reader != null){
                    reader.close();
                }
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 获取html标签内容
     *
     * @param url the url
     * @return the string
     * @Auth Will
     * @Date 2017 -03-23 14:41:25
     */
    public static String getHtmlByTag(String url,String tagName){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc.getElementsByTag(tagName).toString();
    }
    /**
     * 获取html标签内容根据ID
     *
     * @param url the url
     * @return the string
     * @Auth Will
     * @Date 2017 -03-23 14:41:25
     */
    public static String getHtmlById(String url,String id){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(30000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc.getElementById(id).toString();
    }
}
