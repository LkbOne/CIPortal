package com.test.demo.common.httpHelper;

import lombok.Getter;
import lombok.ToString;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;


@Getter
@ToString
@Component
public class StatusHelper {
    private final String SUCCESS = "success";
    private final String STATUS = "status";
    private final String SERVICEERROR ="fail";
    private final String BUG ="bug";
    private final String ACCESS ="access";
    private final String HOSTORURL ="HOSTORIP";
    private final String SENTRYTOKEN ="TOKEN";
    private final String TRELLOTOKEN ="TOKEN";
    private final String TRELLOKEY ="KEY";
    private final String GITTOKEN ="TOKEN";
    private final String FLURRYTOKEN="TOKEN";
    private final String PROJECTS ="projects";
    private final String USER ="user";
    private final String FAIL ="fail";
    private final String StatusCode ="Status Code";
    public JSONObject jsonArray2Success(JSONArray array){
        JSONObject send2Front = new JSONObject();
        send2Front.element(PROJECTS,array);
        send2Front.element(STATUS,SUCCESS);
        send2Front.element(STATUS,200);
        return send2Front;
    }
    public JSONObject boardNameAndBoardData(String name,JSONArray data){
        JSONObject object = new JSONObject();
        object.element("name",name);
        object.element("boardData",data);
        return object;
    }
//    public JSONObject jsonSentry2Success(JSONObject object){
//        JSONObject send2Front = new JSONObject();
//        JSONArray newArray = object.getJSONArray("projects");
//        int flag = 0;
//        if(object.has("status")&&object.getString("status").equals("bug")){
//            object.remove("status");
//            flag=1;
//        }
//        send2Front.element(PROJECTS,newArray);
//        if(flag==1){
//            return addBugStatus(send2Front);
//        }
//        return addSuccessStatus(send2Front);
//    }
    public JSONObject jsonObject2Success(JSONObject object){
        JSONObject send2Front = new JSONObject();
        int flag = 0;
        if(object.has("status")&&object.getString("status").equals("bug")){
            object.remove("status");
            flag=1;
        }
        send2Front.element(PROJECTS,object);
        if(flag==1){
            return addBugStatus(send2Front);
        }
        return addSuccessStatus(send2Front);
    }
    public JSONObject addBugStatus(JSONObject object){
        return object.element(STATUS,BUG).element(StatusCode,200);
    }
    private JSONObject addSuccessStatus(JSONObject object){
        object.element(STATUS,SUCCESS);
        object.element(StatusCode,200);
        return object;
    }
    public JSONObject onlySuccess(){

        return addSuccessStatus(new JSONObject()).element(StatusCode,203);
    }
    public JSONObject user2Success(JSONObject object){
        JSONObject send2Front = new JSONObject();
        send2Front.element(USER,object);
        return addSuccessStatus(send2Front);
    }
    public JSONObject addBugStatusForArray(JSONArray array){
        return addBugStatus(jsonArray2Success(array));
    }
    public JSONObject onlyFail(){
        return addFailStatus(new JSONObject());
    }
    public JSONObject addFailStatus(JSONObject object){
        return object.element(STATUS,FAIL).element(StatusCode,503);
    }

}
