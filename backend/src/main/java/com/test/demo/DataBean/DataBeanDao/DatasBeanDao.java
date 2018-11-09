package com.test.demo.DataBean.DataBeanDao;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DatasBeanDao {
    @Autowired
    DatasBeanRepository datasBeanRepository;
    public DatasBean datasBeanByName(String name){
        return datasBeanRepository.findByName(name);
    }
    public DatasBean datasBeanSaveAndUpdate(DatasBean datasBean){
        return datasBeanRepository.save(datasBean);
    }
    public void datasBeanFindAndUpdate(String name,JSONArray array){
        DatasBean datasBean = datasBeanByName(name);
        if(datasBean == null){
            datasBean = new DatasBean();
            datasBean.setName(name);
        }
        datasBean.setDatas(array.toString());
        datasBeanSaveAndUpdate(datasBean);
    }
}
