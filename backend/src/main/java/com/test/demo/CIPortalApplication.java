package com.test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CIPortalApplication {
    public enum Day2 {
        aaa("星期一"),
        bbb("星期日");
        private String desc;
        private Day2(String desc){
            this.desc=desc;
        }
        public String getDesc(){
            return desc;
        }
    }

    public static void main(String[] args)  {
//        for (Day2 day:Day2.values()) {
//
//            System.out.println("name:"+day.name()+" ："+day.ordinal()+" desc:"+day.getDesc());
//        }
        char MIN_HIGH_SURROGATE = '\uD800';
        System.out.println(MIN_HIGH_SURROGATE);
        SpringApplication.run(CIPortalApplication.class, args);
    }
}
