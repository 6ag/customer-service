package com.kefu.common.util.tree;

import java.util.List;

/**
 * 树结构接口
 * 实现这个接口后，可以调用TreeUtils构建树结构
 *
 * @author feng
 * @date 2019-05-30
 */
public interface ITree<T extends ITree> {

    /**
     * 获取节点id
     *
     * @return
     */
    Integer getId();

    /**
     * 获取父节点id
     *
     * @return
     */
    Integer getParentId();

    /**
     * 设置子节点集合
     *
     * @param children
     */
    void setChildren(List<T> children);

    /**
     * 获取子节点集合
     *
     * @return
     */
    List<T> getChildren();
}
