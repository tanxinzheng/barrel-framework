package com.github.tanxinzheng.framework.model;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tanxinzheng on 2018/10/22.
 */
@Data
public class TreeNode<T> implements Serializable {

    private String id;
    private String parentId;
    private String label;
    private String name;
    private T value;
    private boolean hasNodes;
    private List<TreeNode> children;

    public TreeNode(String id, String name, String parentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public TreeNode(String id, String name, TreeNode parent) {
        this.id = id;
        this.name = name;
        if(parent != null){
            this.parentId = parent.id;
        }
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
        if(CollectionUtils.isNotEmpty(children)){
            this.hasNodes = Boolean.TRUE;
        }
    }
}
