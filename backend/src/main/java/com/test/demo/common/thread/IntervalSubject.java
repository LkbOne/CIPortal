package com.test.demo.common.thread;

import com.test.demo.DataBean.Platform.PlatformFactory;

import com.test.demo.bean.platform.data.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

@Service
public class IntervalSubject {
    public static volatile boolean engine = true;
    @Autowired
    private CallAPIThread callAPIThread;
    @Autowired
    private PlatformFactory platformFactory;
    //因为是线程安全，所以选择了Vector
    static private Vector obervers = new Vector();
    private Logger logger = Logger.getLogger(IntervalSubject.class);
    public static  String[] datas = new String[]{"Sonar", "Trello","Sentry", "Merge Request", "CI Testing", "Technical DashBoard", "Geography DashBoard", "Event Logs"};
    public void firstRegisterOberver() throws IOException, ParseException, InterruptedException {
        for(int i = 0; i < datas.length; i++){
            registerOberver(platformFactory.dataFactoryMethod(datas[i]));
        }
        notifyObervers();
    }
    public void registerOberver(Data d){
        if(d != null && d.getPlatformFromDb() && d.getInterval() != 0) {
            int i = obervers.indexOf(d);
            if(i < 0){
                obervers.add(d);
            }
        }
    }
    public void removeOberver(Data d){
        int i = obervers.indexOf(d);
        if(i >= 0 && d.getInterval() == 0){
            obervers.remove(i);
        }
    }
    @Async
    public void notifyObervers() throws IOException, ParseException, InterruptedException {
        while(true) {
            if(engine){
                int nowTime = (int) System.currentTimeMillis();
                for (int i = 0; i < obervers.size(); i++) {
                    Data data = (Data) obervers.get(i);
                    if (data.calcTimeForInterval(nowTime)) {
                        callAPIThread.sendMessage(data);
                    }
                }
                Thread.sleep(1000);
            }
        }
    }
}
