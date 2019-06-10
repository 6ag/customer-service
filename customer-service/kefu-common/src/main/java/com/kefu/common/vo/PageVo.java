package com.kefu.common.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 分页工具类
 */
@Data
public class PageVo<T> implements Serializable {

    private static final long serialVersionUID = 784152205848439588L;

    /**
     * 当前页数
     */
    private long currentPage;

    /**
     * 每页记录数
     */
    private long pageSize;

    /**
     * 总记录数
     */
    private long totalCount;

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 列表数据
     */
    private List<T> list;

}
