package com.test.demo.DataBean.Platform.dao;

import com.test.demo.DataBean.Platform.Interface.PlatformRepository;
import com.test.demo.DataBean.Platform.bean.GitLab;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GitLabRepository extends MongoRepository<GitLab, String> {
    GitLab findByName(String name);
}
