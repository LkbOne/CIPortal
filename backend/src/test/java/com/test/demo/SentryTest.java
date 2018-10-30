package com.test.demo;

import com.test.demo.User.dao.UserDaoImp;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SentryTest {
    @Autowired
    UserDaoImp userDaoImp;
    private TestRestTemplate template = new TestRestTemplate();
    String admin="admin010";
    String port = "8070";
    String sentryToken = "d49f8f5f4b274144a664d2ab120e76bf40a9e604a37341bba9b78be431cbd9bf";
    @Test
    public void sentryProjectIdTest(){
        String url = "http://localhost:"+port+"/api/sentry/sentryToken="+sentryToken;
        JSONObject result = template.getForObject(url, JSONObject.class);
    }
//    @Test
//    public void sentryTest() throws IOException, ParseException {
//        User user= userDaoImp.userByAccount(admin);
//        JSONObject sentryData = sentryPlatform.dataOfChosenProject(user.getChosenSentryProjects(),user.getSentryToken());
//    }
}
