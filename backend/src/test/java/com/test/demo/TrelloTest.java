package com.test.demo;

import com.test.demo.User.dao.UserDaoImp;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloTest {
    private TestRestTemplate template = new TestRestTemplate();
    @Autowired
    UserDaoImp userDaoImp;
    String port = "8070";
    String trelloToken = "a4f99d894674645f9debb528cbd0ae86bf88d5119eb67e125d48904e2b369983";
    String trelloAppkey = "2f5cba1d75d5e6e2303d17e39f5a9ea6";
    String admin="admin010";

//    @Test
//    public void trelloProjectIdTest(){
//       String url = "http://localhost:"+port+"/api/trello/token="+trelloToken+"&key="+trelloAppkey;
//        template.getForObject(url, JSONObject.class);
//    }
//    @Test
//    public void trelloTest() throws IOException, ParseException {
//        User user= userDaoImp.userByAccount(admin);
//        JSONArray trelloPlatformData = trelloPlatform.dataOfChosenProject(user.getChosenTrelloProjects(),user.getAllTrelloProjects(),trelloToken,trelloAppkey);
//    }
}
