package com.github.tanxinzheng.jwt.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.tanxinzheng.jwt.dao.entity.PermissionDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthPermissionMapper extends BaseMapper<PermissionDO> {

    List<PermissionDO> selectAllProtectPermission();

}
