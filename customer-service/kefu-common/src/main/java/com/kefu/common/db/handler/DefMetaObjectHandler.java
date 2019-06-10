package com.kefu.common.db.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * MyBatisPlus自动填充处理器
 *
 * @author feng
 * @date 2019-05-23
 */
@Slf4j
@Component
public class DefMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insertFill");
        this.setFieldValByName("createdAt", new Date(), metaObject);
        this.setFieldValByName("updatedAt", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("updateFill");
        this.setFieldValByName("updatedAt", new Date(), metaObject);
    }
}
