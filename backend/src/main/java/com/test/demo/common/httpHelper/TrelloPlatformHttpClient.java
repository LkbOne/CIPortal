package com.test.demo.common.httpHelper;
import com.test.demo.common.exception.ApiHttpCodeException;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;


public class TrelloPlatformHttpClient extends PlatformHttpClientHelper {
    private Logger logger = Logger.getLogger(TrelloPlatformHttpClient.class);
    private final String ERRORKEY ="invalid key";
    private final String ERRORMESSAGE ="invalid token";

    private StatusHelper statusHelper = new StatusHelper();

    @Override
    public String dealUrl() {
        return null;
    }

    @Override
    public String judgeVisitErroe() {
        return null;
    }

    public  String httpClientGetUilt(String url) throws IOException {
        String responseData =getRequest(url,new HashMap<>());
        judgeVisitErroe(responseData);
        return responseData;
    }
    public void judgeVisitErroe(String responseData){
        //logger.info("responseData:"+responseData);
         if(responseData.equals(ERRORMESSAGE)){
             throw new ApiHttpCodeException(statusHelper.getSTATUS(),statusHelper.getTRELLOTOKEN());
         } else if(responseData.equals(ERRORKEY)){
            throw new ApiHttpCodeException(statusHelper.getSTATUS(),statusHelper.getTRELLOKEY());
         }
//        else{
//            throw new ApiHttpCodeException(statusHelper.getSTATUS(),statusHelper.getSERVICEERROR());
//        }
    }
    public String httpClientPostUilt(String url) throws IOException {
        String responseData = postRequest(url,new HashMap(),new JSONObject());
        judgeVisitErroe(responseData);
        return responseData;
    }
    public String httpClientDelete(String url) throws IOException {
        HttpClient client = new HttpClient();
        DeleteMethod delete = new DeleteMethod(url);
        client.executeMethod(delete);
        InputStream in = delete.getResponseBodyAsStream();
//下面将stream转换为String
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(in, "UTF-8");
        char[] b = new char[4096];
        for (int n; (n = isr.read(b)) != -1; ) {
            sb.append(new String(b, 0, n));
        }
        String returnStr = sb.toString();
        return returnStr;

    }


    public String dealUrl(String choice,String boardId,String token,String key,String cardName,String ListId,String cardId,String desc) throws UnsupportedEncodingException {
        String returnString = "" ;
        switch (choice){
            case "ALLPROJECTS":
                returnString= "https://api.trello.com/1/members/me/boards?fields=name,url&token="+token+"&key="+key;
                //logger.info("ALLBORAD:"+returnString);
                break;
            case "AllCARDS":
                returnString="https://trello.com/1/boards/"+boardId+"/lists?cards=open&card_fields=id,dateLastActivity,name,badges,idMembers,labels,url&filterBychosen=open&fields=name&token="+token+"&key="+key;
               // returnString="https://trello.com/1/boards/5af8e74835813a39caa9d3af/lists?cards=open&card_fields=id,dateLastActivity,name,badges,idMembers,labels,url&filter=open&fields=name&token=a4f99d894674645f9debb528cbd0ae86bf88d5119eb67e125d48904e2b369983&key=2f5cba1d75d5e6e2303d17e39f5a9ea6";
                // logger.info("AllCARDS:"+returnString);
                break;
            case "MEMBERS":
                returnString= "https://api.trello.com/1/cards/"+cardId+"/members?&token="+token+"&key="+key;
                //logger.info("MEMBERS:"+returnString);
                break;

            case "SHOWLIST":
                returnString = "https://trello.com/1/boards/"+boardId+"/lists?fields=name&token="+token+"&key="+key;
                break;
            case "ADDLIST":
                returnString = "https://api.trello.com/1/boards/"+boardId+"/lists?name="+encoderUrl("Dev In-Progress")+"&token="+token+"&key="+key;
                break;
            case "ADDCARD":
                 returnString="https://api.trello.com/1/cards?pos=top&name="+cardName+"&desc="+desc+"&idList="+ListId+"&keepFromSource=all&token="+token+"&key="+key;

                 break;
            case "ADDLABEL":
                returnString="https://api.trello.com/1/cards/"+cardId+"/labels?color=red&name=bug&token="+token+"&key="+key;
                break;
            case "DELETECARD":
                returnString="https://api.trello.com/1/cards/"+cardId+"?color=red&name=bug&token="+token+"&key="+key;
                break;
        }
        return returnString;
    }
    public String encoderUrl(String code) throws UnsupportedEncodingException {
        return URLEncoder.encode(code, "gb2312");
    }
}
