package com.kefu.common.util.tree;


import java.util.ArrayList;
import java.util.List;

/**
 * 树结构工具
 *
 * @author feng
 * @date 2019-05-30
 */
public class TreeUtils {

    /**
     * 根据id/parentId/children递归创建树结构
     *
     * @param allNodes
     * @return
     */
    public static <T extends ITree> List<T> findRoots(List<T> allNodes) {
        // 根节点
        List<T> root = new ArrayList<>();
        allNodes.forEach(node -> {
            if (node.getParentId() == 0) {
                root.add(node);
            }
        });
        // 递归查找子节点
        root.forEach(node -> findChildren(node, allNodes));
        return root;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    private static <T extends ITree> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

}
