package com.test.demo.controller;

import com.test.demo.common.httpHelper.TrelloPlatformHttpClient;
import com.test.demo.service.TrelloService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.Logger;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
@Api("Frontend直接向Trello请求数据")
public class TrelloController {
    private Logger logger = Logger.getLogger(TrelloController.class);
    @Autowired
    private TrelloService trelloService;
    private TrelloPlatformHttpClient trelloHttpClientHelper = new TrelloPlatformHttpClient();
    @ApiOperation("获取Trello下的所有项目名字与id")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "trelloToken", dataType="String", required = true, value = "Trello的Token",defaultValue="a4f99d894674645f9debb528cbd0ae86bf88d5119eb67e125d48904e2b369983"),
            @ApiImplicitParam(paramType = "path", name = "trelloAppkey", dataType="String", required = true, value = "Trello的Appkey",defaultValue="2f5cba1d75d5e6e2303d17e39f5a9ea6"),
    })
    @RequestMapping(value = "trello", method = RequestMethod.POST)
    JSONObject majorProjectsData(@RequestBody String body) throws IOException, JSONException {
        return trelloService.majorProjectsData(body);
    }
    @RequestMapping(value = "trello/init", method = RequestMethod.POST)
    JSONObject init(@RequestBody String body) throws IOException, JSONException, ParseException {
        return trelloService.initCallApi(body);
    }
}
