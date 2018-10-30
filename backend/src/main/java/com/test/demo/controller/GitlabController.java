package com.test.demo.controller;

import com.test.demo.common.httpHelper.StatusHelper;

import com.test.demo.service.GitlabService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
@Controller
@Api("Frontend直接向gitlab请求数据")
public class GitlabController {
    private Logger logger = Logger.getLogger(GitlabController.class);
    @Autowired
    private GitlabService gitlabService;
    private StatusHelper statusHelper = new StatusHelper();
    @RequestMapping(value = "/{account}", method = RequestMethod.POST)
    String postSendMessageToUser(@RequestBody String data,@PathVariable("account") String account) throws IOException, ParseException, EncodeException {
        //logger.info("account:"+account);
        //测试gitlab——hook
        logger.info("account:_____hooks"+data);

        gitlabService.sendAndComfirm(account, data);
        return "success";
    }
    @ApiOperation("获取gitlab下的所有项目名字与id")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path",name="gitIp",dataType="String",required=true,value="git的ip地址",defaultValue="146.222.94.208"),
            @ApiImplicitParam(paramType="path",name="gitPrivateToken",dataType="String",required=true,value="git的token",defaultValue="bSSzMHFP7fB5gpGUM27w")
    })
    //job/gitIp={gitIp}&gitPrivateToken={gitPrivateToken}
    @RequestMapping(value = "gitLab", method = RequestMethod.POST)
    JSONObject majorProjectsData(@RequestBody String body) throws IOException {
        return gitlabService.majorProjectsData(body);
    }
    @RequestMapping(value = "gitLab/init", method = RequestMethod.POST)
    JSONObject init(@RequestBody String body) throws IOException, ParseException {
        logger.info("body:"+body);
        return gitlabService.initCallApi(body);
    }
}
