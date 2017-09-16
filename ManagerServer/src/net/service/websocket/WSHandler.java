package net.service.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wzd on 2016/6/25.
 */
public class WSHandler implements WebSocketHandler
{
    //用户列表
    public Map<String,WebSocketSession> map=new HashMap<>();
    //ais接收
    public Map<String,WebSocketSession> ais_map=new HashMap<>();

    //publicdata void NoticeAll()

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
    {
        System.out.println("afterConnectionEstablished");

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
    {
        String s=message.getPayload().toString();
        if("AIS".equals(s.split("_")[0]))
        {
            ais_map.put(message.getPayload().toString(),session);
        }else
        {
            map.put(message.getPayload().toString(),session);
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
    {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
    {
        System.out.println("afterConnectionClosed");

        map.remove(session);

    }

    @Override
    public boolean supportsPartialMessages()
    {
        return false;
    }
}
