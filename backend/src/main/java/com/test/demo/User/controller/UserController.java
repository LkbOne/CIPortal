package com.test.demo.User.controller;

import com.test.demo.User.Users;
import com.test.demo.User.service.UserService;
import com.test.demo.common.httpHelper.StatusHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
@Api("User直接向Backend请求数据")
public class UserController {
    private Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @ApiOperation("登录")
    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public JSONObject login(@RequestBody Users users) throws IOException {
        return userService.usersLogin(users);
    }
    @ApiOperation("获取所有的平台数据")
    @RequestMapping(value = "/datas/", method = RequestMethod.POST)
    public JSONObject platformDataFromDB(@RequestBody String account) throws IOException, JSONException {
        account = account.substring(0, account.length() - 1);
        return userService.datasFromDB(account);
    }
    @ApiOperation("更改设置")
    @RequestMapping(value = "/setting/{account}", method = RequestMethod.POST)
    JSONObject settingForConfig(@RequestBody String projectIdAndSetting, @PathVariable("account") String account) throws IOException, JSONException, ParseException, EncodeException {
      return userService.toSetting(account,projectIdAndSetting);
    }
    @ApiOperation("个性化面板设置")
    @RequestMapping(value = "/individuation/{account}", method = RequestMethod.POST)
    JSONObject individuateForBoardLocation(@RequestBody String boardChoice, @PathVariable("account") String account) throws IOException, JSONException, ParseException, EncodeException {
        JSONArray boardChoiceArray = JSONArray.fromObject(boardChoice);
        return userService.individuate(account,boardChoiceArray);
    }
    @ApiOperation("新建子用户")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    JSONObject subUsers(@RequestBody Users users) throws IOException, JSONException, ParseException, EncodeException {
        return userService.createSubAccount(users);
    }
    @RequestMapping(value = "/boardChoice", method = RequestMethod.GET)
    JSONObject initComfirmBoardChoice(){
        return userService.canCallPlatform();
    }
    @RequestMapping(value = "/boardChoice", method = RequestMethod.POST)
    JSONObject initSureBoardChoice(@RequestBody String boardChoice){
        return userService.selectBoard2BoardChoice(JSONArray.fromObject(boardChoice));
    }
    @RequestMapping(value = "/engine", method = RequestMethod.POST)
    JSONObject engine(){
        return userService.changeEngineStatus();
    }
    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    JSONObject showForSonar(@RequestBody Users user) throws IOException, JSONException, ParseException {
        return userService.changePassword(user);
    }
}
