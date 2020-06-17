package com.github.tanxinzheng.jwt.handler;

import com.github.tanxinzheng.jwt.access.PermissionGrantedAuthority;
import com.github.tanxinzheng.jwt.dao.entity.PermissionAction;
import com.github.tanxinzheng.jwt.dao.entity.PermissionDO;
import com.github.tanxinzheng.jwt.dao.mapper.AuthPermissionMapper;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tanxinzheng on 2018/10/22.
 */
@Component
public class TokenSecurityMetadataHandler implements SecurityMetadataHandler {

    @Resource
    AuthPermissionMapper authPermissionMapper;

    /**
     * 加载所有权限
     *
     * @return
     */
    @Cacheable(value = "ALL_GROUP_PERMISSIONS")
    @Override
    public List<PermissionGrantedAuthority> loadAllPermission() {
        List<PermissionDO> permissionDOList = authPermissionMapper.selectAllProtectPermission();
        if(CollectionUtils.isEmpty(permissionDOList)){
            return Lists.newArrayList();
        }
        List<PermissionGrantedAuthority> permissionGrantedAuthorityList = Lists.newArrayList();
        Map<String, List<PermissionDO>> map = permissionDOList.stream().collect(Collectors.groupingBy(PermissionDO::getId));
        for (List<PermissionDO> value : map.values()) {
            PermissionGrantedAuthority permissionGrantedAuthority = new PermissionGrantedAuthority();
            permissionGrantedAuthority.setUrl(value.get(0).getPermissionUrl());
            permissionGrantedAuthority.setRequestMethod(PermissionAction.valueOf(value.get(0).getPermissionAction()).getRequestMethod());
            List<String> data = Lists.newArrayList();
            for (PermissionDO permissionDO : value) {
                data.add(permissionDO.getGroupCode());
            }
            permissionGrantedAuthority.setRoles(data);
            permissionGrantedAuthorityList.add(permissionGrantedAuthority);
        }
        return permissionGrantedAuthorityList;
    }
}
