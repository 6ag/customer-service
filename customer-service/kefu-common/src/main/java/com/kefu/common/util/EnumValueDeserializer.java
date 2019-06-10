package com.kefu.common.util;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 枚举值序列化/反序列化器
 *
 * @author feng
 * @date 2019-05-23
 */
public class EnumValueDeserializer implements ObjectDeserializer, ObjectSerializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        final JSONLexer lexer = parser.lexer;
        final int token = lexer.token();
        Class cls = (Class) type;
        Object[] enumConstants = cls.getEnumConstants();
        String value;
        if (token == JSONToken.LITERAL_INT) {
            value = String.valueOf(lexer.intValue());
        } else {
            value = lexer.stringVal();
        }
        lexer.nextToken(JSONToken.COMMA);
        for (Object enumConstant : enumConstants) {
            if (enumConstant.toString().equals(value)) {
                return (T) enumConstant;
            }
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        serializer.write(object.toString());
    }

}
