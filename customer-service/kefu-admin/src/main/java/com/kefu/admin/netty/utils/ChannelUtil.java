package com.kefu.admin.netty.utils;

import com.kefu.admin.entity.User;
import com.kefu.admin.netty.attribute.Attributes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * channel工具类
 *
 * @author feng
 * @date 2019-04-21
 */
@Slf4j
public class ChannelUtil {

    /**
     * username -> Channel 的映射集合
     */
    private static final Map<String, Channel> USER_ID_CHANNEL_MAP = new ConcurrentHashMap<>();

    /**
     * 登录成功后缓存【用户 -> 用户连接】的映射关系
     *
     * @param user    用户对象
     * @param channel 连接
     */
    public static void bindUser(User user, Channel channel) {
        log.info("缓存【username:channel】映射,username={},channel={}", user.getUsername(), channel.toString());
        USER_ID_CHANNEL_MAP.put(user.getUsername(), channel);
        channel.attr(Attributes.USER_ATTRIBUTE_KEY).set(user);
    }

    /**
     * 退出登录或断开连接后清除【用户 -> 用户连接】的映射关系
     *
     * @param channel 连接
     */
    public static void unBindUser(Channel channel) {
        if (hasLogin(channel)) {
            log.info("移除【username:channel】映射,username={},channel={}", getUser(channel).getUsername(), channel.toString());
            USER_ID_CHANNEL_MAP.remove(getUser(channel).getUsername());
            channel.attr(Attributes.USER_ATTRIBUTE_KEY).set(null);
        }
    }

    /**
     * 根据连接判断是否已经登录
     *
     * @param channel 连接
     * @return true 则表示已登录
     */
    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.USER_ATTRIBUTE_KEY);
    }

    /**
     * 根据连接获取用户对象
     *
     * @param channel 连接
     * @return 会话身份信息
     */
    public static User getUser(Channel channel) {
        return channel.attr(Attributes.USER_ATTRIBUTE_KEY).get();
    }

    /**
     * 根据username获取连接
     *
     * @param username 用户名
     * @return 连接
     */
    public static Channel getChannel(String username) {
        return USER_ID_CHANNEL_MAP.get(username);
    }

}
