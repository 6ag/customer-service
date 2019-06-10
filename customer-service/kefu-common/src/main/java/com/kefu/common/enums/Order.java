package com.kefu.common.enums;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.kefu.common.util.EnumValueDeserializer;

import lombok.Getter;

/**
 * 排序方式
 *
 * @author feng
 * @date 2019-05-23
 */
@Getter
@JSONType(deserializer = EnumValueDeserializer.class, serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
public enum Order implements IEnum {
    /**
     * 按指定列升序排序
     */
    ASC("ASC"),
    /**
     * 按指定列降序排序
     */
    DESC("DESC");

    private String value;

    Order(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
