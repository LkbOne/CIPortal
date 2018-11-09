package com.test.demo.controller;

import com.test.demo.common.httpHelper.SentryPlatformHttpClientHelper;
import com.test.demo.common.socket.WebSockets;
import com.test.demo.service.SentryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
//import org.json.JSONArray;

//import org.json.JSONObject;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.text.ParseException;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
@Api("Frontend直接向Sentry请求数据")

public class SentryController {
    private SentryPlatformHttpClientHelper sentryHttpClientHelper = new SentryPlatformHttpClientHelper();
    @Autowired
    private SentryService sentryService;
    private Logger logger = Logger.getLogger(SentryController.class);
    @ApiOperation("获取Sentry下的所有项目名字与id")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "sentryToken", dataType = "String", required = true, value = "Sentry的Token", defaultValue = "d49f8f5f4b274144a664d2ab120e76bf40a9e604a37341bba9b78be431cbd9bf"),
    })
    @RequestMapping(value = "sentry", method = RequestMethod.POST)
    JSONObject majorProjectsData(@RequestBody String body) throws IOException {
        return sentryService.majorProjectsData(body);
    }
    @RequestMapping(value = "sentry/init", method = RequestMethod.POST)
    JSONObject init(@RequestBody String body) throws IOException, ParseException {
        return sentryService.initCallApi(body);
    }
    @RequestMapping(value = "sentry/dispel", method = RequestMethod.POST)
    void dispelBug() throws IOException {
        JSONObject bugsObject = sentryService.dispelBug();
        bugsObject.element("name", "Sentry");
        WebSockets.sendMessageAll(bugsObject.toString());
    }
}
