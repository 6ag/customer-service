package com.kefu.common.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * 实体基类
 *
 * @author feng
 * @date 2019-05-20
 */
@Data
@ToString(callSuper = true)
public class BaseEntity extends TimeEntity implements Serializable {

    private static final long serialVersionUID = 7157174985675948034L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    public static final String COL_ID = "id";
}
