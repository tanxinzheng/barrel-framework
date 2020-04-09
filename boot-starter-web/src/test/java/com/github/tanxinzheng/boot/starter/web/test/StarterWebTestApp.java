package com.github.tanxinzheng.boot.starter.web.test;

import com.github.tanxinzheng.framework.web.model.RestResponse;
import com.google.common.collect.Maps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class},
        scanBasePackages = "com.github.tanxinzheng.**")
@RestController
@RequestMapping("/test")
public class StarterWebTestApp {

    public static void main(String[] args) {
        SpringApplication.run(StarterWebTestApp.class, args);
    }

    /**
     *
     * @return
     */
    @GetMapping(value = "/data")
    public String test(){
        return "hello boot starter web";
    }

    /**
     *
     * @return
     */
    @GetMapping(value = "/object")
    public Map<String, String> testObject(){
        Map<String, String> data = Maps.newHashMap();
        data.put("id", "123456");
        data.put("name", "测试");
        return data;
    }

    /**
     *
     * @return
     */
    @GetMapping(value = "/origin")
    public RestResponse origin(){
        Map<String, String> data = Maps.newHashMap();
        data.put("id", "123456");
        data.put("name", "测试");
        RestResponse restResponse = RestResponse.success(data);
        restResponse.setKeepOriginFormat(Boolean.TRUE);
        return restResponse;
    }

}
