package com.loozb.core.base;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author： 龙召碧
 * @Date: Created in 2017-2-25 19:51
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseModel implements Serializable {
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    private String available;
    @TableField("create_id")
    private Long createId;
    private Date ctime;
    private Date mtime;

    public Long getId() {
        return id;
    }

    public String getId_() {
        return id == null ? "" : id.toString();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
}

