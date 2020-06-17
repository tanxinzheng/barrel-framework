package com.github.tanxinzheng.framework.utils;

import com.github.tanxinzheng.framework.model.TreeNode;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by tanxinzheng on 2019/1/12.
 */
public class TreeUtils {

    /**
     * 两层循环实现建树
     * @param treeNodes 传入的树节点列表
     * @return
     */
    public static List<TreeNode> build(List<TreeNode> treeNodes) {
        List<TreeNode> trees = Lists.newArrayList();
        for (TreeNode treeNode : treeNodes) {
            if (StringUtils.isBlank(treeNode.getParentId())) {
                trees.add(treeNode);
            }
            for (TreeNode it : treeNodes) {
                if (it.getParentId() == treeNode.getId()) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(Lists.newArrayList());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     * @param treeNodes
     * @return
     */
    public static List<TreeNode> buildByRecursive(List<TreeNode> treeNodes) {
        List<TreeNode> trees = Lists.newArrayList();
        for (TreeNode treeNode : treeNodes) {
            if (StringUtils.isBlank(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static TreeNode findChildren(TreeNode treeNode, List<TreeNode> treeNodes) {
        for (TreeNode it : treeNodes) {
            if(treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(Lists.newArrayList());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }
}
