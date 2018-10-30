package com.test.demo;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitTest {
    private TestRestTemplate template = new TestRestTemplate();
    String port = "8070";
    String gitIp = "146.222.94.208";
    String gitPrivateToken = "bSSzMHFP7fB5gpGUM27w";
    @Test
    public void gitProjectIdTest(){
        String url = "http://localhost:"+port+"/api/job/gitIp="+gitIp+"&gitPrivateToken="+gitPrivateToken;
        JSONObject result = template.getForObject(url, JSONObject.class);
    }
//    @Test
//    public void mergeRequestOfEmptyTest() throws IOException, ParseException {
//        JSONArray jsonArray=gitPlatform.dataOfChosenProject2MergeRequest(new JSONArray(),gitIp,gitPrivateToken);
//        JSONArray array = JSONArray.fromObject("[]");
//        Assert.assertEquals(array,jsonArray);
//    }
//    @Test
//    public void mergeRequestTest() throws IOException, ParseException {
//        JSONArray jsonArray= gitPlatform.dataOfChosenProject2MergeRequest(JSONArray.fromObject("[2]"),gitIp,gitPrivateToken);
//        JSONArray array = JSONArray.fromObject("[{\"targetBranch\":\"master\",\"sourceBranch\":\"dev\",\"projectImage\":\"http://146.222.94.208/uploads/-/system/project/avatar/2/全屏.png\",\"authorName\":\"hugh\",\"authorImage\":\"https://www.gravatar.com/avatar/a6845eebf1348a3102ee05c543992929?s=80&d=identicon\",\"mergeRequestId\":\"5\",\"projectName\":\"IPT-IPCC\",\"url\":\"http://146.222.94.208/root/IPT-IPCC/merge_requests/2\",\"updatedAt\":\"2018-08-23 12:00\",\"timeFlag\":false,\"mergeStatus\":\"true\"},{\"targetBranch\":\"master\",\"sourceBranch\":\"feature/ci-test\",\"projectImage\":\"http://146.222.94.208/uploads/-/system/project/avatar/2/全屏.png\",\"authorName\":\"hugh\",\"authorImage\":\"https://www.gravatar.com/avatar/a6845eebf1348a3102ee05c543992929?s=80&d=identicon\",\"mergeRequestId\":\"18\",\"projectName\":\"IPT-IPCC\",\"url\":\"http://146.222.94.208/root/IPT-IPCC/merge_requests/8\",\"updatedAt\":\"2018-08-23 09:00\",\"timeFlag\":false,\"mergeStatus\":\"true\"},{\"targetBranch\":\"dev\",\"sourceBranch\":\"feature/ci-test\",\"projectImage\":\"http://146.222.94.208/uploads/-/system/project/avatar/2/全屏.png\",\"authorName\":\"hugh\",\"authorImage\":\"https://www.gravatar.com/avatar/a6845eebf1348a3102ee05c543992929?s=80&d=identicon\",\"mergeRequestId\":\"16\",\"projectName\":\"IPT-IPCC\",\"url\":\"http://146.222.94.208/root/IPT-IPCC/merge_requests/6\",\"updatedAt\":\"2018-08-23 09:00\",\"timeFlag\":false,\"mergeStatus\":\"true\"},{\"targetBranch\":\"dev\",\"sourceBranch\":\"deleteTemplate\",\"projectImage\":\"http://146.222.94.208/uploads/-/system/project/avatar/2/全屏.png\",\"authorName\":\"hugh\",\"authorImage\":\"https://www.gravatar.com/avatar/a6845eebf1348a3102ee05c543992929?s=80&d=identicon\",\"mergeRequestId\":\"19\",\"projectName\":\"IPT-IPCC\",\"url\":\"http://146.222.94.208/root/IPT-IPCC/merge_requests/9\",\"updatedAt\":\"2018-08-23 07:29\",\"timeFlag\":false,\"mergeStatus\":\"false\"},{\"targetBranch\":\"deleteTemplate\",\"sourceBranch\":\"master\",\"projectImage\":\"http://146.222.94.208/uploads/-/system/project/avatar/2/全屏.png\",\"authorName\":\"hugh\",\"authorImage\":\"https://www.gravatar.com/avatar/a6845eebf1348a3102ee05c543992929?s=80&d=identicon\",\"mergeRequestId\":\"7\",\"projectName\":\"IPT-IPCC\",\"url\":\"http://146.222.94.208/root/IPT-IPCC/merge_requests/4\",\"updatedAt\":\"2018-08-11 17:00\",\"timeFlag\":false,\"mergeStatus\":\"false\"},{\"targetBranch\":\"feature/ci-test\",\"sourceBranch\":\"deleteTemplate\",\"projectImage\":\"http://146.222.94.208/uploads/-/system/project/avatar/2/全屏.png\",\"authorName\":\"hugh\",\"authorImage\":\"https://www.gravatar.com/avatar/a6845eebf1348a3102ee05c543992929?s=80&d=identicon\",\"mergeRequestId\":\"17\",\"projectName\":\"IPT-IPCC\",\"url\":\"http://146.222.94.208/root/IPT-IPCC/merge_requests/7\",\"updatedAt\":\"2018-08-08 10:52\",\"timeFlag\":false,\"mergeStatus\":\"false\"},{\"targetBranch\":\"master\",\"sourceBranch\":\"feature/ci-test\",\"projectImage\":\"http://146.222.94.208/uploads/-/system/project/avatar/2/全屏.png\",\"authorName\":\"hugh\",\"authorImage\":\"https://www.gravatar.com/avatar/a6845eebf1348a3102ee05c543992929?s=80&d=identicon\",\"mergeRequestId\":\"15\",\"projectName\":\"IPT-IPCC\",\"url\":\"http://146.222.94.208/root/IPT-IPCC/merge_requests/5\",\"updatedAt\":\"2018-08-08 10:04\",\"timeFlag\":false,\"mergeStatus\":\"true\"},{\"targetBranch\":\"master\",\"sourceBranch\":\"deleteTemplate\",\"projectImage\":\"http://146.222.94.208/uploads/-/system/project/avatar/2/全屏.png\",\"authorName\":\"hugh\",\"authorImage\":\"https://www.gravatar.com/avatar/a6845eebf1348a3102ee05c543992929?s=80&d=identicon\",\"mergeRequestId\":\"6\",\"projectName\":\"IPT-IPCC\",\"url\":\"http://146.222.94.208/root/IPT-IPCC/merge_requests/3\",\"updatedAt\":\"2018-07-20 19:12\",\"timeFlag\":false,\"mergeStatus\":\"true\"},{\"targetBranch\":\"master\",\"sourceBranch\":\"test\",\"projectImage\":\"http://146.222.94.208/uploads/-/system/project/avatar/2/全屏.png\",\"authorName\":\"barry\",\"authorImage\":\"https://www.gravatar.com/avatar/bd57dd82dd51ee4f5664af987042ad25?s=80&d=identicon\",\"mergeRequestId\":\"1\",\"projectName\":\"IPT-IPCC\",\"url\":\"http://146.222.94.208/root/IPT-IPCC/merge_requests/1\",\"updatedAt\":\"2018-06-03 17:25\",\"timeFlag\":false,\"mergeStatus\":\"true\"}]");
//        Assert.assertEquals(array,jsonArray);
//    }
//    @Test
//    public void CIDatatOfEmptyTest() throws IOException, ParseException {
//        JSONArray jsonArray=gitPlatform.dataOfChosenProject2CIData(JSONArray.fromObject("[]"),1,gitIp,gitPrivateToken);
//        JSONArray array = JSONArray.fromObject("[]");
//        Assert.assertEquals(array,jsonArray);
//    }
//    @Test
//    public void CIDatatTest() throws IOException, ParseException {
//        JSONArray jsonArray=gitPlatform.dataOfChosenProject2CIData(JSONArray.fromObject("[1]"),10,gitIp,gitPrivateToken);
//        JSONArray array = JSONArray.fromObject("[{\"authorName\":\"root\",\"authorImage\":\"https://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=80&d=identicon\",\"projectName\":\"CI-test\",\"branch\":\"dev\",\"url\":\"http://146.222.94.208/root/CI-test/pipelines/3\",\"pipelineId\":\"3\",\"updatedAt\":\"2018-07-21 21:26\",\"status\":\"other\"},{\"authorName\":\"root\",\"authorImage\":\"https://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=80&d=identicon\",\"projectName\":\"CI-test\",\"branch\":\"master\",\"url\":\"http://146.222.94.208/root/CI-test/pipelines/2\",\"pipelineId\":\"2\",\"updatedAt\":\"2018-05-29 16:59\",\"status\":\"other\"},{\"authorName\":\"root\",\"authorImage\":\"https://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=80&d=identicon\",\"projectName\":\"CI-test\",\"branch\":\"dev\",\"url\":\"http://146.222.94.208/root/CI-test/pipelines/1\",\"pipelineId\":\"1\",\"updatedAt\":\"2018-05-29 16:59\",\"status\":\"other\"}]");
//        Assert.assertEquals(array,jsonArray);
//    }
}

