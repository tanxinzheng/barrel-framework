package com.github.tanxinzheng.jwt.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.tanxinzheng.jwt.dao.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper extends BaseMapper<UserDO> {
}
