package com.github.tanxinzheng;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.tanxinzheng.framework.model.Result;
import com.github.tanxinzheng.jwt.controller.dto.LoginRequest;
import com.github.tanxinzheng.jwt.controller.dto.LoginResponse;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppTestRun.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTest {
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
    public void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin2");
        loginRequest.setPassword("admin123");
        ResultActions actions = mockMvc.perform(post("/system/login")
                .content(JSONObject.toJSONString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
        String resultJson = actions.andReturn().getResponse().getContentAsString();
        Result<LoginResponse> result = JSONObject.parseObject(resultJson, new TypeReference<Result<LoginResponse>>(){});
        Assert.assertEquals("该用户名未注册", result.getMessage());
    }

    @Test
    public void testLogin2() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin2");
        loginRequest.setPassword("admin123");
        ResultActions actions = mockMvc.perform(post("/system/login")
                .content(JSONObject.toJSONString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
        String resultJson = actions.andReturn().getResponse().getContentAsString();
        Result<LoginResponse> result = JSONObject.parseObject(resultJson, new TypeReference<Result<LoginResponse>>(){});
        Assert.assertEquals("未注册用户名测试", "该用户名未注册", result.getMessage());
    }

    @Test
    public void testLogin3() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("111111");
        ResultActions action = mockMvc.perform(post("/system/login")
                .content(JSONObject.toJSONString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
        String resultJson = action.andReturn().getResponse().getContentAsString();
        Result<LoginResponse> result = JSONObject.parseObject(resultJson, new TypeReference<Result<LoginResponse>>(){});
        Assert.assertEquals("用户名或密码不正确", "用户名或密码不正确", result.getMessage());
    }

    @Test
    public void testLogin4() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("123456");
        ResultActions action = mockMvc.perform(post("/system/login")
                .content(JSONObject.toJSONString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
        String resultJson = action.andReturn().getResponse().getContentAsString();
        Result<LoginResponse> result = JSONObject.parseObject(resultJson, new TypeReference<Result<LoginResponse>>(){});
        Assert.assertNotNull("登录成功", result.getData().getAccessToken());
        Assert.assertEquals("登录成功", "admin", result.getData().getUsername());
    }

    @Test
    public void testLogin5() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("123456");
        ResultActions action = mockMvc.perform(post("/system/login")
                .content(JSONObject.toJSONString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
        String resultJson = action.andReturn().getResponse().getContentAsString();
        Result<LoginResponse> result = JSONObject.parseObject(resultJson, new TypeReference<Result<LoginResponse>>(){});
        Assert.assertEquals("请输入用户名", result.getMessage());

        loginRequest.setPassword(null);
        loginRequest.setUsername("admin");
        ResultActions action2 = mockMvc.perform(post("/system/login")
                .content(JSONObject.toJSONString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
        String resultJson2 = action2.andReturn().getResponse().getContentAsString();
        Result<LoginResponse> result2 = JSONObject.parseObject(resultJson2, new TypeReference<Result<LoginResponse>>(){});
        Assert.assertEquals("请输入密码", result2.getMessage());
    }


}
