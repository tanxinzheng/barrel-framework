package com.github.tanxinzheng.boot.starter.web.test;

import com.github.tanxinzheng.boot.starter.web.test.domain.DemoResponse;
import com.github.tanxinzheng.framework.web.model.RestResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    /**
     *
     * @return
     */
    @GetMapping(value = "/dictionary")
    public List<DemoResponse> getDemoList(){
        DemoResponse demoModel = new DemoResponse();
        demoModel.setId(UUID.randomUUID().toString());
        demoModel.setSex("W");
        demoModel.setUserId("1");
        demoModel.setDisable(Boolean.FALSE.toString());
        DemoResponse demoModel2 = new DemoResponse();
        demoModel2.setId(UUID.randomUUID().toString());
        demoModel2.setSex("M");
        demoModel2.setUserId("2");
        demoModel2.setDisable(Boolean.TRUE.toString());
        return Lists.newArrayList(demoModel, demoModel2);
    }
}
