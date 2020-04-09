package com.github.tanxinzheng.jwt.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.tanxinzheng.framework.core.service.CurrentUserService;
import com.github.tanxinzheng.jwt.dao.entity.GroupDO;
import com.github.tanxinzheng.jwt.dao.entity.UserDO;
import com.github.tanxinzheng.jwt.dao.mapper.AuthGroupMapper;
import com.github.tanxinzheng.jwt.dao.mapper.AuthMapper;
import com.github.tanxinzheng.jwt.dao.mapper.AuthPermissionMapper;
import com.github.tanxinzheng.jwt.support.JwtUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.List;
import java.util.Set;

@Component
public class AuthManagerImpl implements CurrentUserService, UserDetailsService {

    @Resource
    AuthMapper authMapper;
    @Resource
    AuthPermissionMapper authPermissionMapper;
    @Resource
    AuthGroupMapper authGroupMapper;

    @Override
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            return null;
        }
        JwtUser jwtUser = (JwtUser) authentication.getDetails();
        return jwtUser.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = authMapper.selectOne(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getUsername, username));
        if(userDO == null){
            return null;
        }
        JwtUser jwtUser = new JwtUser();
        jwtUser.setAccountNonExpired(Boolean.TRUE);
        jwtUser.setAccountNonLocked(Boolean.TRUE);
        jwtUser.setCredentialsNonExpired(Boolean.TRUE);
        jwtUser.setId(userDO.getId());
        jwtUser.setSalt(userDO.getSalt());
        jwtUser.setEmail(userDO.getEmail());
        jwtUser.setName(userDO.getName());
        jwtUser.setPassword(userDO.getPassword());
        jwtUser.setUsername(userDO.getUsername());
        // 查询登录用户的用户组
        List<GroupDO> groupDOList = authGroupMapper.selectCurrentUserGroups(userDO.getId());
        Set<String> roles = Sets.newHashSet("ROLE_USER");
        if(CollectionUtils.isNotEmpty(groupDOList)){
            groupDOList.stream().forEach(groupDO -> {
                roles.add(groupDO.getGroupCode());
            });
        }
        jwtUser.setRoles(roles);
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = Lists.newArrayList();
        for (String role : jwtUser.getRoles()) {
            simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(role));
        }
        jwtUser.setAuthorities(simpleGrantedAuthorityList);
        return jwtUser;
    }
}
