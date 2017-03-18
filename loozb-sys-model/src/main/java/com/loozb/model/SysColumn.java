package com.loozb.model;

import java.io.Serializable;

/**
 * Columns
 * @Author： 龙召碧
 * @Date: Created in 2017-3-12 14:35
 */
public class SysColumn implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *字段名
     */
    private String columnName;
    /**
     *表名
     */
    private String tableName;
    /**
     *默认值
     */
    private String columnDefault;
    /**
     *是否为空，NO / YES
     */
    private String isNullable;
    /**
     *字段类型
     */
    private String columnType;
    /**
     *主键
     */
    private String columnKey;
    /**
     *备注
     */
    private String columnComment;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
}
