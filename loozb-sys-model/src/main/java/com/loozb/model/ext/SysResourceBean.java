package com.loozb.model.ext;

import com.loozb.model.SysResource;

import java.util.List;

/**
 * 对资源进行扩展，增加组装树型菜单需要的字段
 * @Author： 龙召碧
 * @Date: Created in 2017-2-26 13:23
 */
public class SysResourceBean extends SysResource {
    private List<SysResourceBean> children;

    private Boolean leaf = true;

    public List<SysResourceBean> getChildren() {
        return children;
    }

    public void setChildren(List<SysResourceBean> children) {
        this.children = children;

    }
    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }
}
