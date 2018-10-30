package com.test.demo;

import com.test.demo.bean.User;
import net.sf.json.JSONObject;
import org.junit.Assert;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    private TestRestTemplate template = new TestRestTemplate();
    private String admin = "admin010";
    private String password = "123456";
    private String port = "8070";
    @Test
    public void loginTest() {
        String url = "http://localhost:" + port + "/user/authorization";
        User user = new User();
        user.setAccount(admin);
        user.setPassword(password);
        JSONObject result = template.postForObject(url, user, JSONObject.class);
        Assert.assertEquals(result.get("status"), "success");
    }
//    @Test
//    public void registerTest(){
//        String url = "http://localhost:"+port+"/user/info";
//        User user = new User();
//        user.setAccount("admin111");
//        user.setPassword(password);
//        JSONObject result = template.postForObject(url,user,JSONObject.class);
//        Assert.assertEquals(result.get("status"), "success");
//    }
//    @Test
//    public void platformDataFromDBTest(){
//        String url = "http://localhost:"+port+"/user/authorization/";
//        JSONObject result = template.postForObject(url,"admin010=",JSONObject.class);
//        Assert.assertEquals(result.get("status"), "success");
//    }
//    @Test
//    public void gitSettingTest(){
//        String url = "http://localhost:"+port+"/user/setting/"+admin;
//        String gitObject= "{\"gitIp\":\"146.222.94.208\",\"gitInterval\":\"5\",\"gitPrivateToken\":\"bSSzMHFP7fB5gpGUM27w\",\"account\":\"\",\"chosenGitProjects\":[\"10\",\"8\",\"7\",\"5\",\"9\",\"6\",\"4\",\"3\",\"2\",\"1\"],\"allGitProjects\":[{\"id\":\"10\",\"projectName\":\"notify_ios_final\"},{\"id\":\"9\",\"projectName\":\"notify_ios\"},{\"id\":\"8\",\"projectName\":\"bigs_ios\"},{\"id\":\"7\",\"projectName\":\"merge-request-test2\"},{\"id\":\"6\",\"projectName\":\"merge-request-test\"},{\"id\":\"5\",\"projectName\":\"BoxSnapPlusPlus-Android\"},{\"id\":\"4\",\"projectName\":\"git-flow-test\"},{\"id\":\"3\",\"projectName\":\"MBC-CI-Portal\"},{\"id\":\"2\",\"projectName\":\"IPT-IPCC\"},{\"id\":\"1\",\"projectName\":\"CI-test\"}],\"choice\":1}";
//        JSONObject result = template.postForObject(url, gitObject,JSONObject.class);
//        Assert.assertEquals(result.get("status"), "success");
//    }
//    @Test
//    public void sentrySettingTest(){
//        String url = "http://localhost:"+port+"/user/setting/"+admin;
//        String sentryObject= "{\"sentryInterval\":\"5\",\"sentryToken\":\"d49f8f5f4b274144a664d2ab120e76bf40a9e604a37341bba9b78be431cbd9bf\",\"chosenSentryProjects\":[\"1197222\",\"985996\",\"269227\",\"269226\",\"269224\",\"249981\",\"240948\",\"240330\"],\"account\":\"\",\"allSentryProjects\":[{\"id\":\"1197222\",\"projectName\":\"Vessel-IT\"},{\"id\":\"985996\",\"projectName\":\"dev-debug\"},{\"id\":\"269227\",\"projectName\":\"OOCLite-New-Android\"},{\"id\":\"269226\",\"projectName\":\"OOCLite-New-ApiCall\"},{\"id\":\"269224\",\"projectName\":\"OOCLite-New-iPhone\"},{\"id\":\"249981\",\"projectName\":\"BoxSnap iOS\"},{\"id\":\"240948\",\"projectName\":\"Big Schedules Android\"},{\"id\":\"240330\",\"projectName\":\"OOCL Lite iOS\"}],\"sentryTrelloMapping\":[{\"trello\":\"5afa391b1dd3441b04a99308\",\"sentry\":[{\"id\":\"269224\"}]},{\"trello\":\"5af8e74835813a39caa9d3af\",\"sentry\":[{\"id\":\"269226\"},{\"id\":\"240948\"}]},{\"trello\":\"5af8e6a0957b1fca6b9f5726\",\"sentry\":[{\"id\":\"269227\"},{\"id\":\"249981\"}]},{\"trello\":\"5afa37bdd015619c742896c8\",\"sentry\":[{\"id\":\"985996\"},{\"id\":\"1197222\"}]}],\"choice\":4}";
//        JSONObject result = template.postForObject(url, sentryObject,JSONObject.class);
//        Assert.assertEquals(result.get("status"), "success");
//    }
//    @Test
//    public void sonarSettingTest(){
//        String url = "http://localhost:"+port+"/user/setting/"+admin;
//        String sonarObject= "{\"sonarHost\":\"9000\",\"chosenSonarProjects\":[\"AWSibGAq5CISj_uvBMFn\",\"AWUTZrvjIUl3HHiFM4Dq\",\"AWRblmF2fonFimP8cQdw\",\"AWR-EsBIfonFimP8cQkx\",\"AWSxjFRO5CISj_uvBMJN\",\"AWVAt4CQaAYzvXyZmo_j\",\"AWUTeOfMIUl3HHiFM4Dy\",\"AWS3R9-W5CISj_uvBMKs\",\"AWSTBqdl5CISj_uvBMDK\",\"AWTQcK1RsKHV06yCKVXO\",\"AWSnsq1A5CISj_uvBMHJ\",\"AWUSZg6qHT2L9HU84Tgi\",\"AWRZnQTwfonFimP8cQcK\",\"AWRZmsAifonFimP8cQcD\",\"AWRaZXucfonFimP8cQdd\",\"AWRZ4pz5fonFimP8cQc_\",\"AWRZwHesfonFimP8cQci\",\"AWRZweD_fonFimP8cQcp\"],\"allSonarProjects\":[{\"id\":\"AWSibGAq5CISj_uvBMFn\",\"key\":\"ir4.bcr\",\"projectName\":\"IRIS4_BCR\"},{\"id\":\"AWUTZrvjIUl3HHiFM4Dq\",\"key\":\"ir4.cmn\",\"projectName\":\"IRIS4_CMN\"},{\"id\":\"AWRblmF2fonFimP8cQdw\",\"key\":\"com.oocl.cmtx\",\"projectName\":\"IRIS4_CMTX\"},{\"id\":\"AWR-EsBIfonFimP8cQkx\",\"key\":\"ir4.cus\",\"projectName\":\"IRIS4_CUS\"},{\"id\":\"AWSxjFRO5CISj_uvBMJN\",\"key\":\"com.oocl.gsp\",\"projectName\":\"IRIS4_GSP\"},{\"id\":\"AWVAt4CQaAYzvXyZmo_j\",\"key\":\"IRIS4_GSP_COSCON_MVP\",\"projectName\":\"IRIS4_GSP_COSCON_MVP\"},{\"id\":\"AWUTeOfMIUl3HHiFM4Dy\",\"key\":\"ir4.home\",\"projectName\":\"IRIS4_HOME\"},{\"id\":\"AWS3R9-W5CISj_uvBMKs\",\"key\":\"ir4.its\",\"projectName\":\"IRIS4_ITS_MC\"},{\"id\":\"AWSTBqdl5CISj_uvBMDK\",\"key\":\"oocl.qm.ir4.bcr.IRIS4_BCR\",\"projectName\":\"oocl_iris4_bcr_qm\"},{\"id\":\"AWTQcK1RsKHV06yCKVXO\",\"key\":\"oocl.ir4.cus\",\"projectName\":\"OOCL_IRIS4_CUS\"},{\"id\":\"AWSnsq1A5CISj_uvBMHJ\",\"key\":\"oocl.qm.ir4.shp.IRIS4_SHP\",\"projectName\":\"oocl_iris4_shp_qm\"},{\"id\":\"AWUSZg6qHT2L9HU84Tgi\",\"key\":\"oocl.ir4.shp.wls_dom_shp\",\"projectName\":\"OOCL_WLS_DOM_SHP\"},{\"id\":\"AWRZmsAifonFimP8cQcD\",\"key\":\"oocl.ir4.shp.wls_prs_bkg\",\"projectName\":\"OOCL_WLS_PRS_BKG\"},{\"id\":\"AWRZnQTwfonFimP8cQcK\",\"key\":\"oocl.ir4.shp.wls_prs_shv\",\"projectName\":\"OOCL_WLS_PRS_SHV\"},{\"id\":\"AWRaZXucfonFimP8cQdd\",\"key\":\"ir4.eqm.wls_dom_eqm\",\"projectName\":\"WLS_DOM_EQM\"},{\"id\":\"AWRZ4pz5fonFimP8cQc_\",\"key\":\"ir4.shp.wls_dom_shp\",\"projectName\":\"WLS_DOM_SHP\"},{\"id\":\"AWRZwHesfonFimP8cQci\",\"key\":\"ir4.shp.wls_prs_bkg\",\"projectName\":\"WLS_PRS_BKG\"},{\"id\":\"AWRZweD_fonFimP8cQcp\",\"key\":\"ir4.shp.wls_prs_shv\",\"projectName\":\"WLS_PRS_SHV\"}],\"sonarInterval\":\"5\",\"allTrelloProjects\":[{\"id\":\"5afa37bdd015619c742896c8\",\"projectName\":\"CI/CD Setup\",\"url\":\"https://trello.com/b/4ygxvGvp/ci-cd-setup\"},{\"id\":\"5af8e6a0957b1fca6b9f5726\",\"projectName\":\"Container Inspection\",\"url\":\"https://trello.com/b/N0l5Ithw/container-inspection\"},{\"id\":\"5af8e74835813a39caa9d3af\",\"projectName\":\"MBC CI Portal\",\"url\":\"https://trello.com/b/u19kCJNY/mbc-ci-portal\"},{\"id\":\"5afa391b1dd3441b04a99308\",\"projectName\":\"Mobile Knowledge Sharing\",\"url\":\"https://trello.com/b/Ug8f009o/mobile-knowledge-sharing\"}],\"chosenTrelloProjects\":[\"5afa37bdd015619c742896c8\",\"5af8e6a0957b1fca6b9f5726\",\"5af8e74835813a39caa9d3af\",\"5afa391b1dd3441b04a99308\"],\"sonarTrelloMapping\":[{\"trello\":\"5afa37bdd015619c742896c8\",\"sonar\":[{\"id\":\"AWSibGAq5CISj_uvBMFn\"},{\"id\":\"AWSxjFRO5CISj_uvBMJN\"}]},{\"trello\":\"5af8e6a0957b1fca6b9f5726\",\"sonar\":[{\"id\":\"AWUTZrvjIUl3HHiFM4Dq\"},{\"id\":\"AWUTeOfMIUl3HHiFM4Dy\"}]},{\"trello\":\"5af8e74835813a39caa9d3af\",\"sonar\":[{\"id\":\"AWR-EsBIfonFimP8cQkx\"},{\"id\":\"AWS3R9-W5CISj_uvBMKs\"}]},{\"trello\":\"5afa391b1dd3441b04a99308\",\"sonar\":[{\"id\":\"AWVAt4CQaAYzvXyZmo_j\"},{\"id\":\"AWSTBqdl5CISj_uvBMDK\"}]}],\"choice\":8}\n";
//        JSONObject result = template.postForObject(url, sonarObject,JSONObject.class);
//        Assert.assertEquals(result.get("status"), "success");
//    }
//    @Test
//    public void trelloSettingTest(){
//        String url = "http://localhost:"+port+"/user/setting/"+admin;
//        String trelloObject= "{\"trelloToken\":\"a4f99d894674645f9debb528cbd0ae86bf88d5119eb67e125d48904e2b369983\",\"trelloAppkey\":\"2f5cba1d75d5e6e2303d17e39f5a9ea6\",\"allTrelloProjects\":[{\"id\":\"5afa37bdd015619c742896c8\",\"projectName\":\"CI/CD Setup\",\"url\":\"https://trello.com/b/4ygxvGvp/ci-cd-setup\"},{\"id\":\"5af8e6a0957b1fca6b9f5726\",\"projectName\":\"Container Inspection\",\"url\":\"https://trello.com/b/N0l5Ithw/container-inspection\"},{\"id\":\"5af8e74835813a39caa9d3af\",\"projectName\":\"MBC CI Portal\",\"url\":\"https://trello.com/b/u19kCJNY/mbc-ci-portal\"},{\"id\":\"5afa391b1dd3441b04a99308\",\"projectName\":\"Mobile Knowledge Sharing\",\"url\":\"https://trello.com/b/Ug8f009o/mobile-knowledge-sharing\"}],\"account\":\"\",\"chosenTrelloProjects\":[\"5afa37bdd015619c742896c8\",\"5af8e6a0957b1fca6b9f5726\",\"5af8e74835813a39caa9d3af\",\"5afa391b1dd3441b04a99308\"],\"choice\":2,\"trelloInterval\":\"5\"}";
//        JSONObject result = template.postForObject(url, trelloObject,JSONObject.class);
//        Assert.assertEquals(result.get("status"), "success");
//    }
//
//    @Test
//    public void individualTest() {
//        String url = "http://localhost:" + port + "/user/individuation/" + admin;
//        String individuationObject = "[{\"x\":0,\"y\":0,\"w\":6,\"h\":12,\"i\":\"Sonar\",\"moved\":false},{\"x\":6,\"y\":0,\"w\":6,\"h\":12,\"i\":\"Sentry\",\"moved\":false},{\"x\":0,\"y\":12,\"w\":6,\"h\":12,\"i\":\"Trello\",\"moved\":false},{\"x\":6,\"y\":12,\"w\":6,\"h\":12,\"i\":\"CI Testing\",\"moved\":false}]\n";
//        JSONObject result = template.postForObject(url, individuationObject, JSONObject.class);
//        Assert.assertEquals(result.get("status"), "success");
//    }
//
//    @Test
//    public void newSettingTest(){
//        String url = "http://localhost:"+port+"/user/setting/"+admin;
//
//
//        String trelloObject= "{\"interval\":5,\"privateToken\":\"d49f8f5f4b274144a664d2ab120e76bf40a9e604a37341bba9b78be431cbd9bf\",\"chosenProjects\":[\"1279206\",\"1197222\",\"985996\"],\"name\":\"Sentry\",\"allProjects\":[{\"id\":\"1279206\",\"projectName\":\"ForeSea-Hybird\"},{\"id\":\"1197222\",\"projectName\":\"Vessel-IT\"},{\"id\":\"985996\",\"projectName\":\"dev-debug\"},{\"id\":\"269227\",\"projectName\":\"OOCLite-New-Android\"},{\"id\":\"269226\",\"projectName\":\"OOCLite-New-ApiCall\"},{\"id\":\"269224\",\"projectName\":\"OOCLite-New-iPhone\"},{\"id\":\"249981\",\"projectName\":\"BoxSnap iOS\"},{\"id\":\"240948\",\"projectName\":\"Big Schedules Android\"},{\"id\":\"240330\",\"projectName\":\"OOCL Lite iOS\"}],\"sentryTrelloMapping\":[]}";
//        JSONObject result = template.postForObject(url, trelloObject,JSONObject.class);
//        Assert.assertEquals(result.get("status"), "success");
//    }

}
