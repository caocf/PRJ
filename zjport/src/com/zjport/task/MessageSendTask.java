package com.zjport.task;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by TWQ on 2016/9/22.
 */
@Controller
@RequestMapping("/Task")
public class MessageSendTask {

    @RequestMapping(value = "/sendMessage")
    public String sendMessage(String tel, String code) throws Exception{
        String[] telNum = tel.split(",");
        for(int i = 0; i<telNum.length; i++) {
            this.sendMSGAL(telNum[i],code);
        }
        return "success";
    }

    private String sendMSGAL(String tel,String code) throws Exception
    {
        //发送短信
        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23418011", "9610d7dd1cb4c01a9d1a18895f3fa226");
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        //req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("身份验证");
        JSONObject jb=new JSONObject();
        jb.put("code",code);//变量
        //jb.put("product","注册");
        req.setSmsParamString(jb.toString());
        req.setRecNum(tel);
        req.setSmsTemplateCode("SMS_12740170");
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
        /*//System.out.println(rsp.getBody());
        JSONObject jsonObject=new JSONObject(rsp.getBody());
        JSONObject jsonObject1=jsonObject.getJSONObject("alibaba_aliqin_fc_sms_num_send_response");
        //EncodeTool.convertStreamToString(new String(rsp.getBody()));
        */
        return rsp.getBody();
    }
}
