//package com.test.demo.common.socket;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//import java.util.Queue;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.SynchronousQueue;
//
//@Setter
//@Getter
//@Component
//public class SocketHashHandle {
//    private final int width = 2;
//    private static int hashNumber = 0;
//    public static ConcurrentHashMap<Integer, WebSocket> webSocketSet = new ConcurrentHashMap<>();
//    public static Queue<WebSocket> leftSocket = new SynchronousQueue<>();
//    public int addSocket2Seat(){
//        return searchEmptyNumber(0,0);
//    }
//    public int searchEmptyNumber(int index,int root){
//        if((hashNumber&(1<<index))>0){
//            index++;
//            index%=width;
//            if(index==root)return -1;
//            return searchEmptyNumber(index,root);
//        }else{
//            hashNumber = (hashNumber|(1<<index));
//            return index;
//        }
//    }
//    public void setEmpty2Seat(int index){
//        if(index!=-1) {
//            hashNumber = hashNumber & (~(1 << index));
//        }
//    }
//    public void setSocket2Queue(WebSocket webSocket){
//        leftSocket.add(webSocket);
//    }
//    @Async
//    public void handleLeftSocket(){
//        while(true){
//            if(!leftSocket.isEmpty()){
//                int sessionId = addSocket2Seat();
//                if(sessionId!=-1){
//                    webSocketSet.put(sessionId,leftSocket.remove());
//                }
//            }
//        }
//    }
//}
