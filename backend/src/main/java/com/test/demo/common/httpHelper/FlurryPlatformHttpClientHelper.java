package com.test.demo.common.httpHelper;

import com.test.demo.common.exception.ApiHttpCodeException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;

public class FlurryPlatformHttpClientHelper extends PlatformHttpClientHelper {
    private Logger logger = Logger.getLogger(FlurryPlatformHttpClientHelper.class);
    private final String ERRORKEY = "message";
    private final String ERRORMESSAGE = "Unauthorized";
    private StatusHelper statusHelper = new StatusHelper();
    public String dealUrl(String choice,String projectID) {
        String flurryHeader = "https://dev1.flurry.com/api/";
        String url = "";
        switch (choice) {
            case "USER":
                url = flurryHeader+"user";
                break;
            case "EVENTLOG":
                url = flurryHeader + "rs/project/" + projectID + "/eventLog";
                break;
            case "NEWDEVICESOFREGION":
                url = flurryHeader + "metrics/fluidTopN";
                break;
            case "NEWDEVICESOFTECHNICAL":
                url = flurryHeader + "metrics/topNBreakdown";
                break;
        }
        return url;
    }
    public String httpClientGetUilt(String url, String flurryToken) throws IOException {
        HashMap<String, String> header = new HashMap();
        header.put("flurry-auth-token", flurryToken);
        String responseData = super.getRequest(url, header);
        judgeVisitErroe(responseData);
        return responseData;
    }
    public String httpClientPostUilt(String url, String flurryToken, JSONObject bodyObject) throws IOException {
        HashMap<String,String> header = new HashMap();
        header.put("flurry-auth-token", flurryToken);
        header.put("Content-Type", "application/json");
        String responseData = postRequest(url, header, bodyObject);
        judgeVisitErroe(responseData);
        return responseData;
    }
   public void judgeVisitErroe(String responseData){
        try{
            JSONArray.fromObject(responseData);
        }catch (net.sf.json.JSONException ex){
            JSONObject judgeObject = JSONObject.fromObject(responseData);
            if(judgeObject.has(ERRORKEY) && judgeObject.getString(ERRORKEY).equals(ERRORMESSAGE)){
                throw new ApiHttpCodeException(statusHelper.getSTATUS(), statusHelper.getFLURRYTOKEN());
            }else if(judgeObject.has(ERRORKEY)){
                logger.info("flurry:" + judgeObject);
            }
        }
    }

    @Override
    public String dealUrl() {
        return null;
    }

    @Override
    public String judgeVisitErroe() {
        return null;
    }

    @Override
    public String httpClientGetUilt(String url) throws IOException {
        return null;
    }
    @Override
    public String httpClientPostUilt(String url) {
        return null;
    }
}
