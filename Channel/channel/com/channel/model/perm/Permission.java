package com.channel.model.perm;

import com.common.utils.tree.model.TreeNode;

/**
 * Created by 25019 on 2015/12/9.
 */
public class Permission extends TreeNode {
    private String permlimit;
    private Integer xzqh;

    public String getPermlimit() {
        return permlimit;
    }

    public void setPermlimit(String permlimit) {
        this.permlimit = permlimit;
    }

    public Integer getXzqh() {
        return xzqh;
    }

    public void setXzqh(Integer xzqh) {
        this.xzqh = xzqh;
    }

    public Permission() {

    }

    public Permission(int id) {
        setId(id);
    }

}
