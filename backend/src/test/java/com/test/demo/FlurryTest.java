package com.test.demo;

import com.test.demo.User.dao.UserDaoImp;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlurryTest {
    private TestRestTemplate template = new TestRestTemplate();
    @Autowired
    UserDaoImp userDaoImp;
    String port = "8070";
    String flurryToken = "eyJhbGciOiJIUzI1NiIsImtpZCI6ImZsdXJyeS56dXVsLnByb2Qua2V5c3RvcmUua2V5LjIifQ.eyJpc3MiOiJodHRwczovL3p1dWwuZmx1cnJ5LmNvbTo0NDMvdG9rZW4iLCJpYXQiOjE1MzY4MjI1NDgsImV4cCI6MzMwOTM3MzEzNDgsInN1YiI6IjQyODA4NCIsImF1ZCI6IjQiLCJ0eXBlIjo0LCJqdGkiOiI2NTUzIn0.gRcCgdhXZPApIgZrKLF1tary_jlpyG_CP5qBPanFOlA";
    @Test
    public void sonarProjectIdTest(){
        String url = "http://localhost:"+port+"/api/flurry";
        JSONObject result = template.postForObject(url, flurryToken,JSONObject.class);
        System.out.println("result:"+result);
        Assert.assertEquals(result.get("status"), "success");
    }
}
