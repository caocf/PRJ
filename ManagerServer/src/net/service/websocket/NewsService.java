package net.service.websocket;

import net.modol.CrewBaseEN;
import net.service.BaseService;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.RunnableFuture;

/**
 * Created by Admin on 2016/7/7.
 */
@Service
public class NewsService extends BaseService
{
    @Resource
    WSHandler wsHandler;

    //向全体在线用户推送
    public void SendMSAll(final String ms) throws Exception
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (String key : wsHandler.map.keySet())
                {
                    try
                    {
                        wsHandler.map.get(key).sendMessage(new TextMessage(ms));
                    }catch (Exception e)
                    {

                    }
                }
            }
        }).start();

    }
}
