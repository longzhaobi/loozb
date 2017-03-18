package com.loozb.model;

import java.io.Serializable;

/**
 * 表名和表注释
 * @Author： 龙召碧
 * @Date: Created in 2017-3-12 13:51
 */
public class SysTable implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
}
