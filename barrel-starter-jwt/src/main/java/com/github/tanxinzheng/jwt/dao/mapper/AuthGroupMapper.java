package com.github.tanxinzheng.jwt.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.tanxinzheng.jwt.dao.entity.GroupDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthGroupMapper extends BaseMapper<GroupDO> {

    List<GroupDO> selectCurrentUserGroups(@Param(value = "userId") String userId);
}
