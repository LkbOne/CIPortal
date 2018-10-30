package com.test.demo.common.httpHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.HashMap;


public class SonarPlatformHttpClientHelper extends PlatformHttpClientHelper {
    private Logger logger = Logger.getLogger(SonarPlatformHttpClientHelper.class);
    public String dealUrl(String choice,String host,String projectKeys) throws JSONException {
        String returnString="";
        switch (choice){
            case "ALLPROJECTS":
                returnString = "http://zha-mvp-005-w7.corp.oocl.com:"+host+"/api/components/search_projects";
                break;
            case "ALLDATA":
                returnString ="http://zha-mvp-005-w7.corp.oocl.com:"+host+"/api/measures/search?projectKeys="+dealUrlHelper(projectKeys)+"&metricKeys=alert_status%2Cbugs%2Creliability_rating%2Cvulnerabilities%2Csecurity_rating%2Ccode_smells%2Csqale_rating%2Cduplicated_lines_density%2Ccoverage%2Cncloc%2Cncloc_language_distribution";
                 break;
            case "ONEPROJECT":
                returnString ="http://zha-mvp-005-w7.corp.oocl.com:"+host+"/dashboard?id="+projectKeys;
                break;
        }
        return returnString;
    }
    public String dealUrlHelper(String projectKeys) throws JSONException {
        JSONArray projectArray = JSONArray.fromObject(projectKeys);
        String url = "";
        for(int i=0;i<projectArray.size();i++){

            JSONObject oneUrl = projectArray.getJSONObject(i);
            url+=oneUrl.getString("key");
            if(i!=projectArray.size()-1){
                url+="%2C";
            }
        }
        return url;
    }

    @Override
    public String dealUrl() {
        return null;
    }

    @Override
    public String judgeVisitErroe() {
        return null;
    }

    public  String httpClientGetUilt(String url) throws IOException {
        return getRequest(url,new HashMap<>());
    }

    @Override
    public String httpClientPostUilt(String url) {
        return null;
    }
}
