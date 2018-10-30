package com.test.demo.bean.platform.data;

import net.sf.json.JSONObject;

import java.util.HashMap;

public interface ParamByLifeChosen {
    JSONObject dealWithData();
    HashMap<String,String> dealWithChosenField();
    HashMap<String,String> dealWithChosenFunction();
    JSONObject dealWithFieldData();
    JSONObject dealWithFunctionData();
    JSONObject removeData();
}
