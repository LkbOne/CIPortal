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
    private String datas;
}
