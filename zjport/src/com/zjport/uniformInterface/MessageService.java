package com.zjport.uniformInterface;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by TWQ on 2016/12/2.
 */
@Controller
@RequestMapping("/ZJGH_JK/SMS")
public class MessageService {

    /**
     * 发送短信接口
     * @param phone 手机号集
     * @param code  发送内容
     * @return successNum_Sum_failNumber(成功发送条数_总条数_失败号码)
     * @throws Exception
     */
    @RequestMapping(value = "/sendMessage")
    @ResponseBody
    public String sendMessage(String phone,String code,String signature) throws Exception {
        String responseMsg = "";
        //1.构造HttpClient的实例
        HttpClient httpClient=new HttpClient();
        /*httpClient.getParams().setContentCharset("GBK");*/
        /*String url="http://218.75.63.106:3966/MessageTask/task/sendMessage";*/
        String url="http://172.20.24.100:3966/Middle/task/sendMessage";
        /*String url="http://218.75.63.106:3966/Middle/task/sendMessage";*/
        //2.构造PostMethod的实例
        PostMethod postMethod=new PostMethod(url);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
        //3.把参数值放入到PostMethod对象中
        //方式1：
        /*NameValuePair[] data = { new NameValuePair("tel", "15888398873,15990305045"),
        new NameValuePair("code", "SMS_12740170") };
        postMethod.setRequestBody(data);*/

        //方式2：
        /*String phone = "15888398873,15868324190";*/
        postMethod.addParameter("phone", phone);
        postMethod.addParameter("code", code);
        postMethod.addParameter("signature",signature);
        try {
            // 4.执行postMethod,调用http接口
            httpClient.executeMethod(postMethod);//200
            //5.读取内容
            responseMsg = postMethod.getResponseBodyAsString().trim();
            //6.处理返回的内容
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //7.释放连接
            postMethod.releaseConnection();
        }
        String result = responseMsg.replace("\\","");
        result = result.replace("\"","");

        return result;
    }
}
