package com.test.demo.controller;

import com.test.demo.common.httpHelper.SonarPlatformHttpClientHelper;
import com.test.demo.service.SonarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
@Api("Frontend直接向Sonar请求数据")
public class SonarController {
    private Logger logger = Logger.getLogger(SonarController.class);
    SonarPlatformHttpClientHelper sonarHttpClientHelper = new SonarPlatformHttpClientHelper();
    @Autowired
    SonarService sonarService;
    @ApiOperation("获取Sonar下的所有项目名字与id和key")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path",name="sonarHost",dataType="String",required=true,value="Sonar的Host",defaultValue="9000"),
    })
    @RequestMapping(value = "sonar", method = RequestMethod.POST)
    JSONObject majorProjectsData(@RequestBody String body) throws IOException, JSONException {
        return sonarService.majorProjectsData(body);
    }
    @RequestMapping(value = "sonar/init", method = RequestMethod.POST)
    JSONObject init(@RequestBody String body) throws IOException, JSONException, ParseException {
        return sonarService.initCallApi(body);
    }
}
