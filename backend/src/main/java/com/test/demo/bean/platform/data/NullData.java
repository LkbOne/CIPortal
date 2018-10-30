package com.test.demo.bean.platform.data;

import com.test.demo.DataBean.Platform.Interface.Platforms;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

public class NullData extends Data{

    @Override
    public boolean getPlatformFromDb() {
        return false;
    }
    @Override
    public boolean calcTimeForInterval(int now) {

        return false;
    }

    @Override
    public JSONArray comfirmBaseAuthorityAndGetCheckBoxData(String token) throws IOException {
        return new JSONArray();
    }

    @Override
    public int getInterval() {
        return 0;
    }

    @Override
    public boolean setInterval(int interval) {
        return false;
    }

    @Override
    public String dataOfAllProject(String code) throws IOException {
        return "";
    }

    @Override
    public JSONArray changeParams(JSONObject setting) throws IOException, ParseException {
        return new JSONArray();
    }

    @Override
    public JSONObject callDataAndSave2DataBean(String name) throws IOException, ParseException {
        return statusHelper.onlyFail();
    }

    @Override
    public JSONObject socketGate(String name) throws IOException, ParseException {
        return new JSONObject();
    }

    @Override
    public JSONArray dataOfChosenProject(Platforms platforms) throws IOException, ParseException {
        return new JSONArray();
    }
}
