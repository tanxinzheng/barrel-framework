package com.github.tanxinzheng;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisTestServiceImpl implements RedisTestService {



    @Override
    public DemoDTO getUser(String id) {
        log.info("缓存穿透，id: {}", id);
        DemoDTO demoDTO = new DemoDTO();
        demoDTO.setId(id);
        demoDTO.setAge(18);
        demoDTO.setName("谭先生");
        return null;
    }

    @Override
    public void deleteUser(String id) {
        log.info("删除成功, id: {}", id);
    }
}
