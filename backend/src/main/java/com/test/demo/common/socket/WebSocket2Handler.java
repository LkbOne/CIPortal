package com.test.demo.common.socket;

import com.test.demo.User.service.UserService;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import javax.websocket.Session;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

@RestController
@Getter
@Setter
public class WebSocket2Handler extends TextWebSocketHandler {
    private static Logger logger = Logger.getLogger(TextWebSocketHandler.class);
    @Value("${spring.profiles}")
    private String env;
    private int account;
    private Session session;
    @Autowired
    private WebSockets webSockets;
    @Autowired
    private UserService userService;
    //@OnOpen
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        JSONObject rebackMessage = new JSONObject();
        this.account = webSockets.addSocket2Seat();
        if(this.account != -1) {
            WebSockets.webSocketSet.put(this.account, webSocketSession);
            rebackMessage.put("eventType", "SUCCESS");
        }else{
            rebackMessage.put("eventType", "FULL");
            WebSockets.sendMessage(webSocketSession, rebackMessage.toString());
            rebackMessage.put("eventType", "SUCCESSSSSSS");
        }
        WebSockets.sendMessage(webSocketSession, rebackMessage.toString());
    }
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        WebSockets.setEmpty2Seat(this.account);
        if(account != -1) {
            WebSockets.webSocketSet.remove(this.account);
        }
        webSockets.setEmpty2Seat(this.account);
        WebSockets.webSocketSet.remove(this.account);
    }
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
