package com.test.demo.common.httpHelper;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class PlatformHttpClientHelper {
    abstract public String dealUrl();
    abstract public String judgeVisitErroe();
    abstract public String httpClientGetUilt(String url) throws IOException;
    abstract public String httpClientPostUilt(String url) throws IOException;
    public String getRequest(String url, Map<String,String> header) throws IOException {
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        GetMethod get = new GetMethod(url);
        Iterator it = header.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            get.setRequestHeader(entry.getKey().toString(), entry.getValue().toString());
        }
        client.executeMethod(get);
        InputStream in = get.getResponseBodyAsStream();
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(in, "UTF-8");
        char[] b = new char[4096];
        for (int n; (n = isr.read(b)) != -1; ) {
            sb.append(new String(b, 0, n));
        }
        String returnStr = sb.toString();
        return returnStr;
    }
    public String postRequest(String url, Map<String,String> header, JSONObject bodyObject) throws IOException {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        Iterator it = header.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            post.setRequestHeader(entry.getKey().toString(), entry.getValue().toString());
        }
        post.setRequestBody(bodyObject.toString());
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
}
