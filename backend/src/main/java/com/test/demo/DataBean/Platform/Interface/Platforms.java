package com.test.demo.DataBean.Platform.Interface;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.persistence.Id;

public interface Platforms {
     @Id
//     String id = null;
     String name = null;
     JSONObject setting = new JSONObject();
     JSONArray chosenProjects = new JSONArray();
     JSONArray allProjects = new JSONArray();
     int interval =0;
     JSONObject allSettingParam();
     Platforms setAllPlatformsParams(JSONObject settings);
}
