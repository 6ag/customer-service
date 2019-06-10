package com.kefu.admin.common.jwt;

import com.kefu.admin.entity.User;
import com.kefu.admin.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author feng
 * @date 2019-05-23
 */
@Service
public class JwtUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * JWT通过用户名加载用户
     *
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null || StringUtils.isEmpty(user.getId())) {
            throw new UsernameNotFoundException(String.format("【%s】用户不存在", username));
        }
        // 这里返回的JwtUser还不包含authorities
        return new JwtUser(user.getId(), user.getStatus(), user.getUsername(), user.getPassword());
    }
}
