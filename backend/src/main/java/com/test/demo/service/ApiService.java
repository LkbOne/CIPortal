package com.test.demo.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;

public abstract class ApiService {
    Logger logger = Logger.getLogger(ApiService.class);
    String adminAcount = "admin";
    int initInterval = 5;
    JSONArray getIdArray(JSONArray allProject){
        JSONArray array = new JSONArray();
        for(int i = 0; i < allProject.size(); i++){
            array.element(allProject.getJSONObject(i).getString("id"));
        }
        return array;
    }
    abstract public JSONObject initCallApi(String body) throws IOException, ParseException;
}
