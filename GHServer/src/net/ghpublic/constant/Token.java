package net.ghpublic.constant;

import framework.tool.HttpFileUpTool;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wzd on 2016/6/23.
 */
public class Token
{
    static String token=null;
    static long lastdate=0;

    static String urlPath = GlobalVar.PORTDATABASE_IP + GlobalVar.PORTDATABASE_LOGIN;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getDate() {
        return lastdate;
    }

    public void setDate(long date) {
        this.lastdate = date;
    }

    public static String makeToken() throws Exception
    {
        if(token==null||((new Date().getTime()-lastdate)/(1000*60)>25))
        {
            HttpFileUpTool post=new HttpFileUpTool();
            Map<String,Object> map=new HashMap<>();
            map.put("login","apiuser_ydyy");
            map.put("password","ydyy");
            map.put("format","json");
            String result=post.post(urlPath,map);

		    JSONObject data=new JSONObject(result);
            JSONObject record =  data.getJSONObject("record");
            token=record.getString("token");
            //System.out.println(token);
            lastdate=new Date().getTime();
            return token;
        }
        else
        {
            return token;
        }

    }
}
