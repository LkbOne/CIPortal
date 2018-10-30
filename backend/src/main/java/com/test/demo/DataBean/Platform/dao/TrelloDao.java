package com.test.demo.DataBean.Platform.dao;

import com.test.demo.DataBean.Platform.Interface.PlatformDao;
import com.test.demo.DataBean.Platform.Interface.Platforms;
import com.test.demo.DataBean.Platform.bean.Trello;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

@Repository
public class TrelloDao implements PlatformDao {
    @Autowired
    private TrelloRepository trelloRepository;

    public Trello byName(String name){
        return trelloRepository.findByName(name);
    }
    public Trello saveAndUpdate(Platforms platforms){
        Trello trello = (Trello) platforms;
        return trelloRepository.save(trello);
    }

    @Override
    public Platforms platformFindAndUpdate(String name, JSONObject setting) {
        Trello trello = byName(name);
        if(trello==null){
            trello = new Trello();
            trello.setName(name);
        }
        trello = trello.setAllPlatformsParams(setting);
        saveAndUpdate(trello);
        return trello;
    }
}
