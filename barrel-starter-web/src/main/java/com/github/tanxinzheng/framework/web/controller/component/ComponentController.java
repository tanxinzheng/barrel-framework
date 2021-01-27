package com.github.tanxinzheng.framework.web.controller.component;

import com.github.tanxinzheng.framework.web.dictionary.domain.DictInfoVO;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/component")
public class ComponentController {

    /**
     * 下拉组件查询
     * @param type
     * @return
     */
    @ApiOperation(value = "this is get request api")
    @GetMapping(value = "/select")
    public List<DictInfoVO> getInfo(@RequestParam(value = "type") String type){
        return Lists.newArrayList();
    }
}
