package com.kefu.common.db.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kefu.common.enums.Order;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import lombok.Data;

/**
 * 分页查询vo，暂时这样写，后面会封装基类和枚举，并处理一些约束
 *
 * @author feng
 * @date 2019-05-20
 */
@Data
public class PageDto<T> extends Page<T> implements Serializable {

    private static final long serialVersionUID = -4864447250966063233L;

    /**
     * 当前页
     */
    private Integer page = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String orderByField = "id";

    /**
     * 排序方式
     */
    private Order order = Order.DESC;

    public PageDto() {
        setDesc(orderByField);
    }

    public void setPage(Integer page) {
        if (page > 0) {
            this.page = page;
        }
        setCurrent(this.page);
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
        setSize(this.pageSize);
    }

    public void setOrderByField(String orderByField) {
        this.orderByField = orderByField;
        setPageOrder();
    }

    public void setOrder(Order order) {
        this.order = order;
        setPageOrder();
    }

    /**
     * 设置分页排序
     */
    private void setPageOrder() {
        if (StringUtils.isNotEmpty(orderByField)) {
            switch (order) {
                case ASC:
                    setAsc(orderByField);
                    setDesc(null);
                    break;
                case DESC:
                    setDesc(orderByField);
                    setAsc(null);
                    break;
                default:
                    break;
            }
        }
    }
}
