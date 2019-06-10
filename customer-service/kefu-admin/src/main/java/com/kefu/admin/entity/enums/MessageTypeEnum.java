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
public enum MessageTypeEnum {
    /**
     * 消息类型 [1.文字 2.图片]
     */
    TEXT(1, "文字"),
    IMAGE(2, "图片");

    @EnumValue
    private final int value;
    private final String type;

    MessageTypeEnum(int value, String type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
