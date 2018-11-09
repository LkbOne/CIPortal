package com.test.demo.common.thread;
import com.test.demo.bean.platform.data.*;
import com.test.demo.common.socket.WebSockets;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.ParseException;
@Service
public class CallAPIThread {
    private Logger logger = Logger.getLogger(CallAPIThread.class);
    @Async
    public void sendMessage(Data dataClass) throws IOException, ParseException {
        JSONObject data;
        data = dataClass.socketGate(dataClass.getName());
        data.element("name", dataClass.getName());
        WebSockets.sendMessageAll(data.toString());
    }
}
