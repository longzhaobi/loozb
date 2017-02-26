package com.loozb.model.ext;

import java.util.List;
import java.util.Set;

/**
 * 权限
 * @Author： 龙召碧
 * @Date: Created in 2017-2-10 0:35
 */
public class Authority {
    /**
     * 拥有角色
     */
    private Set<String> hasRoles;

    /**
     * 拥有权限
     */
    private Set<String> hasPermissions;

    /**
     * 拥有菜单资源
     */
    private List<SysResourceBean> hasMenus;

    public Authority() {
    };

    public Authority(Set<String> hasRoles, Set<String> hasPermissions, List<SysResourceBean> hasMenus) {
        this.hasRoles = hasRoles;
        this.hasPermissions = hasPermissions;
        this.hasMenus = hasMenus;
    }

    public Set<String> getHasRoles() {
        return hasRoles;
    }

    public void setHasRoles(Set<String> hasRoles) {
        this.hasRoles = hasRoles;
    }

    public Set<String> getHasPermissions() {
        return hasPermissions;
    }

    public void setHasPermissions(Set<String> hasPermissions) {
        this.hasPermissions = hasPermissions;
    }

    public List<SysResourceBean> getHasMenus() {
        return hasMenus;
    }

    public void setHasMenus(List<SysResourceBean> hasMenus) {
        this.hasMenus = hasMenus;
    }
}