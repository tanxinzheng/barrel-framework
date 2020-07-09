package com.github.tanxinzheng;

import com.github.tanxinzheng.framework.web.annotation.LoginUser;
import com.github.tanxinzheng.framework.web.model.CurrentLoginUser;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "com.github.tanxinzheng.**")
@MapperScan("com.github.tanxinzheng.**.mapper")
@RestController
@RequestMapping("/test")
public class AppTestRun {

    public static void main(String[] args) {
        SpringApplication.run(AppTestRun.class, args);
    }

    /**
     * 查询当前用户简要信息
     * @param loginUser
     * @return
     */
    @GetMapping(value = "/currentUser")
    @ApiOperation(value = "查询当前用户资料信息")
    public CurrentLoginUser accountSetting(@LoginUser CurrentLoginUser loginUser){
        return loginUser;
    }

    /**
     * 匿名可访问资源
     * @return
     */
    @GetMapping(value = "/anonymous")
    @ApiOperation(value = "匿名可访问资源")
    public String anonymous(){
        return "成功访问匿名资源";
    }

    /**
     * 已保护[user]资源
     * @return
     */
    @GetMapping(value = "/protect")
    @ApiOperation(value = "已保护[user]资源")
    public String protectUser(){
        return "成功访问已保护[user]资源";
    }

    /**
     * 已保护[管理员]资源
     * @return
     */
    @GetMapping(value = "/protect/admin")
    @ApiOperation(value = "已保护[admin]资源")
    public String protectAdmin(){
        return "成功访问已保护[admin]资源";
    }

    /**
     * 已保护[tester]资源
     * @return
     */
    @GetMapping(value = "/protect/tester")
    @ApiOperation(value = "已保护[tester]资源")
    public String protectTest(){
        return "成功访问已保护[tester]资源";
    }

}
