package com.test.demo.User;

import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Component;

import javax.persistence.Id;

@Component
@Setter
@Getter
public class Users {
    @Id
    private String id;
    private String account;
    private String password;
    private JSONArray allProjects = new JSONArray();
    private JSONArray chosenProjects = new JSONArray();
    private JSONArray boardChoice = new JSONArray();
    private int authority = 1;
}
