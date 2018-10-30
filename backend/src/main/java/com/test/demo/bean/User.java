package com.test.demo.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import javax.persistence.Id;

@Setter
@Getter
@ToString
public class User {
    @Id
    private String id;
    private String account;
    private String password;
    private String gitIp;
    private String gitPrivateToken;
    private String trelloToken;
    private String trelloAppkey;
    private String sentryToken;
    private String sonarHost;
    private String flurryPrivateToken;
    //
    private JSONObject choiceSonarShow2Trello = new JSONObject();
    //
    private int showNumber =20;
    private JSONArray boardChoice=new JSONArray();
//---
    private JSONArray chosenSentryProjects = new JSONArray();
    private JSONArray allSentryProjects = new JSONArray();
    private JSONArray sortSentryProjects = new JSONArray();

    private JSONArray chosenGitProjects = new JSONArray();
    private JSONArray allGitProjects = new JSONArray();

    private JSONArray chosenSonarProjects = new JSONArray();
    private JSONArray allSonarProjects = new JSONArray();

    private JSONArray chosenTrelloProjects = new JSONArray();
    private JSONArray allTrelloProjects = new JSONArray();

    private JSONArray chosenFlurryProjects = new JSONArray();
    private JSONArray allFlurryProjects = new JSONArray();
//---
    private JSONArray mergeRequestData = new JSONArray();
    private JSONArray CIData = new JSONArray();
    private JSONArray gitHooks = new JSONArray();

    private JSONArray sonarPlatflomData =new JSONArray();
    private JSONArray trelloPlatflomData =new JSONArray();
    private JSONArray sentryPlatflomData = new JSONArray();
    private JSONArray flurryOfEventLogData = new JSONArray();
//---
    private JSONObject allData=new JSONObject();
    private String lastIssue="";
    private JSONArray sentryTrelloMapping = new JSONArray();
    private JSONArray sonarTrelloMapping = new JSONArray();

    private String gitInterval="5";
    private String trelloInterval="5";
    private String sentryInterval="5";
    private String sonarInterval="5";
    private String flurryOfEventLogInterval="5";
    public User() {}

}
