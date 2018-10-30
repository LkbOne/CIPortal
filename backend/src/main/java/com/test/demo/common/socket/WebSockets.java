package com.test.demo.common.socket;

import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

@Setter
@Getter
@Component
public class WebSockets {
    private final int width = 100;
    private static int hashNumber = 0;
    public static ConcurrentHashMap<Integer, WebSocketSession> webSocketSet = new ConcurrentHashMap<>();
    public static Queue<WebSocketSession> leftSocket = new SynchronousQueue<>();
    public int addSocket2Seat(){
        return searchEmptyNumber(0,0);
    }
    public int searchEmptyNumber(int index,int root){
        if((hashNumber&(1<<index))>0){
            index++;
            index%=width;
            if(index==root)return -1;
            return searchEmptyNumber(index,root);
        }else{
            hashNumber = (hashNumber|(1<<index));
            return index;
        }
    }
    public static void setEmpty2Seat(int index){
        if(index!=-1) {
            hashNumber = hashNumber & (~(1 << index));
        }
    }
    public void setSocket2Queue(WebSocketSession webSocket){
        leftSocket.add(webSocket);
    }
//    @Async
//    public void handleLeftSocket() throws InterruptedException {
//
//        while(true){
//            if(!leftSocket.isEmpty()){
//                int sessionId = addSocket2Seat();
//                if(sessionId!=-1){
//                    System.out.println("aaaaaaaaa");
//                    webSocketSet.put(sessionId,leftSocket.remove());
//                }
//            }
//            Thread.sleep(1000);
//                    webSocketSet.put(sessionId,leftSocket.remove());
//        }
//    }

    public static void sendMessageAll(String message) throws IOException {
        for(Map.Entry<Integer, WebSocketSession> entry: webSocketSet.entrySet())
        {
            WebSocketSession socket = entry.getValue();
            sendMessage(socket,message);
        }
    }
    public static void sendMessage(WebSocketSession socketSession, String message) throws IOException {
        synchronized (socketSession) {
            TextMessage textMessage = new TextMessage(message);
            if (socketSession.isOpen()) {
                socketSession.sendMessage(textMessage);
            }
        }
    }
}
