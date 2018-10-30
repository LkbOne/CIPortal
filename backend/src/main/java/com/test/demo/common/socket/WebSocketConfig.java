package com.test.demo.common.socket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;


@Configuration
@EnableWebSocket
@EnableWebMvc
public class WebSocketConfig  implements WebSocketConfigurer {
    @Autowired
    WebSocket2Handler WebSocket2Handler;
    @Autowired
    HandshakeInterceptor aHandshakeInterceptor;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        String[] allowsOrigins = {"*"};
        registry.addHandler(WebSocket2Handler, "/api").addInterceptors(aHandshakeInterceptor).setAllowedOrigins(allowsOrigins); //url和handler的mapping
    }
}