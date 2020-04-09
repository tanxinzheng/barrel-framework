package com.github.tanxinzheng.boot.starter.web.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.tanxinzheng.framework.web.model.RestResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StarterWebTestApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebTest {

    /**
     * Interface to provide configuration for a web application.
     */
    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    /**
     * 初始化 MVC 的环境
     */
    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void testString() throws Exception {
        ResultActions actions = mockMvc.perform(get("/test/data")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        String resultJson = actions.andReturn().getResponse().getContentAsString();
        RestResponse restResponse = JSONObject.parseObject(resultJson, new TypeReference<RestResponse<String>>(){});
        Assert.assertEquals("hello boot starter web", restResponse.getData());
    }

    @Test
    public void testObject() throws Exception {
        ResultActions actions = mockMvc.perform(get("/test/object")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
        String resultJson = actions.andReturn().getResponse().getContentAsString();
        RestResponse<Map<String, String>> restResponse = JSONObject.parseObject(resultJson, new TypeReference<RestResponse<Map<String, String>>>(){});
        Assert.assertEquals("测试", restResponse.getData().get("name"));
    }

    @Test
    public void testOrigin() throws Exception {
        ResultActions actions = mockMvc.perform(get("/test/origin")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
        String resultJson = actions.andReturn().getResponse().getContentAsString();
        Map<String, String> data = JSONObject.parseObject(resultJson, Map.class);
        Assert.assertEquals("测试", data.get("name"));
    }


}
