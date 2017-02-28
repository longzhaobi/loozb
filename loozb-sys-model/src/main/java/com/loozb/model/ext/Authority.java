package com.loozb.model.ext;

import com.loozb.model.SysUser;

import java.util.List;
import java.util.Set;

/**
 * 权限
 * @Author： 龙召碧
 * @Date: Created in 2017-2-10 0:35
 */
public class Authority {

    private String token;
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

    /**
     * 用户信息
     */
    private SysUser sysUser;

    public Authority() {
    };

    public Authority(String token, Set<String> hasRoles, Set<String> hasPermissions, List<SysResourceBean> hasMenus,SysUser sysUser) {
        this.token = token;
        this.hasRoles = hasRoles;
        this.hasPermissions = hasPermissions;
        this.hasMenus = hasMenus;
        this.sysUser = sysUser;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "token='" + token + '\'' +
                ", hasRoles=" + hasRoles +
                ", hasPermissions=" + hasPermissions +
                ", hasMenus=" + hasMenus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority = (Authority) o;

        if (token != null ? !token.equals(authority.token) : authority.token != null) return false;
        if (hasRoles != null ? !hasRoles.equals(authority.hasRoles) : authority.hasRoles != null) return false;
        if (hasPermissions != null ? !hasPermissions.equals(authority.hasPermissions) : authority.hasPermissions != null)
            return false;
        return hasMenus != null ? hasMenus.equals(authority.hasMenus) : authority.hasMenus == null;

    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + (hasRoles != null ? hasRoles.hashCode() : 0);
        result = 31 * result + (hasPermissions != null ? hasPermissions.hashCode() : 0);
        result = 31 * result + (hasMenus != null ? hasMenus.hashCode() : 0);
        return result;
    }
}