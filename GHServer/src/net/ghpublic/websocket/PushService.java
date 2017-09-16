package net.ghpublic.websocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;

/**
 * Created by Admin on 2016/10/13.
 */
@Service
public class PushService
{
    @Resource
    WSHandler handler;
    //向全体在线用户推送
    public void SendMSAll(final String ms) throws Exception
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (String key : handler.map.keySet())
                {
                    try
                    {
                        handler.map.get(key).sendMessage(new TextMessage(ms));
                    }catch (Exception e)
                    {

                    }
                }

            }
        }).start();
    }
}
