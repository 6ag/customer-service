package com.kefu.admin.entity.enums;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.kefu.common.util.EnumValueDeserializer;

import lombok.Getter;

/**
 * @author feng
 * @date 2019-05-22
 */
@Getter
@JSONType(deserializer = EnumValueDeserializer.class, serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
public enum UserStatusEnum {
    /**
     * 状态 [0.禁用 1.正常 2.已删除]
     */
    DISABLE(0, "禁用"),
    NORMAL(1, "正常"),
    DELETE(2, "已删除");

    @EnumValue
    private final int value;
    private final String status;

    UserStatusEnum(int value, String status) {
        this.value = value;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
