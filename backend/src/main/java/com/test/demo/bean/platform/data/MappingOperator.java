package com.test.demo.bean.platform.data;

import net.sf.json.JSONArray;

import java.util.HashMap;

public interface MappingOperator {
    HashMap dealWithOneForManyMapping(JSONArray gitlabSentryMapping);
}
