package com.test.demo.DataBean.Platform.dao;

import com.test.demo.DataBean.Platform.Interface.PlatformDao;
import com.test.demo.DataBean.Platform.bean.GitLab;
import com.test.demo.DataBean.Platform.Interface.Platforms;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GitLabDao implements PlatformDao {
    @Autowired
    private GitLabRepository gitLabRepository;
    public GitLab byName(String name){
        return gitLabRepository.findByName(name);
    }
    public GitLab saveAndUpdate(Platforms platforms){
        GitLab gitLab = (GitLab) platforms;
        return gitLabRepository.save(gitLab);
    }

    @Override
    public Platforms platformFindAndUpdate(String name, JSONObject setting) {
        GitLab gitLab = byName(name);
        if(gitLab == null){
            gitLab = new GitLab();
            gitLab.setName(name);
        }
        gitLab.setAllPlatformsParams(setting);
        saveAndUpdate(gitLab);
        return gitLab;
    }
}
