package com.loozb.model.ext;

import java.io.Serializable;

/**
 * @Author： 龙召碧
 * @Date: Created in 2017-3-9 21:05
 */
public class Columns implements Serializable {

    private static final long serialVersionUID = 1L;
    private String key;

    private String title;

    private String dataIndex;

    private String className;

    private String width;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
