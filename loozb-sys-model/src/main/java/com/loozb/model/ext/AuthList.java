package com.loozb.model.ext;

import java.io.Serializable;
import java.util.List;

/**
 * @Author： 龙召碧
 * @Date: Created in 2017-3-9 22:18
 */
public class AuthList implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;

    private String isAuth;

    private String disable;

    private String key;

    private String value;

    private List<AuthList> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }

    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
    }

    public List<AuthList> getChildren() {
        return children;
    }

    public void setChildren(List<AuthList> children) {
        this.children = children;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
