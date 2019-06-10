package com.kefu.admin.netty.attribute;

import com.kefu.admin.entity.User;

import io.netty.util.AttributeKey;

/**
 * 用于缓存到 channel 中的属性的键
 *
 * @author feng
 * @date 2019-04-20
 */
public interface Attributes {

    AttributeKey<User> USER_ATTRIBUTE_KEY = AttributeKey.newInstance("user");
}
