package com.test.demo.common.httpHelper;

import com.test.demo.common.exception.ApiHttpCodeException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GitlabPlatformHttpClientHelper extends PlatformHttpClientHelper {
    private Logger logger = Logger.getLogger(GitlabPlatformHttpClientHelper.class);
    private final String ERRORKEY = "message";
    private final String ERRORMESSAGE = "401 Unauthorized";
    private StatusHelper statusHelper = new StatusHelper();
    public void judgeVisitErroe(String responseData){
        try{
            JSONArray.fromObject(responseData);
        }catch (net.sf.json.JSONException ex){
            JSONObject judgeObject = JSONObject.fromObject(responseData);
            if(judgeObject.has(ERRORKEY) && judgeObject.getString(ERRORKEY).equals(ERRORMESSAGE)){
                throw new ApiHttpCodeException(statusHelper.getSTATUS(), statusHelper.getGITTOKEN());
            }else if(judgeObject.has(ERRORKEY)){
                logger.info("git_message:" + judgeObject);
            }
        }
    }
    public String dealUrl(String choice, String gitIp, String projectId, String hookUrl){
        String url = "";
        switch(choice){
            case "ALLGITPROJECT":
                url += "http://" + gitIp + "/api/v4/projects/" ;
                break;
            case "HOOK":
                url+="http://" + gitIp + "/api/v4/projects/" + projectId + "/hooks";
                break;
            case "ADDWS":
                url+="ws"+hookUrl;
                break;
            case "Merge Request":
                url += "http://" + gitIp + "/api/v4/projects/" + projectId + "/merge_requests";
                break;
        }
        return url;
    }
    public String dealPipelinesUrl(String choice, String projectId, String ip, String pipelineId, String projectName) {
        String http = "http://";
        String apiString = "/api/v4/projects/";
        String piplinesString = "/pipelines/";
        String url = "";
        String rootString = "/root/";
        switch (choice) {
            case "ALLPIPELINE":
                url = http + ip + apiString + projectId + piplinesString;
                break;
            case "ONEPIPELINE":
                url = http + ip + apiString + projectId + piplinesString + pipelineId;
                break;
            case "ONEPIPELINEURL":
                url = http + ip + rootString + projectName + piplinesString + pipelineId;
                break;
        }
        return url;
    }
    public  String httpClientGetUilt(String url, String token) throws IOException {
        Map<String,String> header = new HashMap<String,String>();
        header.put("PRIVATE-TOKEN", token);
        String responseData = super.getRequest(url, header);
        judgeVisitErroe(responseData);
        return responseData;
    }
    public String httpClientDelete(String url,String token) throws IOException {
        HttpClient client = new HttpClient();
        DeleteMethod delete = new DeleteMethod(url);
        delete.setRequestHeader("PRIVATE-TOKEN", token);
        delete.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        client.executeMethod(delete);
        return "1";
    }
    public JSONObject bodyObject(String ip,String account){
        JSONObject body = new JSONObject();
        //需要得到本机的ip动态的更改ip，为了绑定hook
        body.element("url", "http://10.222.46.28:8070/api/"+account);
        body.element("push_events", "1");
        body.element("tag_push_events", "1");
        body.element("merge_requests_events", "1");
        body.element("repository_update_events", "1");
        body.element("enable_ssl_verification", "1");
        body.element("project_id", "1");
        body.element("issues_events", "1");
        body.element("confidential_issues_events", "1");
        body.element("note_events", "1");
        body.element("confidential_note_events", "1");
        body.element("pipeline_events", "1");
        body.element("wiki_page_events", "1");
        body.element("job_events", "1");
        return body;
    }
    public PostMethod setBody(PostMethod postMethod, String account){
        //需要得到本机的ip动态的更改ip，为了绑定hook
        postMethod.addParameter("url", "http://10.222.46.28:8070/api/"+account);
        postMethod.addParameter("push_events", "1");
        postMethod.addParameter("tag_push_events", "1");
        postMethod.addParameter("merge_requests_events", "1");
        postMethod.addParameter("repository_update_events", "1");
        postMethod.addParameter("enable_ssl_verification", "1");
        postMethod.addParameter("project_id", "1");
        postMethod.addParameter("issues_events", "1");
        postMethod.addParameter("confidential_issues_events", "1");
        postMethod.addParameter("note_events", "1");
        postMethod.addParameter("confidential_note_events", "1");
        postMethod.addParameter("pipeline_events", "1");
        postMethod.addParameter("wiki_page_events", "1");
        postMethod.addParameter("job_events", "1");
        return postMethod;
    }
    public String httpClientPostUilt(String url, String token,String account) throws IOException {
        HashMap<String,String> header = new HashMap();
        header.put("PRIVATE-TOKEN", token);
        header.put("Content-Type", "application/x-www-form-urlencoded");
        header.put("Access-Control-Allow-Origin","*");
        String responseData = postRequest22(url,header,bodyObject("",account));
        judgeVisitErroe(responseData);
        return responseData;
    }
    public String postRequest22(String url,Map<String,String> header,JSONObject bodyObject) throws IOException {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        Iterator it = header.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            post.setRequestHeader(entry.getKey().toString(), entry.getValue().toString());
        }
        post = setBody(post,"admin");
        client.executeMethod(post);
        InputStream in = post.getResponseBodyAsStream();
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
