package com.github.tanxinzheng.framework.utils;

import com.github.tanxinzheng.framework.model.TreeModel;
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

    List<TreeModel> list = null;

    @Before
    public void setUp() throws Exception {
        TreeModel treeNode1 = new TreeModel("1","广州","");
        TreeModel treeNode2 = new TreeModel("2","深圳","");

        TreeModel treeNode3 = new TreeModel("3","天河区",treeNode1);
        TreeModel treeNode4 = new TreeModel("4","越秀区",treeNode1);
        TreeModel treeNode5 = new TreeModel("5","黄埔区",treeNode1);
        TreeModel treeNode6 = new TreeModel("6","石牌",treeNode3);
        TreeModel treeNode7 = new TreeModel("7","百脑汇",treeNode6);

        TreeModel treeNode8 = new TreeModel("8","南山区",treeNode2);
        TreeModel treeNode9 = new TreeModel("9","宝安区",treeNode2);
        TreeModel treeNode10 = new TreeModel("10","科技园",treeNode8);

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
        List<TreeModel> trees = TreeUtils.build(list);
        Assert.assertTrue("二层循环建树", trees.size() > 0);
        Assert.assertTrue("二层循环建树", trees.get(0).getChildren().size() > 0);
    }

    @Test
    public void buildByRecursive() throws Exception {
        List<TreeModel> trees_ = TreeUtils.buildByRecursive(list);
        Assert.assertTrue("二层循环建树", trees_.size() > 0);
        Assert.assertTrue("二层循环建树", trees_.get(0).getChildren().size() > 0);
    }

}