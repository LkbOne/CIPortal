package com.test.demo.common.httpHelper;

import com.test.demo.common.exception.ApiHttpCodeException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class SentryPlatformHttpClientHelper extends PlatformHttpClientHelper {
    private Logger logger = Logger.getLogger(SentryPlatformHttpClientHelper.class);
    private final String ERRORKEY ="detail";
    private final String ERRORMESSAGE ="Invalid token";
    private StatusHelper statusHelper = new StatusHelper();
    public void judgeVisitErroe(String responseData){
        try{
            JSONArray.fromObject(responseData);
        }catch (net.sf.json.JSONException ex){
            JSONObject judgeObject = JSONObject.fromObject(responseData);
            if(judgeObject.has(ERRORKEY)&&judgeObject.getString(ERRORKEY).equals(ERRORMESSAGE)){
                throw new ApiHttpCodeException(statusHelper.getSTATUS(),statusHelper.getSENTRYTOKEN());
            }else if(judgeObject.has(ERRORKEY)){
                logger.info("sentry_message:"+judgeObject);
            }
        }
    }
    public String httpClientGetUilt(String url, String authorization) throws IOException {
        Map<String,String> header = new HashMap<String,String>();
        header.put("Content-Type","application/json ");
        header.put("Authorization","Bearer "+authorization);
        String responseData =getRequest(url,header);
        judgeVisitErroe(responseData);
        return responseData;
    }
//    public String httpClientDeleteUilt(String url, String authorization) throws IOException {
//        HttpClient client = new HttpClient();
//        DeleteMethod delete = new DeleteMethod(url);
//        delete.setRequestHeader("Content-Type", "application/json ");
//
//        delete.setRequestHeader("Authorization", "Bearer "+authorization);
//        client.executeMethod(delete);
//        InputStream in = delete.getResponseBodyAsStream();
//        StringBuffer sb = new StringBuffer();
//        InputStreamReader isr = new InputStreamReader(in, "UTF-8");
//        char[] b = new char[4096];
//        for (int n; (n = isr.read(b)) != -1; ) {
//            sb.append(new String(b, 0, n));
//        }
//        String returnStr = sb.toString();
//        return returnStr;
//    }
    public String dealUrl(String choice,String organization,String projectSlug,String issueId){
       String returnString = "" ;
       switch (choice){
           case "ALLPROJECTS":
               returnString= "https://sentry.io/api/0/projects/";
               break;
           case "ISSUE":
               returnString = "https://sentry.io/api/0/projects/"+organization+"/"+projectSlug+"/issues/";
               break;
           case "ISSUEEVENT":
               returnString = "https://sentry.io/api/0/issues/"+issueId+"/events/";
               break;
           case "DELETEISSUE":
               returnString = "https://sentry.io/api/0/issues/"+issueId+"/";
               break;
       }
       return returnString;
    }
    public String encoderUrl(String code) throws UnsupportedEncodingException {
        return URLEncoder.encode(code, "gb2312");
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
