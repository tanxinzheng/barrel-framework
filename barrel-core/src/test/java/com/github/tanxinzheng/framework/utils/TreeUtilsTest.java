package com.github.tanxinzheng.framework.utils;

import com.github.tanxinzheng.framework.model.TreeNode;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by tanxinzheng on 2019/1/12.
 */
public class TreeUtilsTest {

    List<TreeNode> list = null;

    @Before
    public void setUp() throws Exception {
        TreeNode treeNode1 = new TreeNode("1","广州","");
        TreeNode treeNode2 = new TreeNode("2","深圳","");

        TreeNode treeNode3 = new TreeNode("3","天河区",treeNode1);
        TreeNode treeNode4 = new TreeNode("4","越秀区",treeNode1);
        TreeNode treeNode5 = new TreeNode("5","黄埔区",treeNode1);
        TreeNode treeNode6 = new TreeNode("6","石牌",treeNode3);
        TreeNode treeNode7 = new TreeNode("7","百脑汇",treeNode6);

        TreeNode treeNode8 = new TreeNode("8","南山区",treeNode2);
        TreeNode treeNode9 = new TreeNode("9","宝安区",treeNode2);
        TreeNode treeNode10 = new TreeNode("10","科技园",treeNode8);

        list = Lists.newArrayList();

        list.add(treeNode1);
        list.add(treeNode2);
        list.add(treeNode3);
        list.add(treeNode4);
        list.add(treeNode5);
        list.add(treeNode6);
        list.add(treeNode7);
        list.add(treeNode8);
        list.add(treeNode9);
        list.add(treeNode10);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void build() throws Exception {
        List<TreeNode> trees = TreeUtils.build(list);
        Assert.assertTrue("二层循环建树", trees.size() > 0);
        Assert.assertTrue("二层循环建树", trees.get(0).getChildren().size() > 0);
    }

    @Test
    public void buildByRecursive() throws Exception {
        List<TreeNode> trees_ = TreeUtils.buildByRecursive(list);
        Assert.assertTrue("二层循环建树", trees_.size() > 0);
        Assert.assertTrue("二层循环建树", trees_.get(0).getChildren().size() > 0);
    }

}