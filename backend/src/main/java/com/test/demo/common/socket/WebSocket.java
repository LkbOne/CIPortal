//package com.test.demo.common.socket;
//
//import com.test.demo.User.service.UserService;
//import lombok.Getter;
//import lombok.Setter;
//import net.sf.json.JSONException;
//import net.sf.json.JSONObject;
//import org.apache.log4j.Logger;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import javax.websocket.server.PathParam;
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@ServerEndpoint("/api/{accounts}")
//@Component
//@RestController
//@Getter
//@Setter
//public class WebSocket {
//    private static Logger logger = Logger.getLogger(WebSocket.class);
////    private static ConcurrentHashMap<String, String> sendMessageOfCI = new ConcurrentHashMap<>();
////    private static ConcurrentHashMap<String, String> sendMessageOfMergeData = new ConcurrentHashMap<>();
//    private Session session;
//    private int account;
//    @Autowired
//    private SocketHashHandle socketHashHandle = new SocketHashHandle();
//    @Autowired
//    private UserService userService;
//    @OnOpen
//    public void onOpen(@PathParam("accounts") String accounts, Session session) throws IOException, JSONException {
//        this.session = session;
//        JSONObject rebackMessage = new JSONObject();
//        this.account = socketHashHandle.addSocket2Seat();
//        SocketHashHandle.webSocketSet.put(this.account, this);
//        if(this.account!=-1) {
//            rebackMessage.put("eventType", "SUCCESS");
//        }else{
//            rebackMessage.put("eventType", "FULL");
//            sendMessageTo(rebackMessage.toString());
//            socketHashHandle.setSocket2Queue(this);
//            onClose();
//        }
//        sendMessageTo(rebackMessage.toString());
//    }
//
//    @OnClose
//    public void onClose()  {
//        socketHashHandle.setEmpty2Seat(this.account);
//        SocketHashHandle.webSocketSet.remove(this.account);
//    }
//
//    @OnMessage
//    public void onMessage(String message)  {
//        logger.info("来自客户端的消息:" + message);
//    }
//
//    @OnError
//    public void onError(Session session, Throwable error) {
//       // error.printStackTrace();
//        throw new RuntimeException();
//    }
//
//    public void sendMessage(String message) throws IOException {
//        synchronized (this.session) {
//            if (this.session.isOpen()) {
//                this.session.getBasicRemote().sendText(message);
//            }
//        }
//    }
//
//    public void sendMessageTo(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//    }
//
//    public static void sendMessageAll(String message) throws IOException {
//        for(Map.Entry<Integer, WebSocket> entry: SocketHashHandle.webSocketSet.entrySet())
//        {
//            WebSocket socket = entry.getValue();
//            socket.sendMessage(message);
//        }
//    }
//}
