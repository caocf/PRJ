package net.ghpublic.websocket;

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

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
    {
        System.out.println("afterConnectionEstablished");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
    {
        //把用户加入列表
        System.out.println(message.getPayload().toString());
        map.put(message.getPayload().toString(),session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
    {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
    {
        //把用户移除列表
        map.remove(session);
    }

    @Override
    public boolean supportsPartialMessages()
    {
        return false;
    }
}
