package net.service.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by wzd on 2016/6/25.
 */
@Configuration
@Service
@EnableWebSocket
public class WebSocket extends WebMvcConfigurerAdapter implements WebSocketConfigurer
{
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
    {
        registry.addHandler(getHandler(),"/webSocketServer");
        //
    }
    @Bean
    public WSHandler getHandler()
    {
        return new WSHandler();
    }
}
