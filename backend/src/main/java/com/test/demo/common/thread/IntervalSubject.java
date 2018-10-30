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
    public static volatile boolean engine =true;
    @Autowired
    private CallAPIThread callAPIThread;
    @Autowired
    private PlatformFactory platformFactory;
    //因为是线程安全，所以选择了Vector
    static private Vector obervers = new Vector();
    private Logger logger = Logger.getLogger(IntervalSubject.class);
    public static  String[] datas = new String[]{"Sonar","Trello","Sentry","Merge Request","CI Testing","Technical DashBoard","Geography DashBoard","Event Logs"};
    public void firstRegisterOberver() throws IOException, ParseException, InterruptedException {
//        String[] datas = new String[]{"Sonar","Trello","Technical DashBoard","Geography DashBoard","Event Logs"};
        for(int i=0;i<datas.length;i++){
            registerOberver(platformFactory.dataFactoryMethod(datas[i]));
        }
        notifyObervers();
    }


    public void registerOberver(Data d){
        if(d!=null&&d.getPlatformFromDb()&&d.getInterval()!=0) {
            int i= obervers.indexOf(d);
            if(i<0){
                obervers.add(d);
            }
        }
    }
    public void removeOberver(Data d){
        int i= obervers.indexOf(d);
        if(i>=0&&d.getInterval()==0){
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
//                    logger.info("obervers_size:"+obervers.size());
                    if (data.calcTimeForInterval(nowTime)) {
                        callAPIThread.sendMessage(data);
                    }
                }
                Thread.sleep(1000);
            }

        }
    }
//    @Async
//    public boolean showHooksList() throws IOException, InterruptedException {
//
//        JSONArray array = new JSONArray();
//        array.element(1);
//        array.element(2);
//        array.element(3);
//        array.element(4);
//        array.element(5);
//        array.element(6);
//        array.element(7);
//        int count=0;
//        int sleepCount =0;
//        while(true) {
//
//            for (int i = 0; i < array.size(); i++) {
//                String token = "bSSzMHFP7fB5gpGUM27w";
//                String url = "http://146.222.94.208/api/v4/projects/" + array.getString(i) + "/hooks";
//                JSONArray allGitHook = JSONArray.fromObject(gitlabHttpClientHelper.httpClientGetUilt(url, token));
//                int tempcount = deleteHooks(allGitHook, array.getString(i), token);
//                count += tempcount;
//                sleepCount+=tempcount;
//                logger.info("project_" + array.getString(i) + ":" + tempcount);
//            }
//            if(sleepCount>800){
//                Thread.sleep(300000);
//                sleepCount=0;
//            }
//            logger.info("allHook:" + count);
//        }
//    }
//    public int deleteHooks(JSONArray allGitHook,String id,String token) throws IOException {
//        int count=0;
//        for(int i=0;i<allGitHook.size();i++){
//            JSONObject aHook = allGitHook.getJSONObject(i);
//            String url = "http://146.222.94.208/api/v4/projects/"+id+"/hooks/"+aHook.getString("id");
//            String a= gitlabHttpClientHelper.httpClientDelete(url,token);
//            count++;
//        }
//        return count;
//    }
}
