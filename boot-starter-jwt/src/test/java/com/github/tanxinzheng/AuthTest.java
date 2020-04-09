package com.github.tanxinzheng;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.tanxinzheng.framework.web.model.CurrentLoginUser;
import com.github.tanxinzheng.framework.web.model.RestResponse;
import com.github.tanxinzheng.jwt.config.JwtConfigProperties;
import com.github.tanxinzheng.jwt.controller.dto.LoginRequest;
import com.github.tanxinzheng.jwt.controller.dto.LoginResponse;
import com.github.tanxinzheng.jwt.exception.AuthErrorCode;
import com.github.tanxinzheng.jwt.support.TokenType;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Unit test for simple App.
 */
public class AuthTest extends JwtBaseTest {

    @Before
    public void before() throws MalformedURLException {
        super.setUp();
    }

    @Test
    public void testJwtLoginSuccess() throws Exception {
        headerLogin();
        String data =  testRestTemplate.getForObject(this.base.toString() + "/test/currentUser", String.class);
        RestResponse<CurrentLoginUser> response = JSONObject.parseObject(data, new TypeReference<RestResponse<CurrentLoginUser>>(){});
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getData());
        Assert.assertEquals("admin", response.getData().getUsername());
    }

    @Test
    public void testProtectAccess() throws Exception {
        headerLogin("Tom");
        String data = testRestTemplate.getForObject(this.base.toString() + "/test/protect", String.class);
        RestResponse<String> response = JSONObject.parseObject(data, new TypeReference<RestResponse<String>>(){});
        Assert.assertNotNull(response);
        Assert.assertEquals("测试访问已保护资源", "成功访问已保护[user]资源", response.getData());
    }

    @Test
    public void testTestUserProtectAccess() throws Exception {
        String data = testRestTemplate.getForObject(this.base.toString() + "/test/protect", String.class);
        System.out.println(data);
        RestResponse<String> response = JSONObject.parseObject(data, new TypeReference<RestResponse<String>>(){});
        Assert.assertNotNull(response);
        Assert.assertEquals("测试访问已保护资源", AuthErrorCode.UNAUTHORIZED.getErrorCode(), response.getCode());
    }

    @Test
    public void testTesterProtectAccess() throws Exception {
        headerLogin("tester");
        String data = testRestTemplate.getForObject(this.base.toString() + "/test/protect/tester", String.class);
        System.out.println(data);
        RestResponse<String> response = JSONObject.parseObject(data, new TypeReference<RestResponse<String>>(){});
        Assert.assertNotNull(response);
        Assert.assertEquals("200", response.getCode());
        Assert.assertEquals("使用[tester]账号测试访问已保护[tester]资源", "成功访问已保护[tester]资源", response.getData());
    }

    @Test
    public void testTesterProtectAccess02() throws Exception {
        headerLogin("admin");
        String data = testRestTemplate.getForObject(this.base.toString() + "/test/protect/tester", String.class);
        System.out.println(data);
        RestResponse<String> response = JSONObject.parseObject(data, new TypeReference<RestResponse<String>>(){});
        Assert.assertNotNull(response);
        Assert.assertEquals("200", response.getCode());
        Assert.assertEquals("使用[admin]账号测试访问已保护[tester]资源", "成功访问已保护[tester]资源", response.getData());
    }

    @Test
    public void testAdminProtectAccess() throws Exception {
        headerLogin("admin");
        String data = testRestTemplate.getForObject(this.base.toString() + "/test/protect/admin", String.class);
        System.out.println(data);
        RestResponse<String> response = JSONObject.parseObject(data, new TypeReference<RestResponse<String>>(){});
        Assert.assertNotNull(response);
        Assert.assertEquals("200", response.getCode());
        Assert.assertEquals("使用[admin]账号测试访问已保护[admin]资源", "成功访问已保护[admin]资源", response.getData());
    }

    @Test
    public void testAdminProtectAccess02() throws Exception {
        headerLogin("tester");
        String data = testRestTemplate.getForObject(this.base.toString() + "/test/protect/admin", String.class);
        System.out.println(data);
        RestResponse<String> response = JSONObject.parseObject(data, new TypeReference<RestResponse<String>>(){});
        Assert.assertNotNull(response);
        Assert.assertEquals("使用[tester]账号测试访问已保护[admin]资源", "403", response.getCode());
        Assert.assertEquals("使用[tester]账号测试访问已保护[admin]资源", "forbidden", response.getMessage());
    }

    @Test
    public void testAnonymousAccess() throws Exception {
        String data = testRestTemplate.getForObject(this.base.toString() + "/test/anonymous", String.class);
        System.out.println(data);
        RestResponse<String> response = JSONObject.parseObject(data, new TypeReference<RestResponse<String>>(){});
        Assert.assertNotNull(response);
        Assert.assertEquals("测试匿名访问资源", "成功访问匿名资源", response.getData());
    }

}
