package com.test.demo.DataBean.Platform;

import com.test.demo.DataBean.Platform.Interface.PlatformDao;
import com.test.demo.DataBean.Platform.bean.*;
import com.test.demo.DataBean.Platform.dao.*;
import com.test.demo.bean.platform.data.*;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class PlatformFactory {
    @Autowired
    NullPlatformDao nullPlatformDao;
    @Autowired
    SonarData sonarData;
    @Autowired
    TrelloData trelloData;
    @Autowired
    SentryData sentryData;
    @Autowired
    CIData ciData;
    @Autowired
    MergeRequestData mergeRequestData;
    @Autowired
    NewDevicesOfRegion newDevicesOfRegion;
    @Autowired
    NewDevicesOfTechnical newDevicesOfTechnical;
    @Autowired
    FlurryData flurryData;
    @Autowired
    FlurryDao flurryDao;
    @Autowired
    TrelloDao trelloDao;
    @Autowired
    SonarDao sonarDao;
    @Autowired
    SentryDao sentryDao;
    @Autowired
    GitLabDao gitLabDao;
    @Autowired
    EventLogData eventLogData;
    public Data dataFactoryMethod(String name){
        Data data;
        switch (name){
            case "Sonar":
                data = sonarData;
                break;
            case "Trello":
                data = trelloData;
                break;
            case "Sentry":
                data = sentryData;
                break;
            case "Merge Request":
                data = mergeRequestData;
                break;
            case "CI Testing":
                data = ciData;
                break;
            case "Event Logs":
                data = eventLogData;
                break;
            case "Technical DashBoard":
                data = newDevicesOfTechnical;
                break;
            case "Geography DashBoard":
                data = newDevicesOfRegion;
                break;
            default:
                data = new NullData();
                break;
        }
        return data;
    }
    public PlatformDao platformDaoFactoryMethod(String name){
        PlatformDao platformDao;
        switch (name){
            case "Sonar":
                platformDao = sonarDao;
                break;
            case "Trello":
                platformDao = trelloDao;
                break;
            case "Sentry":
                platformDao = sentryDao;
                break;
            case "Event Logs":
            case "Geography DashBoard":
            case "Technical DashBoard":
                platformDao =  flurryDao;
                break;
            case "Merge Request":
            case "CI Testing":
                platformDao = gitLabDao;
                break;
            default:
                platformDao = nullPlatformDao;
                break;
        }

        return platformDao;
    }
}
