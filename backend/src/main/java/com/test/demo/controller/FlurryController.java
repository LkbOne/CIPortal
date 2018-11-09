package com.test.demo.controller;

import com.test.demo.service.FlurryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
@Controller
@Api("Frontend直接向Flurry请求数据")
public class FlurryController {
    @Autowired
    FlurryService flurryService;
    @ApiOperation("获取flurry下的所有项目名字与id")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "flurryToken", dataType = "String", required = true, value = "flurry的token", defaultValue = "eyJhbGciOiJIUzI1NiIsImtpZCI6ImZsdXJyeS56dXVsLnByb2Qua2V5c3RvcmUua2V5LjIifQ.eyJpc3MiOiJodHRwczovL3p1dWwuZmx1cnJ5LmNvbTo0NDMvdG9rZW4iLCJpYXQiOjE1MzY4MjI1NDgsImV4cCI6MzMwOTM3MzEzNDgsInN1YiI6IjQyODA4NCIsImF1ZCI6IjQiLCJ0eXBlIjo0LCJqdGkiOiI2NTUzIn0.gRcCgdhXZPApIgZrKLF1tary_jlpyG_CP5qBPanFOlA"),
    })
    @RequestMapping(value = "flurry", method = RequestMethod.POST)
    //这里需要abby做更改
    JSONObject majorProjectsData(@RequestBody String body) throws IOException {
        return flurryService.majorProjectsData(body);
    }
    @RequestMapping(value = "flurry/init", method = RequestMethod.POST)
    JSONObject init(@RequestBody String body) throws IOException, ParseException {
        return flurryService.initCallApi(body);
    }
}
