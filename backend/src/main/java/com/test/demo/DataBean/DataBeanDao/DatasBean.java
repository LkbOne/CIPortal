package com.test.demo.DataBean.DataBeanDao;


import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.persistence.Id;


@Component
@Getter
@Setter
public class DatasBean {
    @Id
    private String id;
    private String name;
//    private JSONArray datas;
    private String datas;
//    private JSONObject setting;
//    public boolean getBugs(){
//        if(setting.has("bug")){
//            return setting.getBoolean("bug");
//        }
//        return false;
//    }
//    public void setBugs(boolean flag){
//        setting.element("bug",flag);
//    }
}
