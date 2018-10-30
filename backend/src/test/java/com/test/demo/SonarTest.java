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
public class SonarTest {
    private TestRestTemplate template = new TestRestTemplate();
    @Autowired
    UserDaoImp userDaoImp;
    String port = "8070";
    String sonarHost = "9000";
    String admin = "admin010";
    @Test
    public void sonarProjectIdTest(){
        String url = "http://localhost:"+port+"/api/sonar/sonarHost="+sonarHost;
        JSONObject result = template.getForObject(url, JSONObject.class);
    }

}
